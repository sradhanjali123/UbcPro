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

@Entity(name="MetaDataa")
@Table(name="METADATADESC")
public class MetaDataa {
	 @Id
	 @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_META")
	 @SequenceGenerator(sequenceName = "SEQ_META", allocationSize = 1, name = "SEQ_META")
	 @Column(name="META_ID")
	 @Expose
	 @SerializedName("meta_id")
	 private Long contentid;
	 @Expose
	 @SerializedName("content")
	@Column(name="CONTENT")
	private String meta;
	 @Expose
	 @SerializedName("keywords")
	@Column(name="KEYWORDS")
	 private String keywords;
	 @Column(name="MENU_ID")
	 @Expose
	 @SerializedName("menu_id")
	 private Long menuId;

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getMeta() {
		return meta;
	}

	public void setMeta(String meta) {
		this.meta = meta;
	}

	
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Long getContentid() {
		return contentid;
	}

	public void setContentid(Long contentid) {
		this.contentid = contentid;
	}
	

}
