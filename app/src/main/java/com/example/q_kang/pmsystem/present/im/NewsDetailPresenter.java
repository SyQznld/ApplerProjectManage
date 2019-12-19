package com.example.q_kang.pmsystem.present.im;

import com.example.q_kang.pmsystem.model.INewsDetailModel;
import com.example.q_kang.pmsystem.model.im.NewsDetailModel;
import com.example.q_kang.pmsystem.present.BaseMvpPresenter;
import com.example.q_kang.pmsystem.view.NewsDetailView;

/**
 * Created by appler on 2018/7/27.
 */

public class NewsDetailPresenter extends BaseMvpPresenter {

    private String TAG = "NewsDetailPresenter";

    private NewsDetailModel newsDetailModel;

    private NewsDetailView newsDetailView;

    public NewsDetailPresenter(NewsDetailView newsDetailView) {
        this.newsDetailView = newsDetailView;
        newsDetailModel = new NewsDetailModel(new INewsDetailModel() {
            @Override
            public void getNewsDetail(String string) {
                newsDetailView.getNewsDetail(string);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onError() {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onNext(String str) {

            }
        });
    }


    public void getNewsDetail(String newsId) {
        newsDetailModel.getNewsDetail(mCompositeSubscription, newsId);
    }
}
