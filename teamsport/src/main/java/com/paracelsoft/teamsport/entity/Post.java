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
@Table(name="post")
@NamedQuery(name="Post.findAll", query="SELECT p FROM Post p")
public class Post implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id", unique = true, nullable = false, length = 20)
	private BigInteger postId;
	
	@Column(name = "post_parent_id", length = 20)
	private BigInteger postParentId;
	
	@Column(name = "post_Type")
	private String postType; //ConstantUtils: POST_NORMAL,POST_PHOTO, POST_ALBUML, POST_SURVEY_TEXT, POST_SURVEY_SELECTION
	
	@Temporal(TemporalType.DATE)
	@Column(name="post_survey_deadline")
	private Date postSurveyDeadline;
	
	@Column(name="post_media")
	private BigInteger postMedia; //uplopadId
	
	@Column(name="post_content")
	private String postContent;
	
	@Column(name="post_description")
	private String postDescription; //album
	
	@Column(name="is_multiple")
	private int isMultiple = 0;//dc chon 1 hay nhieu
	
	@Column(name="is_extends_ans")
	private int isExtendsAns = 0;
	
	@Column(name="locale")
	private String locale;
	
	@Column(name = "privacy_id", nullable = false, length = 20)
	private BigInteger privacyId;
	
	@Column(name = "team_id", length = 20)
	private BigInteger teamId;
	
	@Column(name="background_color")
	private String backgroundColor;
	
	@Column(name="background_image")
	private BigInteger backgroundImage; //uplopadId
	
	@Column(name="background_default")
	private String backgroundDefault;
	
	@Column(name="post_content_font_size")
	private String postContentFontSize;
	
	@Column(name="created_by")
	private BigInteger createdby;
	
	@Column(name="is_active")
	private int isActive = 1;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_date")
	private Date updatedDate;

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
	 * @return the postParentId
	 */
	public BigInteger getPostParentId() {
		return postParentId;
	}

	/**
	 * @param postParentId the postParentId to set
	 */
	public void setPostParentId(BigInteger postParentId) {
		this.postParentId = postParentId;
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
	 * @return the postDescription
	 */
	public String getPostDescription() {
		return postDescription;
	}

	/**
	 * @param postDescription the postDescription to set
	 */
	public void setPostDescription(String postDescription) {
		this.postDescription = postDescription;
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
	
}