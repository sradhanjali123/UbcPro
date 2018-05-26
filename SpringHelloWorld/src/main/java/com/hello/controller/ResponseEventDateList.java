package com.hello.controller;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ubc.pojo.Event;


public class ResponseEventDateList {
	@Expose
	@SerializedName("eventdetails")
	private List<Event> eventdatedatas;

	public List<Event> getEventdatedatas() {
		return eventdatedatas;
	}

	public void setEventdatedatas(List<Event> eventdatedatas) {
		this.eventdatedatas = eventdatedatas;
	}

	
	

}
