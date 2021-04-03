package com.paracelsoft.teamsport.api.team.controller;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paracelsoft.teamsport.api.dto.InviteDTO;
import com.paracelsoft.teamsport.api.dto.RequestDTO;
import com.paracelsoft.teamsport.api.dto.SearchDTO;
import com.paracelsoft.teamsport.api.user.controller.AbstractController;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.entity.User;
import com.paracelsoft.teamsport.service.PostService;
import com.paracelsoft.teamsport.service.TeamService;
import com.paracelsoft.teamsport.service.UserService;
import com.paracelsoft.teamsport.util.ConstantUtils;
import com.paracelsoft.teamsport.util.MessageUtils;
import com.paracelsoft.teamsport.util.ReponseCode;

@RestController
@RequestMapping("/api/v1/team")
public class TeamController extends AbstractController {
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	PostService postService;

	@Autowired
	MessageUtils messageUtils;
	
	/**
	 * 
	 * @param teamId
	 * @param keyword
	 * @return
	 */
	@GetMapping("/user/search")
	public ApiResponse searchUserTeam(@RequestParam(value="teamId", required = false) BigInteger teamId,
			@RequestParam(value="keyword", required = false) String keyword){
		ApiResponse respon = new ApiResponse();
		try {
			if(teamId == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E002", "teamId"));
				return respon;
			}
			respon = teamService.searchUserTeam(teamId, keyword);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @param search
	 * @return
	 */
	@PostMapping("/user/searchpage")
	public ApiResponse searchPageUserTeam(@RequestBody SearchDTO search){
		ApiResponse respon = new ApiResponse();
		try {
			if(search == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E003"));
				return respon;
			}
			
			respon = teamService.searchUserPageTeam(search);
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
	@GetMapping("/getInfo")
	public ApiResponse getTeamInfo(@RequestParam(value="teamId", required = false) BigInteger teamId) {
		ApiResponse respon = new ApiResponse();
		try {
			if(teamId == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E002", "teamId"));
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
			respon = teamService.getTeamInfo(teamId,user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des api get list request join team
	 * @param teamId
	 * @return
	 */
	@GetMapping("/users/request")
	public ApiResponse getRequestJoinTeam(@RequestParam(value="teamId", required = false) BigInteger teamId){
		ApiResponse respon = new ApiResponse();
		try {
			if(teamId == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E001", "teamId"));
				return respon;
			}

			respon = teamService.getRequestJoinTeam(teamId); 
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des get post by type
	 * @return
	 * @throws Exception
	 * @TODO: 
	 */
	@GetMapping("/postpublic")
	public ApiResponse getPostPublic(@RequestParam(value="teamId", required = false) BigInteger teamId) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {		
			if(teamId == null) {
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
			respon = postService.getPostPublic(teamId, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des get invite team 
	 * @return
	 * @throws Exception
	 * @TODO: 
	 */
	@GetMapping("/users/invite")
	public ApiResponse getInviteTeam(@RequestParam(value = "teamId", required = false) BigInteger teamId) throws Exception {
		ApiResponse respon = new ApiResponse();
		
		try {
			if(teamId == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E001", "teamId"));
				return respon;
			}
			
			respon = teamService.getListInvited(teamId); 
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
	@PostMapping("/updateavatar")
	public ApiResponse updateAvatarTeam(@RequestBody RequestDTO request) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if(request == null || request.getTeamId() == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E001", "teamId"));
				return respon;
			}
			if(request.getUploadId() == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}
			
			respon = teamService.updateAvatar(request.getUploadId(),request.getTeamId()); 
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * @TaoN
	 * @Des Send email to join team
	 * @throws Exception
	 * @TODO: 
	 */
	@PostMapping("/user/sendemailinvite")
	public ApiResponse sendListInvite(@RequestBody InviteDTO inviteDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			
			if (inviteDTO == null || inviteDTO.getTeamId() == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E001", "teamId"));
				return respon;
				
			}
			
			if (inviteDTO.getEmailInvite() == null || inviteDTO.getEmailInvite().isEmpty()) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E016"));
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
			
			respon = teamService.sendEmailInvite( inviteDTO, user.getUserId());
			
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * @author TaoN
	 * @Des Api get list request join team
	 * @param teamId
	 * @return
	 */
	@GetMapping("/users/listrequestjointeam")
	public ApiResponse getListInviteTeam(@RequestParam(value="teamId", required = false) BigInteger teamId){
		ApiResponse respon = new ApiResponse();
		try {
			
			if(teamId == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E001", "teamId"));
				return respon;
				
			}

			respon = teamService.listRequests(teamId); 
			
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * @author TaoN
	 * @Des Approach user 
	 * @param InviteDTO
	 * @return
	 */
	@PostMapping("/users/approachjointeam")
	public ApiResponse approachJoinTeam(@RequestBody InviteDTO inviteDTO){
		ApiResponse respon = new ApiResponse();
		try {
			
			if (inviteDTO.getTeamId() == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E001", "teamId"));
				return respon;
				
			}
			
			User user = getCurrentUserLogin();
			
			if (user == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E006"));
				respon.setData(new ArrayList<>());
	            return respon;
	            
			}
			
			if (inviteDTO.getUserId() == null ) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E020"));
				respon.setData(new ArrayList<>());
	            return respon;
	            
			}
			
			respon = teamService.approachJoinTeam(inviteDTO, user.getUserId()); 
			
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * @author TaoN
	 * @Des Reject
	 * @param InviteDTO
	 * @return
	 */
	@PostMapping("/users/rejectjointeam")
	public ApiResponse rejectRequestJoinTeam(@RequestBody(required = false) InviteDTO inviteDTO){
		ApiResponse respon = new ApiResponse();
		try {
			if(inviteDTO == null || inviteDTO.getTeamId() == null) {
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
			
			respon = teamService.rejectJoinTeam(inviteDTO, user.getUserId() ); 
			
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @param search
	 * @return
	 */
	@PostMapping("/users")
	public ApiResponse listUserOfTeam(@RequestBody SearchDTO search){
		ApiResponse respon = new ApiResponse();
		try {
			if(search == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E003"));
				return respon;
			}
			
			respon = teamService.listUserPageTeam(search);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des kick member
	 * @param request
	 * @return
	 */
	@PostMapping("/users/kick")
	public ApiResponse listUserOfTeam(@RequestBody(required = false) RequestDTO request){
		ApiResponse respon = new ApiResponse();
		try {
			if(request == null || request.getTeamId() == null || request.getUserId() == null) {
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
			
			respon = teamService.kickMember(request, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des list position sport
	 * @param request
	 * @return
	 */
	@GetMapping("/users/positions")
	public ApiResponse listPositionSport(@RequestParam(value="teamId", required = false) BigInteger teamId){
		ApiResponse respon = new ApiResponse();
		try {
			if(teamId == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}
			
			respon = teamService.getPositionSport(teamId);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des change position sport
	 * @param request
	 * @return
	 */
	@PostMapping("/users/changepositions")
	public ApiResponse changePositionSport(@RequestBody(required = false) RequestDTO request){
		ApiResponse respon = new ApiResponse();
		try {
			
			if(request == null || request.getTeamId() == null || request.getUserId() == null
					|| StringUtils.isEmpty(request.getPositionSport())) {
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
			respon = teamService.changePosition(request, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des change position sport
	 * @param request
	 * @return
	 */
	@GetMapping("/users/roles")
	public ApiResponse getListRole(){
		ApiResponse respon = new ApiResponse();
		try {
			List<String> teamRole = new ArrayList<String>();
			teamRole.add(ConstantUtils.Team_Member_Role.TEAM_ADMIN.getValue());
			teamRole.add(ConstantUtils.Team_Member_Role.TEAM_MEMBER.getValue());
			respon.setData(teamRole);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des change position sport
	 * @param request
	 * @return
	 */
	@PostMapping("/users/role/change")
	public ApiResponse changeRole(@RequestBody(required = false) RequestDTO request){
		ApiResponse respon = new ApiResponse();
		try {
			
			if(request == null || request.getTeamId() == null || request.getUserId() == null
					|| StringUtils.isEmpty(request.getTeamMemberRole())) {
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
			respon = teamService.changeRoleMember(request, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * @author TaoN
	 * @Des Api get list request join team
	 * @param teamId
	 * @return
	 */
	@GetMapping("/user/changeposition")
	public ApiResponse updateUserPosition(@RequestParam(value="teamId", required = false) BigInteger teamId,@RequestParam(value="userId", required = false) BigInteger userId,@RequestParam(value="newPosition", required = false) String newPosition){
		ApiResponse respon = new ApiResponse();
		try {
			if(teamId == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E001", "teamId"));
				return respon;
			}
			if(userId == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E001", "userId"));
				return respon;
			}
			if("".compareTo(newPosition) == 0) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}

			respon = teamService.updateUserPosition(teamId,userId,newPosition); 
			
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * @Des search team info by team name
	 * @param teamId
	 * @param keyword
	 * @return
	 */
	@PostMapping("/search/info")
	public ApiResponse searchTeamInfoByTeamName(@RequestBody SearchDTO search){
		ApiResponse respon = new ApiResponse();
		try {
			if(search == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E002", "requestBody"));
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
			
			respon = teamService.searchTeamInfoByTeamName(search);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
}
