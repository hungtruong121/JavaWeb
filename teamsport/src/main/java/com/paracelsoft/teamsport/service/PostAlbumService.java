
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.paracelsoft.teamsport.api.dto.PostAlbumDTO;
import com.paracelsoft.teamsport.api.dto.UploadDTO;
import com.paracelsoft.teamsport.api.dto.UserDTO;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.entity.Post;
import com.paracelsoft.teamsport.entity.PostIncludeUser;
import com.paracelsoft.teamsport.entity.PostLike;
import com.paracelsoft.teamsport.entity.TeamFile;
import com.paracelsoft.teamsport.entity.TeamFolder;
import com.paracelsoft.teamsport.entity.TeamMember;
import com.paracelsoft.teamsport.entity.Upload;
import com.paracelsoft.teamsport.entity.User;
import com.paracelsoft.teamsport.repository.HashTagContainerRepository;
import com.paracelsoft.teamsport.repository.PostCommentRepository;
import com.paracelsoft.teamsport.repository.PostIncludeUserRepository;
import com.paracelsoft.teamsport.repository.PostLikeRepository;
import com.paracelsoft.teamsport.repository.PostRepository;
import com.paracelsoft.teamsport.repository.PrivacyRepository;
import com.paracelsoft.teamsport.repository.TeamFileRepository;
import com.paracelsoft.teamsport.repository.TeamFolderRepository;
import com.paracelsoft.teamsport.repository.TeamMemberRepository;
import com.paracelsoft.teamsport.repository.UploadRepository;
import com.paracelsoft.teamsport.repository.UserRepository;
import com.paracelsoft.teamsport.util.ConstantUtils;
import com.paracelsoft.teamsport.util.JsonUtil;
import com.paracelsoft.teamsport.util.MessageUtils;
import com.paracelsoft.teamsport.util.ReponseCode;

@Transactional(rollbackFor = { Exception.class, ParseException.class })
@Service("postAlbumnService")
public class PostAlbumService {

	@Autowired
	PrivacyRepository privacyRepository;

	@Autowired
	PostRepository postRepository;

	@Autowired
	MessageUtils messageUtils;

	@Autowired
	HashTagContainerRepository hashTagContainerRepository;

	@Autowired
	PostIncludeUserRepository postIncludeUserRepository;

	@Autowired
	PostCommentRepository postCommentRepository;

	@Autowired
	TeamMemberRepository teamMemberRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PostLikeRepository postLikeRepository;

	@Autowired
	PostService postService;

	@Autowired
	UploadService uploadService;

	@Autowired
	NotificationService notificationService;

	@Autowired
	UploadRepository uploadRepository;

	@Autowired
	Environment evn;

	@Autowired
	TeamFolderRepository teamFolderRepository;

	@Autowired
	TeamFileRepository teamFileRepository;

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

	/**
	 * 
	 * @param teamId
	 * @param postId
	 * @return
	 */
	public ApiResponse getAlbumPublic(BigInteger teamId, BigInteger userId) {
		ApiResponse respon = new ApiResponse();
		List<PostAlbumDTO> listPostAlbumDTO = new ArrayList<PostAlbumDTO>();

		// return về user có thuộc team hay ko?
		TeamMember teamMember = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(teamId, userId, 1);

		// get post public by team id
		List<Post> listAlbum = new ArrayList<Post>();
		Collection<BigInteger> listPrivacy = Arrays.asList(new BigInteger("1"), new BigInteger("2"));
		if (teamMember != null) {// public or include
			listAlbum = postRepository.getListPostByTeamId(teamId, ConstantUtils.Post_Type.POST_ALBUM.getValue(),
					listPrivacy, userId, 1);
		} else {// only public
			listAlbum = postRepository.getListPostByTeamId(teamId, ConstantUtils.Post_Type.POST_ALBUM.getValue(),
					listPrivacy, null, 1);
		}

		if (listAlbum != null && !listAlbum.isEmpty()) {
			for (Post post : listAlbum) {
				PostAlbumDTO postAlbumDTO = convertToDTO(post, userId);
				listPostAlbumDTO.add(postAlbumDTO);
			}
		}
		respon.setData(listPostAlbumDTO);
		return respon;
	}

