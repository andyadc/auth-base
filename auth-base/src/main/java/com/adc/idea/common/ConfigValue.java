package com.adc.idea.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigValue {

	public static String algorithmName;
	public static String hashIterations;

	@Value("${hash.algorithm.name:md5}")
	public void setAlgorithmName(String algorithmName) {
		ConfigValue.algorithmName = algorithmName;
	}

	@Value("${hash.iterations:2}")
	public void setHashIterations(String hashIterations) {
		ConfigValue.hashIterations = hashIterations;
	}

	
}
