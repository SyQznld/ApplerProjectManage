package com.example.q_kang.pmsystem.model;

import com.example.q_kang.pmsystem.modules.bean.bean.Frame;
import com.example.q_kang.pmsystem.modules.bean.bean.event.EventData;

import java.util.List;


public interface IShowEventListModel extends IModel{
    void showEventList(EventData eventData);

    void showEventSearchUser(List<Frame> frameList);

    void showEventDepartment(String string);

}
