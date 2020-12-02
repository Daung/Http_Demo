package com.wzy.http_demo;

import java.util.Map;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class HttpTask<T> implements Runnable, Delayed {

    private IRequestBuilder builder;

    public HttpTask(String mUrl, Map<String, String> mParams, IRequestBuilder builder,  IResponseCallback callback) {
        this.builder = builder;
        builder.setUrl(mUrl);
        builder.setParams(mParams);
        builder.setListener(callback);
    }


    @Override
    public void run() {
        if (builder != null) {
            builder.execute();
        }
    }

    public long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(long delayTime) {
        this.delayTime = System.currentTimeMillis() + delayTime;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    private long delayTime;
    private int retryCount;

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.delayTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return 0;
    }
}
