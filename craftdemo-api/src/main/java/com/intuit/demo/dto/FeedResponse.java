package com.intuit.demo.dto;

import java.util.Collection;

public class FeedResponse {

	private Collection<TweetDTO> feed;

	public Collection<TweetDTO> getFeed() {
		return feed;
	}

	public void setFeed(Collection<TweetDTO> feed) {
		this.feed = feed;
	}

}
