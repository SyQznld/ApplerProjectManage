package com.example.q_kang.pmsystem.ui.adpter.photo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.dao.ImageDao;
import com.example.q_kang.pmsystem.modules.bean.bean.photo.ImageItemBean;
import com.example.q_kang.pmsystem.ui.view.Utils.imageloader.NativeImageLoader;
import com.example.q_kang.pmsystem.ui.view.Utils.view_.MyImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class SystemChildAdapter extends BaseAdapter {
    private Point mPoint = new Point(0, 0);//用来封装ImageView的宽和高的对象
    private GridView mGridView;
    private List<String> list;
    private LayoutInflater mInflater;
    private int state;//1 全选   0 单选
    private TextView tv;
    private Activity context;

    private List<Integer> selectList = new ArrayList<>();

    /**
     * 用来存储图片的选中情况
     */
    // private HashMap<String, Boolean> selectMap = new HashMap<>();
    private HashMap<Integer, Boolean> selectMap = new HashMap<>();

    private List<Integer> indexList = new ArrayList<>();

    public SystemChildAdapter(Activity context, List<String> list, GridView mGridView, int state, TextView tv) {
        this.context = context;
        this.list = list;
        this.mGridView = mGridView;
        this.state = state;
        this.tv = tv;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        String path = list.get(position);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.system_gv_child_item, null);
            viewHolder = new ViewHolder();
            viewHolder.mImageView = (MyImageView) convertView.findViewById(R.id.iv_system_child);
            viewHolder.cb_system_child = convertView.findViewById(R.id.cb_system_child);

            //用来监听ImageView的宽和高
            viewHolder.mImageView.setOnMeasureListener(new MyImageView.OnMeasureListener() {

                @Override
                public void onMeasureSize(int width, int height) {
                    mPoint.set(width, height);
                }
            });

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.mImageView.setImageResource(R.mipmap.ic_launcher);
        }
        viewHolder.mImageView.setTag(path);


        viewHolder.cb_system_child.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //如果是未选中的CheckBox,则添加动画
                if (!selectMap.containsKey(position) || !selectMap.get(position)) {
                    addAnimation(viewHolder.cb_system_child);
                }
                // selectMap.put(position, isChecked);
                if (isChecked) {
                    viewHolder.cb_system_child.setChecked(true);
                    selectMap.put(position, true);
                } else {
                    viewHolder.cb_system_child.setChecked(false);
                    selectMap.put(position, false);
                    selectMap.remove(position);
                }
                Log.i("onClick222", "selectMap: " + selectMap);
                indexList.clear();
                Set<Integer> keys = selectMap.keySet();// 得到全部的key
                Iterator<Integer> iter = keys.iterator();
                while (iter.hasNext()) {
                    int index = iter.next();
                    Log.i("onClick222", "index: " + index);
                    indexList.add(index);
                }
                Log.i("onClick222", "onCheckedChanged: " + indexList.size() + indexList);

            }
        });

        //全选
        if (state == 1) {
            for (int i = 0; i < list.size(); i++) {
                selectMap.put(i, true);
                viewHolder.cb_system_child.setChecked(true);
            }

        }


        viewHolder.cb_system_child.setChecked(selectMap.containsKey(position) ? selectMap.get(position) : false);


        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getSelectItems().size() == 0) {
                    Toast.makeText(context, "未选择图片", Toast.LENGTH_SHORT).show();
                    context.finish();
                } else {
                    new MaterialDialog.Builder(context)
                            .title("选择图片！")
                            .content("确定选择选中的" + getSelectItems().size() + "张图片？")
                            .positiveText("确定")
                            .negativeText("取消")
                            .widgetColor(Color.BLUE)

                            .onAny(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    if (which == DialogAction.POSITIVE) {


                                        for (int i = 0; i < indexList.size(); i++) {
                                            Log.i("onClick222", "indexList: " + indexList);
                                            Log.i("onClick222", "onClick: " + list.get(indexList.get(i)));
                                            ImageItemBean imageItemBean = new ImageItemBean();
                                            imageItemBean.setImagePath(list.get(indexList.get(i)));
                                            imageItemBean.setTime(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
                                            ImageDao.insertLabel(imageItemBean);
                                        }

                                        context.finish();

                                    } else if (which == DialogAction.NEGATIVE) {

                                    }
                                }
                            }).show();
                }

            }
        });


        //利用NativeImageLoader类加载本地图片
        Bitmap bitmap = NativeImageLoader.getInstance().loadNativeImage(path, mPoint, new NativeImageLoader.NativeImageCallBack() {

            @Override
            public void onImageLoader(Bitmap bitmap, String path) {
                ImageView mImageView = (MyImageView) mGridView.findViewWithTag(path);
                if (bitmap != null && mImageView != null) {
                    mImageView.setImageBitmap(bitmap);
                }
            }
        });

        if (bitmap != null) {
            viewHolder.mImageView.setImageBitmap(bitmap);
        } else {
            viewHolder.mImageView.setImageResource(R.mipmap.ic_launcher);
        }

        return convertView;
    }


    /**
     * 给CheckBox加点击动画，利用开源库nineoldandroids设置动画
     *
     * @param view
     */

    private void addAnimation(View view) {
        float[] vaules = new float[]{0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1.0f, 1.1f, 1.2f, 1.3f, 1.25f, 1.2f, 1.15f, 1.1f, 1.0f};
        AnimatorSet set = new AnimatorSet();
        set.playTogether(ObjectAnimator.ofFloat(view, "scaleX", vaules),
                ObjectAnimator.ofFloat(view, "scaleY", vaules));
        set.setDuration(150);
        set.start();
    }


    public List<Integer> getSelectItems() {
        List<Integer> list = new ArrayList<Integer>();
        for (Iterator<Map.Entry<Integer, Boolean>> it = selectMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Integer, Boolean> entry = it.next();
            if (entry.getValue()) {
                list.add(entry.getKey());
            }
        }

        return list;
    }


    public static class ViewHolder {
        public MyImageView mImageView;

        CheckBox cb_system_child;
    }

}