package com.example.q_kang.pmsystem.ui.adpter.project;


import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.modules.bean.bean.project.ProjectAddBean;

import java.util.List;

public class ProjectAddKeyAdapter extends BaseQuickAdapter<ProjectAddBean, BaseViewHolder> {
    private Context context;
    private List<ProjectAddBean> data;
    private int flag;

    public ProjectAddKeyAdapter(Context context, @Nullable List<ProjectAddBean> data, int flag) {
        super(R.layout.project_add_layout, data);
        this.context = context;
        this.data = data;
        this.flag = flag;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectAddBean item) {
        helper.setText(R.id.et_key, item.getKey() + ":");
        helper.setText(R.id.et_value, item.getValue());

        EditText et_key = helper.getView(R.id.et_key);

        et_key.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setKey(s.toString());
            }
        });

        EditText et_value = helper.getView(R.id.et_value);
        et_value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setValue(s.toString());
            }
        });

        if (flag == 1) {
            et_key.setCursorVisible(false);
            et_value.setCursorVisible(false);

            helper.getView(R.id.ib_add_delete).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.ib_add_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "onClick111: " + data.size() + data);
                    data.remove(helper.getLayoutPosition());
                    notifyDataSetChanged();
                }
            });
        }

    }
}