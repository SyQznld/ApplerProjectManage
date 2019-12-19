package com.example.q_kang.pmsystem.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.BaseActivity;
import com.example.q_kang.pmsystem.modules.bean.bean.EventModelData;
import com.example.q_kang.pmsystem.modules.bean.bean.ModelData;
import com.example.q_kang.pmsystem.modules.bean.bean.filter.ModelUtil;
import com.example.q_kang.pmsystem.present.im.ModelPresent;
import com.example.q_kang.pmsystem.ui.adpter.EventModelAdapter;
import com.example.q_kang.pmsystem.ui.adpter.ModelAdapter;
import com.example.q_kang.pmsystem.view.ModelView;
import com.fingdo.statelayout.StateLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ModelActivity extends BaseActivity implements ModelView {
    @BindView(R.id.re_work_model)
    RecyclerView re_work_Model;
    @BindView(R.id.re_event_model)
    RecyclerView re_event_Model;
    @BindView(R.id.iv_model_back)
    ImageView iv_model_back;
    @BindView(R.id.tv_model_new)
    TextView tv_model_new;
    @BindView(R.id.tv_model_one)
    TextView tv_model_one;
    @BindView(R.id.tv_model_two)
    TextView tv_model_two;
    @BindView(R.id.tv_model_three)
    TextView tv_model_three;


    @BindView(R.id.rl_gongzuo_arrow)
    RelativeLayout rl_gongzuo_arrow;
    @BindView(R.id.rl_shijian_arrow)
    RelativeLayout rl_shijian_arrow;
    @BindView(R.id.sl_work_model)
    StateLayout sl_work_model;
    @BindView(R.id.sl_event_model)
    StateLayout sl_event_model;
    @BindView(R.id.ib_gongzuo_arrows)
    ImageButton ib_gongzuo_arrows;
    @BindView(R.id.ib_shijian_arrows)
    ImageButton ib_shijian_arrows;


    private ModelPresent modelPresent;
    private List<ModelData> data;
    private ModelData newModelData;
    private EventModelData.DataBean newEventModelData;


    private String templet = "";
    private boolean workModelShow = true;
    private boolean eventModelShow = true;


    @Override
    public int bindLayout() {
        return R.layout.activity_model;
    }

    @Override
    public void doBusiness(Context mContext) {

        modelPresent = new ModelPresent(this);
//        modelPresent.getModels();
//        modelPresent.getEventModels(templet);

    }

    @OnClick({R.id.iv_model_back, R.id.tv_model_new, R.id.tv_model_one, R.id.tv_model_two, R.id.tv_model_three, R.id.rl_gongzuo_arrow, R.id.rl_shijian_arrow})
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.iv_model_back:
                finish();
                break;
            case R.id.rl_gongzuo_arrow:

                if (workModelShow) {
                    ib_gongzuo_arrows.setImageDrawable(getResources().getDrawable(R.drawable.ic_row_up));
                    workModelShow = false;
                    re_work_Model.setVisibility(View.GONE);
                } else {
                    re_work_Model.setVisibility(View.VISIBLE);
                    ib_gongzuo_arrows.setImageDrawable(getResources().getDrawable(R.drawable.ic_row_down));
                    workModelShow = true;
                }

                break;
            case R.id.rl_shijian_arrow:
                if (eventModelShow) {
                    ib_shijian_arrows.setImageDrawable(getResources().getDrawable(R.drawable.ic_row_up));
                    eventModelShow = false;
                    re_event_Model.setVisibility(View.GONE);
                } else {
                    re_event_Model.setVisibility(View.VISIBLE);
                    ib_shijian_arrows.setImageDrawable(getResources().getDrawable(R.drawable.ic_row_down));
                    eventModelShow = true;
                }

                break;
            case R.id.tv_model_new:

                new MaterialDialog.Builder(ModelActivity.this)
                        .items(R.array.createModel)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                switch (position) {
                                    case 0://新建工作模板

                                        newModelData = ModelUtil.getNewModel();
                                        startActivityForResult(CreadModelActivity.class, toCreateModel(newModelData, Globle.MODEL_EDIT), Globle.MODEL_UPLOAD);
                                        break;
                                    case 1: //新建事件模板
                                        newEventModelData = ModelUtil.getNewEventModel();
                                        startActivityForResult(CreadModelActivity.class, toCreateEventModel(newEventModelData, Globle.EVENT_MODEL_EDIT), Globle.EVENT_MODEL_UPLOAD);
                                        break;

                                    default:
                                        break;
                                }
                            }
                        })
                        .show();

                break;
            case R.id.tv_model_one:
