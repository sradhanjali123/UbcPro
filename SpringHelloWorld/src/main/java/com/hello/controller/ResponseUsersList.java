package com.hello.controller;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ubc.pojo.SocialLoginUsers;


public class ResponseUsersList {
	@Expose
	@SerializedName("users")
	private List<SocialLoginUsers> users;

	
	public List<SocialLoginUsers> getUsers() {
		return users;
	}

	
	public void setUsers(List<SocialLoginUsers> users) {
		this.users = users;
	}
	
}
