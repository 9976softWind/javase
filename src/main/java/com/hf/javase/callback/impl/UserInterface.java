package com.hf.javase.callback.impl;

import com.hf.javase.callback.DownloadCallback;

/**
 * @author tdw
 * @date 2025.5.16
 */
// 回调接口实现类
public class UserInterface implements DownloadCallback {
    @Override
    public void onDownloadComplete(String fileName) {
        System.out.println("[通知] 文件下载完成: " + fileName);
    }

    @Override
    public void onError(String errorMessage) {
        System.out.println("[错误] " + errorMessage);
    }
}
