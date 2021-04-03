package com.paracelsoft.teamsport.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.paracelsoft.teamsport.entity.SportPoint;

@Repository
public interface SportPointRepository extends JpaRepository<SportPoint, BigInteger>, JpaSpecificationExecutor<SportPoint>, QuerydslPredicateExecutor<SportPoint> {
	
	List<SportPoint> findBySportIdAndIsActive(BigInteger sportId, int isActive);
	
	@Modifying
	@Query(value="delete from sport_point where sport_id = :sportId", nativeQuery = true)
	void deleteOldSportPoint(BigInteger sportId);
}