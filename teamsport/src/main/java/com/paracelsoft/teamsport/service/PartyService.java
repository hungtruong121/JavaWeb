//package com.paracelsoft.teamsport.service;
//
//import java.text.ParseException;
//import java.util.Date;
//import java.util.Optional;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.paracelsoft.teamsport.api.dto.PartyDTO;
//import com.paracelsoft.teamsport.entity.Party;
//import com.paracelsoft.teamsport.entity.PartyLogin;
//import com.paracelsoft.teamsport.entity.PartyRole;
//import com.paracelsoft.teamsport.repository.PartyLoginRepository;
//import com.paracelsoft.teamsport.repository.PartyRepository;
//import com.paracelsoft.teamsport.repository.PartyRoleRepository;
//import com.paracelsoft.teamsport.util.ConstantUtils;
//import com.paracelsoft.teamsport.util.DateUtil;
//import com.paracelsoft.teamsport.util.GenerateIDUtils;
//import com.paracelsoft.teamsport.util.HashCrypt;
//import com.paracelsoft.teamsport.util.MessageUtils;
//
//@Transactional(rollbackFor = { Exception.class, ParseException.class })
//@Service("partyService")
//public class PartyService {

//	@Autowired
//	PartyRepository partyRepository;
//
//	@Autowired
//	PartyLoginRepository userLoginRepository;
//
//	@Autowired
//	PartyRoleRepository partyRoleRepository;
	
//	@Autowired
//	MessageUtils messageUtils

	/**
	 * 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
//	public PartyLogin getUserLoginByUserName(String userName) throws Exception {
//		Optional<PartyLogin> userLogin = userLoginRepository.findById(userName);
//		return userLogin.isPresent() ? userLogin.get() : null;
//	}

	/**
	 * 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
//	public Party getPartyByUserName(String userName) throws Exception {
//		Optional<PartyLogin> userLogin = userLoginRepository.findById(userName);
//		if (!userLogin.isPresent()) {
//			return null;
//		}
//		Optional<Party> party = partyRepository.findById(userLogin.get().getPartyId());
//		return party.isPresent() ? party.get() : null;
//	}
	
	/**
	 * 
	 * @param partyId
	 * @return
	 * @throws Exception
	 */
//	public PartyRole getPartyRoleByPartyId(String partyId) throws Exception {
//		return this.partyRoleRepository.findByPartyId(partyId);
//	}
	
	/**
	 * @param partyDTO
	 * @return
	 * @throws Exception
	 */
//	public PartyDTO createCustomer(PartyDTO partyDTO) throws Exception {
		// create party
//		if (partyDTO == null) {
//			throw new Exception(messageUtils.getMessage("E008", "Customer"));
//		}
//		
//		if (StringUtils.isEmpty(partyDTO.getCustomerId())){
//			String partyId = GenerateIDUtils.getUUID(partyDTO.getPhone());
			//check partyId is exist
//			boolean isExist = false;
//			while(!isExist) {
//				Party checkParty = partyRepository.findByPartyId(partyId);
//				if(checkParty != null) {
//					partyId = GenerateIDUtils.getUUID(partyDTO.getPhone());
//				}else {
//					isExist = true;
//				}
//			}
			//generate new party Id
//			partyDTO.setCustomerId(partyId);
//		}
		//check party exist
//		Party party = partyRepository.findByPartyId(partyDTO.getCustomerId());
//		if(party == null) {
			//create new 
//			party = this.createParty(partyDTO.getCustomerId(), ConstantUtils.PartyType.PERSON.getValue(),
//					ConstantUtils.Party_Status.PARTY_ENABLED.getValue(), "", "");
			//create user login 
//			if(party != null) {
//				if(this.generateUserLogin(partyDTO) == null) {
//					throw new Exception(messageUtils.getMessage("E003", "Customer"));
//				}
//			}
//		}
		//updated party
//		if(!StringUtils.isEmpty(partyDTO.getFirstName())) {
//			Date toDate = new Date();
//			party.setAddress(partyDTO.getAddress());
//			party.setAvatar(partyDTO.getAvatar());
//			party.setBirthDate(DateUtil.formatStringToDate(partyDTO.getBirthDate(), DateUtil.PT_DD_MM_YYYY, DateUtil.PT_YYYY_MM_DD));
//			party.setFirstName(partyDTO.getFirstName());
//			party.setGender(partyDTO.getGender());
//			party.setLastName(partyDTO.getLastName());
//			party.setMail(partyDTO.getEmail());
//			party.setPhoneNumber(partyDTO.getPhone());
//			party.setUpdatedDate(toDate);
//			party.setCreatedDate(toDate);
			//updated party
