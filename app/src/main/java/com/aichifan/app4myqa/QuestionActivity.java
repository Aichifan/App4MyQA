package com.aichifan.app4myqa;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.aichifan.app4myqa.pojo.City;
import com.aichifan.app4myqa.pojo.Group;
import com.aichifan.app4myqa.pojo.GroupUtils;
import com.aichifan.app4myqa.pojo.IdText;
import com.aichifan.app4myqa.util.ConductorUtils;
import com.aichifan.app4myqa.util.MyUrlUtil;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

public class QuestionActivity extends AppCompatActivity {

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
                DatePickerDialog dialog=new DatePickerDialog(QuestionActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)  {
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

        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream iscity= MyUrlUtil.requestByUrl(MainActivity.HOST+"/dict/cities","GET",null) ;
                InputStream isprojects=MyUrlUtil.requestByUrl(MainActivity.HOST+"/dict/projects","GET",null) ;
                InputStream isquestiontype=MyUrlUtil.requestByUrl(MainActivity.HOST+"/dict/types/ms","GET",null) ;
                InputStream isscaleofproblem=MyUrlUtil.requestByUrl(MainActivity.HOST+"/dict/severity","GET",null) ;
                Gson gson=new Gson() ;
                IdText[]cityarr=gson.fromJson(new InputStreamReader(iscity),IdText[].class) ;
                IdText[]projectarr=gson.fromJson(new InputStreamReader(isprojects),IdText[].class) ;
                IdText[]questiontypearr=gson.fromJson(new InputStreamReader(isquestiontype),IdText[].class) ;
                IdText[]scaleofproblemarr=gson.fromJson(new InputStreamReader(isscaleofproblem),IdText[].class) ;
                final ArrayList<String>citynames=new ArrayList<String>() ;
                final ArrayList<String>projectnames=new ArrayList<String>() ;
                final ArrayList<String>questiontypenames=new ArrayList<String>() ;
                final ArrayList<String>scaleofproblemnames=new ArrayList<String>() ;
                InputStream isemailgroup=MyUrlUtil.requestByUrl(MainActivity.HOST+"/user/group/all","GET",null) ;
                final ArrayList<String>emailgroupnames= new ArrayList<String>(GroupUtils.arr(isemailgroup)) ;
                InputStream isconductor=MyUrlUtil.requestByUrl(MainActivity.HOST+"/user/role/handlers","GET",null) ;
                final ArrayList<String>conductornames= ConductorUtils.arr(isconductor) ;
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

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Spinner sncity= (Spinner) findViewById(R.id.sp_city);
                        ArrayAdapter<String>cityadapter=new ArrayAdapter<String>(QuestionActivity.this,android.R.layout.simple_spinner_item,citynames) ;
                        sncity.setAdapter(cityadapter);
                        Spinner snproject= (Spinner) findViewById(R.id.sp_program);
                        ArrayAdapter<String>projectadapter=new ArrayAdapter<String>(QuestionActivity.this,android.R.layout.simple_spinner_item,projectnames) ;
                        snproject.setAdapter(projectadapter);
                        Spinner snquestiontype= (Spinner) findViewById(R.id.sp_questiontype);
                        ArrayAdapter<String>questiontypeadapter=new ArrayAdapter<String>(QuestionActivity.this,android.R.layout.simple_spinner_item,questiontypenames) ;
                        snquestiontype.setAdapter(questiontypeadapter);
                        Spinner snscaleofproblem= (Spinner) findViewById(R.id.sp_scaleofproblem);
                        ArrayAdapter<String>scaleofproblemadapter=new ArrayAdapter<String>(QuestionActivity.this,android.R.layout.simple_spinner_item,scaleofproblemnames) ;
                        snscaleofproblem.setAdapter(scaleofproblemadapter);
                        GroupUtils.setAdapter(R.id.sp_emailgroup,QuestionActivity.this,android.R.layout.simple_spinner_item,emailgroupnames);
                        ConductorUtils.setAdapter(R.id.sp_conductor,QuestionActivity.this,android.R.layout.simple_spinner_item,conductornames);
                    }
                });

            }
        }).start();
    }
}
