package com.paracelsoft.teamsport.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.paracelsoft.teamsport.entity.TodoListInclude;
import com.paracelsoft.teamsport.entity.TodoListIncludePK;

public interface TodoListIncludeRepository extends JpaRepository<TodoListInclude, TodoListIncludePK>,
		JpaSpecificationExecutor<TodoListInclude>, QuerydslPredicateExecutor<TodoListInclude> {
	
	TodoListInclude findById_TodoListIdAndId_UserIdAndIsActive(BigInteger todoListId, BigInteger userId, int isActive);

	List<TodoListInclude> findById_TodoListIdAndIsActive(BigInteger todoListId, int isActive);
	
	List<TodoListInclude> findById_TodoListIdAndStatusId(BigInteger todoListId, BigInteger status); // To delete all include by todolistId and status doing
	
	List<TodoListInclude> findById_TodoListId(BigInteger todoListId); // To delete all include by todolistId
}
