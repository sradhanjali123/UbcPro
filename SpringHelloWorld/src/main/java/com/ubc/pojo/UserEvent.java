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

@Entity(name = "UserEvent")
@Table(name = "USEREVENTDETAILS")
public class UserEvent {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USEREVENT")
	@SequenceGenerator(sequenceName = "SEQ_USEREVENT", allocationSize = 1, name = "SEQ_USEREVENT")
	@Column(name = "USEREVENT_ID")
	@Expose
	@SerializedName("userevent_id")
	private Long userEventId;
	@Column(name = "USER_ID")
	@Expose
	@SerializedName("user_id")
	private Long userid;
//	@Column(name = "EVENT_NAME")
//	@Expose
//	@SerializedName("event_name")
//	private String eventName;
	@Expose
	@SerializedName("like_status")
	@Column(name = "LIKE_STATUS")
	private String likeStatus;
//	@Expose
//	@SerializedName("like_id")
//	@Column(name = "LIKESNUM")
//	private String like;
	@Column(name="EVENT_ID")
	@Expose
    @SerializedName("event_id")
    private Long eventId;

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public String getLikeStatus() {
		return likeStatus;
	}

	public void setLikeStatus(String likeStatus) {
		this.likeStatus = likeStatus;
	}

//	public String getLike() {
//		return like;
//	}
//
//	public void setLike(String like) {
//		this.like = like;
//	}

	public Long getUserEventId() {
		return userEventId;
	}

	public void setUserEventId(Long userEventId) {
		this.userEventId = userEventId;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

//	public String getEventName() {
//		return eventName;
//	}
//
//	public void setEventName(String eventName) {
//		this.eventName = eventName;
//	}

}
