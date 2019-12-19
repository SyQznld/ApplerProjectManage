package com.example.q_kang.pmsystem.ui.adpter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.search.sug.SuggestionResult;
import com.example.q_kang.pmsystem.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by appler on 2018/8/13.
 */

public class MapShowAdressAdapter extends android.widget.BaseAdapter implements Filterable {

    private Context context;
    private List<SuggestionResult.SuggestionInfo> poiData;

    private ArrayFilter mFilter;

    private final Object mLock = new Object();
    private ArrayList<SuggestionResult.SuggestionInfo> mOriginalValues;

    public MapShowAdressAdapter(Context context, List<SuggestionResult.SuggestionInfo> poiData) {
        this.context = context;
        this.poiData = poiData;
    }

    @Override
    public int getCount() {
        return poiData.size();
    }

    @Override
    public Object getItem(int position) {
        return poiData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final SuggestionResult.SuggestionInfo poiInfo = poiData.get(position);
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.address_item, null);
            holder = new ViewHolder();
            holder.poiName = (TextView) convertView.findViewById(R.id.poiName);
            holder.poiAddress = (TextView) convertView.findViewById(R.id.poiAddress);
            holder.ll_poi = (LinearLayout) convertView.findViewById(R.id.ll_poi);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.poiName.setText(poiInfo.key);
        holder.poiAddress.setText(poiInfo.city);

        holder.ll_poi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, poiInfo.key, Toast.LENGTH_SHORT).show();
                onMapAddrClickListener.mapAddressClick(position, poiInfo);
            }

        });
        return convertView;
    }

    static class ViewHolder {
        TextView poiName;
        TextView poiAddress;
        LinearLayout ll_poi;
    }


    private onMapAddrClickListener onMapAddrClickListener;

    public interface onMapAddrClickListener {
        void mapAddressClick(int item, SuggestionResult.SuggestionInfo address);
    }

    public void setOnMapAddrClickListener(MapShowAdressAdapter.onMapAddrClickListener onMapAddrClickListener) {
        this.onMapAddrClickListener = onMapAddrClickListener;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    private class ArrayFilter extends Filter {
        //执行刷选
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();//过滤的结果
            //原始数据备份为空时，上锁，同步复制原始数据
            if (mOriginalValues == null) {
                synchronized (mLock) {
                    mOriginalValues = new ArrayList<>(poiData);
                }
            }
            //当首字母为空时
            if (prefix == null || prefix.length() == 0) {
                ArrayList<SuggestionResult.SuggestionInfo> list;
                synchronized (mLock) {//同步复制一个原始备份数据
                    list = new ArrayList<>(mOriginalValues);
                }
                results.values = list;
                results.count = list.size();//此时返回的results就是原始的数据，不进行过滤
                Log.i("", "performFiltering: " + list.size());
            } else {
                String prefixString = prefix.toString().toLowerCase();//转化为小写

                ArrayList<SuggestionResult.SuggestionInfo> values;
                synchronized (mLock) {//同步复制一个原始备份数据
                    values = new ArrayList<>(mOriginalValues);
                }
                final int count = values.size();
                final List<SuggestionResult.SuggestionInfo> newValues = new ArrayList<>();

                for (int i = 0; i < count; i++) {
                    final SuggestionResult.SuggestionInfo value = values.get(i);//从List<User>中拿到User对象
//                    final String valueText = value.toString().toLowerCase();
                    final String valueText = value.key;//User对象的name属性作为过滤的参数
                    Log.i("", "performFiltering: " + prefixString);
                    Log.i("", "performFiltering: " + valueText);
//                    final String valueid = value.getProperties().getId().toLowerCase();//User对象的name属性作为过滤的参数
                    // First match against the whole, non-splitted value
                    if (valueText.startsWith(prefixString) || valueText.indexOf(prefixString.toString()) != -1
//                            || valueid.startsWith(prefixString) || valueid.indexOf(prefixString.toString()) != -1
                            ) {//第一个字符是否匹配
                        newValues.add(value);//将这个item加入到数组对象中
//                        Log.i(TAG, "performFiltering: " + newValues.size());
                    } else {//处理首字符是空格
                        final String[] words = valueText.split(" ");
                        final int wordCount = words.length;

                        // Start at index 0, in case valueText starts with space(s)
                        for (int k = 0; k < wordCount; k++) {
                            if (words[k].startsWith(prefixString)) {//一旦找到匹配的就break，跳出for循环
                                newValues.add(value);
                                break;
                            }
                        }
                    }
                }
                results.values = newValues;//此时的results就是过滤后的List<User>数组
                Log.i("", "performFiltering: " + newValues.size());
                results.count = newValues.size();
            }
            return results;
        }

        //刷选结果
        @Override
        protected void publishResults(CharSequence prefix, FilterResults results) {
            //noinspection unchecked
            poiData = (List<SuggestionResult.SuggestionInfo>) results.values;//此时，Adapter数据源就是过滤后的Results
            if (results.count > 0) {
                notifyDataSetChanged();//这个相当于从mDatas中删除了一些数据，只是数据的变化，故使用notifyDataSetChanged()
            } else {
                /**
                 * 数据容器变化 ----> notifyDataSetInValidated

                 容器中的数据变化  ---->  notifyDataSetChanged
                 */
                notifyDataSetInvalidated();//当results.count<=0时，此时数据源就是重新new出来的，说明原始的数据源已经失效了
            }
        }
    }
}
