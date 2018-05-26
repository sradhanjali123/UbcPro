package com.hello.controller;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ubc.pojo.UserEvent;

public class ResponseSingleUserEventList {
	@Expose
	@SerializedName("singleusersevent_description")
	private List<UserEvent> singleuserEventDatas;

	public List<UserEvent> getSingleuserEventDatas() {
		return singleuserEventDatas;
	}

	public void setSingleuserEventDatas(List<UserEvent> singleuserEventDatas) {
		this.singleuserEventDatas = singleuserEventDatas;
	}


}
