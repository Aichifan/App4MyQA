package com.aichifan.app4myqa;

import android.os.Bundle;

public class TMSHistoricalActivity extends UserInfoActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmshistorical);

        setHeader(getString(R.string.TMSHistoricalActivityTitle), true, true);
    }
}
