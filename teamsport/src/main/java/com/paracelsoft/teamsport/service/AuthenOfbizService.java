package com.paracelsoft.teamsport.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.paracelsoft.teamsport.entity.UserLogin;
import com.paracelsoft.teamsport.repository.UserLoginRepository;
import com.paracelsoft.teamsport.util.HashCrypt;

@Service("loginService")
public class AuthenOfbizService {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private UserLoginRepository loginRepository;
	
	public String getHashType() {
        String hashType = environment.getProperty("password.encrypt.hash.type");

        if (HashCrypt.isEmpty(hashType)) {
//            Debug.logWarning("Password encrypt hash type is not specified in security.properties, use SHA", module);
            hashType = "SHA";
        }

        return hashType;
    }

    public boolean checkPassword(String dataPass, String inputPass) {
        boolean passwordMatches = false;
        if (dataPass != null) {
            passwordMatches = HashCrypt.comparePassword(dataPass, getHashType(), inputPass);
        }
        if (!passwordMatches && "true".equals(environment.getProperty("password.accept.encrypted.and.plain"))) {
            passwordMatches = inputPass.equals(dataPass);
        }
        return passwordMatches;
    }
    
    public Optional<UserLogin> getByUserLoginId(String username) {
    	return loginRepository.findByUserNameAndIsActive(username, 1);
    }
}
