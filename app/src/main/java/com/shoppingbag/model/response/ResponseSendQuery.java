package com.shoppingbag.model.response;

import com.google.gson.annotations.SerializedName;

public class ResponseSendQuery {

    @SerializedName("response")
    private String response;

    @SerializedName("messageId")
    private String messageId;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Override
    public String toString() {
        return
                "ResponseSendQuery{" +
                        "response = '" + response + '\'' +
                        ",messageId = '" + messageId + '\'' +
                        "}";
    }
}