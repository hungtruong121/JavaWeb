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
@Table(name="sport_point")
@NamedQuery(name="SportPoint.findAll", query="SELECT p FROM SportPoint p")
public class SportPoint implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sport_point_id", unique = true, nullable = false, length = 20)
	private BigInteger sportPointId;
	
	@Column(name = "sport_id", nullable = false, length = 20)
	private BigInteger sportId;
	
	@Column(name = "sport_point_avatar", length = 20)
	private BigInteger sportPointAvatar;
	
	@Column(name = "sport_point_type")
	private String sportPointType;
	
	@Column(name = "sport_point_name")
	private String sportPointName;
	
	@Column(name = "sport_point_description")
	private String sportPointDescription;
	
	@Column(name = "sport_point_value")
	private String sportPointValue;
	
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
	 * @return the sportPointId
	 */
	public BigInteger getSportPointId() {
		return sportPointId;
	}

	/**
	 * @param sportPointId the sportPointId to set
	 */
	public void setSportPointId(BigInteger sportPointId) {
		this.sportPointId = sportPointId;
	}

	/**
	 * @return the sportId
	 */
	public BigInteger getSportId() {
		return sportId;
	}

	/**
	 * @param sportId the sportId to set
	 */
	public void setSportId(BigInteger sportId) {
		this.sportId = sportId;
	}

	/**
	 * @return the sportPointType
	 */
	public String getSportPointType() {
		return sportPointType;
	}

	/**
	 * @param sportPointType the sportPointType to set
	 */
	public void setSportPointType(String sportPointType) {
		this.sportPointType = sportPointType;
	}

	/**
	 * @return the sportPointName
	 */
	public String getSportPointName() {
		return sportPointName;
	}

	/**
	 * @param sportPointName the sportPointName to set
	 */
	public void setSportPointName(String sportPointName) {
		this.sportPointName = sportPointName;
	}

	/**
	 * @return the sportPointDescription
	 */
	public String getSportPointDescription() {
		return sportPointDescription;
	}

	/**
	 * @param sportPointDescription the sportPointDescription to set
	 */
	public void setSportPointDescription(String sportPointDescription) {
		this.sportPointDescription = sportPointDescription;
	}

	/**
	 * @return the sportPointValue
	 */
	public String getSportPointValue() {
		return sportPointValue;
	}

	/**
	 * @param sportPointValue the sportPointValue to set
	 */
	public void setSportPointValue(String sportPointValue) {
		this.sportPointValue = sportPointValue;
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
	 * @return the sportPointAvatar
	 */
	public BigInteger getSportPointAvatar() {
		return sportPointAvatar;
	}

	/**
	 * @param sportPointAvatar the sportPointAvatar to set
	 */
	public void setSportPointAvatar(BigInteger sportPointAvatar) {
		this.sportPointAvatar = sportPointAvatar;
	}

}