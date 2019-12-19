package com.example.q_kang.pmsystem.view;

import com.example.q_kang.pmsystem.modules.bean.bean.Frame;
import com.example.q_kang.pmsystem.modules.bean.bean.work.WorkData;

import java.util.List;

/**
 * Created by appler on 2018/7/13.
 */

public interface WorkListView extends BaseView {
    void showWorkList(WorkData workData);
    void showWorkSearchUser(List<Frame> frameList);

    void showWorkDepartment(String string);

    void endRefresh();
    void endLoadMore();
}
