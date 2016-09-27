package com.aichifan.app4myqa.vagerview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by mic on 2016/9/8.
 */
public class attachmentListView  extends ListView{

    public attachmentListView(Context context) {
        super(context);
    }

    public attachmentListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public attachmentListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST));
    }
}
