package com.example.q_kang.pmsystem.ui.view.Utils.view_;


import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.modules.bean.bean.MessageNotifyData;
import com.gongwen.marqueen.MarqueeFactory;

public class MessageNoticeMF extends MarqueeFactory<TextView, MessageNotifyData.DataBean> {
    private LayoutInflater inflater;

    public MessageNoticeMF(Context mContext) {
        super(mContext);
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public TextView generateMarqueeItemView(MessageNotifyData.DataBean data) {
        TextView mView = (TextView) inflater.inflate(R.layout.notice_item, null);
        int type = Integer.parseInt(data.get_Type());
        String titleType = "";
        if (type == 0) {
            titleType = "项目";
        } else if (type == 1) {
            titleType = "工作";
        } else if (type == 2) {
            titleType = "事件";
        } else if (type == 3) {
            titleType = "公文";
        } else if (type == 4) {
            titleType = "新闻";
        }
        String partA = data.getPartA();
        String partB = data.getPartB();
        String partC = data.getPartC();
        String s = titleType + ": " + partB + partC;
        mView.setText(s);
        return mView;
    }
}
