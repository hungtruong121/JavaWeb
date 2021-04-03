package com.paracelsoft.teamsport.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

	private String secretKey = "secret";

	//validity in milliseconds
	private long validityInMs = 360000000; // 1h

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public long getValidityInMs() {
		return validityInMs;
	}

	public void setValidityInMs(long validityInMs) {
		this.validityInMs = validityInMs;
	}
	
	
}
