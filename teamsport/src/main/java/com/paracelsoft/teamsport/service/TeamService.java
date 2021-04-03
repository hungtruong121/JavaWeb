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

import com.paracelsoft.teamsport.api.dto.InviteDTO;
import com.paracelsoft.teamsport.api.dto.RequestDTO;
import com.paracelsoft.teamsport.api.dto.SearchDTO;
import com.paracelsoft.teamsport.api.dto.SearchResultUserDTO;
import com.paracelsoft.teamsport.api.dto.SearchTeamDTO;
import com.paracelsoft.teamsport.api.dto.StatusDTO;
import com.paracelsoft.teamsport.api.dto.TeamDTO;
import com.paracelsoft.teamsport.api.dto.UserDTO;
import com.paracelsoft.teamsport.api.dto.UserRequestDTO;
import com.paracelsoft.teamsport.api.dto.UserRoleDTO;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.entity.Sport;
import com.paracelsoft.teamsport.entity.Team;
import com.paracelsoft.teamsport.entity.TeamMember;
import com.paracelsoft.teamsport.entity.TeamMemberPK;
import com.paracelsoft.teamsport.entity.TeamMemberStatus;
import com.paracelsoft.teamsport.entity.TeamMemberStatusPK;
import com.paracelsoft.teamsport.entity.TeamRank;
import com.paracelsoft.teamsport.entity.User;
import com.paracelsoft.teamsport.repository.PostRepository;
import com.paracelsoft.teamsport.repository.SportRepository;
import com.paracelsoft.teamsport.repository.TeamMemberRepository;
import com.paracelsoft.teamsport.repository.TeamMemberStatusRepository;
import com.paracelsoft.teamsport.repository.TeamRankRepository;
import com.paracelsoft.teamsport.repository.TeamRepository;
import com.paracelsoft.teamsport.repository.UserRepository;
import com.paracelsoft.teamsport.util.ConstantUtils;
import com.paracelsoft.teamsport.util.MessageUtils;
import com.paracelsoft.teamsport.util.ReponseCode;

@Transactional(rollbackFor = { Exception.class, ParseException.class })
@Service("teamService")
public class TeamService {
	
	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	TeamRepository teamRepository;

	@Autowired
	TeamMemberRepository teamMemberRepository;

	@Autowired
	TeamMemberStatusRepository teamMemberStatusRepository;

	@Autowired
	PostRepository postRepository;

	@Autowired
	MessageUtils messageUtils;
	
	@Autowired
	UploadService uploadService;
	
	@Autowired
	TeamRankRepository teamRankRepository;
	
	@Autowired
	SportRepository sportRepository;
	
	@Autowired
	NotificationService notificationService;
	
	@Autowired
	MailService mailService;

	/**
	 * 
	 * @param teamId
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	public ApiResponse searchUserTeam(BigInteger teamId, String keyword) throws Exception {
		
		if(StringUtils.isEmpty(keyword)) {//full
			keyword = "%%";
		}else {//start with keywork
			keyword = "%" + keyword + "%";
		}
		
		ApiResponse respon = new ApiResponse();
		List<Map<String, Object>> listUser = userRepository.searchByTeamId(teamId, keyword);
		
		List<UserDTO> userDTO = new ArrayList<>();
		if(listUser == null || listUser.isEmpty()) {
			respon.setData(userDTO);
			return respon;
		}
		
		for(Map<String, Object> user:listUser) {
			UserDTO dto = new UserDTO();
			dto.setUserId(new BigInteger(user.get("user_id").toString()));
			dto.setUserFullName(user.get("user_full_name").toString());
			dto.setUserAvatar(new BigInteger(user.get("user_avatar").toString()));
			dto.setPositionSport(user.get("position_sport").toString());
			userDTO.add(dto);
		}
		respon.setData(userDTO);
		
		return respon;
	}

	/**
	 * 
	 * search user page
	 * @param search
	 * @return
	 */
	public ApiResponse searchUserPageTeam(SearchDTO search) {
		
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
		List<Map<String, Object>> listUser = userRepository.searchPageByTeamId(search.getTeamId(), keyword, search.getFirstResult(), search.getMaxResult());
		
		SearchResultUserDTO result = new SearchResultUserDTO();
		List<UserDTO> userDTO = new ArrayList<>();
		if(listUser == null || listUser.isEmpty()) {
			respon.setData(userDTO);
			return respon;
		}
		
		for(Map<String, Object> user:listUser) {
			UserDTO dto = new UserDTO();
			dto.setUserId(new BigInteger(user.get("user_id").toString()));
			dto.setUserFullName(user.get("user_full_name").toString());
			dto.setUserAvatar(new BigInteger(user.get("user_avatar").toString()));
			dto.setPositionSport(user.get("position_sport").toString());
			userDTO.add(dto);
		}
		
		//get total
		result.setTotal(userRepository.searchCountPageByTeamId(search.getTeamId(), keyword));
		result.setUsers(userDTO);
		respon.setData(result);
		
		return respon;
	}
	
