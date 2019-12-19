package com.example.q_kang.pmsystem.converter;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;


public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private static final String TAG = "GsonResponseBodyConvert";
    private final Gson gson;
    private final Type type;

    GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String a = value.string();
        String response = a.substring(1, a.length() - 1);
        Log.i(TAG, "convert: " + response);
//        Log.i(TAG, "convert: "+response);
//        Log.i(TAG, "convert: "+type);
        return gson.fromJson(response, type);
    }
}


