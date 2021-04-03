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
@Table(name="sport_attribute")
@NamedQuery(name="SportAttribute.findAll", query="SELECT p FROM SportAttribute p")
public class SportAttribute implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sport_attribute_id", unique = true, nullable = false, length = 20)
	private BigInteger sportAttributeId;
	
	@Column(name = "sport_id", nullable = false, length = 20)
	private BigInteger sportId;
	
	@Column(name="sport_attribute_name", nullable = false, length=200)
	private String sportAttributeName;
	
	@Column(name="sport_attribute_description", nullable = false, length=200)
	private String sportAttributeDescription;
	
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
	 * @return the sportAttributeId
	 */
	public BigInteger getSportAttributeId() {
		return sportAttributeId;
	}

	/**
	 * @param sportAttributeId the sportAttributeId to set
	 */
	public void setSportAttributeId(BigInteger sportAttributeId) {
		this.sportAttributeId = sportAttributeId;
	}

	/**
	 * @return the sportAttributeName
	 */
	public String getSportAttributeName() {
		return sportAttributeName;
	}

	/**
	 * @param sportAttributeName the sportAttributeName to set
	 */
	public void setSportAttributeName(String sportAttributeName) {
		this.sportAttributeName = sportAttributeName;
	}

	/**
	 * @return the sportAttributeDescription
	 */
	public String getSportAttributeDescription() {
		return sportAttributeDescription;
	}

	/**
	 * @param sportAttributeDescription the sportAttributeDescription to set
	 */
	public void setSportAttributeDescription(String sportAttributeDescription) {
		this.sportAttributeDescription = sportAttributeDescription;
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
	
}