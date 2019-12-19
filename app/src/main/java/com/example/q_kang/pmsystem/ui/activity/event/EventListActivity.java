package com.example.q_kang.pmsystem.ui.activity.event;

import android.content.Context;
import android.content.Intent;
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
import com.example.q_kang.pmsystem.modules.bean.bean.event.EventData;
import com.example.q_kang.pmsystem.modules.bean.bean.filter.FilterData;
import com.example.q_kang.pmsystem.modules.bean.bean.filter.FilterEntity;
import com.example.q_kang.pmsystem.modules.bean.bean.filter.ModelUtil;
import com.example.q_kang.pmsystem.present.im.ShowEventListPresenter;
import com.example.q_kang.pmsystem.ui.adpter.LabelHistoryAdapter;
import com.example.q_kang.pmsystem.ui.adpter.event.EventListAdapter;
import com.example.q_kang.pmsystem.ui.view.Utils.CommonUtils;
import com.example.q_kang.pmsystem.ui.view.Utils.view_.FilterView;
import com.example.q_kang.pmsystem.ui.view.Utils.view_.SearchLabelFlowLayout;
import com.example.q_kang.pmsystem.view.EventListView;
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

public class EventListActivity extends BaseActivity implements EventListView {

    @BindView(R.id.iv_event_list_back)
    ImageView iv_back;
    @BindView(R.id.rv_event_list)
    RecyclerView rv_event_list;

