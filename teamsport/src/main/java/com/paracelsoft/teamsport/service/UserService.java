package com.paracelsoft.teamsport.service;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paracelsoft.teamsport.api.dto.PeriodMatchesDTO;
import com.paracelsoft.teamsport.api.dto.PostDTO;
import com.paracelsoft.teamsport.api.dto.RequestDTO;
import com.paracelsoft.teamsport.api.dto.TeamDTO;
import com.paracelsoft.teamsport.api.dto.TeamFollow;
import com.paracelsoft.teamsport.api.dto.TeamFollowDTO;
import com.paracelsoft.teamsport.api.dto.UserAchievementDTO;
import com.paracelsoft.teamsport.api.dto.UserDTO;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.entity.ActiveOtp;
import com.paracelsoft.teamsport.entity.EventMatchKendo;
import com.paracelsoft.teamsport.entity.Post;
import com.paracelsoft.teamsport.entity.PostIncludeUser;
import com.paracelsoft.teamsport.entity.Sport;
import com.paracelsoft.teamsport.entity.Team;
import com.paracelsoft.teamsport.entity.TeamMember;
import com.paracelsoft.teamsport.entity.TeamMemberPK;
import com.paracelsoft.teamsport.entity.TeamMemberStatus;
import com.paracelsoft.teamsport.entity.TeamMemberStatusPK;
import com.paracelsoft.teamsport.entity.User;
import com.paracelsoft.teamsport.entity.UserAchievement;
import com.paracelsoft.teamsport.entity.UserLogin;
import com.paracelsoft.teamsport.entity.UserRole;
import com.paracelsoft.teamsport.repository.EventKendoRepository;
import com.paracelsoft.teamsport.repository.EventMatchKendoRepository;
import com.paracelsoft.teamsport.repository.NotificationRepository;
import com.paracelsoft.teamsport.repository.PostCommentRepository;
import com.paracelsoft.teamsport.repository.PostIncludeUserRepository;
import com.paracelsoft.teamsport.repository.PostLikeRepository;
import com.paracelsoft.teamsport.repository.PostRepository;
import com.paracelsoft.teamsport.repository.SportRepository;
import com.paracelsoft.teamsport.repository.TeamMemberRepository;
import com.paracelsoft.teamsport.repository.TeamMemberStatusRepository;
import com.paracelsoft.teamsport.repository.TeamRepository;
import com.paracelsoft.teamsport.repository.UserAchievementRepository;
import com.paracelsoft.teamsport.repository.UserLoginRepository;
import com.paracelsoft.teamsport.repository.UserRepository;
import com.paracelsoft.teamsport.repository.UserRoleRepository;
import com.paracelsoft.teamsport.util.ConstantUtils;
import com.paracelsoft.teamsport.util.DateUtil;
import com.paracelsoft.teamsport.util.HashCrypt;
import com.paracelsoft.teamsport.util.MessageUtils;
import com.paracelsoft.teamsport.util.ReponseCode;

