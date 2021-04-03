package com.paracelsoft.teamsport.repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.paracelsoft.teamsport.entity.TeamFile;

@Repository
public interface TeamFileRepository extends JpaRepository<TeamFile, BigInteger>, JpaSpecificationExecutor<TeamFile>, QuerydslPredicateExecutor<TeamFile> {
	
	
	@Query(value="SELECT * FROM team_file \r\n" + 
			"where team_id = :teamId and folder_id is null and is_active = :isActive", nativeQuery = true)
	List<TeamFile> getAllFile(BigInteger teamId,int isActive);
	
	@Query(value="SELECT * FROM team_file \r\n" + 
			"where team_id = :teamId and folder_id = :folderId and is_active = :isActive", nativeQuery = true)
	List<TeamFile> getAllFileInFolder(BigInteger teamId,BigInteger folderId,int isActive);
	
	@Query(value="SELECT * FROM team_file \r\n" + 
			"			where team_id = :teamId and file_name LIKE :keyword and is_active = :isActive limit :firstResult, :maxResult", nativeQuery = true)
	List<TeamFile> findByFileNameAndIsActive(BigInteger teamId, String keyword, int firstResult, int maxResult,int isActive);
	
	@Query(value="SELECT * FROM team_file \r\n" + 
			"where file_id = :fileId  and is_active = :isActive", nativeQuery = true)
	TeamFile findByFileIdAndIsActive(BigInteger fileId, int isActive);

	@Modifying
    @Transactional
	@Query(value="DELETE FROM team_file \r\n" + 
			"where folder_id = :folderId AND is_active = :isActive", nativeQuery = true)
	void deleteAllFileInFolder(BigInteger folderId,int isActive);
	
	@Query(value="SELECT count(file_id) as totalItems,ifnull(SUM(file_size),0) as fileSize FROM team_file\r\n" + 
			"WHERE team_id = :teamId and file_type IN ('mp4','mp3')", nativeQuery = true)
	Map<String, Object> totalFileSzMedia(BigInteger teamId);
	
	@Query(value="SELECT count(file_id) as totalItems,ifnull(SUM(file_size),0) as fileSize FROM team_file\r\n" + 
			"WHERE team_id = :teamId and file_type IN ('jpeg','jpg','jpe','tiff','tif','png','gif')", nativeQuery = true)
	Map<String, Object> totalFileSzPhotos(BigInteger teamId);
	
	@Query(value="SELECT count(file_id) as totalItems,ifnull(SUM(file_size),0) as fileSize FROM team_file\r\n" + 
			"WHERE team_id = :teamId and file_type IN ('doc','xls','pdf','docx','xlsx')", nativeQuery = true)
	Map<String, Object> totalFileSzDoc(BigInteger teamId);
	
	@Query(value="SELECT count(file_id) as totalItems,ifnull(SUM(file_size),0) as fileSize FROM team_file\r\n" + 
			"WHERE team_id = :teamId and file_type IN ('zip','rar')", nativeQuery = true)
	Map<String, Object> totalFileSzOthers(BigInteger teamId);
	
	@Query(value = "SELECT ifnull(SUM(file_size),0) as fileSize FROM team_file\r\n" + 
			"WHERE team_id = :teamId", nativeQuery = true)
	BigInteger totalSizeTeam(BigInteger teamId);
	
	@Query(value = "SELECT ifnull(SUM(file_size),0) as fileSize FROM team_file\r\n" + 
			"WHERE folder_id = :folderId", nativeQuery = true)
	BigInteger totalSizeFolder(BigInteger folderId);
	
	@Query(value = "SELECT * FROM team_file \r\n"
			+ "where file_delete_date <= :date AND is_active = :isActive", nativeQuery = true)
	List<TeamFile> getDeleteFileByDate(Date date,int isActive);
	
	@Query(value = "SELECT * FROM team_file \r\n" + 
			"where file_path_at_server LIKE :path%", nativeQuery = true)
	List<TeamFile> findByFilePathAtServer(String path);
	
}