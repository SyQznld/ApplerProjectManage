package com.example.q_kang.pmsystem.ui.fragment.document;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.BaseFragment;
import com.example.q_kang.pmsystem.dao.AdminDao;
import com.example.q_kang.pmsystem.modules.bean.bean.DocumentData;
import com.example.q_kang.pmsystem.present.im.ShowDocuListPresenter;
import com.example.q_kang.pmsystem.ui.adpter.DocuListAdapter;
import com.example.q_kang.pmsystem.view.DocumentListView;
import com.fingdo.statelayout.StateLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class DocuSendFragment extends BaseFragment implements DocumentListView {

    @BindView(R.id._rv_send_doc)
    RecyclerView rv_docu;
    @BindView(R.id.sl_send)
    StateLayout sl_send;

    private ShowDocuListPresenter docuListPresenter;
    private DocuListAdapter docuListAdapter;
    private List<DocumentData.DataBean> documentDataList = new ArrayList<>();
    private String userId;


    @Override
    public int setFragView() {
        return R.layout.fragment_docu_send;
    }

    @Override
    protected void initFragView() {

    }

    @Override
    protected void doFragBusiness() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rv_docu.setLayoutManager(linearLayoutManager);

        docuListPresenter = new ShowDocuListPresenter(this);
        userId = AdminDao.getUserID();
//        docuListPresenter.getSendDocumentsList(userId,true);

        docuListAdapter = new DocuListAdapter(getActivity(), documentDataList, 2);
        rv_docu.setAdapter(docuListAdapter);

    }


    @Override
    public void showReceiveDocList(String string) {

    }

    @Override
    public void showSendDocList(String string) {
        Log.i("", "showSendDocList: " + string);
        if ("".equals(string)) {
            sl_send.showEmptyView("", R.drawable.ic_no_data);
        } else {
            sl_send.showContentView();

            documentDataList.clear();
            DocumentData documentData = new Gson().fromJson(string, new TypeToken<DocumentData>() {
            }.getType());
            List<DocumentData.DataBean> data = documentData.getData();
            documentDataList.addAll(data);
            Log.i("", "showDocument  11: " + documentDataList.size());
            Log.i("", "showDocument 22: " + documentDataList);
            docuListAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        docuListPresenter.getSendDocumentsList(userId, true);
    }
}
