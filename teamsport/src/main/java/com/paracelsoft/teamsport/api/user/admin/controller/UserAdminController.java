package com.paracelsoft.teamsport.api.user.admin.controller;

import java.math.BigInteger;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paracelsoft.teamsport.api.dto.UserDTO;
import com.paracelsoft.teamsport.api.user.controller.AbstractController;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.service.TeamService;
import com.paracelsoft.teamsport.service.UserService;
import com.paracelsoft.teamsport.util.MessageUtils;
import com.paracelsoft.teamsport.util.ReponseCode;

@RestController
@RequestMapping("/api/v1/user/admin")
public class UserAdminController extends AbstractController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	MessageUtils messageUtils;
	
	@Autowired
	TeamService teamService;
	
	
	/**
	 * 
	 * @Des create user (webadmin)
	 * @param UserDTO
	 * @return
	 * @throws Exception
	 */
//	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	public ApiResponse createCustomer(@RequestBody UserDTO userDTO, HttpServletRequest request) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			respon.setSuccess(true);
			respon.setMessage(messageUtils.getMessage("I004", "user"));
			respon = userService.createUser(request, userDTO);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setMessage(messageUtils.getMessage("E003"));
			respon.setSuccess(false);
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des update user (webadmin)
	 * @param UserDTO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/update")
//	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse updateCustomer(@RequestBody UserDTO userDTO) throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			respon = userService.updateUser(userDTO);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	
	/**
	 * 
	 * @Des get list detail users  
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/all")
//	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse selectListUser() throws Exception {
		ApiResponse respon = new ApiResponse();
		try {
			List<UserDTO> listUser = userService.getListUserDTO();
			respon.setData(listUser);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
	/**
	 * 
	 * @Des Block/UnBlock user
	 * @Des change isActive to 0 (Block) to 1 (Active)
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/block")
//	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse blockUser(@RequestParam(value="userId", required = false) BigInteger userId) throws Exception {
		ApiResponse respon = new ApiResponse();

		try {
			if (userId == null) {
				respon.setSuccess(false);
				respon.setErrorCode(ReponseCode.INVALID_CODE.INVALID_REQUEST.getCode());
				respon.setMessage(messageUtils.getMessage("E002", "userId"));
				return respon;
			}
			
//			User user = getCurrentUserLogin();
//			if(user == null) {
//				respon.setSuccess(false);
//				respon.setErrorCode("INVALID_CODE");
//				respon.setMessage(messageUtils.getMessage("E006"));
//				respon.setData(new ArrayList<>());
//	            return respon;
//			}
//			if (new BigInteger(ConstantUtils.
//					ROLE_TYPE_ID.ADMIN.getValue()).compareTo(user.getUserRoleId()) != 0) { // Not Admin
//				respon.setSuccess(false);
//				respon.setMessage(messageUtils.getMessage("E015"));
//				respon.setData(new ArrayList<>());
//	            return respon;
//			}

			respon = userService.blockUserByUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E003"));
		}
		return respon;
	}
	
}
