package com.paracelsoft.teamsport.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paracelsoft.teamsport.api.dto.TeamRankDTO;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.entity.TeamRank;
import com.paracelsoft.teamsport.entity.TeamRankPackagePrice;
import com.paracelsoft.teamsport.entity.Upload;
import com.paracelsoft.teamsport.repository.TeamRankPackagePriceRepository;
import com.paracelsoft.teamsport.repository.TeamRankRepository;
import com.paracelsoft.teamsport.repository.UploadRepository;
import com.paracelsoft.teamsport.util.MessageUtils;

@Transactional(rollbackFor = { Exception.class, ParseException.class })
@Service("rankService")
public class RankService {
	
	@Autowired
	TeamRankRepository teamRankRepository;
	
	@Autowired
	MessageUtils messageUtils;
	
	@Autowired
	UploadService uploadService;
	
	@Autowired
	TeamRankPackagePriceRepository teamRankPackagePriceRepository;
	
	@Autowired
	UploadRepository uploadRepository;

	/**
	 * @author TaoN
	 * @Des If the id exists it's mean update rank and the opposite creates a new rank
	 * @param TeamRankDTO, createdBy
	 * 
	 * @return
	 */
	public ApiResponse updateRank(TeamRankDTO teamRankDTO, BigInteger createdBy) {
		ApiResponse respon = new ApiResponse();
		TeamRank teamRank = new TeamRank();
		Date date = new Date();
		boolean isCreate = false;
		
			// Rank update
			if (teamRankDTO.getTeamRankId() != null) {
				TeamRank oldTeamRank = teamRankRepository.findByTeamRankIdAndIsActive(teamRankDTO.getTeamRankId(), 1);
				if (oldTeamRank == null) {
					respon.setSuccess(false);
					respon.setMessage(messageUtils.getMessage("E001", "rank"));
					return respon;
				}
				teamRank = this.convertToEntity(teamRankDTO, createdBy);
				respon.setMessage(messageUtils.getMessage("I003","rank"));
			} else {
				// Create new rank
				isCreate = true;
				teamRank = this.convertToEntity(teamRankDTO, createdBy);
				respon.setMessage(messageUtils.getMessage("I001","rank"));
			}
			teamRank = teamRankRepository.save(teamRank);
			if (!isCreate) {
				// Delete old rank package when user update
				teamRankPackagePriceRepository.deleteOldTeamRankPackagePrice(teamRank.getTeamRankId());
			}
			// Add rank package
			if (teamRankDTO.getTeamRankPackagePrices() != null && !teamRankDTO.getTeamRankPackagePrices().isEmpty()) {
				this.addTeamRankPackage(teamRankDTO.getTeamRankPackagePrices(), createdBy, teamRank.getTeamRankId(), date);
			}
			return respon;
	}

	/**
	 * @author TaoN
	 * @Des convert  RankDTO to entity
	 * @param TeamRankDTO
	 * @return
	 */
	public TeamRank convertToEntity(TeamRankDTO teamRankDTO, BigInteger createdby) {
		TeamRank teamRank = new TeamRank();
		Date date = new Date();
		
		teamRank.setTeamRankId(teamRankDTO.getTeamRankId());
		teamRank.setTeamRankName(teamRankDTO.getTeamRankName());
		teamRank.setTeamRankAvatar(teamRankDTO.getTeamRankAvatar());
		teamRank.setStorageCapacity(teamRankDTO.getStorageCapacity());
		teamRank.setTeamRankMemberLimit(teamRankDTO.getTeamRankMemberLimit());
		teamRank.setTeamRankDescription(teamRankDTO.getTeamRankDescription());
		teamRank.setCreatedby(createdby);
		teamRank.setCreatedDate(date);
		teamRank.setUpdatedDate(date);
		return teamRank;
	}
	
	/**
	 * @param TeamRankDTO
	 * @return
	 */
	public TeamRankPackagePrice convertToDTO(TeamRankDTO teamRankDTO, BigInteger createdby) {
		TeamRankPackagePrice teamRankPrice = new TeamRankPackagePrice();
		Date date = new Date();
		
		teamRankPrice.setRankPackagePriceId(teamRankDTO.getRankPackagePriceId());
		teamRankPrice.setTeamRankId(teamRankDTO.getTeamRankId());
		teamRankPrice.setRankPackagePriceValue(teamRankDTO.getRankPackagePriceValue());
		teamRankPrice.setRankPackagePriceUnit(teamRankDTO.getRankPackagePriceUnit());
		teamRankPrice.setRankPackagePriceTime(teamRankDTO.getRankPackagePriceTime());
		teamRankPrice.setCreatedby(createdby);
		teamRankPrice.setCreatedDate(date);
		teamRankPrice.setUpdatedDate(date);
		return teamRankPrice;
	}
	
