package com.example.q_kang.pmsystem.view;


import com.example.q_kang.pmsystem.base.IView;
import com.example.q_kang.pmsystem.modules.bean.bean.EventModelData;
import com.example.q_kang.pmsystem.modules.bean.bean.ModelData;

public interface CreateModelView extends IView {

    void RenameItem(ModelData.MContentListBean item, int position);

    void CreateItem(ModelData.MContentListBean item, int position);

    void delItem(int Position);




    void RenameEventMB(EventModelData.DataBean.TempletsBean item, int position);

    void CreateEventMB(EventModelData.DataBean.TempletsBean item, int position);
    void delEventMBItem(int Position);


}
