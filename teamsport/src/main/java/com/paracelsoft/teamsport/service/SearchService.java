package com.paracelsoft.teamsport.service;

import java.text.ParseException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paracelsoft.teamsport.api.dto.SearchDTO;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.util.ConstantUtils;
import com.paracelsoft.teamsport.util.MessageUtils;

@Transactional(rollbackFor = { Exception.class, ParseException.class })
@Service("searchService")
public class SearchService {

	@Autowired
	TeamService teamService;
	
	@Autowired
	MessageUtils messageUtils;
	
	public ApiResponse searchAll(SearchDTO search) throws Exception {
		ApiResponse respon = new ApiResponse();
		
		
		
		String keyword = search.getKeyword();
		if (StringUtils.isEmpty(keyword)) {// full
			keyword = "%%";
		} else {// start with keywork
			keyword = "%" + keyword + "%";
		}
		if (search.getMaxResult() == 0) {
			search.setMaxResult(10);
		}
		ConstantUtils.SearchType.TEAM.getValue();
		switch (search.getSearchType()) {
		case "TEAM":
			respon = teamService.searchTeamInfoByTeamName(search);
			break;
		case "PEOPLE":
			respon = teamService.searchUserPageTeam(search);
			break;
		case "HASHTAG":
			
			break;
		default:
			break;
		}
		return respon;
	}
}
