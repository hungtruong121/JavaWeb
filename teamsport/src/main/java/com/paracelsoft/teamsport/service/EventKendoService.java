package com.paracelsoft.teamsport.service;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paracelsoft.teamsport.api.dto.EventKendoDTO;
import com.paracelsoft.teamsport.api.dto.EventMatchKendoDTO;
import com.paracelsoft.teamsport.api.dto.LocationDTO;
import com.paracelsoft.teamsport.api.dto.SearchDTO;
import com.paracelsoft.teamsport.api.dto.SearchEventKendoDTO;
import com.paracelsoft.teamsport.api.dto.UserDTO;
import com.paracelsoft.teamsport.api.dto.TeamFoldersDTO;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.entity.EventKendo;
import com.paracelsoft.teamsport.entity.EventMatchKendo;
import com.paracelsoft.teamsport.entity.Location;
import com.paracelsoft.teamsport.entity.Team;
import com.paracelsoft.teamsport.entity.TeamFile;
import com.paracelsoft.teamsport.entity.TeamFolder;
import com.paracelsoft.teamsport.entity.TeamMember;
import com.paracelsoft.teamsport.entity.User;
import com.paracelsoft.teamsport.repository.EventKendoRepository;
import com.paracelsoft.teamsport.repository.EventMatchKendoRepository;
import com.paracelsoft.teamsport.repository.LocationRepository;
import com.paracelsoft.teamsport.repository.TeamFileRepository;
import com.paracelsoft.teamsport.repository.TeamFolderRepository;
import com.paracelsoft.teamsport.repository.TeamMemberRepository;
import com.paracelsoft.teamsport.repository.TeamRepository;
import com.paracelsoft.teamsport.repository.UserRepository;
import com.paracelsoft.teamsport.util.ConstantUtils;
import com.paracelsoft.teamsport.util.DateUtil;
import com.paracelsoft.teamsport.util.MessageUtils;
import com.paracelsoft.teamsport.util.ReponseCode;

@Transactional(rollbackFor = { Exception.class, ParseException.class })
@Service("eventKendoService")
public class EventKendoService {
	@Autowired
	UserService userService;
	
	@Autowired
	EventKendoRepository eventKendoRepository;
	
	@Autowired
	EventMatchKendoRepository eventMatchKendoRepository;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	TeamRepository teamRepository;

	@Autowired
	TeamMemberRepository teamMemberRepository;

	@Autowired
	MessageUtils messageUtils;

	@Autowired
	NotificationService notificationService;

	@Autowired
	MailService mailService;
	
	@Autowired
	TeamFolderRepository teamFolderRepository;
	
	@Autowired
	Environment evn;
	
	@Autowired
	FolderService folderService;
	
	@Autowired
	LocationRepository locationRepository;
	
	@Autowired
	TeamFileRepository teamFileRepository;

	/**
	 * 
	 * @Des create/update EventKendo
	 * @param EventKendoDTO
	 * @return
	 * @throws Exception 
	 */
	public ApiResponse updateEventKendo(EventKendoDTO eventKendoDTO, BigInteger createdBy) throws Exception {
		ApiResponse respon = new ApiResponse();
		// Validate
		if (!ConstantUtils.Event_Type.NORMAL.getValue().equals(eventKendoDTO.getEventType())
				&& !ConstantUtils.Event_Type.MATCH.getValue().equals(eventKendoDTO.getEventType())) {
			respon.setSuccess(false);
			respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
			respon.setMessage(messageUtils.getMessage("E002", "eventType"));
			return respon;
		}
		if (!ConstantUtils.Event_Loop_Type.NONE.getValue().equals(eventKendoDTO.getEventLoopType())
				&& !ConstantUtils.Event_Loop_Type.WEEKLY.getValue().equals(eventKendoDTO.getEventLoopType())
				&& !ConstantUtils.Event_Loop_Type.MONTHLY.getValue().equals(eventKendoDTO.getEventLoopType())) {
			respon.setSuccess(false);
			respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
			respon.setMessage(messageUtils.getMessage("E002", "eventLoopType"));
			return respon;
		}
		if (ConstantUtils.Event_Type.MATCH.getValue().equals(eventKendoDTO.getEventType())) {
			if (!ConstantUtils.Event_Game_Type.IN_TEAM.getValue().equals(eventKendoDTO.getEventGameType())
					&& !ConstantUtils.Event_Game_Type.PRACTICE.getValue().equals(eventKendoDTO.getEventGameType())
					&& !ConstantUtils.Event_Game_Type.OFFICIAL.getValue().equals(eventKendoDTO.getEventGameType())) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E002", "eventGameType"));
				return respon;
			}
		}

