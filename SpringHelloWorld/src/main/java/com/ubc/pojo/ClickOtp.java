package com.ubc.pojo;

import java.util.Date;



import javax.persistence.Column;
import javax.persistence.Entity;

//import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Entity(name="ClickOtp")
@Table(name="OTPUSERS")
public class ClickOtp {
	 @Id
	 @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_OTP")
	 @SequenceGenerator(sequenceName = "SEQ_OTP", allocationSize = 1, name = "SEQ_OTP")
	 @Column(name="OTP_ID")
	 @Expose
	 @SerializedName("otp_id")
	 private Long otpid;
	 @Column(name = "USER_ID")
	@Expose
	@SerializedName("user_id")
	private Long userid;
	@Expose
	@SerializedName("phone_no")
	@Column(name="PHONE_NO")
	private String phoneno;
	 @Expose
	 @SerializedName("otp")
	 @Column(name="OTP")
	 private char[] otp;
	 @Column(name = "TIME")
	 @Expose
	 @SerializedName("time")
	private Date time;
	 @Expose
	 @SerializedName("email_status")
	 @Column(name = "STATUS")
	 private String status;
	 @Expose
	@SerializedName("email_id")
	@Column(name = "EMAIL_ID")
	private String emailId;
	 
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
		public Long getOtpid() {
			return otpid;
		}
		public void setOtpid(Long otpid) {
			this.otpid = otpid;
		}
		public String getPhoneno() {
			return phoneno;
		}
		public void setPhoneno(String phoneno) {
			this.phoneno = phoneno;
		}
		public char[] getOtp() {
			return otp;
		}
		public void setOtp(char[] cs) {
			this.otp = cs;
		}
		public Date getTime() {
			return time;
		}
		public void setTime(Date time) {
			this.time = time;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
	
}
