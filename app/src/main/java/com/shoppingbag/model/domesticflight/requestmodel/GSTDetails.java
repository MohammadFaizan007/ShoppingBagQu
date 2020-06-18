package com.shoppingbag.model.domesticflight.requestmodel;

import com.google.gson.annotations.SerializedName;

public class GSTDetails {

    @SerializedName("CompanyName")
    private String companyName;

    @SerializedName("GSTNumber")
    private String gSTNumber;

    @SerializedName("Address")
    private String address;

    @SerializedName("FirstName")
    private String firstName;

    @SerializedName("ContactNumber")
    private String contactNumber;

    @SerializedName("LastName")
    private String lastName;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
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