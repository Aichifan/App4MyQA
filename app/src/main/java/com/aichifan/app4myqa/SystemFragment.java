package com.aichifan.app4myqa;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.aichifan.app4myqa.util.MyUrlUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.LogRecord;

import com.aichifan.app4myqa.pojo.Question;
import com.aichifan.app4myqa.vo.ItemAdapter;
import com.aichifan.app4myqa.vo.PageList;
import com.aichifan.app4myqa.vo.Pagination;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Fragment;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;

public class SystemFragment extends Fragment implements AbsListView.OnScrollListener {
    PageList page=new PageList();
    ItemAdapter adapter;
    ArrayList<Question> questions=new ArrayList<Question>();
    ArrayList<Question> questions1=new ArrayList<Question>();
    ListView listView;
    Button btn;
    ProgressBar pg;
    View moreView;
    Handler handler=new Handler();
    int lastVisibleIndex;//最后的可视项索引 
    int visibleItemCount; // 当前窗口可见项总数 
    int dataTotal ; //模拟数据集的条数
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View systemLayout = inflater.inflate(R.layout.fragment_system, container, false);
        return systemLayout;
    }
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        listView=(ListView) getActivity().findViewById(R.id.systemlist);
        moreView=getActivity().getLayoutInflater().inflate(R.layout.moredata,null);
        btn= (Button) moreView.findViewById(R.id.bt_load);
        pg= (ProgressBar) moreView.findViewById(R.id.pg);
        listView.addFooterView(moreView);
        new Thread()
        {
            public void run()
            {
                ObjectMapper objectMapper=new ObjectMapper();
                InputStream is=MyUrlUtil.requestByUrl(MainActivity.HOST+"/question/page2","GET",null);
                if (is == null)
                {
                    Log.v("test","system null");
                }else
                {
                    try
                    {
                        Log.v("test","system success");
                        page = objectMapper.readValue(is, PageList.class);
                        //questions.addAll(page.getData());
                        questions1.addAll(page.getData());
                        System.out.println(questions1.size());
                        for (int i=0;i<7;i++)
                        {
                            questions.add(questions1.get(i));
                        }
                        dataTotal=(int)page.getRecordsTotal();
                        System.out.println(dataTotal);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run()
                            {
                                EditText et=(EditText)getActivity().findViewById(R.id.searchsystem);
                                adapter=new ItemAdapter(getActivity(),R.layout.itemlayout,questions);
                                //向listView的底部添加布局

                                listView.setAdapter(adapter);
                                listView.setOnScrollListener(SystemFragment.this);
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
                                        System.out.println(str);
                                        questions.clear();
                                        for (int i=0;i<questions1.size();i++)
                                        {
                                            if (questions1.get(i).getNumber().contains(str)||questions1.get(i).getCreator().getUsername().contains(str)||
                                                    questions1.get(i).getType().contains(str))
                                            {
                                                questions.add(questions1.get(i));
                                            }
                                        }
                                        adapter.notifyDataSetChanged();
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
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount)
    {
        this.visibleItemCount=visibleItemCount;
        lastVisibleIndex=firstVisibleItem+visibleItemCount-1;
        this.dataTotal=totalItemCount;
        if (dataTotal==questions1.size()+1)
        {
            listView.removeFooterView(moreView);
            Toast.makeText(getActivity(),"没有更多数据，加载完毕",Toast.LENGTH_SHORT).show();
        }
    }
    public void onScrollStateChanged(AbsListView view, int scrollState)
    {
        int itemsLastIndex = adapter.getCount()-1; //数据集最后一项的索引 
        int lastIndex = itemsLastIndex + 1;
        if (scrollState== AbsListView.OnScrollListener.SCROLL_STATE_IDLE&&
                lastVisibleIndex==lastIndex)
        {
          loadMore();
        }
    }
    public void loadMore()
    {
        btn.setVisibility(View.GONE);
        pg.setVisibility(View.VISIBLE);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadData();
                btn.setVisibility(View.VISIBLE);
                pg.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }
        },2000);
    }
    private void loadData()
    {
        int count=adapter.getCount();
        for (int i=count;i<count+10;i++)
        {
            if (i<questions1.size())
            {
                adapter.addEvent(questions1.get(i));
            }
        }
    }
}
