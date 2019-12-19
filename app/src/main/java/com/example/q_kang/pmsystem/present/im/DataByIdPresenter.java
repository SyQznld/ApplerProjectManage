package com.example.q_kang.pmsystem.present.im;

import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.model.IDataByIdModel;
import com.example.q_kang.pmsystem.model.im.DataByIdModel;
import com.example.q_kang.pmsystem.modules.bean.bean.AllData;
import com.example.q_kang.pmsystem.modules.bean.bean.Frame;
import com.example.q_kang.pmsystem.present.BaseMvpPresenter;
import com.example.q_kang.pmsystem.view.DataByIdView;

import java.util.List;

/**
 * Created by appler on 2018/7/23.
 */

public class DataByIdPresenter extends BaseMvpPresenter {

    private String TAG = "DataByIdPresenter";

    private DataByIdModel dataByIdModel;

    private DataByIdView dataByIdView;

    public DataByIdPresenter(DataByIdView dataByIdView) {
        this.dataByIdView = dataByIdView;

        dataByIdModel = new DataByIdModel(new IDataByIdModel() {
            @Override
            public void getDataById(AllData allData) {
                dataByIdView.getDataById(allData);
            }

            @Override
            public void showSearchUser(List<Frame> frames) {
                dataByIdView.showSearchUser(frames);
            }

            @Override
            public void onStart() {
dataByIdView.setState(Globle.LOADING_STATE);
            }

            @Override
            public void onError() {
                dataByIdView.setState(Globle.LOADING_FAIL);
            }

            @Override
            public void onComplete() {
                dataByIdView.setState(Globle.LOADING_SUCEESS);
            }

            @Override
            public void onNext(String str) {

            }
        });
    }

    public void getDataById(String userId){
        dataByIdModel.getDataById(mCompositeSubscription,userId);
    }

    public void getSearchUserById(String userId,String role){
        dataByIdModel.getSearchUserById(mCompositeSubscription,userId,role);
    }
}
