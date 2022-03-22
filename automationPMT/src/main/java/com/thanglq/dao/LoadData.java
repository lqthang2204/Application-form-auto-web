package com.thanglq.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.thanglq.bean.FieldBean;
import com.thanglq.connection.Connect;

public class LoadData {
	
	Connect con;
	
	public List<String> LoadScenario() throws SQLException {
		List<String> list = new LinkedList<>();
		con = new Connect();
		Connection connection = null;
		PreparedStatement pst= null;
		try {
			connection = con.getConnection();
			String sql="select * FROM management_scenario;";
				 pst = connection.prepareStatement(sql);
				 ResultSet rs = pst.executeQuery();
				 while(rs.next()) {
					 list.add(rs.getString("name"));
				 }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		finally {
			pst.close();
			connection.close();
		}
		return list;
	}
	public int getIDScenario(String name) throws SQLException {
		int id=0;
		con = new Connect();
		Connection connection = null;
		PreparedStatement pst= null;
		try {
			connection = con.getConnection();
			String sql="select * FROM management_scenario where name=?;";
				 pst = connection.prepareStatement(sql);
				 pst.setString(1, name);
				 ResultSet rs = pst.executeQuery();
				 if(rs.next()) {
					id=  rs.getInt("id");
				 }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		finally {
			pst.close();
			connection.close();
		}
		return id;
	}
	public int getIDFeature(String name) throws SQLException {
		int id=0;
		con = new Connect();
		Connection connection = null;
		PreparedStatement pst= null;
		try {
			connection = con.getConnection();
			String sql="select * FROM features where features_name=?;";
				 pst = connection.prepareStatement(sql);
				 pst.setString(1, name);
				 ResultSet rs = pst.executeQuery();
				 if(rs.next()) {
					id=  rs.getInt("id");
				 }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		finally {
			pst.close();
			connection.close();
		}
		return id;
	}
	public List<String> LoadFeature(int Scenario_id) throws SQLException{
		List<String> list = new LinkedList<>();
		con = new Connect();
		Connection connection = null;
		PreparedStatement pst= null;
		try {
			connection = con.getConnection();
			String sql="select * FROM features where scenario_id= ?";
				 pst = connection.prepareStatement(sql);
				 pst.setInt(1, Scenario_id);
				 ResultSet rs = pst.executeQuery();
				 while(rs.next()) {
					 list.add(rs.getString("features_name"));
				 }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		finally {
			pst.close();
			connection.close();
		}
		return list;
		
	}
	public List<FieldBean> getListField(int scenario_id, int feature_id) throws SQLException{
		List<FieldBean> list = new LinkedList<>();
		con = new Connect();
		Connection connection = null;
		PreparedStatement pst= null;
		try {
			connection = con.getConnection();
			String sql="select * FROM  management_testcase where scenario_id= ? and feature_id = ?";
				 pst = connection.prepareStatement(sql);
				 pst.setInt(1, scenario_id);
				 pst.setInt(2, feature_id);
				 ResultSet rs = pst.executeQuery();
				 while(rs.next()) {
					 
					 JSONObject obj = new JSONObject(rs.getString("steps"));
					 Object object = obj.get("Steps");
					 JSONArray arr = new JSONArray(object.toString());
					 for(int i=0;i<arr.length();i++) {
						 FieldBean field = new FieldBean();
						 JSONObject objTemp= new JSONObject(arr.get(i).toString());
						 field.setAction(objTemp.getString("action")==null ? "": objTemp.getString("action"));
						 
						 field.setType(objTemp.getString("type")==null ? "" : objTemp.getString("type"));
						 field.setLocator(objTemp.getString("locator")==null ? "" : objTemp.getString("locator"));
						 field.setValue(objTemp.getString("value")==null ? "" : objTemp.getString("value"));
						 field.setOrder(objTemp.getString("order")==null ? "" : objTemp.getString("order"));
						 field.setPage(objTemp.getString("page")==null ? "" : objTemp.getString("page"));
						 field.setLocator_page(objTemp.getString("locator_page")==null ? "" : objTemp.getString("locator_page"));
						 field.setAction_page(objTemp.getString("action_page")==null ? "" : objTemp.getString("action_page"));
						 field.setRun(objTemp.getBoolean("run"));
						 if(objTemp.has("description")) {
							 field.setDescription(objTemp.getString("description")==null ? "" : objTemp.getString("description"));
						 }
						 
						 list.add(field);
					 }
					 
					 
				 }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		finally {
			pst.close();
			connection.close();
		}
		return list;
		
		
	}
	public String getName(int id,String sql) throws SQLException {
		con = new Connect();
		String name="";
		Connection connection = null;
		PreparedStatement pst= null;
		
		try {
			 connection = con.getConnection();
			pst = connection.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				name =rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			pst.close();
			connection.close();
		}
		return name;
	}
	
	
	

}
