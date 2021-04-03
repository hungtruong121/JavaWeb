package com.paracelsoft.teamsport.service;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paracelsoft.teamsport.api.dto.PromotionDTO;
import com.paracelsoft.teamsport.api.dto.TeamRankDTO;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.entity.PromotionInclude;
import com.paracelsoft.teamsport.entity.Team;
import com.paracelsoft.teamsport.entity.TeamRank;
import com.paracelsoft.teamsport.entity.Promotion;
import com.paracelsoft.teamsport.entity.User;
import com.paracelsoft.teamsport.repository.PromotionIncludeRepository;
import com.paracelsoft.teamsport.repository.PromotionRepository;
import com.paracelsoft.teamsport.repository.TeamRankRepository;
import com.paracelsoft.teamsport.repository.TeamRepository;
import com.paracelsoft.teamsport.util.ConstantUtils;
import com.paracelsoft.teamsport.util.DateUtil;
import com.paracelsoft.teamsport.util.MessageUtils;

@Transactional(rollbackFor = { Exception.class, ParseException.class })
@Service("promotionService")
public class PromotionService {
	
	@Autowired
	UserService userService;

	@Autowired
	PromotionRepository promotionRepository;
	
	@Autowired
	PromotionIncludeRepository promotionIncludeRepository;
	
	@Autowired
	TeamRepository teamRepository;
	
	@Autowired
	TeamRankRepository teamRankRepository;
	
	@Autowired
	MessageUtils messageUtils;

	/**
	 * 
	 * @Des create/update Promotion
	 * @param PromotionDTO
	 * @return
	 * @throws ParseException
	 */
	public ApiResponse updatePromotion(PromotionDTO promotionDTO, User user) throws ParseException {
		ApiResponse respon = new ApiResponse();
		
		// Check user creating is System Admin
		if (new BigInteger(ConstantUtils.ROLE_TYPE_ID.ADMIN.getValue()).compareTo(user.getUserRoleId()) != 0) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E015"));
			return respon;
		}
		
		Promotion promotion = new Promotion();
		Date date = new Date();

		if (promotionDTO.getPromotionId() != null) { // is updated
			Promotion oldPromotion = promotionRepository.findByPromotionIdAndIsActive(promotionDTO.getPromotionId(), 1);
			if (oldPromotion == null) { // oldPromotion not exist
				respon.setSuccess(false);
				respon.setMessage(messageUtils.getMessage("E001", "promotion"));
				return respon;
			}

			// Delete all oldPromotionInclude
			List<PromotionInclude> listOldPromotionInclude = promotionIncludeRepository.findByPromotionIdAndIsActive(oldPromotion.getPromotionId(), 1);
			if (listOldPromotionInclude != null && !listOldPromotionInclude.isEmpty()) {
				promotionIncludeRepository.deleteAll(listOldPromotionInclude);
			}
			
			// update
			promotion = this.convertToEntity(promotionDTO);
			promotion.setPromotionCode(oldPromotion.getPromotionCode()); // No change
			promotion.setCreatedBy(oldPromotion.getCreatedBy());
			promotion.setCreatedDate(oldPromotion.getCreatedDate());
			promotion.setUpdatedDate(date);
			respon.setMessage(messageUtils.getMessage("I003", "promotion"));
		} else { // create
			// Check PromotionCode exits in DB
			if (promotionRepository.findByPromotionCode(promotionDTO.getPromotionCode()).size() != 0) {
				respon.setSuccess(false);
				respon.setMessage(messageUtils.getMessage("E005", "promotionCode"));
				return respon;
			}
			
			promotion = this.convertToEntity(promotionDTO);
			promotion.setCreatedBy(user.getUserId());
			promotion.setCreatedDate(date);
			promotion.setUpdatedDate(date);
			respon.setMessage(messageUtils.getMessage("I001", "promotion"));
		}
		promotion = promotionRepository.save(promotion);

		// Add list team apply for promotion
		if (promotionDTO.getListTeamApplyPromo() != null && !promotionDTO.getListTeamApplyPromo().isEmpty()) {
			// Create PromotionInclude follow numbers of teams chosen
			List<PromotionInclude> listIncludeTeamId = new ArrayList<>();
			
			for (BigInteger team : promotionDTO.getListTeamApplyPromo()) {
				PromotionInclude promoIncludeTeam = new PromotionInclude();

				// Check team is exist
				Team thisTeam = teamRepository.findByTeamIdAndIsActive(team, 1);
				if (thisTeam != null) {
					promoIncludeTeam.setPromotionId(promotion.getPromotionId());
					promoIncludeTeam.setTeamId(team);
					promoIncludeTeam.setPromotionObject(null);
					promoIncludeTeam.setCreatedBy(user.getUserId());
					promoIncludeTeam.setCreatedDate(date);
					promoIncludeTeam.setUpdatedDate(date);
					listIncludeTeamId.add(promoIncludeTeam);
				}
			}
			promotionIncludeRepository.saveAll(listIncludeTeamId);
		}

