package com.paracelsoft.teamsport.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class MyCustomAuthSuccessHandler implements AuthenticationSuccessHandler {
	
	@Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
		
        UserDetail user = (UserDetail)authentication.getPrincipal();
        //check role
        if(user == null || (user.getAuthorities() != null && !user.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")))) {
        	response.sendRedirect(request.getContextPath() + "/login-error");
        }
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
        		authentication.getPrincipal(),
        		user.getUsername(), 
        		user.getAuthorities());
		auth.setDetails(user);
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(auth);
		
        //set our response to OK status
        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect(request.getContextPath() + "/dashboard");
    }

}
