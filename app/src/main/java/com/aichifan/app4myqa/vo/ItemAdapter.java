package com.aichifan.app4myqa.vo;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aichifan.app4myqa.R;
import com.aichifan.app4myqa.pojo.Question;

import java.util.List;

/**
 * Created by michael on 2016/8/31.
 */
public class ItemAdapter extends ArrayAdapter<Question>
{
    private Activity context;
    private List<Question> events;
    public ItemAdapter(Activity context, int resource, List<Question> events) {
        super(context, resource,events);
        this.context = context;
        this.events=events;
        if (events.toString().equals("[]"))
        {
            Log.v("test","null");
        }

    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        LayoutInflater inflater = context.getLayoutInflater();
        ////将xml布局转换为view对象
        rowView = inflater.inflate(R.layout.itemlayout, parent, false);
        ////利用view对象，找到布局中的组件
        TextView number=(TextView)rowView.findViewById(R.id.bianhao);
        TextView creator=(TextView)rowView.findViewById(R.id.faqiren);
        TextView status=(TextView)rowView.findViewById(R.id.state);

            Question event= new Question();
                    event = events.get(position);
            number.setText(event.getNumber());
            creator.setText(event.getCreator().getUsername());
            status.setText(new Boolean(event.getClosed()).toString());
        return rowView;
    }
}
