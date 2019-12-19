package com.example.q_kang.pmsystem.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.BaseActivity;
import com.example.q_kang.pmsystem.dao.AdminDao;
import com.example.q_kang.pmsystem.modules.bean.bean.AllData;
import com.example.q_kang.pmsystem.modules.bean.bean.AllShowData;
import com.example.q_kang.pmsystem.present.im.GlobalSearchPresenter;
import com.example.q_kang.pmsystem.ui.adpter.SearchListAdapter;
import com.example.q_kang.pmsystem.view.SearchGlobalView;
import com.fantasy.doubledatepicker.DoubleDateSelectDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jimmy.common.util.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity implements SearchGlobalView {

    @BindView(R.id.iv_search_back)
    ImageView iv_search_back;
    @BindView(R.id.et_search_search)
    EditText et_search;
    @BindView(R.id.tv_search_search)
    TextView tv_search;
    @BindView(R.id.tv_search_project)
    TextView tv_project;
    @BindView(R.id.tv_search_work)
    TextView tv_work;
    @BindView(R.id.tv_search_event)
    TextView tv_event;
    @BindView(R.id.tv_search_name)
    TextView tv_name;
    @BindView(R.id.tv_search_label)
    TextView tv_label;
    @BindView(R.id.tv_search_title)
    TextView tv_title;
    @BindView(R.id.tv_search_content)
    TextView tv_content;
    @BindView(R.id.tv_search_time)
    TextView tv_time;
    @BindView(R.id.tv_search_person)
    TextView tv_person;
    @BindView(R.id.ib_search_cancel)
    ImageButton ib_cancel;
    @BindView(R.id.ll_search_search)
    LinearLayout ll_text;
    @BindView(R.id.ll_list)
    LinearLayout ll_list;
    @BindView(R.id.rv_search_list)
    RecyclerView rv_list;

    private GlobalSearchPresenter searchPresenter;
    private int flag = 0;
    private String state = "pname"; //Name人名  Lable标签  pname标题  Content内容  Time时间
    private String userId;

    private List<AllShowData> allShowDataList = new ArrayList<>();
    private SearchListAdapter allDataAdapter;

    private DoubleDateSelectDialog mDoubleTimeSelectDialog;
    private String allowedSmallestTime, allowedBiggestTime, defaultChooseDate;

    @Override
    public int bindLayout() {
        return R.layout.activity_search;
    }

    @Override
    public void doBusiness(Context mContext) {

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);

        allowedSmallestTime = (year - 30 ) + "-01-01";
        allowedBiggestTime = (year + 30 ) + "-12-31";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        defaultChooseDate = sdf.format(new Date());


        userId = AdminDao.getUserID();

        searchPresenter = new GlobalSearchPresenter(this);


        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        rv_list.setLayoutManager(linearLayout);
        allDataAdapter = new SearchListAdapter(SearchActivity.this, allShowDataList);
        rv_list.setAdapter(allDataAdapter);
    }

    @OnClick({R.id.iv_search_back, R.id.tv_search_search, R.id.tv_search_project, R.id.tv_search_work,
            R.id.tv_search_event, R.id.tv_search_name, R.id.tv_search_label,
            R.id.tv_search_title, R.id.tv_search_content, R.id.tv_search_time,
            R.id.tv_search_person, R.id.ib_search_cancel})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search_back:
                finish();
                break;
            case R.id.tv_search_search:
                // flag必填（0项目，1工作，2事件，3人员）
                searchPresenter.showGlobalSearch(flag, et_search.getText().toString(), state, userId);
                break;
            case R.id.tv_search_project:
                setFlagTvColor(tv_project);
                flag = 0;
                setHintStr("根据分类搜索项目");
                break;
            case R.id.tv_search_work:
                setFlagTvColor(tv_work);
                flag = 1;
                setHintStr("根据分类搜索工作");
                break;
            case R.id.tv_search_event:
                setFlagTvColor(tv_event);
                flag = 2;
                setHintStr("根据分类搜索事件");
                break;
            case R.id.tv_search_name:
                setFlagTvColor(tv_name);
                flag = 3;
                setHintStr("输入人名搜索");
                break;
            case R.id.tv_search_label: //Name人名  Lable标签  pname标题  Content内容  Time时间
                setStateTvColor(tv_label);
                state = "Lable";
                setHintStr("输入标签进行搜索");
                break;
            case R.id.tv_search_title:
                setStateTvColor(tv_title);
                state = "pname";
                setHintStr("输入标题进行搜索");
                break;
            case R.id.tv_search_content:
                setStateTvColor(tv_content);
                state = "Content";
                setHintStr("输入内容进行搜索");
                break;
            case R.id.tv_search_time:
                setStateTvColor(tv_time);
                state = "Time";
                setHintStr("选择时间段进行搜索");

