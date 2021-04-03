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
@Table(name="post_comment")
@NamedQuery(name="PostComment.findAll", query="SELECT p FROM PostComment p")
public class PostComment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_comment_id", unique = true, nullable = false, length = 20)
	private BigInteger postCommentId;
	
	@Column(name = "post_comment_parent_Id", nullable = false, length = 20)
	private BigInteger postCommentParentId;
	
	@Column(name = "post_id", nullable = false, length = 20)
	private BigInteger postId;
	
	@Column(name = "user_id", nullable = false, length = 20)
	private BigInteger userId;
	
	@Column(name = "gif_id")
	private String gif_id;
	
	@Column(name="post_comment_content")
	private String postCommentContent;
	
	@Column(name="post_comment_image")
	private BigInteger postCommentImage; //uplopadId
	
	@Column(name="is_active")
	private int isActive = 1;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_date")
	private Date updatedDate;

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

}