package com.paracelsoft.teamsport.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.google.protobuf.TextFormat.ParseException;
import com.paracelsoft.teamsport.api.dto.SurveyDTO;
import com.paracelsoft.teamsport.api.dto.UploadDTO;
import com.paracelsoft.teamsport.api.dto.UserDTO;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.entity.HashtagContainer;
import com.paracelsoft.teamsport.entity.Post;
import com.paracelsoft.teamsport.entity.PostIncludeUser;
import com.paracelsoft.teamsport.entity.PostSurveyAns;
import com.paracelsoft.teamsport.entity.PostSurveyVote;
import com.paracelsoft.teamsport.entity.Upload;
import com.paracelsoft.teamsport.entity.User;
import com.paracelsoft.teamsport.repository.HashTagContainerRepository;
import com.paracelsoft.teamsport.repository.PostCommentRepository;
import com.paracelsoft.teamsport.repository.PostIncludeUserRepository;
import com.paracelsoft.teamsport.repository.PostLikeRepository;
import com.paracelsoft.teamsport.repository.PostRepository;
import com.paracelsoft.teamsport.repository.PostSurveyAnsRepository;
import com.paracelsoft.teamsport.repository.PostSurveyRepository;
import com.paracelsoft.teamsport.repository.PostSurveyVoteRepository;
import com.paracelsoft.teamsport.repository.UploadRepository;
import com.paracelsoft.teamsport.util.ConstantUtils;
import com.paracelsoft.teamsport.util.DateUtil;
import com.paracelsoft.teamsport.util.JsonUtil;
import com.paracelsoft.teamsport.util.MessageUtils;

@Transactional(rollbackFor = { Exception.class, ParseException.class })
@Service("surveyService")
public class SurveyService {
	
	@Autowired 
	PostRepository postRepository;
	
	@Autowired
	MessageUtils messageUtils;
	
	@Autowired
	HashTagContainerRepository hashTagContainerRepository;
	
	@Autowired 
	PostIncludeUserRepository postIncludeUserRepository;
	
	@Autowired
	PostSurveyRepository postSurveyRepository;
	
	@Autowired
	PostSurveyAnsRepository postSurveyAnsRepository;
	
	@Autowired
	PostSurveyVoteRepository postSurveyVoteRepository;

	@Autowired
	UploadService uploadService;
	
	@Autowired
    Environment evn;
	
	@Autowired
	UploadRepository uploadRepository;
	
	@Autowired
	PostCommentRepository postCommentRepository;
	
	@Autowired
	PostLikeRepository postLikeRepository;

