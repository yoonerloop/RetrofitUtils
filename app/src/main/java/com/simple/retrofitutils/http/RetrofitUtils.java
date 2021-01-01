package com.simple.retrofitutils.http;

import com.simple.retrofitutils.http.api.StringApi;
import com.simple.retrofitutils.http.api.UpLoadApi;
import com.simple.retrofitutils.http.callback.DownLoadCallback;
import com.simple.retrofitutils.http.callback.ResponseCallback;
import com.simple.retrofitutils.http.callback.RetrofitStringCallback;
import com.simple.retrofitutils.http.callback.StringCallback;
import com.simple.retrofitutils.http.download.DownloadManager;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * author: style12520@163.com
 * date：2020/12/17 on 4:19 PM
 * description: http请求封装
 */
public class RetrofitUtils {

    /**
     * 通用的网络请求，返回数据是字符串或者对象，get/post
     */
    public static <T> void executeAsync(Call<T> call, ResponseCallback<T> callback) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful() && callback != null) {
                    callback.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                if (callback != null) {
                    callback.onFailure(t);
                }
            }
        });
    }

    /**
     * get：无参数
     */
    public static void get(String url, StringCallback callback) {
        Retrofit retrofit = RetrofitManager.getInstance().getStringRetrofit();
        get(retrofit, url, callback);
    }

    public static void get(Retrofit retrofit, String url, StringCallback callback) {
        StringApi baseApi = retrofit.create(StringApi.class);
        Call<String> call = baseApi.executeGet(url);
        call.enqueue(new RetrofitStringCallback(callback));
    }

    /**
     * get：有参数
     */
    public static void get(String url, Map<String, String> map, StringCallback callback) {
        Retrofit retrofit = RetrofitManager.getInstance().getStringRetrofit();
        get(retrofit, url, map, callback);
    }

    public static void get(Retrofit retrofit, String url, Map<String, String> map, StringCallback callback) {
        StringApi baseApi = retrofit.create(StringApi.class);
        Call<String> call = baseApi.executeGet(url, map);
        call.enqueue(new RetrofitStringCallback(callback));
    }


    /**
     * post：无参数
     */
    public static void post(String url, StringCallback callback) {
        Retrofit retrofit = RetrofitManager.getInstance().getStringRetrofit();
        post(retrofit, url, callback);
    }

    public static void post(Retrofit retrofit, String url, StringCallback callback) {
        StringApi baseApi = retrofit.create(StringApi.class);
        Call<String> call = baseApi.executePost(url);
        call.enqueue(new RetrofitStringCallback(callback));
    }

    /**
     * post有参数
     */
    public static void post(String url, Map<String, String> map, StringCallback callback) {
        Retrofit retrofit = RetrofitManager.getInstance().getStringRetrofit();
        post(retrofit, url, map, callback);
    }

    public static void post(Retrofit retrofit, String url, Map<String, String> map, StringCallback callback) {
        StringApi baseApi = retrofit.create(StringApi.class);
        Call<String> call = baseApi.executePost(url, map);
        call.enqueue(new RetrofitStringCallback(callback));
    }

    /**
     * 文件下载
     */
    public static void downLoadFile(String downloadUrl, DownLoadCallback callback) {
        Retrofit retrofit = RetrofitManager.getInstance().getStringRetrofit();
        downLoadFile(retrofit, downloadUrl, callback);
    }

    public static void downLoadFile(Retrofit retrofit, String downloadUrl, DownLoadCallback callback) {
        DownloadManager.getInstance().executeDownLoadAsync(retrofit, downloadUrl, callback);
    }


    public static void downLoadFile(String downloadUrl, Map<String, String> map, DownLoadCallback callback) {
        Retrofit retrofit = RetrofitManager.getInstance().getStringRetrofit();
        downLoadFile(retrofit, downloadUrl, map, callback);
    }

    public static void downLoadFile(Retrofit retrofit, String downloadUrl, Map<String, String> map, DownLoadCallback callback) {
        DownloadManager.getInstance().executeDownLoadAsync(retrofit, downloadUrl, map, callback);
    }

    /**
     * 上传文件单个
     *
     * path：文件路径
     * action：服务器接收的字段，和服务器对应
     */
    public static void upLoadFile(String url, String path, String action, StringCallback callback) {
        Retrofit retrofit = RetrofitManager.getInstance().getStringRetrofit();
        upLoadFile(retrofit, url, path, action, callback);
    }

    public static void upLoadFile(Retrofit retrofit, String url, String path, String action, StringCallback callback) {
        UpLoadApi upLoadApi = retrofit.create(UpLoadApi.class);
        File file = new File(path);
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData(action, file.getName(), body);
        Call<String> call = upLoadApi.uploadFileApi(url, part);
        call.enqueue(new RetrofitStringCallback(callback));
    }

    /**
     * 上传多个文件
     * listPath：文件路径集合
     * action：服务器接收的字段，和服务器对应
     */
    public static void upLoadFiles(String url, List<String> listPath, String action, StringCallback callback) {
        Retrofit retrofit = RetrofitManager.getInstance().getStringRetrofit();
        upLoadFiles(retrofit, url, listPath, action, callback);
    }

    public static void upLoadFiles(Retrofit retrofit, String url, List<String> listPath, String action, StringCallback callback) {
        Map<String, RequestBody> map = new HashMap<>();
        for (int i = 0; i < listPath.size(); i++) {
            File file = new File(listPath.get(i));
            RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
            map.put("" + action + "\"; filename=\"" + file.getName(), body);
        }
        UpLoadApi upLoadApi = retrofit.create(UpLoadApi.class);
        Call<String> call = upLoadApi.uploadFilesApi(url, map);
        call.enqueue(new RetrofitStringCallback(callback));
    }
}






