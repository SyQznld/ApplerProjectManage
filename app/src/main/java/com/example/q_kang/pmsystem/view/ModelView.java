package com.example.q_kang.pmsystem.view;

import com.example.q_kang.pmsystem.modules.bean.bean.ModelData;

import java.util.List;

public interface ModelView extends BaseView {

    void setModelAdapter(List<ModelData> datas);


    void showEventModel(String string);

    void loadEventModel(String string);

}
