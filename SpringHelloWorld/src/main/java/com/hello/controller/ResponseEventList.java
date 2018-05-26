package com.hello.controller;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ubc.pojo.Event;


public class ResponseEventList {
	@Expose
	@SerializedName("eventdetails")
	private List<Event> eventdatas;

	public List<Event> getEventdatas() {
		return eventdatas;
	}

	public void setEventdatas(List<Event> eventdatas) {
		this.eventdatas = eventdatas;
	}

	

}
