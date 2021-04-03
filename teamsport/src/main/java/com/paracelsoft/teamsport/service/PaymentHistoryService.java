package com.paracelsoft.teamsport.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.paracelsoft.teamsport.api.dto.PaymentHistoryDTO;
import com.paracelsoft.teamsport.api.user.controller.AbstractController;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.entity.PaymentHistory;
import com.paracelsoft.teamsport.entity.Team;
import com.paracelsoft.teamsport.entity.User;
import com.paracelsoft.teamsport.repository.PaymentHistoryRepository;
import com.paracelsoft.teamsport.repository.TeamRepository;
import com.paracelsoft.teamsport.repository.UserRepository;
import com.paracelsoft.teamsport.util.ConstantUtils;
import com.paracelsoft.teamsport.util.DateUtil;
import com.paracelsoft.teamsport.util.MessageUtils;

@Transactional(rollbackFor = { Exception.class, ParseException.class })
@Service("paymentHistoryService")
public class PaymentHistoryService extends AbstractController{
	
	@Autowired
	PaymentHistoryRepository paymentHistoryRepository;
	
	@Autowired
	MessageUtils messageUtils;
	
	@Autowired
	TeamRepository teamRepository;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	NotificationService notificationService;
	
	public List<PaymentHistoryDTO> getListPaymentHistory() {
		List<Map<String, Object>> listPaymentHistory = paymentHistoryRepository.listPaymentHistories();
		List<PaymentHistoryDTO> listPaymentHistories = new ArrayList<PaymentHistoryDTO>();
		
		if (listPaymentHistory != null && !listPaymentHistory.isEmpty()) {
			for (Map<String, Object> obj : listPaymentHistory) {
				PaymentHistoryDTO paymentHistoryDTO = new PaymentHistoryDTO();
				paymentHistoryDTO.setPaymentHistoryId(new BigInteger(obj.get("payment_history_id").toString()));
				paymentHistoryDTO.setInvoiceId(obj.get("invoice_id").toString());
				paymentHistoryDTO.setPromotionCode(obj.get("promotion_code").toString());
				paymentHistoryDTO.setTeamName(obj.get("team_name").toString());
				paymentHistoryDTO.setTeamId(new BigInteger(obj.get("team_id").toString()));
				paymentHistoryDTO.setExpiredDate(DateUtil.getFormatDate(obj.get("team_rank_expire").toString(), DateUtil.PT_YYYY_MM_DD_HH_MM_SS));
				paymentHistoryDTO.setServiceName(obj.get("service_name").toString()); 
				paymentHistoryDTO.setDuration(new Integer(obj.get("duration").toString()));
				paymentHistoryDTO.setAmount(new BigDecimal(obj.get("amount").toString()));
				paymentHistoryDTO.setTransactionId(obj.get("transaction_id").toString());
				paymentHistoryDTO.setPaymentMethod(obj.get("payment_method").toString());
				paymentHistoryDTO.setStatusName(obj.get("status_name").toString());
				paymentHistoryDTO.setStatusId(new BigInteger(obj.get("status_id").toString()));
				paymentHistoryDTO.setErrorCode(obj.get("error_code").toString());
				paymentHistoryDTO.setCreatedby(new BigInteger(obj.get("created_by").toString()));
				paymentHistoryDTO.setCreatedDate(paymentHistoryDTO.getCreatedDate());
				paymentHistoryDTO.setCreatedDate(DateUtil.getFormatDate(obj.get("created_date").toString(), DateUtil.PT_YYYY_MM_DD_HH_MM_SS));
				paymentHistoryDTO.setUpdatedDate(DateUtil.getFormatDate(obj.get("updated_date").toString(), DateUtil.PT_YYYY_MM_DD_HH_MM_SS));
				listPaymentHistories.add(paymentHistoryDTO);
			}
		}
		return listPaymentHistories;
	}