	/**
	 * @author TaoN
	 * @Des Delete rank and also delete rank package
	 * @param rankId
	 * @return
	 */
	public ApiResponse removeRank(BigInteger rankId) {
		ApiResponse respon = new ApiResponse();
		TeamRank oldRank = teamRankRepository.findByTeamRankIdAndIsActive(rankId, 1);
		
		if (oldRank == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001","rank"));
			return respon;
		}
		// get id image
		Upload uploadId = uploadRepository.findByUploadId(oldRank.getTeamRankAvatar());
		
		if (uploadId == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "photo"));
			return respon;
		} 
		// Delete Rank (rank package, rank, image of rank)
		teamRankPackagePriceRepository.deleteOldTeamRankPackagePrice(rankId);
		teamRankRepository.delete(oldRank);
		uploadRepository.deleteById(oldRank.getTeamRankAvatar());
		respon.setMessage(messageUtils.getMessage("I002","rank"));
		return respon;
	}

	/**
	 * @author TaoN
	 * @Des Using for list ranks
	 * @return
	 */
	public List<TeamRankDTO> getListRank() {
		List<TeamRankDTO> result = new ArrayList<TeamRankDTO>();
		List<TeamRank> listRanks = teamRankRepository.findByIsActive(1);
		
		if(listRanks != null && !listRanks.isEmpty()) {
			for(TeamRank teamRank:listRanks) {
				TeamRankDTO dto = new TeamRankDTO();
				List<TeamRankPackagePrice> rankPrice = teamRankPackagePriceRepository.findByTeamRankId(teamRank.getTeamRankId());
				dto.setTeamRankId(teamRank.getTeamRankId());
				dto.setTeamRankName(teamRank.getTeamRankName());
				dto.setTeamRankAvatar(teamRank.getTeamRankAvatar());
				dto.setStorageCapacity(teamRank.getStorageCapacity());
				dto.setTeamRankMemberLimit(teamRank.getTeamRankMemberLimit());
				dto.setTeamRankDescription(teamRank.getTeamRankDescription());
				dto.setCreatedDate(teamRank.getCreatedDate());
				dto.setUpdatedDate(teamRank.getUpdatedDate());
				dto.setTeamRankPackagePrices(rankPrice);
				result.add(dto);
			}
		}
		return result;
	}
	
	/**
	 * @author TaoN
	 * @Des This is the function of saving info of the ranking packages 
	 * @param list teamRankPackage, createdBy, teamRankId, toDate
	 * @return
	 */
	public void addTeamRankPackage(List<TeamRankPackagePrice> teamRankPackage, BigInteger createdBy, BigInteger teamRankId, Date toDate) {
		List<TeamRankPackagePrice> entitys = new ArrayList<>();
		
		for(TeamRankPackagePrice teamRankPackagePrice:teamRankPackage) {
			TeamRankPackagePrice entity = new TeamRankPackagePrice();
			entity.setRankPackagePriceId(null);
			entity.setCreatedby(createdBy);
			entity.setTeamRankId(teamRankId);
			entity.setRankPackagePriceValue(teamRankPackagePrice.getRankPackagePriceValue());
			entity.setRankPackagePriceUnit(teamRankPackagePrice.getRankPackagePriceUnit());
			entity.setRankPackagePriceTime(teamRankPackagePrice.getRankPackagePriceTime());
			entity.setCreatedDate(toDate);
			entity.setUpdatedDate(toDate);
			entitys.add(entity);
		}
		teamRankPackagePriceRepository.saveAll(entitys);
	}
	
	/**
	 * add avatar
	 * @param uploadId
	 * @param teamId
	 * @return
	 */
	public ApiResponse updateAvatar(TeamRankDTO teamRankDTO) {
		ApiResponse respon = new ApiResponse();

		TeamRank teamRank = teamRankRepository.findByTeamRankIdAndIsActive(teamRankDTO.getTeamRankId(), 1);
		if (teamRank == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "rank"));
			return respon;
		}
		uploadService.deleteByUploadId(teamRank.getTeamRankAvatar());
		teamRank.setTeamRankAvatar(teamRankDTO.getUploadId());
		teamRank.setUpdatedDate(new Date());
		teamRank = teamRankRepository.save(teamRank);
		return respon;
	}
	
	/**
	 * @author TaoN
	 * Name: get Rank Package Price By Id
	 * @param teamRankId
	 * @return
	 */
	public ApiResponse listRankPackageById(BigInteger teamRankId) {
		ApiResponse respon = new ApiResponse();

		List<TeamRankDTO> listRankPackage = new ArrayList<TeamRankDTO>();
		List<Map<String, Object>> listTeamPackage = teamRankPackagePriceRepository.findPackagePriceByTeamRankId(teamRankId);
		if (listTeamPackage != null && !listTeamPackage.isEmpty()) {
			for (Map<String, Object> obj : listTeamPackage) {
				TeamRankDTO teamRankDTO = new TeamRankDTO();
				teamRankDTO.setRankPackagePriceValue(new BigDecimal(obj.get("rank_package_price_value").toString()));
				teamRankDTO.setRankPackagePriceUnit(obj.get("rank_package_price_unit").toString());
				teamRankDTO.setRankPackagePriceTime(new BigDecimal(obj.get("rank_package_price_time").toString()));
				listRankPackage.add(teamRankDTO);
			}
		}
		respon.setData(listRankPackage);
		return respon;
	}
}
