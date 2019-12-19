package com.example.q_kang.pmsystem.ui.activity.frame;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.BaseActivity;
import com.example.q_kang.pmsystem.ui.adpter.GroupFrameAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class FrameFragmentActivity extends BaseActivity implements View.OnClickListener {

    private List<String> datas = new ArrayList<>();
    private GroupFrameAdapter groupFrameAdapter;
    @BindView(R.id.tv_frag_title)
    TextView tv_frag_title;
    @BindView(R.id.tv_frag_search)
    TextView tv_frag_search;
    @BindView(R.id.iv_frag_back)
    ImageView iv_frag_back;
    @BindView(R.id.iv_frag_search_back)
    ImageView iv_frag_search_back;
    @BindView(R.id.ll_frag_search)
    LinearLayout ll_frag_search;
    @BindView(R.id.et_frag_search)
    EditText et_frag_search;
    @BindView(R.id.recycler_group)
    RecyclerView recycler_group;

    @Override
    public int bindLayout() {
        return R.layout.activity_frame_fragment;
    }

    @Override
    public void doBusiness(Context mContext) {
        initFragAndTitle();
        recycler_group.setHasFixedSize(true);
        recycler_group.setNestedScrollingEnabled(false);
        recycler_group.setLayoutManager(new LinearLayoutManager(this));
        recycler_group.setAdapter(groupFrameAdapter);
        setOnClick();
    }

    private void initFragAndTitle() {
        tv_frag_search.setText("搜索组织架构");
        datas = Arrays.asList(getResources().getStringArray(R.array.groups));
        groupFrameAdapter = new GroupFrameAdapter(this, datas);
        groupFrameAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    private void setOnClick() {
        iv_frag_back.setOnClickListener(this);
        tv_frag_search.setOnClickListener(this);
        iv_frag_search_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_frag_back:
                finish();
                break;
            case R.id.tv_frag_search:
                ll_frag_search.setVisibility(View.VISIBLE);
                tv_frag_search.setVisibility(View.GONE);
                break;
            case R.id.iv_frag_search_back:
                ll_frag_search.setVisibility(View.GONE);
                tv_frag_search.setVisibility(View.VISIBLE);
                break;
        }
    }
}
