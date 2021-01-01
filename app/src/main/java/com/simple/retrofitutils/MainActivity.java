package com.simple.retrofitutils;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.simple.retrofitutils.http.RetrofitUtils;
import com.simple.retrofitutils.http.RetrofitManager;
import com.simple.retrofitutils.http.callback.DownLoadCallback;
import com.simple.retrofitutils.http.callback.ResponseCallback;
import com.simple.retrofitutils.http.callback.StringCallback;
import com.simple.retrofitutils.http.download.DownloadManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * 测试代码
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    //统一配置
    private static final Retrofit retrofit = RetrofitManager.getInstance().setBaseUrl("http://127.0.0.1:3000/").getStringRetrofit();

    private Button textView1;
    private Button textView2;
    private Button textView3;
    private Button textView4;
    private Button textView5;
    private Button textView6;
    private TextView textView;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1 = findViewById(R.id.tv_text1);
        textView2 = findViewById(R.id.tv_text2);
        textView3 = findViewById(R.id.tv_text3);
        textView4 = findViewById(R.id.tv_text4);
        textView5 = findViewById(R.id.tv_text5);
        textView6 = findViewById(R.id.tv_text6);
        textView = findViewById(R.id.text);
        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setOnClickListener(this);
        textView4.setOnClickListener(this);
        textView5.setOnClickListener(this);
        textView6.setOnClickListener(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 100);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_text1:
                /**
                 * 返回对象
                 */
                Retrofit retrofitWeather = RetrofitManager.getInstance().getRetrofit("http://www.weather.com.cn/");
                WeatherApi weatherAPI = retrofitWeather.create(WeatherApi.class);
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

                /**
                 * 返回字符串
                 */
                RetrofitUtils.get("get", new StringCallback() {
                    @Override
                    public void onSuccess(String s) {
                        Log.d(TAG, "onSuccess: " + s);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.d(TAG, "onSuccess: " + t);
                    }
                });
                break;
            case R.id.tv_text2:
                //post请求
                LoginApi loginApi = retrofit.create(LoginApi.class);
                RetrofitUtils.executeAsync(loginApi.post("张三", 30), new ResponseCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        textView.setText(s);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        textView.setText("post请求" + t.getMessage());
                    }
                });
                break;
            case R.id.tv_text3:
                //下载
                download("22", "222.apk");
                break;
            case R.id.tv_text4:
                //取消下载
                DownloadManager.getInstance().cancel("11");
                // DownloadManager.getInstance().cancelAll();
                break;
            case R.id.tv_text5:
                //单文件上传
                RetrofitUtils.upLoadFile("upload", "/storage/emulated/0/DCIM/Screenshots/Screenshot_2020_1225_122945.png", "logo", new StringCallback() {
                    @Override
                    public void onSuccess(String s) {
                        Log.d(TAG, "onSuccess: " + s);
                        textView.setText(s);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
                break;
            case R.id.tv_text6:
                //多文件上传
                ArrayList<String> strings = new ArrayList<>();
                strings.add("/storage/emulated/0/DCIM/Screenshots/Screenshot_2020_1225_122945.png");
                strings.add("/storage/emulated/0/DCIM/Screenshots/Screenshot_2020_1225_181054.png");
                RetrofitUtils.upLoadFiles("form", strings, "logo", new StringCallback() {
                    @Override
                    public void onSuccess(String s) {
                        Log.d(TAG, "onSuccess: " + s);
                        textView.setText(s);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
                break;
        }
    }

    private void download(String tag, String name) {
        String folder = getExternalFilesDir("download").getAbsolutePath() + File.separator;
        String fileFile = name;
        String baseUrl = "https://a03b43ffc82fef2b91fbc0a7f95042ec.dlied1.cdntips.net/download.sj.qq.com/upload/connAssitantDownload/upload/";
        String url = "MobileAssistant_1.apk";
        HashMap<String, String> map = new HashMap<>();
        map.put("mkey", "5fd710380e1130be");
        map.put("f", "0f1e");
        map.put("cip", "14.17.22.75");
        map.put("proto", "https");
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit(baseUrl);
        RetrofitUtils.downLoadFile(retrofit, url, new DownLoadCallback(folder, fileFile, tag) {
            @Override
            public void onFinish(String path) {
                Log.d(TAG, "onFinish: " + path);
                textView.setText("下载完成" + path);
            }

            @Override
            public void onFailure(String msg) {
                textView.setText("onFailure：" + msg);
            }

            @Override
            public void progress(int progress, float currentSize, float totalSize) {
                Log.d(TAG, tag + "----progress: " + progress + "---:" + currentSize + "----:" + totalSize);
                textView.setText("下载进度" + progress);
            }

            @Override
            public void onCancel(String tag) {
                Log.d(TAG, "onCancel: " + tag);
            }
        });
    }
}
