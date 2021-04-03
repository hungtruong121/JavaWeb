package com.paracelsoft.teamsport.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.paracelsoft.teamsport.entity.Accounting;

@Repository
public interface AccountingRepository extends JpaRepository<Accounting, BigInteger>, JpaSpecificationExecutor<Accounting>, QuerydslPredicateExecutor<Accounting> {

	Accounting findByAccountingIdAndIsActive(BigInteger accountingId, int isActive);
	
	Accounting findByAccountingIdAndTeamIdAndIsActive(BigInteger accountingId, BigInteger teamId, int isActive);
	
	Accounting findByAccountingId(BigInteger accountingId); // Get to delete
	
	List<Accounting> findByTeamIdAndIsActive(BigInteger teamId, int isActive);
	
	@Query(value = "select a.accounting_id as accounting_id, a.team_id as team_id, ai.user_id as user_id,\r\n" + 
			"ifnull(a.accounting_deadline,'') as accounting_deadline, ifnull(a.accounting_title,'') as accounting_title,\r\n" + 
			"ifnull(a.accounting_amount,'') as accounting_amount, ifnull(a.accounting_notice,'') as accounting_notice,\r\n" + 
			"ifnull(ai.status_id,'') as status_id, ifnull(ai.img_evidence,'') as img_evidence\r\n" + 
			"FROM accounting_include ai JOIN accounting a ON ai.accounting_id = a.accounting_id\r\n" + 
			"WHERE ai.user_id = :userId AND a.team_id = :teamId AND ai.is_active = :isActive AND a.is_active = :isActive",nativeQuery = true)
	List<Map<String, Object>> getListAccountingUserInclude(BigInteger userId, BigInteger teamId, int isActive);

	@Query(value="select (select count(*) from accounting_include where accounting_id = a.accounting_id and is_active = 1) as members_include,\r\n" + 
			"(select count(*) from accounting_include where accounting_id = a.accounting_id and status_id = :notPaid and is_active = 1) as members_not_paid,\r\n" + 
			"(select count(*) from accounting_include where accounting_id = a.accounting_id and status_id = :waiting and is_active = 1) as members_waiting,\r\n" + 
			"(select count(*) from accounting_include where accounting_id = a.accounting_id and status_id = :done and is_active = 1) as members_done,\r\n" + 
			" a.accounting_id as accounting_id, a.team_id as team_id, ifnull(a.accounting_deadline,'') as accounting_deadline, \r\n" + 
			" ifnull(a.accounting_title,'') as accounting_title, ifnull(a.accounting_amount,'') as accounting_amount, \r\n" + 
			" ifnull(a.accounting_notice,'') as accounting_notice FROM accounting a WHERE a.team_id = :teamId AND a.is_active = 1\r\n" + 
			"AND a.accounting_title like :keyword ORDER BY a.created_date DESC limit :firstResult, :maxResult", nativeQuery = true)
	List<Map<String, Object>> searchAccountingByAdmin(BigInteger notPaid, BigInteger waiting, BigInteger done, BigInteger teamId, String keyword, int firstResult, int maxResult);

	@Query(value="select count(a.accounting_id) FROM accounting a WHERE a.team_id = :teamId AND a.is_active = 1 AND a.accounting_title like :keyword", nativeQuery = true)
	Integer searchCountAccountingByAdmin(BigInteger teamId, String keyword);
	
	@Query(value="select a.accounting_id as accounting_id, a.team_id as team_id, ai.user_id as user_id,\r\n" + 
			"ifnull(a.accounting_deadline,'') as accounting_deadline, ifnull(a.accounting_title,'') as accounting_title,\r\n" + 
			"ifnull(a.accounting_amount,'') as accounting_amount, ifnull(a.accounting_notice,'') as accounting_notice,\r\n" + 
			"ifnull(ai.status_id,'') as status_id, ifnull(ai.img_evidence,'') as img_evidence\r\n" + 
			"FROM accounting_include ai JOIN accounting a ON ai.accounting_id = a.accounting_id\r\n" + 
			"WHERE ai.user_id = :userId AND a.team_id = :teamId AND ai.is_active = 1 AND a.is_active = 1\r\n" + 
			"AND a.accounting_title like :keyword ORDER BY a.created_date DESC limit :firstResult, :maxResult", nativeQuery = true)
	List<Map<String, Object>> searchAccountingByUser(BigInteger userId, BigInteger teamId, String keyword, int firstResult, int maxResult);

	@Query(value="select count(a.accounting_id) FROM accounting_include ai JOIN accounting a \r\n" + 
			"ON ai.accounting_id = a.accounting_id\r\n" + 
			"WHERE ai.user_id = :userId AND a.team_id = :teamId AND ai.is_active = 1 AND a.is_active = 1\r\n" + 
			"AND a.accounting_title like :keyword", nativeQuery = true)
	Integer searchCountAccountingByUser(BigInteger userId, BigInteger teamId, String keyword);
}
