package com.ubc.pojo;

import com.google.gson.Gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BasicResponseObject {
	@Expose
	@SerializedName("status_code")
	int statusCode;
	@Expose
	@SerializedName("response")
	Object responseObject;
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public Object getResponseObject() {
		return responseObject;
	}
	public void setResponseObject(Object responseObject) {
		this.responseObject = responseObject;
	}
	public BasicResponseObject(int statusCode, Object responseObject) {
		super();
		this.statusCode = statusCode;
		this.responseObject = responseObject;
	}
	@Override
	public String toString() {
		try {
			String response = new Gson().toJson(this);
			return response;
		}catch (Exception e) {
			
		}
		return "";
	}
	
}
