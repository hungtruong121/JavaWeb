package com.paracelsoft.teamsport.api.dto;

import java.math.BigInteger;

public class PostLikeDTO {
	
	private BigInteger postId;
	
	private BigInteger userId;
	
	private String createdByName;
	
	private BigInteger createdByAvatar;
	
	private BigInteger postCommentId; //like comment

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

	/**
	 * @return the createdByName
	 */
	public String getCreatedByName() {
		return createdByName;
	}

	/**
	 * @param createdByName the createdByName to set
	 */
	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	/**
	 * @return the createdByAvatar
	 */
	public BigInteger getCreatedByAvatar() {
		return createdByAvatar;
	}

	/**
	 * @param createdByAvatar the createdByAvatar to set
	 */
	public void setCreatedByAvatar(BigInteger createdByAvatar) {
		this.createdByAvatar = createdByAvatar;
	}

}
