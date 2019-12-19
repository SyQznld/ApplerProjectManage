package com.example.q_kang.pmsystem.ui.fragment.file;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.BaseFragment;
import com.example.q_kang.pmsystem.dao.ImageDao;
import com.example.q_kang.pmsystem.modules.bean.bean.photo.ImageItemBean;
import com.example.q_kang.pmsystem.ui.activity.photo.JCameraActivity;
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
import butterknife.OnClick;


@SuppressLint("ValidFragment")
public class AlbumPicFragment extends BaseFragment {

    private String TAG = "AlbumPicFragment";
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

    private boolean state ;//false checkbox选择框隐藏 只是展示，true  checkbox显示 可选择


    @SuppressLint("ValidFragment")
    public AlbumPicFragment(boolean state) {
        this.state = state;

    }

    public AlbumPicFragment() {
    }

    @Override
    public int setFragView() {
        return R.layout.fragment_album_pic;
    }

    @Override
    protected void initFragView() {

        if (state ) {
            fam_menu.setVisibility(View.GONE);
            fab_sure.setVisibility(View.GONE);
            tv_photo_empty.setText("暂无可供选择的图片资源");
        } else {
            fam_menu.setVisibility(View.GONE);
            fab_sure.setVisibility(View.GONE);
        }
    }

    @Override
    protected void doFragBusiness() {

        rv_photo.setHasFixedSize(true);
        rv_photo.setLayoutManager(new LinearLayoutManager(getActivity()));

//        initAlbumItemData();

    }

    public void initAlbumItemData() {


        List<ImageItemBean> imageItemBeanList = ImageDao.queryAll();
        Log.i(TAG, "imageItemBeanList: " + imageItemBeanList.size() + "        " + imageItemBeanList);

        Set<String> dateSet = new LinkedHashSet<>();
        List<List<ImageItemBean>> imageList = new ArrayList<>();
        List<List<ImageItemBean>> imageLists = new ArrayList<>();

        if (null != imageItemBeanList && imageItemBeanList.size() > 0) {
            tv_photo_empty.setVisibility(View.GONE);
            rv_photo.setVisibility(View.VISIBLE);
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

            // Log.i(TAG, "imageLists: " + +imageLists.size() + "           11111111" + imageLists);

            photoViewAdapter = new PhotoViewAdapter(getActivity(), state, imageLists);
            rv_photo.setAdapter(photoViewAdapter);
        } else if (imageItemBeanList.size() == 0 || "[]".equals(imageItemBeanList)){
            tv_photo_empty.setVisibility(View.VISIBLE);
            rv_photo.setVisibility(View.GONE);
        }
    }






    @OnClick({R.id.fab_camera, R.id.fab_album, R.id.fab_sure})
    public void viewOnClick(View view) {
        switch (view.getId()) {

            case R.id.fab_camera:
                Intent cameraIntent = new Intent(getActivity(), JCameraActivity.class);
                startActivity(cameraIntent);

                break;
            case R.id.fab_album:
//                Intent albumIntent = new Intent(getActivity(), AlbumImageActivity.class);
//                startActivity(albumIntent);
//                Intent albumIntent = new Intent(getActivity(), ImageSelectActivity.class);
//                startActivity(albumIntent);

                // ImageSelectUtils.openPhoto(getActivity(), REQUEST_CODE, false, 9);
                break;
            case R.id.fab_sure:
//                List<ImageItemBean> imageItemBeans = ImageViewAdapter.getAttribute();
//                Log.i(TAG, "viewOnClick: " + imageItemBeans);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initAlbumItemData();
    }
}
