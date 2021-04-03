package com.paracelsoft.teamsport.api.user.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.paracelsoft.teamsport.config.UserDetail;
import com.paracelsoft.teamsport.entity.User;
import com.paracelsoft.teamsport.service.UserService;

public abstract class AbstractController {

	@Autowired
	protected ServletContext context;
	@Autowired
	protected HttpServletRequest request;
	@Autowired
	protected HttpServletResponse response;
	@Autowired
	protected UserService userService;

	public User getCurrentUserLogin() throws Exception {
		User currentUser = (User) request.getSession().getAttribute("userName");

		if (currentUser != null) {
			return currentUser;
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}

		Object principal = authentication.getPrincipal();
		if (principal == null || (principal instanceof String && "anonymousUser".equals(principal))) {
			return null;
		}

		if (principal instanceof UserDetails) {
			UserDetail u = (UserDetail) principal;
			currentUser = userService.getUserByUserName(u.getUsername());
		}

		request.getSession().setAttribute("userName", currentUser);
		return currentUser;
	}

}
