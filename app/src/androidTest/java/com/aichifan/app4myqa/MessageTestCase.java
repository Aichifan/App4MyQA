package com.aichifan.app4myqa;

import android.app.UiAutomation;
import android.os.RemoteException;
import android.support.test.uiautomator.UiAutomatorTestCase;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import java.io.IOException;

/**
 * Created by mic on 2016/9/19.
 */
public class MessageTestCase extends UiAutomatorTestCase{
    public void testDemo() throws UiObjectNotFoundException,  RemoteException {
        UiDevice device = getUiDevice();
        device.pressHome();
//

        UiObject app = new UiObject(new UiSelector().description("应用"));
        app.clickAndWaitForNewWindow();
//        UiObject seven = new UiObject(new UiSelector().resourceId("com.android.calculator2:id/digit7"));

        UiObject appname = new UiObject(new UiSelector().description("App 4 MyQA"));
        appname.clickAndWaitForNewWindow();
        UiObject et1 = new UiObject(new UiSelector().resourceId("com.aichifan.app4myqa:id/edit_loginID"));
        et1.setText("cuiyuanhang");
        UiObject et2 = new UiObject(new UiSelector().resourceId("com.aichifan.app4myqa:id/edit_password"));
        et2.setText("1234");
        UiObject bt1 = new UiObject(new UiSelector().resourceId("com.aichifan.app4myqa:id/loginBtn"));
        bt1.click();
        device.pressBack();
    }
}