	/**
	 * @author TaoN
	 * @des create or update Survey
	 */
	public ApiResponse updateSurvey(List<MultipartFile> files, List<MultipartFile> backgroundImage, String surveyInfo, BigInteger userId) throws Exception {
		ApiResponse respon = new ApiResponse();
		Date date = new Date();
		List<PostIncludeUser> oldUserInclude = new ArrayList<PostIncludeUser>();
		BigInteger privacyInclude = new BigInteger(ConstantUtils.Privacy.INCLUDE.getValue());
		BigInteger privacyPublic = new BigInteger(ConstantUtils.Privacy.PUBLIC.getValue());
		List<UploadDTO> uploadDTOs = uploadService.uploadFiles(files, evn.getProperty("source.image.path").toString(), userId);
		List<UploadDTO> uploadDTO = uploadService.uploadFiles(backgroundImage, evn.getProperty("source.image.path").toString(), userId);
		SurveyDTO dto = (SurveyDTO) JsonUtil.convertToObject(surveyInfo, SurveyDTO.class);
		Post postSurvey = new Post();
		
		// Add options with selection survey [Text + Image] || [only Text]
		// If the user doesn't add an option, the message will be displayed to inform the user must add the option for this survey type
		if (ConstantUtils.Post_Type.POST_SURVEY_SELECTION.getValue().equals(dto.getPostType())) {
			if(dto.getAnswerList() == null && dto.getAnswerList().isEmpty()) {
				respon.setSuccess(false);
				respon.setMessage(messageUtils.getMessage("E034","survey"));
				return respon;
			}
		}
		
		if (dto.getPostId() == null) {
			// create
			postSurvey = convertToEntity(dto, uploadDTO);
			postSurvey.setCreatedDate(date);
			postSurvey.setUpdatedDate(date);
			postSurvey.setCreatedby(userId);
			respon.setMessage(messageUtils.getMessage("I001","survey"));
		} else {
			// update
			postSurvey = postRepository.findByPostIdAndIsActive(dto.getPostId(), 1);
			if(postSurvey == null) { //survey not exist
				respon.setSuccess(false);
				respon.setMessage(messageUtils.getMessage("E001","survey"));
				return respon;
			}
			
			// Handle update with survey Text and Selection type. If the survey is progressing, then the user cannot update the survey content.
			if (ConstantUtils.Post_Type.POST_SURVEY_SELECTION.getValue().equals(dto.getPostType())) {
				PostSurveyVote dataVoteExists = postSurveyVoteRepository.getVoteExistingByPostId(postSurvey.getPostId());
				if (dataVoteExists == null) { // No One Votes
					postSurveyAnsRepository.deleteUploadByPostId(postSurvey.getPostId()); // delete all Upload by post id
					postSurveyAnsRepository.deleteAnsByPostId(postSurvey.getPostId()); // delete all answers by post id
				} 
				if (dataVoteExists != null) {
					respon.setSuccess(false);
					respon.setMessage(messageUtils.getMessage("E033","survey"));
					return respon;
				}
			}
			
			if (ConstantUtils.Post_Type.POST_SURVEY_TEXT.getValue().equals(dto.getPostType())) {
				PostSurveyAns dataAnsExists = postSurveyAnsRepository.getAnsExistingByPostId(postSurvey.getPostId());
				if (dataAnsExists != null) { // data exist, show mess user cannot update survey
					respon.setSuccess(false);
					respon.setMessage(messageUtils.getMessage("E033","survey"));
					return respon;
				}
			}
			
			hashTagContainerRepository.deleteOldByPostAndTeam(postSurvey.getPostId(), dto.getTeamId()); // delete all hashTag by post id
			postIncludeUserRepository.deleteOldByPost(postSurvey.getPostId()); // delete all UncludeUser by post id
			postSurvey = this.convertToEntity(dto, uploadDTO);
			postSurvey.setCreatedby(postSurvey.getCreatedby()); //no change
			postSurvey.setCreatedDate(postSurvey.getCreatedDate()); //no change
			postSurvey.setUpdatedDate(date);
			respon.setMessage(messageUtils.getMessage("I003","survey"));
		}
		
		postSurvey = postRepository.save(postSurvey);
		
		if (ConstantUtils.Post_Type.POST_SURVEY_SELECTION.getValue().equals(dto.getPostType())) {
			this.saveAnswerOrOption(dto.getAnswerList(), uploadDTOs, userId, postSurvey.getPostId(), date);
		}
		
		//save new hash tag of survey
		if(dto.getHashTag() != null && !dto.getHashTag().isEmpty()) {
			this.saveHastag(dto.getHashTag(), userId, postSurvey.getPostId(), dto.getTeamId(), dto.getPrivacyId(), date);
		}
		
		//save survey include user : privacy is include or public
		if(dto.getUserInclude() != null && !dto.getUserInclude().isEmpty()
				&& (privacyInclude.compareTo(dto.getPrivacyId()) == 0
					|| privacyPublic.compareTo(dto.getPrivacyId()) == 0)) {
			this.savePostIncludeUser(dto.getUserInclude(), userId, postSurvey.getPostId(), 
					date, ConstantUtils.Post_Include_User_Type.PRIVACY.getValue(), null, oldUserInclude);
		}
		return respon;
	}
	
	/**
	 * @author TaoN
	 * @return 
	 * @Des create answer and option for selection and for text survey
	 */
	public void saveAnswerOrOption(List<SurveyDTO> answerList, List<UploadDTO> uploadDTOs, BigInteger createdBy, BigInteger postId, Date toDate) {
		List<PostSurveyAns> entitys = new ArrayList<>();
		
		int i = 0;
		for(SurveyDTO answer:answerList) {
			PostSurveyAns entity = new PostSurveyAns();
			entity.setAnsId(null);
			entity.setPostId(postId);
			entity.setAnsContent(answer.getPostSurveyContent());
			if (uploadDTOs != null && !uploadDTOs.isEmpty()) {
				if (uploadDTOs.get(i).getUploadId() != null) {
					entity.setAnsImage(uploadDTOs.get(i).getUploadId()); 
				}
			}
			entity.setCreatedby(createdBy);
			entity.setCreatedDate(toDate);
			entity.setUpdatedDate(toDate);
			entitys.add(entity);
			i++;
		}
		postSurveyRepository.saveAll(entitys);
	}
	
