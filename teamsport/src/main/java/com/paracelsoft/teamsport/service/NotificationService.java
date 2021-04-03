package com.paracelsoft.teamsport.service;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paracelsoft.teamsport.api.dto.NotificationDTO;
import com.paracelsoft.teamsport.api.dto.SearchDTO;
import com.paracelsoft.teamsport.api.dto.TeamDTO;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.entity.Notification;
import com.paracelsoft.teamsport.entity.Post;
import com.paracelsoft.teamsport.entity.PostComment;
import com.paracelsoft.teamsport.entity.Team;
import com.paracelsoft.teamsport.entity.User;
import com.paracelsoft.teamsport.repository.NotificationRepository;
import com.paracelsoft.teamsport.repository.PostCommentRepository;
import com.paracelsoft.teamsport.repository.PostRepository;
import com.paracelsoft.teamsport.repository.TeamFolderRepository;
import com.paracelsoft.teamsport.repository.TeamRepository;
import com.paracelsoft.teamsport.repository.UserRepository;
import com.paracelsoft.teamsport.util.ConstantUtils;
import com.paracelsoft.teamsport.util.MessageUtils;

@Transactional(rollbackFor = { Exception.class, ParseException.class })
@Service("notificationService")
public class NotificationService {
	
	@Autowired
	NotificationRepository notificationRepository;
	
	@Autowired
	MessageUtils messageUtils;
	
	@Autowired
	TeamRepository teamRepository;
	
	@Autowired
	FirebaseService firebaseService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	PostCommentRepository postCommentRepository;
	
	@Autowired
	TeamFolderRepository teamFolderRepository;
	
	/**
	 * 
	 * @Des save list notifi to team --> list user to is list admin
	 * @param notification
	 * @param createdBy
	 * @return
	 * @throws JSONException 
	 * @throws NoSuchMessageException 
	 */
	public void notificationFromUserToTeam(User userFrom, List<User> userTo, BigInteger teamId, 
			String messageCode, String actionNavigate,String notificationType) throws NoSuchMessageException, JSONException {
		Team team = teamRepository.findByTeamIdAndIsActive(teamId, 1);
		if(team == null) {
			return;
		}
		
		if(userTo != null && !userTo.isEmpty()) {
			for(User user:userTo) {
				Notification entity = new Notification();
				entity.setNotificationId(null);
				entity.setTeamId(teamId);
				entity.setFromObjectType(ConstantUtils.Notification_From_Object_Type.USER_TO_TEAM.getValue());
				entity.setNotificationAction(actionNavigate);
				//send to firebase
				entity.setNotificationMessCode(messageCode);
				entity.setNotificationFromId(userFrom.getUserId());
				entity.setNotificationToId(user.getUserId());
				entity.setNotificationType(notificationType);
				entity.setIsActive(1);
				entity.setIsRead(0);
				entity.setCreatedBy(userFrom.getUserId());
				entity.setCreatedDate(new Date());
				entity.setUpdatedDate(new Date());
				entity = notificationRepository.save(entity);
				
				//send to firebase
				JSONObject listMesI18n = messageUtils.getListNotification("frb."+messageCode, userFrom.getUserFullName());
				entity.setNotificationContent(listMesI18n.toString());
				firebaseService.send(user.getUserId(), entity);
			}
		}
		
		return;
	}
	
	/**
	 * @Des Inform to team about expiry day
	 */
	public void notificationFromSystemToTeam(BigInteger SysImage ,List<User> userTo, BigInteger teamId, 
			String messageCode, String actionNavigate,String notificationType) throws NoSuchMessageException, JSONException {
		Team team = teamRepository.findByTeamIdAndIsActive(teamId, 1);
		if(team == null) {
			return;
		}
		if(userTo != null) {
			for(User user:userTo) {
				Notification entity = new Notification();
				entity.setNotificationId(null);
				entity.setTeamId(teamId);
				entity.setFromObjectType(ConstantUtils.Notification_From_Object_Type.SYSTEM_TO_TEAM.getValue());
				entity.setNotificationAction(actionNavigate);
				//send to firebase
				entity.setNotificationMessCode(messageCode);
				entity.setNotificationToId(user.getUserId());
				entity.setNotificationType(notificationType);
				entity.setIsActive(1);
				entity.setIsRead(0);
				entity.setCreatedDate(new Date());
				entity.setUpdatedDate(new Date());
				entity = notificationRepository.save(entity);
				//send to firebase
				JSONObject listMesI18n = messageUtils.getListNotification("frb."+messageCode);
				entity.setNotificationContent(listMesI18n.toString());
				firebaseService.send(user.getUserId(), entity);
			}
		}
		return;
	}
	
