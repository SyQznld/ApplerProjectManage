package com.example.q_kang.pmsystem.ui.activity.news;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.BaseActivity;
import com.example.q_kang.pmsystem.modules.bean.bean.NewsData;
import com.example.q_kang.pmsystem.present.im.NewsListPresenter;
import com.example.q_kang.pmsystem.ui.adpter.NewsListAdapter;
import com.example.q_kang.pmsystem.ui.view.Utils.CommonUtils;
import com.example.q_kang.pmsystem.view.NewsListView;
import com.fingdo.statelayout.StateLayout;
import com.jimmy.common.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class NewsActivity extends BaseActivity implements NewsListView {

    @BindView(R.id.ib_news_back)
    ImageButton ibBack;
    @BindView(R.id.rv_news)
    RecyclerView rvNews;
    @BindView(R.id.srl_news)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.iv_news_list_search)
    ImageView iv_search;
    @BindView(R.id.et_news_search)
    EditText et_search;
    @BindView(R.id.tv_news_search)
    TextView tv_search;
    @BindView(R.id.rl_news_search)
    RelativeLayout rl_search;
    @BindView(R.id.sl_news)
    StateLayout sl_news;


    private NewsListPresenter newsListPresenter;
    private int page = 1;
    private List<NewsData.DataBean> newsDataList = new ArrayList<>();
    private NewsListAdapter newsAdapter;
    private int count;


    @Override
    public int bindLayout() {
        return R.layout.activity_news;
    }



    @Override
    public void doBusiness(Context context) {
        initState();

        newsListPresenter = new NewsListPresenter(this);
//        newsListPresenter.getNewsList(page, Globle.PAGE_LIMIT, "");

        rvNews.setLayoutManager(new LinearLayoutManager(context));
        newsAdapter = new NewsListAdapter(context,newsDataList);
        rvNews.setAdapter(newsAdapter);


        initRefreahAndLoad();

    }


    private void initState() {

        if (!CommonUtils.isNetConnected(NewsActivity.this)) {
            sl_news.showNoNetworkView(R.string.no_net, R.drawable.ic_no_net);
            sl_news.setRefreshListener(new StateLayout.OnViewRefreshListener() {
                @Override
                public void refreshClick() {
                    if (!CommonUtils.isNetConnected(NewsActivity.this)) {
                        ToastUtils.showToast(NewsActivity.this, "请确保网络连接正常~");
                    } else {
                        newsDataList.clear();
                        smartRefreshLayout.setEnableLoadMore(true);
                        page = 1;
                        newsListPresenter.getNewsList(page, Globle.PAGE_LIMIT, "");
                    }
                }

                @Override
                public void loginClick() {

                }
            });
        } else {
            sl_news.showContentView();
        }
    }

    private void initRefreahAndLoad() {
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initState();
//                newsDataList.clear();
//                smartRefreshLayout.setEnableLoadMore(true);
//                page = 1;
//                newsListPresenter.getNewsList(page, Globle.PAGE_LIMIT, "");
                refreshLayout.finishRefresh();
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (newsDataList.size() < count) {
                    newsListPresenter.getNewsList(++page, Globle.PAGE_LIMIT, "");
                } else if (newsDataList.size() == count) {
                    refreshLayout.setEnableLoadMore(false);
                }
                refreshLayout.finishLoadMore();
            }
        });
    }



    @OnClick({R.id.ib_news_back,R.id.tv_news_search,R.id.iv_news_list_search})
    public void OnViewClick(View view) {
        switch (view.getId()){
            case R.id.ib_news_back:
                if (et_search.getVisibility() == View.VISIBLE) {
                    et_search.setVisibility(View.GONE);
                    tv_search.setVisibility(View.GONE);
                    iv_search.setVisibility(View.VISIBLE);
                    newsDataList.clear();
                    newsListPresenter.getNewsList(page, Globle.PAGE_LIMIT, "");
                } else {
                    finish();
                }
                break;
            case R.id.iv_news_list_search:
                et_search.setText("");
                et_search.setTextColor(Color.parseColor("#616161"));
                YoYo.with(Techniques.SlideInRight)
                        .duration(200)
                        .playOn(rl_search);
                et_search.setVisibility(View.VISIBLE);
                tv_search.setVisibility(View.VISIBLE);
                iv_search.setVisibility(View.GONE);
                break;
            case R.id.tv_news_search:
                newsDataList.clear();
                newsListPresenter.getNewsList(page, Globle.PAGE_LIMIT, et_search.getText().toString());
                break;

        }
    }

    @Override
    public void showNewsList(NewsData newsData) {
        //flag:    0 新闻    1 公告
        Log.i(TAG, "showNewsList: " + newsData);
        count = newsData.getCount();

        if (count == 0 || "".equals(newsData)){
            sl_news.showEmptyView("",R.drawable.ic_no_data);
        }else {
            sl_news.showContentView();
            List<NewsData.DataBean> data = newsData.getData();
            newsDataList.addAll(data);
            if (newsDataList.size() == count  && page > 1) {
                smartRefreshLayout.setEnableLoadMore(false);
                View footerView = getFooterView();
                newsAdapter.addFooterView(footerView);

            }
            newsAdapter.notifyDataSetChanged();
        }

    }
    private View getFooterView() {
        View view = getLayoutInflater().inflate(R.layout.footer_layout, (ViewGroup) rvNews.getParent(), false);
        return view;
    }




    @Override
    public void setState(int state) {
        super.setState(state);
        switch (state) {
            case Globle.LOADING_FAIL:


                smartRefreshLayout.setEnableRefresh(false);
                if (!CommonUtils.isNetConnected(NewsActivity.this)) {
                    sl_news.showNoNetworkView(R.string.no_net, R.drawable.ic_no_net);
                    sl_news.setRefreshListener(new StateLayout.OnViewRefreshListener() {
                        @Override
                        public void refreshClick() {
                            if (!CommonUtils.isNetConnected(NewsActivity.this)) {
                                ToastUtils.showToast(NewsActivity.this, "请确保网络连接正常~");
                            } else {

                                sl_news.showTimeoutView("", R.drawable.ic_time_out);
                                sl_news.setRefreshListener(new StateLayout.OnViewRefreshListener() {
                                    @Override
                                    public void refreshClick() {
                                        if (state == Globle.LOADING_FAIL) {
                                            smartRefreshLayout.setEnableRefresh(false);
                                            sl_news.showTimeoutView("", R.drawable.ic_time_out);
                                            ToastUtils.showToast(NewsActivity.this, "请求超时，请稍后再试~");
                                        } else if (state == Globle.LOADING_SUCEESS) {
                                            sl_news.showContentView();
                                        } else {
                                            initState();
                                        }
                                    }

                                    @Override
                                    public void loginClick() {

                                    }
                                });
                            }
                        }

                        @Override
                        public void loginClick() {

                        }
                    });
                }
                break;
        }
    }




    @Override
    protected void onResume() {
        super.onResume();

        newsDataList.clear();
        smartRefreshLayout.setEnableLoadMore(true);
        page = 1;
        newsListPresenter.getNewsList(page, Globle.PAGE_LIMIT, "");
    }
}
