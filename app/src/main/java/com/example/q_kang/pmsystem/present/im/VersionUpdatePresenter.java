package com.example.q_kang.pmsystem.present.im;

import com.example.q_kang.pmsystem.model.IVersionUpdateModel;
import com.example.q_kang.pmsystem.model.im.VersionUpdateModel;
import com.example.q_kang.pmsystem.present.BaseMvpPresenter;
import com.example.q_kang.pmsystem.view.VersionUpdateView;

/**
 * Created by appler on 2018/9/14.
 */

public class VersionUpdatePresenter extends BaseMvpPresenter {

    private String TAG = "VersionUpdatePresenter";
    private VersionUpdateView versionUpdateView;
    private VersionUpdateModel versionUpdateModel;

    public VersionUpdatePresenter(VersionUpdateView versionUpdateView) {
        this.versionUpdateView = versionUpdateView;

        versionUpdateModel = new VersionUpdateModel(new IVersionUpdateModel() {
            @Override
            public void versionUpdate(String string) {
                versionUpdateView.versionUpdate(string);
            }

            @Override
            public void resetPassword(String string) {
                versionUpdateView.resetPassword(string);
            }

            @Override
            public void getMessage(String string) {
                versionUpdateView.getMessage(string);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onError() {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onNext(String str) {

            }
        });
    }




    public void versionUpdate(String Version){
        versionUpdateModel.versionUpdate(mCompositeSubscription,Version);
    }

    public void resetPassword(String oldpwd, String newpwd, String tnewpwd, String userId){
        versionUpdateModel.resetPassword(mCompositeSubscription,oldpwd,newpwd,tnewpwd,userId);
    }


    public void getMessage(String userId){
        versionUpdateModel.getMessagesList(mCompositeSubscription,userId);
    }


}
