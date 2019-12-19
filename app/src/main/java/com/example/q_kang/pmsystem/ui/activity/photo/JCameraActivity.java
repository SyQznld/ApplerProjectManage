package com.example.q_kang.pmsystem.ui.activity.photo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.cjt2325.cameralibrary.JCameraView;
import com.cjt2325.cameralibrary.lisenter.JCameraLisenter;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.BaseActivity;
import com.example.q_kang.pmsystem.dao.ImageDao;
import com.example.q_kang.pmsystem.modules.bean.bean.photo.ImageItemBean;
import com.example.q_kang.pmsystem.ui.view.Utils.CommonUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JCameraActivity extends BaseActivity {

    @BindView(R.id.jcv_camera)
    JCameraView cameraView;

    private String videoPath = CommonUtils.videoPath;

    private ArrayList<ImageItemBean> imageItemBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_jcamera;
    }

    @Override
    public void doBusiness(Context mContext) {

        cameraView.setSaveVideoPath(videoPath);
        cameraView.setJCameraLisenter(new JCameraLisenter() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void captureSuccess(Bitmap bitmap) {
                String imagePath = CommonUtils.saveBitmap(bitmap);
                Log.i(TAG, "captureSuccess: " + imagePath);
                ImageItemBean imageItemBean = new ImageItemBean();
                imageItemBean.setImageName(new File(imagePath).getName());
                imageItemBean.setImagePath(imagePath);
                imageItemBean.setTime(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));

                ImageDao.insertLabel(imageItemBean);
                imageItemBeanList.add(imageItemBean);
                Log.i(TAG, "captureSuccess  : " + imageItemBeanList);

            }

            @SuppressLint("SimpleDateFormat")
            @Override
            public void recordSuccess(String url, Bitmap firstFrame) {
                if (url != null && !"".equals(url)) {
                    ImageItemBean imageItemBean = new ImageItemBean();
                    imageItemBean.setImageName(new File(url).getName());
                    imageItemBean.setImagePath(url);
                    imageItemBean.setTime(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));

                    ImageDao.insertLabel(imageItemBean);
                    imageItemBeanList.add(imageItemBean);
                    Log.i(TAG, "recordSuccess  : " + imageItemBeanList);
                    Log.i(TAG, "recordSuccess  : " + imageItemBeanList.size());
                }


                Log.i(TAG, "recordSuccess: " + url);
            }

            @Override
            public void quit() {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        initJCameraView();
    }

    @SuppressLint("ObsoleteSdkInt")
    private void initJCameraView() {
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraView.onPause();
    }


}
