package com.aichifan.app4myqa;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

public class UserInfoActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setHeader(String title, boolean btnHomeVisible, boolean btnFeedbackVisible){
        ViewStub stub=(ViewStub)findViewById(R.id.stubHeader);
        /**
         * Inflates the layout resource identified by getLayoutResource() and
         * replaces this StubbedView in its parent by the inflated layout resource
         * Return:View -- The inflated layout resource
         */
        View inflated = stub.inflate();

        TextView textTitle=(TextView)findViewById(R.id.textHeading);
        textTitle.setText(title);

        if(!btnHomeVisible){
            Button btnHome = (Button) inflated.findViewById(R.id.btnHome);
            btnHome.setVisibility(View.INVISIBLE);
        }

        if(!btnFeedbackVisible){
            Button btnFeedback = (Button) inflated.findViewById(R.id.btnFeedback);
            btnFeedback.setVisibility(View.INVISIBLE);
        }

    }

    public void btnHomeClick(View view) {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        //FLAG_ACTIVITY_CLEAR_TOP:从栈中弹出销毁目标Activity之上的Activity
        intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    public void btnFeedbackClick(View view) {
        Intent intent = new Intent(getApplicationContext(), FeedbackActivity.class);
        startActivity(intent);
    }
}
