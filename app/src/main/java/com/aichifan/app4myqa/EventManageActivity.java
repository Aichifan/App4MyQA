package com.aichifan.app4myqa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EventManageActivity extends UserInfoActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_manage);

        setHeader(getString(R.string.EventManageActivityTitle), true, true);
    }
}
