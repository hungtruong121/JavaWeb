package com.paracelsoft.teamsport.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paracelsoft.teamsport.api.dto.FolderDTO;
import com.paracelsoft.teamsport.api.dto.SearchDTO;
import com.paracelsoft.teamsport.api.dto.TeamFilesDTO;
import com.paracelsoft.teamsport.api.dto.TeamFoldersDTO;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.entity.Team;
import com.paracelsoft.teamsport.entity.TeamFile;
import com.paracelsoft.teamsport.entity.TeamFolder;
import com.paracelsoft.teamsport.entity.TeamMember;
import com.paracelsoft.teamsport.entity.TeamRank;
import com.paracelsoft.teamsport.entity.User;
import com.paracelsoft.teamsport.repository.TeamFileRepository;
import com.paracelsoft.teamsport.repository.TeamFolderRepository;
import com.paracelsoft.teamsport.repository.TeamMemberRepository;
import com.paracelsoft.teamsport.repository.TeamRankRepository;
import com.paracelsoft.teamsport.repository.TeamRepository;
import com.paracelsoft.teamsport.repository.UserRepository;
import com.paracelsoft.teamsport.util.DateUtil;
import com.paracelsoft.teamsport.util.MessageUtils;

@Transactional(rollbackFor = { Exception.class, ParseException.class })
@Service("folderService")
public class FolderService {

	@Autowired
	Environment evn;

	@Autowired
	TeamFolderRepository teamFolderRepository;

	@Autowired
	TeamFileRepository teamFileRepository;

	@Autowired
	TeamRankRepository teamRankRepository;

	@Autowired
	TeamRepository teamRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	MessageUtils messageUtils;
	
	@Autowired
	TeamMemberRepository teamMemberRepository;

