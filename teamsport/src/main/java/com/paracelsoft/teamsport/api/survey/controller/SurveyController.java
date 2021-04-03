package com.paracelsoft.teamsport.api.survey.controller;

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
import org.springframework.web.multipart.MultipartFile;

import com.paracelsoft.teamsport.api.dto.PrivacyDTO;
import com.paracelsoft.teamsport.api.dto.SurveyDTO;
import com.paracelsoft.teamsport.api.user.controller.AbstractController;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.entity.User;
import com.paracelsoft.teamsport.service.PostService;
import com.paracelsoft.teamsport.service.SurveyService;
import com.paracelsoft.teamsport.util.MessageUtils;
import com.paracelsoft.teamsport.util.ReponseCode;

@RestController
@RequestMapping("/api/v1/survey")
public class SurveyController extends AbstractController{
	
	@Autowired
	MessageUtils messageUtils;
	
	@Autowired
	SurveyService surveyService;
	
	@Autowired
	PostService postService;
	
	/**
	 * @Des get list privacy 
	 * @return
	 */
	@GetMapping("/privacys")
	public ApiResponse selectListPrivacy() throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			List<PrivacyDTO> privacy = postService.getListPrivacy();
			respon.setData(privacy);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * @author TaoN
	 * @Des Create and update survey 
	 */
	@PostMapping("/update")
	ApiResponse updateSurvey(@RequestParam(value = "files", required = false) List<MultipartFile> files, @RequestParam(value = "backgroundImage", required = false) List<MultipartFile> backgroundImage, @RequestParam(value = "surveyInfo", required = false) String surveyInfo) {
		ApiResponse respon = new ApiResponse();
		try {
			User user = getCurrentUserLogin();
			if(user == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E006"));
				respon.setData(new ArrayList<>());
	            return respon;
			}
			respon= surveyService.updateSurvey(files, backgroundImage, surveyInfo, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * @author TaoN
	 * @Des get survey detail
	 * @return
	 */
	@GetMapping("/detail")
	public ApiResponse getSurveyDetail(@RequestParam(value="postId", required = false) BigInteger postId) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if (postId == null) {
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
				respon.setData(new ArrayList<>());
				return respon;
			}
			respon = surveyService.getDetailSurveyByPostId(postId, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * @author TaoN
	 * @Des get answer or option by Post Id and Privacy Id
	 * @Des This API using to display user info and user answer's content for the text survey and it also uses for showing all options for survey selection
	 * @return
	 */
	@GetMapping("/listanswer")
	public ApiResponse getListAnswer(@RequestParam(value="postId", required = false) BigInteger postId) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if (postId == null) {
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
				respon.setData(new ArrayList<>());
				return respon;
			}
			respon = surveyService.getListAnswer(postId);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}

	/**
	 * @author TaoN
	 * @Des get user by answer id
	 * @return
	 */
	@PostMapping("/listuservoteans")
	public ApiResponse listUsersByAnsId(@RequestBody SurveyDTO surveyDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if (surveyDTO == null ) {
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
				respon.setData(new ArrayList<>());
				return respon;
			}
			respon = surveyService.getUsersByAnsId(surveyDTO, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * @author TaoN
	 * @Des total votes by answer id
	 * @return
	 */
	@GetMapping("/totalvotebyans")
	public ApiResponse countTotalVoteByAnsId(@RequestParam(value="postId", required = false) BigInteger postId, @RequestParam(value="ansId", required = false) BigInteger ansId) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if (postId == null && ansId == null ) {
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
				respon.setData(new ArrayList<>());
				return respon;
			}
			respon = surveyService.totalVotedByAnsId(postId, ansId);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * @author TaoN
	 * @Des Answer survey or add more option
	 * @return
	 */
	@PostMapping("/answer")
	ApiResponse answerSurveyOraddMoreOption(@RequestParam(value = "files", required = false) List<MultipartFile> files, @RequestParam(value = "answerInfo", required = false) String answerInfo) {
		ApiResponse respon = new ApiResponse();
		try {
			User user = getCurrentUserLogin();
			if (user == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E006"));
				respon.setData(new ArrayList<>());
				return respon;
			}
			respon =  surveyService.addAnswerOrAddOption(files, answerInfo, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * @author TaoN
	 * @Des delete option
	 * @return
	 */
	@PostMapping("/deleteoption")
	ApiResponse answerSurveyOraddMoreOption(@RequestBody SurveyDTO surveyDTO) {
		ApiResponse respon = new ApiResponse();
		try {
			if (surveyDTO.getAnsId() == null && surveyDTO.getPostId() == null) {
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
				respon.setData(new ArrayList<>());
				return respon;
			}
			respon =  surveyService.deleteOptionByOptionId(surveyDTO, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * @author TaoN
	 * @Des Vote answer
	 * @return
	 */
	@PostMapping("/vote")
	ApiResponse voteAnswer(@RequestBody SurveyDTO surveyDTO) {
		ApiResponse respon = new ApiResponse();
		try {
			if (surveyDTO == null) {
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
			respon =  surveyService.addVote(surveyDTO, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}

	/**
	 * @author TaoN
	 * @Des delete survey
	 * @return
	 */
	@PostMapping("/delete")
	ApiResponse deleteSurvey(@RequestBody SurveyDTO surveyDTO) {
		ApiResponse respon = new ApiResponse();
		try {
			if (surveyDTO == null) {
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
			respon =  surveyService.deleteSurvey(surveyDTO, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}

	
}
