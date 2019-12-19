package com.example.q_kang.pmsystem.ui.activity.event;

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
import com.example.q_kang.pmsystem.modules.bean.bean.work.WorkData;
import com.example.q_kang.pmsystem.present.im.ShowWorkListPresenter;
import com.example.q_kang.pmsystem.ui.adpter.event.ChooseWorkAdapter;
import com.example.q_kang.pmsystem.view.WorkListView;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChooseBelongWorkActivity extends BaseActivity implements WorkListView {

    @BindView(R.id.iv_choose_work_back)
    ImageView iv_back;
    @BindView(R.id.et_choose_work_search)
    EditText editText;
    @BindView(R.id.fab_choose_work)
    FloatingActionButton fab_choose;
    @BindView(R.id.rv_choose_work)
    RecyclerView rv_choose;

    private ChooseWorkAdapter chooseWorkAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private List<ChooseData> mList = new ArrayList<>();

    private List<ChooseData> chooseDataList = new ArrayList<>();
    private List<ChooseData> allDatalist = new ArrayList<>();

    private int flag;
    private ShowWorkListPresenter workPresenter;
    private int page = 1;


    @Override
    public int bindLayout() {
        return R.layout.activity_choose_belong_work;
    }

    @Override
    public void doBusiness(Context mContext) {
        chooseDataList = new ArrayList<>();

        String role = AdminDao.getUser().getRole();
        if (Globle.ROLE_FZ.equals(role) || Globle.ROLE_ZJL.equals(role) || Globle.ROLE_DUCHA.equals(role)) {
            flag = 1;
        } else if (Globle.ROLE_BMJL.equals(role)) {
            flag = 0;
        } else {
            flag = 2;
        }
        workPresenter = new ShowWorkListPresenter(this);
        Log.i(TAG, "doBusiness: " + flag);
        workPresenter.getWorkList(page, 30, flag, "", AdminDao.getUserID(), "","","");


        mLinearLayoutManager = new LinearLayoutManager(this);
        rv_choose.setLayoutManager(mLinearLayoutManager);

        WorkData.DataBean first = new WorkData.DataBean();
        first.setName("独立事件");
        first.setStartTime("");
        ChooseData chooseData1 = new ChooseData();
        chooseData1.setBelongWork(first);
        allDatalist.add(chooseData1);

        mList = allDatalist;
        chooseWorkAdapter = new ChooseWorkAdapter(ChooseBelongWorkActivity.this, mList);
        rv_choose.setAdapter(chooseWorkAdapter);


        chooseWorkAdapter.setOnItemClickListener(new ChooseWorkAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, List<ChooseData> datas) {
                for (ChooseData data : datas) {
                    data.setSelect(false);
                }
                ChooseData chooseData = datas.get(position);
                chooseData.setSelect(true);
                fab_choose.setEnabled(true);
                chooseWorkAdapter.notifyDataSetChanged();

                chooseDataList.add(chooseData);
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
                    chooseWorkAdapter.notifyAdapter(mList, false);
                } else {
                    mList = new ArrayList<>();
                    String inputStr = editText.getText().toString();
                    for (int i = 0; i < allDatalist.size(); i++) {
                        if (allDatalist.get(i).getBelongWork().getName().contains(inputStr)) {
                            mList.add(allDatalist.get(i));
                        }
                    }
                    chooseWorkAdapter.notifyAdapter(mList, false);
                }
            }
        });

    }

    @OnClick({R.id.fab_choose_work, R.id.iv_choose_work_back})
    public void onViewClick(View view) {
        switch (view.getId()) {

            case R.id.fab_choose_work:
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("choose", (ArrayList<? extends Parcelable>) chooseDataList);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.iv_choose_work_back:
                finish();
                break;
        }
    }


    @Override
    public void showWorkList(WorkData workData) {
        Log.i(TAG, "showWorkList: " + workData);
        List<WorkData.DataBean> data = workData.getData();


        for (int i = 0; i < data.size(); i++) {
            ChooseData chooseData = new ChooseData();
            WorkData.DataBean dataBean = data.get(i);
            chooseData.setBelongWork(dataBean);
            allDatalist.add(chooseData);
        }

        mList = allDatalist;
        chooseWorkAdapter.notifyDataSetChanged();

    }

    @Override
    public void showWorkSearchUser(List<Frame> frameList) {

    }

    @Override
    public void showWorkDepartment(String string) {

    }

    @Override
    public void endRefresh() {

    }

    @Override
    public void endLoadMore() {

    }
}
