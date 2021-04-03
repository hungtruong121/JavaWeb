package com.paracelsoft.teamsport.service;

import java.math.BigInteger;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paracelsoft.teamsport.entity.PersistentToken;
import com.paracelsoft.teamsport.repository.PersistentTokenRepository;
import com.paracelsoft.teamsport.util.ConstantUtils;

@Transactional(rollbackFor = { Exception.class})
@Service("tokenService")
public class TokenService {
	
	@Autowired
    PersistentTokenRepository persistentTokenRepository;
	
	/**
	 * 
	 * @param token
	 * @param userId
	 */
	public void savePersistentLogin(String token, BigInteger userId) {
		//save token to DB
		PersistentToken persis = new PersistentToken();
		persis.setUserId(userId);
		persis.setTokenAuth(token);
		persis.setTokenType(ConstantUtils.Token_Type.AUTH_LOGIN.getValue());
		persis.setCreatedDate(new Date());
		persis.setUpdatedDate(new Date());
		persistentTokenRepository.save(persis);
	}
	 
}