	/**
	 * 
	 * @param userIdFrom
	 * @param userTo
	 * @param messageCode
	 * @param postId
	 * @param mobileScreen
	 * @param notificationType
	 * @throws JSONException 
	 * @throws NoSuchMessageException 
	 */
	public void notificationFromUserToPost(BigInteger userIdFrom, BigInteger userTo, String messageCode, BigInteger postId, String actionNavigate,
			String notificationType) throws NoSuchMessageException, JSONException {
		
		User user = userRepository.findByUserIdAndIsActive(userIdFrom, 1);
		if(user == null) {
			return;
		}
		//find team by post
		Post post = postRepository.findByPostIdAndIsActive(postId, 1);
		if(post == null) {
			return;
		}
		
		Notification entity = new Notification();
		entity.setNotificationId(null);
		entity.setTeamId(post.getTeamId());
		entity.setFromObjectType(ConstantUtils.Notification_From_Object_Type.USER_TO_POST.getValue());
		entity.setNotificationAction(actionNavigate);
		//send to firebase
		entity.setNotificationMessCode(messageCode);
		entity.setNotificationFromId(userIdFrom);
		entity.setNotificationToId(userTo);
		entity.setNotificationType(notificationType);
		entity.setPostId(postId);
		entity.setIsActive(1);
		entity.setIsRead(0);
		entity.setCreatedBy(userIdFrom);
		entity.setCreatedDate(new Date());
		entity.setUpdatedDate(new Date());
		entity = notificationRepository.save(entity);
		
		//send to firebase
		JSONObject listMesI18n = messageUtils.getListNotification("frb."+messageCode, user.getUserFullName());
		entity.setNotificationContent(listMesI18n.toString());
		firebaseService.send(user.getUserId(), entity);

	}

	/**
	 * 
	 * @param userIdFrom
	 * @param userTo
	 * @param messageCode
	 * @param postId
	 * @param postCommentId
	 * @param mobileScreen
	 * @param notificationType
	 * @throws JSONException 
	 * @throws NoSuchMessageException 
	 */
	public void notificationFromUserToComment(BigInteger userIdFrom, BigInteger userTo, String messageCode, 
			BigInteger postId, BigInteger postCommentId, String actionNavigate, String notificationType) throws NoSuchMessageException, JSONException {

		User user = userRepository.findByUserIdAndIsActive(userIdFrom, 1);
		if(user == null) {
			return;
		}
		//find team by post
		Post post = postRepository.findByPostIdAndIsActive(postId, 1);
		if(post == null) {
			return;
		}
		
		Notification entity = new Notification();
		entity.setNotificationId(null);
		entity.setTeamId(post.getTeamId());
		entity.setFromObjectType(ConstantUtils.Notification_From_Object_Type.USER_TO_COMMENT.getValue());
		entity.setNotificationAction(actionNavigate);
		//send to firebase
		entity.setNotificationMessCode(messageCode);
		entity.setNotificationFromId(userIdFrom);
		entity.setNotificationToId(userTo);
		entity.setNotificationType(notificationType);
		entity.setPostId(postId);
		entity.setPostCommentId(postCommentId);
		entity.setIsActive(1);
		entity.setIsRead(0);
		entity.setCreatedBy(userIdFrom);
		entity.setCreatedDate(new Date());
		entity.setUpdatedDate(new Date());
		entity = notificationRepository.save(entity);
		
		//send to firebase
		JSONObject listMesI18n = messageUtils.getListNotification("frb."+messageCode, user.getUserFullName());
		entity.setNotificationContent(listMesI18n.toString());
		firebaseService.send(user.getUserId(), entity);
		
	}
	
