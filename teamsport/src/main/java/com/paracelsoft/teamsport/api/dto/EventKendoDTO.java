package com.paracelsoft.teamsport.api.dto;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.paracelsoft.teamsport.entity.Team;

public class EventKendoDTO {
	private BigInteger eventId;
	private BigInteger teamId;
	private Team homeTeam;
	private BigInteger opponentTeamId;
	private Team opponentTeam;
	private String opponentEmail;
	private BigInteger privacyId;
	private BigInteger locationId;
	private LocationDTO location;
	private String eventType;
	private String eventTitle;
	private String eventLoopType;
	private List<Integer> daysPicked;
	private String eventDate;
	private String eventDateExpired;
	private Date dateEvent;
	private Date dateEventExpired;
	private String eventGameType;
	private int eventGround;
	private int eventMatch;
	private int isApproved;
	private BigInteger approvedBy;
	private BigInteger createdBy;
	private String eventHomeTeam;
	private String eventOpponentTeam;
	private String eventTournament;
	private int eventSubMatchs; //3 - 5 - 7
	private List<EventMatchKendoDTO> listMatchKendo;
	private String teamColor; // RED || WHITE
	private String eventHashtag;
	private String eventNotice;
	private int matchHomeScore;
	private int matchOpponentScore;
	private int subMatchHomeScore;
	private int subMatchOpponentScore;
	private int isLocked;
	private UserDTO userUpdating;
	private Integer matchStatus; // 0: Next Match 1:Playing 2:Finish
	private Date createdDate;
	private Date updatedDate;
	
