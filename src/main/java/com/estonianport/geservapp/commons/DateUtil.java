package com.estonianport.geservapp.commons;

import java.time.format.DateTimeFormatter;

public class DateUtil {

	public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	public static final DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM");
	
	public static final DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("dd");

	public static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
	
	public static final DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("HH");
	
	public static final DateTimeFormatter minuteFormatter = DateTimeFormatter.ofPattern("mm");
	
}
