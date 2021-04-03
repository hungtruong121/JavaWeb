package com.paracelsoft.teamsport.api.dto;

import java.math.BigInteger;
import java.util.List;

public class EventMatchKendoDTO {

	private BigInteger eventMatchId;
	
	private BigInteger eventKendoId;
	
	private int eventMatch;
	
	private BigInteger folderId;
	
	private BigInteger homeUserId;
	
	private UserDTO userHome;
	
	private BigInteger opponentUserId;
	
	private UserDTO userOpponent;
	
	private String homePlayerName;
	
	private String opponentPlayerName;
	
	private List<Integer> homeNoteBox;
	
	private List<Integer> opponentNoteBox;
	
	private String scoreBox;
	
	private int userWin; // 0: home team win 1: opponent team win
	
	private int subMatchStatus = 0; // 0:Ready 1:Playing 2:Finish

	/**
	 * @return the eventMatchId
	 */	
	public BigInteger getEventMatchId() {
		return eventMatchId;
	}
	
	/**
	 * @return the eventMatchId
	 */
	public void setEventMatchId(BigInteger eventMatchId) {
		this.eventMatchId = eventMatchId;
	}

	/**
	 * @return the eventKendoId
	 */	
	public BigInteger getEventKendoId() {
		return eventKendoId;
	}
	
	/**
	 * @return the eventKendoId
	 */
	public void setEventKendoId(BigInteger eventKendoId) {
		this.eventKendoId = eventKendoId;
	}

	/**
	 * @return the eventMatch
	 */
	public int getEventMatch() {
		return eventMatch;
	}

	/**
	 * @return the eventMatch
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
	 * @return the homeUserId
	 */
	public BigInteger getHomeUserId() {
		return homeUserId;
	}

	/**
	 * @return the opponentUserId
	 */
	public BigInteger getOpponentUserId() {
		return opponentUserId;
	}

	/**
	 * @return the homePlayerName
	 */
	public String getHomePlayerName() {
		return homePlayerName;
	}

	/**
	 * @return the opponentPlayerName
	 */
	public String getOpponentPlayerName() {
		return opponentPlayerName;
	}

	/**
	 * @param eventMatch the eventMatch to set
	 */
	public void setEventMatch(int eventMatch) {
		this.eventMatch = eventMatch;
	}

	/**
	 * @param homeUserId the homeUserId to set
	 */
	public void setHomeUserId(BigInteger homeUserId) {
		this.homeUserId = homeUserId;
	}

	/**
	 * @param opponentUserId the opponentUserId to set
	 */
	public void setOpponentUserId(BigInteger opponentUserId) {
		this.opponentUserId = opponentUserId;
	}

	/**
	 * @param homePlayerName the homePlayerName to set
	 */
	public void setHomePlayerName(String homePlayerName) {
		this.homePlayerName = homePlayerName;
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
	public List<Integer> getHomeNoteBox() {
		return homeNoteBox;
	}

	/**
	 * @return the opponentNoteBox
	 */
	public List<Integer> getOpponentNoteBox() {
		return opponentNoteBox;
	}

	/**
	 * @return the scoreBox
	 */
	public String getScoreBox() {
		return scoreBox;
	}

	/**
	 * @param homeNoteBox the homeNoteBox to set
	 */
	public void setHomeNoteBox(List<Integer> homeNoteBox) {
		this.homeNoteBox = homeNoteBox;
	}

	/**
	 * @param opponentNoteBox the opponentNoteBox to set
	 */
	public void setOpponentNoteBox(List<Integer> opponentNoteBox) {
		this.opponentNoteBox = opponentNoteBox;
	}

	/**
	 * @param scoreBox the scoreBox to set
	 */
	public void setScoreBox(String scoreBox) {
		this.scoreBox = scoreBox;
	}

	/**
	 * @return the userWin
	 */
	public int getUserWin() {
		return userWin;
	}

	/**
	 * @return the subMatchStatus
	 */
	public int getSubMatchStatus() {
		return subMatchStatus;
	}

	/**
	 * @param userWin the userWin to set
	 */
	public void setUserWin(int userWin) {
		this.userWin = userWin;
	}

	/**
	 * @param subMatchStatus the subMatchStatus to set
	 */
	public void setSubMatchStatus(int subMatchStatus) {
		this.subMatchStatus = subMatchStatus;
	}

	/**
	 * @return the userHome
	 */
	public UserDTO getUserHome() {
		return userHome;
	}

	/**
	 * @param userHome the userHome to set
	 */
	public void setUserHome(UserDTO userHome) {
		this.userHome = userHome;
	}

	/**
	 * @return the userOpponent
	 */
	public UserDTO getUserOpponent() {
		return userOpponent;
	}

	/**
	 * @param userOpponent the userOpponent to set
	 */
	public void setUserOpponent(UserDTO userOpponent) {
		this.userOpponent = userOpponent;
	}
}
