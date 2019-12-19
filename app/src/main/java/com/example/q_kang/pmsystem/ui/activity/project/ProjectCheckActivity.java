package com.example.q_kang.pmsystem.ui.activity.project;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.BaseActivity;
import com.example.q_kang.pmsystem.ui.view.Utils.SmoothListView.SmoothListView;
import com.example.q_kang.pmsystem.ui.view.Utils.TitleView;
import com.example.q_kang.pmsystem.ui.view.Utils.Utils.ColorUtil;
import com.example.q_kang.pmsystem.ui.view.Utils.Utils.DensityUtil;
import com.example.q_kang.pmsystem.ui.view.Utils.WorkListView;
import com.example.q_kang.pmsystem.ui.view.Utils.WorkOfProjectView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class ProjectCheckActivity extends BaseActivity implements SmoothListView.ISmoothListViewListener {

    @BindView(R.id.listView)
    SmoothListView listView;
    @BindView(R.id.rl_bar)
    RelativeLayout rl_bar;
    @BindView(R.id.tv_title_bg)
    TextView tv_title_bg;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.real_workView)
    WorkOfProjectView real_workView;

    private TitleView titleView;
    private TitleView titleView1;
    private WorkListView workListView;
    private MyListAdapter myListAdapter;
    private int workViewPosition; // 筛选视图的位置
    private int titleViewHeight = 65; // 标题栏的高度
    private int FirstHeaderViewTopMargin; // 广告视图距离顶部的距离

    private View itemFirstHeaderView; // 从ListView获取的广告子View
    private int FirstHeaderHeight = 180; // 广告视图的高度
    private View itemHeaderWorkView; // 从ListView获取的筛选子View
    private int workViewTopMargin; // 工作视图距离顶部的距离
    private Context mContext;
    private boolean isScrollIdle = true; // ListView是否在滑动
    private boolean isStickyTop = false; // 是否吸附在顶部

    @Override
    public int bindLayout() {
        return R.layout.activity_project_check;
    }

    @Override
    public void doBusiness(Context mContext) {
        mContext = this;
        titleView = new TitleView(mContext);
        titleView.getView(listView);
        workListView = new WorkListView(mContext);
        workListView.getView(listView);
        myListAdapter = new MyListAdapter(Arrays.asList(getResources().getStringArray(R.array.groupsMember)));
        listView.setAdapter(myListAdapter);

        workViewPosition = listView.getHeaderViewsCount() - 1;

        setOnClick();

    }

    private void setOnClick() {
        workListView.setOnWorkViewClick(new WorkListView.OnWorkViewClick() {
            @Override
            public void onWorkViewClick(int position) {
                listView.smoothScrollToPositionFromTop(workViewPosition, DensityUtil.dip2px(ProjectCheckActivity.this, titleViewHeight));
            }
        });

        listView.setRefreshEnable(true);
        listView.setLoadMoreEnable(true);
        listView.setSmoothListViewListener(this);
        listView.setOnScrollListener(new SmoothListView.OnSmoothScrollListener() {
            @Override
            public void onSmoothScrolling(View view) {

            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                isScrollIdle = (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (isScrollIdle && FirstHeaderViewTopMargin < 0) return;

                if (itemFirstHeaderView == null) {
                    itemFirstHeaderView = listView.getChildAt(1);
                }
                if (itemFirstHeaderView != null) {
                    FirstHeaderViewTopMargin = DensityUtil.px2dip(ProjectCheckActivity.this, itemFirstHeaderView.getTop());
                    FirstHeaderHeight = DensityUtil.px2dip(ProjectCheckActivity.this, itemFirstHeaderView.getHeight());
                }

                // 获取筛选View、距离顶部的高度
                if (itemHeaderWorkView == null) {
                    itemHeaderWorkView = listView.getChildAt(workViewPosition - firstVisibleItem);
                }
                if (workListView != null) {
                    workViewTopMargin = DensityUtil.px2dip(ProjectCheckActivity.this, itemHeaderWorkView.getTop());
                }
                // 处理筛选是否吸附在顶部
                if (workViewTopMargin <= titleViewHeight || firstVisibleItem > workViewTopMargin) {
                    isStickyTop = true; // 吸附在顶部
                    real_workView.setVisibility(View.VISIBLE);
                } else {
                    isStickyTop = false; // 没有吸附在顶部
                    real_workView.setVisibility(View.GONE);
                }

                handleTitleBarColorEvaluate();

            }
        });
    }

    // 处理标题栏颜色渐变
    private void handleTitleBarColorEvaluate() {
        float fraction = 0;
        if (FirstHeaderViewTopMargin > 0) {
            fraction = 1f - FirstHeaderViewTopMargin * 1f / 60;
            if (fraction < 0f) fraction = 0f;
            rl_bar.setAlpha(fraction);
            return;
        }
        Log.i("ABC", "FirstHeaderViewTopMargin: " + FirstHeaderViewTopMargin);

        float space = Math.abs(FirstHeaderViewTopMargin) * 1f;
        fraction = space / (FirstHeaderHeight - titleViewHeight);
        Log.i("ABC", "fraction: " + String.valueOf(fraction));
        if (fraction < 0f) fraction = 0f;
        if (fraction > 1f) fraction = 1f;
        rl_bar.setAlpha(1f);

        if (fraction >= 1f || isStickyTop) {
            isStickyTop = true;
            tv_title_bg.setAlpha(0f);
//            viewActionMoreBg.setAlpha(0f);
            rl_bar.setBackgroundColor(ProjectCheckActivity.this.getResources().getColor(R.color.colorPrimary));
        } else {
            tv_title_bg.setAlpha(1f - fraction);
//            viewActionMoreBg.setAlpha(1f - fraction);
            rl_bar.setBackgroundColor(ColorUtil.getNewColorByStartEndColor(ProjectCheckActivity.this, fraction, R.color.transparent, R.color.colorPrimary));
        }
    }

    @Override
    public void onRefresh() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listView.stopRefresh();
                listView.setRefreshTime("刚刚");
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listView.stopLoadMore();
            }
        }, 2000);

    }

    private class MyListAdapter extends BaseAdapter {
        private List<String> datas;
        private LayoutInflater inflater;

        public MyListAdapter(List<String> datas) {
            this.datas = datas;

            inflater = LayoutInflater.from(ProjectCheckActivity.this);
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = inflater.inflate(R.layout.a_layout, parent, false);
            return view;
        }

    }

}
