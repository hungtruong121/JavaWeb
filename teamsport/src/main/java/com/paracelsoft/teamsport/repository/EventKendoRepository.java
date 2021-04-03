package com.paracelsoft.teamsport.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.paracelsoft.teamsport.entity.EventKendo;

@Repository
public interface EventKendoRepository extends JpaRepository<EventKendo, BigInteger>, JpaSpecificationExecutor<EventKendo>, QuerydslPredicateExecutor<EventKendo>{
	
	EventKendo findByEventKendoIdAndIsActive(BigInteger eventKendoId, int isActive);
	
	EventKendo findByEventKendoId(BigInteger eventKendoId);
	
	List<EventKendo> findByTeamIdAndIsActive(BigInteger teamId, int isActive);
	
	List<EventKendo> findByEventParentId(BigInteger eventParentId); // To delete all child event
	
	@Query(value="SELECT YEAR(updated_date) year from event_kendo WHERE team_id = :teamId OR opponent_team_id = :teamId GROUP BY year", nativeQuery = true)
	List<Map<String, Object>> getAllYearsEventKendoByTeam(BigInteger teamId);
	
	@Query(value="SELECT e.event_kendo_id, ifnull(e.folder_id,'') as folder_id, e.team_id,\n" + 
			"ifnull(e.opponent_team_id,'') as opponent_team_id, e.privacy_id, ifnull(e.location_id,'') as location_id, e.event_type,\n" + 
			"ifnull(e.event_loop_type,'') as event_loop_type, e.event_date, ifnull(e.event_game_type,'') as event_game_type,\n" + 
			"ifnull(e.event_ground,'') as event_ground, ifnull(e.event_match,'') as event_match, ifnull(e.event_home_team,'') as event_home_team,\n" + 
			"ifnull(e.event_opponent_team,'') as event_opponent_team, ifnull(e.event_tournament,'') as event_tournament, ifnull(e.event_sub_matchs,'') \n" + 
			"as event_sub_matchs, ifnull(e.team_color,'') as team_color, ifnull(e.event_hashtag,'') as event_hashtag, ifnull(e.event_notice,'') as event_notice,\n" + 
			"e.is_locked, ifnull(e.user_locked,'') as user_locked, ifnull(e.match_status,'') as match_status, e.created_by, e.created_date, e.updated_date \n" + 
			"FROM event_kendo e JOIN event_match_kendo em ON e.event_kendo_id = em.event_kendo_id WHERE e.event_parent_id IS NOT NULL \n" + 
			"AND e.event_type = :eventType AND e.event_date BETWEEN :dateFrom AND :dateTo AND e.team_id = :teamId AND em.home_user_id IN :userIds \n" + 
			"OR em.opponent_user_id IN :userIds AND e.is_active = 1 GROUP BY e.event_kendo_id ORDER BY e.event_date DESC LIMIT :firstResult, :maxResult", nativeQuery = true)
	List<Map<String, Object>> searchFixtureKendo(String eventType, String dateFrom, String dateTo, BigInteger teamId, List<BigInteger> userIds, int firstResult, int maxResult);
	
	@Query(value="SELECT COUNT(*) FROM ( Select count(e.event_kendo_id)\n" + 
			"FROM event_kendo e JOIN event_match_kendo em ON e.event_kendo_id = em.event_kendo_id WHERE e.event_parent_id IS NOT NULL \n" + 
			"AND e.event_type = :eventType AND e.event_date BETWEEN :dateFrom AND :dateTo AND e.team_id = :teamId AND em.home_user_id IN :userIds \n" + 
			"OR em.opponent_user_id IN :userIds AND e.is_active = 1 GROUP BY e.event_kendo_id ORDER BY e.event_date DESC) AS id;", nativeQuery = true)
	Integer searchCountFixtureKendo(String eventType, String dateFrom, String dateTo, BigInteger teamId, List<BigInteger> userIds);
	
