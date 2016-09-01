package com.aichifan.app4myqa;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class ListActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String HOST = "http://139.196.56.98:8080/myQA";
    private EventFragment eventFragment;
    /**
     * 用于展示联系人的Fragment
     */
    private SystemFragment systemFragment;
    /**
     * 用于展示动态的Fragment
     */

    private View eventLayout;
    /**
     * 联系人界面布局
     */
    private View systemLayout;
    /**
     * 动态界面布局
     */
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initViews();
        fragmentManager = getFragmentManager();
        // 第一次启动时选中第0个tab
        setTabSelection(0);
    }
    private void initViews()
    {
        eventLayout = findViewById(R.id.event_layout);
        systemLayout = findViewById(R.id.system_layout);
        eventLayout.setOnClickListener(this);
        systemLayout.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.event_layout:
                // 当点击了消息tab时，选中第1个tab
                setTabSelection(0);
                break;
            case R.id.system_layout:
                // 当点击了联系人tab时，选中第2个tab
                setTabSelection(1);
                break;
            default:
                break;
        }
    }
    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index
     * 每个tab页对应的下标。0表示消息，1表示联系人，2表示动态，3表示设置。
     */
    private void setTabSelection(int index) {
        // 每次选中之前先清楚掉上次的选中状态
        clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                eventLayout.setBackgroundColor(0xffffffff);
                if (eventFragment == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    eventFragment = new EventFragment();
                    transaction.add(R.id.content,eventFragment);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(eventFragment);
                }
                break;
            case 1:
                // 当点击了联系人tab时，改变控件的图片和文字颜色
                systemLayout.setBackgroundColor(0xffffffff);
                if (systemFragment == null) {
                    // 如果ContactsFragment为空，则创建一个并添加到界面上
                    systemFragment = new SystemFragment();
                    transaction.add(R.id.content, systemFragment);
                } else {
                    // 如果ContactsFragment不为空，则直接将它显示出来
                    transaction.show(systemFragment);
                }
                break;

        }
        transaction.commit();
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction
     * 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction)
    {
        if (eventFragment!= null) {
            transaction.hide(eventFragment);
        }
        if (systemFragment != null) {
            transaction.hide(systemFragment);
        }

    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection()
    {
        eventLayout.setBackgroundColor(0xffffffff);
        systemLayout.setBackgroundColor(0xffffffff);

    }
}
