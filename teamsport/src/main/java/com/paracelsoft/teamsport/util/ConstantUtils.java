package com.paracelsoft.teamsport.util;

public class ConstantUtils {
	
	public static enum Party_Status {
		PARTY_DISABLED("PARTY_DISABLED"),
		PARTY_ENABLED("PARTY_ENABLED");

		private String value;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
		private Party_Status(String value) {
			this.value = value;
		}
	}
	
	public static enum PartyType{
		PERSON("PERSON"),
		PARTY_GROUP("PARTY_GROUP"),
		COMPANY("Company"),
		FAMILY("FAMILY"),
		Company("Company");
		private String value;
		PartyType(String value){
			this.setValue(value);
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
	
	public static enum ROLE_TYPE_ID{

		CUSTOMER("1","CUSTOMER"),
		ADMIN("2","ADMIN");

		private String value;
		private String description;

		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		
		private ROLE_TYPE_ID(String value, String description) {
			this.value = value;
			this.description = description;
		}

	}
	
	public static enum Sport_Competition {
		TEAM("TEAM","nhóm"),
		SINGLE("SINGLE","single");

		private String value;
		private String description;

		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		private Sport_Competition(String value, String description) {
			this.value = value;
			this.description = description;
		}
	}
	
	public static enum Sport_Type_Point{
		RREWARD_POINT("RREWARD_POINT","Reward point"),
		MINUS_POINT("MINUS_POINT","Minus point");

		private String value;
		private String description;

		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		private Sport_Type_Point(String value, String description) {
			this.value = value;
			this.description = description;
		}
	}
	
	public static enum Sport_Type_ScorePenal{
		SCORE("SCORE","Score"),
		PENAL("PENAL","Penal");

		private String value;
		private String description;

		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		private Sport_Type_ScorePenal(String value, String description) {
			this.value = value;
			this.description = description;
		}
	}
	
	public static enum Sport_Type_Competition{
		HALF("HALF","Half"),
		ROUND("ROUND","Round"),
		SET("SET","Set"),
		EXTRA_TIME("EXTRA_TIME", "Extra time");

		private String value;
		private String description;

		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		private Sport_Type_Competition(String value, String description) {
			this.value = value;
			this.description = description;
		}
	}
	
	public static enum Post_Include_User_Type{
		PRIVACY("PRIVACY","privacy"), //list user can view post
		TAG_COMMENT("TAG_COMMENT","tag comment"), //list user taged
		TAG_POST("TAG_POST","tag post"); //list user taged
		private String value;
		private String description;

		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		
		private Post_Include_User_Type(String value, String description) {
			this.value = value;
			this.description = description;
		}
	}
	
	public static enum Post_Type{
		POST_NORMAL("POST_NORMAL","POST_NORMAL"),
		POST_PHOTO("POST_PHOTO","POST_PHOTO"),
		POST_ALBUM("POST_ALBUM","POST_ALBUM"),
		POST_SURVEY_TEXT("POST_SURVEY_TEXT","POST_SURVEY_TEXT"),
		POST_SURVEY_SELECTION("POST_SURVEY_SELECTION","POST_SURVEY_SELECTION");

		private String value;
		private String description;

		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		
		private Post_Type(String value, String description) {
			this.value = value;
			this.description = description;
		}
	}
	
	public static enum Team_Member_Role{
		TEAM_ADMIN("TEAM_ADMIN","team admin"),
		TEAM_MEMBER("TEAM_MEMBER","team member");

		private String value;
		private String description;

		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		
		private Team_Member_Role(String value, String description) {
			this.value = value;
			this.description = description;
		}
	}
	
	public static enum Team_Member_Status{
		REQUESTED("1","REQUESTED"),
		FOLLOWED("2","FOLLOWED"),
		INVITED("3","INVITED");

		private String value;
		private String description;

		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		
		private Team_Member_Status(String value, String description) {
			this.value = value;
			this.description = description;
		}
	}
	
	public static enum Privacy{
		PUBLIC("1","Public"),
		MY_TEAM("2","My team"),
		INCLUDE("3", "Include"),
		ONLY_ME("4", "Only me");

		private String value;
		private String description;

		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		
		private Privacy(String value, String description) {
			this.value = value;
			this.description = description;
		}
	}
	
	public static enum Team_Rank{
		BASE("1","BASE");

		private String value;
		private String description;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}

		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}

