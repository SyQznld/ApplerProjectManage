package com.example.q_kang.pmsystem.ui.adpter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.modules.bean.bean.EventBusMessage;
import com.example.q_kang.pmsystem.modules.bean.bean.MessageNotifyData;
import com.example.q_kang.pmsystem.present.im.MessageUpdatePresenter;
import com.example.q_kang.pmsystem.ui.activity.document.DocumentDetailActivity;
import com.example.q_kang.pmsystem.ui.activity.event.EventDetailActivity;
import com.example.q_kang.pmsystem.ui.activity.news.NewsDetailActivity;
import com.example.q_kang.pmsystem.ui.activity.project.ProjectDetailActivity;
import com.example.q_kang.pmsystem.ui.activity.work.WorkDetailActivity;
import com.example.q_kang.pmsystem.view.UpdateMessageView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class MessageListAdapter extends BaseQuickAdapter<MessageNotifyData.DataBean, BaseViewHolder> implements UpdateMessageView {
    private Context context;
    private List<MessageNotifyData.DataBean> data;

    private MessageUpdatePresenter updatePresenter;


    public MessageListAdapter(Context context, @Nullable List<MessageNotifyData.DataBean> data) {
        super(R.layout.message_list_layout, data);
        this.context = context;
        this.data = data;

        updatePresenter = new MessageUpdatePresenter(this);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageNotifyData.DataBean item) {

        String partA = item.getPartA();
        ImageView iv_delete = helper.getView(R.id.iv_message_delete);
        //0项目  1工作   2事件   3公文   4新闻公告
        ImageView iv_read = helper.getView(R.id.iv_message_dot);
        boolean isRead = item.isIsRead();
        if (isRead) {
            partA = "您已查看";
            iv_read.setImageResource(R.drawable.ic_doc_read);


            iv_delete.setVisibility(View.VISIBLE);
            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new MaterialDialog.Builder(context)
                            .title("删除消息！")
                            .content("确定删除当前消息！")
                            .positiveText("确定")
                            .negativeText("取消")
                            .widgetColor(Color.BLUE)//不再提醒的checkbox 颜色

                            .onAny(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    if (which == DialogAction.POSITIVE) {

                                        updatePresenter.deleteMessage(item.getMessage_Id(), "2");
                                        dialog.dismiss();

                                    } else if (which == DialogAction.NEGATIVE) {
                                        dialog.dismiss();
                                    }
                                }
                            }).show();
                }
            });

        } else {
            partA = item.getPartA();
            iv_read.setImageResource(R.drawable.ic_doc_unread);
        }

        ImageView iv_classify = helper.getView(R.id.iv_message_classify);
        TextView tv_classify = helper.getView(R.id.tv_message_classify);

        int type = Integer.parseInt(item.get_Type());
        if (type == 0) {
            iv_classify.setImageResource(R.mipmap.classify_pro);
            tv_classify.setText("项目");
            tv_classify.setTextColor(Color.parseColor("#16BD92"));
        } else if (type == 1) {
            iv_classify.setImageResource(R.mipmap.classify_work);
            tv_classify.setText("工作");
            tv_classify.setTextColor(Color.parseColor("#fa5148"));
        } else if (type == 2) {
            iv_classify.setImageResource(R.mipmap.classify_event);
            tv_classify.setText("事件");
            tv_classify.setTextColor(Color.parseColor("#2a7fd7"));
        } else if (type == 3) {
            iv_classify.setImageResource(R.drawable.ic_classify_document);
            tv_classify.setText("公文");
            tv_classify.setTextColor(Color.parseColor("#EEC900"));
        } else if (type == 4) {
            iv_classify.setImageResource(R.drawable.ic_news_news);
            tv_classify.setText("新闻公告");
            tv_classify.setTextColor(Color.parseColor("#D33636"));
        }

        TextView tv_title = helper.getView(R.id.tv_message_title);

        String partB = item.getPartB();
        String partC = item.getPartC();
        String title = partA + " " + partC + partB;
        tv_title.setText(title);


        helper.getView(R.id.cv_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updatePresenter.updateMessageState(item.getMessage_Id());


                Intent intent = null;

                if (type == 0) {
                    intent = new Intent(context, ProjectDetailActivity.class);
                } else if (type == 1) {
                    intent = new Intent(context, WorkDetailActivity.class);
                } else if (type == 2) {
                    intent = new Intent(context, EventDetailActivity.class);
                } else if (type == 3) {
                    intent = new Intent(context, DocumentDetailActivity.class);
                } else if (type == 4) {
                    intent = new Intent(context, NewsDetailActivity.class);
                }

                intent.putExtra("messagenotify", item);
                intent.putExtra("flag", 10);
                context.startActivity(intent);

            }
        });

    }


    @Override
    public void setState(int state) {

    }

    @Override
    public void updateMessage(String string) {
        Log.i(TAG, "updateMessage: " + string);
    }

    @Override
    public void deleteMessage(String string) {
        Log.i(TAG, "deleteMessage: " + string);

        try {
            JSONObject jsonObject = new JSONObject(string);
            String Result = jsonObject.getString("Result");
            String Message = jsonObject.getString("Message");


            if ("1".equals(Result)) {
                Toast.makeText(context, "消息删除失败~", Toast.LENGTH_SHORT).show();
            } else if ("0".equals(Result)) {
                Toast.makeText(context, "消息删除成功~", Toast.LENGTH_SHORT).show();

                EventBusMessage eventBusMessage = new EventBusMessage("deleteMessage");
                EventBus.getDefault().post(eventBusMessage);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
