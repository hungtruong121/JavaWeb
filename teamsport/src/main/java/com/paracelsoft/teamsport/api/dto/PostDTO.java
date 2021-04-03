package com.paracelsoft.teamsport.api.dto;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class PostDTO {
	
	private BigInteger postId;
	
	private BigInteger teamId;
	
	private String postContent; //html string
	
	private BigInteger postMedia; //uplopadId
	
	private String postType;
	
	private BigInteger privacyId;
	
	private String backgroundColor;
	
	private String location;
	
	private BigInteger backgroundImage;
	
	private String backgroundDefault; //image default in mobile 
	
	private String postContentFontSize;
	
	private List<String> hashTag; //["#chelsea","#myteam"]
	
	private List<UserDTO> userInclude;
	
	private List<UserDTO> userTag;
	
	private BigInteger createdby;
	
	private String createdByName;
	
	private BigInteger createdByAvatar;
	
	private Date createdDate;
	
	private Date updatedDate;
	
	private int isLiked;
	
	private int isActive = 1;
	
	private BigInteger userId;
	
	private Integer numberOfLikes;
	
	private Integer numberOfComments;
	
	private String timeAgo;
	
	private List<PostCommentDTO> listComment;
	
	private List<PostAlbumDTO> postChild;

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
	 * @return the postContent
	 */
	public String getPostContent() {
		return postContent;
	}

	/**
	 * @param postContent the postContent to set
	 */
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	/**
	 * @return the postMedia
	 */
	public BigInteger getPostMedia() {
		return postMedia;
	}

	/**
	 * @param postMedia the postMedia to set
	 */
	public void setPostMedia(BigInteger postMedia) {
		this.postMedia = postMedia;
	}

	/**
	 * @return the privacyId
	 */
	public BigInteger getPrivacyId() {
		return privacyId;
	}

	/**
	 * @param privacyId the privacyId to set
	 */
	public void setPrivacyId(BigInteger privacyId) {
		this.privacyId = privacyId;
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
	 * @return the backgroundColor
	 */
	public String getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * @param backgroundColor the backgroundColor to set
	 */
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the postContentFontSize
	 */
	public String getPostContentFontSize() {
		return postContentFontSize;
	}

	/**
	 * @param postContentFontSize the postContentFontSize to set
	 */
	public void setPostContentFontSize(String postContentFontSize) {
		this.postContentFontSize = postContentFontSize;
	}

	/**
	 * @return the createdby
	 */
	public BigInteger getCreatedby() {
		return createdby;
	}

	/**
	 * @param createdby the createdby to set
	 */
	public void setCreatedby(BigInteger createdby) {
		this.createdby = createdby;
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
	 * @return the hashTag
	 */
	public List<String> getHashTag() {
		return hashTag;
	}

	/**
	 * @param hashTag the hashTag to set
	 */
	public void setHashTag(List<String> hashTag) {
		this.hashTag = hashTag;
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

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public Integer getNumberOfLikes() {
		return numberOfLikes;
	}

	public void setNumberOfLikes(Integer numberOfLikes) {
		this.numberOfLikes = numberOfLikes;
	}

	public Integer getNumberOfComments() {
		return numberOfComments;
	}

	public void setNumberOfComments(Integer numberOfComments) {
		this.numberOfComments = numberOfComments;
	}

	public String getTimeAgo() {
		return timeAgo;
	}

	public void setTimeAgo(String timeAgo) {
		this.timeAgo = timeAgo;
	}

	/**
	 * @return the backgroundImage
	 */
	public BigInteger getBackgroundImage() {
		return backgroundImage;
	}

	/**
	 * @param backgroundImage the backgroundImage to set
	 */
	public void setBackgroundImage(BigInteger backgroundImage) {
		this.backgroundImage = backgroundImage;
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
	 * @return the listComment
	 */
	public List<PostCommentDTO> getListComment() {
		return listComment;
	}

	/**
	 * @param listComment the listComment to set
	 */
	public void setListComment(List<PostCommentDTO> listComment) {
		this.listComment = listComment;
	}

	/**
	 * @return the userTag
	 */
	public List<UserDTO> getUserTag() {
		return userTag;
	}

	/**
	 * @param userTag the userTag to set
	 */
	public void setUserTag(List<UserDTO> userTag) {
		this.userTag = userTag;
	}

	/**
	 * @return the postType
	 */
	public String getPostType() {
		return postType;
	}

	/**
	 * @param postType the postType to set
	 */
	public void setPostType(String postType) {
		this.postType = postType;
	}

	/**
	 * @return the backgroundDefault
	 */
	public String getBackgroundDefault() {
		return backgroundDefault;
	}

	/**
	 * @param backgroundDefault the backgroundDefault to set
	 */
	public void setBackgroundDefault(String backgroundDefault) {
		this.backgroundDefault = backgroundDefault;
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

	public List<PostAlbumDTO> getPostChild() {
		return postChild;
	}

	public void setPostChild(List<PostAlbumDTO> postChild) {
		this.postChild = postChild;
	}
	
}
