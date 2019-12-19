package com.example.q_kang.pmsystem.ui.activity.project;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.BaseActivity;
import com.example.q_kang.pmsystem.dao.AdminDao;
import com.example.q_kang.pmsystem.modules.bean.bean.ChooseData;
import com.example.q_kang.pmsystem.modules.bean.bean.ModelData;
import com.example.q_kang.pmsystem.modules.bean.bean.filter.ModelUtil;
import com.example.q_kang.pmsystem.modules.bean.bean.project.ProjectAddBean;
import com.example.q_kang.pmsystem.modules.bean.bean.project.ProjectData;
import com.example.q_kang.pmsystem.modules.bean.bean.work.ModelWorkBean;
import com.example.q_kang.pmsystem.modules.bean.bean.work.WorkData;
import com.example.q_kang.pmsystem.present.im.ModelPresent;
import com.example.q_kang.pmsystem.present.im.ProjectPresent;
import com.example.q_kang.pmsystem.ui.activity.ChooseActivity;
import com.example.q_kang.pmsystem.ui.activity.map.BaseMapActivity;
import com.example.q_kang.pmsystem.ui.adpter.project.ModelChooseWorkAdapter;
import com.example.q_kang.pmsystem.ui.adpter.project.ProjectAddKeyAdapter;
import com.example.q_kang.pmsystem.ui.view.Utils.CommonUtils;
import com.example.q_kang.pmsystem.ui.view.Utils.DataPick;
import com.example.q_kang.pmsystem.ui.view.Utils.Utils.GlideHelper;
import com.example.q_kang.pmsystem.ui.view.Utils.view_.PileLayout;
import com.example.q_kang.pmsystem.view.CreatProjectView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jimmy.common.util.ToastUtils;
import com.warkiz.widget.IndicatorSeekBar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class CreateProjectActivity extends BaseActivity implements CreatProjectView {

    @BindView(R.id.ib_project_back)
    ImageButton ibBack;
    @BindView(R.id.ib_project_add)
    TextView ibAdd;

    @BindView(R.id.tv_create_project_title)
    TextView tv_title;
    @BindView(R.id.et_project_name)
    EditText et_name;
    @BindView(R.id.civ_fuzeren_head)
    CircleImageView civ_fuzeren;
    @BindView(R.id.tv_project_fuzeren)
    TextView tv_project_fuzeren;
    @BindView(R.id.ib_fuzeren_add)
    ImageButton ib_add_fuzeren;
    @BindView(R.id.ib_member_more)
    ImageButton ib_member_more;
    @BindView(R.id.ib_member_add)
    ImageButton ib_member_add;
    @BindView(R.id.pl_project_member)
    PileLayout pile_member;
    @BindView(R.id.tv_project_department)
    TextView tv_department;
    @BindView(R.id.tv_time_start)
    EditText tv_time_start;
    @BindView(R.id.tv_time_end)
    EditText tv_time_end;
    @BindView(R.id.tv_project_location)
    TextView tv_location;

    @BindView(R.id.ll_project_add)
    LinearLayout ll_project_add;
    @BindView(R.id.rv_project_add)
    RecyclerView rv_project_add;

    @BindView(R.id.et_project_describe)
    EditText et_remark;
    @BindView(R.id.isb_project_progress)
    IndicatorSeekBar isb_progress;
    @BindView(R.id.tv_project_progress)
    TextView tv_progress;

    @BindView(R.id.tv_choose)
    TextView tvChoose;
    @BindView(R.id.recy_a)
    RecyclerView recyA;
    @BindView(R.id.tv_project_submit)
    TextView tv_submit;
    @BindView(R.id.et_project_label)
    EditText et_label;
    @BindView(R.id.tv_list)
    TextView textView;

    private ModelChooseWorkAdapter modelAdapter;
    private List<ModelWorkBean> names = new ArrayList<>();

    private List<ProjectAddBean> projectAddBeanList = new ArrayList<>();
    private ProjectAddKeyAdapter projectAddKeyAdapter;

    private ArrayList<ChooseData> choosePerson;
    private LayoutInflater inflater;
    private CircleImageView imageView;

    private ModelPresent modelPresent;
    private List<ModelData> modeldatas;

    private int flag;
    private ProjectData.DataBean projectBean;

    private ProjectPresent projectPresent;
    private String leaderId = "";
    private String locationLatlng = "";
    private ArrayList<WorkData.DataBean> workList_;
    private String address = "";


    @Override
    public int bindLayout() {
        return R.layout.activity_create_project;
    }

    @Override
    public void doBusiness(Context mContext) {
        inflater = LayoutInflater.from(CreateProjectActivity.this);
        tv_time_start.setFocusable(false);
        tv_time_end.setFocusable(false);

        flag = getIntent().getIntExtra("flag", 0);
        projectBean = getIntent().getParcelableExtra("project");
        workList_ = getIntent().getParcelableArrayListExtra("worklist");
        Log.i(TAG, "doBusiness: " + workList_);
        if (flag == 2) {
            tv_title.setText("编辑项目");
            et_name.setText(projectBean.getName());
            et_label.setText(projectBean.getLable());
            leaderId = projectBean.getLeaderId();
            locationLatlng = projectBean.getLocation();
            GlideHelper.loadNet(civ_fuzeren, Globle.PHOTO_URL + projectBean.getLeaderImage(), R.mipmap.image_weibo_home_2);
            tv_project_fuzeren.setText(projectBean.getLeaderName());
            ib_add_fuzeren.setVisibility(View.GONE);
            tv_time_start.setText(projectBean.getStartTime().split(" ")[0]);
            tv_time_end.setText(projectBean.getEndTime().split(" ")[0]);
            address = projectBean.getAddress();
            if ("".equals(address) || "null".equals(address) || null == address) {
                address = "";
            } else {
                address = address.trim();
            }
            tv_location.setText(address + "(点击选择)");
            et_remark.setText(projectBean.getDescribe());
            String progress = projectBean.getProgress();
            String s = progress + "%";
            if ("null".equals(progress) || "".equals(progress) || null == progress) {
                progress = "0";
                s = "0";
            }
            isb_progress.setProgress(Float.parseFloat(progress));
            tv_progress.setText(s);


            ProjectAddBean projectAddBean;
            List<ProjectData.DataBean.OtherListBean> otherList = projectBean.getOtherList();
            if (!"[]".equals(otherList) && null != otherList) {
                ll_project_add.setVisibility(View.VISIBLE);
                for (int i = 0; i < otherList.size(); i++) {
                    projectAddBean = new ProjectAddBean();
                    String name = otherList.get(i).getName();
                    String value = otherList.get(i).getValue();
                    projectAddBean.setKey(name);
                    projectAddBean.setValue(value);
                    projectAddBeanList.add(projectAddBean);
                }
                rv_project_add.setLayoutManager(new LinearLayoutManager(CreateProjectActivity.this));
                projectAddKeyAdapter = new ProjectAddKeyAdapter(CreateProjectActivity.this, projectAddBeanList, 0);
                rv_project_add.setAdapter(projectAddKeyAdapter);
            } else {
                ll_project_add.setVisibility(View.GONE);
            }

            tvChoose.setVisibility(View.GONE);
            ModelWorkBean modelWorkBean;
            if (!"[]".equals(workList_) && null != workList_ && workList_.size() > 0) {
                for (int i = 0; i < workList_.size(); i++) {
                    modelWorkBean = new ModelWorkBean();
                    String name = workList_.get(i).getName();
                    String image = workList_.get(i).getLeaderImage();
                    String leader = workList_.get(i).getLeader();
                    String leaderId = workList_.get(i).getLeaderId();
                    modelWorkBean.setUserId(leaderId);
                    modelWorkBean.setWorkName(name);
                    modelWorkBean.setHead(image);
                    modelWorkBean.setFuzerenName(leader);
                    names.add(modelWorkBean);
                }
                recyA.setLayoutManager(new LinearLayoutManager(CreateProjectActivity.this));
                modelAdapter = new ModelChooseWorkAdapter(CreateProjectActivity.this, names, 1);
                recyA.setAdapter(modelAdapter);
            } else {
                textView.setVisibility(View.GONE);
            }

        }

        projectPresent = new ProjectPresent(this);

        isb_progress.setOnSeekChangeListener(new IndicatorSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(IndicatorSeekBar seekBar, int progress, float progressFloat, boolean fromUserTouch) {
                tv_progress.setText(progress + "%");
            }

            @Override
            public void onSectionChanged(IndicatorSeekBar seekBar, int thumbPosOnTick, String tickBelowText, boolean fromUserTouch) {

            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar, int thumbPosOnTick) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });

    }


    @OnClick({R.id.tv_choose, R.id.tv_project_submit, R.id.tv_time_end, R.id.tv_time_start, R.id.ib_fuzeren_add, R.id.ib_member_add,
            R.id.ib_project_back, R.id.tv_project_location, R.id.ib_project_add})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_choose:
