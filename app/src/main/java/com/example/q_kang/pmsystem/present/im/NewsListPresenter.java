package com.example.q_kang.pmsystem.present.im;

import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.model.INewsListModel;
import com.example.q_kang.pmsystem.model.im.NewsListModel;
import com.example.q_kang.pmsystem.modules.bean.bean.NewsData;
import com.example.q_kang.pmsystem.present.BaseMvpPresenter;
import com.example.q_kang.pmsystem.view.NewsListView;

/**
 * Created by appler on 2018/7/27.
 */

public class NewsListPresenter extends BaseMvpPresenter {

    private String TAG = "NewsListPresenter";

    private NewsListModel newsListModel;

    private NewsListView newsListView;

    public NewsListPresenter(NewsListView newsListView) {
        this.newsListView = newsListView;
        newsListModel = new NewsListModel(new INewsListModel() {
            @Override
            public void showNewsList(NewsData newsData) {
                newsListView.showNewsList(newsData);
            }

            @Override
            public void onStart() {
                newsListView.setState(Globle.LOADING_STATE);
            }

            @Override
            public void onError() {
                newsListView.setState(Globle.LOADING_FAIL);
            }

            @Override
            public void onComplete() {
                newsListView.setState(Globle.LOADING_SUCEESS);
            }

            @Override
            public void onNext(String str) {

            }
        });
    }


    public void getNewsList(int page, int limit, String keyword) {
        newsListModel.getNewsList(mCompositeSubscription, page, limit, keyword);
    }
}
