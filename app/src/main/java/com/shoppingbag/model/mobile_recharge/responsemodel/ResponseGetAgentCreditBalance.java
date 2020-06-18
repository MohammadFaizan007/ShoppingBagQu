package com.shoppingbag.model.mobile_recharge.responsemodel;

import com.google.gson.annotations.SerializedName;

public class ResponseGetAgentCreditBalance {

    @SerializedName("ResponseStatus")
    private int responseStatus;

    @SerializedName("AgentCreditBalanceOutput")
    private AgentCreditBalanceOutput agentCreditBalanceOutput;

    public int getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }

    public AgentCreditBalanceOutput getAgentCreditBalanceOutput() {
        return agentCreditBalanceOutput;
    }

    public void setAgentCreditBalanceOutput(AgentCreditBalanceOutput agentCreditBalanceOutput) {
        this.agentCreditBalanceOutput = agentCreditBalanceOutput;
    }

    @Override
    public String toString() {
        return
                "ResponseGetAgentCreditBalance{" +
                        "responseStatus = '" + responseStatus + '\'' +
                        ",agentCreditBalanceOutput = '" + agentCreditBalanceOutput + '\'' +
                        "}";
    }
}