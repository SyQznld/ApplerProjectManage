package com.example.q_kang.pmsystem.model;

import com.example.q_kang.pmsystem.modules.bean.bean.ModelData;

import java.util.List;

/**
 * Created by appler on 2018/8/31.
 */

public interface ICreateMobanModel extends IModel {

    void initMobanData(List<ModelData> modelDataList);
}
