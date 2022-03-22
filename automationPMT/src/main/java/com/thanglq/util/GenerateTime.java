package com.thanglq.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GenerateTime {
	
	public String getTimeNow() {
		DateFormat dateFormate = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		String format = dateFormate.format(date);
		return format;
	}
	public static void main(String[] args) {
		GenerateTime ti = new GenerateTime();
		ti.getTimeNow();
	}

}
