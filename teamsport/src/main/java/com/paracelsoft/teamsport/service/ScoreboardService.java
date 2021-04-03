package com.paracelsoft.teamsport.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.paracelsoft.teamsport.api.dto.EventKendoDTO;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.entity.TeamFile;
import com.paracelsoft.teamsport.entity.TeamFolder;
import com.paracelsoft.teamsport.repository.TeamFileRepository;
import com.paracelsoft.teamsport.repository.TeamFolderRepository;
import com.paracelsoft.teamsport.util.MessageUtils;
import com.paracelsoft.teamsport.util.ReponseCode;

@Transactional(rollbackFor = { Exception.class, ParseException.class })
@Service("scoreboardService")
public class ScoreboardService {

	@Autowired
	TeamFileRepository teamFileRepository;

	@Autowired
	MessageUtils messageUtils;

	@Autowired
	Environment evn;

	@Autowired
	TeamFolderRepository teamFolderRepository;

	private final String MIME_IMG_JPEG = "image/jpeg";
	private final String MIME_IMG_PNG = "image/png";
	private final String MIME_VIDEO_MP4 = "video/mp4";

	public ApiResponse addPhotos(List<MultipartFile> files, TeamFolder folder, BigInteger userId) throws Exception {
		ApiResponse respon = new ApiResponse();

		for (int i = 0; i < files.size(); i++) {
			MultipartFile file = files.get(i);
			String fileType = FilenameUtils.getExtension(file.getOriginalFilename());
			if ((!MIME_IMG_JPEG.equals(file.getContentType())) && (!MIME_IMG_PNG.equals(file.getContentType()))
					&& (!MIME_VIDEO_MP4.equals(file.getContentType()))) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E023"));
				return respon;
			}
			String fileNameAtServer = createFileNameAtServer(file.getOriginalFilename());
			// copy file to server
			saveFileToServer(file, folder.getFolderPathAtServer(), fileNameAtServer);
			saveFileInDB(file, folder.getFolderId(), folder.getTeamId(), folder.getFolderPathAtServer(), fileType,
					fileNameAtServer, userId);
		}
		respon.setMessage(messageUtils.getMessage("I026", "photo"));
		return respon;
	}

	public ApiResponse getPhotoInFolder(BigInteger teamId, BigInteger folderId, BigInteger userId) throws Exception {
		ApiResponse respon = new ApiResponse();
		List<TeamFile> listTeamFile = teamFileRepository.getAllFileInFolder(teamId, folderId, 1);
		respon.setData(listTeamFile);
		return respon;
	}

	private String createFileNameAtServer(String originalName) throws Exception {
		return String.format("%s_%s", new Date().getTime(), originalName);
	}

	private void saveFileToServer(MultipartFile file, String filePath, String fileNameAtServer) throws Exception {
		try {
			final File directories = new File(filePath);

			if (!directories.exists()) {
				if (directories.mkdirs()) {
					// logger.info("Multiple directories are created!");
				} else {
					// logger.info("Failed to create multiple directories!");
				}
			}

			final Path path = Paths.get(filePath + "/" + fileNameAtServer);
			OutputStream out = null;
			try {
				out = new BufferedOutputStream(new FileOutputStream(path.toString()));
				out.write(file.getBytes());
			} finally {
				if (out != null)
					out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private TeamFile saveFileInDB(MultipartFile file, BigInteger folderId, BigInteger teamId, String filePath,
			String fileType, String fileNameAtServer, BigInteger createBy) throws Exception {
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
}
