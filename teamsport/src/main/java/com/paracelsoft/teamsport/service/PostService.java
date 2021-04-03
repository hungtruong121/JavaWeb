package com.paracelsoft.teamsport.service;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paracelsoft.teamsport.api.dto.PostAlbumDTO;
import com.paracelsoft.teamsport.api.dto.PostCommentDTO;
import com.paracelsoft.teamsport.api.dto.PostDTO;
import com.paracelsoft.teamsport.api.dto.PostLikeDTO;
import com.paracelsoft.teamsport.api.dto.PrivacyDTO;
import com.paracelsoft.teamsport.api.dto.UserDTO;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.entity.HashtagContainer;
import com.paracelsoft.teamsport.entity.Post;
import com.paracelsoft.teamsport.entity.PostComment;
import com.paracelsoft.teamsport.entity.PostIncludeUser;
import com.paracelsoft.teamsport.entity.PostLike;
import com.paracelsoft.teamsport.entity.Privacy;
import com.paracelsoft.teamsport.entity.TeamMember;
import com.paracelsoft.teamsport.entity.User;
import com.paracelsoft.teamsport.repository.HashTagContainerRepository;
import com.paracelsoft.teamsport.repository.PostCommentRepository;
import com.paracelsoft.teamsport.repository.PostIncludeUserRepository;
import com.paracelsoft.teamsport.repository.PostLikeRepository;
import com.paracelsoft.teamsport.repository.PostRepository;
import com.paracelsoft.teamsport.repository.PrivacyRepository;
import com.paracelsoft.teamsport.repository.TeamMemberRepository;
import com.paracelsoft.teamsport.repository.UserRepository;
import com.paracelsoft.teamsport.util.ConstantUtils;
import com.paracelsoft.teamsport.util.DateUtil;
import com.paracelsoft.teamsport.util.MessageUtils;

@Transactional(rollbackFor = { Exception.class, ParseException.class })
@Service("postService")
public class PostService {

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
	UploadService uploadService;

	@Autowired
	PostAlbumService postAlbumService;

	@Autowired
	NotificationService notificationService;

	/**
	 * 
	 * @param no prams
	 * @return List<PrivacyDTO>
	 */
	public List<PrivacyDTO> getListPrivacy() {
		List<PrivacyDTO> result = new ArrayList<PrivacyDTO>();

		List<Privacy> entity = privacyRepository.findAllByIsActive(1);
		if (entity != null && !entity.isEmpty()) {
			for (Privacy item : entity) {
				PrivacyDTO dto = new PrivacyDTO();
				dto.setPrivacyId(item.getPrivacyId());
				dto.setPrivacyName(item.getPrivacyName());
				result.add(dto);
			}
		}
		return result;
	}

