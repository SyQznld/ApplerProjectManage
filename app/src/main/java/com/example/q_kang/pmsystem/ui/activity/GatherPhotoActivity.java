package com.example.q_kang.pmsystem.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.BaseActivity;
import com.example.q_kang.pmsystem.ui.view.Utils.CommonUtils;
import com.example.q_kang.pmsystem.ui.view.Utils.Utils.PermissionsDelegate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.fotoapparat.Fotoapparat;
import io.fotoapparat.error.CameraErrorListener;
import io.fotoapparat.exception.camera.CameraException;
import io.fotoapparat.parameter.ScaleType;
import io.fotoapparat.preview.Frame;
import io.fotoapparat.preview.FrameProcessor;
import io.fotoapparat.result.BitmapPhoto;
import io.fotoapparat.result.PhotoResult;
import io.fotoapparat.result.WhenDoneListener;
import io.fotoapparat.view.CameraView;

import static io.fotoapparat.log.LoggersKt.fileLogger;
import static io.fotoapparat.log.LoggersKt.logcat;
import static io.fotoapparat.log.LoggersKt.loggers;
import static io.fotoapparat.result.transformer.ResolutionTransformersKt.scaled;
import static io.fotoapparat.selector.LensPositionSelectorsKt.back;

public class GatherPhotoActivity extends BaseActivity {

    @BindView(R.id.cameraView)
    CameraView cameraView;
    @BindView(R.id.bg)
    ImageView bg;
    @BindView(R.id.img)
    PhotoView img;
    @BindView(R.id.parent)
    FrameLayout parent;
    @BindView(R.id.no_permission)
    TextView noPermission;
    @BindView(R.id.iv_yulan)
    PhotoView ivYulan;
    @BindView(R.id.iv_take)
    ImageView ivTake;
    @BindView(R.id.tv_photo_back)
    TextView tvPhotoBack;

    private int taketype;


    private final PermissionsDelegate permissionsDelegate = new PermissionsDelegate(this);
    private boolean hasCameraPermission;
    private String path;

    private Fotoapparat fotoapparat;

    AlphaAnimation in = new AlphaAnimation(0, 1);
    AlphaAnimation out = new AlphaAnimation(1, 0);

    private Info info;
    private List<String> photos = new ArrayList<>();

    @Override
    public int bindLayout() {
        return R.layout.activity_gather_photo;
    }

    @Override
    public void doBusiness(Context mContext) {
        path = CommonUtils.initPath();

        if (CommonUtils.getSDPath() == null) {
            Toast.makeText(this, "请安装SD卡", Toast.LENGTH_SHORT).show();
            return;
        }
        File folderAddr = new File(path);
        if (!folderAddr.exists() || !folderAddr.isDirectory()) {
            folderAddr.mkdirs();
        }
//        path = CommonUtil.getSDPath() + File.separator + "Fotoapparat/Images" + File.separator;
        Log.i(TAG, "onCreate: " + path);
        hasCameraPermission = permissionsDelegate.hasCameraPermission();
        if (hasCameraPermission) {
            cameraView.setVisibility(View.VISIBLE);
        } else {
            permissionsDelegate.requestCameraPermission();
        }
        in.setDuration(1000);
        out.setDuration(1000);
        out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                bg.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        fotoapparat = createFotoapparat();

    }

    private Fotoapparat createFotoapparat() {
        return Fotoapparat
                .with(this)
                .into(cameraView)
                .previewScaleType(ScaleType.CenterCrop)
                .lensPosition(back())
                .frameProcessor(new SampleFrameProcessor())
                .logger(loggers(
                        logcat(),
                        fileLogger(this)
                ))
                .cameraErrorCallback(new CameraErrorListener() {
                    @Override
                    public void onError(@NotNull CameraException e) {
                        Toast.makeText(GatherPhotoActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                })
                .build();
    }

    @OnClick({R.id.tv_photo_back, R.id.iv_take})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_photo_back:
                Intent intent = new Intent();
                intent.putStringArrayListExtra("gatherPhotos", (ArrayList<String>) photos);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.iv_take:
                parent.setVisibility(View.VISIBLE);
                takePhoto();
                break;
        }
    }


    private class SampleFrameProcessor implements FrameProcessor {
        @Override
        public void process(@NotNull Frame frame) {
            // Perform frame processing, if needed
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void takePhoto() {
        Configuration mConfiguration = this.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向
        if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {
            //横屏
            taketype = 0;
        } else if (ori == mConfiguration.ORIENTATION_PORTRAIT) {
            //竖屏
            taketype = 1;
        }
        PhotoResult photoResult = fotoapparat.takePicture();
        final String photo_path = path + CommonUtils.getUUID32() + "photo.jpg";
        Log.i(TAG, "takePhoto: " + photo_path);
        photos.add(photo_path);
//        photoResult.saveToFile(new File(path));

        tvPhotoBack.setVisibility(View.VISIBLE);  //完成按钮  可见

        photoResult
                .toBitmap(scaled(0.25f))
                .whenDone(new WhenDoneListener<BitmapPhoto>() {
                    @Override
                    public void whenDone(@Nullable final BitmapPhoto bitmapPhoto) {
                        if (bitmapPhoto == null) {
                            Log.e("", "Couldn't capture photo.");
                            return;
                        }
                        img.setImageBitmap(bitmapPhoto.bitmap);
                        Log.i(TAG, "takePhoto: " + photoResult.toBitmap());
                        info = ivYulan.getInfo();
                        if (parent.getVisibility() == View.VISIBLE) {
                            bg.startAnimation(out);
                            img.animaTo(info, new Runnable() {
                                @Override
                                public void run() {
                                    CommonUtils.saveBitmap(taketype,photo_path, bitmapPhoto.bitmap);
                                    bg.setVisibility(View.GONE);
                                    ivYulan.setImageBitmap(bitmapPhoto.bitmap);
                                    ivYulan.setRotation(-bitmapPhoto.rotationDegrees);
                                }
                            });
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionsDelegate.resultGranted(requestCode, permissions, grantResults)) {
            fotoapparat.start();
            cameraView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (hasCameraPermission) {
            fotoapparat.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (hasCameraPermission) {
            fotoapparat.stop();
        }
    }

}
