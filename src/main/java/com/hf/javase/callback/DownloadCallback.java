package com.hf.javase.callback;

/**
 * @author tdw
 * @date 2025.5.16
 */
// 定义回调接口
public interface DownloadCallback {

    void onDownloadComplete(String fileName);  // 成功回调方法
    void onError(String errorMessage);          // 失败回调方法

}
