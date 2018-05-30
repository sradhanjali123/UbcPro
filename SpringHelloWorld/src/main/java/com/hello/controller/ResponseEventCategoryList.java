package com.hello.controller;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ubc.pojo.EventCategory;

public class ResponseEventCategoryList {
	@Expose
	@SerializedName("eventcategorydetails")
	private List<EventCategory> eventdatas;

	public List<EventCategory> getEventdatas() {
		return eventdatas;
	}

	public void setEventdatas(List<EventCategory> eventdatas) {
		this.eventdatas = eventdatas;
	}

}
