package com.example.q_kang.pmsystem.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.BaseActivity;
import com.example.q_kang.pmsystem.modules.bean.bean.EventModelData;
import com.example.q_kang.pmsystem.modules.bean.bean.ModelData;
import com.example.q_kang.pmsystem.ui.adpter.CreateEventModelAdapter;
import com.example.q_kang.pmsystem.ui.adpter.CreateModelAdapter;
import com.example.q_kang.pmsystem.view.CreateModelView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CreadModelActivity extends BaseActivity implements CreateModelView {
    public static final String MODEL_ID = "id";
    public static final String MODEL_NAME = "name";
    public static final String MODEL_OLD_NAME = "oldname";
    public static final String MODEL_ITEMS = "modelData";
    public static final String MODEL_SORT = "modelSort";
    public static final String MODEL_YTPE = "code";


    public static final String MODEL_FLAG = "flag";


    public static final String EVENT_MODEL_ID = "event_Id";
    public static final String EVENT_MODEL_NAME = "event_Name";
    public static final String EVENT_MODEL_CNAMELIST = "event_cNameList";


    @BindView(R.id.rec_item)
    RecyclerView rec_item;
    @BindView(R.id.iv_model_create_back)
    ImageView iv_model_create_back;
    @BindView(R.id.tv_tile)
    TextView tv_tile;
    @BindView(R.id.tv_model_create_save)
    TextView tv_model_create_save;
    @BindView(R.id.tv_model_create_edit)
    TextView tv_model_create_edit;
    private List<ModelData.MContentListBean> datas;
    private CreateModelAdapter createModelAdapter;

    private List<EventModelData.DataBean.TempletsBean> eventDatas;
    private CreateEventModelAdapter createEventModelAdapter;

    private String modelId;
    private String modelName;
    private List<ModelData.MContentListBean> modelData;

    private int flag;

    private String event_modelId;
    private String event_modelName;
    private List<EventModelData.DataBean.TempletsBean> eventModelData;
    private int modelIdentify;

    @Override
    public int bindLayout() {
        return R.layout.activity_cread_model;
    }

    @Override
    public void doBusiness(final Context mContext) {
        modelData = getIntent().getParcelableArrayListExtra(MODEL_ITEMS);
        modelName = getIntent().getStringExtra(MODEL_NAME);
        modelId = getIntent().getStringExtra(MODEL_ID);
        modelIdentify = getIntent().getIntExtra(MODEL_YTPE, -1);

        flag = getIntent().getIntExtra(MODEL_FLAG, 0);


        eventModelData = getIntent().getParcelableArrayListExtra(EVENT_MODEL_CNAMELIST);
        event_modelName = getIntent().getStringExtra(EVENT_MODEL_NAME);
        Log.i(TAG, "doBusiness event_modelName: " + event_modelName);

        event_modelId = getIntent().getStringExtra(EVENT_MODEL_ID);

        if (flag == 1) {
            tv_tile.setText(modelName);
            switch (modelIdentify) {
                case Globle.MODEL_CHECK:
                    tv_model_create_save.setVisibility(View.INVISIBLE);
                    tv_model_create_edit.setVisibility(View.VISIBLE);
                    break;
                case Globle.MODEL_EDIT:
                    tv_model_create_save.setVisibility(View.VISIBLE);
                    tv_model_create_edit.setVisibility(View.INVISIBLE);
                    break;
                case Globle.MODEL_SHOW:
                    tv_model_create_save.setVisibility(View.INVISIBLE);
                    tv_model_create_edit.setVisibility(View.INVISIBLE);
                    break;
            }
        } else if (flag == 2) {
            tv_tile.setText(event_modelName);

            switch (modelIdentify) {
                case Globle.EVENT_MODEL_CHECK:
                    tv_model_create_save.setVisibility(View.INVISIBLE);
                    tv_model_create_edit.setVisibility(View.VISIBLE);
                    break;
                case Globle.EVENT_MODEL_EDIT:
                        tv_model_create_save.setVisibility(View.VISIBLE);
                        tv_model_create_edit.setVisibility(View.INVISIBLE);
                    break;
                case Globle.EVENT_MODEL_SHOW:
                    tv_model_create_save.setVisibility(View.INVISIBLE);
                    tv_model_create_edit.setVisibility(View.INVISIBLE);
                    break;
            }

            if ("空模板".equals(event_modelName)){
                tv_model_create_save.setVisibility(View.GONE);
                tv_model_create_edit.setVisibility(View.GONE);
            }
        }


        rec_item.setLayoutManager(new LinearLayoutManager(mContext));
        if (flag == 1) {
            datas = modelData;
            createModelAdapter = new CreateModelAdapter(CreadModelActivity.this, datas, modelIdentify);
            rec_item.setAdapter(createModelAdapter);
        } else if (flag == 2) {
            eventDatas = eventModelData;
            Log.i(TAG, "doBusiness 事件模板: " + eventDatas);
            createEventModelAdapter = new CreateEventModelAdapter(CreadModelActivity.this, eventDatas, modelIdentify);
            rec_item.setAdapter(createEventModelAdapter);
        }
    }


    @Override
    public void RenameItem(ModelData.MContentListBean item, int position) {
        datas.set(position, item);
        createModelAdapter.notifyDataSetChanged();
    }

    @Override
    public void CreateItem(ModelData.MContentListBean item, int position) {
        datas.add((position + 1), item);
        createModelAdapter.notifyDataSetChanged();
    }

    @Override
    public void delItem(int Position) {
        datas.remove(Position);
        createModelAdapter.notifyDataSetChanged();
    }


    @Override
    public void RenameEventMB(EventModelData.DataBean.TempletsBean item, int position) {
        eventDatas.set(position, item);
        createEventModelAdapter.notifyDataSetChanged();
    }

    @Override
    public void CreateEventMB(EventModelData.DataBean.TempletsBean item, int position) {
        eventDatas.add((position + 1), item);
        createEventModelAdapter.notifyDataSetChanged();
    }

    @Override
    public void delEventMBItem(int Position) {
        eventDatas.remove(Position);
        createEventModelAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.iv_model_create_back, R.id.tv_model_create_save, R.id.tv_model_create_edit, R.id.tv_tile})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.iv_model_create_back:
                finish();
                break;
            case R.id.tv_model_create_save:

                new MaterialDialog.Builder(CreadModelActivity.this)
                        .title("提交模板！")
                        .content("确定提交当前模板！")
                        .positiveText("确定")
                        .negativeText("取消")
                        .widgetColor(Color.BLUE)//不再提醒的checkbox 颜色

                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (which == DialogAction.POSITIVE) {
                                    Log.i(TAG, "onClick flag: " + flag);
                                    if (flag == 1) {  //工作模板
                                        String name = tv_tile.getText().toString().trim();
                                        List<String> items = new ArrayList<>();
                                        List<Integer> sorts = new ArrayList<>();
                                        for (int i = 0; i < datas.size(); i++) {
                                            items.add(datas.get(i).getName());
                                            sorts.add(i + 1);
                                        }
                                        Intent intent = new Intent();
                                        intent.putExtra(MODEL_ID, modelId);
                                        intent.putExtra(MODEL_NAME, name);
                                        intent.putExtra(MODEL_OLD_NAME, modelName);
                                        intent.putStringArrayListExtra(MODEL_ITEMS, (ArrayList<String>) items);
                                        intent.putIntegerArrayListExtra(MODEL_SORT, (ArrayList<Integer>) sorts);
                                        setResult(RESULT_OK, intent);
                                        finish();
                                    } else if (flag == 2) {    //事件模板
                                        String name = tv_tile.getText().toString().trim();
                                        List<String> items = new ArrayList<>();
                                        for (int i = 0; i < eventDatas.size(); i++) {
                                            items.add(eventDatas.get(i).getName());
                                        }
                                        Intent intent = new Intent();
                                        intent.putExtra(EVENT_MODEL_ID, event_modelId);
                                        intent.putExtra(EVENT_MODEL_NAME, name);
                                        intent.putStringArrayListExtra(EVENT_MODEL_CNAMELIST, (ArrayList<String>) items);
                                        Log.i(TAG, "onClick id: " + event_modelId);
                                        Log.i(TAG, "onClick name: " + name);
                                        Log.i(TAG, "onClick items: " + items);
                                        setResult(RESULT_OK, intent);
                                        finish();
                                    }
                                    dialog.dismiss();

                                } else if (which == DialogAction.NEGATIVE) {
                                    dialog.dismiss();
                                }
                            }
                        }).show();

                break;
            case R.id.tv_model_create_edit:
                tv_model_create_save.setVisibility(View.VISIBLE);
                tv_model_create_edit.setVisibility(View.INVISIBLE);
                if (flag == 1) {
                    modelIdentify = Globle.MODEL_EDIT;
                    if (createModelAdapter != null) {
                        createModelAdapter = null;
                    }
                    createModelAdapter = new CreateModelAdapter(CreadModelActivity.this, datas, modelIdentify);
                    rec_item.setAdapter(createModelAdapter);
                } else if (flag == 2) {
                    modelIdentify = Globle.EVENT_MODEL_EDIT;
                    if (createEventModelAdapter != null) {
                        createEventModelAdapter = null;
                    }
                    createEventModelAdapter = new CreateEventModelAdapter(CreadModelActivity.this, eventDatas, modelIdentify);
                    rec_item.setAdapter(createEventModelAdapter);
                }

//                showToast("点击了编辑按钮");
                break;
            case R.id.tv_tile:
                new MaterialDialog.Builder(CreadModelActivity.this)
                        .title("模板名称")
                        .content("修改模板名称！")
                        .inputType(
                                InputType.TYPE_CLASS_TEXT
                                        | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                        | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                        .inputRange(2, 16)
                        .positiveText("完成")
                        .input("请输入新的模板名称", "", false, new MaterialDialog.InputCallback() {
                                    @Override
                                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                        tv_tile.setText(input.toString());
                                    }
                                }
                        )
                        .show();
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

    }
}
