package com.intuit.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.intuit.demo.dao.BaseDAO;
import com.intuit.demo.model.Tweet;

@Service
@Transactional
public class TweetService {
	@Autowired
	private BaseDAO baseDao;

	public List<Tweet> getLatestTweets(Long userId, int start, int size) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		return baseDao
				.findPaginatedResults(
						"Select t From Tweet t LEFT JOIN FETCH t.parentTweet pt where t.toUser.id=:userId ORDER BY t.creationDate DESC",
						params, start, size);
	}

	public List<Tweet> getTweetsById(List<Long> tweetIds) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", tweetIds);
		return baseDao.find("SELECT t FROM Tweet t WHERE t.id IN (:ids)",
				params);
	}
}
