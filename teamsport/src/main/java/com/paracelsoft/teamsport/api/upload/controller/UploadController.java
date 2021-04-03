package com.paracelsoft.teamsport.api.upload.controller;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.paracelsoft.teamsport.api.dto.UploadDTO;
import com.paracelsoft.teamsport.api.user.controller.AbstractController;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.entity.Upload;
import com.paracelsoft.teamsport.entity.User;
import com.paracelsoft.teamsport.service.UploadService;
import com.paracelsoft.teamsport.util.MessageUtils;
import com.paracelsoft.teamsport.util.ReponseCode;

@RequestMapping(value = { "/api/v1/file"})
@RestController
public class UploadController extends AbstractController {
    
    @Autowired
    Environment evn;
    
    @Autowired
    UploadService uploadService;
    
    @Autowired
	MessageUtils messageUtils;
  
    /**
     * 
     * @param files
     * @param uploadId
     * @return
     */
	@RequestMapping(value = "/uploads", method = RequestMethod.POST)
    public @ResponseBody ApiResponse reaFiles(@RequestParam(value ="files", required = false) List<MultipartFile> files){
		ApiResponse respon = new ApiResponse();
		List<UploadDTO> uploadDTOs = new ArrayList<>();
		try {
			
			if(files == null || files.isEmpty()) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E003"));
				respon.setData(uploadDTOs);
	            return respon;
			}
			BigInteger userId = null;
			try {
				User user = getCurrentUserLogin();
				if(user != null) {
					userId = user.getUserId();
				}
			} catch (Exception e) {
			}
			uploadDTOs = uploadService.uploadFiles(files, evn.getProperty("source.image.path").toString(), userId); 
	        respon.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(e.getMessage());
		}
		respon.setData(uploadDTOs);
        return respon;
    }
	
	/**
	 * 
	 * @param uploadID
	 * @param seq
	 * @return
	 */
	@RequestMapping(value = "/reads", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<byte[]> fileDecoder(
    		@RequestParam(value = "uploadId", required = false) BigInteger uploadID, boolean isException) {
  
        UploadDTO uploadDTO = null;
        if(uploadID != null){
        	uploadDTO = uploadService.findById(uploadID);
        }else{
        	//get default picture
        	uploadDTO = uploadService.findById(BigInteger.ONE);
        }
        Path path = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(uploadDTO.getContentType()));
        headers.add("content-disposition", "inline;filename=" + uploadDTO.getFileName());
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        path = Paths.get(uploadDTO.getFilePath()+"/" + uploadDTO.getFileNameAtServer());
        ResponseEntity<byte[]> response = null;

        byte[] content = "No file found".getBytes();
        try {
            content = Files.readAllBytes(path);
        } catch (Exception e) {
        	e.printStackTrace();
        	if(!isException) {
        		return this.fileDecoder(BigInteger.ONE, true);
        	}
        }
    	response = new ResponseEntity<byte[]>(content, headers, HttpStatus.OK);
        return response;
    }
    
    /**
     * 
     * @param uploadId
     * @param seq
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delete/{uploadId}", method = RequestMethod.GET)
    public ApiResponse deleteByUploadId(@PathVariable("uploadId")BigInteger uploadId) throws Exception {
    	ApiResponse response = new ApiResponse();
    	Upload upload = uploadService.deleteByUploadId(uploadId);
    	response.setData(upload);
    	response.setMessage("");
    	return response;
    }
}