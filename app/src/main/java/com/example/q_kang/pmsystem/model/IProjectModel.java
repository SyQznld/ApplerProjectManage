package com.example.q_kang.pmsystem.model;

import com.example.q_kang.pmsystem.modules.bean.bean.ModelData;

import java.util.List;

/**
 * Created by appler on 2018/7/16.
 */

public interface IProjectModel extends IModel {

    void createProject(String s);

    void editProject(String s);

    void initMobanData(List<ModelData> modelDataList);

}
