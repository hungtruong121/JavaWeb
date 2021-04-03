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
@Table(name="active_otp")
@NamedQuery(name="ActiveOtp.findAll", query="SELECT p FROM ActiveOtp p")
public class ActiveOtp implements Serializable ,Comparable<ActiveOtp>{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "active_otp_id", unique = true, nullable = false, length = 20)
	private BigInteger activeOtpId;
	
	@Column(name="active_email", length=45)
	private String activeEmail;
	
	@Column(name="active_otp_code", length=45)
	private String activeOtpCode;
	
	@Column(name="is_active")
	private int isActive = 0;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="begin_time")
	private Date beginTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="end_time")
	private Date endTime;

	/**
	 * @return the activeOtpId
	 */
	public BigInteger getActiveOtpId() {
		return activeOtpId;
	}

	/**
	 * @param activeOtpId the activeOtpId to set
	 */
	public void setActiveOtpId(BigInteger activeOtpId) {
		this.activeOtpId = activeOtpId;
	}

	/**
	 * @return the activeEmail
	 */
	public String getActiveEmail() {
		return activeEmail;
	}

	/**
	 * @param activeEmail the activeEmail to set
	 */
	public void setActiveEmail(String activeEmail) {
		this.activeEmail = activeEmail;
	}

	/**
	 * @return the activeOtpCode
	 */
	public String getActiveOtpCode() {
		return activeOtpCode;
	}

	/**
	 * @param activeOtpCode the activeOtpCode to set
	 */
	public void setActiveOtpCode(String activeOtpCode) {
		this.activeOtpCode = activeOtpCode;
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
	 * @return the beginTime
	 */
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * @param beginTime the beginTime to set
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public int compareTo(ActiveOtp arg0) {
		// TODO Auto-generated method stub
		int number = beginTime.compareTo(arg0.getBeginTime());
		if (number >=0)
			return -1;
		else {
			return number ;
		}	}
		
}