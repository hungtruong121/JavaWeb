package com.paracelsoft.teamsport.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the party database table.
 * 
 */
@Entity
@Table(name="notification")
@NamedQuery(name="Notification.findAll", query="SELECT p FROM Notification p")
public class Notification implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notification_id", unique = true, nullable = false, length = 20)
	private BigInteger notificationId;
	
	@Column(name = "notification_type", length = 45)
	private String notificationType;
	
	@Column(name="team_id")
	private BigInteger teamId;
	
	@Column(name="post_id")
	private BigInteger postId;
	
	@Column(name="post_comment_id")
	private BigInteger postCommentId;
	
	@Column(name = "from_object_type")
	private String fromObjectType; //team, system, user
	
	@Column(name = "notification_from_id")
	private BigInteger notificationFromId;
	
	@Column(name = "notification_to_id")
	private BigInteger notificationToId;
	
	@Column(name = "notification_content")
	private String notificationContent;
	
	@Column(name = "notification_mess_code")
	private String notificationMessCode;
	
	@Column(name = "notification_action", length = 255)
	private String notificationAction;
	
	@Column(name="is_read")
	private int isRead = 0;
	
	@Column(name="created_by")
	private BigInteger createdBy;
	
	@Column(name="is_active")
	private int isActive = 1;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_date")
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
	 * @return the isActive
	 */
	public int getIsActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(int isActive) {
		this.isActive = isActive;
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
	 * @return the createdBy
	 */
	public BigInteger getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(BigInteger createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the notificationMessCode
	 */
	public String getNotificationMessCode() {
		return notificationMessCode;
	}

	/**
	 * @param notificationMessCode the notificationMessCode to set
	 */
	public void setNotificationMessCode(String notificationMessCode) {
		this.notificationMessCode = notificationMessCode;
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

}