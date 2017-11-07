package com.cts.user.application.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ApplicationConstants {
	public static final String RESPONSE_OBJECT_USER = "user";
	public static final String RESPONSE_OBJECT_ALLUSER = "users";
	public static final String RESPONSE_POLICYADDED_IND = "userPolicyAdded";
	public static final String RESPONSE_MESSAGE = "message";
	public static final String RESPONSE_STATUS = "status";
	
	public static final String SUCCESS = "1";
	public static final String ERROR = "0";
	
	public static final String YES = "Y";
	public static final String NO = "N";
	
	public static final String ROLE_ADMIN = "admin";
	public static final String ROLE_GENERAL_USER = "user";
	
	public static final DateFormat DATE_FORMATER = new SimpleDateFormat("yyyy-MM-dd");
}
