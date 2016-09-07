package com.aichifan.app4myqa;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aichifan.app4myqa.pojo.City;
import com.aichifan.app4myqa.pojo.ProjectName;
import com.aichifan.app4myqa.pojo.Question;
import com.aichifan.app4myqa.pojo.QuestionAttachment;
import com.aichifan.app4myqa.pojo.User;
import com.aichifan.app4myqa.util.MyUrlUtil;
import com.aichifan.app4myqa.vagerview.NiceSpinner;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.TypeVariable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EventClosedActivity extends UserInfoActivity {
    private static final int IMAGE_REQUEST_CODE = 0;
    private NiceSpinner event_project_name_select;
    private NiceSpinner event_supplier_select;
    private NiceSpinner event_feedback_select;
    private NiceSpinner event_issue_type_select;
    private NiceSpinner event_severity_select;
    private NiceSpinner event_provenace_info_select;
    private LinearLayout ll;
    private TextView event_tv_city;
    private EditText event_et_lssue_date;
    private EditText event_et_containment_date;
    private EditText event_et_Action_plan_date;
    private EditText event_scheduled_delivery_time;
    private EditText event_actual_delivery_time;

    private EditText event_spc_name_select;
    private EditText event_et_order_no;
    private EditText event_hawb;
    private EditText event_part_information;
    private EditText event_problem_statement;
    private EditText event_recovery_description;
    private EditText event_root_cause;
    private EditText event_corrective_action;
    private EditText event_close_bynumber;

    private Button event_close_bt_search;
    private Button event_bt_accessory;
    private Button event_bt_commit;
    private Question question;
    private SimpleDateFormat sim;
    private City city;
    private List<Integer> lists;
    private List<String> ids;
    private List<String> names;
    private List<Integer> checkBoxId;
    private List<CheckBox> checkBoxs;
    private List<String> datesProject;
    private List<String> datesSupplier;
    private List<String> datesFeedback;
    private List<String> datesGroup;
    private List<String> datesIssueType;
    private List<String> datesSeverity;
    private List<String> datesProvenace;
    private String picturePath;

    private int id;

    private List<QuestionAttachment> questionAttachments;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_closed);

        setHeader(getString(R.string.EventClosedActivityTitle), true, true);

        init();
        new Thread(new Runnable() {
            @Override
            public void run() {
                projectName("/dict/projects");
                supplier("/dict/suppliers");
                group("/user/group/pm");

                feedback();
                issueType("/dict/types/pm");
                severity("/dict/severity");
                provenaceInfo("/dict/beginStorehouses");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        groupadd();
                        event_feedback_select.attachDataSource(datesFeedback);
                        event_project_name_select.attachDataSource(datesProject);
                        event_supplier_select.attachDataSource(datesSupplier);
                        event_feedback_select.attachDataSource(datesFeedback);
                        event_issue_type_select.attachDataSource(datesIssueType);
                        event_severity_select.attachDataSource(datesSeverity);
                        event_provenace_info_select.attachDataSource(datesProvenace);
                    }
                });
            }


        }).start();

    }

    private void provenaceInfo(String s) {
        InputStream is = MyUrlUtil.requestByUrl(MainActivity.HOST + s, "GET", null);
        Gson gson = new Gson();
        ProjectName[] projectNames = gson.fromJson(new InputStreamReader(is), ProjectName[].class);
        datesProvenace = new ArrayList<String>();
        datesProvenace.add("请选择");
        for (int i = 0; i < projectNames.length; i++) {
            datesProvenace.add(projectNames[i].getText().toString().trim());
        }
    }

    private void severity(String s) {
        InputStream is = MyUrlUtil.requestByUrl(MainActivity.HOST + s, "GET", null);
        Gson gson = new Gson();
        ProjectName[] projectNames = gson.fromJson(new InputStreamReader(is), ProjectName[].class);
        datesSeverity = new ArrayList<String>();
        datesSeverity.add("请选择");
        for (int i = 0; i < projectNames.length; i++) {
            datesSeverity.add(projectNames[i].getText().toString().trim());
        }
    }

    private void issueType(String s) {
        InputStream is = MyUrlUtil.requestByUrl(MainActivity.HOST + s, "GET", null);
        Gson gson = new Gson();
        ProjectName[] projectNames = gson.fromJson(new InputStreamReader(is), ProjectName[].class);
        datesIssueType = new ArrayList<String>();
        datesIssueType.add("请选择");
        for (int i = 0; i < projectNames.length; i++) {
            datesIssueType.add(projectNames[i].getText().toString().trim());
        }
    }

    private void supplier(String s) {
        InputStream is = MyUrlUtil.requestByUrl(MainActivity.HOST + s, "GET", null);
        Gson gson = new Gson();
        ProjectName[] projectNames = gson.fromJson(new InputStreamReader(is), ProjectName[].class);
        datesSupplier = new ArrayList<String>();
        datesSupplier.add("请选择");
        for (int i = 0; i < projectNames.length; i++) {
            datesSupplier.add(projectNames[i].getText().toString().trim());
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

    private void groupadd() {
        event_tv_city.setText(city.getName());
        for (int i = 0; i < datesGroup.size(); i++) {
            final CheckBox checkBox = new CheckBox(getApplicationContext());
            checkBox.setText(datesGroup.get(i));
            checkBox.setTag(i);
            checkBox.setTextColor(Color.parseColor("#000000"));
            checkBoxs.add(checkBox);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        System.out.println(checkBox.getText().toString());
                    }
                }
            });
            ll.addView(checkBox, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        }
    }

    private void feedback() {
        datesFeedback = new ArrayList<String>();
        datesFeedback.add("Y");
        datesFeedback.add("N");
    }

    private void init() {
        event_close_bynumber = (EditText) findViewById(R.id.event_close_bynumber);
        event_close_bt_search = (Button) findViewById(R.id.event_close_bt_search);
        event_project_name_select = (NiceSpinner) findViewById(R.id.event_project_name_select);
        event_supplier_select = (NiceSpinner) findViewById(R.id.event_supplier_select);
        event_feedback_select = (NiceSpinner) findViewById(R.id.event_feedback_select);

        event_tv_city = (TextView) findViewById(R.id.event_tv_city);
        event_et_lssue_date = (EditText) findViewById(R.id.event_et_lssue_date);

        event_et_containment_date = (EditText) findViewById(R.id.event_et_containment_date);
        event_et_Action_plan_date = (EditText) findViewById(R.id.event_et_Action_plan_date);
        event_issue_type_select = (NiceSpinner) findViewById(R.id.event_issue_type_select);
        event_severity_select = (NiceSpinner) findViewById(R.id.event_severity_select);
        event_provenace_info_select = (NiceSpinner) findViewById(R.id.event_provenace_info_select);
        event_spc_name_select = (EditText) findViewById(R.id.event_spc_name_select);
        event_et_order_no = (EditText) findViewById(R.id.event_et_order_no);
        event_hawb = (EditText) findViewById(R.id.event_hawb);
        event_part_information = (EditText) findViewById(R.id.event_part_information);
        ll = (LinearLayout) findViewById(R.id.event_ll_check);
        event_scheduled_delivery_time = (EditText) findViewById(R.id.event_scheduled_delivery_time);
        event_actual_delivery_time = (EditText) findViewById(R.id.event_actual_delivery_time);

        event_problem_statement = (EditText) findViewById(R.id.event_problem_statement);
        event_recovery_description = (EditText) findViewById(R.id.event_recovery_description);
        event_root_cause = (EditText) findViewById(R.id.event_root_cause);
        event_corrective_action = (EditText) findViewById(R.id.event_corrective_action);
        event_bt_accessory = (Button) findViewById(R.id.event_bt_accessory);
        event_bt_commit = (Button) findViewById(R.id.event_bt_commit);

        lists = new ArrayList<Integer>();
        ids = new ArrayList<String>();
        names = new ArrayList<String>();
        checkBoxs = new ArrayList<CheckBox>();
        datesFeedback = new ArrayList<String>();
        datesProject = new ArrayList<String>();
        checkBoxId = new ArrayList<Integer>();
    }

    public void onCloseClick(View view) {
        switch (view.getId()) {
            case R.id.event_close_bt_search:
                search();
                break;
            case R.id.event_bt_accessory:
                selectPicture();
                break;
            case R.id.event_bt_commit:
                commitInfo();
                break;
        }
    }

    private void commit(String s, String s1, String s2) {
        InputStream is = MyUrlUtil.requestByUrl(MainActivity.HOST + s, s1, s2);
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

    private void commitInfo() {
        question = new Question();
        question.setCategory("PM");
        if (event_project_name_select.getText().toString().equals("请选择")) {
            question.setProject("");
        } else {
            question.setProject(event_project_name_select.getText().toString());
        }

        if (event_supplier_select.getText().toString().equals("请选择")) {
            question.setSupplier("");
        } else {
            question.setSupplier(event_supplier_select.getText().toString());
        }

        SimpleDateFormat sim = new SimpleDateFormat("yyy年MM月dd日");
        try {
            Date date = sim.parse(event_et_lssue_date.getText().toString());
            question.setIssueDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (event_feedback_select.getText().toString().equals("Y")) {
            question.setIsCFeedback(true);
        } else {
            System.out.println("+++++++++++");
            question.setIsCFeedback(false);
        }

        for (int i = 0; i < checkBoxs.size(); i++) {
            if (checkBoxs.get(i).isChecked()) {
                checkBoxId.add(lists.get(i));
            }
        }
        Gson gson = new Gson();
        String json = gson.toJson(checkBoxId);
        question.setTeammates(json);

        sim = new SimpleDateFormat("yyy年MM月dd日");
        try {
            Date date = sim.parse(event_et_containment_date.getText().toString());
            question.setContainmentPlanDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        sim = new SimpleDateFormat("yyy年MM月dd日");
        try {
            Date date = sim.parse(event_et_Action_plan_date.getText().toString());
            question.setActionPlanDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (event_issue_type_select.getText().toString().equals("请选择")) {
            question.setType("");
        } else {
            question.setType(event_issue_type_select.getText().toString());
        }

        if (event_severity_select.getText().toString().equals("请选择")) {
            question.setSeverity("");
        } else {
            question.setSeverity(event_severity_select.getText().toString());
        }

        if (event_provenace_info_select.getText().toString().equals("请选择")) {
            question.setBeginStorehouse("");
        } else {
            question.setBeginStorehouse(event_provenace_info_select.getText().toString());
        }

        question.setSpc(event_spc_name_select.getText().toString());
        question.setOrderNo(event_et_order_no.getText().toString());
        question.setHawb(event_hawb.getText().toString());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间       
        String str1 = formatter.format(curDate);
        question.setPartInformation(str1 + " 来自 " + MainActivity.user.getUsername() + "\n" + event_part_information.getText().toString());

        sim = new SimpleDateFormat("yyy年MM月dd日");
        try {
            Date date = sim.parse(event_scheduled_delivery_time.getText().toString());
            question.setScheduledTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        sim = new SimpleDateFormat("yyy年MM月dd日");
        try {
            Date date = sim.parse(event_actual_delivery_time.getText().toString());
            question.setActualTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        question.setProblemStatement(event_problem_statement.getText().toString());
        question.setDescription(event_recovery_description.getText().toString());
        question.setRootCause(event_root_cause.getText().toString());
        question.setCorrectiveAction(event_corrective_action.getText().toString());
        if (questionAttachments!=null) {
            question.setAttachmentList(questionAttachments);
        }
        clean();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            final String str = objectMapper.writeValueAsString(question);
            System.out.println(str);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    commit("/question/close/"+String.valueOf(id), "PUT", str);
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void clean() {
        event_et_lssue_date.setText("");
        event_et_containment_date.setText("");
        event_et_Action_plan_date.setText("");
        event_spc_name_select.setText("");
        event_et_order_no.setText("");
        event_hawb.setText("");
        event_part_information.setText("");
        event_scheduled_delivery_time.setText("");
        event_actual_delivery_time.setText("");
        event_problem_statement.setText("");
        event_recovery_description.setText("");
        event_root_cause.setText("");
        event_corrective_action.setText("");
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

    private void search() {
        final String s = event_close_bynumber.getText().toString().trim();
        if (s != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    InputStream is = MyUrlUtil.requestByUrl(MainActivity.HOST + "/question/byNumber/" + s, "GET", null);
//                    BufferedReader isb = new BufferedReader(new InputStreamReader(is));
//                    StringBuffer sb = new StringBuffer();
//                    String line = null;
//                    try {
//                        while ((line = isb.readLine()) != null) {
//                            sb.append(line);
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    System.out.println(sb);
                    if(is != null) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        question = new Question();
                        try {
                            question = objectMapper.readValue(new InputStreamReader(is), Question.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                id = question.getId();
                                if (question.getProject()!=null)
                                event_project_name_select.setText(question.getProject());
                                if (question.getSupplier()!=null)
                                event_supplier_select.setText(question.getSupplier());

                                sim = new SimpleDateFormat("yyy年MM月dd日");

                                if (question.getIssueDate()!=null)
                                event_et_lssue_date.setText(sim.format(question.getIssueDate()));

                                if (question.getFeedback()!=null)
                                event_feedback_select.setText(question.getFeedback());
                                
                                Gson gson = new Gson();

                                ids = gson.fromJson(question.getTeammates(), new TypeToken<List<String>>(){}.getType());
                                if (ids!=null) {
                                    for (int i = 0; i < ids.size(); i++) {
                                        for (int j = 0; j < lists.size(); j++) {
                                            if (Integer.parseInt(ids.get(i).toString()) == lists.get(j)) {
                                                names.add(datesGroup.get(i));
                                            }
                                        }
                                    }
                                    if (names.size() != 0) {
                                        for (int i = 0; i < names.size(); i++) {
                                            for (int j = 0; j < checkBoxs.size(); j++) {
                                                if (names.get(i).toString().equals(checkBoxs.get(j).getText().toString())) {
                                                    checkBoxs.get(j).setChecked(true);
                                                }
                                            }
                                        }
                                    }
                                }
                                sim = new SimpleDateFormat("yyy年MM月dd日");
                                if (question.getContainmentPlanDate()!=null)
                                event_et_containment_date.setText(sim.format(question.getContainmentPlanDate()));

                                sim = new SimpleDateFormat("yyy年MM月dd日");
                                if (question.getActionPlanDate()!=null)
                                event_et_Action_plan_date.setText(sim.format(question.getActionPlanDate()));

                                if (question.getType()!=null)
                                event_issue_type_select.setText(question.getType());

                                if (question.getSeverity()!=null)
                                event_severity_select.setText(question.getSeverity());

                                if (question.getBeginStorehouse()!=null)
                                event_provenace_info_select.setText(question.getBeginStorehouse());
                                if (question.getSpc()!=null)
                                event_spc_name_select.setText(question.getSpc());
                                if (question.getOrderNo()!=null)
                                event_et_order_no.setText(question.getOrderNo());
                                if (question.getHawb()!=null)
                                event_hawb.setText(question.getHawb());

                                sim = new SimpleDateFormat("yyy年MM月dd日");
                                if (question.getScheduledTime()!=null)
                                event_scheduled_delivery_time.setText(sim.format(question.getScheduledTime()));

                                sim = new SimpleDateFormat("yyy年MM月dd日");
                                if (question.getActualTime()!=null)
                                event_actual_delivery_time.setText(sim.format(question.getActualTime()));

                                if (question.getProblemStatement()!=null)
                                event_problem_statement.setText(question.getProblemStatement());
                                if (question.getRecoveryDescription()!=null)
                                event_recovery_description.setText(question.getRecoveryDescription());
                                if (question.getRootCause()!=null)
                                event_root_cause.setText(question.getRootCause());
                                if (question.getCorrectiveAction()!=null)
                                event_corrective_action.setText(question.getCorrectiveAction());

                            }
                        });
                    }
                }
            }).start();
        }
    }

    private void group(String s) {
        InputStream is = MyUrlUtil.requestByUrl(MainActivity.HOST + s, "GET", null);

        ObjectMapper objectMapper = new ObjectMapper();
        User[] projectNames = new User[0];
        try {
            projectNames = objectMapper.readValue(new InputStreamReader(is), User[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        city = projectNames[0].getCity();
        datesGroup = new ArrayList<String>();
        for (int i = 0; i < projectNames.length; i++) {
            datesGroup.add(projectNames[i].getUsername().toString().trim());
            lists.add(projectNames[i].getId());
        }
    }
}
