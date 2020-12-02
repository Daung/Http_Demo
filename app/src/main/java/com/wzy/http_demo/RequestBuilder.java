package com.wzy.http_demo;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class RequestBuilder implements IRequestBuilder {
    private String mUrl;
    private Map<String, String> mParams;
    private IResponseCallback mCallback;

    @Override
    public void setUrl(String url) {
        this.mUrl = url;
    }

    @Override
    public void setParams(Map<String, String> params) {
         this.mParams = params;
    }

    @Override
    public void setListener(IResponseCallback callback) {
      this.mCallback = callback;
    }

    @Override
    public void execute() {
        //访问网络
        URL url = null;
        HttpURLConnection urlConn = null;
        try {
            url = new URL(this.mUrl); // 打开一个HttpURLConnection连接
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setUseCaches(false);
            urlConn.setInstanceFollowRedirects(true);
            urlConn.setReadTimeout(3000);
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setRequestMethod("GET");
            urlConn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            urlConn.connect();

            OutputStream out = urlConn.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(out);
            bos.write(GsonHelper.jsonString(mParams).getBytes());
            bos.flush();
            out.close();
            bos.close();
            if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = urlConn.getInputStream();
                mCallback.onSuccess(in);
            } else {
                throw new RuntimeException("请求失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("请求失败");
        } finally {

            urlConn.disconnect();

        }
    }
}
