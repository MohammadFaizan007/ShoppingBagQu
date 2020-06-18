package com.shoppingbag.model.domesticflight.requestmodel;

import com.google.gson.annotations.SerializedName;

public class Authentication {

    @SerializedName("LoginId")
    private String loginId;

    @SerializedName("Password")
    private String password;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return
                "Authentication{" +
                        "loginId = '" + loginId + '\'' +
                        ",password = '" + password + '\'' +
                        "}";
    }
}