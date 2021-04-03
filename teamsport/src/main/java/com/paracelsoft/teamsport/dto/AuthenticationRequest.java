package com.paracelsoft.teamsport.dto;

import java.io.Serializable;
import java.math.BigInteger;

public class AuthenticationRequest implements Serializable {

	private static final long serialVersionUID = -6986746375915710855L;
	
	private String username;
    private String password ;
    private BigInteger userId;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public BigInteger getUserId() {
		return userId;
	}
	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}
    
}
