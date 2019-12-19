package com.example.q_kang.pmsystem.present.im;

import com.example.q_kang.pmsystem.model.IPro_WorkListModel;
import com.example.q_kang.pmsystem.model.im.ShowPro_WorkListModel;
import com.example.q_kang.pmsystem.modules.bean.bean.project.ProjectData;
import com.example.q_kang.pmsystem.present.BaseMvpPresenter;
import com.example.q_kang.pmsystem.view.Pro_WorkListView;

/**
 * Created by appler on 2018/7/16.
 */

public class Pro_WorkListPresenter extends BaseMvpPresenter {

    private String TAG = "Pro_WorkListPresenter";
    private Pro_WorkListView pro_workListView;
    private ShowPro_WorkListModel showPro_workListModel;

    public Pro_WorkListPresenter(Pro_WorkListView pro_workListView) {
        this.pro_workListView = pro_workListView;

        showPro_workListModel = new ShowPro_WorkListModel(new IPro_WorkListModel() {
            @Override
            public void showPro_WorkList(String string) {
                pro_workListView.showPro_WorkList(string);
            }

            @Override
            public void getProDetail(ProjectData.DataBean projectData) {
                pro_workListView.getProDetail(projectData);
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

    public void getPro_WorkList(String projectId) {
        showPro_workListModel.getPro_WorkList(mCompositeSubscription, projectId);
    }

    public void getProDetail(String projectId) {
        showPro_workListModel.getProDetail(mCompositeSubscription, projectId);
    }
}
