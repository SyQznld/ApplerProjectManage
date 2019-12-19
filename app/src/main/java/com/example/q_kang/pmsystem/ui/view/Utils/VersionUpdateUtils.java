package com.example.q_kang.pmsystem.ui.view.Utils;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.q_kang.pmsystem.base.Application;
import com.example.q_kang.pmsystem.modules.bean.bean.VersionUpdateData;
import com.jimmy.common.util.ToastUtils;

import java.io.File;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by appler on 2018/9/14.
 */

public class VersionUpdateUtils {
    public static String TAG = "VersionUpdateUtils";

//    public static VersionUpdateData versionUpdateData;

//    public static Context mContext  = Application.getApplication().getAppContext();
    public static DownloadManager mDownloadManager;
    public static long downloadId;
    public static boolean isForceUpdate = true;//是否强制升级

    /**
     * 检测软件更新
     */
    public static void checkUpdate(VersionUpdateData data, TextView textView, Activity mContext) {
        if (isUpdate(data,mContext)) {
            // 显示提示对话框
            showNoticeDialog(data,textView,mContext);
        } else {
            Toast.makeText(mContext, "当前已是最新版本", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 显示软件更新对话框
     */
    private static void showNoticeDialog(VersionUpdateData versionUpdateData,TextView textView, Activity mContext) {
        // 更新
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);

        dialog.setTitle("版本更新")
                .setMessage(versionUpdateData.getContent())
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (!CommonUtils.isNetConnected(mContext)) {
                            ToastUtils.showToast(mContext, "请确认连接网络~");
                        } else {
                            // 显示下载对话框
                            showDownloadDialog(versionUpdateData,mContext);
                            textView.setText(versionUpdateData.getVersion());
                        }

                        dialog.dismiss();
                        Toast.makeText(mContext, "正在通知栏下载中", Toast.LENGTH_SHORT).show();
                    }
                });
        if (!isForceUpdate) {
            dialog.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

        }
        dialog.setCancelable(false);
        dialog.show();


    }

    /**
     * 显示软件下载对话框
     */
    private static void showDownloadDialog(VersionUpdateData versionUpdateData, Activity mContext) {
        //安装包路径 此处模拟为百度新闻APP地址
        // String downPath = "http://gdown.baidu.com/data/wisegame/f98d235e39e29031/baiduxinwen.apk";

        String downPath = versionUpdateData.getURL();
        Log.i("versionUpdate", "downPath: " + downPath);
        //使用系统下载类
        mDownloadManager = (DownloadManager) mContext.getSystemService(DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(downPath);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedOverRoaming(false);

        //创建目录下载
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, getAppName(mContext) + ".apk");
        // 把id保存好，在接收者里面要用
        downloadId = mDownloadManager.enqueue(request);
        //设置允许使用的网络类型，这里是移动网络和wifi都可以
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        //机型适配
        request.setMimeType("application/vnd.android.package-archive");
        //通知栏显示
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle(getAppName(mContext));
        request.setDescription("正在下载中...");
        request.setVisibleInDownloadsUi(true);
        mContext.registerReceiver(mReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    /**
     * 判断是否有更新，需要跟后台产生信息交互
     *
     * @return
     */
    private static boolean isUpdate(VersionUpdateData data, Activity mContext) {
        // 获取当前软件版本
        String versionCode = getVersionCode(mContext);

        String serviceCode = data.getVersion().trim();
        Log.i("versionUpdate", "serviceCode: " + serviceCode);
        if (Float.parseFloat(serviceCode) > Float.parseFloat(versionCode)) {
            return true;
        }

        return false;
    }

    /**
     * 获取本地软件版本号
     *
     * @param context
     * @return
     */
    private static String getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkStatus();
        }
    };

    /**
     * 检查下载状态
     */
    private static void checkStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        Cursor cursor = mDownloadManager.query(query);
        if (cursor.moveToFirst()) {
            int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                //下载暂停
                case DownloadManager.STATUS_PAUSED:
                    break;
                //下载延迟
                case DownloadManager.STATUS_PENDING:
                    break;
                //正在下载
                case DownloadManager.STATUS_RUNNING:
                    break;
                //下载完成
                case DownloadManager.STATUS_SUCCESSFUL:
                    installAPK();
                    break;
                //下载失败
                case DownloadManager.STATUS_FAILED:
                    Toast.makeText(Application.getApplication().getAppContext(), "下载失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
        cursor.close();
    }

    /**
     * 7.0兼容
     */
    private static void installAPK() {
        File apkFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), getAppName(Application.getApplication().getAppContext()) + ".apk");
        Log.i("123", "installAPK: " + apkFile.getAbsolutePath());

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri apkUri = FileProvider.getUriForFile(Application.getApplication().getAppContext(), Application.getApplication().getAppContext().getPackageName() + ".fileprovider", apkFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        Application.getApplication().getAppContext().startActivity(intent);
    }


    /**
     * 获取应用程序名称
     */
    public static synchronized String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
