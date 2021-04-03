package com.paracelsoft.teamsport.api.rank.controller;

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

import com.paracelsoft.teamsport.api.dto.TeamRankDTO;
import com.paracelsoft.teamsport.api.user.controller.AbstractController;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.entity.User;
import com.paracelsoft.teamsport.service.RankService;
import com.paracelsoft.teamsport.util.ConstantUtils;
import com.paracelsoft.teamsport.util.MessageUtils;
import com.paracelsoft.teamsport.util.ReponseCode;

@RestController
@RequestMapping("/api/v1/admin/rank")
public class RankController extends AbstractController{
	
	@Autowired
	RankService rankService;
	
	@Autowired
	MessageUtils messageUtils;
	
	/**
	 * @author TaoN
	 * @Des Add new rank
	 * @param TeamRankDTO
	 * @return
	 */
	@PostMapping("/update")
	public ApiResponse updateRank(@RequestBody TeamRankDTO rankDTO) {
		ApiResponse respon = new ApiResponse();
		try {
			if (rankDTO == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
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
			if (new BigInteger(ConstantUtils.ROLE_TYPE_ID.ADMIN.getValue()).compareTo(user.getUserRoleId()) != 0) { 
				respon.setSuccess(false);
				respon.setMessage(messageUtils.getMessage("E015"));
				respon.setData(new ArrayList<>());
	            return respon;
			}
			respon = rankService.updateRank(rankDTO, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}		
		return respon;
	}
	
	/**
	 * @author TaoN
	 * @Des Delete rank
	 * @param Rank Id
	 * @return
	 */
	@PostMapping("/delete")
	public ApiResponse removeRank(@RequestBody TeamRankDTO rankDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if (rankDTO == null || rankDTO.getTeamRankId() == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E002", "requestBody"));
				return respon;
			}
			User user = getCurrentUserLogin();
			if (user == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E006"));
	            return respon;
			}
			if (new BigInteger(ConstantUtils.ROLE_TYPE_ID.ADMIN.getValue()).compareTo(user.getUserRoleId()) != 0) { 
				respon.setSuccess(false);
				respon.setMessage(messageUtils.getMessage("E015"));
	            return respon;
			}
			respon = rankService.removeRank(rankDTO.getTeamRankId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * @author TaoN
	 * @Des List rank
	 * @param Rank Id
	 * @return
	 */
	@GetMapping("/list")
	public ApiResponse listRanks() throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			List<TeamRankDTO> listRank = rankService.getListRank();
			respon.setData(listRank);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des update image for rank
	 * @return
	 * @throws Exception
	 * @TODO: 
	 */
	@PostMapping("/update/avatar")
	public ApiResponse updateAvatarRank(@RequestBody TeamRankDTO requestDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if(requestDTO == null && requestDTO.getTeamRankId() == null && requestDTO.getTeamRankAvatar() == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E002", "requestBody"));
				return respon;
			}
			User user = getCurrentUserLogin();
			if (user == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E006"));
	            return respon;
			}
			respon = rankService.updateAvatar(requestDTO); 
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * @author TaoN
	 * @Des API get rank Package Price
	 * @param teamId
	 * @return
	 */
	@GetMapping("/listrankpackage")
	public ApiResponse getRankPackage(@RequestParam(value="teamRankId", required = false) BigInteger teamRankId){
		ApiResponse respon = new ApiResponse();
		try {
			if(teamRankId == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E001", "rankId")); 
				return respon;
			}
			respon = rankService.listRankPackageById(teamRankId);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
}
