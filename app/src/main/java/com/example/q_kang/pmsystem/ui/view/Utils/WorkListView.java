package com.example.q_kang.pmsystem.ui.view.Utils;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.ui.activity.project.ProjectCheckActivity;
import com.example.q_kang.pmsystem.ui.adpter.MyListAdapter;
import com.google.gson.internal.bind.ArrayTypeAdapter;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkListView extends LinearLayout {
    @BindView(R.id.recy_project_work)
    RecyclerView recy_project_work;

    private MyListAdapter myListAdapter;
    private Context mContext;
    private LayoutInflater inflater;

    public WorkListView(Context context) {
        super(context);
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    public void getView(ListView listView) {
        View view = inflater.inflate(R.layout.view_project_work, this);
        ButterKnife.bind(this);
        myListAdapter = new MyListAdapter(Arrays.asList(getResources().getStringArray(R.array.projectFlow)));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recy_project_work.setLayoutManager(linearLayoutManager);
        recy_project_work.setAdapter(myListAdapter);
        listView.addHeaderView(view);

        myListAdapter.setOnWorkListClick(new MyListAdapter.OnWorkListClick() {
            @Override
            public void onWorkListClick(int position) {
                Toast.makeText(mContext, String.valueOf(position), Toast.LENGTH_SHORT).show();
                onWorkViewClick.onWorkViewClick(position);
            }
        });
    }

    private OnWorkViewClick onWorkViewClick;

    public void setOnWorkViewClick(OnWorkViewClick onWorkViewClick) {
        this.onWorkViewClick = onWorkViewClick;
    }

    public interface OnWorkViewClick {
        void onWorkViewClick(int position);
    }


}
