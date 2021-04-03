package com.paracelsoft.teamsport.api.payment.admin.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paracelsoft.teamsport.api.dto.PaymentHistoryDTO;
import com.paracelsoft.teamsport.api.user.controller.AbstractController;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.entity.User;
import com.paracelsoft.teamsport.service.PaymentHistoryService;
import com.paracelsoft.teamsport.util.MessageUtils;
import com.paracelsoft.teamsport.util.ReponseCode;

@RestController
@RequestMapping("/api/v1/admin/paymenthistory")
public class PaymentHistoryController  extends AbstractController {
	
	@Autowired
	MessageUtils messageUtils;
	
	@Autowired
	PaymentHistoryService paymentHistoryService;
	
	/**
	 * @author TaoN
	 * @Des List payment histories
	 * @return
	 */
	@GetMapping("/list")
	public ApiResponse listPaymentHistory() throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			List<PaymentHistoryDTO> listPaymentHistory = paymentHistoryService.getListPaymentHistory();
			respon.setData(listPaymentHistory);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * @author TaoN
	 * @Des Update Payment History By Id
	 * @param PaymentHistoryDTO
	 * @return
	 */
	@PostMapping("/update")
	public ApiResponse updatePaymentHistory(@RequestBody PaymentHistoryDTO paymentHistoryDTO) {
		ApiResponse respon = new ApiResponse();
		try {
			if (paymentHistoryDTO == null) {
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
			respon = paymentHistoryService.updatePaymentHistory(paymentHistoryDTO, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}		
		return respon;
	}
}
