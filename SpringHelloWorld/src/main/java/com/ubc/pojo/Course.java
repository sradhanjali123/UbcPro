package com.ubc.pojo;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(name="Course")
@Table(name="COURSEPOINT")
public class Course {
	 @Id
	 @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COURSE")
	 @SequenceGenerator(sequenceName = "SEQ_COURSE", allocationSize = 1, name = "SEQ_COURSE")
	 @Column(name="COURSE_ID")
	 @Expose
	 @SerializedName("course_id")
	 private Long courseId;
	 @Expose
	 @SerializedName("course_name")
	@Column(name="COURSE_NAME")
	private String courseName;
	 @Expose
	 @SerializedName("course_desc")
	@Column(name="COURSE_DESC")
	private String courseDesc;
	 @Expose
	 @SerializedName("course_image")
	@Column(name="COURSE_IMAGEPATH")
	private String courseImage;
	 @Expose
	 @SerializedName("course_price")
	@Column(name="COURSE_PRICE")
	private String coursePrice;
	 @Column(name="COURSE_DATE")
	  @Expose
	  @SerializedName("course_date")
	private String courseDate;
	public Long getCourseId() {
		return courseId;
	}
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseDesc() {
		return courseDesc;
	}
	public void setCourseDesc(String courseDesc) {
		this.courseDesc = courseDesc;
	}
	public String getCourseImage() {
		return courseImage;
	}
	public void setCourseImage(String courseImage) {
		this.courseImage = courseImage;
	}
	public String getCoursePrice() {
		return coursePrice;
	}
	public void setCoursePrice(String coursePrice) {
		this.coursePrice = coursePrice;
	}
	public String getCourseDate() {
		return courseDate;
	}
	public void setCourseDate(String courseDate) {
		this.courseDate = courseDate;
	}

}