	/**
	 * 
	 * @param teamId
	 * @param postId
	 * @return
	 * @throws JSONException
	 * @throws NoSuchMessageException
	 */
	public ApiResponse createAlbum(PostAlbumDTO postAlbumDTO, BigInteger createdBy)
			throws NoSuchMessageException, JSONException {
		ApiResponse respon = new ApiResponse();
		BigInteger privacyInclude = new BigInteger(ConstantUtils.Privacy.INCLUDE.getValue());
		BigInteger privacyPublic = new BigInteger(ConstantUtils.Privacy.PUBLIC.getValue());

		// Check creating is member in team
		TeamMember teamMember = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(postAlbumDTO.getTeamId(),
				createdBy, 1);
		if (teamMember == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E013"));
			return respon;
		}

		if (postAlbumDTO.getPostId() != null) {
			Post oldpostAlbum = postRepository.findByPostIdAndIsActive(postAlbumDTO.getPostId(), 1);
			if (oldpostAlbum == null) { // postAlbum not exist
				respon.setSuccess(false);
				respon.setMessage(messageUtils.getMessage("E001", "album"));
				return respon;
			} else {
				if (!ConstantUtils.Post_Type.POST_ALBUM.getValue().equals(oldpostAlbum.getPostType())) {
					respon.setSuccess(false);
					respon.setMessage(messageUtils.getMessage("E001", "album"));
					return respon;
				}
			}
			if (!oldpostAlbum.getPrivacyId().equals(postAlbumDTO.getPrivacyId())) {
				// update Privacy Photo in Albumm
				List<Post> listParent = postRepository.getListPostParent(oldpostAlbum.getPostId(), 1);
				if (listParent != null && !listParent.isEmpty()) {
					for (Post parent : listParent) {
						List<Post> listChild = postRepository.getListPostParent(parent.getPostId(), 1);
						if (listChild != null && !listChild.isEmpty()) {
							for (Post child : listChild) {

								// if old is include
								List<PostIncludeUser> oldUserIncludeChild = new ArrayList<PostIncludeUser>();
								if (privacyInclude.compareTo(child.getPrivacyId()) == 0
										|| privacyPublic.compareTo(child.getPrivacyId()) == 0) {
									// get old include user
									oldUserIncludeChild = postIncludeUserRepository
											.findByPostIdAndUserIdAndPostIncludeUseTypeAndIsActiveAndPostCommentIdIsNull(
													child.getPostId(), createdBy,
													ConstantUtils.Post_Include_User_Type.PRIVACY.getValue(), 1);

									// delete old post include user
									postIncludeUserRepository.deleteOldByPostAndType(child.getPostId(),
											ConstantUtils.Post_Include_User_Type.PRIVACY.getValue());
								}

								// if new is include, check include user and save new include user
								if ((privacyInclude.compareTo(child.getPrivacyId()) == 0
										|| privacyPublic.compareTo(child.getPrivacyId()) == 0)
										&& postAlbumDTO.getUserInclude() != null
										&& !postAlbumDTO.getUserInclude().isEmpty()) {
									postService.savePostIncludeUser(postAlbumDTO.getUserInclude(), createdBy,
											postAlbumDTO.getPostId(), new Date(),
											ConstantUtils.Post_Include_User_Type.PRIVACY.getValue(), null,
											oldUserIncludeChild);

								}
								child.setPrivacyId(postAlbumDTO.getPrivacyId());
								child.setUpdatedDate(new Date());
								child = postRepository.save(child);
							}
						} else {

							// if old is include
							List<PostIncludeUser> oldUserIncludeParent = new ArrayList<PostIncludeUser>();
							if (privacyInclude.compareTo(parent.getPrivacyId()) == 0
									|| privacyPublic.compareTo(parent.getPrivacyId()) == 0) {
								// get old include user
								oldUserIncludeParent = postIncludeUserRepository
										.findByPostIdAndUserIdAndPostIncludeUseTypeAndIsActiveAndPostCommentIdIsNull(
												parent.getPostId(), createdBy,
												ConstantUtils.Post_Include_User_Type.PRIVACY.getValue(), 1);

								// delete old post include user
								postIncludeUserRepository.deleteOldByPostAndType(parent.getPostId(),
										ConstantUtils.Post_Include_User_Type.PRIVACY.getValue());
							}

							// if new is include, check include user and save new include user
							if ((privacyInclude.compareTo(parent.getPrivacyId()) == 0
									|| privacyPublic.compareTo(parent.getPrivacyId()) == 0)
									&& postAlbumDTO.getUserInclude() != null
									&& !postAlbumDTO.getUserInclude().isEmpty()) {
								postService.savePostIncludeUser(postAlbumDTO.getUserInclude(), createdBy,
										postAlbumDTO.getPostId(), new Date(),
										ConstantUtils.Post_Include_User_Type.PRIVACY.getValue(), null,
										oldUserIncludeParent);

							}
							parent.setPrivacyId(postAlbumDTO.getPrivacyId());
							parent.setUpdatedDate(new Date());
							parent = postRepository.save(parent);
						}
					}
				}
			}
			oldpostAlbum.setPostContent(postAlbumDTO.getPostContent());
			oldpostAlbum.setPostDescription(postAlbumDTO.getPostDescription());
			oldpostAlbum.setPrivacyId(postAlbumDTO.getPrivacyId());
			oldpostAlbum.setUpdatedDate(new Date());
			oldpostAlbum = postRepository.save(oldpostAlbum);
			respon.setMessage(messageUtils.getMessage("I003", "album"));
		} else {
			Post album = new Post();
			album.setTeamId(postAlbumDTO.getTeamId());
			album.setPostType(ConstantUtils.Post_Type.POST_ALBUM.getValue());
			album.setPostContent(postAlbumDTO.getPostContent());
			album.setPostDescription(postAlbumDTO.getPostDescription());
			album.setPrivacyId(postAlbumDTO.getPrivacyId());
			album.setCreatedby(createdBy);
			album.setCreatedDate(new Date());
			album.setUpdatedDate(new Date());
			album = postRepository.save(album);

			// if new is include, check include user and save new include user
			if ((privacyInclude.compareTo(album.getPrivacyId()) == 0
					|| privacyPublic.compareTo(album.getPrivacyId()) == 0) && postAlbumDTO.getUserInclude() != null
					&& !postAlbumDTO.getUserInclude().isEmpty()) {
				postService.savePostIncludeUser(postAlbumDTO.getUserInclude(), createdBy, album.getPostId(), new Date(),
						ConstantUtils.Post_Include_User_Type.PRIVACY.getValue(), null, null);
			}
			respon.setData(album);
			respon.setMessage(messageUtils.getMessage("I001", "album"));
		}
		return respon;
	}

