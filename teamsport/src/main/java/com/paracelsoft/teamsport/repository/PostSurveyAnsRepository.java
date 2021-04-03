package com.paracelsoft.teamsport.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.paracelsoft.teamsport.entity.PostSurveyAns;

@Repository
public interface PostSurveyAnsRepository extends JpaRepository<PostSurveyAns, BigInteger>, JpaSpecificationExecutor<PostSurveyAns>, QuerydslPredicateExecutor<PostSurveyAns>{
	PostSurveyAns findByAnsIdAndIsActive(BigInteger ansId, int isActive);
	
	@Modifying
	@Query(value = "DELETE pu FROM post_survey_ans pa JOIN portal_upload pu ON pu.pupload_id = pa.ans_image WHERE pa.post_id = :postId", nativeQuery = true)
	void deleteUploadByPostId(BigInteger postId); 
	
	@Modifying
	@Query(value = "DELETE pa FROM post_survey_ans pa JOIN post p ON p.post_id = pa.post_id WHERE p.post_id = :postId", nativeQuery = true)
	void deleteAnsByPostId(BigInteger postId); 
	
	@Query(value = "SELECT * FROM post_survey_ans pa JOIN post p ON p.post_id = pa.post_id WHERE p.post_id = :postId", nativeQuery = true)
	PostSurveyAns getAnsExistingByPostId(BigInteger postId); 
	
	@Query(value = "SELECT u.user_id, p.post_id, p.privacy_id, u.user_full_name, p.team_id, u.user_avatar, \r\n" +  
			"ifnull(p.post_Type,'') as post_type, p.post_content, pc.privacy_name,\r\n" + 
			"ifnull(p.background_image,'') as background_image, ifnull(p.background_default,'') as background_default, \r\n" + 
			"p.post_survey_deadline, ifnull(p.is_multiple,'0') as is_multiple , ifnull(p.is_extends_ans,'0') as is_extends_ans, \r\n" + 
			"ifnull(p.locale,'') as locale, ifnull(p.post_content_font_size,'') as post_content_font_size, p.created_date\r\n" + 
			"FROM post p \r\n" + 
			"JOIN user u ON u.user_id = p.created_by \r\n" + 
			"JOIN privacy pc on pc.privacy_id = p.privacy_id\r\n" + 
			"WHERE p.post_id = :postId  AND p.is_active = 1", nativeQuery = true)
	Map<String, Object> getSurveyDetail(BigInteger postId);
	
	@Query(value = "SELECT u.user_id, p.post_id, sa.ans_id, u.user_avatar, u.user_full_name,   \r\n" + 
			"p.post_Type, p.post_content,\r\n" + 
			"ifnull(sa.ans_content,'') as ans_content, ifnull(sa.ans_image,'') as ans_image, \r\n" + 
			"ifnull(p.is_multiple,'') as is_multiple , ifnull(p.is_extends_ans,'') as is_extends_ans \r\n" + 
			"FROM post p  \r\n" + 
			"JOIN post_survey_ans sa on sa.post_id = p.post_id \r\n" + 
			"JOIN user u ON u.user_id = sa.created_by\r\n" + 
			"WHERE p.post_id = :postId AND p.is_active = 1", nativeQuery = true)
	List<Map<String, Object>> listAnswerByPostId(BigInteger postId);
	
	@Query(value = "SELECT u.user_id, pa.ans_id, u.user_avatar, u.user_full_name, pa.ans_content FROM post_survey_vote pv\r\n" + 
			"	JOIN user u ON u.user_id = pv.created_by\r\n" + 
			"	JOIN post_survey_ans pa ON pa.ans_id = pv.ans_id\r\n" + 
			"	WHERE pa.ans_id = :ansId AND u.is_active = 1", nativeQuery = true)
	List<Map<String, Object>> listUsersByAnsId(BigInteger ansId);
	
	@Query(value = "SELECT COUNT(pa.ans_id) FROM post_survey_ans pa JOIN post p ON p.post_id = pa.post_id WHERE p.post_Type = :postType AND pa.post_id = :postId AND p.is_active = 1", nativeQuery = true)
	Integer totalAnswers(BigInteger postId, String postType);
	
	@Query(value = "SELECT COUNT(pv.vote_id) FROM post_survey_vote pv JOIN post_survey_ans pa ON pa.ans_id = pv.ans_id JOIN post p ON p.post_id = pa.post_id WHERE p.post_Type = :postType AND pa.post_id = :postId AND p.is_active = 1", nativeQuery = true)
	Integer totalVotes(BigInteger postId, String postType);
	
	@Query(value = "SELECT COUNT(pv.vote_id) FROM post_survey_vote pv JOIN post_survey_ans pa ON pa.ans_id = pv.ans_id JOIN post p ON p.post_id = pa.post_id WHERE p.post_Type = :postType AND pa.post_id = :postId AND pv.ans_id = :ansId AND p.is_active = 1", nativeQuery = true)
	Integer totalVotedByAnsId(BigInteger postId, String postType, BigInteger ansId);
	
	@Modifying
	@Query(value = "DELETE pa FROM post_survey_ans pa JOIN post p ON p.post_id = pa.post_id WHERE pa.ans_id = :ansId  AND pa.is_active = 1", nativeQuery = true)
	void deleteAnsByAnId(BigInteger ansId); 
	
	@Query(value = "SELECT hc.hashtag_container_id, hc.hashtag_container_value FROM hashtag_container hc JOIN post p ON p.post_id = hc.post_id WHERE p.post_id = :postId AND p.is_active = 1", nativeQuery = true)
	List<Map<String, Object>> listHashagByPostId(BigInteger postId);
	
	@Query(value = "SELECT pu.post_include_user_id, u.user_id, u.user_full_name, u.user_avatar FROM post_include_user pu JOIN post p ON p.post_id = pu.post_id JOIN user u ON u.user_id = pu.user_id WHERE p.post_id = :postId AND p.is_active = 1", nativeQuery = true)
	List<Map<String, Object>> listIncludeUserByPostId(BigInteger postId);

}
