package com.paracelsoft.teamsport.service;

import java.io.File;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.paracelsoft.teamsport.api.dto.TeamFoldersDTO;
import com.paracelsoft.teamsport.entity.Team;
import com.paracelsoft.teamsport.entity.TeamFile;
import com.paracelsoft.teamsport.entity.User;
import com.paracelsoft.teamsport.repository.TeamFileRepository;
import com.paracelsoft.teamsport.repository.TeamFolderRepository;
import com.paracelsoft.teamsport.repository.TeamRepository;
import com.paracelsoft.teamsport.repository.UserRepository;
import com.paracelsoft.teamsport.util.ConstantUtils;
import com.paracelsoft.teamsport.util.DateUtil;

@Component
public class CronJob {

	@Autowired
	TeamFileRepository teamFileRepository;

	@Autowired
	TeamFolderRepository teamFolderRepository;
	
	@Autowired
	FolderService folderService;
	
	@Autowired
	TeamRepository teamRepository;
	
	@Autowired
	PaymentHistoryService paymentHistoryService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	NotificationService notificationService;
	
	@Scheduled(cron = "00 00 * * * *")
	public void deleteByDeleteDate() throws Exception {
		String dateDel = DateUtil.getFormatDate(new Date(), DateUtil.PT_YYYY_MM_DD_HH);
		System.out.println("DeleteFile - " + dateDel);
		List<TeamFile> listFile = teamFileRepository.getDeleteFileByDate(DateUtil.getFormatDate(dateDel, DateUtil.PT_YYYY_MM_DD_HH), 1);
		if (listFile != null && !listFile.isEmpty()) {
			for (TeamFile file : listFile) {
				String fullPath = file.getFilePathAtServer() + file.getFileNameAtServer();
				File fileDelete = new File(fullPath);
				if(fileDelete != null) {
					fileDelete.delete();
					teamFileRepository.delete(file);
				}		
			}
		}
		
		System.out.println("deleteFolderByDeleteDate - " + DateUtil.getFormatDate(new Date(), DateUtil.PT_YYYY_MM_DD_HH));
		List<BigInteger> listFolderId = teamFolderRepository.getFolderByDate(DateUtil.getFormatDate(dateDel, DateUtil.PT_YYYY_MM_DD_HH), 1);
		if (listFolderId != null) {
			for (BigInteger folderId : listFolderId) {
				if (folderId != null) {
					List<TeamFoldersDTO> listFolderChild = folderService.removeFolderChild(folderId);
				}
			}
		}
	}
	
	@Scheduled(cron = "0 0 6 * * *") // run 6 o'clock of every day.
	public void informServiceExpire() throws Exception {
		this.paymentHistoryService.handleExpiredNotification();
	}
}