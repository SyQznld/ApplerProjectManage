package com.example.q_kang.pmsystem.ui.view.Utils.map;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupWindow;

import com.example.q_kang.pmsystem.R;

/**
 * Created by appler on 2018/8/16.
 */

public class MapLayerPop extends PopupWindow implements CompoundButton.OnCheckedChangeListener{
    private String TAG = "MapLayerPop";

    private CheckBox cb_project;
    private CheckBox cb_work;
    private CheckBox cb_event;

    private Context context;
    private View view;
    private PopupWindow popupWindow;

//    private DataByIdPresenter dataByIdPresenter;




    public MapLayerPop(Context context) {
        super(context);
        this.context = context;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.map_layer_layout, null);

        cb_project = view.findViewById(R.id.cb_layer_project);
        cb_work = view.findViewById(R.id.cb_layer_work);
        cb_event = view.findViewById(R.id.cb_layer_event);
//        dataByIdPresenter = new DataByIdPresenter(this);
//        dataByIdPresenter.getDataById(AdminDao.getUserID());

    }

    public void showLayerPop(View parent) {

        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = manager.getDefaultDisplay().getWidth();
        popupWindow = new PopupWindow(view, width / 2, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.showAsDropDown(parent, 0, 20);


        CheckBoxIsClick();

    }

    private void CheckBoxIsClick() {
        cb_project.setOnCheckedChangeListener(this);
        cb_work.setOnCheckedChangeListener(this);
        cb_event.setOnCheckedChangeListener(this);
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_layer_project:
                if (isChecked == true) {

                } else {

                }
                break;
            case R.id.cb_layer_work:
                if (isChecked == true) {

                } else {

                }
                break;
            case R.id.cb_layer_event:
                if (isChecked == true) {

                } else {

                }
                break;
        }

    }
//
//    @Override
//    public void setState(int state) {
//
//    }
//
//    @Override
//    public void getDataById(AllData allData) {
//        Log.i(TAG, "getDataById: " + allData);
//        List<AllData.ProListBean> proList = allData.getProList();
//        for (int i = 0; i < proList.size(); i++) {
//            AllData.ProListBean proListBean = proList.get(i);
//            String location = proListBean.getLocation();
//            String substring = location.substring(6, location.length() - 1);
//            String longitude = substring.split(",")[0];
//            String latitude = substring.split(",")[1];
//        }
//
//    }
//
//    @Override
//    public void showSearchUser(List<Frame> frames) {
//
//    }
}
