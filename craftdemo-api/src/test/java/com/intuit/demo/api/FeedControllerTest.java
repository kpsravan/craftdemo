package com.intuit.demo.api;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;

import com.intuit.demo.api.security.DummyUser;
import com.intuit.demo.dto.FeedResponse;
import com.intuit.demo.dto.TweetDTO;
import com.intuit.demo.model.Tweet;
import com.intuit.demo.model.User;
import com.intuit.demo.service.TweetService;

@RunWith(MockitoJUnitRunner.class)
public class FeedControllerTest {

	@Mock
	private TweetService tweetService;

	@InjectMocks
	private FeedController feedController;

	@Test
	public void testFeedOk() {
		User u = new User();
		u.setId(1l);
		DummyUser d = new DummyUser(u, null);
		SecurityContext secCtx = Mockito.mock(SecurityContext.class);
		SecurityContextHolder.setContext(secCtx);
		Authentication auth = Mockito.mock(Authentication.class);
		Mockito.when(secCtx.getAuthentication()).thenReturn(auth);
		Mockito.when(auth.getPrincipal()).thenReturn(d);

		List<Tweet> tweets = new ArrayList<Tweet>();
		for (long i = 1; i <= 11; i++) {
			Tweet t1 = new Tweet();
			t1.setId(i);
			User uN = new User();
			uN.setUserName("user" + i);
			t1.setByUser(uN);
			tweets.add(t1);
		}
		for (int i = 1; i <= 4; i++) {
			tweets.get(i).setParentTweet(tweets.get(0));
		}
		for (int i = 6; i <= 9; i++) {
			tweets.get(i).setParentTweet(tweets.get(5));
		}
		Mockito.when(tweetService.getLatestTweets(Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(tweets);

		FeedResponse res = feedController.feed(0, 100);
		Assert.assertTrue(res.getFeed().size() == 3);
		for (TweetDTO tweetDto : res.getFeed()) {
			if (tweetDto.getId().longValue() == 1) {
				Assert.assertTrue(tweetDto.getReplies().size() == 4);
				long id = 2;
				for (TweetDTO rep : tweetDto.getReplies()) {
					Assert.assertEquals(id++, rep.getId().longValue());
				}
			} else if (tweetDto.getId().longValue() == 6) {
				Assert.assertTrue(tweetDto.getReplies().size() == 4);
				long id = 7;
				for (TweetDTO rep : tweetDto.getReplies()) {
					Assert.assertEquals(id++, rep.getId().longValue());
				}
			} else if (tweetDto.getId().longValue() == 11) {
				Assert.assertTrue(CollectionUtils.isEmpty(tweetDto.getReplies()));
			} else {
				Assert.fail("Invalid ID received.");
			}
		}
	}
}
