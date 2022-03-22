package com.thanglq.connection;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;




public class Connect {

	public Connection getConnection() throws SQLException {
		Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            SQLiteConfig config = new SQLiteConfig();
            config.enableFullSync(true);
            config.setReadOnly(false);
            SQLiteDataSource sQLiteDataSource = new SQLiteDataSource(config);
            String url = "jdbc:sqlite:" +System.getProperty("user.dir")+"/Database/dbAutomation.db3";
            sQLiteDataSource.setUrl(url);
             conn = sQLiteDataSource.getConnection();
            System.out.println("connect success " + conn);
        } catch (ClassNotFoundException se) {
            System.out.println("" + se.getMessage());
        }
		return conn;
	}
	public static void main(String[] args) {
		Connect con = new Connect();
		try {
			Connection connection = con.getConnection();
			con.getData(connection);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void getData(Connection conn) {
			String sql = "SELECT id,steps  FROM management_testcase;";
			Statement stmt  = null;
				try {
					 stmt  = conn.createStatement();
					ResultSet rs = stmt.executeQuery(sql);
			        	 System.out.println("cau tra loi dung la "+rs.getString("steps"));	
			        	 String tmp = rs.getString("steps");
//			        	 System.out.println("thang=="+ string);
			        	 String test="{\"feature_name\":\"login\",\"scenario_id\":1,\"steps\":[{\"order\":\"step1\",\"action\":\"Click\",\"locator\":\"//div[text()='Program Manager']\",\"type\":\"Xpath\",\"value\":\"thang test\"},{\"order\":\"step2\",\"action\":\"Click\",\"locator\":\"//div[text()='Program Manager']\",\"type\":\"Xpath\",\"value\":\"thang test\"},{\"order\":\"step3\",\"action\":\"Click\",\"locator\":\"//div[text()='Program Manager']\",\"type\":\"Xpath\",\"value\":\"thang test\"}]}";
			        	JSONObject obj = new JSONObject(test);
			        	System.out.println(obj.get("steps").toString());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally {
					try {
						stmt.close();
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						
					}
				}
	}
//	public static void main(String[] args) {
//		Connect con = new Connect();
//		con.getConnection();
//	}
}
