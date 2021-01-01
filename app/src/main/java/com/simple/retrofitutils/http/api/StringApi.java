package com.simple.retrofitutils.http.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * author: style12520@163.com
 * date：2020/12/29 on 4:36 PM
 * description:
 */
public interface StringApi {

    //带参数的通用get请求
    @GET()
    Call<String> executeGet(@Url String url, @QueryMap Map<String, String> maps);

    //不带参数的通用get请求
    @GET()
    Call<String> executeGet(@Url String url);

    //不带参数的通用post请求
    @POST()
    Call<String> executePost( @Url String url);

    //带参数的通用post请求
    @POST()
    @FormUrlEncoded
    Call<String> executePost( @Url String url, @FieldMap Map<String, String> maps);
}
