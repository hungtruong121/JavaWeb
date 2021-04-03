package com.paracelsoft.teamsport.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.paracelsoft.teamsport.entity.SportRound;

@Repository
public interface SportRoundRepository extends JpaRepository<SportRound, BigInteger>, JpaSpecificationExecutor<SportRound>, QuerydslPredicateExecutor<SportRound> {
	
	List<SportRound> findBySportIdAndIsActive(BigInteger sportId, int isActive);
	
	@Modifying
	@Query(value="delete from sport_round where sport_id = :sportId", nativeQuery = true)
	void deleteOldSportRound(BigInteger sportId);
	
}