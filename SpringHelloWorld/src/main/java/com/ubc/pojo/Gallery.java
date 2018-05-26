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

@Entity(name="Gallery")
@Table(name="GALLERIA")
public class Gallery {
	  @Id
	  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GALLERY")
	  @SequenceGenerator(sequenceName = "SEQ_GALLERY", allocationSize = 1, name = "SEQ_GALLERY")
	  @Expose
	  @SerializedName("gallery_id")
	  @Column(name="GALLERY_ID")
	  private Long galleryId;
	  @Expose
	  @SerializedName("image_title")
	  @Column(name="IMAGE_TITLE")
	private String imageTitle;
	  @Expose
	  @SerializedName("image_uri")
	  @Column(name="IMAGE_URI")
	private String imageUri;
	  @Expose
	  @SerializedName("image_desc")
	  @Column(name="IMAGE_DESC")
	private String imageDesc;
	  @Expose
	  @SerializedName("image_priority")
	  @Column(name="IMAGE_PRIORITY")
	private String imagePriority;
	public Long getGalleryId() {
		return galleryId;
	}
	public void setGalleryId(Long galleryId) {
		this.galleryId = galleryId;
	}
	public String getImageTitle() {
		return imageTitle;
	}
	public void setImageTitle(String imageTitle) {
		this.imageTitle = imageTitle;
	}
	public String getImageUri() {
		return imageUri;
	}
	public void setImageUri(String imageUri) {
		this.imageUri = imageUri;
	}
	public String getImageDesc() {
		return imageDesc;
	}
	public void setImageDesc(String imageDesc) {
		this.imageDesc = imageDesc;
	}
	public String getImagePriority() {
		return imagePriority;
	}
	public void setImagePriority(String imagePriority) {
		this.imagePriority = imagePriority;
	}

}