//                Calendar c = Calendar.getInstance();
//                new DoubleDatePickerDialog(SearchActivity.this,
//                        0,
//                        new DoubleDatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear, int endDayOfMonth) {
//                                String start = startYear + "-" + (startMonthOfYear + 1) + "-" + startDayOfMonth;
//                                String end = endYear + "-" + (endMonthOfYear + 1) + "-" + endDayOfMonth;
//
//                                et_search.setText(start + " - " + end);
//                            }
//                        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();


                showCustomTimePicker();

                break;
            case R.id.tv_search_person:
                setStateTvColor(tv_person);
                state = "Name";
                setHintStr("输入人名进行搜索");
                break;
            case R.id.ib_search_cancel:
                et_search.setText("");
                setHintStr("输入关键字搜索");
                state = "pname";
                classifyTvPreColor();
                ll_list.setVisibility(View.GONE);
                ll_text.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void setFlagTvColor(TextView textView) {
        tv_work.setTextColor(Color.parseColor("#16BD92"));
        tv_project.setTextColor(Color.parseColor("#16BD92"));
        tv_event.setTextColor(Color.parseColor("#16BD92"));
        tv_name.setTextColor(Color.parseColor("#16BD92"));
        textView.setTextColor(Color.parseColor("#fa5148"));
    }

    private void setStateTvColor(TextView textView) {
        classifyTvPreColor();

        textView.setTextColor(Color.parseColor("#fa5148"));
    }

    private void classifyTvPreColor() {
        tv_label.setTextColor(Color.parseColor("#16BD92"));
        tv_title.setTextColor(Color.parseColor("#16BD92"));
        tv_content.setTextColor(Color.parseColor("#16BD92"));
        tv_time.setTextColor(Color.parseColor("#16BD92"));
        tv_person.setTextColor(Color.parseColor("#16BD92"));
    }

    private void setHintStr(String str) {
        SpannableString s = new SpannableString(str);//这里输入自己想要的提示文字
        et_search.setHint(s);
    }

    @Override
    public void showGlobalSearch(String string) {
        Log.i(TAG, "showGlobalSearch: " + string);
        if ("".equals(string) || "[]".equals(string)) {
            ToastUtils.showToast(SearchActivity.this, "暂无查询结果~");
        } else {
            ll_text.setVisibility(View.GONE);
            ll_list.setVisibility(View.VISIBLE);
            if (flag == 0) {   //项目

                allShowDataList.clear();
                showProList(string);
                allDataAdapter.notifyDataSetChanged();

            } else if (flag == 1) {  //工作

                allShowDataList.clear();
                showWorkList(string);
                allDataAdapter.notifyDataSetChanged();

            } else if (flag == 2) {  //事件

                allShowDataList.clear();
                showEventList(string);
                allDataAdapter.notifyDataSetChanged();

            } else if (flag == 3) {

            }

        }
    }

    private void showWorkList(String string) {
        AllShowData allShowData;
        List<AllData.WorkListBean> workListBeanList = new Gson().fromJson(string, new TypeToken<List<AllData.WorkListBean>>() {
        }.getType());
        for (int i = 0; i < workListBeanList.size(); i++) {
            allShowData = new AllShowData();
            String id = workListBeanList.get(i).getId();
            String title = workListBeanList.get(i).getName();
            String startTime = workListBeanList.get(i).getStartTime().split("T")[0];
            allShowData.setId(id);
            allShowData.setTime(startTime);
            allShowData.setName(title);
            allShowData.setFlag(2);
            allShowDataList.add(allShowData);
        }
    }

    private void showProList(String string) {
        AllShowData allShowData;
        List<AllData.ProListBean> proList = new Gson().fromJson(string, new TypeToken<List<AllData.ProListBean>>() {
        }.getType());
        for (int i = 0; i < proList.size(); i++) {
            allShowData = new AllShowData();
            String id = proList.get(i).getId();
            String name = proList.get(i).getName();
            String startTime = proList.get(i).getStartTime();

//            try {
//                Date parse = new SimpleDateFormat("yyyy/MM/dd").parse(startTime);
//                startTime = new SimpleDateFormat("yyyy-MM-dd").format(parse);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }

            allShowData.setId(id);
            allShowData.setTime(startTime);
            allShowData.setName(name);
            allShowData.setFlag(1);
            allShowDataList.add(allShowData);
        }
    }

    private void showEventList(String string) {
        AllShowData allShowData;
        List<AllData.EventListBean> eventList = new Gson().fromJson(string, new TypeToken<List<AllData.EventListBean>>() {
        }.getType());
        for (int i = 0; i < eventList.size(); i++) {
            allShowData = new AllShowData();
            String id = eventList.get(i).getId();
            String title = eventList.get(i).getTitle();
            String startTime = eventList.get(i).getStartTime().split("T")[0];
            allShowData.setId(id);
            allShowData.setTime(startTime);
            allShowData.setName(title);
            allShowData.setFlag(3);
            allShowDataList.add(allShowData);
        }
    }



    public void showCustomTimePicker() {

        if (mDoubleTimeSelectDialog == null) {
            mDoubleTimeSelectDialog = new DoubleDateSelectDialog(this, allowedSmallestTime, allowedBiggestTime, defaultChooseDate);
            mDoubleTimeSelectDialog.setOnDateSelectFinished(new DoubleDateSelectDialog.OnDateSelectFinished() {
                @Override
                public void onSelectFinished(String startTime, String endTime) {
                    et_search.setText(startTime + " - " + endTime);

                }
            });

            mDoubleTimeSelectDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                }
            });
        }
        if (!mDoubleTimeSelectDialog.isShowing()) {
            mDoubleTimeSelectDialog.show();
        }
    }

}
