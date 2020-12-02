package com.wzy.http_demo;

import java.util.Map;

public class NetHttp {
    public static  <T> void request(String mUrl, Map<String, String> map, Class<T> clazz,  IResponseResult<T> result) {
        IRequestBuilder builder = new RequestBuilder();
        IResponseCallback callback = new ResponseCallback<>(clazz, result);
        HttpTask<T> httpTask = new HttpTask<>(mUrl, map, builder, callback);
        HttpRequestManager.getManager().addTask(httpTask);
    }
}
