package com.intuit.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "tweet")
public class Tweet {
	@Id
	@GeneratedValue
	private Long id;
	@Column(name = "content", length = 140)
	private String content;

	@ManyToOne(fetch = FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "to_user_id", nullable = false)
	private User toUser;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "by_user_id", nullable = false)
	@Fetch(FetchMode.JOIN)
	private User byUser;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", nullable = false, updatable = false)
	private Date creationDate;

	@ManyToOne
	@JoinColumn(name = "parent_tweet_id", nullable = true)
	private Tweet parentTweet;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getToUser() {
		return toUser;
	}

	public void setToUser(User toUser) {
		this.toUser = toUser;
	}

	public User getByUser() {
		return byUser;
	}

	public void setByUser(User byUser) {
		this.byUser = byUser;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Tweet getParentTweet() {
		return parentTweet;
	}

	public void setParentTweet(Tweet parentTweet) {
		this.parentTweet = parentTweet;
	}

}
