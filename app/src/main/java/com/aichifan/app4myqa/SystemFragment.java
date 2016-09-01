package com.aichifan.app4myqa;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.aichifan.app4myqa.util.MyUrlUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import com.aichifan.app4myqa.pojo.Question;
import com.aichifan.app4myqa.vo.ItemAdapter;
import com.aichifan.app4myqa.vo.Pagination;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Fragment;
import android.widget.TextView;

public class SystemFragment extends Fragment {

    Pagination<Question> page=new Pagination<Question>();
    ItemAdapter adapter;
    ArrayList<Question> questions=new ArrayList<Question>();
    ArrayList<Question> questions1=new ArrayList<Question>();
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View systemLayout = inflater.inflate(R.layout.fragment_system, container, false);
        return systemLayout;
    }
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        new Thread()
        {
            public void run()
            {
                ObjectMapper objectMapper=new ObjectMapper();
                InputStream is=MyUrlUtil.requestByUrl(MainActivity.HOST+"/question/page2","GET",null);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                String line = null;
                StringBuffer sb = new StringBuffer();
                try {
                    while((line = bufferedReader.readLine()) != null){
                        sb.append(line);
                    }
                    System.out.println(sb.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (is == null)
                {
                    Log.v("test","system null");
                }else
                {
                    try
                    {
                        Log.v("test","system success");
                        page = objectMapper.readValue(is, Pagination.class);
                        System.out.println(page.getData().get(0).getCorrectiveAction().toString());
                        questions.addAll(page.getData());
                        questions1.addAll(page.getData());

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run()
                            {
                                ListView listView=(ListView) getActivity().findViewById(R.id.systemlist);
                                EditText et=(EditText)getActivity().findViewById(R.id.search_et);
                                adapter=new ItemAdapter(getActivity(),R.layout.itemlayout,questions);
                                listView.setAdapter(adapter);
                                et.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                    }

                                    @Override
                                    public void afterTextChanged(Editable editable) {
                                        String str=editable.toString();
                                        questions.clear();
                                        for (int i=0;i<questions1.size();i++)
                                        {
                                            if (questions1.get(i).getNumber().contains(str)||questions1.get(i).getCreator().getUsername().contains(str)||
                                                    questions1.get(i).getClosed().toString().contains(str))
                                            {
                                                questions.add(questions1.get(i));
                                                adapter.notifyDataSetChanged();
                                            }
                                        }
                                    }
                                });
                            }
                        });
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
