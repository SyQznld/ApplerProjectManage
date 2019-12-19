package com.example.q_kang.pmsystem.view;

import com.example.q_kang.pmsystem.modules.bean.bean.Frame;

import java.util.List;


public interface ProjectListView extends BaseView{
    void showProjectList(String string);

    void showSearchUser(List<Frame> frameList);


    void showProDepartment(String string);


}
