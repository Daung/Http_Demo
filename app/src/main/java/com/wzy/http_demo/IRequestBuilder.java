package com.wzy.http_demo;

import java.util.Map;

public interface IRequestBuilder {
   void setUrl(String url);
   void setParams(Map<String, String> params);
   void setListener(IResponseCallback callback);
   void execute();
}