	/**
	 * @Des convert to entity
	 * @param surveyDTO
	 * @return
	 */
	public Post convertToEntity(SurveyDTO surveyDTO, List<UploadDTO> uploadDTO) {
		Post post = new Post();
		post.setTeamId(surveyDTO.getTeamId());
		post.setBackgroundImage(surveyDTO.getBackgroundImage());
		if (uploadDTO != null && !uploadDTO.isEmpty()) {
			if (uploadDTO.get(0).getUploadId() != null) {
				post.setBackgroundImage(uploadDTO.get(0).getUploadId()); 
			}
		}
		post.setPostContent(surveyDTO.getPostSurveyContent());
		post.setPostContentFontSize(surveyDTO.getPostContentFontSize());
		post.setPostType(surveyDTO.getPostType());
		post.setPostId(surveyDTO.getPostId());
		post.setBackgroundDefault(surveyDTO.getBackgroundDefault());
		post.setIsMultiple(surveyDTO.getIsMultiple());
		post.setIsExtendsAns(surveyDTO.getIsExtendsAns());
		post.setLocale(surveyDTO.getLocale());
		post.setPostSurveyDeadline(surveyDTO.getPostSurveyDeadline());
		post.setPrivacyId(surveyDTO.getPrivacyId());
		return post;
	}
	
