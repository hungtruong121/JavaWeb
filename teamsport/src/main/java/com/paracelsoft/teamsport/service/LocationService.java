package com.paracelsoft.teamsport.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paracelsoft.teamsport.api.dto.LocationDTO;
import com.paracelsoft.teamsport.api.dto.SearchDTO;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.entity.Location;
import com.paracelsoft.teamsport.repository.LocationRepository;
import com.paracelsoft.teamsport.util.MessageUtils;

@Transactional(rollbackFor = { Exception.class, ParseException.class })
@Service("locationService")
public class LocationService {

	@Autowired
	LocationRepository locationRepository;

	@Autowired
	MessageUtils messageUtils;

	public ApiResponse addLocation(LocationDTO locationDTO, BigInteger userId) {
		ApiResponse respon = new ApiResponse();
		Location location = new Location();
		if (locationDTO.getLocationId() != null) {
			Location oldLocation = locationRepository.findByLocationIdAndIsActive(locationDTO.getLocationId(), 1);
			if (oldLocation == null) {
				respon.setSuccess(false);
				respon.setMessage(messageUtils.getMessage("E001", "location"));
				return respon;
			}
			// update
			location = convertToEntity(locationDTO);
			location.setLocationName(locationDTO.getLocationName());
			location.setCreatedby(oldLocation.getCreatedby()); // no change
			location.setCreatedDate(oldLocation.getCreatedDate()); // no change
			location.setUpdatedDate(new Date());
		} else {
			location = convertToEntity(locationDTO);
			location.setCreatedby(userId);
			location.setCreatedDate(new Date());
			location.setUpdatedDate(new Date());
		}
		location = locationRepository.save(location);
		respon.setData(location);
		return respon;
	}

	public ApiResponse getListLocationInTeam(BigInteger teamId, BigInteger userId) {
		ApiResponse respon = new ApiResponse();
		List<LocationDTO> listDTO = new ArrayList<LocationDTO>();
		List<Location> listLocation = locationRepository.findByTeamIdAndIsActive(teamId, 1);
		if (listLocation != null && !listLocation.isEmpty()) {
			for (Location loc : listLocation) {
				LocationDTO dto = convertToDTO(loc);
				listDTO.add(dto);
			}
		}
		respon.setData(listDTO);
		return respon;
	}
	
	public ApiResponse delLocation(BigInteger locationId) {
		ApiResponse respon = new ApiResponse();
		Location location = locationRepository.findByLocationIdAndIsActive(locationId, 1);
		if (location == null ) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "location"));
			return respon;
		}
		locationRepository.delete(location);
		respon.setMessage(messageUtils.getMessage("I002", "location"));
		return respon;
	}

	public ApiResponse searchLocation(SearchDTO search) throws Exception {
		ApiResponse respon = new ApiResponse();
		List<LocationDTO> listDTO = new ArrayList<LocationDTO>();
		String keyword = search.getKeyword();
		if (StringUtils.isEmpty(keyword)) {// full
			keyword = "%%";
		} else {// start with keywork
			keyword = "%" + keyword + "%";
		}
		if (search.getMaxResult() == 0) {
			search.setMaxResult(10);
		}

		List<Location> listLocation = locationRepository.findByLocationNameAndIsActive(search.getTeamId(), keyword,
				search.getFirstResult(), search.getMaxResult(), 1);
		if (listLocation != null && !listLocation.isEmpty()) {
			for (Location loc : listLocation) {
				LocationDTO dto = convertToDTO(loc);
				listDTO.add(dto);
			}
		}
		respon.setData(listDTO);
		return respon;
	}

	public LocationDTO convertToDTO(Location loc) {
		LocationDTO dto = new LocationDTO();
		dto.setLocationId(loc.getLocationId());
		dto.setTeamId(loc.getTeamId());
		dto.setLocationName(loc.getLocationName());
		dto.setLocationAddress(loc.getLocationAddress());
		dto.setLocationLat(loc.getLocationLat().toString());
		dto.setLocationLng(loc.getLocationLng().toString());
		dto.setCreatedBy(loc.getCreatedby());
		dto.setCreatedDate(loc.getCreatedDate());
		dto.setUpdatedDate(loc.getUpdatedDate());
		return dto;
	}

	public Location convertToEntity(LocationDTO locationDTO) {
		Location loc = new Location();
		if (locationDTO.getLocationId() != null) {
			loc.setLocationId(locationDTO.getLocationId());
		} else {
			loc.setLocationId(null);
		}
		loc.setTeamId(locationDTO.getTeamId());
		loc.setLocationName(locationDTO.getLocationName());
		loc.setLocationAddress(locationDTO.getLocationAddress());
		loc.setLocationLat(new BigDecimal(locationDTO.getLocationLat()));
		loc.setLocationLng(new BigDecimal(locationDTO.getLocationLng()));
		return loc;
	}

}
