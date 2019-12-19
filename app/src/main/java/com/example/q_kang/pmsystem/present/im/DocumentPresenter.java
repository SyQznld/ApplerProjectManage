package com.example.q_kang.pmsystem.present.im;

import com.example.q_kang.pmsystem.model.IDocumentModel;
import com.example.q_kang.pmsystem.model.im.DocumentModel;
import com.example.q_kang.pmsystem.present.BaseMvpPresenter;
import com.example.q_kang.pmsystem.view.DocumentView;

/**
 * Created by appler on 2018/8/13.
 */

public class DocumentPresenter extends BaseMvpPresenter {
    private String TAG = "DocumentPresenter";
    private DocumentModel documentModel;
    private DocumentView documentView;

    public DocumentPresenter(DocumentView documentView) {
        this.documentView = documentView;

        documentModel = new DocumentModel(new IDocumentModel() {
            @Override
            public void createDocument(String s) {
                documentView.createDocument(s);
            }

            @Override
            public void editDocument(String string) {
                documentView.editDocument(string);
            }

            @Override
            public void getDocumentDetail(String string) {
                documentView.getDocumentDetail(string);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onError() {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onNext(String str) {

            }
        });
    }


    public void createDocument(String Name, String Content, String AssignPerson,
                               String Upload, String AddUserId, String LoginUserId,boolean isSend) {
        documentModel.createDocument(mCompositeSubscription,Name,Content,AssignPerson,Upload,AddUserId,LoginUserId,isSend);
    }

    public void editDocument(String Name, String Content, String AssignPerson,
                               String Upload, String AddUserId, String LoginUserId,boolean isSend) {
        documentModel.editDocument(mCompositeSubscription,Name,Content,AssignPerson,Upload,AddUserId,LoginUserId,isSend);
    }


    public void getDocumentDetail(String docId) {
        documentModel.getDocumentDatail(mCompositeSubscription,docId);
    }


}