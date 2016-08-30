package com.aichifan.app4myqa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TMSManageActivity extends UserInfoActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmsmanage);

        setHeader(getString(R.string.TMSManageActivityTitle), true, true);
    }
}
