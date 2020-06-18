package com.shoppingbag.model.mobile_recharge.responsemodel;

import com.google.gson.annotations.SerializedName;

public class ResponseGetAccountStatement {

    @SerializedName("ResponseStatus")
    private int responseStatus;

    @SerializedName("AccountStatementOutput")
    private AccountStatementOutput accountStatementOutput;

    public int getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }

    public AccountStatementOutput getAccountStatementOutput() {
        return accountStatementOutput;
    }

    public void setAccountStatementOutput(AccountStatementOutput accountStatementOutput) {
        this.accountStatementOutput = accountStatementOutput;
    }

    @Override
    public String toString() {
        return
                "ResponseGetAccountStatement{" +
                        "responseStatus = '" + responseStatus + '\'' +
                        ",accountStatementOutput = '" + accountStatementOutput + '\'' +
                        "}";
    }
}