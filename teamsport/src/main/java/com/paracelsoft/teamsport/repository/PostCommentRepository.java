package com.paracelsoft.teamsport.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.paracelsoft.teamsport.entity.PostComment;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, BigInteger>, JpaSpecificationExecutor<PostComment>, QuerydslPredicateExecutor<PostComment> {

	@Modifying
	@Query(value="delete from post_comment where post_id = :postId", nativeQuery = true)
	void deleteOldByPost(BigInteger postId);
	
	@Query(value = "select count(*) from post_comment where post_id = :postId and is_active = 1", nativeQuery = true)
	Integer countPostComments(BigInteger postId);

	PostComment findByPostCommentIdAndIsActive(BigInteger postCommentId, int isActive);

	@Modifying
	@Query(value="delete from post_comment where post_comment_parent_Id = :postCommentId", nativeQuery = true)
	void deleteOldbyPostCommentParent(BigInteger postCommentId);

	List<PostComment> findAllByPostIdAndIsActiveAndPostCommentParentIdIsNullOrderByCreatedDateAsc(BigInteger postId, int isActive);

	List<PostComment> findAllByPostIdAndPostCommentParentIdAndIsActiveOrderByCreatedDateAsc(BigInteger postId, BigInteger postCommentId,
			int isActive);

	@Query(value = "select count(*) from post_comment where post_comment_parent_id = :postCommentId and is_active = :isActive", nativeQuery = true)
	Integer countCommentOfComment(BigInteger postCommentId, int isActive);
	
}