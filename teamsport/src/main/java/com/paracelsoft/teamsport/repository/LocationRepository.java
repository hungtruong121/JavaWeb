package com.paracelsoft.teamsport.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.paracelsoft.teamsport.entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, BigInteger>, JpaSpecificationExecutor<Location>, QuerydslPredicateExecutor<Location> {

	Location findByLocationIdAndIsActive(BigInteger locationId, int i);
	
	List<Location> findByTeamIdAndIsActive(BigInteger teamId, int i);

	@Query(value = "SELECT * FROM location \r\n"
			+ "where team_id = :teamId AND location_name LIKE :keyword OR location_address LIKE :keyword AND is_active = :isActive limit :firstResult, :maxResult", nativeQuery = true)
	List<Location> findByLocationNameAndIsActive(BigInteger teamId, String keyword, int firstResult, int maxResult,
			int isActive);
}
