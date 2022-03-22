package com.thanglq.form;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.thanglq.bean.FieldBean;
import com.thanglq.dao.FieldDao;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class InputName extends JFrame {

	private JPanel contentPane;
	private JTextField txtScenario_name;
	private JTextField txtFeature_name;
	boolean flag= false;
	private JButton btnSave;
	private  List<FieldBean> listField;
	private FieldDao dao;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InputName frame = new InputName();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public InputName(List<FieldBean> listField) {
		this.listField =listField;
		initGUI();
	}

	public void initGUI() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 506, 318);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 480, 268);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Input Scenario");
		lblNewLabel.setForeground(Color.ORANGE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel.setBounds(208, 25, 188, 49);
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 74, 470, 183);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Scenario Name:");
		lblNewLabel_1.setBounds(10, 11, 106, 29);
		panel_1.add(lblNewLabel_1);
		
		txtScenario_name = new JTextField();
		txtScenario_name.setBounds(109, 15, 275, 25);
		panel_1.add(txtScenario_name);
		txtScenario_name.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Feature Name:");
		lblNewLabel_2.setBounds(10, 85, 85, 19);
		panel_1.add(lblNewLabel_2);
		
		txtFeature_name = new JTextField();
		txtFeature_name.setBounds(109, 84, 275, 29);
		panel_1.add(txtFeature_name);
		txtFeature_name.setColumns(10);
		dao = new FieldDao();
		 btnSave = new JButton("Save");
		 btnSave.setSelectedIcon(new ImageIcon(System.getProperty("user.dir")+"\\icon\\save.png"));
		 btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==btnSave) {					
					String[] arrayName = getArrayName();
						try {
							if(arrayName[0]!=null && arrayName[1]!=null) {
								
								dao.InsertData(arrayName[0], arrayName[1], listField);
								dispose();
								 
										
								
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

				}
			}
		});
		 btnSave.setBounds(150, 135, 174, 37);
		panel_1.add(btnSave);
	
	}

	/**
	 * Create the frame.
	 */
	public InputName() {
		initGUI();
	}
	
	public String[] getArrayName() {
		String[] arr = new String[2];
		if(!txtScenario_name.getText().trim().equals("") && !txtFeature_name.getText().trim().equals("") ) {

				arr[0] = txtScenario_name.getText().trim();
				arr[1] = txtFeature_name.getText().trim();
				
	
		}else {
			JOptionPane.showMessageDialog(null, "Please! input name Scenario and Feature", "Insert", JOptionPane.ERROR_MESSAGE);
			
		}
		return arr;
	}
}
