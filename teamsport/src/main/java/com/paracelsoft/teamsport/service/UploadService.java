package com.paracelsoft.teamsport.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.paracelsoft.teamsport.api.dto.UploadDTO;
import com.paracelsoft.teamsport.entity.Upload;
import com.paracelsoft.teamsport.repository.UploadRepository;
import com.paracelsoft.teamsport.util.GenerateIDUtils;
import com.paracelsoft.teamsport.util.MessageUtils;
import com.paracelsoft.teamsport.util.ReponseCode;

@Transactional(rollbackFor = { Exception.class, IOException.class })
@Service("UploadService")
public class UploadService {
	
	public final String MIME_VIDEO_MP4 = "video/mp4";
	public final String MIME_VIDEO_WMV = "video/x-ms-wmv";
	public final String MIME_VIDEO_AVI = "video/x-msvideo";
	public final String MIME_VIDEO_MKV = "video/x-matroska";
	public final String MIME_VIDEO_FLV = "video/x-flv";
	public final String MIME_VIDEO_QUICKTIME = "video/quicktime";
	public final String MIME_IMAGE_JPEG = "image/jpeg";
	public final String MIME_IMAGE_PNG = "image/png";
	public final String MIME_AUDIO_MPEG = "audio/mpeg"; // mp3
	public final String MIME_IMAGE_GIF = "image/gif";
	public final String MIME_IMAGE_TIFF = "image/tiff";
	
	@Autowired
	UploadRepository uploadRepository;
	
	@Autowired
	MessageUtils messageUtils;
	
	public List<UploadDTO> uploadFiles(List<MultipartFile> files, String fileStoragePath, BigInteger createBy) throws Exception {
		List<UploadDTO> uploadDTOs = new ArrayList<UploadDTO>();
		UploadDTO uploadDTO = new UploadDTO();

		for(int i = 0; i < files.size(); i++) {
			
			MultipartFile file = files.get(i);

			String fileType = FilenameUtils.getExtension(file.getOriginalFilename());
			if (lookupMimeType(fileType) == null) {
				throw new Exception(messageUtils.getMessage("E007"));
			}
			
			String fileNameAtServer = createFileNameAtServer(file.getOriginalFilename());
			saveFileToServer(file, fileStoragePath, fileNameAtServer);
			
			Upload uploadNew = this.saveFileUploadInformation(file, fileStoragePath, fileNameAtServer, createBy);
			
			uploadDTO = convertToDTO(uploadNew);
			uploadDTOs.add(uploadDTO);
		}
			 
		return uploadDTOs;
	}

	public UploadDTO rawedUploadDTO(MultipartFile file, String fileStoragePath) throws Exception {
		UploadDTO uploadDTO = new UploadDTO();
        if (file != null && file.getSize() > 0) {
            final String fullFilename = file.getOriginalFilename();
            String contentType = file.getContentType();
            final String fileExtension = FilenameUtils.getExtension(fullFilename).toLowerCase();
            final String fileName = GenerateIDUtils.getUUID(FilenameUtils.getBaseName(fullFilename));
            final String newFileName = String.format("%s.%s", fileName, fileExtension);

            uploadDTO.setFileName(newFileName);
            uploadDTO.setFilePath(fileStoragePath);
            uploadDTO.setContentType(contentType);

            try {
                final byte[] bytes = file.getBytes();
                final File directories = new File(fileStoragePath);
                if (!directories.exists()) {
                    if (directories.mkdirs()) {
                        System.out.println("Multiple directories are created!");
                    } else {
                        System.out.println("Failed to create multiple directories!");
                    }
                }
                final Path path = Paths.get(fileStoragePath + newFileName);
                Files.write(path, bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return uploadDTO;
	}

	public UploadDTO findById(BigInteger bigInteger) {

		Upload upload = uploadRepository.findByUploadId(bigInteger);
		return upload != null?this.convertToDTO(upload):new UploadDTO();
	}
	
	private Upload saveFileUploadInformation(MultipartFile file, String filePath, String fileNameAtServer, BigInteger createBy) throws Exception {
		Upload portalUpload = new Upload();
		portalUpload.setUploadId(null);
		portalUpload.setFileName(file.getOriginalFilename());
		portalUpload.setFilePath(filePath);
		portalUpload.setFileNameAtServer(fileNameAtServer);
		portalUpload.setContentType(file.getContentType());
		portalUpload.setCreatedDate(new Date());
		portalUpload.setCreatedby(createBy);
		portalUpload = uploadRepository.save(portalUpload);
		return portalUpload;
	}
	
	private UploadDTO convertToDTO(Upload upload) {
		UploadDTO uploadDTo = new UploadDTO();
		uploadDTo.setUploadId(upload.getUploadId());
		uploadDTo.setContentType(upload.getContentType());
		uploadDTo.setFileName(upload.getFileName());
		uploadDTo.setFileNameAtServer(upload.getFileNameAtServer());
		uploadDTo.setFilePath(upload.getFilePath());
		return uploadDTo;
	}
	
	private String createFileNameAtServer(String originalName) throws Exception {
		return String.format("%s_%s", new Date().getTime(), originalName);
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
	
	public Upload deleteByUploadId(BigInteger uploadId) { 
		//1,2,3 default picture
		try {
			if(uploadId == null || uploadId.intValue() == 1 || uploadId.intValue() == 2 || uploadId.intValue() == 3) {
				return null;
			}
		} catch (Exception e) {
		}
		
		Upload upload = uploadRepository.findByUploadId(uploadId);
		if (Objects.nonNull(upload)) {
			try {
				String fullPath = upload.getFilePath() + "/" + upload.getFileNameAtServer();
				File imagesPath = new File(fullPath);
				imagesPath.delete();
				uploadRepository.delete(upload);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
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
				put1("mov", MIME_VIDEO_QUICKTIME);
				put1("mkv", MIME_VIDEO_MKV);
				put1("wmv", MIME_VIDEO_WMV);
				put1("avi", MIME_VIDEO_AVI);
				put1("flv", MIME_VIDEO_FLV);
			}
		};
	}
	
	/**
	 * Simply returns MIME type or <code>null</code> if no type is found.
	 */
	public String lookupMimeType(String ext) {
		return mimeTypeMapping.get(ext.toLowerCase());
	}

}
