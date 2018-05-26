package com.hello.controller;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ubc.pojo.ClickOtp;


public class ResponseClickOtpList {
	@Expose
	@SerializedName("otplist")
	private List<ClickOtp> otps;

	public List<ClickOtp> getOtps() {
		return otps;
	}

	public void setOtps(List<ClickOtp> otps) {
		this.otps = otps;
	}


}
