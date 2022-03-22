package com.thanglq.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;





public class LoadConfig {
	public static String ACTION;
	public static String TYPE;
	public static  String BROWSER;
//	public static String NAVIGATE_URL;
	public static String WAIT;
	public static String APP_FORM;
	public static String URL;
	public static void readConfigAcction() {
		File f = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\Configuration\\configTable.properties");
		
		if(f.exists()) {
			
			Properties config = new Properties();
			try {
				config.load(new FileInputStream(f));
				LoadConfig.ACTION = config.getProperty("action");
				LoadConfig.TYPE = config.getProperty("type");
				LoadConfig.BROWSER  = config.getProperty("browser");
				LoadConfig.WAIT  = config.getProperty("time_wait");
				LoadConfig.APP_FORM= config.getProperty("test_app_form");
				LoadConfig.URL = config.getProperty("url");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

}

