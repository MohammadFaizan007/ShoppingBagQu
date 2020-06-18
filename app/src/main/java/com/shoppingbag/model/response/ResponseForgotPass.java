package com.shoppingbag.model.response;

import com.google.gson.annotations.SerializedName;

public class ResponseForgotPass {

    @SerializedName("password")
    private String password;

    @SerializedName("fk_memId")
    private String fk_memId;

    @SerializedName("ewalletPassword")
    private String ewalletPassword;

    @SerializedName("response")
    private String response;

    @SerializedName("name")
    private String name;

    @SerializedName("mobileNo")
    private String mobileNo;

    @SerializedName("message")
    private String message;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getfk_memId() {
        return fk_memId;
    }

    public void setfk_memId(String fk_memId) {
        this.fk_memId = fk_memId;
    }

    public String getEwalletPassword() {
        return ewalletPassword;
    }

    public void setEwalletPassword(String ewalletPassword) {
        this.ewalletPassword = ewalletPassword;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return
                "ResponseForgotPass{" +
                        "password = '" + password + '\'' +
                        ",fk_memId = '" + fk_memId + '\'' +
                        ",ewalletPassword = '" + ewalletPassword + '\'' +
                        ",response = '" + response + '\'' +
                        ",name = '" + name + '\'' +
                        ",mobileNo = '" + mobileNo + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}