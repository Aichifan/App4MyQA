package com.aichifan.app4myqa;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class OverviewActivity extends UserInfoActivity {
    ViewPager pager=null;
    PagerTabStrip tabStrip=null;
    ArrayList<View> viewContainer=new ArrayList<>();
    ArrayList<String> titleContainer=new ArrayList<>();
    public String TAG="tag";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        setHeader(getString(R.string.OverviewActivityTitle), true, true);

        pager=(ViewPager)findViewById(R.id.viewpager);
        View eventView = LayoutInflater.from(this).inflate(R.layout.fragment_event, null);
        View systemView = LayoutInflater.from(this).inflate(R.layout.fragment_system, null);
        View photoView = LayoutInflater.from(this).inflate(R.layout.fragment_photo,null);
        viewContainer.add(eventView);
        viewContainer.add(systemView);
        viewContainer.add(photoView);
        titleContainer.add("事件列表");
        titleContainer.add("系统列表");
        titleContainer.add("图表列表");

        pager.setAdapter(new PagerAdapter() {

            //viewpager中的组件数量
            @Override
            public int getCount() {
                return viewContainer.size();
            }
            //滑动切换的时候销毁当前的组件
            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                ((ViewPager) container).removeView(viewContainer.get(position));
            }
            //每次滑动的时候生成的组件
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ((ViewPager) container).addView(viewContainer.get(position));
                return viewContainer.get(position);
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getItemPosition(Object object) {
                return super.getItemPosition(object);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titleContainer.get(position);
            }
        });

    }
}