@Transactional(rollbackFor = { Exception.class, ParseException.class })
@Service("userService")
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	TeamRepository teamRepository;

	@Autowired
	TeamMemberRepository teamMemberRepository;

	@Autowired
	TeamMemberStatusRepository teamMemberStatusRepository;

	@Autowired
	MessageUtils messageUtils;

	@Autowired
	UserLoginRepository userLoginRepository;

	@Autowired
	UserRoleRepository userRoleRepository;

	@Autowired
	UserAchievementRepository userAchievementRepository;

	@Autowired
	PostIncludeUserRepository postIncludeUserRepository;

	@Autowired
	PostRepository postRepository;

	@Autowired
	PostLikeRepository postLikeRepository;

	@Autowired
	PostCommentRepository postCommentRepository;

	@Autowired
	MailService mailService;

	@Autowired
	AuthenOfbizService authenOfbizService;

	@Autowired
	UploadService uploadService;
	
	@Autowired
	PostService postService;
	
	@Autowired
	ActiveOTPService activeOTPService;
	
	@Autowired
	SportRepository sportRepository;
	
	@Autowired
	NotificationService notificationService;
	
	@Autowired
	FirebaseService firebaseService;
	
	@Autowired
	NotificationRepository notificationRepository;
	
	@Autowired
	EventKendoRepository eventKendoRepository;
	
	@Autowired
	EventMatchKendoRepository eventMatchKendoRepository;
	/**
	 * 
	 * @param userId
	 * @param teamDTO
	 */
	public ApiResponse createTeam(BigInteger userId, TeamDTO teamDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		// TODO: get and check current user Login
		if (teamDTO == null) {
			throw new Exception(messageUtils.getMessage("E008", "Team"));
		}

		Team team = new Team();
		Date date = new Date();
		if (teamDTO.getTeamId() != null) {
			Team oldTeam = teamRepository.findByTeamIdAndIsActive(teamDTO.getTeamId(), 1);
			if (oldTeam == null) {
				respon.setSuccess(false);
				respon.setMessage(messageUtils.getMessage("E001", "teamId"));
				return respon;
			}
			// update
			team = convertToEntity(teamDTO, userId, teamDTO.getTeamRankId(), oldTeam.getIsActive(),
					oldTeam.getCreatedDate(), date, teamDTO.getPrivacyId());
			team.setSportId(oldTeam.getSportId()); // khong thay doi sport id
			team.setTeamId(oldTeam.getTeamId());
			team = teamRepository.save(team);
			respon.setMessage(messageUtils.getMessage("I003", "team"));
		} else {
			// create new team
			team = convertToEntity(teamDTO, userId, new BigInteger(ConstantUtils.Team_Rank.BASE.getValue()), 1, date,
					date, new BigInteger(ConstantUtils.Privacy.PUBLIC.getValue()));
			team = teamRepository.save(team);
			// created admin member
			insertTeamMember(team);
			respon.setMessage(messageUtils.getMessage("I004", "team"));

		}
		respon.setData(team);
		return respon;
	}

	public ApiResponse getListTeamByUserId(BigInteger userId) throws Exception {
		ApiResponse respon = new ApiResponse();

		List<Map<String, Object>> listTeam = teamRepository.getListTeamByUser(userId, 1);
		List<TeamDTO> listTeamDTO = new ArrayList<TeamDTO>();
		if (listTeam != null && !listTeam.isEmpty()) {
			for (Map<String, Object> obj : listTeam) {
				TeamDTO dto = new TeamDTO();
				dto.setTeamId(new BigInteger(obj.get("team_id").toString()));
				dto.setTeamAvatar(new BigInteger(obj.get("team_avatar").toString()));
				dto.setTeamName(obj.get("team_name").toString());
				dto.setTeamMemberRole(obj.get("team_member_role").toString());
				dto.setTotalMember(teamRepository.countMembersInTeam(dto.getTeamId()));
				listTeamDTO.add(dto);
			}
		}
		respon.setData(listTeamDTO);

		return respon;
	}

	/**
	 * 
	 * @param bigInteger
	 * @param teamId
	 */
	public ApiResponse leaveTeam(BigInteger userId, BigInteger teamId) throws Exception {
		ApiResponse respon = new ApiResponse();
		
		User user = userRepository.findByUserIdAndIsActive(userId, 1);
		if(user == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "user"));
			return respon;
		}
		// return về user có thuộc team hay ko?
		TeamMember teamMember = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(teamId, userId, 1);
		if (teamMember == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E013"));
			return respon;
		}

		// get số Admin con lai có trong team tru thang user nay
		List<User> teamAdmins = userRepository.getAllAdminExcepUser(teamId, userId, ConstantUtils.Team_Member_Role.TEAM_ADMIN.getValue());

		// check user co phai admin dung nhat hay khong
		if (!isAdmin(teamMember.getTeamMemberRole()) || (teamAdmins != null && !teamAdmins.isEmpty()) ) {
			// delete record in Team Member
			teamMemberRepository.delete(teamMember);
			respon.setMessage(messageUtils.getMessage("I005"));
						
			//check co phai team la team active khong
			if(user.getActiveTeam().compareTo(teamId) == 0) {
				List<Map<String, Object>> listTeamJoined = teamRepository.getListTeamByUser(userId, 1);
				BigInteger newActiveTeamId = null;
				if(listTeamJoined != null && !listTeamJoined.isEmpty()) {
					newActiveTeamId = new BigInteger(listTeamJoined.get(0).get("team_id").toString());
					//change new active team
					user.setActiveTeam(newActiveTeamId);
				}else {
					user.setActiveTeam(null);
				}
				userRepository.save(user);
				TeamDTO teamDto = new TeamDTO();
				teamDto.setTeamId(newActiveTeamId);
				respon.setData(teamDto);
			}
			//save notification to all admin of team
			notificationService.notificationFromUserToTeam(user, teamAdmins, teamId, "N001",
					ConstantUtils.MobileScreen.TEAM_PROFILE_DETAIL.getDescription(),
					ConstantUtils.Notification_Type.NOTIFI_MESS.getValue());
		} else {
			respon.setSuccess(false);
			respon.setErrorCode(ReponseCode.INVALID_CODE.LAST_ADMIN.getCode());
			respon.setMessage(messageUtils.getMessage("E004"));// message user is latest admin
		}
		return respon;
	}

	private boolean isAdmin(String teamMemberRole) {
		return ConstantUtils.Team_Member_Role.TEAM_ADMIN.getValue().equals(teamMemberRole);
	}

	/**
	 * 
	 * @param bigInteger
	 * @param teamId
	 */
	public ApiResponse updateGroupActive(BigInteger userId, BigInteger teamId) throws Exception {
		ApiResponse respon = new ApiResponse();

		User user = userRepository.findByUserIdAndIsActive(userId, 1);

		// update group active of user
		user.setActiveTeam(teamId);
		user.setUpdatedDate(new Date());
		userRepository.save(user);
		respon.setMessage(messageUtils.getMessage("I003", "active"));
		return respon;
	}

	/**
	 * 
	 * @param userId
	 * @return ApiResponse with TeamFollowDTO
	 * @throws Exception
	 */
	public ApiResponse getTeamFollowByUserId(BigInteger userId) throws Exception {
		ApiResponse respon = new ApiResponse();

		TeamFollowDTO teamFollowDTO = new TeamFollowDTO();
		List<Team> listTeam = teamRepository.findByUserId(userId,
				new BigInteger(ConstantUtils.Team_Member_Status.FOLLOWED.getValue())); // 2:REQUESTED | 3:FOLLOWED

		if (listTeam != null) {
			List<TeamFollow> listTeamFollow = new ArrayList<TeamFollow>();

			for (Team team : listTeam) {
				TeamFollow teamFollow = new TeamFollow();

				teamFollow.setUserId(userId);
				teamFollow.setTeamId(team.getTeamId());
				teamFollow.setTeamName(team.getTeamName());
				teamFollow.setTeamShortName(team.getTeamShortName());
				if (team.getTeamAvatar() != null) {
					teamFollow.setTeamAvatar(team.getTeamAvatar());
				}
				teamFollow.setMembersInTeam(teamRepository.countMembersInTeam(team.getTeamId()));
				teamFollow.setFollowersInTeam(teamRepository.countMemberInTeamByStatus(team.getTeamId(),
						new BigInteger(ConstantUtils.Team_Member_Status.FOLLOWED.getValue())));

				listTeamFollow.add(teamFollow);
			}
			teamFollowDTO.setLisTeamFollow(listTeamFollow);
			teamFollowDTO.setTeamUserFollowing(listTeam.size());
		}

		respon.setData(teamFollowDTO);

		return respon;
	}

	/**
	 * @return Remove team_member_status
	 * @param userId, teamID
	 */
	public ApiResponse unFollowTeam(BigInteger teamId, BigInteger userId) throws Exception {
		ApiResponse respon = new ApiResponse();

		TeamMemberStatus team_member_status = teamMemberStatusRepository
				.findById_UserIdAndId_TeamIdAndId_StatusIdAndIsActive(userId, teamId,
						new BigInteger(ConstantUtils.Team_Member_Status.FOLLOWED.getValue()), 1);
		if (team_member_status == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "teamMemberStatus"));
			return respon;
		}

		// Remove team_member_status
		teamMemberStatusRepository.delete(team_member_status);
		respon.setMessage(messageUtils.getMessage("I007", "team"));
		return respon;
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	public UserDTO convertToDTO(User user) {
		UserDTO dto = new UserDTO();
		dto.setUserId(user.getUserId());
		dto.setActiveTeam(user.getActiveTeam());
		dto.setUserAddress(user.getUserAddress());
		dto.setUserNational(user.getUserNational());
		dto.setUserMail(user.getUserMail());
		dto.setRelativeId(user.getRelativeId());
		String birthDate = user.getUserBirthDate();
		if (!StringUtils.isEmpty(birthDate)) {
			dto.setUserAge(DateUtil.CalculatorAge(birthDate));
		}
		dto.setUserBirthDay(birthDate);
		dto.setUserGender(user.getUserGender());
		dto.setUserPhone(user.getUserPhone());
		dto.setUserHeight(user.getUserHeight());
		dto.setUserWeight(user.getUserWeight());
		dto.setUserPreferredFoot(user.getUserPreferredFoot());
		dto.setUserPreferredHand(user.getUserPreferredHand());
		dto.setUserClass(user.getUserClass());
		dto.setUserDan(user.getUserDan());
		dto.setUserFullName(user.getUserFullName());
		dto.setUnReadNotify(notificationRepository.countByTeamIdAndNotificationToIdAndIsReadAndIsActive(user.getActiveTeam(), user.getUserId(), 0, 1));
		dto.setUserShortIntroduction(user.getUserShortIntroduction());
		dto.setUserAvatar(user.getUserAvatar() != null ? user.getUserAvatar() : new BigInteger("1"));
		List<UserAchievement> listUserAchievement = userAchievementRepository.findByUserIdAndIsActive(user.getUserId(), 1);
		if(listUserAchievement != null && !listUserAchievement.isEmpty()) {
			List<UserAchievementDTO> listAchiDto = new ArrayList<UserAchievementDTO>();
			for(UserAchievement achi: listUserAchievement) {
				UserAchievementDTO dtoAchi = new UserAchievementDTO();
				dtoAchi.setUserAchievementId(achi.getUserAchievementId());
				dtoAchi.setUserAchievementName(achi.getUserAchievementName());
				dtoAchi.setUserAchievementSport(achi.getUserAchievementSport());
				dtoAchi.setUserAchievementTime(achi.getUserAchievementTime());
				dtoAchi.setCreatedDate(achi.getCreatedDate());
				dtoAchi.setUpdatedDate(achi.getUpdatedDate());
				listAchiDto.add(dtoAchi);
			}
			dto.setUserAchievements(listAchiDto);
		}
		
		return dto;
	}

	/**
	 * 
	 * @param userId
	 * @param privacyId
	 * @return
	 */
	public ApiResponse updatePrivacyActive(BigInteger userId, BigInteger privacyId) {
		ApiResponse respon = new ApiResponse();

		User user = userRepository.findByUserIdAndIsActive(userId, 1);
		if (user == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "user"));
			return respon;
		}
		// update group active of user
		user.setIsActivePrivacy(privacyId);
		user.setUpdatedDate(new Date());
		userRepository.save(user);

		return respon;
	}

	/**
	 * 
	 * @param teamNew
	 */
	public void insertTeamMember(Team teamNew) {
		TeamMemberPK teamPK = new TeamMemberPK();
		teamPK.setTeamId(teamNew.getTeamId());
		teamPK.setUserId(teamNew.getCreatedby());

		TeamMember teamMember = new TeamMember();
		teamMember.setId(teamPK);
		teamMember.setTeamMemberRole(ConstantUtils.Team_Member_Role.TEAM_ADMIN.getValue());
		teamMember.setTeamRankId(teamNew.getTeamRankId());
		teamMember.setCreatedby(teamNew.getCreatedby());
		teamMember.setIsActive(1);
		teamMember.setCreatedDate(new Date());
		teamMember.setUpdatedDate(new Date());

		teamMemberRepository.save(teamMember);
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
	public Team convertToEntity(TeamDTO teamDTO, BigInteger userId, BigInteger teamRankId, int isActive,
			Date createdDate, Date updateDate, BigInteger privacyId) {
		Team team = new Team();
		team.setTeamId(null);
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
	 * 
	 * @param host
	 * @param userDTO
	 * @return
	 * @throws Exception
	 */
	public ApiResponse createUser(HttpServletRequest host, UserDTO userDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		// create party
		if (userDTO == null) { // check xem DTO truyen len co hop le hay khong
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "user"));
			return respon;
		}
		
		if (StringUtils.isEmpty(userDTO.getUserFullName())
				|| StringUtils.isEmpty(userDTO.getUserMail())
				|| StringUtils.isEmpty(userDTO.getPassword())) {
			respon.setSuccess(false);
			respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
			return respon;
		}			
		//check email is exist
		List<User> oldUsers = userRepository.findAllByUserMail(userDTO.getUserMail()); //truong hop check dang ki, trang thai nao cung phai check
		if(oldUsers != null && !oldUsers.isEmpty()) {
			if(Integer.parseInt(ConstantUtils.Is_Active.BLOCK.getValue()) == oldUsers.get(0).getIsActive()) {
				respon.setMessage(messageUtils.getMessage("E008", "user"));
			}else if(Integer.parseInt(ConstantUtils.Is_Active.NO_ACTIVE.getValue()) == oldUsers.get(0).getIsActive()) {
				respon.setMessage(messageUtils.getMessage("E009", "user"));
			}else {
				respon.setMessage(messageUtils.getMessage("E005", "user"));
			}
			respon.setSuccess(false);
			return respon;
		}
		
		// create new user
		User user = this.createUser(userDTO, new BigInteger(ConstantUtils.Privacy.PUBLIC.getValue()), 2,
				new BigInteger(ConstantUtils.ROLE_TYPE_ID.CUSTOMER.getValue()), new Date(), new Date());
		// if create new user success then create user login
		if (user != null) {
			user = userRepository.save(user);
			if (this.generateUserLogin(userDTO.getUserMail(), userDTO.getPassword(),
					user.getUserId()) == null) {
				respon.setMessage(messageUtils.getMessage("E003"));
				respon.setSuccess(false);
				return respon;
			}
		}
		
		// toi cho nay la tao thanh cong roi,
		// send mail cho nguoi dung nhe

		if (StringUtils.isEmpty(userDTO.getUserPhone())) { // Mobile
			String mailContent = host.getScheme() +"://"+host.getServerName()+":"+host.getServerPort()+host.getContextPath()+"/api/v1/user/active?email="+user.getUserMail()+"&otp=" + activeOTPService.generateOTP(user.getUserMail()).getActiveOtpCode();
			mailService.sendMailActiveAccount(user.getUserMail(), mailContent);
			respon.setMessage(messageUtils.getMessage("I010"));
			respon.setData(userDTO);

			return respon;
		} else { // Webadmin
			this.updateUserActive(user.getUserMail(), activeOTPService.generateOTP(user.getUserMail()).getActiveOtpCode());
			respon.setData(userDTO);

			return respon;
		}
		
	}
	
	/**
	 * 
	 * @param host
	 * @param userDTO
	 * @return
	 * @throws Exception
	 */
	public ApiResponse createRelativeUser(HttpServletRequest host, UserDTO userDTO,BigInteger userId) throws Exception {
		ApiResponse respon = new ApiResponse();
		// create party
		if (userDTO == null) { // check xem DTO truyen len co hop le hay khong
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "user"));
			return respon;
		}
		
		if (StringUtils.isEmpty(userDTO.getUserFullName())) {
			respon.setSuccess(false);
			respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
			return respon;
		}
		User parentUser = userRepository.findByUserIdAndIsActive(userId, 1);
		if(parentUser == null ) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001","user"));
			return respon;
		}
		List<User> listUser = userRepository.findByRelativeIdAndIsActive(userId, 1);
		if(listUser != null && !listUser.isEmpty()) {
			for(User obj : listUser) {
				if(userDTO.getUserFullName().equals(obj.getUserFullName()) && userId.equals(obj.getRelativeId())) {
					respon.setSuccess(false);
					respon.setMessage(messageUtils.getMessage("E005","user"));
					return respon;
				}
			}
		}
		// create new user
		User user = this.createUser(userDTO, new BigInteger(ConstantUtils.Privacy.PUBLIC.getValue()), 1,
				new BigInteger(ConstantUtils.ROLE_TYPE_ID.CUSTOMER.getValue()), new Date(), new Date());
		String emailGen = DateUtil.getFormatDate(new Date(), DateUtil.PT_DD_MM_YYYY__HH_MM_SS).replaceAll(" ", "")+"@teamsport.tk";
		user.setUserMail(emailGen);
		user.setRelativeId(userId);
		user.setUserRelationship(userDTO.getUserRelationship());
		// if create new user success then create user login
		if (user != null) {
			user = userRepository.save(user);
			userDTO = convertToDTO(user);
		}
		respon.setData(userDTO);
		respon.setMessage(messageUtils.getMessage("I001","user"));
		return respon;
	}
	
	/**
	 * @param userId
	 * 
	 * @return ApiResponse with Relative User
	 */
	public ApiResponse getRelativeUser(BigInteger userId) throws Exception {
		ApiResponse respon = new ApiResponse();
		List<UserDTO> listUserDTO = new ArrayList<UserDTO>();
		User parentUser = userRepository.findByUserIdAndIsActive(userId, 1);
		if(parentUser == null ) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001","user"));
			return respon;
		}
		
		List<User> listUser = userRepository.findByRelativeIdAndIsActive(userId, 1);
		if (listUser == null || listUser.isEmpty()) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E009", "user"));
			return respon;
		}
		for(User user : listUser) {
			UserDTO userDTO = this.convertToDTO(user);
			listUserDTO.add(userDTO);
		}
		respon.setData(listUserDTO);
		return respon;
	}

	/**
	 * 
	 * @param partyId
	 * @param partyTypedId
	 * @param statusId
	 * @param Description
	 * @param phoneNumber
	 * @return
	 */
	protected User createUser(UserDTO userDTO, BigInteger isActivePrivacy, int isActive, BigInteger roleID,
			Date createdDate, Date updatedDate) {
		User user = new User();
		user.setUserId(null);// auto create
		user.setIsActivePrivacy(isActivePrivacy); // default
		user.setUserFullName(userDTO.getUserFullName());
		user.setUserGender(userDTO.getUserGender());
		user.setUserAddress(userDTO.getUserAddress());
		user.setUserHeight(userDTO.getUserHeight());
		user.setUserWeight(userDTO.getUserWeight());
		user.setUserShortIntroduction(userDTO.getUserShortIntroduction());
		user.setUserNational(userDTO.getUserNational());
		user.setUserPreferredHand(userDTO.getUserPreferredHand());
		user.setUserPreferredFoot(userDTO.getUserPreferredFoot());
		user.setUserPhone(userDTO.getUserPhone());
		user.setIsActive(isActive); // default
		user.setUserMail(userDTO.getUserMail());
		user.setUserRoleId(roleID); // default role is customer
		user.setCreatedDate(createdDate);
		user.setUpdatedDate(updatedDate);
		user.setUserPhone(userDTO.getUserPhone()); // add in web-admin
		return user;
	}

	/**
	 * @Des
	 * @param
	 * @return
	 * @throws Exception
	 **/
	protected UserLogin generateUserLogin(String email, String password, BigInteger userId) throws Exception {
		UserLogin userLogin = new UserLogin();
		// check username is exist
		if (this.getUserByUserName(email) != null) {
			throw new Exception(messageUtils.getMessage("E005", "user"));
		}
		Date date = new Date();
		userLogin.setUserName(email);
		userLogin.setCreatedDate(date);
		userLogin.setUpdatedDate(date);
		userLogin.setUserId(userId);
		userLogin.setCurrentPassword(HashCrypt.cryptUTF8(null, null, password));
		return userLoginRepository.save(userLogin);
	}

	/**
	 * 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public User getUserByUserName(String userName) throws Exception {
		Optional<UserLogin> userLogin = userLoginRepository.findByUserNameAndIsActive(userName, 1);
		if (!userLogin.isPresent()) {
			return null;
		}
		return userRepository.findByUserIdAndIsActive(userLogin.get().getUserId(), 1);
	}
	
	/**
	 * 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public User getUserByUserId(BigInteger userId) throws Exception {
		User currentUser = userRepository.findByUserIdAndIsActive(userId, 1);
		if (currentUser == null) {
			return null;
		}
		return currentUser;
	}

	/**
	 * 
	 * @note
	 * @Des
	 * @Des
	 * @param userDTO
	 * @throws Exception 
	 */
	public ApiResponse changePassword(UserDTO userDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		Optional<UserLogin> oldUserLogin = userLoginRepository.findByUserIdAndIsActive(userDTO.getUserId(), 1); // update pass by user id

		if (!oldUserLogin.isPresent()) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "user")); // inform user account doesn't exist
			return respon;
		}
		
		UserLogin userLogin = oldUserLogin.get(); // Section change password

		if (!authenOfbizService.checkPassword(userLogin.getCurrentPassword(), userDTO.getOldPassword())) { // section check password valid
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E002", "oldPassword"));
			return respon;
		}

		userLogin.setCurrentPassword(HashCrypt.cryptUTF8(null, null, userDTO.getPassword()));
		userLoginRepository.save(userLogin);

		respon.setMessage(messageUtils.getMessage("I003", "password")); // Message updated password successfully
		return respon;
	}

	/**
	 * 
	 * @note
	 * @Des
	 * @Des
	 * @param userDTO
	 * @throws Exception 
	 */
	public ApiResponse forgotPassword(UserDTO userDTO){
		ApiResponse respon = new ApiResponse();
		Optional<UserLogin> oldUserLogin = userLoginRepository.findByUserNameAndIsActive(userDTO.getUserMail(), 1); // update pass by email
		if (!oldUserLogin.isPresent()) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "email")); // inform email account doesn't exist
			return respon;
		}
		ActiveOtp otp = activeOTPService.generateOTP(userDTO.getUserMail());
		if(otp == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
			return respon;
		}
		// send password to user
		mailService.sendMailForgotPass(userDTO.getUserMail(),otp.getActiveOtpCode());
		respon.setMessage(messageUtils.getMessage("I008")); // Message updated password successfully
		return respon;
	}

	/**
	 * @param userId
	 * 
	 * @return ApiResponse with PersonalInfoDTO
	 */
	public ApiResponse getPersonalInfoByUserId(BigInteger userId) throws Exception {
		ApiResponse respon = new ApiResponse();

		User user = userRepository.findByUserIdAndIsActive(userId, 1);
		if (user == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "user"));
			return respon;
		}
		
		UserDTO userDTO = new UserDTO();
		
		userDTO = this.convertToDTO(user);
		respon.setData(userDTO);
		return respon;
	}

	/**
	 * 
	 * @param userId
	 * @return ApiResponse with List<PersonalInfoTeamDTO>
	 * @throws Exception
	 */
	public ApiResponse getTeamInPersonalInfo(BigInteger userId) throws Exception {
		ApiResponse respon = new ApiResponse();

		List<TeamDTO> personalInfoTeamDTO = new ArrayList<TeamDTO>();
		List<Team> listTeam = teamRepository.getTeamByUser(userId); // 1: JOINED

		if (listTeam != null) {
			for (Team team : listTeam) {
				TeamDTO teamInfo = new TeamDTO();
				TeamMember teamMember = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(team.getTeamId(),
						userId, 1);
				List<PeriodMatchesDTO> periods = new ArrayList<PeriodMatchesDTO>();
				int totalMatchs = 0;
				int totalWin = 0;
				int totalLose = 0;
				int totalDrawn = 0;

				teamInfo.setTeamId(team.getTeamId());
				teamInfo.setUserId(userId);
				teamInfo.setTeamName(team.getTeamName());
				if(team.getSportId() != null) {
					Sport sport = sportRepository.findBySportIdAndIsActive(team.getSportId(), 1);
					if(sport != null) {
						teamInfo.setSportId(sport.getSportId());
						teamInfo.setSportName(sport.getSportName());
					}
				}
				teamInfo.setTeamAvatar(team.getTeamAvatar());
				teamInfo.setMemberSince(teamMember.getCreatedDate());
				teamInfo.setTeamMemberRole(teamMember.getTeamMemberRole());
				teamInfo.setPosition(teamMember.getPositionSport());
				
				List<Map<String, Object>> years = eventKendoRepository.getAllYearsEventKendoByTeam(team.getTeamId());
				
				if(years != null && !years.isEmpty()) {
					for(Map<String, Object> year:years) {
						List<EventMatchKendo> listMatchsKendo = eventMatchKendoRepository.listMatchByYear(
								ConstantUtils.Event_Type.MATCH.getValue(), ConstantUtils.Event_Match_Status.FINISH.getValue(),
								team.getTeamId(), userId, year.get("year").toString());
						PeriodMatchesDTO period = new PeriodMatchesDTO();
						int win = 0;
						int lose = 0;
						int drawn = 0;
						
						if (listMatchsKendo != null && !listMatchsKendo.isEmpty()) {
							for (EventMatchKendo match : listMatchsKendo) {
								if (match.getHomeUserId() != null && userId.compareTo(match.getHomeUserId()) == 0) { // 0:win 1:lose 2:drawn
									if (Integer.parseInt(match.getUserWin()) == ConstantUtils.Event_Match_Result.HOME_WIN.getValue()) {
										win++;
										totalWin++;
									} else if (Integer.parseInt(match.getUserWin()) == ConstantUtils.Event_Match_Result.OPPONENT_WIN.getValue()) {
										lose++;
										totalLose++;
									} else {
										drawn++;
										totalDrawn++;
									}
								} else { // 0:lose 1:win 2:drawn
									if (Integer.parseInt(match.getUserWin()) == ConstantUtils.Event_Match_Result.HOME_WIN.getValue()) {
										lose++;
										totalLose++;
									} else if (Integer.parseInt(match.getUserWin()) == ConstantUtils.Event_Match_Result.OPPONENT_WIN.getValue()) {
										win++;
										totalWin++;
									} else {
										drawn++;
										totalDrawn++;
									}
								}
							}
						}
						totalMatchs += listMatchsKendo.size();
						period.setPeriod(year.get("year").toString());
						period.setMatches(listMatchsKendo.size());
						period.setWin(win);
						period.setLose(lose);
						period.setDraw(drawn);
						periods.add(period);
					}
				}
				PeriodMatchesDTO periodAll = new PeriodMatchesDTO();
				periodAll.setPeriod("ALL");
				periodAll.setMatches(totalMatchs);
				periodAll.setWin(totalWin);
				periodAll.setLose(totalLose);
				periodAll.setDraw(totalDrawn);
				periods.add(periodAll);
				teamInfo.setListPeriodMatches(periods);
				personalInfoTeamDTO.add(teamInfo);
			}
		}
		respon.setData(personalInfoTeamDTO);

		return respon;
	}

	/**
	 * 
	 * @param userId(viewer)
	 * @return ApiResponse with List<PersonalInfoTeamDTO>
	 * @throws Exception
	 */
	public ApiResponse getRelatedPostByUserId(BigInteger viewUserId, BigInteger curentLoginUserId) throws Exception {
		ApiResponse respon = new ApiResponse();

		List<PostDTO> relatedPostDTO = new ArrayList<PostDTO>();
		List<PostIncludeUser> listPostIncludeUser = postIncludeUserRepository
				.findByPostIncludeUseTypeAndUserIdAndIsActive(ConstantUtils.Post_Include_User_Type.PRIVACY.getValue(),
						viewUserId, 1);

		if (listPostIncludeUser != null) {
			for (PostIncludeUser postIncludeUser : listPostIncludeUser) {
				Post post = new Post();

				// check userId truyền vào với current userlogin có trùng nhau không
				if (curentLoginUserId.compareTo(viewUserId) == 0) { // chính user này xem của mình => all privacy
					post = postRepository.findByPostIdAndIsActive(postIncludeUser.getPostId(), 1);

				} else { // user khác xem => PrivacyId = 1 : Public
					post = postRepository.findByPostIdAndPrivacyIdAndIsActive(postIncludeUser.getPostId(),
							new BigInteger(ConstantUtils.Privacy.PUBLIC.getValue()), 1);
				}

				if (post != null) {
					PostDTO dto = postService.convertToDTO(post, curentLoginUserId);
					relatedPostDTO.add(dto);
				}
			}
		}
		respon.setData(relatedPostDTO);

		return respon;
	}

	public ApiResponse updateAvatar(RequestDTO requestDTO) {
		ApiResponse respon = new ApiResponse();

		// get Team by TeamId
		User user = userRepository.findByUserIdAndIsActive(requestDTO.getUserId(), 1);
		if (user == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "user"));
			return respon;
		}

		uploadService.deleteByUploadId(user.getUserAvatar());

		user.setUserAvatar(requestDTO.getUploadId()); // set new Avatar
		user.setUpdatedDate(new Date());
		user = userRepository.save(user);
		respon.setMessage(messageUtils.getMessage("I003", "user"));

		return respon;
	}
	
	/**
	 * 
	 * @param bigInteger
	 * @param userId
	 */
	public String updateUserActive(String email,String otp) throws Exception {
		List<User> listUser = userRepository.findByUserEmailAndIsActive(email, 2);
		if(listUser == null) {
			return messageUtils.getMessage("E010","user");
		}
		
		Optional<UserLogin> userLogin = userLoginRepository.findByUserNameAndIsActive(email, 2);
		if (!userLogin.isPresent()) {
			return messageUtils.getMessage("E010","user");
		}
		if (activeOTPService.checkOTP(email, otp, new Date()) != null ){
			// update active of user
			listUser.get(0).setIsActive(1);
			listUser.get(0).setUpdatedDate(new Date());
			userRepository.save(listUser.get(0));
		
			// update active of user Login
			userLogin.get().setIsActive(1);
			userLogin.get().setUpdatedDate(new Date());
			userLoginRepository.save(userLogin.get());
		}else {
			return messageUtils.getMessage("E010","user");
		}

		return messageUtils.getMessage("I012","user");	
	}
	
	 /**
	 * @param userId
	 * @return
	 */
	public UserRole getRoleNameByUserId(BigInteger userId) {
		return userRoleRepository.findByUserIdAndIsActive(userId, 1);
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public ApiResponse checkOTPByEmail(RequestDTO request) {
		
		ApiResponse respon = new ApiResponse();
		Optional<UserLogin> oldUserLogin = userLoginRepository.findByUserNameAndIsActive(request.getUserMail(), 1); // update pass by email
		if (!oldUserLogin.isPresent()) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "email")); // inform email account doesn't exist
			return respon;
		}
		UserLogin userLogin = oldUserLogin.get(); // Section change password

		//check OTP
		if (activeOTPService.checkOTP(request.getUserMail(), request.getOtp(), new Date()) == null ){
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E002", "otp")); // inform email account doesn't exist
			return respon;
		}

		userLogin.setCurrentPassword(HashCrypt.cryptUTF8(null, null, request.getNewPassword()));
		userLoginRepository.save(userLogin);

		respon.setMessage(messageUtils.getMessage("I003", "password")); // Message updated password successfully
		return respon;
	}

	/**
	 * 
	 * @param request
	 * @param userDTO
	 * @return
	 */
	public ApiResponse updateUser(UserDTO userDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		User oldUser = new User();
		boolean isWebAdmin = false;
		
		if (userDTO.getStatus() != null) { // WebAdmin update
			isWebAdmin = true;
			oldUser = userRepository.findByUserIdAndIsActive(userDTO.getUserId(), userDTO.getStatus().intValue());
		} else {
			oldUser = userRepository.findByUserIdAndIsActive(userDTO.getUserId(), 1);
		}

		if (oldUser == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "user"));
			return respon;
		}
		oldUser.setUserFullName(userDTO.getUserFullName());
		if(!StringUtils.isEmpty(userDTO.getUserBirthDay())) {
			oldUser.setUserBirthDate(userDTO.getUserBirthDay());
		}
		
		if (isWebAdmin) {
			oldUser.setIsActive(userDTO.getStatus().intValue());
			oldUser.setUserNational(userDTO.getUserNational());
			oldUser.setUserPhone(userDTO.getUserPhone());
			//Update email?
		}
		oldUser.setUserAddress(userDTO.getUserAddress());
		oldUser.setUserShortIntroduction(userDTO.getUserShortIntroduction());
		oldUser.setUserHeight(userDTO.getUserHeight());
		oldUser.setUserWeight(userDTO.getUserWeight());
		oldUser.setUserGender(userDTO.getUserGender());
		oldUser.setUserPreferredFoot(userDTO.getUserPreferredFoot());
		oldUser.setUserPreferredHand(userDTO.getUserPreferredHand());
		oldUser.setUpdatedDate(new Date());
		oldUser = userRepository.save(oldUser);
		
		//del old acchivment
		userAchievementRepository.deleteByUserId(oldUser.getUserId());
		//update acchivment
		if(userDTO.getUserAchievements() != null && !userDTO.getUserAchievements().isEmpty()) {
			List<UserAchievement> listAchi = new ArrayList<UserAchievement>();
			for(UserAchievementDTO item:userDTO.getUserAchievements()) {
				if(!StringUtils.isEmpty(item.getUserAchievementSport())) {
					UserAchievement achi = new UserAchievement();
					achi.setIsActive(1);
					achi.setUserId(oldUser.getUserId());
					achi.setUserAchievementName(item.getUserAchievementName());
					achi.setUserAchievementId(null);
					achi.setUserAchievementSport(item.getUserAchievementSport());
					achi.setUserAchievementTime(item.getUserAchievementTime());
					achi.setCreatedDate(new Date());
					achi.setUpdatedDate(new Date());
					listAchi.add(achi);
				}
			}
			userAchievementRepository.saveAll(listAchi);
		}
		respon.setMessage(messageUtils.getMessage("I003", "user"));
		respon.setData(this.convertToDTO(oldUser));
		return respon;
	}
	
	/**
	 * 
	 * @param no
	 * @return List<UserDTO>
	 */
	public List<UserDTO> getListUserDTO() {
		List<UserDTO> result = new ArrayList<UserDTO>();
		
		List<User> listUser = userRepository.findByUserRoleId(new BigInteger(ConstantUtils.ROLE_TYPE_ID.CUSTOMER.getValue()));
		
		if(listUser != null && !listUser.isEmpty()) {
			for(User user:listUser) {
				UserDTO dto = new UserDTO();
				
				dto = this.convertToDTO(user);
				dto.setCreatedDate(user.getCreatedDate());
				dto.setUpdatedDate(user.getUpdatedDate());
				dto.setStatus(new BigInteger(Integer.toString(user.getIsActive()))); // ACTIVE("1","active"), NO_ACTIVE("2","no active"), BLOCK("0", "block");
				
				List<Map<String, Object>> listTeam = teamRepository.getListTeamByUser(user.getUserId(), user.getIsActive());
				List<TeamDTO> listTeamDTO = new ArrayList<TeamDTO>();
				
				if (listTeam != null && !listTeam.isEmpty()) {
					for (Map<String, Object> obj : listTeam) {
						TeamDTO teamdto = new TeamDTO();
						teamdto.setTeamId(new BigInteger(obj.get("team_id").toString()));
						teamdto.setTeamAvatar(new BigInteger(obj.get("team_avatar").toString()));
						teamdto.setTeamName(obj.get("team_name").toString());
						teamdto.setTeamMemberRole(obj.get("team_member_role").toString());
						teamdto.setTotalMember(teamRepository.countMembersInTeam(teamdto.getTeamId()));
						listTeamDTO.add(teamdto);
					}
				}
				dto.setTeamsJoined(listTeamDTO);
				
				result.add(dto);
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param userID
	 * @return User
	 */
	public ApiResponse blockUserByUserId(BigInteger userId ) {
		ApiResponse respon = new ApiResponse();

		User user = userRepository.findByUserId(userId);
		if (user == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "user"));
			return respon;
		}
		
		if (user.getIsActive() == 0) {
			user.setIsActive(1); // Unblock user
			respon.setMessage(messageUtils.getMessage("I021", "user"));
		} else {
			user.setIsActive(0); // Block
			respon.setMessage(messageUtils.getMessage("I020", "user"));
		}

		user = userRepository.save(user);
		respon.setData(user);
		return respon;
	}
	
	/**
	 * 
	 * @param teamId
	 * @param userId
	 * @return
	 * @throws JSONException 
	 * @throws NoSuchMessageException 
	 */
	public ApiResponse acceptInvite(BigInteger teamId, BigInteger userId) throws NoSuchMessageException, JSONException {
		ApiResponse respon = new ApiResponse();
		TeamMemberStatus teammberStatus = teamMemberStatusRepository.
				findById_UserIdAndId_TeamIdAndId_StatusIdAndIsActive(userId, teamId, new BigInteger(ConstantUtils.Team_Member_Status.INVITED.getValue()), 1);
		if(teammberStatus == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E014"));
			return respon;
		}
		//check user moi co phai la admin hay khong, neu la admin thi join luon
		TeamMember teamMember = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(teamId, teammberStatus.getCreatedby(), 1);
		if(teamMember != null && ConstantUtils.Team_Member_Role.TEAM_ADMIN.getValue().equals(teamMember.getTeamMemberRole())) {
			TeamMember newMember = new TeamMember();
			TeamMemberPK pk = new TeamMemberPK();
			pk.setTeamId(teamId);
			pk.setUserId(userId);
			newMember.setId(pk);
			newMember.setIsActive(1);
			newMember.setPositionSport("");
			newMember.setTeamMemberRole(ConstantUtils.Team_Member_Role.TEAM_MEMBER.getValue());
			newMember.setCreatedby(teammberStatus.getCreatedby());
			newMember.setCreatedDate(new Date());
			newMember.setUpdatedDate(new Date());
			teamMemberRepository.save(newMember);
			teamMemberStatusRepository.delete(teammberStatus);//delete invite
			respon.setMessage(messageUtils.getMessage("I014"));
			
		}else {
			
			TeamMemberStatusPK tmsPK = new TeamMemberStatusPK();
			tmsPK.setTeamId(teamId);
			tmsPK.setUserId(userId);
			tmsPK.setStatusId(new BigInteger(ConstantUtils.Team_Member_Status.REQUESTED.getValue()));
			
			TeamMemberStatus tms = new TeamMemberStatus();
			tms.setCreatedby(teammberStatus.getCreatedby());
			tms.setId(tmsPK);
			tms.setCreatedDate(new Date());
			tms.setUpdatedDate(new Date());
			
			teamMemberStatusRepository.delete(teammberStatus);//delete invite
			teamMemberStatusRepository.save(tms);
			respon.setMessage(messageUtils.getMessage("I015"));
			
			User user = userRepository.findByUserIdAndIsActive(userId, 1);
			if(user != null) {
				// get all admin of team
				List<User> teamAdmins = userRepository.getAllAdmin(teamId, ConstantUtils.Team_Member_Role.TEAM_ADMIN.getValue());
				//save notification to all admin of team
				notificationService.notificationFromUserToTeam(user, teamAdmins, teamId, "N002",
						ConstantUtils.MobileScreen.TEAM_PROFILE_DETAIL.getDescription(),
						ConstantUtils.Notification_Type.NOTIFI_REQUEST_JOIN_TEAM.getValue());
			}
		}
		return respon;
	}

	/**
	 * 
	 * @param teamId
	 * @param userId
	 * @return
	 */
	public ApiResponse rejectInvite(BigInteger teamId, BigInteger userId) {
		
		ApiResponse respon = new ApiResponse();
		TeamMemberStatus teammberStatus = teamMemberStatusRepository.
				findById_UserIdAndId_TeamIdAndId_StatusIdAndIsActive(userId, teamId, new BigInteger(ConstantUtils.Team_Member_Status.INVITED.getValue()), 1);
		if(teammberStatus == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E014"));
			return respon;
		}
		teamMemberStatusRepository.delete(teammberStatus);//delete invite
		respon.setMessage(messageUtils.getMessage("I016"));
		return respon;
	}

	/**
	 * @Des
	 * @param
	 * @return
	 * @throws Exception
	 **/
	public ApiResponse generateUserRelative(UserDTO userDTO)
			throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			User user = userRepository.findByUserIdAndIsActive(userDTO.getUserId(), 1);
			if(user == null) {
				respon.setSuccess(false);
				respon.setMessage(messageUtils.getMessage("E001", "user"));
				return respon;
			}
			if (this.generateUserLogin(userDTO.getUserMail(), userDTO.getPassword(), userDTO.getUserId()) == null) {
				respon.setMessage(messageUtils.getMessage("E003"));
				respon.setSuccess(false);
				return respon;
			}
			user.setUserMail(userDTO.getUserMail());
			user.setIsActive(2);
			user.setUpdatedDate(new Date());
			userRepository.save(user);
			respon.setMessage(messageUtils.getMessage("I003", "user"));
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}

		return respon;
	}
}