package com.hf.javase.callback;

import com.hf.javase.callback.impl.UserInterface;

/**
 * @author tdw
 * @date 2025.5.16
 */
public class CallBackTest {

    public static void main(String[] args) {
        FileDownloader fileDownloader = new FileDownloader(new UserInterface());
        fileDownloader.downloadFile("report.pdf");
    }

}
