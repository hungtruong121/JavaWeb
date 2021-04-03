package com.paracelsoft.teamsport.service;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paracelsoft.teamsport.api.dto.SearchDTO;
import com.paracelsoft.teamsport.api.dto.SearchTodoListDTO;
import com.paracelsoft.teamsport.api.dto.TodoListDTO;
import com.paracelsoft.teamsport.api.dto.UserDTO;
import com.paracelsoft.teamsport.dto.ApiResponse;
import com.paracelsoft.teamsport.entity.TodoListInclude;
import com.paracelsoft.teamsport.entity.TodoListIncludePK;
import com.paracelsoft.teamsport.entity.User;
import com.paracelsoft.teamsport.entity.TeamMember;
import com.paracelsoft.teamsport.entity.TodoList;
import com.paracelsoft.teamsport.repository.TeamMemberRepository;
import com.paracelsoft.teamsport.repository.TeamRepository;
import com.paracelsoft.teamsport.repository.TodoListIncludeRepository;
import com.paracelsoft.teamsport.repository.TodoListRepository;
import com.paracelsoft.teamsport.repository.UserRepository;
import com.paracelsoft.teamsport.util.ConstantUtils;
import com.paracelsoft.teamsport.util.DateUtil;
import com.paracelsoft.teamsport.util.MessageUtils;

@Transactional(rollbackFor = { Exception.class, ParseException.class })
@Service("todoListService")
public class TodoListService {
	@Autowired
	TodoListRepository todoListRepository;
	
	@Autowired
	TodoListIncludeRepository todoListIncludeRepository;
	
	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	TeamRepository teamRepository;

	@Autowired
	TeamMemberRepository teamMemberRepository;

	@Autowired
	MessageUtils messageUtils;

	@Autowired
	NotificationService notificationService;