	public ApiResponse updatePhotoInTeam(PostAlbumDTO postAlbumDTO, BigInteger createdBy)
			throws NoSuchMessageException, JSONException {
		ApiResponse respon = new ApiResponse();
		BigInteger privacyInclude = new BigInteger(ConstantUtils.Privacy.INCLUDE.getValue());
		BigInteger privacyPublic = new BigInteger(ConstantUtils.Privacy.PUBLIC.getValue());
		Date date = new Date();
		List<PostIncludeUser> oldUserInclude = new ArrayList<PostIncludeUser>();
		if (postAlbumDTO != null) {
			Post photo = postRepository.findByPostIdAndIsActive(postAlbumDTO.getPostId(), 1);
			if (photo != null) {
				photo.setPostDescription(postAlbumDTO.getPostDescription());
				photo.setPostContent(postAlbumDTO.getPostContent());
				photo.setUpdatedDate(new Date());
				if (!isAlbum(photo)) {
					photo.setPrivacyId(postAlbumDTO.getPrivacyId());
					photo.setLocale(postAlbumDTO.getLocation());
				}
			}
			photo = postRepository.save(photo);
			// delete old hash tag of post
			hashTagContainerRepository.deleteOldByPostAndTeam(postAlbumDTO.getPostId(), postAlbumDTO.getTeamId());
			// get old include user
			oldUserInclude = postIncludeUserRepository
					.findByPostIdAndUserIdAndPostIncludeUseTypeAndIsActiveAndPostCommentIdIsNull(
							postAlbumDTO.getPostId(), createdBy,
							ConstantUtils.Post_Include_User_Type.PRIVACY.getValue(), 1);

			postIncludeUserRepository.deleteOldByPostAndType(photo.getPostId(),
					ConstantUtils.Post_Include_User_Type.PRIVACY.getValue());

			// delete old post tag user
			postIncludeUserRepository.deleteOldByPostAndType(photo.getPostId(),
					ConstantUtils.Post_Include_User_Type.TAG_POST.getValue());

			if (postAlbumDTO.getHashTag() != null && !postAlbumDTO.getHashTag().isEmpty()) {
				// save new hash tag of post
				postService.saveHastag(postAlbumDTO.getHashTag(), createdBy, photo.getPostId(), photo.getTeamId(),
						photo.getPrivacyId(), date);
			}

			// save post include user : privacy is include or public
			if (postAlbumDTO.getUserInclude() != null && !postAlbumDTO.getUserInclude().isEmpty()
					&& (privacyInclude.compareTo(postAlbumDTO.getPrivacyId()) == 0
							|| privacyPublic.compareTo(postAlbumDTO.getPrivacyId()) == 0)) {
				postService.savePostIncludeUser(postAlbumDTO.getUserInclude(), createdBy, postAlbumDTO.getPostId(),
						date, ConstantUtils.Post_Include_User_Type.PRIVACY.getValue(), null, oldUserInclude);
			}

			// save post tag user : tag
			if (postAlbumDTO.getUserTag() != null && !postAlbumDTO.getUserTag().isEmpty()) {
				postService.savePostIncludeUser(postAlbumDTO.getUserTag(), createdBy, photo.getPostId(), date,
						ConstantUtils.Post_Include_User_Type.TAG_POST.getValue(), null, oldUserInclude);
			}
		}
		respon.setMessage(messageUtils.getMessage("I003", "photo"));
		return respon;
	}

