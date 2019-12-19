package com.example.q_kang.pmsystem.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;

import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.BaseActivity;
import com.example.q_kang.pmsystem.dao.FileDao;
import com.example.q_kang.pmsystem.dao.ImageDao;
import com.example.q_kang.pmsystem.modules.bean.bean.FileBean;
import com.example.q_kang.pmsystem.modules.bean.bean.photo.ImageItemBean;
import com.example.q_kang.pmsystem.ui.activity.photo.JCameraActivity;
import com.example.q_kang.pmsystem.ui.adpter.FileListManageAdapter;
import com.example.q_kang.pmsystem.ui.adpter.photo.ImageViewAdapter;
import com.example.q_kang.pmsystem.ui.fragment.file.AlbumPicFragment;
import com.example.q_kang.pmsystem.ui.fragment.file.FileManageFragment;
import com.example.q_kang.pmsystem.ui.view.Utils.CommonUtils;
import com.example.q_kang.pmsystem.ui.view.Utils.imageloader.ImageSelectUtils;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.leon.lfilepickerlibrary.LFilePicker;
import com.leon.lfilepickerlibrary.utils.Constant;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class FileActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.ib_file_back)
    ImageButton ibBack;
    @BindView(R.id.mic_file)
    MagicIndicator magicIndicator;
    @BindView(R.id.vp_file)
    ViewPager viewPager;

    @BindView(R.id.fam_photo)
    FloatingActionMenu fam_menu;
    @BindView(R.id.fab_camera)
    FloatingActionButton fab_camera;
    @BindView(R.id.fab_album)
    FloatingActionButton fab_album;
    @BindView(R.id.fab_file_manage_choose)
    FloatingActionButton fab_choose;
    @BindView(R.id.fab_file_sure)
    FloatingActionButton fab_sure;


    private List<String> titleList;
    private List<Fragment> fragmentList = new ArrayList<>();
    private PagerAdapter pagerAdapter;


    private boolean chooseFile;
    //
    private AlbumPicFragment albumPicFragment;
    private FileManageFragment fileManageFragment;
    private ArrayList<FileBean> fileBeans = new ArrayList<>();
    private ArrayList<ImageItemBean> imageItemBeans = new ArrayList<>();
    private static final int REQUEST_CODE = 0x00000011;



    @Override
    public int bindLayout() {
        return R.layout.activity_file;
    }

    @Override
    public void doBusiness(Context mContext) {

        chooseFile = getIntent().getBooleanExtra("choose", false);
        Log.i(TAG, "doBusiness chooseFile: " + chooseFile);

        fileBeans = (ArrayList<FileBean>) FileListManageAdapter.getFileDatas();
        imageItemBeans = ImageViewAdapter.getAttribute();

        if (chooseFile) {
            fab_sure.setVisibility(View.VISIBLE);
            fam_menu.setVisibility(View.GONE);
            fab_choose.setVisibility(View.GONE);
            Log.i(TAG, "initMagicIndicator: " + fileBeans + imageItemBeans);
//            if (null == fileBeans && null == imageItemBeans){
//                fab_sure.setVisibility(View.GONE);
//            }
        }else {
            fab_sure.setVisibility(View.GONE);
            fam_menu.setVisibility(View.VISIBLE);
//            fab_choose.setVisibility(View.VISIBLE);
        }



        //ViewPager设置
        initViewPager();


        //viewPager 标题及数据
        initMagicIndicator();

    }

    private void initViewPager() {

        fragmentList = new ArrayList<>(2);
        albumPicFragment = new AlbumPicFragment(chooseFile);
        fragmentList.add(albumPicFragment);
        fileManageFragment = new FileManageFragment(chooseFile);
        fragmentList.add(fileManageFragment);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragmentList, this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setOnPageChangeListener(this);
    }

    private void initMagicIndicator() {
        titleList = new ArrayList<>();
        titleList.add("图片");
        titleList.add("文件");


        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);//即标题平分屏幕宽度的模式
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titleList == null ? 0 : titleList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setText(titleList.get(index));
                simplePagerTitleView.setTextSize(16);
                simplePagerTitleView.setNormalColor(Color.parseColor("#88000000"));
                simplePagerTitleView.setSelectedColor(Color.BLUE);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                        switch (index) {
                            case 0:
                                viewPager.setCurrentItem(0);
                                albumPicFragment.initAlbumItemData();
                                if (!chooseFile) {
                                    fam_menu.setVisibility(View.VISIBLE);
                                    fab_choose.setVisibility(View.GONE);
                                    fab_sure.setVisibility(View.GONE);
                                }else {
                                    fab_sure.setVisibility(View.VISIBLE);
                                }
                                break;
                            case 1:
                                viewPager.setCurrentItem(1);
                                fileManageFragment.initFileData();
                                if (!chooseFile) {
                                    fab_choose.setVisibility(View.VISIBLE);
                                    fab_sure.setVisibility(View.GONE);
                                    fam_menu.setVisibility(View.GONE);
                                }else {
                                    fab_sure.setVisibility(View.VISIBLE);
                                }
                                break;
                        }
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 4));
                indicator.setLineWidth(UIUtil.dip2px(context, 30));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(Color.BLUE);
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }


    private class PagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragList;
        private Context context;

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public PagerAdapter(FragmentManager fm, List<Fragment> fragList, Context context) {
            super(fm);
            this.fragList = fragList;
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            return fragList.get(position);
        }

        @Override
        public int getCount() {
            return fragList.size();
        }


    }

    @OnClick({R.id.ib_file_back, R.id.fab_camera, R.id.fab_album, R.id.fab_file_manage_choose, R.id.fab_file_sure})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.ib_file_back:
                finish();
                break;
            case R.id.fab_camera:
                Intent cameraIntent = new Intent(FileActivity.this, JCameraActivity.class);
                startActivity(cameraIntent);
                break;
            case R.id.fab_album:
