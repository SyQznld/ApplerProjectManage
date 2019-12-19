package com.example.q_kang.pmsystem.ui.adpter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.dao.ScheduleDao;
import com.example.q_kang.pmsystem.modules.bean.bean.ScheduleData;
import com.example.q_kang.pmsystem.modules.bean.bean.ScheduleItemData;
import com.example.q_kang.pmsystem.ui.activity.schedule.CreateScheduleActivity;
import com.example.q_kang.pmsystem.ui.view.Utils.date_time.DateChooseWheelViewDialog;
import com.jimmy.common.util.ToastUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by appler on 2018/7/28.
 */

public class ScheduleListAdapter extends BaseQuickAdapter<ScheduleItemData, BaseViewHolder> {

    private Activity context;
    private List<ScheduleItemData> data;

    public ScheduleListAdapter(Activity context, @Nullable List<ScheduleItemData> data) {
        super(R.layout.schedule_list_layout, data);

        this.context = context;
        this.data = data;

    }

    @Override
    protected void convert(BaseViewHolder helper, ScheduleItemData item) {

        helper.setText(R.id.tv_title_schedule, item.getTitle());
        helper.setText(R.id.tv_time_schedule, item.getAddTime().split("T")[0]);

        TextView tv_state = helper.getView(R.id.tv_state_schedule);
        boolean finish = item.isIsAllDay();
        if (finish) {
            tv_state.setText("全天");
            tv_state.setBackgroundResource(R.drawable.start_schedule);
            tv_state.setTextColor(Color.parseColor("#9082BD"));
        } else {
            tv_state.setText("时间阈");
            tv_state.setBackgroundResource(R.drawable.schedule_input_edit_text);
            tv_state.setTextColor(Color.parseColor("#EC4A5F"));
        }

        helper.getView(R.id.ll_schedule).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                onDeleteScheduleListener.deleteScheduleItem(helper.getLayoutPosition(), item);
                return false;
            }
        });

        helper.getView(R.id.ll_schedule).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                createSchedule(item, finish);
                Intent intent = new Intent(context, CreateScheduleActivity.class);
                intent.putExtra("flag",2);
                intent.putExtra("scheduleId",item.getId());
                context.startActivity(intent);

            }
        });


    }

    private void createSchedule(ScheduleData item, boolean finish) {
        final String[] startTime = new String[1];
        final String[] endTime = new String[1];
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("查看/编辑日程");

        View dialog = LayoutInflater.from(context).inflate(R.layout.schedule_detail_layout, null);
        EditText et_title = dialog.findViewById(R.id.et_schedule_title);
        EditText et_start = dialog.findViewById(R.id.et_schedule_start);
        EditText et_end = dialog.findViewById(R.id.et_schedule_end);
        Switch swt_finish = dialog.findViewById(R.id.swt_schedule_finish);
        EditText et_describe = dialog.findViewById(R.id.et_schedule_describe);
        et_title.setText(item.getTitle());
        et_start.setText(item.getStartTime());
        et_end.setText(item.getEndTime());
        et_describe.setText(item.getContent());
        et_start.setFocusable(false);
        et_start.setCursorVisible(false);
        et_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DateChooseWheelViewDialog startDateChooseDialog = new DateChooseWheelViewDialog(context, new DateChooseWheelViewDialog.DateChooseInterface() {
                    @Override
                    public void getDateTime(String time, boolean longTimeChecked) {
                        et_start.setText(time);
                    }
                });
                startDateChooseDialog.setDateDialogTitle("日程开始时间");
                startDateChooseDialog.showDateChooseDialog();
//                        new DataPick(context, "日程开始时间", et_start);
            }
        });


        et_end.setFocusable(false);
        et_end.setCursorVisible(false);
        et_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateChooseWheelViewDialog startDateChooseDialog = new DateChooseWheelViewDialog(context, new DateChooseWheelViewDialog.DateChooseInterface() {
                    @Override
                    public void getDateTime(String time, boolean longTimeChecked) {
                        et_end.setText(time);
                    }
                });
                startDateChooseDialog.setDateDialogTitle("日程结束时间");
                startDateChooseDialog.showDateChooseDialog();
//                        new DataPick(context, "日程结束时间", et_end);
            }
        });

        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date dt1 = df.parse(et_start.getText().toString());
            Date dt2 = df.parse(et_end.getText().toString());
            long time = dt1.getTime() - dt2.getTime();
            Log.i(TAG, "onClick: " + time);
            if (time > 0) {
                ToastUtils.showToast(context, "请保证结束时间在开始时间之后~");
            } else {

            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }


        if (finish) {
            swt_finish.setChecked(true);
        } else {
            swt_finish.setChecked(false);
        }

        swt_finish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    item.setFinish(true);
                } else {
                    item.setFinish(false);
                }

            }
        });

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = et_title.getText().toString();
                String start = et_start.getText().toString();
                String end = et_end.getText().toString();
                String describe = et_describe.getText().toString();


                item.setTitle(title);
                item.setStartTime(start);
                item.setEndTime(end);
                item.setContent(describe);

                ScheduleDao.updateLabel(item);

                notifyDataSetChanged();


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
    }


    private OnDeleteScheduleListener onDeleteScheduleListener;

    public void setOnDeleteScheduleListener(OnDeleteScheduleListener onDeleteScheduleListener) {
        this.onDeleteScheduleListener = onDeleteScheduleListener;
    }

    public interface OnDeleteScheduleListener {
        void deleteScheduleItem(int position, ScheduleItemData scheduleData);
    }
}
