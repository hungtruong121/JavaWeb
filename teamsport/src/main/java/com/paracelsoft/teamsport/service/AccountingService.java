 package com.paracelsoft.teamsport.service;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.paracelsoft.teamsport.api.dto.AccountingDTO;
import com.paracelsoft.teamsport.api.dto.SearchAccountingDTO;
import com.paracelsoft.teamsport.api.dto.SearchDTO;
import com.paracelsoft.teamsport.api.dto.UploadDTO;
import com.paracelsoft.teamsport.api.dto.UserDTO;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.entity.Accounting;
import com.paracelsoft.teamsport.entity.AccountingInclude;
import com.paracelsoft.teamsport.entity.AccountingIncludePK;
import com.paracelsoft.teamsport.entity.TeamMember;
import com.paracelsoft.teamsport.entity.User;
import com.paracelsoft.teamsport.repository.AccountingIncludeRepository;
import com.paracelsoft.teamsport.repository.AccountingRepository;
import com.paracelsoft.teamsport.repository.TeamMemberRepository;
import com.paracelsoft.teamsport.repository.TeamRepository;
import com.paracelsoft.teamsport.repository.UserRepository;
import com.paracelsoft.teamsport.util.ConstantUtils;
import com.paracelsoft.teamsport.util.DateUtil;
import com.paracelsoft.teamsport.util.MessageUtils;

@Transactional(rollbackFor = { Exception.class, ParseException.class })
@Service("accountingService")
public class AccountingService {

	@Autowired
	UserService userService;

	@Autowired
	AccountingRepository accountingRepository;

	@Autowired
	AccountingIncludeRepository accountingIncludeRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	TeamRepository teamRepository;

	@Autowired
	TeamMemberRepository teamMemberRepository;

	@Autowired
	MessageUtils messageUtils;

	@Autowired
	UploadService uploadService;

	@Autowired
	NotificationService notificationService;

	@Autowired
	MailService mailService;

	/**
	 * 
	 * @Des create/update Accounting
	 * @param AccountingDTO
	 * @return
	 * @throws ParseException
	 */
	public ApiResponse updateAccounting(AccountingDTO accountingDTO, BigInteger createdBy) throws ParseException {
		ApiResponse respon = new ApiResponse();
		// Check creating is Admin in team
		TeamMember teamMember = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(accountingDTO.getTeamId(),
				createdBy, 1);
		if (teamMember == null
				|| !ConstantUtils.Team_Member_Role.TEAM_ADMIN.getValue().equals(teamMember.getTeamMemberRole())) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E015"));
			respon.setData(new ArrayList<>());
			return respon;
		}
		
		Accounting accounting = new Accounting();
		Date date = new Date();
		boolean isCreated = true;

		if (accountingDTO.getAccountingId() != null) { // is updated
			isCreated = false;
			Accounting oldAccounting = accountingRepository
					.findByAccountingIdAndIsActive(accountingDTO.getAccountingId(), 1);
			if (oldAccounting == null) { // oldAccounting not exist
				respon.setSuccess(false);
				respon.setMessage(messageUtils.getMessage("E001", "accounting"));
				return respon;
			}

			// Delete all accountingInclude NOT_PAID
			List<AccountingInclude> listNotPaid = accountingIncludeRepository.findById_AccountingIdAndStatusIdAndIsActive(
					oldAccounting.getAccountingId(),
					new BigInteger(ConstantUtils.Accounting_Status.NOT_PAID.getValue()), 1);
			if (listNotPaid != null && !listNotPaid.isEmpty()) {
				accountingIncludeRepository.deleteAll(listNotPaid);
			}
			// update
			accounting = this.convertToEntity(accountingDTO);
			accounting.setCreatedby(oldAccounting.getCreatedby()); // no change
			accounting.setCreatedDate(oldAccounting.getCreatedDate()); // no change
			accounting.setUpdatedDate(date);
			respon.setMessage(messageUtils.getMessage("I003", "accounting"));
		} else { // create
			accounting = this.convertToEntity(accountingDTO);
			accounting.setCreatedby(createdBy);
			accounting.setCreatedDate(date);
			accounting.setUpdatedDate(date);
			respon.setMessage(messageUtils.getMessage("I001", "accounting"));
		}
		accounting = accountingRepository.save(accounting);

