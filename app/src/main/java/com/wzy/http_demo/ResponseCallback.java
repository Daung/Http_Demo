package com.wzy.http_demo;

import android.os.Handler;
import android.os.Looper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResponseCallback<T> implements IResponseCallback {

    private Class<T> clazz;

    private final Handler mHandler = new Handler(Looper.getMainLooper());

    public ResponseCallback(Class<T> clazz, IResponseResult<T> result) {
        this.clazz = clazz;
        this.result = result;
    }

    private final IResponseResult<T> result;


    @Override
    public void onSuccess(InputStream stream) {
        if (result != null) {
            String content = getContent(stream);

//            mHandler.post(() -> result.onSuccess(GsonHelper.fromJson(content, clazz)));
            mHandler.post(() -> result.onSuccess((T) content));
        }

    }

    @Override
    public void onFailure() {
        if (result != null) {
            result.onFailure();
        }
    }


    private String getContent(InputStream inputStream) {
        String content = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                System.out.println("Error=" + e.toString());
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    System.out.println("Error=" + e.toString());
                }
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }
}
