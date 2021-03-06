package com.paracelsoft.teamsport.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.paracelsoft.teamsport.entity.TeamMemberStatus;
import com.paracelsoft.teamsport.entity.TeamMemberStatusPK;

/**
* Generated by Spring Data Generator on 16/03/2019
*/
@Repository
public interface TeamMemberStatusRepository extends JpaRepository<TeamMemberStatus, TeamMemberStatusPK>, JpaSpecificationExecutor<TeamMemberStatus>, QuerydslPredicateExecutor<TeamMemberStatus> {
	TeamMemberStatus findById_UserIdAndId_TeamIdAndId_StatusIdAndIsActive(BigInteger userId, BigInteger teamId, BigInteger statusId,int isActive);
	
	List<TeamMemberStatus> findById_UserIdAndId_TeamIdAndIsActive(BigInteger userId, BigInteger teamId,int isActive);
}