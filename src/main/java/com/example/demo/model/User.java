package com.example.demo.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("user properties")
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ApiModelProperty("userName must contain letters and numbers only with first character being a letter. Length must be between 8 and 18 characters.")
	@Column(unique=true)
	@NotEmpty(message = "{user.userName.notEmpty}")
	@Pattern(regexp = "^(?=.{8,18}$)[a-zA-Z][a-zA-Z\\d]*", 
	message = "{user.username.validFormat}")
	private String userName;
	
	@ApiModelProperty("email id must not be empty")
	@Column(unique=true)
	@NotEmpty(message = "{user.emailId.notEmpty}")
	@Pattern(regexp = "^.+@.+\\..+$", message = "{user.emailId.notValid}")
	private String emailId;
	
	@Column
	private String firstName;
	
	@Column
	private String lastName;
	
	@Column
	private Date dateCreated;
	
	@Column
	private String summary;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="user_follower",
	joinColumns=@JoinColumn(name="user_id"),
	inverseJoinColumns=@JoinColumn(name="follower_id"))
	private Set<User> followSet = new HashSet<>();
	
	
	public User() {
		
	}
	
	
	public User(String userName, String emailId, String firstName, String lastName) {
		super();
		this.userName = userName;
		this.emailId = emailId;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
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
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public Set<User> getFollowSet() {
		return followSet;
	}


	public void setFollowSet(Set<User> followSet) {
		this.followSet = followSet;
	}


	public Date getDateCreated() {
		return dateCreated;
	}


	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}


	public String getSummary() {
		return summary;
	}


	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	
	
	
	
}
