package com.example.q_kang.pmsystem.present.im;

import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.model.IMessageListModel;
import com.example.q_kang.pmsystem.model.im.MessageListModel;
import com.example.q_kang.pmsystem.present.BaseMvpPresenter;
import com.example.q_kang.pmsystem.view.MessageListView;

/**
 * Created by appler on 2018/9/3.
 */

public class MessageListPresenter extends BaseMvpPresenter{

    private String TAG = "MessageListPresenter";
    private MessageListModel messageListModel;
    private MessageListView messageListView;


    public MessageListPresenter(MessageListView messageListView) {
        this.messageListView = messageListView;

        messageListModel = new MessageListModel(new IMessageListModel() {
            @Override
            public void getMessageList(String string) {
                messageListView.getMessageList(string);
            }

            @Override
            public void deleteAllMessage(String string) {
                messageListView.deleteAllMessage(string);
            }

            @Override
            public void onStart() {
                messageListView.setState(Globle.LOADING_STATE);

            }

            @Override
            public void onError() {
                messageListView.setState(Globle.LOADING_FAIL);
            }

            @Override
            public void onComplete() {
                messageListView.setState(Globle.LOADING_SUCEESS);
            }

            @Override
            public void onNext(String str) {

            }
        });
    }




    public void getMessageList(String userId){
        messageListModel.getMessagesList(mCompositeSubscription,userId);
    }

    public void deleteAllMessage(String userId,String type){
        messageListModel.deleteAllMessage(mCompositeSubscription,userId,type);
    }
}
