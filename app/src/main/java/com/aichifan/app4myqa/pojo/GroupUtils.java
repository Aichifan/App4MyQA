package com.aichifan.app4myqa.pojo;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.aichifan.app4myqa.WMSAddActivity;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by mic on 2016/8/30.
 */
public class GroupUtils {
    public static Group[]arr=new Group[0];
    public static Group[] arr(InputStream is)
    {
        ObjectMapper objectMapper=new ObjectMapper() ;

        try {

            arr = objectMapper.readValue(new InputStreamReader(is),Group[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return arr ;
    }
   static public Integer groupID ;
    public static Integer setAdapter(int layout , WMSAddActivity activity , int itemlayout , final ArrayList<String>arr, final Group[]groups)
    {
        final Spinner sn= (Spinner) activity.findViewById(layout);

        sn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 groupID=groups[i].getId() ;
                Log.v("groupID",groupID.toString()) ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(activity,itemlayout,arr) ;
        sn.setAdapter(adapter);
        return groupID ;
    }
}
