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

@Entity(name="EventCategory")
@Table(name="EVENT_CATEGORY")
public class EventCategory {
	 @Id
	 @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EVENTCATEGORY")
	 @SequenceGenerator(sequenceName = "SEQ_EVENTCATEGORY", allocationSize = 1, name = "SEQ_EVENTCATEGORY")
	 @Column(name="EVENT_CATEGORYID")
	 @Expose
	 @SerializedName("eventcategory_id")
	 private Long eventCategoryId;
	 @Column(name="EVENT_CATEGORY")
	 @Expose
	 @SerializedName("event_category") 
	 private String eventCategory;
	public Long getEventCategoryId() {
		return eventCategoryId;
	}
	public void setEventCategoryId(Long eventCategoryId) {
		this.eventCategoryId = eventCategoryId;
	}
	public String getEventCategory() {
		return eventCategory;
	}
	public void setEventCategory(String eventCategory) {
		this.eventCategory = eventCategory;
	}

}
