package com.aichifan.app4myqa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.aichifan.app4myqa.util.MyUrlUtil;

public class MainActivity extends AppCompatActivity {
    public static final String HOST = "http://192.168.0.103:8080/myQA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