	public void notificationFromTeamToUser(BigInteger teamIdFrom, BigInteger userTo, BigInteger loginUser, String messageCode,
			String actionNavigate, String notificationType) throws NoSuchMessageException, JSONException {

		Team team = teamRepository.findByTeamIdAndIsActive(teamIdFrom, 1);
		if(team == null) {
			return;
		}
		
		Notification entity = new Notification();
		entity.setNotificationId(null);
		entity.setTeamId(teamIdFrom);
		entity.setFromObjectType(ConstantUtils.Notification_From_Object_Type.TEAM_TO_USER.getValue());
		entity.setNotificationAction(actionNavigate);
		//send to firebase
		entity.setNotificationMessCode(messageCode);
		entity.setNotificationFromId(teamIdFrom);
		entity.setNotificationToId(userTo);
		entity.setNotificationType(notificationType);
		entity.setIsActive(1);
		entity.setIsRead(0);
		entity.setCreatedBy(loginUser);
		entity.setCreatedDate(new Date());
		entity.setUpdatedDate(new Date());
		entity = notificationRepository.save(entity);
		
		//send to firebase
		JSONObject listMesI18n = messageUtils.getListNotification("frb."+messageCode, team.getTeamName());
		entity.setNotificationContent(listMesI18n.toString());
		firebaseService.send(userTo, entity);
		
	}
	
	/**
	 * 
	 * @Des save list notifi from team to team --> list user to is list admin
	 * @param notification
	 * @param createdBy
	 * @return
	 * @throws JSONException 
	 * @throws NoSuchMessageException 
	 */
	public void notificationFromTeamToTeam(BigInteger teamIdFrom, List<User> userTo, BigInteger loginUser, String messageCode,
			String actionNavigate, String notificationType) throws NoSuchMessageException, JSONException {

		Team team = teamRepository.findByTeamIdAndIsActive(teamIdFrom, 1);
		if(team == null) {
			return;
		}
		
		if(userTo != null && !userTo.isEmpty()) {
			for(User user:userTo) {
				Notification entity = new Notification();
				entity.setNotificationId(null);
				entity.setTeamId(teamIdFrom);
				entity.setFromObjectType(ConstantUtils.Notification_From_Object_Type.TEAM_TO_TEAM.getValue());
				entity.setNotificationAction(actionNavigate);
				//send to firebase
				entity.setNotificationMessCode(messageCode);
				entity.setNotificationFromId(teamIdFrom);
				entity.setNotificationToId(user.getUserId());
				entity.setNotificationType(notificationType);
				entity.setIsActive(1);
				entity.setIsRead(0);
				entity.setCreatedBy(loginUser);
				entity.setCreatedDate(new Date());
				entity.setUpdatedDate(new Date());
				entity = notificationRepository.save(entity);
				
				//send to firebase
				JSONObject listMesI18n = messageUtils.getListNotification("frb."+messageCode, team.getTeamName());
				entity.setNotificationContent(listMesI18n.toString());
				firebaseService.send(user.getUserId(), entity);
			}
		}
		
		return;
	}
	
	/**
	 * 
	 * @param dto
	 * @param createdBy
	 * @return
	 */
	public Notification saveNotification(NotificationDTO dto, BigInteger createdBy) {
		Notification entity = new Notification();
		entity.setNotificationId(null);
		entity.setTeamId(dto.getTeamId());
		entity.setFromObjectType(dto.getFromObjectType());
		entity.setNotificationAction(dto.getNotificationAction());
		entity.setNotificationContent(dto.getNotificationContent());
		entity.setNotificationFromId(dto.getNotificationFromId());
		entity.setNotificationToId(dto.getNotificationToId());
		entity.setNotificationType(dto.getNotificationType());
		entity.setIsActive(1);
		entity.setIsRead(0);
		entity.setCreatedBy(createdBy);
		entity.setCreatedDate(new Date());
		entity.setUpdatedDate(new Date());
		entity = notificationRepository.save(entity);
		return entity;
	}

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public ApiResponse updateIsReadAllNotifi(BigInteger teamId,BigInteger userId) {
		ApiResponse respon = new ApiResponse();
		notificationRepository.readAllNotifi(teamId,userId);
		respon.setMessage(messageUtils.getMessage("I013"));
		return respon;
	}
	
