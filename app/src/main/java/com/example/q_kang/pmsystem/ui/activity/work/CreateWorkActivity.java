package com.example.q_kang.pmsystem.ui.activity.work;

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
import com.example.q_kang.pmsystem.modules.bean.bean.EventModelData;
import com.example.q_kang.pmsystem.modules.bean.bean.filter.ModelUtil;
import com.example.q_kang.pmsystem.modules.bean.bean.project.ProjectAddBean;
import com.example.q_kang.pmsystem.modules.bean.bean.work.ModelWorkBean;
import com.example.q_kang.pmsystem.modules.bean.bean.work.WorkData;
import com.example.q_kang.pmsystem.present.im.WorkPresenter;
import com.example.q_kang.pmsystem.ui.activity.ChooseActivity;
import com.example.q_kang.pmsystem.ui.activity.map.BaseMapActivity;
import com.example.q_kang.pmsystem.ui.adpter.event.EventAssignAdapter;
import com.example.q_kang.pmsystem.ui.adpter.project.ModelChooseWorkAdapter;
import com.example.q_kang.pmsystem.ui.adpter.project.ProjectAddKeyAdapter;
import com.example.q_kang.pmsystem.ui.view.Utils.CommonUtils;
import com.example.q_kang.pmsystem.ui.view.Utils.DataPick;
import com.example.q_kang.pmsystem.ui.view.Utils.Utils.GlideHelper;
import com.example.q_kang.pmsystem.ui.view.Utils.view_.PileLayout;
import com.example.q_kang.pmsystem.view.WorkView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jimmy.common.util.ToastUtils;
import com.warkiz.widget.IndicatorSeekBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


public class CreateWorkActivity extends BaseActivity implements WorkView {

    @BindView(R.id.ib_work_back)
    ImageButton ibBack;
    @BindView(R.id.tv_work_submit)
    TextView tv_submit;
    @BindView(R.id.tv_work_project)
    TextView tv_projectname;
    @BindView(R.id.et_work_name)
    EditText et_name;
    @BindView(R.id.et_work_label)
    EditText et_label;
    @BindView(R.id.tv_work_add)
    TextView tv_work_add;
    @BindView(R.id.civ_work_fuzeren_head)
    CircleImageView civ_head;
    @BindView(R.id.tv_work_fuzeren)
    TextView tv_work_fuzeren;
    @BindView(R.id.ib_work_fuzeren_add)
    ImageButton ib_add_fuzeren;
    @BindView(R.id.ib_work_member_more)
    ImageButton ib_member_more;
    @BindView(R.id.ib_work_member_add)
    ImageButton ib_member_add;
    @BindView(R.id.pl_work_member)
    PileLayout pile_member;
    @BindView(R.id.tv_work_time_start)
    EditText tv_time_start;
    @BindView(R.id.tv_work_time_end)
    EditText tv_time_end;
    @BindView(R.id.isb_progress)
    IndicatorSeekBar isb_progress;
    @BindView(R.id.tv_work_progress)
    TextView tv_progress;
    @BindView(R.id.tv_work_location)
    TextView tv_location;
    @BindView(R.id.tv_work_canyu)
    TextView tv_canyu;

    @BindView(R.id.ll_work_add)
    LinearLayout ll_work_add;
    @BindView(R.id.rv_work_add)
    RecyclerView rv_work_add;

    @BindView(R.id.et_work_describe)
    EditText et_remark;
    @BindView(R.id.tv_creat_work_title)
    TextView tv_title;


    @BindView(R.id.tv_event_list)
    TextView textview;
    @BindView(R.id.tv_event_choose)
    TextView tv_choose;
    @BindView(R.id.rv_event_model)
    RecyclerView rv_event_model;
    @BindView(R.id.ll_event_model)
    LinearLayout ll_event_model;
    private ModelChooseWorkAdapter modelAdapter;
    private List<ModelWorkBean> names = new ArrayList<>();
    private List<EventModelData.DataBean> modeldatas;
    private String templet;


    private String projectName;

    private List<ProjectAddBean> projectAddBeanList = new ArrayList<>();
    private ProjectAddKeyAdapter projectAddKeyAdapter;

    private ArrayList<ChooseData> choosePerson = new ArrayList<>();
    private LayoutInflater inflater;
    private CircleImageView imageView;

    private int flag;
    //Name, pId, StartTime, JoinPersons, EndTime, LeaderId,Content, Location, AddUserId, Progress, Lable

