package com.example.q_kang.pmsystem.ui.adpter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.modules.bean.bean.AllShowData;
import com.example.q_kang.pmsystem.ui.activity.event.EventDetailActivity;
import com.example.q_kang.pmsystem.ui.activity.project.ProjectDetailActivity;
import com.example.q_kang.pmsystem.ui.activity.work.WorkDetailActivity;
import com.flyco.roundview.RoundLinearLayout;

import java.util.List;

/**
 * Created by appler on 2018/7/24.
 */

public class SearchListAdapter extends BaseQuickAdapter<AllShowData, BaseViewHolder> {
    private Context context;
    private List<AllShowData> data;


    public SearchListAdapter(Context context, @Nullable List<AllShowData> data) {
        super(R.layout.search_list_layout, data);
        this.context = context;
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, AllShowData item) {
        int flag = item.getFlag();

        helper.setText(R.id.tvtime, item.getTime());
        String name = item.getName();
        if (null == name || "".equals(name) || "null".equals(name)) {
            name = "暂无名称";
        }
        helper.setText(R.id.tvname, name);

        ImageView iv_classify = helper.getView(R.id.ivclassify);
        if (flag == 1) {
            iv_classify.setImageResource(R.mipmap.classify_pro);
        }
        if (flag == 2) {
            iv_classify.setImageResource(R.mipmap.classify_work);
        }
        if (flag == 3) {
            iv_classify.setImageResource(R.mipmap.classify_event);
        }


        RoundLinearLayout rll_all = helper.getView(R.id.rllclick);
        rll_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                if (flag == 1) {
                    intent = new Intent(context, ProjectDetailActivity.class);
                }
                if (flag == 2) {
                    intent = new Intent(context, WorkDetailActivity.class);
                }
                if (flag == 3) {
                    intent = new Intent(context, EventDetailActivity.class);
                }

                intent.putExtra("alldata", item);
                intent.putExtra("flag", 4);
                context.startActivity(intent);
            }
        });

    }
}