package com.paracelsoft.teamsport.service;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paracelsoft.teamsport.entity.ActiveOtp;
import com.paracelsoft.teamsport.repository.ActiveOtpRepository;

@Transactional(rollbackFor = { Exception.class, ParseException.class })
@Service("activeOTPService")
public class ActiveOTPService {
	@Autowired
	ActiveOtpRepository activeOtpRepository;
	
	/**
	 * 
	 * @param email
	 * @param otp
	 * @param timeNow
	 * @return
	 */
	public ActiveOtp checkOTP(String email, String otp, Date timeNow) {
		
		List<ActiveOtp> otpActive = activeOtpRepository.findAllByActiveEmailAndBeginTimeLessThanEqualAndEndTimeGreaterThanEqualOrderByEndTimeDesc(email, timeNow, timeNow);
		if(otpActive!=null && otpActive.size()>0) {
			if(otp != null && !StringUtils.EMPTY.equals(otp)) {
				if(otp.equals(otpActive.get(0).getActiveOtpCode())) {
					return otpActive.get(0);
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param email
	 * @param token
	 */
	public ActiveOtp generateOTP(String email) {
		//create and save otp activate account
		ActiveOtp otp = new ActiveOtp();
		otp.setActiveOtpId(null);
		otp.setIsActive(0);
		otp.setBeginTime(new Date());
		otp.setActiveEmail(email);
		//plus 5 min
		Calendar date = Calendar.getInstance();
		long t= date.getTimeInMillis();
		otp.setEndTime(new Date(t + (10 * 8640000)));
		
		otp.setActiveOtpCode(generateToken());
		otp = activeOtpRepository.save(otp);
		return otp;
	}
	
	public String generateToken() {

        String values = "0123456789";

        // Using random method
        Random rndm_method = new Random();

        char[] OTP = new char[6];

        for (int i = 0; i < 6; i++) {
            OTP[i] = values.charAt(rndm_method.nextInt(values.length()));
        }

        return String.valueOf(OTP);
    }
	
}
