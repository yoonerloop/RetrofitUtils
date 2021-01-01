package com.simple.retrofitutils.http.download;

import android.text.TextUtils;

import com.simple.retrofitutils.http.RetrofitManager;
import com.simple.retrofitutils.http.api.DownLoadApi;
import com.simple.retrofitutils.http.callback.DownLoadCallback;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * author: style12520@163.com
 * date：2020/12/24 on 4:13 PM
 * description: 下载管理类
 */
public class DownloadManager {

    private static final ExecutorService executor = Executors.newCachedThreadPool();

    private static Map<Object, Call<ResponseBody>> callMap = new HashMap<>();

    private static DownloadManager mInstance;

    private DownloadManager() {

    }

    public static DownloadManager getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitManager.class) {
                if (mInstance == null) {
                    mInstance = new DownloadManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 文件下载
     */
    public void executeDownLoadAsync(Retrofit retrofit, String downloadUrl, DownLoadCallback callback) {
        DownLoadApi baseApi = retrofit.create(DownLoadApi.class);
        Call<ResponseBody> call = baseApi.downLoadApi(downloadUrl);
        setDownLoadTag(callback.getTag(), call);
        executor.execute(new DownloadRunnable(call, callback, executor, callMap));
    }

    public void executeDownLoadAsync(Retrofit retrofit, String downloadUrl, Map<String, String> map, DownLoadCallback callback) {
        DownLoadApi baseApi = retrofit.create(DownLoadApi.class);
        Call<ResponseBody> call = baseApi.downLoadApi(downloadUrl, map);
        setDownLoadTag(callback.getTag(), call);
        executor.execute(new DownloadRunnable(call, callback, executor, callMap));
    }

    /**
     * 设置取消下载回调
     */
    private void setDownLoadTag(String tag, Call<ResponseBody> call) {
        if (TextUtils.isEmpty(tag)) {
            return;
        }
        Call put = callMap.get(tag);
        if (put == null) {
            callMap.put(tag, call);
        }
    }

    /**
     * 取消请求
     *
     * @param tag
     */
    public void cancel(String tag) {
        if (TextUtils.isEmpty(tag)) {
            return;
        }
        Call call = callMap.get(tag);
        if (call != null) {
            callMap.remove(tag);
            if (!call.isCanceled()) {
                call.cancel();
            }
        }
    }

    /**
     * 取消所有的请求
     */
    public void cancelAll() {
        Iterator it = callMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Call<ResponseBody> call = (Call<ResponseBody>) entry.getValue();
            call.cancel();
        }
    }
}

