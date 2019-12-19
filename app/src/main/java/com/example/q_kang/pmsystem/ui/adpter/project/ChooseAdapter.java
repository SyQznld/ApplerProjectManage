package com.example.q_kang.pmsystem.ui.adpter.project;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.modules.bean.bean.ChooseData;
import com.example.q_kang.pmsystem.ui.view.Utils.Utils.GlideHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseAdapter extends RecyclerView.Adapter<ChooseAdapter.ViewHolder> {

    private Context context;
    private List<ChooseData> datas;
    private OnItemClickListener onItemClickListener;



//    public ChooseAdapter(Context context) {
//        this.context = context;
//    }


    public ChooseAdapter(Context context, List<ChooseData> datas) {
        this.context = context;
        this.datas = datas;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_member_img)
        ImageView iv_member_img;
        @BindView(R.id.tv_member_name)
        TextView tv_member_name;
        @BindView(R.id.tv_member_department)
        TextView tv_member_department;
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
    public ChooseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ChooseAdapter.ViewHolder holder, int position) {
        ChooseData chooseData = datas.get(holder.getAdapterPosition());
        GlideHelper.loadNet(holder.iv_member_img, Globle.PHOTO_URL + chooseData.getFrame().getImage(), R.mipmap.icon_cy);


        holder.tv_member_name.setText(chooseData.getFrame().getRealName());
        holder.tv_member_department.setText(chooseData.getFrame().getDepartName());
        if (chooseData.isSelect()) {
            holder.mCheckBox.setImageResource(R.mipmap.ic_checked);
        } else {
            holder.mCheckBox.setImageResource(R.mipmap.ic_uncheck);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i("111", "onClick: " + holder.getAdapterPosition());
//                Log.i("111", "onClick: " + datas);
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

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position, List<ChooseData> datas);
    }


}
