package com.paracelsoft.teamsport.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.paracelsoft.teamsport.entity.EventMatchKendo;

@Repository
public interface EventMatchKendoRepository extends JpaRepository<EventMatchKendo, BigInteger>, JpaSpecificationExecutor<EventMatchKendo>, QuerydslPredicateExecutor<EventMatchKendo>{
	
	EventMatchKendo findByEventMatchIdAndIsActive(BigInteger eventMatchId, int isActive);
	
	List<EventMatchKendo> findByEventKendoIdAndIsActive(BigInteger eventKendoId, int isActive);
	
	List<EventMatchKendo> findByEventKendoId(BigInteger eventKendoId); // to delete
	
	@Query(value="SELECT em.* FROM event_kendo e JOIN event_match_kendo em ON em.event_kendo_id = e.event_kendo_id where e.event_parent_id IS NOT NULL \n" + 
			"AND e.event_type = :eventType AND e.match_status = :matchStatus AND (em.home_user_id = :userId OR em.opponent_user_id = :userId) AND e.is_active = 1 \n" + 
			"AND (e.team_id = :teamId OR e.opponent_team_id = :teamId) AND year(e.updated_date) = :year", nativeQuery = true)
	List<EventMatchKendo> listMatchByYear(String eventType, int matchStatus, BigInteger teamId, BigInteger userId, String year);
}
