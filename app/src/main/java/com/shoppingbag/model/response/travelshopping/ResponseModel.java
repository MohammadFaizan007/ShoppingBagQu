package com.shoppingbag.model.response.travelshopping;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseModel{

	@SerializedName("response")
	private String response;

	@SerializedName("travel")
	private List<TravelItem> travel;

	@SerializedName("utilities")
	private List<UtilitiesItem> utilities;

	@SerializedName("shopping")
	private List<ShoppingItem> shopping;

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	public void setTravel(List<TravelItem> travel){
		this.travel = travel;
	}

	public List<TravelItem> getTravel(){
		return travel;
	}

	public void setUtilities(List<UtilitiesItem> utilities){
		this.utilities = utilities;
	}

	public List<UtilitiesItem> getUtilities(){
		return utilities;
	}

	public void setShopping(List<ShoppingItem> shopping){
		this.shopping = shopping;
	}

	public List<ShoppingItem> getShopping(){
		return shopping;
	}

	@Override
 	public String toString(){
		return 
			"ResponseModel{" + 
			"response = '" + response + '\'' + 
			",travel = '" + travel + '\'' + 
			",utilities = '" + utilities + '\'' + 
			",shopping = '" + shopping + '\'' + 
			"}";
		}
}