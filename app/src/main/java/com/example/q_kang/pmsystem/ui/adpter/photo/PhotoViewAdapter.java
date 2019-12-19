package com.example.q_kang.pmsystem.ui.adpter.photo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.modules.bean.bean.photo.ImageItemBean;

import java.util.List;



public class PhotoViewAdapter extends BaseQuickAdapter<List<ImageItemBean>, BaseViewHolder> {
    private Context context;
    private Boolean state;

    public PhotoViewAdapter(Context context, Boolean state, @Nullable List<List<ImageItemBean>> data) {
        super(R.layout.photo_view_item, data);
        this.context = context;
        this.state = state;
    }

    @Override
    protected void convert(BaseViewHolder helper, List<ImageItemBean> item) {
        helper.setText(R.id.tv_date, item.get(0).getTime());
        RecyclerView recyclerView = helper.getView(R.id.rv_photo_view);
        recyclerView.setLayoutManager(new GridLayoutManager(context,4));
        Log.i(TAG, "imageLists  item: " + item);
        recyclerView.setAdapter(new ImageViewAdapter(context, state, item));

    }
}