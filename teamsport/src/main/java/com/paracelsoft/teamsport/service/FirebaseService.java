package com.paracelsoft.teamsport.service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.paracelsoft.teamsport.config.firebase.HeaderFireBaseRequestInterceptor;
import com.paracelsoft.teamsport.entity.Notification;
import com.paracelsoft.teamsport.entity.TokenFirebase;
import com.paracelsoft.teamsport.repository.TokenFirebaseRepository;

@Transactional(rollbackFor = { Exception.class})
@Service
public class FirebaseService {
	
	@Autowired
	private Environment evn;
	
	@Autowired
	TokenFirebaseRepository tokenFirebaseRepository;
	
	/**
	 * 
	 * @Des function created and push notification for firebase server
	 * @param token
	 * @return
	 */
	public boolean send(BigInteger userId, Notification message) {
		try {
			if(userId == null) {
				return false;
			}
			//get token by customer Id
			TokenFirebase token = tokenFirebaseRepository.findByUserId(userId);
			if(token == null || StringUtils.isEmpty(token.getDevice_token())) {
				return false;
			}

			//content message
			JSONObject notification = new JSONObject();
			notification.put("title", "TEAMSPORT");
			notification.put("body", new JSONObject(message));
			notification.put("sound", "default");
			//body request
			JSONObject body = new JSONObject();
			body.put("to", token.getDevice_token());
			body.put("priority", "high");
			body.put("timeToLive", "1000");
			body.put("notification", notification);
			System.out.println(body.toString());
			HttpEntity<String> request = new HttpEntity<>(body.toString());
	
			CompletableFuture<String> pushNotification = this.send(request);
			CompletableFuture.allOf(pushNotification).join();//not sure used

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 
	 * @Des connect fire base and call api push message
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@Async
	public CompletableFuture<String> send(HttpEntity<String> entity) throws Exception {

		RestTemplate restTemplate = new RestTemplate();

		ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new HeaderFireBaseRequestInterceptor("Authorization", "key=" + evn.getProperty("firebase.server.key")));
		interceptors.add(new HeaderFireBaseRequestInterceptor("Content-Type", "application/json;charset=UTF-8"));
		restTemplate.setInterceptors(interceptors);
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
		String firebaseResponse = restTemplate.postForObject(evn.getProperty("firebase.api.url"), entity, String.class);

		return CompletableFuture.completedFuture(firebaseResponse);
	}
	
	/**
	 * 
	 * @Des api updated token for customerId
	 * @param token
	 * @throws Exception
	 */
	public void updateToken(TokenFirebase token) throws Exception {
		token.setCreatedDate(new Date());
		tokenFirebaseRepository.save(token);
	}
}
