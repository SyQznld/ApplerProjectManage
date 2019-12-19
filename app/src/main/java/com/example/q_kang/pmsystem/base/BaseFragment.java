package com.example.q_kang.pmsystem.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.present.BaseMvpPresenter;
import com.example.q_kang.pmsystem.view.BaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment implements BaseView {
    protected View view;
    protected Activity activity;
    private Unbinder bind;
    private BaseMvpPresenter baseMvpPresenter;

    protected final int LOADING_STATE = 1001;

    protected final int LOADING_FAIL = 1002;

    protected final int LOADING_SUCEESS = 1003;
    private MaterialDialog show;

    @Nullable
    @Override//加载布局
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(setFragView(), container, false);
        activity = getActivity();
        baseMvpPresenter = new BaseMvpPresenter();
        return view;
    }

    @Override//布局加载完毕，执行数据处理
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind = ButterKnife.bind(this, view);
        initFragView();
        doFragBusiness();
     //   baseMvpPresenter.detachView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
        baseMvpPresenter.detachView();
    }


    public abstract int setFragView();

    protected abstract void initFragView();

    protected abstract void doFragBusiness();

    @Override
    public void setState(int state) {
        switch (state) {
            case LOADING_STATE:     //加载状态
                show = new MaterialDialog.Builder(activity)
                        .content(R.string.loading)
                        .progress(true, 0)
                        .progressIndeterminateStyle(false)
                        .show();
                Toast.makeText(activity, "正在加载", Toast.LENGTH_SHORT).show();
                break;
            case LOADING_FAIL:   //加载失败
                if (show!=null){

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            show.dismiss();
                        }
                    }, 1000);
                }
                Toast.makeText(activity, "加载失败", Toast.LENGTH_SHORT).show();
                break;
            case LOADING_SUCEESS:           //加载失成功
                if (show!=null){

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            show.dismiss();
                        }
                    }, 1000);
                }
                Toast.makeText(activity, "加载完成", Toast.LENGTH_SHORT).show();
                break;
        }
    }


}
