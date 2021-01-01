package com.simple.retrofitutils;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * author: style12520@163.com
 * date：2020/12/30 on 3:04 PM
 * description:
 */
public interface LoginApi {

    @POST("post")
    @FormUrlEncoded
    Call<String> post(@Field("name")String name,@Field("pwd")int pwd);
}
