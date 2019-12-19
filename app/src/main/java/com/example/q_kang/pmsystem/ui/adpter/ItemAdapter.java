package com.example.q_kang.pmsystem.ui.adpter;


import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.q_kang.pmsystem.R;

import java.util.List;

public class ItemAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ItemAdapter(@Nullable List<String> data) {
        super(R.layout.layout_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_position = helper.getView(R.id.tv_position);
        ImageButton ib_add = helper.getView(R.id.ib_add);
        ImageButton ib_del = helper.getView(R.id.ib_del);
        ib_add.setVisibility(View.GONE);
        ib_del.setVisibility(View.GONE);
        tv_name.setTextSize(12);
        tv_name.setText(item);
        tv_position.setTextSize(10);
        tv_position.setText((helper.getAdapterPosition() + 1) + "");
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) tv_position.getLayoutParams();
        linearParams.height = 60;
        linearParams.width = 60;
        tv_position.setLayoutParams(linearParams);
    }
}
