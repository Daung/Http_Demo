package com.wzy.http_demo;

import java.io.InputStream;

public interface IResponseCallback {
    void onSuccess(InputStream stream);
    void onFailure();
}
