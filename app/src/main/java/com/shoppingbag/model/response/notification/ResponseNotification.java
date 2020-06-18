package com.shoppingbag.model.response.notification;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseNotification {

    @SerializedName("notificationList")
    private List<NotificationListItem> notificationList;

    @SerializedName("response")
    private String response;

    public List<NotificationListItem> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<NotificationListItem> notificationList) {
        this.notificationList = notificationList;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}