//                modelPresent = new ModelPresent(this);
//                modelPresent.getModels();

                projectPresent.getModels();

                break;
            case R.id.tv_project_submit:

//                List<String> otherList = new ArrayList<>();
                JsonArray otherArray = new JsonArray();
                for (int i = 0; i < projectAddBeanList.size(); i++) {
                    String key1 = projectAddBeanList.get(i).getKey();
                    String value1 = projectAddBeanList.get(i).getValue();

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("name", key1);
                    jsonObject.addProperty("value", value1);
                    otherArray.add(jsonObject);
//                    String string = "name:" + key1 + "," + "value:" + value1;
//                    otherList.add(string);
                }


//                List<String> workList = new ArrayList<>();
                JsonArray workArray = new JsonArray();
                for (int i = 0; i < names.size(); i++) {
                    String workName = names.get(i).getWorkName();
                    String userId = names.get(i).getUserId();

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("name", workName);
                    jsonObject.addProperty("LeaderId", userId);

                    workArray.add(jsonObject);
//                    String string = "name:" + workName + "," + "leader:" + userId;
//                    workList.add(string);
                }


                new MaterialDialog.Builder(CreateProjectActivity.this)
                        .title("提交项目！")
                        .content("确定提交当前项目！")
                        .positiveText("确定")
                        .negativeText("取消")
                        .widgetColor(Color.BLUE)//不再提醒的checkbox 颜色

                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (which == DialogAction.POSITIVE) {

                                    String s = tv_progress.getText().toString();
                                    String progress;
                                    if ("".equals(s)) {
                                        progress = "";
                                    } else {
                                        progress = s.substring(0, s.length() - 1);
                                    }
                                    String name = et_name.getText().toString();
                                    String start = tv_time_start.getText().toString();
                                    String end = tv_time_end.getText().toString();
                                    String content = et_remark.getText().toString();

                                    try {
                                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                        Date dt1 = df.parse(start);
                                        Date dt2 = df.parse(end);
                                        long time = dt1.getTime() - dt2.getTime();
                                        Log.i(TAG, "onClick: " + time);
                                        if (time > 0) {
                                            ToastUtils.showToast(CreateProjectActivity.this, "请保证结束时间在开始时间之后~");
                                        } else {
                                            if (!"".equals(name) && !"".equals(start) && !"".equals(end) && !"".equals(leaderId)
                                                    && !"".equals(locationLatlng) && !"".equals(content)) {

                                                if (flag == 2) {  //编辑
                                                    JsonObject jsonElement = new JsonObject();
                                                    //Name StartTime EndTime LeaderId Location Describe AddUserId Progress Lable
                                                    jsonElement.addProperty("Id", projectBean.getId());
                                                    jsonElement.addProperty("Name", name);
                                                    jsonElement.addProperty("StartTime", start);
                                                    jsonElement.addProperty("EndTime", end);
                                                    jsonElement.addProperty("LeaderId", leaderId);
                                                    jsonElement.addProperty("Location", locationLatlng);
                                                    jsonElement.addProperty("address", address);
                                                    jsonElement.addProperty("Describe", content);
                                                    jsonElement.addProperty("AddUserId", AdminDao.getUserID());
                                                    jsonElement.addProperty("Progress", progress);
                                                    jsonElement.addProperty("Lable", et_label.getText().toString());
                                                    jsonElement.add("OtherList", otherArray);
                                                    jsonElement.add("WorkList", workArray);
                                                    String string = jsonElement.toString();
                                                    Log.i(TAG, "onClick string 编辑: " + string);

                                                    projectPresent.editProject(string);

                                                } else {   //新建
                                                    JsonObject jsonElement = new JsonObject();
                                                    //Name StartTime EndTime LeaderId Location Describe AddUserId Progress Lable
                                                    jsonElement.addProperty("Name", name);
                                                    jsonElement.addProperty("StartTime", start);
                                                    jsonElement.addProperty("EndTime", end);
                                                    jsonElement.addProperty("LeaderId", leaderId);
                                                    jsonElement.addProperty("Location", locationLatlng);
                                                    jsonElement.addProperty("address", address);
                                                    jsonElement.addProperty("Describe", content);
                                                    jsonElement.addProperty("AddUserId", AdminDao.getUserID());
                                                    jsonElement.addProperty("Progress", progress);
                                                    jsonElement.addProperty("Lable", et_label.getText().toString());
                                                    jsonElement.add("OtherList", otherArray);
                                                    jsonElement.add("WorkList", workArray);
                                                    String string = jsonElement.toString();
                                                    Log.i(TAG, "onClick string: " + string);

                                                    projectPresent.createProject(string);
                                                }
                                            } else {

                                                if (name == null || "".equals(name)) {
                                                    ToastUtils.showToast(CreateProjectActivity.this, "项目名称不能为空~");
                                                } else if (leaderId == null || "".equals(leaderId)) {
                                                    ToastUtils.showToast(CreateProjectActivity.this, "项目负责人不能为空~");
                                                } else if (start == null || "".equals(start)) {
                                                    ToastUtils.showToast(CreateProjectActivity.this, "项目开始时间不能为空~");
                                                } else if (end == null || "".equals(end)) {
                                                    ToastUtils.showToast(CreateProjectActivity.this, "项目结束时间不能为空~");
                                                } else if (locationLatlng == null || "".equals(locationLatlng)) {
                                                    ToastUtils.showToast(CreateProjectActivity.this, "项目位置不能为空~");
                                                } else if (content == null || "".equals(content)) {
                                                    ToastUtils.showToast(CreateProjectActivity.this, "项目内容描述不能为空~");
                                                }


//                                        Toast.makeText(CreateProjectActivity.this, "请保持信息完整~", Toast.LENGTH_SHORT).show();
                                            }

                                        }

                                    } catch (Exception exception) {
                                        exception.printStackTrace();
                                    }

                                    dialog.dismiss();

                                } else if (which == DialogAction.NEGATIVE) {
                                    dialog.dismiss();
                                }
                            }
                        }).show();
                break;
            case R.id.ib_fuzeren_add:
                Intent fuzerenIntent = new Intent(this, ChooseActivity.class);
                fuzerenIntent.putExtra("flag", "fuzeren");
                startActivityForResult(fuzerenIntent, CommonUtils.PROJECT_CHOOSE_PERSON_FUZEREN);
                break;
            case R.id.ib_member_add:
                Intent memberIntent = new Intent(this, ChooseActivity.class);
                memberIntent.putExtra("flag", "member");
                startActivityForResult(memberIntent, CommonUtils.PROJECT_CHOOSE_PERSON_MEMBER);
                break;
            case R.id.tv_time_start:
                new DataPick(this, "项目开始时间", tv_time_start);
                break;
            case R.id.tv_time_end:
                new DataPick(this, "项目结束时间", tv_time_end);
                break;
            case R.id.ib_project_back:
                finish();
                break;
            case R.id.tv_project_location:
                Intent locationIntent = new Intent(this, BaseMapActivity.class);
                locationIntent.putExtra("map", "project");
                startActivityForResult(locationIntent, CommonUtils.PROJECT_MAP_LOCATION_CODE);
                break;
            case R.id.ib_project_add:
                ProjectAddBean projectAddBean = new ProjectAddBean();

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = getLayoutInflater();
                View dialog = inflater.inflate(R.layout.project_add_key, null);
                EditText et_key = dialog.findViewById(R.id.et_add_key);
                EditText et_value = dialog.findViewById(R.id.et_add_value);

                builder.setTitle("新增字段属性");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String key = et_key.getText().toString();
                        String value = et_value.getText().toString();
                        projectAddBean.setKey(key);
                        projectAddBean.setValue(value);
                        projectAddBeanList.add(projectAddBean);


                        ll_project_add.setVisibility(View.VISIBLE);
                        rv_project_add.setLayoutManager(new LinearLayoutManager(CreateProjectActivity.this));
                        projectAddKeyAdapter = new ProjectAddKeyAdapter(CreateProjectActivity.this, projectAddBeanList, 0);
                        rv_project_add.setAdapter(projectAddKeyAdapter);
                        projectAddKeyAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setView(dialog);
                builder.show();

                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == CommonUtils.PROJECT_MAP_LOCATION_CODE) {
                address = data.getStringExtra("address");

                double latitude = data.getDoubleExtra("latitude", 0);
                double longitude = data.getDoubleExtra("longitude", 0);
                if (!"".equals(address) && address != null) {
                    tv_location.setText(address);
                } else {
                    tv_location.setText("未获取到位置信息");
                }

                locationLatlng = "point(" + longitude + "," + latitude + ")";
            } else if (requestCode == CommonUtils.PROJECT_CHOOSE_PERSON_FUZEREN) {//负责人 单选
                choosePerson = data.getParcelableArrayListExtra("choose");
                for (int i = 0; i < choosePerson.size(); i++) {
                    tv_project_fuzeren.setText(choosePerson.get(i).getFrame().getRealName());
                    GlideHelper.loadNet(civ_fuzeren, Globle.PHOTO_URL + choosePerson.get(i).getFrame().getImage(), R.mipmap.image_weibo_home_2);
                    leaderId = choosePerson.get(i).getFrame().getId();
                }

            } else if (requestCode == CommonUtils.PROJECT_CHOOSE_PERSON_MEMBER) {//参与人员 多选
                pile_member.removeAllViews();
                choosePerson = data.getParcelableArrayListExtra("choose");
                Log.i("member", "onActivityResult: " + choosePerson.size() + choosePerson);
                for (int i = 0; i < choosePerson.size(); i++) {
                    imageView = (CircleImageView) inflater.inflate(R.layout.fuzeren_head_praise, pile_member, false);
                    if (i < 6) {
                        GlideHelper.loadNet(imageView,
                                Globle.PHOTO_URL + choosePerson.get(i).getFrame().getImage(), R.mipmap.image_weibo_home_2);
                        pile_member.addView(imageView);
                    } else if (i == 6) {
                        ib_member_more.setVisibility(View.VISIBLE);
                    }

                }
            } else if (requestCode == CommonUtils.MODEL_CHOOSE_WORK_FUZEREN) {  //模板选择后 每个工作负责人 单选

                choosePerson = data.getParcelableArrayListExtra("choose");

                String fuzeren = "";
                String head = "";
                String fuzerenId = "";
                for (int i = 0; i < choosePerson.size(); i++) {
                    fuzeren = choosePerson.get(i).getFrame().getRealName();
                    head = choosePerson.get(i).getFrame().getImage();
                    fuzerenId = choosePerson.get(i).getFrame().getId();

                }

                int index = data.getIntExtra("index", 0);
                for (int i = 0; i < names.size(); i++) {
                    if (index == i) {
                        names.get(i).setHead(head);
                        names.get(i).setFuzerenName(fuzeren);
                        names.get(i).setUserId(fuzerenId);
                    }
                }
                Log.i(TAG, "onActivityResult: " + choosePerson.size() + choosePerson);
                Log.i(TAG, "onActivityResult: " + names.size() + names);


                modelAdapter = new ModelChooseWorkAdapter(CreateProjectActivity.this, names, 0);
                recyA.setAdapter(modelAdapter);

            }
        }

    }

    @Override
    public void initMobanData(List<ModelData> modelDataList) {
        modeldatas = new ArrayList<>();
        modeldatas.add(0, ModelUtil.getModelEmpty());
//        modeldatas.add(0, ModelUtil.getModel1());
//        modeldatas.add(1, ModelUtil.getModel2());
//        modeldatas.add(2, ModelUtil.getModel3());
        modeldatas.addAll(modelDataList);
        List<String> modelNames = new ArrayList<>();
        if (modeldatas != null) {
            for (int i = 0; i < modeldatas.size(); i++) {
                String name = modeldatas.get(i).getName();
                modelNames.add(name);
            }
        }
        new MaterialDialog.Builder(this)
                .title(R.string.projectModel)
                .items(modelNames)
                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        tvChoose.setText(modelNames.get(which));
                        List<ModelData.MContentListBean> mContentList = modeldatas.get(which).getMContentList();
                        names = new ArrayList<>();
                        for (int i = 0; i < mContentList.size(); i++) {
                            names.add(new ModelWorkBean(mContentList.get(i).getName(), "", ""));
                        }
                        recyA.setLayoutManager(new LinearLayoutManager(CreateProjectActivity.this));
                        modelAdapter = new ModelChooseWorkAdapter(CreateProjectActivity.this, names, 0);
                        recyA.setAdapter(modelAdapter);
                        return true;
                    }
                })
                .positiveText(R.string.confirm)
                .negativeText(R.string.cancle)
                .show();
    }

    @Override
    public void createProject(String s) {
        Log.i(TAG, "createProject: " + s);
        if ("添加成功".equals(s)) {
            Toast.makeText(CreateProjectActivity.this, "项目创建成功", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(CreateProjectActivity.this, "项目创建失败", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void editProject(String s) {
        if ("编辑成功".equals(s)) {
            Toast.makeText(CreateProjectActivity.this, "项目编辑成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        } else if ("已存在项目名".equals(s)) {
            Toast.makeText(CreateProjectActivity.this, "已存在项目名", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(CreateProjectActivity.this, "项目编辑失败", Toast.LENGTH_SHORT).show();
        }
    }

}
