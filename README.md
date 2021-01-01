# RetrofitUtils
Retrofit简单封装

统一配置retrofit:

RetrofitManager.getInstance().setBaseUrl("http://127.0.0.1:3000/");

##  1、get请求

```
  RetrofitUtils.get("api/info", new StringCallback() {
      @Override
      public void onSuccess(String s) {
          Log.d(TAG, "onSuccess: " + s);
      }

      @Override
      public void onFailure(Throwable t) {
          Log.d(TAG, "onFailure: " + t);
      }
  });
```

## 2、post请求

```
  HashMap<String, String> hashMap = new HashMap<>();
  hashMap.put("name","张三");
  hashMap.put("age","20");
  RetrofitUtils.post("api/login", hashMap, new StringCallback() {
      @Override
      public void onSuccess(String s) {
          Log.d(TAG, "onSuccess: " + s);
      }

      @Override
      public void onFailure(Throwable t) {
          Log.d(TAG, "onSuccess: " + t);
      }
  });
```

## 3、文件下载

```
  String downloadPath = getExternalFilesDir("download").getAbsolutePath() + File.separator;
  RetrofitUtils.downLoadFile("download/test.apk", new DownLoadCallback(downloadPath,"test.apk","test") {
      @Override
      public void onFinish(String path) {

      }

      @Override
      public void onFailure(String msg) {

      }

      @Override
      public void onStart() {
          
      }

      /**
       * @param progress      下载进度
       * @param currentSize   当前下载了多少
       * @param totalSize     文件总大小
       *                      
       *  单位：M，保留三位小数                  
       */
      @Override
      public void progress(int progress, float currentSize, float totalSize) {

      }

      /**
       * 取消下载回调
       * @param tag
       */
      @Override
      public void onCancel(String tag) {

      }
  });
```

## 4、取消下载

1、取消指定的下载

`DownloadManager.getInstance().cancel("test");`

2、取所有下载

`DownloadManager.getInstance().cancelAll();`

## 5、上传文件

1、上传单个文件

  ```
/**
 * path：文件路径
 * action：服务器接收的字段，和服务器对应
 */
  RetrofitUtils.upLoadFile("upload", "/storage/emulated/0/DCIM/Screenshots/Screenshot_2020_1225_122945.png", "logo", new StringCallback() {
      @Override
      public void onSuccess(String s) {
          Log.d(TAG, "onSuccess: " + s);
      }

      @Override
      public void onFailure(Throwable t) {
          Log.d(TAG, "onFailure: " + t.getMessage());
      }
  });
```
2、上传多个文件

```
/**
 * listPath：文件路径集合
 * action：服务器接收的字段，和服务器对应
 */
ArrayList<String> strings = new ArrayList<>();
strings.add("/storage/emulated/0/DCIM/Screenshots/Screenshot_2020_1225_122945.png");
strings.add("/storage/emulated/0/DCIM/Screenshots/Screenshot_2020_1225_181054.png");
RetrofitUtils.upLoadFiles("form", strings, "logo", new StringCallback() {
    @Override
    public void onSuccess(String s) {
        Log.d(TAG, "onSuccess: " + s);
    }

    @Override
    public void onFailure(Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
    }
});
```

## 6、通用请求

上面的请求返回字符串，如果需要返回对象，则使用：
    
`private static final Retrofit retrofit = RetrofitManager.getInstance().setBaseUrl("http://127.0.0.1:3000/").getRetrofit();`
    
```
WeatherApi weatherAPI = retrofit.create(WeatherApi.class);
Call<WeatherBean> weather = weatherAPI.getWeather();
RetrofitUtils.executeAsync(weather, new ResponseCallback<WeatherBean>() {
    @Override
    public void onSuccess(WeatherBean weatherBean) {
        Log.d(TAG, "onSuccess: " + weatherBean.weatherinfo.city);
        Log.d(TAG, "onSuccess: " + weatherBean.weatherinfo.WS);
        Log.d(TAG, "onSuccess: " + weatherBean.weatherinfo.WSE);
    }

    @Override
    public void onFailure(Throwable t) {

    }
});
```
## 7、动态替换baseUrl

  在以上的实例方法中还有一个含有retrofit的构造方法，传入参数即可，例如下载的：
  
  ` Retrofit retrofit = RetrofitManager.getInstance().getRetrofit(baseUrl);`
  
  ```
    RetrofitUtils.downLoadFile(retrofit, url, new DownLoadCallback(folder, fileFile) {

        @Override
        public void onFinish(String path) {

        }

        @Override
        public void onFailure(String msg) {

        }
    });
  ```
    
    
    

