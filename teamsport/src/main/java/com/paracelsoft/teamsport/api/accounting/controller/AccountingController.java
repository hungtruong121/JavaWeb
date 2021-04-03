package com.paracelsoft.teamsport.api.accounting.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.paracelsoft.teamsport.api.dto.AccountingDTO;
import com.paracelsoft.teamsport.api.dto.SearchDTO;
import com.paracelsoft.teamsport.api.user.controller.AbstractController;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.entity.User;
import com.paracelsoft.teamsport.service.AccountingService;
import com.paracelsoft.teamsport.service.NotificationService;
import com.paracelsoft.teamsport.service.TeamService;
import com.paracelsoft.teamsport.service.UserService;
import com.paracelsoft.teamsport.util.MessageUtils;
import com.paracelsoft.teamsport.util.ReponseCode;

@RestController
@RequestMapping("/api/v1/accounting")
public class AccountingController extends AbstractController{

	@Autowired
	AccountingService accountingService;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	UserService userService;

	@Autowired
	MessageUtils messageUtils;
	
	@Autowired
	NotificationService notificationService;
	
    @Autowired
    Environment evn;
	
	/**
	 * 
	 * @Des create/update accounting
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/update")
	public ApiResponse updateAccounting(@RequestBody AccountingDTO accountingDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if(accountingDTO == null) {
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

			respon = accountingService.updateAccounting(accountingDTO, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des delete accounting
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/delete")
	public ApiResponse deleteAccounting(@RequestBody AccountingDTO request) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if(request == null || request.getAccountingId() == null) {
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

			respon = accountingService.deleteAccounting(request, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * @param teamId
	 * @return
	 */
	@GetMapping("/list/collecting")
	public ApiResponse getListAccountingCollect(@RequestParam(value="teamId", required = false) BigInteger teamId) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if(teamId == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E002", "teamId"));
				return respon;
			}
			
			respon = accountingService.getListAccountingCollect(teamId);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des get all accounting expired
	 * @param teamId
	 * @return
	 */
	@GetMapping("/list/expired")
	public ApiResponse getListAccountingExpired(@RequestParam(value="teamId", required = false) BigInteger teamId) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if(teamId == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E002", "teamId"));
				return respon;
			}
			
			respon = accountingService.getListAccountingExpired(teamId);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des get detail accounting
	 * @param accountingId, teamId
	 * @return AccountingDTO
	 */
	@GetMapping("/detail")
	public ApiResponse getDetailAccounting(@RequestParam(value="accountingId", required = false) BigInteger accountingId) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if(accountingId == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E002", "requestParams"));
				return respon;
			}
			
			respon = accountingService.getDetailAccounting(accountingId);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des Remind all user Not_paid
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/remindall")
	public ApiResponse remindAllNotPaid(@RequestBody AccountingDTO request) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if(request == null || request.getAccountingId() == null) {
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

			respon = accountingService.remindAllUserNotPaid(request, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des Remind user Not_paid
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/remind")
	public ApiResponse remindUserNotPaid(@RequestBody AccountingDTO request) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if (request == null || request.getAccountingId() == null || request.getUserId() == null) {
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

			respon = accountingService.remindUserNotPaid(request, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * @Des get all accounting of a member in team
	 * @param teamId
	 * @return
	 */
	@GetMapping("/member/list")
	public ApiResponse getListMemberAccounting(@RequestParam(value="teamId", required = false) BigInteger teamId) throws Exception {
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
			
			respon = accountingService.getListMemberAccounting(teamId, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * @Des add img evidence of a member in team
	 * @param file, accountingId
	 * @return
	 */
	@PostMapping("/add/evidence")
	public ApiResponse addImgEvidence(@RequestParam(value = "file", required = false) List<MultipartFile> file,
			@RequestParam(value = "accountingId", required = false) BigInteger accountingId) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
//			if(file == null || file.isEmpty()) {
//				respon.setSuccess(false);
//				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
//				respon.setMessage(messageUtils.getMessage("E001", "file"));
//	            return respon;
//			}
			
			if(accountingId == null) {
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
			
			respon = accountingService.addImgEvidence(file, evn.getProperty("source.image.path").toString(),
					accountingId, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des Confirm all member Paid
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/confirmall")
	public ApiResponse confirmAllPaid(@RequestBody AccountingDTO request) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if(request == null || request.getAccountingId() == null) {
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

			respon = accountingService.confirmAllMemberPaid(request, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des Confirm user Paid Accounting
	 * @param teamId, accountingId
	 */
	@PostMapping("/confirm")
	public ApiResponse confirmPaid(@RequestBody AccountingDTO request) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if (request == null || request.getAccountingId() == null || request.getUserId() == null) {
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

			respon = accountingService.confirmMemberPaid(request, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	
	/**
	 * @Des search accounting by admin
	 * @param search
	 * @return
	 */
	@PostMapping("/admin/search")
	public ApiResponse searchAccountingByAdmin(@RequestBody SearchDTO search){
		ApiResponse respon = new ApiResponse();
		try {
			if(search == null || search.getTeamId() == null || search.getSearchType() == null) {
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
			
			respon = accountingService.searchAccountingAdmin(search, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * @Des search accounting by user
	 * @param search
	 * @return
	 */
	@PostMapping("/user/search")
	public ApiResponse searchAccountingByUser(@RequestBody SearchDTO search){
		ApiResponse respon = new ApiResponse();
		try {
			if(search == null || search.getTeamId() == null) {
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
			
			respon = accountingService.searchAccountingUser(search, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
}
