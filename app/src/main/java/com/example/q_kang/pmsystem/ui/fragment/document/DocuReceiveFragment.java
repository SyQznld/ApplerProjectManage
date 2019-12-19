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


public class DocuReceiveFragment extends BaseFragment implements DocumentListView{


    @BindView(R.id._rv_receive_doc)
    RecyclerView rv_receive;
    @BindView(R.id.sl_receive)
    StateLayout sl_receive;

    private ShowDocuListPresenter docuListPresenter;
    private DocuListAdapter docuListAdapter;
    private List<DocumentData.DataBean> documentDataList = new ArrayList<>();
    private String userId;

    @Override
    public int setFragView() {
        return R.layout.fragment_docu_receive;
    }

    @Override
    protected void initFragView() {

    }

    @Override
    protected void doFragBusiness() {


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rv_receive.setLayoutManager(linearLayoutManager);

        docuListPresenter = new ShowDocuListPresenter(this);
        userId = AdminDao.getUserID();
//        docuListPresenter.getReceiveDocumentsList(userId);

        docuListAdapter = new DocuListAdapter(getActivity(),documentDataList,1);
        rv_receive.setAdapter(docuListAdapter);
    }


    @Override
    public void showReceiveDocList(String string) {
        Log.i("", "showReceiveDocList: " + string);
        if ("".equals(string)) {
            sl_receive.showEmptyView("", R.drawable.ic_no_data);
        } else {
            sl_receive.showContentView();
            documentDataList.clear();
            DocumentData documentData = new Gson().fromJson(string, new TypeToken<DocumentData>() {
            }.getType());
            List<DocumentData.DataBean> data = documentData.getData();
            documentDataList.addAll(data);
            docuListAdapter.notifyDataSetChanged();


        }
    }

    @Override
    public void showSendDocList(String string) {

    }

    @Override
    public void onResume() {
        super.onResume();
        docuListPresenter.getReceiveDocumentsList(userId);
    }
}
