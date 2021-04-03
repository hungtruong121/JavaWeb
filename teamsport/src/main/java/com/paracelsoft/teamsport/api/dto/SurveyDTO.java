package com.paracelsoft.teamsport.api.dto;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.paracelsoft.teamsport.entity.HashtagContainer;
import com.paracelsoft.teamsport.entity.PostSurveyAns;
import com.paracelsoft.teamsport.entity.User;

public class SurveyDTO {
	private BigInteger ansId;
	
	private BigInteger voteId;
	
	private BigInteger postId;
	
	private BigInteger userId;
	
	private BigInteger ansImage; // upload id
	
	private String postSurveyContent;
	
	private BigInteger backgroundImage;
	
	private String backgroundDefault; //image default in mobile 
	
	private String backgroundColor;
	
	private String postContentFontSize;
	
	private List<String> hashTag; //["#chelsea","#myteam"]
	
	private BigInteger createdby;
	
	private int isActive = 1;
	
	private Date createdDate;
	
	private Date updatedDate;
	
	private BigInteger teamId;
	
	private BigInteger privacyId;
	
	private String privacyName;
	
	private int totalAnswers;
	
	private int totalVotes;
	
	private String postType; //POST_SURVEY_TEXT, POST_SURVEY_SELECTION
	
	private String locale;
	
	private int isMultiple = 0;//dc chon 1 hay nhieu
	
	private int isExtendsAns = 0;
	
	private String fullName;
	
	private BigInteger userAvatar;
	
	private String ansContent;
	
	private Date postSurveyDeadline;
	
	private List<PostSurveyAns> optionList;
	
	private List<HashtagContainer> hashtagList;
	
	private List<User> includeUserList;
	
	private List<SurveyDTO> answerList;
	
	private List<SurveyDTO> votes;
	
	private List<UserDTO> userInclude;
	
	private List<UserDTO> userTag;
	
	private String hashtags;
	
	private String includeUsers;
	
	private int flagVote;
	
	private int totalVoteOption;
	
	private int numberOfLikes;
	
	private int numberOfComments;
	
	private String timeAgo;

	/**
	 * @return the ansId
	 */
	public BigInteger getAnsId() {
		return ansId;
	}

