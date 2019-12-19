package com.example.q_kang.pmsystem.view;

import com.example.q_kang.pmsystem.modules.bean.bean.event.EventData;
import com.example.q_kang.pmsystem.modules.bean.bean.work.WorkData;

/**
 * Created by appler on 2018/7/17.
 */

public interface Work_EventListView extends BaseView{

    void showWork_EvetList(EventData eventData);

    void getWorkDetail(WorkData.DataBean dataBean);
}
