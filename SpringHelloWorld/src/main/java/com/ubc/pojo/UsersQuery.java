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

@Entity(name="UsersQuery")
@Table(name="PERSONSQUERY")
public class UsersQuery {
	@Id
	 @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_QUERY")
	 @SequenceGenerator(sequenceName = "SEQ_QUERY", allocationSize = 1, name = "SEQ_QUERY")
	 @Column(name="QUERYID")
	  @Expose
	  @SerializedName("query_id")
	 private Long queryid;
	  @Expose
	  @SerializedName("person_name")
	 @Column(name="PERSON_NAME")
	private String personName;  
	  @Expose
	 @SerializedName("email_id")
	@Column(name="EMAIL_ID")
	private String emailId;
	@Column(name="FUNCTIONS")
	@Expose
   @SerializedName("function")
	private String function;
	@Column(name="MESSAGE")
	@Expose
    @SerializedName("message")
	private String message;
	
	
	public Long getQueryid() {
		return queryid;
	}
	public void setQueryid(Long queryid) {
		this.queryid = queryid;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
