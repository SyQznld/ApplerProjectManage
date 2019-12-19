package com.example.q_kang.pmsystem.ui.activity.schedule;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.BaseActivity;
import com.example.q_kang.pmsystem.dao.ScheduleDao;
import com.example.q_kang.pmsystem.modules.bean.bean.ScheduleData;
import com.example.q_kang.pmsystem.modules.bean.bean.ScheduleItemData;
import com.example.q_kang.pmsystem.present.im.ShowScheduleListPresenter;
import com.example.q_kang.pmsystem.ui.adpter.ScheduleListAdapter;
import com.example.q_kang.pmsystem.ui.view.Utils.CommonUtils;
import com.example.q_kang.pmsystem.view.ScheduleListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jeek.calendar.widget.calendar.OnCalendarClickListener;
import com.jeek.calendar.widget.calendar.month.MonthCalendarView;
import com.jeek.calendar.widget.calendar.schedule.ScheduleLayout;
import com.jeek.calendar.widget.calendar.schedule.ScheduleRecyclerView;
import com.jeek.calendar.widget.calendar.week.WeekCalendarView;
import com.jimmy.common.listener.OnTaskFinishedListener;
import com.jimmy.common.util.ToastUtils;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ScheduleActivity extends BaseActivity implements
        OnCalendarClickListener,
        OnTaskFinishedListener<List<ScheduleData>>,
        ScheduleListView {

    @BindView(R.id.tv_schedule_month)
    TextView tv_month;
    @BindView(R.id.tv_schedule_day)
    TextView tv_day;
    @BindView(R.id.mcvCalendar)
    MonthCalendarView mc_month;
    @BindView(R.id.rlMonthCalendar)
    RelativeLayout rl_month_cal;
    @BindView(R.id.wcvCalendar)
    WeekCalendarView cla_week;
    @BindView(R.id.rvScheduleList)
    ScheduleRecyclerView rv_list;
    @BindView(R.id.rlNoTask)
    RelativeLayout rl_no_task;
    @BindView(R.id.rlScheduleList)
    RelativeLayout rl_sdu_list;
    @BindView(R.id.slSchedule)
    ScheduleLayout sl_schedule;
    @BindView(R.id.etInputContent)
    EditText et_input;
    @BindView(R.id.ibMainOk)
    ImageButton ib_ok;
    private String TAG = "ScheduleActivity";
    @BindView(R.id.iv_schedule_back)
    ImageView iv_back;
    @BindView(R.id.cv_schedule)
    MaterialCalendarView calendarView;

    @BindView(R.id.tv_schedule_back)
    TextView tv_today;

    private String[] mMonthText;
    private int mCurrentSelectYear, mCurrentSelectMonth, mCurrentSelectDay;

    //    private List<ScheduleData> scheduleDataList = new ArrayList<>();
    private List<ScheduleData> scheduleDataList;
    private ScheduleListAdapter mScheduleAdapter;
    private String startTime;

    private ShowScheduleListPresenter scheduleListPresenter;

    @Override
    public int bindLayout() {
        return R.layout.activity_schedule;
    }

    @Override
    public void doBusiness(Context mContext) {

        mMonthText = getResources().getStringArray(R.array.calendar_month);

        scheduleListPresenter = new ShowScheduleListPresenter(this);

        Calendar calendar = Calendar.getInstance();
        setCurrentSelectDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        resetMainTitleDate(mCurrentSelectYear, mCurrentSelectMonth, mCurrentSelectDay);
        sl_schedule.setOnCalendarClickListener(this);

        startTime = new SimpleDateFormat(CommonUtils.DATETYPE).format(new Date());

        et_input.setCursorVisible(false);
        et_input.setFocusable(false);
        et_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScheduleActivity.this, CreateScheduleActivity.class);
                intent.putExtra("date", startTime);
                startActivity(intent);
            }
        });


