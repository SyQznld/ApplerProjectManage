package com.example.q_kang.pmsystem.ui.adpter.project;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.dao.AdminDao;
import com.example.q_kang.pmsystem.modules.bean.bean.EventBusMessage;
import com.example.q_kang.pmsystem.modules.bean.bean.project.ProjectData;
import com.example.q_kang.pmsystem.present.im.DeleteItemPresenter;
import com.example.q_kang.pmsystem.ui.activity.project.ProjectDetailActivity;
import com.example.q_kang.pmsystem.ui.view.Utils.Utils.GlideHelper;
import com.example.q_kang.pmsystem.view.DeleteView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProjectAllListAdapter extends BaseQuickAdapter<ProjectData.DataBean, BaseViewHolder> {
    private Activity context;
    private List<ProjectData.DataBean> data;

    private DeleteItemPresenter deleteItemPresenter;


    public ProjectAllListAdapter(Activity context, @Nullable List<ProjectData.DataBean> data) {
        super(R.layout.project_all_list_layout, data);
        this.context = context;
        this.data = data;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void convert(BaseViewHolder helper, ProjectData.DataBean item) {
        helper.setText(R.id.tv_project_name, item.getName());
        helper.setText(R.id.tv_project_charge, item.getLeaderName());
        String startTime = item.getStartTime().split(" ")[0];
        String endTime = item.getEndTime().split(" ")[0];

        try {
            Date parse = new SimpleDateFormat("yyyy/MM/dd").parse(startTime);
            startTime = new SimpleDateFormat("yyyy-MM-dd").format(parse);

            Date parse1 = new SimpleDateFormat("yyyy/MM/dd").parse(endTime);
            endTime = new SimpleDateFormat("yyyy-MM-dd").format(parse1);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        helper.setText(R.id.tv_project_time, startTime + " ~ " + endTime);


        ProgressBar progressBar = helper.getView(R.id.pb_project);
        String progress = item.getProgress();
        String s;
        if ("null".equals(progress) || "".equals(progress) || null == progress) {
            progress = "0";
            s = " ";
        } else {
            s = progress + "%";
        }
//        progressBar.setProgress(Integer.parseInt(progress), true);
        progressBar.setProgress(Integer.parseInt(progress));
        helper.setText(R.id.tv_project_progress, s);

        CircleImageView head = helper.getView(R.id.civ_project_head);
        String leaderImage = item.getLeaderImage();
        if (!"null".equals(leaderImage) && !"".equals(leaderImage) && null != leaderImage) {
            GlideHelper.loadNet(head, Globle.PHOTO_URL + leaderImage, R.mipmap.image_weibo_home_2);
        } else {
            head.setImageResource(R.mipmap.image_weibo_home_2);
        }


//        String location = item.getLocation();
//        if ("".equals(location) || "null".equals(location) || null == location) {
//            helper.getView(R.id.iv_project_location).setVisibility(View.GONE);
//        } else {
//            helper.getView(R.id.iv_project_location).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String substring = location.substring(6, location.length() - 1);
//                    String longitude = substring.split(",")[0];
//                    String latitude = substring.split(",")[1];
//                    Intent intent = new Intent(context, BaseMapActivity.class);
//                    intent.putExtra("lat", Double.valueOf(latitude));
//                    intent.putExtra("long", Double.valueOf(longitude));
//                    intent.putExtra("type", "project");
//                    intent.putExtra("name", item.getName());
//                    context.startActivity(intent);
//                }
//            });
//        }

        helper.getView(R.id.tv_project_detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProjectDetailActivity.class);
                intent.putExtra("project", item);
                context.startActivity(intent);
            }
        });


        ImageView iv_delete = helper.getView(R.id.iv_project_delete);
        String userID = AdminDao.getUserID();
        String addUserId = item.getAddUserId();
        if (userID.equals(addUserId)) {
//            iv_delete.setVisibility(View.VISIBLE);
            iv_delete.setVisibility(View.GONE);
            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new MaterialDialog.Builder(context)
                            .title("删除项目！")
                            .content("确定删除当前项目！")
                            .positiveText("确定")
                            .negativeText("取消")
                            .widgetColor(Color.BLUE)//不再提醒的checkbox 颜色

                            .onAny(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    if (which == DialogAction.POSITIVE) {

                                        String name = item.getName();
                                        initDeletePst(name);

                                        deleteItemPresenter.deleteItem(item.getId(), "1");

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
                Log.i(TAG, "deleteItem 项目: " + string);
                //{"Result":1,"Message":"删除成功！"}
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    String result = jsonObject.getString("Result");
                    String message = jsonObject.getString("Message");
                    if ("删除成功！".equals(message)) {
                        Toast.makeText(context, name + "---项目已删除~", Toast.LENGTH_SHORT).show();
//                        context.finish();

                        EventBusMessage eventBusMessage = new EventBusMessage("delete_pro");
                        EventBus.getDefault().post(eventBusMessage);

                    } else {
                        Toast.makeText(context, "当前项目删除失败~", Toast.LENGTH_SHORT).show();
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

