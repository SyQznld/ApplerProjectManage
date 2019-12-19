package com.example.q_kang.pmsystem.model.im;

import android.util.Log;

import com.example.q_kang.pmsystem.base.BaseRetrofit;
import com.example.q_kang.pmsystem.model.IShowEventListModel;
import com.example.q_kang.pmsystem.modules.bean.bean.Frame;
import com.example.q_kang.pmsystem.modules.bean.bean.event.EventData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class ShowEventListModel {

    private static final String TAG = "ShowEventListModel";

    private IShowEventListModel iModel;

    private BaseRetrofit baseRetrofit = new BaseRetrofit();

    public ShowEventListModel(IShowEventListModel iModel) {
        this.iModel = iModel;
    }

    public void getEventList(CompositeSubscription mCompositeSubscription, int page, int limit, int flag,
                             String userId, String keyword, String SearchUserId,String orgId,String Labels) {

        Log.i(TAG, "getEventList  flag: " + flag);
        Log.i(TAG, "getEventList  keyword: " + keyword);
        Log.i(TAG, "getEventList  SearchUserId: " + SearchUserId);
        Log.i(TAG, "getEventList  orgId: " + orgId);
        Log.i(TAG, "getEventList  Labels: " + Labels);

        mCompositeSubscription.add(
                baseRetrofit.getApiService().getEventList(page, limit, flag, userId, keyword, SearchUserId,orgId,Labels)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                //          iModel.onStart();
                            }
                        })
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                               // iModel.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "getEventList onError: " + e.getMessage());
                                iModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String s = responseBody.string();
                                    Log.i(TAG, "getEventList onNext------------------: " + s);

                                    EventData eventData = new Gson().fromJson(s, new TypeToken<EventData>() {
                                    }.getType());
                                    iModel.showEventList(eventData);

//                                    try {
//                                        List<EventData> eventDataList = new ArrayList<>();
//                                        List<EventData.DataBean> dataBeanList = new ArrayList<>();
//                                        JSONObject object = new JSONObject(s);
//                                        int code = object.getInt("code");
//                                        String msg = object.getString("msg");
//                                        int count = object.getInt("count");
//
//                                        JSONArray data = object.getJSONArray("data");
//                                        for (int j = 0; j < data.length(); j++) {
//                                            JSONObject jsonObject = data.getJSONObject(j);
//
//                                            String Id = jsonObject.getString("Id");
//                                            String pId = jsonObject.getString("pId");
//                                            String Content = jsonObject.getString("Content");
//                                            String StartTime = jsonObject.getString("StartTime");
//                                            String EndTime = jsonObject.getString("EndTime");
//                                            String LaunchPerson = jsonObject.getString("LaunchPerson");
//                                            String AssignPerson = jsonObject.getString("AssignPerson");
//                                            String Location = jsonObject.getString("Location");
//                                            String AddTime = jsonObject.getString("AddTime");
//                                            String UpdateTime = jsonObject.getString("UpdateTime");
//                                            boolean Flag = jsonObject.getBoolean("Flag");
//                                            String Upload = jsonObject.getString("Upload");
//                                            String Progress = jsonObject.getString("Progress");
//                                            String Lable = jsonObject.getString("Lable");
//                                            String Title = jsonObject.getString("Title");
//
//                                            String assignPersons = jsonObject.getString("assignPersons");
//                                            String imagePerson = jsonObject.getString("imagePerson");
//                                            String LeaderPerson = jsonObject.getString("LeaderPerson");
//                                            String WorkName = jsonObject.getString("WorkName");
//                                            String image = jsonObject.getString("image");
//                                            if (WorkName == null || "".equals(WorkName) || "null".equals(WorkName)) {
//                                                WorkName = "暂无所属工作";
//                                            }
//                                            if (Lable == null || "".equals(Lable) || "null".equals(Lable)) {
//                                                Lable = "暂无所属标签";
//                                            }
//
//                                            EventData.DataBean dataBean = new EventData.DataBean();
//                                            dataBean.setId(Id);
//                                            dataBean.setpId(pId);
//                                            dataBean.setContent(Content);
//                                            dataBean.setStartTime(StartTime);
//                                            dataBean.setEndTime(EndTime);
//                                            dataBean.setLaunchPerson(LaunchPerson);
//                                            dataBean.setAssignPerson(AssignPerson);
//                                            dataBean.setLocation(Location);
//                                            dataBean.setAddTime(AddTime);
//                                            dataBean.setUpdateTime(UpdateTime);
//                                            dataBean.setFlag(Flag);
//                                            dataBean.setUpload(Upload);
//                                            dataBean.setProgress(Progress);
//                                            dataBean.setLable(Lable);
//                                            dataBean.setTitle(Title);
//                                            dataBean.setAssignPersons(assignPersons);
//                                            dataBean.setImagePerson(imagePerson);
//                                            dataBean.setLeaderPerson(LeaderPerson);
//                                            dataBean.setWorkName(WorkName);
//                                            dataBean.setImage(image);
//
//                                            dataBeanList.add(dataBean);
//                                        }
//
//                                        EventData eventData = new EventData();
//                                        eventData.setCode(code);
//                                        eventData.setCount(count);
//                                        eventData.setMsg(msg);
//                                        eventData.setData(dataBeanList);
//
//                                        eventDataList.add(eventData);
//
//                                        Log.i(TAG, "onNext eventDataList----------------------: " + eventDataList);
//                                        iModel.showEventList(eventDataList);
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iModel.onError();
                                }
                            }
                        }));
    }


    public void getEventSearchUser(CompositeSubscription mCompositeSubscription, String userId, String role) {

        Log.i(TAG, "getEventSearchUser  currUserId: " + userId);

        mCompositeSubscription.add(
                baseRetrofit.getApiService()
                        .getSearchUserById(userId, role)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                                Log.i(TAG, "onCompleted getEventSearchUser");
                                iModel.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError  getEventSearchUser: " + e.getMessage());
                                iModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String s = responseBody.string();
                                    Log.i(TAG, "onNext  getEventSearchUser: " + s);
                                    List<Frame> frames = new Gson().fromJson(s, new TypeToken<List<Frame>>() {
                                    }.getType());

                                    iModel.showEventSearchUser(frames);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }));
    }






    public void getEventDepartment(CompositeSubscription mCompositeSubscription, String userId) {

        Log.i(TAG, "getEventDepartment  currUserId: " + userId);

        mCompositeSubscription.add(
                baseRetrofit.getApiService()
                        .getDepartment(userId)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                                Log.i(TAG, "onCompleted getEventDepartment");
                                iModel.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError  getEventDepartment: " + e.getMessage());
                                iModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String s = responseBody.string();
                                    Log.i(TAG, "onNext  getEventDepartment: " + s);

                                    iModel.showEventDepartment(s);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }));
    }






}
