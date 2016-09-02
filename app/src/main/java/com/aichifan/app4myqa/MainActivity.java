package com.aichifan.app4myqa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.aichifan.app4myqa.pojo.User;
import com.aichifan.app4myqa.util.MyUrlUtil;
import com.aichifan.app4myqa.vo.LoginAccount;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    public static final String HOST = "http://139.196.56.98:8080/myQA";
    public ImageView mImageView;

    public static final User user = new User();
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
        user.setLoginid(account.getLoginID());
        user.setPassword(account.getPassword());

        new Thread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                InputStream is = MyUrlUtil.requestByUrl(HOST+"/main/login","POST",gson.toJson(account));
                BufferedReader isb = new BufferedReader(new InputStreamReader(is));
                StringBuffer sb = new StringBuffer();
                String line = null;
                try {
                    while ((line = isb.readLine()) != null) {
                        sb.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.v("return", sb.toString());
                if(is != null){
//                    User returnAccount = gson.fromJson(new InputStreamReader(is), User.class);
                    user.setUsername(sb.toString());

                    if(!TextUtils.isEmpty(sb)){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                                startActivity(intent);
                                Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        Toast.makeText(MainActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                    }
                }
                /*
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(account.getLoginID()!=null){
                            Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                            startActivity(intent);
                            Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
            }
        }).start();
    }
}