		// Add list promotionOjects(team_rank_id) apply for promotion
		if (promotionDTO.getListPromotionObject() != null && !promotionDTO.getListPromotionObject().isEmpty()) {
			// Create PromotionInclude follow numbers of teams chosen
			List<PromotionInclude> listIncludePromoObject = new ArrayList<>();

			for (BigInteger rank : promotionDTO.getListPromotionObject()) {
				PromotionInclude promoIncludeRank = new PromotionInclude();

				// Check rank is exist
				TeamRank thisRank = teamRankRepository.findByTeamRankIdAndIsActive(rank, 1);
				if (thisRank != null || rank.compareTo(new BigInteger(ConstantUtils.Promotion_Object_All.ALL.getValue())) == 0){
					promoIncludeRank.setPromotionId(promotion.getPromotionId());
					promoIncludeRank.setTeamId(null);
					promoIncludeRank.setPromotionObject(rank);
					promoIncludeRank.setCreatedBy(user.getUserId());
					promoIncludeRank.setCreatedDate(date);
					promoIncludeRank.setUpdatedDate(date);
					listIncludePromoObject.add(promoIncludeRank);
				}
			}
			promotionIncludeRepository.saveAll(listIncludePromoObject);
		}
		respon.setData(promotion);
		return respon;
	}
	
	/**
	 * 
	 * @Des convert PromotionDTO to entity
	 * @param PromotionDTO
	 * @return
	 * @throws ParseException 
	 */
	private Promotion convertToEntity(PromotionDTO promotionDTO) throws ParseException {
		Promotion promotion = new Promotion();
		Date dateBegin = DateUtil.fomatTimeZoneToUTC(promotionDTO.getBeginDate(),
				DateUtil.PT_YYYY_MM_DD_HH_MM_SS, DateUtil.PT_YYYY_MM_DD_HH_MM_SS, "");
		Date dateEnd = DateUtil.fomatTimeZoneToUTC(promotionDTO.getEndDate(),
				DateUtil.PT_YYYY_MM_DD_HH_MM_SS, DateUtil.PT_YYYY_MM_DD_HH_MM_SS, "");
		promotion.setBeginDate(dateBegin);
		promotion.setEndDate(dateEnd);
		// Check status promotion
		if (DateUtil.compareToDatimeNow(dateBegin) <= 0 && DateUtil.compareToDatimeNow(dateEnd) >= 0) { // đang diễn ra khuyến mãi => 1:Active
			promotion.setStatusId(new BigInteger(ConstantUtils.Promotion_Status.ACTIVE.getValue()));
		} else { // chưa diễn ra hoặc đã hết hạn khuyến mãi
			promotion.setStatusId(new BigInteger(ConstantUtils.Promotion_Status.IN_ACTIVE.getValue()));
		}
			
		promotion.setPromotionId(promotionDTO.getPromotionId());
		promotion.setPromotionCode(promotionDTO.getPromotionCode());
		promotion.setPromotionTitle(promotionDTO.getPromotionTitle());
		promotion.setPromotionDescription(promotionDTO.getPromotionDescription());
		promotion.setIsUnlimitedTeam(promotionDTO.getIsUnlimitedTeam());
		promotion.setPromotionType(promotionDTO.getPromotionType());
		promotion.setPromotionValue(promotionDTO.getPromotionValue());
		promotion.setIncreaseDuration(promotionDTO.getIncreaseDuration());
		
		return promotion;
	}
	
	/**
	 * 
	 * @Des convert entity to PromotionDTO
	 * @param Promotion
	 * @return
	 * @throws ParseException 
	 */
	private PromotionDTO convertEntityToDTO(Promotion oldPromotion) throws ParseException {
		PromotionDTO dto = new PromotionDTO();
		List<PromotionInclude> listPromoInclude = promotionIncludeRepository.findByPromotionIdAndIsActive(oldPromotion.getPromotionId(), 1);
		List<BigInteger> listTeamIdPromoInclude = new ArrayList<BigInteger>();
		List<BigInteger> listPromotionObject = new ArrayList<BigInteger>();
		List<TeamRankDTO> promotionObjects = new ArrayList<TeamRankDTO>();
		List<Team> teamObjects = new ArrayList<Team>();
		
		if (listPromoInclude != null && !listPromoInclude.isEmpty()) {
			for (PromotionInclude promoInclude : listPromoInclude) {
				if (promoInclude.getTeamId() != null) {
					listTeamIdPromoInclude.add(promoInclude.getTeamId());
					Team team = teamRepository.findByTeamIdAndIsActive(promoInclude.getTeamId(), 1);
					if (team != null) {
						teamObjects.add(team);
					}
				} else {
					listPromotionObject.add(promoInclude.getPromotionObject());
					TeamRank teamRank = teamRankRepository.findByTeamRankIdAndIsActive(promoInclude.getPromotionObject(), 1);
					TeamRankDTO rankDTO = new TeamRankDTO();
					
					rankDTO.setTeamRankId(promoInclude.getPromotionObject());
					if (teamRank != null) {
						rankDTO.setTeamRankName(teamRank.getTeamRankName());
						
					}
					if (new BigInteger(ConstantUtils.Promotion_Object_All.ALL.getValue())
							.compareTo(promoInclude.getPromotionObject()) == 0) {
						rankDTO.setTeamRankName(ConstantUtils.Promotion_Object_All.ALL.getDescription());
					}
					promotionObjects.add(rankDTO);
				}
			}
		}
		dto.setListTeamApplyPromo(listTeamIdPromoInclude);
		dto.setListPromotionObject(listPromotionObject);
		dto.setTeamObjects(teamObjects);
		dto.setPromotionObjects(promotionObjects);
		dto.setPromotionId(oldPromotion.getPromotionId());
		dto.setPromotionCode(oldPromotion.getPromotionCode());
		dto.setPromotionTitle(oldPromotion.getPromotionTitle());
		dto.setPromotionDescription(oldPromotion.getPromotionDescription());
		dto.setPromotionStatus(oldPromotion.getStatusId());
		dto.setIsUnlimitedTeam(oldPromotion.getIsUnlimitedTeam());
		dto.setPromotionType(oldPromotion.getPromotionType());
		dto.setPromotionValue(oldPromotion.getPromotionValue());
		dto.setIncreaseDuration(oldPromotion.getIncreaseDuration());
		dto.setDateBeginDate(oldPromotion.getBeginDate());
		dto.setDateEndDate(oldPromotion.getEndDate());
		dto.setCreatedDate(oldPromotion.getCreatedDate());
		dto.setUpdatedDate(oldPromotion.getUpdatedDate());
		
		return dto;
	}

	/**
	 * @Des Get promotion detail
	 * @param promotionId
	 * @return PromotionDTO
	 */
	public ApiResponse getPromotionById(BigInteger promotionId) throws ParseException {
		ApiResponse respon = new ApiResponse();
		
		Promotion oldPromotion = promotionRepository.findByPromotionIdAndIsActive(promotionId, 1);
		if (oldPromotion == null) { // oldPromotion not exist
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "promotion"));
			return respon;
		}
		
		PromotionDTO dto = convertEntityToDTO(oldPromotion);
			
		respon.setData(dto);
		return respon;
	}

	/**
	 * @Des Get list promotion
	 * @param no
	 * @return List<PromotionDTO>
	 * @throws ParseException 
	 */
	public ApiResponse getListPromotion() throws ParseException {
		ApiResponse respon = new ApiResponse();
		List<PromotionDTO> result = new ArrayList<PromotionDTO>();
		List<Promotion> listPromotion = promotionRepository.findByIsActive(1);

		if (listPromotion != null && !listPromotion.isEmpty()) {
			for (Promotion promotion : listPromotion) {
				PromotionDTO dto = new PromotionDTO();
				dto = convertEntityToDTO(promotion);
				result.add(dto);
			}
		}

		respon.setData(result);
		return respon;
	}

	public ApiResponse deletePromotion(BigInteger promotionId, User user) {
		ApiResponse respon = new ApiResponse();
		
		// Check user Deleting is System Admin
		if (new BigInteger(ConstantUtils.ROLE_TYPE_ID.ADMIN.getValue()).compareTo(user.getUserRoleId()) != 0) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E015"));
			return respon;
		}
		
		Promotion oldPromotion = promotionRepository.findByPromotionIdAndIsActive(promotionId, 1);
		if (oldPromotion == null) { // oldPromotion not exist
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "promotion"));
			return respon;
		}

		// Delete promotion by id
		promotionRepository.delete(oldPromotion);
		
		// Delete all oldPromotionInclude
		List<PromotionInclude> listOldPromotionInclude = promotionIncludeRepository.findByPromotionId(oldPromotion.getPromotionId());
		if (listOldPromotionInclude != null && !listOldPromotionInclude.isEmpty()) {
			promotionIncludeRepository.deleteAll(listOldPromotionInclude);
		}
		
		respon.setMessage(messageUtils.getMessage("I002","promotion"));
		return respon;
	}
}