	/**
	 * 
	 * @des create or update post
	 * @param postDTO
	 * @return
	 * @throws JSONException
	 * @throws NoSuchMessageException
	 */
	public ApiResponse updatePost(PostDTO postDTO, BigInteger createdBy) throws NoSuchMessageException, JSONException {
		ApiResponse respon = new ApiResponse();
		Post post = new Post();
		Date date = new Date();
		BigInteger privacyInclude = new BigInteger(ConstantUtils.Privacy.INCLUDE.getValue());
		BigInteger privacyPublic = new BigInteger(ConstantUtils.Privacy.PUBLIC.getValue());
		List<PostIncludeUser> oldUserInclude = new ArrayList<PostIncludeUser>();
		if (postDTO.getPostId() != null) { // is updated
			Post oldPost = postRepository.findByPostIdAndIsActive(postDTO.getPostId(), 1);
			if (oldPost == null) { // post not exist
				respon.setSuccess(false);
				respon.setMessage(messageUtils.getMessage("E001", "post"));
				return respon;
			}

			// delete old hash tag of post
			hashTagContainerRepository.deleteOldByPostAndTeam(oldPost.getPostId(), oldPost.getTeamId());

			// get old include user
//			oldUserInclude  = postIncludeUserRepository.findByPostIdAndUserIdAndPostIncludeUseTypeAndIsActiveAndPostCommentIdIsNull(
//					oldPost.getPostId(), createdBy, ConstantUtils.Post_Include_User_Type.PRIVACY.getValue(), 1);
//			
//			//if old is include or public
//			if(oldUserInclude != null && !oldUserInclude.isEmpty() && privacyInclude.compareTo(oldPost.getPrivacyId()) == 0
//					|| privacyPublic.compareTo(oldPost.getPrivacyId()) == 0) {
			// delete old post include user
			postIncludeUserRepository.deleteOldByPostAndType(oldPost.getPostId(),
					ConstantUtils.Post_Include_User_Type.PRIVACY.getValue());
//			}

			// delete old post tag user
			postIncludeUserRepository.deleteOldByPostAndType(oldPost.getPostId(),
					ConstantUtils.Post_Include_User_Type.TAG_POST.getValue());

			// update
			post = this.convertToEntity(postDTO);
			post.setCreatedby(oldPost.getCreatedby()); // no change
			post.setCreatedDate(oldPost.getCreatedDate()); // no change
			post.setUpdatedDate(date);
		} else {

			post = this.convertToEntity(postDTO);
			post.setCreatedby(createdBy);
			post.setCreatedDate(date);
			post.setUpdatedDate(date);
		}
		post = postRepository.save(post);

		if (postDTO.getHashTag() != null && !postDTO.getHashTag().isEmpty()) {
			// save new hash tag of post
			this.saveHastag(postDTO.getHashTag(), createdBy, post.getPostId(), post.getTeamId(), post.getPrivacyId(),
					date);
		}

		// save post include user : privacy is include or public
		if (postDTO.getUserInclude() != null && !postDTO.getUserInclude().isEmpty()
				&& (privacyInclude.compareTo(postDTO.getPrivacyId()) == 0
						|| privacyPublic.compareTo(postDTO.getPrivacyId()) == 0)) {
			this.savePostIncludeUser(postDTO.getUserInclude(), createdBy, post.getPostId(), date,
					ConstantUtils.Post_Include_User_Type.PRIVACY.getValue(), null, oldUserInclude);
		}

		// save post tag user : tag
		if (postDTO.getUserTag() != null && !postDTO.getUserTag().isEmpty()) {
			this.savePostIncludeUser(postDTO.getUserTag(), createdBy, post.getPostId(), date,
					ConstantUtils.Post_Include_User_Type.TAG_POST.getValue(), null, oldUserInclude);
		}

		respon.setData(post);
		return respon;
	}

	/**
	 * 
	 * @Des convert product DTO to entity
	 * @param postDTO
	 * @return
	 */
	public Post convertToEntity(PostDTO postDTO) {
		Post post = new Post();
		post.setTeamId(postDTO.getTeamId());
		post.setPostMedia(postDTO.getPostMedia());
		post.setBackgroundColor(postDTO.getBackgroundColor());
		post.setPostContent(postDTO.getPostContent());
		post.setPostContentFontSize(postDTO.getPostContentFontSize());
		post.setPostType(postDTO.getPostType());
		post.setPostId(postDTO.getPostId());
		post.setBackgroundImage(postDTO.getBackgroundImage());
		post.setBackgroundDefault(postDTO.getBackgroundDefault());
		post.setPrivacyId(postDTO.getPrivacyId());
		return post;
	}

	/**
	 * 
	 * @Des save list hash tag to container
	 * @param hashTag
	 */
	public void saveHastag(List<String> hashTag, BigInteger createdBy, BigInteger postId, BigInteger teamId,
			BigInteger privacyId, Date toDate) {
		List<HashtagContainer> entitys = new ArrayList<>();
		for (String tag : hashTag) {
			HashtagContainer entity = new HashtagContainer();
			entity.setHashtagContainerId(null);
			entity.setCreatedby(createdBy);
			entity.setPostId(postId);
			entity.setHashtagContainerValue(tag);
			entity.setPrivacyId(privacyId);
			entity.setTeamId(teamId);
			entity.setCreatedDate(toDate);
			entity.setUpdatedDate(toDate);
			entitys.add(entity);
		}
		hashTagContainerRepository.saveAll(entitys);
	}

