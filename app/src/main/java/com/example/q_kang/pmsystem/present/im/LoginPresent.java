package com.example.q_kang.pmsystem.present.im;


import android.util.Log;
import android.widget.Toast;

import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.api.apiService;
import com.example.q_kang.pmsystem.base.Application;
import com.example.q_kang.pmsystem.base.BaseRetrofit;
import com.example.q_kang.pmsystem.dao.AdminDao;
import com.example.q_kang.pmsystem.modules.bean.bean.UserBean;
import com.example.q_kang.pmsystem.present.BaseMvpPresenter;
import com.example.q_kang.pmsystem.view.LoginView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class LoginPresent extends BaseMvpPresenter<LoginView> {

    private LoginView loginView;

    public LoginPresent(LoginView loginView) {
        this.loginView = loginView;

    }

    BaseRetrofit retrofit = new BaseRetrofit();

    public void getResult(String name, String password, String isAndroid,String RegistrationID) {
        Log.i("login", "  name: " + name);
        Log.i("login", "  password: " + password);
        apiService apiService = retrofit.get().create(apiService.class);
        mCompositeSubscription.add(
                apiService.getLoginResult(name, password, isAndroid,RegistrationID)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                loginView.setState(Globle.LOADING_STATE);
                            }
                        })
                        .doOnTerminate(new Action0() {
                            @Override
                            public void call() {
                                //加载完成处理
                            }
                        })
                        .subscribe(new Subscriber<ResponseBody>() {

                            @Override
                            public void onCompleted() {
                                loginView.setState(Globle.LOADING_SUCEESS);

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i("login", "onError: " + e.getMessage());
                                loginView.setState(Globle.LOADING_FAIL);

                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i("login", "onNext: " + string);
                                    try {
                                        if ("{\"Result\":1,\"Message\":\"用户名或密码错误，请重新输入！\"}".equals(string)) {
                                            Toast.makeText(Application.getApplication().getAppContext(), "用户名或密码错误，请重新输入！", Toast.LENGTH_SHORT).show();
                                        } else if ("{\"Result\":1,\"Message\":\"用户名和密码不能为空！\"}".equals(string)) {
                                            Toast.makeText(Application.getApplication().getAppContext(), "用户名和密码不能为空！", Toast.LENGTH_SHORT).show();
                                        } else if ("{\"Result\":1,\"Message\":\"用户没有访问权限！\"}".equals(string)) {
                                            Toast.makeText(Application.getApplication().getAppContext(), "用户没有访问权限！", Toast.LENGTH_SHORT).show();
                                        } else {
                                            JSONObject jsonObject = new JSONObject(string);
                                            String userId = jsonObject.getString("Id");
                                            String Name = jsonObject.getString("Name");
                                            String Password = jsonObject.getString("Password");
                                            String IdCard = jsonObject.getString("IdCard");
                                            String Telephone = jsonObject.getString("Telephone");
                                            String RealName = jsonObject.getString("RealName");
                                            String Enabled = jsonObject.getString("Enabled");
                                            String DepartmentId = jsonObject.getString("DepartmentId");
                                            String image = jsonObject.getString("image");
                                            String DepartName = jsonObject.getString("DepartName");
                                            String RoleId = jsonObject.getString("RoleId");
                                            if (DepartName.equals("null")) {
                                                DepartName = "";
                                            }
                                            String Role = jsonObject.getString("Role");
                                            String IsAndroid = jsonObject.getString("IsAndroid");

                                            UserBean admin = AdminDao.getUser();
                                            if (admin == null) {
                                                admin = new UserBean();
                                                admin.setUserId(userId);
                                                admin.setName(Name);
                                                admin.setPassword(Password);
                                                admin.setIdCard(IdCard);
                                                admin.setTelephone(Telephone);
                                                admin.setRealName(RealName);
                                                admin.setEnabled(Boolean.valueOf(Enabled));
                                                admin.setDepartmentId(DepartmentId);
                                                admin.setImage(image);
                                                admin.setDepartName(DepartName);
                                                admin.setRole(Role);
                                                admin.setRoleId(RoleId);
                                                admin.setIsAndroid(IsAndroid);
                                                AdminDao.insertLabel(admin);

                                                loginView.onSuccess(admin);

                                            } else {
                                                admin.setUserId(userId);
                                                admin.setName(Name);
                                                admin.setPassword(Password);
                                                admin.setIdCard(IdCard);
                                                admin.setTelephone(Telephone);
                                                admin.setRealName(RealName);
                                                admin.setEnabled(Boolean.valueOf(Enabled));
                                                admin.setDepartmentId(DepartmentId);
                                                admin.setImage(image);
                                                admin.setDepartName(DepartName);
                                                admin.setRole(Role);
                                                admin.setRoleId(RoleId);
                                                admin.setIsAndroid(IsAndroid);

                                                AdminDao.updateLabel(admin);
                                                loginView.onSuccess(admin);
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

//                                    Gson gson = new Gson();
//                                    UserBean userBean = gson.fromJson(string, new TypeToken<UserBean>() {
//                                    }.getType());
//
//                                    Log.i("========", "onNext: "+userBean);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
        );
    }


}
