package com.paracelsoft.teamsport.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.paracelsoft.teamsport.entity.Upload;

/**
* Generated by Spring Data Generator on 16/03/2019
*/
@Repository
public interface UploadRepository extends JpaRepository<Upload, BigInteger>, JpaSpecificationExecutor<Upload>, QuerydslPredicateExecutor<Upload> {

	Upload findByUploadId(BigInteger bigInteger);

	Upload findFirstByOrderByUploadIdDesc();

	List<Upload> findAllByUploadId(BigInteger uploadId);
	
}