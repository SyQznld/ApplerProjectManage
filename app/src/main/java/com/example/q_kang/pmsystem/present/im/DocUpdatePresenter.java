package com.example.q_kang.pmsystem.present.im;

import com.example.q_kang.pmsystem.model.IDocUpdateModel;
import com.example.q_kang.pmsystem.model.im.DocUpdateModel;
import com.example.q_kang.pmsystem.present.BaseMvpPresenter;
import com.example.q_kang.pmsystem.view.DocUpdateView;

/**
 * Created by appler on 2018/8/17.
 */

public class DocUpdatePresenter extends BaseMvpPresenter {
    private String TAG = "DocUpdatePresenter";
    private DocUpdateModel updateModel;
    private DocUpdateView updateView;


    public DocUpdatePresenter(DocUpdateView updateView) {
        this.updateView = updateView;
        updateModel = new DocUpdateModel(new IDocUpdateModel() {
            @Override
            public void updateDocState(String string) {
                updateView.updateDocState(string);
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


    public void updateDocState(String id, boolean isRead) {
        updateModel.updateDocState(mCompositeSubscription, id, isRead);
    }


}
