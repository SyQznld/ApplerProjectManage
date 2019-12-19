package com.example.q_kang.pmsystem.ui.adpter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.dao.FileDao;
import com.example.q_kang.pmsystem.modules.bean.bean.FileBean;
import com.example.q_kang.pmsystem.ui.view.Utils.CommonUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileListManageAdapter extends BaseItemDraggableAdapter<FileBean, BaseViewHolder> {
    private FileListManageAdapter adapter;
    private Context context;
    private boolean state;
    private static List<FileBean> fileDatas;

    public FileListManageAdapter(Context context, boolean state, @Nullable List<FileBean> data) {
        super(R.layout.file_choose_rv_list, data);
        adapter = this;
        this.context = context;
        this.state = state;
        fileDatas = new ArrayList<>();

    }

    public static List<FileBean> getFileDatas() {
        return fileDatas;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final FileBean item) {
        helper.setText(R.id.tv_file_list_name, item.getFileName());


        helper.setOnClickListener(R.id.tv_file_list_name, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonUtils.openFile(new File(item.getFilePath()), context);
            }
        });


        helper.setOnLongClickListener(R.id.tv_file_list_name, new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new MaterialDialog.Builder(mContext)
                        .title("删除文件！")
                        .content("删除当前选择文件！")
                        .positiveText("确定")
                        .negativeText("取消")
                        .widgetColor(Color.BLUE)//不再提醒的checkbox 颜色

                        //点击事件添加 方式1
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (which == DialogAction.POSITIVE) {
                                    adapter.remove(helper.getLayoutPosition());
                                    adapter.notifyDataSetChanged();
                                    FileDao.deleteLabel(item.getId());

                                } else if (which == DialogAction.NEGATIVE) {
                                    Toast.makeText(mContext, "取消删除操作", Toast.LENGTH_LONG).show();
                                }

                            }
                        }).show();
                return false;
            }
        });

        if (state) {
            helper.setVisible(R.id.checkbox, true);

        } else {
            helper.setVisible(R.id.checkbox, false);
        }

        helper.setOnCheckedChangeListener(R.id.checkbox, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    fileDatas.add(item);
                }else {
                    fileDatas.remove(item);
                }
                Log.i(TAG, "onCheckedChanged: " + fileDatas.size() + " " + fileDatas);
            }
        });


    }


}
