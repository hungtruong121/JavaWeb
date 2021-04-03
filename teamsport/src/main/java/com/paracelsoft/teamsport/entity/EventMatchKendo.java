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
@Table(name="event_match_kendo")
@NamedQuery(name="EventMatchKendo.findAll", query="SELECT e FROM EventMatchKendo e")
public class EventMatchKendo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "event_match_id", unique = true, nullable = false, length = 20)
	private BigInteger eventMatchId;
	
	@Column(name="event_kendo_id", length = 20)
	private BigInteger eventKendoId;
	
	@Column(name = "event_match")
	private int eventMatch;
	
	@Column(name="folder_id", length = 20)
	private BigInteger folderId;
	
	@Column(name="home_user_id", length = 20)
	private BigInteger homeUserId;
	
	@Column(name="opponent_user_id", length = 20)
	private BigInteger opponentUserId;
	
	@Column(name = "home_player_name", length = 100)
	private String homePlayerName;
	
	@Column(name = "opponent_player_name", length = 100)
	private String opponentPlayerName;
	
	@Column(name = "home_note_box", length = 45)
	private String homeNoteBox;
	
	@Column(name = "opponent_note_box", length = 45)
	private String opponentNoteBox;
	
	@Column(name = "score_box", length = 10)
	private String scoreBox;
	
	@Column(name="user_win", length = 10)
	private String userWin; // 0: home team win 1: opponent team win 2:Hoa
	
	@Column(name="home_score")
	private int homeScore = 0;
	
	@Column(name="opponent_score")
	private int opponentScore = 0;
	
	@Column(name="sub_match_status")
	private int subMatchStatus = 0; // 0:Ready 1:Playing 2:Finish
	
	@Column(name="is_active")
	private int isActive = 1;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_date")
	private Date updatedDate;

	/**
	 * @return the eventMatchId
	 */
	public BigInteger getEventMatchId() {
		return eventMatchId;
	}

	/**
	 * @param eventMatchId the eventMatchId to set
	 */
	public void setEventMatchId(BigInteger eventMatchId) {
		this.eventMatchId = eventMatchId;
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
	 * @return the eventKendoId
	 */
	public BigInteger getEventKendoId() {
		return eventKendoId;
	}

	/**
	 * @param eventKendoId the eventKendoId to set
	 */
	public void setEventKendoId(BigInteger eventKendoId) {
		this.eventKendoId = eventKendoId;
	}

	public int getEventMatch() {
		return eventMatch;
	}

	public void setEventMatch(int eventMatch) {
		this.eventMatch = eventMatch;
	}

	/**
	 * @return the homeUserId
	 */
	public BigInteger getHomeUserId() {
		return homeUserId;
	}

	/**
	 * @param homeUserId the homeUserId to set
	 */
	public void setHomeUserId(BigInteger homeUserId) {
		this.homeUserId = homeUserId;
	}

	/**
	 * @return the opponentUserId
	 */
	public BigInteger getOpponentUserId() {
		return opponentUserId;
	}

	/**
	 * @param opponentUserId the opponentUserId to set
	 */
	public void setOpponentUserId(BigInteger opponentUserId) {
		this.opponentUserId = opponentUserId;
	}

	/**
	 * @return the homePlayerName
	 */
	public String getHomePlayerName() {
		return homePlayerName;
	}

	/**
	 * @param homePlayerName the homePlayerName to set
	 */
	public void setHomePlayerName(String homePlayerName) {
		this.homePlayerName = homePlayerName;
	}

	/**
	 * @return the opponentPlayerName
	 */
	public String getOpponentPlayerName() {
		return opponentPlayerName;
	}

	/**
	 * @param opponentPlayerName the opponentPlayerName to set
	 */
	public void setOpponentPlayerName(String opponentPlayerName) {
		this.opponentPlayerName = opponentPlayerName;
	}

	/**
	 * @return the homeNoteBox
	 */
	public String getHomeNoteBox() {
		return homeNoteBox;
	}

	/**
	 * @return the opponentNoteBox
	 */
	public String getOpponentNoteBox() {
		return opponentNoteBox;
	}

	/**
	 * @return the scoreBox
	 */
	public String getScoreBox() {
		return scoreBox;
	}

	/**
	 * @return the userWin
	 */
	public String getUserWin() {
		return userWin;
	}

	/**
	 * @return the homeScore
	 */
	public int getHomeScore() {
		return homeScore;
	}

	/**
	 * @return the opponentScore
	 */
	public int getOpponentScore() {
		return opponentScore;
	}

	/**
	 * @param homeScore the homeScore to set
	 */
	public void setHomeScore(int homeScore) {
		this.homeScore = homeScore;
	}

	/**
	 * @param opponentScore the opponentScore to set
	 */
	public void setOpponentScore(int opponentScore) {
		this.opponentScore = opponentScore;
	}

	/**
	 * @param homeNoteBox the homeNoteBox to set
	 */
	public void setHomeNoteBox(String homeNoteBox) {
		this.homeNoteBox = homeNoteBox;
	}

	/**
	 * @param opponentNoteBox the opponentNoteBox to set
	 */
	public void setOpponentNoteBox(String opponentNoteBox) {
		this.opponentNoteBox = opponentNoteBox;
	}

	/**
	 * @param scoreBox the scoreBox to set
	 */
	public void setScoreBox(String scoreBox) {
		this.scoreBox = scoreBox;
	}

	/**
	 * @param userWin the userWin to set
	 */
	public void setUserWin(String userWin) {
		this.userWin = userWin;
	}

	/**
	 * @return the subMatchStatus
	 */
	public int getSubMatchStatus() {
		return subMatchStatus;
	}

	/**
	 * @param subMatchStatus the subMatchStatus to set
	 */
	public void setSubMatchStatus(int subMatchStatus) {
		this.subMatchStatus = subMatchStatus;
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
