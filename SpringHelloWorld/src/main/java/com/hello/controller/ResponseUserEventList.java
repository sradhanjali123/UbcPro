package com.hello.controller;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ubc.pojo.UserEvent;

public class ResponseUserEventList {
	@Expose
	@SerializedName("usersevents_description")
	private List<UserEvent> usereventdatas;

	public List<UserEvent> getUsereventdatas() {
		return usereventdatas;
	}

	public void setUsereventdatas(List<UserEvent> usereventdatas) {
		this.usereventdatas = usereventdatas;
	}

}
