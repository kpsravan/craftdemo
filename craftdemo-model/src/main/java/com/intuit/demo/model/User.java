package com.intuit.demo.model;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue
	private Long id;
	@Column(name = "first_name", length = 50)
	private String firstName;
	@Column(name = "last_name", length = 50)
	private String lastName;
	@Column(name = "user_name", length = 50)
	private String userName;

	@ElementCollection
	@CollectionTable(name = "user_follower", joinColumns = @JoinColumn(name = "user_id") )
	private Set<Follower> followers;
	private String profileImgDir;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Set<Follower> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<Follower> followers) {
		this.followers = followers;
	}

	public String getProfileImgDir() {
		return profileImgDir;
	}

	public void setProfileImgDir(String profileImgDir) {
		this.profileImgDir = profileImgDir;
	}

}
