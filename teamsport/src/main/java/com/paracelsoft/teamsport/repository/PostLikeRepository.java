package com.paracelsoft.teamsport.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.paracelsoft.teamsport.entity.PostLike;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, BigInteger>, JpaSpecificationExecutor<PostLike>, QuerydslPredicateExecutor<PostLike> {

	@Modifying
	@Query(value="delete from post_like where post_id = :postId", nativeQuery = true)
	void deleteOldByPost(BigInteger postId);
	
	@Query(value = "select count(*) from post_like where post_id = :postId and (post_comment_id is null or post_comment_id = '') and is_active = 1", nativeQuery = true)
	Integer countPostLike(BigInteger postId);

	List<PostLike> findAllByPostIdAndUserIdAndIsActive(BigInteger postId, BigInteger userId, int isActive);

	@Modifying
	@Query(value="delete from post_like where post_comment_id= :postCommentId", nativeQuery = true)
	void deleteOldbyPostComment(BigInteger postCommentId);
	
	/**
	 * 
	 * @Des like post
	 * @param postId
	 * @param curentLoginUserId
	 * @param isActive
	 * @return
	 */
	List<PostLike> findAllByPostIdAndUserIdAndIsActiveAndPostCommentIdIsNull(BigInteger postId,
			BigInteger curentLoginUserId, int isActive);

	/**
	 * 
	 * @Des like comment
	 * @param postId
	 * @param curretnUserLoginId
	 * @param postCommentId
	 * @param isActive
	 * @return
	 */
	List<PostLike> findAllByPostIdAndUserIdAndPostCommentIdAndIsActive(BigInteger postId, BigInteger curretnUserLoginId,
			BigInteger postCommentId, int isActive);

	@Query(value="select count(*) from post_like where post_comment_id = :postCommentId and is_active = :isActive", nativeQuery = true)
	Integer countLikePostComment(BigInteger postCommentId, int isActive);
	
}