    private WorkPresenter workPresenter;
    private String pId = "";
    private String JoinPersons = "";
    private String LeaderId = "";
    private String Location = "";
    private String address = "";
    private String nextId = "";
    private String AddUserId, StartTime, EndTime;
    private List<String> assignPersonList = new ArrayList<>();
    private List<String> nameList;
    private WorkData.DataBean workBean;

    private String nameStr = "";
    private String leaderIdStr = "";

    //   private List<Integer> indexList = new ArrayList<>();
    private List<String> indexList = new ArrayList<>();


    @Override
    public int bindLayout() {
        return R.layout.activity_creat_work;
    }

    @Override
    public void doBusiness(Context mContext) {

        AddUserId = AdminDao.getUserID();
        workPresenter = new WorkPresenter(this);

        tv_time_start.setFocusable(false);
        tv_time_end.setFocusable(false);

        inflater = LayoutInflater.from(mContext);

        flag = getIntent().getIntExtra("flag", 0);
        if (flag == 1) {    //工作列表页面新建工作，选择所属项目，可能与项目无关
            tv_projectname.setText("点击选择项目");
            tv_projectname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ChooseBelongProjectActivity.class);
                    startActivityForResult(intent, CommonUtils.WORK_CREATE_CHOOSE_PROJECT);
                }
            });
        } else if (flag == 3) {
            projectName = getIntent().getStringExtra("projectname"); //与项目有关，新建工作
            pId = getIntent().getStringExtra("projectId");
            nextId = getIntent().getStringExtra("nextId");
            tv_projectname.setText(projectName);
        } else if (flag == 2) {    //编辑工作

            tv_title.setText("编辑工作");
            workBean = getIntent().getParcelableExtra("work");
            Log.i(TAG, "doBusiness 工作: " + workBean);

            workPresenter.getEventListOrNo(workBean.getId());

            ib_add_fuzeren.setVisibility(View.GONE);
            LeaderId = workBean.getLeaderId();
            pId = workBean.getpId();

            projectName = workBean.getProjectName();
            if ("".equals(projectName) || "null".equals(projectName)) {
                projectName = "独立工作 （点击选择所属项目）";
            }
            tv_projectname.setText(projectName);
            tv_projectname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ChooseBelongProjectActivity.class);
                    startActivityForResult(intent, CommonUtils.WORK_CREATE_CHOOSE_PROJECT);
                }
            });

            et_name.setText(workBean.getName());
            et_label.setText((CharSequence) workBean.getLable());

            String leaderImage = workBean.getLeaderImage();
            if ("".equals(leaderImage) || "null".equals(leaderImage)) {
                civ_head.setImageResource(R.mipmap.image_weibo_home_2);
            } else {
                GlideHelper.loadNet(civ_head, Globle.PHOTO_URL + leaderImage, R.mipmap.image_weibo_home_2);
            }
            tv_work_fuzeren.setText(workBean.getLeader());
            // tv_canyu.setText(workBean.getJoinPersons());

            JoinPersons = workBean.getJoinPerson();
            if (!"".equals(JoinPersons) && !"null".equals(JoinPersons) && null != JoinPersons) {
                String[] split = JoinPersons.split(",");
                for (int i = 0; i < split.length; i++) {
                    indexList.add(split[i]);
                }
            }

            List<String> list = new ArrayList<>();
            String joinImage = (String) workBean.getJoinImage();
            if ("".equals(joinImage) || "null".equals(joinImage) || null == joinImage) {
                joinImage = "";
            } else {
                String[] split = joinImage.split(",");
                int member = split.length;
                Log.i(TAG, "doBusiness split.length: " + member);

                pile_member.removeAllViews();
                if (member == 0) {
                    for (int j = 0; j < JoinPersons.split(",").length; j++) {
                        imageView = (CircleImageView) inflater.inflate(R.layout.fuzeren_head_praise, pile_member, false);
                        GlideHelper.loadNet(imageView, " ", R.mipmap.image_weibo_home_2);
                        pile_member.addView(imageView);
                    }
                } else {
                    for (int j = 0; j < member; j++) {
                        imageView = (CircleImageView) inflater.inflate(R.layout.fuzeren_head_praise, pile_member, false);
                        GlideHelper.loadNet(imageView, Globle.PHOTO_URL + split[j], R.mipmap.image_weibo_home_2);
                        pile_member.addView(imageView);
                    }
                }


                String assignPersons = workBean.getJoinPersons();
                String imagePerson = workBean.getJoinImage();
                if ("".equals(assignPersons) || "null".equals(assignPersons) || null == assignPersons) {
                    assignPersons = "";
                }
                if ("".equals(imagePerson) || "null".equals(imagePerson) || null == imagePerson) {
                    imagePerson = "";
                }
                String[] name = assignPersons.split(",");
                String[] image = imagePerson.split(",");

                if (image.length == 0) {
                    for (int i = 0; i < name.length; i++) {
                        String s = " ";
                        String s1 = name[i];
                        list.add(s + "," + s1);
                    }
                } else {
                    for (int i = 0; i < name.length; i++) {
                        String s = image[i];
                        String s1 = name[i];
                        list.add(s + "," + s1);
                    }
                }


                pile_member.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new MaterialDialog.Builder(CreateWorkActivity.this)
                                .items(list)
                                .adapter(new EventAssignAdapter(CreateWorkActivity.this, list),
                                        new LinearLayoutManager(CreateWorkActivity.this))
                                .show();
                    }
                });

            }

            StartTime = workBean.getStartTime().split("T")[0];
            EndTime = workBean.getEndTime().split("T")[0];
            tv_time_start.setText(StartTime);
            tv_time_end.setText(EndTime);

            String progress = workBean.getProgress();
            String s = progress + "%";
            if ("null".equals(progress) || "".equals(progress) || null == progress) {
                progress = "0";
                s = "0";
            }
            isb_progress.setProgress(Float.parseFloat(progress));
            tv_progress.setText(s);

            Location = workBean.getLocation();
            address = workBean.getAddress();
            if ("".equals(address) || "null".equals(address) || null == address) {
                address = "";
            } else {
                address = address.trim();
            }
            tv_location.setText(address + " (点击选择)");