	/**
	 * 
	 * get Team Info
	 * 
	 * @param teamId,userId
	 * @return
	 */
	public ApiResponse getTeamInfo(BigInteger teamId, BigInteger userId) throws Exception {
		ApiResponse respon = new ApiResponse();
		
		// Team info (avatar,team name,slogan,about us,sport_name,national)
		Team teamInfo = teamRepository.findByTeamIdAndIsActive(teamId, 1);
		TeamDTO teamDTO = convertToDTO(teamInfo);
		if (teamInfo == null) {
			respon.setSuccess(false);
			respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
			respon.setMessage(messageUtils.getMessage("E001", "team"));
			return respon;
		}
		List<UserDTO> listUserMember = new ArrayList<UserDTO>();
		List<UserDTO> listUserAdmin = new ArrayList<UserDTO>();
		
		List<Map<String, Object>> listMember = teamMemberRepository.findMemberTeam(teamId,1);
		for (Map<String, Object> obj : listMember) {
			
			UserDTO dto = new UserDTO();
			dto.setUserId(new BigInteger(obj.get("user_id").toString()));
			if(!StringUtils.isEmpty(obj.get("user_avatar").toString())) {
				dto.setUserAvatar(new BigInteger(obj.get("user_avatar").toString()));
			}
			dto.setUserFullName(obj.get("user_full_name").toString());
			//check role current user
			if(userId != null && userId.compareTo(new BigInteger(obj.get("user_id").toString())) == 0) {
				teamDTO.setTeamMemberRole(obj.get("team_member_role").toString());
			}
			if(ConstantUtils.Team_Member_Role.TEAM_ADMIN.getValue().equals(obj.get("team_member_role").toString())) {
				listUserAdmin.add(dto);
			}else {
				listUserMember.add(dto);
			}
		}
		teamDTO.setUserMember(listUserMember);
		teamDTO.setUserAdmin(listUserAdmin);
		
		// Check da join or follow team hay chưa?
		List<TeamMemberStatus> listTMS = teamMemberStatusRepository.findById_UserIdAndId_TeamIdAndIsActive(userId, teamId,1);
		
		if (listTMS != null) {
			List<StatusDTO> listStatusDTO = new ArrayList<StatusDTO>();
			for (TeamMemberStatus tms : listTMS) {
				StatusDTO statusDTO = new StatusDTO();
				if (ConstantUtils.Team_Member_Status.REQUESTED.getValue().equals(tms.getId().getStatusId().toString())) {
					statusDTO.setStatusId(new BigInteger(ConstantUtils.Team_Member_Status.REQUESTED.getValue()));
					statusDTO.setStatusName(ConstantUtils.Team_Member_Status.REQUESTED.getDescription());
				}else if (ConstantUtils.Team_Member_Status.FOLLOWED.getValue().equals(tms.getId().getStatusId().toString())) {
					statusDTO.setStatusId(new BigInteger(ConstantUtils.Team_Member_Status.FOLLOWED.getValue()));
					statusDTO.setStatusName(ConstantUtils.Team_Member_Status.FOLLOWED.getDescription());
				}else {
					statusDTO.setStatusId(new BigInteger(ConstantUtils.Team_Member_Status.INVITED.getValue()));
					statusDTO.setStatusName(ConstantUtils.Team_Member_Status.INVITED.getDescription());
				}
				listStatusDTO.add(statusDTO);
			}
			teamDTO.setTeamMemberStatus(listStatusDTO);
		}
		respon.setData(teamDTO);

		return respon;
	}
	
	/**
	 * 
	 * get user request join team
	 * 
	 * @param teamId
	 * @return
	 */
	public ApiResponse requestJoinTeam(BigInteger teamId, BigInteger userId) throws NoSuchMessageException, JSONException {
		ApiResponse respon = new ApiResponse();
		
		Team team = teamRepository.findByTeamIdAndIsActive(teamId, 1);
		
		TeamMemberStatusPK tmsPK = new TeamMemberStatusPK();
		tmsPK.setTeamId(teamId);
		tmsPK.setUserId(userId);
		tmsPK.setStatusId(new BigInteger(ConstantUtils.Team_Member_Status.REQUESTED.getValue()));
		
		TeamMemberStatus tms = new TeamMemberStatus();
		tms.setCreatedby(team.getCreatedby());
		tms.setId(tmsPK);
		tms.setCreatedDate(new Date());
		tms.setUpdatedDate(new Date());
		
		teamMemberStatusRepository.save(tms);
		
		User user = userRepository.findByUserIdAndIsActive(userId, 1);
		if(user != null) {
			// get all admin of team
			List<User> teamAdmins = userRepository.getAllAdmin(teamId, ConstantUtils.Team_Member_Role.TEAM_ADMIN.getValue());
			//save notification to all admin of team
			notificationService.notificationFromUserToTeam(user, teamAdmins, teamId, "N002",
					ConstantUtils.MobileScreen.TEAM_PROFILE_DETAIL.getDescription(),
					ConstantUtils.Notification_Type.NOTIFI_REQUEST_JOIN_TEAM.getValue());
		}

		return respon;
	}
	
