package com.paracelsoft.teamsport.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.paracelsoft.teamsport.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, BigInteger>, JpaSpecificationExecutor<Notification>, QuerydslPredicateExecutor<Notification> {

	Notification findByNotificationIdAndIsActive(BigInteger notificationFromId, int isActive);

	@Modifying
	@Query(value = "update notification set is_read = 1 where notification_to_id = :userId and team_id = :teamId", nativeQuery = true)
	void readAllNotifi(BigInteger teamId,BigInteger userId);

	@Query(value="SELECT n.* FROM notification n where n.notification_to_id = :userId and n.notification_type = :notificationType\r\n" + 
			"and n.is_active = :isActive order by notification_type*1 limit :firstresult, :maxResult", nativeQuery = true)
	List<Notification> findAllByNotificationToIdAndNotificationTypeAndIsActive(BigInteger userId, String notificationType, int isActive,
			int firstresult, int maxResult);
	
	@Query(value="SELECT n.* FROM notification n where n.team_id = :teamId and n.notification_to_id = :userId \r\n" + 
			"and n.is_active = :isActive order by notification_type*1 limit :firstresult, :maxResult", nativeQuery = true)
	List<Notification> findAllByTeamIdAndNotificationToIdAndIsActiveOrderByNotificationTypeAsc(BigInteger teamId,
			BigInteger userId, int isActive, int firstresult, int maxResult);

	long countByTeamIdAndNotificationToIdAndIsReadAndIsActive(BigInteger teamId, BigInteger userId, int isRead, int isActive);

	long countByTeamIdAndNotificationToIdAndIsActive(BigInteger teamId, BigInteger userId, int j);
	
}