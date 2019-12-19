package com.example.q_kang.pmsystem.ui.activity.work;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.BaseActivity;
import com.example.q_kang.pmsystem.dao.AdminDao;
import com.example.q_kang.pmsystem.modules.bean.bean.AllShowData;
import com.example.q_kang.pmsystem.modules.bean.bean.EventBusMessage;
import com.example.q_kang.pmsystem.modules.bean.bean.MessageNotifyData;
import com.example.q_kang.pmsystem.modules.bean.bean.event.EventData;
import com.example.q_kang.pmsystem.modules.bean.bean.filter.FilterData;
import com.example.q_kang.pmsystem.modules.bean.bean.work.WorkData;
import com.example.q_kang.pmsystem.present.im.Work_EventPresenter;
import com.example.q_kang.pmsystem.ui.activity.event.CreateEventActivity;
import com.example.q_kang.pmsystem.ui.activity.map.BaseMapActivity;
import com.example.q_kang.pmsystem.ui.adpter.event.EventAssignAdapter;
import com.example.q_kang.pmsystem.ui.adpter.work.WorkDetailAdapter;
import com.example.q_kang.pmsystem.ui.view.Utils.CommonUtils;
import com.example.q_kang.pmsystem.ui.view.Utils.Utils.GlideHelper;
import com.example.q_kang.pmsystem.ui.view.Utils.view_.PileLayout;
import com.example.q_kang.pmsystem.view.Work_EventListView;
import com.fingdo.statelayout.StateLayout;
import com.github.clans.fab.FloatingActionButton;
import com.jimmy.common.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class WorkDetailActivity extends BaseActivity implements Work_EventListView {


    @BindView(R.id.iv_back_workDetail)
    ImageView iv_back_workDetail;


    @BindView(R.id.recy_work_event)
    RecyclerView recy_work_event;
    @BindView(R.id.pl_work_detail_member)
    PileLayout pl_project_member;
    @BindView(R.id.tv_work_detail_canyu)
    TextView tv_canyu;
    @BindView(R.id.iv_work_edit)
    ImageView iv_edit;
    @BindView(R.id.ib_work_detail_location)
    ImageButton ib_location;
    @BindView(R.id.tv_work_detail_address)
    TextView tv_address;

    @BindView(R.id.tv_work_detail_title)
    TextView tv_title;
    @BindView(R.id.tv_work_detail_project)
    TextView tv_project;
    @BindView(R.id.civ_work_detail_fuzeren)
    CircleImageView civ_fuzeren;
    @BindView(R.id.tv_work_detail_fuzeren)
    TextView tv_fuzeren;
    @BindView(R.id.tv_work_detail_department)
    TextView tv_department;
    @BindView(R.id.tv_work_detail_date)
    TextView tv_date;
    @BindView(R.id.tv_work_detail_content)
    TextView tv_content;
    @BindView(R.id.fab_add_event)
    FloatingActionButton fab_add_event;
    @BindView(R.id.tv_eventlist)
    TextView textView;
    @BindView(R.id.tv_work_detail_label)
    TextView tv_label;
    @BindView(R.id.tv_work_detail_progress)
    TextView tv_progress;
    @BindView(R.id.ll_partIn)
    LinearLayout ll_canyu;
    @BindView(R.id.sl_work_event)
    StateLayout sl_work_event;


    private FilterData filterData;
    private CircleImageView imageView;
    private LayoutInflater from;
    private WorkDetailAdapter workDetailAdapter;

    private WorkData.DataBean workBean;

    private Work_EventPresenter work_eventPresenter;
    private List<EventData.DataBean> work_eventList = new ArrayList<>();

    private String proLeaderId, proAddId;
    private int flag;
    private AllShowData alldata;

    private String marker_id;

    private int showAddIv ;

    private MessageNotifyData.DataBean messageNotifyData;

    @Override
    public int bindLayout() {
        return R.layout.activity_work_detail;
    }

    @Override
    public void doBusiness(Context mContext) {

        EventBus.getDefault().register(this);
        initState();

        from = LayoutInflater.from(this);

        workBean = getIntent().getParcelableExtra("work");
        Log.i(TAG, "doBusiness: " + workBean);

        flag = getIntent().getIntExtra("flag", 0);
        proLeaderId = getIntent().getStringExtra("proLeaderId");
        proAddId = getIntent().getStringExtra("proAddId");


        work_eventPresenter = new Work_EventPresenter(this);


        marker_id = getIntent().getStringExtra("marker_id");
        alldata = getIntent().getParcelableExtra("alldata");
        messageNotifyData = getIntent().getParcelableExtra("messagenotify");
        int flag = getIntent().getIntExtra("flag", 0);
        if (flag == 4) {
            work_eventPresenter.getWorkDetail(alldata.getId());
            work_eventPresenter.getWork_EventList(alldata.getId());
        } else if (flag == 5) {
            work_eventPresenter.getWorkDetail(marker_id);
            work_eventPresenter.getWork_EventList(marker_id);
        }  else if (flag == 10) {
            work_eventPresenter.getWorkDetail(messageNotifyData.getId());
            work_eventPresenter.getWork_EventList(messageNotifyData.getId());
        }else {
            work_eventPresenter.getWork_EventList(workBean.getId());
        }

        if (workBean != null) {
            initWorkInfo();
        }
    }

    private void initState() {
        if (!CommonUtils.isNetConnected(WorkDetailActivity.this)) {
            sl_work_event.showNoNetworkView(R.string.no_net, R.drawable.ic_no_net);
            sl_work_event.setRefreshListener(new StateLayout.OnViewRefreshListener() {
                @Override
                public void refreshClick() {
                    if (!CommonUtils.isNetConnected(WorkDetailActivity.this)) {
                        ToastUtils.showToast(WorkDetailActivity.this, "请确保网络连接正常~");
                    } else {
                        if (flag == 4) {
                            work_eventPresenter.getWorkDetail(alldata.getId());
                            work_eventPresenter.getWork_EventList(alldata.getId());
                        } else if (flag == 5) {
                            work_eventPresenter.getWorkDetail(marker_id);
                            work_eventPresenter.getWork_EventList(marker_id);
                        } else if (flag == 10){
                            work_eventPresenter.getWorkDetail(messageNotifyData.getId());
                            work_eventPresenter.getWork_EventList(messageNotifyData.getId());
                        }else {
                            work_eventPresenter.getWork_EventList(workBean.getId());
                        }
                    }
                }

                @Override
                public void loginClick() {

                }
            });
        } else {
            sl_work_event.showContentView();
        }
    }

    private void initFacButton() {
        fab_add_event.hide(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fab_add_event.show(true);
                fab_add_event.setShowAnimation(AnimationUtils.loadAnimation(WorkDetailActivity.this, R.anim.show_from_bottom));
                fab_add_event.setHideAnimation(AnimationUtils.loadAnimation(WorkDetailActivity.this, R.anim.hide_to_bottom));
            }
        }, 300);

        recy_work_event.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int mScrollThreshold;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isSignificantDelta = Math.abs(dy) > mScrollThreshold;
                if (isSignificantDelta) {
                    if (dy > 0) {
                        fab_add_event.hide(true);
                    } else {
                        fab_add_event.show(true);
                    }
                }
            }
        });
    }

    private void initWorkInfo() {

        Log.i(TAG, "initWorkInfo workBean: " + workBean);



        recy_work_event.setNestedScrollingEnabled(false);
        recy_work_event.setFocusableInTouchMode(false);

        recy_work_event.setLayoutManager(new LinearLayoutManager(this));
        workDetailAdapter = new WorkDetailAdapter(WorkDetailActivity.this,work_eventList,workBean,showAddIv);
        recy_work_event.setAdapter(workDetailAdapter);

        /**
         * 工作编辑
         * 编辑：项目创建人、负责人
         * */
        String userID = AdminDao.getUserID();
        String addUserId = workBean.getAddUserId();
        String leaderId = workBean.getLeaderId();

        if (userID.equals(leaderId) || userID.equals(addUserId)) {
            iv_edit.setVisibility(View.VISIBLE);
            fab_add_event.setVisibility(View.VISIBLE);
            showAddIv = 1;
            initFacButton();
        } else {
            iv_edit.setVisibility(View.GONE);
            fab_add_event.setVisibility(View.GONE);
            showAddIv = 0;
        }
        if ("100".equals(workBean.getProgress())) {
            iv_edit.setVisibility(View.GONE);
        }
        /**
         * 新建事件
         * 创建：单独工作相关  工作创建人、负责人、工作参与人员
         *      项目相关     项目创建人、负责人；工作创建人、负责人、工作参与人员
         * */
        String joinPerson = workBean.getJoinPerson();

        String pId = workBean.getpId();  //与项目有关
        if (!"".equals(pId) && !"00000000-0000-0000-0000-000000000000".equals(pId)) {

            if (userID.equals(proLeaderId) || userID.equals(proAddId) || userID.equals(leaderId) || userID.equals(addUserId) || joinPerson.contains(userID)) {
                fab_add_event.setVisibility(View.VISIBLE);
                showAddIv = 1;
                initFacButton();
            } else {
                fab_add_event.setVisibility(View.GONE);
                showAddIv = 0;
            }
        } else {
            if (userID.equals(leaderId) || userID.equals(addUserId) || joinPerson.contains(userID)) {
                fab_add_event.setVisibility(View.VISIBLE);
                showAddIv = 1;
                initFacButton();
            } else {
                fab_add_event.setVisibility(View.GONE);
                showAddIv = 0;
            }
        }


        tv_title.setText(workBean.getName());
        String projectName = workBean.getProjectName();
        if ("null".equals(projectName) || "".equals(projectName) || null == projectName) {
            projectName = "独立工作，暂无所属项目";
        } else {
            projectName = workBean.getProjectName();
        }
        tv_project.setText("@ " + projectName);
        String leaderImage = workBean.getLeaderImage();
        GlideHelper.loadNet(civ_fuzeren, Globle.PHOTO_URL + leaderImage, R.mipmap.image_weibo_home_2);


        String progress = workBean.getProgress();
        if ("null".equals(progress) || "".equals(progress) || null == progress) {
            tv_progress.setText("工作进度为0");
        } else {
            tv_progress.setText("已完成 " + progress + "%");
        }
        String label = workBean.getLable();
        if ("null".equals(label) || "".equals(label) || null == label) {
            label = "暂无所属标签";
        }
        tv_label.setText(label);
        tv_fuzeren.setText(workBean.getLeader());


        String joinPersons = workBean.getJoinPersons();
//        Log.i(TAG, "initWorkInfo joinPersons: " + joinPersons);
        if ("".equals(workBean.getJoinPersons()) || "null".equals(workBean.getJoinPersons()) || null == joinPersons) {
            joinPersons = "暂无参与人员";
        }
        tv_canyu.setText(joinPersons);
        tv_date.setText(workBean.getStartTime().split("T")[0] + " ~ " + workBean.getEndTime().split("T")[0]);


        String joinImage = workBean.getJoinImage();
//        Log.i(TAG, "initWorkInfo joinImage: " + joinImage);
        if ("null".equals(joinImage) || "".equals(joinImage) || joinImage == null) {
            tv_canyu.setText("暂无参与人员");
            ll_canyu.setEnabled(false);
        } else {
            ll_canyu.setEnabled(true);
            String[] split = joinImage.split(",");
            int member = split.length;
            pl_project_member.removeAllViews();
            if (member == 0){
                for (int j = 0; j < joinPersons.split(",").length; j++) {
                    imageView = (CircleImageView) from.inflate(R.layout.fuzeren_head_praise, pl_project_member, false);
                    GlideHelper.loadNet(imageView, " ", R.mipmap.image_weibo_home_2);
                    pl_project_member.addView(imageView);
                }
            }else {
                for (int j = 0; j < split.length; j++) {
                    imageView = (CircleImageView) from.inflate(R.layout.fuzeren_head_praise, pl_project_member, false);
                    GlideHelper.loadNet(imageView, Globle.PHOTO_URL + split[j], R.mipmap.image_weibo_home_2);
                    pl_project_member.addView(imageView);
                }
            }

        }


        String location = workBean.getLocation();

//        Log.i(TAG, "initWorkInfo: " + location);
        if ("null".equals(location) || "".equals(location) || null == location) {
            ib_location.setVisibility(View.GONE);
        }else {
            ib_location.setVisibility(View.VISIBLE);
        }
        String address = workBean.getAddress();
        if ("null".equals(address) || "".equals(address) || null == address) {
            address = "";
        }else {
            address = address.trim();
        }
        tv_address.setText(address);

        //工作内容填写后表明工作基本信息完成，可创建事件
        String content = workBean.getContent();
        tv_content.setText(content);
        if ("null".equals(content) || "".equals(content) || null == content) {
            fab_add_event.setVisibility(View.GONE);
            showAddIv = 0;
        }

        String leaderDepartment = workBean.getLeaderDepartment();
        String role = workBean.getLeaderRole();
        if ("null".equals(role) || "".equals(role) || null == role) {
            role = "";
        }else {
            if (role.equals(Globle.ROLE_ZJL)) {
                role = Globle.ROLE_JZ;
            } else if (role.equals(Globle.ROLE_FZ)) {
                role = Globle.ROLE_FJZ;
            } else if (role.equals(Globle.ROLE_BMJL)) {
                role = Globle.ROLE_KZ;
            }else if (role.equals(Globle.ROLE_YUANGONG)) {
                role = Globle.ROLE_KY;
            }
        }

        if ("null".equals(leaderDepartment) || "".equals(leaderDepartment) || null == leaderDepartment) {
            leaderDepartment = "";
        }
        tv_department.setText(role + "  " + leaderDepartment);

    }


    //delete_work_event
    /**
     * EventBus监听
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(EventBusMessage messageEvent) {
        switch (messageEvent.getMessage()) {
            case "delete_work_event":
                work_eventList.clear();
                if (flag == 4) {
                    work_eventPresenter.getWork_EventList(alldata.getId());
                } else if (flag == 5) {
                    work_eventPresenter.getWork_EventList(marker_id);
                }  else if (flag == 10){
                    work_eventPresenter.getWork_EventList(messageNotifyData.getId());
                }else {
                    work_eventPresenter.getWork_EventList(workBean.getId());
                }
                break;

        }
    }

    @OnClick({R.id.iv_back_workDetail, R.id.iv_work_edit, R.id.ib_work_detail_location, R.id.fab_add_event, R.id.ll_partIn})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back_workDetail:
                finish();
                break;
            case R.id.iv_work_edit:
                Intent editIntent = new Intent(WorkDetailActivity.this, CreateWorkActivity.class);
                editIntent.putExtra("flag", 2);
                editIntent.putExtra("work", workBean);
                startActivityForResult(editIntent, Globle.WORK_EDIT);
                break;
            case R.id.ib_work_detail_location:
                String location = workBean.getLocation();
                if ("".equals(location) || "null".equals(location) || null == location) {
                    ib_location.setVisibility(View.GONE);
                } else {
                    String substring = location.substring(6, location.length() - 1);
                    String longitude = substring.split(",")[0];
                    String latitude = substring.split(",")[1];

                    Intent intent = new Intent(WorkDetailActivity.this, BaseMapActivity.class);
                    intent.putExtra("lat", Double.valueOf(latitude));
                    intent.putExtra("long", Double.valueOf(longitude));
                    intent.putExtra("type", "work");
                    intent.putExtra("map", "location_");
                    intent.putExtra("name", workBean.getName());
                    startActivity(intent);
                }

                break;
            case R.id.fab_add_event:
                Intent intent1 = new Intent(this, CreateEventActivity.class);
                intent1.putExtra("work", workBean);
                intent1.putExtra("flag", 3);
                startActivity(intent1);
                break;
            case R.id.ll_partIn:
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
                List<String> list = new ArrayList<>();

                if (image.length == 0) {
                    for (int i = 0; i < name.length; i++) {
                        String s = " ";
                        String s1 = name[i];
                        list.add(s + "," + s1);
                    }
                }else {
                    for (int i = 0; i < name.length; i++) {
                        String s = image[i];
                        String s1 = name[i];
                        list.add(s + "," + s1);
                    }
                }

                new MaterialDialog.Builder(this)
                        .items(list)
                        .adapter(new EventAssignAdapter(WorkDetailActivity.this, list), new LinearLayoutManager(WorkDetailActivity.this))
                        .show();
                break;
        }
    }


    @Override
    public void showWork_EvetList(EventData eventData) {
        work_eventList.clear();
        Log.i(TAG, "showWork_EvetList: " + eventData);
        if ("null".equals(eventData) || null == eventData) {
            textView.setVisibility(View.GONE);
            sl_work_event.showEmptyView("", R.drawable.ic_no_data);
        } else {
            textView.setVisibility(View.VISIBLE);
            sl_work_event.showContentView();
            List<EventData.DataBean> data = eventData.getData();
            work_eventList.addAll(data);
            workDetailAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getWorkDetail(WorkData.DataBean dataBean) {
        Log.i(TAG, "getWorkDetail: " + dataBean);
        workBean = dataBean;
        initWorkInfo();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Globle.WORK_EDIT) {

                if (flag == 4) {
                    work_eventPresenter.getWorkDetail(alldata.getId());
                    work_eventPresenter.getWork_EventList(alldata.getId());
                } else if (flag == 5) {
                    work_eventPresenter.getWorkDetail(marker_id);
                    work_eventPresenter.getWork_EventList(marker_id);
                }  else if (flag == 10) {
                    work_eventPresenter.getWorkDetail(messageNotifyData.getId());
                    work_eventPresenter.getWork_EventList(messageNotifyData.getId());
                }else {
                    work_eventPresenter.getWorkDetail(workBean.getId());
                    work_eventPresenter.getWork_EventList(workBean.getId());
                }

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (flag == 4) {
            work_eventPresenter.getWorkDetail(alldata.getId());
            work_eventPresenter.getWork_EventList(alldata.getId());
        } else if (flag == 5) {
            work_eventPresenter.getWorkDetail(marker_id);
            work_eventPresenter.getWork_EventList(marker_id);
        }  else if (flag == 10) {
            work_eventPresenter.getWorkDetail(messageNotifyData.getId());
            work_eventPresenter.getWork_EventList(messageNotifyData.getId());
        }else {
            work_eventPresenter.getWork_EventList(workBean.getId());
        }
    }


    @Override
    public void setState(int state) {
        switch (state) {
            case Globle.LOADING_FAIL:
                if (!CommonUtils.isNetConnected(WorkDetailActivity.this)) {
                    sl_work_event.showNoNetworkView(R.string.no_net, R.drawable.ic_no_net);
                    sl_work_event.setRefreshListener(new StateLayout.OnViewRefreshListener() {
                        @Override
                        public void refreshClick() {
                            if (!CommonUtils.isNetConnected(WorkDetailActivity.this)) {
                                ToastUtils.showToast(WorkDetailActivity.this, "请确保网络连接正常~");
                            } else {

                                sl_work_event.showTimeoutView("", R.drawable.ic_time_out);
                                sl_work_event.setRefreshListener(new StateLayout.OnViewRefreshListener() {
                                    @Override
                                    public void refreshClick() {
                                        if (state == Globle.LOADING_FAIL) {
                                            sl_work_event.showTimeoutView("", R.drawable.ic_time_out);
                                            ToastUtils.showToast(WorkDetailActivity.this, "请求超时，请稍后再试~");
                                        } else if (state == Globle.LOADING_SUCEESS) {
                                            sl_work_event.showContentView();
                                        } else {
                                            initState();
                                        }
                                    }

                                    @Override
                                    public void loginClick() {

                                    }
                                });
                            }
                        }

                        @Override
                        public void loginClick() {

                        }
                    });
                }
                break;
        }
    }

}
