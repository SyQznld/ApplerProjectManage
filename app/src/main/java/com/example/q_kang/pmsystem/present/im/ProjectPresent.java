package com.example.q_kang.pmsystem.present.im;

import com.example.q_kang.pmsystem.model.IProjectModel;
import com.example.q_kang.pmsystem.model.im.ProjectModel;
import com.example.q_kang.pmsystem.modules.bean.bean.ModelData;
import com.example.q_kang.pmsystem.present.BaseMvpPresenter;
import com.example.q_kang.pmsystem.view.CreatProjectView;

import java.util.List;


public class ProjectPresent extends BaseMvpPresenter {
    private String TAG = "ProjectPresent";
    private ProjectModel projectModel;
    private CreatProjectView projectView;

    public ProjectPresent(CreatProjectView projectView) {
        this.projectView = projectView;

        projectModel = new ProjectModel(new IProjectModel() {
            @Override
            public void createProject(String s) {
                projectView.createProject(s);
            }

            @Override
            public void editProject(String s) {
                projectView.editProject(s);
            }

            @Override
            public void initMobanData(List<ModelData> modelDataList) {
                projectView.initMobanData(modelDataList);
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


    public void createProject(String json) {
        projectModel.createProject(mCompositeSubscription,json);
    }

    public void editProject(
            String json) {
        projectModel.editProject(mCompositeSubscription, json);
    }



    public void getModels() {
        projectModel.getMobans(mCompositeSubscription);
    }
}