	/**
	 * 
	 * @TODO: not used
	 * @Des get list request join team
	 * @param teamId
	 * @return
	 */
	public ApiResponse getRequestJoinTeam(BigInteger teamId) {
		ApiResponse respon = new ApiResponse();
		//
		List<UserRequestDTO> listUserRequestDTO = new ArrayList<UserRequestDTO>();

		// get List user request join team(avatar, full name,poistion)
		List<Map<String, Object>> listUser = userRepository.getListRequest(teamId,
				new BigInteger(ConstantUtils.Team_Member_Status.REQUESTED.getValue()), 1); // 2:REQUESTED
		if (listUser != null && !listUser.isEmpty()) {
			for (Map<String, Object> obj : listUser) {
				UserRequestDTO userRequestDTO = new UserRequestDTO();
				userRequestDTO.setUserId(new BigInteger(obj.get("user_id").toString()));
				userRequestDTO.setUserAvatar(new BigInteger(obj.get("user_avatar").toString()));
				userRequestDTO.setUserFullName(obj.get("user_full_name").toString());
				userRequestDTO.setPositionSport(obj.get("position_sport").toString());

				listUserRequestDTO.add(userRequestDTO);
			}
		}
		respon.setData(listUserRequestDTO);
		return respon;
	}

	/**
	 * 
	 * get list follow team
	 * 
	 * @param teamId
	 * @return
	 */
	public ApiResponse setFollowTeam(BigInteger teamId,BigInteger userId) {
		ApiResponse respon = new ApiResponse();
		
		Team team = teamRepository.findByTeamIdAndIsActive(teamId, 1);
		
		TeamMemberStatusPK tmsPK = new TeamMemberStatusPK();
		tmsPK.setTeamId(teamId);
		tmsPK.setUserId(userId);
		tmsPK.setStatusId(new BigInteger(ConstantUtils.Team_Member_Status.FOLLOWED.getValue()));
		
		TeamMemberStatus tms = new TeamMemberStatus();
		tms.setCreatedby(team.getCreatedby());
		tms.setId(tmsPK);
		tms.setCreatedDate(new Date());
		tms.setUpdatedDate(new Date());
		
		teamMemberStatusRepository.save(tms);
		return respon;
	}

	/**
	 * 
	 * get list invited team
	 * 
	 * @param teamId
	 * @return
	 */
	public ApiResponse getListInvited(BigInteger teamId) {
		ApiResponse respon = new ApiResponse();
		//
		List<UserRequestDTO> listUserRequestDTO = new ArrayList<UserRequestDTO>();

		// get List user request join team(avatar, full name,poistion)
		List<Map<String, Object>> listUser = userRepository.getListRequest(teamId,
				new BigInteger(ConstantUtils.Team_Member_Status.INVITED.getValue()), 1); // 4:INVITED
		if (listUser != null && !listUser.isEmpty()) {
			for (Map<String, Object> obj : listUser) {
				UserRequestDTO userRequestDTO = new UserRequestDTO();
				userRequestDTO.setUserId(new BigInteger(obj.get("user_id").toString()));
				userRequestDTO.setUserAvatar(new BigInteger(obj.get("user_avatar").toString()));
				userRequestDTO.setUserFullName(obj.get("user_full_name").toString());
				userRequestDTO.setPositionSport(obj.get("position_sport").toString());
				if(!StringUtils.isEmpty(obj.get("created_by").toString())) {
					userRequestDTO.setInvitedBy(new BigInteger(obj.get("created_by").toString()));
					userRequestDTO.setInvitedByName(obj.get("invitedName").toString());
				}
				listUserRequestDTO.add(userRequestDTO);
			}
		}
		respon.setData(listUserRequestDTO);
		return respon;
	}

