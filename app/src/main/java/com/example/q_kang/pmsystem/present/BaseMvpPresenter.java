package com.example.q_kang.pmsystem.present;

import rx.subscriptions.CompositeSubscription;

public class BaseMvpPresenter<V> implements MvpPresent<V> {

    private V mMvpView;

    protected CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    @Override
    public void attachView(V mvpView) {
        mMvpView = mvpView;
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        mMvpView = null;
        mCompositeSubscription.clear();
        mCompositeSubscription = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public V getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }


}
