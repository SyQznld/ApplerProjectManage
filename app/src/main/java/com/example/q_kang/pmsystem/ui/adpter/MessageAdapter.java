package com.example.q_kang.pmsystem.ui.adpter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.modules.bean.bean.MessageData;

import java.util.List;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.Holder> implements View.OnClickListener {

    private Activity activity;
    private List<MessageData> datas;
    private OnItemClickListener mItemClickListener;

    public MessageAdapter(Activity activity, List<MessageData> datas) {
        this.activity = activity;
        this.datas = datas;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(activity).inflate(R.layout.message_item, parent, false);
        Holder holder = new Holder(inflate);
        inflate.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.textView.setText(datas.get(position).getName());
        holder.badge.setBadgeNumber(datas.get(position).getNumber());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick((Integer) v.getTag());
        }

    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView textView;
        Badge badge;

        public Holder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv2);
            badge = new QBadgeView(activity).bindTarget(itemView.findViewById(R.id.tv_mesContext));
            badge.setBadgeGravity(Gravity.CENTER | Gravity.END);
            badge.setBadgeTextSize(13, true);
            badge.setGravityOffset(30, false);
            badge.setBadgePadding(4, true);
            badge.setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
                @Override
                public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                    if (dragState == STATE_SUCCEED) {
                        datas.get(getAdapterPosition()).setNumber(0);
                        Toast.makeText(activity, String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }


}
