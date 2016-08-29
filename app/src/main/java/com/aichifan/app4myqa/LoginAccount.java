package com.aichifan.app4myqa;

/**
 * Created by S on 2016/8/29.
 */
public class LoginAccount {
    private String loginID;
    private String password;

    public LoginAccount() {

    }

    public LoginAccount(String loginID, String password) {
        this.loginID = loginID;
        this.password = password;
    }

    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
