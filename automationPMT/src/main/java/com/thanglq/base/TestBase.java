package com.thanglq.base;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableCellRenderer;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.python.modules.synchronize;
import org.python.modules.thread.thread;
import org.sikuli.script.Key;
import org.sikuli.script.Screen;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import com.thanglq.bean.ActionsElement;
import com.thanglq.bean.FieldBean;
import com.thanglq.bean.Locators;
import com.thanglq.bean.SettingConfig;
import com.thanglq.dao.LoadActions;
import com.thanglq.dao.LoadData;
import com.thanglq.form.FormMain;
import com.thanglq.util.ActionWithSikuli;
import com.thanglq.util.ConfigurationUser;
import com.thanglq.util.FakeData;
import com.thanglq.util.LoadConfig;
import com.thanglq.util.ReportHTMl;
import com.thanglq.util.ScreenShot;
import com.thanglq.util.WriteFileProperties;

import atu.testrecorder.ATUTestRecorder;
import atu.testrecorder.exceptions.ATUTestRecorderException;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

	public WebDriver driver;
	public WebDriverWait wait;
	public ReportHTMl report;
	public LoadData data;
	String sql_feature = "SELECT features_name FROM features where id=? ;";
	String sql_scenario = "SELECT name  FROM management_scenario where id =? ;";
	private ScreenShot screenShot;
	private Map<String, Locators> mapElements;
	private Map<String, ActionsElement> mapActions;
	private LoadActions loadActions;
	private ExtentTest extentTest;
	private FakeData fakeData;
	private ActionWithSikuli Sikuli;
	private WriteFileProperties write;
	private String path_file = System.getProperty("user.dir")
			+ "\\src\\main\\resources\\Configuration\\save_tmp.properties";
	public static String content;
	public Map<String, String> keyValue= new LinkedHashMap<String, String>();
	public void StartBrowser(String browser, String Url) {
		DesiredCapabilities caps = null;

		if (browser.equals("chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();

			if (!SettingConfig.RESOLUTION.equals("")) {
				caps = new DesiredCapabilities();
				options.addArguments("--disable-notifications");
				options.addArguments("disable-infobars");
				// options.setHeadless(true);
				caps = DesiredCapabilities.chrome();
				caps.setCapability(ChromeOptions.CAPABILITY, options);
				driver = new ChromeDriver(caps);
				driver.manage().window()
						.setSize(new Dimension(Integer.parseInt(SettingConfig.RESOLUTION.substring(0, 4)),
								Integer.parseInt(SettingConfig.RESOLUTION.substring(5))));

			} else {
				options.addArguments("--disable-notifications");
				options.addArguments("disable-infobars");
				// options.setHeadless(true);
				caps = DesiredCapabilities.chrome();
				caps.setCapability(ChromeOptions.CAPABILITY, options);
				driver = new ChromeDriver(caps);

				driver.manage().window().maximize();
			}

		} else if (browser.equals("firefox")) {
			WebDriverManager.firefoxdriver().setup(); // can also use set property method for browser executables
			if (!SettingConfig.RESOLUTION.equals("")) {
				driver = new FirefoxDriver();
				driver.manage().window()
						.setSize(new Dimension(Integer.parseInt(SettingConfig.RESOLUTION.substring(0, 4)),
								Integer.parseInt(SettingConfig.RESOLUTION.substring(5))));

			} else {
				driver = new FirefoxDriver();
				driver.manage().window().maximize();
			}

		} else {
			throw new RuntimeException("Browser type unsupported");
		}

		driver.get(Url);
	}

	public void StepByStep(List<FieldBean> listBean, JTable tblData, boolean flag) {

		loadActions = new LoadActions();
		fakeData = new FakeData();
		ExtentReports extent = null;
		report = new ReportHTMl();
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(SettingConfig.PATH + "/extent.html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		data = new LoadData();
		screenShot = new ScreenShot();
		if(!flag) {
			
			wait = getWait();
		}
		write = new WriteFileProperties();

		content = "--------Starting----------------" + "\n";
		updateProgress(content);
		for (int i = 0; i < listBean.size(); i++) {
			if (listBean.get(i).getRun()) {

				if (listBean.get(i).getAction().equals("Generate Data")) {
					content = content + "generating data........" + "\n";
					updateProgress(content);
					ConfigurationUser.GenerateDataUser();
					continue;
				}

				if (!listBean.get(i).getAction_page().equals("")) {
					content = content + "running action " + listBean.get(i).getAction_page().trim() + " \n";
					updateProgress(content);
					runAction(listBean.get(i).getAction_page().trim(), extent);
					continue;
				}
				content = content + "Action : " + listBean.get(i).getAction() + " at Step " + (i + 1) + "\n";
				updateProgress(content);

				boolean action = Action(listBean.get(i).getAction(), listBean.get(i).getLocator(),
						listBean.get(i).getType(), getDataFake(listBean.get(i).getValue()));
				if (SettingConfig.IS_REPORT) {

					try {
						extentTest = extent.createTest(data.getName(FormMain.scenario_id, sql_scenario),
								data.getName(FormMain.feature_id, sql_scenario));
						report.getExtendReport(action, extentTest, listBean.get(i).getAction(),
								listBean.get(i).getType(), screenShot, driver, SettingConfig.PATH, "Step " + (i + 1));
					} catch (SQLException e) {

						e.printStackTrace();

					}

				}

				if (!action) {
					System.out.println("Error Steps " + (i + 1) + "with action is " + listBean.get(i).getAction());
				}
			}

		}
		if (SettingConfig.IS_REPORT) {
			content = content + "generating Report........" + "\n";
			updateProgress(content);
			extent.flush();
		}
		if (SettingConfig.LOOP != null) {
			content = content + "Starting run loop........" + "\n";
			updateProgress(content);
			if (!SettingConfig.LOOP.equals("")) {
				String loop = SettingConfig.LOOP;
				String[] arr_loop = loop.split(";");

				for (int i = 0; i < SettingConfig.COUNT_LOOP; i++) {

					for (int j = (Integer.parseInt(arr_loop[0].trim()) - 1); j < Integer.parseInt(arr_loop[1]); j++) {
						if (listBean.get(j).getAction().equals("Generate Data")) {
							ConfigurationUser.GenerateDataUser();
							continue;
						}
						if (listBean.get(j).getRun()) {
							if (!listBean.get(j).getAction_page().equals("")) {
								runAction(listBean.get(j).getAction_page().trim(), extent);
								continue;
							}
							Action(listBean.get(j).getAction(), listBean.get(j).getLocator(), listBean.get(j).getType(),
									getDataFake(listBean.get(j).getValue()));
						}
					}
				}

			}
		}
		content = content + "End test automation........" + "\n";
		updateProgress(content);

	}

	public WebDriverWait getWait() {

		WebDriverWait wait = new WebDriverWait(driver, Integer.parseInt(LoadConfig.WAIT));
		return wait;
	}

	public List<By> getWaitElement(String element, String type, String action) {
		List<By> list = new LinkedList<By>();
		By element_temp;
		String[] arrElement = null;
		if (!element.equals("")) {
			arrElement = element.split(";");
		}

		switch (type) {

		case "ID":
			content = content + "find ID........" + arrElement[0] + "\n";
			updateProgress(content);
			for (int i = 0; i < arrElement.length; i++) {
				element_temp = By.id(arrElement[i]);
				list.add(element_temp);
			}

			break;
		case "CSS":
			content = content + "find CSS........" + arrElement[0] + "\n";
			updateProgress(content);
			for (int i = 0; i < arrElement.length; i++) {
				element_temp = By.cssSelector(arrElement[i]);
				list.add(element_temp);
			}

			break;
		case "NAME":
			content = content + "find name........" + arrElement[0] + "\n";
			updateProgress(content);
			for (int i = 0; i < arrElement.length; i++) {
				element_temp = By.name(arrElement[i]);
				list.add(element_temp);
			}
			break;
		case "TAG":
			content = content + "find tag........" + arrElement[0] + "\n";
			updateProgress(content);
			for (int i = 0; i < arrElement.length; i++) {
				element_temp = By.tagName(arrElement[i]);
				list.add(element_temp);
			}
			break;
		case "XPATH":
			content = content + "find xpatht........" + arrElement[0] + "\n";
			updateProgress(content);
			for (int i = 0; i < arrElement.length; i++) {
				element_temp = By.xpath(arrElement[i]);
				list.add(element_temp);
			}
			break;
		case "CLASS":
			content = content + "find class........" + arrElement[0] + "\n";
			updateProgress(content);
			for (int i = 0; i < arrElement.length; i++) {
				element_temp = By.className(arrElement[i]);
				list.add(element_temp);
			}
			break;
		case "partialLinkText":
			content = content + "find partialLinkText........" + arrElement[0] + "\n";
			updateProgress(content);
			for (int i = 0; i < arrElement.length; i++) {
				element_temp = By.partialLinkText(arrElement[i]);
				list.add(element_temp);
			}
			break;
		default:
			content = content + "Not SupportType........" + "\n";
			updateProgress(content);
			System.out.println("Not SupportType");
			break;
		}

		return list;

	}

	public boolean Action(String action, String element, String type, String data) {
		List<By> list = getWaitElement(element, type, action);
//		wait = getWait();
		boolean flag = false;
		Object result;
//		 Thread currentThread = Thread.currentThread();
		if (type.equals("IMAGE"))
			try {
				Sikuli = new ActionWithSikuli(action, data);
				Sikuli.runWithImage();
//					 Sikuli.start();
//				Thread t1 = new Thread(Sikuli);
//				t1.start();
//				

			} catch (Exception e) {
				e.printStackTrace();
			}
		else {
			By byElement = null;
			if (list.size() > 0) {

				byElement = list.get(0);
			}
			switch (action) {

			case "Click":
				result = getResult(wait, byElement, action, data);
				((WebElement) result).click();
				flag = true;
				break;
			case "Double Click":
				result = getResult(wait, byElement, action, data);
				((WebElement) result).click();
				((WebElement) result).click();
				flag = true;
				break;
			case "Input":
				result = getResult(wait, byElement, action, data);
				((WebElement) result).sendKeys(data);
				flag = true;
				break;
			case "Verify Exact":
				result = getResult(wait, byElement, action, data);
				String text = ((WebElement) result).getText();
				if (text.equals(data)) {
					flag = true;
				}
				break;
			case "Verify":
				result = getResult(wait, byElement, action, data);
				String tmp = ((WebElement) result).getText();
				if (tmp.contains(data)) {
					flag = true;
				}
				System.out.println("result =" + flag);
				break;
			case "clear":
				result = getResult(wait, byElement, action, data);
				((WebElement) result).clear();
				flag = true;
				break;
			case "Save text":
				result = getResult(wait, byElement, action, data);
				String value = ((WebElement) result).getText();
//				flag = write.WriteFile(path_file, data, value);
				addKeyvalue(data, value);
				
				break;
			case "displayed":
				try {
					wait.until(ExpectedConditions.visibilityOfElementLocated(byElement));
					flag = true;
					break;
				} catch (Exception e) {
					flag = false;
					break;
				}
			case "Drag And Drop":
				try {
					result = getResult(wait, byElement, action, data);
					Object result2 = getResult(wait, list.get(1), action, data);
					Actions act = new Actions(driver);
					act.dragAndDrop(((WebElement) result), ((WebElement) result2)).build().perform();
					flag = true;
					break;
				} catch (Exception e) {
					flag = false;
					break;
				}
			case "not_displayed":
				wait.until(ExpectedConditions.invisibilityOfElementLocated(byElement));
				flag = true;
				break;
			case "enabled":
				By byElement_tmp = byElement;
				flag = wait.until(new ExpectedCondition<Boolean>() {

					public Boolean apply(WebDriver driver) {
						WebElement button = driver.findElement(byElement_tmp);
						String enabled = button.getAttribute("disabled");
						if (enabled == null)
							return true;
						else
							return false;
					}
				});

				break;
			case "not_enabled":
				By byElement_tmp_2 = byElement;
				flag = wait.until(new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver driver) {
						WebElement button = driver.findElement(byElement_tmp_2);
						String enabled = button.getAttribute("disabled");
						if (enabled != null)
							return true;
						else
							return false;
					}
				});
				break;

			case "sleep":
				try {
					Thread.sleep(Integer.parseInt(data));
					return true;
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "Navigate":
				try {
					driver.navigate().to(data);
					flag = true;
				} catch (Exception e) {
					flag = false;
				}

				break;
			case "swithTo":
				try {
					wait.until(ExpectedConditions.numberOfWindowsToBe(2));
					Set<String> titles = driver.getWindowHandles();
					for (String title : titles) {
						if (title.equals(data)) {
							driver.switchTo().window(title);
							flag = true;
						}
					}

				} catch (Exception e) {
					flag = false;
				}

				break;
			default:
//					waitElement.sendKeys(data);
				break;
			}
		}

		return flag;

	}

//	By byElement = getWaitElement(element, type, action);
//	wait = getWait();
//	 boolean flag= false;
//	switch (action) {
//	case "Click":
//		Object result = getResult(wait, byElement, action, data);
//		((WebElement) result).click();
//		break;
//	case "Input":
//		Object result = getResult(wait, byElement, action, data);
//		result.sendKeys(data);
//		break;
//	case "Verify":
//		String text = waitElement.getText();
//		flag = getVerify(text, data);
//		break;	
//	case "clear":
//		
//		waitElement.clear();
//		
//		break;	
//	default:
//		waitElement.sendKeys(data);
//		break;
//	}
//	return flag;
//	
//	
//	
//}
	public boolean getVerify(String autual, String expect) {
		if (autual.trim().equals(expect.trim())) {
			return true;
		} else {
			return false;
		}
	}

	public <T> Object getResult(WebDriverWait wait, By byElement, String action, String data) {

		Object obj = null;
		switch (action) {
		case "Click":
			obj = wait.until(ExpectedConditions.elementToBeClickable(byElement));
			break;
		case "Input":
			obj = wait.until(ExpectedConditions.elementToBeClickable(byElement));
			break;
		case "Verify":
			obj = wait.until(ExpectedConditions.visibilityOfElementLocated(byElement));
			break;
		case "Verify Exact":
			obj = wait.until(ExpectedConditions.visibilityOfElementLocated(byElement));
			break;
		case "not_enabled":
			obj = wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOfElementLocated(byElement)));
			break;
		case "clear":
			obj = wait.until(ExpectedConditions.elementToBeClickable(byElement));
			break;
		case "Save text":
			obj = wait.until(ExpectedConditions.visibilityOfElementLocated(byElement));
			break;
		case "Drag And Drop":
			obj = wait.until(ExpectedConditions.visibilityOfElementLocated(byElement));
			break;

		default:
			System.out.println("not support for element " + byElement);
			break;
		}

		return obj;

	}

	public void runAction(String name, ExtentReports extent) {
		boolean action = false;
		try {
			List<FieldBean> listBean = loadActions.getListActions(name);

			for (int i = 0; i < listBean.size(); i++) {
				List<By> list = getWaitElement(listBean.get(i).getLocator(), listBean.get(i).getType(),
						listBean.get(i).getAction());
				By byElement = list.get(0);
				if (!listBean.get(i).getCondition().equals("")) {
					String condition = listBean.get(i).getCondition();
					if (!listBean.get(i).getTimeOut().equals("")) {
						long timeOut = Long.parseLong(listBean.get(i).getTimeOut());
						WebDriverWait wait_tmp = new WebDriverWait(driver, timeOut);
						if (condition.equals("DISPLAYED")) {

							try {
								WebElement element = wait_tmp
										.until(ExpectedConditions.visibilityOfElementLocated(byElement));
								if (element.isDisplayed()) {
									action = Action(listBean.get(i).getAction(), listBean.get(i).getLocator(),
											listBean.get(i).getType(), getDataFake(listBean.get(i).getValue()));
								}
							} catch (Exception e) {
								continue;
							}

						} else if (condition.equals("ENABLED")) {
							Boolean result = wait_tmp.until(new ExpectedCondition<Boolean>() {
								public Boolean apply(WebDriver driver) {
									WebElement button = driver.findElement(byElement);
									String enabled = button.getAttribute("disabled");
									if (enabled == null)
										return true;
									else
										return false;
								}
							});
							if (result) {
								action = Action(listBean.get(i).getAction(), listBean.get(i).getLocator(),
										listBean.get(i).getType(), getDataFake(listBean.get(i).getValue()));
							}

						}
					} else {
						if (condition.equals("DISPLAYED")) {

							try {
								WebElement element = wait
										.until(ExpectedConditions.visibilityOfElementLocated(byElement));
								if (element.isDisplayed()) {
									action = Action(listBean.get(i).getAction(), listBean.get(i).getLocator(),
											listBean.get(i).getType(), getDataFake(listBean.get(i).getValue()));
								}
							} catch (Exception e) {
								continue;
							}

						} else if (condition.equals("ENABLED")) {
							Boolean result = wait.until(new ExpectedCondition<Boolean>() {
								public Boolean apply(WebDriver driver) {
									WebElement button = driver.findElement(byElement);
									String enabled = button.getAttribute("disabled");
									if (enabled == null)
										return true;
									else
										return false;
								}
							});
							if (result) {
								action = Action(listBean.get(i).getAction(), listBean.get(i).getLocator(),
										listBean.get(i).getType(), getDataFake(listBean.get(i).getValue()));
							}

						}

					}
				} else {
					action = Action(listBean.get(i).getAction(), listBean.get(i).getLocator(),
							listBean.get(i).getType(), getDataFake(listBean.get(i).getValue()));
				}

				if (SettingConfig.IS_REPORT) {

					try {
						extentTest = extent.createTest(data.getName(FormMain.scenario_id, sql_scenario),
								data.getName(FormMain.feature_id, sql_scenario));
						report.getExtendReport(action, extentTest, listBean.get(i).getAction(),
								listBean.get(i).getType(), screenShot, driver, SettingConfig.PATH,
								"Step Action " + (i + 1));
					} catch (SQLException e) {

						e.printStackTrace();

					}

				}
			}
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.toString(), "Warning", JOptionPane.CLOSED_OPTION);
			e.printStackTrace();

		}

	}

	public String getRandomNumber() {
		int min = 0;
		int max = 10000;
		String number = (int) Math.floor(Math.random() * (max - min + 1) + min) + "";
		return number;
	}

	public String getDataFake(String value) {
		switch (value.trim()) {
		case "USER.firstname":
			return ConfigurationUser.FIRST_NAME;
		case "USER.lastname":
			return ConfigurationUser.LAST_NAME;
		case "USER.prefix":
			return ConfigurationUser.PREFIX;
		case "USER.suffix":
			return ConfigurationUser.SUFFIX;
		case "USER.street":
			return ConfigurationUser.STREET;
		case "USER.city":
			return ConfigurationUser.CITY;
		case "USER.zipcode":
			return ConfigurationUser.ZIP_CODE;
		case "USER.phone":
			return ConfigurationUser.PHONE;
		case "USER.email":
			return ConfigurationUser.EMAIL;
		default:
//			return value;
//			String value_tmp = convertData(value, path_file);
			return getData(value);
//			return value_tmp;
		}
	}

	public void deleteFile(List<String> listPath) {
		if (listPath.size() > 0) {
			for (int i = 0; i < listPath.size(); i++) {
				System.out.println("path==== " + listPath.get(i));
				File f = new File(listPath.get(i));
				if (f.exists()) {
					f.delete();
				}
			}
		}

	}

	public String convertData(String value, String path) {
		if (value.contains("KEY.")) {
			value = write.readFile(value, path);

		}

		return value;
	}

	public void updateProgress(String content) {
		FormMain.textPane.setText(content);
		FormMain.textPane.setCaretPosition(FormMain.textPane.getDocument().getLength());
	}
	public void addKeyvalue(String key, String data) {
		if(key!="" || key!=null) {
			keyValue.put("KEY."+key, data);
		}else {
			JOptionPane.showConfirmDialog(null, "please input key in saveText function", "Warning",
					JOptionPane.CLOSED_OPTION);
			return;
		}
	}
	public String getData(String data) {
//		String key = "KEY."+data;
		if(keyValue.containsKey(data)) {
			return keyValue.get(data);
		}
	
		return data;
	}
	
}
