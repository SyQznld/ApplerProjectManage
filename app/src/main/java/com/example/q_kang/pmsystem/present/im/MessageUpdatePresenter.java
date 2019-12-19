package com.example.q_kang.pmsystem.present.im;

import com.example.q_kang.pmsystem.model.IUpdateMessageModel;
import com.example.q_kang.pmsystem.model.im.MessageUpdateModel;
import com.example.q_kang.pmsystem.present.BaseMvpPresenter;
import com.example.q_kang.pmsystem.view.UpdateMessageView;

/**
 * Created by appler on 2018/8/17.
 */

public class MessageUpdatePresenter extends BaseMvpPresenter {
    private String TAG = "MessageUpdatePresenter";
    private MessageUpdateModel updateModel;
    private UpdateMessageView updateView;


    public MessageUpdatePresenter(UpdateMessageView updateView) {
        this.updateView = updateView;
        updateModel = new MessageUpdateModel(new IUpdateMessageModel() {
            @Override
            public void updateMessage(String string) {
                updateView.updateMessage(string);
            }

            @Override
            public void deleteMessage(String string) {
                updateView.deleteMessage(string);
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


    public void updateMessageState(String id) {
        updateModel.updateMessageState(mCompositeSubscription, id);
    }

    public void deleteMessage(String userId,String type) {
        updateModel.deleteMessage(mCompositeSubscription, userId,type);
    }


}