//            if (Location != null && !"".equals(Location) && !"null".equals(Location)) {
//                tv_location.setText("点击选择工作位置");
//            }

            et_remark.setText(workBean.getContent());

        }


        isb_progress.setOnSeekChangeListener(new IndicatorSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(IndicatorSeekBar seekBar, int progress, float progressFloat, boolean fromUserTouch) {
                Log.i(TAG, "onProgressChanged: " + progress);
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


    @OnClick({R.id.ib_work_back, R.id.tv_work_submit, R.id.tv_work_add, R.id.et_work_name, R.id.ib_work_fuzeren_add, R.id.ib_work_member_add,
            R.id.tv_work_time_start, R.id.tv_work_time_end, R.id.tv_work_location, R.id.tv_event_choose})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.ib_work_back:
                finish();
                break;
            case R.id.tv_work_submit:

                Log.i(TAG, "onViewClicked: " + tv_progress.getText().toString());

                new MaterialDialog.Builder(CreateWorkActivity.this)
                        .title("提交工作！")
                        .content("确定提交当前工作！")
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
                                        progress = "0";
                                    } else {
                                        progress = s.substring(0, s.length() - 1);
                                    }

                                    String name = et_name.getText().toString();
                                    String start = tv_time_start.getText().toString();
                                    String end = tv_time_end.getText().toString();
                                    String content = et_remark.getText().toString();

                                    List<String> eventnameList = new ArrayList<>();
                                    List<String> userIdList = new ArrayList<>();
                                    for (int i = 0; i < names.size(); i++) {
                                        ModelWorkBean modelWorkBean = names.get(i);
                                        String userId = modelWorkBean.getUserId();
                                        String workName = modelWorkBean.getWorkName();

                                        eventnameList.add(workName);
                                        userIdList.add(userId);
                                    }
                                    String nameStr = eventnameList.toString();
                                    nameStr = nameStr.substring(1, nameStr.length() - 1).trim();

                                    String leaderIdStr = userIdList.toString();
                                    leaderIdStr = leaderIdStr.substring(1, leaderIdStr.length() - 1).trim();


//                                    Log.i(TAG, "onClick Name: " + name);
//                                    Log.i(TAG, "onClick pId: " + pId);
//                                    Log.i(TAG, "onClick StartTime: " + start);
//                                    Log.i(TAG, "onClick JoinPersons: " + JoinPersons);
//                                    Log.i(TAG, "onClick EndTime: " + end);
//                                    Log.i(TAG, "onClick LeaderId: " + LeaderId);
//                                    Log.i(TAG, "onClick Content: " + content);
//                                    Log.i(TAG, "onClick Location: " + Location);
//                                    Log.i(TAG, "onClick address: " + address);
//                                    Log.i(TAG, "onClick AddUserId: " + AddUserId);
//                                    Log.i(TAG, "onClick progress: " + progress);
//                                    Log.i(TAG, "onClick Lable: " + et_label.getText().toString());
//                                    Log.i(TAG, "onClick nameList: " + nameStr);
//                                    Log.i(TAG, "onClick leaderIdList: " + leaderIdStr);
//                                    Log.i(TAG, "onClick nextId: " + nextId);


                                    try {
                                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                        Date dt1 = df.parse(start);
                                        Date dt2 = df.parse(end);
                                        long time = dt1.getTime() - dt2.getTime();
                                        Log.i(TAG, "onClick: " + time);
                                        if (time > 0) {
                                            ToastUtils.showToast(CreateWorkActivity.this, "请保证结束时间在开始时间之后~");
                                        } else {
                                            if (!"".equals(name) && !"".equals(start) && !"".equals(JoinPersons) && !"".equals(end)
                                                    && !"".equals(LeaderId) && !"".equals(content) && !"".equals(Location)
                                                    && !"".equals(AddUserId)) {

                                                if (flag == 2) {  //编辑
                                                    Log.i(TAG, "onClick Id编辑: " + workBean.getId());

                                                    workPresenter.editWork(workBean.getId(), name, pId, start,
                                                            JoinPersons, end,
                                                            LeaderId, content, Location, address,
                                                            AddUserId, progress, et_label.getText().toString(), nameStr, leaderIdStr, nextId);
                                                } else {   //新建
                                                    workPresenter.createWork(name, pId, start,
                                                            JoinPersons, end,
                                                            LeaderId, content, Location, address,
                                                            AddUserId, progress, et_label.getText().toString(), nameStr, leaderIdStr, nextId);
                                                }
                                            } else {

                                                if (name == null || "".equals(name)) {
                                                    ToastUtils.showToast(CreateWorkActivity.this, "工作名称不能为空~");
                                                } else if (LeaderId == null || "".equals(LeaderId)) {
                                                    ToastUtils.showToast(CreateWorkActivity.this, "工作负责人不能为空~");
                                                } else if (JoinPersons == null || "".equals(JoinPersons)) {
                                                    ToastUtils.showToast(CreateWorkActivity.this, "工作参与人员不能为空~");
                                                } else if (start == null || "".equals(start)) {
                                                    ToastUtils.showToast(CreateWorkActivity.this, "工作开始时间不能为空~");
                                                } else if (end == null || "".equals(end)) {
                                                    ToastUtils.showToast(CreateWorkActivity.this, "工作结束时间不能为空~");
                                                } else if (Location == null || "".equals(Location)) {
                                                    ToastUtils.showToast(CreateWorkActivity.this, "工作位置不能为空~");
                                                } else if (content == null || "".equals(content)) {
                                                    ToastUtils.showToast(CreateWorkActivity.this, "工作内容描述不能为空~");
                                                }

//                                        Toast.makeText(CreateWorkActivity.this, "请保持信息完整~", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }


                                    dialog.dismiss();

                                } else if (which == DialogAction.NEGATIVE) {
                                    dialog.dismiss();
                                }
                            }
                        }).show();
                break;
            case R.id.ib_work_fuzeren_add:
                Intent fuzerenIntent = new Intent(this, ChooseActivity.class);
                fuzerenIntent.putExtra("flag", "fuzeren");
                startActivityForResult(fuzerenIntent, CommonUtils.PROJECT_CHOOSE_PERSON_FUZEREN);
                break;
            case R.id.ib_work_member_add:
                Intent memberIntent = new Intent(this, ChooseActivity.class);
                memberIntent.putExtra("flag", "member");
                memberIntent.putStringArrayListExtra("indexList", (ArrayList<String>) indexList);
                startActivityForResult(memberIntent, CommonUtils.PROJECT_CHOOSE_PERSON_MEMBER);
                break;
            case R.id.tv_work_time_start:
                new DataPick(this, "工作开始时间", tv_time_start);
                break;
            case R.id.tv_work_time_end:
                new DataPick(this, "工作结束时间", tv_time_end);
                break;
            case R.id.tv_work_location:
                Intent locationIntent = new Intent(this, BaseMapActivity.class);
                locationIntent.putExtra("map", "project");
                startActivityForResult(locationIntent, CommonUtils.PROJECT_MAP_LOCATION_CODE);
                break;
            case R.id.tv_work_add:
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

                        ll_work_add.setVisibility(View.VISIBLE);
                        rv_work_add.setLayoutManager(new LinearLayoutManager(CreateWorkActivity.this));
                        projectAddKeyAdapter = new ProjectAddKeyAdapter(CreateWorkActivity.this, projectAddBeanList, 0);
                        rv_work_add.setAdapter(projectAddKeyAdapter);
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

            case R.id.tv_event_choose:
                workPresenter.getEventModels(1, 50, templet);
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

                Location = "point(" + longitude + "," + latitude + ")";
            } else if (requestCode == CommonUtils.PROJECT_CHOOSE_PERSON_FUZEREN) {//负责人 单选
                choosePerson.clear();
                choosePerson = data.getParcelableArrayListExtra("choose");
                for (int i = 0; i < choosePerson.size(); i++) {
                    tv_work_fuzeren.setText(choosePerson.get(i).getFrame().getRealName());
                    GlideHelper.loadNet(civ_head, Globle.PHOTO_URL + choosePerson.get(i).getFrame().getImage(), R.mipmap.image_weibo_home_2);

                    LeaderId = choosePerson.get(i).getFrame().getId();
                }

            } else if (requestCode == CommonUtils.PROJECT_CHOOSE_PERSON_MEMBER) {//参与人员 多选
                choosePerson.clear();
                assignPersonList.clear();
                tv_canyu.setVisibility(View.GONE);
                List<String> list = new ArrayList<>();
                pile_member.removeAllViews();
                nameList = new ArrayList<>();
                choosePerson = data.getParcelableArrayListExtra("choose");
                indexList = data.getStringArrayListExtra("indexList");
                Log.i("member", "onClick 人员集合: " + choosePerson.size() + choosePerson);
                Log.i("member", "onActivityResult 下标集合: " + indexList.size() + indexList);
                for (int i = 0; i < choosePerson.size(); i++) {
                    imageView = (CircleImageView) inflater.inflate(R.layout.fuzeren_head_praise, pile_member, false);
                    if (i < 6) {

                        GlideHelper.loadNet(imageView, Globle.PHOTO_URL + choosePerson.get(i).getFrame().getImage(), R.mipmap.image_weibo_home_2);
                        pile_member.addView(imageView);
                    } else if (i == 6) {
                        ib_member_more.setVisibility(View.VISIBLE);
                    }
                    JoinPersons = choosePerson.get(i).getFrame().getId();
                    assignPersonList.add(JoinPersons);
                    String s1 = assignPersonList.toString();
                    JoinPersons = s1.substring(1, s1.length() - 1).trim();


                    String image = choosePerson.get(i).getFrame().getImage();
                    String name = choosePerson.get(i).getFrame().getRealName();
                    list.add(image + "," + name);

                    final EventAssignAdapter assignAdapter = new EventAssignAdapter(CreateWorkActivity.this, list);
                    pile_member.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new MaterialDialog.Builder(CreateWorkActivity.this)
                                    .items(list)
                                    .adapter(assignAdapter,
                                            new LinearLayoutManager(CreateWorkActivity.this))
                                    .show();
                        }
                    });
                    assignAdapter.setDeleteAssignClickListener(new EventAssignAdapter.DeleteAssignClickListener() {
                        @Override
                        public void deleteAssign(int position, String item) {

                        }
                    });