	public ApiResponse addPhotos(List<MultipartFile> files, String infoString, BigInteger userId) throws Exception {
		ApiResponse respon = new ApiResponse();
		Date date = new Date();
		BigInteger privacyInclude = new BigInteger(ConstantUtils.Privacy.INCLUDE.getValue());
		BigInteger privacyPublic = new BigInteger(ConstantUtils.Privacy.PUBLIC.getValue());

		infoString = infoString.replaceAll("undefined", "\"\"");
		infoString = infoString.replaceAll("Array", "");

		PostAlbumDTO dto = (PostAlbumDTO) JsonUtil.convertToObject(infoString, PostAlbumDTO.class);
		List<PostAlbumDTO> listChild = new ArrayList<PostAlbumDTO>();
		List<PostAlbumDTO> listChildDTO = new ArrayList<PostAlbumDTO>();
		if (dto == null) {
			respon.setSuccess(false);
			respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
			return respon;
		}
		if (dto.getPostChild() != null && !dto.getPostChild().isEmpty()) {
			listChild = dto.getPostChild();
		}
		// Check creating is member in team
		TeamMember teamMember = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(dto.getTeamId(), userId, 1);
		if (teamMember == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E013"));
			return respon;
		}
		Post postAlbum = null;
		Post postParent = new Post();

		if (dto.getPostId() == null) {// check album not exist
			postParent = convertToEntity(dto);
			postParent.setCreatedby(userId);
		} else {
			postAlbum = postRepository.findByPostIdAndIsActive(dto.getPostId(), 1);
			if (postAlbum == null) {
				respon.setSuccess(false);
				respon.setMessage(messageUtils.getMessage("E001", "album"));
				return respon;
			}
		}
		// add photo

		if (postAlbum != null && ConstantUtils.Post_Type.POST_ALBUM.getValue().equals(postAlbum.getPostType())) {
			List<UploadDTO> uploadDTOs = uploadService.uploadFiles(files,
					evn.getProperty("source.image.path").toString(), userId);
			if (uploadDTOs != null && !uploadDTOs.isEmpty()) {
				if (uploadDTOs.size() == 1) {// if photo = 1,postParent = postPhoto
					postParent.setPostParentId(postAlbum.getPostId());
					postParent.setTeamId(postAlbum.getTeamId());
					postParent.setPrivacyId(postAlbum.getPrivacyId());
					postParent.setLocale(postAlbum.getLocale());
					postParent.setCreatedby(userId);
					postParent.setPostMedia(uploadDTOs.get(0).getUploadId());
					postParent.setPostType(ConstantUtils.Post_Type.POST_PHOTO.getValue());
					if (dto.getPostContent() != null) {
						postParent.setPostContent(dto.getPostContent());
					}
					if (dto.getPostDescription() != null) {
						postParent.setPostDescription(dto.getPostDescription());
					}
					if (listChild != null && !listChild.isEmpty()) {
						postParent.setPostContent(listChild.get(0).getPostContent());
						postParent.setPostContent(listChild.get(0).getPostDescription());
					}
					postParent.setLocale(dto.getLocation());
					postParent.setPostDescription(dto.getPostDescription());
					postParent.setCreatedDate(new Date());
					postParent.setUpdatedDate(new Date());
					postParent = postRepository.save(postParent);

				} else {
					for (int j = 0; j < uploadDTOs.size(); j++) {
						Post photo = new Post();
						photo.setTeamId(postAlbum.getTeamId());
						photo.setPostParentId(postAlbum.getPostId());
						if (listChild != null && !listChild.isEmpty()) {
							photo.setPostContent(listChild.get(j).getPostContent());
							photo.setPostDescription(listChild.get(j).getPostDescription());
						}
						photo.setPostType(ConstantUtils.Post_Type.POST_PHOTO.getValue());
						photo.setPostMedia(uploadDTOs.get(j).getUploadId());
						photo.setLocale(postAlbum.getLocale());
						photo.setPrivacyId(postAlbum.getPrivacyId());
						photo.setCreatedby(postAlbum.getCreatedby());
						photo.setCreatedDate(new Date());
						photo.setUpdatedDate(new Date());
						photo = postRepository.save(photo);

						if (listChild != null && !listChild.isEmpty()) {
							if (listChild.get(j).getHashTag() != null && !listChild.get(j).getHashTag().isEmpty()) {
								// save new hash tag of post
								postService.saveHastag(listChild.get(j).getHashTag(), userId, photo.getPostId(),
										photo.getTeamId(), photo.getPrivacyId(), date);
							}
							List<PostIncludeUser> oldUserInclude = new ArrayList<PostIncludeUser>();
							// save post include user : privacy is include or public
							if (listChild.get(j).getUserInclude() != null
									&& !listChild.get(j).getUserInclude().isEmpty()
									&& (privacyInclude.compareTo(photo.getPrivacyId()) == 0
											|| privacyPublic.compareTo(photo.getPrivacyId()) == 0)) {
								postService.savePostIncludeUser(listChild.get(j).getUserInclude(), userId,
										photo.getPostId(), date,
										ConstantUtils.Post_Include_User_Type.PRIVACY.getValue(), null, oldUserInclude);
							}
							// save post tag user : tag
							if (listChild.get(j).getUserTag() != null && !listChild.get(j).getUserTag().isEmpty()) {
								postService.savePostIncludeUser(listChild.get(j).getUserTag(), userId,
										photo.getPostId(), date,
										ConstantUtils.Post_Include_User_Type.TAG_POST.getValue(), null, oldUserInclude);
							}
						}

						PostAlbumDTO postDTO = convertToDTO(photo, userId);
						listChildDTO.add(postDTO);
						if (dto.getHashTag() != null && !dto.getHashTag().isEmpty()) {
							// save new hash tag of post
							postService.saveHastag(dto.getHashTag(), userId, postAlbum.getPostId(),
									postAlbum.getTeamId(), postAlbum.getPrivacyId(), date);
						}
						List<PostIncludeUser> oldUserInclude = new ArrayList<PostIncludeUser>();
						// save post include user : privacy is include or public
						if (dto.getUserInclude() != null && !dto.getUserInclude().isEmpty()
								&& (privacyInclude.compareTo(postAlbum.getPrivacyId()) == 0
										|| privacyPublic.compareTo(postAlbum.getPrivacyId()) == 0)) {
							postService.savePostIncludeUser(dto.getUserInclude(), userId, postAlbum.getPostId(), date,
									ConstantUtils.Post_Include_User_Type.PRIVACY.getValue(), null, oldUserInclude);
						}
						// save post tag user : tag
						if (dto.getUserTag() != null && !dto.getUserTag().isEmpty()) {
							postService.savePostIncludeUser(dto.getUserTag(), userId, postAlbum.getPostId(), date,
									ConstantUtils.Post_Include_User_Type.TAG_POST.getValue(), null, oldUserInclude);
						}
					}
				}
			}
			postAlbum.setUpdatedDate(new Date());
			postAlbum = postRepository.save(postAlbum);
		} else {
			if (postAlbum == null) {
				postParent.setCreatedDate(new Date());
				postParent.setUpdatedDate(new Date());
				postParent = postRepository.save(postParent);

				postAlbum = postParent;
			}
			for (int i = 0; i < files.size(); i++) {
				MultipartFile file = files.get(i);
				String fileType = FilenameUtils.getExtension(file.getOriginalFilename());
				if (lookupMimeType(fileType) == null) {
					respon.setSuccess(false);
					respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
					respon.setMessage(messageUtils.getMessage("E023"));
					return respon;
				}
				String fileNameAtServer = createFileNameAtServer(file.getOriginalFilename());
				String pathRoot = evn.getProperty("source.image.path").toString() + "/" + postAlbum.getTeamId()
						+ "/POST/";
				TeamFile teamFile = new TeamFile();
				List<TeamFolder> folder = teamFolderRepository.findByFolderPathAtServer(pathRoot);
				if (folder == null || folder.isEmpty()) {
					respon.setSuccess(false);
					respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
					respon.setMessage(messageUtils.getMessage("E001", "folder"));
					return respon;
				}
				// copy file to server
				saveFileToServer(file, pathRoot, fileNameAtServer);
				teamFile = saveFileInDB(file, folder.get(0).getFolderId(), postAlbum.getTeamId(), pathRoot, fileType,
						fileNameAtServer, userId);

				// save info post in team file
				Post photo = new Post();
				photo.setTeamId(postAlbum.getTeamId());
				photo.setPostParentId(postAlbum.getPostId());
				if (listChild != null && !listChild.isEmpty()) {
					photo.setPostContent(listChild.get(i).getPostContent());
					photo.setPostDescription(listChild.get(i).getPostDescription());
				}
				photo.setPostType(ConstantUtils.Post_Type.POST_PHOTO.getValue());
				photo.setPostMedia(teamFile.getFileId());
				photo.setPrivacyId(postAlbum.getPrivacyId());
				photo.setCreatedby(postAlbum.getCreatedby());
				photo.setCreatedDate(new Date());
				photo.setUpdatedDate(new Date());
				photo = postRepository.save(photo);

				if (listChild != null && !listChild.isEmpty()) {
					if (listChild.get(i).getHashTag() != null && !listChild.get(i).getHashTag().isEmpty()) {
						// save new hash tag of post
						postService.saveHastag(listChild.get(i).getHashTag(), userId, photo.getPostId(),
								photo.getTeamId(), photo.getPrivacyId(), date);
					}
					List<PostIncludeUser> oldUserInclude = new ArrayList<PostIncludeUser>();
					// save post include user : privacy is include or public
					if (listChild.get(i).getUserInclude() != null && !listChild.get(i).getUserInclude().isEmpty()
							&& (privacyInclude.compareTo(photo.getPrivacyId()) == 0
									|| privacyPublic.compareTo(photo.getPrivacyId()) == 0)) {
						postService.savePostIncludeUser(listChild.get(i).getUserInclude(), userId, photo.getPostId(),
								date, ConstantUtils.Post_Include_User_Type.PRIVACY.getValue(), null, oldUserInclude);
					}
					// save post tag user : tag
					if (listChild.get(i).getUserTag() != null && !listChild.get(i).getUserTag().isEmpty()) {
						postService.savePostIncludeUser(listChild.get(i).getUserTag(), userId, photo.getPostId(), date,
								ConstantUtils.Post_Include_User_Type.TAG_POST.getValue(), null, oldUserInclude);
					}
				}

				PostAlbumDTO postDTO = convertToDTO(photo, userId);
				listChildDTO.add(postDTO);
				if (postParent.getPostParentId() == null) {
					Path source = Paths.get(teamFile.getFilePathAtServer());
					if (source == null) {
						respon.setSuccess(false);
						respon.setMessage(messageUtils.getMessage("E001", "folder"));
						return respon;
					}
					try {
						Files.move(source, source.resolveSibling(pathRoot + "/"));
					} catch (Exception e) {
						respon.setSuccess(false);
						respon.setMessage(messageUtils.getMessage("E005", "folder"));
						return respon;
					}
				}

				if (dto.getHashTag() != null && !dto.getHashTag().isEmpty()) {
					// save new hash tag of post
					postService.saveHastag(dto.getHashTag(), userId, postAlbum.getPostId(), postAlbum.getTeamId(),
							postAlbum.getPrivacyId(), date);
				}
				List<PostIncludeUser> oldUserInclude = new ArrayList<PostIncludeUser>();
				// save post include user : privacy is include or public
				if (dto.getUserInclude() != null && !dto.getUserInclude().isEmpty()
						&& (privacyInclude.compareTo(postParent.getPrivacyId()) == 0
								|| privacyPublic.compareTo(postParent.getPrivacyId()) == 0)) {
					postService.savePostIncludeUser(dto.getUserInclude(), userId, postAlbum.getPostId(), date,
							ConstantUtils.Post_Include_User_Type.PRIVACY.getValue(), null, oldUserInclude);
				}
				// save post tag user : tag
				if (dto.getUserTag() != null && !dto.getUserTag().isEmpty()) {
					postService.savePostIncludeUser(dto.getUserTag(), userId, postAlbum.getPostId(), date,
							ConstantUtils.Post_Include_User_Type.TAG_POST.getValue(), null, oldUserInclude);
				}
				PostAlbumDTO postParentDTO = convertToDTO(postAlbum, userId);
				postParentDTO.setPostChild(listChildDTO);
				if (dto.getPostId() == null) {
					respon.setData(postParentDTO);
				}
			}
			postAlbum.setUpdatedDate(new Date());
			postAlbum = postRepository.save(postAlbum);
		}

		respon.setMessage(messageUtils.getMessage("I026", "photo"));
		return respon;
	}

