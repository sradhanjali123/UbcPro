package com.hello.controller;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ubc.pojo.Banner;


public class ResponseBannerList {
	@Expose
	@SerializedName("banners")
	private List<Banner> bannerrs;

	public List<Banner> getBannerrs() {
		return bannerrs;
	}

	public void setBannerrs(List<Banner> bannerrs) {
		this.bannerrs = bannerrs;
	}

	

}