	/**
	 * @Des save list hash tag to container
	 * @param hashTag
	 */
	public void saveHastag(List<String> hashTag, BigInteger createdBy, BigInteger postId, BigInteger teamId, BigInteger privacyId, Date toDate) {
		List<HashtagContainer> entitys = new ArrayList<>();
		for(String tag:hashTag) {
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
	
	/**
	 * @Des save include user
	 */
	public void savePostIncludeUser(List<UserDTO> users, BigInteger createdBy, BigInteger postId, Date toDate, String includeType, BigInteger commentId, List<PostIncludeUser> oldInclude) throws NoSuchMessageException, JSONException {
		List<PostIncludeUser> entitys = new ArrayList<>();
		for(UserDTO user:users) {
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
		}
		postIncludeUserRepository.saveAll(entitys);
	}
	
	/**
	 * @Des survey detail scn 12
	 */
	public ApiResponse getDetailSurveyByPostId(BigInteger postId, BigInteger userId) {
		ApiResponse response = new ApiResponse();
		SurveyDTO surveyDTO = new SurveyDTO();
		
		Map<String, Object> listResponses = postSurveyAnsRepository.getSurveyDetail(postId); 
		
		List<Map<String, Object>> listOptionResponses = postSurveyAnsRepository.listAnswerByPostId(postId);
		List<Map<String, Object>> listHashtagResponses = postSurveyAnsRepository.listHashagByPostId(postId);
		List<Map<String, Object>> listUserIncludeResponses = postSurveyAnsRepository.listIncludeUserByPostId(postId);
		Post postSurvey = postRepository.findByPostIdAndIsActive(postId, 1);
		Integer voted = postSurveyVoteRepository.getUserVoteOrNot(userId);
		int flagVoted = 0;
		
		if (voted >= 1 ) { flagVoted = 1; }
		if (ConstantUtils.Post_Type.POST_SURVEY_TEXT.getValue().equals(postSurvey.getPostType())) {
			Integer totalAnswers = postSurveyAnsRepository.totalAnswers(postId, postSurvey.getPostType());
			surveyDTO.setTotalAnswers(totalAnswers);
		}
		if (ConstantUtils.Post_Type.POST_SURVEY_SELECTION.getValue().equals(postSurvey.getPostType())) {
			Integer totalVotes = postSurveyAnsRepository.totalVotes(postId, postSurvey.getPostType());
			surveyDTO.setTotalVotes(totalVotes);
		}
		
		if (listResponses != null && !listResponses.isEmpty()) {
			surveyDTO.setFlagVote(flagVoted);
			surveyDTO.setUserId(new BigInteger(listResponses.get("user_id").toString()));
			surveyDTO.setPostId(new BigInteger(listResponses.get("post_id").toString()));
			surveyDTO.setPrivacyId(new BigInteger(listResponses.get("privacy_id").toString()));
			surveyDTO.setTeamId(new BigInteger(listResponses.get("team_id").toString()) );
			surveyDTO.setFullName(listResponses.get("user_full_name").toString());
			surveyDTO.setUserAvatar(new BigInteger(listResponses.get("user_avatar").toString()));
			surveyDTO.setPostSurveyContent(listResponses.get("post_content").toString());
			surveyDTO.setPrivacyName(listResponses.get("privacy_name").toString());
			if (listResponses.get("post_type").toString() != null && !listResponses.get("post_type").toString().isEmpty()) {
				surveyDTO.setPostType(listResponses.get("post_type").toString());
			}
			if (listResponses.get("background_image").toString() != null && !listResponses.get("background_image").toString().isEmpty()) {
				surveyDTO.setBackgroundImage(new BigInteger(listResponses.get("background_image").toString()));
			}
			
			if (listResponses.get("background_default").toString() != null && !listResponses.get("background_default").toString().isEmpty()) {
				surveyDTO.setBackgroundDefault(listResponses.get("background_default").toString());
			}
			surveyDTO.setPostSurveyDeadline(DateUtil.getFormatDate(listResponses.get("post_survey_deadline").toString(), DateUtil.PT_YYYY_MM_DD_HH_MM_SS));
			surveyDTO.setIsMultiple(Integer.parseInt(listResponses.get("is_multiple").toString()));
			surveyDTO.setIsExtendsAns(Integer.parseInt(listResponses.get("is_extends_ans").toString()));
			surveyDTO.setLocale(listResponses.get("locale").toString());
			if (listResponses.get("post_content_font_size").toString() != null || !listResponses.get("post_content_font_size").toString().isEmpty()) {
				surveyDTO.setPostContentFontSize(listResponses.get("post_content_font_size").toString());
			}
			surveyDTO.setCreatedDate(DateUtil.getFormatDate(listResponses.get("created_date").toString(), DateUtil.PT_YYYY_MM_DD_HH_MM_SS));
			surveyDTO.setNumberOfLikes(postLikeRepository.countPostLike(new BigInteger(listResponses.get("post_id").toString())));
			surveyDTO.setNumberOfComments(postCommentRepository.countPostComments(new BigInteger(listResponses.get("post_id").toString())));
			surveyDTO.setTimeAgo(DateUtil.getDiffDatetime(postSurvey.getCreatedDate()));
			
			if (listOptionResponses != null && !listOptionResponses.isEmpty()) {
				List<PostSurveyAns> listOption = new ArrayList<PostSurveyAns>();
				for (int i = 0; i < listOptionResponses.size(); i++) {
					PostSurveyAns postSurveyAns = new PostSurveyAns();
					
					postSurveyAns.setAnsId(new BigInteger( listOptionResponses.get(i).get("ans_id").toString()));
					postSurveyAns.setAnsContent(listOptionResponses.get(i).get("ans_content").toString());
					
					if (new BigInteger( listOptionResponses.get(i).get("ans_image").toString()) != null) {
						postSurveyAns.setAnsImage(new BigInteger( listOptionResponses.get(i).get("ans_image").toString()));
					}
					listOption.add(postSurveyAns);
				}
				surveyDTO.setOptionList(listOption);
			}
			
			if (listHashtagResponses != null && !listHashtagResponses.isEmpty()) {
				List<HashtagContainer> listHashtag = new ArrayList<HashtagContainer>();
				for (int i = 0; i < listHashtagResponses.size(); i++) {
					HashtagContainer hashtagContainer = new HashtagContainer();
					
					if (listHashtagResponses.get(i).get("hashtag_container_id").toString() != null &&  !listHashtagResponses.get(i).get("hashtag_container_id").toString().isEmpty()) {
						hashtagContainer.setHashtagContainerId(new BigInteger(listHashtagResponses.get(i).get("hashtag_container_id").toString()));
					}
					if (listHashtagResponses.get(i).get("hashtag_container_value").toString() != null && !listHashtagResponses.get(i).get("hashtag_container_value").toString().isEmpty()) {
						hashtagContainer.setHashtagContainerValue((listHashtagResponses.get(i).get("hashtag_container_value").toString()));
					}
					listHashtag.add(hashtagContainer);
				}
				surveyDTO.setHashtagList(listHashtag);
			}
			
			if (listUserIncludeResponses != null && !listUserIncludeResponses.isEmpty()) {
				List<User> listIncludeUser = new ArrayList<User>();
				for (int i = 0; i < listUserIncludeResponses.size(); i++) {
					User user = new User();
					
					if (listUserIncludeResponses.get(i).get("user_id").toString() != null && !listUserIncludeResponses.get(i).get("user_id").toString().isEmpty()) {
						user.setUserId(new BigInteger(listUserIncludeResponses.get(i).get("user_id").toString()));
					}
					if (listUserIncludeResponses.get(i).get("user_full_name").toString() != null && !listUserIncludeResponses.get(i).get("user_full_name").toString().isEmpty()) {
						user.setUserFullName((listUserIncludeResponses.get(i).get("user_full_name").toString()));
					}
					if (listUserIncludeResponses.get(i).get("user_avatar").toString() != null &&  !listUserIncludeResponses.get(i).get("user_avatar").toString().isEmpty()) {
						user.setUserAvatar(new BigInteger(listUserIncludeResponses.get(i).get("user_avatar").toString()));
					}
					listIncludeUser.add(user);
				}
				surveyDTO.setIncludeUserList(listIncludeUser);
			}
		}
		response.setData(surveyDTO);
		return response;
	}
	
	/**
	 * @Des get Answer or Option by post id 
	 * @Des applying for both list
	 */
	public ApiResponse getListAnswer(BigInteger postId) {
		ApiResponse response = new ApiResponse();
		List<Map<String, Object>> listResponses = postSurveyAnsRepository.listAnswerByPostId(postId);
		List<SurveyDTO> answerResponses = new ArrayList<SurveyDTO>();
		
		if (listResponses != null && !listResponses.isEmpty()) {
			for (Map<String, Object> obj: listResponses) {
				SurveyDTO surveyDTO = new SurveyDTO();
				if (ConstantUtils.Post_Type.POST_SURVEY_TEXT.getValue().equals(obj.get("post_Type").toString())) {
				surveyDTO.setPostId(new BigInteger(obj.get("post_id").toString()));
				surveyDTO.setPostType(obj.get("post_Type").toString());
				surveyDTO.setPostSurveyContent(obj.get("post_content").toString());
				surveyDTO.setUserId(new BigInteger(obj.get("user_id").toString()));
				surveyDTO.setUserAvatar(new BigInteger(obj.get("user_avatar").toString()));
				surveyDTO.setFullName(obj.get("user_full_name").toString());
				surveyDTO.setAnsContent(obj.get("ans_content").toString());
				answerResponses.add(surveyDTO);
				}
			}
		}
		response.setData(answerResponses);
		return response;
	}

	/**
	 * @Des get info user voted for selection survey by Post and Answer Id scn 14
	 */
	public ApiResponse getUsersByAnsId(SurveyDTO surveyDTOs, BigInteger userId) {
		ApiResponse response = new ApiResponse();
		PostSurveyAns postSurveyAns = new PostSurveyAns();
		Post postSurvey = postRepository.findByPostIdAndIsActive(surveyDTOs.getPostId(), 1);
		postSurveyAns = postSurveyAnsRepository.findByAnsIdAndIsActive(surveyDTOs.getAnsId(), 1);
		SurveyDTO surveyDTO = new SurveyDTO();
		
		if (postSurvey ==  null) {
			response.setSuccess(false);
			response.setMessage(messageUtils.getMessage("E001","survey"));
			return response;
		}
		if (postSurveyAns == null) {
			response.setSuccess(false);
			response.setMessage(messageUtils.getMessage("E001","answer"));
			return response;
		}
		
		if (ConstantUtils.Post_Type.POST_SURVEY_SELECTION.getValue().equals(postSurvey.getPostType())) {
			Integer totalVotes = postSurveyAnsRepository.totalVotedByAnsId(surveyDTOs.getPostId(), postSurvey.getPostType(), surveyDTOs.getAnsId());
			surveyDTO.setTotalVotes(totalVotes);
		}
		
		List<Map<String, Object>> listResponses = postSurveyAnsRepository.listUsersByAnsId(surveyDTOs.getAnsId());
		List<SurveyDTO> usersVoted = new ArrayList<SurveyDTO>();
		
		if (listResponses != null && !listResponses.isEmpty()) {
			for (Map<String, Object> obj: listResponses) {
				
				surveyDTO.setUserId(new BigInteger(obj.get("user_id").toString()));
				surveyDTO.setAnsId(new BigInteger(obj.get("ans_id").toString()));
				surveyDTO.setUserAvatar(new BigInteger(obj.get("user_avatar").toString()));
				surveyDTO.setFullName(obj.get("user_full_name").toString());
				surveyDTO.setAnsContent(obj.get("ans_content").toString());
				usersVoted.add(surveyDTO);
			}
		}
		response.setData(usersVoted);
		return response;
	}

	/**
	 * @Des count total user voted for answer id of the selection survey
	 */
	public ApiResponse totalVotedByAnsId(BigInteger postId, BigInteger ansId) {
		ApiResponse response = new ApiResponse();
		Post postSurvey = postRepository.findByPostIdAndIsActive(postId, 1);
		SurveyDTO surveyDTO = new SurveyDTO();
		
		if (postSurvey == null) {
			response.setSuccess(false);
			response.setMessage(messageUtils.getMessage("E001", "survey"));
			return response;
		}
		// total users voted for answer by answer id
		if (ConstantUtils.Post_Type.POST_SURVEY_SELECTION.getValue().equals(postSurvey.getPostType())) {
			Integer totalVotes = postSurveyAnsRepository.totalVotedByAnsId(postId, postSurvey.getPostType(), ansId);
			surveyDTO.setTotalVotes(totalVotes);
		}
		response.setData(surveyDTO);
		return response;
	}

	/**
	 * @Des using for client add answer the text survey and is also add more option for selection survey
	 */
	public ApiResponse addAnswerOrAddOption(List<MultipartFile> files, String answerInfo, BigInteger userId) throws Exception {
		ApiResponse response = new ApiResponse();
		Date date = new Date();
		
		SurveyDTO dto = (SurveyDTO) JsonUtil.convertToObject(answerInfo, SurveyDTO.class);
		List<UploadDTO> uploadImageDTO = uploadService.uploadFiles(files, evn.getProperty("source.image.path").toString(), userId);
		Post postSurvey = new Post();
		
		if (dto.getPostId() != null ) {
			postSurvey = postRepository.findByPostIdAndIsActive(dto.getPostId(), 1);
			// add answer for text survey
			if (ConstantUtils.Post_Type.POST_SURVEY_TEXT.getValue().equals(postSurvey.getPostType())) {
				this.saveAnswerOrOption(dto.getAnswerList(), uploadImageDTO, userId, dto.getPostId(), date);
			}
			// the survey allows the client to add new options
			if (ConstantUtils.Post_Type.POST_SURVEY_SELECTION.getValue().equals(postSurvey.getPostType())) {
				if (new Integer(ConstantUtils.ExtendsAnswer.IS_EXTENDS_ANS.getValue()).equals(postSurvey.getIsMultiple())) {
					saveAnswerOrOption(dto.getAnswerList(), uploadImageDTO, userId, dto.getPostId(), date);
				}
				else {
					response.setSuccess(false);
					response.setMessage(messageUtils.getMessage("E035", "survey"));
					return response;
				}
			}
			response.setMessage(messageUtils.getMessage("I026","answer"));
		} else {
			response.setSuccess(false);
			response.setMessage(messageUtils.getMessage("E001", "survey"));
			return response;
		}
		return response;
	}
	
	/**
	 * @Des delete option in the select survey
	 */
	public ApiResponse deleteOptionByOptionId(SurveyDTO surveyDTO, BigInteger userId) {
		ApiResponse response = new ApiResponse();
		
		Post postSurvey = postRepository.findByPostIdAndIsActive(surveyDTO.getPostId(), 1);
		if (postSurvey == null) {
			response.setSuccess(false);
			response.setMessage(messageUtils.getMessage("E001", "survey"));
			return response;
		}
		PostSurveyAns oldAns = postSurveyAnsRepository.findByAnsIdAndIsActive(surveyDTO.getAnsId(), 1);
		if (oldAns == null) {
			response.setSuccess(false);
			response.setMessage(messageUtils.getMessage("E001", "answer"));
			return response;
		}
		Integer dataVoteExists = postSurveyVoteRepository.getVoteExist(surveyDTO.getPostId(), surveyDTO.getAnsId());
		if (dataVoteExists >= 1) {
			response.setSuccess(false);
			response.setMessage(messageUtils.getMessage("E036", "option"));
			return response;
		}
		
		if (ConstantUtils.Post_Type.POST_SURVEY_SELECTION.getValue().equals(postSurvey.getPostType())) {
			// get id image0
			if (oldAns.getAnsImage() != null) {
				Upload uploadId = uploadRepository.findByUploadId(oldAns.getAnsImage());
				uploadRepository.deleteById(uploadId.getUploadId());
			}
			postSurveyAnsRepository.deleteAnsByAnId(oldAns.getAnsId());
			response.setMessage(messageUtils.getMessage("I002", "answer"));
		}
		return response;
	}
	
	/**
	 * @Des using for client add vote multiple or one
	 */
	public ApiResponse addVote(SurveyDTO surveyDTO, BigInteger userId) throws Exception {
		ApiResponse response = new ApiResponse(); 
		Date date = new Date();
		
		if (surveyDTO.getPostId() != null ) { 
			Post postSurvey = postRepository.findByPostIdAndIsActive(surveyDTO.getPostId(), 1);
			if (postSurvey != null ) {
				if (ConstantUtils.Post_Type.POST_SURVEY_SELECTION.getValue().equals(postSurvey.getPostType())) {
					// Which select one option
					if (!ConstantUtils.SelectionType.IS_MULTIPLE.getValue().equals(postSurvey.getIsMultiple())) {
						Integer checkExsit = postSurveyVoteRepository.countVoteByPostAndUserId(surveyDTO.getPostId(), userId);
						if (checkExsit >= 1) {
							response.setSuccess(false);
							response.setMessage(messageUtils.getMessage("E037", "answer")); 
							return response;
						}
					}
					this.saveVotes(surveyDTO.getVotes(), userId, date);
				} 
			}
		}  
		return response; 
	}
	
	/**
	 * @Des using for answer one or multiple options
	 */
	public void saveVotes(List<SurveyDTO> votes, BigInteger createdBy, Date toDate) {
		List<PostSurveyVote> entitys = new ArrayList<>();
		
		for(SurveyDTO answers:votes) {
			PostSurveyVote postSurveyVote = new PostSurveyVote();
			postSurveyVote.setVoteId(null);
			postSurveyVote.setAnsId(answers.getAnsId());
			postSurveyVote.setCreatedby(createdBy);
			postSurveyVote.setCreatedDate(toDate);
			postSurveyVote.setUpdatedDate(toDate);
			entitys.add(postSurveyVote);
		}
		postSurveyVoteRepository.saveAll(entitys);
	}

	public ApiResponse deleteSurvey(SurveyDTO surveyDTO, BigInteger userId) {
		ApiResponse response = new ApiResponse();
		Post postSurvey = postRepository.findByPostIdAndIsActive(surveyDTO.getPostId(), 1);
		
		if (postSurvey == null) {
			response.setSuccess(false);
			response.setMessage(messageUtils.getMessage("E001", "survey"));
			return response;
		}
		
		postSurveyVoteRepository.deleteVoteByPostId(postSurvey.getPostId());
		postSurveyAnsRepository.deleteUploadByPostId(postSurvey.getPostId());
		postSurveyAnsRepository.deleteAnsByPostId(postSurvey.getPostId()); 
		hashTagContainerRepository.deleteOldByPostAndTeam(postSurvey.getPostId(), postSurvey.getTeamId()); 
		postIncludeUserRepository.deleteOldByPost(postSurvey.getPostId());
		postRepository.delete(postSurvey);
		
		response.setMessage(messageUtils.getMessage("I002", "survey"));
		return response;
	}

}
