package com.paracelsoft.teamsport.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.paracelsoft.teamsport.entity.PersistentToken;

@Repository
public interface PersistentTokenRepository extends JpaRepository<PersistentToken, BigInteger>, JpaSpecificationExecutor<PersistentToken>, QuerydslPredicateExecutor<PersistentToken> {

}
