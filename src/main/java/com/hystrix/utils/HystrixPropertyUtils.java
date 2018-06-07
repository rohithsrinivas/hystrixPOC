package com.hystrix.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import org.apache.commons.configuration.AbstractConfiguration;

import com.netflix.config.ConfigurationManager;

public class HystrixPropertyUtils {
	
	private static final AbstractConfiguration hystrixConfigurationManager = ConfigurationManager.getConfigInstance();
	
	private static Properties hystrixProperties;
	
	public static void loadHystrixProperties(){
		hystrixProperties = new Properties();
		InputStream fileInputStream = HystrixPropertyUtils.class.getClassLoader().getResourceAsStream("hystrix.properties");
		try {
			hystrixProperties.load(fileInputStream);
		} catch (IOException e) {
			return;
		}
		System.out.println("%%%%%%%%%%%"+hystrixProperties.entrySet());
		for(Entry<Object, Object> property : hystrixProperties.entrySet())
			hystrixConfigurationManager.setProperty(property.getKey().toString(), property.getValue().toString());
	}
	
}
