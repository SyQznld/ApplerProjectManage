package com.example.q_kang.pmsystem.ui.adpter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.q_kang.pmsystem.R;

import java.util.List;

/**
 * Created by appler on 2018/9/14.
 */

public class LabelHistoryAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public LabelHistoryAdapter( @Nullable List<String> data) {
        super(R.layout.label_history_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
      helper.setText(R.id.tv_history_label,item);
      helper.getView(R.id.iv_history_label_delete).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              labelItemDeleteListener.deleteLabelItem(item);
          }
      });
    }


    private LabelItemDeleteListener labelItemDeleteListener;

    public void setLabelItemDeleteListener(LabelItemDeleteListener labelItemDeleteListener) {
        this.labelItemDeleteListener = labelItemDeleteListener;
    }

    public interface LabelItemDeleteListener{
        void deleteLabelItem(String string);
    }

}
