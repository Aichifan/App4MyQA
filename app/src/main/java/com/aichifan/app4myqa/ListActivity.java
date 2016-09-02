package com.aichifan.app4myqa;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class ListActivity extends UserInfoActivity implements View.OnClickListener{
    public static final String HOST = "http://139.196.56.98:8080/myQA";
    private EventFragment eventFragment;
    private SystemFragment systemFragment;
    private View eventLayout;
    private View systemLayout;
    private FragmentManager fragmentManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setHeader(getString(R.string.OverviewActivityTitle), true, true);

        initViews();
        fragmentManager = getFragmentManager();
        setTabSelection(0);
    }
    private void initViews()
    {
        eventLayout = findViewById(R.id.event_layout);
        systemLayout = findViewById(R.id.system_layout);
        eventLayout.setOnClickListener(this);
        systemLayout.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.event_layout:
                setTabSelection(0);
                break;
            case R.id.system_layout:
                setTabSelection(1);
                break;
            default:
                break;
        }
    }

    private void setTabSelection(int index) {
        clearSelection();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                eventLayout.setBackgroundColor(0xffffffff);
                if (eventFragment == null) {
                    eventFragment = new EventFragment();
                    transaction.add(R.id.content,eventFragment);
                } else {
                    transaction.show(eventFragment);
                }
                break;
            case 1:
                systemLayout.setBackgroundColor(0xffffffff);
                if (systemFragment == null) {
                    systemFragment = new SystemFragment();
                    transaction.add(R.id.content, systemFragment);
                } else {
                    transaction.show(systemFragment);
                }
                break;

        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction)
    {
        if (eventFragment!= null) {
            transaction.hide(eventFragment);
        }
        if (systemFragment != null) {
            transaction.hide(systemFragment);
        }

    }

    private void clearSelection()
    {
        eventLayout.setBackgroundColor(0xffffffff);
        systemLayout.setBackgroundColor(0xffffffff);

    }
}
