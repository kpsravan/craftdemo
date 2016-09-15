package com.intuit.demo.dto;

import java.util.List;

public class FeedResponse {

	private List<TweetDTO> feed;

	public List<TweetDTO> getFeed() {
		return feed;
	}

	public void setFeed(List<TweetDTO> feed) {
		this.feed = feed;
	}

}
