package com.example.q_kang.pmsystem.ui.view.Utils;


import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.q_kang.pmsystem.R;
import com.gongwen.marqueen.MarqueeFactory;

public class NoticeMF extends MarqueeFactory<TextView, String> {
    private LayoutInflater inflater;

    public NoticeMF(Context mContext) {
        super(mContext);
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public TextView generateMarqueeItemView(String data) {
        TextView mView = (TextView) inflater.inflate(R.layout.notice_item, null);
        mView.setText(data);
        return mView;
    }
}
