package com.hello.controller;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ubc.pojo.Comment;


public class ResponseCommentList {
	@Expose
	@SerializedName("commentflows")
	private List<Comment> commentResult;

	public List<Comment> getCommentResult() {
		return commentResult;
	}

	public void setCommentResult(List<Comment> commentResult) {
		this.commentResult = commentResult;
	}
	

}
