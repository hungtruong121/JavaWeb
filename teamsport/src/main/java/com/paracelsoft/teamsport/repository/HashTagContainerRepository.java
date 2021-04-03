package com.paracelsoft.teamsport.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.paracelsoft.teamsport.entity.HashtagContainer;

@Repository
public interface HashTagContainerRepository extends JpaRepository<HashtagContainer, BigInteger>, JpaSpecificationExecutor<HashtagContainer>, QuerydslPredicateExecutor<HashtagContainer> {

	@Modifying
	@Query(value="delete from hashtag_container where post_id = :postId and team_id = :teamId", nativeQuery = true)
	void deleteOldByPostAndTeam(BigInteger postId, BigInteger teamId);

	@Query(value="SELECT distinct(hashtag_container_value) FROM hashtag_container where \r\n" + 
			"team_id = :teamId and privacy_id = :privacyId and is_active = :isActive \r\n" + 
			"and hashtag_container_value like :keyword limit 0, 10", nativeQuery = true)
	List<String> searchPublicByTeamAndKeyWordAndIsActive(BigInteger privacyId, BigInteger teamId, String keyword,
			int isActive);
	
	@Query(value = "SELECT hashtag_container_value  FROM teamsport.hashtag_container\r\n" + 
			"WHERE team_id = :teamId\r\n" + 
			"AND post_id = :postId \r\n" + 
			"AND is_active = :isActive", nativeQuery = true)
	List<String> getListHashTagByTeamIdAndPostId(BigInteger teamId, BigInteger postId,int isActive);
}