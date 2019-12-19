package com.example.q_kang.pmsystem.ui.view.Utils.Utils;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by appler on 2017/6/1.
 */

public class MainHandler extends Handler {
    private static volatile MainHandler instance;

    public static MainHandler getInstance() {
        if (null == instance) {
            synchronized (MainHandler.class) {
                if (null == instance) {
                    instance = new MainHandler();
                }
            }
        }
        return instance;
    }

    private MainHandler() {
        super(Looper.getMainLooper());
    }
}
