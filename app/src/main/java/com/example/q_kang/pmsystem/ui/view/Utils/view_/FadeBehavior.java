package com.example.q_kang.pmsystem.ui.view.Utils.view_;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Q-Kang on 2018/6/25.
 */

public class FadeBehavior extends FloatingActionButton.Behavior {

    /**
     * 因为是在XML中使用app:layout_behavior定义静态的这种行为,
     * 必须实现一个构造函数使布局的效果能够正常工作。
     * 否则 Could not inflate Behavior subclass error messages.
     * @param context
     * @param attrs
     */
    public FadeBehavior(Context context, AttributeSet attrs) {
        super();
    }

    /**
     * 处理垂直方向上的滚动事件
     *
     *  @param coordinatorLayout
     *  @param child
     *  @param directTargetChild
     *  @param target
     *  @param nestedScrollAxes
     *  @return
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout,
                                       FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {

        // Ensure we react to vertical scrolling
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL ||
                super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target,
                        nestedScrollAxes);
    }

    /**
     * 检查Y的位置，并决定按钮是否动画进入或退出
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     */
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
                               View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed,
                dyUnconsumed);

        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
            // User scrolled down and the FAB is currently visible -> hide the FAB
            child.setVisibility(View.INVISIBLE);
        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
            // User scrolled up and the FAB is currently not visible -> show the FAB
            child.show();
        }
    }



}
