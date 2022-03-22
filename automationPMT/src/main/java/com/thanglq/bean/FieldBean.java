package com.thanglq.bean;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FieldBean {
	
	private String order;
	private String action;
	private String locator;
	private String type;
	private String value;
	private Map<String, FieldBean> map;
	private boolean isImage;
	private String browser;
	private String AssertValue;
	private String page;
	private String locator_page;
	private String action_page;
	private boolean run;
	private String condition;
	private String timeOut;
	private String description;
	

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public boolean getRun() {
		return run;
	}
	public void setRun(boolean run) {
		this.run = run;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getLocator_page() {
		return locator_page;
	}
	public void setLocator_page(String locator_page) {
		this.locator_page = locator_page;
	}
	public String getAction_page() {
		return action_page;
	}
	public void setAction_page(String action_page) {
		this.action_page = action_page;
	}
	public String getAssertValue() {
		return this.AssertValue;
	}
	public void setAssertValue(String assertValue) {
		this.AssertValue = assertValue;
	}
	public boolean getIsImage() {
		return this.isImage;
	}
	public void setImage(boolean isImage) {
		this.isImage = isImage;
	}
	public String getOrder() {
		return this.order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getAction() {
		return this.action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getLocator() {
		return this.locator;
	}
	public void setLocator(String locator) {
		this.locator = locator;
	}
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return this.value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Map<String, FieldBean> getMap() {
		return this.map;
	}
	public void setMap(Map<String, FieldBean> map) {
		this.map = map;
	}
	
	
	
	
	

}
