package com.example.q_kang.pmsystem.ui.activity.frame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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
import com.example.q_kang.pmsystem.modules.bean.bean.AllData;
import com.example.q_kang.pmsystem.modules.bean.bean.AllShowData;
import com.example.q_kang.pmsystem.modules.bean.bean.Frame;
import com.example.q_kang.pmsystem.present.im.DataByIdPresenter;
import com.example.q_kang.pmsystem.ui.adpter.AllDataAdapter;
import com.example.q_kang.pmsystem.ui.view.Utils.CommonUtils;
import com.example.q_kang.pmsystem.ui.view.Utils.Utils.GlideHelper;
import com.example.q_kang.pmsystem.ui.view.Utils.Utils.PhoneFormatCheckUtils;
import com.example.q_kang.pmsystem.ui.view.Utils.Utils.StatusBarUtil;
import com.example.q_kang.pmsystem.view.DataByIdView;
import com.flyco.roundview.RoundTextView;
import com.jimmy.common.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class FrameMemberActivity extends BaseActivity implements DataByIdView {

    private String TAG = "FrameMemberActivity";

    @BindView(R.id.iv_frame_background)
    ImageView iv_background;
    @BindView(R.id.tv_frame_tel)
    RoundTextView tv_tel;
    @BindView(R.id.tv_frame_email)
    RoundTextView tv_email;
    @BindView(R.id.tv_frame_name)
    TextView tv_name;
    @BindView(R.id.tv_frame_department)
    TextView tv_department;
    @BindView(R.id.tv_frame_role)
    TextView tv_role;
    @BindView(R.id.tv_frame_project)
    TextView tv_project;
    @BindView(R.id.tv_frame_work)
    TextView tv_work;
    @BindView(R.id.tv_frame_event)
    TextView tv_event;
    @BindView(R.id.tv_frame_all)
    TextView tv_all;
    @BindView(R.id.civ_frame_image)
    CircleImageView civ_head;
    @BindView(R.id.srl_frame)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_frame_search)
    LinearLayout ll_search;
    @BindView(R.id.et_frame_search)
    EditText et_search;
    @BindView(R.id.ll_data)
    LinearLayout ll_data;
    @BindView(R.id.rv_frame_all)
    RecyclerView rv_all;
    @BindView(R.id.rl_frame_alldata)
    RelativeLayout rl_alldata;
    @BindView(R.id.rl_frame_info)
    RelativeLayout rl_info;
    @BindView(R.id.tv_info_tel)
    TextView tv_info_tel;
    @BindView(R.id.tv_info_name)
    TextView tv_info_name;
    @BindView(R.id.tv_info_department)
    TextView tv_info_department;
    @BindView(R.id.tv_info_role)
    TextView tv_info_role;
    @BindView(R.id.civ_info_image)
    CircleImageView civ_info;
    @BindView(R.id.toolbar_info)
    Toolbar toolbar_info;

    private int mOffset = 0;
    private int mScrollY = 0;


    private Frame frame;

    private DataByIdPresenter presenter;
    private String userID, userRole;

    private AllDataAdapter allDataAdapter;
    private List<AllShowData> allShowDataList = new ArrayList<>();

    @Override
    public int bindLayout() {
        return R.layout.activity_frame_member;

    }

    @Override
    public void doBusiness(Context mContext) {
        frame = getIntent().getParcelableExtra("frame");

        initBasicInfo();

        userID = AdminDao.getUserID();
        userRole = AdminDao.getUser().getRole();
        presenter = new DataByIdPresenter(this);

        /**
         * 根据不同用户角色判断权限 显示不同界面
         * */
        if (userRole.equals(Globle.ROLE_BMJL)) {    //部门经理
            presenter.getSearchUserById(userID, userRole);
        } else if (userRole.equals(Globle.ROLE_YUANGONG)) {
            if (frame.getId().equals(AdminDao.getUserID())) {  //员工
                rl_alldata.setVisibility(View.VISIBLE);
                rl_info.setVisibility(View.GONE);
            } else {
                rl_alldata.setVisibility(View.GONE);
                rl_info.setVisibility(View.VISIBLE);

                showOnlyInfo();
            }
        } else if (userRole.equals(Globle.ROLE_FZ) || userRole.equals(Globle.ROLE_ZJL) || userRole.equals(Globle.ROLE_DUCHA)) {  //总经理、副总
            rl_alldata.setVisibility(View.VISIBLE);
            rl_info.setVisibility(View.GONE);
        }
        presenter.getDataById(frame.getId());


        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        rv_all.setLayoutManager(linearLayout);
        allDataAdapter = new AllDataAdapter(mContext, allShowDataList);
        rv_all.setAdapter(allDataAdapter);


    }

    private void initBasicInfo() {
        GlideHelper.loadNet(civ_head, Globle.PHOTO_URL + frame.getImage(), R.mipmap.logo);
        //  GlideHelper.loadNet(iv_background, Globle.PHOTO_URL + frame.getImage(), R.mipmap.image_weibo_home_2);

        tv_name.setText(frame.getRealName());
        tv_department.setText(frame.getDepartName());
        String role = frame.getRole();
        if (role.equals(Globle.ROLE_ZJL)) {
            role = Globle.ROLE_JZ;
        } else if (role.equals(Globle.ROLE_FZ)) {
            role = Globle.ROLE_FJZ;
        } else if (role.equals(Globle.ROLE_BMJL)) {
            role = Globle.ROLE_KZ;
        } else if (role.equals(Globle.ROLE_YUANGONG)) {
            role = Globle.ROLE_KY;
        }
        tv_role.setText(role);


        initWidget();
    }

    private void initWidget() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        toolbar_info.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //状态栏透明和间距处理
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, toolbar);

        final View parallax = findViewById(R.id.iv_frame_background);
        final View buttonBar = findViewById(R.id.buttonBarLayout);
        final NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.scrollView);
        final RefreshLayout refreshLayout = (RefreshLayout) findViewById(R.id.srl_frame);

        String telephone = AdminDao.getUser().getTelephone();
        boolean chinaPhoneLegal = PhoneFormatCheckUtils.isPhoneLegal(telephone);
        findViewById(R.id.tv_frame_tel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chinaPhoneLegal) {
                    CommonUtils.call(FrameMemberActivity.this, telephone);
                } else {
                    ToastUtils.showToast(FrameMemberActivity.this, "电话号码无效，请保证格式正确~");
                }
            }
        });
        findViewById(R.id.tv_frame_email).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chinaPhoneLegal) {
                    CommonUtils.sendMsg(FrameMemberActivity.this, telephone);
                } else {
                    ToastUtils.showToast(FrameMemberActivity.this, "电话号码无效，请保证格式正确~");
                }
            }
        });

        refreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(3000);
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(2000);
            }

            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                mOffset = offset / 2;
                parallax.setTranslationY(mOffset - mScrollY);
                toolbar.setAlpha(1 - Math.min(percent, 1));
            }

        });

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            private int lastScrollY = 0;
            private int h = DensityUtil.dp2px(170);
            private int m = DensityUtil.dp2px(100);
            private int color = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary) & 0x00ffffff;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (lastScrollY < h) {
                    scrollY = Math.min(h, scrollY);
                    mScrollY = scrollY > h ? h : scrollY;
                    buttonBar.setAlpha(1f * mScrollY / h);
                    toolbar.setBackgroundColor(((255 * mScrollY / h) << 24) | color);
                    parallax.setTranslationY(mOffset - mScrollY);
                }
                if (lastScrollY > m) {
                    if (ll_search.getVisibility() == View.INVISIBLE) {
                        YoYo.with(Techniques.SlideInRight)
                                .duration(200)
                                .playOn(ll_search);
                        ll_search.setVisibility(View.VISIBLE);
                    }
                } else {
                    ll_search.setVisibility(View.INVISIBLE);
                }
                lastScrollY = scrollY;
            }
        });
        buttonBar.setAlpha(0);
        toolbar.setBackgroundColor(0);

    }


    @SuppressLint("SimpleDateFormat")
    @Override
    public void getDataById(AllData allData) {
        Log.i(TAG, "getDataById: " + allData);
        tv_project.setText("项目 " + allData.getProCount());
        tv_work.setText("工作 " + allData.getWorkCount());
        tv_event.setText("事件 " + allData.getEventCount());

        showAllData(allData);

        tv_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTvColor(tv_project);
                allShowDataList.clear();
                showProData(allData);
                allDataAdapter.notifyDataSetChanged();
            }
        });

        tv_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTvColor(tv_work);
                allShowDataList.clear();
                showWorkData(allData);
                allDataAdapter.notifyDataSetChanged();
            }
        });

        tv_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTvColor(tv_event);
                allShowDataList.clear();
                showEventData(allData);
                allDataAdapter.notifyDataSetChanged();
            }
        });

        tv_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTvColor(tv_all);
                allShowDataList.clear();
                showAllData(allData);
                allDataAdapter.notifyDataSetChanged();
            }
        });


        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, "afterTextChanged: " + allShowDataList.size() + allShowDataList);

                List<AllShowData> searchData = new ArrayList<>();
                String inputStr = et_search.getText().toString();
                if ("".equals(inputStr) || null == inputStr) {
                    searchData = allShowDataList;
                } else {
                    for (int i = 0; i < allShowDataList.size(); i++) {
                        if (allShowDataList.get(i).getName().contains(inputStr)) {
                            searchData.add(allShowDataList.get(i));
                        }
                    }
                    allDataAdapter.notifyDataSetChanged();
                }
                if (allDataAdapter != null) {
                    allDataAdapter = null;
                }
                allDataAdapter = new AllDataAdapter(FrameMemberActivity.this, searchData);
                rv_all.setAdapter(allDataAdapter);

            }
        });

    }


    private void changeTvColor(TextView textView) {
        tv_all.setTextColor(Color.BLACK);
        tv_project.setTextColor(Color.BLACK);
        tv_event.setTextColor(Color.BLACK);
        tv_work.setTextColor(Color.BLACK);
        textView.setTextColor(Color.parseColor("#16BD92"));
    }

    private void showAllData(AllData allData) {
        String proCount = allData.getProCount().trim();
        String workCount = allData.getWorkCount().trim();
        String eventCount = allData.getEventCount().trim();
        Log.i(TAG, "showAllData: " + proCount);
        if ("0".equals(proCount) && "0".equals(workCount) && "0".equals(eventCount)) {
            ll_data.setVisibility(View.GONE);
        } else {
            ll_data.setVisibility(View.VISIBLE);

            showProData(allData);

            showWorkData(allData);

            showEventData(allData);

            allDataAdapter.notifyDataSetChanged();
        }


    }

    private void showEventData(AllData allData) {
        AllShowData allShowData;
        String eventCount = allData.getEventCount().trim();
        if ("0".equals(eventCount)) {
            ll_data.setVisibility(View.GONE);
        } else {
            ll_data.setVisibility(View.VISIBLE);
            List<AllData.EventListBean> eventList = allData.getEventList();
            for (int i = 0; i < eventList.size(); i++) {
                allShowData = new AllShowData();
                String id = eventList.get(i).getId();
                String title = eventList.get(i).getTitle();
                if (null == title || "".equals(title) || "null".equals(title)) {
                    title = "暂无事件名称";
                }
                String startTime = eventList.get(i).getStartTime().split("T")[0];
                allShowData.setId(id);
                allShowData.setTime(startTime);
                allShowData.setName(title);
                allShowData.setFlag(3);
                allShowDataList.add(allShowData);
            }
        }

    }

    private void showWorkData(AllData allData) {
        AllShowData allShowData;
        String workCount = allData.getWorkCount().trim();
        if ("0".equals(workCount)) {
            ll_data.setVisibility(View.GONE);
        } else {
            ll_data.setVisibility(View.VISIBLE);
            List<AllData.WorkListBean> workList = allData.getWorkList();
            for (int i = 0; i < workList.size(); i++) {
                allShowData = new AllShowData();
                String id = workList.get(i).getId();
                String name = workList.get(i).getName();
                String startTime = workList.get(i).getStartTime().split("T")[0];
                allShowData.setId(id);
                allShowData.setTime(startTime);
                allShowData.setName(name);
                allShowData.setFlag(2);
                allShowDataList.add(allShowData);
            }
        }

    }

    private void showProData(AllData allData) {
        AllShowData allShowData;
        String proCount = allData.getProCount().trim();
        if ("0".equals(proCount)) {
            ll_data.setVisibility(View.GONE);
        } else {
            ll_data.setVisibility(View.VISIBLE);
            List<AllData.ProListBean> proList = allData.getProList();
            for (int i = 0; i < proList.size(); i++) {
                allShowData = new AllShowData();
                String id = proList.get(i).getId();
                String name = proList.get(i).getName();
                String startTime = proList.get(i).getStartTime().split(" ")[0];

                try {
                    Date parse = new SimpleDateFormat("yyyy/MM/dd").parse(startTime);
                    startTime = new SimpleDateFormat("yyyy-MM-dd").format(parse);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                allShowData.setId(id);
                allShowData.setTime(startTime);
                allShowData.setName(name);
                allShowData.setFlag(1);
                allShowDataList.add(allShowData);
            }
        }

    }

    @Override
    public void showSearchUser(List<Frame> frames) {
        Log.i(TAG, "showSearchUser: " + frames);

        if (frames.toString().contains(frame.getId())) {
            rl_alldata.setVisibility(View.VISIBLE);
            rl_info.setVisibility(View.GONE);
        } else {
            rl_alldata.setVisibility(View.GONE);
            rl_info.setVisibility(View.VISIBLE);

            showOnlyInfo();
        }


    }

    private void showOnlyInfo() {
        GlideHelper.loadNet(civ_info, Globle.PHOTO_URL + frame.getImage(), R.mipmap.icon_cy);

        tv_info_name.setText(frame.getRealName());
        tv_info_department.setText(frame.getDepartName());
        String role = frame.getRole();
        if (role.equals(Globle.ROLE_ZJL)) {
            role = Globle.ROLE_JZ;
        } else if (role.equals(Globle.ROLE_FZ)) {
            role = Globle.ROLE_FJZ;
        } else if (role.equals(Globle.ROLE_BMJL)) {
            role = Globle.ROLE_KZ;
        } else if (role.equals(Globle.ROLE_YUANGONG)) {
            role = Globle.ROLE_KY;
        }
        tv_info_role.setText(role);
        tv_info_tel.setText(frame.getTelephone());
    }
}

