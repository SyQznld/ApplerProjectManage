package com.example.q_kang.pmsystem.ui.activity.photo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.BaseActivity;
import com.example.q_kang.pmsystem.dao.ImageDao;
import com.example.q_kang.pmsystem.modules.bean.bean.photo.ImageItemBean;
import com.example.q_kang.pmsystem.ui.adpter.photo.ImageViewAdapter;
import com.example.q_kang.pmsystem.ui.adpter.photo.PhotoViewAdapter;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhotoAlbumActivity extends BaseActivity {

    @BindView(R.id.ib_photo_back)
    ImageButton ibBack;
    @BindView(R.id.fam_photo)
    FloatingActionMenu fam_menu;
    @BindView(R.id.fab_camera)
    FloatingActionButton fab_camera;
    @BindView(R.id.fab_album)
    FloatingActionButton fab_album;
    @BindView(R.id.fab_sure)
    FloatingActionButton fab_sure;

    @BindView(R.id.rv_photo)
    RecyclerView rv_photo;
    @BindView(R.id.tv_photo_empty)
    TextView tv_photo_empty;

    private PhotoViewAdapter photoViewAdapter;
    private List<ImageItemBean> imageItemBeanList;
    private boolean state = false;//false checkbox选择框隐藏 只是展示，true  checkbox显示 可选择


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_photo_album;
    }

    @Override
    public void doBusiness(Context mContext) {

        rv_photo.setHasFixedSize(true);
        rv_photo.setLayoutManager(new LinearLayoutManager(mContext));

        initImageItemData(mContext);

        if (state == true) {
            fam_menu.setVisibility(View.GONE);
            fab_sure.setVisibility(View.VISIBLE);
        } else {
            fam_menu.setVisibility(View.VISIBLE);
            fab_sure.setVisibility(View.GONE);
        }


    }

    private void initImageItemData(Context mContext) {
        Set<String> dateSet = new LinkedHashSet<>();
        List<List<ImageItemBean>> imageList = new ArrayList<>();
        List<List<ImageItemBean>> imageLists = new ArrayList<>();
        imageItemBeanList = ImageDao.queryAll();
          Log.i(TAG, "imageItemBeanList: " + imageItemBeanList.size() + "        "  +imageItemBeanList);
        if (null != imageItemBeanList && imageItemBeanList.size() > 0) {
            tv_photo_empty.setVisibility(View.GONE);
            for (int i = 0; i < imageItemBeanList.size(); i++) {
                String imagePath = imageItemBeanList.get(i).getImagePath();
                File file = new File(imagePath);
                if (!(file.exists())) {
                    ImageDao.deleteLabel(imageItemBeanList.get(i).getId());
                } else {
                    dateSet.add(imageItemBeanList.get(i).getTime());
                }
            }

            for (Iterator<String> iterator = dateSet.iterator(); iterator.hasNext(); ) {
                String time = iterator.next();
                List<ImageItemBean> imageItemBeans = ImageDao.queryTimer(time);
                // Log.i(TAG, "imageItemBeans: " + imageItemBeans.size());
                imageList.add(imageItemBeans);
            }
            for (int i = imageList.size() - 1; i >= 0; i--) {
                imageLists.add(imageList.get(i));
            }

            if (photoViewAdapter != null) {
                photoViewAdapter = null;
            }

            Log.i(TAG, "imageLists: " + + imageLists.size() + "           11111111" + imageLists);

            photoViewAdapter = new PhotoViewAdapter(mContext, state, imageLists);
            rv_photo.setAdapter(photoViewAdapter);
        } else {
            tv_photo_empty.setVisibility(View.VISIBLE);
        }

    }


    @OnClick({R.id.ib_photo_back, R.id.fab_camera, R.id.fab_album, R.id.fab_sure})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.ib_photo_back:
                finish();
                break;
            case R.id.fab_camera:
                Intent cameraIntent = new Intent(PhotoAlbumActivity.this, JCameraActivity.class);
                startActivity(cameraIntent);
                break;
            case R.id.fab_album:
                Intent albumIntent = new Intent(PhotoAlbumActivity.this, AlbumImageActivity.class);
                startActivity(albumIntent);
                break;
            case R.id.fab_sure:
                List<ImageItemBean> imageItemBeans = ImageViewAdapter.getAttribute();
                Log.i(TAG, "viewOnClick: " + imageItemBeans);
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        initImageItemData(PhotoAlbumActivity.this);
    }
}
