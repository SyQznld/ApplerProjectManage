package com.example.q_kang.pmsystem.ui.activity.photo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.BaseActivity;
import com.example.q_kang.pmsystem.modules.bean.bean.photo.ImageItemBean;
import com.example.q_kang.pmsystem.ui.adpter.photo.MyImageShowAdapter;
import com.example.q_kang.pmsystem.ui.view.Utils.view_.MyPhotoViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ImageShowActivity extends BaseActivity {

    public static final String TAG = ImageShowActivity.class.getSimpleName();
    @BindView(R.id.ib_image_show_back)
    ImageButton ibBack;
    @BindView(R.id.vp_show_image)
    MyPhotoViewPager myPhotoViewPager;
    @BindView(R.id.tv_image_position)
    TextView tvPosition;
    @BindView(R.id.tv_image_count)
    TextView tvCount;

    private int currentPosition;
    private MyImageShowAdapter adapter;

    private List<String> list = new ArrayList<>(); //当前选择的所有图片

    private List<ImageItemBean> imageItemBeanArrayList;
    private String flag;


    @Override
    public int bindLayout() {
        return R.layout.activity_image_show;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void doBusiness(Context mContext) {

        flag = getIntent().getStringExtra("flag");
        currentPosition = getIntent().getIntExtra("position", 0);
        if ("string".equals(flag)) {
            list = getIntent().getStringArrayListExtra("data");
        } else if ("bean".equals(flag)) {
            imageItemBeanArrayList = getIntent().getParcelableArrayListExtra("data");
            Log.i(TAG, "imageItemBeanArrayList: " + imageItemBeanArrayList);
            for (int i = 0; i < imageItemBeanArrayList.size(); i++) {
                String imagePath = imageItemBeanArrayList.get(i).getImagePath();
                list.add(imagePath);
            }
        }

        Log.i(TAG, "list: " + list);
        adapter = new MyImageShowAdapter(list, this);
        myPhotoViewPager.setAdapter(adapter);
        myPhotoViewPager.setCurrentItem(currentPosition, false);
        tvPosition.setText(currentPosition + 1 + "");
        tvCount.setText(list.size() + "");
        myPhotoViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPosition = position;
                tvPosition.setText(currentPosition + 1 + "");
                tvCount.setText(list.size() + "");
            }
        });
    }


    @OnClick(R.id.ib_image_show_back)
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.ib_image_show_back:
                finish();
                break;
        }
    }


}