		// Check creating is Admin in team
		TeamMember teamMember = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(eventKendoDTO.getTeamId(),
				createdBy, 1);
		if (teamMember == null
				|| !ConstantUtils.Team_Member_Role.TEAM_ADMIN.getValue().equals(teamMember.getTeamMemberRole())) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E015"));
			return respon;
		}

		Date date = new Date();
		EventKendo parentEvent = null;
		
		if (eventKendoDTO.getEventId() != null) { // update event
			// Check oldEventKendo exists
			EventKendo oldEventKendo = eventKendoRepository.findByEventKendoIdAndIsActive(eventKendoDTO.getEventId(), 1);
			if (oldEventKendo == null) {
				respon.setSuccess(false);
				respon.setMessage(messageUtils.getMessage("E001", "event"));
				return respon;
			}
			
			if (oldEventKendo.getEventParentId() == null) { // Parent event
				// Delete all child event
				List<EventKendo> listChildEvent = eventKendoRepository.findByEventParentId(oldEventKendo.getEventKendoId());
				if (listChildEvent != null && !listChildEvent.isEmpty()) {
					// Delete all listMatchChild
					for (EventKendo matchChild : listChildEvent) {
						List<EventMatchKendo> listMatchChild = eventMatchKendoRepository.findByEventKendoId(matchChild.getEventKendoId());
						if (listMatchChild != null && !listMatchChild.isEmpty()) {
							eventMatchKendoRepository.deleteAll(listMatchChild);
						}
					}
					eventKendoRepository.deleteAll(listChildEvent);
				}
			}
			// Delete all eventMatchKendo
			List<EventMatchKendo> listMatchKendo = eventMatchKendoRepository.findByEventKendoId(oldEventKendo.getEventKendoId());
			if (listMatchKendo != null && !listMatchKendo.isEmpty()) {
				eventMatchKendoRepository.deleteAll(listMatchKendo);
			}
			// update
			parentEvent = this.convertToEntity(eventKendoDTO);
			parentEvent.setCreatedBy(oldEventKendo.getCreatedBy());
			parentEvent.setCreatedDate(oldEventKendo.getCreatedDate());
			respon.setMessage(messageUtils.getMessage("I003", "event"));
		} else { // create
			parentEvent = this.convertToEntity(eventKendoDTO);
			parentEvent.setCreatedBy(createdBy);
			parentEvent.setCreatedDate(date);
			respon.setMessage(messageUtils.getMessage("I001", "event"));
		}
		parentEvent = eventKendoRepository.save(parentEvent);
		
		// Tính loop event từ dateExpired nếu type là WEEKLY or MONTHLY
		if (!ConstantUtils.Event_Loop_Type.NONE.getValue().equals(eventKendoDTO.getEventLoopType())) {
			List<Integer> listDayPicked = eventKendoDTO.getDaysPicked();
			List<EventMatchKendo> listMatchEventKendo = new ArrayList<EventMatchKendo>();
			List<EventMatchKendoDTO> listMatchDTO = eventKendoDTO.getListMatchKendo();
			
			if (listDayPicked != null && !listDayPicked.isEmpty()) {
				List<Date> listDates = new ArrayList<Date>();
				for (Integer loopDate : listDayPicked) {
					if (ConstantUtils.Event_Loop_Type.WEEKLY.getValue().equals(eventKendoDTO.getEventLoopType())) {
						listDates.addAll(DateUtil.getAllDayBetweenTwoDates(eventKendoDTO.getEventDate(),
								eventKendoDTO.getEventDateExpired(), loopDate));
					} else { // MONTHLY
						listDates.addAll(DateUtil.getAllDayOfMonthBetweenTwoDates(eventKendoDTO.getEventDate(),
								eventKendoDTO.getEventDateExpired(), loopDate));
					}
				}
				
				// Save EventKendo theo list dates
				if (listDates != null && !listDates.isEmpty()) {
					for (Date day : listDates) {
						EventKendo eventKendo = this.convertToEntity(eventKendoDTO);
						eventKendo.setEventKendoId(null);
						eventKendo.setEventParentId(parentEvent.getEventKendoId());
						eventKendo.setEventDate(day); // DateUtil.getFormatDate(day, DateUtil.PT_YYYY_MM_DD)
						eventKendo.setCreatedBy(createdBy);
						eventKendo.setCreatedDate(date);
						eventKendo = eventKendoRepository.save(eventKendo);

						// Add eventMatchKendo
						if (listMatchDTO != null && !listMatchDTO.isEmpty()) {
							for (EventMatchKendoDTO matchDTO : listMatchDTO) {
								EventMatchKendo match = this.convertToMatchEntity(matchDTO, date);
								match.setEventKendoId(eventKendo.getEventKendoId());
								listMatchEventKendo.add(match);
							}
						}
					}
				}
				eventMatchKendoRepository.saveAll(listMatchEventKendo);
			} else { // SET NONE Event_Loop_Type of eventKendo
				eventKendoDTO.setEventLoopType(ConstantUtils.Event_Loop_Type.NONE.getValue());
				this.saveEventMatchKendo(eventKendoDTO, parentEvent, createdBy, date);
			}
		} else {
			this.saveEventMatchKendo(eventKendoDTO, parentEvent, createdBy, date);
		}
		
		// Notification invite opponent team join match
		if (eventKendoDTO.getOpponentTeamId() != null) {
			Team teamOpponent = teamRepository.findByTeamIdAndIsActive(eventKendoDTO.getOpponentTeamId(), 1);
			if (teamOpponent != null) {
				// get all admin of opponent team
				List<User> teamAdmins = userRepository.getAllAdmin(teamOpponent.getTeamId(),
						ConstantUtils.Team_Member_Role.TEAM_ADMIN.getValue());
				// save notification to all admin of team
				notificationService.notificationFromTeamToTeam(eventKendoDTO.getTeamId(), teamAdmins, createdBy, "N016",
						ConstantUtils.MobileScreen.POST_EVENT_KENDO_DETAIL.getDescription(),
						ConstantUtils.Notification_Type.NOTIFI_MESS.getValue());
			}
		}
		createFolderByEvent(parentEvent);
		respon.setData(parentEvent);
		return respon;
	}
	
	private void saveEventMatchKendo(EventKendoDTO eventKendoDTO, EventKendo parentEvent, BigInteger createdBy, Date date) {
		if (ConstantUtils.Event_Type.MATCH.getValue().equals(eventKendoDTO.getEventType())) {
			// Add eventMatchKendo
			List<EventMatchKendoDTO> listMatchDTO = eventKendoDTO.getListMatchKendo();
			List<EventMatchKendo> listMatchKendo = new ArrayList<EventMatchKendo>();
			if (listMatchDTO != null && !listMatchDTO.isEmpty()) {
				for (EventMatchKendoDTO matchDTO : listMatchDTO) {
					EventMatchKendo match = this.convertToMatchEntity(matchDTO, date);
					match.setEventKendoId(parentEvent.getEventKendoId());
					listMatchKendo.add(match);
				}
				eventMatchKendoRepository.saveAll(listMatchKendo);
			}
		}
	}

	/**
	 * 
	 * @Des convert eventKendoDTO to entity
	 * @param eventKendoDTO
	 * @return
	 * @throws ParseException 
	 */
	private EventKendo convertToEntity(EventKendoDTO eventKendoDTO) throws ParseException {
		EventKendo eventKendo = new EventKendo();
		
		if (eventKendoDTO.getEventId() != null) {
			eventKendo.setEventKendoId(eventKendoDTO.getEventId());
		} else {
			eventKendo.setEventKendoId(null);
		}
		eventKendo.setTeamId(eventKendoDTO.getTeamId());
		eventKendo.setOpponentTeamId(eventKendoDTO.getOpponentTeamId());
		eventKendo.setLocationId(eventKendoDTO.getLocationId());
		eventKendo.setPrivacyId(eventKendoDTO.getPrivacyId());
		eventKendo.setEventType(eventKendoDTO.getEventType()); // MATCH - NORMAL
		eventKendo.setEventTitle(eventKendoDTO.getEventTitle());
		eventKendo.setEventLoopType(eventKendoDTO.getEventLoopType()); // NONE - WEEKLY - MONTHLY
		eventKendo.setEventDate(DateUtil.formatStringToDate(eventKendoDTO.getEventDate(), DateUtil.PT_DD_MM_YYYY_, DateUtil.PT_YYYY_MM_DD));
		eventKendo.setEventExpired(DateUtil.formatStringToDate(eventKendoDTO.getEventDateExpired(), DateUtil.PT_DD_MM_YYYY_, DateUtil.PT_YYYY_MM_DD));
		eventKendo.setEventGameType(eventKendoDTO.getEventGameType()); // IN_TEAM - PRACTICE - OFFICIAL
		eventKendo.setEventGround(eventKendoDTO.getEventGround());
		eventKendo.setEventMatch(eventKendoDTO.getEventMatch());
		eventKendo.setEventHomeTeam(eventKendoDTO.getEventHomeTeam());
		eventKendo.setEventOpponentTeam(eventKendoDTO.getEventOpponentTeam());
		eventKendo.setEventTournament(eventKendoDTO.getEventTournament());
		eventKendo.setEventSubMatchs(eventKendoDTO.getEventSubMatchs());
		eventKendo.setTeamColor(eventKendoDTO.getTeamColor());
		eventKendo.setEventHashtag(eventKendoDTO.getEventHashtag());
		eventKendo.setEventNotice(eventKendoDTO.getEventNotice());
		eventKendo.setUpdatedDate(new Date());
		
		return eventKendo;
	}
	
	private EventMatchKendo convertToMatchEntity(EventMatchKendoDTO matchDTO, Date date) {
		EventMatchKendo match = new EventMatchKendo();
		
		match.setEventMatchId(null);
		match.setEventMatch(matchDTO.getEventMatch());
		match.setHomeUserId(matchDTO.getHomeUserId());
		match.setOpponentUserId(matchDTO.getOpponentUserId());
		match.setHomePlayerName(matchDTO.getHomePlayerName());
		match.setOpponentPlayerName(matchDTO.getOpponentPlayerName());
		match.setCreatedDate(date);
		match.setUpdatedDate(date);
		
		return match;
	}
			
	/**
	 * 
	 * @Des Send email to invite use app & join event
	 * @param EventKendoDTO
	 * @return
	 * @throws ParseException 
	 */
	public ApiResponse sendEmailInvite(EventKendoDTO eventDTO, User userAdmin) throws ParseException {
		ApiResponse respon = new ApiResponse();

		// Check send email is Admin in team
		TeamMember teamMember = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(eventDTO.getTeamId(),userAdmin.getUserId(), 1);
		if (teamMember == null
				|| !ConstantUtils.Team_Member_Role.TEAM_ADMIN.getValue().equals(teamMember.getTeamMemberRole())) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E015"));
			return respon;
		}
		Team team = teamRepository.findByTeamIdAndIsActive(eventDTO.getTeamId(), 1);
		String adminName = userAdmin.getUserFullName();
