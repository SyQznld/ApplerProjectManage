package com.example.q_kang.pmsystem.ui.adpter.event;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.modules.bean.bean.ChooseData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by appler on 2018/6/14.
 */

public class ChooseWorkAdapter extends RecyclerView.Adapter<ChooseWorkAdapter.ViewHolder> {

    private Context context;
    private List<ChooseData> datas;
    private OnItemClickListener onItemClickListener;

    public ChooseWorkAdapter(Context context, List<ChooseData> datas) {
        this.context = context;
        this.datas = datas;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.tv_work_name)
        TextView tv_work_name;
        @BindView(R.id.tv_project_name)
        TextView tv_project_name;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.root_view)
        RelativeLayout mRootView;
        @BindView(R.id.check_box)
        ImageView mCheckBox;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public ChooseWorkAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.choose_work_item, parent, false);
        ChooseWorkAdapter.ViewHolder holder = new ChooseWorkAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ChooseWorkAdapter.ViewHolder holder, int position) {
        ChooseData data = datas.get(holder.getAdapterPosition());


            holder.tv_work_name.setText(data.getBelongWork().getName());
            String projectName = data.getBelongWork().getProjectName();
            if ("".equals(projectName) || "null".equals(projectName)) {
                projectName = "独立工作，无所属项目";
            }
            holder.tv_project_name.setText(projectName);
            holder.tv_time.setText(data.getBelongWork().getStartTime().split("T")[0]);



        if (data.isSelect()) {
            holder.mCheckBox.setImageResource(R.mipmap.ic_checked);
        } else {
            holder.mCheckBox.setImageResource(R.mipmap.ic_uncheck);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onItemClickListener.onItemClickListener(holder.getAdapterPosition(), datas);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void notifyAdapter(List<ChooseData> datas, boolean isAdd) {
        if (!isAdd) {
            this.datas = datas;
        } else {
            this.datas.addAll(datas);
        }
        notifyDataSetChanged();
    }

    public List<ChooseData> getDatas() {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        return datas;
    }

    public void setOnItemClickListener(ChooseWorkAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position, List<ChooseData> datas);
    }


}
