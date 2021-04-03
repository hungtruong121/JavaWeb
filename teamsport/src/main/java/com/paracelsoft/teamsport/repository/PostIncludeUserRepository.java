package com.paracelsoft.teamsport.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.paracelsoft.teamsport.entity.PostIncludeUser;

@Repository
public interface PostIncludeUserRepository extends JpaRepository<PostIncludeUser, BigInteger>, JpaSpecificationExecutor<PostIncludeUser>, QuerydslPredicateExecutor<PostIncludeUser> {

	List<PostIncludeUser> findByPostIncludeUseTypeAndUserIdAndIsActive(String postIncludeUseType, BigInteger userId, int isActive);
	
	@Modifying
	@Query(value="delete from post_include_user where post_id = :postId and post_include_user_type = :includeType", nativeQuery = true)
	void deleteOldByPostAndType(BigInteger postId, String includeType);

	@Modifying
	@Query(value="delete from post_include_user where post_id = :postId", nativeQuery = true)
	void deleteOldByPost(BigInteger postId);

	@Modifying
	@Query(value="delete from post_include_user where post_comment_id = :postCommentId", nativeQuery = true)
	void deleteOldByPostCommentId(BigInteger postCommentId);
	
	List<PostIncludeUser> findByPostIdAndUserIdAndPostIncludeUseTypeAndIsActiveAndPostCommentIdIsNull(BigInteger postId,
			BigInteger createdBy, String includeType, int isActive);
	
}