	public BigInteger getEventId() {
		return eventId;
	}
	public BigInteger getTeamId() {
		return teamId;
	}
	public BigInteger getOpponentTeamId() {
		return opponentTeamId;
	}
	public BigInteger getPrivacyId() {
		return privacyId;
	}
	public BigInteger getLocationId() {
		return locationId;
	}
	public String getEventType() {
		return eventType;
	}
	public String getEventTitle() {
		return eventTitle;
	}
	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}
	public String getEventLoopType() {
		return eventLoopType;
	}
	public String getEventDate() {
		return eventDate;
	}
	public Date getDateEvent() {
		return dateEvent;
	}
	public String getEventGameType() {
		return eventGameType;
	}
	public int getEventGround() {
		return eventGround;
	}
	public int getEventMatch() {
		return eventMatch;
	}
	public String getEventHomeTeam() {
		return eventHomeTeam;
	}
	public String getEventOpponentTeam() {
		return eventOpponentTeam;
	}
	public String getEventTournament() {
		return eventTournament;
	}
	public int getEventSubMatchs() {
		return eventSubMatchs;
	}
	public String getTeamColor() {
		return teamColor;
	}
	public String getEventHashtag() {
		return eventHashtag;
	}
	public String getEventNotice() {
		return eventNotice;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setEventId(BigInteger eventId) {
		this.eventId = eventId;
	}
	public void setTeamId(BigInteger teamId) {
		this.teamId = teamId;
	}
	public void setOpponentTeamId(BigInteger opponentTeamId) {
		this.opponentTeamId = opponentTeamId;
	}
	public void setPrivacyId(BigInteger privacyId) {
		this.privacyId = privacyId;
	}
	public void setLocationId(BigInteger locationId) {
		this.locationId = locationId;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public void setEventLoopType(String eventLoopType) {
		this.eventLoopType = eventLoopType;
	}
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
	public void setDateEvent(Date dateEvent) {
		this.dateEvent = dateEvent;
	}
	public void setEventGameType(String eventGameType) {
		this.eventGameType = eventGameType;
	}
	public void setEventGround(int eventGround) {
		this.eventGround = eventGround;
	}
	public void setEventMatch(int eventMatch) {
		this.eventMatch = eventMatch;
	}
	public void setEventHomeTeam(String eventHomeTeam) {
		this.eventHomeTeam = eventHomeTeam;
	}
	public void setEventOpponentTeam(String eventOpponentTeam) {
		this.eventOpponentTeam = eventOpponentTeam;
	}
	public void setEventTournament(String eventTournament) {
		this.eventTournament = eventTournament;
	}
	public void setEventSubMatchs(int eventSubMatchs) {
		this.eventSubMatchs = eventSubMatchs;
	}
	public void setTeamColor(String teamColor) {
		this.teamColor = teamColor;
	}
	public void setEventHashtag(String eventHashtag) {
		this.eventHashtag = eventHashtag;
	}
	public void setEventNotice(String eventNotice) {
		this.eventNotice = eventNotice;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public List<EventMatchKendoDTO> getListMatchKendo() {
		return listMatchKendo;
	}
	public void setListMatchKendo(List<EventMatchKendoDTO> listMatchKendo) {
		this.listMatchKendo = listMatchKendo;
	}
	public List<Integer> getDaysPicked() {
		return daysPicked;
	}
	public void setDaysPicked(List<Integer> daysPicked) {
		this.daysPicked = daysPicked;
	}
	public String getOpponentEmail() {
		return opponentEmail;
	}
	public void setOpponentEmail(String opponentEmail) {
		this.opponentEmail = opponentEmail;
	}
	public String getEventDateExpired() {
		return eventDateExpired;
	}
	public void setEventDateExpired(String eventDateExpired) {
		this.eventDateExpired = eventDateExpired;
	}
	public Date getDateEventExpired() {
		return dateEventExpired;
	}
	public void setDateEventExpired(Date dateEventExpired) {
		this.dateEventExpired = dateEventExpired;
	}
	public int getIsApproved() {
		return isApproved;
	}
	public void setIsApproved(int isApproved) {
		this.isApproved = isApproved;
	}
	public BigInteger getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(BigInteger approvedBy) {
		this.approvedBy = approvedBy;
	}
	public BigInteger getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(BigInteger createdBy) {
		this.createdBy = createdBy;
	}
	public int getIsLocked() {
		return isLocked;
	}
	public void setIsLocked(int isLocked) {
		this.isLocked = isLocked;
	}
	public LocationDTO getLocation() {
		return location;
	}
	public void setLocation(LocationDTO location) {
		this.location = location;
	}
	public Team getHomeTeam() {
		return homeTeam;
	}
	public void setHomeTeam(Team homeTeam) {
		this.homeTeam = homeTeam;
	}
	public Team getOpponentTeam() {
		return opponentTeam;
	}
	public void setOpponentTeam(Team opponentTeam) {
		this.opponentTeam = opponentTeam;
	}
	public UserDTO getUserUpdating() {
		return userUpdating;
	}
	public void setUserUpdating(UserDTO userUpdating) {
		this.userUpdating = userUpdating;
	}
	public Integer getMatchStatus() {
		return matchStatus;
	}
	public void setMatchStatus(Integer matchStatus) {
		this.matchStatus = matchStatus;
	}
	public int getMatchHomeScore() {
		return matchHomeScore;
	}
	public void setMatchHomeScore(int matchHomeScore) {
		this.matchHomeScore = matchHomeScore;
	}
	public int getMatchOpponentScore() {
		return matchOpponentScore;
	}
	public void setMatchOpponentScore(int matchOpponentScore) {
		this.matchOpponentScore = matchOpponentScore;
	}
	public int getSubMatchHomeScore() {
		return subMatchHomeScore;
	}
	public void setSubMatchHomeScore(int subMatchHomeScore) {
		this.subMatchHomeScore = subMatchHomeScore;
	}
	public int getSubMatchOpponentScore() {
		return subMatchOpponentScore;
	}
	public void setSubMatchOpponentScore(int subMatchOpponentScore) {
		this.subMatchOpponentScore = subMatchOpponentScore;
	}
}
