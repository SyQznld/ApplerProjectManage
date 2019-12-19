package com.example.q_kang.pmsystem.ui.adpter.work;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.modules.bean.bean.ChooseData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChooseProjectAdapter extends RecyclerView.Adapter<ChooseProjectAdapter.ViewHolder> {

    private Context context;
    private List<ChooseData> datas;
    private ChooseProjectAdapter.OnItemClickListener onItemClickListener;

    public ChooseProjectAdapter(Context context, List<ChooseData> datas) {
        this.context = context;
        this.datas = datas;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_projectname)
        TextView tv_projectname;
        @BindView(R.id.ll_item)
        LinearLayout mRootView;
        @BindView(R.id.cb_projectname)
        ImageView mCheckBox;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public ChooseProjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.choose_project_item, parent, false);
        ChooseProjectAdapter.ViewHolder holder = new ChooseProjectAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ChooseProjectAdapter.ViewHolder holder, int position) {
        ChooseData data = datas.get(holder.getAdapterPosition());


        holder.tv_projectname.setText(data.getBelongProject().getName());
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

    public void setOnItemClickListener(ChooseProjectAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position, List<ChooseData> datas);
    }


}
