package com.example.q_kang.pmsystem.ui.adpter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.q_kang.pmsystem.R;

import java.util.List;

public class GroupFrameAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context context;

    public GroupFrameAdapter(Context context, @Nullable List<String> data) {
        super(R.layout.adapter_group, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView tv_adapter_group_name = helper.getView(R.id.tv_adapter_group_name);
        TextView tv_adapter_group_count = helper.getView(R.id.tv_adapter_group_count);
        tv_adapter_group_name.setText(item);
        tv_adapter_group_count.setText("2");
    }
}
