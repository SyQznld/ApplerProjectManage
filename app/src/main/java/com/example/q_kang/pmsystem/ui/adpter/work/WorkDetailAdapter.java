package com.example.q_kang.pmsystem.ui.adpter.work;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.Application;
import com.example.q_kang.pmsystem.dao.AdminDao;
import com.example.q_kang.pmsystem.modules.bean.bean.EventBusMessage;
import com.example.q_kang.pmsystem.modules.bean.bean.event.EventData;
import com.example.q_kang.pmsystem.modules.bean.bean.work.WorkData;
import com.example.q_kang.pmsystem.present.im.DeleteItemPresenter;
import com.example.q_kang.pmsystem.ui.activity.event.CreateEventActivity;
import com.example.q_kang.pmsystem.ui.activity.event.EventDetailActivity;
import com.example.q_kang.pmsystem.ui.view.Utils.Utils.GlideHelper;
import com.example.q_kang.pmsystem.ui.view.Utils.view_.SearchLabelFlowLayout;
import com.example.q_kang.pmsystem.view.DeleteView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class WorkDetailAdapter extends RecyclerView.Adapter<WorkDetailAdapter.ViewHolder> {

    private Context context;
    private List<EventData.DataBean> datas;
    private TextView[] textViews;//指示点集合
    private TextView textView;

    private WorkData.DataBean workBean;
    private int showAddIv;

    private DeleteItemPresenter deleteItemPresenter;

    public WorkDetailAdapter(Context context, List<EventData.DataBean> datas, WorkData.DataBean workBean, int showAddIv) {
        this.context = context;
        this.datas = datas;
        this.workBean = workBean;
        this.showAddIv = showAddIv;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_event, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        EventData.DataBean dataBean = datas.get(position);

        GlideHelper.loadNet(holder.civ_fuzeren, Globle.PHOTO_URL + dataBean.getImage(), R.mipmap.image_weibo_home_2);
        holder.tv_fuzeren.setText(dataBean.getLeaderPerson());
        String title = dataBean.getTitle();
        if ("null".equals(title) || "".equals(title) || title == null) {
            title = "暂无事件名称";
        }
        holder.tv_name.setText(title);



        holder.ll_label.removeAllViews();
        String lable = dataBean.getLable();
        if ("null".equals(lable) || "".equals(lable) || lable == null) {
            holder.tv_label.setVisibility(View.GONE);
        } else {
            String[] split = lable.split(" ");
            Log.i("", "onBindViewHolder: " + split.toString());

            textViews = new TextView[split.length];
            for (int i = 0; i < split.length; i++) {

                TextView tv_lable = (TextView) LayoutInflater.from(context).inflate(R.layout.label_show, holder.ll_label, false);
                tv_lable.setText(split[i]);
                tv_lable.setBackgroundResource(R.drawable.shape_location_bg
                );
                tv_lable.setPadding(12, 12, 12, 12);
                holder.ll_label.addView(tv_lable);//添加到父View



//
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
//                holder.ll_label.addView(textViews[i]);
            }
        }


        holder.tv_date.setText(dataBean.getStartTime().split("T")[0] + "~" + dataBean.getEndTime().split("T")[0]);
        boolean flag = dataBean.isFlag();
        if (flag) {
            holder.iv_complete.setImageResource(R.mipmap.event_finish);
        } else {
            holder.iv_complete.setImageResource(R.mipmap.event_doing);
        }


        holder.cv_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = Application.getApplication().getAppContext();
                Intent intent = new Intent(context, EventDetailActivity.class);
                intent.putExtra("event", dataBean);
                context.startActivity(intent);
            }
        });

        if (showAddIv == 1) {
            holder.iv_add.setVisibility(View.VISIBLE);
            holder.iv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(context, CreateEventActivity.class);
                    intent1.putExtra("work", workBean);
                    intent1.putExtra("flag", 3);
                    intent1.putExtra("nextId", dataBean.getId());
                    context.startActivity(intent1);
                }
            });
        } else {
            holder.iv_add.setVisibility(View.GONE);
        }


        String userID = AdminDao.getUserID();
        String addUserId = dataBean.getLaunchPerson();
        if (userID.equals(addUserId)) {
//            holder.iv_delete.setVisibility(View.VISIBLE);
            holder.iv_delete.setVisibility(View.GONE);
            holder.iv_delete.setOnClickListener(new View.OnClickListener() {
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

                                        final String title1 = dataBean.getTitle();
                                        initDeletePst(title1);

                                        deleteItemPresenter.deleteItem(dataBean.getId(), "3");

                                        dialog.dismiss();

                                    } else if (which == DialogAction.NEGATIVE) {
                                        dialog.dismiss();
                                    }
                                }
                            }).show();

                }
            });
        } else {
            holder.iv_delete.setVisibility(View.GONE);
        }
    }

    private void initDeletePst(String title) {
        deleteItemPresenter = new DeleteItemPresenter(new DeleteView() {
            @Override
            public void deleteItem(String string) {
                Log.i("", "deleteItem 事件: " + string);
                //{"Result":1,"Message":"删除成功！"}
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    String result = jsonObject.getString("Result");
                    String message = jsonObject.getString("Message");
                    if ("删除成功！".equals(message)) {
                        Toast.makeText(context, title + "---事件已删除~", Toast.LENGTH_SHORT).show();
//                        context.finish();

                        EventBusMessage eventBusMessage = new EventBusMessage("delete_work_event");
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

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_work_event_name)
        TextView tv_name;
        @BindView(R.id.tv_work_event_fzr)
        TextView tv_fuzeren;
        @BindView(R.id.tv_work_event_label)
        TextView tv_label;
        @BindView(R.id.tv_work_event_date)
        TextView tv_date;
        @BindView(R.id.cv_event)
        CardView cv_event;
        @BindView(R.id.civ_work_event_fzr)
        CircleImageView civ_fuzeren;
        @BindView(R.id.iv_work_event_complete)
        ImageView iv_complete;
        @BindView(R.id.ll_label)
        SearchLabelFlowLayout ll_label;
        @BindView(R.id.iv_work_event_add)
        ImageView iv_add;
        @BindView(R.id.iv_work_event_delete)
        ImageView iv_delete;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
