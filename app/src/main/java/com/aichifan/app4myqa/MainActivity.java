package com.aichifan.app4myqa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.aichifan.app4myqa.pojo.User;
import com.aichifan.app4myqa.util.MyUrlUtil;
import com.aichifan.app4myqa.vo.LoginAccount;
import com.google.gson.Gson;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    public static final String HOST = "http://139.196.56.98:8080/myQA";
    public ImageView mImageView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView=(ImageView)findViewById(R.id.imageView);
        mImageView.setImageResource(R.mipmap.dhl);
    }

    public void loginClick(View view) {
        final LoginAccount account = new LoginAccount(((EditText) findViewById(R.id.edit_loginID)).getText().toString(),
                ((EditText) findViewById(R.id.edit_password)).getText().toString());

        new Thread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                InputStream is = MyUrlUtil.requestByUrl(HOST+"/main/login","POST",gson.toJson(account));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(account.getLoginID()!=null && account.getPassword()!=null){
                            Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                            startActivity(intent);
                            Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }
}
