package com.paracelsoft.teamsport.api.user.controller;

import java.math.BigInteger;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paracelsoft.teamsport.api.dto.RequestDTO;
import com.paracelsoft.teamsport.api.dto.TeamDTO;
import com.paracelsoft.teamsport.api.dto.UserDTO;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.entity.User;
import com.paracelsoft.teamsport.service.NotificationService;
import com.paracelsoft.teamsport.service.TeamService;
import com.paracelsoft.teamsport.service.UserService;
import com.paracelsoft.teamsport.util.MessageUtils;
import com.paracelsoft.teamsport.util.ReponseCode;

@RestController
@RequestMapping("/api/v1/user")
public class UserController extends AbstractController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	MessageUtils messageUtils;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	NotificationService notificationService;
	
	/**
	 * 
	 * @Des get list team follow
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/teams/follow")
	public ApiResponse getAllTeamsFollow(@RequestParam(value = "userId", required = false) BigInteger userId) throws Exception {
		ApiResponse respon = new ApiResponse();
		
		try {
			if(userId == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E001", "userId"));
				return respon;
			}
			
			respon = userService.getTeamFollowByUserId(userId); 
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des unfollow the team
	 * @param userId, teamId
	 * @return
	 */
	@PostMapping("/team/unfollow")
	public ApiResponse unfollowTeam(@RequestBody RequestDTO request) throws Exception{
		ApiResponse respon = new ApiResponse();
		try {
			if(request == null || request.getTeamId() == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}
			
			if(request.getUserId() == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}
			
			respon = userService.unFollowTeam(request.getTeamId(), request.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @param teamDTO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/team/update")
	public ApiResponse createTeam(@RequestBody TeamDTO teamDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if(teamDTO == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E002", "teamDTO"));
				return respon;
			}
			if(StringUtils.isEmpty(teamDTO.getTeamName())) {
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
			respon = userService.createTeam(user.getUserId(),teamDTO);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des get all team joinned
	 * @param 
	 * @return
	 */
	@GetMapping("/teams")
	public ApiResponse getListTeamByUserId(@RequestParam(value="userId", required = false) BigInteger userId) {
		ApiResponse respon = new ApiResponse();
		try {
			if (userId == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E002", "userId"));
				return respon;
			}
			respon = userService.getListTeamByUserId(userId); 
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
	 * @Des leave the team
	 * @param teamId
	 * @return
	 */
	@PostMapping("/team/leave")
	public ApiResponse leaveTeam(@RequestBody RequestDTO request) {
		ApiResponse respon = new ApiResponse();
		try {
			if(request == null || request.getTeamId() == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}
			
			if(request.getUserId() == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}
			respon = userService.leaveTeam(request.getUserId(), request.getTeamId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des remember team viewing
	 * @param teamId
	 * @return
	 */
	@PostMapping("/team/active")
	public ApiResponse updateGroupActive(@RequestBody RequestDTO request) {
		ApiResponse respon = new ApiResponse();
		try {
			if(request == null || request.getTeamId() == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}
			
			if(request.getUserId() == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}
			respon.setSuccess(true);
			respon = userService.updateGroupActive(request.getUserId(), request.getTeamId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des remember team privacy
	 * @param teamId
	 * @return
	 */
	@PostMapping("/privacy")
	public ApiResponse remmeberTeamPrivacy(@RequestBody RequestDTO request) {
		ApiResponse respon = new ApiResponse();
		try {
			if(request == null || request.getTeamId() == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}
			
			if(request.getUserId() == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}
			respon = userService.updatePrivacyActive(request.getUserId(), request.getTeamId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des create user (register)
	 * @param partyDTO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/create")
	public ApiResponse createCustomer(@RequestBody UserDTO userDTO,HttpServletRequest request) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			respon.setSuccess(true);
			respon.setMessage(messageUtils.getMessage("I010"));
			respon = userService.createUser(request,userDTO);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setMessage(messageUtils.getMessage("E003"));
			respon.setSuccess(false);
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des create relative user (register)
	 * @param partyDTO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/create/relative")
	public ApiResponse createRelativeUser(@RequestBody UserDTO userDTO,HttpServletRequest request) throws Exception {
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
			
			respon = userService.createRelativeUser(request,userDTO,user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setMessage(messageUtils.getMessage("E003"));
			respon.setSuccess(false);
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des create relative user (register)
	 * @param partyDTO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/relative")
	public ApiResponse createRelativeUser() throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			User user = getCurrentUserLogin();
			if(user == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E006"));
	            return respon;
			}
			respon = userService.getRelativeUser(user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setMessage(messageUtils.getMessage("E003"));
			respon.setSuccess(false);
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des change password
	 * @param partyDTO(email, userId)
	 * @return
	 * @throws Exception
	 */
	
	@PostMapping("/changepass")
	public ApiResponse changePassWord(@RequestBody UserDTO userDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			respon = userService.changePassword(userDTO);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		
		return respon;
	}
	
	/**
	 * 
	 * @Des forgot password
	 * @param partyDTO(email, userId)
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/forgotpass")
	public ApiResponse forgotPassWord(@RequestBody UserDTO userDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			userService.forgotPassword(userDTO);
			respon.setMessage(messageUtils.getMessage("I008"));
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
	 * @Des api request join team
	 * @param teamId
	 * @return
	 */
	@PostMapping("/requestjointeam")
	public ApiResponse requestJoinTeam(@RequestBody RequestDTO request){
		ApiResponse respon = new ApiResponse();
		try {
			if(request == null || request.getTeamId() == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E001", "teamId"));
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
			
			respon = teamService.requestJoinTeam(request.getTeamId(),user.getUserId()); 
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
	 * @Des user follow team
	 * @return
	 * @throws Exception
	 * @TODO: 
	 */
	@PostMapping("/followteams")
	public ApiResponse getFollowTeam(@RequestBody RequestDTO request) throws Exception {
		ApiResponse respon = new ApiResponse();
		
		try {
			if(request == null || request.getTeamId() == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E001", "teamId"));
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
			
			respon = teamService.setFollowTeam(request.getTeamId(),user.getUserId());
			respon.setMessage(messageUtils.getMessage("I011", "team"));
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des get personal info
	 * @param 
	 * @return
	 */
	@GetMapping("/info")
	public ApiResponse getPersonalInfoByUserId(@RequestParam(value="userId", required = false) BigInteger userId) {
		ApiResponse respon = new ApiResponse();
		try {
			if (userId == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E002", "userId"));
				return respon;
			}
			respon = userService.getPersonalInfoByUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des get personal team info
	 * @param userId
	 * @return
	 */
	@GetMapping("/personal/team/info")
	public ApiResponse getTeamInPersonalInfo(@RequestParam(value="userId", required = false) BigInteger userId) {
		ApiResponse respon = new ApiResponse();
		try {
			if (userId == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E002", "userId"));
				return respon;
			}
			respon = userService.getTeamInPersonalInfo(userId);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des get related post in Personal Info
	 * @param userId
	 * @return
	 */
	@GetMapping("/personal/related/post")
	public ApiResponse getRelatedPostByUserId(@RequestParam(value="userId", required = false) BigInteger userId) throws Exception {
		ApiResponse respon = new ApiResponse();

		try {
			if (userId == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E002", "userId"));
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

			respon = userService.getRelatedPostByUserId(userId,user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des update avatar 
	 * @return
	 * @throws Exception
	 * @TODO: 
	 */
	@PostMapping("/update/avatar")
	public ApiResponse updateAvatarUser(@RequestBody RequestDTO requestDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if(requestDTO == null || requestDTO.getUserId() == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E001", "userId"));
				return respon;
			}
			if(requestDTO.getUploadId() == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}

			
			respon = userService.updateAvatar(requestDTO); 
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des active user by otp
	 * @param teamId
	 * @return
	 */
	@GetMapping("/active")
	public String updateUserActive(@RequestParam(value="email", required = false) String email, @RequestParam(value="otp", required = false) String otp ) throws Exception {
		String result = "";
		try {
			if(email == null || otp == null) {
				return messageUtils.getMessage("E010","user");
			}
			result = userService.updateUserActive(email,otp);
		} catch (Exception e) {
			e.printStackTrace();
			return messageUtils.getMessage("E010","user");
		}
		return result;
	}
	
	/**
	 * 
	 * @Des check otp
	 * @param teamId
	 * @return
	 */
	@PostMapping("/changepass/otp")
	public ApiResponse checkotp(@RequestBody RequestDTO request) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if(request == null || StringUtils.isEmpty(request.getUserMail())
					|| StringUtils.isEmpty(request.getOtp())
					|| StringUtils.isEmpty(request.getNewPassword())) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}
			respon = userService.checkOTPByEmail(request);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des create user (register)
	 * @param partyDTO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/update")
	public ApiResponse updateCustomer(@RequestBody UserDTO userDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			respon = userService.updateUser(userDTO);
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
	
	/**
	 * 
	 * @TODO: generate relative account
	 * @Des api generate relative account
	 * @param userDTO
	 * @return
	 */
	@PostMapping("/relative/account")
	public ApiResponse generateUserRelative(@RequestBody UserDTO userDTO){
		ApiResponse respon = new ApiResponse();
		try {
			if (userDTO.getUserMail() == null && userDTO.getPassword() == null && userDTO.getUserId() == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}
			respon = userService.generateUserRelative(userDTO);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setMessage(messageUtils.getMessage("E003"));
			respon.setSuccess(false);
		}
		return respon;
	}
}
