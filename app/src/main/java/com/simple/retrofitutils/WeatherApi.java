package com.simple.retrofitutils;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * author: style12520@163.com
 * date：2020/12/30 on 10:51 AM
 * description:
 */
public interface WeatherApi {

    @GET("data/sk/101190408.html")
    Call<WeatherBean> getWeather();
}