	public void savePostIncludeUser(List<UserDTO> users, BigInteger createdBy, BigInteger postId, Date toDate,
			String includeType, BigInteger commentId, List<PostIncludeUser> oldInclude)
			throws NoSuchMessageException, JSONException {
		List<PostIncludeUser> entitys = new ArrayList<>();
		for (UserDTO user : users) {
			PostIncludeUser entity = new PostIncludeUser();
			entity.setPostIncludeUserId(null);
			entity.setPostIncludeUseType(includeType);
			entity.setPostCommentId(commentId);
			entity.setCreatedby(createdBy);
			entity.setPostId(postId);
			entity.setUserId(user.getUserId());
			entity.setCreatedDate(toDate);
			entity.setUpdatedDate(toDate);
			entitys.add(entity);
			if (!IsOldUserInclude(user.getUserId(), oldInclude)) {
				if (commentId == null) { // messs include post
					// save notification to all admin of team
					notificationService.notificationFromUserToPost(createdBy, user.getUserId(), "N003", postId,
							ConstantUtils.MobileScreen.MENU_POST.getDescription(),
							ConstantUtils.Notification_Type.NOTIFI_MESS.getValue());
				} else {// mess tag comment
						// save notification to all admin of team
					notificationService.notificationFromUserToComment(createdBy, user.getUserId(), "N004", postId,
							commentId, ConstantUtils.MobileScreen.MENU_POST.getDescription(),
							ConstantUtils.Notification_Type.NOTIFI_MESS.getValue());
				}
			}
		}
		postIncludeUserRepository.saveAll(entitys);
	}

