package com.paracelsoft.teamsport.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.paracelsoft.teamsport.entity.TodoList;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, BigInteger>, JpaSpecificationExecutor<TodoList>, QuerydslPredicateExecutor<TodoList> {
	TodoList findByTodoListIdAndIsActive(BigInteger todoListId, int isActive);

	TodoList findByTodoListId(BigInteger todoListId); // Get to delete old todoList
	
	List<TodoList> findByTeamIdAndTodoListParentIdAndIsActive(BigInteger teamId, BigInteger todoListParentId, int isActive);
	
	@Query(value="select (select count(*) from todo_list_include where todo_list_id = t.todo_list_id and is_active = 1) as members_assign,\r\n" + 
			"(select count(*) from todo_list_include where todo_list_id = t.todo_list_id and status_id = :statusDone and is_active = 1) as members_done,\r\n" + 
			"t.todo_list_id as todo_list_id, ifnull(t.todo_list_parent_id,'') as todo_list_parent_id, t.team_id as team_id, ti.user_id as user_id,\r\n" + 
			"ifnull(t.todo_list_deadline,'') as todo_list_deadline, ifnull(t.todo_list_title,'') as todo_list_title,\r\n" + 
			"ifnull(t.todo_list_type,'') as todo_list_type, ifnull(t.todo_list_notice,'') as todo_list_notice,\r\n" + 
			"ifnull(ti.status_id,'') as status_id FROM todo_list_include ti JOIN todo_list t ON ti.todo_list_id = t.todo_list_id\r\n" + 
			"WHERE ti.user_id = :userId  AND t.team_id = :teamId AND ti.is_active = 1 AND t.is_active = 1\r\n" + 
			"AND t.todo_list_title like :keyword ORDER BY t.created_date DESC limit :firstResult, :maxResult", nativeQuery = true)
	List<Map<String, Object>> searchYourTodoList(BigInteger userId, BigInteger teamId, BigInteger statusDone, String keyword, int firstResult, int maxResult);

	@Query(value="select count(t.todo_list_id) FROM todo_list_include ti JOIN todo_list t ON ti.todo_list_id = t.todo_list_id\r\n" + 
			"WHERE ti.user_id = :userId AND t.team_id = :teamId AND ti.is_active = 1 AND t.is_active = 1\r\n" + 
			"AND t.todo_list_title like :keyword", nativeQuery = true)
	Integer searchCountYourTodoList(BigInteger userId, BigInteger teamId, String keyword);

	@Query(value="select (select count(*) from todo_list_include where todo_list_id = t.todo_list_id and is_active = 1) as members_assign,\r\n" + 
			"(select count(*) from todo_list_include where todo_list_id = t.todo_list_id and status_id = :statusDone and is_active = 1) as members_done,\r\n" + 
			"t.todo_list_id as todo_list_id, ifnull(t.todo_list_parent_id,'') as todo_list_parent_id, t.team_id as team_id,\r\n" + 
			"ifnull(t.todo_list_deadline,'') as todo_list_deadline, ifnull(t.todo_list_title,'') as todo_list_title,\r\n" + 
			"ifnull(t.todo_list_type,'') as todo_list_type, ifnull(t.todo_list_notice,'') as todo_list_notice,\r\n" + 
			"ifnull(t.privacy_id,'') as privacy_id FROM todo_list t\r\n" + 
			"WHERE t.team_id = :teamId AND t.privacy_id = :privacyId AND t.is_active = 1\r\n" + 
			"AND t.todo_list_title like :keyword ORDER BY t.created_date DESC limit :firstResult, :maxResult", nativeQuery = true)
	List<Map<String, Object>> searchTeamTodoList(BigInteger teamId, BigInteger privacyId, BigInteger statusDone, String keyword, int firstResult, int maxResult);
	
	@Query(value="select count(t.todo_list_id) FROM todo_list t WHERE t.team_id = :teamId AND t.privacy_id = :privacyId \r\n" + 
			"AND t.is_active = 1 AND t.todo_list_title like :keyword", nativeQuery = true)
	Integer searchCountTeamTodoList(BigInteger teamId, BigInteger privacyId, String keyword);
	
	@Query(value="select count(*) from todo_list_include where todo_list_id = :todoListId and is_active = 1", nativeQuery = true)
	Integer countTotalMemberAssigned(BigInteger todoListId); // Count total userAssigned in todoList
	
	@Query(value="select count(*) from todo_list_include where todo_list_id = :todoListId and status_id = :statusDone and is_active = 1", nativeQuery = true)
	Integer countTotalMemberDone(BigInteger todoListId, BigInteger statusDone); // Count total userAssigned is Done task
}
