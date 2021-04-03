package com.paracelsoft.teamsport.util;

import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;

public class DateUtil {
	public static final String SHORT_JANUARY = "Jan";
	public static final String SHORT_FEBRUARY = "Feb";
	public static final String SHORT_MARCH = "Mar";
	public static final String SHORT_APRIL = "Apr";
	public static final String SHORT_MAY = "May";
	public static final String SHORT_JUNE = "Jun";
	public static final String SHORT_JULY = "Jul";
	public static final String SHORT_AUGUST = "Aug";
	public static final String SHORT_SEPTEMBER = "Sep";
	public static final String SHORT_OCTOBER = "Oct";
	public static final String SHORT_NOVEMBER = "Nov";
	public static final String SHORT_DECEMBER = "Dec";

	public static final String PT_MM_DD_YYYY = "MM/dd/yyyy";
	public static final String PT_MMM_YY = "MMM-yy";
	public static final String PT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String PT_MM_DD_YYYY_HH_MM_SS = "MM-dd-yyyy HH:mm:ss";
	public static final String PT_DD_MMM_YYYY = "dd MMM yyyy";
	public static final String PT_DD_MM_YYYY = "dd/MM/yyyy";
	public static final String PT_DD_MM_YYYY_ = "dd-MM-yyyy";
	public static final String PT_DD_MM_YYYY__HH_MM_SS = "dd-MM-yyyy HH:mm:ss";
	public static final String PT_DD_MM_YYYY_HH = "dd/MM/yyyy HH";
	public static final String PT_DD_MM_YYYY_HH_MM_A = "dd/MM/yyyy hh:mm a";
	public static final String PT_YYYY_MM_DD = "yyyy-MM-dd";
	public static final String PT_YYYY_MM_DD_HH = "yyyy-MM-dd HH";
	public static final String PT_DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";
	public static final String PT_YYYY_MM_DD_HH_MM_A = "yyyy-MM-dd hh:mm a";
	public static final String PT_YYYY_MM_DD_T_HH_MM_SS_SSS = "yyyy-MM-dd'T'HH:mm:ss.SSS";
	public static final String PT_YYYY_MM_DD_T_HH_MM_SS_SSS_Z = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	public static final String PT_DD_MM_YYYY_HH_MM = "dd/MM/yyyy HH:mm";

	public static Date getFormatDateIso(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		if (date != null) {
			try {
				sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
				return sdf.parse(date);
			} catch (ParseException e) {
				System.out.println("ParseException: " + date);
			}
		}
		return null;
	}

	public static String getFormatDateIso(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		if (date != null) {
			sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			return sdf.format(date);
		} else {
			return "";
		}
	}

	public static Date getFormatDate(String date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
		if (date != null) {
			try {
				sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
				return sdf.parse(date);
			} catch (ParseException e) {
				System.out.println("ParseException: " + date);
			}
		}
		return null;
	}
	