	/**
	 * 
	 * @param userId
	 * @param oldInclude
	 * @return
	 */
	private boolean IsOldUserInclude(BigInteger userId, List<PostIncludeUser> oldInclude) {
		if (oldInclude != null && oldInclude.isEmpty()) {
			for (PostIncludeUser item : oldInclude) {
				if (userId.compareTo(item.getUserId()) == 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * @param teamId
	 * @param postId
	 * @return
	 */
	public ApiResponse delPost(BigInteger teamId, BigInteger postId) {
		ApiResponse respon = new ApiResponse();
		Post oldPost = postRepository.findByPostIdAndIsActive(postId, 1);
		if (oldPost == null) { // post not exist
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "post"));
			return respon;
		}

		// delete hash tag of post
		hashTagContainerRepository.deleteOldByPostAndTeam(postId, teamId);

		// delete post include user
		postIncludeUserRepository.deleteOldByPost(postId);

		// delete post like
		postLikeRepository.deleteOldByPost(postId);

		// delete post comment
		postCommentRepository.deleteOldByPost(postId);

		// delete post
		postRepository.delete(oldPost);

		respon.setMessage(messageUtils.getMessage("I002", "post"));
		return respon;
	}

	/**
	 * 
	 * @Des search hashtag by team, user, keyword
	 * @param teamId
	 * @param userId
	 * @param keyword
	 * @return
	 */
	public ApiResponse searchHashtag(BigInteger teamId, String keyword) {
		ApiResponse respon = new ApiResponse();
		String detectKeyword = "%%";
		if (!StringUtils.isEmpty(keyword)) {
			detectKeyword = "%" + keyword + "%";
		}
		// find hash tag by user id, team id, hash tag
		List<String> hashTag = hashTagContainerRepository.searchPublicByTeamAndKeyWordAndIsActive(
				new BigInteger(ConstantUtils.Privacy.PUBLIC.getValue()), teamId, detectKeyword, 1);
		if (hashTag == null) {
			hashTag = new ArrayList<>();
		}
		respon.setData(hashTag);
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
	public ApiResponse updatePrivacyPost(PostDTO postDTO, BigInteger userId)
			throws NoSuchMessageException, JSONException {
		ApiResponse respon = new ApiResponse();

		Post oldPost = postRepository.findByPostIdAndIsActive(postDTO.getPostId(), 1);
		if (oldPost == null) { // post not exist
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "post"));
			return respon;
		}

		BigInteger privacyInclude = new BigInteger(ConstantUtils.Privacy.INCLUDE.getValue());
		BigInteger privacyPublic = new BigInteger(ConstantUtils.Privacy.PUBLIC.getValue());

		// if old is include
		List<PostIncludeUser> oldUserInclude = new ArrayList<PostIncludeUser>();
		// if old is include
		if (privacyInclude.compareTo(oldPost.getPrivacyId()) == 0
				|| privacyPublic.compareTo(oldPost.getPrivacyId()) == 0) {
			// get old include user
			oldUserInclude = postIncludeUserRepository
					.findByPostIdAndUserIdAndPostIncludeUseTypeAndIsActiveAndPostCommentIdIsNull(oldPost.getPostId(),
							userId, ConstantUtils.Post_Include_User_Type.PRIVACY.getValue(), 1);

			// delete old post include user
			postIncludeUserRepository.deleteOldByPostAndType(oldPost.getPostId(),
					ConstantUtils.Post_Include_User_Type.PRIVACY.getValue());

			// delete old post tag user
			postIncludeUserRepository.deleteOldByPostAndType(oldPost.getPostId(),
					ConstantUtils.Post_Include_User_Type.TAG_POST.getValue());
		}

		// if new is include, check include user and save new include user
		if ((privacyInclude.compareTo(oldPost.getPrivacyId()) == 0
				|| privacyPublic.compareTo(oldPost.getPrivacyId()) == 0) && postDTO.getUserInclude() != null
				&& !postDTO.getUserInclude().isEmpty()) {
			this.savePostIncludeUser(postDTO.getUserInclude(), userId, postDTO.getPostId(), new Date(),
					ConstantUtils.Post_Include_User_Type.PRIVACY.getValue(), null, oldUserInclude);
		}

		// save new post privacy
		oldPost.setPrivacyId(postDTO.getPrivacyId());
		postRepository.save(oldPost);
		return respon;
	}

	/**
	 * 
	 * @param teamId
	 * @param postId
	 * @return
	 */
	public ApiResponse getPostPublic(BigInteger teamId, BigInteger userId) {
		ApiResponse respon = new ApiResponse();

		List<PostDTO> listPostDTO = new ArrayList<PostDTO>();

		// return về user có thuộc team hay ko?
		TeamMember teamMember = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(teamId, userId, 1);

		// get post public by team id
		List<Post> listPost = new ArrayList<Post>();
		if (teamMember != null) {// public or include
			listPost = postRepository.getListPostByTeamId(teamId,
					new BigInteger(ConstantUtils.Privacy.PUBLIC.getValue()), userId, 1);
		} else {// only public
			listPost = postRepository.getListPostByTeamId(teamId,
					new BigInteger(ConstantUtils.Privacy.PUBLIC.getValue()), null, 1);
		}

		if (listPost != null && !listPost.isEmpty()) {
			for (Post post : listPost) {
				PostAlbumDTO dto = new PostAlbumDTO();
				if (ConstantUtils.Post_Type.POST_NORMAL.getValue().equals(post.getPostType())) {
					PostDTO postDTO = convertToDTO(post, userId);
					listPostDTO.add(postDTO);
				} else if (!ConstantUtils.Post_Type.POST_PHOTO.getValue().equals(post.getPostType())) {
					List<PostAlbumDTO> listPostChildDTO = new ArrayList<PostAlbumDTO>();
					List<Post> listPostChild = new ArrayList<Post>();
					if(ConstantUtils.Post_Type.POST_ALBUM.getValue().equals(post.getPostType())) {
						listPostChild = postRepository.getListPostByPostParent(post.getPostId(), 1);
					}else {
						listPostChild = postRepository.getPostsByPostParent(post.getPostId(), 1);
					}
					if (listPostChild != null && !listPostChild.isEmpty()) {
						for (Post photo : listPostChild) {
							dto = postAlbumService.convertToDTO(photo, userId);
							listPostChildDTO.add(dto);
						}
					}
					PostDTO postDTO = convertToDTO(post, userId);
					postDTO.setPostChild(listPostChildDTO);
					listPostDTO.add(postDTO);
				} else {
					if(post.getPostParentId() == null && !ConstantUtils.Post_Type.POST_ALBUM.getValue().equals(post.getPostType())) {// post Photo
						PostDTO postDTO = convertToDTO(post, userId);
						listPostDTO.add(postDTO);
					}
				}
			}
		}
		respon.setData(listPostDTO);
		return respon;
	}

	public PostDTO convertToDTO(Post post, BigInteger curentLoginUserId) {
		PostDTO dto = new PostDTO();
		dto.setPostId(post.getPostId());
		dto.setTeamId(post.getTeamId());
		dto.setPostContent(post.getPostContent());
		dto.setPostType(post.getPostType());
		dto.setPostMedia(post.getPostMedia());
		dto.setLocation(post.getLocale());
		dto.setPrivacyId(post.getPrivacyId());
		dto.setBackgroundColor(post.getBackgroundColor());
		dto.setPostContentFontSize(post.getPostContentFontSize());
		dto.setBackgroundDefault(post.getBackgroundDefault());
		dto.setBackgroundImage(post.getBackgroundImage());

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
		dto.setTimeAgo(DateUtil.getDiffDatetime(post.getCreatedDate()));

		// check is liked with current user login
		List<PostLike> postLike = postLikeRepository
				.findAllByPostIdAndUserIdAndIsActiveAndPostCommentIdIsNull(dto.getPostId(), curentLoginUserId, 1);
		if (postLike != null && !postLike.isEmpty()) {
			dto.setIsLiked(1);
		}

		// get list comment of post
		dto.setListComment(this.listPostComment(dto.getPostId(), curentLoginUserId));

		dto.setCreatedDate(post.getCreatedDate());
		dto.setUpdatedDate(post.getUpdatedDate());
		return dto;

	}

	/**
	 * 
	 * @Des like or dislike
	 * @param postLikeDTO
	 * @param userId
	 * @return
	 */
	public ApiResponse likePostOrComment(PostLikeDTO postLikeDTO, BigInteger userId)
			throws NoSuchMessageException, JSONException {
		ApiResponse respon = new ApiResponse();
		List<PostLike> oldPostLike = new ArrayList<PostLike>();

		// get Post by Id
		Post post = postRepository.findByPostIdAndIsActive(postLikeDTO.getPostId(), 1);
		if (post == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
			return respon;
		}

		if (postLikeDTO.getPostCommentId() != null) { // like comment
			oldPostLike = postLikeRepository.findAllByPostIdAndUserIdAndPostCommentIdAndIsActive(
					postLikeDTO.getPostId(), userId, postLikeDTO.getPostCommentId(), 1);
		} else {// like post
			oldPostLike = postLikeRepository
					.findAllByPostIdAndUserIdAndIsActiveAndPostCommentIdIsNull(postLikeDTO.getPostId(), userId, 1);
		}
		// neu ton lai thi dislike, delete all
		if (oldPostLike != null && !oldPostLike.isEmpty()) {
			postLikeRepository.deleteAll(oldPostLike);
			return respon;
		}
		// created new like record
		PostLike entity = new PostLike();
		entity.setPostLikeId(null);
		entity.setPostId(postLikeDTO.getPostId());
		entity.setPostCommentId(postLikeDTO.getPostCommentId());
		entity.setIsActive(1);
		entity.setUserId(userId);
		entity.setCreatedDate(new Date());
		entity.setUpdatedDate(new Date());
		entity = postLikeRepository.save(entity);

		// notification
		if (postLikeDTO.getPostCommentId() == null) {// like post
			if (userId.compareTo(post.getCreatedby()) != 0) {
				// save notification to all admin of team
				notificationService.notificationFromUserToPost(userId, post.getCreatedby(), "N005",
						postLikeDTO.getPostId(), ConstantUtils.MobileScreen.MENU_POST.getDescription(),
						ConstantUtils.Notification_Type.NOTIFI_MESS.getValue());
			}
		} else {// like comment
				// get comment
			PostComment postComment = postCommentRepository
					.findByPostCommentIdAndIsActive(postLikeDTO.getPostCommentId(), 1);
			if (postComment == null) {
				respon.setSuccess(false);
				respon.setMessage(messageUtils.getMessage("E003"));
				return respon;
			}
			if (userId.compareTo(postComment.getUserId()) != 0) {
				// save notification to all admin of team
				notificationService.notificationFromUserToComment(userId, post.getCreatedby(), "N006",
						postLikeDTO.getPostId(), postLikeDTO.getPostCommentId(),
						ConstantUtils.MobileScreen.MENU_POST.getDescription(),
						ConstantUtils.Notification_Type.NOTIFI_MESS.getValue());
			}
		}

		respon.setData(entity);
		return respon;
	}

	/**
	 * 
	 * @param postCommentDTO
	 * @param userId
	 * @param createdDate
	 * @param updatedDate
	 * @return
	 */
	private PostComment convertToEntity(PostCommentDTO postCommentDTO, BigInteger userId, Date createdDate,
			Date updatedDate) {
		PostComment postComment = new PostComment();
		postComment.setPostCommentId(postCommentDTO.getPostCommentId());
		postComment.setGif_id(postCommentDTO.getGif_id());
		postComment.setIsActive(1);
		postComment.setPostCommentContent(postCommentDTO.getPostCommentContent());
		postComment.setPostCommentImage(postCommentDTO.getPostCommentImage());
		postComment.setPostCommentParentId(postCommentDTO.getPostCommentParentId());
		postComment.setPostId(postCommentDTO.getPostId());
		postComment.setUserId(userId);
		postComment.setCreatedDate(createdDate);
		postComment.setUpdatedDate(updatedDate);
		return postComment;
	}

	/**
	 * 
	 * @param postCommentDTO
	 * @return
	 */
	public ApiResponse deleteComment(PostCommentDTO postCommentDTO) {
		ApiResponse respon = new ApiResponse();
		PostComment oldPostComment = postCommentRepository
				.findByPostCommentIdAndIsActive(postCommentDTO.getPostCommentId(), 1);
		if (oldPostComment == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "comment"));
			return respon;
		}
		// delete child post comment
		postCommentRepository.deleteOldbyPostCommentParent(oldPostComment.getPostCommentId());

