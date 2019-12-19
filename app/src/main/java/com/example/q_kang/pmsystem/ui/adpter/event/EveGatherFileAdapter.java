package com.example.q_kang.pmsystem.ui.adpter.event;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.modules.bean.bean.FileBean;
import com.example.q_kang.pmsystem.ui.view.Utils.CommonUtils;

import java.io.File;
import java.util.List;


public class EveGatherFileAdapter extends BaseQuickAdapter<FileBean, BaseViewHolder> {
    private Context context;
    private List<FileBean> data;


    public EveGatherFileAdapter(Context context, @Nullable List<FileBean> data) {
        super(R.layout.file_event_creat_gather, data);
        this.context = context;
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, FileBean item) {
        Log.i(TAG, "initEventDetail  convert: " + item.getFileName());
        helper.setText(R.id.tv_name, item.getFileName());


        helper.getView(R.id.line).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filePath = item.getFilePath();
                if (filePath.contains(Globle.PHOTO_URL)) {
                    Log.i(TAG, "Globle.PHOTO_URL  getFilePath: " + filePath);
                    File file = new File(CommonUtils.filePath + File.separator + item.getFileName());
                    if (!file.exists()) {
                        Log.i(TAG, "onClickgetFilePath 不存在: " );
                        CommonUtils.downloadNetFile(filePath);
                        Log.i(TAG, "onClick: 下载以后 getFilePath   " + file.getAbsolutePath());
                    }
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            CommonUtils.openFile(file, context);
                        }
                    },1000);

                } else {
                    Log.i(TAG, "  getFilePath: " + filePath);
                    CommonUtils.openFile(new File(item.getFilePath()), context);
                }
            }
        });
        helper.getView(R.id.line).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onFileItemLongClick.deleteFile(helper.getLayoutPosition());
                return false;
            }
        });
    }

    private OnFileItemLongClick onFileItemLongClick;

    public void setOnFileItemLongClick(OnFileItemLongClick onFileItemLongClick) {
        this.onFileItemLongClick = onFileItemLongClick;
    }

    public interface OnFileItemLongClick {
        void deleteFile(int position);
    }
}