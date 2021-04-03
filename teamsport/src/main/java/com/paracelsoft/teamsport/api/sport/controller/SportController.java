package com.paracelsoft.teamsport.api.sport.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paracelsoft.teamsport.api.dto.SportDTO;
import com.paracelsoft.teamsport.api.user.controller.AbstractController;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.entity.Sport;
import com.paracelsoft.teamsport.entity.User;
import com.paracelsoft.teamsport.service.SportService;
import com.paracelsoft.teamsport.util.MessageUtils;
import com.paracelsoft.teamsport.util.ReponseCode;

@RestController
@RequestMapping("/api/v1/sport")
public class SportController extends AbstractController {

	@Autowired
	SportService sportService;
	
	@Autowired
	MessageUtils messageUtils;
	
	/**
	 * 
	 * @Des get list sports 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/sports")
//	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse selectListPrivacy() throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			List<SportDTO> listSport = sportService.getListSportDTO();
			respon.setData(listSport);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des api created, updated sport
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/update")
//	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse updatePost(@RequestBody SportDTO sportDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if(sportDTO == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}
			
			User user = getCurrentUserLogin();
			if(user == null) {
				respon.setSuccess(false);
				respon.setErrorCode("INVALID_CODE");
				respon.setMessage(messageUtils.getMessage("E006"));
				respon.setData(new ArrayList<>());
	            return respon;
			}

			BigInteger createdBy = user.getUserId();
			respon = sportService.updateSport(sportDTO, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des api remove sport
	 * @return
	 * @param sportId
	 * @throws Exception
	 */
	@GetMapping("/delete")
	public ApiResponse removeSport(@RequestParam(value="sportId", required = false) BigInteger sportId) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if(sportId == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}
			
			respon = sportService.removeSport(sportId);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	
	/**
	 * 
	 * @Des get list sports name for webadmin (Sporting Achievements - UserDetail) 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/all")
	public ApiResponse selectListSport() throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			List<Sport> listSport = sportService.getListSport();
			respon.setData(listSport);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
}
