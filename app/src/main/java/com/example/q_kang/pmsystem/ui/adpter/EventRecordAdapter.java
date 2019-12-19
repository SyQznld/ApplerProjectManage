package com.example.q_kang.pmsystem.ui.adpter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.modules.bean.bean.RecordBean;
import com.example.q_kang.pmsystem.ui.view.Utils.CommonUtils;
import com.example.q_kang.pmsystem.ui.view.Utils.custom_view.record.MediaManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：wgyscsf on 2017/1/2 18:46
 * 邮箱：wgyscsf@163.com
 * 博客：http://blog.csdn.net/wgyscsf
 */
public class EventRecordAdapter extends BaseAdapter {
    List<RecordBean> mRecordBeans;
    Context mContext;
    List<AnimationDrawable> mAnimationDrawables;
    int pos = -1;//标记当前录音索引，默认没有播放任何一个


    public EventRecordAdapter(Context context, List<RecordBean> recordBeans) {
        this.mContext = context;
        this.mRecordBeans = recordBeans;
        mAnimationDrawables = new ArrayList<>();
    }


    @Override
    public int getCount() {
        Log.i("size", "getCount: " + mRecordBeans.size());
        return mRecordBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return mRecordBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_example_activity, null);
            viewHolder = new ViewHolder();
            viewHolder.ieaIvVoiceLine = (ImageView) convertView.findViewById(R.id.iea_iv_voiceLine);
            viewHolder.ieaLlSinger = (LinearLayout) convertView.findViewById(R.id.iea_ll_singer);
            viewHolder.ieaTvVoicetime1 = (TextView) convertView.findViewById(R.id.iea_tv_voicetime1);
            //  viewHolder.ieaIvRed = (ImageView) convertView.findViewById(R.id.iea_iv_red);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        final RecordBean recordBean = mRecordBeans.get(position);
        //设置显示时长
        viewHolder.ieaTvVoicetime1.setText(recordBean.getSecond() <= 0 ? 1 + "''" : recordBean.getSecond() + "''");
//        if (!recordBean.isPlayed()) {
//            viewHolder.ieaIvRed.setVisibility(View.VISIBLE);
//        } else {
//            viewHolder.ieaIvRed.setVisibility(View.GONE);
//        }
        //更改并显示录音条长度
        RelativeLayout.LayoutParams ps = (RelativeLayout.LayoutParams) viewHolder.ieaIvVoiceLine.getLayoutParams();
        ps.width = CommonUtils.getVoiceLineWight(mContext, recordBean.getSecond());
        viewHolder.ieaIvVoiceLine.setLayoutParams(ps); //更改语音长条长度

        //开始设置监听
        final LinearLayout ieaLlSinger = viewHolder.ieaLlSinger;
        viewHolder.ieaIvVoiceLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("path", "onClick: " + recordBean.getPath());
                //只要点击就设置为已播放状态（隐藏小红点）
                recordBean.setPlayed(true);
                notifyDataSetChanged();


                final AnimationDrawable animationDrawable = (AnimationDrawable) ieaLlSinger.getBackground();
                //重置动画
                resetAnim(animationDrawable);
                animationDrawable.start();

                //处理点击正在播放的语音时，可以停止；再次点击时重新播放。
                if (pos == position) {
                    if (recordBean.isPlaying()) {
                        recordBean.setPlaying(false);
                        MediaManager.release();
                        animationDrawable.stop();
                        animationDrawable.selectDrawable(0);//reset
                        return;
                    } else {
                        recordBean.setPlaying(true);
                    }
                }
                //记录当前位置正在播放。
                pos = position;
                recordBean.setPlaying(true);

                //播放前重置。
                MediaManager.release();
                //开始实质播放
                MediaManager.playSound(recordBean.getPath(),
                        new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                animationDrawable.selectDrawable(0);//显示动画第一帧
                                animationDrawable.stop();

                                //播放完毕，当前播放索引置为-1。
                                pos = -1;
                            }
                        });
            }
        });


        viewHolder.ieaIvVoiceLine.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onRecordItemLongClick.deleteRecord(position);
                return false;
            }
        });
        return convertView;
    }

    private void resetData() {
        for (RecordBean recordBean : mRecordBeans) {
            recordBean.setPlaying(false);//保证在第二次点击该语音栏时当作没有“不是在播放状态”。
        }
    }

    private void resetAnim(AnimationDrawable animationDrawable) {
        if (!mAnimationDrawables.contains(animationDrawable)) {
            mAnimationDrawables.add(animationDrawable);
        }
        for (AnimationDrawable ad : mAnimationDrawables) {
            ad.selectDrawable(0);
            ad.stop();
        }
    }

    class ViewHolder {
        ImageView ieaIvVoiceLine;
        LinearLayout ieaLlSinger;
        TextView ieaTvVoicetime1;
        // ImageView ieaIvRed;
    }


    private OnRecordItemLongClick onRecordItemLongClick;

    public void setOnRecordItemLongClick(OnRecordItemLongClick onRecordItemLongClick) {
        this.onRecordItemLongClick = onRecordItemLongClick;
    }

    public interface OnRecordItemLongClick {
        void deleteRecord(int position);
    }

}
