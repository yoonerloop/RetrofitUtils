package com.simple.retrofitutils.http.download;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.simple.retrofitutils.http.callback.DownLoadCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author: style12520@163.com
 * date：2020/12/24 on 4:06 PM
 * description: 下载任务
 */
public class DownloadRunnable implements Runnable {

    private static final Handler handler = new Handler(Looper.getMainLooper());

    private Call<ResponseBody> call;
    private DownLoadCallback callback;
    private ExecutorService executor;
    private Map<Object, Call<ResponseBody>> callMap;

    public DownloadRunnable(Call<ResponseBody> call, DownLoadCallback callback, ExecutorService executor, Map<Object, Call<ResponseBody>> callMap) {
        this.call = call;
        this.callback = callback;
        this.executor = executor;
        this.callMap = callMap;
    }

    @Override
    public void run() {
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (callback == null) {
                    return;
                }
                if (response.isSuccessful()) {
                    callback.onStart();
                    executor.execute(() -> writeResponseBodyToDisk(response.body()));
                } else {
                    callback.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    private void writeResponseBodyToDisk(ResponseBody body) {
        try {
            File file = new File(callback.getFolder() + File.separator + callback.getFileName());
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdir();
            }
            if (file.exists()) {
                file.delete();
            }
            if (!file.createNewFile()) {
                handler.post(() -> callback.onFailure("file path is not exit"));
                return;
            }
            InputStream inputStream = null;
            OutputStream outputStream = null;
            ProgerssRun progerssRun = new ProgerssRun(callback, file.getAbsolutePath());
            try {
                byte[] fileReader = new byte[1024 * 4];
                long fileSize = body.contentLength();
                long downLoadSize = 0;
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);
                handler.postDelayed(progerssRun, 300);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    downLoadSize += read;
                    DecimalFormat df = new DecimalFormat("0.000");
                    df.setRoundingMode(RoundingMode.HALF_UP);
                    float currentSize = Float.parseFloat(df.format(downLoadSize / (1024 * 1024f)));
                    float totalSize = Float.parseFloat(df.format(fileSize / (1024 * 1024f)));
                    int progress = (int) (downLoadSize * 100 / fileSize);
                    progerssRun.setProgress(progress, currentSize, totalSize);
                }
                outputStream.flush();

            } catch (IOException e) {
                handler.post(() -> {
                    handler.removeCallbacks(progerssRun);
                    callback.onCancel(callback.getTag());
                });
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (!TextUtils.isEmpty(callback.getTag()) && callMap.get(callback.getTag()) != null) {
                    callMap.remove(callback.getTag());
                }
            }
        } catch (IOException e) {
            handler.post(() -> {
                callback.onFailure(e.getMessage());
            });
        }
    }


    public static class ProgerssRun implements Runnable {

        private int progress;
        private float currentSize;
        private float totalSize;
        private DownLoadCallback callback;
        private String filePath;

        public ProgerssRun(DownLoadCallback callback, String filePath) {
            this.callback = callback;
            this.filePath = filePath;
        }

        public void setProgress(int progress, float currentSize, float totalSize) {
            this.progress = progress;
            this.currentSize = currentSize;
            this.totalSize = totalSize;
        }

        @Override
        public void run() {
            if (currentSize == totalSize) {
                callback.progress(progress, currentSize, totalSize);
                handler.removeCallbacks(this);
                handler.post(() -> callback.onFinish(filePath));
            } else {
                callback.progress(progress, currentSize, totalSize);
                handler.postDelayed(this, 300);
            }
        }
    }
}