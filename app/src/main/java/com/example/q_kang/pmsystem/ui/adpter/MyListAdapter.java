package com.example.q_kang.pmsystem.ui.adpter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.q_kang.pmsystem.R;

import java.util.List;

public class MyListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private OnWorkListClick onWorkListClick;

    public void setOnWorkListClick(OnWorkListClick onWorkListClick) {
        this.onWorkListClick = onWorkListClick;
    }

    public MyListAdapter(@Nullable List<String> data) {
        super(R.layout.adapter_project_work_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_project_work, item);
        helper.setOnClickListener(R.id.tv_project_work, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onWorkListClick.onWorkListClick(helper.getAdapterPosition());
            }
        });
    }

    public interface OnWorkListClick {
        void onWorkListClick(int position);
    }

}

