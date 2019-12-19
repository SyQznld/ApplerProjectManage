package com.example.q_kang.pmsystem.base;


import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.ui.view.Utils.CommonUtils;
import com.example.q_kang.pmsystem.ui.view.Utils.Logger;
import com.example.q_kang.pmsystem.ui.view.Utils.Utils.UpgradeHelper;
import com.q_kang.pmsystem.greendao.gen.DaoMaster;
import com.q_kang.pmsystem.greendao.gen.DaoSession;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.io.File;
import java.util.LinkedList;

import cn.jpush.android.api.JPushInterface;

import static com.example.q_kang.pmsystem.ui.view.Utils.CommonUtils.writeTxtToFile;

public class Application extends android.app.Application {
    private static final String TAG = "Application";
    private Context appContext;
    private boolean isLoading = false;
    private static Application application;
    public static LinkedList<Activity> activityLinkedList;


    private static String txtPath = Environment.getExternalStorageDirectory() + File.separator + "PMSystem" + File.separator;

    private static String url;


    private static DaoSession daoSession;

    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorHui, R.color.colorH);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Logger.d(TAG, "[PmsystemApplication] onCreate");
        SDKInitializer.initialize(this);
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);  // 初始化 JPush

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        appContext = this;
        application = this;

        setupDatabase();



        String fileName = "pmsystem_url.txt";
        String strFilePath = txtPath + fileName;
        if (new File(strFilePath).exists()) {
            url = CommonUtils.ReadTxtFromSDCard(txtPath, "pmsystem_url.txt");
            setUrl(url);
        } else {

            writeTxtToFile("http://192.168.2.221:8004", txtPath, fileName);
        }

        activityLinkedList = new LinkedList<>();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                activityLinkedList.add(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                activityLinkedList.remove(activity);
            }
        });
    }

    public static void setUrl(String url) {
        Application.url = url;
    }

    public static String getUrl() {
        return url;
    }



    private void setupDatabase() {
        UpgradeHelper helper = new UpgradeHelper(appContext, "qk_pmsystem.db", null);

        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);

        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }


    public static Application getApplication() {
        return application;
    }

    public Context getAppContext() {
        return appContext;
    }


    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }

    public void exitApp() {
        Log.i("789", "exitApp: " + "容器内的Activity列表如下");
        // 先打印当前容器内的Activity列表
        for (Activity activity : activityLinkedList) {
            Log.d(TAG, activity.getLocalClassName());
        }
        Log.i("789", "exitApp: " + "正逐步退出容器内所有Activity");
        // 逐个退出Activity
        for (Activity activity : activityLinkedList) {
            activity.finish();
        }
        //  结束进程
        System.exit(0);
    }

}
