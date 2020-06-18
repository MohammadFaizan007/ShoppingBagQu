package com.shoppingbag.model.response;

import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("lastLogin")
    private String lastLogin;

    @SerializedName("inviteCode")
    private String inviteCode;

    @SerializedName("pincode")
    private String pincode;

    @SerializedName("Status")
    private String status;

    @SerializedName("loginId")
    private String loginId;

    @SerializedName("city")
    private String city;

    @SerializedName("profilePic")
    private String profilePic;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("message")
    private String message;

    @SerializedName("tensil")
    private String tensil;

    @SerializedName("Flag")
    private int flag;

    @SerializedName("deviceId")
    private String deviceId;

    @SerializedName("token")
    private String token;

    //@SerializedName("name")
    @SerializedName("firstName")
    private String name;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("email")
    private String email;

    @SerializedName("state")
    private String state;

    @SerializedName("androidId")
    private String androidId;

    @SerializedName("memberId")
    private int memberId;

    @SerializedName("bankaccount")
    private String bankaccount;

    @SerializedName("bankname")
    private String bankname;

    @SerializedName("benIFSC")
    private String benIFSC;

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPincode() {
        return pincode;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setTensil(String tensil) {
        this.tensil = tensil;
    }

    public String getTensil() {
        return tensil;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBankaccount() {
        return bankaccount;
    }

    public void setBankaccount(String bankaccount) {
        this.bankaccount = bankaccount;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBenIFSC() {
        return benIFSC;
    }

    public void setBenIFSC(String benIFSC) {
        this.benIFSC = benIFSC;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}