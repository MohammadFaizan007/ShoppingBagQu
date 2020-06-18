package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

public class ChildReqFields {

    @SerializedName("DateOfBirth")
    private String dateOfBirth;

    @SerializedName("PassportExpiryDate")
    private String passportExpiryDate;

    @SerializedName("IdProof")
    private String idProof;

    @SerializedName("PassportIssuingCountry")
    private String passportIssuingCountry;

    @SerializedName("PassportNumber")
    private String passportNumber;

    @SerializedName("Gender")
    private String gender;

    @SerializedName("Age")
    private String age;

    @SerializedName("Nationality")
    private String nationality;

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassportExpiryDate() {
        return passportExpiryDate;
    }

    public void setPassportExpiryDate(String passportExpiryDate) {
        this.passportExpiryDate = passportExpiryDate;
    }

    public String getIdProof() {
        return idProof;
    }

    public void setIdProof(String idProof) {
        this.idProof = idProof;
    }

    public String getPassportIssuingCountry() {
        return passportIssuingCountry;
    }

    public void setPassportIssuingCountry(String passportIssuingCountry) {
        this.passportIssuingCountry = passportIssuingCountry;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Override
    public String toString() {
        return
                "ChildReqFields{" +
                        "dateOfBirth = '" + dateOfBirth + '\'' +
                        ",passportExpiryDate = '" + passportExpiryDate + '\'' +
                        ",idProof = '" + idProof + '\'' +
                        ",passportIssuingCountry = '" + passportIssuingCountry + '\'' +
                        ",passportNumber = '" + passportNumber + '\'' +
                        ",gender = '" + gender + '\'' +
                        ",age = '" + age + '\'' +
                        ",nationality = '" + nationality + '\'' +
                        "}";
    }
}