package com.shoppingbag.model.request;

import com.google.gson.annotations.SerializedName;

public class BookedHistoryInput{

	@SerializedName("RecordsBy")
	private String recordsBy;

	@SerializedName("FromDate")
	private String fromDate;

	@SerializedName("ToDate")
	private String toDate;

	public void setRecordsBy(String recordsBy){
		this.recordsBy = recordsBy;
	}

	public void setFromDate(String fromDate){
		this.fromDate = fromDate;
	}

	public void setToDate(String toDate){
		this.toDate = toDate;
	}
}