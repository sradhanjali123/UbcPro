package com.hello.controller;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ubc.pojo.Menu;

public class ResponseMenuList {
	@Expose
	@SerializedName("menulist")
	private List<Menu> menuData;

	public List<Menu> getMenuData() {
		return menuData;
	}

	public void setMenuData(List<Menu> menuData) {
		this.menuData = menuData;
	}

}
