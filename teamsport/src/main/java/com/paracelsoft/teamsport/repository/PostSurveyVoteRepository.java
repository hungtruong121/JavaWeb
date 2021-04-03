package com.paracelsoft.teamsport.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.paracelsoft.teamsport.entity.PostSurveyVote;

@Repository
public interface PostSurveyVoteRepository extends JpaRepository<PostSurveyVote, BigInteger>, JpaSpecificationExecutor<PostSurveyVote>, QuerydslPredicateExecutor<PostSurveyVote>{
	
	@Query(value = "SELECT * FROM post_survey_vote pv JOIN post_survey_ans pa ON pv.ans_id = pa.ans_id JOIN post p ON p.post_id = pa.post_id WHERE p.post_id = :postId", nativeQuery = true)
	PostSurveyVote getVoteExistingByPostId(BigInteger postId);
	
	@Modifying
	@Query(value = "DELETE pv FROM post_survey_vote pv JOIN post_survey_ans pa ON pa.ans_id = pv.ans_id JOIN post p ON p.post_id = pa.post_id WHERE p.post_id = :postId AND p.is_active = 1", nativeQuery = true)
	void deleteVoteByPostId(BigInteger postId); 
	
	@Query(value = "SELECT count(pv.ans_id) FROM post_survey_vote pv JOIN post_survey_ans pa ON pv.ans_id = pa.ans_id JOIN post p ON p.post_id = pa.post_id WHERE p.post_id = :postId AND pv.ans_id = :ansId", nativeQuery = true)
	 Integer getVoteExist(BigInteger postId, BigInteger ansId);
	
	@Query(value = "SELECT count(u.user_id)	FROM post_survey_vote pv JOIN user u ON u.user_id = pv.created_by WHERE pv.created_by = :userId AND u.is_active = 1", nativeQuery = true)
	 Integer getUserVoteOrNot(BigInteger userId);		
	
	@Query(value="SELECT count(pv.ans_id) FROM post_survey_vote pv JOIN post_survey_ans pa ON pv.ans_id = pa.ans_id JOIN post p ON p.post_id = pa.post_id WHERE p.post_id = :postId AND pv.created_by = :userId AND pv.is_active = 1", nativeQuery = true)
	Integer countVoteByPostAndUserId(BigInteger postId, BigInteger userId);
}
