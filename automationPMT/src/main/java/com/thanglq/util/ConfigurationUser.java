package com.thanglq.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.github.javafaker.Faker;

public class ConfigurationUser {
	
	static Faker faker = new Faker();
	public static String LAST_NAME;
	public static String FIRST_NAME;
	public static String PREFIX;
	public static String SUFFIX;
	public static String EMAIL;
	public static String STREET;
	public static String CITY;
	public static String ZIP_CODE;
	public static String PHONE;
	
	
	public static String setZipCode5Number(String zipcode) {
		if(zipcode.length()>5) {
			String[] arr =zipcode.split("-");
			return arr[0];
		}
		return zipcode;
			
		}
	public static void GenerateDataUser() {
		ConfigurationUser.LAST_NAME = faker.name().lastName();
		ConfigurationUser.FIRST_NAME = faker.name().firstName();
		ConfigurationUser.PREFIX  = faker.name().prefix();
		ConfigurationUser.SUFFIX = faker.name().suffix();
		ConfigurationUser.EMAIL = faker.internet().emailAddress();
		ConfigurationUser.STREET  = faker.address().streetAddress();			
		ConfigurationUser.CITY = faker.address().city();	
		ConfigurationUser.ZIP_CODE = setZipCode5Number(faker.address().zipCode());
		ConfigurationUser.PHONE  = faker.phoneNumber().subscriberNumber(10);
		
	}
}

