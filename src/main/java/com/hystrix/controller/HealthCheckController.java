package com.hystrix.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.hystrix.finalutil.HystrixHealthCheckUtil;
import com.hystrix.utils.HystrixPropertyUtils;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Controller
public class HealthCheckController {

	private RestTemplate restTemplate;

	static {
		HystrixPropertyUtils.loadHystrixProperties();
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcomePage() {
		return "index";
	}

	@RequestMapping(value = "/trigger", method = RequestMethod.GET)
	@HystrixCommand(fallbackMethod = "fallbackForHealthCheck")
	public String triggerAPICall(Model model) {
		System.out.println("inside the controller...");
		this.restTemplate = new RestTemplate();
//		this.restTemplate.exchange("https://bookingmicroservice.herokuapp.com/api/booking/health",
//				HttpMethod.GET, null, Object.class);
		this.restTemplate.exchange("http://localhost:1010/health",
				HttpMethod.GET, null, Object.class);
		model.addAttribute("message", "server is up and running...");
		return "index";
	}

	public String fallbackForHealthCheck(Model model,Throwable exception) {
		System.out.println("from fallback");
		model.addAttribute("message", "server is down...please try again (from fallback)");
		return "index";
	}
	
	@RequestMapping(value="/triggerRest",method=RequestMethod.GET)
	@ResponseBody
	@HystrixCommand(fallbackMethod="fallbackForRestHealthCheck")
	public String triggerApiCall() {
		System.out.println("inside response body controller");
		this.restTemplate = new RestTemplate();
		this.restTemplate.exchange("http://localhost:1010/health",
				HttpMethod.GET, null, Object.class);
		return "server is up and running from rest controller..";
	}

	public String fallbackForRestHealthCheck() {
		System.out.println("from rest fallback");
		return "server is down.. (from rest fallback)";
	}
}
