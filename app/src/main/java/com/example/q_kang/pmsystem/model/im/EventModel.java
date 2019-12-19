package com.example.q_kang.pmsystem.model.im;

import android.util.Log;
import android.widget.Toast;

import com.example.q_kang.pmsystem.base.Application;
import com.example.q_kang.pmsystem.base.BaseRetrofit;
import com.example.q_kang.pmsystem.model.IModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.Result;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class EventModel {

    private String TAG = "EventModel";

    private IModel iModel;

    private BaseRetrofit baseRetrofit = new BaseRetrofit();

    public EventModel(IModel iModel) {
        this.iModel = iModel;
    }


    public void getEventSearchUser(CompositeSubscription compositeSubscription, String userId, String role) {

        compositeSubscription.add(
                baseRetrofit.getApiService()
                        .getSearchUserById(userId, role)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError getEventSearchUser: " + e.getMessage());
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext getEventSearchUser: " + string);

                                    iModel.onNext(string);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                            }
                        }));
    }


    public void getFilterEventList(CompositeSubscription mCompositeSubscription, int page, int limit, int flag, String userId, String keyword) {
        mCompositeSubscription.add(
                baseRetrofit.getApiService().getFilterEventList(page, limit, flag, userId, keyword)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String s = responseBody.string();
                                    Log.i(TAG, "getFilterEventList onNext------------------: " + s);

                                    iModel.onNext(s);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }));
    }


    //新建事件  上传附件  多个文件 多种类型
    public void eventFileUpload(CompositeSubscription compositeSubscription, List<File> fileList) {


//        File file = null;
//        MultipartBody.Part body = null;
//        for (int i = 0; i < fileList.size(); i++) {
//            file = new File(fileList.get(i));
//            RequestBody requestBody = RequestBody.create(MediaType.parse("application/otcet-stream"), file);
//            body = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
//        }

        Map<String, RequestBody> map = new HashMap<>();
        for (File file : fileList) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/otcet-stream"), file);
            map.put(file.getName(), requestBody);
        }
        Log.i(TAG, "eventFileUpload: " + map.size());

        compositeSubscription.add(baseRetrofit.getApiService().getEventPath(map)
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {

                    }
                })
                .subscribe(new Subscriber<Result<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<String> stringResult) {
                        Log.i(TAG, "onNext======================: " + stringResult.toString());
                    }
                }));


    }


    //pId,Content,StartTime,EndTime,LaunchPerson,AssignPerson,Location,Upload,Progress,Lable
    public void eventCreate(CompositeSubscription mCompositeSubscription,
                            String pId, String Title, String Content, String StartTime, String EndTime, String LaunchPerson, String leaderId, String AssignPerson,
                            String Location, String address, String Upload, String Progress, String Lable, String nextId) {
        Log.i(TAG, "eventCreate: " + Title);
        Log.i(TAG, "eventCreate:  LaunchPerson " + LaunchPerson);
        Log.i(TAG, "eventCreate:  leaderId" + leaderId);


        mCompositeSubscription.add(baseRetrofit.getApiService().
                createEvent(pId, Title, Content, StartTime, EndTime, LaunchPerson, leaderId, AssignPerson, Location, address, Upload, Progress, Lable, nextId)
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
//                        iModel.onStart();
                    }
                })
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
//                        iModel.onComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError eventCreate: " + e.getMessage());
//                        iModel.onError();

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            Log.i(TAG, "onNext  eventCreate=====: " + string);

                            JSONObject jsonObject = new JSONObject(string);
                            String result = jsonObject.getString("Result");
                            String message = jsonObject.getString("Message");
                            if ("保存成功！".equals(message)) {
                                Toast.makeText(Application.getApplication().getAppContext(), "事件创建成功", Toast.LENGTH_SHORT).show();
                            }

                            iModel.onNext(string);

                        } catch (IOException e) {
                            e.printStackTrace();
//                            iModel.onError();
                        } catch (JSONException e) {
                            e.printStackTrace();
//                            iModel.onError();
                        }
                    }
                }));

    }


    public void editCreate(CompositeSubscription mCompositeSubscription,
                           String pId, String Id, String Title, String Content, String StartTime, String EndTime, String LaunchPerson, String leaderId, String AssignPerson,
                           String Location, String address, String Upload, String Progress, String Lable, String nextId) {


        mCompositeSubscription.add(baseRetrofit.getApiService().
                editEvent(pId, Id, Title, Content, StartTime, EndTime, LaunchPerson, leaderId, AssignPerson, Location, address, Upload, Progress, Lable, nextId)
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        iModel.onStart();
                    }
                })
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        iModel.onComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, " editCreate onError: " + e.getMessage());
                        iModel.onError();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            Log.i(TAG, "onNext  edit=====: " + string);
                            iModel.onNext(string);

                        } catch (IOException e) {
                            e.printStackTrace();
                            iModel.onError();
                        }
                    }
                }));

    }


}