	public ApiResponse getAllFolderAndFile(BigInteger teamId, BigInteger folderId,BigInteger userId) throws Exception {
		ApiResponse respon = new ApiResponse();
		FolderDTO folderDTO = new FolderDTO();
		List<TeamFolder> listTeamFolder = new ArrayList<TeamFolder>();
		List<TeamFile> listTeamFile = new ArrayList<TeamFile>();
		Team team = teamRepository.findByTeamIdAndIsActive(teamId, 1);
		TeamRank teamRank = teamRankRepository.findByTeamRankIdAndIsActive(team.getTeamRankId(), 1);
		BigInteger totalSizeTeam = teamFileRepository.totalSizeTeam(teamId);

		// Check creating is member in team
		TeamMember teamMember = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(teamId,
				userId, 1);
		if (teamMember == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E013"));
			return respon;
		}
				
		if (folderId == null) {
			listTeamFolder = teamFolderRepository.getAllFolder(teamId, 1);
			listTeamFile = teamFileRepository.getAllFile(teamId, 1);
			Map<String, Object> fileMedia = teamFileRepository.totalFileSzMedia(teamId);
			Map<String, Object> filePhoto = teamFileRepository.totalFileSzPhotos(teamId);
			Map<String, Object> fileDoc = teamFileRepository.totalFileSzDoc(teamId);
			Map<String, Object> fileOthers = teamFileRepository.totalFileSzOthers(teamId);

			if (fileMedia == null) {
				folderDTO.setMedia(folderDTO.setSummary(new BigInteger("0"), new BigInteger("0")));
			} else {
				folderDTO.setMedia(folderDTO.setSummary(new BigInteger(fileMedia.get("totalItems").toString()),
						new BigInteger(fileMedia.get("fileSize").toString())));
			}
			if (filePhoto == null) {
				folderDTO.setPhotos(folderDTO.setSummary(new BigInteger("0"), new BigInteger("0")));
			} else {
				folderDTO.setPhotos(folderDTO.setSummary(new BigInteger(filePhoto.get("totalItems").toString()),
						new BigInteger(filePhoto.get("fileSize").toString())));
			}
			if (filePhoto == null) {
				folderDTO.setDoc(folderDTO.setSummary(new BigInteger("0"), new BigInteger("0")));
			} else {
				folderDTO.setDoc(folderDTO.setSummary(new BigInteger(fileDoc.get("totalItems").toString()),
						new BigInteger(fileDoc.get("fileSize").toString())));
			}
			if (fileOthers == null) {
				folderDTO.setOthers(folderDTO.setSummary(new BigInteger("0"), new BigInteger("0")));
			} else {
				folderDTO.setOthers(folderDTO.setSummary(new BigInteger(fileOthers.get("totalItems").toString()),
						new BigInteger(fileOthers.get("fileSize").toString())));
			}
		} else {
			listTeamFolder = teamFolderRepository.getAllFolderByParent(teamId, folderId, 1);
			listTeamFile = teamFileRepository.getAllFileInFolder(teamId, folderId, 1);
			List<TeamFoldersDTO> getFolderParent = getFolderParent(folderId);
			folderDTO.setPathFolder(getFolderParent);

		}
		List<TeamFoldersDTO> listTeamFolderDTO = new ArrayList<TeamFoldersDTO>();

		if (listTeamFolder != null) {
			for (TeamFolder teamFolderTmp : listTeamFolder) {
				TeamFoldersDTO teamFolderDTO = convertToDTO(teamFolderTmp);
				User user = userRepository.findByUserIdAndIsActive(teamFolderTmp.getCreatedby(), 1);
				BigInteger folderSize = teamFolderRepository.folderSize(teamFolderTmp.getFolderId());
				teamFolderDTO.setFolderSize(folderSize);
				if (user != null) {
					teamFolderDTO.setCreatedByName(user.getUserFullName());
				}
				listTeamFolderDTO.add(teamFolderDTO);
			}
		}

		List<TeamFilesDTO> listTeamFileDTO = new ArrayList<TeamFilesDTO>();
		if (listTeamFile != null) {
			for (TeamFile teamFile : listTeamFile) {
				TeamFilesDTO teamFileDTO = convertToDTO(teamFile);
				User user = userRepository.findByUserIdAndIsActive(teamFile.getCreatedBy(), 1);
				if (user != null) {
					teamFileDTO.setCreatedByName(user.getUserFullName());
				}
				listTeamFileDTO.add(teamFileDTO);
			}
		}
		folderDTO.setStorage(totalSizeTeam);
		if (teamRank != null) {
			folderDTO.setStorageCapacity(teamRank.getStorageCapacity());
		}
		folderDTO.setListTeamFolderDTO(listTeamFolderDTO);
		folderDTO.setListTeamFileDTO(listTeamFileDTO);

		respon.setData(folderDTO);

		return respon;
	}