		if (accountingDTO.getUserInclude() != null && !accountingDTO.getUserInclude().isEmpty()) {
			// Create AccountingInclude follow numbers of members picked
			List<AccountingInclude> listAccountingInclude = new ArrayList<>();
			
			for (BigInteger member : accountingDTO.getUserInclude()) {
				AccountingInclude accInclude = new AccountingInclude();

				// Check member is in team
				TeamMember thisMember = teamMemberRepository
						.findById_TeamIdAndId_UserIdAndIsActive(accountingDTO.getTeamId(), member, 1);
				if (thisMember != null) {
					if (isCreated) { // create list include
						accInclude = this.convertToEntityInclude(accounting.getAccountingId(), member, date, createdBy);
						listAccountingInclude.add(accInclude);
					} else { // update accountingInclude
						AccountingInclude oldAccInclude = accountingIncludeRepository
								.findById_AccountingIdAndId_UserIdAndIsActive(accounting.getAccountingId(), member, 1);
						// Check memberInclude existed
						if (oldAccInclude == null) { // Add new accountingInclude, còn lại bỏ qua
							accInclude = this.convertToEntityInclude(accounting.getAccountingId(), member, date, createdBy);
							listAccountingInclude.add(accInclude);
						}
					}
				}
			}
			accountingIncludeRepository.saveAll(listAccountingInclude);
		}
		respon.setData(accounting);
		return respon;
	}

	private AccountingInclude convertToEntityInclude(BigInteger accountingId, BigInteger member, Date date,
			BigInteger createdBy) {
		AccountingInclude accInclude = new AccountingInclude();
		AccountingIncludePK accInPK = new AccountingIncludePK();

		accInPK.setAccountingId(accountingId);
		accInPK.setUserId(member);

		accInclude.setId(accInPK);
		accInclude.setStatusId(new BigInteger(ConstantUtils.Accounting_Status.NOT_PAID.getValue()));
		accInclude.setImgEvidence(null); // No_Image
		accInclude.setCreatedby(createdBy);
		accInclude.setCreatedDate(date);
		accInclude.setUpdatedDate(date);

		return accInclude;
	}

	/**
	 * 
	 * @Des convert accountingDTO to entity
	 * @param accountingDTO
	 * @return
	 * @throws ParseException 
	 */
	private Accounting convertToEntity(AccountingDTO accountingDTO) throws ParseException {
		Accounting accounting = new Accounting();
		
		accounting.setAccountingId(accountingDTO.getAccountingId());
		accounting.setAccountingTitle(accountingDTO.getTitle());
		accounting.setTeamId(accountingDTO.getTeamId());
		accounting.setAccountingDeadline(DateUtil.fomatTimeZoneToUTC(accountingDTO.getDeadline(),
				DateUtil.PT_DD_MM_YYYY_HH_MM, DateUtil.PT_YYYY_MM_DD_HH_MM_SS, accountingDTO.getTimeZone()));
		accounting.setAccountingTitle(accountingDTO.getTitle());
		accounting.setStatusId(new BigInteger(ConstantUtils.Accounting_Status.NOT_PAID.getValue()));
		accounting.setAccountingAmount(accountingDTO.getAmount());
		accounting.setAccountingNotice(accountingDTO.getNotice());
		
		return accounting;
	}
	
	/**
	 * 
	 * @Des convert entity to accountingDTO
	 * @param Accounting
	 * @return
	 * @throws ParseException 
	 */
	private AccountingDTO convertEntityToDTO(Accounting accounting) throws ParseException {
		AccountingDTO dto = new AccountingDTO();
		int membersInclude = accountingIncludeRepository.countMemberInclude(accounting.getAccountingId());
		int membersNotPaid = accountingIncludeRepository.countMemberIncludeByStatus(accounting.getAccountingId(),
				new BigInteger(ConstantUtils.Accounting_Status.NOT_PAID.getValue()));
		int membersDone = accountingIncludeRepository.countMemberIncludeByStatus(accounting.getAccountingId(),
				new BigInteger(ConstantUtils.Accounting_Status.DONE.getValue()));
		
		dto.setAccountingId(accounting.getAccountingId());
		dto.setTeamId(accounting.getTeamId());
		dto.setTitle(accounting.getAccountingTitle());
		dto.setAmount(accounting.getAccountingAmount());
		dto.setDateDeadline(accounting.getAccountingDeadline());
		dto.setNotice(accounting.getAccountingNotice());
		dto.setCreatedDate(accounting.getCreatedDate());
		dto.setUpdatedDate(accounting.getUpdatedDate());
		dto.setTotalMemberInclude(membersInclude);
		dto.setTotalMemberNotPaid(membersNotPaid);
		dto.setTotalMemberDone(membersDone);
		dto.setTotalMemberWaiting(membersInclude - (membersNotPaid + membersDone));
		dto.setTotalMemberPaid(membersInclude - membersNotPaid);

		return dto;
	}


	/**
	 * 
	 * @Des delete Accounting
	 * @param AccountingDTO
	 * @return
	 */
	public ApiResponse deleteAccounting(AccountingDTO request, BigInteger deleteBy) {
		ApiResponse respon = new ApiResponse();
		Accounting oldAccounting = accountingRepository.findByAccountingId(request.getAccountingId());

		if (oldAccounting == null) { // oldAccounting not exist
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "accounting"));
			return respon;
		}

		// Check oldAccounting đã có thành viên thanh toán rồi thì không dc xóa
		int membersInclude = accountingIncludeRepository.countMemberInclude(oldAccounting.getAccountingId());
		int membersNotPaid = accountingIncludeRepository.countMemberIncludeByStatus(oldAccounting.getAccountingId(),
				new BigInteger(ConstantUtils.Accounting_Status.NOT_PAID.getValue()));
		if ((membersInclude - membersNotPaid) != 0 ) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E030", "accounting"));
			return respon;
		}

		// Check user deleting is Admin of team
		TeamMember teamMember = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(oldAccounting.getTeamId(),
				deleteBy, 1);
		if (teamMember == null
				|| !ConstantUtils.Team_Member_Role.TEAM_ADMIN.getValue().equals(teamMember.getTeamMemberRole())) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E015"));
			respon.setData(new ArrayList<>());
			return respon;
		}

		// delete accounting
		accountingRepository.delete(oldAccounting);

		// delete all accountingInclude
		List<AccountingInclude> listInclude = accountingIncludeRepository
				.findById_AccountingIdAndIsActive(request.getAccountingId(), 1);
		if (listInclude != null && !listInclude.isEmpty()) {
			accountingIncludeRepository.deleteAll(listInclude);
		}
		
		respon.setMessage(messageUtils.getMessage("I002", "accounting"));

		return respon;
	}

	/**
	 * 
	 * @Des Get list Accounting Collecting
	 * @param teamId
	 * @return List<AccountingDTO>
	 * @throws ParseException 
	 */
	public ApiResponse getListAccountingCollect(BigInteger teamId) throws ParseException {
		ApiResponse respon = new ApiResponse();
		List<Accounting> listAccouting = accountingRepository.findByTeamIdAndIsActive(teamId, 1);
		List<AccountingDTO> listCollecting = new ArrayList<AccountingDTO>();
		
		if (listAccouting != null && !listAccouting.isEmpty()) {
			for (Accounting accounting : listAccouting) {
				AccountingDTO dto = new AccountingDTO();
				
				 // compare datetime now and deadline
				if (DateUtil.compareToDatimeNow(accounting.getAccountingDeadline()) >= 0 ) {
					dto = convertEntityToDTO(accounting);
					listCollecting.add(dto);
				}
			}
		}
		respon.setData(listCollecting);
		return respon;
	}

	/**
	 * 
	 * @Des Get list Accounting Expired
	 * @param teamId
	 * @return List<AccountingDTO>
	 * @throws ParseException 
	 */
	public ApiResponse getListAccountingExpired(BigInteger teamId) throws ParseException {
		ApiResponse respon = new ApiResponse();
		List<Accounting> listAccouting = accountingRepository.findByTeamIdAndIsActive(teamId, 1);
		List<AccountingDTO> listExpired = new ArrayList<AccountingDTO>();
		
		if (listAccouting != null && !listAccouting.isEmpty()) {
			for (Accounting accounting : listAccouting) {
				AccountingDTO dto = new AccountingDTO();
				
				 // compare datetime now and deadline
				if (DateUtil.compareToDatimeNow(accounting.getAccountingDeadline()) < 0 ) {
					dto = convertEntityToDTO(accounting);
					listExpired.add(dto);
				}
			}
		}
		respon.setData(listExpired);
		return respon;
	}

	/**
	 * 
	 * @Des Get detail Accounting
	 * @param accountingId
	 * @return AccountingDTO
	 * @throws ParseException 
	 */
	public ApiResponse getDetailAccounting(BigInteger accountingId) throws ParseException {
		ApiResponse respon = new ApiResponse();
		Accounting accouting = accountingRepository.findByAccountingIdAndIsActive(accountingId, 1);
		
		if (accouting == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "accounting"));
			return respon;
		}
		AccountingDTO accountingDTO = convertEntityToDTO(accouting);
		List<AccountingInclude> listUserInclude = accountingIncludeRepository.findById_AccountingIdAndIsActive(accountingId, 1);
		List<BigInteger> usersInclude = new ArrayList<BigInteger>();
		
		if (listUserInclude != null && !listUserInclude.isEmpty()) {
			List<UserDTO> listMemberNotPaid = new ArrayList<UserDTO>();
			List<UserDTO> listMemberWaiting = new ArrayList<UserDTO>();
			List<UserDTO> listMemberDone = new ArrayList<UserDTO>();
			
			for (AccountingInclude userInclude : listUserInclude) {
				UserDTO userDTO = new UserDTO();
				User user = userRepository.findByUserIdAndIsActive(userInclude.getId().getUserId(), 1);
				
				if (user != null) {
					usersInclude.add(userInclude.getId().getUserId());
					userDTO.setUserId(user.getUserId());
					userDTO.setUserFullName(user.getUserFullName());
					userDTO.setUserAvatar(user.getUserAvatar());
					userDTO.setAccountingEvidence(userInclude.getImgEvidence()); // for Waiting
					
					if (new BigInteger(ConstantUtils.Accounting_Status.NOT_PAID.getValue())
							.compareTo(userInclude.getStatusId()) == 0) { // is Not_Paid
						listMemberNotPaid.add(userDTO);
					} else if (new BigInteger(ConstantUtils.Accounting_Status.WAITING.getValue())
							.compareTo(userInclude.getStatusId()) == 0) { // is Waiting
						listMemberWaiting.add(userDTO);
					} else { // is Done
						listMemberDone.add(userDTO);
					}
				}
			}
			accountingDTO.setUserInclude(usersInclude);
			accountingDTO.setListMemberNotPaid(listMemberNotPaid);
			accountingDTO.setListMemberWaiting(listMemberWaiting);
			accountingDTO.setListMemberDone(listMemberDone);
		}
		
		respon.setData(accountingDTO);
		return respon;
	}

	/**
	 * 
	 * @Des Remind all user Not Paid Accounting
	 * @param teamId, accountingId
	 * @throws JSONException 
	 * @throws NoSuchMessageException 
	 */
	public ApiResponse remindAllUserNotPaid(AccountingDTO request, BigInteger userAdmin) throws NoSuchMessageException, JSONException {
		ApiResponse respon = new ApiResponse();
		Accounting oldAccounting = accountingRepository.findByAccountingIdAndIsActive(request.getAccountingId(), 1);
		if (oldAccounting == null) { // oldAccounting not exist
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "accounting"));
			return respon;
		}
		
		// Get list userInclude Not_paid
		List<AccountingInclude> listNotPaid = accountingIncludeRepository.findById_AccountingIdAndStatusIdAndIsActive(request.getAccountingId(),
				new BigInteger(ConstantUtils.Accounting_Status.NOT_PAID.getValue()), 1);
		if (listNotPaid != null && !listNotPaid.isEmpty()) {
			for (AccountingInclude userNotPaid : listNotPaid) {
				// Notification remind member paid accounting
				notificationService.notificationFromTeamToUser(oldAccounting.getTeamId(), userNotPaid.getId().getUserId(), userAdmin, "N013",
						ConstantUtils.MobileScreen.TEAM_PROFILE_DETAIL.getDescription(),
						ConstantUtils.Notification_Type.NOTIFI_MESS.getValue());
			}
		}
		
		respon.setMessage(messageUtils.getMessage("I022", "members"));
		return respon;
	}
	
	/**
	 * 
	 * @Des Remind user Not Paid Accounting
	 * @param teamId, accountingId
	 * @throws JSONException 
	 * @throws NoSuchMessageException 
	 */
	public ApiResponse remindUserNotPaid(AccountingDTO request, BigInteger userAdmin) throws NoSuchMessageException, JSONException {
		ApiResponse respon = new ApiResponse();
		Accounting oldAccounting = accountingRepository.findByAccountingIdAndIsActive(request.getAccountingId(), 1);
		if (oldAccounting == null) { // oldAccounting not exist
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "accounting"));
			return respon;
		}
		
		// Get userInclude Not_paid
		AccountingInclude userInclude = accountingIncludeRepository.findById_AccountingIdAndId_UserIdAndIsActive(request.getAccountingId(), request.getUserId(), 1);
		if (userInclude == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "user"));
			return respon;
		}
		
		// Check this user is not_paid
		if (new BigInteger(ConstantUtils.Accounting_Status.NOT_PAID.getValue()).compareTo(userInclude.getStatusId()) != 0) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E024"));
			return respon;
		} else { // is not_paid
			// Notification remind member paid accounting
			notificationService.notificationFromTeamToUser(oldAccounting.getTeamId(), request.getUserId(), userAdmin, "N013",
					ConstantUtils.MobileScreen.TEAM_PROFILE_DETAIL.getDescription(),
					ConstantUtils.Notification_Type.NOTIFI_MESS.getValue());
		}
		
		respon.setMessage(messageUtils.getMessage("I022", "member"));
		return respon;
	}

	/**
	 * 
	 * @Des Get list Accounting of member in team
	 * @param teamId, userId
	 * @return List<AccountingDTO>
	 * @throws ParseException 
	 */
	public ApiResponse getListMemberAccounting(BigInteger teamId, BigInteger userId) throws ParseException {
		ApiResponse respon = new ApiResponse();
		
		// get list accounting user included
		List<Map<String, Object>> listAccoutingUserInclude = accountingRepository.getListAccountingUserInclude(userId, teamId, 1);
		List<AccountingDTO> listMemAccounting = new ArrayList<AccountingDTO>();
		
		if (listAccoutingUserInclude != null) {
			for (Map<String, Object> accounting : listAccoutingUserInclude) {
				AccountingDTO accountingDTO = convertMapAccoungtingToDTO(accounting);
				
				accountingDTO.setStatusPaid(new BigInteger(accounting.get("status_id").toString()));
				if (!StringUtils.isEmpty(accounting.get("img_evidence").toString())) {
					accountingDTO.setImgEvidence(new BigInteger(accounting.get("img_evidence").toString()));
				} else {
					accountingDTO.setImgEvidence(null);
				}
				listMemAccounting.add(accountingDTO);
			}
		}
		respon.setData(listMemAccounting);
		return respon;
	}

	/**
	 * @Des add img evidence of a member in team
	 * @param file, accountingId, userId
	 * @return
	 */
	public ApiResponse addImgEvidence(List<MultipartFile> file, String fileStoragePath, BigInteger accountingId, BigInteger userId) throws Exception {
		ApiResponse respon = new ApiResponse();
		
		AccountingInclude userInclude = accountingIncludeRepository.findById_AccountingIdAndId_UserIdAndIsActive(accountingId, userId, 1);
		if (userInclude == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "user"));
			return respon;
		}

		if(file == null || file.isEmpty()) {
			userInclude.setImgEvidence(null);
		} else {
			// Upload file
			List<UploadDTO> listUploadDTO = uploadService.uploadFiles(file, fileStoragePath, userId);
			uploadService.deleteByUploadId(userInclude.getImgEvidence());
			userInclude.setImgEvidence(listUploadDTO.get(0).getUploadId());
		}
		
		userInclude.setStatusId(new BigInteger(ConstantUtils.Accounting_Status.WAITING.getValue()));
		userInclude.setUpdatedDate(new Date());
		userInclude = accountingIncludeRepository.save(userInclude);
		
		respon.setMessage(messageUtils.getMessage("I023"));
		respon.setData(userInclude);
		return respon;
	}

	/**
	 * 
	 * @Des Confirm all member Paid Accounting
	 * @param AccountingDTO
	 */
	public ApiResponse confirmAllMemberPaid(AccountingDTO request, BigInteger userAdmin) throws Exception {
		ApiResponse respon = new ApiResponse();
		Accounting oldAccounting = accountingRepository.findByAccountingIdAndIsActive(request.getAccountingId(), 1);
		if (oldAccounting == null) { // oldAccounting not exist
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "accounting"));
			return respon;
		}
		
		// Get list userInclude Waiting
		List<AccountingInclude> listWaiting = accountingIncludeRepository.findById_AccountingIdAndStatusIdAndIsActive(request.getAccountingId(),
				new BigInteger(ConstantUtils.Accounting_Status.WAITING.getValue()), 1);
		if (listWaiting != null && !listWaiting.isEmpty()) {
			for (AccountingInclude userWaiting : listWaiting) {
				userWaiting.setStatusId(new BigInteger(ConstantUtils.Accounting_Status.DONE.getValue()));
				userWaiting.setConfirmBy(userAdmin);
				userWaiting.setUpdatedDate(new Date());
				userWaiting = accountingIncludeRepository.save(userWaiting);
			}
			respon.setMessage(messageUtils.getMessage("I024", "members"));
		} else {
			respon.setMessage(messageUtils.getMessage("E025"));
		}
		
		return respon;
	}
	
	/**
	 * 
	 * @Des Confirm user Paid Accounting
	 * @param AccountingDTO
	 */
	public ApiResponse confirmMemberPaid(AccountingDTO request, BigInteger userAdmin) throws Exception {
		ApiResponse respon = new ApiResponse();
		Accounting oldAccounting = accountingRepository.findByAccountingIdAndIsActive(request.getAccountingId(), 1);
		if (oldAccounting == null) { // oldAccounting not exist
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "accounting"));
			return respon;
		}
		
		// Get userInclude Confirm
		AccountingInclude userConfirm = accountingIncludeRepository
				.findById_AccountingIdAndId_UserIdAndIsActive(request.getAccountingId(), request.getUserId(), 1);
		if (userConfirm == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "user"));
			return respon;
		}
		
		// Check this user is Not_paid => return
		if (new BigInteger(ConstantUtils.Accounting_Status.NOT_PAID.getValue()).compareTo(userConfirm.getStatusId()) == 0) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E026"));
			return respon;
		} else { // is waiting or done
			userConfirm.setStatusId(new BigInteger(ConstantUtils.Accounting_Status.DONE.getValue()));
			userConfirm.setConfirmBy(userAdmin);
			userConfirm.setUpdatedDate(new Date());
			userConfirm = accountingIncludeRepository.save(userConfirm);
		}
		
		respon.setMessage(messageUtils.getMessage("I024", "member"));
		return respon;
	}

	/**
	 * 
	 * search accounting for admin
	 * @param search
	 * @return
	 * @throws ParseException 
	 */
	public ApiResponse searchAccountingAdmin(SearchDTO search, BigInteger userId) throws ParseException {
		
		String keyword = search.getKeyword();
		if(StringUtils.isEmpty(keyword)) {//full
			keyword = "%%";
		}else {//start with keywork
			keyword = "%" + keyword + "%";
		}
		if(search.getMaxResult() == 0) {
			search.setMaxResult(10);
		}
		
		ApiResponse respon = new ApiResponse();
		List<Map<String, Object>> listAccounting = new ArrayList<Map<String,Object>>();
		List<AccountingDTO> listAccountingDTO = new ArrayList<>();
		SearchAccountingDTO result = new SearchAccountingDTO();
		
		if (ConstantUtils.Accounting_Search_Type.YOUR_ACCOUNTING.getValue().equals(search.getSearchType())) {
			 listAccounting = accountingRepository.searchAccountingByUser(userId,
					search.getTeamId(), keyword, search.getFirstResult(), search.getMaxResult());
			
			if(listAccounting == null || listAccounting.isEmpty()) {
				respon.setData(listAccountingDTO);
				return respon;
			}
			
			for(Map<String, Object> accounting:listAccounting) {
				AccountingDTO accountingDTO = convertMapAccoungtingToDTO(accounting);
				accountingDTO.setUserId(userId);
				accountingDTO.setStatusPaid(new BigInteger(accounting.get("status_id").toString()));
				if (!StringUtils.isEmpty(accounting.get("img_evidence").toString())) {
					accountingDTO.setImgEvidence(new BigInteger(accounting.get("img_evidence").toString()));
				} else {
					accountingDTO.setImgEvidence(null);
				}
				listAccountingDTO.add(accountingDTO);
			}
			
			//get total
			result.setTotal(accountingRepository.searchCountAccountingByUser(userId, search.getTeamId(), keyword));
		} else { // team's accounting
			listAccounting = accountingRepository.searchAccountingByAdmin(
					new BigInteger(ConstantUtils.Accounting_Status.NOT_PAID.getValue()),
					new BigInteger(ConstantUtils.Accounting_Status.WAITING.getValue()),
					new BigInteger(ConstantUtils.Accounting_Status.DONE.getValue()), search.getTeamId(), keyword,
					search.getFirstResult(), search.getMaxResult());
			if(listAccounting == null || listAccounting.isEmpty()) {
				respon.setData(listAccountingDTO);
				return respon;
			}
			
			for(Map<String, Object> accounting:listAccounting) {
				AccountingDTO accountingDTO = convertMapAccoungtingToDTO(accounting);
				int totalMembersInclude = Integer.parseInt(accounting.get("members_include").toString());
				int totalMembersNotPaid = Integer.parseInt(accounting.get("members_not_paid").toString());
				
				accountingDTO.setTotalMemberInclude(totalMembersInclude);
				accountingDTO.setTotalMemberNotPaid(totalMembersNotPaid);
				accountingDTO.setTotalMemberWaiting(Integer.parseInt(accounting.get("members_waiting").toString()));
				accountingDTO.setTotalMemberDone(Integer.parseInt(accounting.get("members_done").toString()));
				accountingDTO.setTotalMemberPaid(totalMembersInclude - totalMembersNotPaid);
				listAccountingDTO.add(accountingDTO);
			}
			
			//get total
			result.setTotal(accountingRepository.searchCountAccountingByAdmin(search.getTeamId(), keyword));
		}
		
		respon.setData(result);
		result.setAccountings(listAccountingDTO);
		
		return respon;
	}
	
	/**
	 * 
	 * search accounting for user
	 * @param search
	 * @return
	 * @throws ParseException 
	 */
	public ApiResponse searchAccountingUser(SearchDTO search, BigInteger userId) throws ParseException {
		
		String keyword = search.getKeyword();
		if(StringUtils.isEmpty(keyword)) {//full
			keyword = "%%";
		}else {//start with keywork
			keyword = "%" + keyword + "%";
		}
		if(search.getMaxResult() == 0) {
			search.setMaxResult(10);
		}
		
		ApiResponse respon = new ApiResponse();
		List<Map<String, Object>> listAccounting = accountingRepository.searchAccountingByUser(userId,
				search.getTeamId(), keyword, search.getFirstResult(), search.getMaxResult());

		SearchAccountingDTO result = new SearchAccountingDTO();
		List<AccountingDTO> listAccountingDTO = new ArrayList<>();
		if(listAccounting == null || listAccounting.isEmpty()) {
			respon.setData(listAccountingDTO);
			return respon;
		}
		
		for(Map<String, Object> accounting:listAccounting) {
			AccountingDTO accountingDTO = convertMapAccoungtingToDTO(accounting);
			accountingDTO.setUserId(userId);
			accountingDTO.setStatusPaid(new BigInteger(accounting.get("status_id").toString()));
			if (!StringUtils.isEmpty(accounting.get("img_evidence").toString())) {
				accountingDTO.setImgEvidence(new BigInteger(accounting.get("img_evidence").toString()));
			} else {
				accountingDTO.setImgEvidence(null);
			}
			listAccountingDTO.add(accountingDTO);
		}
		
		//get total
		result.setTotal(accountingRepository.searchCountAccountingByUser(userId, search.getTeamId(), keyword));
		result.setAccountings(listAccountingDTO);
		respon.setData(result);
		
		return respon;
	}
	
	/**
	 * 
	 * @Des convert Map<String, Object> Accounting to accountingDTO
	 * @param Map<String, Object> Accounting
	 * @return
	 * @throws ParseException 
	 */
	private AccountingDTO convertMapAccoungtingToDTO(Map<String, Object> accounting) throws ParseException {
		AccountingDTO accountingDTO = new AccountingDTO();
		
		accountingDTO.setAccountingId(new BigInteger(accounting.get("accounting_id").toString()));
		accountingDTO.setTeamId(new BigInteger(accounting.get("team_id").toString()));
		if (!StringUtils.isEmpty(accounting.get("accounting_deadline").toString())) {
			accountingDTO.setDateDeadline(DateUtil.getFormatDate(
					accounting.get("accounting_deadline").toString(), DateUtil.PT_YYYY_MM_DD_HH_MM_SS));
		}
		//Compare date deadline expired
		if (DateUtil.compareToDatimeNow(DateUtil.getFormatDate(
				accounting.get("accounting_deadline").toString(), DateUtil.PT_YYYY_MM_DD_HH_MM_SS)) < 0 ) {
			accountingDTO.setIsExpired(true); // isExpired
		} else {
			accountingDTO.setIsExpired(false);
		}
		accountingDTO.setTitle(accounting.get("accounting_title").toString());
		accountingDTO.setAmount(accounting.get("accounting_amount").toString());
		accountingDTO.setNotice(accounting.get("accounting_notice").toString());
		
		return accountingDTO;
	}
}
