package com.shoppingbag.model.response_shopping;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MediaGalleryEntriesItem{

	@SerializedName("types")
	private List<String> types;

	@SerializedName("file")
	private String file;

	@SerializedName("media_type")
	private String mediaType;

	@SerializedName("disabled")
	private boolean disabled;

	@SerializedName("id")
	private int id;

	@SerializedName("label")
	private Object label;

	@SerializedName("position")
	private int position;

	public void setTypes(List<String> types){
		this.types = types;
	}

	public List<String> getTypes(){
		return types;
	}

	public void setFile(String file){
		this.file = file;
	}

	public String getFile(){
		return file;
	}

	public void setMediaType(String mediaType){
		this.mediaType = mediaType;
	}

	public String getMediaType(){
		return mediaType;
	}

	public void setDisabled(boolean disabled){
		this.disabled = disabled;
	}

	public boolean isDisabled(){
		return disabled;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setLabel(Object label){
		this.label = label;
	}

	public Object getLabel(){
		return label;
	}

	public void setPosition(int position){
		this.position = position;
	}

	public int getPosition(){
		return position;
	}

	@Override
 	public String toString(){
		return 
			"MediaGalleryEntriesItem{" + 
			"types = '" + types + '\'' + 
			",file = '" + file + '\'' + 
			",media_type = '" + mediaType + '\'' + 
			",disabled = '" + disabled + '\'' + 
			",id = '" + id + '\'' + 
			",label = '" + label + '\'' + 
			",position = '" + position + '\'' + 
			"}";
		}
}