    @BindView(R.id.fv_event_list)
    FilterView filterView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.iv_event_list_filter)
    ImageView iv_label_filter;
    @BindView(R.id.iv_event_list_search)
    ImageView iv_search;
    @BindView(R.id.rl_search)
    RelativeLayout rl_search;
    @BindView(R.id.rl_list)
    RelativeLayout rl_list;

    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.et_event_search)
    EditText et_search;
    @BindView(R.id.fab_event_list_add)
    FloatingActionButton fab_add;
    @BindView(R.id.sl_event)
    StateLayout sl_event;

    @BindView(R.id.line)
    LinearLayout line;
    @BindView(R.id.ll_event_list)
    LinearLayout llEventList;

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

    private StringBuffer searchLabels;
    private List<String> searchLabelList = new ArrayList<>();


    private FilterData filterData;
    private LinearLayoutManager linearLayoutManager;

    private EventListAdapter eventListAdapter;

    private ShowEventListPresenter eventPresenter;
    private int flag;
    private int page = 1;
    private boolean isLoading;
    private Handler handler = new Handler();
    private int count;
    private String userId;

    private List<EventData.DataBean> eventDataList = new ArrayList<>();
    private String keyword = "";
    private String searchUserId = "";
    private String orgId = "";
    private String role;
    private List<FilterEntity> filterOrgList = new ArrayList<>();
    private List<FilterEntity> filterList = new ArrayList<>();


    @Override
    public int bindLayout() {
        return R.layout.activity_event_list;
    }

    @Override
    public void doBusiness(Context mContext) {

        EventBus.getDefault().register(this);

        //设置手势不能抽出抽屉
        dl_label_drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        searchLabels = new StringBuffer();

        initState();

        linearLayoutManager = new LinearLayoutManager(EventListActivity.this);
        rv_event_list.setLayoutManager(linearLayoutManager);
        userId = AdminDao.getUserID();
        Log.i(TAG, "doBusiness: " + userId);


        role = AdminDao.getUser().getRole();
        if (Globle.ROLE_FZ.equals(role) || Globle.ROLE_ZJL.equals(role) || role.equals(Globle.ROLE_DUCHA)) {
            flag = 1;
        } else if (Globle.ROLE_BMJL.equals(role)) {
            flag = 0;
        } else {
            flag = 2;
        }
        eventPresenter = new ShowEventListPresenter(this);

        eventPresenter.getEventDepartment(userId);
        eventPresenter.getEventSearchUser(userId, role);

//        eventPresenter.getEventList(page, Globle.PAGE_LIMIT, flag, userId, "", "");

        eventListAdapter = new EventListAdapter(EventListActivity.this, eventDataList);
        rv_event_list.setAdapter(eventListAdapter);


        initFacButton();

        initRefreshAndLoad();

        filterEventData();

    }


    private void initState() {
        if (!CommonUtils.isNetConnected(EventListActivity.this)) {
            sl_event.showNoNetworkView(R.string.no_net, R.drawable.ic_no_net);
            sl_event.setRefreshListener(new StateLayout.OnViewRefreshListener() {
                @Override
                public void refreshClick() {
                    if (!CommonUtils.isNetConnected(EventListActivity.this)) {
                        ToastUtils.showToast(EventListActivity.this, "请确保网络连接正常~");
                    } else {
                        eventDataList.clear();
                        smartRefreshLayout.setEnableLoadMore(true);
                        page = 1;
                        eventPresenter.getEventList(page, Globle.PAGE_LIMIT, flag, userId, keyword, searchUserId, orgId, searchLabels.toString());
                    }
                }

                @Override
                public void loginClick() {

                }
            });
        } else {
            sl_event.showContentView();
        }
    }

    private void initFacButton() {
        fab_add.hide(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fab_add.show(true);
                fab_add.setShowAnimation(AnimationUtils.loadAnimation(EventListActivity.this, R.anim.show_from_bottom));
                fab_add.setHideAnimation(AnimationUtils.loadAnimation(EventListActivity.this, R.anim.hide_to_bottom));
            }
        }, 300);

        rv_event_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
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


    private void initRefreshAndLoad() {
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initState();
//                refreshLayout.setEnableLoadMore(true);
//                eventDataList.clear();
//                page = 1;
//                eventPresenter.getEventList(page, Globle.PAGE_LIMIT, flag, userId, "", searchUserId);
                refreshLayout.finishRefresh();
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (eventDataList.size() < count) {
                    eventPresenter.getEventList(++page, Globle.PAGE_LIMIT, flag, userId, keyword, searchUserId, orgId, searchLabels.toString());
                } else if (eventDataList.size() == count) {
                    refreshLayout.setEnableLoadMore(false);
                }
                refreshLayout.finishLoadMore();
            }
        });
    }

    private void filterEventData() {
        // 筛选数据
        filterData = new FilterData();
        if (Globle.ROLE_FZ.equals(role) || Globle.ROLE_ZJL.equals(role) || role.equals(Globle.ROLE_DUCHA)) {
            filterData.setSorts(ModelUtil.getZJLEventData());
        } else if (Globle.ROLE_BMJL.equals(role)) {
            filterData.setSorts(ModelUtil.getBMJLEventData());
        } else {
            filterData.setSorts(ModelUtil.getYUANGONGEventData());
        }

        filterData.getSorts().get(0).setSelected(true);

        if (Globle.ROLE_FZ.equals(role) || Globle.ROLE_ZJL.equals(role) || role.equals(Globle.ROLE_DUCHA)) {
            filterOrgList.add(new FilterEntity("全部", ""));
            filterData.setOrgFilter(filterOrgList);
            filterData.getOrgFilter().get(0).setSelected(true);
        }

        if (!Globle.ROLE_YUANGONG.equals(role)) {
            filterList.add(new FilterEntity("全部", ""));
            filterData.setFilters(filterList);
            filterData.getFilters().get(0).setSelected(true);
        }

        filterView.setFilterData(EventListActivity.this, filterData);
        // (真正的)筛选视图点击
        filterView.setOnFilterClickListener(new FilterView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                filterView.show(position);
            }
        });


        // 排序Item点击
        filterView.setOnItemSortClickListener(new FilterView.OnItemSortClickListener() {
            @Override
            public void onItemSortClick(FilterEntity entity) {
                eventDataList.clear();
                String value = entity.getValue();

                if ("0".equals(value)) {
                    flag = 0;
                } else if ("1".equals(value)) {
                    flag = 1;
                } else if ("2".equals(value)) {
                    flag = 2;
                } else if ("3".equals(value)) {
                    flag = 3;
                } else if ("4".equals(value)) {
                    flag = 4;
                }
                eventPresenter.getEventList(page, Globle.PAGE_LIMIT, flag, userId, keyword, searchUserId, orgId, searchLabels.toString());
            }
        });

        filterView.setOnItemOrgFilterClickListener(new FilterView.OnItemOrgFilterClickListener() {
            @Override
            public void onItemOrgFilterClick(FilterEntity entity) {
                eventDataList.clear();
                String key = entity.getKey();
                Log.i(TAG, "setOnItemOrgFilterClickListener: " + filterOrgList);
                for (int i = 0; i < filterOrgList.size(); i++) {
                    String key1 = filterOrgList.get(i).getKey();
                    if (key.equals(key1)) {
                        orgId = filterOrgList.get(i).getValue();
                    }
                }
                eventPresenter.getEventList(page, Globle.PAGE_LIMIT, flag, userId, keyword, searchUserId, orgId, searchLabels.toString());
            }
        });

        // 筛选Item点击
        filterView.setOnItemFilterClickListener(new FilterView.OnItemFilterClickListener() {
            @Override
            public void onItemFilterClick(FilterEntity entity) {
                eventDataList.clear();
                String key = entity.getKey();
                for (int i = 0; i < filterList.size(); i++) {
                    String key1 = filterList.get(i).getKey();
                    if (key.equals(key1)) {
                        searchUserId = filterList.get(i).getValue();
                    }
                }

                eventPresenter.getEventList(page, Globle.PAGE_LIMIT, flag, userId, keyword, searchUserId, orgId, searchLabels.toString());
            }
        });
    }


    /**
     * EventBus监听
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(EventBusMessage messageEvent) {
        switch (messageEvent.getMessage()) {
            case "delete_event":
                eventDataList.clear();
                eventPresenter.getEventList(page, Globle.PAGE_LIMIT, flag, userId, keyword, searchUserId, orgId, searchLabels.toString());
                break;

        }
    }

    @OnClick({R.id.iv_event_list_back, R.id.fab_event_list_add, R.id.iv_event_list_search, R.id.tv_search, R.id.iv_event_list_filter, R.id.btn_label_sure})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_event_list_back:
                if (line.getVisibility() == View.VISIBLE) {
                    iv_search.setVisibility(View.VISIBLE);
                    line.setVisibility(View.INVISIBLE);
                    iv_label_filter.setVisibility(View.VISIBLE);
                    keyword = "";
                    eventPresenter.getEventList(page, Globle.PAGE_LIMIT, flag, userId, keyword, searchUserId, orgId, searchLabels.toString());
                } else {
                    finish();
                }
                break;
            case R.id.iv_event_list_search:
                et_search.setText("");
                et_search.setTextColor(Color.parseColor("#616161"));
                YoYo.with(Techniques.SlideInRight)
                        .duration(200)
                        .playOn(rl_search);
                iv_search.setVisibility(View.GONE);
                line.setVisibility(View.VISIBLE);
                iv_label_filter.setVisibility(View.GONE);
                break;

            case R.id.tv_search:
                eventDataList.clear();
                keyword = et_search.getText().toString();
                eventPresenter.getEventList(page, Globle.PAGE_LIMIT, flag, userId, keyword, searchUserId, orgId, searchLabels.toString());
                break;
            case R.id.fab_event_list_add:
                Intent intent = new Intent(this, CreateEventActivity.class);
                intent.putExtra("flag", 1);
                startActivity(intent);
                break;
            case R.id.iv_event_list_filter:
                dl_label_drawer.openDrawer(ll_label_drawer);
                break;
            case R.id.btn_label_sure:

                dl_label_drawer.closeDrawers();
                eventPresenter.getEventList(page, Globle.PAGE_LIMIT, flag, userId, keyword, searchUserId, orgId, searchLabels.toString());
                break;
        }
    }


    @Override
    public void showEventList(EventData eventData) {
        Log.i(TAG, "showEventList: " + eventData);
        eventDataList.clear();
        count = eventData.getCount();
        eventListAdapter.removeAllFooterView();
        if ("".equals(eventData) || count == 0) {
            sl_event.showEmptyView("", R.drawable.ic_no_data);
        } else {
            sl_event.showContentView();
            List<EventData.DataBean> data = eventData.getData();
            eventDataList.addAll(data);
            if (eventDataList.size() == count && page > 1) {
                smartRefreshLayout.setEnableLoadMore(false);
                View footerView = getFooterView();
                eventListAdapter.addFooterView(footerView);
            }
            eventListAdapter.notifyDataSetChanged();
            showLabelsList(eventData);
        }
    }

    private void showLabelsList(EventData eventData) {
        tv_labels.setText("事件标签：");
        lablesList.clear();
        String labels = eventData.get_lable();
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
            tv_lable = (TextView) LayoutInflater.from(EventListActivity.this).inflate(R.layout.label_search_item, flowLayout, false);
            tv_lable.setText(lablesList.get(i));
            tv_lable.setBackgroundResource(R.drawable.shape_login_bg);
            tv_lable.setPadding(12, 12, 12, 12);
            flowLayout.addView(tv_lable);//添加到父View

            String str = tv_lable.getText().toString();
            tv_lable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!searchLabelList.contains(str)) {
                        searchLabelList.add(str);
                    }


                    if (searchLabelList.size() > 0) {
                        ll_label_history.setVisibility(View.VISIBLE);
                    } else {
                        ll_label_history.setVisibility(View.GONE);
                    }


                    rv_label_history.setLayoutManager(new LinearLayoutManager(EventListActivity.this));
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
//                            eventPresenter.getEventList(page, Globle.PAGE_LIMIT, flag, userId, keyword, searchUserId, orgId,searchLabels.toString());
                        }
                    });
                    searchLabels = new StringBuffer();
                    for (int j = 0; j < searchLabelList.size(); j++) {
                        searchLabels.append(searchLabelList.get(j)).append(" ");
                    }
//
//                    dl_label_drawer.closeDrawers();
//                    eventPresenter.getEventList(page, Globle.PAGE_LIMIT, flag, userId, keyword, searchUserId, orgId,searchLabels.toString());
                }
            });


        }
    }


    @Override
    public void showEventSearchUser(List<Frame> frameList) {
        Log.i(TAG, "showEventSearchUser: " + frameList);
        List<FilterEntity> list = new ArrayList<>();
        for (int i = 0; i < frameList.size(); i++) {
            String realName = frameList.get(i).getRealName();
            String useriD = frameList.get(i).getId();
            list.add(new FilterEntity(realName, useriD));
        }
        filterList.addAll(list);
    }

    @Override
    public void showEventDepartment(String string) {
        Log.i(TAG, "showEventDepartment: " + string);
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


    private View getFooterView() {
        View view = getLayoutInflater().inflate(R.layout.footer_layout, (ViewGroup) rv_event_list.getParent(), false);
        return view;
    }

    @Override
    protected void onResume() {
        super.onResume();
        eventDataList.clear();
        smartRefreshLayout.setEnableLoadMore(true);
        page = 1;
        eventPresenter.getEventList(page, Globle.PAGE_LIMIT, flag, userId, keyword, searchUserId, orgId, searchLabels.toString());
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

//                smartRefreshLayout.setEnableRefresh(false);
                if (!CommonUtils.isNetConnected(EventListActivity.this)) {
                    sl_event.showNoNetworkView(R.string.no_net, R.drawable.ic_no_net);
                    sl_event.setRefreshListener(new StateLayout.OnViewRefreshListener() {
                        @Override
                        public void refreshClick() {
                            if (!CommonUtils.isNetConnected(EventListActivity.this)) {
                                ToastUtils.showToast(EventListActivity.this, "请确保网络连接正常~");
                            } else {

                                sl_event.showTimeoutView("", R.drawable.ic_time_out);
                                sl_event.setRefreshListener(new StateLayout.OnViewRefreshListener() {
                                    @Override
                                    public void refreshClick() {
                                        if (state == Globle.LOADING_FAIL) {
                                            smartRefreshLayout.setEnableRefresh(false);
                                            sl_event.showTimeoutView("", R.drawable.ic_time_out);
                                            ToastUtils.showToast(EventListActivity.this, "请求超时，请稍后再试~");
                                        } else if (state == Globle.LOADING_SUCEESS) {
                                            sl_event.showContentView();
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
