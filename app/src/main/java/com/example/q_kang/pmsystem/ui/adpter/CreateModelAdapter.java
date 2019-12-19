package com.example.q_kang.pmsystem.ui.adpter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.modules.bean.bean.ModelData;
import com.example.q_kang.pmsystem.ui.activity.CreadModelActivity;

import java.util.List;

public class CreateModelAdapter extends BaseQuickAdapter<ModelData.MContentListBean, BaseViewHolder> {
    CreadModelActivity activity;
    int i;

    public CreateModelAdapter(CreadModelActivity activity, @Nullable List<ModelData.MContentListBean> data, int i) {
        super(R.layout.layout_item, data);
        this.activity = activity;
        this.i = i;
    }

    @Override
    protected void convert(final BaseViewHolder helper, ModelData.MContentListBean item) {
        switch (i) {
            case Globle.MODEL_CHECK:
            case Globle.MODEL_SHOW:
                helper.setVisible(R.id.ib_add, false);
                helper.setVisible(R.id.ib_del, false);
                helper.getView(R.id.tv_name).setEnabled(false);
                break;
            case Globle.MODEL_EDIT:
                helper.setVisible(R.id.ib_add, true);
                helper.setVisible(R.id.ib_del, true);
                helper.getView(R.id.tv_name).setEnabled(true);
                break;
        }
        helper.setText(R.id.tv_position, (helper.getAdapterPosition() + 1) + "");
        helper.setText(R.id.tv_name, item.getName());
//        helper.addOnClickListener(R.id.tv_name);
        helper.setOnClickListener(R.id.tv_name, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(activity)
                        .title("编辑工作流程")
                        .content("修改工作流程名称")
                        .inputType(
                                InputType.TYPE_CLASS_TEXT
                                        | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                        | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                        .inputRange(2, 16)
                        .positiveText("完成")
                        .input(
                                "请输入新的工作流程名称",
                                item.getName(),
                                false,
                                new MaterialDialog.InputCallback() {
                                    @Override
                                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                        item.setName(input.toString());
                                        activity.RenameItem(item, helper.getAdapterPosition());
                                    }
                                })
//                (dialog, input) ->
//                                        activity.RenameItem(input.toString(), helper.getAdapterPosition()))
                        .show();

            }
        });
        helper.setOnClickListener(R.id.ib_add, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(activity)
                        .title("工作模板创建")
                        .content("添加新的工作流程")
                        .inputType(
                                InputType.TYPE_CLASS_TEXT
                                        | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                        | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                        .inputRange(2, 16)
                        .positiveText("完成")
                        .input(
                                "请输入工作流程名称",
                                "",
                                false,
                                new MaterialDialog.InputCallback() {
                                    @Override
                                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                        ModelData.MContentListBean data = new ModelData.MContentListBean();
                                        data.setName(input.toString());
                                        activity.CreateItem(data, helper.getAdapterPosition());

                                    }
                                }
//                                activity.showToast("Hello, " + input.toString() + "!")
//                                (dialog, input) -> activity.CreateItem(input.toString(), helper.getAdapterPosition())
                        )
                        .show();
//                activity.CreateItem("项目基本流程", helper.getAdapterPosition());
            }
        });
        helper.setOnClickListener(R.id.ib_del, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.delItem(helper.getAdapterPosition());
            }
        });

    }
}
