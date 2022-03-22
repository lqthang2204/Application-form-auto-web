package com.thanglq.util;



public class EncodeJson {
	public String encodeKey(String key) {
//		System.out.println(key.replace("\\", "\\\\").replace(".", "\\u002e"));
		return key.replace("\\", "\\\\").replace(".", "\\u002e");
	}
	public String decodeKey(String key) {
		 return key.replace("\\u002e", ".").replace("\\\\", "\\");
	}

	
	public static void main(String[] args) {
		EncodeJson e = new EncodeJson();
		String tmp = e.decodeKey(e.encodeKey("{\"feature_name\":\"login\",\"scenario_id\":1,\"steps\":[{\"order\":\"step1\",\"action\":\"Click\",\"locator\":\"//div[text()='Program Manager']\",\"type\":\"Xpath\",\"value\":\"thang test\"},{\"order\":\"step2\",\"action\":\"Click\",\"locator\":\"//div[text()='Program Manager']\",\"type\":\"Xpath\",\"value\":\"thang test\"},{\"order\":\"step3\",\"action\":\"Click\",\"locator\":\"//div[text()='Program Manager']\",\"type\":\"Xpath\",\"value\":\"thang test\"}]}"));
		System.out.println(tmp);
		System.out.println(e.encodeKey(tmp));
	}

}
