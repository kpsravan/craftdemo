package com.intuit.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "user_follower")
public class Follower {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "owner_user_id", nullable = false, unique = false)
	private User ownerUser;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "following_user_id", nullable = false, unique = false)
	private User followingUser;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", nullable = false, updatable = false)
	private Date createdDate;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "req_id", nullable = false, unique = false)
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
