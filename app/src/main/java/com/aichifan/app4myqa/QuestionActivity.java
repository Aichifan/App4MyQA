package com.aichifan.app4myqa;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.aichifan.app4myqa.pojo.City;
import com.aichifan.app4myqa.pojo.Group;
import com.aichifan.app4myqa.pojo.GroupUtils;
import com.aichifan.app4myqa.pojo.IdText;
import com.aichifan.app4myqa.pojo.Question;
import com.aichifan.app4myqa.pojo.User;
import com.aichifan.app4myqa.util.ConductorUtils;
import com.aichifan.app4myqa.util.MyUrlUtil;
import com.aichifan.app4myqa.vagerview.NiceSpinner;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class QuestionActivity extends AppCompatActivity {

    String scaleofproblem ;
    City city ;
    String project ;
    String questiontype;
    Integer emailgroupID ;
    User conductor ;
    Date startdata ;
    Date enddata ;
    ArrayAdapter<String>cityadapter ;
    private Spinner snquestiontype;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        final EditText startText= (EditText) findViewById(R.id.ed_starttime);
        final EditText endText= (EditText) findViewById(R.id.ed_endtime);
        final Calendar c = Calendar.getInstance();
        startText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(QuestionActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar c = Calendar.getInstance();
                        c.set(year, monthOfYear, dayOfMonth);
                        startText.setText(DateFormat.format("yyy年MM月dd日", c));

                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
        endText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog=new DatePickerDialog(QuestionActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)  {
                        Calendar c = Calendar.getInstance();
                        c.set(year, monthOfYear, dayOfMonth);
                        endText.setText(DateFormat.format("yyy年MM月dd日", c));
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dialog.show();

            }
        });

        String starttime=((EditText)findViewById(R.id.ed_starttime)).getText().toString() ;
        String endtime=((EditText)findViewById(R.id.ed_endtime)).getText().toString() ;
        Log.v("starttime",starttime) ;
        startdata=new Date() ;
        enddata=new Date() ;
        SimpleDateFormat sim=new SimpleDateFormat("yyyy年MM月dd");
        try {
            if(!TextUtils.isEmpty(starttime)) {
                startdata = sim.parse(starttime);
            }
            if(!TextUtils.isEmpty(endtime)) {
                enddata=sim.parse(endtime) ;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyUrlUtil.moniLogin("zhanghao","1234");
                InputStream iscity= MyUrlUtil.requestByUrl(MainActivity.HOST+"/dict/cities","GET",null) ;
                InputStream isprojects=MyUrlUtil.requestByUrl(MainActivity.HOST+"/dict/projects","GET",null) ;
                InputStream isquestiontype=MyUrlUtil.requestByUrl(MainActivity.HOST+"/dict/types/ms","GET",null) ;
                InputStream isscaleofproblem=MyUrlUtil.requestByUrl(MainActivity.HOST+"/dict/severity","GET",null) ;
                Gson gson=new Gson() ;
                final IdText[]cityarr=gson.fromJson(new InputStreamReader(iscity),IdText[].class) ;
                IdText[]projectarr=gson.fromJson(new InputStreamReader(isprojects),IdText[].class) ;
                IdText[]questiontypearr=gson.fromJson(new InputStreamReader(isquestiontype),IdText[].class) ;
                IdText[]scaleofproblemarr=gson.fromJson(new InputStreamReader(isscaleofproblem),IdText[].class) ;
                final ArrayList<String>citynames=new ArrayList<String>() ;
                final ArrayList<String>projectnames=new ArrayList<String>() ;
                final ArrayList<String>questiontypenames=new ArrayList<String>() ;
                final ArrayList<String>scaleofproblemnames=new ArrayList<String>() ;
                InputStream isemailgroup=MyUrlUtil.requestByUrl(MainActivity.HOST+"/user/group/all","GET",null) ;
                final Group[]grouparr= GroupUtils.arr(isemailgroup) ;
                final ArrayList<String>emailgroupnames=new ArrayList<String>() ;
                InputStream isconductor=MyUrlUtil.requestByUrl(MainActivity.HOST+"/user/role/handlers","GET",null) ;
                final User[]conductorarr= ConductorUtils.arr(isconductor) ;
                final ArrayList<String>conductornames=new ArrayList<String>() ;
                for(int i=0 ;i<cityarr.length ;i++) {
                    citynames.add(cityarr[i].getText());
                }
                for(int i=0 ;i<projectarr.length ;i++) {
                    projectnames.add(projectarr[i].getText()) ;
                }
                for(int i=0 ;i<questiontypearr.length ;i++) {
                    questiontypenames.add(questiontypearr[i].getText()) ;
                }
                for(int i=0 ;i<scaleofproblemarr.length ;i++)
                {
                    scaleofproblemnames.add(scaleofproblemarr[i].getText()) ;
                }
                for(int i=0 ;i<grouparr.length ;i++)
                {
                    emailgroupnames.add(grouparr[i].getName().toString()) ;
                }
                for(int i=0 ;i<conductorarr.length ;i++)
                {
                    conductornames.add(conductorarr[i].getUsername()) ;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final Spinner sncity= (Spinner) findViewById(R.id.sp_city);
                        sncity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                 IdText idText=cityarr[i] ;
                                Log.v("idText",idText.getId()) ;
                                if(idText!=null) {
                                    city=new City() ;
                                    city.setId(new Integer(idText.getId()));
                                    city.setName(idText.getText());
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        cityadapter=new ArrayAdapter<String>(QuestionActivity.this,android.R.layout.simple_spinner_item,citynames) ;
                        sncity.setAdapter(cityadapter);
                        final Spinner snproject= (Spinner) findViewById(R.id.sp_program);

                        snproject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                project=snproject.getSelectedItem().toString() ;
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        ArrayAdapter<String>projectadapter=new ArrayAdapter<String>(QuestionActivity.this,android.R.layout.simple_spinner_item,projectnames) ;
                        snproject.setAdapter(projectadapter);

                        snquestiontype= (Spinner) findViewById(R.id.sp_questiontype);
                        snquestiontype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                questiontype=snquestiontype.getSelectedItem().toString() ;
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        ArrayAdapter<String>questiontypeadapter=new ArrayAdapter<String>(QuestionActivity.this,android.R.layout.simple_spinner_item,questiontypenames) ;
                        snquestiontype.setAdapter(questiontypeadapter);
                        final Spinner snscaleofproblem= (Spinner) findViewById(R.id.sp_scaleofproblem);
                        snscaleofproblem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                try {
                                     scaleofproblem = snscaleofproblem.getSelectedItem().toString();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        ArrayAdapter<String>scaleofproblemadapter=new ArrayAdapter<String>(QuestionActivity.this,android.R.layout.simple_spinner_item,scaleofproblemnames) ;
                        snscaleofproblem.setAdapter(scaleofproblemadapter);
                        emailgroupID=GroupUtils.setAdapter(R.id.sp_emailgroup,QuestionActivity.this,android.R.layout.simple_spinner_item,emailgroupnames,grouparr);
                        conductor=ConductorUtils.setAdapter(R.id.sp_conductor,QuestionActivity.this,android.R.layout.simple_spinner_item,conductornames,conductorarr);
                    }
                });

            }
        }).start();
    }
    Question question=new Question() ;
    public void sumbitQuestion(View view)
    {


        new Thread(new Runnable() {
            @Override
            public void run() {
                ObjectMapper objectMapper=new ObjectMapper() ;
                try {
                    question.setCategory("WMS");
                    question.setProject(project);
                    question.setCity(city);
                    question.setType(questiontype);
                    question.setEmailto(((EditText)findViewById(R.id.ed_email)).getText().toString());
                    Integer group_id=emailgroupID;
                    Group group = new Group();
                    group.setId(group_id);
                    question.setGroup(group);
                    question.setDescription(((EditText)findViewById(R.id.ed_describequestion)).getText().toString());
                    question.setSeverity(scaleofproblem);
                    question.setHandler(conductor);
                    question.setStartdate(startdata);
                    question.setPromisedate(enddata);

                    InputStream is=MyUrlUtil.requestByUrl(MainActivity.HOST+"/question/create","POST",objectMapper.writeValueAsString(question)) ;
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void resetQuestion(View view)
    {
        ((EditText)findViewById(R.id.ed_email)).setText(null);
        ((EditText)findViewById(R.id.ed_describequestion)).setText(null);
        ((EditText)findViewById(R.id.ed_starttime)).setText(null);
        ((EditText)findViewById(R.id.ed_starttime)).setText(null);
    }
    public void postAttchment(View view)
    {

    }
}
