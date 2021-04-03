package com.paracelsoft.teamsport.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.paracelsoft.teamsport.entity.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, BigInteger>, JpaSpecificationExecutor<Promotion>, QuerydslPredicateExecutor<Promotion> {

	Promotion findByPromotionIdAndIsActive(BigInteger promotionId, int isActive);
	
	Promotion findByPromotionId(BigInteger promotionId); // Get to delete
	
	List<Promotion> findByIsActive(int isActive); // Get list Promotion
	
	List<Promotion> findByStatusIdAndIsActive(BigInteger statusId, int isActive);
	
	List<Promotion> findByPromotionCode(String promotionCode); // to check promotionCode exists
}
