package com.shoppingbag.model.domesticflight.requestmodel;

import com.google.gson.annotations.SerializedName;

public class CustomerDetails {

    @SerializedName("CountryId")
    private String countryId;

    @SerializedName("EmailId")
    private String emailId;

    @SerializedName("Address")
    private String address;

    @SerializedName("Title")
    private String title;

    @SerializedName("ContactNumber")
    private String contactNumber;

    @SerializedName("City")
    private String city;

    @SerializedName("Name")
    private String name;

    @SerializedName("PinCode")
    private String pinCode;

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    @Override
    public String toString() {
        return
                "CustomerDetails{" +
                        "countryId = '" + countryId + '\'' +
                        ",emailId = '" + emailId + '\'' +
                        ",address = '" + address + '\'' +
                        ",title = '" + title + '\'' +
                        ",contactNumber = '" + contactNumber + '\'' +
                        ",city = '" + city + '\'' +
                        ",name = '" + name + '\'' +
                        ",pinCode = '" + pinCode + '\'' +
                        "}";
    }
}