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
import com.example.q_kang.pmsystem.modules.bean.bean.EventModelData;
import com.example.q_kang.pmsystem.ui.activity.CreadModelActivity;

import java.util.List;

public class CreateEventModelAdapter extends BaseQuickAdapter<EventModelData.DataBean.TempletsBean, BaseViewHolder> {
    CreadModelActivity activity;
    int i;

    public CreateEventModelAdapter(CreadModelActivity activity, @Nullable List<EventModelData.DataBean.TempletsBean> data, int i) {
        super(R.layout.layout_item, data);
        this.activity = activity;
        this.i = i;
    }

    @Override
    protected void convert(final BaseViewHolder helper, EventModelData.DataBean.TempletsBean item) {
        switch (i) {
            case Globle.EVENT_MODEL_CHECK:
            case Globle.EVENT_MODEL_SHOW:
                helper.setVisible(R.id.ib_add, false);
                helper.setVisible(R.id.ib_del, false);
                helper.getView(R.id.tv_name).setEnabled(false);
                break;
            case Globle.EVENT_MODEL_EDIT:
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
                        .title("编辑事件流程")
                        .content("修改事件流程名称")
                        .inputType(
                                InputType.TYPE_CLASS_TEXT
                                        | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                        | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                        .inputRange(2, 16)
                        .positiveText("完成")
                        .input(
                                "请输入新的事件流程名称",
                                item.getName(),
                                false,
                                new MaterialDialog.InputCallback() {
                                    @Override
                                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                        item.setName(input.toString());
                                        activity.RenameEventMB(item, helper.getAdapterPosition());
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
                        .title("事件模板创建")
                        .content("添加新的事件流程")
                        .inputType(
                                InputType.TYPE_CLASS_TEXT
                                        | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                        | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                        .inputRange(2, 16)
                        .positiveText("完成")
                        .input(
                                "请输入事件流程名称",
                                "",
                                false,
                                new MaterialDialog.InputCallback() {
                                    @Override
                                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                        EventModelData.DataBean.TempletsBean data = new EventModelData.DataBean.TempletsBean();
                                        data.setName(input.toString());
                                        activity.CreateEventMB(data, helper.getAdapterPosition());

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
                activity.delEventMBItem(helper.getAdapterPosition());
            }
        });

    }
}