//        initRvShow(startTime);

    }

    private void initRvShow(String time) {

        Log.i(TAG, "initRvShow time: " + time);
        scheduleDataList = new ArrayList<>();

        scheduleDataList = ScheduleDao.queryByTime(time);

        Log.i(TAG, "initRvShow进入页面   点击日期: " + scheduleDataList.size() + scheduleDataList);


        if (scheduleDataList != null && scheduleDataList.size() > 0 && !"[]".equals(scheduleDataList)) {
            rl_no_task.setVisibility(View.GONE);

            rv_list = sl_schedule.getSchedulerRecyclerView();
            LinearLayoutManager manager = new LinearLayoutManager(this);
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            rv_list.setLayoutManager(manager);
            DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
            itemAnimator.setSupportsChangeAnimations(false);
            rv_list.setItemAnimator(itemAnimator);
//            mScheduleAdapter = new ScheduleListAdapter(this, scheduleDataList);
//            rv_list.setAdapter(mScheduleAdapter);

//            mScheduleAdapter.setOnDeleteScheduleListener(new ScheduleListAdapter.OnDeleteScheduleListener() {
//                @Override
//                public void deleteScheduleItem(int position, ScheduleData scheduleData) {
//
//                    new MaterialDialog.Builder(ScheduleActivity.this)
//                            .title("删除日程条目")
//                            .positiveText("确定")
//                            .negativeText("取消")
//                            .widgetColor(Color.BLUE)//不再提醒的checkbox 颜色
//
//                            .onAny(new MaterialDialog.SingleButtonCallback() {
//                                @Override
//                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                    if (which == DialogAction.POSITIVE) {
//                                        scheduleDataList.remove(position);
//                                        ScheduleDao.deleteLabel(scheduleData.getId());
//                                        Log.i(TAG, "initRvShow删除后: " + scheduleDataList.size() + scheduleDataList);
//                                        mScheduleAdapter.notifyDataSetChanged();
//                                        if (!"[]".equals(scheduleDataList) && scheduleDataList.size() > 0 && null != scheduleDataList) {
//                                            rl_no_task.setVisibility(View.GONE);
//                                        } else {
//                                            rl_no_task.setVisibility(View.VISIBLE);
//                                        }
//
//                                    } else if (which == DialogAction.NEGATIVE) {
//                                        dialog.dismiss();
//                                    }
//
//                                }
//                            }).show();
//                }
//            });
        } else {
            rl_no_task.setVisibility(View.VISIBLE);
        }


    }


    @OnClick({R.id.iv_schedule_back, R.id.ibMainOk, R.id.tv_schedule_back})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_schedule_back:
                finish();
                break;
            case R.id.ibMainOk:
                String title = et_input.getText().toString();
                if (TextUtils.isEmpty(title)) {
                    ToastUtils.showShortToast(ScheduleActivity.this, "标题不能为空");
                } else {

                    ScheduleData schedule = new ScheduleData();
                    schedule.setTitle(title);
                    schedule.setStartTime(startTime);
                    schedule.setEndTime(startTime);
                    schedule.setFinish(false);

                    ScheduleDao.insertLabel(schedule);

                    scheduleDataList.add(schedule);
                    et_input.setText("");
                    Log.i(TAG, "initRvShow新建，插入数据库: " + scheduleDataList.size() + scheduleDataList);

                    initRvShow(startTime);
                }

                break;
            case R.id.tv_schedule_back:
                mc_month.setTodayToView();


                break;
        }
    }


    @Override
    public void onTaskFinished(List<ScheduleData> data) {

    }

    @Override
    public void onClickDate(int year, int month, int day) {
        resetMainTitleDate(year, month, day);
        startTime = year + "-" + (month + 1) + "-" + day;
        try {
            Date parse = new SimpleDateFormat("yyyy-MM-dd").parse(startTime);
            startTime = new SimpleDateFormat("yyyy-MM-dd").format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        initRvShow(startTime);
        scheduleListPresenter.getDayScheduleList(startTime);

    }

    @Override
    public void onPageChange(int year, int month, int day) {

    }


    public void resetMainTitleDate(int year, int month, int day) {

        Calendar calendar = Calendar.getInstance();
        if (year == calendar.get(Calendar.YEAR) &&
                month == calendar.get(Calendar.MONTH) &&
                day == calendar.get(Calendar.DAY_OF_MONTH)) {
            tv_month.setText(mMonthText[month]);
            tv_day.setText(getString(R.string.calendar_today));
        } else {
            if (year == calendar.get(Calendar.YEAR)) {
                tv_month.setText(mMonthText[month]);
            } else {
                tv_month.setText(String.format("%s%s", String.format(getString(R.string.calendar_year), year),
                        mMonthText[month]));
            }
            tv_day.setText(String.format(getString(R.string.calendar_day), day));
        }
        setCurrentSelectDate(year, month, day);
    }

    private void setCurrentSelectDate(int year, int month, int day) {
        mCurrentSelectYear = year;
        mCurrentSelectMonth = month;
        mCurrentSelectDay = day;

    }

    @Override
    public void getScheduleList(String string) {
        Log.i(TAG, "getScheduleList: " + string);
        List<ScheduleItemData> scheduleItemDataList = new Gson().fromJson(string, new TypeToken<List<ScheduleItemData>>() {
        }.getType());
        if ("".equals(string) || scheduleItemDataList.size() == 0 || "null".equals(string) || null == string) {
            rl_no_task.setVisibility(View.VISIBLE);
        } else {
            rl_no_task.setVisibility(View.GONE);

            rv_list = sl_schedule.getSchedulerRecyclerView();
            LinearLayoutManager manager = new LinearLayoutManager(this);
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            rv_list.setLayoutManager(manager);
            DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
            itemAnimator.setSupportsChangeAnimations(false);
            rv_list.setItemAnimator(itemAnimator);

            mScheduleAdapter = new ScheduleListAdapter(this, scheduleItemDataList);
            rv_list.setAdapter(mScheduleAdapter);

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        scheduleListPresenter.getDayScheduleList(startTime);
    }
}
