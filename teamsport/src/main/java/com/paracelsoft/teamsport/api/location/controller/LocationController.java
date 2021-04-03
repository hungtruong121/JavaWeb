package com.paracelsoft.teamsport.api.location.controller;

import java.math.BigInteger;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paracelsoft.teamsport.api.dto.LocationDTO;
import com.paracelsoft.teamsport.api.dto.SearchDTO;
import com.paracelsoft.teamsport.api.user.controller.AbstractController;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.entity.User;
import com.paracelsoft.teamsport.service.LocationService;
import com.paracelsoft.teamsport.util.MessageUtils;
import com.paracelsoft.teamsport.util.ReponseCode;

@RestController
@RequestMapping("/api/v1/location")
public class LocationController extends AbstractController {

	@Autowired
	LocationService locationService;
	
	@Autowired
	MessageUtils messageUtils;
	
	@Autowired
	Environment evn;
	/**
	 * 
	 * @Des api created, updated location
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/update")
	public ApiResponse addLocation(@RequestBody LocationDTO locationDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if (locationDTO == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}

			User user = getCurrentUserLogin();
			if (user == null) {
				respon.setSuccess(false);
				respon.setErrorCode("INVALID_CODE");
				respon.setMessage(messageUtils.getMessage("E006"));
				respon.setData(new ArrayList<>());
				return respon;
			}
			respon = locationService.addLocation(locationDTO, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des get list location by teamId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/list")
	public ApiResponse getListLocationInTeam(@RequestParam(value = "teamId", required = false) BigInteger teamId)
			throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if (teamId == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}

			User user = getCurrentUserLogin();
			if (user == null) {
				respon.setSuccess(false);
				respon.setErrorCode("INVALID_CODE");
				respon.setMessage(messageUtils.getMessage("E006"));
				respon.setData(new ArrayList<>());
				return respon;
			}
			respon = locationService.getListLocationInTeam(teamId, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des delete location
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/delete")
	public ApiResponse delLocation(@RequestBody LocationDTO request)
			throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if (request.getLocationId() == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}
			respon = locationService.delLocation(request.getLocationId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des search location by keyword
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/search")
	public ApiResponse searchLocation(@RequestBody SearchDTO search) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if (search == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}
			respon = locationService.searchLocation(search);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
}
