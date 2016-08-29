package com.aichifan.app4myqa.vo;

/**
 * Created by yoda on 16/8/29.
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
