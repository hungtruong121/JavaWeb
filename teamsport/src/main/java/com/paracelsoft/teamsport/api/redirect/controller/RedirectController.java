package com.paracelsoft.teamsport.api.redirect.controller;

import java.math.BigInteger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/api/v1")
public class RedirectController {
	@GetMapping("/invite")
	public RedirectView redirectWithUsingRedirectView(
			@RequestParam(value = "teamId", required = false) BigInteger teamId,
			@RequestParam(value = "os", required = false) String os) {
		String link = "";
		try {
			
			if("android".equals(os)) {
				link = "https://play.google.com/store";
			}else {
				link = "https://apple.com/app-store/";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new RedirectView(link);
		
	}
}
