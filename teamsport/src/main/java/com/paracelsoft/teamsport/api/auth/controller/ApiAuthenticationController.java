package com.paracelsoft.teamsport.api.auth.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.dto.AuthenticationRequest;
import com.paracelsoft.teamsport.entity.User;
import com.paracelsoft.teamsport.repository.UserRepository;
import com.paracelsoft.teamsport.security.jwt.JwtTokenProvider;
import com.paracelsoft.teamsport.service.TokenService;
import com.paracelsoft.teamsport.service.UserService;
import com.paracelsoft.teamsport.util.MessageUtils;

@RestController
@RequestMapping("/api/v1")
public class ApiAuthenticationController {

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	UserService userservice;

	@Autowired
	UserRepository userRepository;

	@Autowired
	MessageUtils messageUtils;

	@Autowired
	TokenService tokenService;

	/**
	 * 
	 * @Des customer login for api
	 * @param loginDTO
	 * @return
	 */
	@PostMapping(value = "/login", produces = "application/json;charset=UTF-8")
	public ApiResponse checkLogin(@RequestBody AuthenticationRequest loginDTO) {

		ApiResponse result = new ApiResponse();

		Map<String, String> data = new HashMap<String, String>();
		try {
			String username = loginDTO.getUsername();
			String password = loginDTO.getPassword();

			UsernamePasswordAuthenticationToken create = new UsernamePasswordAuthenticationToken(username, password);
			authenticationManager.authenticate(create);
			// get user id
			User user = userservice.getUserByUserName(username);
			if (user == null) {
				result.setSuccess(false);
				result.setMessage(messageUtils.getMessage("E003"));
				return result;
			}
			// created new token
			String token = jwtTokenProvider.createToken(username,
					user.getUserRoleId() != null ? user.getUserRoleId().toString() : "", 31556952000l);// create token
																										// Expiration 1
																										// year

			// save persistent token
			tokenService.savePersistentLogin(token, user.getUserId());

			data.put("username", username);
			data.put("userId", user.getUserId() != null ? user.getUserId().toString() : null);
			data.put("token", token);
			data.put("role", user.getUserRoleId() != null ? user.getUserRoleId().toString() : null);
			data.put("activeTeam", user.getActiveTeam() != null ? user.getActiveTeam().toString() : null);
			result.setSuccess(true);
			result.setData(data);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(messageUtils.getMessage("E011"));
		}
		return result;
	}

	/**
	 * 
	 * @Des customer login for api
	 * @param loginDTO
	 * @return
	 */
	@PostMapping(value = "/switchuser", produces = "application/json;charset=UTF-8")
	public ApiResponse checkRelativeLogin(@RequestBody AuthenticationRequest loginDTO, HttpSession session) {

		ApiResponse result = new ApiResponse();
		Map<String, String> data = new HashMap<String, String>();
		try {
			
			User currentUser = (User) session.getAttribute("userName");
			if (currentUser.getRelativeId() == null) {//user parent
				boolean isChild = true;
				List<User> listChild = userRepository.findByRelativeIdAndIsActive(currentUser.getUserId(), 1);
				if (listChild != null && !listChild.isEmpty()) {
					for (User obj : listChild) {
						if (!obj.getUserId().equals(loginDTO.getUserId())) {
							isChild = false;
						}
					}
					if(!isChild) {
		            	result.setSuccess(false);
		                result.setMessage(messageUtils.getMessage("E001","user"));
		            	return result;
					}
				}
			} else {
				boolean isParent = true;
				if (!currentUser.getRelativeId().equals(loginDTO.getUserId())) {//user relative
					isParent = false;
				}
				
				if(!isParent) {
	            	result.setSuccess(false);
	                result.setMessage(messageUtils.getMessage("E001","user"));
	            	return result;
				}
			}

			// get user id
			User user = userservice.getUserByUserId(loginDTO.getUserId());
			if (user == null) {
				result.setSuccess(false);
				result.setMessage(messageUtils.getMessage("E001", "user"));
				return result;
			}

			session.setAttribute("userName", user);
			// created new token
			String token = jwtTokenProvider.createToken(user.getUserFullName(),
					user.getUserRoleId() != null ? user.getUserRoleId().toString() : "", 31556952000l);// create token
																										// Expiration 1
																										// year

			// save persistent token
			tokenService.savePersistentLogin(token, user.getUserId());

			data.put("username", user.getUserFullName());
			data.put("userId", user.getUserId() != null ? user.getUserId().toString() : null);
			data.put("token", token);
			data.put("role", user.getUserRoleId() != null ? user.getUserRoleId().toString() : null);
			data.put("activeTeam", user.getActiveTeam() != null ? user.getActiveTeam().toString() : null);
			result.setSuccess(true);
			result.setData(data);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(messageUtils.getMessage("E011"));
		}
		return result;
	}

	@GetMapping(value = "/logout")
	public ApiResponse logout(HttpServletRequest request, HttpServletResponse response) {
		ApiResponse result = new ApiResponse();
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null) {
				new SecurityContextLogoutHandler().logout(request, response, auth);
			}
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}

		return result;
	}

	@GetMapping("/.well-known/apple-app-site-association")
	public String readFile() {
		ApiResponse result = new ApiResponse();
		String jsonLocation = null;
		try {
			Resource resource = new ClassPathResource("apple-app-site-association");
			jsonLocation = FileUtils.readFileToString(resource.getFile());
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage(messageUtils.getMessage("E003"));
		}
		return jsonLocation;
	}

}
