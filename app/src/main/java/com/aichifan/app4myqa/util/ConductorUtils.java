package com.aichifan.app4myqa.util;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.aichifan.app4myqa.QuestionActivity;
import com.aichifan.app4myqa.pojo.Group;
import com.aichifan.app4myqa.pojo.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by mic on 2016/8/30.
 */
public class ConductorUtils {
    public static User[]arr= new User[0];
    public static User[] arr(InputStream is)
    {
        ObjectMapper objectMapper=new ObjectMapper() ;

        try {
            arr = objectMapper.readValue(new InputStreamReader(is),User[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return arr ;
    }
    public static User conductor ;
    public static User setAdapter(int layout , QuestionActivity activity , int itemlayout , ArrayList<String>arr, final User[]users)
    {
        final Spinner sn= (Spinner) activity.findViewById(layout);

        sn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                conductor=users[i] ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(activity,itemlayout,arr) ;
        sn.setAdapter(adapter);
        return conductor ;
    }
}