	/**
	 * 
	 * @Des create/update TodoList
	 * @param TodoListDTO
	 * @return
	 * @throws ParseException
	 */
	public ApiResponse updateTodoList(TodoListDTO todoListDTO, BigInteger createdBy) throws ParseException {
		ApiResponse respon = new ApiResponse();
		TodoList todoList = new TodoList();
		Date date = new Date();
		boolean isCreate = true;
		
		if (todoListDTO.getTodoListId() != null) { // is updated
			isCreate = false;
			TodoList oldTodoList = todoListRepository.findByTodoListIdAndIsActive(todoListDTO.getTodoListId(), 1);
			if (oldTodoList == null) { // oldTodoList not exist
				respon.setSuccess(false);
				respon.setMessage(messageUtils.getMessage("E001", "todoList"));
				return respon;
			}

			// Delete all TodoListInclude is Doing task
			List<TodoListInclude> listUserIncludeDoing = todoListIncludeRepository.findById_TodoListIdAndStatusId(
					oldTodoList.getTodoListId(), new BigInteger(ConstantUtils.TodoList_Status.DOING.getValue()));
			if (listUserIncludeDoing != null && !listUserIncludeDoing.isEmpty()) {
				todoListIncludeRepository.deleteAll(listUserIncludeDoing);
			}
			
			// update
			todoList = this.convertToEntity(todoListDTO, todoListDTO.getTimeZone());
			todoList.setTodoListId(oldTodoList.getTodoListId()); // no change
			todoList.setCreatedby(oldTodoList.getCreatedby()); // no change
			todoList.setCreatedDate(oldTodoList.getCreatedDate()); // no change
			todoList.setUpdatedDate(date);
			respon.setMessage(messageUtils.getMessage("I003", "todoList"));
		} else { // create
			todoList = this.convertToEntity(todoListDTO, todoListDTO.getTimeZone());
			todoList.setCreatedby(createdBy);
			todoList.setCreatedDate(date);
			todoList.setUpdatedDate(date);
			respon.setMessage(messageUtils.getMessage("I001", "todoList"));
		}
		todoList = todoListRepository.save(todoList);
		
		List<TeamMember> listMemberInTeam = teamMemberRepository.findById_TeamIdAndIsActive(todoListDTO.getTeamId(), 1);
		if (ConstantUtils.TodoList_Type.SINGLE_TASK.getValue().equals(todoListDTO.getTodoListType())) {
			// Save member assign for Single task
			this.saveTodoListIncludeUser(todoListDTO, todoList.getTodoListId(), todoListDTO.getTeamId(), listMemberInTeam, isCreate, createdBy, date);
		} else { // Group task
			List<TodoListDTO> listChild = todoListDTO.getTodoListChild();
			if (listChild != null) {
				for (TodoListDTO todo : listChild) {
					TodoList todoListChild = new TodoList();
					boolean isCreateChild = true;
					
					if (todo.getTodoListId() != null) { // is updated child
						isCreateChild = false;
						TodoList oldTodoListChild = todoListRepository.findByTodoListIdAndIsActive(todo.getTodoListId(), 1);
						if (oldTodoListChild != null) {
							// Delete all TodoListChildInclude is Doing
							List<TodoListInclude> listUserIncludeChild = todoListIncludeRepository.findById_TodoListIdAndStatusId(oldTodoListChild.getTodoListId(),
											new BigInteger(ConstantUtils.TodoList_Status.DOING.getValue()));
							if (listUserIncludeChild != null && !listUserIncludeChild.isEmpty()) {
								todoListIncludeRepository.deleteAll(listUserIncludeChild);
							}
							
							// update
							todoListChild = this.convertToEntity(todo, todoListDTO.getTimeZone());
							todoListChild.setTodoListId(oldTodoListChild.getTodoListId()); // no change
							todoListChild.setTodoListParentId(todoList.getTodoListId()); 
							todoListChild.setTeamId(todoList.getTeamId());
							todoListChild.setTodoLisType(todoList.getTodoLisType()); // SINGLE_TASK || GROUP_TASK
							todoListChild.setCreatedby(oldTodoListChild.getCreatedby()); // no change
							todoListChild.setCreatedDate(oldTodoListChild.getCreatedDate()); // no change
							todoListChild.setUpdatedDate(date);
						}
					} else {  // create child
						todoListChild = this.convertToEntity(todo, todoListDTO.getTimeZone());
						todoListChild.setTodoListParentId(todoList.getTodoListId());
						todoListChild.setTeamId(todoList.getTeamId());
						todoListChild.setTodoLisType(todoList.getTodoLisType()); // SINGLE_TASK || GROUP_TASK
						todoListChild.setCreatedby(createdBy);
						todoListChild.setCreatedDate(date);
						todoListChild.setUpdatedDate(date);
					}
					todoListChild = todoListRepository.save(todoListChild);
					
					// Save member assign for Group task
					this.saveTodoListIncludeUser(todo, todoListChild.getTodoListId(), todoListDTO.getTeamId(), listMemberInTeam, isCreateChild, createdBy, date);
				}
			}
		}

		respon.setData(todoList);
		return respon;
	}

	private TodoList convertToEntity(TodoListDTO todoListDTO, String timeZone) {
		TodoList todoList = new TodoList();

		todoList.setTodoListId(null);
		todoList.setTeamId(todoListDTO.getTeamId());
		todoList.setTodoListParentId(null);
		todoList.setPrivacyId(todoListDTO.getPrivacyId()); // 2:MY_TEAM || 3:INCLUDE || 4:ONLY_ME
		todoList.setTodoListTitle(todoListDTO.getTodoListTitle());
		todoList.setTodoLisType(todoListDTO.getTodoListType()); // SINGLE_TASK || GROUP_TASK
		todoList.setTodoListDeadline(DateUtil.fomatTimeZoneToUTC(todoListDTO.getTodoListDeadline(),
				DateUtil.PT_YYYY_MM_DD_HH_MM_SS, DateUtil.PT_YYYY_MM_DD_HH_MM_SS, timeZone));
		todoList.setTodoListNotice(todoListDTO.getTodoListNotice());

		return todoList;
	}
	
