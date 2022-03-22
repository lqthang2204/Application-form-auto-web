package com.thanglq.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;

import org.sikuli.script.Screen;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;

public class FakeData {

	Faker faker = new Faker();
	
	public void getname(String suffix) {
		Name nameTep = faker.name();
//		https://www.youtube.com/watch?v=YHZVhncWBac&ab_channel=RaVen
		https://stackoverflow.com/questions/26271760/filtering-a-jlist-from-text-field-input
			//winfoen builder
//			https://www.techlistic.com/p/selenium-practice-form.html
		System.out.println(nameTep);
	}
	
	public void generateData(String path) {
		String firstname = faker.name().firstName();
		String lastname = faker.name().lastName();
		String prefix = faker.name().prefix();
		String  suffix = faker.name().suffix();
		String street = faker.address().streetAddress();
		String city = faker.address().city();		
		String zipcode = setZipCode5Number(faker.address().zipCode());
		String phone = faker.phoneNumber().subscriberNumber(10);
		String email = faker.internet().emailAddress();
		Properties properties = new Properties();
		try(FileOutputStream outputStream = new FileOutputStream(path)){  
		    properties.setProperty("USER.firstname", firstname);
		    properties.setProperty("USER.lastname", lastname);
		    properties.setProperty("USER.prefix", prefix);
		    properties.setProperty("USER.suffix", suffix);
		    properties.setProperty("USER.street", street);
		    properties.setProperty("USER.city", city);
		    properties.setProperty("USER.zipcode", zipcode);
		    properties.setProperty("USER.phone", phone);
		    properties.setProperty("USER.email", email);
		    properties.store(outputStream, null);
		} catch (IOException e) {
		    e.printStackTrace();
		} 
		
	}
	public static void main(String[] args) {
		  Screen s = new Screen();
////	                s.doubleClick("C:/Users/lquangthang/Desktop/new/Screenshot_4.png");
//	        	s.click("C:/Users/lquangthang/Desktop/new/Screenshot_4.png");
//	        	s.type(Key.F2);
		s.type("C:\\Users\\lquangthang\\Desktop\\new\\Screenshot_5.png", "lquangthang");
	}
	
	public String setZipCode5Number(String zipcode) {
	if(zipcode.length()>5) {
		String[] arr =zipcode.split("-");
		return arr[0];
	}
	return zipcode;
		
	}
	
}
