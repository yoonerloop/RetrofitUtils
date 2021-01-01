package com.simple.retrofitutils.http.callback;


/**
 * author: style12520@163.com
 * date：2020/12/17 on 8:46 PM
 * description: 请求回调
 */
public interface ResponseCallback<T> {

    void onSuccess(T t);

    void onFailure(Throwable t);
}
