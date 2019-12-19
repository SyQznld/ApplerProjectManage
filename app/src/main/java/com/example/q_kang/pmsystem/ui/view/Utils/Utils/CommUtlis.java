package com.example.q_kang.pmsystem.ui.view.Utils.Utils;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.ui.view.Utils.SpinerPopWindow;

import java.util.List;

public class CommUtlis {

    private static SpinerPopWindow<Object> spinerPopWindow;

    public static void SetSinnpers(final List<String> item, final TextView textView, final Context context, final TvLisenter lisenter) {

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(item.get(position));
                lisenter.OnClick(position);
                spinerPopWindow.dismiss();
                CommUtlis.setTextImage(R.drawable.arrow, textView, context);

            }
        };
        spinerPopWindow = new SpinerPopWindow<>(context, item, itemClickListener);
        spinerPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                CommUtlis.setTextImage(R.drawable.arrow, textView, context);
            }
        });
        spinerPopWindow.setWidth(textView.getWidth());
        spinerPopWindow.showAsDropDown(textView);
        CommUtlis.setTextImage(R.drawable.arrow2, textView, context);
    }

    public static void setTextImage(int resId, TextView textView, Context context) {
        Drawable drawable = context.getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 必须设置图片大小，否则不显示
        textView.setCompoundDrawables(null, null, drawable, null);
    }

    public interface TvLisenter {
        void OnClick(int position);
    }
}
