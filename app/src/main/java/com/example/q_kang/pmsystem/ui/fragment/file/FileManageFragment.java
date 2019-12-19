package com.example.q_kang.pmsystem.ui.fragment.file;


import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.BaseFragment;
import com.example.q_kang.pmsystem.dao.FileDao;
import com.example.q_kang.pmsystem.modules.bean.bean.FileBean;
import com.example.q_kang.pmsystem.ui.adpter.FileListManageAdapter;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


@SuppressLint("ValidFragment")
public class FileManageFragment extends BaseFragment {

    private String TAG = "FileManageFragment";
    @BindView(R.id.tv_file_empty)
    TextView tv_empty;
    @BindView(R.id.rv_file_list)
    RecyclerView rv_file;
    @BindView(R.id.fab_file_manage_choose)
    FloatingActionButton fab_choose;
    @BindView(R.id.fab_file_manage_sure)
    FloatingActionButton fab_sure;

    private boolean choose = false;
    private FileListManageAdapter fileListManageAdapter;
    private List<FileBean> fileBeanList;


    @SuppressLint("ValidFragment")
    public FileManageFragment(boolean choose) {
        this.choose = choose;
    }

    @Override
    public int setFragView() {
        return R.layout.fragment_file_manage;
    }

    @Override
    protected void initFragView() {
        if (choose == true) {
            fab_choose.setVisibility(View.GONE);
            fab_sure.setVisibility(View.GONE);
            tv_empty.setText("暂无可供选择的文件资源");
        } else {
            fab_sure.setVisibility(View.GONE);
            fab_choose.setVisibility(View.GONE);
        }

    }

    @Override
    protected void doFragBusiness() {

        initFileData();

    }

    public void initFileData() {
        fileBeanList = new ArrayList<>();
        List<FileBean> fileDatas = FileDao.queryAll();

        for (int i = fileDatas.size() - 1; i >= 0; --i) {
            fileBeanList.add(fileDatas.get(i));
        }
        Log.i(TAG, "fileBeanList: " + fileBeanList.size() + fileBeanList);
        if (null != fileBeanList && !"[]".equals(fileBeanList) && fileBeanList.size() > 0) {

            tv_empty.setVisibility(View.GONE);
            rv_file.setVisibility(View.VISIBLE);

            rv_file.setLayoutManager(new LinearLayoutManager(getActivity()));
            fileListManageAdapter = new FileListManageAdapter(getActivity(), this.choose, fileBeanList);
            rv_file.setAdapter(fileListManageAdapter);
        } else {
            tv_empty.setVisibility(View.VISIBLE);
            rv_file.setVisibility(View.GONE);
        }
    }


    @OnClick({R.id.fab_file_manage_choose, R.id.fab_file_manage_sure})
    public void viewOnClick(View view) {
        switch (view.getId()) {

            case R.id.fab_file_manage_choose:
                break;
            case R.id.fab_file_manage_sure:
                List<FileBean> fileDatas = FileListManageAdapter.getFileDatas();
                Log.i(TAG, "viewOnClick: " + fileDatas);
                break;
        }
    }


}
