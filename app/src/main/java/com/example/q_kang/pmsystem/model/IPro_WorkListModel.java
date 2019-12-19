package com.example.q_kang.pmsystem.model;

import com.example.q_kang.pmsystem.modules.bean.bean.project.ProjectData;

/**
 * Created by appler on 2018/7/16.
 */

public interface IPro_WorkListModel extends IModel{

    void showPro_WorkList(String string);

    void getProDetail(ProjectData.DataBean projectData);
}
