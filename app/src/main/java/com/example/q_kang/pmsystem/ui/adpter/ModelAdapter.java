package com.example.q_kang.pmsystem.ui.adpter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.modules.bean.bean.ModelData;
import com.example.q_kang.pmsystem.ui.activity.CreadModelActivity;
import com.example.q_kang.pmsystem.ui.activity.LoginActivity;
import com.example.q_kang.pmsystem.ui.activity.MainActivity;
import com.example.q_kang.pmsystem.ui.activity.ModelActivity;

import java.util.ArrayList;
import java.util.List;

public class ModelAdapter extends BaseQuickAdapter<ModelData, BaseViewHolder> {

    private TextView tv_model_bg;
    private ModelActivity activity;

    public ModelAdapter(ModelActivity activity, @Nullable List<ModelData> data) {
        super(R.layout.model_grid, data);
        this.activity = activity;

    }

    @Override
    protected void convert(BaseViewHolder helper, ModelData item) {
        RelativeLayout rl_model = helper.getView(R.id.rl_model);
        tv_model_bg = helper.getView(R.id.tv_model_bg);
        tv_model_bg.setText(item.getName());
        rl_model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivityForResult(CreadModelActivity.class, activity.toCreateModel(item, Globle.MODEL_CHECK), Globle.MODEL_UPLOAD);
            }
        });
//        re_modelContext = helper.getView(R.id.re_modelContext);
//        re_modelContext.setLayoutManager(new LinearLayoutManager(activity));
//        List<String> data = new ArrayList<>();
//        data.add("项目模板创建_1");
//        data.add("项目模板创建_2");
//        data.add("项目模板创建_3");
//        item.setItem(data);
//        re_modelContext.setAdapter(new ItemAdapter(item.getItem()));
    }
}
