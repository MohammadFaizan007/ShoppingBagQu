package com.shoppingbag.model.response.wallet;

import com.google.gson.annotations.SerializedName;

public class HoldwalletListItem {

    @SerializedName("date")
    private String date;

    @SerializedName("amount")
    private String amount;

    @SerializedName("loginId")
    private String loginId;

    @SerializedName("name")
    private String name;

    @SerializedName("remark")
    private String remark;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return
                "HoldwalletListItem{" +
                        "date = '" + date + '\'' +
                        ",amount = '" + amount + '\'' +
                        ",loginId = '" + loginId + '\'' +
                        ",name = '" + name + '\'' +
                        "}";
    }
}