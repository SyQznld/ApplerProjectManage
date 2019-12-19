package com.example.q_kang.pmsystem.present.im;

import com.example.q_kang.pmsystem.model.IGlobalSearchModel;
import com.example.q_kang.pmsystem.model.im.GlobalSearchModel;
import com.example.q_kang.pmsystem.present.BaseMvpPresenter;
import com.example.q_kang.pmsystem.view.SearchGlobalView;

/**
 * Created by appler on 2018/8/2.
 */

public class GlobalSearchPresenter extends BaseMvpPresenter {

    private String TAG = "GlobalSearchPresenter";
    private GlobalSearchModel searchModel;
    private SearchGlobalView searchView;

    public GlobalSearchPresenter(SearchGlobalView searchView) {
        this.searchView = searchView;

        searchModel = new GlobalSearchModel(new IGlobalSearchModel() {
            @Override
            public void showGlobalSearch(String string) {
                searchView.showGlobalSearch(string);
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

    public void showGlobalSearch(int flag,String keyword,String state,String userId) {
        searchModel.showGlobalSearch(mCompositeSubscription, flag,keyword,state,userId);
    }
}
