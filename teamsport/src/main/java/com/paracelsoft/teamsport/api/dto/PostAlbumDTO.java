package com.paracelsoft.teamsport.api.dto;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.paracelsoft.teamsport.entity.Post;


public class PostAlbumDTO {

	private BigInteger postId;
	
	private BigInteger teamId;
	
	private BigInteger postParentId;
	
	private String postContent; //html string
	
	private BigInteger postMedia; //uplopadId
	
	private String postType;
	
	private BigInteger privacyId;
	
	private String postDescription;
	
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
	
	private Integer numberOfPhotos;
	
	private Integer numberOfVideos;
	
	private String timeAgo;
	
	private String location;
	
	private String fileType;
	
	private List<Post> listMedia;
	
	private List<PostCommentDTO> listComment;
	
	private List<PostAlbumDTO> postChild;

	public BigInteger getPostId() {
		return postId;
	}

	public void setPostId(BigInteger postId) {
		this.postId = postId;
	}

	public BigInteger getTeamId() {
		return teamId;
	}

	public void setTeamId(BigInteger teamId) {
		this.teamId = teamId;
	}
	
	public BigInteger getPostMedia() {
		return postMedia;
	}

	public void setPostMedia(BigInteger postMedia) {
		this.postMedia = postMedia;
	}

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	
	public BigInteger getPostParentId() {
		return postParentId;
	}

	public void setPostParentId(BigInteger postParentId) {
		this.postParentId = postParentId;
	}

	public String getPostContentFontSize() {
		return postContentFontSize;
	}

	public void setPostContentFontSize(String postContentFontSize) {
		this.postContentFontSize = postContentFontSize;
	}

	public String getPostDescription() {
		return postDescription;
	}

	public void setPostDescription(String postDescription) {
		this.postDescription = postDescription;
	}

	public BigInteger getPrivacyId() {
		return privacyId;
	}

	public void setPrivacyId(BigInteger privacyId) {
		this.privacyId = privacyId;
	}

	public List<String> getHashTag() {
		return hashTag;
	}

	public void setHashTag(List<String> hashTag) {
		this.hashTag = hashTag;
	}

	public List<UserDTO> getUserInclude() {
		return userInclude;
	}

	public void setUserInclude(List<UserDTO> userInclude) {
		this.userInclude = userInclude;
	}

	public List<UserDTO> getUserTag() {
		return userTag;
	}

	public void setUserTag(List<UserDTO> userTag) {
		this.userTag = userTag;
	}

	public BigInteger getCreatedby() {
		return createdby;
	}

	public void setCreatedby(BigInteger createdby) {
		this.createdby = createdby;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public BigInteger getCreatedByAvatar() {
		return createdByAvatar;
	}

	public void setCreatedByAvatar(BigInteger createdByAvatar) {
		this.createdByAvatar = createdByAvatar;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public int getIsLiked() {
		return isLiked;
	}

	public void setIsLiked(int isLiked) {
		this.isLiked = isLiked;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
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

	public Integer getNumberOfPhotos() {
		return numberOfPhotos;
	}

	public void setNumberOfPhotos(Integer numberOfPhotos) {
		this.numberOfPhotos = numberOfPhotos;
	}

	public Integer getNumberOfVideos() {
		return numberOfVideos;
	}

	public void setNumberOfVideos(Integer numberOfVideos) {
		this.numberOfVideos = numberOfVideos;
	}

	public String getTimeAgo() {
		return timeAgo;
	}
	
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setTimeAgo(String timeAgo) {
		this.timeAgo = timeAgo;
	}

	public List<Post> getListMedia() {
		return listMedia;
	}

	public void setListMedia(List<Post> listMedia) {
		this.listMedia = listMedia;
	}

	public List<PostCommentDTO> getListComment() {
		return listComment;
	}

	public void setListComment(List<PostCommentDTO> listComment) {
		this.listComment = listComment;
	}

	public List<PostAlbumDTO> getPostChild() {
		return postChild;
	}

	public void setPostChild(List<PostAlbumDTO> postChild) {
		this.postChild = postChild;
	}
	
}

