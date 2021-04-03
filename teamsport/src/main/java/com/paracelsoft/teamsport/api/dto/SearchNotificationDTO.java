package com.paracelsoft.teamsport.api.dto;

import java.util.List;

public class SearchNotificationDTO {
	
	private List<NotificationDTO> notifications;
	
	private long totalRecord;

	/**
	 * @return the notifications
	 */
	public List<NotificationDTO> getNotifications() {
		return notifications;
	}

	/**
	 * @param notifications the notifications to set
	 */
	public void setNotifications(List<NotificationDTO> notifications) {
		this.notifications = notifications;
	}

	/**
	 * @return the totalRecord
	 */
	public long getTotalRecord() {
		return totalRecord;
	}

	/**
	 * @param totalRecord the totalRecord to set
	 */
	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
	}
	
}