		private Team_Rank(String value, String description) {
			this.value = value;
			this.description = description;
		}
	}
	
	public static enum Is_Active{
		ACTIVE("1","active"),
		NO_ACTIVE("2","no active"),
		BLOCK("0", "block");

		private String value;
		private String description;

		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		
		private Is_Active(String value, String description) {
			this.value = value;
			this.description = description;
		}
	}
	
	public static enum User_Gender{
		OTHER("0", "OTHER"),
		MALE("1","MALE"),
		FEMALE("2","FEMALE");

		private String value;
		private String description;

		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		
		private User_Gender(String value, String description) {
			this.value = value;
			this.description = description;
		}
	}
	
	public static enum Sport_Gender{
		BOTH("0", "BOTH"),
		MALE("1","MALE"),
		FEMALE("2","FEMALE");

		private String value;
		private String description;

		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		
		private Sport_Gender(String value, String description) {
			this.value = value;
			this.description = description;
		}
	}
	
	public static enum Accounting_Status{
		NOT_PAID("7","NOT_PAID"),
		WAITING("8","WAITING"),
		DONE("9","DONE");

		private String value;
		private String description;

		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		
		private Accounting_Status(String value, String description) {
			this.value = value;
			this.description = description;
		}
	}
	
	public static enum Promotion_Status{
		IN_ACTIVE("0","IN_ACTIVE"),
		ACTIVE("1","ACTIVE");

		private String value;
		private String description;

		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		
		private Promotion_Status(String value, String description) {
			this.value = value;
			this.description = description;
		}
	}
	
	public static enum Accounting_Search_Type{
		YOUR_ACCOUNTING("YOUR_ACCOUNTING", "YOUR_ACCOUNTING"),
		TEAM_ACCOUNTING("TEAM_ACCOUNTING", "TEAM_ACCOUNTING");

		private String value;
		private String description;

		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		
		private Accounting_Search_Type(String value, String description) {
			this.value = value;
			this.description = description;
		}
	}
	
	public static enum Promotion_Type_Discount{
		DISCOUNT_PERCENT("%","DISCOUNT_PERCENT"),
		MONEY("$","MONEY");

		private String value;
		private String description;

		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		
		private Promotion_Type_Discount(String value, String description) {
			this.value = value;
			this.description = description;
		}
	}
	
	public static enum Promotion_Object_All{
		ALL("0","All"); // all rank/level

