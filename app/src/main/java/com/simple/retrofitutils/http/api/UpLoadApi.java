package com.simple.retrofitutils.http.api;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;

/**
 * author: style12520@163.com
 * date：2020/12/24 on 8:24 PM
 * description: 参考：https://www.jianshu.com/p/74b7da380855
 */
public interface UpLoadApi {

    /**
     * 上传字符串
     */
    @FormUrlEncoded
    @POST()
    Call<String> uploadStringApi(@Url String url, @FieldMap Map<String, String> map);

    /**
     * FormBody为上传参数的子类，引入了okhttp可以直接使用
     * <p>
     * FormBody body=new FormBody.Builder()
     * .add("name","zahngsan")
     * .add("paw","sfasfasdfasdfasd")
     * .build();
     */
    @POST()
    Call<String> uploadStringApi(@Url String url, @Body RequestBody body);


    /**
     * 上传单个文件
     *
     * RequestBody fileRQ = RequestBody.create(MediaType.parse("image/*"), file);
     * MultipartBody.Part part MultipartBody.Part.createFormData("picture", file.getName(), fileRQ);
     */
    @Multipart
    @POST()
    Call<String> uploadFileApi(@Url String url, @Part MultipartBody.Part body);

    /**
     * 上传多个文件
     *
     * RequestBody fb = RequestBody.create(MediaType.parse("text/plain"), "hello,retrofit");
     * RequestBody fileTwo = RequestBody.create(MediaType.parse("image/*"), new File(Environment.getExternalStorageDirectory()
     * + file.separator + "original.png"));
     * Map<String, RequestBody> map = new HashMap<>();
     * //这里的key必须这么写，否则服务端无法识别
     * map.put("file\"; filename=\""+ file.getName(), fileRQ);
     * map.put("file\"; filename=\""+ "2.png", fileTwo);
     * 如果服务端是php，需要name需要加中括号：
     * map.put("file[]\"; filename=\""+ file.getName(), fileRQ);
     * map.put("file[]\"; filename=\""+ "2.png", fileTwo);
     */
    @Multipart
    @POST()
    Call<String> uploadFilesApi(@Url String url, @PartMap Map<String, RequestBody> map);

    /**
     * RequestBody fileRQ = RequestBody.create(MediaType.parse("image/*"), file);
     * <p>
     * MultipartBody.Part part = MultipartBody.Part.createFormData("picture", file.getName(), fileRQ);
     * <p>
     * RequestBody fb = RequestBody.create(MediaType.parse("text/plain"), "hello,retrofit");
     * RequestBody fileTwo = RequestBody.create(MediaType.parse("image/*"), new File(Environment.getExternalStorageDirectory()
     * + file.separator + "original.png"));
     * MultipartBody.Part two=MultipartBody.Part.createFormData("one","one.png",fileTwo);
     * List<MultipartBody.Part> parts=new ArrayList<>();
     * parts.add(part);
     * parts.add(two);
     */
    @Multipart
    @POST()
    Call<String> uploadFilesApi(@Url String url, @Part List<MultipartBody.Part> parts);

    /**
     * 文件字符串混合传递
     *
     * MultipartBody.Part part = MultipartBody.Part.createFormData("picture", file.getName(), fileRQ);
     * RequestBody fb =RequestBody.create(MediaType.parse("text/plain"), "hello,retrofit");
     */
    @Multipart
    @POST()
    Call<String> uploadFileStringApi(@Url String url, @Part("body") RequestBody body, @Part MultipartBody.Part file);

    /**
     * 通用的上传：这种方式上传的时候，不能再接口上加上@Multipart的注解，否者会报错
     *
     *  RequestBody fileRQ = RequestBody.create(MediaType.parse("multipart/form-data"), file);
     *  MultipartBody.Part part = MultipartBody.Part.createFormData("picture", file.getName(), fileRQ);
     *
     *  RequestBody body=new MultipartBody.Builder()
     *     .addFormDataPart("userName","lange")
     *     .addFormDataPart("token","dxjdkdjkj9203kdckje0")
     *     .addFormDataPart("header",file.getName(),fileRQ)
     *     .build();
     */
    @POST()
    Call<String> uploadApi(@Url String url,@Body RequestBody body);

}
