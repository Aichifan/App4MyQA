package com.aichifan.app4myqa.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.aichifan.app4myqa.EventAddActivity;
import com.aichifan.app4myqa.R;
import com.aichifan.app4myqa.pojo.QuestionAttachment;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by mic on 2016/9/8.
 */
public class attachmentAdapter extends BaseAdapter implements View.OnClickListener{

    public  List<QuestionAttachment> questionAttachments;
    private QuestionAttachment questionAttachment;
    private Context context;
    private SimpleDateFormat simple;
    private Callback callback;

    @Override
    public void onClick(View view) {
        callback.click(view);
    }

    public interface Callback {
      public void click(View v);
     }


    public attachmentAdapter(Context context, List<QuestionAttachment> questionAttachments,Callback callback) {
        this.questionAttachments = questionAttachments;
        this.context = context;
        this.callback = callback;
    }

    @Override
    public int getCount() {
        return questionAttachments.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, final ViewGroup viewGroup) {
        View convertView = null;
        ViewHolder viewHolder;
        questionAttachment = new QuestionAttachment();
        questionAttachment = questionAttachments.get(i);
        viewHolder = new ViewHolder();
        if (view != null) {
            convertView = view;
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = view.inflate(context,R.layout.adapter_add_attachment,null);
            viewHolder.event_tv_file_name = (TextView) convertView.findViewById(R.id.event_tv_file_name);
            viewHolder.event_tv_date = (TextView) convertView.findViewById(R.id.event_tv_date);
            viewHolder.event_tv_size = (TextView) convertView.findViewById(R.id.event_tv_size);
            viewHolder.event_tv_owner = (TextView) convertView.findViewById(R.id.event_tv_owner);
            viewHolder.event_bt_delete = (Button) convertView.findViewById(R.id.event_bt_delete);
            convertView.setTag(viewHolder);
        }

        viewHolder.event_tv_file_name.setText(questionAttachment.getFilename());
        simple = new SimpleDateFormat("yyy年MM月dd日 HH:mm:ss");
        if (questionAttachment.getUploaded()!=null)
            viewHolder.event_tv_date.setText(simple.format(questionAttachment.getUploaded()));
        viewHolder.event_tv_size.setText(questionAttachment.getSize());
        viewHolder.event_tv_owner.setText(questionAttachment.getUploader().getUsername());
//        viewHolder.event_bt_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.v("+++++i",i+"");
//                questionAttachments.remove(i);
//                notifyDataSetChanged();
//
//            }
//        });
        viewHolder.event_bt_delete.setOnClickListener(this);
        viewHolder.event_bt_delete.setTag(i);
        return  convertView;
    }
}

 class ViewHolder {
    public TextView event_tv_file_name;
    public TextView event_tv_date;
    public TextView event_tv_size;
    public TextView event_tv_owner;
    public Button event_bt_delete;
}



