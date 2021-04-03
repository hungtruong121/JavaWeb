package com.paracelsoft.teamsport.util;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

public class GenerateIDUtils {
	
	public static final int CARD_NUMBER_LENGTH = 14;
    public static final int PIN_NUMBER_LENGTH = 6;

	public static String getUUID(String firstChar) {
		UUID idOne = UUID.randomUUID();
		int uid = idOne.hashCode();
		StringBuilder sb = new StringBuilder();
		if(!StringUtils.isEmpty(firstChar)) {
			sb.append(firstChar);
		}
		sb.append(String.valueOf(uid).replaceAll("-", ""));
		if(sb.length() > 20) {
			sb.substring(0, 20);
		}
		return sb.toString();
	}
	
	public static String generateCardNumber(){
  
        Random rand = new SecureRandom();
        StringBuilder number = null;
        number = new StringBuilder("");
        for (int i = 0; i < CARD_NUMBER_LENGTH; i++) {
            int randInt = rand.nextInt(9);
            number.append(randInt);
        }

        number.append(UtilValidate.getLuhnCheckDigit(number.toString()));
        return number.toString();
    }
	
	public static String generatePinNumber(){

        Random rand = new SecureRandom();
        StringBuilder number = null;
        number = new StringBuilder("");
        for (int i = 0; i < CARD_NUMBER_LENGTH; i++) {
            int randInt = rand.nextInt(9);
            number.append(randInt);
        }

        number.append(UtilValidate.getLuhnCheckDigit(number.toString()));
        return number.toString();
    }
}
