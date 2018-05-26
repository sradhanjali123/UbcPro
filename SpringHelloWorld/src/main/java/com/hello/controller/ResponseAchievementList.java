package com.hello.controller;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ubc.pojo.Achievement;


public class ResponseAchievementList {
	@Expose
	@SerializedName("achievements")
	private List<Achievement> achieResult;

	public List<Achievement> getAchieResult() {
		return achieResult;
	}

	public void setAchieResult(List<Achievement> achieResult) {
		this.achieResult = achieResult;
	}


}
