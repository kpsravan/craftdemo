package com.intuit.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class Follower {

	@ManyToOne
	@JoinColumn(name = "following_user_id")
	private User followingUser;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", nullable = false, updatable = false)
	private Date createdDate;

	@OneToOne
	@JoinColumn(name = "req_id")
	private FollowerRequest request;

	public User getFollowingUser() {
		return followingUser;
	}

	public void setFollowingUser(User followingUser) {
		this.followingUser = followingUser;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public FollowerRequest getRequest() {
		return request;
	}

	public void setRequest(FollowerRequest request) {
		this.request = request;
	}

}
