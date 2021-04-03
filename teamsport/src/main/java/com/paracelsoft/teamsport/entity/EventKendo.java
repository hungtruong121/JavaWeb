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
@Table(name="event_kendo")
@NamedQuery(name="EventKendo.findAll", query="SELECT e FROM EventKendo e")
public class EventKendo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "event_kendo_id", unique = true, nullable = false, length = 20)
	private BigInteger eventKendoId;
	
	@Column(name="event_parent_id", nullable = false, length = 20)
	private BigInteger eventParentId;
	
	@Column(name="folder_id", length = 20)
	private BigInteger folderId;
	
	@Column(name="team_id", nullable = false, length = 20)
	private BigInteger teamId;
	
	@Column(name="opponent_team_id", length = 20)
	private BigInteger opponentTeamId;
	
	@Column(name="privacy_id", length = 20)
	private BigInteger privacyId;
	
	@Column(name="location_id", length = 20)
	private BigInteger locationId;
	
	@Column(name = "event_type", nullable = false, length = 45)
	private String eventType;
	
	@Column(name = "event_title", length = 255)
	private String eventTitle;
	
	@Column(name = "event_loop_type", length = 45)
	private String eventLoopType;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="event_date")
	private Date eventDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="event_expired")
	private Date eventExpired;
	
	@Column(name = "event_game_type", length = 45)
	private String eventGameType;

	@Column(name = "event_ground")
	private int eventGround;
	
	@Column(name = "event_match")
	private int eventMatch;
	
	@Column(name = "event_home_team", length = 45)
	private String eventHomeTeam;
	
	@Column(name = "event_opponent_team", length = 45)
	private String eventOpponentTeam;

	@Column(name = "event_tournament", length = 100)
	private String eventTournament;

	@Column(name = "event_sub_matchs")
	private int eventSubMatchs; //3 - 5 - 7
	
	@Column(name = "team_color", length = 45)
	private String teamColor;
	
	@Column(name = "event_hashtag", length = 45)
	private String eventHashtag;
	
	@Column(name = "event_notice", length = 45)
	private String eventNotice;
	
	@Column(name="match_status")
	private Integer matchStatus = 0; // 0: Next Match 1:Playing 2:Finish
	
	@Column(name="created_by")
	private BigInteger createdBy;

	@Column(name="is_approve")
	private int isApprove = 0; // 0: chưa đồng ý tham gia - 1:đã đồng ý
	
	@Column(name="is_locked")
	private int isLocked = 0; // 0: chưa lock - 1:đã lock
	
	@Column(name="user_locked")
	private BigInteger userLocked;
	
	@Column(name="approved_by")
	private BigInteger approvedBy;
	
	@Column(name = "team_win", length = 10)
	private String teamWin; // 0: home win 1: opponent win 2:hoa
	
	@Column(name="is_active")
	private int isActive = 1;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_date")
	private Date updatedDate;

	/**
	 * @return the eventKendoId
	 */
	public BigInteger getEventKendoId() {
		return eventKendoId;
	}

	/**
	 * @return the eventParentId
	 */
	public BigInteger getEventParentId() {
		return eventParentId;
	}

	/**
	 * @param eventParentId the eventParentId to set
	 */
	public void setEventParentId(BigInteger eventParentId) {
		this.eventParentId = eventParentId;
	}

	/**
	 * @param eventKendoId the eventKendoId to set
	 */
	public void setEventKendoId(BigInteger eventKendoId) {
		this.eventKendoId = eventKendoId;
	}

	
	/**
	 * @return the folderId
	 */
	public BigInteger getFolderId() {
		return folderId;
	}
	
	/**
	 * @param folderId the folderId to set
	 */
	public void setFolderId(BigInteger folderId) {
		this.folderId = folderId;
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
	 * @return the opponentTeamId
	 */
	public BigInteger getOpponentTeamId() {
		return opponentTeamId;
	}

	/**
	 * @param opponentTeamId the opponentTeamId to set
	 */
	public void setOpponentTeamId(BigInteger opponentTeamId) {
		this.opponentTeamId = opponentTeamId;
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
	 * @return the locationId
	 */
	public BigInteger getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(BigInteger locationId) {
		this.locationId = locationId;
	}

	/**
	 * @return the eventType
	 */
	public String getEventType() {
		return eventType;
	}

	/**
	 * @param eventType the eventType to set
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	/**
	 * @return the eventTitle
	 */
	public String getEventTitle() {
		return eventTitle;
	}

	/**
	 * @param eventTitle the eventTitle to set
	 */
	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	/**
	 * @return the eventLoopType
	 */
	public String getEventLoopType() {
		return eventLoopType;
	}

	/**
	 * @param eventLoopType the eventLoopType to set
	 */
	public void setEventLoopType(String eventLoopType) {
		this.eventLoopType = eventLoopType;
	}

	/**
	 * @return the eventDate
	 */
	public Date getEventDate() {
		return eventDate;
	}

	/**
	 * @param eventDate the eventDate to set
	 */
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	/**
	 * @return the eventExpired
	 */
	public Date getEventExpired() {
		return eventExpired;
	}

	/**
	 * @param eventExpired the eventExpired to set
	 */
	public void setEventExpired(Date eventExpired) {
		this.eventExpired = eventExpired;
	}

	/**
	 * @return the eventGameType
	 */
	public String getEventGameType() {
		return eventGameType;
	}

	/**
	 * @param eventGameType the eventGameType to set
	 */
	public void setEventGameType(String eventGameType) {
		this.eventGameType = eventGameType;
	}

	/**
	 * @return the eventGround
	 */
	public int getEventGround() {
		return eventGround;
	}

	/**
	 * @param eventGround the eventGround to set
	 */
	public void setEventGround(int eventGround) {
		this.eventGround = eventGround;
	}

	/**
	 * @return the eventMatch
	 */
	public int getEventMatch() {
		return eventMatch;
	}

	/**
	 * @param eventMatch the eventMatch to set
	 */
	public void setEventMatch(int eventMatch) {
		this.eventMatch = eventMatch;
	}

	/**
	 * @return the eventHomeTeam
	 */
	public String getEventHomeTeam() {
		return eventHomeTeam;
	}

	/**
	 * @param eventHomeTeam the eventHomeTeam to set
	 */
	public void setEventHomeTeam(String eventHomeTeam) {
		this.eventHomeTeam = eventHomeTeam;
	}

	/**
	 * @return the eventOpponentTeam
	 */
	public String getEventOpponentTeam() {
		return eventOpponentTeam;
	}

	/**
	 * @param eventOpponentTeam the eventOpponentTeam to set
	 */
	public void setEventOpponentTeam(String eventOpponentTeam) {
		this.eventOpponentTeam = eventOpponentTeam;
	}

	/**
	 * @return the eventTournament
	 */
	public String getEventTournament() {
		return eventTournament;
	}

	/**
	 * @param eventTournament the eventTournament to set
	 */
	public void setEventTournament(String eventTournament) {
		this.eventTournament = eventTournament;
	}

	/**
	 * @return the eventSubMatchs
	 */
	public int getEventSubMatchs() {
		return eventSubMatchs;
	}

	/**
	 * @param eventSubMatchs the eventSubMatchs to set
	 */
	public void setEventSubMatchs(int eventSubMatchs) {
		this.eventSubMatchs = eventSubMatchs;
	}

	/**
	 * @return the teamColor
	 */
	public String getTeamColor() {
		return teamColor;
	}

	/**
	 * @param teamColor the teamColor to set
	 */
	public void setTeamColor(String teamColor) {
		this.teamColor = teamColor;
	}

	/**
	 * @return the eventHashtag
	 */
	public String getEventHashtag() {
		return eventHashtag;
	}

	/**
	 * @param eventHashtag the eventHashtag to set
	 */
	public void setEventHashtag(String eventHashtag) {
		this.eventHashtag = eventHashtag;
	}

	/**
	 * @return the eventNotice
	 */
	public String getEventNotice() {
		return eventNotice;
	}

	/**
	 * @param eventNotice the eventNotice to set
	 */
	public void setEventNotice(String eventNotice) {
		this.eventNotice = eventNotice;
	}

	/**
	 * @return the matchStatus
	 */
	public Integer getMatchStatus() {
		return matchStatus;
	}

	/**
	 * @param matchStatus the matchStatus to set
	 */
	public void setMatchStatus(Integer matchStatus) {
		this.matchStatus = matchStatus;
	}

	/**
	 * @return the createdBy
	 */
	public BigInteger getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(BigInteger createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the isApprove
	 */
	public int getIsApprove() {
		return isApprove;
	}

	/**
	 * @param isApprove the isApprove to set
	 */
	public void setIsApprove(int isApprove) {
		this.isApprove = isApprove;
	}

	/**
	 * @return the isLocked
	 */
	public int getIsLocked() {
		return isLocked;
	}

	/**
	 * @param isLocked the isLocked to set
	 */
	public void setIsLocked(int isLocked) {
		this.isLocked = isLocked;
	}

	/**
	 * @return the userLocked
	 */
	public BigInteger getUserLocked() {
		return userLocked;
	}

	/**
	 * @param userLocked the userLocked to set
	 */
	public void setUserLocked(BigInteger userLocked) {
		this.userLocked = userLocked;
	}

	/**
	 * @return the approvedBy
	 */
	public BigInteger getApprovedBy() {
		return approvedBy;
	}

	/**
	 * @param approvedBy the approvedBy to set
	 */
	public void setApprovedBy(BigInteger approvedBy) {
		this.approvedBy = approvedBy;
	}

	/**
	 * @return the teamWin
	 */
	public String getTeamWin() {
		return teamWin;
	}

	/**
	 * @param teamWin the teamWin to set
	 */
	public void setTeamWin(String teamWin) {
		this.teamWin = teamWin;
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
