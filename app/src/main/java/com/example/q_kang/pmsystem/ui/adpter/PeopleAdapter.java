package com.example.q_kang.pmsystem.ui.adpter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.modules.bean.bean.Frame;
import com.example.q_kang.pmsystem.ui.activity.frame.FrameMemberActivity;
import com.example.q_kang.pmsystem.ui.view.Utils.Utils.GlideHelper;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PeopleAdapter extends BaseQuickAdapter<Frame, BaseViewHolder> {
    private static final String TAG = "PeopleAdapter";
    private Activity context;

    public PeopleAdapter(Activity context, @Nullable List<Frame> data) {
        super(R.layout.adapter_people, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final Frame item) {
        TextView tv_adapter_name = helper.getView(R.id.tv_adapter_name);
        CircleImageView cir_frame_image = helper.getView(R.id.cir_frame_image);
        helper.addOnClickListener(R.id.tv_adapter_name);
        tv_adapter_name.setText(item.getRealName());
        GlideHelper.loadNet(cir_frame_image, Globle.PHOTO_URL + item.getImage(), R.mipmap.logo);
        Log.i(TAG, "convert: " + Globle.PHOTO_URL + item.getImage());

        helper.getView(R.id.ll_adapter_member).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FrameMemberActivity.class);
                intent.putExtra("frame", item);
                context.startActivity(intent);
            }
        });
    }
}
