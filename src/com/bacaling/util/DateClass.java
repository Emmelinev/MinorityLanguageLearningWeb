package com.bacaling.util;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateClass {
	public static String getDateNow(){
		Date now = new Date(); 
		Calendar cal = Calendar.getInstance(); 
		DateFormat d = DateFormat.getDateTimeInstance(); 
		String str = d.format(now); 
		return str;
	}
	
}
