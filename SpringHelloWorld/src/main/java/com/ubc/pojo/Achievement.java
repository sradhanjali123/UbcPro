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

@Entity(name="Achievement")
@Table(name="ACHIEVEMENTS")
public class Achievement {
	 @Id
	  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ACHIEVE")
	  @SequenceGenerator(sequenceName = "SEQ_ACHIEVE", allocationSize = 1, name = "SEQ_ACHIEVE")
	  @Expose
	  @SerializedName("achieve_id")
	  @Column(name="ACHIEVE_ID")
	private Long achieveId;
	  @Expose
	  @SerializedName("achieve_value")
	  @Column(name="ACHIEVE_VALUE")
	private Long achieveValue;
	  @Expose
	  @SerializedName("achieve_title")
	  @Column(name="ACHIEVE_TITLE")
	private String achieveTitle;
	  @Column(name="ACHIEVE_CONTENT")
	  @Expose
	  @SerializedName("achieve_text")
	private String achieveText;
	public Long getAchieveId() {
		return achieveId;
	}
	public void setAchieveId(Long achieveId) {
		this.achieveId = achieveId;
	}
	public Long getAchieveValue() {
		return achieveValue;
	}
	public void setAchieveValue(Long achieveValue) {
		this.achieveValue = achieveValue;
	}
	public String getAchieveTitle() {
		return achieveTitle;
	}
	public void setAchieveTitle(String achieveTitle) {
		this.achieveTitle = achieveTitle;
	}
	public String getAchieveText() {
		return achieveText;
	}
	public void setAchieveText(String achieveText) {
		this.achieveText = achieveText;
	}
	

}