	/**
	 * delete album
	 * 
	 * @param teamId
	 * @param postId
	 * @return
	 */
	public ApiResponse delPost(BigInteger teamId, BigInteger postId, BigInteger createdBy) throws Exception {
		ApiResponse respon = new ApiResponse();

		// Check creating is member in team
		TeamMember teamMember = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(teamId, createdBy, 1);
		if (teamMember == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E013"));
			return respon;
		}

		Post postPhoto = postRepository.findByPostIdAndIsActive(postId, 1);
		// post parent have 1 one photo
		if (postPhoto != null) {
			if (ConstantUtils.Post_Type.POST_PHOTO.getValue().equals(postPhoto.getPostType())) {

				// delete hash tag of post
				hashTagContainerRepository.deleteOldByPostAndTeam(postId, teamId);

				// delete post include user
				postIncludeUserRepository.deleteOldByPost(postId);

				// delete post like
				postLikeRepository.deleteOldByPost(postId);

				// delete post comment
				postCommentRepository.deleteOldByPost(postId);

				// delete post
				postRepository.delete(postPhoto);
				if (postPhoto.getPostParentId() != null) {
					int postLeft = postRepository.countPostLeftInAlbum(postPhoto.getPostParentId(), 1);
					if (postLeft == 0) {// not photo in album,delete album
						Post parent = postRepository.findByPostIdAndIsActive(postPhoto.getPostParentId(), 1);
						postRepository.delete(parent);
					}
				}
				respon.setMessage(messageUtils.getMessage("I002", "photo"));
			} else {
				List<PostAlbumDTO> listPostChild = removePostChild(postId, teamId);
				if (ConstantUtils.Post_Type.POST_ALBUM.getValue().equals(postPhoto.getPostType())) {
					respon.setMessage(messageUtils.getMessage("I002", "album"));
				} else {
					respon.setMessage(messageUtils.getMessage("I002", "post"));
				}
			}
		} else {
			respon.setMessage(messageUtils.getMessage("E001", "album"));
		}
		return respon;
	}

