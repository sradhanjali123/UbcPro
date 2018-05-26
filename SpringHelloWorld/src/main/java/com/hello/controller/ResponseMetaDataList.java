package com.hello.controller;

import java.util.List;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import com.ubc.pojo.MetaDataa;

public class ResponseMetaDataList {
	@Expose
	@SerializedName("metadatadescription")
	private List<MetaDataa> metadatas;

	public List<MetaDataa> getMetadatas() {
		return metadatas;
	}

	public void setMetadatas(List<MetaDataa> metadatas) {
		this.metadatas = metadatas;
	}

	

}
