package com.example.q_kang.pmsystem.ui.activity.photo;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoPlayerActivity extends BaseActivity {

    @BindView(R.id.vv_videoView)
    VideoView videoView;

    private String video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_video_player;
    }

    @Override
    public void doBusiness(Context mContext) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        //设置手机屏幕横向   竖屏播放视频时视频旋转90
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        video = getIntent().getStringExtra("video");
        Log.i("", "video: " + video);


        //设置视频控制器
        videoView.setMediaController(new MediaController(this));
        //   videoView.setVideoURI(Uri.parse(fileName));
        videoView.setVideoPath(video);
        videoView.requestFocus();
        videoView.start();


        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                finish();
            }
        });

    }
}
