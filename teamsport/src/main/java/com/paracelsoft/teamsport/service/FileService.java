package com.paracelsoft.teamsport.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.paracelsoft.teamsport.api.dto.TeamFilesDTO;
import com.paracelsoft.teamsport.api.dto.TeamFoldersDTO;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.entity.TeamFile;
import com.paracelsoft.teamsport.entity.TeamMember;
import com.paracelsoft.teamsport.repository.TeamFileRepository;
import com.paracelsoft.teamsport.repository.TeamMemberRepository;
import com.paracelsoft.teamsport.util.DateUtil;
import com.paracelsoft.teamsport.util.MessageUtils;
import com.paracelsoft.teamsport.util.ReponseCode;

@Transactional(rollbackFor = { Exception.class, ParseException.class })
@Service("fileService")
public class FileService {
	
	public final String MIME_IMAGE_JPEG = "image/jpeg";
	public final String MIME_IMAGE_PNG = "image/png";
	public final String MIME_APPLICATION_MSWORD = "application/msword";
	public final String MIME_APPLICATION_MSWORD1 = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
	public final String MIME_VIDEO_MP4 = "video/mp4";
	public final String MIME_AUDIO_MPEG = "audio/mpeg"; // mp3
	public final String MIME_IMAGE_GIF = "image/gif";
	public final String MIME_IMAGE_TIFF = "image/tiff";
	public final String MIME_APPLICATION_PDF = "application/pdf";
	public final String MIME_TEXT_PLAIN = "text/plain";
	public final String MIME_APPLICATION_ZIP = "application/zip";
	public final String MIME_APPLICATION_X_RAR_COMPRESSED = "application/x-rar-compressed";
	public final String MIME_APPLICATION_VND_MSEXCEL = "application/vnd.ms-excel";
	public final String MIME_APPLICATION_VND_MSEXCEL1 = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	
	@Autowired
	TeamFileRepository teamFileRepository;
	
	@Autowired
	FolderService folderService;

	@Autowired
	MessageUtils messageUtils;
	
	@Autowired
	Environment evn;
	
	@Autowired
	TeamMemberRepository teamMemberRepository;

	public ApiResponse addFiles(List<MultipartFile> files,BigInteger folderId,BigInteger teamId, String fileStoragePath, BigInteger createBy)
			throws Exception {
		ApiResponse respon = new ApiResponse();

		List<TeamFilesDTO> listTeamFileDTO = new ArrayList<TeamFilesDTO>();

		// Check creating is member in team
		TeamMember teamMember = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(teamId,
				createBy, 1);
		if (teamMember == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E013"));
			return respon;
		}
		
		for (int i = 0; i < files.size(); i++) {
			MultipartFile file = files.get(i);
			String fileType = FilenameUtils.getExtension(file.getOriginalFilename());
			if (lookupMimeType(fileType) != null) {
				String fileNameAtServer = createFileNameAtServer(file.getOriginalFilename());
				List<TeamFoldersDTO> getFolderParent = folderService.getFolderParent(folderId);
				String path = "";
				if (getFolderParent == null || getFolderParent.isEmpty()) {
					path = fileStoragePath + "/" + teamId + "/";
				}else {
					for (int j = 0 ; j < getFolderParent.size(); j++) {
						if (j == 0) {
							path = getFolderParent.get(j).getFolderName();
						}else {
							path = getFolderParent.get(j).getFolderName() + "/" + path;
						}
					}
					path = fileStoragePath + "/" + teamId + "/" + path + "/";
				}
				
				//copy file to server
				saveFileToServer(file, path, fileNameAtServer);
				
				TeamFile teamFile = saveFileInDB(file, folderId, teamId, path, fileType, fileNameAtServer, createBy);
				TeamFilesDTO teamFileDTO = convertToDTO(teamFile);
				listTeamFileDTO.add(teamFileDTO);
			}else {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E023"));
				return respon;
			}
			
		}
		respon.setData(listTeamFileDTO);

		return respon;
	}

	public ApiResponse getAllFile(BigInteger teamId,BigInteger folderId) throws Exception {
		ApiResponse respon = new ApiResponse();
		
		List<TeamFile> listTeamFile = new ArrayList<TeamFile>();
		if (folderId == null) {
			listTeamFile = teamFileRepository.getAllFile(teamId,1);
		}else {
			listTeamFile = teamFileRepository.getAllFileInFolder(teamId,folderId,1);
		}
		
		List<TeamFilesDTO> listTeamFileDTO = new ArrayList<TeamFilesDTO>();
		if (listTeamFile != null) {
			for (TeamFile teamFile : listTeamFile) {
				TeamFilesDTO teamFileDTO = convertToDTO(teamFile);
				listTeamFileDTO.add(teamFileDTO);
			}
		}
		
		respon.setData(listTeamFileDTO);

		return respon;
	}

