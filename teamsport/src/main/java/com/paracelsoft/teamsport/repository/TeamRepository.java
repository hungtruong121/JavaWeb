package com.paracelsoft.teamsport.repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.paracelsoft.teamsport.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, BigInteger>, JpaSpecificationExecutor<Team>, QuerydslPredicateExecutor<Team> {
	Team findByTeamIdAndIsActive(BigInteger teamId, int isActive);
	
	@Query(value = "SELECT * FROM team\r\n" + 
			"Where team_name = :teamName AND\r\n" + 
			"is_active = :isActive", nativeQuery = true)
	Team findByTeamNameAndIsActive(String teamName, int isActive);
	
	List<Team> findByIsActive(int isActive); // Get all team Active
	
	Team findByTeamId(BigInteger teamId); // Get team by TeamId to change IsActive - Block team (Webadmin)
	
	@Query(value = "select t.* from team t\n" + 
			"join team_member_status tms on t.team_id = tms.team_id  where t.team_id = tms.team_id and tms.user_id = :userId\n" + 
			"and tms.status_id = :statusId and tms.is_active = 1 and t.is_active = 1", nativeQuery = true)
	List<Team> findByUserId(BigInteger userId, BigInteger statusId);
	
	@Query(value = "select count(tm.user_id) from team t join team_member tm \n" + 
			"on t.team_id = tm.team_id  where t.team_id = tm.team_id and tm.team_id = :teamId\n" + 
			"and t.is_active = 1", nativeQuery = true)
	Integer countMembersInTeam(BigInteger teamId);
	
	@Query(value = "select count(tms.user_id) from team t join team_member_status tms \n" + 
			"on t.team_id = tms.team_id  where t.team_id = tms.team_id and tms.team_id = :teamId\n" + 
			"and tms.status_id = :statusId and tms.is_active = 1 and t.is_active = 1", nativeQuery = true)
	Integer countMemberInTeamByStatus(BigInteger teamId, BigInteger statusId);
	
	@Query(value = "SELECT ifnull(t.team_id,'') as team_id,ifnull(t.team_name,'') as team_name,ifnull(t.team_avatar,'1') as team_avatar, \r\n" +  
			" tm.team_member_role\r\n" +
			"FROM team t \r\n" + 
			"JOIN team_member tm ON t.team_id = tm.team_id\r\n" + 
			"WHERE tm.user_id = :userId \r\n" + 
			"AND tm.is_active = :isActive\r\n" + 
			"AND t.is_active = :isActive\r\n"
			+" order by t.team_name asc", nativeQuery = true)
	List<Map<String, Object>> getListTeamByUser(BigInteger userId, int isActive);
	
	@Query(value = "select t.* from team t join team_member tm on t.team_id = tm.team_id\n" + 
			"where t.team_id = tm.team_id and tm.user_id = :userId \n" + 
			"and tm.is_active = 1 and t.is_active = 1 order by t.team_name asc", nativeQuery = true)
	List<Team> getTeamByUser(BigInteger userId); // is JOINED
	
	@Query(value = "SELECT ifnull(t.team_id,'') as team_id,ifnull(t.team_avatar,'') as team_avatar,ifnull(t.sport_name,'') as sport_name,\r\n" + 
			"ifnull(t.team_national,'') as team_national,ifnull(t.team_slogan,'') as team_slogan,ifnull(t.team_description,'') as team_description,ifnull(t.created_by,'') as created_by,\r\n" + 
			"ifnull(t.is_active,'') as is_active,tm.team_member_role,count(tm.user_id) as total_member FROM team t \r\n" + 
			"JOIN team_member tm ON t.team_id = tm.team_id\r\n" + 
			"JOIN team_member_status tms ON t.team_id = tms.team_id\r\n" + 
			"WHERE t.team_id = :teamId\r\n" + 
			"AND tm.is_active = :isActive\r\n" + 
			"AND t.is_active = :isActive\r\n" + 
			"AND tms.is_active = :isActive", nativeQuery = true)
	Map<String, Object> getTeamInfo(BigInteger teamId, int isActive);
	
	@Query(value = "select t.* from team t join team_member tm on t.team_id = tm.team_id\n" + 
			"where t.team_id = tm.team_id and tm.user_id = :userId \n" + 
			"and t.team_id != :teamId \n" +
			"and tm.is_active = 1 and t.is_active = 1 order by t.team_name asc", nativeQuery = true)
	List<Team> getTeamByUserAndTeamNot(BigInteger userId, BigInteger teamId); //joined tru team
	
	@Query(value="SELECT team_member_role FROM team_member WHERE user_id = :userId AND team_id = :teamId AND team_member.is_active = 1;", nativeQuery =  true)
	String roleMemberInTeam(BigInteger teamId, BigInteger userId);
	
	@Query(value="SELECT tr.team_rank_member_limit FROM team t\r\n" + 
			"JOIN team_rank tr on tr.team_rank_id = t.team_rank_id WHERE t.team_id = :teamId AND t.is_active = 1;", nativeQuery =  true)
	Integer maxMembershipOfRank(BigInteger teamId);
	
	@Query(value="SELECT ifnull(sp.sport_name,'') AS sport_name, ifnull(t.team_name,'') AS  team_name, ifnull(u.user_full_name,'') AS user_full_name FROM team t\r\n" + 
			"JOIN sport sp ON t.sport_id = sp.sport_id \r\n" + 
			"JOIN team_member tm ON t.team_id = tm.team_id\r\n" + 
			"JOIN user u ON u.user_id = tm.user_id\r\n" + 
			"WHERE t.team_id = :teamId AND u.user_id = :userId AND u.is_active = 1 AND t.is_active = 1 AND sp.is_active = 1", nativeQuery = true)
	List<Map<String, Object>> listInfoContentEmailByTeamIdandUserId(BigInteger teamId, BigInteger userId);

	@Query(value="SELECT sp.positions FROM team t join sport sp on t.sport_id = sp.sport_id\r\n" + 
			"where t.team_id = :teamId and t.is_active = :isActive and sp.is_active = :isActive", nativeQuery = true)
	List<String> getPositionSportAndIsActive(BigInteger teamId, int isActive);
	
	@Query(value="SELECT team_id, ifnull(t.team_name,'') as team_name, ifnull(t.team_short_name,'') as team_short_name, \r\n" + 
			"ifnull(t.team_mail,'') as team_mail, ifnull(t.sport_id,'') as sport_id,\r\n" + 
			"ifnull(t.team_avatar,'1') as team_avatar, ifnull(t.team_national,'') as team_national, ifnull(t.team_address,'') as team_address,\r\n" + 
			"ifnull(t.team_slogan,'') as team_slogan, ifnull(t.team_description,'') as team_description\r\n" + 
			"FROM team t WHERE is_active = 1 AND team_name like :keyword limit :firstResult, :maxResult", nativeQuery = true)
	List<Map<String, Object>> searchTeamInfoByTeamName(String keyword, int firstResult, int maxResult);
	
	@Query(value="SELECT count(t.team_id) FROM team t WHERE is_active = 1 AND team_name like :keyword ", nativeQuery = true)
	Integer searchCountTeamInfoByTeamName(String keyword);
	
	@Query
	(value="SELECT t.* FROM team t WHERE t.team_rank_expire IS NOT NULL AND t.is_active = 1", nativeQuery = true)
	List<Team>  findExpireTeam();
	
	@Modifying
	@Query
	(value="UPDATE team t SET t.team_rank_expire = :exprieDay WHERE t.team_id = :teamId AND t.is_active = :isActive", nativeQuery = true)
	Integer  updateTeamRankExprie(BigInteger teamId, Date exprieDay, int isActive);
}