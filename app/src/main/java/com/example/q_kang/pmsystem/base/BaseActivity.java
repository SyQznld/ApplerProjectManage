package com.example.q_kang.pmsystem.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.present.BaseMvpPresenter;
import com.example.q_kang.pmsystem.view.BaseView;

import butterknife.ButterKnife;

public abstract class BaseActivity extends FragmentActivity implements BaseView {

    private View mContextView = null;
    /**
     * 是否输出日志信息
     **/
    private boolean isDebug;
    private String APP_NAME;
    protected final String TAG = this.getClass().getSimpleName();

    private BaseMvpPresenter baseMvpPresenter;
    private Activity activity;
    private Toast toast;
    private MaterialDialog loadDialog;
    private MaterialDialog uploadDialog;

//
//    private ActivityComponent mActivityComponent;
//
//
//    public ActivityComponent getActivityComponent() {
//        if (mActivityComponent == null) {
//            mActivityComponent = DaggerActivityComponent.builder()
//                    .activityModule(new ActivityModule(this))
//                    .applicationComponent(Application.get(this).getComponent())
//                    .build();
//        }
//        return mActivityComponent;
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(bindLayout());
        ButterKnife.bind(this);
        baseMvpPresenter = new BaseMvpPresenter();
        doBusiness(this);

    }


    public void showInputMethod() {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.showSoftInputFromInputMethod(getCurrentFocus().getWindowToken(), 0);
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


    /**
     * [绑定布局]
     * 抽象方法：必须实现的方法  返回数据不一样但是必须实现的方法
     *
     * @return
     */
    public abstract int bindLayout();


    /**
     * [业务操作]
     *
     * @param mContext
     */
    public abstract void doBusiness(Context mContext);


    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onResume() {
        super.onResume();
        $Log(TAG + "--->onResume()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        baseMvpPresenter.detachView();
        $Log(TAG + "--->onDestroy()");
    }

    /**
     * [日志输出]
     *
     * @param msg
     */
    protected void $Log(String msg) {
        if (isDebug) {
            Log.d(APP_NAME, msg);
        }
    }

    public void showToast(String message) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }


    @Override
    public void setState(int state) {
        switch (state) {
            case Globle.LOADING_STATE:
                loadDialog = new MaterialDialog.Builder(this)
                        .content(R.string.loading)
                        .progress(true, 0)
                        .progressIndeterminateStyle(false)
                        .show();
                break;
            case Globle.LOADING_FAIL:
                if (loadDialog != null) {
                    loadDialog.setContent(R.string.load_failed);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadDialog.dismiss();
                        }
                    }, 1000);
                }
                break;
            case Globle.LOADING_SUCEESS:
                if (loadDialog != null) {
                    loadDialog.setContent(R.string.loading_success);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadDialog.dismiss();
                        }
                    }, 500);
                }
                break;
            case Globle.UPLOADING_STATE:
                uploadDialog = new MaterialDialog.Builder(this)
                        .content(R.string.uploading)
                        .progress(true, 0)
                        .progressIndeterminateStyle(false)
                        .show();
                break;
            case Globle.UPLOADING_FAIL:
                if (uploadDialog != null) {
                    uploadDialog.setContent(R.string.uploading_fail);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            uploadDialog.dismiss();
                        }
                    }, 1000);
                }
                break;
            case Globle.UPLOADING_SUCCESS:
                if (uploadDialog != null) {
                    uploadDialog.setContent(R.string.uploading_success);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            uploadDialog.dismiss();
                        }
                    }, 1000);
                }
                break;
            default:
                break;
        }
    }
}
