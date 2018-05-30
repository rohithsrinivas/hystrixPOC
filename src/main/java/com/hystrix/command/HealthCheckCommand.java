package com.hystrix.command;

import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.hystrix.utils.HystrixPropertyUtils;
import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;

public class HealthCheckCommand extends HystrixCommand<String>{
	
	private RestTemplate restTemplate;

	public HealthCheckCommand(String name) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("HealthGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("HealthCheckKey")));
			HystrixPropertyUtils.loadHystrixProperties();
	}

	@Override
	protected String run() throws Exception {
		System.out.println("inside run method of command");
		this.restTemplate = new RestTemplate();
		this.restTemplate.exchange("http://localhost:1010/health", HttpMethod.GET, null, Object.class);
		return "server is up and running...";
	}
	
	@Override
    protected String getFallback() {
		System.out.println(ConfigurationManager.getConfigInstance().getProperty(""
				+ "hystrix.command.HealthCheckKey.circuitBreaker.sleepWindowInMilliseconds"));
		System.out.println(ConfigurationManager.getConfigInstance().getProperty(""
				+ "hystrix.command.HealthCheckKey.execution.isolation.thread.timeoutInMilliseconds"));
		return "server is down, please try again...";
    }

}
