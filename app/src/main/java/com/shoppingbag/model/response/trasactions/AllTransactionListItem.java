package com.shoppingbag.model.response.trasactions;

import com.google.gson.annotations.SerializedName;

public class AllTransactionListItem {

    @SerializedName("Amount")
    private String amount;

    @SerializedName("TransactionStatus")
    private String transactionStatus;

    @SerializedName("Session")
    private String session;

    @SerializedName("MobileNo")
    private String mobileNo;

    @SerializedName("Error")
    private String error;

    @SerializedName("Type")
    private String type;

    @SerializedName("Operator")
    private String operator;

    @SerializedName("TransactionId")
    private String transactionId;

    @SerializedName("Result")
    private String result;

    @SerializedName("ActionType")
    private String actionType;

    @SerializedName("PaymentDate")
    private String paymentDate;

    @SerializedName("DrAmount")
    private String drAmount;

    @SerializedName("CrAmount")
    private String crAmount;

    @SerializedName("Remarks")
    private String remarks;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getDrAmount() {
        return drAmount;
    }

    public void setDrAmount(String drAmount) {
        this.drAmount = drAmount;
    }

    public String getCrAmount() {
        return crAmount;
    }

    public void setCrAmount(String crAmount) {
        this.crAmount = crAmount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}