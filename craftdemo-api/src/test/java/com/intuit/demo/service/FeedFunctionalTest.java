package com.intuit.demo.service;

import java.net.URI;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.intuit.demo.dto.FeedResponse;

public class FeedFunctionalTest {

	@Test
	public void testGetLatestFeedsOk() throws Exception {
		RestTemplate rt = new RestTemplate();
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.add("Authorization", "Basic dXNlcjU6dXNlcjU=");
		RequestEntity<String> requestEntity = new RequestEntity<String>(headers, HttpMethod.GET,
				new URI("http://localhost:8080/craftdemo-api/feed"));
		ResponseEntity<FeedResponse> resEn = rt.exchange(requestEntity, FeedResponse.class);
		FeedResponse res = resEn.getBody();
		Assert.assertTrue(res.getFeed().size() == 1);
		Assert.assertTrue(res.getFeed().get(0).getReplies().size() == 1);
	}
}
