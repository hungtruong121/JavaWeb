package com.paracelsoft.teamsport.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.paracelsoft.teamsport.entity.AccountingInclude;
import com.paracelsoft.teamsport.entity.AccountingIncludePK;

@Repository
public interface AccountingIncludeRepository extends JpaRepository<AccountingInclude, AccountingIncludePK>,
		JpaSpecificationExecutor<AccountingInclude>, QuerydslPredicateExecutor<AccountingInclude> {

	AccountingInclude findById_AccountingIdAndId_UserIdAndIsActive(BigInteger accountingId, BigInteger userId, int isActive);

	List<AccountingInclude> findById_AccountingIdAndIsActive(BigInteger accountingId, int isActive);
	
	List<AccountingInclude> findById_AccountingIdAndStatusIdAndIsActive(BigInteger accountingId, BigInteger statusId, int isActive);

	@Query(value = "select count(*) from accounting_include where accounting_id = :accountingId and status_id = :statusId and is_active = 1", nativeQuery = true)
	Integer countMemberIncludeByStatus(BigInteger accountingId, BigInteger statusId);
	
	@Query(value = "select count(*) from accounting_include where accounting_id = :accountingId and is_active = 1", nativeQuery = true)
	Integer countMemberInclude(BigInteger accountingId);
}
