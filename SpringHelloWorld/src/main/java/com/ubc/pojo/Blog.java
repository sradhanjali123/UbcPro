package com.ubc.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(name="Blog")
@Table(name="BLOGDETAILS")
public class Blog {
	 @Id
	 @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BLOG")
	 @SequenceGenerator(sequenceName = "SEQ_BLOG", allocationSize = 1, name = "SEQ_BLOG")
	 @Column(name="BLOG_ID")
	 @Expose
	 @SerializedName("blog_id")
	 private Long blogId; 
	@Column(name="BLOG_TITLE")
	 @Expose
	 @SerializedName("blog_title")
	 private String blogTitle;
	 @Column(name="BLOG_DESC")
	 @Expose
	 @SerializedName("blog_desc")
	 private String blogDesc;
	 @Column(name="BLOG_CATEGORY")
	 @Expose
	 @SerializedName("blog_category")
	 private String blogCategory;
	 @Column(name="BLOG_TAGS")
	 @Expose
	 @SerializedName("blog_tags")
	 private String blogTags;
	 @Column(name="BLOG_IMAGE")
	 @Expose
	 @SerializedName("blog_image")
	 private String blogImage;
	 @Column(name = "BLOG_DATE")
	 @Expose
	 @SerializedName("blog_date")
	private Date blogDate;
	 
	 public Long getBlogId() {
			return blogId;
		}
		public void setBlogId(Long blogId) {
			this.blogId = blogId;
		}
		public String getBlogTitle() {
			return blogTitle;
		}
		public void setBlogTitle(String blogTitle) {
			this.blogTitle = blogTitle;
		}
		public String getBlogDesc() {
			return blogDesc;
		}
		public void setBlogDesc(String blogDesc) {
			this.blogDesc = blogDesc;
		}
		public String getBlogCategory() {
			return blogCategory;
		}
		public void setBlogCategory(String blogCategory) {
			this.blogCategory = blogCategory;
		}
		public String getBlogTags() {
			return blogTags;
		}
		public void setBlogTags(String blogTags) {
			this.blogTags = blogTags;
		}
		public String getBlogImage() {
			return blogImage;
		}
		public void setBlogImage(String blogImage) {
			this.blogImage = blogImage;
		}
		public Date getBlogDate() {
			return blogDate;
		}
		public void setBlogDate(Date blogDate) {
			this.blogDate = blogDate;
		}
		
	 
}
