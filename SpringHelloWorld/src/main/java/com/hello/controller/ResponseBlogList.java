package com.hello.controller;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ubc.pojo.Blog;

public class ResponseBlogList {
	@Expose
	@SerializedName("blogflows")
	private List<Blog> blogResult;

	public List<Blog> getBlogResult() {
		return blogResult;
	}

	public void setBlogResult(List<Blog> blogResult) {
		this.blogResult = blogResult;
	}
}
