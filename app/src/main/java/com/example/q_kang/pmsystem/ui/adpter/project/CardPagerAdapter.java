package com.example.q_kang.pmsystem.ui.adpter.project;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.q_kang.pmsystem.modules.bean.bean.work.WorkData;
import com.example.q_kang.pmsystem.present.im.DeleteItemPresenter;
import com.example.q_kang.pmsystem.ui.activity.work.CreateWorkActivity;
import com.example.q_kang.pmsystem.ui.activity.work.WorkDetailActivity;
import com.example.q_kang.pmsystem.ui.view.Utils.Utils.GlideHelper;
import com.example.q_kang.pmsystem.view.DeleteView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private Context context;
    private List<CardView> mViews;
    private List<WorkData.DataBean> mData;
    private float mBaseElevation;

    private String proLeaderId;
    private String proAddId;
    private String projectId;
    private String projectName;
    private DeleteItemPresenter deleteItemPresenter;


    public CardPagerAdapter(Context context,String proLeaderId, String proAddId,String projectId, String projectName) {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();

        this.context = context;
        this.proLeaderId = proLeaderId;
        this.proAddId = proAddId;
        this.projectId = projectId;
        this.projectName = projectName;
    }

    public void addCardItem(WorkData.DataBean item) {
        mViews.add(null);
        mData.add(item);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.adapter, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(WorkData.DataBean item, View view) {
        TextView workName = view.findViewById(R.id.tv_name1);
        TextView workFuzeren = view.findViewById(R.id.tv_fuzeren1);
        TextView workTime = view.findViewById(R.id.tv_time1);
        TextView endTime = view.findViewById(R.id.tv_time2);
        ImageView iv_complete11 = view.findViewById(R.id.iv_complete11);
        Button button = view.findViewById(R.id.btn_work_detail);
        CircleImageView imageView = view.findViewById(R.id.civ_head1);
        ImageView iv_delete = view.findViewById(R.id.iv_delete_work_);
        ImageView iv_add = view.findViewById(R.id.iv_add_work_);


        boolean complete = item.isFlag();
        if (complete) {
            iv_complete11.setImageResource(R.mipmap.work_finish);
        } else {
            iv_complete11.setImageResource(R.mipmap.work_process);
        }

        String leaderImage = item.getLeaderImage();
        if ("".equals(leaderImage) || "null".equals(leaderImage)) {
            imageView.setImageResource(R.mipmap.image_weibo_home_2);
        } else {
            GlideHelper.loadNet(imageView, Globle.PHOTO_URL + leaderImage, R.mipmap.image_weibo_home_2);
        }
        workName.setText(item.getName());
        workFuzeren.setText("负责人：" + item.getLeader());
        workTime.setText("开始时间：" + item.getStartTime().split("T")[0]);
        endTime.setText("结束时间：" + item.getEndTime().split("T")[0]);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = Application.getApplication().getAppContext();
                Intent intent = new Intent(context, WorkDetailActivity.class);
                intent.putExtra("work", item);
                intent.putExtra("proLeaderId", proLeaderId);
                intent.putExtra("proAddId", proAddId);
                context.startActivity(intent);

            }
        });

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



        Log.i("", "bind leaderId: " + proLeaderId);
        Log.i("", "bind proaddUserId1: " + proAddId);
        Log.i("", "bind userID: " + userID);
        if (userID.equals(proAddId) || userID.equals(proLeaderId)){
            iv_add.setVisibility(View.VISIBLE);
            iv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new MaterialDialog.Builder(context)
                            .title("添加工作！")
                            .content("确定在 " + item.getName() +  " 前添加工作！")
                            .positiveText("确定")
                            .negativeText("取消")
                            .widgetColor(Color.BLUE)//不再提醒的checkbox 颜色

                            .onAny(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    if (which == DialogAction.POSITIVE) {

                                        Context context = Application.getApplication().getAppContext();
                                        Intent intent = new Intent(context, CreateWorkActivity.class);
                                        intent.putExtra("projectname", projectName);
                                        intent.putExtra("projectId", projectId);
                                        intent.putExtra("flag", 3);
                                        intent.putExtra("nextId", item.getId());
                                        context.startActivity(intent);

                                        dialog.dismiss();

                                    } else if (which == DialogAction.NEGATIVE) {
                                        dialog.dismiss();
                                    }
                                }
                            }).show();

                }
            });
        }else {
            iv_add.setVisibility(View.GONE);
        }
    }

    private void initDeletePst(String name) {
        deleteItemPresenter = new DeleteItemPresenter(new DeleteView() {
            @Override
            public void deleteItem(String string) {
                Log.i("", "deleteItem 工作: " + string);
                //{"Result":1,"Message":"删除成功！"}
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    String result = jsonObject.getString("Result");
                    String message = jsonObject.getString("Message");
                    if ("删除成功！".equals(message)) {
                        Toast.makeText(context, name + "---工作已删除~", Toast.LENGTH_SHORT).show();
//                        context.finish();

                        EventBusMessage eventBusMessage = new EventBusMessage("delete_pro_work");
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
