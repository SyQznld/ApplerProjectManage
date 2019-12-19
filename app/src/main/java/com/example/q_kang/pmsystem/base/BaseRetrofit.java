package com.example.q_kang.pmsystem.base;

import android.util.Log;

import com.example.q_kang.pmsystem.BuildConfig;
import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.api.apiService;
import com.example.q_kang.pmsystem.converter.ResponseConvertFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;


public class BaseRetrofit {

    private static apiService apiService;

    public static final long TIMEOUT_CONNECT = 30 * 1000;

    private String Api = Globle.URL;


    public BaseRetrofit() {
    }

    public Retrofit get() {
        Retrofit.Builder builder = new Retrofit.Builder();
        Log.i("", "get  Api: " + Api);
        builder.baseUrl(Api)
                .client(getHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ResponseConvertFactory.create());
        return builder.build();
    }

    public apiService getApiService() {
        if (apiService == null) {
            apiService = get().create(apiService.class);
        }
        return apiService;
    }


    private OkHttpClient getHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();


        builder.connectTimeout(TIMEOUT_CONNECT, TimeUnit.MILLISECONDS)
                .addInterceptor(new HttpLoggingInterceptor()
                        .setLevel(BuildConfig.DEBUG ?
                                HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE));

        return builder.build();
    }


}
