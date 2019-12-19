package com.example.q_kang.pmsystem.ui.activity.photo;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;

import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.BaseActivity;
import com.example.q_kang.pmsystem.modules.bean.bean.photo.SystemPicBean;
import com.example.q_kang.pmsystem.ui.adpter.photo.SystemPicFolderAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class AlbumImageActivity extends BaseActivity {
    @BindView(R.id.ib_album_back)
    ImageButton ib_back;
    @BindView(R.id.gv_album)
    GridView gv_album;

    private HashMap<String, List<String>> mGruopMap = new HashMap<>();
    private List<SystemPicBean> list = new ArrayList<>();
    private final static int SCAN_OK = 1;
    private ProgressDialog mProgressDialog;
    private SystemPicFolderAdapter adapter;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SCAN_OK:
                    //关闭进度条
                    mProgressDialog.dismiss();

                    adapter = new SystemPicFolderAdapter(AlbumImageActivity.this, list = subGroupOfImage(mGruopMap), gv_album);
                    gv_album.setAdapter(adapter);

                    break;
            }
        }

    };


    @Override
    public int bindLayout() {
        return R.layout.activity_album_image;
    }

    @Override
    public void doBusiness(Context mContext) {

        getImages();

        gv_album.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<String> childList = mGruopMap.get(list.get(position).getFolderName());

                Intent mIntent = new Intent(AlbumImageActivity.this, SystemGroupImageActivity.class);
                mIntent.putStringArrayListExtra("data", (ArrayList<String>) childList);
                startActivity(mIntent);

            }
        });


    }

    @OnClick(R.id.ib_album_back)
    public void viewOnClick() {
        finish();
    }


    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中
     */
    private void getImages() {
        //显示进度条
        mProgressDialog = ProgressDialog.show(AlbumImageActivity.this, null, "正在加载...");

        new Thread(new Runnable() {

            @Override
            public void run() {
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = AlbumImageActivity.this.getContentResolver();

                //查询图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
//                        MediaStore.Images.Media.MIME_TYPE + "=? or "
//                                + MediaStore.Images.Media.MIME_TYPE + "=?",

                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png", "image/jpg"}, MediaStore.Images.Media.DATE_MODIFIED);

                if (mCursor == null) {
                    return;
                }

                while (mCursor.moveToNext()) {
                    //获取图片的路径
                    String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));

                    //获取该图片的父路径名
                    String parentName = new File(path).getParentFile().getName();


                    //根据父路径名将图片放入到mGruopMap中
                    if (!mGruopMap.containsKey(parentName)) {
                        List<String> chileList = new ArrayList<String>();
                        chileList.add(path);
                        mGruopMap.put(parentName, chileList);
                    } else {
                        mGruopMap.get(parentName).add(path);
                    }
                }

                //通知Handler扫描图片完成
                mHandler.sendEmptyMessage(SCAN_OK);
                mCursor.close();
            }
        }).start();

    }


    /**
     * 组装分组界面GridView的数据源，因为我们扫描手机的时候将图片信息放在HashMap中
     * 所以需要遍历HashMap将数据组装成List
     *
     * @param mGruopMap
     * @return
     */
    private List<SystemPicBean> subGroupOfImage(HashMap<String, List<String>> mGruopMap) {
        if (mGruopMap.size() == 0) {
            return null;
        }
        List<SystemPicBean> list = new ArrayList<SystemPicBean>();

        Iterator<Map.Entry<String, List<String>>> it = mGruopMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, List<String>> entry = it.next();
            SystemPicBean mImageBean = new SystemPicBean();
            String key = entry.getKey();
            List<String> value = entry.getValue();

            mImageBean.setFolderName(key);
            mImageBean.setImageCounts(value.size());
            mImageBean.setFirstImagePath(value.get(0));//获取该组的第一张图片

            list.add(mImageBean);
        }

        return list;

    }
}
