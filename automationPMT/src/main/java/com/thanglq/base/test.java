package com.thanglq.base;


import java.io.IOException;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.restassured.*;
import io.restassured.response.Response;

public class test {
	
//	protected String getLocator(String string) {
//        try {
//            Object proxyOrigin = FieldUtils.readField(string, "h", true);
//            Object locator = FieldUtils.readField(proxyOrigin, "locator", true);
//            Object findBy = FieldUtils.readField(locator, "by", true);
//            if (findBy != null) {
//                return findBy.toString();
//            }
//        } catch (IllegalAccessException ignored) {
//        }
//        return "[unknown]";
//    }

	public void testAbc() {

		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		RestAssured.useRelaxedHTTPSValidation();
//		RestAssured.config = RestAssured.config().sslConfig(sslConfig().allowAllHostnames());
		Response response = RestAssured.get("https://reqres.in/api/user");
//		  Response res = RestAssured.get("https://reqres.in/api/user");
	        System.out.println(response.getStatusCode());
	}
//	https://www.baeldung.com/java-http-response-body-as-string
//	https://www.baeldung.com/httpclient-4-cookies
	public static void main(String[] args) throws IOException, ParseException {
////		

		HttpGet request = new HttpGet("https://reqres.in/api/user/2");


		CloseableHttpClient client = HttpClients.createDefault();


		CloseableHttpResponse response = client.execute(request);
	

		HttpEntity entity = response.getEntity();
		String result = EntityUtils.toString(entity);
		System.out.println("result === "+ result);
		
		
	}
	

}
