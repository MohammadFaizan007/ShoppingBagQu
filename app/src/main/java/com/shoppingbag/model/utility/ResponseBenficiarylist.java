package com.shoppingbag.model.utility;

import com.google.gson.annotations.SerializedName;

public class ResponseBenficiarylist {

    @SerializedName("RemitterId")
    private String remitterId;

    @SerializedName("BeneficiaryAccountNo")
    private String beneficiaryAccountNo;

    @SerializedName("BeneficiaryIFSC")
    private String beneficiaryIFSC;

    @SerializedName("BeneficiaryBankName")
    private String beneficiaryBankName;

    @SerializedName("BeneficiaryLastName")
    private String beneficiaryLastName;

    @SerializedName("BeneficiaryFirstName")
    private String beneficiaryFirstName;

    @SerializedName("BeneficiaryMobileNo")
    private String beneficiaryMobileNo;

    @SerializedName("BeneficiaryId")
    private String beneficiaryId;

    @SerializedName("Status")
    private String status;

    private boolean select;

    public String getRemitterId() {
        return remitterId;
    }

    public void setRemitterId(String remitterId) {
        this.remitterId = remitterId;
    }

    public String getBeneficiaryAccountNo() {
        return beneficiaryAccountNo;
    }

    public void setBeneficiaryAccountNo(String beneficiaryAccountNo) {
        this.beneficiaryAccountNo = beneficiaryAccountNo;
    }

    public String getBeneficiaryIFSC() {
        return beneficiaryIFSC;
    }

    public void setBeneficiaryIFSC(String beneficiaryIFSC) {
        this.beneficiaryIFSC = beneficiaryIFSC;
    }

    public String getBeneficiaryBankName() {
        return beneficiaryBankName;
    }

    public void setBeneficiaryBankName(String beneficiaryBankName) {
        this.beneficiaryBankName = beneficiaryBankName;
    }

    public String getBeneficiaryLastName() {
        return beneficiaryLastName;
    }

    public void setBeneficiaryLastName(String beneficiaryLastName) {
        this.beneficiaryLastName = beneficiaryLastName;
    }

    public String getBeneficiaryFirstName() {
        return beneficiaryFirstName;
    }

    public void setBeneficiaryFirstName(String beneficiaryFirstName) {
        this.beneficiaryFirstName = beneficiaryFirstName;
    }

    public String getBeneficiaryMobileNo() {
        return beneficiaryMobileNo;
    }

    public void setBeneficiaryMobileNo(String beneficiaryMobileNo) {
        this.beneficiaryMobileNo = beneficiaryMobileNo;
    }

    public String getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(String beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}