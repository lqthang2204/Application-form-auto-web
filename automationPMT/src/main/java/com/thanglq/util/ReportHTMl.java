package com.thanglq.util;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ReportHTMl {
	
	public void getExtendReport( boolean result,ExtentTest extentTest, String action, String type, ScreenShot screenShot, WebDriver driver, String path,String step) {
		
		if(result) {
			extentTest.pass("passed with action is "+ action +" and type "+ type );
			
		}else {
			extentTest.fail("Failed at "+ step+" with action is "+ action +" and type "+ type);
			try {
				extentTest.addScreenCaptureFromPath(screenShot.takeSnapShot(driver, path,step), step);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}

}
