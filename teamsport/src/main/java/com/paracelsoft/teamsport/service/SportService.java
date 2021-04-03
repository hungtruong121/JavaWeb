package com.paracelsoft.teamsport.service;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paracelsoft.teamsport.util.MessageUtils;
import com.paracelsoft.teamsport.api.dto.SportDTO;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.entity.Sport;
import com.paracelsoft.teamsport.entity.SportAttribute;
import com.paracelsoft.teamsport.entity.SportPoint;
import com.paracelsoft.teamsport.entity.SportRound;
import com.paracelsoft.teamsport.repository.SportAttributeRepository;
import com.paracelsoft.teamsport.repository.SportPointRepository;
import com.paracelsoft.teamsport.repository.SportRepository;
import com.paracelsoft.teamsport.repository.SportRoundRepository;

@Transactional(rollbackFor = { Exception.class, ParseException.class })
@Service("sportService")
public class SportService {

	@Autowired
	SportRepository sportRepository;
	
	@Autowired
	SportRoundRepository sportRoundRepository;
	
	@Autowired
	SportPointRepository sportPointRepository;
	
	@Autowired
	SportAttributeRepository sportAttributeRepository;
	
	@Autowired
	MessageUtils messageUtils;
	
	/**
	 * 
	 * @param no
	 * @return List<SportDTO>
	 */
	public List<SportDTO> getListSportDTO() {
		List<SportDTO> result = new ArrayList<SportDTO>();
		
		List<Sport> listSport = sportRepository.findByIsActive(1);
		
		if(listSport != null && !listSport.isEmpty()) {
			for(Sport sport:listSport) {
				SportDTO dto = new SportDTO();
				BigInteger sportId = sport.getSportId();
				
				List<SportRound> sportRounds = sportRoundRepository.findBySportIdAndIsActive(sportId, 1);
				List<SportPoint> sportScorePenal = sportPointRepository.findBySportIdAndIsActive(sportId, 1);
				List<SportAttribute> sportAtrribute = sportAttributeRepository.findBySportIdAndIsActive(sportId, 1);
				
				dto.setSportId(sportId);
				dto.setSportName(sport.getSportName());
				dto.setPositionsLevels(sport.getPositions());
				dto.setCompetition(sport.getCompetition());
				dto.setMatchType(sport.getMatchType());
				dto.setMatchTypeValue(sport.getMatchTypeValue());
				dto.setGender(sport.getGender());
				dto.setAgeFrom(sport.getAgeFrom());
				dto.setAgeTo(sport.getAgeTo());
				dto.setDescription(sport.getDescription());
				dto.setCreatedDate(sport.getCreatedDate());
				dto.setUpdatedDate(sport.getUpdatedDate());
				dto.setSportRound(sportRounds);
				dto.setSportScorePenal(sportScorePenal);
				dto.setSportAttribute(sportAtrribute);
				
				result.add(dto);
			}
		}
		return result;
	}

	/**
	 * 
	 * @param sportId
	 * @return
	 */
	public ApiResponse removeSport(BigInteger sportId) {
		ApiResponse respon = new ApiResponse();
		Sport oldSport = sportRepository.findBySportIdAndIsActive(sportId, 1);
		
		if(oldSport == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001","sport"));
			return respon;
		}

		//delete sportRound Half/Set/Round
		sportRoundRepository.deleteOldSportRound(sportId);
		
		//delete sportPoint Score/Penal
		sportPointRepository.deleteOldSportPoint(sportId);
		
		//delete sportAttribute
		sportAttributeRepository.deleteOldSportAttribute(sportId);
		
		//delete Sport
		sportRepository.delete(oldSport);
		
		respon.setMessage(messageUtils.getMessage("I002","sport"));
		return respon;
	}
	
	/**
	 * 
	 * @des create or update sport
	 * @param SportDTO
	 * @return
	 */
	public ApiResponse updateSport(SportDTO sportDTO, BigInteger createdBy) {
		ApiResponse respon = new ApiResponse();
		Sport sport = new Sport();
		Date date = new Date();
		BigInteger sportId = sportDTO.getSportId();
		boolean isCreate = false;
		
		if(sportDTO.getSportId() != null) { //is updated
			Sport oldSport = sportRepository.findBySportIdAndIsActive(sportDTO.getSportId(), 1);
			
			if(oldSport == null) { //sport not exist
				respon.setSuccess(false);
				respon.setMessage(messageUtils.getMessage("E001","sport"));
				return respon;
			}
			
			// update sport
			sport = this.convertToEntity(sportDTO);
			sport.setCreatedby(oldSport.getCreatedby()); //no change
			sport.setCreatedDate(oldSport.getCreatedDate()); //no change
			sport.setUpdatedDate(date);
		}else {
			isCreate = true;
			
			sport = this.convertToEntity(sportDTO);
			sport.setCreatedby(createdBy);
			sport.setCreatedDate(date);
			sport.setUpdatedDate(date);
		}
		sport = sportRepository.save(sport);

		sportId = sport.getSportId(); // Get lai neu TH create

		if (!isCreate) {
			// delete sportRound Half/Set/Round
			sportRoundRepository.deleteOldSportRound(sportId);

			// delete sportPoint Score/Penal
			sportPointRepository.deleteOldSportPoint(sportId);

			// delete sportAttribute
			sportAttributeRepository.deleteOldSportAttribute(sportId);
		}
		
		// save sportRound Half/Set/Round
		if(sportDTO.getSportRound() != null && !sportDTO.getSportRound().isEmpty()) {
			this.saveSportRound(sportDTO.getSportRound(), createdBy, sportId, date);
		}

		// save sportPoint Score/Penal
		if (sportDTO.getSportScorePenal() != null && !sportDTO.getSportScorePenal().isEmpty()) {
			this.saveSportPoint(sportDTO.getSportScorePenal(), createdBy, sportId, date);
		}

		// save sportAttribute
		if (sportDTO.getSportAttribute() != null && !sportDTO.getSportAttribute().isEmpty()) {
			this.saveSportAttribute(sportDTO.getSportAttribute(), createdBy, sportId, date);
		}

		respon.setData(sport);
		return respon;
	}
	
