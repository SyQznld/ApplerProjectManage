package com.example.q_kang.pmsystem.ui.view.Utils.view_;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by appler on 2018/5/28.
 */

public class MyPhotoViewPager extends ViewPager {
    public MyPhotoViewPager(Context context) {
        super(context);
    }

    public MyPhotoViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }
}