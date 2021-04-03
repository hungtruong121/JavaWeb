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
import org.springframework.web.multipart.MultipartFile;

import com.paracelsoft.teamsport.api.dto.PostAlbumDTO;
import com.paracelsoft.teamsport.api.dto.PostCommentDTO;
import com.paracelsoft.teamsport.api.dto.PostLikeDTO;
import com.paracelsoft.teamsport.api.dto.RequestDTO;
import com.paracelsoft.teamsport.api.user.controller.AbstractController;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.entity.User;
import com.paracelsoft.teamsport.service.PostAlbumService;
import com.paracelsoft.teamsport.service.PostService;
import com.paracelsoft.teamsport.util.MessageUtils;
import com.paracelsoft.teamsport.util.ReponseCode;

@RestController
@RequestMapping("/api/v1/postalbum")
public class PostAlbumController extends AbstractController {

	@Autowired
	PostAlbumService postAlbumnService;

	@Autowired
	PostService postService;

	@Autowired
	MessageUtils messageUtils;

	/**
	 * 
	 * @Des api created, updated post album
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/create")
	public ApiResponse createAlbum(@RequestBody PostAlbumDTO postAlbumDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if (postAlbumDTO == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}

			User user = getCurrentUserLogin();
			if (user == null) {
				respon.setSuccess(false);
				respon.setErrorCode("INVALID_CODE");
				respon.setMessage(messageUtils.getMessage("E006"));
				respon.setData(new ArrayList<>());
				return respon;
			}

			respon = postAlbumnService.createAlbum(postAlbumDTO, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}

	/**
	 * 
	 * @Des api add post to album
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/photo/add")
	public ApiResponse addPhotos(@RequestParam(value = "files", required = false) List<MultipartFile> files,
			@RequestParam(value = "infoString", required = false) String infoString) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if (files == null || files.isEmpty()) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E003"));
				return respon;
			}

			User user = getCurrentUserLogin();
			if (user == null) {
				respon.setSuccess(false);
				respon.setErrorCode("INVALID_CODE");
				respon.setMessage(messageUtils.getMessage("E006"));
				respon.setData(new ArrayList<>());
				return respon;
			}
			respon = postAlbumnService.addPhotos(files, infoString, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));

			respon.setMessage(e.getMessage());
		}
		return respon;
	}

	/**
	 * 
	 * @Des api add post to album
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/photo/update")
	public ApiResponse updatePhotosInAlbum(@RequestBody PostAlbumDTO postAlbumDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if (postAlbumDTO == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E003"));
				return respon;
			}

			User user = getCurrentUserLogin();
			if (user == null) {
				respon.setSuccess(false);
				respon.setErrorCode("INVALID_CODE");
				respon.setMessage(messageUtils.getMessage("E006"));
				respon.setData(new ArrayList<>());
				return respon;
			}
			respon = postAlbumnService.updatePhotoInTeam(postAlbumDTO, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}

	/**
	 * 
	 * @Des get list album public by teamId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/team/listalbum")
	public ApiResponse getListAlbumInTeam(@RequestParam(value = "teamId", required = false) BigInteger teamId)
			throws Exception {
		ApiResponse respon = new ApiResponse();
		try {

			if (teamId == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}

			User user = getCurrentUserLogin();
			if (user == null) {
				respon.setSuccess(false);
				respon.setErrorCode("INVALID_CODE");
				respon.setMessage(messageUtils.getMessage("E006"));
				respon.setData(new ArrayList<>());
				return respon;
			}

			respon = postAlbumnService.getListAlbumInTeam(teamId, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}

	/**
	 * 
	 * @Des get all photo and video in album
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/post/list")
	public ApiResponse getListPhotoInAlbum(@RequestParam(value = "postId", required = false) BigInteger postId)
			throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if (postId == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}

			User user = getCurrentUserLogin();
			if (user == null) {
				respon.setSuccess(false);
				respon.setErrorCode("INVALID_CODE");
				respon.setMessage(messageUtils.getMessage("E006"));
				respon.setData(new ArrayList<>());
				return respon;
			}

			respon = postAlbumnService.getListPostInAlbum(postId, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}

	/**
	 * 
	 * @Des get list photo in team
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/team/listphoto")
	public ApiResponse getListPhotoInTeam(@RequestParam(value = "teamId", required = false) BigInteger teamId)
			throws Exception {
		ApiResponse respon = new ApiResponse();
		try {

			if (teamId == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}

			User user = getCurrentUserLogin();
			if (user == null) {
				respon.setSuccess(false);
				respon.setErrorCode("INVALID_CODE");
				respon.setMessage(messageUtils.getMessage("E006"));
				respon.setData(new ArrayList<>());
				return respon;
			}

			respon = postAlbumnService.getListPhotoInTeam(teamId, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}

	/**
	 * 
	 * @Des get list photo in team
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/team/listvideo")
	public ApiResponse getListVideoInTeam(@RequestParam(value = "teamId", required = false) BigInteger teamId)
			throws Exception {
		ApiResponse respon = new ApiResponse();
		try {

			if (teamId == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}

			User user = getCurrentUserLogin();
			if (user == null) {
				respon.setSuccess(false);
				respon.setErrorCode("INVALID_CODE");
				respon.setMessage(messageUtils.getMessage("E006"));
				respon.setData(new ArrayList<>());
				return respon;
			}

			respon = postAlbumnService.getListVideoInTeam(teamId, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des get list photo in team
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/team/listpost")
	public ApiResponse getListPostInTeam(@RequestParam(value = "teamId", required = false) BigInteger teamId)
			throws Exception {
		ApiResponse respon = new ApiResponse();
		try {

			if (teamId == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}

			User user = getCurrentUserLogin();
			if (user == null) {
				respon.setSuccess(false);
				respon.setErrorCode("INVALID_CODE");
				respon.setMessage(messageUtils.getMessage("E006"));
				respon.setData(new ArrayList<>());
				return respon;
			}

			respon = postAlbumnService.getListPostInTeam(teamId, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}

	/**
	 * 
	 * @Des api del album
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/delete")
	public ApiResponse delPost(@RequestBody RequestDTO request) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			if (request == null || request.getTeamId() == null || request.getPostId() == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}
			
			User user = getCurrentUserLogin();
			if (user == null) {
				respon.setSuccess(false);
				respon.setErrorCode("INVALID_CODE");
				respon.setMessage(messageUtils.getMessage("E006"));
				respon.setData(new ArrayList<>());
				return respon;
			}

			respon = postAlbumnService.delPost(request.getTeamId(), request.getPostId(), user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}

	/**
	 * 
	 * @Des get detail post(photo+video)
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/photo/detail")
	public ApiResponse getDetailPost(@RequestParam(value = "postId", required = false) BigInteger postId)
			throws Exception {
		ApiResponse respon = new ApiResponse();
		try {

			if (postId == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}

			User user = getCurrentUserLogin();
			if (user == null) {
				respon.setSuccess(false);
				respon.setErrorCode("INVALID_CODE");
				respon.setMessage(messageUtils.getMessage("E006"));
				respon.setData(new ArrayList<>());
				return respon;
			}

			respon = postAlbumnService.getDetailPost(postId, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/like")
	public ApiResponse likePostOrComment(@RequestBody PostLikeDTO postLikeDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			// validate
			if (postLikeDTO == null || postLikeDTO.getPostId() == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}

			User user = getCurrentUserLogin();
			if (user == null) {
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
	 * @Des api delete comment
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/comment/delete")
	public ApiResponse deleteComment(@RequestBody PostCommentDTO postCommentDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			// validate
			if (postCommentDTO == null || postCommentDTO.getPostCommentId() == null) {
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
	 * @Des api add comment
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/comment")
	public ApiResponse addComment(@RequestBody PostCommentDTO postCommentDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			// validate
			if (postCommentDTO == null || postCommentDTO.getPostId() == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				return respon;
			}

			User user = getCurrentUserLogin();
			if (user == null) {
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
}
