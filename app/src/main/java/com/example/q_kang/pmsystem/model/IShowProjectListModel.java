package com.example.q_kang.pmsystem.model;

import com.example.q_kang.pmsystem.modules.bean.bean.Frame;

import java.util.List;


public interface IShowProjectListModel extends IModel{
    void onProjectList(String string);

    void showSearchUser(List<Frame> frameList);

    void showProDepartment(String string);


}
