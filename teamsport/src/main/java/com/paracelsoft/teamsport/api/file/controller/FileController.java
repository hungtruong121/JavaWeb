package com.paracelsoft.teamsport.api.file.controller;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.paracelsoft.teamsport.api.dto.TeamFilesDTO;
import com.paracelsoft.teamsport.api.user.controller.AbstractController;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.entity.User;
import com.paracelsoft.teamsport.service.FileService;
import com.paracelsoft.teamsport.util.MessageUtils;
import com.paracelsoft.teamsport.util.ReponseCode;

@RestController
@RequestMapping("/api/v1/file")
public class FileController extends AbstractController {

	@Autowired
    Environment evn;
	
	@Autowired
	FileService fileService;
	
	@Autowired
	MessageUtils messageUtils;
	
	/**
	 * 
	 * @Des add files
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/add")
	public ApiResponse addFiles(@RequestParam(value ="files", required = false) List<MultipartFile> files,@RequestParam(value ="folderId", required = false) BigInteger folderId,@RequestParam(value ="teamId", required = false) BigInteger teamId) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if(files == null || files.isEmpty()) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
	            return respon;
			}
			if(teamId == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
	            return respon;
			}
			
			User user = getCurrentUserLogin();
			if(user == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E006"));
	            return respon;
			}
			respon = fileService.addFiles(files, folderId, teamId, evn.getProperty("source.image.path").toString(),user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
		
	/**
	 * 
	 * @Des rename File
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/rename")
	public ApiResponse renameFile(@RequestBody TeamFilesDTO fileDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if(fileDTO.getFileId() == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E001", "fileId"));
				return respon;
			}
			if(StringUtils.isEmpty(fileDTO.getFileName())) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}
			User user = getCurrentUserLogin();
			if(user == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E006"));
	            return respon;
			}
			
			respon = fileService.renameFile(fileDTO.getFileId(),fileDTO.getFileName(),user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des remove File
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/remove")
	public ApiResponse removeFile(@RequestParam(value="fileIds", required = false) List<BigInteger> fileIds) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if(fileIds == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E001", "fileId"));
				return respon;
			}
			User user = getCurrentUserLogin();
			if(user == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E006"));
	            return respon;
			}
			respon = fileService.removeFile(fileIds,user.getUserId());
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
	public ApiResponse downloadFile(HttpServletResponse resonse,@RequestParam(value="fileId", required = false) BigInteger fileId) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if(fileId == null) {
				respon.setSuccess(false);
				respon.setMessage(messageUtils.getMessage("E001", "file"));
				return respon;
			}
			User user = getCurrentUserLogin();
			if(user == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E006"));
	            return respon;
			}
			respon = fileService.downloadFile(resonse,fileId,user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des download many File
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/downloads")
	public ApiResponse downloadFiles(HttpServletResponse resonse,@RequestParam(value="teamId", required = false) BigInteger teamId, @RequestParam List<BigInteger> fileIds) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if(fileIds == null || fileIds.isEmpty() ) {
				respon.setSuccess(false);
				respon.setMessage(messageUtils.getMessage("E001", "file"));
				return respon;
			}
			respon = fileService.downloadFiles(resonse,teamId,fileIds);
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
	public ApiResponse setTimeDelete(@RequestParam(value="fileId", required = false) BigInteger fileId, @RequestParam(value="date", required = false) String date,
			@RequestParam(value="timeZone", required = false) String timeZone) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if(fileId == null) {
				respon.setSuccess(false);
				respon.setMessage(messageUtils.getMessage("E001", "file"));
				return respon;
			}
			respon = fileService.setTimeDelete(fileId,date,timeZone);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @param fileId
	 * @param seq
	 * @return
	 */
	@RequestMapping(value = "/read", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<byte[]> fileDecoderPost(
    		@RequestParam(value = "fileId", required = false) BigInteger fileId, boolean isException) {
  
        TeamFilesDTO fileDTO = null;
        if(fileId != null){
        	fileDTO = fileService.findById(fileId);
        }else{
        	//get default picture
        	fileDTO = fileService.findById(BigInteger.ONE);
        }
        Path path = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(fileDTO.getContentType()));
        headers.add("content-disposition", "inline;filename=" + fileDTO.getFileName());
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        path = Paths.get(fileDTO.getFilePathAtServer() + fileDTO.getFileNameAtServer());
        ResponseEntity<byte[]> response = null;

        byte[] content = "No file found".getBytes();
        try {
            content = Files.readAllBytes(path);
        } catch (Exception e) {
        	e.printStackTrace();
        	if(!isException) {
        		return fileDecoderPost(BigInteger.ONE, true);
        	}
        }
    	response = new ResponseEntity<byte[]>(content, headers, HttpStatus.OK);
        return response;
    }
}