	private TodoListInclude convertToEntityInclude(BigInteger todoListId, BigInteger userId, Date date,
			BigInteger createdBy) {
		TodoListInclude unserInclude = new TodoListInclude();
		TodoListIncludePK userInPK = new TodoListIncludePK();

		userInPK.setTodoListId(todoListId);
		userInPK.setUserId(userId);
		unserInclude.setId(userInPK);
		unserInclude.setStatusId(new BigInteger(ConstantUtils.TodoList_Status.DOING.getValue()));
		unserInclude.setCreatedby(createdBy);
		unserInclude.setCreatedDate(date);
		unserInclude.setUpdatedDate(date);

		return unserInclude;
	}
	
	public void saveTodoListIncludeUser(TodoListDTO todoListDTO, BigInteger todoListId, BigInteger teamId,
			List<TeamMember> listMemberInTeam, Boolean isCreated, BigInteger createdBy, Date date) {
		BigInteger privacyOnlyMe = new BigInteger(ConstantUtils.Privacy.ONLY_ME.getValue()); // 4
		BigInteger privacyMyTeam = new BigInteger(ConstantUtils.Privacy.MY_TEAM.getValue()); // 2

		if (privacyMyTeam.compareTo(todoListDTO.getPrivacyId()) == 0) {
			if (listMemberInTeam != null && !listMemberInTeam.isEmpty()) {
				List<TodoListInclude> listUserInclude = new ArrayList<TodoListInclude>();
				for (TeamMember member : listMemberInTeam) {
					TodoListInclude userInclude = new TodoListInclude();
					if (isCreated) { // create list include
						userInclude = this.convertToEntityInclude(todoListId, member.getId().getUserId(), date, createdBy);
						listUserInclude.add(userInclude);
					} else { // Chỉ add những user không tồn tại
						TodoListInclude oldUserInclude = todoListIncludeRepository.findById_TodoListIdAndId_UserIdAndIsActive(todoListId, member.getId().getUserId(), 1);
						if (oldUserInclude == null) { // Add new userInclude(với status Doing), còn lại bỏ qua
							userInclude = this.convertToEntityInclude(todoListId,  member.getId().getUserId(), date, createdBy);
							listUserInclude.add(userInclude);
						}
					}
				}
				todoListIncludeRepository.saveAll(listUserInclude);
			}
		} else { // Specific member(privacyInclude) OR Only me
			// TH Only me có thể add thêm các member include(privacyInclude)
			if (privacyOnlyMe.compareTo(todoListDTO.getPrivacyId()) == 0) {
				// Check createBy is member in team
				TeamMember memberCreate = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(teamId, createdBy, 1);
				if (memberCreate != null) {
					if (isCreated) { // create
						TodoListInclude userInclude = this.convertToEntityInclude(todoListId, createdBy, date, createdBy);
						todoListIncludeRepository.save(userInclude);
					} else { // Chỉ add khi không tồn tại
						TodoListInclude oldUserInclude = todoListIncludeRepository.findById_TodoListIdAndId_UserIdAndIsActive(todoListId, createdBy, 1);
						if (oldUserInclude == null) {
							TodoListInclude userInclude = this.convertToEntityInclude(todoListId,  createdBy, date, createdBy);
							todoListIncludeRepository.save(userInclude);
						}
					}
				}
			}
			if (todoListDTO.getUserAssign() != null && !todoListDTO.getUserAssign().isEmpty()) {
				// Create TodoListInclude follow numbers of members picked
				List<TodoListInclude> listUserInclude = new ArrayList<TodoListInclude>();

				for (BigInteger member : todoListDTO.getUserAssign()) {
					TodoListInclude userInclude = new TodoListInclude();
					// Check member is in team
					TeamMember thisMember = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(teamId, member, 1);
					if (thisMember != null) {
						if (isCreated) { // create list include
							userInclude = this.convertToEntityInclude(todoListId, member, date, createdBy);
							listUserInclude.add(userInclude);
						} else { // Chỉ add những user không tồn tại
							TodoListInclude oldUserInclude = todoListIncludeRepository.findById_TodoListIdAndId_UserIdAndIsActive(todoListId, member, 1);
							if (oldUserInclude == null) { // Add new userInclude(với status Doing), còn lại bỏ qua
								userInclude = this.convertToEntityInclude(todoListId,  member, date, createdBy);
								listUserInclude.add(userInclude);
							}
						}
						
					}
				}
				todoListIncludeRepository.saveAll(listUserInclude);
			}
		}
	}

