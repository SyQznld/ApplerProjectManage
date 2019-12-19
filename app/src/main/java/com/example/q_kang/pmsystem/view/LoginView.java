package com.example.q_kang.pmsystem.view;


import com.example.q_kang.pmsystem.modules.bean.bean.UserBean;

public interface LoginView extends BaseView {

    void onSuccess(UserBean user);

    void onFailure();

    void onError();


}