		// delete post comment like
		postLikeRepository.deleteOldbyPostComment(oldPostComment.getPostCommentId());

		// delete post comment include
		postIncludeUserRepository.deleteOldByPostCommentId(oldPostComment.getPostCommentId());

		// delete post comment
		postCommentRepository.delete(oldPostComment);
		respon.setMessage(messageUtils.getMessage("I002", "comment"));
		return respon;
	}

	public ApiResponse addComment(PostCommentDTO postCommentDTO, BigInteger userId) throws Exception {
		ApiResponse respon = new ApiResponse();
		PostComment entity = new PostComment();
		// update
		if (postCommentDTO.getPostCommentId() != null) {
			PostComment oldPostComment = postCommentRepository
					.findByPostCommentIdAndIsActive(postCommentDTO.getPostCommentId(), 1);
			if (oldPostComment == null) {
				respon.setSuccess(false);
				respon.setMessage(messageUtils.getMessage("E001", "comment"));
				return respon;
			}
			entity = this.convertToEntity(postCommentDTO, userId, oldPostComment.getCreatedDate(), new Date());

			// delete post comment include
			postIncludeUserRepository.deleteOldByPostCommentId(oldPostComment.getPostCommentId());

		} else {// add new
			entity = this.convertToEntity(postCommentDTO, userId, new Date(), new Date());
			// notifi reply comment
			if (postCommentDTO.getPostCommentParentId() != null) {
				// notifi comment post
				PostComment postcomment = postCommentRepository
						.findByPostCommentIdAndIsActive(postCommentDTO.getPostCommentParentId(), 1);
				if (postcomment == null) {
					respon.setSuccess(false);
					respon.setMessage(messageUtils.getMessage("E003"));
					return respon;
				}
				if (userId.compareTo(postcomment.getUserId()) != 0) {
					// save notification to all admin of team
					notificationService.notificationFromUserToComment(userId, postcomment.getUserId(), "N008",
							postCommentDTO.getPostId(), postCommentDTO.getPostCommentParentId(),
							ConstantUtils.MobileScreen.MENU_POST.getDescription(),
							ConstantUtils.Notification_Type.NOTIFI_MESS.getValue());
				}

			} else {// comment post
					// notifi comment post
				Post post = postRepository.findByPostIdAndIsActive(postCommentDTO.getPostId(), 1);
				if (post == null) {
					respon.setSuccess(false);
					respon.setMessage(messageUtils.getMessage("E003"));
					return respon;
				}

				if (userId.compareTo(post.getCreatedby()) != 0) {
					// save notification to all admin of team
					notificationService.notificationFromUserToPost(userId, post.getCreatedby(), "N007",
							postCommentDTO.getPostId(), ConstantUtils.MobileScreen.MENU_POST.getDescription(),
							ConstantUtils.Notification_Type.NOTIFI_MESS.getValue());
				}
			}
		}

		entity = postCommentRepository.save(entity);
		// save post comment include user : tag in comment
		if (postCommentDTO.getUserInclude() != null && !postCommentDTO.getUserInclude().isEmpty()) {
			this.savePostIncludeUser(postCommentDTO.getUserInclude(), userId, postCommentDTO.getPostId(), new Date(),
					ConstantUtils.Post_Include_User_Type.TAG_COMMENT.getValue(), entity.getPostCommentId(), null);
		}
		respon.setData(entity);
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

		Post oldPost = postRepository.findByPostIdAndIsActive(postId, 1);
		if (oldPost == null) { // post not exist
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "post"));
			return respon;
		}
		PostDTO postDTO = convertToDTO(oldPost, userId);
		respon.setData(postDTO);
		return respon;
	}

	/**
	 * 
	 * @param postId
	 * @param curretnUserLoginId
	 * @return
	 */
	public List<PostCommentDTO> listPostComment(BigInteger postId, BigInteger curretnUserLoginId) {
		List<PostCommentDTO> result = new ArrayList<PostCommentDTO>();
		// get list post comment c1 by post id
		List<PostComment> listComment = postCommentRepository
				.findAllByPostIdAndIsActiveAndPostCommentParentIdIsNullOrderByCreatedDateAsc(postId, 1);
		if (listComment == null || listComment.isEmpty()) {
			return result;
		}
		for (PostComment item : listComment) {
			PostCommentDTO dtoParent = this.convertToDTO(item, curretnUserLoginId);
			List<PostComment> childComment = postCommentRepository
					.findAllByPostIdAndPostCommentParentIdAndIsActiveOrderByCreatedDateAsc(postId,
							item.getPostCommentId(), 1);
			if (childComment != null && !childComment.isEmpty()) {
				List<PostCommentDTO> dtoChilds = new ArrayList<PostCommentDTO>();
				for (PostComment child : childComment) {
					PostCommentDTO dtoChil = this.convertToDTO(child, curretnUserLoginId);
					dtoChilds.add(dtoChil);
				}
				dtoParent.setChildComment(dtoChilds);
			}
			result.add(dtoParent);
		}
		return result;
	}

	/**
	 * 
	 * @param postComment
	 * @param curretnUserLoginId
	 * @return
	 */
	public PostCommentDTO convertToDTO(PostComment postComment, BigInteger curretnUserLoginId) {
		PostCommentDTO dto = new PostCommentDTO();
		dto.setPostCommentId(postComment.getPostCommentId());
		dto.setPostCommentContent(postComment.getPostCommentContent());
		dto.setPostCommentImage(postComment.getPostCommentImage());
		dto.setPostCommentParentId(postComment.getPostCommentParentId());
		dto.setPostId(postComment.getPostId());
		dto.setUserId(postComment.getUserId());
		User userCreated = userRepository.findByUserId(postComment.getUserId());
		if (userCreated != null) {
			dto.setCreatedByName(userCreated.getUserFullName());
			dto.setCreatedByAvatar(userCreated.getUserAvatar());
		}
		dto.setGif_id(postComment.getGif_id());
		dto.setIsActive(postComment.getIsActive());
		dto.setCreatedDate(postComment.getCreatedDate());
		dto.setUpdatedDate(postComment.getUpdatedDate());
		// check is liked with current user login
		List<PostLike> postLike = postLikeRepository.findAllByPostIdAndUserIdAndPostCommentIdAndIsActive(
				dto.getPostId(), curretnUserLoginId, dto.getPostCommentId(), 1);
		if (postLike != null && !postLike.isEmpty()) {
			dto.setIsLiked(1);
		}

		// get user include in comment
		List<Map<String, Object>> listUserInclude = postRepository.getListUserIncludeComment(dto.getPostCommentId(),
				ConstantUtils.Post_Include_User_Type.TAG_COMMENT.getValue(), // include
				dto.getPostId(), 1);
		if (listUserInclude != null) {
			List<UserDTO> listUserDTO = new ArrayList<UserDTO>();
			for (Map<String, Object> user : listUserInclude) {
				UserDTO userDTO = new UserDTO();
				userDTO.setUserId(new BigInteger(user.get("user_id").toString()));
				userDTO.setUserFullName(user.get("user_full_name").toString());
				if (!StringUtils.isEmpty(user.get("user_avatar").toString())) {
					userDTO.setUserAvatar(new BigInteger(user.get("user_avatar").toString()));
				}
				listUserDTO.add(userDTO);
			}
			dto.setUserInclude(listUserDTO);
		}

		// check post like
		dto.setNumberOfLikes(postLikeRepository.countLikePostComment(postComment.getPostCommentId(), 1));
		dto.setNumberOfComments(postCommentRepository.countCommentOfComment(postComment.getPostCommentId(), 1));
		return dto;
	}

}
