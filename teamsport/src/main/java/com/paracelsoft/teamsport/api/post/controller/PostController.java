package com.paracelsoft.teamsport.api.post.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paracelsoft.teamsport.api.dto.PostCommentDTO;
import com.paracelsoft.teamsport.api.dto.PostDTO;
import com.paracelsoft.teamsport.api.dto.PostLikeDTO;
import com.paracelsoft.teamsport.api.dto.PrivacyDTO;
import com.paracelsoft.teamsport.api.dto.RequestDTO;
import com.paracelsoft.teamsport.api.user.controller.AbstractController;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.entity.User;
import com.paracelsoft.teamsport.service.PostService;
import com.paracelsoft.teamsport.util.MessageUtils;
import com.paracelsoft.teamsport.util.ReponseCode;

@RestController
@RequestMapping("/api/v1/post")
public class PostController extends AbstractController {
	
	@Autowired
	PostService postService;
	
	@Autowired
	MessageUtils messageUtils;
	
	/**
	 * 
	 * @Des get list privacy 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/privacys")
	public ApiResponse selectListPrivacy() throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			List<PrivacyDTO> privacy = postService.getListPrivacy();
			respon.setData(privacy);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des api created, updated post text
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/update")
	public ApiResponse updatePost(@RequestBody PostDTO postDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if(postDTO == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}
			
			User user = getCurrentUserLogin();
			if(user == null) {
				respon.setSuccess(false);
				respon.setErrorCode("INVALID_CODE");
				respon.setMessage(messageUtils.getMessage("E006"));
				respon.setData(new ArrayList<>());
	            return respon;
			}
			BigInteger createdBy = user.getUserId();	
			respon = postService.updatePost(postDTO, createdBy);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des api del post
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/delete")
	public ApiResponse delPost(@RequestBody RequestDTO request) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if(request == null || request.getTeamId() == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}
			
			if(request.getPostId() == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}
			
			respon = postService.delPost(request.getTeamId(), request.getPostId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des api search hashtag
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/hashtags")
	public ApiResponse searchHashtag(@RequestParam(value="teamId", required = false) BigInteger teamId,
			@RequestParam(value="keyword", required = false) String keyword) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if(teamId == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}

			respon = postService.searchHashtag(teamId, keyword);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des api update privacy post
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/privacys")
	public ApiResponse updatePrivacyPost(@RequestBody PostDTO postDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			//validate
			if(postDTO == null
					|| postDTO.getPostId() == null
					|| postDTO.getPrivacyId() == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}
			
			User user = getCurrentUserLogin();
			if(user == null) {
				respon.setSuccess(false);
				respon.setErrorCode("INVALID_CODE");
				respon.setMessage(messageUtils.getMessage("E006"));
				respon.setData(new ArrayList<>());
	            return respon;
			}
	
			respon = postService.updatePrivacyPost(postDTO, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des api update privacy post
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/like")
	public ApiResponse likePostOrComment(@RequestBody PostLikeDTO postLikeDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			//validate
			if(postLikeDTO == null || postLikeDTO.getPostId() == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}
			
			User user = getCurrentUserLogin();
			if(user == null) {
				respon.setSuccess(false);
				respon.setErrorCode("INVALID_CODE");
				respon.setMessage(messageUtils.getMessage("E006"));
				respon.setData(new ArrayList<>());
	            return respon;
			}
	
			respon = postService.likePostOrComment(postLikeDTO, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des api update privacy post
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/comment")
	public ApiResponse addComment(@RequestBody PostCommentDTO postCommentDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			//validate
			if(postCommentDTO == null || postCommentDTO.getPostId() == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}
			
			User user = getCurrentUserLogin();
			if(user == null) {
				respon.setSuccess(false);
				respon.setErrorCode("INVALID_CODE");
				respon.setMessage(messageUtils.getMessage("E006"));
				respon.setData(new ArrayList<>());
	            return respon;
			}
	
			respon = postService.addComment(postCommentDTO, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des api delete comment
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/comment/delete")
	public ApiResponse deleteComment(@RequestBody PostCommentDTO postCommentDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			//validate
			if(postCommentDTO == null || postCommentDTO.getPostCommentId() == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}

			respon = postService.deleteComment(postCommentDTO);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des get list privacy 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/detail")
	public ApiResponse postDetail(@RequestParam(value="postId", required = false) BigInteger postId) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			
			if(postId == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}
			
			User user = getCurrentUserLogin();
			if(user == null) {
				respon.setSuccess(false);
				respon.setErrorCode("INVALID_CODE");
				respon.setMessage(messageUtils.getMessage("E006"));
				respon.setData(new ArrayList<>());
	            return respon;
			}
			
			respon = postService.getDetailPost(postId, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}

}
