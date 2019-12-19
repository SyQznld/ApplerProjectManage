package com.example.q_kang.pmsystem.ui.activity.schedule;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.BaseActivity;
import com.example.q_kang.pmsystem.modules.bean.bean.ScheduleItemData;
import com.example.q_kang.pmsystem.present.im.SchedulePresenter;
import com.example.q_kang.pmsystem.ui.view.Utils.date_time.DateChooseWheelViewDialog;
import com.example.q_kang.pmsystem.view.CreateScheduleView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jimmy.common.util.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class CreateScheduleActivity extends BaseActivity implements CreateScheduleView {

    @BindView(R.id.ib_create_schedule_back)
    ImageView iv_back;
    @BindView(R.id.tv_schedule_creat_title)
    TextView tv_title;
    @BindView(R.id.tv_schedule_submit)
    TextView tv_sumit;
    @BindView(R.id.et_schedule_title)
    EditText et_title;
    @BindView(R.id.ll_schedule_event)
    LinearLayout ll_event;
    @BindView(R.id.tv_schedule_event)
    TextView tv_event;
    @BindView(R.id.swt_schedule_allday)
    Switch swt_allday;
    @BindView(R.id.et_schedule_start)
    EditText et_start;
    @BindView(R.id.et_schedule_end)
    EditText et_end;
    @BindView(R.id.swt_schedule_finish)
    Switch swt_finish;
    @BindView(R.id.et_schedule_describe)
    EditText et_describe;
    @BindView(R.id.ll_endtime)
    LinearLayout ll_endtime;
    @BindView(R.id.ll_endtime_line)
    LinearLayout ll_endtime_line;


    private String date;
    private int flag;
    private boolean isAllDay = false;

    private SchedulePresenter schedulePresenter;
    private String scheduleId;
    private String eventId = "";
    private String eventName = "";


    @Override
    public int bindLayout() {
        return R.layout.activity_create_schedule;
    }

    @Override
    public void doBusiness(Context mContext) {

        schedulePresenter = new SchedulePresenter(this);
        date = getIntent().getStringExtra("date");
        scheduleId = getIntent().getStringExtra("scheduleId");
        flag = getIntent().getIntExtra("flag", 0);
        Log.i(TAG, "doBusiness date: " + date);

        swt_allday.setChecked(true);
        et_start.setText(date + " 00:00");
        et_end.setText(date + " 00:00");
        ll_endtime.setVisibility(View.GONE);
        ll_endtime_line.setVisibility(View.GONE);
        et_end.setEnabled(false);


        et_start.setFocusable(false);
        et_start.setCursorVisible(false);

        et_end.setFocusable(false);
        et_end.setCursorVisible(false);


        swt_allday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isAllDay = true;
                    String time_ = et_start.getText().toString().split(" ")[0] + " 00:00";
                    et_start.setText(time_);
                    et_end.setText(time_);
                    ll_endtime.setVisibility(View.GONE);
                    ll_endtime_line.setVisibility(View.GONE);
                    et_end.setEnabled(false);
                } else {
                    isAllDay = false;
                    et_end.setEnabled(true);
                    ll_endtime.setVisibility(View.VISIBLE);
                    ll_endtime_line.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    @OnClick({R.id.tv_schedule_submit, R.id.et_schedule_start, R.id.et_schedule_end, R.id.ib_create_schedule_back})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.ib_create_schedule_back:
                finish();
                break;
            case R.id.tv_schedule_submit:
                new MaterialDialog.Builder(CreateScheduleActivity.this)
                        .title("提交日程！")
                        .content("确定提交当前日程！")
                        .positiveText("确定")
                        .negativeText("取消")
                        .widgetColor(Color.BLUE)//不再提醒的checkbox 颜色
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (which == DialogAction.POSITIVE) {
                                    String title = et_title.getText().toString();
                                    String content = et_describe.getText().toString();
                                    String startTime = et_start.getText().toString();
                                    String endTime = et_end.getText().toString();

                                    if ("".equals(title)) {
                                        ToastUtils.showToast(CreateScheduleActivity.this, "日程标题不能为空~~~");
                                    } else if ("".equals(startTime)) {
                                        ToastUtils.showToast(CreateScheduleActivity.this, "日程开始时间不能为空~~~");
                                    } else if ("".equals(endTime)) {
                                        ToastUtils.showToast(CreateScheduleActivity.this, "日程结束时间不能为空~~~");
                                    } else {

                                        try {
                                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                                            Date dt1 = df.parse(et_start.getText().toString());
                                            Date dt2 = df.parse(et_end.getText().toString());
                                            long time = dt1.getTime() - dt2.getTime();
                                            Log.i(TAG, "onClick: " + time);
                                            if (time > 0) {
                                                ToastUtils.showToast(CreateScheduleActivity.this, "请保证结束时间在开始时间之后~");
                                            } else {
                                                if (flag == 2) {   //编辑
                                                    //scheduleId, title, content, startTime, endTime, isFinish, ItemId, ItemName, ItemType, IsDelete, IsAllDay, AddTime
                                                    schedulePresenter.editSchedule(scheduleId, title, content, startTime, endTime, false,
                                                            eventId, eventName, 0, false, isAllDay, date);
                                                } else {     //新建
                                                    schedulePresenter.createSchedule(title, content, startTime, endTime, false,
                                                            eventId, eventName, 0, false, isAllDay, date);
                                                }
                                            }

                                        } catch (Exception exception) {
                                            exception.printStackTrace();
                                        }
                                    }


                                } else if (which == DialogAction.NEGATIVE) {
                                    dialog.dismiss();
                                }
                            }
                        }).show();
                break;
            case R.id.et_schedule_start:
                DateChooseWheelViewDialog startDateChooseDialog = new DateChooseWheelViewDialog(CreateScheduleActivity.this, new DateChooseWheelViewDialog.DateChooseInterface() {
                    @Override
                    public void getDateTime(String time, boolean longTimeChecked) {
                        Log.i(TAG, "getDateTime: " + isAllDay);
                        if (isAllDay){
                            time = time.split(" ")[0] + " 00:00";
                            et_end.setText(time);
                            ll_endtime.setVisibility(View.GONE);
                            ll_endtime_line.setVisibility(View.GONE);
                        }
                        et_start.setText(time);
                    }
                });
                startDateChooseDialog.setDateDialogTitle("日程开始时间");
                startDateChooseDialog.showDateChooseDialog();
                break;
            case R.id.et_schedule_end:
                DateChooseWheelViewDialog endDateChooseDialog = new DateChooseWheelViewDialog(CreateScheduleActivity.this, new DateChooseWheelViewDialog.DateChooseInterface() {
                    @Override
                    public void getDateTime(String time, boolean longTimeChecked) {
                        et_end.setText(time);
                    }
                });
                endDateChooseDialog.setDateDialogTitle("日程结束时间");
                endDateChooseDialog.showDateChooseDialog();
                break;

        }
    }

    @Override
    public void createSchedule(String string) {
        Log.i(TAG, "createSchedule: " + string);
        //{"Result":0,"Message":"添加成功！"}
        try {
            JSONObject jsonObject = new JSONObject(string);
            String result = jsonObject.getString("Result");
            String message = jsonObject.getString("Message");
            if ("0".equals(result)) {
                finish();
            }

            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editSchedule(String string) {
        Log.i(TAG, "editSchedule: " + string);
        try {
            JSONObject jsonObject = new JSONObject(string);
            String result = jsonObject.getString("Result");
            String message = jsonObject.getString("Message");
            if ("0".equals(result)) {
                finish();
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getScheduleDetail(String string) {
        Log.i(TAG, "getScheduleDetail: " + string);
        ScheduleItemData scheduleItemData = new Gson().fromJson(string, new TypeToken<ScheduleItemData>() {
        }.getType());
        if (null != scheduleItemData) {
            initScheduleData(scheduleItemData);
        }


    }

    private void initScheduleData(ScheduleItemData scheduleItemData) {
        Log.i(TAG, "initScheduleData: " + flag + "   " + scheduleItemData);
        if (flag == 2) {     //编辑日程
            tv_title.setText("日程详情");
            tv_sumit.setText("编辑提交");
            et_title.setText(scheduleItemData.getTitle());
            et_describe.setText(scheduleItemData.getContent());
            et_start.setText(scheduleItemData.getStartTime().replace("T", " "));
            et_end.setText(scheduleItemData.getEndTime().replace("T", " "));
            boolean isAllDay = scheduleItemData.isIsAllDay();
            if (isAllDay) {
                swt_allday.setChecked(true);


            } else {
                swt_allday.setChecked(false);
            }
            String eventName = scheduleItemData.getItemName();
            if ("".equals(eventName) || null == eventName || "null".equals(eventName)) {
                ll_event.setVisibility(View.GONE);
            } else {
                ll_event.setVisibility(View.VISIBLE);
                tv_event.setText(eventName);
                tv_event.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
            date = scheduleItemData.getAddTime();
        } else {
            ll_event.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        schedulePresenter.getScheduleDetail(scheduleId);
    }
}
