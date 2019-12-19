package com.example.q_kang.pmsystem.ui.adpter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.modules.bean.bean.ChatData;
import com.example.q_kang.pmsystem.ui.activity.PersonelInfoActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<ChatData> datas;
    private Activity activity;
    private OnItemClickListener onItemClickListener;

    public ChatAdapter(Activity activity, List<ChatData> datas) {
        this.activity = activity;
        this.datas = datas;
    }

    @Override
    public int getItemViewType(int position) {
        return datas.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.push_item, parent, false);
                view.setOnClickListener(this);
                return new ChatViewHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
                view.setOnClickListener(this);
                return new ImagerViewHolder(view);
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ziji_layout, parent, false);
                view.setOnClickListener(this);
                return new MessageViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        holder.itemView.setTag(position);
        if (holder instanceof ImagerViewHolder) {
            if (position == 1) {
                Glide.with(activity).load(R.mipmap.aaa).into(((ImagerViewHolder) holder).iv);
            } else {
                Glide.with(activity).load(R.mipmap.active_invite_banner).into(((ImagerViewHolder) holder).iv);
            }
            ((ImagerViewHolder) holder).head.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.startActivity(new Intent(activity, PersonelInfoActivity.class));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick((Integer) v.getTag());
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView head;

        public ChatViewHolder(View itemView) {
            super(itemView);
            head = itemView.findViewById(R.id.head);
        }
    }

    public class ImagerViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv;
        private CircleImageView head;

        public ImagerViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv_a);
            head = itemView.findViewById(R.id.head);
        }
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView head;

        public MessageViewHolder(View itemView) {
            super(itemView);
            head = itemView.findViewById(R.id.head);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