//                Intent albumIntent = new Intent(FileActivity.this, AlbumImageActivity.class);
//                startActivity(albumIntent);
                ImageSelectUtils.openPhoto(FileActivity.this, REQUEST_CODE, false, 9);
                break;
            case R.id.fab_file_manage_choose:
                new LFilePicker()
                        .withActivity(FileActivity.this)
                        .withRequestCode(CommonUtils.FILE_CHOOSE_REQUESTCODE)
                        .withIconStyle(Constant.ICON_STYLE_BLUE)
                        .withTitle("文件选择")
                        .start();
                break;
            case R.id.fab_file_sure:

                fileBeans = (ArrayList<FileBean>) FileListManageAdapter.getFileDatas();
                imageItemBeans = ImageViewAdapter.getAttribute();

                returnChooseFile();
                break;
        }
    }

    private void returnChooseFile() {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra("fileChoose", fileBeans);
        intent.putParcelableArrayListExtra("imageChoose", imageItemBeans);
        setResult(RESULT_OK, intent);
        this.finish();

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        switch (position) {
            case 0:
                viewPager.setCurrentItem(0);
                albumPicFragment.initAlbumItemData();
                if (!chooseFile) {
                    fam_menu.setVisibility(View.VISIBLE);
                    fab_sure.setVisibility(View.GONE);
                    fab_choose.setVisibility(View.GONE);
                }else {
                    fab_sure.setVisibility(View.VISIBLE);
                }
                break;
            case 1:
                viewPager.setCurrentItem(1);
                fileManageFragment.initFileData();
                if (!chooseFile) {
                    fab_choose.setVisibility(View.VISIBLE);
                    fab_sure.setVisibility(View.GONE);
                    fam_menu.setVisibility(View.GONE);
                }else {
                    fab_sure.setVisibility(View.VISIBLE);
                }
                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @SuppressLint("SimpleDateFormat")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CommonUtils.FILE_CHOOSE_REQUESTCODE) {
                List<String> list = data.getStringArrayListExtra("paths");
//                Log.i(TAG, "onActivityResult: " + list);
                for (int i = 0; i < list.size(); i++) {
                    FileBean fileBean = new FileBean();
                    String s = list.get(i);
                    File file = new File(s);
                    fileBean.setFileName(file.getName());
                    fileBean.setFilePath(file.getAbsolutePath());
                    fileBean.setChooseTime(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
                    FileDao.insertLabel(fileBean);
                }
                fileManageFragment.initFileData();
            }
        }

        if (requestCode == REQUEST_CODE && data != null) {
            ArrayList<String> images = data.getStringArrayListExtra(ImageSelectUtils.SELECT_RESULT);
            Log.i("", "onActivityResult: " + images);
            //mAdapter.refresh(images);
            for (int i = 0; i < images.size(); i++) {
                ImageItemBean imageItemBean = new ImageItemBean();
                imageItemBean.setImagePath(images.get(i));
                imageItemBean.setTime(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
                ImageDao.insertLabel(imageItemBean);
            }
            albumPicFragment.initAlbumItemData();
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        chooseFile = getIntent().getBooleanExtra("choose", false);
    }
}
