package com.example.q_kang.pmsystem.ui.activity.project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.dao.AdminDao;
import com.example.q_kang.pmsystem.modules.bean.bean.AllShowData;
import com.example.q_kang.pmsystem.modules.bean.bean.EventBusMessage;
import com.example.q_kang.pmsystem.modules.bean.bean.MessageNotifyData;
import com.example.q_kang.pmsystem.modules.bean.bean.project.ProjectAddBean;
import com.example.q_kang.pmsystem.modules.bean.bean.project.ProjectData;
import com.example.q_kang.pmsystem.modules.bean.bean.work.WorkData;
import com.example.q_kang.pmsystem.present.im.Pro_WorkListPresenter;
import com.example.q_kang.pmsystem.ui.activity.map.BaseMapActivity;
import com.example.q_kang.pmsystem.ui.activity.work.CreateWorkActivity;
import com.example.q_kang.pmsystem.ui.adpter.project.CardPagerAdapter;
import com.example.q_kang.pmsystem.ui.adpter.project.ProjectAddKeyAdapter;
import com.example.q_kang.pmsystem.ui.view.Utils.CommonUtils;
import com.example.q_kang.pmsystem.ui.view.Utils.view_.CustomViewPager;
import com.example.q_kang.pmsystem.ui.view.Utils.view_.ShadowTransformer;
import com.example.q_kang.pmsystem.ui.view.Utils.view_.WaveProgressView;
import com.example.q_kang.pmsystem.view.Pro_WorkListView;
import com.fingdo.statelayout.StateLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jimmy.common.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProjectDetailActivity extends AppCompatActivity implements Pro_WorkListView {

    private String TAG = "ProjectDetailActivity";
    @BindView(R.id.ctl_detail)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar_project_detail)
    Toolbar toolbar;
    @BindView(R.id.ib_detail_file)
    ImageButton ib_file;
    @BindView(R.id.fab_add_work)
    FloatingActionButton fab_add;
    @BindView(R.id.wpv_progress)
    WaveProgressView progressView;
    @BindView(R.id.tv_detail_name)
    TextView tv_name;
    @BindView(R.id.tv_detail_fuzeren)
    TextView tv_fuzeren;
    @BindView(R.id.tv_detail_label)
    TextView tv_label;
    @BindView(R.id.tv_detail_begintime)
    TextView tv_beginTime;
    @BindView(R.id.tv_detail_endtime)
    TextView tv_endTime;
    @BindView(R.id.tv_detail_location)
    TextView tv_location;
    @BindView(R.id.tv_detail_remark)
    TextView tv_remark;

    @BindView(R.id.vp_project_work)
    CustomViewPager viewPager;
    @BindView(R.id.nestScrollView)
    NestedScrollView nestScrollView;
    @BindView(R.id.ll_dot)
    LinearLayout ll_dot;
    @BindView(R.id.ll_project_other)
    LinearLayout ll_other;
    @BindView(R.id.rv_project_other)
    RecyclerView rv_other;
    @BindView(R.id.sl_pro_work)
    StateLayout sl_pro_work;
    private List<ProjectAddBean> projectAddBeanList = new ArrayList<>();
    private ProjectAddKeyAdapter projectAddKeyAdapter;


    private ImageView[] mImageViews;//指示点集合
    private ImageView imageView;
    private List<WorkData.DataBean> workBeanList = new ArrayList<>();

    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer shadowTransformer;

    private ProjectData.DataBean projectBean;

    private Pro_WorkListPresenter pro_workListPresenter;

    private String userID, leaderId, addUserId;
    private int flag;
    private AllShowData alldata;
    private String marker_id;

    private String projectId,projectName;

    private MessageNotifyData.DataBean messageNotifyData;


    private static final int one = 0X0001;
    private int progress;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progress += 5;
            switch (msg.what) {
                case one:
                    String progress = projectBean.getProgress();
                    if ("".equals(progress)) {
                        progress = "0";
                    } else {
                        if (ProjectDetailActivity.this.progress <= Integer.parseInt(progress)) {
                            progressView.setCurrent(ProjectDetailActivity.this.progress, "完成度\n" + progress + "%");
                            progressView.setmWaveSpeed(20);
                            sendEmptyMessageDelayed(one, 100);
                        }
                    }

                    break;
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        initState();


        pro_workListPresenter = new Pro_WorkListPresenter(this);

        projectBean = getIntent().getParcelableExtra("project");
        Log.i(TAG, "onCreate projectBean: " + projectBean);

        marker_id = getIntent().getStringExtra("marker_id");
        alldata = getIntent().getParcelableExtra("alldata");
        messageNotifyData = getIntent().getParcelableExtra("messagenotify");
        flag = getIntent().getIntExtra("flag", 0);
//        if (flag == 4) {
//            pro_workListPresenter.getProDetail(alldata.getId());
//            pro_workListPresenter.getPro_WorkList(alldata.getId());
//        } else if (flag == 5) {
//            pro_workListPresenter.getProDetail(marker_id);
//            pro_workListPresenter.getPro_WorkList(marker_id);
//        }  else if (flag == 10) {
//            pro_workListPresenter.getProDetail(messageNotifyData.getId());
//            pro_workListPresenter.getPro_WorkList(messageNotifyData.getId());
//        } else {
//            pro_workListPresenter.getPro_WorkList(projectBean.getId());
//        }


        if (projectBean != null) {
            initProjectDetailInfo();
        }

    }

    private void initState() {
        if (!CommonUtils.isNetConnected(ProjectDetailActivity.this)) {
            sl_pro_work.showNoNetworkView(R.string.no_net, R.drawable.ic_no_net);
            sl_pro_work.setRefreshListener(new StateLayout.OnViewRefreshListener() {
                @Override
                public void refreshClick() {
                    if (!CommonUtils.isNetConnected(ProjectDetailActivity.this)) {
                        ToastUtils.showToast(ProjectDetailActivity.this, "请确保网络连接正常~");
                    } else {
                        if (flag == 4) {
                            pro_workListPresenter.getProDetail(alldata.getId());
                            pro_workListPresenter.getPro_WorkList(alldata.getId());
                        } else if (flag == 5) {
                            pro_workListPresenter.getProDetail(marker_id);
                            pro_workListPresenter.getPro_WorkList(marker_id);
                        }  else if (flag == 10) {
                            pro_workListPresenter.getProDetail(messageNotifyData.getId());
                            pro_workListPresenter.getPro_WorkList(messageNotifyData.getId());
                        }else {
                            pro_workListPresenter.getPro_WorkList(projectBean.getId());
                        }

                    }
                }

                @Override
                public void loginClick() {

                }
            });
        } else {
            sl_pro_work.showContentView();
        }
    }

    private void initProjectDetailInfo() {

        toolbar.setFocusable(true);
        toolbar.setFocusableInTouchMode(true);
        toolbar.requestFocus();

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.image_b);
        //CollapsedTitleText推上去Toolbar标题
        //ExpandedTitleText展开式标题
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTypeface(Typeface.SERIF);
        collapsingToolbarLayout.setExpandedTitleGravity(Gravity.CENTER | Gravity.BOTTOM);
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setExpandedTitleTypeface(Typeface.DEFAULT_BOLD);
        collapsingToolbarLayout.setTitle(projectBean.getName());

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        handler.sendEmptyMessageDelayed(one, 500);


        /**
         * 创建：项目无关  任何人
         *      项目相关  项目创建人、负责人
         * */
        userID = AdminDao.getUserID();
        leaderId = projectBean.getLeaderId();
        addUserId = projectBean.getAddUserId();

        projectId = projectBean.getId();
        projectName = projectBean.getName();

        //项目负责人 创建人
        if (userID.equals(leaderId) || userID.equals(addUserId)) {
            ib_file.setVisibility(View.VISIBLE);
            fab_add.setVisibility(View.VISIBLE);
        } else {
            fab_add.setVisibility(View.GONE);
            ib_file.setVisibility(View.GONE);
        }

        if ("100".equals(projectBean.getProgress())) {
            fab_add.setVisibility(View.GONE);
        }
        tv_name.setText(projectBean.getName());
        tv_fuzeren.setText(projectBean.getLeaderName());
        String lable = projectBean.getLable();
        if ("".equals(lable) || "null".equals(lable) || null == lable) {
            lable = "暂无所属标签";
        }
        tv_label.setText(lable);

        String startTime = projectBean.getStartTime().split(" ")[0];
        String endTime = projectBean.getEndTime().split(" ")[0];
        try {
            Date parse = new SimpleDateFormat("yyyy/MM/dd").parse(startTime);
            startTime = new SimpleDateFormat("yyyy-MM-dd").format(parse);

            Date parse1 = new SimpleDateFormat("yyyy/MM/dd").parse(endTime);
            endTime = new SimpleDateFormat("yyyy-MM-dd").format(parse1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tv_beginTime.setText(startTime);
        tv_endTime.setText(endTime);

        String address = projectBean.getAddress();
        if ("".equals(address) || "null".equals(address) || null == address){
            address = "";
        }else {
            address = address.trim();
        }
        tv_location.setText(address);


        String projectRemark = projectBean.getDescribe();
        if (!"".equals(projectRemark) && projectRemark != null) {
            tv_remark.setText(projectRemark);
        } else {
            tv_remark.setText("暂无项目描述");
        }

        ProjectAddBean projectAddBean;
        projectAddBeanList.clear();
        List<ProjectData.DataBean.OtherListBean> otherList = projectBean.getOtherList();
        if (!"[]".equals(otherList) && null != otherList) {
            ll_other.setVisibility(View.VISIBLE);
            for (int i = 0; i < otherList.size(); i++) {
                projectAddBean = new ProjectAddBean();
                String name = otherList.get(i).getName();
                String value = otherList.get(i).getValue();
                projectAddBean.setKey(name);
                projectAddBean.setValue(value);
                projectAddBeanList.add(projectAddBean);

                rv_other.setLayoutManager(new LinearLayoutManager(ProjectDetailActivity.this));
                projectAddKeyAdapter = new ProjectAddKeyAdapter(ProjectDetailActivity.this, projectAddBeanList, 1);
                rv_other.setAdapter(projectAddKeyAdapter);
                projectAddKeyAdapter.notifyDataSetChanged();
            }
        } else {
            ll_other.setVisibility(View.GONE);
        }


        nestScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int i = v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight();

                int dealtX = 0;
                int dealtY = 0;
                dealtX += Math.abs(scrollX - oldScrollX);
                dealtY += Math.abs(scrollY - oldScrollY);
                // 这里是够拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截
                if (dealtX < dealtY) {
                    nestScrollView.requestDisallowInterceptTouchEvent(true);
//                } else {
//                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                if (scrollY == 0 || scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {

                    nestScrollView.requestDisallowInterceptTouchEvent(true);
                    nestScrollView.setSmoothScrollingEnabled(false);
                }
            }
        });

        viewPager.setNestedScrollingEnabled(false);
    }



