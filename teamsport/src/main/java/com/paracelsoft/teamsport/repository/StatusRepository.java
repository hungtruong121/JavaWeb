package com.paracelsoft.teamsport.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.paracelsoft.teamsport.entity.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, BigInteger>, JpaSpecificationExecutor<Status>, QuerydslPredicateExecutor<Status> {
	
}