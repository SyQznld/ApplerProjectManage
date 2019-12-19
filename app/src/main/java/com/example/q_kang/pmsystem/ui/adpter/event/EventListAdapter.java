package com.example.q_kang.pmsystem.ui.adpter.event;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.dao.AdminDao;
import com.example.q_kang.pmsystem.modules.bean.bean.EventBusMessage;
import com.example.q_kang.pmsystem.modules.bean.bean.event.EventData;
import com.example.q_kang.pmsystem.present.im.DeleteItemPresenter;
import com.example.q_kang.pmsystem.ui.activity.event.EventDetailActivity;
import com.example.q_kang.pmsystem.ui.view.Utils.Utils.GlideHelper;
import com.example.q_kang.pmsystem.view.DeleteView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EventListAdapter extends BaseQuickAdapter<EventData.DataBean, BaseViewHolder> {
    private Context context;
    private List<EventData.DataBean> data;

    private DeleteItemPresenter deleteItemPresenter;


    public EventListAdapter(Context context, @Nullable List<EventData.DataBean> data) {
        super(R.layout.even_list_item, data);
        this.context = context;
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, EventData.DataBean item) {
        String title = item.getTitle();
        if ("".equals(title) || "null".equals(title) || null == title) {
            title = "暂无事件名称";
        }

        helper.setText(R.id.tv_event_list_content, title);
        ImageView iv_pishi = helper.getView(R.id.iv_event_list_pishi);
        int commentCount = item.getCommentCount();
        if (commentCount == 0) {
            iv_pishi.setVisibility(View.GONE);
        }else {
            iv_pishi.setVisibility(View.VISIBLE);
        }

        helper.setText(R.id.tv_event_list_work, item.getWorkName());
        // helper.setText(R.id.tv_event_list_project, item.getBelongProject());
        helper.setText(R.id.tv_event_list_fuzeren, item.getLeaderPerson());
        CircleImageView head = helper.getView(R.id.civ_event_list_fuzeren);
        GlideHelper.loadNet(head, Globle.PHOTO_URL + item.getImage(), R.mipmap.image_weibo_home_2);

        String assignPerson = item.getAssignPerson();
        Log.i(TAG, "convert: " + assignPerson);
//        int member = assignPerson.split(",").length;
        TextView tv_member = helper.getView(R.id.tv_event_list_member);
//        tv_member.setText(member + "人");
        String assignPersons = item.getAssignPersons();
        if ("null".equals(assignPersons) || "".equals(assignPersons) || assignPersons == null) {
            assignPersons = "暂无参与人员";
        }
        tv_member.setText(assignPersons);

        helper.setText(R.id.tv_event_list_begin, item.getStartTime().split("T")[0]);
        helper.setText(R.id.tv_event_list_end, item.getEndTime().split("T")[0]);
        TextView tv_complete = helper.getView(R.id.tv_event_list_complete);
        boolean complete = item.isFlag();
        if (complete) {
            tv_complete.setText("已完成");
            tv_complete.setTextColor(Color.parseColor("#2a7fd7"));
        } else {
            tv_complete.setText("未完成");
            tv_complete.setTextColor(Color.parseColor("#444444"));
        }

        String lable = item.getLable();
        if ("".equals(lable) || "null".equals(lable)) {
            lable = "暂无标签";
        }
        helper.setText(R.id.tv_event_list_lable, lable);

        helper.getView(R.id.rl_event_list_detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventDetailActivity.class);
                intent.putExtra("event", item);
                context.startActivity(intent);
            }
        });

        CardView cv_event = helper.getView(R.id.card_event);
        ImageView iv_delete = helper.getView(R.id.iv_event_delete);
        String userID = AdminDao.getUserID();
        String addUserId = item.getLaunchPerson();
        if (userID.equals(addUserId)) {
//            iv_delete.setVisibility(View.VISIBLE);
            iv_delete.setVisibility(View.GONE);
            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new MaterialDialog.Builder(context)
                            .title("删除事件！")
                            .content("确定删除当前事件！")
                            .positiveText("确定")
                            .negativeText("取消")
                            .widgetColor(Color.BLUE)//不再提醒的checkbox 颜色

                            .onAny(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    if (which == DialogAction.POSITIVE) {

                                        final String title1 = item.getTitle();
                                        initDeletePst(title1);

                                        deleteItemPresenter.deleteItem(item.getId(), "3");

                                        dialog.dismiss();

                                    } else if (which == DialogAction.NEGATIVE) {
                                        dialog.dismiss();
                                    }
                                }
                            }).show();

                }
            });
        } else {
            iv_delete.setVisibility(View.GONE);
        }
    }

    private void initDeletePst(String title) {
        deleteItemPresenter = new DeleteItemPresenter(new DeleteView() {
            @Override
            public void deleteItem(String string) {
                Log.i(TAG, "deleteItem 事件: " + string);
                //{"Result":1,"Message":"删除成功！"}
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    String result = jsonObject.getString("Result");
                    String message = jsonObject.getString("Message");
                    if ("删除成功！".equals(message)) {
                        Toast.makeText(context, title + "---事件已删除~", Toast.LENGTH_SHORT).show();
//                        context.finish();

                        EventBusMessage eventBusMessage = new EventBusMessage("delete_event");
                        EventBus.getDefault().post(eventBusMessage);

                    } else {
                        Toast.makeText(context, "当前事件删除失败~", Toast.LENGTH_SHORT).show();
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