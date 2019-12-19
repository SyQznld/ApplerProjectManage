package com.example.q_kang.pmsystem.model.im;

import android.util.Log;

import com.example.q_kang.pmsystem.base.BaseRetrofit;
import com.example.q_kang.pmsystem.model.IDocumentModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by appler on 2018/8/10.
 */

public class DocumentModel {
    private String TAG = "DocumentModel";

    private BaseRetrofit baseRetrofit = new BaseRetrofit();

    private IDocumentModel iDocumentModel;

    public DocumentModel(IDocumentModel iDocumentModel) {
        this.iDocumentModel = iDocumentModel;
    }

    //string Name = "", string Content = "", string AssignPerson = "", string Upload = "", string AddUserId = "",string LoginUserId=""
    public void createDocument(CompositeSubscription compositeSubscription,
                           String Name, String Content, String AssignPerson,
                           String Upload, String AddUserId, String LoginUserId,boolean isSend) {
//        Log.i(TAG, "createDocument name: " + Name);
//        Log.i(TAG, "createDocument content: " + Content);
//        Log.i(TAG, "createDocument assignperson: " + AssignPerson);
//        Log.i(TAG, "createDocument file: " + Upload);
//        Log.i(TAG, "createDocument pid: " + AddUserId);
//        Log.i(TAG, "createDocument userid: " + LoginUserId);
//        Log.i(TAG, "createDocument usisSenderid: " + isSend);

        compositeSubscription.add(baseRetrofit.getApiService().createDocument(
                Name, Content, AssignPerson, Upload, AddUserId, LoginUserId,isSend )
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                iDocumentModel.onError();
                                Log.i(TAG, "createDocument onError: " + e.getMessage());

                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {

                                    String string = responseBody.string();
                                    Log.i(TAG, " createDocument  onNext: " + string);
                                    iDocumentModel.createDocument(string);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iDocumentModel.onError();
                                }
                            }
                        })
        );
    }


    public void editDocument(CompositeSubscription compositeSubscription,
                               String Name, String Content, String AssignPerson,
                               String Upload, String AddUserId, String LoginUserId,boolean isSend) {

        Log.i(TAG, "editDocument name: " + Name);
        Log.i(TAG, "editDocument content: " + Content);
        Log.i(TAG, "editDocument assignperson: " + AssignPerson);
        Log.i(TAG, "editDocument file: " + Upload);
        Log.i(TAG, "editDocument pid: " + AddUserId);
        Log.i(TAG, "editDocument userid: " + LoginUserId);
        Log.i(TAG, "editDocument isSend: " + isSend);

        compositeSubscription.add(baseRetrofit.getApiService().
                editDocument(
                Name, Content, AssignPerson, Upload, AddUserId, LoginUserId,isSend )
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        iDocumentModel.onError();
                        Log.i(TAG, "editDocument onError: " + e.getMessage());

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {

                            String string = responseBody.string();
                            Log.i(TAG, " editDocument  onNext: " + string);
                            iDocumentModel.editDocument(string);

                        } catch (IOException e) {
                            e.printStackTrace();
                            iDocumentModel.onError();
                        }
                    }
                })
        );
    }




    public void getDocumentDatail(CompositeSubscription compositeSubscription,String docId) {
        Log.i(TAG, "getDocumentDatail id: " + docId);


        compositeSubscription.add(baseRetrofit.getApiService().
                getDocumentDetail(docId )
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        iDocumentModel.onError();
                        Log.i(TAG, "getDocumentDatail onError: " + e.getMessage());

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {

                            String string = responseBody.string();
                            Log.i(TAG, " getDocumentDatail  onNext: " + string);
                            iDocumentModel.getDocumentDetail(string);

                        } catch (IOException e) {
                            e.printStackTrace();
                            iDocumentModel.onError();
                        }
                    }
                })
        );
    }


}