	/**
	 * @param ansId the ansId to set
	 */
	public void setAnsId(BigInteger ansId) {
		this.ansId = ansId;
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
	 * @return the postSurveyContent
	 */
	public String getPostSurveyContent() {
		return postSurveyContent;
	}

	/**
	 * @param postSurveyContent the postSurveyContent to set
	 */
	public void setPostSurveyContent(String postSurveyContent) {
		this.postSurveyContent = postSurveyContent;
	}

	/**
	 * @return the ansImage
	 */
	public BigInteger getAnsImage() {
		return ansImage;
	}

	/**
	 * @param ansImage the ansImage to set
	 */
	public void setAnsImage(BigInteger ansImage) {
		this.ansImage = ansImage;
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
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * @param locale the locale to set
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

	/**
	 * @return the postSurveyDeadline
	 */
	public Date getPostSurveyDeadline() {
		return postSurveyDeadline;
	}

	/**
	 * @param postSurveyDeadline the postSurveyDeadline to set
	 */
	public void setPostSurveyDeadline(Date postSurveyDeadline) {
		this.postSurveyDeadline = postSurveyDeadline;
	}

	/**
	 * @return the answerList
	 */
	public List<SurveyDTO> getAnswerList() {
		return answerList;
	}

	/**
	 * @param answerList the answerList to set
	 */
	public void setAnswerList(List<SurveyDTO> answerList) {
		this.answerList = answerList;
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
	 * @return the voteId
	 */
	public BigInteger getVoteId() {
		return voteId;
	}

	/**
	 * @param voteId the voteId to set
	 */
	public void setVoteId(BigInteger voteId) {
		this.voteId = voteId;
	}

	/**
	 * @return the multipleResponse
	 */
	public List<SurveyDTO> getVotes() {
		return votes;
	}

	/**
	 * @param multipleResponse the multipleResponse to set
	 */
	public void setVotes(List<SurveyDTO> votes) {
		this.votes = votes;
	}
	
	/**
	 * @return the isMultiple
	 */
	public int getIsMultiple() {
		return isMultiple;
	}

	/**
	 * @param isMultiple the isMultiple to set
	 */
	public void setIsMultiple(int isMultiple) {
		this.isMultiple = isMultiple;
	}

	/**
	 * @return the isExtendsAns
	 */
	public int getIsExtendsAns() {
		return isExtendsAns;
	}

	/**
	 * @param isExtendsAns the isExtendsAns to set
	 */
	public void setIsExtendsAns(int isExtendsAns) {
		this.isExtendsAns = isExtendsAns;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the userAvatar
	 */
	public BigInteger getUserAvatar() {
		return userAvatar;
	}

	/**
	 * @param userAvatar the userAvatar to set
	 */
	public void setUserAvatar(BigInteger userAvatar) {
		this.userAvatar = userAvatar;
	}

	/**
	 * @return the ansContent
	 */
	public String getAnsContent() {
		return ansContent;
	}

	/**
	 * @param ansContent the ansContent to set
	 */
	public void setAnsContent(String ansContent) {
		this.ansContent = ansContent;
	}

	/**
	 * @return the totalAnswers
	 */
	public int getTotalAnswers() {
		return totalAnswers;
	}

	/**
	 * @param totalAnswers the totalAnswers to set
	 */
	public void setTotalAnswers(int totalAnswers) {
		this.totalAnswers = totalAnswers;
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
	 * @return the privacyName
	 */
	public String getPrivacyName() {
		return privacyName;
	}

	/**
	 * @param privacyName the privacyName to set
	 */
	public void setPrivacyName(String privacyName) {
		this.privacyName = privacyName;
	}

	/**
	 * @return the hashtags
	 */
	public String getHashtags() {
		return hashtags;
	}

	/**
	 * @param hashtags the hashtags to set
	 */
	public void setHashtags(String hashtags) {
		this.hashtags = hashtags;
	}

	/**
	 * @return the includeUsers
	 */
	public String getIncludeUsers() {
		return includeUsers;
	}

	/**
	 * @param includeUsers the includeUsers to set
	 */
	public void setIncludeUsers(String includeUsers) {
		this.includeUsers = includeUsers;
	}

	/**
	 * @return the totalVotes
	 */
	public int getTotalVotes() {
		return totalVotes;
	}

	/**
	 * @param totalVotes the totalVotes to set
	 */
	public void setTotalVotes(int totalVotes) {
		this.totalVotes = totalVotes;
	}

	/**
	 * @return the optionList
	 */
	public List<PostSurveyAns> getOptionList() {
		return optionList;
	}

	/**
	 * @param optionList the optionList to set
	 */
	public void setOptionList(List<PostSurveyAns> optionList) {
		this.optionList = optionList;
	}

	/**
	 * @return the hashtagList
	 */
	public List<HashtagContainer> getHashtagList() {
		return hashtagList;
	}

	/**
	 * @param hashtagList the hashtagList to set
	 */
	public void setHashtagList(List<HashtagContainer> hashtagList) {
		this.hashtagList = hashtagList;
	}

	/**
	 * @return the includeUserList
	 */
	public List<User> getIncludeUserList() {
		return includeUserList;
	}

	/**
	 * @param includeUserList the includeUserList to set
	 */
	public void setIncludeUserList(List<User> includeUserList) {
		this.includeUserList = includeUserList;
	}

	/**
	 * @return the flagVote
	 */
	public int getFlagVote() {
		return flagVote;
	}

	/**
	 * @param flagVote the flagVote to set
	 */
	public void setFlagVote(int flagVote) {
		this.flagVote = flagVote;
	}

	/**
	 * @return the totalVoteOption
	 */
	public int getTotalVoteOption() {
		return totalVoteOption;
	}

	/**
	 * @param totalVoteOption the totalVoteOption to set
	 */
	public void setTotalVoteOption(int totalVoteOption) {
		this.totalVoteOption = totalVoteOption;
	}

	/**
	 * @return the numberOfLikes
	 */
	public int getNumberOfLikes() {
		return numberOfLikes;
	}

	/**
	 * @param numberOfLikes the numberOfLikes to set
	 */
	public void setNumberOfLikes(int numberOfLikes) {
		this.numberOfLikes = numberOfLikes;
	}

	/**
	 * @return the numberOfComment
	 */
	public int getNumberOfComments() {
		return numberOfComments;
	}

	/**
	 * @param numberOfComment the numberOfComment to set
	 */
	public void setNumberOfComments(int numberOfComments) {
		this.numberOfComments = numberOfComments;
	}

	/**
	 * @return the timeAgo
	 */
	public String getTimeAgo() {
		return timeAgo;
	}

	/**
	 * @param timeAgo the timeAgo to set
	 */
	public void setTimeAgo(String timeAgo) {
		this.timeAgo = timeAgo;
	}

}
