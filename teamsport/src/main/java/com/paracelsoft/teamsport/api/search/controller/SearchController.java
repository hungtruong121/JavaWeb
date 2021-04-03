package com.paracelsoft.teamsport.api.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paracelsoft.teamsport.api.dto.SearchDTO;
import com.paracelsoft.teamsport.api.user.controller.AbstractController;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.service.SearchService;
import com.paracelsoft.teamsport.util.MessageUtils;
import com.paracelsoft.teamsport.util.ReponseCode;

@RestController
@RequestMapping("/api/v1/search")
public class SearchController extends AbstractController {

	@Autowired
	SearchService searchService;
	
	@Autowired
	MessageUtils messageUtils;
	
	/**
	 * 
	 * @Des get list all folder and file
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/all")
	public ApiResponse searchAll(@RequestBody SearchDTO search) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if (search == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}
			respon = searchService.searchAll(search);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
}
