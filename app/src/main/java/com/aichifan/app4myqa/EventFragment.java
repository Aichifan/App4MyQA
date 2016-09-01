package com.aichifan.app4myqa;

import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.aichifan.app4myqa.pojo.Question;
import com.aichifan.app4myqa.util.MyUrlUtil;
import com.aichifan.app4myqa.vo.ItemAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import android.app.Fragment;


public class EventFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View eventLayout = inflater.inflate(R.layout.fragment_event, container, false);
        return eventLayout;
    }
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        new Thread()
        {
            public void run()
            {
                MyUrlUtil.moniLogin("admin","admin");
                final ArrayList<Question> questions = new ArrayList<Question>();
                final ArrayList<Question> questions1=new ArrayList<Question>();
                InputStream is=MyUrlUtil.requestByUrl(MainActivity.HOST+"/question/pmList","GET",null);
                if (is!=null)
                {
                    Log.v("test","success");
                    ObjectMapper objectMapper=new ObjectMapper() ;
                    try
                    {
                        Question[] questionArr=objectMapper.readValue(is,Question[].class);
                        questions.addAll(Arrays.asList(questionArr));
                        questions1.addAll(Arrays.asList(questionArr));
                        getActivity().runOnUiThread(new Runnable()
                        {
                            public void run()
                            {
                                ListView listView=(ListView) getActivity().findViewById(R.id.eventlist);
                                final ItemAdapter adapter=new ItemAdapter(getActivity(),R.layout.itemlayout, questions);
                                final EditText search=(EditText) getActivity().findViewById(R.id.search_et);
                                listView.setAdapter(adapter);
                                search.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                    }
                                    @Override
                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                    }
                                    @Override
                                    public void afterTextChanged(Editable editable) {
                                        String str = editable.toString();
                                        questions.clear();
                                        for(int i = 0;i<questions1.size();i++){
                                            if(questions1.get(i).getNumber().contains(str)||questions1.get(i).getCreator().getUsername().contains(str)||
                                                    questions1.get(i).getType().contains(str)){
                                                questions.add(questions1.get(i));
                                            }
                                        }
                                        adapter.notifyDataSetChanged();
                                    }
                                });

                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                {
                                    public void onItemClick(AdapterView<?> var1, View var2,int position, long var4)
                                    {
                                        Intent intent = new Intent(getActivity(),QuestionCloseActivity.class);
                                        //intent.putExtra("key",questions.get(position));
                                        startActivity(intent);
                                    }
                                });
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else
                {
                    Log.v("test","fail");
                }
            }
        }.start();

    }
}
