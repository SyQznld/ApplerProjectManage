package com.example.q_kang.pmsystem.present.im;


import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.model.IShowProjectListModel;
import com.example.q_kang.pmsystem.model.im.ShowProjectListModel;
import com.example.q_kang.pmsystem.modules.bean.bean.Frame;
import com.example.q_kang.pmsystem.modules.bean.bean.project.ProjectData;
import com.example.q_kang.pmsystem.present.BaseMvpPresenter;
import com.example.q_kang.pmsystem.view.ProjectListView;

import java.util.List;

/**
 * Created by appler on 2018/7/16.
 */

public class ShowProjectListPresenter extends BaseMvpPresenter {

    private String TAG = "ShowProjectListPresenter";

    private ProjectListView projectListView;

    private ShowProjectListModel showProjectListModel;

    public ShowProjectListPresenter(ProjectListView projectListView) {
        this.projectListView = projectListView;

        showProjectListModel = new ShowProjectListModel(new IShowProjectListModel() {
            @Override
            public void onProjectList(String string) {
                projectListView.showProjectList(string);
            }

            @Override
            public void showSearchUser(List<Frame> frameList) {
                projectListView.showSearchUser(frameList);
            }

            @Override
            public void showProDepartment(String string) {
                projectListView.showProDepartment(string);
            }


            @Override
            public void onStart() {
                projectListView.setState(Globle.LOADING_STATE);

            }

            @Override
            public void onError() {
                projectListView.setState(Globle.LOADING_FAIL);
            }

            @Override
            public void onComplete() {
                projectListView.setState(Globle.LOADING_SUCEESS);
            }

            @Override
            public void onNext(String str) {

            }
        });

    }


    public void getProjectList(int page,
                               int limit,
                               int flag,
                               String currUserId,
                               String keyword,
                               String currRole,
                               String SearchUserId, String orgId,String Labels,String userName) {
        showProjectListModel.getProjectList(mCompositeSubscription, page,
                limit,
                flag,
                currUserId,
                keyword,
                currRole,
                SearchUserId, orgId,Labels,userName);
    }


    public void getProSearchUser(String userId, String role) {
        showProjectListModel.getProSearchUser(mCompositeSubscription, userId, role);
    }



    public void getProDepartment(String userId) {
        showProjectListModel.getProDepartment(mCompositeSubscription,userId);
    }
}