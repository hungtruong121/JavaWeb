package com.paracelsoft.teamsport.api.folder.controller;

import java.math.BigInteger;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paracelsoft.teamsport.api.dto.SearchDTO;
import com.paracelsoft.teamsport.api.dto.TeamFoldersDTO;
import com.paracelsoft.teamsport.api.user.controller.AbstractController;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.entity.User;
import com.paracelsoft.teamsport.service.FileService;
import com.paracelsoft.teamsport.service.FolderService;
import com.paracelsoft.teamsport.util.MessageUtils;
import com.paracelsoft.teamsport.util.ReponseCode;

@RestController
@RequestMapping("/api/v1/folder")
public class FolderController extends AbstractController {
	
	@Autowired
	FolderService folderService;
	
	@Autowired
	MessageUtils messageUtils;
	
	@Autowired
	FileService fileService;
	
	/**
	 * 
	 * @Des get list all folder and file
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getall")
	public ApiResponse getAllFolder(@RequestParam(value="folderId", required = false) BigInteger folderId,@RequestParam(value="teamId", required = false) BigInteger teamId) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if (teamId == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}
			
			User user = getCurrentUserLogin();
			if(user == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E006"));
				respon.setData(new ArrayList<>());
	            return respon;
			}
			
			respon = folderService.getAllFolderAndFile(teamId,folderId,user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des search folder by keyword
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/search")
	public ApiResponse getListFolder(@RequestBody SearchDTO search) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if (search == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}
			respon = folderService.searchFolderAndFile(search);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des Add New Folder
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/add")
	public ApiResponse addFolder(@RequestBody TeamFoldersDTO folderDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if(folderDTO == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}
			
			User user = getCurrentUserLogin();
			if(user == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E006"));
				respon.setData(new ArrayList<>());
	            return respon;
			}
			
			respon = folderService.addFolder(folderDTO,user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des Rename Folder
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/rename")
	public ApiResponse renameFolder(@RequestBody TeamFoldersDTO folderDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if(folderDTO.getFolderId() == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E001", "folder"));
				return respon;
			}
			
			if(StringUtils.isEmpty(folderDTO.getFolderName())) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}
			User user = getCurrentUserLogin();
			if(user == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E006"));
				respon.setData(new ArrayList<>());
	            return respon;
			}
			respon = folderService.renameFolder(folderDTO.getFolderId(),folderDTO.getFolderName(),user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des Remove Folder
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/remove")
	public ApiResponse removeFolder(@RequestParam(value="folderId", required = false) BigInteger folderId) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if(folderId == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E001", "folderId"));
				return respon;
			}
			User user = getCurrentUserLogin();
			if(user == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E006"));
	            return respon;
			}
									
			respon = folderService.removeFolder(folderId,user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des download File
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/download")
	public ApiResponse downloadFolder(HttpServletResponse resonse,@RequestParam(value="folderId", required = false) BigInteger folderId,@RequestParam(value="teamId", required = false) BigInteger teamId) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if(folderId == null) {
				respon.setSuccess(false);
				respon.setMessage(messageUtils.getMessage("E001", "folder"));
				return respon;
			}
			respon = folderService.downloadFolder(resonse,folderId,teamId);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des set time to delete File
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/timedelete")
	public ApiResponse setTimeDelete(@RequestParam(value="folderId", required = false) BigInteger folderId,@RequestParam(value="date", 
	required = false) String date,@RequestParam(value="timeZone", required = false) String timeZone) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if(folderId == null) {
				respon.setSuccess(false);
				respon.setMessage(messageUtils.getMessage("E001", "folder"));
				return respon;
			}
			respon = folderService.setTimeDelete(folderId,date,timeZone);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
}