//    delete_pro_work

    /**
     * EventBus监听
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(EventBusMessage messageEvent) {
        switch (messageEvent.getMessage()) {
            case "delete_pro_work":
                workBeanList.clear();
                if (flag == 4) {
                    pro_workListPresenter.getPro_WorkList(alldata.getId());
                } else if (flag == 5) {
                    pro_workListPresenter.getPro_WorkList(marker_id);
                }  else if (flag == 10) {
                    pro_workListPresenter.getPro_WorkList(messageNotifyData.getId());
                }else {
                    pro_workListPresenter.getPro_WorkList(projectBean.getId());
                }
                break;

        }
    }

    @OnClick({R.id.fab_add_work, R.id.ib_detail_file, R.id.tv_detail_location})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.fab_add_work:
                Intent intent = new Intent(this, CreateWorkActivity.class);
                intent.putExtra("projectname", projectBean.getName());
                intent.putExtra("projectId", projectBean.getId());
                intent.putExtra("flag", 3);
                startActivity(intent);
                break;
            case R.id.ib_detail_file:   //编辑
//                Intent fileIntent = new Intent(ProjectDetailActivity.this, FileActivity.class);
//                startActivity(fileIntent);

                Intent editIntent = new Intent(ProjectDetailActivity.this, CreateProjectActivity.class);
                editIntent.putParcelableArrayListExtra("worklist", (ArrayList<? extends Parcelable>) workBeanList);
                editIntent.putExtra("project", projectBean);
                editIntent.putExtra("flag", 2);
                startActivityForResult(editIntent, Globle.PROJECT_EDIT);
                break;
            case R.id.tv_detail_location:
                String location = projectBean.getLocation();
                String substring = location.substring(6, location.length() - 1);
                String longitude = substring.split(",")[0];
                String latitude = substring.split(",")[1];

                Intent locationIntent = new Intent(ProjectDetailActivity.this, BaseMapActivity.class);
                locationIntent.putExtra("lat", Double.valueOf(latitude));
                locationIntent.putExtra("long", Double.valueOf(longitude));
                locationIntent.putExtra("type", "project");
                locationIntent.putExtra("map", "location_");
                locationIntent.putExtra("name", projectBean.getName());
                startActivity(locationIntent);
                break;

        }
    }

    @Override
    public void setState(int state) {
        switch (state) {
            case Globle.LOADING_FAIL:
                if (!CommonUtils.isNetConnected(ProjectDetailActivity.this)) {
                    sl_pro_work.showNoNetworkView(R.string.no_net, R.drawable.ic_no_net);
                    sl_pro_work.setRefreshListener(new StateLayout.OnViewRefreshListener() {
                        @Override
                        public void refreshClick() {
                            if (!CommonUtils.isNetConnected(ProjectDetailActivity.this)) {
                                ToastUtils.showToast(ProjectDetailActivity.this, "请确保网络连接正常~");
                            } else {

                                sl_pro_work.showTimeoutView("", R.drawable.ic_time_out);
                                sl_pro_work.setRefreshListener(new StateLayout.OnViewRefreshListener() {
                                    @Override
                                    public void refreshClick() {
                                        if (state == Globle.LOADING_FAIL) {
                                            sl_pro_work.showTimeoutView("", R.drawable.ic_time_out);
                                            ToastUtils.showToast(ProjectDetailActivity.this, "请求超时，请稍后再试~");
                                        } else if (state == Globle.LOADING_SUCEESS) {
                                            sl_pro_work.showContentView();
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

    @Override
    public void showPro_WorkList(String string) {
        Log.i(TAG, "showPro_WorkList: " + string);
        workBeanList.clear();

        if ("".equals(string)) {
            sl_pro_work.showEmptyView("", R.drawable.ic_no_data);
        } else {
            WorkData workData = new Gson().fromJson(string, new TypeToken<WorkData>() {
            }.getType());
            List<WorkData.DataBean> data = workData.getData();
            workBeanList.addAll(data);

            sl_pro_work.showContentView();

            mCardAdapter = new CardPagerAdapter(ProjectDetailActivity.this,leaderId, addUserId,projectId,projectName);
            for (int i = 0; i < workBeanList.size(); i++) {
                mCardAdapter.addCardItem(workBeanList.get(i));
            }


            //viewpager 指示器
            ll_dot.removeAllViews();
            mImageViews = new ImageView[workBeanList.size()];
            for (int i = 0; i < workBeanList.size(); i++) {
                imageView = new ImageView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.CENTER_VERTICAL;
                imageView.setLayoutParams(layoutParams);
                imageView.setPadding(12, 0, 12, 0);
                //  imageView.setForegroundGravity(Gravity.CENTER_VERTICAL);
                mImageViews[i] = imageView;
                if (i == 0) {
                    mImageViews[i].setBackgroundResource(R.drawable.ic_dot_blue);
                } else {
                    mImageViews[i].setBackgroundResource(R.drawable.ic_dot_gray);
                }
                ll_dot.addView(mImageViews[i]);

                int finalI = i;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(finalI);
                    }
                });
            }

            shadowTransformer = new ShadowTransformer(viewPager, mCardAdapter);

            viewPager.setAdapter(mCardAdapter);
            viewPager.setPageTransformer(false, shadowTransformer);
            viewPager.setOffscreenPageLimit(workBeanList.size());
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                /**滑动完成后调用*/
                @Override
                public void onPageSelected(int position) {
                    for (int i = 0; i < mImageViews.length; i++) {
                        mImageViews[i].setBackgroundResource(R.drawable.ic_dot_blue);
                        if (position != i) {
                            mImageViews[i].setBackgroundResource(R.drawable.ic_dot_gray);
                        }
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

    }

    @Override
    public void getProDetail(ProjectData.DataBean projectData) {
        Log.i(TAG, "getProDetail: " + projectData);
        projectBean = projectData;
        initProjectDetailInfo();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Globle.PROJECT_EDIT) {
//                pro_workListPresenter.getProDetail(projectBean.getId());

                if (flag == 4) {
                    pro_workListPresenter.getProDetail(alldata.getId());
                    pro_workListPresenter.getPro_WorkList(alldata.getId());
                } else if (flag == 5) {
                    pro_workListPresenter.getProDetail(marker_id);
                    pro_workListPresenter.getPro_WorkList(marker_id);
                }   else if (flag == 10) {
                    pro_workListPresenter.getProDetail(messageNotifyData.getId());
                    pro_workListPresenter.getPro_WorkList(messageNotifyData.getId());
                }else {
                    pro_workListPresenter.getProDetail(projectBean.getId());
                    pro_workListPresenter.getPro_WorkList(projectBean.getId());
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (flag == 4) {
            pro_workListPresenter.getProDetail(alldata.getId());
            pro_workListPresenter.getPro_WorkList(alldata.getId());
        } else if (flag == 5) {
            pro_workListPresenter.getProDetail(marker_id);
            pro_workListPresenter.getPro_WorkList(marker_id);
        }   else if (flag == 10) {
            pro_workListPresenter.getProDetail(messageNotifyData.getId());
            pro_workListPresenter.getPro_WorkList(messageNotifyData.getId());
        }else {
            pro_workListPresenter.getPro_WorkList(projectBean.getId());
        }

    }
}
