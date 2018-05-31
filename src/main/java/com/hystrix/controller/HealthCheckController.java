package com.hystrix.controller;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.hystrix.utils.HystrixPropertyUtils;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Controller
public class HealthCheckController {

	private RestTemplate restTemplate;

	//private Model model;
	
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
		//this.model = model;
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

}