	/**
	 * 
	 * @param dto
	 * @param createdBy
	 * @return
	 * @throws JSONException 
	 * @throws NoSuchMessageException 
	 */
	public NotificationDTO convertToDTO(Notification entity){
		NotificationDTO dto = new NotificationDTO();
		dto.setNotificationId(entity.getNotificationId());
		dto.setNotificationContent(entity.getNotificationContent());
		dto.setTeamId(entity.getTeamId());
		dto.setFromObjectType(entity.getFromObjectType());
		dto.setMobileScreenKey(entity.getNotificationAction());
		dto.setNotificationFromId(entity.getNotificationFromId());
		dto.setNotificationToId(entity.getNotificationToId());
		dto.setNotificationType(entity.getNotificationType());
		dto.setPostId(entity.getPostId());
		dto.setPostCommentId(entity.getPostCommentId());
		dto.setIsRead(entity.getIsRead());
		dto.setCreatedDate(entity.getCreatedDate());
		dto.setUpdatedDate(entity.getUpdatedDate());
		//mess notification i18n
		try {
			//get avatar notification
			if(ConstantUtils.Notification_From_Object_Type.TEAM_TO_USER.getValue().equals(entity.getFromObjectType())){
				Team team = teamRepository.findByTeamIdAndIsActive(entity.getNotificationFromId(), 1);
				if(team != null) {
					dto.setNotificationFromAvatar(team.getTeamAvatar());
					if(!StringUtils.isEmpty(entity.getNotificationMessCode())) {
						dto.setNotificationContent(messageUtils.getNotification("app."+entity.getNotificationMessCode(), team.getTeamName()));
					}
				}
				
			}else if(ConstantUtils.Notification_From_Object_Type.TEAM_TO_TEAM.getValue().equals(entity.getFromObjectType())){
				Team team = teamRepository.findByTeamIdAndIsActive(entity.getNotificationFromId(), 1);
				if(team != null) {
					dto.setNotificationFromAvatar(team.getTeamAvatar());
					if(!StringUtils.isEmpty(entity.getNotificationMessCode())) {
						dto.setNotificationContent(messageUtils.getNotification("app."+entity.getNotificationMessCode(), team.getTeamName()));
					}
				}
			}else if(ConstantUtils.Notification_From_Object_Type.TEAM_TO_SYS.getValue().equals(entity.getFromObjectType())){
				Team team = teamRepository.findByTeamIdAndIsActive(dto.getNotificationFromId(), 1);
				if(team != null) {
					dto.setNotificationFromAvatar(team.getTeamAvatar());
				}
			}else if(ConstantUtils.Notification_From_Object_Type.USER_TO_TEAM.getValue().equals(entity.getFromObjectType())){
				User user = userRepository.findByUserIdAndIsActive(entity.getNotificationFromId(), 1);
				if(user != null) {
					dto.setNotificationFromAvatar(user.getUserAvatar());
					if(!StringUtils.isEmpty(entity.getNotificationMessCode())) {
						dto.setNotificationContent(messageUtils.getNotification("app."+entity.getNotificationMessCode(), user.getUserFullName()));
					}
				}
			}else if(ConstantUtils.Notification_From_Object_Type.USER_TO_USER.getValue().equals(entity.getFromObjectType())){
				User user = userRepository.findByUserIdAndIsActive(entity.getNotificationFromId(), 1);
				if(user != null) {
					dto.setNotificationFromAvatar(user.getUserAvatar());
				}
			}else if(ConstantUtils.Notification_From_Object_Type.USER_TO_POST.getValue().equals(entity.getFromObjectType())){
				User user = userRepository.findByUserIdAndIsActive(entity.getNotificationFromId(), 1);
				if(user != null) {
					dto.setNotificationFromAvatar(user.getUserAvatar());
					if(!StringUtils.isEmpty(entity.getNotificationMessCode())) {
						dto.setNotificationContent(messageUtils.getNotification("app."+entity.getNotificationMessCode(), user.getUserFullName()));
					}
				}
			}else if(ConstantUtils.Notification_From_Object_Type.USER_TO_COMMENT.getValue().equals(entity.getFromObjectType())){
				User user = userRepository.findByUserIdAndIsActive(entity.getNotificationFromId(), 1);
				if(user != null) {
					dto.setNotificationFromAvatar(user.getUserAvatar());
					PostComment comment = postCommentRepository.findByPostCommentIdAndIsActive(entity.getPostCommentId(), 1);
					String commString = comment != null && !StringUtils.isEmpty(comment.getPostCommentContent())
							?StringUtils.abbreviate(comment.getPostCommentContent(), 10).trim():"";
					if(!StringUtils.isEmpty(entity.getNotificationMessCode())) {
						dto.setNotificationContent(messageUtils.getNotification("app."+entity.getNotificationMessCode(), user.getUserFullName(), commString));
					}
				}
				
			}else if(ConstantUtils.Notification_From_Object_Type.USER_TO_SURVEY.getValue().equals(entity.getFromObjectType())){
				User user = userRepository.findByUserIdAndIsActive(entity.getNotificationFromId(), 1);
				if(user != null) {
					dto.setNotificationFromAvatar(user.getUserAvatar());
				}
			}else if(ConstantUtils.Notification_From_Object_Type.USER_TO_TODO_TASK.getValue().equals(entity.getFromObjectType())){
				User user = userRepository.findByUserIdAndIsActive(entity.getNotificationFromId(), 1);
				if(user != null) {
					dto.setNotificationFromAvatar(user.getUserAvatar());
				}
			}else if(ConstantUtils.Notification_From_Object_Type.SYSTEM_TO_TEAM.getValue().equals(entity.getFromObjectType())){
				dto.setNotificationFromAvatar(new BigInteger(ConstantUtils.Default_Image.SYSTEM_DEFAULT.getValue()));
			}else if(ConstantUtils.Notification_From_Object_Type.SYSTEM_TO_USER.getValue().equals(entity.getFromObjectType())){
				dto.setNotificationFromAvatar(new BigInteger(ConstantUtils.Default_Image.SYSTEM_DEFAULT.getValue()));
			}
		} catch (NoSuchMessageException e) {
			e.printStackTrace();
			return null;
		}
		return dto;
	}

