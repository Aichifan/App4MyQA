package com.aichifan.app4myqa;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aichifan.app4myqa.pojo.City;
import com.aichifan.app4myqa.pojo.ProjectName;
import com.aichifan.app4myqa.pojo.User;
import com.aichifan.app4myqa.util.MyUrlUtil;
import com.aichifan.app4myqa.vagerview.NiceSpinner;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mic on 2016/8/30.
 */
public class EventAddActivity extends UserInfoActivity {

    private NiceSpinner event_project_name_select;
    private NiceSpinner event_supplier_select;
    private NiceSpinner event_feedback_select;
    private List<String> datesProject;
    private List<String> datesSupplier;
    private List<String> datesFeedback;
    private List<String> datesGroup;
    private LinearLayout ll;
    private TextView event_tv_city;
    private City city;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_add);
        setHeader(getString(R.string.EventAddActivityTitle), true, true);

        init();
        new Thread(new Runnable() {
            @Override
            public void run() {
                projectName("/dict/projects");
                supplier("/dict/suppliers");
                feedback();
                group("/user/group/pm");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        event_project_name_select.attachDataSource(datesProject);
                        event_supplier_select.attachDataSource(datesSupplier);
                        event_feedback_select.attachDataSource(datesFeedback);
                        groupAdd();
                    }
                });
            }
        }).start();
    }

    private void groupAdd() {

        event_tv_city.setText(city.getName());
        for (int i = 0; i < datesGroup.size(); i++) {
            CheckBox checkBox = new CheckBox(getApplicationContext());
            checkBox.setText(datesGroup.get(i));
            checkBox.setTag(i);

            checkBox.setTextColor(Color.parseColor("#000000"));
            System.out.println(checkBox.getText());
            ll.addView(checkBox,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    private void group(String s){
        InputStream is = MyUrlUtil.requestByUrl(MainActivity.HOST + s, "GET", null);

        ObjectMapper objectMapper = new ObjectMapper();
        User[] projectNames = new User[0];
        //User[] projectNames = null;
        try {
            projectNames = objectMapper.readValue(new InputStreamReader(is), User[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        city = projectNames[0].getCity();
        datesGroup = new ArrayList<String>();
        for (int i = 0; i < projectNames.length; i++) {
            datesGroup.add(projectNames[i].getUsername().toString().trim());
        }
    }

    private void feedback() {
        datesFeedback = new ArrayList<String>();
        datesFeedback.add("请选择");
        datesFeedback.add("Y");
        datesFeedback.add("N");
    }

    private void supplier(String url) {
        InputStream is = MyUrlUtil.requestByUrl(MainActivity.HOST + url, "GET", null);
        Gson gson = new Gson();
        ProjectName[] projectNames = gson.fromJson(new InputStreamReader(is), ProjectName[].class);
        datesSupplier = new ArrayList<String>();
        datesSupplier.add("请选择");
        for (int i = 0; i < projectNames.length; i++) {
            datesSupplier.add(projectNames[i].getText().toString().trim());
        }
    }

    private void projectName(String url) {
        InputStream is = MyUrlUtil.requestByUrl(MainActivity.HOST + url, "GET", null);
        Gson gson = new Gson();
        ProjectName[] projectNames = gson.fromJson(new InputStreamReader(is), ProjectName[].class);
        datesProject = new ArrayList<String>();
        datesProject.add("请选择");
        for (int i = 0; i < projectNames.length; i++) {
            datesProject.add(projectNames[i].getText().toString().trim());
        }
    }

    private void init() {
        event_project_name_select = (NiceSpinner) findViewById(R.id.event_project_name_select);
        event_supplier_select = (NiceSpinner) findViewById(R.id.event_supplier_select);
        event_feedback_select = (NiceSpinner) findViewById(R.id.event_feedback_select);
        ll = (LinearLayout) findViewById(R.id.event_ll_check);
        event_tv_city = (TextView) findViewById(R.id.event_tv_city);
    }
}
