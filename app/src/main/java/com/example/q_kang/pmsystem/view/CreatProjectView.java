package com.example.q_kang.pmsystem.view;

import com.example.q_kang.pmsystem.modules.bean.bean.ModelData;

import java.util.List;

public interface CreatProjectView extends BaseView {

    void initMobanData(List<ModelData> modelDataList);


    void createProject(String s);

    void editProject(String s);


}
