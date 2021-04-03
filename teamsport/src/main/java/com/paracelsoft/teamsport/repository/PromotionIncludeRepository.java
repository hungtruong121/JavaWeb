package com.paracelsoft.teamsport.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.paracelsoft.teamsport.entity.PromotionInclude;

@Repository
public interface PromotionIncludeRepository extends JpaRepository<PromotionInclude, BigInteger>, JpaSpecificationExecutor<PromotionInclude>, QuerydslPredicateExecutor<PromotionInclude> {

	PromotionInclude findByPromotionIncludeIdAndIsActive(BigInteger promotionIncludeId, int isActive);
	
	PromotionInclude findByPromotionIdAndTeamIdAndIsActive(BigInteger promotionId, BigInteger teamId, int isActive);
	
	List<PromotionInclude> findByTeamIdAndIsActive(BigInteger teamId, int isActive);
	
	List<PromotionInclude> findByPromotionIdAndIsActive(BigInteger promotionId, int isActive);
	
	List<PromotionInclude> findByPromotionId(BigInteger promotionId); // To delete promotion
}
