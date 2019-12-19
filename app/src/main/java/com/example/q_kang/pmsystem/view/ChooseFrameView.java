package com.example.q_kang.pmsystem.view;

import com.example.q_kang.pmsystem.modules.bean.bean.ChooseData;

import java.util.List;

public interface ChooseFrameView extends BaseView {

    void setChooseAdapter(List<ChooseData> datas);

}
