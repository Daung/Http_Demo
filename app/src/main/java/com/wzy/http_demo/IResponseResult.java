package com.wzy.http_demo;

public interface IResponseResult<T> {
    void onSuccess(T data);

    void onFailure();
}
