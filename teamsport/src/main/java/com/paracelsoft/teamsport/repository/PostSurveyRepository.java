package com.paracelsoft.teamsport.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.paracelsoft.teamsport.entity.Post;
import com.paracelsoft.teamsport.entity.PostSurveyAns;

@Repository
public interface PostSurveyRepository extends JpaRepository<PostSurveyAns, BigInteger>, JpaSpecificationExecutor<PostSurveyAns>, QuerydslPredicateExecutor<PostSurveyAns> {
	Post findByPostIdAndIsActive(BigInteger postId, int i);
	
	@Query(value="select count(*) from post_like where post_id = :postId and is_active = 1;", nativeQuery = true)
	Integer countLikePost(BigInteger postId);
}