	/**
	 * 
	 * @param uploadId
	 * @param teamId
	 * @return
	 */
	public ApiResponse updateAvatar(BigInteger uploadId,BigInteger teamId) {
		ApiResponse respon = new ApiResponse();
		//get Team by TeamId
		Team team = teamRepository.findByTeamIdAndIsActive(teamId, 1);
		if(team == null) {
			respon.setSuccess(false);
			respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
			respon.setMessage(messageUtils.getMessage("E001","team"));
			return respon;
		}
		
		//del old avatar
		uploadService.deleteByUploadId(team.getTeamAvatar());
		
		team.setTeamAvatar(uploadId); //set new Avatar
		team.setUpdatedDate(new Date());
		team = teamRepository.save(team);
		return respon;
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	public TeamDTO convertToDTO(Team team) {
		TeamDTO dto = new TeamDTO();
		dto.setTeamId(team.getTeamId());
		dto.setTeamName(team.getTeamName());
		dto.setTeamShortName(team.getTeamShortName());
		dto.setTeamAvatar(team.getTeamAvatar());
		if(team.getSportId() != null) {
			Sport sport = sportRepository.findBySportIdAndIsActive(team.getSportId(), 1);
			if(sport != null) {
				dto.setSportId(sport.getSportId());
				dto.setSportName(sport.getSportName());
			}
		}
		dto.setTeamSlogan(team.getTeamSlogan());
		dto.setTeamAddress(team.getTeamAddress());
		dto.setTeamNational(team.getTeamNational());
		dto.setTeamMail(team.getTeamMail());
		dto.setTeamDescription(team.getTeamDescription());
		//team rank avatar
		if(team.getTeamRankId() != null) {
			TeamRank teamRank = teamRankRepository.findByTeamRankIdAndIsActive(team.getTeamRankId(), 1);
			if(teamRank != null) {
				dto.setTeamRankId(team.getTeamRankId());
				dto.setTeamRankName(teamRank.getTeamRankName());
				dto.setTeamRankAvatar(teamRank.getTeamRankAvatar());
			}
		}
		dto.setIsActive(team.getIsActive());
		dto.setCreatedDate(team.getCreatedDate());
		dto.setUpdatedDate(team.getUpdatedDate());
		return dto;
	}
	
	/**
	 * 
	 * @param teamDTO
	 * @param userId
	 * @param teamRankId
	 * @param isActive
	 * @param createdDate
	 * @param updateDate
	 * @param privacyId
	 * @return
	 */
	public Team convertToEntity(TeamDTO teamDTO,BigInteger userId,BigInteger teamRankId,int isActive,Date createdDate,Date updateDate,BigInteger privacyId) {
		Team team = new Team();
		if (teamDTO.getTeamId() != null) {
			team.setTeamId(teamDTO.getTeamId());
		}else {
			team.setTeamId(null);
		}
		team.setTeamAvatar(teamDTO.getTeamAvatar());
		team.setTeamShortName(teamDTO.getTeamShortName());
		team.setSportId(teamDTO.getSportId());
		team.setTeamName(teamDTO.getTeamName());
		team.setTeamSlogan(teamDTO.getTeamSlogan());
		team.setTeamAddress(teamDTO.getTeamAddress());
		team.setTeamNational(teamDTO.getTeamNational());
		team.setTeamMail(teamDTO.getTeamMail());
		team.setTeamDescription(teamDTO.getTeamDescription());
		team.setTeamRankId(teamRankId);
		team.setCreatedby(userId);
		team.setIsActive(isActive);
		team.setCreatedDate(createdDate);
		team.setUpdatedDate(updateDate);
		team.setPrivacyId(privacyId);
		
		return team;
	}
	
	/**
	 * @author TaoN
	 * Name: Send email to invite member
	 * @des If member doesn't have in the system then the user able to invite by email address
	 * @param inviteDTO, userId
	 * @return
	 */
	
	public ApiResponse sendEmailInvite(InviteDTO inviteDTO, BigInteger userId) {
		ApiResponse respon = new ApiResponse();
		
		for(String emailInvite:inviteDTO.getEmailInvite()) {
			 List<Map<String, Object>> contentEmail = teamRepository.listInfoContentEmailByTeamIdandUserId(inviteDTO.getTeamId(), userId);
			 
			 if (contentEmail != null && !contentEmail.isEmpty()) {
				 
				 for (Map<String, Object> obj : contentEmail) {
						String teamName = obj.get("team_name").toString();
						String sportName = obj.get("sport_name").toString();
						String nameUserInvite = obj.get("user_full_name").toString();
						
						String linkApp = inviteDTO.getTeamId().toString();
						String mailContent[] = {teamName, sportName, nameUserInvite, linkApp};
						
						mailService.sendMailInviteJoinTeam(emailInvite, mailContent);
						
					}
			 }
		}
		
		respon.setMessage(messageUtils.getMessage("I018"));
		return respon;
	}
	
	/**
	 * @author TaoN
	 * Name: Get List Invite Team with status is Requested
	 * @des get List user request join team(avatar, full name, team has joined, sport name of that team)
	 * @param teamId
	 * @return
	 */
	public ApiResponse listRequests(BigInteger teamId) {
		ApiResponse respon = new ApiResponse();

		List<InviteDTO> listUserInviteTeam = new ArrayList<InviteDTO>();

		List<Map<String, Object>> listUserInvited = userRepository.getListInviteTeam(teamId,
				new BigInteger(ConstantUtils.Team_Member_Status.REQUESTED.getValue()), 1); // 1:REQUESTED
		
		if (listUserInvited != null && !listUserInvited.isEmpty()) {
			
			for (Map<String, Object> obj : listUserInvited) {
			
				InviteDTO inviteDTO = new InviteDTO();
				
				inviteDTO.setUserId(new BigInteger(obj.get("user_id").toString()));
				inviteDTO.setUserAvatar(new BigInteger(obj.get("user_avatar").toString()));
				inviteDTO.setUserFullName(obj.get("user_full_name").toString());
				inviteDTO.setTeamJoined(obj.get("team_joined").toString());
				inviteDTO.setSportName(obj.get("sport_name").toString());

				listUserInviteTeam.add(inviteDTO);
			}
		}
		respon.setData(listUserInviteTeam);
		return respon;
	}

	
	
	/**
	 * @author TaoN
	 * @param teamId, AdminId, InviteDTO
	 * @return
	 * @throws JSONException 
	 * @throws NoSuchMessageException 
	 */
	public ApiResponse approachJoinTeam(InviteDTO inviteDTO, BigInteger currentUserId) throws NoSuchMessageException, JSONException {

		ApiResponse respon = new ApiResponse();
		
		String roleOfCurrentUser = teamRepository.roleMemberInTeam(inviteDTO.getTeamId(), currentUserId);
		
		if (roleOfCurrentUser == null || roleOfCurrentUser.isEmpty() ) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
			return respon;
		}
			
		if (!ConstantUtils.Team_Member_Role.TEAM_ADMIN.getValue().equals(roleOfCurrentUser)) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E018")); 
			respon.setData(new ArrayList<>());
            return respon;
            
		}
		
