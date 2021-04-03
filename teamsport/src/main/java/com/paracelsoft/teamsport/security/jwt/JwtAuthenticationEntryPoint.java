package com.paracelsoft.teamsport.security.jwt;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.util.JsonUtil;
import com.paracelsoft.teamsport.util.MessageUtils;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

	private static final long serialVersionUID = -7858869558953243875L;
	@Autowired
	MessageUtils messageUtils;
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		ApiResponse res = new ApiResponse();
	    res.setSuccess(false);
	    res.setErrorCode(String.valueOf(HttpServletResponse.SC_UNAUTHORIZED));
	    res.setMessage(messageUtils.getMessage("E013"));
	    response.getWriter().append(JsonUtil.convertToJson(res));
	}

}
