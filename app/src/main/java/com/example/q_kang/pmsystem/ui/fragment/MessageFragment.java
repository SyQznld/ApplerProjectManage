package com.example.q_kang.pmsystem.ui.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.BaseFragment;
import com.example.q_kang.pmsystem.modules.bean.bean.MessageData;
import com.example.q_kang.pmsystem.ui.activity.ChatActivity;
import com.example.q_kang.pmsystem.ui.adpter.MessageAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

public class MessageFragment extends BaseFragment {

    @BindView(R.id.re_message)
    RecyclerView reMessage;
    Unbinder unbinder;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private Drawable mDrawableProgress;
    private ClassicsHeader mClassicsHeader;


    @Override
    public int setFragView() {
        return R.layout.fragment_message;
    }

    @Override
    protected void doFragBusiness() {


        reMessage.setLayoutManager(new LinearLayoutManager(activity));

        List<MessageData> data = new ArrayList<>();
        data.add(new MessageData("项目管理" + 1, 1));
        data.add(new MessageData("项目管理" + 2, 2));
        data.add(new MessageData("项目管理" + 3, 3));
        for (int i = 0; i < 10; i++) {
            data.add(new MessageData("项目管理" + i, 0));
        }
        MessageAdapter messageAdapter = new MessageAdapter(activity, data);
        reMessage.setAdapter(messageAdapter);


        refreshLayout.setPrimaryColorsId(R.color.blue, android.R.color.white);

        getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });


//        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(RefreshLayout refreshlayout) {
//                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
//            }
//        });
        refreshLayout.setEnableLoadMore(false);
        messageAdapter.setItemClickListener(new MessageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(new Intent(activity, ChatActivity.class));
            }
        });

    }

    @Override
    protected void initFragView() {

    }

}