	/**
	 * @author TaoN
	 * @Des Update PaymentHistory By Id
	 * @param PaymentHistoryDTO
	 */
	public ApiResponse updatePaymentHistory(PaymentHistoryDTO paymentHistoryDTO, BigInteger createdBy) {
		ApiResponse respon = new ApiResponse();
		PaymentHistory paymentHistory = new PaymentHistory();
		if (paymentHistoryDTO.getPaymentHistoryId() != null) {
			PaymentHistory oldPaymentHistory = paymentHistoryRepository.findByPaymentHistoryId(paymentHistoryDTO.getPaymentHistoryId());
			if (oldPaymentHistory == null) {
				respon.setSuccess(false);
				respon.setMessage(messageUtils.getMessage("E001", "paymentHistory"));
				return respon;
			}
			paymentHistory = this.convertToEntity(paymentHistoryDTO, oldPaymentHistory.getCreatedby(), oldPaymentHistory.getCreatedDate());
			paymentHistory = paymentHistoryRepository.save(paymentHistory);
			this.handleExprie(paymentHistoryDTO);
			respon.setMessage(messageUtils.getMessage("I003","paymentHistory"));
			respon.setData(paymentHistory);
		}
		return respon;
	}
		
	/**
	 * @author TaoN
	 * @Des convert to entity
	 * @param PaymentHistoryDTO
	 */
	public PaymentHistory convertToEntity(PaymentHistoryDTO paymentHistoryDTO, BigInteger createdBy, Date createdDate ) {
		PaymentHistory paymentHistory = new PaymentHistory();
		Date date = new Date();
		
		paymentHistory.setPaymentHistoryId(paymentHistoryDTO.getPaymentHistoryId());
		paymentHistory.setTransactionId(paymentHistoryDTO.getTransactionId());
		paymentHistory.setStatusId(paymentHistoryDTO.getStatusId());
		paymentHistory.setTeamId(paymentHistoryDTO.getTeamId());
		paymentHistory.setInvoiceId(paymentHistoryDTO.getInvoiceId());
		paymentHistory.setPromotionCode(paymentHistoryDTO.getPromotionCode());
		paymentHistory.setPaymentMethod(paymentHistoryDTO.getPaymentMethod());
		paymentHistory.setErrorCode(paymentHistoryDTO.getErrorCode());
		paymentHistory.setServiceName(paymentHistoryDTO.getServiceName());
		paymentHistory.setDuration(paymentHistoryDTO.getDuration());
		paymentHistory.setAmount(paymentHistoryDTO.getAmount());
		paymentHistory.setCreatedby(createdBy);
		paymentHistory.setCreatedDate(createdDate);
		paymentHistory.setUpdatedDate(date);
		return paymentHistory;
	}
	
	/**
	 * @author TaoN
	 * @Des Handle expired day for service
	 * @param paymentHistoryDTO
	 */

	public void handleExprie(PaymentHistoryDTO paymentHistoryDTO) {
		if (new BigInteger(ConstantUtils.PaymentStatus.SUCCESSFUL.getValue()).compareTo(paymentHistoryDTO.getStatusId()) == 0){
			Date exprieDate = DateUtils.addMonths(new Date(), paymentHistoryDTO.getDuration());
			teamRepository.updateTeamRankExprie(paymentHistoryDTO.getTeamId(), exprieDate, 1); 
		}  
	}
	
	/**
	 * @author TaoN
	 * @Des Handle to send notification for service
	 */
	public void handleExpiredNotification() {
		try {
			List<Team> expireTeam = teamRepository.findExpireTeam();
			if (expireTeam != null && !expireTeam.isEmpty()) {
				for (Team item : expireTeam) {
					Date expireDate = item.getTeamRankExprie(); // Expired date
					Date tenDaysBeforeExpiry = DateUtils.addDays(expireDate, -10); // Ten days before expiration
					Date fiveDaysBeforeExpiry = DateUtils.addDays(expireDate, -5); // Five days before expiration
					
					if (DateUtil.compareToCurrentDate(tenDaysBeforeExpiry) == 0
							|| DateUtil.compareToCurrentDate(fiveDaysBeforeExpiry) == 0
							|| DateUtil.compareToCurrentDate(expireDate) == 0) {
						List<User> teamAdmins = userRepository.getAllAdmin(item.getTeamId(), ConstantUtils.Team_Member_Role.TEAM_ADMIN.getValue()); //save notification to all admin of team
						notificationService.notificationFromSystemToTeam(new BigInteger(ConstantUtils.Default_Image.SYSTEM_DEFAULT.getValue()) ,teamAdmins, item.getTeamId(), "N015",
								ConstantUtils.MobileScreen.TEAM_PROFILE_DETAIL.getDescription(),
								ConstantUtils.Notification_Type.NOTIFI_SYSTEM.getValue());
						System.out.println("RunExpiredNotification");
					}
				}		
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
