package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

public class GSTDetails {

    @SerializedName("CompanyName")
    private String companyName;

    @SerializedName("GSTNumber")
    private String gSTNumber;

    @SerializedName("Address")
    private Object address;

    @SerializedName("FirstName")
    private Object firstName;

    @SerializedName("ContactNumber")
    private Object contactNumber;

    @SerializedName("LastName")
    private Object lastName;

    @SerializedName("EMailId")
    private String eMailId;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getGSTNumber() {
        return gSTNumber;
    }

    public void setGSTNumber(String gSTNumber) {
        this.gSTNumber = gSTNumber;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getFirstName() {
        return firstName;
    }

    public void setFirstName(Object firstName) {
        this.firstName = firstName;
    }

    public Object getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Object contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Object getLastName() {
        return lastName;
    }

    public void setLastName(Object lastName) {
        this.lastName = lastName;
    }

    public String getEMailId() {
        return eMailId;
    }

    public void setEMailId(String eMailId) {
        this.eMailId = eMailId;
    }

    @Override
    public String toString() {
        return
                "GSTDetails{" +
                        "companyName = '" + companyName + '\'' +
                        ",gSTNumber = '" + gSTNumber + '\'' +
                        ",address = '" + address + '\'' +
                        ",firstName = '" + firstName + '\'' +
                        ",contactNumber = '" + contactNumber + '\'' +
                        ",lastName = '" + lastName + '\'' +
                        ",eMailId = '" + eMailId + '\'' +
                        "}";
    }
}