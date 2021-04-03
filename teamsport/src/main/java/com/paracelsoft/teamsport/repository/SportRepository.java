package com.paracelsoft.teamsport.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.paracelsoft.teamsport.entity.Sport;

@Repository
public interface SportRepository extends JpaRepository<Sport, BigInteger>, JpaSpecificationExecutor<Sport>, QuerydslPredicateExecutor<Sport> {
	
	Sport findBySportIdAndIsActive(BigInteger sportId, int isActive);
	List<Sport> findByIsActive(int isActive);
}