//                    assignAdapter.setDeleteAssignClickListener(new EventAssignAdapter.DeleteAssignClickListener() {
//                        @Override
//                        public void deleteAssign(int position, String item) {
//
//                            new MaterialDialog.Builder(CreateWorkActivity.this)
//                                    .title("删除人员！")
//                                    .content("确定删除该参与人员！")
//                                    .positiveText("确定")
//                                    .negativeText("取消")
//                                    .widgetColor(Color.BLUE)//不再提醒的checkbox 颜色
//
//                                    .onAny(new MaterialDialog.SingleButtonCallback() {
//                                        @Override
//                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                            if (which == DialogAction.POSITIVE) {
//                                                list.remove(position);
//                                                assignAdapter.notifyDataSetChanged();
//
//                                                dialog.dismiss();
//
//                                            } else if (which == DialogAction.NEGATIVE) {
//                                                dialog.dismiss();
//                                            }
//                                        }
//                                    }).show();
//                        }
//                    });


                    String realName = choosePerson.get(i).getFrame().getRealName();
                    nameList.add(realName);
                }
                String s = nameList.toString();
                String name = s.substring(1, s.length() - 1).trim();
                tv_canyu.setText(name);

            } else if (requestCode == CommonUtils.WORK_CREATE_CHOOSE_PROJECT) {
                choosePerson = data.getParcelableArrayListExtra("choose");
                for (int i = 0; i < choosePerson.size(); i++) {
                    projectName = choosePerson.get(i).getBelongProject().getName();
                    if ("独立工作".equals(projectName)) {
                        tv_projectname.setText("独立工作,无所属项目");
                        pId = "";
                    } else {
                        tv_projectname.setText(projectName);
                        pId = choosePerson.get(i).getBelongProject().getId();
                    }

                }
            } else if (requestCode == CommonUtils.MODEL_CHOOSE_WORK_FUZEREN) {  //模板选择后 每个事件负责人 单选

                choosePerson = data.getParcelableArrayListExtra("choose");
                Log.i(TAG, "onActivityResult: " + choosePerson);

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


                modelAdapter = new ModelChooseWorkAdapter(CreateWorkActivity.this, names, 0);
                rv_event_model.setAdapter(modelAdapter);

            }
        }

    }

    @Override
    public void createWork(String string) {
        Log.i(TAG, "createWork: " + string);
        try {
            JSONObject jsonObject = new JSONObject(string);
            String result = jsonObject.getString("Result");
            String message = jsonObject.getString("Message");
            if ("保存成功！".equals(message)) {
                Toast.makeText(CreateWorkActivity.this, "工作提交成功~", Toast.LENGTH_SHORT).show();
                finish();
            } else if ("此工作名称已经存在！".equals(message)) {
                Toast.makeText(CreateWorkActivity.this, "此工作名称已经存在~", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CreateWorkActivity.this, "工作提交失败~", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void editWork(String string) {

        Log.i(TAG, "editWork: " + string);
        try {
            JSONObject jsonObject = new JSONObject(string);
            String result = jsonObject.getString("Result");
            String message = jsonObject.getString("Message");
            if ("工作信息修改成功！".equals(message)) {
                Toast.makeText(CreateWorkActivity.this, message, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            } else {
                Toast.makeText(CreateWorkActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void getEventModel(String string) {
        Log.i(TAG, "getEventModel: " + string);

        EventModelData eventModelData = new Gson().fromJson(string, new TypeToken<EventModelData>() {
        }.getType());
        List<EventModelData.DataBean> modelDataList = eventModelData.getData();


        modeldatas = new ArrayList<>();
        modeldatas.add(0, ModelUtil.getEventModelEmpty());

        modeldatas.addAll(modelDataList);
        List<String> modelNames = new ArrayList<>();
        if (modeldatas != null) {
            for (int i = 0; i < modeldatas.size(); i++) {
                String name = modeldatas.get(i).getName();
                modelNames.add(name);
            }
        }
        new MaterialDialog.Builder(this)
                .title(R.string.eventModel)
                .items(modelNames)
                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        tv_choose.setText(modelNames.get(which));
                        List<EventModelData.DataBean.TempletsBean> mContentList = modeldatas.get(which).getTemplets();

                        Log.i(TAG, "getEventModel onSelection: " + mContentList.size() + mContentList);
                        names = new ArrayList<>();
                        for (int i = 0; i < mContentList.size(); i++) {
                            names.add(new ModelWorkBean(mContentList.get(i).getName(), "", ""));
                        }
                        rv_event_model.setLayoutManager(new LinearLayoutManager(CreateWorkActivity.this));
                        modelAdapter = new ModelChooseWorkAdapter(CreateWorkActivity.this, names, 0);
                        rv_event_model.setAdapter(modelAdapter);
                        return true;
                    }
                })
                .positiveText(R.string.confirm)
                .negativeText(R.string.cancle)
                .show();

    }

    @Override
    public void getEventList(String string) {
        Log.i(TAG, "getEventList: " + string);
        if ("".equals(string) || "null".equals(string)) {
            ll_event_model.setVisibility(View.VISIBLE);
            nameStr = "";
            leaderIdStr = "";
        } else {
//            ll_event_model.setVisibility(View.GONE);
            ll_event_model.setVisibility(View.VISIBLE);
        }
    }

}