		TeamMemberStatus teammberStatus = teamMemberStatusRepository.
				findById_UserIdAndId_TeamIdAndId_StatusIdAndIsActive(inviteDTO.getUserId(), inviteDTO.getTeamId(), new BigInteger(ConstantUtils.Team_Member_Status.REQUESTED.getValue()), 1);
		
		if(teammberStatus == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E014"));
			return respon;
			
		}
		
		Integer maxMemberOfRank = teamRepository.maxMembershipOfRank(inviteDTO.getTeamId());
		Integer membership = teamRepository.countMembersInTeam(inviteDTO.getTeamId());
		
		if (membership >= maxMemberOfRank) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E019"));
			return respon;
			
		}
		
		insertTeamMember(inviteDTO, currentUserId);
		teamMemberStatusRepository.delete(teammberStatus);//delete invite
		respon.setMessage(messageUtils.getMessage("I014"));
		//mess notification approve team 
		notificationService.notificationFromTeamToUser(inviteDTO.getTeamId(), inviteDTO.getUserId(), currentUserId, "N010",
				ConstantUtils.MobileScreen.TEAM_PROFILE_DETAIL.getDescription(),
				ConstantUtils.Notification_Type.NOTIFI_MESS.getValue());
		return respon;
	}
	
	/**
	 * 
	 * @param teamNew
	 */
	public void insertTeamMember(InviteDTO inviteDTO, BigInteger currentUserId) {
		
		TeamMember newMember = new TeamMember();
		TeamMemberPK pk = new TeamMemberPK();
		
		pk.setTeamId(inviteDTO.getTeamId());
		pk.setUserId(inviteDTO.getUserId());
		
		newMember.setId(pk);
		newMember.setIsActive(1);
		newMember.setPositionSport("");
		newMember.setTeamMemberRole(ConstantUtils.Team_Member_Role.TEAM_MEMBER.getValue());
		newMember.setCreatedby(currentUserId);
		newMember.setCreatedDate(new Date());
		newMember.setUpdatedDate(new Date());
		
		teamMemberRepository.save(newMember);
	}
	
	/**
	 * @author TaoN
	 * @param teamId, AdminId, userId
	 * @return
	 * @throws JSONException 
	 * @throws NoSuchMessageException 
	 */
	public ApiResponse rejectJoinTeam(InviteDTO invite, BigInteger currentUserId) throws NoSuchMessageException, JSONException {
		ApiResponse respon = new ApiResponse();
		String roleOfCurrentUser = teamRepository.roleMemberInTeam(invite.getTeamId(), currentUserId);
		
		if (!ConstantUtils.Team_Member_Role.TEAM_ADMIN.getValue().equals(roleOfCurrentUser)) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E017")); 
			respon.setData(new ArrayList<>());
            return respon;
            
		}
		
		TeamMemberStatus teammberStatus = teamMemberStatusRepository.
				findById_UserIdAndId_TeamIdAndId_StatusIdAndIsActive(invite.getUserId(), invite.getTeamId(), new BigInteger(ConstantUtils.Team_Member_Status.REQUESTED.getValue()), 1);
		
		if(teammberStatus == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E014"));
			return respon;
		
		}
		
		teamMemberStatusRepository.delete(teammberStatus);//delete invite
		respon.setMessage(messageUtils.getMessage("I016"));
		//mess notification reject team 
		notificationService.notificationFromTeamToUser(invite.getTeamId(), invite.getUserId(), currentUserId, "N011",
				ConstantUtils.MobileScreen.TEAM_PROFILE_DETAIL.getDescription(),
				ConstantUtils.Notification_Type.NOTIFI_MESS.getValue());
		return respon;
	}
	
	/**
	 * 
	 * @Des list member screen
	 * search user page
	 * @param search
	 * @return
	 */
	public ApiResponse listUserPageTeam(SearchDTO search) {
		
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
		
		List<Map<String, Object>> listUser = userRepository.searchPageByTeamId(
				search.getTeamId(), keyword, search.getFirstResult(), search.getMaxResult());
		
		if(listUser == null || listUser.isEmpty()) {
			return respon;
		}
		
		UserRoleDTO result = new UserRoleDTO();
		List<UserDTO> admin = new ArrayList<UserDTO>();
		List<UserDTO> member = new ArrayList<UserDTO>();
		for(Map<String, Object> user:listUser) {
			UserDTO dto = new UserDTO();
			dto.setUserId(new BigInteger(user.get("user_id").toString()));
			dto.setUserFullName(user.get("user_full_name").toString());
			dto.setUserAvatar(new BigInteger(user.get("user_avatar").toString()));
			dto.setPositionSport(user.get("position_sport").toString());
			String teamMonberRole = user.get("team_member_role").toString();
			dto.setTeamMemberRole(teamMonberRole);
			if(!StringUtils.isEmpty(teamMonberRole) && ConstantUtils.Team_Member_Role.TEAM_ADMIN.getValue().equals(teamMonberRole)) {
				admin.add(dto);
			}else {
				member.add(dto);
			}
		}
		
		//get total
		result.setTotal(userRepository.searchCountPageByTeamId(search.getTeamId(), keyword));
		result.setTeamAdmin(admin);
		result.setTeamMember(member);
		respon.setData(result);
		return respon;
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @throws JSONException 
	 * @throws NoSuchMessageException 
	 */
	public ApiResponse kickMember(RequestDTO request, BigInteger userAdmin) throws NoSuchMessageException, JSONException {
		ApiResponse respon = new ApiResponse();
		//check user is Admin
		TeamMember teammemner = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(request.getTeamId(), userAdmin, 1);
		if(teammemner == null || !ConstantUtils.Team_Member_Role.TEAM_ADMIN.getValue().equals(teammemner.getTeamMemberRole())) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E015")); 
			respon.setData(new ArrayList<>());
            return respon;
		}
		//delete team meber 
		TeamMember teammemnerKick = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(request.getTeamId(), request.getUserId(), 1);
		if(teammemnerKick == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003")); 
			respon.setData(new ArrayList<>());
            return respon;
		}
		if(ConstantUtils.Team_Member_Role.TEAM_ADMIN.getValue().equals(teammemnerKick.getTeamMemberRole())) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E021")); 
			respon.setData(new ArrayList<>());
            return respon;
		}
		teamMemberRepository.delete(teammemnerKick);
		respon.setMessage(messageUtils.getMessage("I013"));
		//mess notification kick team
		notificationService.notificationFromTeamToUser(request.getTeamId(), request.getUserId(), userAdmin, "N009",
				ConstantUtils.MobileScreen.TEAM_PROFILE_DETAIL.getDescription(),
				ConstantUtils.Notification_Type.NOTIFI_MESS.getValue());
		return respon;
	}

	/**
	 * 
	 * @param teamId
	 * @return
	 */
	public ApiResponse getPositionSport(BigInteger teamId) {
		ApiResponse respon = new ApiResponse();
		List<String> position = teamRepository.getPositionSportAndIsActive(teamId, 1);
		respon.setData(position);
		return respon;
	}

	public ApiResponse changePosition(RequestDTO request, BigInteger userAdmin) {
		ApiResponse respon = new ApiResponse();
		//check user is Admin
		TeamMember teammemner = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(request.getTeamId(), userAdmin, 1);
		if(teammemner == null || !ConstantUtils.Team_Member_Role.TEAM_ADMIN.getValue().equals(teammemner.getTeamMemberRole())) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E015")); 
			respon.setData(new ArrayList<>());
            return respon;
		}
		//change team meber 
		TeamMember teammemnerChange = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(request.getTeamId(), request.getUserId(), 1);
		if(teammemnerChange == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003")); 
			respon.setData(new ArrayList<>());
            return respon;
		}
		//if change chinh minh thi dc
