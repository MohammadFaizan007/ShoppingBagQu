package com.shoppingbag.model.response.notification;

import com.google.gson.annotations.SerializedName;

public class NotificationListItem {

    @SerializedName("notificationTitle")
    private String notificationTitle;

    @SerializedName("notificationTime")
    private String notificationTime;

    @SerializedName("notificationMessage")
    private String notificationMessage;

    @SerializedName("isRead")
    private String isRead;

    @SerializedName("notificationId")
    private String notificationId;

    @SerializedName("notificationDate")
    private String notificationDate;

    @SerializedName("image")
    private String image;

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(String notificationTime) {
        this.notificationTime = notificationTime;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}