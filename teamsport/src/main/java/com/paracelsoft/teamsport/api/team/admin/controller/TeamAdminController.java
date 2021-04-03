package com.paracelsoft.teamsport.api.team.admin.controller;

import java.math.BigInteger;
import java.util.List;

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
import com.paracelsoft.teamsport.api.user.controller.AbstractController;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.service.PostService;
import com.paracelsoft.teamsport.service.TeamService;
import com.paracelsoft.teamsport.service.UserService;
import com.paracelsoft.teamsport.util.MessageUtils;
import com.paracelsoft.teamsport.util.ReponseCode;

@RestController
@RequestMapping("/api/v1/team/admin")
public class TeamAdminController extends AbstractController {
	
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
	 * @Des get all teams info
	 * @param NO
	 * @return
	 */
	@GetMapping("/all")
//	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse selectListTeam() throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			List<TeamDTO> listTeam = teamService.getListTeamDTO();
			respon.setData(listTeam);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des Block team change isActive to 0 (Block)
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/block")
//	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse blockTeam(@RequestParam(value="teamId", required = false) BigInteger teamId) throws Exception {
		ApiResponse respon = new ApiResponse();

		try {
			if (teamId == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E002", "teamId"));
				return respon;
			}
			
//			User user = getCurrentUserLogin();
//			if(user == null) {
//				respon.setSuccess(false);
//				respon.setErrorCode("INVALID_CODE");
//				respon.setMessage(messageUtils.getMessage("E006"));
//				respon.setData(new ArrayList<>());
//	            return respon;
//			}
//			if (new BigInteger(ConstantUtils.
//					ROLE_TYPE_ID.ADMIN.getValue()).compareTo(user.getUserRoleId()) != 0) { // Not Admin
//				respon.setSuccess(false);
//				respon.setMessage(messageUtils.getMessage("E015"));
//				respon.setData(new ArrayList<>());
//	            return respon;
//			}

			respon = teamService.blockTeamByTeamId(teamId);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des get all members in team
	 * @param teamId
	 * @return
	 */
	@GetMapping("/members")
	public ApiResponse getAllUserInTeam(@RequestParam(value="teamId", required = false) BigInteger teamId){
		ApiResponse respon = new ApiResponse();
		try {
			if(teamId == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E002", "teamId"));
				return respon;
			}
			respon = teamService.getUserInTeam(teamId);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(e.getMessage());
		}
		return respon;
	}
	
	
	/**
	 * 
	 * @Des create/update team
	 * @param teamDTO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/update")
//	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse createTeam(@RequestBody TeamDTO teamDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if(teamDTO == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E002", "requestBody"));
				return respon;
			}
			
//			User user = getCurrentUserLogin();
//			if(user == null) {
//				respon.setSuccess(false);
//				respon.setErrorCode("INVALID_CODE");
//				respon.setMessage(messageUtils.getMessage("E006"));
//				respon.setData(new ArrayList<>());
//	            return respon;
//			}
//			if (new BigInteger(ConstantUtils.
//					ROLE_TYPE_ID.ADMIN.getValue()).compareTo(user.getUserRoleId()) != 0) { // Not Admin
//				respon.setSuccess(false);
//				respon.setMessage(messageUtils.getMessage("E015"));
//				respon.setData(new ArrayList<>());
//	            return respon;
//			}
			
			respon = userService.createTeam(new BigInteger("2"), teamDTO); // Only for Admin
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des get list sports/ranks name for webadmin (Team Management) 
	 * @return TeamDTO
	 * @throws Exception
	 */
	@GetMapping("/sportsandranks")
	public ApiResponse selectListSportAndRank() throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			TeamDTO listSportAndRank = teamService.getListSportAndRanks();
			respon.setData(listSportAndRank);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des change role member in team
	 * @param request
	 * @return
	 */
	@PostMapping("/role/change")
//	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse changeRole(@RequestBody(required = false) RequestDTO request){
		ApiResponse respon = new ApiResponse();
		try {
			
			if(request == null || request.getTeamId() == null || request.getUserId() == null
					|| StringUtils.isEmpty(request.getTeamMemberRole())) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}
//			User user = getCurrentUserLogin();
//			if(user == null) {
//				respon.setSuccess(false);
//				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
//				respon.setMessage(messageUtils.getMessage("E006"));
//				respon.setData(new ArrayList<>());
//	            return respon;
//			}
//			if (new BigInteger(ConstantUtils.
//				ROLE_TYPE_ID.ADMIN.getValue()).compareTo(user.getUserRoleId()) != 0) { // Not System Admin
//				respon.setSuccess(false);
//				respon.setMessage(messageUtils.getMessage("E015"));
//				respon.setData(new ArrayList<>());
//		        return respon;
//			}
			respon = teamService.changeRoleMemberByAdmin(request, new BigInteger("2"));
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des kick member in team by Admin
	 * @param request
	 * @return
	 */
	@PostMapping("/kick")
//	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse listUserOfTeam(@RequestBody(required = false) RequestDTO request){
		ApiResponse respon = new ApiResponse();
		try {
			if(request == null || request.getTeamId() == null || request.getUserId() == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}
			
//			User user = getCurrentUserLogin();
//			if(user == null) {
//				respon.setSuccess(false);
//				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
//				respon.setMessage(messageUtils.getMessage("E006"));
//				respon.setData(new ArrayList<>());
//	            return respon;
//			}
//			if (new BigInteger(ConstantUtils.
//				ROLE_TYPE_ID.ADMIN.getValue()).compareTo(user.getUserRoleId()) != 0) { // Not System Admin
//				respon.setSuccess(false);
//				respon.setMessage(messageUtils.getMessage("E015"));
//				respon.setData(new ArrayList<>());
//		        return respon;
//			}
			
			respon = teamService.kickMemberByAdmin(request, new BigInteger("2"));
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
}