//		DateFormat fromFormatter = new SimpleDateFormat(DateUtil.PT_YYYY_MM_DD_HH_MM_SS);
//		DateFormat toFormatter = new SimpleDateFormat(DateUtil.PT_YYYY_MM_DD);
//		String eventDate = toFormatter.format(fromFormatter.parse(contentEmail.get("event_date").toString()));
		String teamName = team.getTeamName();
		String mailContent[] = { adminName, teamName};

		mailService.sendMailInviteJoinAppAndEventKendo(eventDTO.getOpponentEmail(), mailContent);

		respon.setMessage(messageUtils.getMessage("I018"));
		return respon;
	}
	
	/**
	 * 
	 * @Des Admin opponent team approve join event
	 * @param EventKendoDTO
	 * @return
	 * @throws JSONException 
	 * @throws NoSuchMessageException 
	 * @throws ParseException
	 */
	public ApiResponse approveInvite(EventKendoDTO eventDTO, User user) throws NoSuchMessageException, JSONException {
		ApiResponse respon = new ApiResponse();
		
		// Check event approve exist
		EventKendo approveEventKendo = eventKendoRepository.findByEventKendoIdAndIsActive(eventDTO.getEventId(), 1);
		if (approveEventKendo == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "event"));
			return respon;
		}
				
		// Check user approve is Admin in opponent team
		TeamMember teamMember = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(approveEventKendo.getOpponentTeamId(), user.getUserId(), 1);
		if (teamMember == null
				|| !ConstantUtils.Team_Member_Role.TEAM_ADMIN.getValue().equals(teamMember.getTeamMemberRole())) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E015"));
			return respon;
		}
		
		// Change status is_approve
		approveEventKendo.setIsApprove(1);
		approveEventKendo.setApprovedBy(user.getUserId());
		eventKendoRepository.save(approveEventKendo);
		
		// Notification to admin in team
		Team teamHome = teamRepository.findByTeamIdAndIsActive(approveEventKendo.getTeamId(), 1);
		if (teamHome != null) {
			// get all admin in team
			List<User> teamAdmins = userRepository.getAllAdmin(teamHome.getTeamId(),
					ConstantUtils.Team_Member_Role.TEAM_ADMIN.getValue());
			// save notification to all admin in team
			notificationService.notificationFromTeamToTeam(approveEventKendo.getOpponentTeamId(), teamAdmins,
					user.getUserId(), "N017", ConstantUtils.MobileScreen.POST_EVENT_KENDO_DETAIL.getDescription(),
					ConstantUtils.Notification_Type.NOTIFI_MESS.getValue());
		}

		respon.setMessage(messageUtils.getMessage("I027", "event"));
		respon.setData(approveEventKendo);
		return respon;
	}
	
	/**
	 * 
	 * @Des Get detail EventKendo
	 * @param eventId
	 * @return EventKendoDTO
	 * @throws ParseException 
	 */

	public ApiResponse getDetailEventKendo(BigInteger eventId) throws ParseException {
		ApiResponse respon = new ApiResponse();
		
		EventKendo eventKendo = eventKendoRepository.findByEventKendoIdAndIsActive(eventId, 1);
		if (eventKendo == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "event"));
			return respon;
		}
		EventKendoDTO eventKendoDTO = convertEntityToDTO(eventKendo);
		eventKendoDTO = this.convertToDetailDTO(eventKendoDTO, eventKendo.getEventKendoId());
		
		respon.setData(eventKendoDTO);
		return respon;
	}

	private EventKendoDTO convertToDetailDTO(EventKendoDTO eventKendoDTO, BigInteger eventKendoId) {
		List<EventMatchKendo> listMatchKendo = eventMatchKendoRepository.findByEventKendoIdAndIsActive(eventKendoId, 1);
		List<EventMatchKendoDTO> listMatchKendoDTO = new ArrayList<EventMatchKendoDTO>();
		int matchHomeScore = 0;
		int matchOpponentScore = 0;
		int subMatchHomeScore = 0;
		int subMatchOpponentScore = 0;
		
		if (listMatchKendo != null && !listMatchKendo.isEmpty()) {
			for (EventMatchKendo match : listMatchKendo) {
				EventMatchKendoDTO matchDTO = new EventMatchKendoDTO();
				User userHome = userRepository.findByUserIdAndIsActive(match.getHomeUserId(), 1);
				User userOpponent = userRepository.findByUserIdAndIsActive(match.getOpponentUserId(), 1);
				
				if (match.getUserWin() != null && !match.getUserWin().isEmpty()) {
					matchDTO.setUserWin(Integer.parseInt(match.getUserWin()));
					if (Integer.compare(ConstantUtils.Event_Match_Result.HOME_WIN.getValue(), Integer.parseInt(match.getUserWin())) == 0) {
						matchHomeScore++;
					}
					if (Integer.compare(ConstantUtils.Event_Match_Result.OPPONENT_WIN.getValue(), Integer.parseInt(match.getUserWin())) == 0) {
						matchOpponentScore++;
					}
				}
				subMatchHomeScore += match.getHomeScore();
				subMatchOpponentScore += match.getOpponentScore();
				if (userHome != null) {
					UserDTO user = new UserDTO();
					user.setUserId(userHome.getUserId());
					user.setUserFullName(userHome.getUserFullName());
					user.setUserAvatar(userHome.getUserAvatar());
					matchDTO.setUserHome(user);
				}
				if (userOpponent != null) {
					UserDTO user = new UserDTO();
					user.setUserId(userOpponent.getUserId());
					user.setUserFullName(userOpponent.getUserFullName());
					user.setUserAvatar(userOpponent.getUserAvatar());
					matchDTO.setUserOpponent(user);
				}
				matchDTO.setEventMatch(match.getEventMatch());
				matchDTO.setHomeUserId(match.getHomeUserId());
				matchDTO.setHomePlayerName(match.getHomePlayerName());
				matchDTO.setOpponentUserId(match.getOpponentUserId());
				matchDTO.setOpponentPlayerName(match.getOpponentPlayerName());
				if (match.getHomeNoteBox() != null && !match.getHomeNoteBox().isEmpty()) {
					List<String> homeNoteBox = new ArrayList<String>(Arrays.asList(match.getHomeNoteBox().split(",")));
					matchDTO.setHomeNoteBox(homeNoteBox.stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList()));
				}
				if (match.getOpponentNoteBox() != null && !match.getOpponentNoteBox().isEmpty()) {
					List<String> opponentNoteBox = new ArrayList<String>(Arrays.asList(match.getOpponentNoteBox().split(",")));
					matchDTO.setOpponentNoteBox(opponentNoteBox.stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList()));
				}
				matchDTO.setScoreBox(match.getScoreBox());
				matchDTO.setSubMatchStatus(match.getSubMatchStatus());
				listMatchKendoDTO.add(matchDTO);
			}
		}
		eventKendoDTO.setMatchHomeScore(matchHomeScore);
		eventKendoDTO.setMatchOpponentScore(matchOpponentScore);
		eventKendoDTO.setSubMatchHomeScore(subMatchHomeScore);
		eventKendoDTO.setSubMatchOpponentScore(subMatchOpponentScore);
		eventKendoDTO.setListMatchKendo(listMatchKendoDTO);
		
		return eventKendoDTO;
	}
	/**
	 * 
	 * @Des convert entity to eventDTO
	 * @param EventKendo
	 * @return
	 * @throws ParseException 
	 */
	private EventKendoDTO convertEntityToDTO(EventKendo eventKendo) {
		EventKendoDTO dto = new EventKendoDTO();
		Location location = locationRepository.findByLocationIdAndIsActive(eventKendo.getLocationId(), 1);
		Team homeTeam = teamRepository.findByTeamIdAndIsActive(eventKendo.getTeamId(), 1);
		Team teamOpponent = teamRepository.findByTeamIdAndIsActive(eventKendo.getOpponentTeamId(), 1);
		User userUpdating = userRepository.findByUserIdAndIsActive(eventKendo.getUserLocked(), 1);

		if (location != null) {
			LocationDTO locationDTO = new LocationDTO();
			locationDTO.setLocationId(location.getLocationId());
			locationDTO.setLocationName(location.getLocationName());
			locationDTO.setLocationAddress(location.getLocationAddress());
			locationDTO.setLocationLat(location.getLocationLat().toString());
			locationDTO.setLocationLng(location.getLocationLng().toString());
			dto.setLocation(locationDTO);
		}
		if (homeTeam != null) {
			dto.setHomeTeam(homeTeam);
		}
		if (teamOpponent != null) {
			dto.setOpponentTeam(teamOpponent);
		}
		if (userUpdating != null) {
			UserDTO user = new UserDTO();
			user.setUserId(userUpdating.getUserId());
			user.setUserFullName(userUpdating.getUserFullName());
			user.setUserAvatar(userUpdating.getUserAvatar());
			dto.setUserUpdating(user);
		}
		dto.setEventId(eventKendo.getEventKendoId());
		dto.setTeamId(eventKendo.getTeamId());
		dto.setOpponentTeamId(eventKendo.getOpponentTeamId());
		dto.setPrivacyId(eventKendo.getPrivacyId());
		dto.setLocationId(eventKendo.getLocationId());
		dto.setEventType(eventKendo.getEventType());
		dto.setEventTitle(eventKendo.getEventTitle());
		dto.setEventLoopType(eventKendo.getEventLoopType());
		dto.setDateEvent(eventKendo.getEventDate());
		dto.setDateEventExpired(eventKendo.getEventExpired());
		dto.setEventGameType(eventKendo.getEventGameType());
		dto.setEventGround(eventKendo.getEventGround());
		dto.setEventMatch(eventKendo.getEventMatch());
		dto.setEventHomeTeam(eventKendo.getEventHomeTeam());
		dto.setEventOpponentTeam(eventKendo.getEventOpponentTeam());
		dto.setEventTournament(eventKendo.getEventTournament());
		dto.setEventSubMatchs(eventKendo.getEventSubMatchs());
		dto.setTeamColor(eventKendo.getTeamColor());
		dto.setEventHashtag(eventKendo.getEventHashtag());
		dto.setEventNotice(eventKendo.getEventNotice());
		dto.setIsApproved(eventKendo.getIsApprove());
		dto.setIsLocked(eventKendo.getIsLocked());
		dto.setApprovedBy(eventKendo.getApprovedBy());
		dto.setMatchStatus(eventKendo.getMatchStatus());
		dto.setCreatedBy(eventKendo.getCreatedBy());
		dto.setCreatedDate(eventKendo.getCreatedDate());
		dto.setUpdatedDate(eventKendo.getUpdatedDate());
		
		return dto;
	}

	public ApiResponse updateMatchKendo(EventKendoDTO eventKendoDTO, BigInteger userId) {
		ApiResponse respon = new ApiResponse();
		
		// Check oldEventKendo exists
		EventKendo oldEventKendo = eventKendoRepository.findByEventKendoIdAndIsActive(eventKendoDTO.getEventId(), 1);
		if (oldEventKendo == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "event"));
			return respon;
		}
		
		// Check updating is members in team
