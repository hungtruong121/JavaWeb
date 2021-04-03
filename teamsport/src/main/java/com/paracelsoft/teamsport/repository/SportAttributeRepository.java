package com.paracelsoft.teamsport.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.paracelsoft.teamsport.entity.SportAttribute;

@Repository
public interface SportAttributeRepository extends JpaRepository<SportAttribute, BigInteger>, JpaSpecificationExecutor<SportAttribute>, QuerydslPredicateExecutor<SportAttribute> {
	
	List<SportAttribute> findBySportIdAndIsActive(BigInteger sportId, int isActive);
	
	@Modifying
	@Query(value="delete from sport_attribute where sport_id = :sportId", nativeQuery = true)
	void deleteOldSportAttribute(BigInteger sportId);
}