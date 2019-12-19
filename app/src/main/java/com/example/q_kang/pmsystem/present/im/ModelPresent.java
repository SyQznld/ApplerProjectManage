package com.example.q_kang.pmsystem.present.im;

import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.model.IMobanModel;
import com.example.q_kang.pmsystem.model.im.MobanModel;
import com.example.q_kang.pmsystem.modules.bean.bean.ModelData;
import com.example.q_kang.pmsystem.present.BaseMvpPresenter;
import com.example.q_kang.pmsystem.view.ModelView;

import java.util.List;

public class ModelPresent extends BaseMvpPresenter {


//    private CreatProjectView creatProjectView0;

    private String TAG = "ModelPresent";

    private MobanModel mobanModel;

    private ModelView modelView;

    public ModelPresent(ModelView modelView) {
        this.modelView = modelView;

        mobanModel = new MobanModel(new IMobanModel() {
            @Override
            public void setModelAdapter(List<ModelData> datas) {
                modelView.setModelAdapter(datas);
            }

            @Override
            public void showEventModel(String string) {
                modelView.showEventModel(string);

            }

            @Override
            public void loadEventModel(String string) {
                modelView.loadEventModel(string);
            }

            @Override
            public void onStart() {
                modelView.setState(Globle.LOADING_STATE);

            }

            @Override
            public void onError() {
                modelView.setState(Globle.LOADING_FAIL);
            }

            @Override
            public void onComplete() {
                modelView.setState(Globle.LOADING_SUCEESS);
            }

            @Override
            public void onNext(String str) {

            }
        });
    }



//    public ModelPresent(CreatProjectView creatProjectView) {
//        this.creatProjectView0 = creatProjectView;
//        mobanModel = new MobanModel(new IModel() {
//            @Override
//            public void onStart() {
//                creatProjectView0.setState(Globle.LOADING_STATE);
//            }
//
//            @Override
//            public void onError() {
//                creatProjectView0.setState(Globle.LOADING_FAIL);
//            }
//
//            @Override
//            public void onComplete() {
//                creatProjectView0.setState(Globle.LOADING_SUCEESS);
//            }
//
//            @Override
//            public void onNext(String s) {
//                Gson gson = new Gson();
//                List<ModelData> models = gson.fromJson(s, new TypeToken<List<ModelData>>() {
//                }.getType());
//                creatProjectView0.initMobanData(models);
//            }
//
//        });

//    }
//
//
//
//    public ModelPresent(ModelView modelView) {
//        this.modelView0 = modelView;
//
//        mobanModel = new MobanModel(new IModel() {
//            @Override
//            public void onStart() {
//
//            }
//
//            @Override
//            public void onError() {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//
//            @Override
//            public void onNext(String s) {
//                Gson gson = new Gson();
//                List<ModelData> models = gson.fromJson(s, new TypeToken<List<ModelData>>() {
//                }.getType());
//                for (int i = 0; i < models.size(); i++) {
//
//                }
//                modelView0.setModelAdapter(models);
//            }
//
//
//        });
//    }

    public void getModels() {
        mobanModel.getMobans(mCompositeSubscription);
    }

    public void getEventModels(int page,int limit,String Templet) {
        mobanModel.getEventMobans(mCompositeSubscription,page,limit,Templet);
    }

    public void loadModel(String id, String name,String oldname, Integer[] sortList, String[] cNameList) {
        mobanModel.loadModel(mCompositeSubscription
                , id
                , name
                , oldname
                , sortList
                , cNameList);
    }


    public void loadEventModel(String id, String name, String[] cNameList) {
        mobanModel.loadEventModel(mCompositeSubscription
                , id
                , name
                , cNameList);
    }

}
