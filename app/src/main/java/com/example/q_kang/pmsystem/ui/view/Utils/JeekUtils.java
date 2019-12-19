package com.example.q_kang.pmsystem.ui.view.Utils;


import com.example.q_kang.pmsystem.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Jimmy on 2016/10/10 0010.
 */
public class JeekUtils {

    public static String timeStamp2Time(long time) {
        return new SimpleDateFormat("HH:mm", Locale.CHINA).format(new Date(time));
    }

    public static int getEventSetColor(int color) {
        switch (color) {
            case 0:
                return R.color.holiday_text_color;
            case 1:
                return R.color.color_schedule_blue;
            case 2:
                return R.color.color_schedule_green;
            case 3:
                return R.color.color_schedule_pink;
            case 4:
                return R.color.color_schedule_orange;
            case 5:
                return R.color.color_schedule_yellow;
            default:
                return R.color.holiday_text_color;
        }
    }



}
