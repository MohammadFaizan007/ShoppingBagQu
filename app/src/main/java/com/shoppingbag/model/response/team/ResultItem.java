package com.shoppingbag.model.response.team;


import com.google.gson.annotations.SerializedName;


public class ResultItem {

    @SerializedName("MemberId")
    private int memberId;

    @SerializedName("Email")
    private Object email;

    @SerializedName("LoginId")
    private String loginId;

    @SerializedName("FirstName")
    private String firstName;

    @SerializedName("LastName")
    private String lastName;

    @SerializedName("TotalBusiness")
    private String totalBusiness;

    @SerializedName("TotalDirect")
    private String totalDirect;

    @SerializedName("joiningDate")
    private String joiningDate;

    @SerializedName("Mobile")
    private String mobile;

    @SerializedName("Totalrefferal")
    private long totalrefferal;

    @SerializedName("Totalteam")
    private long totalteam;

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getEmail() {
        return email;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setjoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getjoiningDate() {
        return joiningDate;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    @Override
    public String toString() {
        return
                "ResultItem{" +
                        "memberId = '" + memberId + '\'' +
                        ",email = '" + email + '\'' +
                        ",loginId = '" + loginId + '\'' +
                        ",firstName = '" + firstName + '\'' +
                        ",lastName = '" + lastName + '\'' +
                        ",mobile = '" + mobile + '\'' +
                        "}";
    }

    public String getTotalBusiness() {
        return totalBusiness;
    }

    public void setTotalBusiness(String totalBusiness) {
        this.totalBusiness = totalBusiness;
    }

    public String getTotalDirect() {
        return totalDirect;
    }

    public void setTotalDirect(String totalDirect) {
        this.totalDirect = totalDirect;
    }

    public long getTotalrefferal() {
        return totalrefferal;
    }

    public void setTotalrefferal(long totalrefferal) {
        this.totalrefferal = totalrefferal;
    }

    public long getTotalteam() {
        return totalteam;
    }

    public void setTotalteam(long totalteam) {
        this.totalteam = totalteam;
    }
}