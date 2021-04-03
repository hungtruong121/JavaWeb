package com.paracelsoft.teamsport.api.dto;

import java.math.BigInteger;

public class RequestDTO {
	
	private BigInteger userId;
	
	private BigInteger teamId;
	
	private BigInteger postId;
	
	private BigInteger uploadId;
	
	private BigInteger notificationId;
	
	private int isAccept; //0:No, 1:Yes
	
	private String userMail;
	
	private String otp;
	
	private String newPassword; //check pass otp
	
	private String positionSport;
	
	private String teamMemberRole;

	/**
	 * @return the userId
	 */
	public BigInteger getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(BigInteger userId) {
		this.userId = userId;
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
	 * @return the uploadId
	 */
	public BigInteger getUploadId() {
		return uploadId;
	}

	/**
	 * @param uploadId the uploadId to set
	 */
	public void setUploadId(BigInteger uploadId) {
		this.uploadId = uploadId;
	}

	/**
	 * @return the userMail
	 */
	public String getUserMail() {
		return userMail;
	}

	/**
	 * @param userMail the userMail to set
	 */
	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	/**
	 * @return the otp
	 */
	public String getOtp() {
		return otp;
	}

	/**
	 * @param otp the otp to set
	 */
	public void setOtp(String otp) {
		this.otp = otp;
	}

	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * @return the isAccept
	 */
	public int getIsAccept() {
		return isAccept;
	}

	/**
	 * @param isAccept the isAccept to set
	 */
	public void setIsAccept(int isAccept) {
		this.isAccept = isAccept;
	}

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
	 * @return the positionSport
	 */
	public String getPositionSport() {
		return positionSport;
	}

	/**
	 * @param positionSport the positionSport to set
	 */
	public void setPositionSport(String positionSport) {
		this.positionSport = positionSport;
	}

	/**
	 * @return the teamMemberRole
	 */
	public String getTeamMemberRole() {
		return teamMemberRole;
	}

	/**
	 * @param teamMemberRole the teamMemberRole to set
	 */
	public void setTeamMemberRole(String teamMemberRole) {
		this.teamMemberRole = teamMemberRole;
	}

}
