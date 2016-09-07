package com.aichifan.app4myqa;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.aichifan.app4myqa.pojo.City;
import com.aichifan.app4myqa.pojo.Group;
import com.aichifan.app4myqa.pojo.ProjectName;
import com.aichifan.app4myqa.pojo.Question;
import com.aichifan.app4myqa.pojo.QuestionAttachment;
import com.aichifan.app4myqa.pojo.User;
import com.aichifan.app4myqa.util.MyUrlUtil;
import com.aichifan.app4myqa.vagerview.NiceSpinner;
import com.fasterxml.jackson.core.util.InternCache;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TMSManageActivity extends UserInfoActivity {
    private static final int IMAGE_REQUEST_CODE = 1;
    private NiceSpinner tms_project_select;
    private NiceSpinner tms_city_select;
    private NiceSpinner tms_problem_type_select;
    private NiceSpinner tms_mail_group_select;
    private NiceSpinner tms_problem_severity_select;
    private NiceSpinner tms_deal_with_select;

    private EditText tms_et_project_lunch_time;
    private EditText tms_et_to_end_time;
    private EditText tms_et_mail_man;
    private EditText tms_problem_statement;

    private List<String> datesProject;
    private List<String> datesProblem;
    private List<String> datesGroup;
    private List<String> datesSeverity;
    private List<String> datesRole;
    private City datesCities;
    private List<String> datesCityText;
    private List<Integer> datesCityId;
    private List<Group> groupList;
    private List<User> userList;

    private Question question;

    private Button tms_bt_commit;
    private Button tms_bt_reset;
    private Button tms_bt_accessory;
    private SimpleDateFormat sim;
    private List<QuestionAttachment> questionAttachments;
    private String picturePath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmsmanage);

        setHeader(getString(R.string.TMSManageActivityTitle), true, true);

        init();

        new Thread(new Runnable() {
            @Override
            public void run() {
                projectName("/dict/projects");
                city("/dict/cities");
                problemType("/dict/types/ms");
                mailGruop("/user/group/all");
                problemSeverity("/dict/severity");
                dealWith("/user/role/handlers");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tms_project_select.attachDataSource(datesProject);
                        tms_city_select.attachDataSource(datesCityText);
                        tms_problem_type_select.attachDataSource(datesProblem);
                        lunchTime(tms_et_project_lunch_time);
                        lunchTime(tms_et_to_end_time);
                        tms_mail_group_select.attachDataSource(datesGroup);
                        tms_problem_severity_select.attachDataSource(datesSeverity);
                        tms_deal_with_select.attachDataSource(datesRole);
                    }
                });
            }
        }).start();
    }

    private void dealWith(String s) {
        InputStream is = MyUrlUtil.requestByUrl(MainActivity.HOST + s, "GET", null);

        ObjectMapper objectMapper = new ObjectMapper();
        User[] projectNames = new User[0];
        try {
            projectNames = objectMapper.readValue(new InputStreamReader(is), User[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        userList = new ArrayList<User>();
        datesRole = new ArrayList<String>();
        for (int i = 0; i < projectNames.length; i++) {
            datesRole.add(projectNames[i].getUsername().toString().trim());
            userList.add(projectNames[i]);
        }
    }

    private void problemSeverity(String s) {
        InputStream is = MyUrlUtil.requestByUrl(MainActivity.HOST + s, "GET", null);
        Gson gson = new Gson();
        ProjectName[] projectNames = gson.fromJson(new InputStreamReader(is), ProjectName[].class);
        datesSeverity = new ArrayList<String>();
        for (int i = 0; i < projectNames.length; i++) {
            datesSeverity.add(projectNames[i].getText().toString().trim());
        }
    }


    private void mailGruop(String s) {
        InputStream is = MyUrlUtil.requestByUrl(MainActivity.HOST + s, "GET", null);

        ObjectMapper objectMapper = new ObjectMapper();
        Group[] groups = new Group[0];
        groupList = new ArrayList<Group>();
        try {
            groups = objectMapper.readValue(new InputStreamReader(is), Group[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        datesGroup = new ArrayList<String>();
        datesGroup.add("请选择");
        for (int i = 0; i < groups.length; i++) {
            datesGroup.add(groups[i].getName().toString().trim());
            System.out.println(groups[i].getName().toString());
            groupList.add(groups[i]);
        }
    }

    private void lunchTime(final EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(TMSManageActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar c = Calendar.getInstance();
                        c.set(year, monthOfYear, dayOfMonth);
                        editText.setText(DateFormat.format("yyy年MM月dd日", c));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }

        });
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    DatePickerDialog dialog = new DatePickerDialog(TMSManageActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            Calendar c = Calendar.getInstance();
                            c.set(year, monthOfYear, dayOfMonth);
                            editText.setText(DateFormat.format("yyy年MM月dd日", c));
                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    dialog.show();
                }
            }
        });
    }

    private void problemType(String s) {
        InputStream is = MyUrlUtil.requestByUrl(MainActivity.HOST + s, "GET", null);
        Gson gson = new Gson();
        ProjectName[] projectNames = gson.fromJson(new InputStreamReader(is), ProjectName[].class);
        datesProblem = new ArrayList<String>();
        for (int i = 0; i < projectNames.length; i++) {
            datesProblem.add(projectNames[i].getText().toString().trim());
        }
    }


    private void city(String s) {
        InputStream is = MyUrlUtil.requestByUrl(MainActivity.HOST + s, "GET", null);
        Gson gson = new Gson();
        ProjectName[] projectNames = gson.fromJson(new InputStreamReader(is), ProjectName[].class);
        datesCityText = new ArrayList<String>();
        datesCityId = new ArrayList<Integer>();
        for (int i = 0; i < projectNames.length; i++) {
            datesCityText.add(projectNames[i].getText().toString().trim());
            datesCityId.add(projectNames[i].getId());

        }
    }

    private void projectName(String s) {
        InputStream is = MyUrlUtil.requestByUrl(MainActivity.HOST + s, "GET", null);
        Gson gson = new Gson();
        ProjectName[] projectNames = gson.fromJson(new InputStreamReader(is), ProjectName[].class);
        datesProject = new ArrayList<String>();
        datesProject.add("请选择");
        for (int i = 0; i < projectNames.length; i++) {
            datesProject.add(projectNames[i].getText().toString().trim());
        }
    }

    private void init() {
        tms_project_select = (NiceSpinner) findViewById(R.id.tms_project_select);
        tms_city_select = (NiceSpinner) findViewById(R.id.tms_city_select);
        tms_problem_type_select = (NiceSpinner) findViewById(R.id.tms_problem_type_select);
        tms_et_project_lunch_time = (EditText) findViewById(R.id.tms_et_project_lunch_time);
        tms_et_to_end_time = (EditText) findViewById(R.id.tms_et_to_end_time);
        tms_et_mail_man = (EditText) findViewById(R.id.tms_et_mail_man);
        tms_mail_group_select = (NiceSpinner) findViewById(R.id.tms_mail_group_select);
        tms_problem_severity_select = (NiceSpinner) findViewById(R.id.tms_problem_severity_select);
        tms_deal_with_select = (NiceSpinner) findViewById(R.id.tms_deal_with_select);
        tms_problem_statement = (EditText) findViewById(R.id.tms_problem_statement);
        tms_bt_accessory = (Button) findViewById(R.id.tms_bt_accessory);
        tms_bt_commit = (Button) findViewById(R.id.tms_bt_commit);
        tms_bt_reset = (Button) findViewById(R.id.tms_bt_reset);

        questionAttachments = new ArrayList<QuestionAttachment>();
    }

    public void onTmsClick(View view){
        switch (view.getId()){
            case R.id.tms_bt_accessory:
                selectPicture();
                break;
            case  R.id.tms_bt_commit:
                commitInfo();
                break;
            case R.id.tms_bt_reset:
                reset();
        }
    }

    private void selectPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        Log.v("++++", permissionCheck + "");
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            startActivityForResult(intent, IMAGE_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK
                && null != data) {
            Uri selectedImage = data.getData();
            Log.v("************:", "image" + selectedImage);
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            Log.v("picture path", picturePath);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    OkHttpClient client = new OkHttpClient();
                    File file = new File(picturePath);
                    MultipartBody body =  new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("Filedata", file.getName(),
                                    RequestBody.create(MediaType.parse("text/plain"), file))
                            .build();

                    Request request = new Request.Builder().url(MainActivity.HOST + "/question/attachment/upload").post(body).build();
                    Response response = null;
                    try {
                        response = client.newCall(request).execute();
//                        Log.v("return body", response.body().string());
                        ObjectMapper objectMapper = new ObjectMapper();
                        QuestionAttachment questionAttachment = null;
                        try {
                            questionAttachment = objectMapper.readValue(response.body().string(), QuestionAttachment.class);
                            questionAttachments.add(questionAttachment);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }).start();
        }
    }

    private void commitInfo() {
        question = new Question();
        question.setCategory("TMS");
        if (tms_project_select.getText().toString().equals("请选择")) {
            question.setProject("");
        } else {
            question.setProject(tms_project_select.getText().toString());
        }
        int i;
        for(i = 0;i<datesCityText.size();i++){
            if (tms_city_select.getText().toString().equals(datesCityText.get(i).toString()))
                break;
        }

        datesCities = new City();
        datesCities.setId(datesCityId.get(i));
        datesCities.setName(datesCityText.get(i));
        question.setCity(datesCities);

        if (tms_problem_type_select.getText().toString().equals("请选择")) {
            question.setType("");
        } else {
            question.setType(tms_problem_type_select.getText().toString());
        }

        sim = new SimpleDateFormat("yyy年MM月dd日");
        try {
            Date date = sim.parse(tms_et_project_lunch_time.getText().toString());
            question.setStartdate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            Date date = sim.parse(tms_et_to_end_time.getText().toString());
            question.setPromisedate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        question.setEmailto(tms_et_mail_man.getText().toString());

        if (tms_mail_group_select.getText().toString().equals("请选择")) {
            question.setGroup(null);
        } else {
            for (int j = 0;j<groupList.size();j++) {
                if (tms_mail_group_select.getText().toString().equals(groupList.get(j).getName())){
                    question.setGroup(groupList.get(j));
                }
            }

        }

        if (tms_problem_severity_select.getText().toString().equals("请选择")) {
            question.setSeverity("");
        } else {
            question.setSeverity(tms_problem_severity_select.getText().toString());
        }

        if (tms_deal_with_select.getText().toString().equals("请选择")) {
            question.setHandler(null);
        } else {
            for (int j = 0; j < userList.size(); j++) {
                if (tms_deal_with_select.getText().toString().equals(userList.get(j).getUsername())) {
                    question.setHandler(userList.get(j));
                }
            }
        }
        question.setProblemStatement(tms_problem_statement.getText().toString());
        if (questionAttachments!=null) {
            question.setAttachmentList(questionAttachments);
        }
        reset();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            final String str = objectMapper.writeValueAsString(question);
            System.out.println(str);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    commit("/question/create", "POST", str);
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void commit(String s, String post, String str) {
        InputStream is = MyUrlUtil.requestByUrl(MainActivity.HOST + s,post, str);
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

        System.out.println(sb);
    }

    private void reset() {
        tms_project_select.attachDataSource(datesProject);
        tms_city_select.attachDataSource(datesCityText);
        tms_problem_type_select.attachDataSource(datesProblem);

        tms_mail_group_select.attachDataSource(datesGroup);
        tms_problem_severity_select.attachDataSource(datesSeverity);
        tms_deal_with_select.attachDataSource(datesRole);

        tms_et_project_lunch_time.getText().clear();
        tms_et_to_end_time.getText().clear();
        tms_et_mail_man.getText().clear();
        tms_problem_statement.getText().clear();
    }
}
