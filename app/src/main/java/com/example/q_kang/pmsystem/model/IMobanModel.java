package com.example.q_kang.pmsystem.model;

import com.example.q_kang.pmsystem.modules.bean.bean.ModelData;

import java.util.List;

/**
 * Created by appler on 2018/8/31.
 */

public interface IMobanModel extends IModel{

    void setModelAdapter(List<ModelData> datas);


    void showEventModel(String string);

    void loadEventModel(String string);
}
