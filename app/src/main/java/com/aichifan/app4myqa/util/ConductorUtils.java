package com.aichifan.app4myqa.util;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
    public static ArrayList<String> arr(InputStream is)
    {
        ObjectMapper objectMapper=new ObjectMapper() ;
        User[]arr= new User[0];
        try {
            arr = objectMapper.readValue(new InputStreamReader(is),User[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ;
        ArrayList<String>arrayList=new ArrayList<>() ;
        for(int i=0 ;i<arr.length ;i++)
        {
            arrayList.add(arr[i].getUsername()) ;
        }
        return arrayList ;
    }

    public static void setAdapter(int layout , Activity activity , int itemlayout , ArrayList<String>arr)
    {
        Spinner sn= (Spinner) activity.findViewById(layout);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(activity,itemlayout,arr) ;
        sn.setAdapter(adapter);
    }
}
