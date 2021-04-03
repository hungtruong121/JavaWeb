
package com.paracelsoft.teamsport.util;

public class ReponseCode {
	
	public static enum INVALID_CODE {
		INVALID_REQUEST("INVALID_REQUEST"),
		IS_ORDERED("IS_ORDERED"),
		SERVER_CONFIG_ERROR("SERVER_CONFIG_ERROR"),
		LAST_ADMIN("LAST_ADMIN"); //admin cuoi cung
		
		private String code;
		
		public String getCode() {
			return code;
		}
		
		public void setCode(String code) {
			this.code = code;
		}
		private INVALID_CODE(String code) {
			this.code = code;
		}
	}
}