	/**
	 * 
	 * @Des convert  SportDTO to entity
	 * @param SportDTO
	 * @return
	 */
	public Sport convertToEntity(SportDTO sportDTO) {
		Sport sport = new Sport();
		
		sport.setSportId(sportDTO.getSportId());
		sport.setSportName(sportDTO.getSportName());
		sport.setPositions(sportDTO.getPositionsLevels());
		sport.setCompetition(sportDTO.getCompetition());
		sport.setMatchType(sportDTO.getMatchType());
		sport.setMatchTypeValue(sportDTO.getMatchTypeValue());
		sport.setGender(sportDTO.getGender());
		sport.setAgeFrom(sportDTO.getAgeFrom());
		sport.setAgeTo(sportDTO.getAgeTo());
		sport.setDescription(sportDTO.getDescription());
		
		return sport;
	}
	
	/**
	 * 
	 * @Des save list sportRound
	 * @param sportRounds
	 */
	public void saveSportRound(List<SportRound> sportRounds, BigInteger createdBy, BigInteger sportId, Date toDate) {
		List<SportRound> entitys = new ArrayList<>();
		
		for(SportRound sportRound:sportRounds) {
			SportRound entity = new SportRound();
			
			entity.setSportRoundId(null);
			entity.setCreatedby(createdBy);
			entity.setSportId(sportId);
			entity.setSportRoundType(sportRound.getSportRoundType());
			entity.setSportRoundName(sportRound.getSportRoundName());
			entity.setSportRoundTime(sportRound.getSportRoundTime());
			entity.setCreatedDate(toDate);
			entity.setUpdatedDate(toDate);
			entitys.add(entity);
		}
		sportRoundRepository.saveAll(entitys);
	}
	
	/**
	 * 
	 * @Des save list sportPoint
	 * @param sportPoints
	 */
	public void saveSportPoint(List<SportPoint> sportPoints, BigInteger createdBy, BigInteger sportId, Date toDate) {
		List<SportPoint> entitys = new ArrayList<>();
		
		for(SportPoint sportPoint:sportPoints) {
			SportPoint entity = new SportPoint();
			
			entity.setSportPointId(null);
			entity.setCreatedby(createdBy);
			entity.setSportId(sportId);
			entity.setSportPointType(sportPoint.getSportPointType());
			entity.setSportPointName(sportPoint.getSportPointName());
			entity.setSportPointDescription(sportPoint.getSportPointDescription());
			entity.setSportPointValue(sportPoint.getSportPointValue());
			entity.setCreatedDate(toDate);
			entity.setUpdatedDate(toDate);
			entitys.add(entity);
		}
		sportPointRepository.saveAll(entitys);
	}
	
	/**
	 * 
	 * @Des save list sportAttribute
	 * @param sportAttributes
	 */
	public void saveSportAttribute(List<SportAttribute> sportAttributes, BigInteger createdBy, BigInteger sportId, Date toDate) {
		List<SportAttribute> entitys = new ArrayList<>();
		
		for(SportAttribute sportAttribute:sportAttributes) {
			SportAttribute entity = new SportAttribute();
			
			entity.setSportAttributeId(null);
			entity.setCreatedby(createdBy);
			entity.setSportId(sportId);
			entity.setSportAttributeName(sportAttribute.getSportAttributeName());
			entity.setSportAttributeDescription(sportAttribute.getSportAttributeDescription());
			entity.setCreatedDate(toDate);
			entity.setUpdatedDate(toDate);
			entitys.add(entity);
		}
		sportAttributeRepository.saveAll(entitys);
	}
	
	/**
	 * 
	 * @param no
	 * @return List<SportDTO>
	 */
	public List<Sport> getListSport() {
		return sportRepository.findByIsActive(1);
	}

}
