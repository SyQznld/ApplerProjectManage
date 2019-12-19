package com.example.q_kang.pmsystem.present.im;

import com.example.q_kang.pmsystem.model.IWorkModel;
import com.example.q_kang.pmsystem.model.im.WorkModel;
import com.example.q_kang.pmsystem.present.BaseMvpPresenter;
import com.example.q_kang.pmsystem.view.WorkView;

/**
 * Created by appler on 2018/7/13.
 */

public class WorkPresenter extends BaseMvpPresenter {

    private String TAG = "WorkPresenter";

    private WorkView workView;

    private WorkModel workModel;


    public WorkPresenter(WorkView workView) {
        this.workView = workView;

        this.workView = workView;

        workModel = new WorkModel(new IWorkModel() {
            @Override
            public void onCreateNext(String str) {
                workView.createWork(str);
            }

            @Override
            public void onEditNext(String str) {
                workView.editWork(str);
            }

            @Override
            public void getEventModels(String string) {
                workView.getEventModel(string);
            }

            @Override
            public void getEventList(String string) {
                workView.getEventList(string);
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


    public void createWork(
            String Name, String pId, String StartTime,
            String JoinPersons, String EndTime, String LeaderId,
            String Content, String Location, String address, String AddUserId,
            String Progress, String Lable, String nameList, String leaderIdList,String nextId) {
        workModel.createWork(mCompositeSubscription, Name, pId, StartTime, JoinPersons, EndTime, LeaderId,
                Content, Location, address, AddUserId, Progress, Lable, nameList, leaderIdList,nextId);

    }


    public void editWork(
            String Id,
            String Name, String pId, String StartTime,
            String JoinPersons, String EndTime, String LeaderId,
            String Content, String Location, String address, String AddUserId,
            String Progress, String Lable, String nameList, String leaderIdList,String nextId) {
        workModel.editWork(mCompositeSubscription, Id, Name, pId, StartTime, JoinPersons, EndTime, LeaderId,
                Content, Location, address, AddUserId, Progress, Lable, nameList, leaderIdList,nextId);

    }


    public void getEventModels(int page ,int limit,String templet) {
        workModel.getEventModels(mCompositeSubscription,page,limit,templet);
    }


    public void getEventListOrNo(String templet) {
        workModel.getEventListOrNo(mCompositeSubscription, templet);
    }
}
