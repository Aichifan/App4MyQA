package com.aichifan.app4myqa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WMSManageActivity extends UserInfoActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wmsmanage);

        setHeader(getString(R.string.WMSManageActivityTitle), true, true);
    }
}
