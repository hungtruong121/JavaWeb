package com.paracelsoft.teamsport.repository;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.paracelsoft.teamsport.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, BigInteger>, JpaSpecificationExecutor<Post>, QuerydslPredicateExecutor<Post> {

	Post findByPostIdAndIsActive(BigInteger postId, int i);
	Post findByPostIdAndPrivacyIdAndIsActive(BigInteger postId, BigInteger privacyId, int isActive);
	
	@Query(value= "SELECT p.* FROM post p left join post_include_user pi\r\n" + 
			"			on pi.post_id = p.post_id\r\n" + 
			"			WHERE p.team_id = :teamId\r\n" + 
			"			AND p.is_active = :isActive AND\r\n" + 
			"            case when :userId is null then p.privacy_id = :privacyid\r\n" + 
			"			else ((pi.user_id = :userId and pi.is_active = :isActive) or p.privacy_id = :privacyid or p.created_by = :userId) end"
			+ " group by p.post_id order by p.updated_date desc", nativeQuery =  true)
	List<Post> getListPostByTeamId(BigInteger teamId,BigInteger privacyid, BigInteger userId, int isActive);
	
	@Query(value= "SELECT * FROM post p left join post_include_user pi\r\n" + 
			"			on pi.post_id = p.post_id\r\n" + 
			"			WHERE p.team_id = :teamId\r\n" + 
			"			AND p.is_active = :isActive AND p.post_Type = :postType AND\r\n" + 
			"            case when :userId is null then p.privacy_id IN :listPrivacy\r\n" + 
			"			else ((pi.user_id = :userId and pi.is_active = :isActive) or p.privacy_id IN :listPrivacy  or p.created_by = :userId) end order by p.created_date desc", nativeQuery =  true)
	List<Post> getListPostByTeamId(BigInteger teamId,String postType,Collection<BigInteger> listPrivacy, BigInteger userId, int isActive);
	
	
	@Query(value= "SELECT * FROM post p left join post_include_user pi\r\n" + 
			" on pi.post_id = p.post_id\r\n" + 
			" WHERE p.team_id = :teamId\r\n" + 
			" AND (p.post_parent_id is null AND p.post_Type is null OR p.post_Type = :postType)\r\n" + 
			" AND p.is_active = 1 AND\r\n" + 
			" case when :userId is null then p.privacy_id IN :listPrivacy\r\n" + 
			" else ((pi.user_id = :userId and pi.is_active = :isActive) or p.privacy_id IN :listPrivacy  or p.created_by = :userId) end order by p.created_date desc", nativeQuery =  true)
	List<Post> getPostsByTeamId(BigInteger teamId,String postType,Collection<BigInteger> listPrivacy, BigInteger userId, int isActive);
	
	@Query(value= "SELECT * FROM post p left join post_include_user pi\r\n" + 
			"			on pi.post_id = p.post_id\r\n" + 
			"			WHERE p.post_parent_id = :postParentId\r\n" + 
			"			AND p.is_active = :isActive AND\r\n" + 
			"            case when :userId is null then p.privacy_id = :privacyid\r\n" + 
			"			else (pi.user_id = :userId or p.privacy_id = :privacyid)  and pi.is_active = :isActive end\r\n" + 
			"            order by p.created_date desc", nativeQuery =  true)
	List<Post> getListPostByPostParentId(BigInteger postParentId,BigInteger privacyid, BigInteger userId, int isActive);
	
	@Query(value = "Select ifnull(u.user_id,'') as user_id, ifnull(u.user_full_name,'') as user_full_name, ifnull(u.user_avatar,'1') as user_avatar\r\n" + 
			"FROM post_include_user piu JOIN user u ON piu.user_id = u.user_id\r\n" + 
			"WHERE\r\n" + 
			"piu.post_include_user_type = :includeType and (piu.post_comment_id is null or piu.post_comment_id = '')\r\n" + 
			"AND piu.post_id = :postId\r\n" + 
			"AND piu.is_active = :isActive\r\n" + 
			"AND u.is_active = :isActive",nativeQuery = true)
	List<Map<String, Object>> getListUserIncludePost(String includeType, BigInteger postId, int isActive);
	
	@Query(value = "Select ifnull(u.user_id,'') as user_id, ifnull(u.user_full_name,'') as user_full_name, ifnull(u.user_avatar,'1') as user_avatar\r\n" + 
			"FROM post_include_user piu JOIN user u ON piu.user_id = u.user_id\r\n" +
			"WHERE\r\n" + 
			"piu.post_include_user_type = :includeType and piu.post_comment_id = :commentId \r\n" + 
			"AND piu.post_id = :postId\r\n" + 
			"AND piu.is_active = :isActive\r\n" + 
			"AND u.is_active = :isActive",nativeQuery = true)
	List<Map<String, Object>> getListUserIncludeComment(BigInteger commentId, String includeType, BigInteger postId, int isActive);
	
	List<Post> findAllByPostParentId(BigInteger postParentId);
	
	@Query(value= "SELECT p.* from post p \r\n" + 
			"JOIN portal_upload pu ON p.post_media = pu.pupload_id\r\n" + 
			"LEFT JOIN post_include_user pi ON p.post_id = pi.post_id\r\n" + 
			"WHERE p.team_id = :teamId AND pu.pupload_contenttype LIKE :type\r\n" + 
			"AND case when :userId is null then p.privacy_id IN :listPrivacy\r\n" + 
			"else ((pi.user_id = :userId and pi.is_active = :isActive) or p.privacy_id IN :listPrivacy or p.created_by = :userId AND pi.is_active = :isActive) end \r\n" + 
			"AND p.is_active = :isActive\r\n" + 
			"AND pu.is_active = :isActive\r\n" + 
			"ORDER BY p.created_date desc", nativeQuery =  true)
	List<Post> getListPostsByTeamId(BigInteger teamId,String type,Collection<BigInteger> listPrivacy, BigInteger userId, int isActive);
	
	@Query(value= "SELECT * FROM post\r\n" + 
			"where post_parent_id = :postId AND is_active = :isActive", nativeQuery =  true)
	List<Post> getListPostParent(BigInteger postId, int isActive);
	
	@Query(value= "SELECT * from post p join portal_upload pu\r\n" + 
			"ON p.post_media = pu.pupload_id\r\n" + 
			"where p.post_parent_id = :postParentId \r\n" + 
			"AND p.is_active = :isActive AND pu.is_active = :isActive", nativeQuery =  true)
	List<Post> getListPostByPostParent(BigInteger postParentId, int isActive);
	
	
	@Query(value= "SELECT p.* from post p \r\n" + 
			"JOIN team_file t ON p.post_media = t.file_id\r\n" + 
			"LEFT JOIN post_include_user pi ON p.post_id = pi.post_id\r\n" + 
			"WHERE p.team_id = :teamId AND t.file_content_type LIKE :type\r\n" + 
			"AND case when :userId is null then p.privacy_id IN :listPrivacy\r\n" + 
			"else ((pi.user_id = :userId and pi.is_active = :isActive) or p.privacy_id IN :listPrivacy or p.created_by = :userId AND pi.is_active = :isActive) end \r\n" + 
			"AND p.is_active = :isActive\r\n" + 
			"AND t.is_active = :isActive\r\n" + 
			"ORDER BY p.created_date desc", nativeQuery =  true)
	List<Post> getPostsInTeamId(BigInteger teamId,String type,Collection<BigInteger> listPrivacy, BigInteger userId, int isActive);
	
	@Query(value= "SELECT * from post p join team_file t\r\n" + 
			"ON p.post_media = t.file_id\r\n" + 
			"where p.post_parent_id = :postParentId \r\n" + 
			"AND p.is_active = :isActive AND t.is_active = :isActive", nativeQuery =  true)
	List<Post> getPostsByPostParent(BigInteger postParentId, int isActive);

	@Query(value= "SELECT count(pupload_id) from portal_upload pu join post p\r\n" + 
			"ON p.post_media = pu.pupload_id\r\n" + 
			"where p.post_parent_id = :postParentId AND pupload_contenttype LIKE 'video/%'\r\n" + 
			"AND p.is_active = :isActive AND pu.is_active = :isActive", nativeQuery =  true)
	Integer countVideoInAlbum(BigInteger postParentId,int isActive);
	
	@Query(value= "SELECT count(pupload_id) from portal_upload pu join post p\r\n" + 
			"ON p.post_media = pu.pupload_id\r\n" + 
			"where p.post_parent_id = :postParentId AND pupload_contenttype LIKE 'image/%'\r\n" + 
			"AND p.is_active = :isActive AND pu.is_active = :isActive", nativeQuery =  true)
	Integer countPhotoInAlbum(BigInteger postParentId,int isActive);
	
	
	@Query(value= "SELECT count(post_id) from post\r\n" + 
			"	where post_parent_id = :postParentId AND is_active = :isActive", nativeQuery =  true)
	Integer countPostLeftInAlbum(BigInteger postParentId,int isActive);
	
	
}