//                newModelData = ModelUtil.getModel1();
                newModelData = ModelUtil.getModelEmpty();
                Log.i(TAG, "onViewClick: " + newModelData);
                startActivityForResult(CreadModelActivity.class, toCreateModel(newModelData, Globle.MODEL_SHOW), Globle.MODEL_UPLOAD);
                break;
            case R.id.tv_model_two:
                newModelData = ModelUtil.getModel2();
                Log.i(TAG, "onViewClick: " + newModelData);
                startActivityForResult(CreadModelActivity.class, toCreateModel(newModelData, Globle.MODEL_SHOW), Globle.MODEL_UPLOAD);
                break;
            case R.id.tv_model_three:
                newModelData = ModelUtil.getModel3();
                Log.i(TAG, "onViewClick: " + newModelData);
                startActivityForResult(CreadModelActivity.class, toCreateModel(newModelData, Globle.MODEL_SHOW), Globle.MODEL_UPLOAD);
                break;
        }
    }

    //工作模板
    public Bundle toCreateModel(ModelData modelData, int i) {
        Bundle bundle = new Bundle();
        bundle.putString(CreadModelActivity.MODEL_ID, modelData.getId());
        bundle.putString(CreadModelActivity.MODEL_NAME, modelData.getName());
        bundle.putParcelableArrayList(CreadModelActivity.MODEL_ITEMS
                , (ArrayList<? extends Parcelable>) modelData.getMContentList());// 序列化
        bundle.putInt(CreadModelActivity.MODEL_YTPE, i);


        bundle.putInt(CreadModelActivity.MODEL_FLAG, 1);

        return bundle;
    }


    //事件模板
    public Bundle toCreateEventModel(EventModelData.DataBean modelData, int i) {      //Id, Name,cNameList


        Bundle bundle = new Bundle();
        bundle.putString(CreadModelActivity.EVENT_MODEL_ID, modelData.getId());
        bundle.putString(CreadModelActivity.EVENT_MODEL_NAME, modelData.getName());
        bundle.putParcelableArrayList(CreadModelActivity.EVENT_MODEL_CNAMELIST
                , (ArrayList<? extends Parcelable>) modelData.getTemplets());// 序列化
        Log.i(TAG, "toCreateEventModel: " + modelData.getTemplets());

        bundle.putInt(CreadModelActivity.MODEL_YTPE, i);

        bundle.putInt(CreadModelActivity.MODEL_FLAG, 2);
        return bundle;
    }


    @Override
    public void setModelAdapter(List<ModelData> datas) {
        if (datas.size() > 0) {
            sl_work_model.showContentView();
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            re_work_Model.setLayoutManager(gridLayoutManager);
            data = new ArrayList<>();
            ModelAdapter adapter = new ModelAdapter(ModelActivity.this, datas);
            re_work_Model.setHasFixedSize(true);
            re_work_Model.setNestedScrollingEnabled(false);
            re_work_Model.setAdapter(adapter);
        } else {
            sl_work_model.showEmptyView("", R.drawable.ic_no_data);

        }

    }

    @Override
    public void showEventModel(String string) {
        Log.i(TAG, "showEventModel: " + string);

//        List<EventModelData.DataBean> datas = new Gson().fromJson(string, new TypeToken<List<EventModelData.DataBean>>() {
//        }.getType());

        EventModelData eventModelData = new Gson().fromJson(string, new TypeToken<EventModelData>() {
        }.getType());
        List<EventModelData.DataBean> datas = eventModelData.getData();

        if (datas.size() > 0) {
            sl_event_model.showContentView();
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            re_event_Model.setLayoutManager(gridLayoutManager);
            EventModelAdapter adapter = new EventModelAdapter(ModelActivity.this, datas);
            re_event_Model.setHasFixedSize(true);
            re_event_Model.setNestedScrollingEnabled(false);
            re_event_Model.setAdapter(adapter);
        } else {
            sl_event_model.showEmptyView("", R.drawable.ic_no_data);
        }

    }

    @Override
    public void loadEventModel(String string) {
        Log.i(TAG, "loadEventModel: " + string);

    }


    @Override
    protected void onResume() {
        super.onResume();
        modelPresent.getModels();
        modelPresent.getEventModels(1,50,templet);
    }

//    @Override
//    protected void onResumeFragments() {
//        super.onResumeFragments();
//        modelPresent.getModels();
//        modelPresent.getEventModels(templet);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Globle.MODEL_UPLOAD && resultCode == RESULT_OK) {    //新建工作模板
            String id = data.getStringExtra(CreadModelActivity.MODEL_ID);
            String name = data.getStringExtra(CreadModelActivity.MODEL_NAME);
            String oldname = data.getStringExtra(CreadModelActivity.MODEL_OLD_NAME);
            List<String> cNameList = data.getStringArrayListExtra(CreadModelActivity.MODEL_ITEMS);
            List<Integer> SortList = data.getIntegerArrayListExtra(CreadModelActivity.MODEL_SORT);
            String[] arrItems = new String[cNameList.size()];
            cNameList.toArray(arrItems);
            Integer[] arrSorts = new Integer[SortList.size()];
            SortList.toArray(arrSorts);
            modelPresent.loadModel(id, name, oldname, arrSorts, arrItems);
            Log.i(TAG, "onActivityResult: " + name);
            Log.i(TAG, "onActivityResult: " + cNameList);

        } else if (requestCode == Globle.EVENT_MODEL_UPLOAD && resultCode == RESULT_OK) {   //新建事件模板
            String id = data.getStringExtra(CreadModelActivity.EVENT_MODEL_ID);
            String name = data.getStringExtra(CreadModelActivity.EVENT_MODEL_NAME);
            List<String> cNameList = data.getStringArrayListExtra(CreadModelActivity.EVENT_MODEL_CNAMELIST);
            String[] arrItems = new String[cNameList.size()];
            cNameList.toArray(arrItems);

//            JsonObject a = new JsonObject();
//            a.addProperty("cNameList", cNameList);
//            JsonObject b = new JsonObject();
//            b.addProperty("SortList", SortList);
            Log.i(TAG, "onActivityResult: " + id);
            Log.i(TAG, "onActivityResult: " + name);
            Log.i(TAG, "onActivityResult: " + cNameList);
            modelPresent.loadEventModel(id, name, arrItems);
        }

        modelPresent.getModels();
        modelPresent.getEventModels(1,50,templet);
    }
}
