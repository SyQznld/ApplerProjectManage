package com.example.q_kang.pmsystem.ui.view.Utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.view.BaseView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Q-Kang on 2018/5/19.
 */

public class WorkOfProjectView extends LinearLayout {

    @BindView(R.id.recy_project_work)
    RecyclerView recy_project_work;

    private Context mContext;
    private List<String> works;
    private ProjectWorkAdapter projectWorkAdapter;

    public WorkOfProjectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WorkOfProjectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_project_work, this);
        ButterKnife.bind(this, view);

        works = Arrays.asList(getResources().getStringArray(R.array.projectFlow));
        projectWorkAdapter = new ProjectWorkAdapter(works);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recy_project_work.setLayoutManager(linearLayoutManager);
        recy_project_work.setAdapter(projectWorkAdapter);
    }

    private class ProjectWorkAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public ProjectWorkAdapter(@Nullable List<String> data) {
            super(R.layout.adapter_project_work_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tv_project_work, item);
        }
    }


}
