package com.intuit.demo.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.intuit.demo.model.Tweet;

public class TweetDTO {

	private Long id;
	private String content;
	private String byUser;
	private Date creationDate;
	private List<TweetDTO> replies;

	public static TweetDTO convert(Tweet t) {
		TweetDTO dto = new TweetDTO();
		dto.setId(t.getId());
		dto.setContent(t.getContent());
		dto.setCreationDate(t.getCreationDate());
		dto.setReplies(new ArrayList<TweetDTO>());
		dto.setByUser(t.getByUser().getUserName());
		return dto;
	}

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

	public String getByUser() {
		return byUser;
	}

	public void setByUser(String byUser) {
		this.byUser = byUser;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public List<TweetDTO> getReplies() {
		return replies;
	}

	public void setReplies(List<TweetDTO> replies) {
		this.replies = replies;
	}

}
