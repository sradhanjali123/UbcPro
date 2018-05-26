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
@Entity(name="Banner")
@Table(name="BANNERDETAILS")
public class Banner {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BANNER")
  @SequenceGenerator(sequenceName = "SEQ_BANNER", allocationSize = 1, name = "SEQ_BANNER")
  @Expose
  @SerializedName("banner_id")
  @Column(name="BANNER_ID")
private Long bannerId;
  @Expose
  @SerializedName("banner_imagepath")
  @Column(name="BANNERIMAGE")
  @OrderBy("PRIORITY ASC")
private String bannerImagePath;
  @Expose
  @SerializedName("banner_text")
  @Column(name="BANNERTEXT")
private String bannerText;
  @Column(name="VIDEO")
  @Expose
  @SerializedName("banner_videopath")
private String videoPath;
  @Column(name="PRIORITY")
  @Expose
  @SerializedName("banner_priority")
private Long bannerPriority;
 

public Long getBannerPriority() {
	return bannerPriority;
}
public void setBannerPriority(Long bannerPriority) {
	this.bannerPriority = bannerPriority;
}
public Long getBannerId() {
	return bannerId;
}
public void setBannerId(Long bannerId) {
	this.bannerId = bannerId;
}
public String getBannerImagePath() {
	return bannerImagePath;
}
public void setBannerImagePath(String bannerImagePath) {
	this.bannerImagePath = bannerImagePath;
}
public String getBannerText() {
	return bannerText;
}
public void setBannerText(String bannerText) {
	this.bannerText = bannerText;
}
public String getVideoPath() {
	return videoPath;
}
public void setVideoPath(String videoPath) {
	this.videoPath = videoPath;
}

}
