package com.aichifan.app4myqa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends UserInfoActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
    }

    public void onButtonClick(View view) {
        Intent intent;

        switch (view.getId()) {
            case R.id.overViewBtn:
                intent = new Intent(this, ListActivity.class);
                startActivity(intent);
                break;

            case R.id.caseHandBtn:
                intent = new Intent(this, CaseHandActivity.class);
                startActivity(intent);
                break;

            case R.id.SysAdminBtn:
                intent = new Intent(this, SystemAdministratorActivity.class);
                startActivity(intent);
                break;

            case R.id.eventManageBtn:
                intent = new Intent(this, EventAddActivity.class);
                startActivity(intent);
                break;

            case R.id.wmsManageBtn:
                intent = new Intent(this, WMSAddActivity.class);
                startActivity(intent);
                break;

            case R.id.tmsManageBtn:
                intent = new Intent(this, TMSManageActivity.class);
                startActivity(intent);
                break;
            case R.id.eventClosedBtn:
                intent = new Intent(this, EventClosedActivity.class);
                startActivity(intent);
                break;

            case R.id.wmsClosedBtn:
                intent = new Intent(this, WMSClosedActivity.class);
                startActivity(intent);
                break;

            case R.id.tmsClosedBtn:
                intent = new Intent(this, TMSClosedActivity.class);
                startActivity(intent);
                break;
            case R.id.eventHistoryBtn:
                intent = new Intent(this, EventHistoricalActivity.class);
                startActivity(intent);
                break;

            case R.id.wmsHistoryBtn:
                intent = new Intent(this, WMSHistoricalActivity.class);
                startActivity(intent);
                break;

            case R.id.tmsHistoryBtn:
                intent = new Intent(this, TMSHistoricalActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