//		TeamMember teamMember = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(oldEventKendo.getTeamId(), userId, 1);
//		if (teamMember == null) {
//			respon.setSuccess(false);
//			respon.setMessage(messageUtils.getMessage("E013"));
//			return respon;
//		}

		// Check match is Finish
		if (ConstantUtils.Event_Match_Status.FINISH.getValue().equals(oldEventKendo.getMatchStatus())) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E032"));
			return respon;
		}
		
		// Check event isLocked
		if (oldEventKendo.getIsLocked() == 1 && userId.compareTo(oldEventKendo.getUserLocked()) != 0) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E031"));
			return respon;
		}
		
		// Update sub_match
		List<EventMatchKendo> listMatchKendo = eventMatchKendoRepository.findByEventKendoIdAndIsActive(oldEventKendo.getEventKendoId(), 1);
		List<EventMatchKendoDTO> listMatchDTO = eventKendoDTO.getListMatchKendo();
		Date date = new Date();
		boolean isPlaying = false;
		int subMatchFinish = 0;
		int matchHomeScore = 0;
		int matchOpponentScore = 0;
		int subMatchHomeScore = 0;
		int subMatchOpponentScore = 0;
		
		if (listMatchKendo != null && !listMatchKendo.isEmpty()) {
			if (listMatchDTO != null && !listMatchDTO.isEmpty()) {
				for (EventMatchKendo match : listMatchKendo) {
					EventMatchKendoDTO matchDTO = this.findMatchKendoDTO(match.getEventMatch(), listMatchDTO);
					int homeScore = 0;
					int opponentScore = 0;
					boolean isPlus = false;
					
					match.setHomeNoteBox(matchDTO.getHomeNoteBox().stream().map(Object::toString).collect(Collectors.joining(",")));
					match.setOpponentNoteBox(matchDTO.getOpponentNoteBox().stream().map(Object::toString).collect(Collectors.joining(",")));
					match.setScoreBox(matchDTO.getScoreBox());
					match.setSubMatchStatus(matchDTO.getSubMatchStatus()); // 0:Ready 1:Playing 2:Finish
					
					if (!ConstantUtils.Event_Match_Status.READY.getValue().equals(matchDTO.getSubMatchStatus())) {
						isPlaying = true;
					}
					for (Integer box : matchDTO.getHomeNoteBox()) {
						if (box != 5 && box != 8 && box != 11) { // fault-hòa-bù giờ
							homeScore++;
						}
					}
					for (Integer box : matchDTO.getOpponentNoteBox()) {
						if (box != 5 && box != 8 && box != 11) { // fault-hòa-bù giờ
							opponentScore++;
						}
					}
//					if (homeScore < 2 && opponentScore < 2 && !"8".equals(matchDTO.getScoreBox())) { // mobile send nhầm status Finish
//						match.setSubMatchStatus(ConstantUtils.Event_Match_Status.PLAYING.getValue());
//					}
					if (homeScore >= 2 || opponentScore >= 2) { // mobile send nhầm status & nhầm score
						match.setSubMatchStatus(ConstantUtils.Event_Match_Status.FINISH.getValue());
					}
					// Check status: nếu Finish => update user_win ||  0: home team win 1: opponent team win 2:Hoa
					if (ConstantUtils.Event_Match_Status.FINISH.getValue().equals(match.getSubMatchStatus())) {
						if (homeScore > opponentScore) {
							match.setUserWin(Integer.toString(ConstantUtils.Event_Match_Result.HOME_WIN.getValue())); // home win
						} else {
							match.setUserWin(Integer.toString(ConstantUtils.Event_Match_Result.OPPONENT_WIN.getValue())); // opponent win
						}
						subMatchFinish++;
						isPlus = true;
					}
					if ("8".equals(matchDTO.getScoreBox())) { // Hoa
						match.setUserWin(Integer.toString(ConstantUtils.Event_Match_Result.DRAWN.getValue()));
						match.setSubMatchStatus(ConstantUtils.Event_Match_Status.FINISH.getValue());
						if (!isPlus) subMatchFinish++;
					}
					subMatchHomeScore += homeScore;
					subMatchOpponentScore += opponentScore;
					match.setHomeScore(homeScore);
					match.setOpponentScore(opponentScore);
					match.setUpdatedDate(date);
					if (match.getUserWin() != null && !match.getUserWin().isEmpty()) {
						if (Integer.compare(ConstantUtils.Event_Match_Result.HOME_WIN.getValue(), Integer.parseInt(match.getUserWin())) == 0) {
							matchHomeScore++;
						}
						if (Integer.compare(ConstantUtils.Event_Match_Result.OPPONENT_WIN.getValue(), Integer.parseInt(match.getUserWin())) == 0) {
							matchOpponentScore++;
						}
					}
				}
			}
			eventMatchKendoRepository.saveAll(listMatchKendo);
		}

		// Lock event
		oldEventKendo.setIsLocked(1); // 0: chưa lock - 1:locked
		oldEventKendo.setUserLocked(userId);
		oldEventKendo.setUpdatedDate(date);
		if (isPlaying) {
			oldEventKendo.setMatchStatus(ConstantUtils.Event_Match_Status.PLAYING.getValue());
		}
		if (listMatchKendo.size() == subMatchFinish) { // Match is Finish
			oldEventKendo.setMatchStatus(ConstantUtils.Event_Match_Status.FINISH.getValue());
			if (matchHomeScore > matchOpponentScore) {
				oldEventKendo.setTeamWin(Integer.toString(ConstantUtils.Event_Match_Result.HOME_WIN.getValue())); // home win
			} else if (matchHomeScore < matchOpponentScore) {
				oldEventKendo.setTeamWin(Integer.toString(ConstantUtils.Event_Match_Result.OPPONENT_WIN.getValue())); // opponent win
			} else {
				oldEventKendo.setTeamWin(Integer.toString(ConstantUtils.Event_Match_Result.DRAWN.getValue())); // Hoa
			}
		}
		eventKendoRepository.save(oldEventKendo);
		
		eventKendoDTO.setMatchHomeScore(matchHomeScore);
		eventKendoDTO.setMatchOpponentScore(matchOpponentScore);
		eventKendoDTO.setSubMatchHomeScore(subMatchHomeScore);
		eventKendoDTO.setSubMatchOpponentScore(subMatchOpponentScore);
		respon.setMessage(messageUtils.getMessage("I003", "match"));
		respon.setData(eventKendoDTO);
		return respon;
	}
	
	public EventMatchKendoDTO findMatchKendoDTO(int match, List<EventMatchKendoDTO> listMatchDTO) {
		Iterator<EventMatchKendoDTO> iterator = listMatchDTO.iterator();
		while (iterator.hasNext()) {
			EventMatchKendoDTO matchDto = iterator.next();
			if (matchDto.getEventMatch() == match) {
				return matchDto;
			}
		}
		return null;
	}

	public ApiResponse lockMatchKendo(EventKendoDTO eventKendoDTO, BigInteger userId) {
		ApiResponse respon = new ApiResponse();

		// Check oldEventKendo exists
		EventKendo oldEventKendo = eventKendoRepository.findByEventKendoIdAndIsActive(eventKendoDTO.getEventId(), 1);
		if (oldEventKendo == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "event"));
			return respon;
		}

		// Check updating is members in team
