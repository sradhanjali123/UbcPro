package com.ubc.pojo;




import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(name="Event")
@Table(name="EVENTDETAILS")
public class Event {
	 @Id
	 @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EVENT")
	 @SequenceGenerator(sequenceName = "SEQ_EVENT", allocationSize = 1, name = "SEQ_EVENT")
	 @Column(name="EVENT_ID")
	 @Expose
	 @SerializedName("event_id")
	 private Long eventId;
	@Column(name="EVENT_NAME")
	 @Expose
	 @SerializedName("event_name")
	 @OrderBy("EVENT_DATE ASC")
	 private String eventName;
	 @Column(name="EVENT_DESC")
	 @Expose
	 @SerializedName("event_desc")
	 private String eventDesc;
	 @Column(name="LIKES_COUNT")
	 @Expose
	 @SerializedName("likes_counter")
	 private Long likeCount;
	 @Column(name="EVENT_VENUE")
	 @Expose
	 @SerializedName("event_venue")
	 private String eventVenue;
	 @Column(name="EVENT_IMAGE")
	 @Expose
	 @SerializedName("event_imagepath")
	 @OrderBy("EVENT_PRIORITY ASC")
	 private String eventImage;
	 @Column(name="EVENT_PRIORITY")
	  @Expose
	  @SerializedName("event_priority")
	private Long eventPriority;
	 @Column(name="EVENT_DATE")
	  @Expose
	  @SerializedName("event_date")
	private String eventDate;
	 @Column(name="EVENT_CATEGORYID")
	  @Expose
	  @SerializedName("event_cateid")
	private Long eventCategoryId;
	 
	 public Long getEventCategoryId() {
		return eventCategoryId;
	}
	public void setEventCategoryId(Long eventCategoryId) {
		this.eventCategoryId = eventCategoryId;
	}
	public String getEventDate() {
		return eventDate;
	}
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
	public Long getEventPriority() {
		return eventPriority;
	}
	public void setEventPriority(Long eventPriority) {
		this.eventPriority = eventPriority;
	}
	public Long getEventId() {
			return eventId;
		}
		public void setEventId(Long eventId) {
			this.eventId = eventId;
		}
		public String getEventName() {
			return eventName;
		}
		public void setEventName(String eventName) {
			this.eventName = eventName;
		}
		public String getEventDesc() {
			return eventDesc;
		}
		public void setEventDesc(String eventDesc) {
			this.eventDesc = eventDesc;
		}
		public Long getLikeCount() {
			return likeCount;
		}
		public void setLikeCount(Long likeCount) {
			this.likeCount = likeCount;
		}
		public String getEventVenue() {
			return eventVenue;
		}
		public void setEventVenue(String eventVenue) {
			this.eventVenue = eventVenue;
		}
		public String getEventImage() {
			return eventImage;
		}
		public void setEventImage(String eventImage) {
			this.eventImage = eventImage;
		} 
	

}