	public ApiResponse deleteTodoList(TodoListDTO todoListDTO, BigInteger deleteBy) {
		ApiResponse respon = new ApiResponse();
		TodoList oldTodoList = todoListRepository.findByTodoListId(todoListDTO.getTodoListId());

		if (oldTodoList == null) { // oldTodoList not exist
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "todoList"));
			return respon;
		}
		
		// Check user delete phải là Admin trong team hoặc là người tạo thì mới dc phép xóa
		TeamMember teamMember = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(oldTodoList.getTeamId(), deleteBy, 1);
		if ((teamMember == null
				|| !ConstantUtils.Team_Member_Role.TEAM_ADMIN.getValue().equals(teamMember.getTeamMemberRole()))
				&& deleteBy.compareTo(oldTodoList.getCreatedby()) != 0) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E027", "todoList"));
			respon.setData(new ArrayList<>());
			return respon;
		}
		
		// delete oldTodoList
		todoListRepository.delete(oldTodoList);
		
		// delete todoListInclude
		List<TodoListInclude> listInclude = todoListIncludeRepository.findById_TodoListId(oldTodoList.getTodoListId());
		if (listInclude != null && !listInclude.isEmpty()) {
			todoListIncludeRepository.deleteAll(listInclude);
		}
		
		//delete todoList child and includeChild nếu nó parent
		if (oldTodoList.getTodoListParentId() == null) {
			List<TodoList> listTodoChild = todoListRepository.findByTeamIdAndTodoListParentIdAndIsActive(oldTodoList.getTeamId(), oldTodoList.getTodoListId(), 1);
			if (listTodoChild != null && !listTodoChild.isEmpty()) {
				// delete all todoListChild
				todoListRepository.deleteAll(listTodoChild);
				
				// delete all todoListIncludeChild
				for (TodoList todoChild : listTodoChild) {
					List<TodoListInclude> listIncludeChild = todoListIncludeRepository.findById_TodoListId(todoChild.getTodoListId());
					if (listIncludeChild != null && !listIncludeChild.isEmpty()) {
						todoListIncludeRepository.deleteAll(listIncludeChild);
					}
				}
			}
		}

		respon.setMessage(messageUtils.getMessage("I002", "accounting"));
		return respon;
	}

	/**
	 * 
	 * search task for user
	 * @param SearchDTO
	 * @return
	 * @throws ParseException 
	 */
	public ApiResponse searchYourTask(SearchDTO search, BigInteger userId) throws ParseException {
		String keyword = search.getKeyword();
		if(StringUtils.isEmpty(keyword)) {//full
			keyword = "%%";
		}else {//start with keywork
			keyword = "%" + keyword + "%";
		}
		if(search.getMaxResult() == 0) {
			search.setMaxResult(10);
		}
		
		ApiResponse respon = new ApiResponse();
		List<Map<String, Object>> listTodoList = todoListRepository.searchYourTodoList(userId, search.getTeamId(),
				new BigInteger(ConstantUtils.TodoList_Status.DONE.getValue()), keyword, search.getFirstResult(), search.getMaxResult());

		SearchTodoListDTO result = new SearchTodoListDTO();
		List<TodoListDTO> listtodoListDTO = new ArrayList<>();
		if(listTodoList == null || listTodoList.isEmpty()) {
			respon.setData(listtodoListDTO);
			return respon;
		}
		
		int totalNotDone = 0;
		for(Map<String, Object> todoList:listTodoList) {
			TodoListDTO todoListDTO = convertMapTodoListToDTO(todoList);
			
			// Get list user assigned
			List<TodoListInclude> listUserInclude = todoListIncludeRepository
					.findById_TodoListIdAndIsActive(new BigInteger(todoList.get("todo_list_id").toString()), 1);
			List<UserDTO> listUserAssigned = new ArrayList<UserDTO>();
			
			if (listUserInclude != null && !listUserInclude.isEmpty()) {
				for (TodoListInclude userInclude:listUserInclude) {
					UserDTO userAssign = new UserDTO();
					User user = userRepository.findByUserIdAndIsActive(userInclude.getId().getUserId(), 1);
					if (user != null) {
						userAssign.setUserId(user.getUserId());
						userAssign.setUserAvatar(user.getUserAvatar());
						userAssign.setUserFullName(user.getUserFullName());
						listUserAssigned.add(userAssign);
					}
				}
			}
			todoListDTO.setListUserAssigned(listUserAssigned);
			todoListDTO.setUserId(new BigInteger(todoList.get("user_id").toString()));
			todoListDTO.setTaskStatus(new BigInteger(todoList.get("status_id").toString()));
			
			// Calculate totalNotDone
			if(new BigInteger(ConstantUtils.TodoList_Status.DOING.getValue()).compareTo(todoListDTO.getTaskStatus()) == 0 ) {
				totalNotDone++;
			}
			//Get parent task
			if (todoListDTO.getTodoListParentId() != null) {
				TodoList parentTask = todoListRepository.findByTodoListIdAndIsActive(todoListDTO.getTodoListParentId(), 1);
				todoListDTO.setParentTask(parentTask);
			}
			listtodoListDTO.add(todoListDTO);
		}
		
		//get total
		result.setTotalNotDone(totalNotDone);
		result.setTotal(todoListRepository.searchCountYourTodoList(userId, search.getTeamId(), keyword));
		result.setListTasks(listtodoListDTO);
		respon.setData(result);
		
		return respon;
	}
	
	/**
	 * 
	 * search task for user
	 * @param SearchDTO
	 * @return
	 * @throws ParseException 
	 */
	public ApiResponse searchTeamTask(SearchDTO search, BigInteger userId) throws ParseException {
		String keyword = search.getKeyword();
		if(StringUtils.isEmpty(keyword)) {//full
			keyword = "%%";
		}else {//start with keywork
			keyword = "%" + keyword + "%";
		}
		if(search.getMaxResult() == 0) {
			search.setMaxResult(10);
		}
		
		ApiResponse respon = new ApiResponse();
		List<Map<String, Object>> listTodoList = todoListRepository.searchTeamTodoList(search.getTeamId(),
				new BigInteger(ConstantUtils.Privacy.MY_TEAM.getValue()), new BigInteger(ConstantUtils.TodoList_Status.DONE.getValue()), 
				keyword, search.getFirstResult(), search.getMaxResult());

		SearchTodoListDTO result = new SearchTodoListDTO();
		List<TodoListDTO> listtodoListDTO = new ArrayList<>();
		if(listTodoList == null || listTodoList.isEmpty()) {
			respon.setData(listtodoListDTO);
			return respon;
		}
		
		int totalNotDone = 0;
		for(Map<String, Object> todoList:listTodoList) {
			TodoListDTO todoListDTO = convertMapTodoListToDTO(todoList);
			
			todoListDTO.setPrivacyId(new BigInteger(todoList.get("privacy_id").toString())); // 2: All team
			// Reset todoListTitle về title parent nếu todoList hiện tại có type là GROUP
			if (ConstantUtils.TodoList_Type.GROUP_TASK.getValue().equals(todoListDTO.getTodoListType()) || todoListDTO.getTodoListParentId() != null) {
				TodoList parentTask = todoListRepository.findByTodoListIdAndIsActive(todoListDTO.getTodoListParentId(), 1);
				if (parentTask != null) {
					todoListDTO.setTodoListTitle(parentTask.getTodoListTitle());
				}
				//set parent task
				todoListDTO.setParentTask(parentTask);
			}
			// Calculate totalNotDone
			if(todoListDTO.getGeneralProgress() != 100) {
				totalNotDone++;
			}
			
			listtodoListDTO.add(todoListDTO);
		}
		
		//get total
		result.setTotalNotDone(totalNotDone);
		result.setTotal(todoListRepository.searchCountTeamTodoList(search.getTeamId(), new BigInteger(ConstantUtils.Privacy.MY_TEAM.getValue()), keyword));
		result.setListTasks(listtodoListDTO);
		respon.setData(result);
		
		return respon;
	}

	/**
	 * 
	 * @Des convert Map<String, Object> TodoList to todolistDTO
	 * @param Map<String, Object> TodoList
	 * @return
	 * @throws ParseException 
	 */
	private TodoListDTO convertMapTodoListToDTO(Map<String, Object> todoList) throws ParseException {
		TodoListDTO todoListDTO = new TodoListDTO();
		int totalMemAssign = Integer.parseInt(todoList.get("members_assign").toString());
		int totalMemDone = Integer.parseInt(todoList.get("members_done").toString());
		
		todoListDTO.setTodoListId(new BigInteger(todoList.get("todo_list_id").toString()));
		if (!StringUtils.isEmpty(todoList.get("todo_list_parent_id").toString())) {
			todoListDTO.setTodoListParentId(new BigInteger(todoList.get("todo_list_parent_id").toString()));
		} else {
			todoListDTO.setTodoListParentId(null);
		}
		todoListDTO.setTeamId(new BigInteger(todoList.get("team_id").toString()));
		todoListDTO.setTotalMemberAssign(totalMemAssign);
		todoListDTO.setTotalMemberDone(totalMemDone);
		todoListDTO.setGeneralProgress((int)Math.round((double)totalMemDone / totalMemAssign * 100));
		
		if (!StringUtils.isEmpty(todoList.get("todo_list_deadline").toString())) {
			todoListDTO.setDateDeadline(DateUtil.getFormatDate(
					todoList.get("todo_list_deadline").toString(), DateUtil.PT_YYYY_MM_DD_HH_MM_SS));
		}
		//Compare date deadline expired
		if (DateUtil.compareToDatimeNow(DateUtil.getFormatDate(
				todoList.get("todo_list_deadline").toString(), DateUtil.PT_YYYY_MM_DD_HH_MM_SS)) < 0 ) {
			todoListDTO.setExpired(true); // isExpired
		} else {
			todoListDTO.setExpired(false);
		}
		todoListDTO.setTodoListTitle(todoList.get("todo_list_title").toString());
		if (!StringUtils.isEmpty(todoList.get("todo_list_type").toString())) {
			todoListDTO.setTodoLisType(todoList.get("todo_list_type").toString());
		}
		todoListDTO.setTodoListNotice(todoList.get("todo_list_notice").toString());
		
		return todoListDTO;
	}

	/**
	 * 
	 * @Des Get detail TodoList
	 * @param todoListId
	 * @return TodoListDTO
	 * @throws ParseException 
	 */

	public ApiResponse getDetailTask(BigInteger todoListId) throws ParseException {
		ApiResponse respon = new ApiResponse();
		TodoList todoList = todoListRepository.findByTodoListIdAndIsActive(todoListId, 1);
		
		if (todoList == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "todoList"));
			return respon;
		}
		TodoListDTO todoListDTO = convertEntityToDTO(todoList);
		
		
		if (todoList.getTodoListParentId() != null) { // task child or single task
			
			
		} else { // Group task
			// Get all child task
			List<TodoList> listChild = todoListRepository
					.findByTeamIdAndTodoListParentIdAndIsActive(todoList.getTeamId(), todoList.getTodoListId(), 1);
			List<TodoListDTO> todoListChild = new ArrayList<TodoListDTO>();
//			int totalMemberDone = 0;
//			int totalMemberAssign = 0;
			int totalChildDone = 0;
			
			if (listChild != null && !listChild.isEmpty()) {
				for (TodoList child : listChild) {
					TodoListDTO dto = convertEntityToDTO(child);
					todoListChild.add(dto);
					
					if (dto.getGeneralProgress() == 100) {
						totalChildDone++;
					}
//					totalMemberDone+=dto.getTotalMemberDone();
//					totalMemberAssign+=dto.getTotalMemberAssign();
				}
				todoListDTO.setGeneralProgress((int) Math.round((double) totalChildDone / listChild.size() * 100));
			}
			todoListDTO.setTodoListChild(todoListChild);
//			todoListDTO.setGeneralProgress((int) Math.round((double) totalMemberDone / totalMemberAssign * 100)); // Progress parent task
		}

		List<TodoListInclude> listUserInclude = todoListIncludeRepository.findById_TodoListIdAndIsActive(todoListId, 1);

		if (listUserInclude != null && !listUserInclude.isEmpty()) {
			
		}
		
		respon.setData(todoListDTO);
		return respon;
	}
	
	/**
	 * 
	 * @Des convert entity to todoListDTO
	 * @param TodoList
	 * @return
	 * @throws ParseException 
	 */
	private TodoListDTO convertEntityToDTO(TodoList todoList) throws ParseException {
		TodoListDTO dto = new TodoListDTO();
		
		dto.setTodoListId(todoList.getTodoListId());
		dto.setTeamId(todoList.getTeamId());
		dto.setTodoListTitle(todoList.getTodoListTitle());
		
		//Get parent task || Nếu parenTask != null thì task này là task child của group task
		if (todoList.getTodoListParentId() != null) {
			dto.setTodoListParentId(todoList.getTodoListParentId());
			TodoList parentTask = todoListRepository.findByTodoListIdAndIsActive(todoList.getTodoListParentId(), 1);
			dto.setParentTask(parentTask);
		}
		
		// Set info UserCreate
		User userCreate = userRepository.findByUserIdAndIsActive(todoList.getCreatedby(), 1);
		if (userCreate != null) {
			UserDTO userDTO = new UserDTO();
			userDTO.setUserId(userCreate.getUserId());
			userDTO.setUserFullName(userCreate.getUserFullName());
			userDTO.setUserAvatar(userCreate.getUserAvatar());
			dto.setCreatedby(userDTO);
		}
		
		dto.setDateDeadline(todoList.getTodoListDeadline());
		// Compare date deadline expired
		if (DateUtil.compareToDatimeNow(todoList.getTodoListDeadline()) < 0) {
			dto.setExpired(true); // isExpired
		} else {
			dto.setExpired(false);
		}
		dto.setTodoLisType(todoList.getTodoLisType());
		
		if (todoList.getPrivacyId() != null) { // task child or single task
			dto.setPrivacyId(todoList.getPrivacyId()); // Assign: 2:All team 3:Include 4:Only me || Nếu null thì nó là Group parent task
			
			// Calculate progress of task
			int totalMemberAssign = todoListRepository.countTotalMemberAssigned(todoList.getTodoListId());
			int totalMemberDone = todoListRepository.countTotalMemberDone(todoList.getTodoListId(),
					new BigInteger(ConstantUtils.TodoList_Status.DONE.getValue()));
			dto.setTotalMemberAssign(totalMemberAssign);
			dto.setTotalMemberDone(totalMemberDone);
			dto.setGeneralProgress((int) Math.round((double) totalMemberDone / totalMemberAssign * 100));

			// Set listUserAssign
			List<TodoListInclude> listUserInclude = todoListIncludeRepository
					.findById_TodoListIdAndIsActive(todoList.getTodoListId(), 1);
			List<UserDTO> listUserDoing = new ArrayList<UserDTO>();
			List<UserDTO> listUserDone = new ArrayList<UserDTO>();
			if (listUserInclude != null && !listUserInclude.isEmpty()) {
				for (TodoListInclude userInclude : listUserInclude) {
					UserDTO user = new UserDTO();
					User userAssign = userRepository.findByUserIdAndIsActive(userInclude.getId().getUserId(), 1);
					if (userAssign != null) {
						user.setUserId(userAssign.getUserId());
						user.setUserAvatar(userAssign.getUserAvatar());
						user.setUserFullName(userAssign.getUserFullName());
						if (new BigInteger(ConstantUtils.TodoList_Status.DOING.getValue())
								.compareTo(userInclude.getStatusId()) == 0) {
							user.setIsDoneTask(false);
							listUserDoing.add(user);
						} else {
							user.setIsDoneTask(true);
							listUserDone.add(user);
						}
					}
				}
			}
			List<UserDTO> listUserAssign = new ArrayList<UserDTO>(listUserDoing);
			listUserAssign.addAll(listUserDone);
			dto.setListUserAssigned(listUserAssign);
			
			dto.setTodoListNotice(todoList.getTodoListNotice());
		}
		return dto;
	}

	/**
	 * 
	 * @Des Remind user Do Task
	 * @param TodoListDTO
	 * @throws JSONException 
	 * @throws NoSuchMessageException 
	 */
	public ApiResponse remindUserDoTask(TodoListDTO request, BigInteger userAdmin) throws NoSuchMessageException, JSONException {
		ApiResponse respon = new ApiResponse();
		TodoList oldTodoList = todoListRepository.findByTodoListIdAndIsActive(request.getTodoListId(), 1);
		if (oldTodoList == null) { // oldTodoList not exist
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "todoList"));
			return respon;
		}

		// Check user remind phải là Admin trong team hoặc là người tạo thì mới dc phép
		TeamMember teamMember = teamMemberRepository.findById_TeamIdAndId_UserIdAndIsActive(oldTodoList.getTeamId(), userAdmin, 1);
		if ((teamMember == null
				|| !ConstantUtils.Team_Member_Role.TEAM_ADMIN.getValue().equals(teamMember.getTeamMemberRole()))
				&& userAdmin.compareTo(oldTodoList.getCreatedby()) != 0) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E029", "user"));
			return respon;
		}

		// Get userInclude Doing
		TodoListInclude userInclude = todoListIncludeRepository.findById_TodoListIdAndId_UserIdAndIsActive(oldTodoList.getTodoListId(), request.getUserId(), 1);
		if (userInclude == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "user"));
			return respon;
		}
		
		// Check this user is Doing
		if (new BigInteger(ConstantUtils.TodoList_Status.DOING.getValue()).compareTo(userInclude.getStatusId()) != 0) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E028"));
			return respon;
		} else { // is not done
			// Notification remind member do task
			notificationService.notificationFromTeamToUser(oldTodoList.getTeamId(), request.getUserId(), userAdmin, "N014",
					ConstantUtils.MobileScreen.TEAM_PROFILE_DETAIL.getDescription(),
					ConstantUtils.Notification_Type.NOTIFI_MESS.getValue());
		}
		
		respon.setMessage(messageUtils.getMessage("I025", "member"));
		return respon;
	}

	/**
	 * 
	 * @Des Change status user Do Task
	 * @param TodoListDTO
	 */
	public ApiResponse changeStatusDoTask(TodoListDTO request, BigInteger userId) throws Exception {
		ApiResponse respon = new ApiResponse();
		TodoList oldTodoList = todoListRepository.findByTodoListIdAndIsActive(request.getTodoListId(), 1);
		if (oldTodoList == null) { // oldTodoList not exist
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "todoList"));
			return respon;
		}
		
		// Get userInclude Change
		TodoListInclude userChange = todoListIncludeRepository.findById_TodoListIdAndId_UserIdAndIsActive(oldTodoList.getTodoListId(), userId, 1);
		if (userChange == null) {
			respon.setSuccess(false);
			respon.setMessage(messageUtils.getMessage("E001", "user"));
			return respon;
		}
		
		// Check current status this user
		if (new BigInteger(ConstantUtils.TodoList_Status.DOING.getValue()).compareTo(userChange.getStatusId()) == 0) {
			userChange.setStatusId(new BigInteger(ConstantUtils.TodoList_Status.DONE.getValue()));
		} else { // Change DONE to DOING
			userChange.setStatusId(new BigInteger(ConstantUtils.TodoList_Status.DOING.getValue()));
		}
		userChange.setUpdatedDate(new Date());
		userChange = todoListIncludeRepository.save(userChange);
		
		respon.setMessage(messageUtils.getMessage("I013"));
		respon.setData(userChange);
		return respon;
	}

}
