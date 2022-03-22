package com.thanglq.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONObject;

import com.thanglq.bean.FieldBean;
import com.thanglq.connection.Connect;
import com.thanglq.form.FormMain;

public class FieldDao {
	private static  Connect conect;
	public  static int id_feature=0;
	public  static int id_scenario=0;
	String sql_scenario="select * from management_scenario where name = ?";
	String sql_features="select * from features where features_name = ?";
	
	public boolean checkScenarioExist(Connection con, String name) {
		PreparedStatement pst= null;
		
		String sql="select * from management_scenario where name = ?";
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
	public boolean checkFeatureExist(Connection con, String name) {
		PreparedStatement pst= null;
		
		String sql="select * from features where features_name = ?";
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
	public int getID(Connection con, String name, String sql) {
		PreparedStatement pst= null;
		
		int id=0;
		try {
			 pst = con.prepareStatement(sql);
			 pst.setString(1, name);
			 ResultSet rs = pst.executeQuery();
			 if(rs.next()) {
				  id = rs.getInt("id");
			 }
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return id;
		
		
	}
//	List<FieldBean> listBean,
	public void InsertData(String scenario, String feature, List<FieldBean> list) throws SQLException {
		conect = new Connect();
		Connection con = conect.getConnection();
		
	
		  if(!checkScenarioExist(con, scenario)) {
			  try {
				if(insertScenario(con, scenario)>0) {
					if(insertFeature(con, scenario,feature )>0) {
						
						boolean result = InsertStep(con, list, feature);
						if(result) {
							FormMain.feature_id = id_feature;
							FormMain.scenario_id = id_scenario;
							JOptionPane.showMessageDialog(null, "Insert Sucessfull", "Insert", JOptionPane.PLAIN_MESSAGE);
						}
					}
				}
			} catch (SQLException e) {
				con.rollback();
				e.printStackTrace();
			}
			  
			  
		  }
		  else {
			 if(checkFeatureExist(con, feature)) {
				 JOptionPane.showConfirmDialog(null, "Insert Scenario","Feature's Name is Exist", JOptionPane.PLAIN_MESSAGE);
			 }
			 else {
				 if(insertFeature(con, scenario,feature )>0) {
						
						boolean result = InsertStep(con, list, feature);
						if(result) {
							FormMain.feature_id = id_feature;
							FormMain.scenario_id = id_scenario;
							JOptionPane.showMessageDialog(null, "Insert Sucessfull", "Insert", JOptionPane.PLAIN_MESSAGE);
						}
					}
			 }
		  }
		
		
	
	}
	
	public boolean InsertStep(Connection con, List<FieldBean> list, String feature) throws SQLException {
		
		 id_feature =  getID(con, feature, sql_features);
		String sql ="INSERT INTO management_testcase ( steps,scenario_id, feature_id) VALUES (?, ?,?);";
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
				objStep.put("description", list.get(i).getDescription());
				stepArray.put(i, objStep);
			}
			obj.put("Steps", stepArray);
			obj.put("browser", "chrome");
			obj.put("id_scenario", id_scenario);
			obj.put("id_feature", id_feature);
			pst.setString(1, obj.toString());
			pst.setInt(2, id_scenario);
			pst.setInt(3, id_feature);
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
	public boolean UpdateStep(List<FieldBean> list, int feature_id, int scenario_id) throws SQLException {
		conect = new Connect();
		 Connection connection = conect.getConnection();
		 PreparedStatement pst = null;
			String sql ="INSERT INTO management_testcase ( steps,scenario_id, feature_id) VALUES (?, ?,?);";
		if(deleteStep(connection, feature_id, scenario_id)) {
			try {
				pst = connection.prepareStatement(sql);
				
				
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
					objStep.put("description", list.get(i).getDescription());
					stepArray.put(i, objStep);
				}
				obj.put("Steps", stepArray);
				obj.put("browser", "chrome");
				obj.put("id_scenario", scenario_id);
				obj.put("id_feature", feature_id);
				pst.setString(1, obj.toString());
				pst.setInt(2, scenario_id);
				pst.setInt(3, feature_id);
				 int executeUpdate = pst.executeUpdate();
//				 int executeUpdate = pst.execute
				 if(executeUpdate>0) {
					 return true;
				 }

			} catch (SQLException e) {
				connection.rollback();
				e.printStackTrace();
			}
			finally {
				pst.close();
				connection.close();
			}
			return false;
		}
		
		return false;
	}
	public boolean deleteStep(Connection con , int feature_id, int scenario_id) throws SQLException {
		 String sql_delete ="DELETE FROM management_testcase where scenario_id = ? and feature_id = ?;";
		 PreparedStatement pst = null;
		 try {
			  pst = con.prepareStatement(sql_delete);
			  pst.setInt(1, scenario_id);
			  pst.setInt(2, feature_id);
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
	
	public int insertScenario(Connection con, String scenario) throws SQLException {
		 int result=0;
		PreparedStatement pst= null;
		String sql= "INSERT INTO management_scenario (name) values (?);";
		
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, scenario);
			 result = pst.executeUpdate();
//			System.out.println(executeUpdate +" Row Inserted");
			
//			view(con);
//			return execute;
		} catch (SQLException e) {
			pst.close();
			con.close();
			e.printStackTrace();
		}
		finally {
//			pst.close();
//			con.close();
		}
		return result;
		
		
	}
	public int insertFeature(Connection con, String scenario, String feature) throws SQLException {
		 int result=0;
		PreparedStatement pst= null;
		id_scenario = getID(con, scenario, sql_scenario);
		String sql= "INSERT INTO features (features_name, scenario_id) VALUES (?, ?);";
		
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, feature);
			pst.setInt(2, id_scenario);
			 result = pst.executeUpdate();
		} catch (SQLException e) {
			pst.close();
			con.close();
			e.printStackTrace();
		}
		finally {
//			pst.close();
//			con.close();
		}
		return result;
		
		
	}
	
	public void view(Connection con) {

PreparedStatement pst= null;
		
		String sql="select * from management_scenario";
		try {
			 pst = con.prepareStatement(sql);
			
			 ResultSet rs = pst.executeQuery();
			 while(rs.next()) {
				 
				 System.out.println(rs.getString("name")+"   ====");
				 System.out.println(rs.getString("id")+"   ====");
//				 return true;
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		retu
	}
	
	public static void main(String[] args) {
		FieldDao dao = new FieldDao();
//		dao.InsertData("login");
//		try {
////			dao.InsertData("thang test inset","feature");
////			conect = new Connect();
////			Connection con = conect.getConnection();
////			dao.view(con);
//			
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