	public ApiResponse addFolder(TeamFoldersDTO folderDTO, BigInteger userId) throws Exception {
		ApiResponse respon = new ApiResponse();

		if (folderDTO == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "folder"));
			return respon;
		}
		
		// Check creating is member in team
		TeamMember teamMember = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(folderDTO.getTeamId(),
				userId, 1);
		if (teamMember == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E013"));
			return respon;
		}

		// check name folder exist
		String checkFolderExist = null;
		if (folderDTO.getParentFolderId() != null) {
			checkFolderExist = teamFolderRepository.findByTeamIdAndParentFolderIdAndFolderName(folderDTO.getTeamId(),
					folderDTO.getParentFolderId(), folderDTO.getFolderName(), 1);
		} else {
			checkFolderExist = teamFolderRepository.findByTeamIdAndFolderName(folderDTO.getTeamId(),
					folderDTO.getFolderName(), 1);
		}

		if (checkFolderExist != null && !checkFolderExist.isEmpty()) {
			int i = 0;
			String listIndex = "";
			String folderName = "";
			do {
				i++;
				folderName = checkFolderExist + "_" + String.valueOf(i);
				if (folderDTO.getParentFolderId() != null) {
					listIndex = teamFolderRepository.findByTeamIdAndParentFolderIdAndFolderName(folderDTO.getTeamId(),
							folderDTO.getParentFolderId(), folderName, 1);
				} else {
					listIndex = teamFolderRepository.findByTeamIdAndFolderName(folderDTO.getTeamId(), folderName, 1);
				}
			} while (listIndex != null);
			folderDTO.setFolderName(folderName);
		}

		TeamFolder teamFolder = convertToEntity(folderDTO, userId, 1, new Date(), new Date());

		List<TeamFoldersDTO> getFolderParent = getFolderParent(folderDTO.getParentFolderId());
		String path = "";
		if (getFolderParent != null && !getFolderParent.isEmpty()) {
			for (TeamFoldersDTO obj : getFolderParent) {
				path = obj.getFolderName() + "/" + path;
			}
		}
		if ("".equals(path)) {
			path = folderDTO.getTeamId() + "/" + folderDTO.getFolderName() + "/";
		} else {
			path = folderDTO.getTeamId() + "/" + path + folderDTO.getFolderName() + "/";
		}

		teamFolder.setFolderPathAtServer(evn.getProperty("source.image.path").toString() + "/" + path);
		teamFolderRepository.save(teamFolder);
		saveFolderToServer(evn.getProperty("source.image.path").toString(), path);
		respon.setMessage(messageUtils.getMessage("I001", "folder"));
		return respon;
	}

	public ApiResponse searchFolderAndFile(SearchDTO search) throws Exception {
		ApiResponse respon = new ApiResponse();

		String keyword = search.getKeyword();
		if (StringUtils.isEmpty(keyword)) {// full
			keyword = "%%";
		} else {// start with keywork
			keyword = "%" + keyword + "%";
		}
		if (search.getMaxResult() == 0) {
			search.setMaxResult(10);
		}
		List<TeamFolder> listTeamFolder = teamFolderRepository.findByFolderNameAndIsActive(search.getTeamId(), keyword,
				search.getFirstResult(), search.getMaxResult(), 1);
		List<TeamFoldersDTO> listTeamFolderDTO = new ArrayList<TeamFoldersDTO>();
		if (listTeamFolder != null && !listTeamFolder.isEmpty()) {
			for (TeamFolder teamFolder : listTeamFolder) {
				TeamFoldersDTO teamFolderDTO = convertToDTO(teamFolder);
				User user = userRepository.findByUserIdAndIsActive(teamFolder.getCreatedby(), 1);
				if (user != null) {
					teamFolderDTO.setCreatedByName(user.getUserFullName());
				}
				BigInteger folderSize = teamFolderRepository.folderSize(teamFolder.getFolderId());
				teamFolderDTO.setFolderSize(folderSize);
				listTeamFolderDTO.add(teamFolderDTO);
			}
		}

		List<TeamFile> listTeamFile = teamFileRepository.findByFileNameAndIsActive(search.getTeamId(), keyword,
				search.getFirstResult(), search.getMaxResult(), 1);
		List<TeamFilesDTO> listTeamFileDTO = new ArrayList<TeamFilesDTO>();
		if (listTeamFile != null && !listTeamFile.isEmpty()) {
			for (TeamFile teamFile : listTeamFile) {
				TeamFilesDTO teamFileDTO = convertToDTO(teamFile);
				listTeamFileDTO.add(teamFileDTO);
			}
		}
		search.setListTeamFileDTO(listTeamFileDTO);
		search.setListTeamFolderDTO(listTeamFolderDTO);
		respon.setData(search);

		return respon;
	}

	public ApiResponse renameFolder(BigInteger folderId, String folderName,BigInteger userId) throws Exception {
		ApiResponse respon = new ApiResponse();
		TeamFolder teamFolder = teamFolderRepository.findByFolderIdAndIsActive(folderId, 1);
		if (teamFolder == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "folder"));
			return respon;
		}
		
		// Check creating is member in team
		TeamMember teamMember = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(teamFolder.getTeamId(),
				userId, 1);
		if (teamMember == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E013"));
			return respon;
		}

		// check name folder exist
		String checkFolderExist = null;
		if (teamFolder.getParentFolderId() != null) {
			checkFolderExist = teamFolderRepository.findByTeamIdAndParentFolderIdAndFolderName(teamFolder.getTeamId(),
					teamFolder.getParentFolderId(), folderName, 1);
		} else {
			checkFolderExist = teamFolderRepository.findByTeamIdAndFolderName(teamFolder.getTeamId(), folderName, 1);
		}

		String listIndex = "";
		String folderNameNew = "";
		if (checkFolderExist != null && !checkFolderExist.isEmpty()) {
			int i = 0;

			do {
				i++;
				folderNameNew = checkFolderExist + "_" + String.valueOf(i);
				if (teamFolder.getParentFolderId() != null) {
					listIndex = teamFolderRepository.findByTeamIdAndParentFolderIdAndFolderName(teamFolder.getTeamId(),
							teamFolder.getParentFolderId(), folderNameNew, 1);
				} else {
					listIndex = teamFolderRepository.findByTeamIdAndFolderName(teamFolder.getTeamId(), folderNameNew,
							1);
				}
			} while (listIndex != null);
		} else {
			folderNameNew = folderName;

		}

		// get folder Parent to rename path
		List<TeamFoldersDTO> getFolderParent = getFolderParent(folderId);

		String newPath = "";
		if (getFolderParent != null && !getFolderParent.isEmpty()) {
			getFolderParent.get(0).setFolderName(folderNameNew);
			for (int i = 0; i < getFolderParent.size(); i++) {
				newPath = getFolderParent.get(i).getFolderName() + "/" + newPath;
			}
		}
		newPath = evn.getProperty("source.image.path").toString() + "/" + teamFolder.getTeamId() + "/" + newPath;
		String oldPath = teamFolder.getFolderPathAtServer();

		// get folder Child to rename path
		List<TeamFolder> listFolderChild = teamFolderRepository.findByFolderPathAtServer(oldPath);
		if (listFolderChild != null) {
			for (TeamFolder obj : listFolderChild) {
				obj.setFolderPathAtServer(obj.getFolderPathAtServer().replace(oldPath, newPath));
				teamFolderRepository.save(obj);
			}
		}

		List<TeamFile> listFileChild = teamFileRepository.findByFilePathAtServer(oldPath);
		if (listFileChild != null) {
			for (TeamFile obj : listFileChild) {
				obj.setFilePathAtServer(obj.getFilePathAtServer().replace(oldPath, newPath));
				teamFileRepository.save(obj);
			}
		}
		teamFolder.setFolderName(folderNameNew);
		teamFolder.setFolderPathAtServer(newPath);
		teamFolder.setUpdatedDate(new Date());
		teamFolder = teamFolderRepository.save(teamFolder);

		// rename folder at server
		Path source = Paths.get(oldPath);
		if (source == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "folder"));
			return respon;
		}
		try {
			Files.move(source, source.resolveSibling(folderNameNew + "/"));
		} catch (Exception e) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E005", "folder"));
			return respon;
		}
		respon.setMessage(messageUtils.getMessage("I003", "folder"));
		return respon;
	}

	public ApiResponse removeFolder(BigInteger folderId,BigInteger userId) throws Exception {
		ApiResponse respon = new ApiResponse();
		
		TeamFolder teamFolder = teamFolderRepository.findByFolderIdAndIsActive(folderId, 1);
		if (teamFolder == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "folder"));
			return respon;
		}
		
		// Check creating is member in team
		TeamMember teamMember = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(teamFolder.getTeamId(),
				userId, 1);
		if (teamMember == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E013"));
			return respon;
		}
		
		List<TeamFoldersDTO> listFolderChild = removeFolderChild(folderId);
		if (listFolderChild == null) {
			respon.setMessage(messageUtils.getMessage("E001", "folder"));
		} else {
			respon.setData(listFolderChild);
			respon.setMessage(messageUtils.getMessage("I002", "folder"));
		}
		return respon;
	}

	public ApiResponse setTimeDelete(BigInteger folderId, String date, String timeZone) throws Exception {
		ApiResponse respon = new ApiResponse();
		TeamFolder teamFolder = teamFolderRepository.findByFolderIdAndIsActive(folderId, 1);
		if (teamFolder != null) {
			teamFolder.setFolderDeleteDate(DateUtil.fomatTimeZoneToUTC(date, DateUtil.PT_DD_MM_YYYY_HH,
					DateUtil.PT_YYYY_MM_DD_HH_MM_SS, timeZone));
			teamFolder.setUpdatedDate(new Date());
			teamFolderRepository.save(teamFolder);
			respon.setMessage(messageUtils.getMessage("I003", "folder"));
		}
		return respon;
	}

	public TeamFoldersDTO convertToDTO(TeamFolder teamFolder) {
		TeamFoldersDTO teamFolderDTO = new TeamFoldersDTO();
		if (teamFolder.getFolderId() != null) {
			teamFolderDTO.setFolderId(teamFolder.getFolderId());
		} else {
			teamFolderDTO.setFolderId(null);
		}
		teamFolderDTO.setTeamId(teamFolder.getTeamId());
		teamFolderDTO.setParentFolderId(teamFolder.getParentFolderId());
		teamFolderDTO.setFolderName(teamFolder.getFolderName());
		teamFolderDTO.setCreatedDate(teamFolder.getCreatedDate());
		teamFolderDTO.setUpdatedDate(teamFolder.getUpdatedDate());
		teamFolderDTO.setFolderDeleteDate(teamFolder.getFolderDeleteDate());
		teamFolderDTO.setCreatedby(teamFolder.getCreatedby());
		teamFolderDTO.setIsActive(teamFolder.getIsActive());
		return teamFolderDTO;
	}

	public TeamFolder convertToEntity(TeamFoldersDTO teamFolderDTO, BigInteger userId, int isActive, Date createdDate,
			Date updatedDate) {
		TeamFolder teamFolder = new TeamFolder();
		if (teamFolderDTO.getFolderId() != null) {
			teamFolder.setFolderId(teamFolderDTO.getFolderId());
		} else {
			teamFolder.setFolderId(null);
		}
		teamFolder.setTeamId(teamFolderDTO.getTeamId());
		teamFolder.setParentFolderId(teamFolderDTO.getParentFolderId());
		teamFolder.setFolderName(teamFolderDTO.getFolderName());
		teamFolder.setCreatedby(userId);
		teamFolder.setIsActive(isActive);
		teamFolder.setFolderDeleteDate(teamFolderDTO.getFolderDeleteDate());
		teamFolder.setCreatedDate(createdDate);
		teamFolder.setUpdatedDate(updatedDate);
		return teamFolder;
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

	/**
	 * 
	 * @Des get child by parent
	 * @param
	 * @return
	 */
	public List<TeamFoldersDTO> removeFolderChild(BigInteger parentFolderId) throws Exception {
		TeamFolder teamFolder = teamFolderRepository.findByFolderId(parentFolderId);
		if (teamFolder == null) {
			return null;
		}
		File folderPath = new File(teamFolder.getFolderPathAtServer());
		if (folderPath != null) {
			folderPath.delete();
		}

		teamFileRepository.deleteAllFileInFolder(parentFolderId, 1);
		teamFolderRepository.deleteById(parentFolderId);

		// find category child
		List<TeamFolder> listteamFolder = teamFolderRepository.findAllByParentFolderId(parentFolderId);
		if (listteamFolder == null || listteamFolder.isEmpty()) {
			return null;
		}
		List<TeamFoldersDTO> testFolder = new ArrayList<TeamFoldersDTO>();
		for (TeamFolder obj : listteamFolder) {
			TeamFoldersDTO test = new TeamFoldersDTO();
			test.setFolderId(obj.getFolderId());
			// de qui
			test.setFolderChild(this.removeFolderChild(obj.getFolderId()));
			testFolder.add(test);
		}
		return testFolder;
	}

	/**
	 * 
	 * @des get parent by child(child dc get tu filed parentFolder)
	 * @param childFolderId
	 * @return
	 */
	public List<TeamFoldersDTO> getFolderParent(BigInteger childFolderId) throws Exception {
		List<TeamFoldersDTO> testFolder = new ArrayList<>();
		// find category parent
		TeamFolder parentFolder = teamFolderRepository.findByFolderId(childFolderId);
		if (parentFolder == null) {
			return null;
		}
		TeamFoldersDTO test = new TeamFoldersDTO();
		test.setFolderId(parentFolder.getFolderId());
		test.setFolderName(parentFolder.getFolderName());
		test.setCreatedby(parentFolder.getCreatedby());
		User user = userRepository.findByUserIdAndIsActive(parentFolder.getCreatedby(), 1);
		if (user != null) {
			test.setCreatedByName(user.getUserFullName());
		}
		testFolder.add(test);
		if (parentFolder.getParentFolderId() == null) {
			return testFolder;
		} else {
			List<TeamFoldersDTO> testDq = this.getFolderParent(parentFolder.getParentFolderId());
			if (testDq != null) {
				testFolder.addAll(testDq);
			}
		}
		return testFolder;
	}

	/**
	 * 
	 * @Des get child by parent
	 * @param
	 * @return
	 */
	public List<TeamFoldersDTO> getFolderChild(BigInteger parentFolderId) throws Exception {
		// find category child
		List<TeamFolder> listteamFolder = teamFolderRepository.findAllByParentFolderId(parentFolderId);
		if (listteamFolder == null || listteamFolder.isEmpty()) {
			return null;
		}
		List<TeamFoldersDTO> testFolder = new ArrayList<TeamFoldersDTO>();
		for (TeamFolder obj : listteamFolder) {
			TeamFoldersDTO test = new TeamFoldersDTO();
			test.setFolderId(obj.getFolderId());
			test.setFolderName(obj.getFolderName());
			// de qui
			test.setFolderChild(this.getFolderChild(obj.getFolderId()));
			testFolder.add(test);
		}
		return testFolder;
	}

	public ApiResponse downloadFolder(HttpServletResponse resonse, BigInteger folderId, BigInteger teamId)
			throws Exception {
		ApiResponse respon = new ApiResponse();
		TeamFolder teamFolder = teamFolderRepository.findByFolderIdAndIsActive(folderId, 1);
		if (teamFolder == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "folder"));
			return respon;
		}
		List<TeamFoldersDTO> getFolderParent = getFolderParent(folderId);
		String path = "";
		if (getFolderParent != null && !getFolderParent.isEmpty()) {
			for (TeamFoldersDTO obj : getFolderParent) {
				path = obj.getFolderName() + "/" + path;
			}
			path = evn.getProperty("source.image.path").toString() + "/" + teamId + "/" + path;
		}

		File fileToZip = new File(path);

		String downloadFilename = new SimpleDateFormat(DateUtil.PT_YYYY_MM_DD).format(new Date()) + ".zip";
		resonse.reset();
		// Content-Type
		resonse.setContentType("application/x-msdownload");

		// Content-Disposition
		resonse.setHeader(HttpHeaders.CONTENT_DISPOSITION,
				"attachment;filename=" + URLEncoder.encode(downloadFilename, "UTF-8"));
		OutputStream fos = resonse.getOutputStream();
		ZipOutputStream zipOut = new ZipOutputStream(fos);

		zipFile(fileToZip, fileToZip.getName(), zipOut);
		zipOut.close();
		fos.close();

		respon.setMessage(messageUtils.getMessage("I019", "file"));
		return respon;
	}

	private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
		if (fileToZip.isHidden()) {
			return;
		}
		if (fileToZip.isDirectory()) {
			if (fileName.endsWith("/")) {
				zipOut.putNextEntry(new ZipEntry(fileName));
				zipOut.closeEntry();
			} else {
				zipOut.putNextEntry(new ZipEntry(fileName + "/"));
				zipOut.closeEntry();
			}
			File[] children = fileToZip.listFiles();
			for (File childFile : children) {
				zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
			}
			return;
		}
		FileInputStream fis = new FileInputStream(fileToZip);
		ZipEntry zipEntry = new ZipEntry(fileName);
		zipOut.putNextEntry(zipEntry);
		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zipOut.write(bytes, 0, length);
		}
		fis.close();
	}

	public void saveFolderToServer(String folderPath, String folderNameAtServer) throws Exception {
		try {
			final File directories = new File(folderPath + "/" + folderNameAtServer);

			if (!directories.exists()) {
				if (directories.mkdirs()) {
				} else {
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
