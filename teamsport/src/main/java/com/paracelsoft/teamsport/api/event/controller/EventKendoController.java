package com.paracelsoft.teamsport.api.event.controller;

import java.math.BigInteger;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paracelsoft.teamsport.api.dto.EventKendoDTO;
import com.paracelsoft.teamsport.api.dto.SearchDTO;
import com.paracelsoft.teamsport.api.dto.EventMatchKendoDTO;
import com.paracelsoft.teamsport.api.user.controller.AbstractController;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.entity.User;
import com.paracelsoft.teamsport.service.EventKendoService;
import com.paracelsoft.teamsport.util.MessageUtils;
import com.paracelsoft.teamsport.util.ReponseCode;

@RestController
@RequestMapping("/api/v1/event/kendo")
public class EventKendoController extends AbstractController{
	@Autowired
	EventKendoService eventKendoService;

	@Autowired
	MessageUtils messageUtils;
	
	/**
	 * 
	 * @Des create/update event kendo
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/update")
	public ApiResponse updateEventKendo(@RequestBody EventKendoDTO eventKendoDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if (eventKendoDTO == null || eventKendoDTO.getTeamId() == null || eventKendoDTO.getPrivacyId() == null
					|| eventKendoDTO.getEventType() == null || eventKendoDTO.getEventDate() == null) {
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
	            return respon;
			}

			respon = eventKendoService.updateEventKendo(eventKendoDTO, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * @Des Send email to invite use app & join event
	 * @throws Exception
	 */
	@PostMapping("/mail/sendinvite")
	public ApiResponse sendMailInvite(@RequestBody EventKendoDTO eventDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if (eventDTO == null || eventDTO.getTeamId() == null || eventDTO.getOpponentEmail() == null) {
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
	            return respon;
			}
			
			respon = eventKendoService.sendEmailInvite(eventDTO, user);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * @Des Admin opponent team approve join event
	 * @throws Exception
	 */
	@PostMapping("/approve")
	public ApiResponse approveInvite(@RequestBody EventKendoDTO eventDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if (eventDTO == null || eventDTO.getEventId() == null) {
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
	            return respon;
			}
			
			respon = eventKendoService.approveInvite(eventDTO, user);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des update macth kendo
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/match/update")
	public ApiResponse updateMatchKendo(@RequestBody EventKendoDTO eventKendoDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if (eventKendoDTO == null || eventKendoDTO.getEventId() == null) {
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
	            return respon;
			}

			respon = eventKendoService.updateMatchKendo(eventKendoDTO, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des lock macth kendo
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/match/lock")
	public ApiResponse lockMatchKendo(@RequestBody EventKendoDTO eventKendoDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if (eventKendoDTO == null || eventKendoDTO.getEventId() == null) {
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
	            return respon;
			}

			respon = eventKendoService.lockMatchKendo(eventKendoDTO, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des unlock macth kendo
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/match/unlock")
	public ApiResponse unlockMatchKendo(@RequestBody EventKendoDTO eventKendoDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if (eventKendoDTO == null || eventKendoDTO.getEventId() == null) {
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
	            return respon;
			}

			respon = eventKendoService.unlockMatchKendo(eventKendoDTO, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * @Des get event detail
	 * @param eventId
	 * @return
	 */
	@GetMapping("/detail")
	public ApiResponse getDetailEventKendo(@RequestParam(value="eventId", required = false) BigInteger eventId) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if(eventId == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E002", "requestParams"));
				return respon;
			}
			
			User user = getCurrentUserLogin();
			if(user == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E006"));
	            return respon;
			}
			
			respon = eventKendoService.getDetailEventKendo(eventId);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * @Des search fixture
	 * @param search
	 * @return
	 */
	@PostMapping("/fixture/search")
	public ApiResponse searchFixture(@RequestBody SearchDTO search){
		ApiResponse respon = new ApiResponse();
		try {
			if (search == null || search.getTeamId() == null || search.getFromDate() == null
					|| search.getToDate() == null || search.getPrivacyId() == null) {
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
			
			respon = eventKendoService.searchFixture(search, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * @Des list fixture
	 * @param search
	 * @return
	 */
	@PostMapping("/fixture/list")
	public ApiResponse listFixture(@RequestBody SearchDTO search){
		ApiResponse respon = new ApiResponse();
		try {
			if (search == null || search.getTeamId() == null) {
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
			
			respon = eventKendoService.listFixture(search, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * @Des list match stats (thống kê trận đấu team vs team)
	 * @param eventKendoDTO
	 * @return
	 */
	@PostMapping("/match/stats")
	public ApiResponse listMatchStats(@RequestBody EventKendoDTO eventKendoDTO){
		ApiResponse respon = new ApiResponse();
		try {
			if (eventKendoDTO == null || eventKendoDTO.getTeamId() == null ||  eventKendoDTO.getOpponentTeamId() == null) {
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
			
			respon = eventKendoService.listMatchStats(eventKendoDTO, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des get photo by match id
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/match/photo")
	public ApiResponse getPhotoByMatch(@RequestBody EventMatchKendoDTO eventMatchKendoDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if (eventMatchKendoDTO == null || eventMatchKendoDTO.getEventMatchId() == null) {
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
	            return respon;
			}

			respon = eventKendoService.getPhotoByMatch(eventMatchKendoDTO, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des get photo by match id
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/match/photo/all")
	public ApiResponse getAllPhotoByMatch(@RequestBody EventKendoDTO eventKendoDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if (eventKendoDTO == null || eventKendoDTO.getEventId() == null) {
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
	            return respon;
			}

			respon = eventKendoService.getAllPhotoByMatch(eventKendoDTO, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des get list folder child by event id
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/folder")
	public ApiResponse getFolderByEvent(@RequestBody EventKendoDTO eventKendoDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if (eventKendoDTO == null || eventKendoDTO.getEventId() == null) {
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
	            return respon;
			}

			respon = eventKendoService.getFolderByEvent(eventKendoDTO, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
}
