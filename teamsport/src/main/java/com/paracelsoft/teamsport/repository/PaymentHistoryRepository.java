package com.paracelsoft.teamsport.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.paracelsoft.teamsport.entity.PaymentHistory;

/**
* Generated by Spring Data Generator on 13/09/2020 
*/
@Repository
public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, BigInteger>, JpaSpecificationExecutor<PaymentHistory>, QuerydslPredicateExecutor<PaymentHistory> {
	
	PaymentHistory findByPaymentHistoryId(BigInteger paymentHistoryId);
	
	@Query(value ="SELECT pmh.payment_history_id, ifnull(pmh.invoice_id, '') as invoice_id, ifnull(pmh.promotion_code,'') as promotion_code, ifnull(t.team_name, '') as team_name, t.team_id, ifnull(t.team_rank_expire, '') as team_rank_expire, ifnull(pmh.service_name, '') as service_name, \r\n" + 
			"pmh.duration, pmh.payment_method, pmh.amount, ifnull(pmh.transaction_id,'') as transaction_id, ifnull(s.status_name, '') as status_name, s.status_id,\r\n" + 
			"ifnull(pmh.error_code, '') as error_code, pmh.created_by, pmh.is_active, pmh.created_date, pmh.updated_date\r\n" + 
			"FROM payment_history pmh\r\n" + 
			"JOIN team t ON t.team_id = pmh.team_id\r\n" + 
			"JOIN status s ON s.status_id = pmh.status_id\r\n", nativeQuery = true)
	List<Map<String, Object>> listPaymentHistories();
}
