package com.paracelsoft.teamsport.repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.paracelsoft.teamsport.entity.TeamFolder;

@Repository
public interface TeamFolderRepository extends JpaRepository<TeamFolder, BigInteger>,
		JpaSpecificationExecutor<TeamFolder>, QuerydslPredicateExecutor<TeamFolder> {

	@Query(value = "SELECT * FROM team_folder \r\n" + "where team_id = :teamId and parent_folder_id is null and is_active = :isActive", nativeQuery = true)
	List<TeamFolder> getAllFolder(BigInteger teamId,int isActive);
	
	@Query(value = "SELECT * FROM team_folder \r\n" + "where team_id = :teamId and parent_folder_id = :parentFolderId and is_active = :isActive", nativeQuery = true)
	List<TeamFolder> getAllFolderByParent(BigInteger teamId,BigInteger parentFolderId,int isActive);

	@Query(value = "SELECT * FROM team_folder \r\n"
			+ "where team_id = :teamId and folder_name LIKE :keyword AND is_active = :isActive limit :firstResult, :maxResult", nativeQuery = true)
	List<TeamFolder> findByFolderNameAndIsActive(BigInteger teamId, String keyword, int firstResult, int maxResult, int isActive);

	TeamFolder findByFolderIdAndIsActive(BigInteger folderId, int isActive);

	@Query(value = "SELECT * FROM team_folder \r\n"
			+ "where parent_folder_id = :folderId AND is_active = :isActive", nativeQuery = true)
	List<TeamFolder> findByParentFolderIdAndIsActive(BigInteger folderId, int isActive);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM team_folder \r\n"
			+ "where folder_id = :folderId AND is_active = :isActive", nativeQuery = true)
	void deleteParentFolderIdAndIsActive(BigInteger folderId, int isActive);
	
	@Query(value = "SELECT ifnull(SUM(file_size),0) as fileSize FROM team_file\r\n" + 
			"WHERE folder_id = :folderId", nativeQuery = true)
	BigInteger folderSize(BigInteger folderId);
	
	List<TeamFolder> findAllByParentFolderId(BigInteger parentFolderId);

	TeamFolder findByFolderId(BigInteger childFolderId);

	@Query(value = "SELECT folder_id FROM team_folder \r\n"
			+ "where folder_delete_date <= :date AND is_active = :isActive", nativeQuery = true)
	List<BigInteger> getFolderByDate(Date date,int isActive);
	
	@Query(value = "SELECT folder_name FROM team_folder \r\n" + 
			"where team_id = :teamId and parent_folder_id = :parentFolderId AND folder_name = :folderName AND is_active = :isActive", nativeQuery = true)
	String findByTeamIdAndParentFolderIdAndFolderName(BigInteger teamId,BigInteger parentFolderId,String folderName,int isActive);
	
	@Query(value = "SELECT folder_name FROM team_folder \r\n" + 
			"where team_id = :teamId and parent_folder_id is null AND folder_name = :folderName AND is_active = :isActive", nativeQuery = true)
	String findByTeamIdAndFolderName(BigInteger teamId,String folderName,int isActive);

	@Query(value = "SELECT * FROM team_folder \r\n" + 
			"where folder_path_at_server LIKE :path%", nativeQuery = true)
	List<TeamFolder> findByFolderPathAtServer(String path);
	
	@Query(value = "SELECT * FROM team_folder \r\n" + 
			"where folder_name LIKE :folderName", nativeQuery = true)
	TeamFolder findByFolderName(String folderName);
}