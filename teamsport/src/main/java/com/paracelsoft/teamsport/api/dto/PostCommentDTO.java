package com.paracelsoft.teamsport.api.dto;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class PostCommentDTO {
	
	private BigInteger postCommentId;
	
	private BigInteger postCommentParentId;
	
	private BigInteger postId;
	
	private BigInteger userId;
	
	private String createdByName;
	
	private BigInteger createdByAvatar;
	
	private String gif_id;
	
	private String postCommentContent;
	
	private BigInteger postCommentImage; //uplopadId
	
	private int isLiked;
	
	private int isActive = 1;
	
	private Date createdDate;
	
	private Date updatedDate;
	
	List<UserDTO> userInclude;
	
	List<PostCommentDTO> childComment;
	
	private Integer numberOfLikes;
	
	private Integer numberOfComments;

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
	 * @return the postCommentParentId
	 */
	public BigInteger getPostCommentParentId() {
		return postCommentParentId;
	}

	/**
	 * @param postCommentParentId the postCommentParentId to set
	 */
	public void setPostCommentParentId(BigInteger postCommentParentId) {
		this.postCommentParentId = postCommentParentId;
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
	 * @return the gif_id
	 */
	public String getGif_id() {
		return gif_id;
	}

	/**
	 * @param gif_id the gif_id to set
	 */
	public void setGif_id(String gif_id) {
		this.gif_id = gif_id;
	}

	/**
	 * @return the postCommentContent
	 */
	public String getPostCommentContent() {
		return postCommentContent;
	}

	/**
	 * @param postCommentContent the postCommentContent to set
	 */
	public void setPostCommentContent(String postCommentContent) {
		this.postCommentContent = postCommentContent;
	}

	/**
	 * @return the postCommentImage
	 */
	public BigInteger getPostCommentImage() {
		return postCommentImage;
	}

	/**
	 * @param postCommentImage the postCommentImage to set
	 */
	public void setPostCommentImage(BigInteger postCommentImage) {
		this.postCommentImage = postCommentImage;
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
	 * @return the childComment
	 */
	public List<PostCommentDTO> getChildComment() {
		return childComment;
	}

	/**
	 * @param childComment the childComment to set
	 */
	public void setChildComment(List<PostCommentDTO> childComment) {
		this.childComment = childComment;
	}

	/**
	 * @return the isLiked
	 */
	public int getIsLiked() {
		return isLiked;
	}

	/**
	 * @param isLiked the isLiked to set
	 */
	public void setIsLiked(int isLiked) {
		this.isLiked = isLiked;
	}

	/**
	 * @return the userInclude
	 */
	public List<UserDTO> getUserInclude() {
		return userInclude;
	}

	/**
	 * @param userInclude the userInclude to set
	 */
	public void setUserInclude(List<UserDTO> userInclude) {
		this.userInclude = userInclude;
	}

	/**
	 * @return the numberOfLikes
	 */
	public Integer getNumberOfLikes() {
		return numberOfLikes;
	}

	/**
	 * @param numberOfLikes the numberOfLikes to set
	 */
	public void setNumberOfLikes(Integer numberOfLikes) {
		this.numberOfLikes = numberOfLikes;
	}

	/**
	 * @return the numberOfComments
	 */
	public Integer getNumberOfComments() {
		return numberOfComments;
	}

	/**
	 * @param numberOfComments the numberOfComments to set
	 */
	public void setNumberOfComments(Integer numberOfComments) {
		this.numberOfComments = numberOfComments;
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
