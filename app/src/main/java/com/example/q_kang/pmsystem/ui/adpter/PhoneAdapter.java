package com.example.q_kang.pmsystem.ui.adpter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.modules.bean.bean.Person;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PhoneAdapter extends BaseQuickAdapter<Person, BaseViewHolder> {

    public PhoneAdapter(@Nullable List<Person> data) {
        super(R.layout.adapter_phone, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Person item) {
        TextView tv_phone_name = helper.getView(R.id.tv_phone_name);
        TextView tv_phone_number = helper.getView(R.id.tv_phone_number);
        CircleImageView cir_phone_image = helper.getView(R.id.cir_phone_image);
        tv_phone_name.setText(item.getName());
        tv_phone_number.setText(item.getPhone());
        cir_phone_image.setImageBitmap(item.getIamge());
    }
}
