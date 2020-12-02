package com.wzy.http_demo;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class GsonHelper {
    private static Gson mGson;

    private static void checkCreateGson() {
        if(mGson == null) {
            mGson = new Gson();
        }
    }

    public static String toJson(Object src) {
        checkCreateGson();
        return mGson.toJson(src);
    }

    public static String toJson(Object src, Type typeOfSrc) {
        checkCreateGson();
        return mGson.toJson(src, typeOfSrc);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        checkCreateGson();
        return mGson.fromJson(json, classOfT);
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        checkCreateGson();
        return mGson.fromJson(json, typeOfT);
    }

    @Deprecated
    public static String toString(Object aClass) {
        checkCreateGson();
        return mGson.toJson(aClass);
    }


    public static String jsonString(Object object) {
        if (object == null) {
            return "";
        }
       return mGson.toJson(object);
    }
}