		private String value;
		private String description;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}

		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}

		private Promotion_Object_All(String value, String description) {
			this.value = value;
			this.description = description;
		}
	}
	
	public static enum TodoList_Type{
		SINGLE_TASK("SINGLE","single task"),
		GROUP_TASK("GROUP","group task");

		private String value;
		private String description;

		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		
		private TodoList_Type(String value, String description) {
			this.value = value;
			this.description = description;
		}
	}
	
	public static enum TodoList_Status{
		DOING("13","DOING"),
		DONE("9","DONE");

		private String value;
		private String description;

		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		
		private TodoList_Status(String value, String description) {
			this.value = value;
			this.description = description;
		}
	}
	
	public static enum Event_Type{
		MATCH("MATCH","Match"),
		NORMAL("NORMAL","Normal");

		private String value;
		private String description;

		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		
		private Event_Type(String value, String description) {
			this.value = value;
			this.description = description;
		}
	}
	
	public static enum Event_Loop_Type{
		NONE("NONE","none"),
		WEEKLY("WEEKLY","weekly"),
		MONTHLY("MONTHLY","monthly");

		private String value;
		private String description;

		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		
		private Event_Loop_Type(String value, String description) {
			this.value = value;
			this.description = description;
		}
	}
	
	public static enum Event_Game_Type{
		IN_TEAM("IN_TEAM","In team"),
		PRACTICE("PRACTICE","Practice"),
		OFFICIAL("OFFICIAL","Official");

		private String value;
		private String description;

		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		
		private Event_Game_Type(String value, String description) {
			this.value = value;
			this.description = description;
		}
	}
	
	public static enum Event_Team_Color{
		RED("RED","Red"),
		WHITE("WHITE","White");

		private String value;
		private String description;

		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		
		private Event_Team_Color(String value, String description) {
			this.value = value;
			this.description = description;
		}
	}
	
	public static enum Event_Match_Status{
		NEXT_MATCH(0,"Next Match"),
		READY(0,"Ready"), // sub_match_status
		PLAYING(1,"Playing"),
		FINISH(2,"Finish");

		private Integer value;
		private String description;

		public Integer getValue() {
			return value;
		}
		
		public void setValue(Integer value) {
			this.value = value;
		}
		
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		
		private Event_Match_Status(Integer value, String description) {
			this.value = value;
			this.description = description;
		}
	}
	
	public static enum Event_Match_Result{
		HOME_WIN(0,"Team home win"),
		OPPONENT_WIN(1,"Team opponent win"),
		DRAWN(2,"Drawn");

		private Integer value;
		private String description;

		public Integer getValue() {
			return value;
		}
		
		public void setValue(Integer value) {
			this.value = value;
		}
		
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		
		private Event_Match_Result(Integer value, String description) {
			this.value = value;
			this.description = description;
		}
	}
	
	public static enum Notification_From_Object_Type{
		
		TEAM_TO_USER("TEAM_TO_USER", "team"),
		TEAM_TO_TEAM("TEAM_TO_TEAM", "team"),
		TEAM_TO_SYS("TEAM_TO_SYS", "TEAM_TO_SYS"),
		
		USER_TO_TEAM("USER_TO_TEAM","user,team"),
		USER_TO_USER("USER_TO_USER","USER_TO_USER"),
		
		USER_TO_POST("USER_TO_POST","user"),
		USER_TO_COMMENT("USER_TO_COMMENT","user"),
		USER_TO_SURVEY("USER_TO_SURVEY", "USER_TO_SURVEY"),
		
		USER_TO_TODO_TASK("USER_TO_TODO_TASK", "user,task"),
		
		SYSTEM_TO_TEAM("SYSTEM_TO_TEAM","team"),
		SYSTEM_TO_USER("SYSTEM_TO_USER","SYSTEM_TO_USER");

		private String value;
		private String params;
		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}
		/**
		 * @param value the value to set
		 */
		public void setValue(String value) {
			this.value = value;
		}
		/**
		 * @return the params
		 */
		public String getParams() {
			return params;
		}
		/**
		 * @param params the params to set
		 */
		public void setParams(String params) {
			this.params = params;
		}
		private Notification_From_Object_Type(String value, String params) {
			this.value = value;
			this.params = params;
		}

	}
	
	public static enum Notification_Type{
		NOTIFI_INVITE_JOIN_TEAM("1", "NOTIFI_REQUEST_JOIN_TEAM"),
		NOTIFI_REQUEST_JOIN_TEAM("2", "NOTIFI_REQUEST_JOIN_TEAM"),
		NOTIFI_MESS("3","NOTIFI_MESS"),
		NOTIFI_SYSTEM("4","NOTIFI_SYSTEM");

		private String value;
		private String description;

		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		
		private Notification_Type(String value, String description) {
			this.value = value;
			this.description = description;
		}
	}
	
	public static enum Token_Type{
		AUTH_LOGIN("AUTH_LOGIN", "AUTH_LOGIN"),
		AUTH_FACEBOOK("AUTH_FACEBOOK","AUTH_FACEBOOK");

		private String value;
		private String description;

		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		
		private Token_Type(String value, String description) {
			this.value = value;
			this.description = description;
		}
	}
	
	public static enum MobileScreen{
		MENU_POST("MENU_POST", "MenuPostScreen"),
		CREATE_POST("CREATE_POST", "CreatePostScreen"),
		MEDIA_SELECT("MEDIA_SELECT", "MediaSelectScreen"),
		PHOTO_ALBUM("PHOTO_ALBUM", "PhotoAlbumScreen"),
		SELECT_ALBUM("SELECT_ALBUM", "SelectAlbumScreen"),
		CREATE_ALBUM("CREATE_ALBUM", "CreateAlbumScreen"),
		PREVIEW_ALBUM("PREVIEW_ALBUM", "PreviewAlbumScreen"),
		EDIT_ALBUM("EDIT_ALBUM", "EditAlbumScreen"),
		CREATE_SURVEY("CREATE_SURVEY", "CreateSurveyScreen"),
		FUNCTION("FUNCTION", "FuntionScreen"),
		NOW("NOW", "NowScreen"),
		PROFILE("PROFILE", "ProfileScreen"),
		EXPLORE("EXPLORE", "ExploreScreen"),
		POST_DETAIL("POST_DETAIL", "PostDetailScreen"),
		TEAM_PROFILE_DETAIL("TEAM_PROFILE_DETAIL", "TeamProfileDetailScreen"),
		TEAM_PROFILE("TEAM_PROFILE_DETAIL", "TeamProfileDetailScreen"),
		LOGIN("LOGIN", "LoginScreen"),
		REGISTER("REGISTER", "RegisterScreen"),
		RESET_PASSWORD("RESETPASSWORD", "ResetPasswordScreen"),
		SETTING("SETTING", "SettingScreen"),
		GENERAL("GENERAL", "GeneralScreen"),
		MEMBER_LIST("MEMBER_LIST", "MemberListScreen"),
		EDIT_PROFILE("EDIT_PROFILE", "EditProfileScreen"),
		SWITCH_TEAM("SWITCH_TEAM", "SwitchTeamScreen"),
		CREATE_TEAM("CREATE_TEAM", "CreateTeamScreen"),
		SCAN_QR_CODE("SCAN_QR_CODE", "ScanQRCode"),
		PROFILE_MEMBER("PROFILE_MEMBER", "ProfileMemberScreen"),
		FOLLOWING("FOLLOWING", "FollowingScreen"),
		FOLDER("FOLDER", "FolderScreen"),
		FOLDER_DETAIL("FOLDER_DETAIL", "FolderDetailScreen"),
		CHANGE_PASSWORD("CHANGE_PASSWORD", "ChangePasswordScreen"),
		NOTIFICATION("NOTIFICATION", "NotificationScreen"),
		ACCOUNTING("ACCOUNTING", "AccountingScreen"),
		ACCOUNTING_DETAIL("ACCOUNTING_DETAIL", "AccountingDetailScreen"),
		ACCOUNTING_CREATE_NEW("ACCOUNTING_CREATE_NEW", "AccountingCreateNewScreen"),
		TODO_LIST("TODO_LIST", "TodoListScreen"),
		TODO_LIST_DETAIL("TODO_LIST_DETAIL", "TodoListDetailScreen"),
		TODO_LIST_PARENT_DETAIL("TODO_LIST_PARENT_DETAIL", "TodoListParentDetailScreen"),
		TRENDING("TRENDING", "TrendingScreen"),
		POST_EVENT_KENDO_DETAIL("POST_EVENT_KENDO_DETAIL", "PostEventKendoDetailScreen"),
		RESETPASSWORD("RESETPASSWORD","ResetPasswordScreen");

		private String value;
		private String description;

		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		
		private MobileScreen(String value, String description) {
			this.value = value;
			this.description = description;
		}
	}
	
	public static enum Default_Image{
		NO_IMAGE("1", "DEFAULT_IMAGE"),
		MALE_DEFAULT_IMAGE("2", "MALE_DEFAULT_IMAGE"),
		FEMALE_DEFAULT_IMAGE("3","FEMALE_DEFAULT_IMAGE"),
		SYSTEM_DEFAULT("4","IMAGE_SYSTEM_DEFAULT");

		private String value;
		private String description;

		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		
		private Default_Image(String value, String description) {
			this.value = value;
			this.description = description;
		}
	}
	
	public static enum PaymentStatus{
		SUCCESSFUL("10", "SUCCESSFUL"),
		FAILURE("11", "FAILURE"),
		WAITING_CONFIRM("12","WAITING_CONFIRM");

		private String value;
		private String description;

		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		
		private PaymentStatus(String value, String description) {
			this.value = value;
			this.description = description;
		}
	}
	
	public static enum SearchType{
		TEAM("TEAM"),
		PEOPLE("PEOPLE"),
		HASHTAG("HASHTAG"),
		EVENT("EVENT");

		private String value;

		private SearchType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
	}
	
	public static enum SelectionType{
		IS_MULTIPLE(1, "IS_MULTIPLE");

		private Integer value;
		private String description;

		public Integer getValue() {
			return value;
		}
		
		public void setValue(Integer value) {
			this.value = value;
		}
		
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		
		private SelectionType(Integer value, String description) {
			this.value = value;
			this.description = description;
		}
	}
	
	public static enum ExtendsAnswer{
		IS_EXTENDS_ANS("1", "IS_EXTENDS_ANS");

		private String value;
		private String description;

		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		
		private ExtendsAnswer(String value, String description) {
			this.value = value;
			this.description = description;
		}
	}
}