//		TeamMember teamMember = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(oldEventKendo.getTeamId(), userId, 1);
//		if (teamMember == null) {
//			respon.setSuccess(false);
//			respon.setMessage(messageUtils.getMessage("E013"));
//			return respon;
//		}

		// Check event isLocked
		if (oldEventKendo.getIsLocked() == 1 && userId.compareTo(oldEventKendo.getUserLocked()) != 0) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E031"));
			return respon;
		}

		oldEventKendo.setIsLocked(1); // 0: chưa lock - 1:locked
		oldEventKendo.setUserLocked(userId);
		eventKendoRepository.save(oldEventKendo);

		respon.setData(oldEventKendo);
		return respon;
	}
	
	public ApiResponse unlockMatchKendo(EventKendoDTO eventKendoDTO, BigInteger userId) {
		ApiResponse respon = new ApiResponse();

		// Check oldEventKendo exists
		EventKendo oldEventKendo = eventKendoRepository.findByEventKendoIdAndIsActive(eventKendoDTO.getEventId(), 1);
		if (oldEventKendo == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "event"));
			return respon;
		}

		// Check updating is members in team
//		TeamMember teamMember = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(oldEventKendo.getTeamId(), userId, 1);
//		if (teamMember == null) {
//			respon.setSuccess(false);
//			respon.setMessage(messageUtils.getMessage("E013"));
//			return respon;
//		}

		// Check is userLocked
		if (userId.compareTo(oldEventKendo.getUserLocked()) != 0) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E018"));
			return respon;
		}

		oldEventKendo.setIsLocked(0); // 0: chưa lock - 1:locked
		eventKendoRepository.save(oldEventKendo);

		respon.setMessage(messageUtils.getMessage("I028", "match"));
		respon.setData(oldEventKendo);
		return respon;
	}
	
	public ApiResponse getPhotoByMatch(EventMatchKendoDTO eventMatchKendoDTO, BigInteger userId) throws Exception {
		ApiResponse respon = new ApiResponse();
		
		// Check eventKendo exists
		EventKendo eventKendo = eventKendoRepository.findByEventKendoIdAndIsActive(eventMatchKendoDTO.getEventKendoId(), 1);
		if (eventKendo == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "event"));
			return respon;
		}
		
		EventMatchKendo eventMatchKendo = eventMatchKendoRepository.findByEventMatchIdAndIsActive(eventMatchKendoDTO.getEventMatchId(), 1);
		if (eventMatchKendo == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "event"));
			return respon;
		}

		// Check updating is members in team
		TeamMember teamMember = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(eventKendo.getTeamId(), userId, 1);
		if (teamMember == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E013"));
			return respon;
		}

		respon = folderService.getAllFolderAndFile(eventKendo.getTeamId(),eventMatchKendo.getFolderId(),userId);
		return respon;
	}
	
	public ApiResponse getAllPhotoByMatch(EventKendoDTO eventKendoDTO, BigInteger userId) throws Exception {
		ApiResponse respon = new ApiResponse();
		
		// Check eventKendo exists
		EventKendo eventKendo = eventKendoRepository.findByEventKendoIdAndIsActive(eventKendoDTO.getEventId(), 1);
		if (eventKendo == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "event"));
			return respon;
		}

		// Check updating is members in team
		TeamMember teamMember = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(eventKendo.getTeamId(), userId, 1);
		if (teamMember == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E013"));
			return respon;
		}

		//get list match in event
		List<EventKendo> listChild = eventKendoRepository.findByEventParentId(eventKendoDTO.getEventId());
		List<TeamFile> listTeamFile = teamFileRepository.getAllFileInFolder(eventKendo.getTeamId(), eventKendo.getFolderId(), 1);
		if (listChild != null && !listChild.isEmpty()) {
			for(EventKendo event : listChild) {
				List<EventMatchKendo> listMatchKendo = eventMatchKendoRepository.findByEventKendoIdAndIsActive(event.getEventKendoId(), 1);
				if (listMatchKendo != null && !listMatchKendo.isEmpty()) {
					for (EventMatchKendo match : listMatchKendo) {
						listTeamFile.addAll(teamFileRepository.getAllFileInFolder(eventKendo.getTeamId(), match.getFolderId(), 1));
					}
				}
			}
		}
		
		respon.setData(listTeamFile);
		return respon;
	}
	
	public ApiResponse getFolderByEvent(EventKendoDTO eventKendoDTO, BigInteger userId) throws Exception {
		ApiResponse respon = new ApiResponse();
		
		// Check eventKendo exists
		EventKendo eventKendo = eventKendoRepository.findByEventKendoIdAndIsActive(eventKendoDTO.getEventId(), 1);
		if (eventKendo == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "event"));
			return respon;
		}

		// Check updating is members in team
		TeamMember teamMember = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(eventKendo.getTeamId(), userId, 1);
		if (teamMember == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E013"));
			return respon;
		}

		//get list match in event
		List<TeamFoldersDTO> listFolderDTO = new ArrayList<TeamFoldersDTO>();
		List<TeamFolder> listFolder = teamFolderRepository.findByParentFolderIdAndIsActive(eventKendo.getFolderId(), 1);
		if (listFolder != null && !listFolder.isEmpty()) {
			for(TeamFolder folder : listFolder) {
				TeamFoldersDTO folderDTO = folderService.convertToDTO(folder);
				listFolderDTO.add(folderDTO);
			}
		}
		
		respon.setData(listFolderDTO);
		return respon;
	}
	
	public void createFolderByEvent(EventKendo event) throws Exception {
		Team teamHome = teamRepository.findByTeamIdAndIsActive(event.getTeamId(), 1);
		Team teamAway = teamRepository.findByTeamNameAndIsActive(event.getEventOpponentTeam(), 1);
		
		if(teamHome != null) {
			//.../MATCH/TEAM_A
			String rootPath = evn.getProperty("source.image.path").toString() + "/" + event.getTeamId() + "/MATCH/" + teamHome.getTeamName().replace(" ", "") +"/";
			TeamFolder rootFolder = teamFolderRepository.findByFolderName("%"+ teamHome.getTeamName().replace(" ", ""));
			EventKendo eventKendo = eventKendoRepository.findByEventKendoIdAndIsActive(event.getEventKendoId(), 1);
			List<EventKendo> listChild = eventKendoRepository.findByEventParentId(event.getEventKendoId());
			List<EventMatchKendo> listMatch = new ArrayList<EventMatchKendo>();
			if (listChild != null && !listChild.isEmpty()) {
//				for(EventKendo match : listChild) {
					List<EventMatchKendo> listTmp = eventMatchKendoRepository.findByEventKendoIdAndIsActive(listChild.get(0).getEventKendoId(),1);
					listMatch.addAll(listTmp);
//				}
			}
			//create folder event in folder MATCH
			String folderName = "";
			if (ConstantUtils.Event_Type.MATCH.getValue().equals(event.getEventType())) {
				if(teamAway != null && teamAway.getTeamShortName() != null) {
					folderName = teamHome.getTeamShortName() +"_VS_" + teamAway.getTeamShortName() + "_" + event.getEventKendoId();//VS_TEAMABC_1
				}else {
					folderName = teamHome.getTeamShortName() +"_VS_" + getShortName(event.getEventOpponentTeam()) + "_" + event.getEventKendoId();//VS_TEAMABC_1
				}
			}
			TeamFolder folderEvent = new TeamFolder();
			folderEvent.setTeamId(event.getTeamId());
			folderEvent.setParentFolderId(rootFolder.getFolderId());
			folderEvent.setFolderName(folderName);
			folderEvent.setEventKendoId(event.getEventKendoId());
			folderEvent.setCreatedby(eventKendo.getCreatedBy());
			folderEvent.setCreatedDate(event.getCreatedDate());
			folderEvent.setUpdatedDate(event.getUpdatedDate());
			folderEvent.setIsActive(1);
			folderEvent.setFolderPathAtServer(rootPath + folderName + "/");
			teamFolderRepository.save(folderEvent);
			folderService.saveFolderToServer(rootPath,folderName);//MATCH/TEAM_A/VS_TEAM_B_1
			eventKendo.setFolderId(folderEvent.getFolderId());
			if(listMatch != null && !listMatch.isEmpty()) {
				for(EventMatchKendo match : listMatch) {
					//create folder match
					String path = null;
					TeamFolder folderMatch = new TeamFolder();
					String homePlayerName = null;
					String awayPlayerName = null;
					if(match.getHomeUserId() != null && match.getOpponentUserId() != null) {
						User homePlayer = userRepository.findByUserIdAndIsActive(match.getHomeUserId(), 1);
						homePlayerName = homePlayer.getUserFullName();
						User awayPlayer = userRepository.findByUserIdAndIsActive(match.getOpponentUserId(), 1);
						awayPlayerName = awayPlayer.getUserFullName();
					}else if(match.getOpponentUserId() == null) {
						homePlayerName = match.getHomePlayerName();
						awayPlayerName = match.getOpponentPlayerName();
					}else {
						homePlayerName = match.getHomePlayerName();
						User awayPlayer = userRepository.findByUserIdAndIsActive(match.getOpponentUserId(), 1);
						awayPlayerName = awayPlayer.getUserFullName();
					}
					folderMatch.setFolderName(homePlayerName.replace(" ", "") + "-" + awayPlayerName.replace(" ", ""));
					path = folderEvent.getFolderPathAtServer() + folderMatch.getFolderName() + "/";
					folderMatch.setTeamId(eventKendo.getTeamId());
					folderMatch.setParentFolderId(folderEvent.getFolderId());
					folderMatch.setEventKendoId(event.getEventKendoId());
					folderMatch.setCreatedby(eventKendo.getCreatedBy());
					folderMatch.setCreatedDate(match.getCreatedDate());
					folderMatch.setUpdatedDate(match.getUpdatedDate());
					folderMatch.setIsActive(1);
					folderMatch.setFolderPathAtServer(path);
					teamFolderRepository.save(folderMatch);
					match.setFolderId(folderMatch.getFolderId());
					folderService.saveFolderToServer(rootPath,folderEvent.getFolderName()+ folderMatch.getFolderName() + "/");//MATCH/TEAM_A/VS_TEAM_B_1/A-B/
				}
			}
		}
	}
	
	public String getShortName(String x) {
		String shortName = "";
	    String[] myName = x.split(" ");
	    for (int i = 0; i < myName.length; i++) {
	    	if(myName.length == 2) {
	    		String s = myName[i];
	    		if(i == 0) {
	    			shortName+= s.charAt(0) +s.charAt(1);
	    		}else {
	    			shortName+= s.charAt(0);
	    		}
		        shortName+=s.charAt(0);
	    	}else if(myName.length == 3) {
	    		String s = myName[i];
		        shortName+=s.charAt(0);
	    	}
	      
	    }
	    return shortName;
	}

	public ApiResponse searchFixture(SearchDTO search, BigInteger userId) throws ParseException {
		String fromDate = search.getFromDate();
		String toDate = search.getToDate();
		List<BigInteger> userIds = new ArrayList<BigInteger>();
		
		if (new BigInteger(ConstantUtils.Privacy.MY_TEAM.getValue()).compareTo(search.getPrivacyId()) == 0) { // 2
			List<TeamMember> listMemberInTeam = teamMemberRepository.findById_TeamIdAndIsActive(search.getTeamId(), 1);
			if (listMemberInTeam != null && !listMemberInTeam.isEmpty()) {
				for (TeamMember member : listMemberInTeam) {
					userIds.add(member.getId().getUserId());
				}
			}
		} else if (new BigInteger(ConstantUtils.Privacy.INCLUDE.getValue()).compareTo(search.getPrivacyId()) == 0) { // 3
			userIds.addAll(search.getListUserIds());
		} else { // Only me: 4
			userIds.add(userId);
		}
		if (search.getMaxResult() == 0) {
			search.setMaxResult(10);
		}

		ApiResponse respon = new ApiResponse();
		List<Map<String, Object>> listFixture = eventKendoRepository.searchFixtureKendo(ConstantUtils.Event_Type.MATCH.getValue(), 
				fromDate, toDate, search.getTeamId(), userIds, search.getFirstResult(), search.getMaxResult());
		List<EventKendoDTO> listEventKendoDTO = new ArrayList<>();
		SearchEventKendoDTO result = new SearchEventKendoDTO();
		
		if(listFixture == null || listFixture.isEmpty()) {
			respon.setData(listEventKendoDTO);
			return respon;
		}
		
		for(Map<String, Object> fixture:listFixture) {
			EventKendoDTO eventKendoDTO = convertMapEventKendoToDTO(fixture);
			eventKendoDTO = this.convertToDetailDTO(eventKendoDTO, eventKendoDTO.getEventId());
			listEventKendoDTO.add(eventKendoDTO);
		}
		
		//get total
		result.setTotal(eventKendoRepository.searchCountFixtureKendo(ConstantUtils.Event_Type.MATCH.getValue(), 
				fromDate, toDate, search.getTeamId(), userIds));
		result.setFixtures(listEventKendoDTO);
		respon.setData(result);

		return respon;
	}

	private EventKendoDTO convertMapEventKendoToDTO(Map<String, Object> fixture) throws ParseException {
		EventKendoDTO dto = new EventKendoDTO();
		BigInteger teamOpponentId = null;
		BigInteger userLocked = null;
		
		if (!StringUtils.isEmpty(fixture.get("opponent_team_id").toString())) {
			teamOpponentId = new BigInteger(fixture.get("opponent_team_id").toString());
		}
		if (!StringUtils.isEmpty(fixture.get("user_locked").toString())) {
			userLocked = new BigInteger(fixture.get("user_locked").toString());
		}

		Location location = locationRepository.findByLocationIdAndIsActive(new BigInteger(fixture.get("location_id").toString()), 1);
		Team homeTeam = teamRepository.findByTeamIdAndIsActive(new BigInteger(fixture.get("team_id").toString()), 1);
		Team teamOpponent = teamRepository.findByTeamIdAndIsActive(teamOpponentId, 1);
		User userUpdating = userRepository.findByUserIdAndIsActive(userLocked, 1);

		if (location != null) {
			LocationDTO locationDTO = new LocationDTO();
			locationDTO.setLocationId(location.getLocationId());
			locationDTO.setLocationName(location.getLocationName());
			locationDTO.setLocationAddress(location.getLocationAddress());
			locationDTO.setLocationLat(location.getLocationLat().toString());
			locationDTO.setLocationLng(location.getLocationLng().toString());
			dto.setLocation(locationDTO);
		}
		if (homeTeam != null) {
			dto.setHomeTeam(homeTeam);
		}
		if (teamOpponent != null) {
			dto.setOpponentTeam(teamOpponent);
		}
		if (userUpdating != null) {
			UserDTO user = new UserDTO();
			user.setUserId(userUpdating.getUserId());
			user.setUserFullName(userUpdating.getUserFullName());
			user.setUserAvatar(userUpdating.getUserAvatar());
			dto.setUserUpdating(user);
		}
		dto.setEventId(new BigInteger(fixture.get("event_kendo_id").toString()));
		dto.setTeamId(new BigInteger(fixture.get("team_id").toString()));
		dto.setOpponentTeamId(teamOpponentId);
		dto.setPrivacyId(new BigInteger(fixture.get("privacy_id").toString()));
		dto.setLocationId(new BigInteger(fixture.get("location_id").toString()));
		dto.setEventType(fixture.get("event_type").toString());
		dto.setEventLoopType(fixture.get("event_loop_type").toString());
		dto.setDateEvent(DateUtil.getFormatDate(fixture.get("event_date").toString(), DateUtil.PT_YYYY_MM_DD));
		dto.setEventGameType(fixture.get("event_game_type").toString());
		dto.setEventGround(Integer.parseInt(fixture.get("event_ground").toString()));
		dto.setEventMatch(Integer.parseInt(fixture.get("event_match").toString()));
		dto.setEventHomeTeam(fixture.get("event_home_team").toString());
		dto.setEventOpponentTeam(fixture.get("event_opponent_team").toString());
		dto.setEventTournament(fixture.get("event_tournament").toString());
		dto.setEventSubMatchs(Integer.parseInt(fixture.get("event_sub_matchs").toString()));
		dto.setTeamColor(fixture.get("team_color").toString());
		dto.setEventHashtag(fixture.get("event_hashtag").toString());
		dto.setEventNotice(fixture.get("event_notice").toString());
		dto.setIsLocked(Integer.parseInt(fixture.get("is_locked").toString()));
		dto.setMatchStatus(Integer.parseInt(fixture.get("match_status").toString()));
		dto.setCreatedBy(new BigInteger(fixture.get("created_by").toString()));
		dto.setCreatedDate(DateUtil.getFormatDate(fixture.get("created_date").toString(), DateUtil.PT_YYYY_MM_DD_HH_MM_SS));
		dto.setUpdatedDate(DateUtil.getFormatDate(fixture.get("updated_date").toString(), DateUtil.PT_YYYY_MM_DD_HH_MM_SS));

		return dto;
	}

	/**
	 * @Des list fixture
	 * @param search
	 * @return
	 */
	public ApiResponse listFixture(SearchDTO search, BigInteger userId) throws ParseException {
		ApiResponse respon = new ApiResponse();
		List<Map<String, Object>> listFixture = eventKendoRepository.listFixtureKendoByCurrentYear(ConstantUtils.Event_Type.MATCH.getValue(), 
				search.getTeamId(), Calendar.getInstance().get(Calendar.YEAR));
		List<EventKendoDTO> listEventKendoDTO = new ArrayList<>();
		SearchEventKendoDTO result = new SearchEventKendoDTO();

		if (listFixture == null || listFixture.isEmpty()) {
			respon.setData(listEventKendoDTO);
			return respon;
		}

		for (Map<String, Object> fixture : listFixture) {
			EventKendoDTO eventKendoDTO = convertMapEventKendoToDTO(fixture);
			eventKendoDTO = this.convertToDetailDTO(eventKendoDTO, eventKendoDTO.getEventId());
			listEventKendoDTO.add(eventKendoDTO);
		}

		// get total
		result.setTotal(eventKendoRepository.listCountFixtureKendo(ConstantUtils.Event_Type.MATCH.getValue(),
				search.getTeamId()));
		result.setFixtures(listEventKendoDTO);
		respon.setData(result);

		return respon;
	}

	/**
	 * @Des list match stats (thống kê trận đấu team vs team)
	 * @param eventKendoDTO
	 * @return
	 * @throws ParseException 
	 */
	public ApiResponse listMatchStats(EventKendoDTO eventDTO, BigInteger userId) throws ParseException {
		ApiResponse respon = new ApiResponse();
		List<Map<String, Object>> listMatchStats = eventKendoRepository.listMatchStats(
				ConstantUtils.Event_Type.MATCH.getValue(), eventDTO.getTeamId(), eventDTO.getOpponentTeamId(),
				ConstantUtils.Event_Match_Status.FINISH.getValue(), 3);
		List<EventKendoDTO> listMatchStatsDTO = new ArrayList<>();
		SearchEventKendoDTO result = new SearchEventKendoDTO();
		
		if(listMatchStats == null || listMatchStats.isEmpty()) {
			respon.setData(listMatchStatsDTO);
			return respon;
		}
		
		List<Integer> homeTeamStats = new ArrayList<Integer>();
		List<Integer> opponentTeamStats = new ArrayList<Integer>();
		List<Map<String, Object>> listHomeTeamStats = eventKendoRepository.listMatchStats(
				ConstantUtils.Event_Type.MATCH.getValue(), eventDTO.getTeamId(), null, ConstantUtils.Event_Match_Status.FINISH.getValue(), 5);
		List<Map<String, Object>> listOpponentTeamStats = eventKendoRepository.listMatchStats(
				ConstantUtils.Event_Type.MATCH.getValue(), null, eventDTO.getOpponentTeamId(), ConstantUtils.Event_Match_Status.FINISH.getValue(), 5);
		
		for(Map<String, Object> match:listMatchStats) {
			EventKendoDTO eventKendoDTO = convertMapEventKendoToDTO(match);
			eventKendoDTO = this.convertToDetailDTO(eventKendoDTO, eventKendoDTO.getEventId());
			listMatchStatsDTO.add(eventKendoDTO);
		}

		if(listHomeTeamStats != null && !listHomeTeamStats.isEmpty()) {
			for(Map<String, Object> match:listHomeTeamStats) {
				if (!StringUtils.isEmpty(match.get("team_win").toString())) {
					homeTeamStats.add(Integer.parseInt(match.get("team_win").toString()));
				} else {
					homeTeamStats.add(ConstantUtils.Event_Match_Result.DRAWN.getValue()); // Hoa
				}
			}
		}
		
		if(listOpponentTeamStats != null && !listOpponentTeamStats.isEmpty()) {
			for(Map<String, Object> match:listOpponentTeamStats) {
				if (!StringUtils.isEmpty(match.get("team_win").toString())) {
					int rs = Integer.parseInt(match.get("team_win").toString()) == ConstantUtils.Event_Match_Result.HOME_WIN.getValue()
									? ConstantUtils.Event_Match_Result.OPPONENT_WIN.getValue()
									: Integer.parseInt(match.get("team_win").toString()) == ConstantUtils.Event_Match_Result.OPPONENT_WIN.getValue()
													? ConstantUtils.Event_Match_Result.HOME_WIN.getValue() : ConstantUtils.Event_Match_Result.DRAWN.getValue();
					opponentTeamStats.add(rs);
				} else {
					opponentTeamStats.add(ConstantUtils.Event_Match_Result.DRAWN.getValue()); // Hoa
				}
			}
		}
		
		result.setListMatchStats(listMatchStatsDTO);
		result.setHomeTeamStats(homeTeamStats);
		result.setOpponentTeamStats(opponentTeamStats);
		respon.setData(result);

		return respon;
	}
}
