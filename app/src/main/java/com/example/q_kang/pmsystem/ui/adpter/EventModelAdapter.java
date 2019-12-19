package com.example.q_kang.pmsystem.ui.adpter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.modules.bean.bean.EventModelData;
import com.example.q_kang.pmsystem.ui.activity.CreadModelActivity;
import com.example.q_kang.pmsystem.ui.activity.ModelActivity;

import java.util.List;

/**
 * Created by appler on 2018/8/31.
 */

public class EventModelAdapter extends BaseQuickAdapter<EventModelData.DataBean, BaseViewHolder> {

    private TextView tv_model_bg;
    private ModelActivity activity;

    public EventModelAdapter(ModelActivity activity, @Nullable List<EventModelData.DataBean> data) {
        super(R.layout.event_model_grid, data);
        this.activity = activity;

    }

    @Override
    protected void convert(BaseViewHolder helper, EventModelData.DataBean item) {
        RelativeLayout rl_model = helper.getView(R.id.rlevent_model);
        tv_model_bg = helper.getView(R.id.tvevent_model_bg);
        tv_model_bg.setText(item.getName());
        rl_model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivityForResult(
                        CreadModelActivity.class,
                        activity.toCreateEventModel(item, Globle.EVENT_MODEL_CHECK),
                        Globle.EVENT_MODEL_UPLOAD);
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
