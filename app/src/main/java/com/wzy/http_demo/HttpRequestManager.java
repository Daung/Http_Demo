package com.wzy.http_demo;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HttpRequestManager {

    private static final String TAG = "HttpRequestManager";

    private static final HttpRequestManager manager = new HttpRequestManager();
    private final ThreadPoolExecutor executor;

    public static HttpRequestManager getManager() {
        return manager;
    }

    private final LinkedBlockingQueue<Runnable> mQueue = new LinkedBlockingQueue<>();

    private final DelayQueue<HttpTask<?>> mDelay = new DelayQueue<>();

    private HttpRequestManager() {
        int coreCount = Runtime.getRuntime().availableProcessors();
        executor = new ThreadPoolExecutor(coreCount, coreCount, 15, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                addTask(r);
            }
        });
        Runnable coreRunnable = () -> {
            while (true) {
                try {
                    Runnable runnable = mQueue.take();
                    executor.execute(runnable);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Log.d(TAG, "HttpRequestManager: coreRunnable");
            }
        };
        executor.execute(coreRunnable);
        Runnable delayRunnable = () -> {
            while (true) {
                try {
                    HttpTask<?> httpTask = mDelay.take();
                    if (httpTask.getRetryCount() < 3) {
                        executor.execute(httpTask);
                        httpTask.setRetryCount(httpTask.getRetryCount() + 1);
                        Log.d(TAG, "开始重试 run: 次数 为 " + httpTask.getRetryCount() + " " + System.currentTimeMillis());
                    } else {
                        Log.d(TAG, "重试机制" + " 放弃");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "HttpRequestManager: delayRunnable");
            }
        };
        executor.execute(delayRunnable);
    }


    public void addTask(Runnable runnable) {
        if (runnable == null) {
            return;
        }

        if (!mQueue.contains(runnable)) {
            mQueue.add(runnable);
        }
    }


}
