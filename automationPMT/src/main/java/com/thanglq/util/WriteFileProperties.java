package com.thanglq.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class WriteFileProperties {

	public boolean WriteFile( String path, String key, String value) {
		boolean flag= false;
		if(CheckFileExist(path)) {
			Properties pro = new Properties();
			pro.setProperty("KEY."+key,value);
			try {
				SaveFile(pro, path);
				flag = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			UpdateFile(path, key, value);
			flag = true;
		}
		return flag;
		
	}
	public void saveData(String key, String value, Map<String, String> keyValue) {
	
			keyValue.put(key, value);
			Set<String> keySet = keyValue.keySet();
			Object[] array = keySet.toArray();
			for(int i=0;i<array.length;i++) {
				keyValue.get(array[i]);
			}
		
	}
	public void SaveFile(Properties pro, String path) throws IOException {
		File file = new File(path);
        FileOutputStream fr;
		try {
			fr = new FileOutputStream(file);
			  pro.store(fr, "Properties");
		        fr.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}       
	}
	public void UpdateFile(String path, String key, String data) {
		try {
			FileInputStream in = new FileInputStream(new File(path));
			Properties props = new Properties();
			props.load(in);
			in.close();

			FileOutputStream out = new FileOutputStream(new File(path));
			props.setProperty("KEY."+key, data);
			props.store(out, null);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	public boolean CheckFileExist(String path) {
		File f = new File(path);
				if(f.exists()) {
					return false;
				}
				else {
					return true;
				}
	}
	public String readFile(String key, String path) {
		String data = null;
		Properties config = new Properties();
			try {
				config.load(new FileInputStream(path));
				 data = config.getProperty(key);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return data;
	}
	public static void main(String[] args) {
		WriteFileProperties t = new WriteFileProperties();
		t.WriteFile("D:/WorkSpaceCode/automationPMT/src/main/resources/Configuration/test.properties", "name2", "lqthang2");
	}
}
