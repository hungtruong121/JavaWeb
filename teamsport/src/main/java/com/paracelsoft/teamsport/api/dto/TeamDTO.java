package com.paracelsoft.teamsport.api.dto;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.paracelsoft.teamsport.entity.Post;
import com.paracelsoft.teamsport.entity.Sport;
import com.paracelsoft.teamsport.entity.TeamRank;

public class TeamDTO {
	
	private BigInteger teamId;
	
	private BigInteger userId;
	
	private String userFullName;
	
	private String userMail;
	
	private int userIsActive;
	
	private String teamName;
	
	private String teamShortName;
	
	private String teamMail;
	
	private BigInteger privacyId = new BigInteger("1");;
	
	private BigInteger teamAvatar;
	
	private BigInteger sportId;
	
	private String sportName;
	
	private BigInteger teamRankId = new BigInteger("1");
	
	private String teamRankName;
	 
	private BigInteger teamRankAvatar;
	
	private String teamNational;
	
	private String teamAddress;
	
	private String teamSlogan;
	
	private String teamDescription;
	
	private BigInteger createdby;
	
	private int isActive = 1;
	
	private Date createdDate;
	
	private Date updatedDate;

	private List<UserDTO> userAdmin;
	
	private List<UserDTO> userMember;
	
	private String teamMemberRole;
	
	private int totalMember;
	
	private List<Post> listPost;
	
	private List<Sport> listSport;
	
	private List<TeamRank> listRank;
	
	private List<StatusDTO> teamMemberStatus;
	
	private Date memberSince; // create_date in team_member
	
	private String position;

	private List<PeriodMatchesDTO> listPeriodMatches;

	private String teamAchievements = "SERIA 2018/2019, SERIA 2019/2020, CHAMPIONS LEAGUE 2019/2020";//TODO: DummyData
	
	private List<NotificationDTO> notifications;
	
	private long totalUnreadNotification;

	public BigInteger getTeamId() {
		return teamId;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public String getUserMail() {
		return userMail;
	}

	public int getUserIsActive() {
		return userIsActive;
	}

	public String getTeamName() {
		return teamName;
	}

	public String getTeamShortName() {
		return teamShortName;
	}

	public String getTeamMail() {
		return teamMail;
	}

	public BigInteger getPrivacyId() {
		return privacyId;
	}

	public BigInteger getTeamAvatar() {
		return teamAvatar;
	}

	public BigInteger getSportId() {
		return sportId;
	}

	public String getSportName() {
		return sportName;
	}

	public BigInteger getTeamRankId() {
		return teamRankId;
	}

	public String getTeamRankName() {
		return teamRankName;
	}

	public BigInteger getTeamRankAvatar() {
		return teamRankAvatar;
	}

	public String getTeamNational() {
		return teamNational;
	}

	public String getTeamAddress() {
		return teamAddress;
	}

	public String getTeamSlogan() {
		return teamSlogan;
	}

	public String getTeamDescription() {
		return teamDescription;
	}

	public BigInteger getCreatedby() {
		return createdby;
	}

	public int getIsActive() {
		return isActive;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public List<UserDTO> getUserAdmin() {
		return userAdmin;
	}

	public List<UserDTO> getUserMember() {
		return userMember;
	}

	public String getTeamMemberRole() {
		return teamMemberRole;
	}

	public int getTotalMember() {
		return totalMember;
	}

	public List<Post> getListPost() {
		return listPost;
	}

	public List<Sport> getListSport() {
		return listSport;
	}

	public List<TeamRank> getListRank() {
		return listRank;
	}

	public List<StatusDTO> getTeamMemberStatus() {
		return teamMemberStatus;
	}

	public Date getMemberSince() {
		return memberSince;
	}

	public String getPosition() {
		return position;
	}

	public List<PeriodMatchesDTO> getListPeriodMatches() {
		return listPeriodMatches;
	}

	public String getTeamAchievements() {
		return teamAchievements;
	}

	public List<NotificationDTO> getNotifications() {
		return notifications;
	}

	public long getTotalUnreadNotification() {
		return totalUnreadNotification;
	}

	public void setTeamId(BigInteger teamId) {
		this.teamId = teamId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public void setUserIsActive(int userIsActive) {
		this.userIsActive = userIsActive;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public void setTeamShortName(String teamShortName) {
		this.teamShortName = teamShortName;
	}

	public void setTeamMail(String teamMail) {
		this.teamMail = teamMail;
	}

	public void setPrivacyId(BigInteger privacyId) {
		this.privacyId = privacyId;
	}

	public void setTeamAvatar(BigInteger teamAvatar) {
		this.teamAvatar = teamAvatar;
	}

	public void setSportId(BigInteger sportId) {
		this.sportId = sportId;
	}

	public void setSportName(String sportName) {
		this.sportName = sportName;
	}

	public void setTeamRankId(BigInteger teamRankId) {
		this.teamRankId = teamRankId;
	}

	public void setTeamRankName(String teamRankName) {
		this.teamRankName = teamRankName;
	}

	public void setTeamRankAvatar(BigInteger teamRankAvatar) {
		this.teamRankAvatar = teamRankAvatar;
	}

	public void setTeamNational(String teamNational) {
		this.teamNational = teamNational;
	}

	public void setTeamAddress(String teamAddress) {
		this.teamAddress = teamAddress;
	}

	public void setTeamSlogan(String teamSlogan) {
		this.teamSlogan = teamSlogan;
	}

	public void setTeamDescription(String teamDescription) {
		this.teamDescription = teamDescription;
	}

	public void setCreatedby(BigInteger createdby) {
		this.createdby = createdby;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public void setUserAdmin(List<UserDTO> userAdmin) {
		this.userAdmin = userAdmin;
	}

	public void setUserMember(List<UserDTO> userMember) {
		this.userMember = userMember;
	}

	public void setTeamMemberRole(String teamMemberRole) {
		this.teamMemberRole = teamMemberRole;
	}

	public void setTotalMember(int totalMember) {
		this.totalMember = totalMember;
	}

	public void setListPost(List<Post> listPost) {
		this.listPost = listPost;
	}

	public void setListSport(List<Sport> listSport) {
		this.listSport = listSport;
	}

	public void setListRank(List<TeamRank> listRank) {
		this.listRank = listRank;
	}

	public void setTeamMemberStatus(List<StatusDTO> teamMemberStatus) {
		this.teamMemberStatus = teamMemberStatus;
	}

	public void setMemberSince(Date memberSince) {
		this.memberSince = memberSince;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setListPeriodMatches(List<PeriodMatchesDTO> listPeriodMatches) {
		this.listPeriodMatches = listPeriodMatches;
	}

	public void setTeamAchievements(String teamAchievements) {
		this.teamAchievements = teamAchievements;
	}

	public void setNotifications(List<NotificationDTO> notifications) {
		this.notifications = notifications;
	}

	public void setTotalUnreadNotification(long totalUnreadNotification) {
		this.totalUnreadNotification = totalUnreadNotification;
	}
}
