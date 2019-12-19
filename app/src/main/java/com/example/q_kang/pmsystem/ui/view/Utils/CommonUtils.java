package com.example.q_kang.pmsystem.ui.view.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.text.Html;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;

import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.api.apiService;
import com.example.q_kang.pmsystem.base.Application;
import com.example.q_kang.pmsystem.modules.bean.bean.ReturnFilePath;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jimmy.common.util.ToastUtils;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CommonUtils {
    public static String TAG = "CommonUtils";

    public static int FILE_CHOOSE_REQUESTCODE = 1111;//从文件选择器选择文件
    public static int RECORD_CHOOSE_REQUESTCODE = 1110;//从文件选择器选择录音

    public static int IMAGE_CAMERA_REQUESTCODE = 1112;//摄像头拍照

    public static int PROJECT_MAP_LOCATION_CODE = 11;//新建项目 地图定位
    public static int PROJECT_CHOOSE_PERSON_FUZEREN = 22;//新建项目  选择负责人
    public static int PROJECT_CHOOSE_PERSON_MEMBER = 33;//新建项目  选择参与人员
    public static int MODEL_CHOOSE_WORK_FUZEREN = 44;//新建项目  选择模板后，每个工作选择负责人
    public static int MODEL_CHOOSE_EVENT_FUZEREN = 55;//新建工作  选择模板后，每个事件选择负责人


    public static int WORK_CREATE_CHOOSE_PROJECT = 1000;//工作列表中新建工作，选择所属项目


    public static int EVENT_CHOOSE_FILE = 1001;//新建事件 选择文件
    public static int EVENT_CREATE_CHOOSE_WORK = 1002;//新建事件 选择文件


    public static int EVENT_EDIT = 1003;//事件编辑 跳转新建事件页面


    public static String videoPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "PMSystem" + File.separator + "video";

    public static String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "PMSystem" + File.separator + "file";

    /**
     * 获取SD卡路径
     *
     * @return
     */
    public static String getSDPath() {
        String sdPath = null;
        // 判断sd卡是否存在
        boolean sdCardExit = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (sdCardExit) {
            // 获取根目录
            sdPath = Environment.getExternalStorageDirectory().toString();
        }
        return sdPath;
    }


    public static String getUUID32() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }

    public static void saveBitmap(int taketype, String path, Bitmap mBitmap) {
        Bitmap bMapRotate;
        if (taketype == 1) {  //坚拍
            Matrix matrix = new Matrix();
            matrix.reset();
            matrix.postRotate(90);
            bMapRotate = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(),
                    mBitmap.getHeight(), matrix, true);
            mBitmap = bMapRotate;
        }
        try {
            FileOutputStream fos = new FileOutputStream(path);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static Date str2long(String s) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = null;
        try {
            date = format.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    //    public static final String DATETYPE = "yyyy-MM-dd HH:mm";
    public static final String DATETYPE = "yyyy-MM-dd";


    public static String getNowTime() {
        String nowTime = CommonUtils.getDate(DATETYPE);//2018-06-25 14:58:22
        CalendarDay calendarDay = new CalendarDay(CommonUtils.str2long(nowTime));
        String format = CommonUtils.FORMATTER.format(calendarDay.getDate());
        return format;
    }

    public static String getDate(String type) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(type);
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }


    public static void call(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    public static void sendMsg(Context context, String phone) {

        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phone));
        intent.putExtra("sms_body", "");
        context.startActivity(intent);
    }


    public static boolean isNetConnected(Context context) {
        // 得到连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }


    public static  String textToHtml(String content) {
        SpannableString spanString = new SpannableString(content);
        String html = Html.toHtml(spanString);
        String html_string = parseUnicodeToStr(html);
        return html_string;
    }


    /**
     * unicode转String
     * Html.toHtml()这个方法会将EditText里面的中文编码为Unicode十进制码，web浏览器能够正常识别，但是如果在非浏览器中显示的话就存在问题了。
     * 在Html.toHtml()转码后，通过正则将转码之后的Unicode再次转为String
     */
    public static String parseUnicodeToStr(String unicodeStr) {
        String regExp = "&#\\d*;";
        Matcher m = Pattern.compile(regExp).matcher(unicodeStr);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String s = m.group(0);
            s = s.replaceAll("(&#)|;", "");//如果上传html样式的文本内容，文本中有&nbsp 字样空格 浏览器识别不了 需如下替换
            char c = (char) Integer.parseInt(s);
            m.appendReplacement(sb, Character.toString(c));
        }
        m.appendTail(sb);
        return sb.toString();
    }


    /**
     * 连接网络获得相对应的图片
     *
     * @param imageUrl
     * @return
     */
    public static Drawable getImageNetwork(String imageUrl) {
        URL myFileUrl = null;
        Drawable drawable = null;
        try {
            myFileUrl = new URL(Globle.PHOTO_URL + imageUrl);
            Log.i(TAG, "getImageNetwork imageUrl: " + Globle.PHOTO_URL + imageUrl);

            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            // 在这一步最好先将图片进行压缩，避免消耗内存过多
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            drawable = new BitmapDrawable(bitmap);

            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drawable;
    }


    //Retrofit  上传附件 返回路径
    public static List<String> returnFilePath(List<String> list) {
        List<String> pathList = new ArrayList<>();
        //创建Retrofit
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Globle.URL).addConverterFactory(GsonConverterFactory.create()).build();
        ////通过动态代理的方式得到网络接口对象
        apiService apiService = retrofit.create(apiService.class);

        //图片文件
        File file = null;
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            file = new File(s);

            Log.i("", "viewOnClick: " + file.getName());

            //创建RequestBody
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/otcet-stream"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            Call<ResponseBody> call = apiService.returnFilePath(body);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    try {
                        String string = response.body().string();
                        Log.i(TAG, "onResponse: " + string);
                        ReturnFilePath returnFilePath = new Gson().fromJson(string, new TypeToken<ReturnFilePath>() {
                        }.getType());

                        Log.i(TAG, "onResponse returnFilePath: " + returnFilePath);
                        String src = returnFilePath.getSrc();
                        pathList.add(src);
                        Log.i(TAG, "onClick=====   path: " + pathList);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.i(TAG, "onResponse failure: " + t.getMessage());
                }
            });
            Log.i(TAG, "returnFilePath: " + pathList);
        }
        return pathList;
    }


    public static void openFile(File file, Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);  //设置intent的Action属性
        String type = getMIMEType(file);  //获取文件file的MIME类型
        intent.setDataAndType(/*uri*/Uri.fromFile(file), type);   //设置intent的data和Type属性。

        context.startActivity(intent);     //比如说你的MIME类型是打开邮箱，但是你手机里面没装邮箱客户端，就会报错。
    }


    //根据时间长短计算语音条宽度:220dp
    public synchronized static int getVoiceLineWight(Context context, int seconds) {
        //1-2s是最短的。2-10s每秒增加一个单位。10-60s每10s增加一个单位。
        if (seconds <= 2) {
            return dip2px(context, 90);
        } else if (seconds <= 10) {
            //90~170
            return dip2px(context, 90 + 8 * seconds);
        } else {
            //170~220
            return dip2px(context, 170 + 10 * (seconds / 10));

        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 格式化时间展示为05’10”
     */
    @SuppressLint("DefaultLocale")
    public static String formatRecordTime(long recTime, long maxRecordTime) {
        int time = (int) ((maxRecordTime - recTime) / 1000);
        int minute = time / 60;
        int second = time % 60;
        //return String.format("%2d’%2d”", minute, second);
        return String.format("%2d:%2d", minute, second);
    }

    public static String formatTime(int recTime) {
        int minute = recTime / 60;
        int second = recTime % 60;
        return String.format("%2d’%2d”", minute, second);
    }


    public static String ReadTxtFromSDCard(String txtPath, String filename) {

        StringBuilder sb = new StringBuilder("");
        //判断是否有读取权限
        if (Environment.getExternalStorageState().
                equals(Environment.MEDIA_MOUNTED)) {

            //打开文件输入流
            try {
                FileInputStream input = new FileInputStream(txtPath + filename);
                byte[] temp = new byte[1024];

                int len = 0;
                //读取文件内容:
                while ((len = input.read(temp)) > 0) {
                    sb.append(new String(temp, 0, len));
                }
                //关闭输入流
                input.close();
            } catch (java.io.IOException e) {
                Log.e("ReadTxtFromSDCard", "ReadTxtFromSDCard");
                e.printStackTrace();
            }

        }
        return sb.toString();
    }


    // 将字符串写入到文本文件中
    public static void writeTxtToFile(String strcontent, String filePath, String fileName) {
        //生成文件夹之后，再生成文件，不然会出错
        makeFilePath(filePath, fileName);

        // 每次写入时，都换行写
//        String strContent = strcontent + "\r\n";
        String strContent = strcontent + "\r";
        try {
            File file = new File(filePath + fileName);
            if (!file.exists()) {
                Log.d("TestFile", "Create the file:" + filePath + fileName);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            Log.e("TestFile", "Error on write File:" + e);
        }
    }


    // 生成文件
    public static File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    // 生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e + "");
        }
    }


    public static Bitmap getViewBitmap(View addViewContent) {

        addViewContent.setDrawingCacheEnabled(true);
        addViewContent.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        addViewContent.layout(0, 0,
                addViewContent.getMeasuredWidth(),
                addViewContent.getMeasuredHeight());
        addViewContent.buildDrawingCache();

        Bitmap cacheBitmap = addViewContent.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        return bitmap;
    }


    private static String getMIMEType(File file) {

        String type = "*/*";
        String fName = file.getName();
        int dotIndex = fName.lastIndexOf(".");  //获取后缀名前的分隔符"."在fName中的位置。
        if (dotIndex < 0) {
            return type;
        }

        String end = fName.substring(dotIndex, fName.length()).toLowerCase();  /* 获取文件的后缀名*/
        if (end == "") return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) { //MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }


    public static void downloadNetFile(String url) {
        //下载路径，如果路径无效了，可换成你的下载路径
        // final String url = "http://192.168.2.193:8002/Upload/2018年08月01日 08时37分32秒/【离】员工离职交接手续表.xlsx";
        if (!new File(filePath).exists()) {
            makeRootDirectory(filePath);
        }
        final long startTime = System.currentTimeMillis();
        Log.i("DOWNLOAD", "startTime=" + startTime);
        Request request = new Request.Builder().url(url).build();
        new OkHttpClient().newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                // 下载失败
                e.printStackTrace();
                ToastUtils.showToast(Application.getApplication().getAppContext(), "获取文件失败~");
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                Sink sink = null;
                BufferedSink bufferedSink = null;
                try {
                    File dest = new File(filePath, url.substring(url.lastIndexOf("/") + 1));
                    Log.i("DOWNLOAD", "dest.getAbsolutePath(): " + dest.getAbsolutePath());


                    sink = Okio.sink(dest);
                    bufferedSink = Okio.buffer(sink);
                    bufferedSink.writeAll(response.body().source());
                    bufferedSink.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bufferedSink != null) {
                        bufferedSink.close();
                    }
                }
            }
        });


    }


    public static String initPath() {
        File parentPath = Environment.getExternalStorageDirectory();
        String storagePath = "";
        String DST_FOLDER_NAME = "PMSystem";
        if (storagePath.equals("")) {
            storagePath = parentPath.getAbsolutePath() + File.separator + DST_FOLDER_NAME + File.separator + "image";
            File f = new File(storagePath);
            if (!f.exists()) {
                f.mkdir();
            }
        }
        return storagePath;
    }

    public static String saveBitmap(Bitmap b) {
        String path = initPath();
        long dataTake = System.currentTimeMillis();
        String jpegName = path + File.separator + "JCamara" + dataTake + ".jpg";
        try {
            FileOutputStream fout = new FileOutputStream(jpegName);
            BufferedOutputStream bos = new BufferedOutputStream(fout);
            b.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            return jpegName;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }


    public static void deleteFile(String filePath) {
        if (filePath == null || "".equals(filePath)) {
            return;
        }
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }


    private final static String[][] MIME_MapTable = {
            //{后缀名，MIME类型}
            {".3gp", "video/3gpp"},
            {".apk", "application/vnd.android.package-archive"},
            {".asf", "video/x-ms-asf"},
            {".avi", "video/x-msvideo"},
            {".bin", "application/octet-stream"},
            {".bmp", "image/bmp"},
            {".c", "text/plain"},
            {".class", "application/octet-stream"},
            {".conf", "text/plain"},
            {".cpp", "text/plain"},
            {".doc", "application/msword"},
            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".exe", "application/octet-stream"},
            {".gif", "image/gif"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h", "text/plain"},
            {".htm", "text/html"},
            {".html", "text/html"},
            {".jar", "application/java-archive"},
            {".java", "text/plain"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".js", "application/x-javascript"},
            {".log", "text/plain"},
            {".m3u", "audio/x-mpegurl"},
            {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"},
            {".m4u", "video/vnd.mpegurl"},
            {".m4v", "video/x-m4v"},
            {".mov", "video/quicktime"},
            {".mp2", "audio/x-mpeg"},
            {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"},
            {".mpc", "application/vnd.mpohun.certificate"},
            {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"},
            {".mpg4", "video/mp4"},
            {".mpga", "audio/mpeg"},
            {".msg", "application/vnd.ms-outlook"},
            {".ogg", "audio/ogg"},
            {".pdf", "application/pdf"},
            {".png", "image/png"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".prop", "text/plain"},
            {".rc", "text/plain"},
            {".rmvb", "audio/x-pn-realaudio"},
            {".rtf", "application/rtf"},
            {".sh", "text/plain"},
            {".tar", "application/x-tar"},
            {".tgz", "application/x-compressed"},
            {".txt", "text/plain"},
            {".wav", "audio/x-wav"},
            {".wma", "audio/x-ms-wma"},
            {".wmv", "audio/x-ms-wmv"},
            {".wps", "application/vnd.ms-works"},
            {".xml", "text/plain"},
            {".z", "application/x-compress"},
            {".zip", "application/x-zip-compressed"},
            {"", "*/*"}
    };

}
