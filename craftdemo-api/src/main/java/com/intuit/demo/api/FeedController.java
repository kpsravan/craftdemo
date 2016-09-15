package com.intuit.demo.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.demo.api.security.DummyUser;
import com.intuit.demo.dto.FeedResponse;
import com.intuit.demo.dto.TweetDTO;
import com.intuit.demo.model.Tweet;
import com.intuit.demo.model.User;
import com.intuit.demo.service.TweetService;

@RestController
@RequestMapping("/feed")
public class FeedController {
	@Autowired
	private TweetService tweetService;

	@RequestMapping(method = RequestMethod.GET)
	public FeedResponse feed(@RequestParam(name = "size", defaultValue = "100") int size,
			@RequestParam(name = "start", defaultValue = "0") int start) {
		DummyUser dUser = (DummyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = dUser.getUser();
		FeedResponse res = new FeedResponse();
		List<Tweet> latestTweets = tweetService.getLatestTweets(user.getId(), start, size);
		if (!CollectionUtils.isEmpty(latestTweets)) {
			Map<Long, TweetDTO> tweetsMap = new HashMap<Long, TweetDTO>();
			for (Tweet latestTweet : latestTweets) {
				if (latestTweet.getParentTweet() != null) {
					if (!tweetsMap.containsKey(latestTweet.getParentTweet().getId())) {
						tweetsMap.put(latestTweet.getParentTweet().getId(),
								TweetDTO.convert(latestTweet.getParentTweet()));
					}
					tweetsMap.get(latestTweet.getParentTweet().getId()).getReplies().add(TweetDTO.convert(latestTweet));
				} else {
					if (!tweetsMap.containsKey(latestTweet.getId())) {
						tweetsMap.put(latestTweet.getId(), TweetDTO.convert(latestTweet));
					}
				}
			}
			res.setFeed(new ArrayList<TweetDTO>(tweetsMap.values()));
		}
		return res;
	}
}
