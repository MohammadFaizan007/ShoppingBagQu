package com.shoppingbag.model.response_shopping.add_address;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("firstname")
	private String firstname;

	@SerializedName("city")
	private String city;

	@SerializedName("street")
	private String street;

	@SerializedName("address_id")
	private String addressId;

	@SerializedName("postcode")
	private String postcode;

	@SerializedName("telephone")
	private String telephone;

	@SerializedName("region")
	private String region;

	@SerializedName("email")
	private String email;

	@SerializedName("lastname")
	private String lastname;

	public void setFirstname(String firstname){
		this.firstname = firstname;
	}

	public String getFirstname(){
		return firstname;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setStreet(String street){
		this.street = street;
	}

	public String getStreet(){
		return street;
	}

	public void setAddressId(String addressId){
		this.addressId = addressId;
	}

	public String getAddressId(){
		return addressId;
	}

	public void setPostcode(String postcode){
		this.postcode = postcode;
	}

	public String getPostcode(){
		return postcode;
	}

	public void setTelephone(String telephone){
		this.telephone = telephone;
	}

	public String getTelephone(){
		return telephone;
	}

	public void setRegion(String region){
		this.region = region;
	}

	public String getRegion(){
		return region;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setLastname(String lastname){
		this.lastname = lastname;
	}

	public String getLastname(){
		return lastname;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"firstname = '" + firstname + '\'' + 
			",city = '" + city + '\'' + 
			",street = '" + street + '\'' + 
			",address_id = '" + addressId + '\'' + 
			",postcode = '" + postcode + '\'' + 
			",telephone = '" + telephone + '\'' + 
			",region = '" + region + '\'' + 
			",email = '" + email + '\'' + 
			",lastname = '" + lastname + '\'' + 
			"}";
		}
}