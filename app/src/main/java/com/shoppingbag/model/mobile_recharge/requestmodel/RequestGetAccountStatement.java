package com.shoppingbag.model.mobile_recharge.requestmodel;

import com.google.gson.annotations.SerializedName;

public class RequestGetAccountStatement {

    @SerializedName("Authentication")
    private Authentication authentication;

    @SerializedName("AccountStatementInput")
    private AccountStatementInput accountStatementInput;

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public AccountStatementInput getAccountStatementInput() {
        return accountStatementInput;
    }

    public void setAccountStatementInput(AccountStatementInput accountStatementInput) {
        this.accountStatementInput = accountStatementInput;
    }

    @Override
    public String toString() {
        return
                "RequestGetAccountStatement{" +
                        "authentication = '" + authentication + '\'' +
                        ",accountStatementInput = '" + accountStatementInput + '\'' +
                        "}";
    }
}