package com.cts.user.application.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cts.user.application.to.UserBirthDate;

public class Utils {
	public static final DateFormat DATE_FORMATER = new SimpleDateFormat("yyyy-MM-dd");
	public static Date convertDateRequestToDate(UserBirthDate birtDate) throws ParseException {
		Date date = null;
		String dateString = null;
		try {
			if(birtDate.getDate() != null)
			{
				dateString = birtDate.getDate().getYear()
						+"-"+birtDate.getDate().getMonth()
						+"-"+birtDate.getDate().getDay();			
			}
			date = DATE_FORMATER.parse(dateString);
		} catch (ParseException e) {
			System.out.println("In Utils: "+ e.getMessage());
			throw e;
		}
		return date;
	}
}
