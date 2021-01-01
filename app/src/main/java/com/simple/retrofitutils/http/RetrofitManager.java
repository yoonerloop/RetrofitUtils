package com.simple.retrofitutils.http;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * author: style12520@163.com
 * date：2020/12/17 on 11:35 AM
 * description:  Retrofit管理类
 */
public class RetrofitManager {

    private static String baseUrl;

    private static RetrofitManager mInstance;

    private static Map<String, Retrofit> retrofitMap = new HashMap<>();

    private RetrofitManager() {

    }

    public static RetrofitManager getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitManager.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 返回全局对象
     *
     * 解析为对象
     */
    public Retrofit getRetrofit() {
        checkBaseUrl();
        Retrofit retrofit = retrofitMap.get(baseUrl);
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            retrofitMap.put(baseUrl, retrofit);
        }
        return retrofit;
    }

    /**
     * 返回局部对象
     *
     * 解析为对象
     */
    public Retrofit getRetrofit(String baseUrl) {
        checkBaseUrl(baseUrl);
        Retrofit retrofit = retrofitMap.get(baseUrl);
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            retrofitMap.put(baseUrl, retrofit);
        }
        return retrofit;
    }

    /**
     *  返回全局对象
     *
     *  解析为字符串
     */
    public Retrofit getStringRetrofit() {
        checkBaseUrl();
        Retrofit retrofit = retrofitMap.get(baseUrl);
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
            retrofitMap.put(baseUrl, retrofit);
        }
        return retrofit;
    }

    /**
     * 返回局部对象
     *
     * 解析为字符串
     */
    public Retrofit getStringRetrofit(String baseUrl) {
        checkBaseUrl();
        Retrofit retrofit = retrofitMap.get(baseUrl);
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
            retrofitMap.put(baseUrl, retrofit);
        }
        return retrofit;
    }

    private void checkBaseUrl(String baseUrl) {
        if (TextUtils.isEmpty(baseUrl)) {
            throw new NullPointerException("baseUrl is null");
        }
    }


    private void checkBaseUrl() {
        if (TextUtils.isEmpty(baseUrl)) {
            throw new NullPointerException("please set setBaseUrl first：RetrofitManager.getInstance().setBaseUrl(url)");
        }
    }

    public  String getBaseUrl() {
        return baseUrl;
    }

    /**
     * 设置全局的url
     * @param baseUrl
     * @return
     */
    public  RetrofitManager setBaseUrl(String baseUrl) {
        RetrofitManager.baseUrl = baseUrl;
        return this;
    }
}