	public ApiResponse renameFile(BigInteger fileId, String fileName,BigInteger userId) throws Exception {
		ApiResponse respon = new ApiResponse();
				
		TeamFile teamFile = teamFileRepository.findByFileIdAndIsActive(fileId, 1);
		if (teamFile == null) {
			respon.setSuccess(false);
			respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
			respon.setMessage(messageUtils.getMessage("E001", "teamFile"));
			return respon;
		}
		
		TeamMember teamMember = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(teamFile.getTeamId(),
				userId, 1);
		if (teamMember == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E013"));
			return respon;
		}
		
		teamFile.setFileName(fileName);
		teamFile.setUpdatedDate(new Date());
		teamFile = teamFileRepository.save(teamFile);
		respon.setMessage(messageUtils.getMessage("I003", "file"));
		return respon;
	}
	
	public ApiResponse downloadFile(HttpServletResponse resonse,BigInteger fileId,BigInteger userId) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			TeamFile teamFile = teamFileRepository.findByFileIdAndIsActive(fileId, 1);
			if(teamFile == null) {
				respon.setSuccess(false);
				respon.setMessage(messageUtils.getMessage("E001","file"));
				return respon;
			}
			TeamMember teamMember = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(teamFile.getTeamId(),
					userId, 1);
			if (teamMember == null) {
				respon.setSuccess(false);
				respon.setMessage(messageUtils.getMessage("E013"));
				return respon;
			}
			
			File file = new File(teamFile.getFilePathAtServer() + teamFile.getFileNameAtServer());
			 
	        // Content-Type
	        resonse.setContentType(teamFile.getFileContentType());
	 
