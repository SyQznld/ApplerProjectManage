package com.example.q_kang.pmsystem.model;

import com.example.q_kang.pmsystem.modules.bean.bean.AllData;
import com.example.q_kang.pmsystem.modules.bean.bean.Frame;

import java.util.List;

/**
 * Created by appler on 2018/7/23.
 */

public interface IDataByIdModel  extends IModel{
    void getDataById(AllData allData);

    void showSearchUser(List<Frame> frames);
}