	/**
	 * 
	 * @param notificationid
	 * @return
	 */
	public ApiResponse updateIsReadNotifi(BigInteger notificationid) {
		ApiResponse respon = new ApiResponse();
		
		Notification notifi = notificationRepository.findByNotificationIdAndIsActive(notificationid, 1);
		if(notifi == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "notification"));
			return respon;
		}
		
		notifi.setIsRead(1);
		notifi.setUpdatedDate(new Date());
		notificationRepository.save(notifi);
		return respon;
	}

	/**
	 * 
	 * @Des delete notification by id
	 * @param notificationId
	 */
	public void deleteNotification(BigInteger notificationId) {
		notificationRepository.deleteById(notificationId);
	}
	
	public List<NotificationDTO> getAllNotificationByTeamAnUser(BigInteger teamId, BigInteger userId, int firstResult, int maxResult){
		List<NotificationDTO> dtos = new ArrayList<NotificationDTO>();
		List<NotificationDTO> dtoRequest = new ArrayList<NotificationDTO>();
		List<NotificationDTO> dtosNormal = new ArrayList<NotificationDTO>();
		
 		List<Notification> notifications = notificationRepository.
 				findAllByTeamIdAndNotificationToIdAndIsActiveOrderByNotificationTypeAsc(teamId, userId, 1, firstResult, maxResult);
		if(notifications != null && !notifications.isEmpty()) {
			for(Notification obj:notifications) {
				NotificationDTO notifi = this.convertToDTO(obj);//convert
				//check no mess content then not show client
				if(notifi != null && !StringUtils.isEmpty(notifi.getNotificationContent())) {
					if(ConstantUtils.Notification_Type.NOTIFI_INVITE_JOIN_TEAM.getValue().equals(obj.getNotificationType())
							|| ConstantUtils.Notification_Type.NOTIFI_REQUEST_JOIN_TEAM.getValue().equals(obj.getNotificationType())) {
						dtoRequest.add(notifi);
					}else {
						dtosNormal.add(notifi);
					}
				}
			}
		}
		Collections.sort(dtoRequest);
		Collections.sort(dtosNormal);
		dtos.addAll(dtoRequest);
		dtos.addAll(dtosNormal);
		return dtos;
	}
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<TeamDTO> getListTeamAndNotifiByUserId(SearchDTO request, BigInteger userId) {
		List<TeamDTO> teamDto = new ArrayList<TeamDTO>();
		
		//get notification of team chosed
		Team teamChosed = teamRepository.findByTeamIdAndIsActive(request.getTeamId(), 1);
		if(teamChosed != null) {
			TeamDTO dto = new TeamDTO(); //team invite
			dto.setTeamId(teamChosed.getTeamId());
			dto.setTeamName(teamChosed.getTeamName());
			dto.setTeamAvatar(teamChosed.getTeamAvatar() != null?teamChosed.getTeamAvatar():new BigInteger("1"));
			dto.setNotifications(this.getAllNotificationByTeamAnUser(teamChosed.getTeamId(), 
					userId, request.getFirstResult(), request.getMaxResult()));
			//count notifi
			dto.setTotalUnreadNotification(notificationRepository.
					countByTeamIdAndNotificationToIdAndIsReadAndIsActive(teamChosed.getTeamId(), userId, 0, 1));
			teamDto.add(dto);
		}
		
		//get list notifi invite user
//		List<Notification> listNotifiInvite = notificationRepository.findAllByNotificationToIdAndNotificationTypeAndIsActive(userId, 
//				ConstantUtils.Notification_Type.NOTIFI_INVITE_JOIN_TEAM.getValue(), 1,
//				request.getFirstResult(), request.getMaxResult());
//		if(listNotifiInvite != null && !listNotifiInvite.isEmpty()) {
//			for(Notification invite:listNotifiInvite) {
//				Team teamInvite = teamRepository.findByTeamIdAndIsActive(invite.getTeamId(), 1);
//				if(teamInvite != null) {
//					TeamDTO dto = new TeamDTO(); //team invite
//					dto.setTeamId(teamInvite.getTeamId());
//					dto.setTeamName(teamInvite.getTeamName());
//					dto.setTeamAvatar(teamInvite.getTeamAvatar() != null?teamInvite.getTeamAvatar():new BigInteger("1"));
//					if(request.getTeamId() == null) {
//						dto.setNotifications(this.getAllNotificationByTeamAnUser(teamInvite.getTeamId(), 
//								userId, request.getFirstResult(), request.getMaxResult()));
//					}
//					//count notifi
//					dto.setTotalUnreadNotification(notificationRepository.
//							countByTeamIdAndNotificationToIdAndIsActive(teamInvite.getTeamId(), userId, 1));
//					teamDto.add(dto);
//				}
//			}
//		}
		
		//list team joinned devide chosed team
		List<Team> userJoined = teamRepository.getTeamByUserAndTeamNot(userId, request.getTeamId());
		if(userJoined == null || userJoined.isEmpty()) {
			return teamDto;
		}
		
		//convert team dto and get notification first
		for(Team item:userJoined) {
			if(request.getTeamId().compareTo(item.getTeamId()) != 0) {
				TeamDTO dto = new TeamDTO();
				dto.setTeamId(item.getTeamId());
				dto.setTeamName(item.getTeamName());
				dto.setTeamAvatar(item.getTeamAvatar() != null?item.getTeamAvatar():new BigInteger("1"));
				if(request.getTeamId() == null) {
					dto.setNotifications(this.getAllNotificationByTeamAnUser(item.getTeamId(), 
							userId, request.getFirstResult(), request.getMaxResult()));
				}
				//count notifi
				dto.setTotalUnreadNotification(notificationRepository.
						countByTeamIdAndNotificationToIdAndIsReadAndIsActive(item.getTeamId(), userId, 0, 1));
				teamDto.add(dto);
			}
		}
		return teamDto;
	}
	
	/**
	 * 
	 * @param teamId
	 * @param userId
	 * @return
	 */
	public ApiResponse countNotification(BigInteger teamId, BigInteger userId) {
		ApiResponse respon = new ApiResponse();
		respon.setData(notificationRepository.
						countByTeamIdAndNotificationToIdAndIsReadAndIsActive(teamId, userId, 0, 1));
		return respon;
	}


}
