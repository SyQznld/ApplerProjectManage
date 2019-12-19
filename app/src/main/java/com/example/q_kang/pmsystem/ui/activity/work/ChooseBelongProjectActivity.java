package com.example.q_kang.pmsystem.ui.activity.work;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.BaseActivity;
import com.example.q_kang.pmsystem.dao.AdminDao;
import com.example.q_kang.pmsystem.modules.bean.bean.ChooseData;
import com.example.q_kang.pmsystem.modules.bean.bean.Frame;
import com.example.q_kang.pmsystem.modules.bean.bean.project.ProjectData;
import com.example.q_kang.pmsystem.present.im.ShowProjectListPresenter;
import com.example.q_kang.pmsystem.ui.adpter.work.ChooseProjectAdapter;
import com.example.q_kang.pmsystem.view.ProjectListView;
import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChooseBelongProjectActivity extends BaseActivity implements ProjectListView {

    @BindView(R.id.iv_choose_project_back)
    ImageView iv_back;
    @BindView(R.id.et_choose_project_search)
    EditText editText;
    @BindView(R.id.fab_choose_project)
    FloatingActionButton fab_choose;
    @BindView(R.id.rv_choose_project)
    RecyclerView rv_choose;

    private ChooseProjectAdapter chooseProjectAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private List<ChooseData> mList = new ArrayList<>();

    private List<ChooseData> chooseDataList = new ArrayList<>();
    private List<ChooseData> allDatalist = new ArrayList<>();

    private ShowProjectListPresenter projectListPresenter;
    private int page = 1;
    private int flag = 1;
    private String userId, userRole, userName;

    @Override
    public int bindLayout() {
        return R.layout.activity_choose_belong_project;
    }

    @Override
    public void doBusiness(Context mContext) {

        userId = AdminDao.getUserID();
        userRole = AdminDao.getUser().getRole();
        userName = AdminDao.getUser().getRealName();

        chooseDataList = new ArrayList<>();

        projectListPresenter = new ShowProjectListPresenter(this);
        projectListPresenter.getProjectList(page, Globle.PAGE_LIMIT, flag, userId, "", userRole, "", "", "", userName);


        mLinearLayoutManager = new LinearLayoutManager(this);
        rv_choose.setLayoutManager(mLinearLayoutManager);

        // ProjectData.DataBean first = new ProjectData.DataBean("", "独立工作", "", "", "", "", "", "", "", "", "", "", false, "", "");
        ProjectData.DataBean first = new ProjectData.DataBean();
        first.setName("独立工作");
        ChooseData chooseData1 = new ChooseData();
        chooseData1.setBelongProject(first);
        allDatalist.add(chooseData1);

        mList = allDatalist;
        chooseProjectAdapter = new ChooseProjectAdapter(mContext, mList);
        rv_choose.setAdapter(chooseProjectAdapter);


        Log.i(TAG, "showProjectList: " + mList);
        chooseProjectAdapter.setOnItemClickListener(new ChooseProjectAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, List<ChooseData> datas) {
                for (ChooseData data : datas) {
                    data.setSelect(false);
                }
                ChooseData chooseData = datas.get(position);
                chooseData.setSelect(true);
                fab_choose.setEnabled(true);
                chooseProjectAdapter.notifyDataSetChanged();

                chooseDataList.add(chooseData);
                Log.i("fuzeren", "onItemClickListener: " + chooseDataList);
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String editable = s.toString();
                if ("".equals(editable) || null == editable) {
                    mList = new ArrayList<>();
                    mList = allDatalist;
                    chooseProjectAdapter.notifyAdapter(mList, false);
                } else {
                    mList = new ArrayList<>();
                    String inputStr = editText.getText().toString();
                    for (int i = 0; i < allDatalist.size(); i++) {
                        if (allDatalist.get(i).getBelongProject().getName().contains(inputStr)) {
                            mList.add(allDatalist.get(i));
                        }
                    }
                    chooseProjectAdapter.notifyAdapter(mList, false);
                }
            }
        });


    }


    @OnClick({R.id.fab_choose_project, R.id.iv_choose_project_back})
    public void onViewClick(View view) {
        switch (view.getId()) {

            case R.id.fab_choose_project:
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("choose", (ArrayList<? extends Parcelable>) chooseDataList);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.iv_choose_project_back:
                finish();
                break;
        }
    }


    @Override
    public void showProjectList(String s) {
        Log.i(TAG, "showProjectList: " + s);

        if (!"".equals(s)) {
            ProjectData projectData = new Gson().fromJson(s, new TypeToken<ProjectData>() {
            }.getType());
            List<ProjectData.DataBean> data = projectData.getData();

            for (int i = 0; i < data.size(); i++) {
                ChooseData chooseData = new ChooseData();
                ProjectData.DataBean dataBean = data.get(i);
                chooseData.setBelongProject(dataBean);
                allDatalist.add(chooseData);
            }

            mList = allDatalist;
            chooseProjectAdapter.notifyDataSetChanged();
        }


    }

    @Override
    public void showSearchUser(List<Frame> frameList) {

    }

    @Override
    public void showProDepartment(String string) {

    }

}
