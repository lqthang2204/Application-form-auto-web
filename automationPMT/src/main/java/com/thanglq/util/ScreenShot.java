package com.thanglq.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenShot {
	
	public String takeSnapShot(WebDriver driver, String path, String title) {
		
		TakesScreenshot scrShot = (TakesScreenshot) driver;
		File file = scrShot.getScreenshotAs(OutputType.FILE);
//		String name = file.getName();
		File des = new File(path+"/"+title+".png");
		try {
			FileUtils.copyFile(file, des);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path+"/"+title+".png";
	}
	

}
