package com.thanglq.util;


import org.sikuli.script.Screen;

public class ActionWithSikuli extends Thread {
	private String action;
	private String data;
	
	public ActionWithSikuli(String action, String data) {
		
		this.action = action;
		this.data = data;
		
	}	
	@Override
	public void run() {		
		Screen s = new Screen();
		 switch (action) {
			case "Click":	
				try {
					s.click(data);
//					flag = true;
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case "Input":
				String[] value = data.split(";");
				try {
					s.type(value[0].trim(), value[1]);
//					flag = true;
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case "Double Click":	
				try {
					s.doubleClick(data);
					
//					flag = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			
				
				
			default:
				System.out.println("Not support function!");
//				flag = true;
				break;
			}
	}
	public boolean runWithImage() {
		boolean flag = false;
		Screen s = new Screen();
		 switch (action) {
			case "Click":	
				try {
					s.click(data);
					flag = true;
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case "Input":
				String[] value = data.split(";");
				try {
					s.type(value[0].trim(), value[1]);
					flag = true;
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case "Double Click":	
				try {
					s.doubleClick(data);
					
					flag = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			
				
				
			default:
				System.out.println("Not support function!");
				flag = true;
				break;
			}
		 return flag ;
		
	}

}
