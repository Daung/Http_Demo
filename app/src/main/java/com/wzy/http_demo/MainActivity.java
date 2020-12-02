package com.wzy.http_demo;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);

        String url = "https://www.baidu.com";
        Map<String, String> map = new HashMap<>();
        NetHttp.request(url, map, String.class, new IResponseResult<String>() {
            @Override
            public void onSuccess(String data) {
                Log.d(TAG, "onSuccess: \n data = " + data);
                tv.setText(data);
                Toast.makeText(MainActivity.this, "成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure() {
                Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_SHORT).show();
            }
        });

    }
}