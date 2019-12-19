package com.example.q_kang.pmsystem.model;

import com.example.q_kang.pmsystem.modules.bean.bean.event.EventData;
import com.example.q_kang.pmsystem.modules.bean.bean.work.WorkData;

/**
 * Created by appler on 2018/7/17.
 */

public interface IWork_EventListModel extends IModel{

    void showWork_EventList(EventData eventData);

    void getWorkDetail(WorkData.DataBean dataBean);
}
