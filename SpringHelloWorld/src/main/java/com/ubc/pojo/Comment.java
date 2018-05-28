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

@Entity(name="Comment")
@Table(name="COMMENTDETAILS")
public class Comment {
	@Id
	 @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COMMENT")
	 @SequenceGenerator(sequenceName = "SEQ_COMMENT", allocationSize = 1, name = "SEQ_COMMENT")
	 @Column(name="COMMENT_ID")
	 @Expose
	 @SerializedName("comment_id")
	 private Long commentId;
	@Column(name="BLOG_ID")
	 @Expose
	 @SerializedName("blog_id")
	 private Long blogId;
	@Column(name="USER_ID")
	 @Expose
	 @SerializedName("user_id")
	 private Long userId;
	 @Column(name="BLOG_COMMENT")
	 @Expose
	 @SerializedName("blog_comment")
	 private String blogComment;
	 @Column(name = "COMMENT_DATE")
	 @Expose
	 @SerializedName("comment_date")
	private Date commentDate;
	public Long getCommentId() {
		return commentId;
	}
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
	public Long getBlogId() {
		return blogId;
	}
	public void setBlogId(Long blogId) {
		this.blogId = blogId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getBlogComment() {
		return blogComment;
	}
	public void setBlogComment(String blogComment) {
		this.blogComment = blogComment;
	}
	public Date getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}
		

}
