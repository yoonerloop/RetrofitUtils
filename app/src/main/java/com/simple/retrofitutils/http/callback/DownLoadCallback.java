package com.simple.retrofitutils.http.callback;

/**
 * author: style12520@163.com
 * date：2020/12/18 on 6:15 PM
 * description: 如果有取消下载的请求，请调用含有tag的三个参数的方法
 */
public abstract class DownLoadCallback implements DownLoadListener {

    private String folder;
    private String fileName;
    private String tag;

    public DownLoadCallback(String folder, String fileName) {
        this.folder = folder;
        this.fileName = fileName;
    }

    public DownLoadCallback(String folder, String fileName, String tag) {
        this.folder = folder;
        this.fileName = fileName;
        this.tag = tag;
    }


    public void onStart() {

    }

    public void progress(int progress, float currentSize, float totalSize) {

    }

    @Override
    public void onCancel(String tag) {

    }

    public String getFolder() {
        return folder;
    }

    public String getFileName() {
        return fileName;
    }

    public String getTag() {
        return tag;
    }
}
