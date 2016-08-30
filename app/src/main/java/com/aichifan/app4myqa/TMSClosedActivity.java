package com.aichifan.app4myqa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TMSClosedActivity extends UserInfoActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmsclosed);

        setHeader(getString(R.string.TMSClosedActivityTitle), true, true);
    }
}
