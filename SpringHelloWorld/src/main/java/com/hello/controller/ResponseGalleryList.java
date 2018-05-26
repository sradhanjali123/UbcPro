package com.hello.controller;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ubc.pojo.Gallery;


public class ResponseGalleryList {
	@Expose
	@SerializedName("gallerylist")
	private List<Gallery> galleryData;

	public List<Gallery> getGalleryData() {
		return galleryData;
	}

	public void setGalleryData(List<Gallery> galleryData) {
		this.galleryData = galleryData;
	}


}