	/**
	 * 
	 * @param postId
	 * @param userId
	 * @return
	 */
	public ApiResponse getListPostInAlbum(BigInteger postId, BigInteger userId) {
		ApiResponse respon = new ApiResponse();
		Post postAlbum = postRepository.findByPostIdAndIsActive(postId, 1);
		if (postAlbum == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "album"));
			return respon;
		}
		PostAlbumDTO postAlbumDTO = convertToDTO(postAlbum, userId);
		List<Post> listParent = postRepository.getListPostParent(postId, 1);
		if (listParent == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "album"));
			return respon;
		}
		List<PostAlbumDTO> listDTO = new ArrayList<PostAlbumDTO>();
		for (Post obj : listParent) {
			Post postPhoto = postRepository.findByPostIdAndIsActive(obj.getPostId(), 1);
			// post parent have 1 one photo
			if (postPhoto != null && postId.equals(postPhoto.getPostParentId())
					&& ConstantUtils.Post_Type.POST_PHOTO.getValue().equals(postPhoto.getPostType())) {
				PostAlbumDTO dto = convertToDTO(postPhoto, userId);
				listDTO.add(dto);
			} else {
				List<Post> listPostChild = postRepository.getListPostByPostParent(obj.getPostId(), 1);
				if (listPostChild != null && !listPostChild.isEmpty()) {
					for (Post photo : listPostChild) {
						PostAlbumDTO dto = convertToDTO(photo, userId);
						listDTO.add(dto);
					}
				}
			}
		}
		postAlbumDTO.setPostChild(listDTO);
		respon.setData(postAlbumDTO);
		return respon;
	}

	/**
	 * 
	 * @param postId
	 * @param userId
	 * @return
	 */
	public ApiResponse getListPhotoInTeam(BigInteger teamId, BigInteger userId) {
		ApiResponse respon = new ApiResponse();
		List<PostAlbumDTO> listPostAlbumDTO = new ArrayList<PostAlbumDTO>();

		// return về user có thuộc team hay ko?
		TeamMember teamMember = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(teamId, userId, 1);

		// get post public by team id
		List<Post> listPhoto = new ArrayList<Post>();
		Collection<BigInteger> listPrivacy = new ArrayList<BigInteger>();
		if (teamMember != null) {// public or include
			listPrivacy = Arrays.asList(new BigInteger("1"), new BigInteger("2"));
			listPhoto = postRepository.getPostsInTeamId(teamId, "image/%", listPrivacy, userId, 1);
		} else {// only public
			listPrivacy = Arrays.asList(new BigInteger("1"));
			listPhoto = postRepository.getPostsInTeamId(teamId, "image/%", listPrivacy, null, 1);
		}
		if (listPhoto != null && !listPhoto.isEmpty()) {
			for (Post photo : listPhoto) {
				if (!isAlbum(photo)) {
					PostAlbumDTO dto = convertToDTO(photo, userId);
					listPostAlbumDTO.add(dto);
				}
			}
		}
		respon.setData(listPostAlbumDTO);
		return respon;
	}

	/**
	 * 
	 * @param postId
	 * @param userId
	 * @return
	 */
	public ApiResponse getListVideoInTeam(BigInteger teamId, BigInteger userId) {
		ApiResponse respon = new ApiResponse();
		List<PostAlbumDTO> listPostAlbumDTO = new ArrayList<PostAlbumDTO>();

		// return về user có thuộc team hay ko?
		TeamMember teamMember = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(teamId, userId, 1);

		// get post public by team id
		List<Post> listVideo = new ArrayList<Post>();
		Collection<BigInteger> listPrivacy = Arrays.asList(new BigInteger("1"), new BigInteger("2"));
		if (teamMember != null) {// public or include
			listVideo = postRepository.getPostsInTeamId(teamId, "video/%", listPrivacy, userId, 1);
		} else {// only public
			listVideo = postRepository.getPostsInTeamId(teamId, "video/%", listPrivacy, null, 1);
		}

		if (listVideo != null && !listVideo.isEmpty()) {
			for (Post video : listVideo) {
				PostAlbumDTO dto = convertToDTO(video, userId);
				listPostAlbumDTO.add(dto);
			}
		}
		respon.setData(listPostAlbumDTO);
		return respon;
	}

	public ApiResponse getListPostInTeam(BigInteger teamId, BigInteger userId) {
		ApiResponse respon = new ApiResponse();
		List<PostAlbumDTO> listPostAlbumDTO = new ArrayList<PostAlbumDTO>();

		// return về user có thuộc team hay ko?
		TeamMember teamMember = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(teamId, userId, 1);

		// get post public by team id
		List<Post> listPost = new ArrayList<Post>();
		Collection<BigInteger> listPrivacy = Arrays.asList(new BigInteger("1"), new BigInteger("2"));
		if (teamMember != null) {// public or include
			listPost = postRepository.getPostsByTeamId(teamId, ConstantUtils.Post_Type.POST_PHOTO.getValue(),
					listPrivacy, userId, 1);
		} else {// only public
			listPost = postRepository.getPostsByTeamId(teamId, ConstantUtils.Post_Type.POST_PHOTO.getValue(),
					listPrivacy, null, 1);
		}

		if (listPost != null && !listPost.isEmpty()) {
			for (Post post : listPost) {
				PostAlbumDTO dto = new PostAlbumDTO();
				if (post.getPostParentId() == null) {
					if (!ConstantUtils.Post_Type.POST_PHOTO.getValue().equals(post.getPostType())) {// post Parent
						List<PostAlbumDTO> listPostChildDTO = new ArrayList<PostAlbumDTO>();
						List<Post> listPostChild = postRepository.getPostsByPostParent(post.getPostId(), 1);
						if (listPostChild != null && !listPostChild.isEmpty()) {
							for (Post photo : listPostChild) {
								dto = convertToDTO(photo, userId);
								listPostChildDTO.add(dto);
							}
						}
						dto = convertToDTO(post, userId);
						dto.setPostChild(listPostChildDTO);
						listPostAlbumDTO.add(dto);
					} else {// post Photo
						dto = convertToDTO(post, userId);
						listPostAlbumDTO.add(dto);
					}
				}
			}
		}
		respon.setData(listPostAlbumDTO);
		return respon;
	}

	/**
	 * 
	 * @param postId
	 * @param userId
	 * @return
	 */
	public ApiResponse getListAlbumInTeam(BigInteger teamId, BigInteger userId) {
		ApiResponse respon = new ApiResponse();
		List<PostAlbumDTO> listPostAlbumDTO = new ArrayList<PostAlbumDTO>();

		// return về user có thuộc team hay ko?
		TeamMember teamMember = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(teamId, userId, 1);

		// get post public by team id
		List<Post> listAlbum = new ArrayList<Post>();
		Collection<BigInteger> listPrivacy = Arrays.asList(new BigInteger("1"), new BigInteger("2"));
		if (teamMember != null) {// public or include
			listAlbum = postRepository.getListPostByTeamId(teamId, ConstantUtils.Post_Type.POST_ALBUM.getValue(),
					listPrivacy, userId, 1);
		} else {// only public
			listAlbum = postRepository.getListPostByTeamId(teamId, ConstantUtils.Post_Type.POST_ALBUM.getValue(),
					listPrivacy, null, 1);
		}

		if (listAlbum != null && !listAlbum.isEmpty()) {
			for (Post post : listAlbum) {
				PostAlbumDTO postAlbumDTO = convertToDTO(post, userId);
				listPostAlbumDTO.add(postAlbumDTO);
			}
		}
		respon.setData(listPostAlbumDTO);
		return respon;
	}

	/**
	 * 
	 * @param postId
	 * @param userId
	 * @return
	 */
	public ApiResponse getDetailPost(BigInteger postId, BigInteger userId) {
		ApiResponse respon = new ApiResponse();
		// get album public by team id
		Post post = postRepository.findByPostIdAndIsActive(postId, 1);

		if (post == null) { // post not exist
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "photo"));
			return respon;
		}

		if (ConstantUtils.Post_Type.POST_ALBUM.getValue().equals(post.getPostType())) {
			PostAlbumDTO postAlbumDTO = convertToDTO(post, userId);
			List<PostAlbumDTO> listPostParentDTO = new ArrayList<PostAlbumDTO>();
			List<Post> listParent = postRepository.getListPostParent(post.getPostId(), 1);
			if (listParent != null && !listParent.isEmpty()) {
				for (Post postParent : listParent) {
					if (!ConstantUtils.Post_Type.POST_PHOTO.getValue().equals(postParent.getPostType())) {
						PostAlbumDTO postParentDTO = convertToDTO(postParent, userId);
						List<Post> listChild = postRepository.getListPostParent(postParent.getPostId(), 1);
						List<PostAlbumDTO> listPostChildDTO = new ArrayList<PostAlbumDTO>();
						if (listChild != null && !listChild.isEmpty()) {
							for (Post photo : listChild) {
								PostAlbumDTO postChildDTO = convertToDTO(photo, userId);
								listPostChildDTO.add(postChildDTO);
							}
						}
						postParentDTO.setPostChild(listPostChildDTO);
						listPostParentDTO.add(postParentDTO);
					} else {
						PostAlbumDTO postParentDTO = convertToDTO(postParent, userId);
						listPostParentDTO.add(postParentDTO);
					}
				}
			}
			postAlbumDTO.setPostChild(listPostParentDTO);
			respon.setData(postAlbumDTO);
		} else if (post.getPostParentId() == null
				&& !ConstantUtils.Post_Type.POST_PHOTO.getValue().equals(post.getPostType())) {// 1 photo in album
			PostAlbumDTO postParentDTO = convertToDTO(post, userId);
			List<PostAlbumDTO> listPostChildDTO = new ArrayList<PostAlbumDTO>();
			List<Post> listPhoto = postRepository.getListPostParent(post.getPostId(), 1);
			if (listPhoto != null && !listPhoto.isEmpty()) {
				for (Post photo : listPhoto) {
					PostAlbumDTO postChildDTO = convertToDTO(photo, userId);
					listPostChildDTO.add(postChildDTO);
				}
			}
			postParentDTO.setPostChild(listPostChildDTO);
			respon.setData(postParentDTO);
		} else {
			PostAlbumDTO postPhotoDTO = convertToDTO(post, userId);
			respon.setData(postPhotoDTO);
		}
		return respon;
	}

	public PostAlbumDTO convertToDTO(Post post, BigInteger curentLoginUserId) {

		TeamFile teamFile = teamFileRepository.findByFileIdAndIsActive(post.getPostMedia(), 1);
		Upload file = uploadRepository.findByUploadId(post.getPostMedia());

		PostAlbumDTO dto = new PostAlbumDTO();
		dto.setPostId(post.getPostId());
		dto.setPostParentId(post.getPostParentId());
		dto.setPostDescription(post.getPostDescription());
		dto.setTeamId(post.getTeamId());
		dto.setPostContent(post.getPostContent());
		dto.setLocation(post.getLocale());
		dto.setPostType(post.getPostType());
		if (teamFile != null) {
			String fileType = teamFile.getFileType().toLowerCase();
			String types = mimeTypeMapping.get(fileType);
			dto.setFileType(types.split("/")[0]);
		} else if (file != null) {
			String[] bits = file.getFileName().split("\\.");
			String fileType = bits[bits.length - 1].toLowerCase();
			String types = mimeTypeMapping.get(fileType);
			dto.setFileType(types.split("/")[0]);
		}
		dto.setPostMedia(post.getPostMedia());
		dto.setPrivacyId(post.getPrivacyId());
		dto.setPostContentFontSize(post.getPostContentFontSize());

		if (post.getCreatedby() != null) {
			User user = userRepository.findByUserIdAndIsActive(post.getCreatedby(), 1);
			if (user != null) {
				dto.setCreatedby(post.getCreatedby());
				dto.setCreatedByName(user.getUserFullName());
				dto.setCreatedByAvatar(user.getUserAvatar());
			}
		} else {
			dto.setCreatedByName("OldMember");
		}
		// hash tag
		List<String> hashTag = hashTagContainerRepository.getListHashTagByTeamIdAndPostId(post.getTeamId(),
				post.getPostId(), 1);
		dto.setHashTag(hashTag);

		// get list user included in post
		List<UserDTO> listUserDTO = new ArrayList<UserDTO>();
		List<Map<String, Object>> listUserInclude = postRepository.getListUserIncludePost(
				ConstantUtils.Post_Include_User_Type.PRIVACY.getValue(), // include
				post.getPostId(), 1);
		if (listUserInclude != null) {
			for (Map<String, Object> user : listUserInclude) {
				UserDTO userDTO = new UserDTO();
				userDTO.setUserId(new BigInteger(user.get("user_id").toString()));
				userDTO.setUserFullName(user.get("user_full_name").toString());
				if (!StringUtils.isEmpty(user.get("user_avatar").toString())) {
					userDTO.setUserAvatar(new BigInteger(user.get("user_avatar").toString()));
				}
				listUserDTO.add(userDTO);
			}
		}
		dto.setUserInclude(listUserDTO);

		// get list user included in post
		List<UserDTO> listUserTagDTO = new ArrayList<UserDTO>();
		List<Map<String, Object>> listUserTag = postRepository.getListUserIncludePost(
				ConstantUtils.Post_Include_User_Type.TAG_POST.getValue(), // include
				post.getPostId(), 1);
		if (listUserTag != null) {
			for (Map<String, Object> user : listUserTag) {
				UserDTO userDTO = new UserDTO();
				userDTO.setUserId(new BigInteger(user.get("user_id").toString()));
				userDTO.setUserFullName(user.get("user_full_name").toString());
				if (!StringUtils.isEmpty(user.get("user_avatar").toString())) {
					userDTO.setUserAvatar(new BigInteger(user.get("user_avatar").toString()));
				}
				listUserTagDTO.add(userDTO);
			}
		}
		dto.setUserTag(listUserTagDTO);
		dto.setNumberOfLikes(postLikeRepository.countPostLike(post.getPostId()));
		dto.setNumberOfComments(postCommentRepository.countPostComments(post.getPostId()));
		// check is liked with current user login
		List<PostLike> postLike = postLikeRepository
				.findAllByPostIdAndUserIdAndIsActiveAndPostCommentIdIsNull(dto.getPostId(), curentLoginUserId, 1);
		if (postLike != null && !postLike.isEmpty()) {
			dto.setIsLiked(1);
		}
		// count number of photo and video
		List<Post> listParent = postRepository.getListPostParent(post.getPostId(), 1);
		int numberOfPhotos = 0;
		int numberOfVideos = 0;
		if (listParent != null && !listParent.isEmpty()) {
			for (Post parent : listParent) {
				numberOfPhotos += postRepository.countPhotoInAlbum(parent.getPostId(), 1);
				numberOfVideos += postRepository.countVideoInAlbum(parent.getPostId(), 1);
			}
		}
		dto.setNumberOfPhotos(numberOfPhotos);
		dto.setNumberOfVideos(numberOfVideos);
		// get list comment of post
		dto.setListComment(postService.listPostComment(dto.getPostId(), curentLoginUserId));
		dto.setCreatedDate(post.getCreatedDate());
		dto.setUpdatedDate(post.getUpdatedDate());
		return dto;
	}

	/**
	 * 
	 * @Des convert product DTO to entity
	 * @param postDTO
	 * @return
	 */
	public Post convertToEntity(PostAlbumDTO postAlbumDTO) {
		Post post = new Post();
		if (postAlbumDTO.getPostId() != null) {
			post.setPostId(postAlbumDTO.getPostId());
		} else {
			post.setPostId(null);
		}
		post.setPostContent(postAlbumDTO.getPostContent());
		post.setPrivacyId(postAlbumDTO.getPrivacyId());
		post.setTeamId(postAlbumDTO.getTeamId());
		post.setPostDescription(postAlbumDTO.getPostDescription());
		post.setLocale(postAlbumDTO.getLocation());
		post.setCreatedby(postAlbumDTO.getCreatedby());
		return post;
	}

	/**
	 * 
	 * @Des get child by parent
	 * @param
	 * @return
	 */
	public List<PostAlbumDTO> removePostChild(BigInteger parentPostId, BigInteger teamId) throws Exception {
		Post post = postRepository.findByPostIdAndIsActive(parentPostId, 1);
		if (post == null) {
			return null;
		}

		postRepository.deleteById(parentPostId);

		// delete hash tag of post
		hashTagContainerRepository.deleteOldByPostAndTeam(parentPostId, teamId);

		// delete post include user
		postIncludeUserRepository.deleteOldByPost(parentPostId);

		// delete post like
		postLikeRepository.deleteOldByPost(parentPostId);

		// delete post comment
		postCommentRepository.deleteOldByPost(parentPostId);

		// find category child
		List<Post> listPost = postRepository.findAllByPostParentId(parentPostId);
		if (listPost == null || listPost.isEmpty()) {
			return null;
		}
		List<PostAlbumDTO> testPost = new ArrayList<PostAlbumDTO>();
		for (Post obj : listPost) {
			PostAlbumDTO test = new PostAlbumDTO();
			test.setPostId(obj.getPostId());
			// de qui
			test.setPostChild(this.removePostChild(obj.getPostId(), obj.getTeamId()));
			testPost.add(test);
		}
		return testPost;
	}

	public boolean isAlbum(Post post) {
		Post photoParent = postRepository.findByPostIdAndIsActive(post.getPostParentId(), 1);
		if (photoParent != null) {
			if (!ConstantUtils.Post_Type.POST_ALBUM.getValue().equals(photoParent.getPostType())) {
				Post photoAlbum = postRepository.findByPostIdAndIsActive(photoParent.getPostParentId(), 1);
				if (photoAlbum != null
						&& ConstantUtils.Post_Type.POST_ALBUM.getValue().equals(photoAlbum.getPostType())) {
					return true;
				}
			} else {
				return true;
			}
		}
		return false;
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
