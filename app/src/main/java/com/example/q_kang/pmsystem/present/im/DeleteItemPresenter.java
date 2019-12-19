package com.example.q_kang.pmsystem.present.im;

import com.example.q_kang.pmsystem.model.IDeleteModel;
import com.example.q_kang.pmsystem.model.im.DeleteModel;
import com.example.q_kang.pmsystem.present.BaseMvpPresenter;
import com.example.q_kang.pmsystem.view.DeleteView;

/**
 * Created by appler on 2018/8/28.
 */

public class DeleteItemPresenter extends BaseMvpPresenter {

    private String TAG = "DeleteItemPresenter";

    private DeleteModel deleteModel;

    private DeleteView deleteView;

    public DeleteItemPresenter(DeleteView deleteView) {
        this.deleteView = deleteView;

       deleteModel = new DeleteModel(new IDeleteModel() {
           @Override
           public void deleteItem(String string) {
               deleteView.deleteItem(string);
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

    public void deleteItem(String id,String type){
       deleteModel.deleteListItem(mCompositeSubscription,id,type);
    }


}
