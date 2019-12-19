package com.example.q_kang.pmsystem.present.im;

import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.model.im.FrameModel;
import com.example.q_kang.pmsystem.model.IModel;
import com.example.q_kang.pmsystem.modules.bean.bean.Frame;
import com.example.q_kang.pmsystem.present.BaseMvpPresenter;
import com.example.q_kang.pmsystem.view.FrameView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class FramePresent extends BaseMvpPresenter {
    private static final String TAG = "FramePresent";
    private FrameModel baseModel;
    private FrameView frameViewa;

    public FramePresent(FrameView frameView) {
        this.frameViewa = frameView;
        baseModel = new FrameModel(new IModel() {
            @Override
            public void onStart() {
                frameViewa.setState(Globle.LOADING_STATE);
            }

            @Override
            public void onError() {
                frameViewa.setState(Globle.LOADING_FAIL);
            }

            @Override
            public void onComplete() {
                frameViewa.setState(Globle.LOADING_SUCEESS);
            }

            @Override
            public void onNext(String str) {
                Gson gson = new Gson();
                List<Frame> stringList = gson.fromJson(str, new TypeToken<List<Frame>>() {
                }.getType());
                frameViewa.showAdapter(stringList);
            }
        });
    }

    public void getFrames() {
        baseModel.getFrames(mCompositeSubscription);
    }


}
