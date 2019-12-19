package com.example.q_kang.pmsystem.ui.view.Utils.map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.modules.bean.bean.EventBusMessage;
import com.example.q_kang.pmsystem.ui.adpter.MapShowAdressAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by appler on 2018/8/13.
 */

public class MapShowAddressPop extends PopupWindow {

    private Activity context;
    private LayoutInflater layoutInflater;
    private View view;
    private EditText et_input;
    private TextView tv_close;
    private ListView lv_address;

    private MapShowAdressAdapter adressAdapter;
    /**
     * 在线建议查询
     */
    private SuggestionSearch keyWordsPoiSearch;
    /**
     * 在线建议查询结果集
     */
    private List<SuggestionResult.SuggestionInfo> keyWordPoiData = new ArrayList<>();

    private PopupWindow popupWindow;


    public MapShowAddressPop(Activity context) {
        super(context);
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.map_show_address, null);
        et_input = view.findViewById(R.id.et_map_input);
        tv_close = view.findViewById(R.id.tv_map_close);
        lv_address = view.findViewById(R.id.lv_map_search);

    }


    /**
     * 在android7.0上，如果不主动约束PopuWindow的大小，比如，设置布局大小为 MATCH_PARENT,那么PopuWindow会变得尽可能大，
     * 以至于 view下方无空间完全显示PopuWindow，而且view又无法向上滚动，此时PopuWindow会主动上移位置，直到可以显示完全。
     * 　解决办法：主动约束PopuWindow的内容大小，重写showAsDropDown方法：
     *
     * @param anchor
     */
    @Override
    public void showAsDropDown(View anchor) {

        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            setHeight(height);
        }
        super.showAsDropDown(anchor);
    }


    public void showpupwindow(final View parent, MapControl mapControl) {


        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = manager.getDefaultDisplay().getWidth();
        int heigh = manager.getDefaultDisplay().getHeight();

        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, heigh / 2, true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.setAnimationStyle(R.style.AnimationBottomFade);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);


        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EventBusMessage eventBusMessage = new EventBusMessage("returnAddr_empty");
                EventBus.getDefault().post(eventBusMessage);

                popupWindow.dismiss();
            }
        });


        et_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //   adressAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

//                if (et_input.getText().toString().equals("")) {
//                    return;
//                }

//                adressAdapter = null;

                /**
                 * 在线建议查询对象实例化+设置监听
                 * @在线建议查询： 根据城市和关键字搜索出相应的位置信息(模糊查询)
                 * */
                keyWordsPoiSearch = SuggestionSearch.newInstance();
                keyWordsPoiSearch.setOnGetSuggestionResultListener(new OnGetSuggestionResultListener() {
                    @Override
                    public void onGetSuggestionResult(SuggestionResult suggestionResult) {
                        keyWordPoiData.clear();
                        if (suggestionResult.getAllSuggestions() == null) {
                            Toast.makeText(context, "暂无数据信息", Toast.LENGTH_LONG).show();
                        } else {
                            keyWordPoiData = suggestionResult.getAllSuggestions();
                            //设置Adapter结束
                            adressAdapter = new MapShowAdressAdapter(context, keyWordPoiData);
                            lv_address.setAdapter(adressAdapter);


                            adressAdapter.setOnMapAddrClickListener(new MapShowAdressAdapter.onMapAddrClickListener() {
                                @Override
                                public void mapAddressClick(int item, SuggestionResult.SuggestionInfo address) {
                                    double latitude = address.pt.latitude;
                                    double longitude = address.pt.longitude;
                                    String addressInfo = address.key;

                                    mapControl.projectLocation(latitude, longitude, "", addressInfo);


                                    EventBusMessage eventBusMessage = new EventBusMessage("returnAddr");
                                    eventBusMessage.setLatLng(address.pt);
                                    eventBusMessage.setAddress(addressInfo);
                                    EventBus.getDefault().post(eventBusMessage);

                                    popupWindow.dismiss();

                                }
                            });
                        }
                    }
                });
                keyWordsPoiSearch.requestSuggestion((new SuggestionSearchOption()).keyword(et_input.getText().toString()).city("郑州"));

            }
        });

    }
}
