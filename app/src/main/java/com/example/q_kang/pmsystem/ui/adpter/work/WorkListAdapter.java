package com.example.q_kang.pmsystem.ui.adpter.work;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.dao.AdminDao;
import com.example.q_kang.pmsystem.modules.bean.bean.EventBusMessage;
import com.example.q_kang.pmsystem.modules.bean.bean.work.WorkData;
import com.example.q_kang.pmsystem.present.im.DeleteItemPresenter;
import com.example.q_kang.pmsystem.ui.activity.map.BaseMapActivity;
import com.example.q_kang.pmsystem.ui.activity.work.WorkDetailActivity;
import com.example.q_kang.pmsystem.ui.view.Utils.view_.SearchLabelFlowLayout;
import com.example.q_kang.pmsystem.view.DeleteView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class WorkListAdapter extends BaseQuickAdapter<WorkData.DataBean, BaseViewHolder> {
    private Context context;
    private List<WorkData.DataBean> data;

    private TextView[] textViews;//指示点集合
    private TextView textView;

    private DeleteItemPresenter deleteItemPresenter;


    public WorkListAdapter(Context context, @Nullable List<WorkData.DataBean> data) {
        super(R.layout.work_list_item, data);
        this.context = context;
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkData.DataBean item) {
        helper.setText(R.id.tv_workname11, item.getName());
        String projectName = item.getProjectName();
        if ("null".equals(projectName) || "".equals(projectName)) {
            projectName = "独立工作，暂无所属项目";
        }

        SearchLabelFlowLayout view = helper.getView(R.id.ll_label11);
        view.removeAllViews();
        String lable = item.getLable();
        if ("null".equals(lable) || "".equals(lable) || lable == null) {
        } else {
            String[] split = lable.split(" ");
            Log.i("", "onBindViewHolder: " + split.toString());

            textViews = new TextView[split.length];
            for (int i = 0; i < split.length; i++) {


                TextView tv_lable = (TextView) LayoutInflater.from(context).inflate(R.layout.label_show,view, false);
                tv_lable.setText(split[i]);
                tv_lable.setBackgroundResource(R.drawable.shape_location_bg);
                tv_lable.setPadding(12, 12, 12, 12);
                view.addView(tv_lable);//添加到父View
//                textView = new TextView(Application.getApplication().getAppContext());
//                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                layoutParams.gravity = Gravity.CENTER_VERTICAL;
//                layoutParams.leftMargin = 12;
//                textView.setLayoutParams(layoutParams);
//                textViews[i] = textView;
//                textView.setText(split[i]);
//                textView.setTextSize(12);
//                textView.setTextColor(Color.WHITE);
//                textView.setBackgroundResource(R.drawable.shape_location_bg);
//                textView.setPadding(17, 8, 17, 8);
//
//
//                view.addView(textViews[i]);
            }
        }


        helper.setText(R.id.tv_projectname11, projectName);
        helper.setText(R.id.tv_fuzeren11, item.getLeader());
        TextView tv_member = helper.getView(R.id.tv_member11);
        String joinPersons = item.getJoinPersons();
        if ("".equals(joinPersons) || "null".equals(joinPersons) || null == joinPersons) {
            joinPersons = "暂无参与人员";
        }
        tv_member.setText(joinPersons);

        helper.setText(R.id.tv_begintime11, item.getStartTime().split("T")[0]);
        helper.setText(R.id.tv_endtime11, item.getEndTime().split("T")[0]);
        ImageView iv_complete = helper.getView(R.id.iv_complete11);
        String progress = item.getProgress();
        if ("100".equals(progress)) {
            iv_complete.setImageResource(R.mipmap.work_finish);
        } else {
            iv_complete.setImageResource(R.mipmap.work_process);
        }


        String location = item.getLocation();
        if ("".equals(location) || "null".equals(location) || null == location) {
            helper.getView(R.id.iv_location11).setVisibility(View.GONE);
        } else {
            // point(113.618768,34.791394)
            String substring = location.substring(6, location.length() - 1);
            String longitude = substring.split(",")[0];
            String latitude = substring.split(",")[1];

            helper.getView(R.id.iv_location11).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, BaseMapActivity.class);
                    intent.putExtra("lat", Double.valueOf(latitude));
                    intent.putExtra("long", Double.valueOf(longitude));
                    intent.putExtra("type", "work");
                    intent.putExtra("map", "location_");
                    intent.putExtra("name", item.getName());
                    context.startActivity(intent);
                }
            });
        }


        helper.getView(R.id.tv_detail11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WorkDetailActivity.class);
                intent.putExtra("work", item);
                context.startActivity(intent);
            }
        });


        CardView cv_work = helper.getView(R.id.cv_item11);
        ImageView iv_delete = helper.getView(R.id.iv_work_delete);
        String userID = AdminDao.getUserID();
        String addUserId = item.getAddUserId();
        if (userID.equals(addUserId)) {
//            iv_delete.setVisibility(View.VISIBLE);
            iv_delete.setVisibility(View.GONE);
            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new MaterialDialog.Builder(context)
                            .title("删除工作！")
                            .content("确定删除当前工作！")
                            .positiveText("确定")
                            .negativeText("取消")
                            .widgetColor(Color.BLUE)//不再提醒的checkbox 颜色

                            .onAny(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    if (which == DialogAction.POSITIVE) {

                                        String name = item.getName();
                                        initDeletePst(name);

                                        deleteItemPresenter.deleteItem(item.getId(), "2");

                                        dialog.dismiss();

                                    } else if (which == DialogAction.NEGATIVE) {
                                        dialog.dismiss();
                                    }
                                }
                            }).show();

                }
            });
        }else {
//            iv_delete.setVisibility(View.INVISIBLE);
            iv_delete.setVisibility(View.GONE);
        }
    }

    private void initDeletePst(String name) {
        deleteItemPresenter = new DeleteItemPresenter(new DeleteView() {
            @Override
            public void deleteItem(String string) {
                Log.i(TAG, "deleteItem 工作: " + string);
                //{"Result":1,"Message":"删除成功！"}
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    String result = jsonObject.getString("Result");
                    String message = jsonObject.getString("Message");
                    if ("删除成功！".equals(message)) {
                        Toast.makeText(context, name + "---工作已删除~", Toast.LENGTH_SHORT).show();
//                        context.finish();

                        EventBusMessage eventBusMessage = new EventBusMessage("delete_work");
                        EventBus.getDefault().post(eventBusMessage);

                    } else {
                        Toast.makeText(context, "当前工作删除失败~", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void setState(int state) {

            }
        });
    }

}