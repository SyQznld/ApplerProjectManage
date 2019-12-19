package com.example.q_kang.pmsystem.ui.adpter.event;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.ui.view.Utils.Utils.GlideHelper;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by appler on 2018/7/26.
 */

public class EventAssignAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context context;
    private List<String> data;


    public EventAssignAdapter(Context context, @Nullable List<String> data) {
        super(R.layout.event_zhixingren_show, data);
        this.context = context;
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        Log.i(TAG, "convert EventAssignAdapter: " + item);
        CircleImageView civ_head = helper.getView(R.id.civ_zhixingren);
        String[] split = item.split(",");

        GlideHelper.loadNet(civ_head, Globle.PHOTO_URL + split[0], R.mipmap.image_weibo_home_2);
        if (!"".equals(split[1])) {
            helper.setText(R.id.tv_zhixingren, split[1]);
        } else {
            helper.setText(R.id.tv_zhixingren, "暂无参与人员");
        }

        helper.getView(R.id.ib_zhixingren_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAssignClickListener.deleteAssign(helper.getLayoutPosition(),item);
            }
        });


    }

    private DeleteAssignClickListener deleteAssignClickListener;

    public void setDeleteAssignClickListener(DeleteAssignClickListener deleteAssignClickListener) {
        this.deleteAssignClickListener = deleteAssignClickListener;
    }

    public interface DeleteAssignClickListener {
        void deleteAssign(int position,String item);
    }

}