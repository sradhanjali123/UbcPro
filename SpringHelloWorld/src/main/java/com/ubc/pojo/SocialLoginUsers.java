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

@Entity(name = "SocialLoginUsers")
@Table(name = "SOCIALUSERS")

public class SocialLoginUsers {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PERSONS")
	@SequenceGenerator(sequenceName = "SEQ_PERSONS", allocationSize = 1, name = "SEQ_PERSONS")
	@Column(name = "USER_ID")
	@Expose
	@SerializedName("user_id")
	private Long userid;
	@Expose
	@SerializedName("login_id")
	@Column(name = "LOGIN_ID")
	private String tokenid;
	@Expose
	@SerializedName("user_name")
	@Column(name = "USER_NAME")
	private String userName;
	@Expose
	@SerializedName("email_id")
	@Column(name = "EMAIL_ID")
	private String emailId;
	@Expose
	@SerializedName("phone_no")
	@Column(name = "PHONE_NO")
	private String phoneno;
	

	public String getPhoneno() {
		return phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	

	public String getTokenid() {
		return tokenid;
	}

	public void setTokenid(String tokenid) {
		this.tokenid = tokenid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

}
