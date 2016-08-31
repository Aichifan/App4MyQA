package com.aichifan.app4myqa;

import android.os.Bundle;

public class WMSHistoricalActivity extends UserInfoActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wmshistorical);

        setHeader(getString(R.string.WMSHistoricalActivityTitle), true, true);
    }
}
