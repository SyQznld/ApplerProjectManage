package com.example.q_kang.pmsystem.ui.adpter.event;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.ui.view.Utils.Utils.GlideHelper;

import java.util.List;

public class EveGatherPhotoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public EveGatherPhotoAdapter(@Nullable List<String> data) {
        super(R.layout.item_photo_gather, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView imageView = helper.getView(R.id.iv_item_photo);
        if ("".equals(item)) {
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            imageView.setImageResource(R.drawable.ic_add_gray);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onGatherClick.toGatherPhoto();
                }
            });
        } else {
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Log.i(TAG, "EveGatherPhotoAdapter convert==========: " + item);
            GlideHelper.loadNet(imageView,item,R.mipmap.logo);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onGatherClick.toShowPhoto(helper.getLayoutPosition());
                }
            });

            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onGatherClick.deletePhoto(helper.getLayoutPosition());
                    return false;
                }
            });
        }
    }

    private OnGatherClick onGatherClick;

    public void setOnGatherClick(OnGatherClick onGatherClick) {
        this.onGatherClick = onGatherClick;
    }

    public interface OnGatherClick {
        void toGatherPhoto();

        void toShowPhoto(int position);

        void deletePhoto(int position);
    }


}
