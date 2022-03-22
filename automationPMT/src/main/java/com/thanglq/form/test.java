package com.thanglq.form;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.yaml.snakeyaml.Yaml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.gson.JsonElement;




public class test {
	public static void main(String[] args) throws IOException  {
		
//		
//		File f = new File("D:\\WorkSpaceCode\\automationPMT\\Pages\\AdminLoginPage.yaml");
//		test t =  new test();
//		String convertYamlToJson = t.convertYamlToJson(f);
//
//		JSONObject obj = new JSONObject(convertYamlToJson);
//		JSONArray arr = new JSONArray(obj.get("actions").toString());
//		for(int i=0;i<arr.length();i++) {
//			System.out.println(arr.get(i));
//
//					
//		}
		

	}
	
	public Map<String, String> findFile(File dir, Map<String, String> list) {
		
		File[] listFiles = dir.listFiles();
		for(File file: listFiles) {
			if(file.isFile()) {
				list.put(file.getName(), file.getAbsolutePath());
				
			}else if(file.isDirectory()) {
				findFile(new File(file.getAbsolutePath()), list);
			}
		}
		return list;
		
	}
	public String convertYamlToJson(File f) throws IOException {
		
		    ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
		    Object obj = yamlReader.readValue(f, Object.class);

		    ObjectMapper jsonWriter = new ObjectMapper();
		    return jsonWriter.writeValueAsString(obj);
		}
	

}