	@Query(value="SELECT e.event_kendo_id, ifnull(e.folder_id,'') as folder_id, e.team_id, ifnull(e.opponent_team_id,'') as opponent_team_id,\n" + 
			"e.privacy_id, ifnull(e.location_id,'') as location_id, e.event_type, ifnull(e.event_loop_type,'') as event_loop_type, \n" + 
			"e.event_date, ifnull(e.event_game_type,'') as event_game_type, ifnull(e.event_ground,'') as event_ground, \n" + 
			"ifnull(e.event_match,'') as event_match, ifnull(e.event_home_team,'') as event_home_team, ifnull(e.event_opponent_team,'') as event_opponent_team, \n" + 
			"ifnull(e.event_tournament,'') as event_tournament, ifnull(e.event_sub_matchs,'') as event_sub_matchs, ifnull(e.team_color,'') as \n" + 
			"team_color, ifnull(e.event_hashtag,'') as event_hashtag, ifnull(e.event_notice,'') as event_notice, e.is_locked, ifnull(e.user_locked,'') \n" + 
			"as user_locked, ifnull(e.match_status,'') as match_status, e.created_by, e.created_date, e.updated_date FROM event_kendo e \n" + 
			"WHERE e.event_parent_id IS NOT NULL AND e.event_type = :eventType AND e.team_id = :teamId AND YEAR(event_date) = :currentYear AND e.is_active = 1 ORDER BY e.event_date DESC", nativeQuery = true)
	List<Map<String, Object>> listFixtureKendoByCurrentYear(String eventType, BigInteger teamId, int currentYear);
	
	@Query(value="SELECT COUNT(*) FROM event_kendo e WHERE e.event_parent_id IS NOT NULL AND e.event_type = :eventType \n" + 
			"AND e.team_id = :teamId AND e.is_active = 1 ORDER BY e.event_date DESC", nativeQuery = true)
	Integer listCountFixtureKendo(String eventType, BigInteger teamId);
	
	@Query(value="SELECT e.event_kendo_id, ifnull(e.folder_id,'') as folder_id, e.team_id, ifnull(e.opponent_team_id,'') as opponent_team_id,\n" + 
			"e.privacy_id, ifnull(e.location_id,'') as location_id, e.event_type, ifnull(e.event_loop_type,'') as event_loop_type, \n" + 
			"e.event_date, ifnull(e.event_game_type,'') as event_game_type, ifnull(e.event_ground,'') as event_ground, \n" + 
			"ifnull(e.event_match,'') as event_match, ifnull(e.event_home_team,'') as event_home_team, ifnull(e.event_opponent_team,'') as event_opponent_team, \n" + 
			"ifnull(e.event_tournament,'') as event_tournament, ifnull(e.event_sub_matchs,'') as event_sub_matchs, ifnull(e.team_color,'') as \n" + 
			"team_color, ifnull(e.event_hashtag,'') as event_hashtag, ifnull(e.event_notice,'') as event_notice, e.is_locked, ifnull(e.user_locked,'') \n" + 
			"as user_locked, ifnull(e.match_status,'') as match_status, ifnull(e.team_win,'') as team_win, e.created_by, e.created_date, e.updated_date FROM event_kendo e \n" + 
			"WHERE e.event_parent_id IS NOT NULL AND e.event_type = :eventType AND e.match_status = :matchStatus AND CASE WHEN :teamId IS NOT NULL \n" + 
			"THEN e.team_id = :teamId WHEN :opponentTeamId IS NOT NULL THEN e.opponent_team_id = :opponentTeamId WHEN :teamId IS NULL THEN e.opponent_team_id = :opponentTeamId \n" + 
			"WHEN :opponentTeamId IS NULL THEN e.team_id = :teamId END AND e.is_active = 1 ORDER BY e.event_date DESC LIMIT 0, :maxResult", nativeQuery = true)
	List<Map<String, Object>> listMatchStats(String eventType, BigInteger teamId, BigInteger opponentTeamId, int matchStatus, int maxResult);
}
