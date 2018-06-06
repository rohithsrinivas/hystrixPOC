package com.hystrix.finalutil;

import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

public class HystrixHealthCheckUtil {
	
	private static RestTemplate restTemplate;
	
	//@HystrixCommand(fallbackMethod = "healthCheckFallback")
	public static boolean checkHealthForService(String url,Map<String, Object> parameters,HttpMethod method){
		UriComponentsBuilder requestBuilder = UriComponentsBuilder.fromHttpUrl(url);
		UriComponents components = UriComponentsBuilder.newInstance().buildAndExpand(parameters);
		System.out.println(requestBuilder.toString());
		return true;
	}
	
	public static boolean healthCheckFallback(String url,Map<Object, Object> parameters){
		return false;
	}

}
