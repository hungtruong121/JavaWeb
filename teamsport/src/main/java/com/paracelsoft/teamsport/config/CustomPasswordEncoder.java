package com.paracelsoft.teamsport.config;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.paracelsoft.teamsport.service.AuthenOfbizService;

@Component(value="passwordEncoder")
public class CustomPasswordEncoder implements PasswordEncoder {

    private AuthenOfbizService usersLoginService;

    public CustomPasswordEncoder(AuthenOfbizService usersLoginService) {
    	this.usersLoginService = usersLoginService;
    }
	@Override
	public String encode(CharSequence rawPassword) {
		try {
			return rawPassword.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		//check password
		if(usersLoginService.checkPassword(encodedPassword, rawPassword.toString())) {
			return true;
		}
		return false;
	}

}
