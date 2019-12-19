package com.example.q_kang.pmsystem.ui.activity.news;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.BaseActivity;
import com.example.q_kang.pmsystem.modules.bean.bean.MessageNotifyData;
import com.example.q_kang.pmsystem.modules.bean.bean.NewsData;
import com.example.q_kang.pmsystem.present.im.NewsDetailPresenter;
import com.example.q_kang.pmsystem.ui.view.Utils.CommonUtils;
import com.example.q_kang.pmsystem.ui.view.Utils.LoadingDialogUtils;
import com.example.q_kang.pmsystem.view.NewsDetailView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class NewsDetailActivity extends BaseActivity implements NewsDetailView {

    @BindView(R.id.iv_news_detail_back)
    ImageView iv_back;
    @BindView(R.id.wv_news_detail)
    WebView webView;
    @BindView(R.id.tv_news_detail_title)
    TextView tv_title;
    private NewsData.DataBean newsData;
    private String url;

    private MessageNotifyData.DataBean messageNotifyData;

    private NewsDetailPresenter newsDetailPresenter;
    private int message_flag;

    private Dialog downDialog;


    @Override
    public int bindLayout() {
        return R.layout.activity_news_detail;
    }

    @Override
    public void doBusiness(Context mContext) {

        newsDetailPresenter = new NewsDetailPresenter(this);


        newsData = getIntent().getParcelableExtra("news");
        messageNotifyData = getIntent().getParcelableExtra("messagenotify");
        message_flag = getIntent().getIntExtra("flag", 0);

        if (message_flag == 10) {
            newsDetailPresenter.getNewsDetail(messageNotifyData.getId());
        } else {
            newsDetailPresenter.getNewsDetail(newsData.getId());
        }

        if (newsData != null) {
            int flag = newsData.getFlag();
            if (flag == 0) {
                tv_title.setText("新闻详情");
            } else {
                tv_title.setText("公告详情");
            }


            url = Globle.NEWS_CONTENT_URL + newsData.getId();
//            Log.i(TAG, "doBusiness url: " + url);
            loadUrl(url);

        }

    }

    private void loadUrl(String url) {

        Log.i(TAG, "loadUrl  url: " + url);
        WebSettings settings = webView.getSettings();
        webView.requestFocusFromTouch();
        settings.setJavaScriptEnabled(true);   //支持js
        settings.setAllowFileAccess(true);     //设置可以访问文件
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        settings.setSupportZoom(true); //支持缩放
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布
        settings.supportMultipleWindows(); //多窗口
        //  settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存

        settings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点 webview
        settings.setBuiltInZoomControls(true); //设置支持缩放
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片


        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


        // 加载需要显示的网页
        webView.loadUrl(url);


        //  使用
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
//                String fileName = URLUtil.guessFileName(url, contentDisposition, mimeType);
////                String destPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + fileName;
//                String destPath = txtPath + fileName;
//                // new DownloadTask().execute(url, destPath);

//                // 使用
//                DownloadCompleteReceiver receiver = new DownloadCompleteReceiver();
//                IntentFilter intentFilter = new IntentFilter();
//                intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
//                registerReceiver(receiver, intentFilter);
//
//                downloadBySystem(url, contentDisposition, mimeType);


                downDialog = LoadingDialogUtils.createLoadingDialog(NewsDetailActivity.this);
//                Log.i(TAG, "onDownloadStart url: " + url);
                String[] split = url.split("/");
                String filename = split[split.length - 1];
                File file = new File(CommonUtils.filePath + File.separator + filename);
                if (!file.exists()) {
//                    Log.i(TAG, "onDownloadStart 不存在: ");
                    CommonUtils.downloadNetFile(url);
//                    Log.i(TAG, "onDownloadStart: 下载以后 getFilePath   " + file.getAbsolutePath());
                }
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        CommonUtils.openFile(file, NewsDetailActivity.this);
                        LoadingDialogUtils.closeDialog(downDialog);
                    }
                }, 1000);

            }
        });
    }

    @OnClick({R.id.iv_news_detail_back})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_news_detail_back:
                finish();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清空所有Cookie
        CookieSyncManager.createInstance(getApplicationContext());  //Create a singleton CookieSyncManager within a context
        CookieManager cookieManager = CookieManager.getInstance(); // the singleton CookieManager instance
        cookieManager.removeAllCookie();// Removes all cookies.
        CookieSyncManager.getInstance().sync(); // forces sync manager to sync now

        webView.setWebChromeClient(null);
        webView.setWebViewClient(null);
        webView.getSettings().setJavaScriptEnabled(false);
        webView.clearCache(true);
    }

    @Override
    public void getNewsDetail(String string) {
        Log.i(TAG, "getNewsDetail string: " + string);

        NewsData.DataBean data = new Gson().fromJson(string, new TypeToken<NewsData.DataBean>() {
        }.getType());
        Log.i(TAG, "getNewsDetail newsData: " + data);
        newsData = data;


        if (data != null) {
            int flag = data.getFlag();
            if (flag == 0) {
                tv_title.setText("新闻详情");
            } else {
                tv_title.setText("公告详情");
            }


            loadUrl(Globle.NEWS_CONTENT_URL + data.getId());

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (message_flag == 10) {
            newsDetailPresenter.getNewsDetail(messageNotifyData.getId());
//            url = Globle.NEWS_CONTENT_URL + messageNotifyData.getId();
        } else {
            newsDetailPresenter.getNewsDetail(newsData.getId());
//            url = Globle.NEWS_CONTENT_URL + newsData.getId();
        }
    }





    private class DownloadCompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("onReceive. intent:{}", intent != null ? intent.toUri(0) : null);
            if (intent != null) {
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
                    long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                    Log.i("downloadId", downloadId + "");
                    DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
                    String type = downloadManager.getMimeTypeForDownloadedFile(downloadId);
                    Log.i("getMimeTypeFo", type);
                    if (TextUtils.isEmpty(type)) {
                        type = "*/*";
                    }
                    Uri uri = downloadManager.getUriForDownloadedFile(downloadId);
                    if (uri != null) {
                        Intent handlerIntent = new Intent(Intent.ACTION_VIEW);
                        handlerIntent.setDataAndType(uri, type);
                        context.startActivity(handlerIntent);
                    }
                }
            }
        }
    }



    private void downloadBySystem(String url, String contentDisposition, String mimeType) {
        // 指定下载地址
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        // 允许媒体扫描，根据下载的文件类型被加入相册、音乐等媒体库
        request.allowScanningByMediaScanner();
        // 设置通知的显示类型，下载进行时和完成后显示通知
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        // 设置通知栏的标题，如果不设置，默认使用文件名
//        request.setTitle("This is title");
        // 设置通知栏的描述
//        request.setDescription("This is description");
        // 允许在计费流量下下载
        request.setAllowedOverMetered(false);
        // 允许该记录在下载管理界面可见
        request.setVisibleInDownloadsUi(false);
        // 允许漫游时下载
        request.setAllowedOverRoaming(true);
        // 允许下载的网路类型
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        // 设置下载文件保存的路径和文件名
        String fileName = URLUtil.guessFileName(url, contentDisposition, mimeType);
        Log.i("fileName:{}", fileName);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOCUMENTS, fileName);

//        Uri uriForFile;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            uriForFile = FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID + ".fileprovider", new File(txtPath + fileName));
//        } else {
//            uriForFile = Uri.fromFile(new File(txtPath + fileName));
//        }
//        Log.i("", "downloadBySystem: " + uriForFile.getPath());
//        request.setDestinationUri(uriForFile);
//        request.setDestinationInExternalFilesDir(MainActivity.this,Environment.getExternalStorageDirectory() + File.separator + "dianwu",fileName);
        final DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        // 添加一个下载任务
        long downloadId = downloadManager.enqueue(request);
        Log.i("downloadId:{}", downloadId + "");
    }


}
