package com.example.q_kang.pmsystem.present.im;


import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.model.IShowWorkModel;
import com.example.q_kang.pmsystem.model.im.ShowWorkListModel;
import com.example.q_kang.pmsystem.modules.bean.bean.Frame;
import com.example.q_kang.pmsystem.modules.bean.bean.work.WorkData;
import com.example.q_kang.pmsystem.present.BaseMvpPresenter;
import com.example.q_kang.pmsystem.view.WorkListView;

import java.util.List;

/**
 * Created by appler on 2018/7/14.
 */

public class ShowWorkListPresenter extends BaseMvpPresenter {

    private String TAG = "ShowWorkListPresenter";

    private WorkListView workView;

    private ShowWorkListModel workModel;

    public ShowWorkListPresenter(WorkListView workView) {
        this.workView = workView;

        workModel = new ShowWorkListModel(new IShowWorkModel() {
            @Override
            public void showWorkList(WorkData workData) {
                workView.showWorkList(workData);
            }

            @Override
            public void showWorkSearchUser(List<Frame> frameList) {
                workView.showWorkSearchUser(frameList);
            }

            @Override
            public void showWorkDepartment(String string) {
                workView.showWorkDepartment(string);
            }

            @Override
            public void onStart() {
                workView.setState(Globle.LOADING_STATE);
            }

            @Override
            public void onError() {
                workView.setState(Globle.LOADING_FAIL);
            }

            @Override
            public void onComplete() {
                workView.setState(Globle.LOADING_SUCEESS);
            }

            @Override
            public void onNext(String str) {

            }
        });

    }


    public void getWorkList(int page, int limit, int flag, String keyword, String userId, String SearchUserId,String orgId,String Labels) {
        workModel.getWorkList(mCompositeSubscription, page, limit, flag, keyword, userId, SearchUserId,orgId,Labels);
    }


    public void getWorkSearchUser(String userId, String role) {
        workModel.getWorkSearchUser(mCompositeSubscription, userId, role);
    }
    public void getWorkDepartment(String userId) {
        workModel.getWorkDepartment(mCompositeSubscription, userId);
    }

}