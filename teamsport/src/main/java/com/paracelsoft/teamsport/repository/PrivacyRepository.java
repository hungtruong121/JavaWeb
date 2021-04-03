package com.paracelsoft.teamsport.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paracelsoft.teamsport.entity.Privacy;

@Repository
public interface PrivacyRepository extends JpaRepository<Privacy, BigInteger>, JpaSpecificationExecutor<Privacy>, QuerydslPredicateExecutor<Privacy>, CrudRepository<Privacy, BigInteger> {
	List<Privacy> findAllByIsActive(int i);
}