package com.example.q_kang.pmsystem.present.im;

import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.model.IDocumentListModel;
import com.example.q_kang.pmsystem.model.im.ShowDocuListModel;
import com.example.q_kang.pmsystem.present.BaseMvpPresenter;
import com.example.q_kang.pmsystem.view.DocumentListView;

/**
 * Created by appler on 2018/8/10.
 */

public class ShowDocuListPresenter extends BaseMvpPresenter {

    private String TAG = "ShowDocuListPresenter";

    private ShowDocuListModel docuListModel;

    private DocumentListView documentListView;

    public ShowDocuListPresenter(DocumentListView documentListView) {
        this.documentListView = documentListView;
        docuListModel = new ShowDocuListModel(new IDocumentListModel() {
            @Override
            public void showReceiveDocList(String string) {
                documentListView.showReceiveDocList(string);
            }

            @Override
            public void showSendDocList(String string) {
                documentListView.showSendDocList(string);
            }



            @Override
            public void onStart() {
                documentListView.setState(Globle.LOADING_STATE);
            }

            @Override
            public void onError() {
                documentListView.setState(Globle.LOADING_FAIL);
            }

            @Override
            public void onComplete() {
                documentListView.setState(Globle.LOADING_SUCEESS);
            }

            @Override
            public void onNext(String str) {

            }
        });
    }


    public void getReceiveDocumentsList(String userId) {
        docuListModel.getReceiveDocumentsList(mCompositeSubscription, userId);
    }

    public void getSendDocumentsList(String userId,boolean IsSend) {
        docuListModel.getSendDocumentsList(mCompositeSubscription, userId,IsSend);
    }
}
