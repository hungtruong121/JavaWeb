package com.paracelsoft.teamsport.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.paracelsoft.teamsport.entity.TokenFirebase;

@Repository
public interface TokenFirebaseRepository extends JpaRepository<TokenFirebase, BigInteger>, JpaSpecificationExecutor<TokenFirebase>, QuerydslPredicateExecutor<TokenFirebase> {

	List<TokenFirebase> findAllByOrderByCreatedDateAsc();

	TokenFirebase findByUserId(BigInteger userId);

}