	        // Content-Disposition
	        resonse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + teamFile.getFileName());

	        // Content-Length
	        resonse.setContentLength((int) file.length());
	 
	        BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(file));
	        BufferedOutputStream outStream = new BufferedOutputStream(resonse.getOutputStream());
	 
	        byte[] buffer = new byte[1024];
	        int bytesRead = 0;
	        while ((bytesRead = inStream.read(buffer)) != -1) {
	            outStream.write(buffer, 0, bytesRead);
	        }
	        outStream.flush();
	        inStream.close();
	        respon.setMessage(messageUtils.getMessage("I016","file"));
		}catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		
		return respon;
	}
	
	public ApiResponse downloadFiles(HttpServletResponse resonse,BigInteger teamId,List<BigInteger> fileIds) throws Exception {
		ApiResponse respon = new ApiResponse();
		fileIds = new ArrayList<BigInteger>();
		fileIds.add(new BigInteger("9"));
		fileIds.add(new BigInteger("10"));
		fileIds.add(new BigInteger("11"));
		List<TeamFile> listTeamFile = new ArrayList<TeamFile>();
		for (BigInteger fileId : fileIds) {
			TeamFile teamFile = teamFileRepository.findByFileIdAndIsActive(fileId, 1);
			listTeamFile.add(teamFile);
		}
		
	    String downloadFilename = new SimpleDateFormat(DateUtil.PT_YYYY_MM_DD).format(new Date()) + ".zip";
	    resonse.reset();
	    //Set the format
	    resonse.setContentType("application/x-msdownload");
	    resonse.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(downloadFilename, "UTF-8"));
		OutputStream fos = resonse.getOutputStream();
        ZipOutputStream zipOut = new ZipOutputStream(fos);
		for (int i = 0 ; i < listTeamFile.size(); i++) {
			File fileToZip = new File(listTeamFile.get(i).getFilePathAtServer() + "/" + listTeamFile.get(i).getFileNameAtServer());
	        FileInputStream fis = new FileInputStream(fileToZip);
	        ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
	        zipOut.putNextEntry(zipEntry);
	        byte[] bytes = new byte[1024];
	        int length;
	        while((length = fis.read(bytes)) >= 0) {
	            zipOut.write(bytes, 0, length);
	        }
	        fis.close();
		}
        zipOut.close();
        fos.close();
		
        respon.setMessage(messageUtils.getMessage("I019","file"));
		return respon;
	}

	public ApiResponse removeFile(List<BigInteger> fileIds,BigInteger userId) throws Exception {
		ApiResponse respon = new ApiResponse();
		for(BigInteger fileId : fileIds) {
			TeamFile teamFile = teamFileRepository.findByFileIdAndIsActive(fileId, 1);
			if (teamFile == null) {
				respon.setSuccess(false);
				respon.setMessage(messageUtils.getMessage("E001", "file"));
				return respon;
			}
			String fullPath = teamFile.getFilePathAtServer() + teamFile.getFileNameAtServer();
			File file = new File(fullPath);
			file.delete();
			teamFileRepository.delete(teamFile);
		}
		
		respon.setMessage(messageUtils.getMessage("I002", "file"));
		return respon;
	}
	
	public ApiResponse setTimeDelete(BigInteger fileId,String date,String timeZone) throws Exception {
		ApiResponse respon = new ApiResponse();
		TeamFile teamFile = teamFileRepository.findByFileIdAndIsActive(fileId, 1);
		if(teamFile != null) {
			teamFile.setFileDeleteDate(DateUtil.fomatTimeZoneToUTC(date, DateUtil.PT_DD_MM_YYYY_HH, DateUtil.PT_YYYY_MM_DD_HH_MM_SS,timeZone));
			teamFile.setUpdatedDate(new Date());
			teamFileRepository.save(teamFile);
			respon.setMessage(messageUtils.getMessage("I003", "file"));
		}
		return respon;
	}

	public TeamFilesDTO convertToDTO(TeamFile teamFile) {
		TeamFilesDTO teamFileDTO = new TeamFilesDTO();
		if (teamFile.getFileId() != null) {
			teamFileDTO.setFileId(teamFile.getFileId());
		} else {
			teamFileDTO.setFileId(null);
		}
		teamFileDTO.setTeamId(teamFile.getTeamId());
		teamFileDTO.setFolderId(teamFile.getFolderId());
		teamFileDTO.setFileType(teamFile.getFileType());
		teamFileDTO.setFileName(teamFile.getFileName());
		teamFileDTO.setContentType(teamFile.getFileContentType());
		teamFileDTO.setFileNameAtServer(teamFile.getFileNameAtServer());
		teamFileDTO.setFilePathAtServer(teamFile.getFilePathAtServer());
		teamFileDTO.setFileSize(teamFile.getFileSize());
		teamFileDTO.setFileDeleteDate(teamFile.getFileDeleteDate());
		teamFileDTO.setCreatedDate(teamFile.getCreatedDate());
		teamFileDTO.setUpdatedDate(teamFile.getUpdatedDate());
		teamFileDTO.setCreatedby(teamFile.getCreatedBy());
		teamFileDTO.setIsActive(teamFile.getIsActive());

		return teamFileDTO;
	}

	private String createFileNameAtServer(String originalName) throws Exception {
		return String.format("%s_%s", new Date().getTime(), originalName);
	}

	private TeamFile saveFileInDB(MultipartFile file, BigInteger folderId,BigInteger teamId, String filePath, String fileType, String fileNameAtServer,
			BigInteger createBy) throws Exception {
		TeamFile teamFile = new TeamFile();
		teamFile.setFileId(null);
		teamFile.setTeamId(teamId);
		teamFile.setFolderId(folderId);
		teamFile.setFileType(fileType);
		teamFile.setFileName(file.getOriginalFilename());
		teamFile.setFileContentType(file.getContentType());
		teamFile.setFilePathAtServer(filePath);
		teamFile.setFileNameAtServer(fileNameAtServer);
		teamFile.setFileSize(BigInteger.valueOf(file.getSize()));
		teamFile.setCreatedBy(createBy);
		teamFile.setCreatedDate(new Date());

		teamFile = teamFileRepository.save(teamFile);
		return teamFile;
	}

	private HashMap<String, String> mimeTypeMapping;

	{
		mimeTypeMapping = new HashMap<String, String>(20) {
			private void put1(String key, String value) {
				if (put(key, value) != null) {
					throw new IllegalArgumentException("Duplicated extension: " + key);
				}
			}

			{
				put1("mp4", MIME_VIDEO_MP4);
				put1("mp3", MIME_AUDIO_MPEG);
				put1("jpeg", MIME_IMAGE_JPEG);
				put1("jpg", MIME_IMAGE_JPEG);
				put1("jpe", MIME_IMAGE_JPEG);
				put1("tiff", MIME_IMAGE_TIFF);
				put1("tif", MIME_IMAGE_TIFF);
				put1("png", MIME_IMAGE_PNG);
				put1("gif", MIME_IMAGE_GIF);
				put1("doc", MIME_APPLICATION_MSWORD);
				put1("docx", MIME_APPLICATION_MSWORD1);
				put1("xls", MIME_APPLICATION_VND_MSEXCEL);
				put1("xlsx", MIME_APPLICATION_VND_MSEXCEL1);
				put1("pdf", MIME_APPLICATION_PDF);
				put1("zip", MIME_APPLICATION_ZIP);
				put1("rar", MIME_APPLICATION_X_RAR_COMPRESSED);
				put1("txt", MIME_TEXT_PLAIN);
			}
		};
	}

	private void saveFileToServer(MultipartFile file, String filePath, String fileNameAtServer) throws Exception {
		try {
			final File directories = new File(filePath);

			if (!directories.exists()) {
				if (directories.mkdirs()) {
					//logger.info("Multiple directories are created!");
				} else {
					//logger.info("Failed to create multiple directories!");
				}
			}

			final Path path = Paths.get(filePath +  "/" + fileNameAtServer);
			OutputStream out = null;
			try {
				out = new BufferedOutputStream(new FileOutputStream(path.toString()));
				out.write(file.getBytes());
			} finally {
				if (out != null)
					out.close();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Simply returns MIME type or <code>null</code> if no type is found.
	 */
	public String lookupMimeType(String ext) {
		return mimeTypeMapping.get(ext.toLowerCase());
	}

	public TeamFilesDTO findById(BigInteger bigInteger) {
		TeamFile file = teamFileRepository.findByFileIdAndIsActive(bigInteger, 1);
		return file != null?convertToDTO(file):new TeamFilesDTO();
	}
}
