package com.hello.controller;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ubc.pojo.UsersQuery;

public class ResponseUsersQueryList {
	@Expose
	@SerializedName("usersquery")
	private List<UsersQuery> userssquery;

	public List<UsersQuery> getUserssquery() {
		return userssquery;
	}

	public void setUserssquery(List<UsersQuery> userssquery) {
		this.userssquery = userssquery;
	}

	
	
}