//		if(request.getUserId().compareTo(userAdmin) != 0
//				&& ConstantUtils.Team_Member_Role.TEAM_ADMIN.getValue().equals(teammemnerChange.getTeamMemberRole())) {
//			respon.setSuccess(false);
//			respon.setMessage(messageUtils.getMessage("E022")); 
//			respon.setData(new ArrayList<>());
//            return respon;
//		}
		teammemnerChange.setPositionSport(request.getPositionSport());
		teammemnerChange.setUpdatedDate(new Date());
		teamMemberRepository.save(teammemnerChange);
		respon.setMessage(messageUtils.getMessage("I013"));
		return respon;
	}

	public ApiResponse changeRoleMember(RequestDTO request, BigInteger userAdmin) throws NoSuchMessageException, JSONException {
		ApiResponse respon = new ApiResponse();
		//check user is Admin
		TeamMember teammemner = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(request.getTeamId(), userAdmin, 1);
		if(teammemner == null || !ConstantUtils.Team_Member_Role.TEAM_ADMIN.getValue().equals(teammemner.getTeamMemberRole())) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E015")); 
			respon.setData(new ArrayList<>());
            return respon;
		}
		//change team meber 
		TeamMember teammemnerChange = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(request.getTeamId(), request.getUserId(), 1);
		if(teammemnerChange == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003")); 
			respon.setData(new ArrayList<>());
            return respon;
		}
		//if change chinh minh thi dc
		if(request.getUserId().compareTo(userAdmin) != 0
				&& ConstantUtils.Team_Member_Role.TEAM_ADMIN.getValue().equals(teammemnerChange.getTeamMemberRole())) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E022")); 
			respon.setData(new ArrayList<>());
            return respon;
		}
		//da ton tai role nay
		if(!request.getTeamMemberRole().equals(teammemnerChange.getTeamMemberRole())) {
			teammemnerChange.setTeamMemberRole(request.getTeamMemberRole());
			teammemnerChange.setUpdatedDate(new Date());
			teamMemberRepository.save(teammemnerChange);
			
			//notification change role
			if(ConstantUtils.Team_Member_Role.TEAM_ADMIN.getValue().equals(request.getTeamMemberRole())) {
				notificationService.notificationFromTeamToUser(request.getTeamId(), request.getUserId(), userAdmin, "N012",
						ConstantUtils.MobileScreen.TEAM_PROFILE_DETAIL.getDescription(),
						ConstantUtils.Notification_Type.NOTIFI_MESS.getValue());
			}
		}
		respon.setMessage(messageUtils.getMessage("I013"));
		return respon;
	}

	public ApiResponse updateUserPosition(BigInteger teamId, BigInteger userId, String newPosition) {
		ApiResponse respon = new ApiResponse();
		TeamMember teamMember = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(teamId, userId, 1);
		if (teamMember != null) {
		}
		return respon;
	}
	
	/**
	 * 
	 * @param no
	 * @return List<TeamDTO>
	 */
	public List<TeamDTO> getListTeamDTO() {
		List<TeamDTO> result = new ArrayList<TeamDTO>();
		
		List<Team> listTeam = teamRepository.findAll();
		
		if(listTeam != null && !listTeam.isEmpty()) {
			for(Team team:listTeam) {
				TeamDTO dto = new TeamDTO();
				
				dto = this.convertToDTO(team);
				dto.setTotalMember(teamRepository.countMembersInTeam(team.getTeamId()));
				
				result.add(dto);
			}
		}
		return result;
	}

	/**
	 * 
	 * @param teamID
	 * @return Team Block/ Unblock
	 */
	public ApiResponse blockTeamByTeamId(BigInteger teamId) {
		ApiResponse respon = new ApiResponse();
		Team team = teamRepository.findByTeamId(teamId);
		
		if (team == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "team"));
			return respon;
		}
		
		if (team.getIsActive() == 0) {
			team.setIsActive(1); // Unblock team
			respon.setMessage(messageUtils.getMessage("I021", "team"));
		} else {
			team.setIsActive(0); // Block
			respon.setMessage(messageUtils.getMessage("I020", "team"));
		}

		team = teamRepository.save(team);
		respon.setData(team);
		return respon;
	}
	
	/**
	 * 
	 * @param teamID
	 * @return List<TeamDTO>
	 */
	public ApiResponse getUserInTeam(BigInteger teamId) {
		ApiResponse respon = new ApiResponse();
		List<TeamDTO> result = new ArrayList<TeamDTO>();
		
		List<TeamMember> listMember = teamMemberRepository.findById_TeamIdAndIsActive(teamId, 1);
		
		if(listMember != null && !listMember.isEmpty()) {
			for(TeamMember member:listMember) {
				TeamDTO dto = new TeamDTO();
				User user = userRepository.findByUserId(member.getId().getUserId()); // Get ca user Block
				
				if (user != null) {
					dto.setTeamId(teamId);
					dto.setUserId(member.getId().getUserId());
					dto.setUserFullName(user.getUserFullName());
					dto.setUserMail(user.getUserMail());
					dto.setUserIsActive(user.getIsActive());
					dto.setTeamMemberRole(member.getTeamMemberRole());
					dto.setMemberSince(member.getCreatedDate());
					dto.setPosition(member.getPositionSport());
					
					result.add(dto);
				}
			}
		}
		respon.setData(result);
		
		return respon;
	}

	
	/**
	 * 
	 * @param NO
	 * @return TeamDTO (listSport, listRank)
	 */
	public TeamDTO getListSportAndRanks() {
		TeamDTO result = new TeamDTO();
		
		result.setListSport(sportRepository.findByIsActive(1));
		result.setListRank(teamRankRepository.findByIsActive(1));
		
		return result;
	}
	
	/**
	 * 
	 * @param request
	 * @Des Change role member in team By Admin
	 * @throws JSONException 
	 * @throws NoSuchMessageException 
	 */
	public ApiResponse changeRoleMemberByAdmin(RequestDTO request, BigInteger userAdmin) throws NoSuchMessageException, JSONException {
		ApiResponse respon = new ApiResponse();
		
		TeamMember teammemberChange = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(request.getTeamId(), request.getUserId(), 1);
		if(teammemberChange == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003")); 
			respon.setData(new ArrayList<>());
            return respon;
		}
		
		// Check Current Role existed
		if(!request.getTeamMemberRole().equals(teammemberChange.getTeamMemberRole())) {
			teammemberChange.setTeamMemberRole(request.getTeamMemberRole());
			teammemberChange.setUpdatedDate(new Date());
			teamMemberRepository.save(teammemberChange);
			
			// notification change role
			if(ConstantUtils.Team_Member_Role.TEAM_ADMIN.getValue().equals(request.getTeamMemberRole())) {
				notificationService.notificationFromTeamToUser(request.getTeamId(), request.getUserId(), userAdmin, "N012",
						ConstantUtils.MobileScreen.TEAM_PROFILE_DETAIL.getDescription(),
						ConstantUtils.Notification_Type.NOTIFI_MESS.getValue());
			}
		}
		respon.setMessage(messageUtils.getMessage("I013"));
		return respon;
	}
	
	/**
	 * 
	 * @param request
	 * @Des Kick a member in team By Admin
	 * @throws JSONException 
	 * @throws NoSuchMessageException 
	 */
	public ApiResponse kickMemberByAdmin(RequestDTO request, BigInteger userAdmin) throws NoSuchMessageException, JSONException {
		ApiResponse respon = new ApiResponse();
		
		// delete teamMember 
		TeamMember teamMemberKick = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(request.getTeamId(), request.getUserId(), 1);
		if(teamMemberKick == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003")); 
			respon.setData(new ArrayList<>());
            return respon;
		}
		
		teamMemberRepository.delete(teamMemberKick);
		
		// Notification kick member in team
		notificationService.notificationFromTeamToUser(request.getTeamId(), request.getUserId(), userAdmin, "N009",
				ConstantUtils.MobileScreen.TEAM_PROFILE_DETAIL.getDescription(),
				ConstantUtils.Notification_Type.NOTIFI_MESS.getValue());
		
		respon.setMessage(messageUtils.getMessage("I013"));
		return respon;
	}

	/**
	 * 
	 * search team info
	 * @param search
	 * @return
	 */
	public ApiResponse searchTeamInfoByTeamName(SearchDTO search) {
		
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
		List<Map<String, Object>> listTeam = teamRepository.searchTeamInfoByTeamName(keyword, search.getFirstResult(), search.getMaxResult());
		
		SearchTeamDTO result = new SearchTeamDTO();
		List<TeamDTO> teamDTO = new ArrayList<>();
		if(listTeam == null || listTeam.isEmpty()) {
			respon.setData(teamDTO);
			return respon;
		}
		
		for(Map<String, Object> team:listTeam) {
			TeamDTO dto = new TeamDTO();
			
			dto.setTeamId(new BigInteger(team.get("team_id").toString()));
			dto.setTeamName(team.get("team_name").toString());
			dto.setTeamShortName(team.get("team_short_name").toString());
			dto.setTeamMail(team.get("team_mail").toString());
			dto.setSportId(new BigInteger(team.get("sport_id").toString()));
			dto.setTeamAvatar(new BigInteger(team.get("team_avatar").toString()));
			dto.setTeamNational(team.get("team_national").toString());
			dto.setTeamAddress(team.get("team_address").toString());
			dto.setTeamSlogan(team.get("team_slogan").toString());
			dto.setTeamDescription(team.get("team_description").toString());
			
			teamDTO.add(dto);
		}
		
		//get total
		result.setTotal(teamRepository.searchCountTeamInfoByTeamName(keyword));
		result.setTeams(teamDTO);
		respon.setData(result);
		
		return respon;
	}
}
