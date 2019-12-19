package com.example.q_kang.pmsystem.ui.view.Utils;

import android.util.Log;

import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.modules.bean.bean.ReturnFilePath;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by appler on 2018/7/10.
 */

public class FileUploadHttps {

    private String TAG = "FileUploadHttps";

    private static FileUploadHttps instance;

    public static FileUploadHttps getInstance() {
        if (null == instance) {
            synchronized (FileUploadHttps.class) {
                if (null == instance) {
                    instance = new FileUploadHttps();
                }
            }
        }
        return instance;
    }

    private FileUploadHttps() {

    }


    public interface FileUploadCallBack {
        void upFailure();

        void upLoadSuccess(ReturnFilePath returnFilePath);
    }


    public void requestFileUpload(String filePath, FileUploadCallBack fileUploadCallBack) {
        OkHttpClient okHttpClient = new OkHttpClient();
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);

        Log.i("requestFileUpload", "imagefilePath: " + filePath);


        if (!"".equals(filePath)) {
            Gson gson = new Gson();
            List<String> videoPath = gson.fromJson(filePath, new TypeToken<List<String>>() {
            }.getType());
            if (videoPath != null) {
                for (int i = 0; i < videoPath.size(); i++) {
                    File file = new File(videoPath.get(i));
                    Log.i("path", "requestFileUpload  videopath: " + videoPath.get(i));

                    if (!videoPath.get(i).startsWith(Globle.PHOTO_URL)) {
                        RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), file);
                        CountingRequestBody countingRequestBody = new CountingRequestBody(body, new CountingRequestBody.Listener() {
                            @Override
                            public void onRequestProgress(long byteWritted, long contentLength) {
                                Log.d("video", "进度 ：" + byteWritted + "/" + contentLength);

                            }
                        });
                        String filename = file.getName();
                        Log.i("requestFileUpload", "UpdataReportData: " + filename);
                        requestBody.addFormDataPart("file", filename, countingRequestBody);
                    }else {

                    }
                }
            }
        }

        Log.i(TAG, "requestFileUpload   1111: " + Globle.URL + "Post_Upload");
        Request request = new Request.Builder()
                .url(Globle.URL + "Post_Upload")
                .post(requestBody.build())
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.i(TAG, "onFailure: " + e.getMessage());
                fileUploadCallBack.upFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.i("requestFileUpload", "onResponse: " + string);
                switch (string) {
                    default:
                        ReturnFilePath returnFilePath = new Gson().fromJson(string, new TypeToken<ReturnFilePath>() {
                        }.getType());
                        fileUploadCallBack.upLoadSuccess(returnFilePath);
                        break;
                }
            }
        });


    }
}
