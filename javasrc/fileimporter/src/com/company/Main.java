package com.company;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
	// write your code here
        String urls[] = {"https://doc.qt.io/qt-5/qdialog.html",
                         "https://doc.qt.io/qt-5/qwidget.html",
                         "https://doc.qt.io/qt-5/qstring.html",
                         "https://doc.qt.io/qt-5/qwhatsthis.html",
                          "https://doc.qt.io/qt-5/qwizard.html"};
        ThreadPoolExecutor executor =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
        for(int i=0;i < urls.length; i++) {
            Runnable fw = new FileWorker(urls[i], "C:\\out\\");
            executor.execute(fw);
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {

        }
    }
}
