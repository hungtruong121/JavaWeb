package com.paracelsoft.teamsport.api.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.entity.TokenFirebase;
import com.paracelsoft.teamsport.service.FirebaseService;
import com.paracelsoft.teamsport.util.MessageUtils;
import com.paracelsoft.teamsport.util.ReponseCode;

@RestController
@RequestMapping("/api/v1/firebase")
public class FirebaseController {
	
	@Autowired
	MessageUtils messageUtils;
	
	@Autowired
	FirebaseService fireBaseService;
	
	/**
	 * 
	 * @Des update token
	 * @param token
	 * @return
	 */
	@PostMapping("/token")
	public ApiResponse updatedToken(@RequestBody TokenFirebase token) {
		ApiResponse respon = new ApiResponse();
		try {
			
			if (token == null || token.getUserId() == null) {
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setSuccess(false);
				return respon;
			}

			fireBaseService.updateToken(token);
			respon.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
		}
		return respon;
	}

}
