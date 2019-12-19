package com.example.q_kang.pmsystem.ui.activity;

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

public class PersonelInfoActivity extends BaseActivity implements DataByIdView {
    @BindView(R.id.parallax)
    ImageView parallax;
    @BindView(R.id.line4)
    LinearLayout line4;
    @BindView(R.id.civ_personel_head)
    CircleImageView civ_head;
    @BindView(R.id.tv_personel_name)
    TextView tv_name;
    @BindView(R.id.tv_personel_department)
    TextView tv_department;
    @BindView(R.id.tv_personel_role)
    TextView tv_role;
    @BindView(R.id.tv_personel_project)
    TextView tv_project;
    @BindView(R.id.tv_personel_work)
    TextView tv_work;
    @BindView(R.id.tv_personel_event)
    TextView tv_event;
    @BindView(R.id.tv_personel_all)
    TextView tv_all;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_personel_search)
    EditText et_search;
    @BindView(R.id.rv_personel_all)
    RecyclerView rv_all;
    @BindView(R.id.ll_personel_data)
    LinearLayout ll_data;
    private int mOffset = 0;
    private int mScrollY = 0;

    private DataByIdPresenter presenter;

    private AllDataAdapter allDataAdapter;
    private List<AllShowData> allShowDataList = new ArrayList<>();


    @Override
    public int bindLayout() {
        return R.layout.activity_personel_info;
    }

    @Override
    public void doBusiness(Context mContext) {

        GlideHelper.loadNet(civ_head, Globle.PHOTO_URL + AdminDao.getUser().getImage(), R.mipmap.icon_cy);
        tv_name.setText(AdminDao.getUser().getRealName());
        tv_department.setText(AdminDao.getUser().getDepartName());
        String role = AdminDao.getUser().getRole();
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

        presenter = new DataByIdPresenter(this);
        presenter.getDataById(AdminDao.getUserID());

        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        rv_all.setLayoutManager(linearLayout);
        allDataAdapter = new AllDataAdapter(mContext, allShowDataList);
        rv_all.setAdapter(allDataAdapter);


        initWidget();
    }

    private void initWidget() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //状态栏透明和间距处理
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, toolbar);

        final View parallax = findViewById(R.id.parallax);
        final View buttonBar = findViewById(R.id.buttonBarLayout);
        final NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.scrollView);
        final SmartRefreshLayout refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);

        String telephone = AdminDao.getUser().getTelephone();
        boolean chinaPhoneLegal = PhoneFormatCheckUtils.isPhoneLegal(telephone);
        findViewById(R.id.tv_personel_tel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chinaPhoneLegal) {
                    CommonUtils.call(PersonelInfoActivity.this, telephone);
                } else {
                    ToastUtils.showToast(PersonelInfoActivity.this, "电话号码无效，请保证格式正确~");
                }
            }
        });
        findViewById(R.id.tv_personel_email).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chinaPhoneLegal) {
                    CommonUtils.sendMsg(PersonelInfoActivity.this, telephone);
                } else {
                    ToastUtils.showToast(PersonelInfoActivity.this, "电话号码无效，请保证格式正确~");
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
                    if (line4.getVisibility() == View.INVISIBLE) {
                        YoYo.with(Techniques.SlideInRight)
                                .duration(200)
                                .playOn(line4);
                        line4.setVisibility(View.VISIBLE);
                    }
                } else {
                    line4.setVisibility(View.INVISIBLE);
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
                allDataAdapter = new AllDataAdapter(PersonelInfoActivity.this, searchData);
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

    }

}