	public static String formatDate(String date, String initDateFormat, String endDateFormat) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(endDateFormat);
		try {
			if (date != null) {
				formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
			    Date initDate = new SimpleDateFormat(initDateFormat).parse(date);
			    String parsedDate = formatter.format(initDate);
			    return parsedDate;
			}
			
		} catch (ParseException e) {
			System.out.println("ParseException: " + date);
		}
	    return null;
	}
	
	public static Date formatStringToDate(String date, String initDateFormat, String endDateFormat) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(endDateFormat);
		try {
			if (date != null) {
				formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
			    Date initDate = new SimpleDateFormat(initDateFormat).parse(date);
			    
			    return formatter.parse(formatter.format(initDate));
			}
		} catch (ParseException e) {
			System.out.println("ParseException: " + date);
		}
	    return null;
	}

	public static Date getFormatDateVN(String date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
//    	sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		if (date != null) {
			try {
				return sdf.parse(date);
			} catch (ParseException e) {
				System.out.println("ParseException: " + date);
			}
		}
		return null;
	}

	public static String getFormatDateVN(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
//    	sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		if (date != null) {
			return sdf.format(date);
		} else {
			return "";
		}
	}

	public static String getFormatDate(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
		if (date != null) {
			sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			return sdf.format(date);
		} else {
			return "";
		}
	}

	public static String CalculatorAge(String date) {
		String[] arr = date.split("/");
		LocalDate dob = LocalDate.parse(arr[2] + "-" + arr[1] + "-" + arr[0]);
		return String.valueOf(getAge(dob));
	}

	public static int getAge(LocalDate dob) {
		LocalDate curDate = LocalDate.now();
		int age = curDate.getYear() - dob.getYear();
		return age;
	}
	
	public static String plusHour(int hour) {
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(new Date()); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY, hour); // adds one hour
	    cal.getTime(); // retu
	    return getFormatDateVN(cal.getTime(), PT_DD_MM_YYYY_HH_MM_SS);
	}
	
	public static int getDiffYears(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(YEAR) - a.get(YEAR);
        if (a.get(MONTH) > b.get(MONTH) || 
            (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
            diff--;
        }
        return diff;
    }
    
    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTime(date);
        return cal;
    }
	
	public static String getDiffDatetime(Date date) {
	    String dateStop = getFormatDate(new Date(), PT_YYYY_MM_DD_HH_MM_SS);
	    String dateStart = getFormatDate(date, PT_YYYY_MM_DD_HH_MM_SS);

		//HH converts hour in 24 hours format (0-23), day calculation
		SimpleDateFormat format = new SimpleDateFormat(PT_YYYY_MM_DD_HH_MM_SS, Locale.ENGLISH);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        
		Date d1 = null;
		Date d2 = null;

		try {
			d1 = format.parse(dateStart);
			d2 = format.parse(dateStop);

			//in milliseconds
			long diff = d2.getTime() - d1.getTime();

			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			int diffYears = getDiffYears(d1, d2);
			
			if (diffYears != 0){
			    return Integer.toString(diffYears) + "y";
			}
			if (diffDays != 0){
			    return Long.toString(diffDays) + "d";
			}
			if (diffHours != 0){
			    return Long.toString(diffHours) + "h";
			}
			if (diffMinutes != 0){
			    return Long.toString(diffMinutes) + "m";
			}
			if (diffSeconds != 0){
			    return Long.toString(diffSeconds) + "s";
			}
			
			return "";
    
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static Date stringToDate(String str) {
		DateFormat df = new SimpleDateFormat(PT_DD_MM_YYYY_HH_MM_SS);
		Date date;
		if (str != null) {
			try {
				date = df.parse(str);
			} catch (ParseException e) {
				try {
					df = new SimpleDateFormat(PT_DD_MMM_YYYY);
					date = df.parse(str);
				} catch (ParseException ex) {
					date = null;
					System.out.println(ex.getMessage());
				}
			}
		} else {
			date = null;
		}
		return date;
	}
	
	public static Date fomatTimeZoneToUTC(String dateString, String patternFrom, String patternTo, String timeZone) {
	
		DateFormat formatterIST = new SimpleDateFormat(patternFrom);
		DateFormat formatterUTC = new SimpleDateFormat(patternTo);
		if (!StringUtils.isEmpty(dateString)) {
			try {
				formatterIST.setTimeZone(TimeZone.getTimeZone(timeZone)); // better than using IST
				Date date = formatterIST.parse(dateString);
				System.out.println(formatterIST.format(date)); // output: 15-05-2014 00:00:00
				
				formatterUTC.setTimeZone(TimeZone.getTimeZone("UTC")); // UTC timezone
				String utc = formatterUTC.format(date); // output: 14-05-2014 18:30:00
				System.out.println(utc); // output: 15-05-2014 00:00:00
				return formatterUTC.parse(utc);
			} catch (ParseException e) {
				System.out.println("ParseException: " + dateString);
			}
		}
		return null;
	}
	
	/*
	 * @Des compare 2 datetime
	 * @return 0 if equal, < 0 if dateToCompare < datenow, > 0 if dateToCompare > datenow
	 */
	public static int compareToDatimeNow(Date dateToCompare) throws ParseException {
		Date now = new Date();
		DateFormat formatterUTC = new SimpleDateFormat(PT_YYYY_MM_DD_HH_MM_SS);
		
		formatterUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
		String utc = formatterUTC.format(dateToCompare);
		Date dateCompare = formatterUTC.parse(utc);
		
        int diff = dateCompare.compareTo(now);
        
        return diff;
	}
	
	/*
	 * @Des compare 2 date
	 * @return 0 if equal, < 0 if dateToCompare < datenow, > 0 if dateToCompare > datenow
	 */
	public static int compareToCurrentDate(Date dateToCompare) throws ParseException {
		Date now = new Date();
		DateFormat formatterUTC = new SimpleDateFormat(PT_YYYY_MM_DD);
		
		formatterUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
		String utc = formatterUTC.format(dateToCompare);
		Date dateCompare = formatterUTC.parse(utc);
		String utcNow = formatterUTC.format(now);
		now = formatterUTC.parse(utcNow);
		
        int diff = dateCompare.compareTo(now);
        
        return diff;
	}

	/*
	 * @Des format of start, end: yyyy-mm-dd
	 *  dayOfWeek : Calendar.MON...
	 */
	public static List<Date> getAllDayBetweenTwoDates(String start, String end, int dayOfWeek) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(PT_DD_MM_YYYY_);
		Date currentDate = new Date();
		Calendar today = Calendar.getInstance();
		today.setTime(dateFormat.parse(getFormatDate(currentDate, PT_DD_MM_YYYY_)));
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(dateFormat.parse(start));
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(dateFormat.parse(end));
		
		if (startDate.before(today)) {
			startDate = today;
		}

		ArrayList<Date> dates = new ArrayList<>();
		boolean reachedDayOfWeek = false;
		while (startDate.before(endDate) || startDate.equals(endDate)) {
			if (startDate.get(Calendar.DAY_OF_WEEK) == dayOfWeek) {
				dates.add(startDate.getTime());
				reachedDayOfWeek = true;
			}
			if (reachedDayOfWeek) {
				startDate.add(Calendar.DATE, 7);
			} else {
				startDate.add(Calendar.DATE, 1);
			}
		}
		return dates;
	}
	
	/*
	 * @Des format of start, end: yyyy-mm-dd
	 *  dayOfMonth : 1-31
	 */
	public static List<Date> getAllDayOfMonthBetweenTwoDates(String start, String end, int dayOfMonth) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(PT_DD_MM_YYYY_);
		Date currentDate = new Date();
		Calendar today = Calendar.getInstance();
		today.setTime(dateFormat.parse(getFormatDate(currentDate, PT_DD_MM_YYYY_)));
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(dateFormat.parse(start));
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(dateFormat.parse(end));

		if (startDate.before(today)) {
			startDate = today;
		}
		
		ArrayList<Date> dates = new ArrayList<>();
		boolean reachedDayOfMonth = false;
		while (startDate.before(endDate) || startDate.equals(endDate)) {
			if (startDate.get(Calendar.DAY_OF_MONTH) == dayOfMonth) {
				dates.add(startDate.getTime());
				reachedDayOfMonth = true;
			}
			if (reachedDayOfMonth) {
				startDate.add(Calendar.MONTH, 1);
			} else {
				startDate.add(Calendar.DATE, 1);
			}
		}
		return dates;
	}
}
