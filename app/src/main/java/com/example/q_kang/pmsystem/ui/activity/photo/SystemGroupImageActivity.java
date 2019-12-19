package com.example.q_kang.pmsystem.ui.activity.photo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.BaseActivity;
import com.example.q_kang.pmsystem.ui.adpter.photo.SystemChildAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SystemGroupImageActivity extends BaseActivity {

    @BindView(R.id.ib_system_all_back)
    ImageButton ibBack;
    @BindView(R.id.tv_select)
    TextView tv_select_all;
    @BindView(R.id.tv_cancel_select)
    TextView tv_cancel_select;
    @BindView(R.id.tv_inverse_select)
    TextView tv_inverse;
    @BindView(R.id.tv_complete)
    TextView tv_complete;
    @BindView(R.id.gv_system_all)
    GridView gvAllImage;

    private List<String> list;
    private SystemChildAdapter adapter;

    private int state = 0;


    @Override
    public int bindLayout() {
        return R.layout.activity_system_group_image;
    }

    @Override
    public void doBusiness(Context mContext) {

        list = getIntent().getStringArrayListExtra("data");//路径
        Log.i(TAG, "doBusiness: " + list.size() + list);

        adapter = new SystemChildAdapter(this, list, gvAllImage, state, tv_complete);
        gvAllImage.setAdapter(adapter);

        gvAllImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(mContext, ImageShowActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("flag", "string");
                intent.putStringArrayListExtra("data", (ArrayList<String>) list);
                startActivity(intent);
            }
        });

    }

    @OnClick({R.id.ib_system_all_back, R.id.tv_select, R.id.tv_cancel_select, R.id.tv_complete, R.id.tv_inverse_select})
    public void viewOnClick(View view) {
        int selectCount = adapter.getSelectItems().size();
        switch (view.getId()) {
            case R.id.ib_system_all_back:
                Toast.makeText(this, "选中了" + selectCount + "张图片", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.tv_select://全选
                state = 1;
                adapter = new SystemChildAdapter(this, list, gvAllImage, state, tv_complete);
                gvAllImage.setAdapter(adapter);
                break;
            case R.id.tv_cancel_select://取消全选
                state = 0;
                adapter = new SystemChildAdapter(this, list, gvAllImage, state, tv_complete);
                gvAllImage.setAdapter(adapter);
                break;//反选
            case R.id.tv_inverse_select:
                state = 2;
                adapter = new SystemChildAdapter(this, list, gvAllImage, state, tv_complete);
                gvAllImage.setAdapter(adapter);
                break;

        }
    }


}
