package com.hystrix.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class HystrixCommandTesting {

	private RestTemplate restTemplate;

	private String requestUrl;

	private static boolean isServiceUp = false;

	@Before
	public void setup() {
		this.restTemplate = new RestTemplate();
		this.requestUrl = "http://localhost:8080/CircuitBreakerDemo2/triggerRest";
	}

	@Test
	public void testWhenServerIsUp() {
		ResponseEntity<String> responseBody = this.restTemplate.exchange(this.requestUrl, HttpMethod.GET, null,
				String.class);
		String response = responseBody.getBody();
		if (response.equals("server is up and running from rest controller.."))
			isServiceUp = true;
		if (isServiceUp)
			assertEquals("the server was supposed to be up..", "server is up and running from rest controller..",
					response);
	}

	@Test
	public void testWhenServerIsDown() {
		if (isServiceUp)
			return;
		ResponseEntity<String> responseBody = this.restTemplate.exchange(this.requestUrl, HttpMethod.GET, null,
				String.class);
		String response = responseBody.getBody();
		assertEquals("the server was supposed to be down..", "server is down.. (from rest fallback)", response);
	}

}
