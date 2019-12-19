package com.example.q_kang.pmsystem.present.im;

import android.util.Log;

import com.example.q_kang.pmsystem.model.IModel;
import com.example.q_kang.pmsystem.model.im.FrameModel;
import com.example.q_kang.pmsystem.modules.bean.bean.ChooseData;
import com.example.q_kang.pmsystem.modules.bean.bean.Frame;
import com.example.q_kang.pmsystem.present.BaseMvpPresenter;
import com.example.q_kang.pmsystem.view.ChooseFrameView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class ChooseFramePresent extends BaseMvpPresenter {
    private static final String TAG = "ChooseFramePresent";
    private FrameModel frameModel;
    private ChooseFrameView chooseFrameView0;

    public ChooseFramePresent(ChooseFrameView chooseFrameView) {
        this.chooseFrameView0 = chooseFrameView;

        frameModel = new FrameModel(new IModel() {
            @Override
            public void onStart() {
//                chooseFrameView0.setState(Globle.LOADING_STATE);
            }

            @Override
            public void onError() {
//                chooseFrameView0.setState(Globle.LOADING_FAIL);
            }

            @Override
            public void onComplete() {
//                chooseFrameView0.setState(Globle.LOADING_SUCEESS);
            }

            @Override
            public void onNext(String str) {
                Gson gson = new Gson();
                List<Frame> stringList = gson.fromJson(str, new TypeToken<List<Frame>>() {
                }.getType());
                Log.i(TAG, "onNext: " + stringList);
                List<ChooseData> chooseDataList = new ArrayList<>();
                for (Frame data : stringList) {
                    ChooseData chooseData = new ChooseData();
                    chooseData.setFrame(data);
                    chooseDataList.add(chooseData);
                }
                Log.i(TAG, "onNext: " + chooseDataList);
                chooseFrameView0.setChooseAdapter(chooseDataList);
            }

        });
    }

    public void getFrame() {
        frameModel.getFrames(mCompositeSubscription);
    }


}
