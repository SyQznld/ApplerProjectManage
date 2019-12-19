package com.example.q_kang.pmsystem.ui.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.BaseFragment;
import com.example.q_kang.pmsystem.modules.bean.bean.Frame;
import com.example.q_kang.pmsystem.present.im.FramePresent;
import com.example.q_kang.pmsystem.ui.activity.SearchActivity;
import com.example.q_kang.pmsystem.ui.activity.frame.FrameFragmentActivity;
import com.example.q_kang.pmsystem.ui.activity.frame.PhoneMemberActivity;
import com.example.q_kang.pmsystem.ui.adpter.PeopleAdapter;
import com.example.q_kang.pmsystem.view.FrameView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class FrameFragment extends BaseFragment implements View.OnClickListener, FrameView {
    private static final String TAG = "FrameFragment";
    private PeopleAdapter peopleAdapter;
    private List<String> peoples;
    @BindView(R.id.recycler_all)
    RecyclerView recycler_all;
    @BindView(R.id.iv_frame_search)
    ImageView iv_frame_search;
    @BindView(R.id.tv_frame_phone)
    TextView tv_frame_phone;
    @BindView(R.id.tv_frame_organization)
    TextView tv_frame_organization;
    @BindView(R.id.tv_frame_friends)
    TextView tv_frame_friends;
    @BindView(R.id.tv_frame_group)
    TextView tv_frame_group;
    @BindView(R.id.srl_frame_list)
    SmartRefreshLayout refreshLayout;

    private FramePresent framePresent;


    @Override
    public int setFragView() {
        return R.layout.fragment_frame;
    }

    @Override
    protected void initFragView() {
        peoples = Arrays.asList(getResources().getStringArray(R.array.provinces));

//        peopleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Intent intent = new Intent(getActivity(), PersonelInfoActivity.class);
//                intent.putExtra("name", frames.get(position));
//                startActivity(intent);
//            }
//        });

    }

    @Override
    protected void doFragBusiness() {
        framePresent = new FramePresent(this);
        framePresent.getFrames();
        iv_frame_search.setOnClickListener(this);
        tv_frame_phone.setOnClickListener(this);
        tv_frame_organization.setOnClickListener(this);
        tv_frame_friends.setOnClickListener(this);
        tv_frame_group.setOnClickListener(this);


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                framePresent.getFrames();
                refreshLayout.finishRefresh();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_frame_search:
                Intent searchIntent = new Intent(getActivity(), SearchActivity.class);
                startActivity(searchIntent);
                break;
            case R.id.tv_frame_phone:
                Intent phoneIntent = new Intent(getActivity(), PhoneMemberActivity.class);
                startActivity(phoneIntent);
                break;
            case R.id.tv_frame_organization:
                Intent organizationIntent = new Intent(getActivity(), FrameFragmentActivity.class);
                startActivity(organizationIntent);
                break;
            case R.id.tv_frame_friends:
                break;
            case R.id.tv_frame_group:
                break;
        }
    }

    @Override
    public void showAdapter(List<Frame> datas) {
        peopleAdapter = new PeopleAdapter(activity, datas);
        peopleAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        peopleAdapter.setNotDoAnimationCount(4);
        recycler_all.setHasFixedSize(true);
        recycler_all.setNestedScrollingEnabled(false);
        recycler_all.setLayoutManager(new LinearLayoutManager(activity));
        recycler_all.setAdapter(peopleAdapter);
    }

}