//			party = partyRepository.save(party);
//		}
		
		//create party role
//		this.generatePartyRoleByPartyId(party.getPartyId(), ConstantUtils.ROLE_TYPE_ID.CUSTOMER.getValue());
//		
//		return partyDTO;
//	}

	/**
	 * 
	 * @param partyId
	 * @return
	 * @throws Exception
	 */
//	public PartyDTO profileCustomer(String partyId) throws Exception {
//		Party party = partyRepository.findByPartyId(partyId);
//		if(party == null) return null;
//		return this.convertMapToDTO(party);
//	}
	/**
	 * 
	 * @param username
	 * @return
	 */
//	public PartyRole findByUserName(String username) throws Exception {
//		return partyRoleRepository.findByUserName(username);
//	}
	
	/**s
	 * 
	 * @param partyGeneral
	 * @return
	 * @throws Exception
	 */
//	protected PartyDTO convertMapToDTO(Party partyGeneral)throws Exception {
//		PartyDTO partyDTO = new PartyDTO();
//		partyDTO.setCustomerId(partyGeneral.getPartyId());
//		partyDTO.setAddress(partyGeneral.getAddress());
//		partyDTO.setCity(partyGeneral.getCity());
//		String birthDate = DateUtil.getFormatDate(partyGeneral.getBirthDate(), DateUtil.PT_DD_MM_YYYY);
//		if(!StringUtils.isEmpty(birthDate)) {
//			partyDTO.setAge(DateUtil.CalculatorAge(birthDate));
//		}
//		partyDTO.setFirstName(partyGeneral.getFirstName());
//		partyDTO.setLastName(partyGeneral.getLastName());
//		partyDTO.setPhone(partyGeneral.getPhoneNumber());
//		partyDTO.setGender(partyGeneral.getGender());
//		partyDTO.setBirthDate(birthDate);
//		partyDTO.setEmail(partyGeneral.getMail());
//		partyDTO.setAvatar(partyGeneral.getAvatar());
//		return partyDTO;
//	}
	
	/**
	 * @param partyId
	 * @param roleTypeId
	 * @return
	 * @throws Exception
	 */
//	protected PartyRole generatePartyRoleByPartyId(String partyId, String roleTypeId) throws Exception {
		// create PartyRolex
//		PartyRole partyRole = new PartyRole();
//		partyRole.setPartyId(partyId);
//		partyRole.setRoleTypeId(roleTypeId);
//		Date toDate = new Date();
//		partyRole.setCreatedDate(toDate);
//		partyRole.setUpdatedDate(toDate);
//		partyRoleRepository.save(partyRole);
//		return partyRole;
//	}
	
	/**
	 * @param benhNhanId
	 * @param partyTypedId
	 * @param statusId
	 * @param Description
	 * @return
	 */
//	protected Party createParty(String partyId, String partyTypedId, String statusId, String Description,
//			String phoneNumber) {
//		Party party = new Party();
//		party.setPartyId(partyId);
//		party.setPartyTypeId(partyTypedId);
//		party.setStatusId(statusId);
//		party.setDescription(Description);
//		Date toDate = new Date();
//		party.setCreatedDate(toDate);
//		party.setUpdatedDate(toDate);
//		party = partyRepository.save(party);
//		return party;
//	}
	
	/**
	 * 
	 * @param party
	 * @return
	 * @throws Exception
	 */
//	protected PartyLogin generateUserLogin(PartyDTO party) throws Exception {
//		PartyLogin userLogin = new PartyLogin();
		//check username is exist
//		if(this.getPartyByUserName(party.getUsername()) != null) {
//			throw new Exception(messageUtils.getMessage("E008", "User Login"));
//		}
//		Date toDate = new Date();
//		userLogin.setUserLoginId(party.getUsername());
//		userLogin.setCreatedDate(toDate);
//		userLogin.setUpdatedDate(toDate);
//		userLogin.setPartyId(party.getCustomerId());
//		userLogin.setCurrentPassword(HashCrypt.cryptUTF8(null, null, party.getPassword()));
//		
//		return userLoginRepository.save(userLogin);
//	}
	
//}
