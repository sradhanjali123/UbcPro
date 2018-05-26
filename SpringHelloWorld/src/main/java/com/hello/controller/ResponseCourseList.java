package com.hello.controller;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ubc.pojo.Course;

public class ResponseCourseList {
	@Expose
	@SerializedName("courselist")
	private List<Course> courseData;

	public List<Course> getCourseData() {
		return courseData;
	}

	public void setCourseData(List<Course> courseData) {
		this.courseData = courseData;
	}

}
