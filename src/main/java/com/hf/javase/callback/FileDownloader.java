package com.hf.javase.callback;

/**
 * @author tdw
 * @date 2025.5.16
 */
// 调用方类
public class FileDownloader {

    private DownloadCallback downloadCallback;

    public FileDownloader(DownloadCallback downloadCallback) {
        this.downloadCallback = downloadCallback;
    }

    public void downloadFile(String fileName) {
        try {
            // 模拟下载过程（耗时操作）
            Thread.sleep(2000);
            downloadCallback.onDownloadComplete(fileName);
        } catch (Exception e) {
            downloadCallback.onError("下载失败：" + e.getMessage()); // 失败时调用
        }
    }
}
