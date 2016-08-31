package com.aichifan.app4myqa;

import android.os.Bundle;

public class EventHistoricalActivity extends UserInfoActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_historical);

        setHeader(getString(R.string.EventHistoricalActivityTitle), true, true);
    }
}
