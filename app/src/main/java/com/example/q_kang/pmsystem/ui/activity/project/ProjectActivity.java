package com.example.q_kang.pmsystem.ui.activity.project;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.BaseActivity;
import com.example.q_kang.pmsystem.dao.AdminDao;
import com.example.q_kang.pmsystem.modules.bean.bean.DepartmentData;
import com.example.q_kang.pmsystem.modules.bean.bean.EventBusMessage;
import com.example.q_kang.pmsystem.modules.bean.bean.Frame;
import com.example.q_kang.pmsystem.modules.bean.bean.filter.FilterData;
import com.example.q_kang.pmsystem.modules.bean.bean.filter.FilterEntity;
import com.example.q_kang.pmsystem.modules.bean.bean.filter.ModelUtil;
import com.example.q_kang.pmsystem.modules.bean.bean.project.ProjectData;
import com.example.q_kang.pmsystem.present.im.ShowProjectListPresenter;
import com.example.q_kang.pmsystem.ui.adpter.LabelHistoryAdapter;
import com.example.q_kang.pmsystem.ui.adpter.project.ProjectAllListAdapter;
import com.example.q_kang.pmsystem.ui.view.Utils.CommonUtils;
import com.example.q_kang.pmsystem.ui.view.Utils.view_.FilterView;
import com.example.q_kang.pmsystem.ui.view.Utils.view_.SearchLabelFlowLayout;
import com.example.q_kang.pmsystem.view.ProjectListView;
import com.fingdo.statelayout.StateLayout;
import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jimmy.common.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ProjectActivity extends BaseActivity implements ProjectListView {
    @BindView(R.id.iv_project_back)
    ImageView iv_back;

    @BindView(R.id.fab_project_add)
    FloatingActionButton fab_add;
    @BindView(R.id.recy_pro)
    RecyclerView recy_pro;
    @BindView(R.id.srl_project)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.fv_project)
    FilterView filterView;

    @BindView(R.id.iv_project_list_search)
    ImageView iv_search;
    @BindView(R.id.iv_project_list_filter)
    ImageView iv_label_filter;
    @BindView(R.id.rl_search)
    RelativeLayout rl_search;
    @BindView(R.id.rl_list)
    RelativeLayout rl_list;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.et_project_search)
    EditText et_search;
    @BindView(R.id.sl_project)
    StateLayout sl_project;
    @BindView(R.id.line)
    LinearLayout line;
    @BindView(R.id.dl_label_drawer)
    DrawerLayout dl_label_drawer;
    @BindView(R.id.ll_label_drawer)
    NestedScrollView ll_label_drawer;
    @BindView(R.id.slfl_lables)
    SearchLabelFlowLayout flowLayout;
    @BindView(R.id.ll_label_history)
    LinearLayout ll_label_history;
    @BindView(R.id.rv_label_history)
    RecyclerView rv_label_history;
    @BindView(R.id.tv_labels)
    TextView tv_labels;
    @BindView(R.id.btn_label_sure)
    Button btn_label_sure;

    private TextView tv_lable;
    private List<String> lablesList = new ArrayList<>();

    private FilterData filterData;
    private LinearLayoutManager linearLayoutManager;

    private List<ProjectData.DataBean> projectList = new ArrayList<>();
    private ProjectAllListAdapter projectAllListAdapter;
    private List<FilterEntity> filterOrgList = new ArrayList<>();
    private List<FilterEntity> filterList = new ArrayList<>();

    private ShowProjectListPresenter showProjectListPresenter;
    private String currUserId, currUserRole, currUserName;

    private int page = 1;
    private int flag;
    private int count;

    private int FLAG_ALL_PROJECT = 0;     //全部项目，对选择的人名进行项目查询（副总、总经理看全部；部门经理看自己及管辖）
    private int FLAG_DOING_PROJECT = 1;   //进行中项目，对选择的人名进行项目查询（副总、总经理看全部；部门经理看自己及管辖）
    private int FLAG_FINISHED_PROJECT = 2;//已完结项目，对选择的人名进行项目查询（副总、总经理看全部；部门经理看自己及管辖）
    private int FLAG_FUZE = 3;            //负责项目，只有副总、总经理、部门经理有
    private int FLAG_CANYU = 4;           //参与项目，只有副总、总经理、部门经理有

    private String keyword = "";
    private String searchUserId = "";
    private String orgId = "";
    private StringBuffer searchLabels;

    private List<String> searchLabelList = new ArrayList<>();


    @Override
    public int bindLayout() {
        return R.layout.activity_project;
    }

    @Override
    public void doBusiness(Context mContext) {
        EventBus.getDefault().register(this);

        //设置手势不能抽出抽屉
        dl_label_drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        searchLabels = new StringBuffer();
        btn_label_sure.setBackgroundResource(R.drawable.shape_location_project_bg);

        initState();

        currUserId = AdminDao.getUserID();
        currUserRole = AdminDao.getUser().getRole();
        currUserName = AdminDao.getUser().getRealName();
        //只有副总、总经理，有权限添加项目
        if (Globle.ROLE_FZ.equals(currUserRole) || Globle.ROLE_ZJL.equals(currUserRole) || currUserRole.equals(Globle.ROLE_DUCHA)) {
            fab_add.setVisibility(View.VISIBLE);
            initFacButton();
        } else {
            fab_add.setVisibility(View.GONE);
        }

        flag = FLAG_ALL_PROJECT;
        showProjectListPresenter = new ShowProjectListPresenter(this);
        showProjectListPresenter.getProDepartment(currUserId);
        showProjectListPresenter.getProSearchUser(currUserId, currUserRole);
//        showProjectListPresenter.getProjectList(page, Globle.PAGE_LIMIT, flag, currUserId, "", currUserRole, "");

        linearLayoutManager = new LinearLayoutManager(this);
        recy_pro.setLayoutManager(linearLayoutManager);

        projectAllListAdapter = new ProjectAllListAdapter(ProjectActivity.this, projectList);
        recy_pro.setAdapter(projectAllListAdapter);


        initRefreshAndLoad();

        //过滤
        filterProjectData();

    }

    private void initState() {

        if (!CommonUtils.isNetConnected(ProjectActivity.this)) {
            sl_project.showNoNetworkView(R.string.no_net, R.drawable.ic_no_net);
            sl_project.setRefreshListener(new StateLayout.OnViewRefreshListener() {
                @Override
                public void refreshClick() {
                    if (!CommonUtils.isNetConnected(ProjectActivity.this)) {
                        ToastUtils.showToast(ProjectActivity.this, "请确保网络连接正常~");
                    } else {
                        projectList.clear();
                        smartRefreshLayout.setEnableLoadMore(true);
                        page = 1;
                        showProjectListPresenter.getProjectList(page, Globle.PAGE_LIMIT, flag, currUserId, keyword, currUserRole, searchUserId, orgId, searchLabels.toString(), currUserName);
                    }
                }

                @Override
                public void loginClick() {

                }
            });
        } else {
            sl_project.showContentView();
        }
    }

    private void initFacButton() {
        fab_add.hide(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fab_add.show(true);
                fab_add.setShowAnimation(AnimationUtils.loadAnimation(ProjectActivity.this, R.anim.show_from_bottom));
                fab_add.setHideAnimation(AnimationUtils.loadAnimation(ProjectActivity.this, R.anim.hide_to_bottom));
            }
        }, 300);

        recy_pro.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        fab_add.hide(true);
                    } else {
                        fab_add.show(true);
                    }
                }
            }
        });
    }

    private void filterProjectData() {


        // 筛选数据
        filterData = new FilterData();
        if (Globle.ROLE_FZ.equals(currUserRole) || Globle.ROLE_ZJL.equals(currUserRole)
                || Globle.ROLE_BMJL.equals(currUserRole) || currUserRole.equals(Globle.ROLE_DUCHA)) {
            filterData.setSorts(ModelUtil.getProjectAllSortData());
        } else {
            filterData.setSorts(ModelUtil.getProjectSingleSortData());
        }
        filterData.getSorts().get(0).setSelected(true);


        if (Globle.ROLE_FZ.equals(currUserRole) || Globle.ROLE_ZJL.equals(currUserRole) || currUserRole.equals(Globle.ROLE_DUCHA)) {
            filterOrgList.add(new FilterEntity("全部", ""));
            filterData.setOrgFilter(filterOrgList);
            filterData.getOrgFilter().get(0).setSelected(true);
        }

        if (!Globle.ROLE_YUANGONG.equals(currUserRole)) {
            filterList.add(new FilterEntity("全部", ""));
            filterData.setFilters(filterList);
            filterData.getFilters().get(0).setSelected(true);
        }


        filterView.setFilterData(this, filterData);
        filterView.setOnFilterClickListener(new FilterView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                filterView.show(position);
            }
        });

        // (真正的)筛选视图点击
        // 排序Item点击
        filterView.setOnItemSortClickListener(new FilterView.OnItemSortClickListener() {
            @Override
            public void onItemSortClick(FilterEntity entity) {

                projectList.clear();
                String value = entity.getValue();
                if ("0".equals(value)) {
                    flag = FLAG_ALL_PROJECT;
                } else if ("1".equals(value)) {
                    flag = FLAG_DOING_PROJECT;
                } else if ("2".equals(value)) {
                    flag = FLAG_FINISHED_PROJECT;
                } else if ("3".equals(value)) {
                    flag = FLAG_FUZE;
                } else {
                    flag = FLAG_CANYU;
                }

                showProjectListPresenter.getProjectList(page, Globle.PAGE_LIMIT, flag, currUserId, keyword, currUserRole, searchUserId, orgId, searchLabels.toString(), currUserName);

            }
        });


        filterView.setOnItemOrgFilterClickListener(new FilterView.OnItemOrgFilterClickListener() {
            @Override
            public void onItemOrgFilterClick(FilterEntity entity) {
                projectList.clear();
                String key = entity.getKey();
                Log.i(TAG, "setOnItemOrgFilterClickListener: " + filterOrgList);
                for (int i = 0; i < filterOrgList.size(); i++) {
                    String key1 = filterOrgList.get(i).getKey();
                    if (key.equals(key1)) {
                        orgId = filterOrgList.get(i).getValue();
                    }
                }

                showProjectListPresenter.getProjectList(page, Globle.PAGE_LIMIT, flag, currUserId, keyword, currUserRole, searchUserId, orgId, searchLabels.toString(), currUserName);
            }
        });

        // 筛选Item点击
        filterView.setOnItemFilterClickListener(new FilterView.OnItemFilterClickListener() {
            @Override
            public void onItemFilterClick(FilterEntity entity) {
                projectList.clear();
                String key = entity.getKey();
                Log.i(TAG, "onItemFilterClick: " + filterList);
                for (int i = 0; i < filterList.size(); i++) {
                    String key1 = filterList.get(i).getKey();
                    if (key.equals(key1)) {
                        searchUserId = filterList.get(i).getValue();
                    }
                }

                showProjectListPresenter.getProjectList(page, Globle.PAGE_LIMIT, flag, currUserId, keyword, currUserRole, searchUserId, orgId, searchLabels.toString(), currUserName);
            }
        });
    }

    private void initRefreshAndLoad() {

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initState();
//                projectList.clear();
//                smartRefreshLayout.setEnableLoadMore(true);
//                page = 1;
//                showProjectListPresenter.getProjectList(page, Globle.PAGE_LIMIT, flag, currUserId, "", currUserRole, searchUserId);
                refreshLayout.finishRefresh();
            }
        });

        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (projectList.size() < count) {
                    showProjectListPresenter.getProjectList(++page, Globle.PAGE_LIMIT, flag, currUserId, keyword, currUserRole, searchUserId, orgId, searchLabels.toString(), currUserName);
                } else if (projectList.size() == count) {
                    refreshLayout.setEnableLoadMore(false);
                }
                refreshLayout.finishLoadMore();
            }
        });

    }


    /**
     * EventBus监听
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(EventBusMessage messageEvent) {
        switch (messageEvent.getMessage()) {
            case "delete_pro":
                projectList.clear();
                showProjectListPresenter.getProjectList(page, Globle.PAGE_LIMIT, flag, currUserId, keyword, currUserRole, searchUserId, orgId, searchLabels.toString(), currUserName);
                break;

        }
    }


    @OnClick({R.id.iv_project_back, R.id.fab_project_add, R.id.iv_project_list_search, R.id.tv_search, R.id.iv_project_list_filter, R.id.btn_label_sure})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.iv_project_back:
                if (line.getVisibility() == View.VISIBLE) {
                    YoYo.with(Techniques.SlideOutRight)
                            .duration(300)
                            .playOn(rl_search);
                    iv_search.setVisibility(View.VISIBLE);
                    iv_label_filter.setVisibility(View.VISIBLE);
                    line.setVisibility(View.INVISIBLE);
                    projectList.clear();
                    keyword = "";
                    showProjectListPresenter.getProjectList(page, Globle.PAGE_LIMIT, flag, currUserId, keyword, currUserRole, searchUserId, orgId, searchLabels.toString(), currUserName);
                } else {
                    finish();
                }

                break;
            case R.id.fab_project_add:
                startActivity(CreateProjectActivity.class);
                break;
            case R.id.iv_project_list_search:
                et_search.setText("");
                et_search.setTextColor(Color.parseColor("#616161"));
                YoYo.with(Techniques.SlideInRight)
                        .duration(500)
                        .playOn(rl_search);
                line.setVisibility(View.VISIBLE);
                iv_search.setVisibility(View.INVISIBLE);
                iv_label_filter.setVisibility(View.GONE);
                break;
            case R.id.tv_search:
                projectList.clear();
                keyword = et_search.getText().toString();
                showProjectListPresenter.getProjectList(page, Globle.PAGE_LIMIT, flag, currUserId, keyword, currUserRole, searchUserId, orgId, searchLabels.toString(), currUserName);
                break;
            case R.id.iv_project_list_filter:
                dl_label_drawer.openDrawer(ll_label_drawer);
                break;
            case R.id.btn_label_sure:
                dl_label_drawer.closeDrawers();
                showProjectListPresenter.getProjectList(page, Globle.PAGE_LIMIT, flag, currUserId, keyword, currUserRole, searchUserId, orgId, searchLabels.toString(), currUserName);
                break;
        }
    }


    @Override
    public void showProjectList(String s) {
        Log.i(TAG, "showProjectList: " + s);
        projectList.clear();
        if (!"".equals(s)) {
            ProjectData projectData = new Gson().fromJson(s, new TypeToken<ProjectData>() {
            }.getType());
            count = projectData.getCount();
            projectAllListAdapter.removeAllFooterView();
            if (count != 0 && !"null".equals(projectData) && null != projectData) {
                sl_project.showContentView();
                List<ProjectData.DataBean> data = projectData.getData();
                projectList.addAll(data);
                if (projectList.size() == count && page > 1) {
                    smartRefreshLayout.setEnableLoadMore(false);
                    View footerView = getFooterView();
                    projectAllListAdapter.addFooterView(footerView);

                }
                projectAllListAdapter.notifyDataSetChanged();
            } else {
                sl_project.showEmptyView("", R.drawable.ic_no_data);
            }

            showLabelsList(projectData);
        } else {
            sl_project.showEmptyView("", R.drawable.ic_no_data);
        }


    }

    private void showLabelsList(ProjectData projectData) {
        tv_labels.setText("项目标签：");
        lablesList.clear();
        String labels = projectData.get_lable();
        if (!"".equals(labels) || labels != null && !"null".equals(labels)) {
            String[] split = labels.split(" ");
            for (int i = 0; i < split.length; i++) {
                String s = split[i];
                if (!"".equals(s)) {
                    lablesList.add(s);
                }
            }
        }


        flowLayout.removeAllViews();
        for (int i = 0; i < lablesList.size(); i++) {
            tv_lable = (TextView) LayoutInflater.from(ProjectActivity.this).inflate(R.layout.label_search_item, flowLayout, false);
            tv_lable.setText(lablesList.get(i));
            tv_lable.setBackgroundResource(R.drawable.shape_location_project_bg);
            tv_lable.setPadding(20, 12, 20, 12);
            flowLayout.addView(tv_lable);//添加到父View

            String str = tv_lable.getText().toString();
            tv_lable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!searchLabelList.contains(str)){
                        searchLabelList.add(str);
                    }


                    if (searchLabelList.size() > 0) {
                        ll_label_history.setVisibility(View.VISIBLE);
                    } else {
                        ll_label_history.setVisibility(View.GONE);
                    }


                    rv_label_history.setLayoutManager(new LinearLayoutManager(ProjectActivity.this));
                    LabelHistoryAdapter labelHistoryAdapter = new LabelHistoryAdapter(searchLabelList);
                    rv_label_history.setAdapter(labelHistoryAdapter);
                    labelHistoryAdapter.setLabelItemDeleteListener(new LabelHistoryAdapter.LabelItemDeleteListener() {
                        @Override
                        public void deleteLabelItem(String string) {
                            searchLabelList.remove(string);
                            labelHistoryAdapter.notifyDataSetChanged();

                            if (searchLabelList.size() > 0) {
                                ll_label_history.setVisibility(View.VISIBLE);
                            } else {
                                ll_label_history.setVisibility(View.GONE);
                            }


                            searchLabels = new StringBuffer();
                            for (int j = 0; j < searchLabelList.size(); j++) {
                                searchLabels.append(searchLabelList.get(j)).append(" ");
                            }
//                            dl_label_drawer.closeDrawers();
//                            showProjectListPresenter.getProjectList(page, Globle.PAGE_LIMIT, flag, currUserId, keyword, currUserRole, searchUserId, orgId, searchLabels.toString(), currUserName);
//

                        }
                    });
                    searchLabels = new StringBuffer();
                    for (int j = 0; j < searchLabelList.size(); j++) {
                        searchLabels.append(searchLabelList.get(j)).append(" ");
                    }

//                    dl_label_drawer.closeDrawers();
//                    showProjectListPresenter.getProjectList(page, Globle.PAGE_LIMIT, flag, currUserId, keyword, currUserRole, searchUserId, orgId, searchLabels.toString(), currUserName);
                }
            });


        }
    }

    @Override
    public void showSearchUser(List<Frame> frameList) {
        Log.i(TAG, "showSearchUser: " + frameList);
        if (frameList != null || frameList.size() > 0) {
            List<FilterEntity> list = new ArrayList<>();
            for (int i = 0; i < frameList.size(); i++) {
                String realName = frameList.get(i).getRealName();
                String useriD = frameList.get(i).getId();
                list.add(new FilterEntity(realName, useriD));
            }
            filterList.addAll(list);
        }


    }

    @Override
    public void showProDepartment(String string) {
        Log.i(TAG, "showProDepartment: " + string);
        if (!"".equals(string)) {
            DepartmentData departmentData = new Gson().fromJson(string, new TypeToken<DepartmentData>() {
            }.getType());
            List<DepartmentData.DataBean> data = departmentData.getData();
            List<FilterEntity> list = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                String name = data.get(i).getName();
                String id = data.get(i).getId();
                list.add(new FilterEntity(name, id));
            }
            filterOrgList.addAll(list);
        }

    }


    private View getFooterView() {
        View view = getLayoutInflater().inflate(R.layout.footer_layout, (ViewGroup) recy_pro.getParent(), false);
        return view;
    }

    @Override
    protected void onResume() {
        super.onResume();

        projectList.clear();
        smartRefreshLayout.setEnableLoadMore(true);
        page = 1;
        showProjectListPresenter.getProjectList(page, Globle.PAGE_LIMIT, flag, currUserId, keyword, currUserRole, searchUserId, orgId, searchLabels.toString(), currUserName);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void setState(int state) {
        super.setState(state);
        switch (state) {
            case Globle.LOADING_FAIL:

                if (!CommonUtils.isNetConnected(ProjectActivity.this)) {
                    sl_project.showNoNetworkView(R.string.no_net, R.drawable.ic_no_net);
                    sl_project.setRefreshListener(new StateLayout.OnViewRefreshListener() {
                        @Override
                        public void refreshClick() {
                            if (!CommonUtils.isNetConnected(ProjectActivity.this)) {
                                ToastUtils.showToast(ProjectActivity.this, "请确保网络连接正常~");
                            } else {

                                sl_project.showTimeoutView("", R.drawable.ic_time_out);
                                sl_project.setRefreshListener(new StateLayout.OnViewRefreshListener() {
                                    @Override
                                    public void refreshClick() {
                                        if (state == Globle.LOADING_FAIL) {
                                            smartRefreshLayout.setEnableRefresh(false);
                                            sl_project.showTimeoutView("", R.drawable.ic_time_out);
                                            ToastUtils.showToast(ProjectActivity.this, "请求超时，请稍后再试~");
                                        } else if (state == Globle.LOADING_SUCEESS) {
                                            sl_project.showContentView();
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
