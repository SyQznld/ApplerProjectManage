package com.example.q_kang.pmsystem.view;

import com.example.q_kang.pmsystem.modules.bean.bean.project.ProjectData;

/**
 * Created by appler on 2018/7/16.
 */

public interface Pro_WorkListView extends BaseView {

    void showPro_WorkList(String string);

    void getProDetail(ProjectData.DataBean projectData);
}
