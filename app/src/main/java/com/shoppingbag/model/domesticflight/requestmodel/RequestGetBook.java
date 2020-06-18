package com.shoppingbag.model.domesticflight.requestmodel;

import com.google.gson.annotations.SerializedName;

public class RequestGetBook {

    @SerializedName("Authentication")
    private Authentication authentication;

    @SerializedName("UserTrackId")
    private String userTrackId;

    @SerializedName("FK_MemID")
    private String fK_MemID;

    @SerializedName("Origin")
    private String origin;

    @SerializedName("Destination")
    private String destination;

    @SerializedName("DepartingDate")
    private String departingDate;

    @SerializedName("ReturningDate")
    private String returningDate;

    @SerializedName("Class")
    private String class_type;

    @SerializedName("BookInput")
    private BookInput bookInput;

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public String getUserTrackId() {
        return userTrackId;
    }

    public void setUserTrackId(String userTrackId) {
        this.userTrackId = userTrackId;
    }

    public BookInput getBookInput() {
        return bookInput;
    }

    public void setBookInput(BookInput bookInput) {
        this.bookInput = bookInput;
    }

    @Override
    public String toString() {
        return
                "RequestGetBook{" +
                        "authentication = '" + authentication + '\'' +
                        ",userTrackId = '" + userTrackId + '\'' +
                        ",bookInput = '" + bookInput + '\'' +
                        "}";
    }

    public String getfK_MemID() {
        return fK_MemID;
    }

    public void setfK_MemID(String fK_MemID) {
        this.fK_MemID = fK_MemID;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepartingDate() {
        return departingDate;
    }

    public void setDepartingDate(String departingDate) {
        this.departingDate = departingDate;
    }

    public String getReturningDate() {
        return returningDate;
    }

    public void setReturningDate(String returningDate) {
        this.returningDate = returningDate;
    }

    public String getClass_type() {
        return class_type;
    }

    public void setClass_type(String class_type) {
        this.class_type = class_type;
    }
}