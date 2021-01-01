package com.simple.retrofitutils.http.callback;

/**
 * author: style12520@163.com
 * date：2020/12/24 on 3:57 PM
 * description: 下载回调
 */
public interface DownLoadListener {

     void onStart();

     void progress(int progress, float currentSize, float totalSize);

     void onFinish(String path);

     void onFailure(String msg);

     void onCancel(String tag);

}
