package com.example.q_kang.pmsystem.ui.adpter.project;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.modules.bean.bean.work.ModelWorkBean;
import com.example.q_kang.pmsystem.ui.activity.ChooseActivity;
import com.example.q_kang.pmsystem.ui.view.Utils.CommonUtils;
import com.example.q_kang.pmsystem.ui.view.Utils.Utils.GlideHelper;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ModelChooseWorkAdapter extends BaseQuickAdapter<ModelWorkBean, BaseViewHolder> {
    private Activity context;

    private List<ModelWorkBean> data;
    private int flag;


    public ModelChooseWorkAdapter(Activity context, @Nullable List<ModelWorkBean> data,int flag) {
        super(R.layout.a_layout, data);
        this.context = context;
        this.data = data;
        this.flag = flag;

    }

    @Override
    protected void convert(BaseViewHolder helper, ModelWorkBean item) {
        int layoutPosition = helper.getLayoutPosition();

        helper.setText(R.id.tv_tileName, item.getWorkName());
        CircleImageView circleImageView = helper.getView(R.id.civ_choose);
        TextView tv_fuzeren = helper.getView(R.id.tv_choose);
        ImageButton ib_choose = helper.getView(R.id.ib_choose);

        String head = item.getHead();
        if ("".equals(head)) {
            circleImageView.setVisibility(View.GONE);
        }
        GlideHelper.loadNet(circleImageView, Globle.PHOTO_URL + head, R.mipmap.icon_cy);
        tv_fuzeren.setText(item.getFuzerenName());

        if (flag == 1){
            ib_choose.setVisibility(View.GONE);
        }else {
            ib_choose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent fuzerenIntent = new Intent(context, ChooseActivity.class);
                    fuzerenIntent.putExtra("flag", "fuzeren");
                    fuzerenIntent.putExtra("index", layoutPosition);
                    context.startActivityForResult(fuzerenIntent, CommonUtils.MODEL_CHOOSE_WORK_FUZEREN);
                }
            });
        }


    }

    @NonNull
    @Override
    public List<ModelWorkBean> getData() {
        return data;
    }
}