package com.thanglq.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONObject;

import com.thanglq.bean.FieldBean;
import com.thanglq.connection.Connect;
import com.thanglq.form.FormMain;

public class LoadActions {
	
	Connect con;
	public List<String> getActionForm() throws SQLException {
	
		List<String> list = new LinkedList<>();
		con = new Connect();
		Connection connection = null;
		PreparedStatement pst= null;
		try {
			connection = con.getConnection();
			String sql="select * FROM actions;";
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
	public  List<FieldBean> getAction(String nameAction) throws SQLException {
		List<FieldBean> list = new LinkedList<>();
		con = new Connect();
		Connection connection = null;
		PreparedStatement pst= null;
		try {
			connection = con.getConnection();
			String sql="select * FROM  actions where name= ?";
				 pst = connection.prepareStatement(sql);
				 pst.setString(1, nameAction);
				
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
						 field.setRun(objTemp.getBoolean("run"));
						 field.setCondition(objTemp.getString("Condition")==null ? "" : objTemp.getString("Condition"));
						 field.setTimeOut(objTemp.getString("Time_Out")==null ? "" : objTemp.getString("Time_Out"));
					
						 
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
	public void InsertActions(String name, String description, List<FieldBean> listField) throws SQLException  {

		con = new Connect();
		Connection connection = null;
		connection = con.getConnection();
		try {
			  if(!checkActionNameExist(connection, name)) {
				  try {
							boolean result = InsertStepAction(connection, listField, name, description);
							if(result) {
								JOptionPane.showMessageDialog(null, "Insert Sucessfull", "Insert", JOptionPane.PLAIN_MESSAGE);
							}
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				  
				  
			  }
			  else {
				if(deleteActions(connection, name)) {
					boolean result = InsertStepAction(connection, listField, name, description);
					if(result) {
						JOptionPane.showMessageDialog(null, "updated Sucessfull", "Insert", JOptionPane.PLAIN_MESSAGE);
					}
				}
			  }
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			connection.close();
		}

		
		  
		
		
	
	
	}
	public boolean checkActionNameExist(Connection con, String name) {
		PreparedStatement pst= null;
		
		String sql="select * from actions where name = ?";
		try {
			 pst = con.prepareStatement(sql);
			 pst.setString(1, name);
			 ResultSet rs = pst.executeQuery();
			 if(rs.next()) {
				 return true;
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
		
	}
	public boolean InsertStepAction(Connection con, List<FieldBean> list, String name, String description) throws SQLException {
		
		
		String sql ="INSERT INTO actions ( Steps, description,name) VALUES (?, ?,?);";
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement(sql);
			
			
			JSONObject obj = new JSONObject();
			
			JSONArray stepArray = new JSONArray();
			for(int i=0;i<list.size();i++) {
				JSONObject objStep = new JSONObject();
				objStep.put("order", list.get(i).getOrder());
				objStep.put("action", list.get(i).getAction());
				objStep.put("locator", list.get(i).getLocator());
				objStep.put("type", list.get(i).getType());
				objStep.put("value", list.get(i).getValue());
				objStep.put("page", list.get(i).getPage());
				objStep.put("locator_page", list.get(i).getLocator_page());
				objStep.put("action_page", list.get(i).getAction_page());
				objStep.put("run", list.get(i).getRun());
				objStep.put("Condition", list.get(i).getCondition());
				objStep.put("Time_Out", list.get(i).getTimeOut());
				stepArray.put(i, objStep);
			}
			obj.put("Steps", stepArray);
			pst.setString(1, obj.toString());
			pst.setString(2, description);
			pst.setString(3, name);
			 int executeUpdate = pst.executeUpdate();
//			 int executeUpdate = pst.execute
			 if(executeUpdate>0) {
				 return true;
			 }

		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		finally {
			pst.close();
			con.close();
		}
		return false;
	}
	public boolean deleteActions(Connection con , String name) throws SQLException {
		 String sql_delete ="DELETE FROM actions where name=?;";
		 PreparedStatement pst = null;
		 try {
			  pst = con.prepareStatement(sql_delete);
			  pst.setString(1, name);

			  int executeUpdate = pst.executeUpdate();
			  if(executeUpdate>0) {
				  return true;
			  }
			 
			
		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
		}
		 return false;
		 
	}
	public List<FieldBean> getListActions(String name) throws SQLException{
		List<FieldBean> list = new LinkedList<>();
		con = new Connect();
		Connection connection = null;
		PreparedStatement pst= null;
		try {
			connection = con.getConnection();
			String sql="select *   FROM actions where name=  ?";
				 pst = connection.prepareStatement(sql);
				 pst.setString(1, name);
				
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
//						 field.setAction_page(objTemp.getString("action_page")==null ? "" : objTemp.getString("action_page"));
						 field.setRun(objTemp.getBoolean("run"));
						 field.setCondition(objTemp.getString("Condition")==null ? "" : objTemp.getString("Condition"));
						 field.setTimeOut(objTemp.getString("Time_Out")==null ? "" : objTemp.getString("Time_Out"));
						 
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
	

}
