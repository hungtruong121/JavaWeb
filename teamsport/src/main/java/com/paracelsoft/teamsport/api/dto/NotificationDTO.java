package com.paracelsoft.teamsport.api.dto;

import java.math.BigInteger;
import java.util.Date;

public class NotificationDTO implements Comparable<NotificationDTO>{

	private BigInteger notificationId;
	
	private String notificationType;
	
	private BigInteger teamId;
	
	private BigInteger postId;
	
	private BigInteger postCommentId;
	
	private String fromObjectType; //team, system, user
	
	private BigInteger notificationFromId;
	
	private BigInteger notificationFromAvatar = new BigInteger("1");
	
	private BigInteger notificationFromName;
	
	private BigInteger notificationToId;
	
	private String notificationContent;
	
	private String notificationAction;
	
	private int isRead = 0;
	
	private String mobileScreenKey;
	
	private Date createdDate;
	
	private Date updatedDate;

	/**
	 * @return the notificationId
	 */
	public BigInteger getNotificationId() {
		return notificationId;
	}

	/**
	 * @param notificationId the notificationId to set
	 */
	public void setNotificationId(BigInteger notificationId) {
		this.notificationId = notificationId;
	}

	/**
	 * @return the notificationType
	 */
	public String getNotificationType() {
		return notificationType;
	}

	/**
	 * @param notificationType the notificationType to set
	 */
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	/**
	 * @return the teamId
	 */
	public BigInteger getTeamId() {
		return teamId;
	}

	/**
	 * @param teamId the teamId to set
	 */
	public void setTeamId(BigInteger teamId) {
		this.teamId = teamId;
	}

	/**
	 * @return the notificationFromId
	 */
	public BigInteger getNotificationFromId() {
		return notificationFromId;
	}

	/**
	 * @param notificationFromId the notificationFromId to set
	 */
	public void setNotificationFromId(BigInteger notificationFromId) {
		this.notificationFromId = notificationFromId;
	}

	/**
	 * @return the notificationFromAvatar
	 */
	public BigInteger getNotificationFromAvatar() {
		return notificationFromAvatar;
	}

	/**
	 * @param notificationFromAvatar the notificationFromAvatar to set
	 */
	public void setNotificationFromAvatar(BigInteger notificationFromAvatar) {
		this.notificationFromAvatar = notificationFromAvatar;
	}

	/**
	 * @return the notificationFromName
	 */
	public BigInteger getNotificationFromName() {
		return notificationFromName;
	}

	/**
	 * @param notificationFromName the notificationFromName to set
	 */
	public void setNotificationFromName(BigInteger notificationFromName) {
		this.notificationFromName = notificationFromName;
	}

	/**
	 * @return the notificationToId
	 */
	public BigInteger getNotificationToId() {
		return notificationToId;
	}

	/**
	 * @param notificationToId the notificationToId to set
	 */
	public void setNotificationToId(BigInteger notificationToId) {
		this.notificationToId = notificationToId;
	}

	/**
	 * @return the notificationContent
	 */
	public String getNotificationContent() {
		return notificationContent;
	}

	/**
	 * @param notificationContent the notificationContent to set
	 */
	public void setNotificationContent(String notificationContent) {
		this.notificationContent = notificationContent;
	}

	/**
	 * @return the notificationAction
	 */
	public String getNotificationAction() {
		return notificationAction;
	}

	/**
	 * @param notificationAction the notificationAction to set
	 */
	public void setNotificationAction(String notificationAction) {
		this.notificationAction = notificationAction;
	}

	/**
	 * @return the isRead
	 */
	public int getIsRead() {
		return isRead;
	}

	/**
	 * @param isRead the isRead to set
	 */
	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	/**
	 * @return the mobileScreenKey
	 */
	public String getMobileScreenKey() {
		return mobileScreenKey;
	}

	/**
	 * @param mobileScreenKey the mobileScreenKey to set
	 */
	public void setMobileScreenKey(String mobileScreenKey) {
		this.mobileScreenKey = mobileScreenKey;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the fromObjectType
	 */
	public String getFromObjectType() {
		return fromObjectType;
	}

	/**
	 * @param fromObjectType the fromObjectType to set
	 */
	public void setFromObjectType(String fromObjectType) {
		this.fromObjectType = fromObjectType;
	}

	/**
	 * @return the postId
	 */
	public BigInteger getPostId() {
		return postId;
	}

	/**
	 * @param postId the postId to set
	 */
	public void setPostId(BigInteger postId) {
		this.postId = postId;
	}

	/**
	 * @return the postCommentId
	 */
	public BigInteger getPostCommentId() {
		return postCommentId;
	}

	/**
	 * @param postCommentId the postCommentId to set
	 */
	public void setPostCommentId(BigInteger postCommentId) {
		this.postCommentId = postCommentId;
	}
	
	@Override
    public int compareTo(NotificationDTO o) {
		return getCreatedDate().compareTo(o.getCreatedDate());
    }

	
}
