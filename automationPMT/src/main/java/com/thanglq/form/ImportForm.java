package com.thanglq.form;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.thanglq.bean.FieldBean;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.border.LineBorder;

public class ImportForm extends JFrame {

	private JPanel contentPane;
	private JTextField txtPath;
	public   List<FieldBean> listTable;
	private String absolutePath;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ImportForm frame = new ImportForm();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ImportForm() {
		initGUI();
	}
	
	public ImportForm(List<FieldBean> listTable) {
		this.listTable = listTable;
		initGUI();
	}
	public void initGUI() {
//		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 681, 327);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBackground(new Color(102, 153, 153));
		panel.setBounds(0, 0, 665, 288);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(102, 153, 153));
		panel_3.setBounds(0, 69, 665, 139);
		panel.add(panel_3);
		panel_3.setLayout(null);
		
		txtPath = new JTextField();
		txtPath.setBounds(10, 47, 398, 20);
		panel_3.add(txtPath);
		txtPath.setColumns(10);
		
		JButton btnFilePath = new JButton("Set File Path");
		btnFilePath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify a file to save");   
				
				int userSelection = fileChooser.showSaveDialog(panel_3);
				 
				if (userSelection == JFileChooser.APPROVE_OPTION) {
				     absolutePath = fileChooser.getSelectedFile().getAbsolutePath();
				     txtPath.setText(absolutePath);
				}

			}
		});
		btnFilePath.setBounds(446, 46, 150, 23);
		panel_3.add(btnFilePath);
		
		JButton btnConvert = new JButton("Import");
		btnConvert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
//				exportFeature(txtPath.getText(), Integer.parseInt(txtFrom.getText()), Integer.parseInt(txtTo.getText()));
			
			}
		});
		btnConvert.setBounds(260, 204, 89, 23);
		panel_3.add(btnConvert);
		
		JLabel lblNewLabel_3 = new JLabel("Import Form");
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblNewLabel_3.setBounds(276, 11, 143, 34);
		panel.add(lblNewLabel_3);
		
		JButton btnImport = new JButton("Import");
		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					 FormMain.LIST_IMPORT = getFeature(txtPath.getText());
					JOptionPane.showConfirmDialog(null, "Import Sucessful", "Sucessful",
							JOptionPane.PLAIN_MESSAGE);
//					ImportForm temp = new ImportForm();
//					temp.dispose();
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnImport.setBounds(235, 208, 113, 34);
		panel.add(btnImport);
	
		
	}
	
	public List<FieldBean> getFeature(String filePath) throws IOException, ParseException {

		List<FieldBean> listTab = new LinkedList<FieldBean>();
		 try {
			 JSONParser parser = new JSONParser();
			FileReader reader = new FileReader(filePath);
				Object obj = parser.parse(reader);
				JSONObject jsonObj = new JSONObject(obj.toString());
				Object object = jsonObj.get("Steps");
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
					 
					 listTab.add(field);
				 }
		
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		 return listTab;
	}
}
