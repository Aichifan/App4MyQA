package com.aichifan.app4myqa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OverviewActivity extends UserInfoActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        setHeader(getString(R.string.OverviewActivityTitle), true, true);
    }
}
