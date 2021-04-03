package com.paracelsoft.teamsport.api.notifi.controller;

import java.math.BigInteger;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paracelsoft.teamsport.api.dto.NotificationDTO;
import com.paracelsoft.teamsport.api.dto.RequestDTO;
import com.paracelsoft.teamsport.api.dto.SearchDTO;
import com.paracelsoft.teamsport.api.user.controller.AbstractController;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.entity.User;
import com.paracelsoft.teamsport.service.NotificationService;
import com.paracelsoft.teamsport.service.TeamService;
import com.paracelsoft.teamsport.service.UserService;
import com.paracelsoft.teamsport.util.MessageUtils;
import com.paracelsoft.teamsport.util.ReponseCode;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController extends AbstractController {
	
	@Autowired
	NotificationService notificationService;
	
	@Autowired
	MessageUtils messageUtils;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	UserService userService;

	/**
	 * 
	 * @Des get all team joinned and count notification
	 * @param 
	 * @return
	 */
	@PostMapping("")
	public ApiResponse getListTeamByUserId(@RequestBody(required = false) SearchDTO request) {
		ApiResponse respon = new ApiResponse();
		try {
			
			if(request == null) {
				request = new SearchDTO();
			}

			User user = getCurrentUserLogin();
			if(user == null) {
				respon.setSuccess(false);
				respon.setErrorCode("INVALID_CODE");
				respon.setMessage(messageUtils.getMessage("E006"));
				respon.setData(new ArrayList<>());
	            return respon;
			}
			respon.setData(notificationService.getListTeamAndNotifiByUserId(request, user.getUserId())); 
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	@GetMapping("/team")
	public ApiResponse getAllNotifiByTeam(@RequestParam(value="teamId", required = false) BigInteger teamId) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			
			User user = getCurrentUserLogin();
			if(user == null) {
				respon.setSuccess(false);
				respon.setErrorCode("INVALID_CODE");
				respon.setMessage(messageUtils.getMessage("E006"));
				respon.setData(new ArrayList<>());
	            return respon;
			}

			respon = notificationService.countNotification(teamId, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	@PostMapping("/read")
	public ApiResponse updateIsReadNotifi(@RequestBody NotificationDTO notification) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			
			if(notification == null || notification.getNotificationId() == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}
			
			respon = notificationService.updateIsReadNotifi(notification.getNotificationId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	@PostMapping("/reads")
	public ApiResponse updateIsReadAllNotifi(@RequestBody NotificationDTO notification) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if(notification == null || notification.getTeamId() == null) {
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

			respon = notificationService.updateIsReadAllNotifi(notification.getTeamId(),user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @TODO: notification
	 * @Des api accept invite join team of user
	 * @param teamId
	 * @return
	 */
	@PostMapping("/actioninvite")
	public ApiResponse acceptinvite(@RequestBody RequestDTO request){
		ApiResponse respon = new ApiResponse();
		try {
			
			if(request == null || request.getTeamId() == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E001", "teamId"));
				return respon;
			}
			
			//notification then delete notification when is confirmed
			if(request.getNotificationId() == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}
			
			User user = getCurrentUserLogin();
			if(user == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E006"));
				respon.setData(new ArrayList<>());
	            return respon;
			}
			
			//confirm
			if(request.getIsAccept() == 1) {//Dong y loi moi
				respon = userService.acceptInvite(request.getTeamId(),user.getUserId()); 
			}else {
				respon = userService.rejectInvite(request.getTeamId(),user.getUserId()); 
			}
			
			//delete notification when confirmed
			notificationService.deleteNotification(request.getNotificationId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
}
