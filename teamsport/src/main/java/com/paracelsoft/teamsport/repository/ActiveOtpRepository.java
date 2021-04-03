package com.paracelsoft.teamsport.repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.paracelsoft.teamsport.entity.ActiveOtp;

@Repository
public interface ActiveOtpRepository extends JpaRepository<ActiveOtp, BigInteger>, JpaSpecificationExecutor<ActiveOtp>, QuerydslPredicateExecutor<ActiveOtp> {

	List<ActiveOtp> findAllByActiveEmailAndBeginTimeLessThanEqualAndEndTimeGreaterThanEqualOrderByEndTimeDesc(
			String email, Date timenoww, Date timeNow);

	List<ActiveOtp> findAllByActiveEmail(String email);
	
}