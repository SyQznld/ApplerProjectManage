package com.example.q_kang.pmsystem.ui.adpter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.modules.bean.bean.NewsData;
import com.example.q_kang.pmsystem.ui.activity.news.NewsDetailActivity;

import java.util.List;


public class NewsListAdapter extends BaseQuickAdapter<NewsData.DataBean, BaseViewHolder> {
    private Context context;
    private List<NewsData.DataBean> data;


    public NewsListAdapter(Context context, @Nullable List<NewsData.DataBean> data) {
        super(R.layout.news_list_layout, data);
        this.context = context;
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsData.DataBean item) {
        helper.setText(R.id.tv_news_title, item.getTitle());
        ImageView civ_classify = helper.getView(R.id.civ_news_image);
        TextView tv_classify = helper.getView(R.id.tv_news_classify);
        int flag = item.getFlag();
        if (flag == 0) {
            tv_classify.setText("新闻");
            civ_classify.setImageResource(R.drawable.ic_news_news);
        } else {
            tv_classify.setText("公告");
            civ_classify.setImageResource(R.drawable.ic_news_notice);
        }
        String addTime = item.getAddTime();
        String substring = addTime.substring(0, addTime.length() - 4);
        Log.i(TAG, "convert  substring: " + substring);
        String t = substring.replace("T", " ");
        Log.i(TAG, "convert t: " + t);
        helper.setText(R.id.tv_news_time, t);

        helper.getView(R.id.ll_news).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra("news",item);
                context.startActivity(intent);
            }
        });


    }
}
