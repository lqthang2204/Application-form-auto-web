package com.thanglq.form;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTabbedPane;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.border.LineBorder;


import com.thanglq.bean.SettingConfig;

import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class FormSetting extends JFrame {

	private JPanel contentPane;
	private JTextField txtStepstep;
	private JTextField txtIP;
	private JTextField txtPort;
	private JTextField txtTime;
	private JTextField txtFilePath;
	private JRadioButton chkReport;
	private JLabel lblPath;
	private JButton btnGetPath;
	private JButton btnSave;
	private String step_loop;
	private JTextField txtCount;
	private SettingConfig setting_config;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormSetting frame = new FormSetting();
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
	public void initGUI() {

		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 733, 575);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 707, 536);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(10, 28, 671, 284);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Loop Step:");
		lblNewLabel.setBounds(10, 11, 74, 31);
		panel_1.add(lblNewLabel);
		
		txtStepstep = new JTextField();
		txtStepstep.setText("1;2");
		txtStepstep.setToolTipText("");
		txtStepstep.setBounds(85, 16, 284, 20);
		panel_1.add(txtStepstep);
		txtStepstep.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Scheduler Reset (minutes):");
		lblNewLabel_4.setBounds(10, 53, 177, 20);
		panel_1.add(lblNewLabel_4);
		
		txtTime = new JTextField();
		txtTime.setBounds(197, 53, 227, 20);
		panel_1.add(txtTime);
		txtTime.setColumns(10);
		
		 chkReport = new JRadioButton("Report");
		chkReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chkReport.isSelected()) {
					lblPath.setVisible(true);
					btnGetPath.setVisible(true);
					txtFilePath.setVisible(true);
				}
				else {
					lblPath.setVisible(false);
					btnGetPath.setVisible(false);
					txtFilePath.setVisible(false);
				}
			}
		});
		chkReport.setBounds(6, 103, 74, 23);
		panel_1.add(chkReport);
		
		lblPath = new JLabel("File Path");
		lblPath.setBounds(96, 106, 71, 17);
		panel_1.add(lblPath);
		
		txtFilePath = new JTextField();
		txtFilePath.setBounds(177, 104, 284, 20);
		panel_1.add(txtFilePath);
		txtFilePath.setColumns(10);
		txtFilePath.setText(System.getProperty("user.dir")+"/Report");
		btnGetPath = new JButton("Get Path");
		btnGetPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();

				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int result = fileChooser.showOpenDialog(panel_1);
				if (result == JFileChooser.APPROVE_OPTION) {
					String absolutePath = fileChooser.getSelectedFile().getAbsolutePath();
					txtFilePath.setText(absolutePath);
				}
			}
		});
		btnGetPath.setBounds(471, 103, 87, 23);
		panel_1.add(btnGetPath);
		
		JLabel lblNewLabel_5 = new JLabel("Screen Resolution");
		lblNewLabel_5.setBounds(10, 156, 128, 18);
		panel_1.add(lblNewLabel_5);
		
		JComboBox cbbSolution = new JComboBox();
		cbbSolution.setBounds(148, 152, 144, 22);
		panel_1.add(cbbSolution);
		cbbSolution.addItem("");
		cbbSolution.addItem("1024x768");
		cbbSolution.addItem("1280x800");
		cbbSolution.addItem("1280x1024");
		cbbSolution.addItem("1366x768");
		cbbSolution.addItem("1440x900");
		cbbSolution.addItem("1680x1050");
		cbbSolution.addItem("1600x1200");
		cbbSolution.addItem("1920x1200");
		cbbSolution.addItem("1920x1080");
		cbbSolution.addItem("2048x1536");
		
		
		JLabel lblNewLabel_7 = new JLabel("Count");
		lblNewLabel_7.setBounds(397, 19, 46, 14);
		panel_1.add(lblNewLabel_7);
		
		txtCount = new JTextField();
		txtCount.setBounds(461, 16, 86, 20);
		panel_1.add(txtCount);
		txtCount.setColumns(10);
		
		JRadioButton rdRecord = new JRadioButton("Record Video");
		rdRecord.setBounds(6, 208, 109, 23);
		panel_1.add(rdRecord);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Use Cross Browser");
		rdbtnNewRadioButton.setBounds(10, 234, 177, 23);
		panel_1.add(rdbtnNewRadioButton);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.setBounds(10, 323, 671, 153);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblRemoteComputer = new JLabel("Remote Computer");
		lblRemoteComputer.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblRemoteComputer.setBounds(225, 0, 146, 31);
		panel_2.add(lblRemoteComputer);
		
		JLabel lblNewLabel_1 = new JLabel("IP Address:");
		lblNewLabel_1.setBounds(10, 42, 91, 21);
		panel_2.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Port:");
		lblNewLabel_2.setBounds(10, 74, 46, 14);
		panel_2.add(lblNewLabel_2);
		
		txtIP = new JTextField();
		txtIP.setBounds(88, 42, 160, 20);
		panel_2.add(txtIP);
		txtIP.setColumns(10);
		
		txtPort = new JTextField();
		txtPort.setBounds(87, 74, 161, 20);
		panel_2.add(txtPort);
		txtPort.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Browser");
		lblNewLabel_3.setBounds(325, 45, 46, 14);
		panel_2.add(lblNewLabel_3);
		
		JComboBox cbbBrowser = new JComboBox();
		cbbBrowser.setBounds(417, 41, 96, 22);
		panel_2.add(cbbBrowser);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(10, 476, 687, 55);
		panel.add(panel_3);
		panel_3.setLayout(null);
		
		 btnSave = new JButton("Save Setting");
		 btnSave.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\icon\\setting.png"));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 step_loop = getLoop();
				 if(!step_loop.equals("") && !txtCount.getText().trim().equals("")) {
					 SettingConfig.COUNT_LOOP= (Integer.parseInt(txtCount.getText().trim()));
					 SettingConfig.LOOP = step_loop;
					
				 }else if (step_loop.equals("") && txtCount.getText().trim().equals("")){
					 JOptionPane.showConfirmDialog(null, "Error at field Count and Loop","Setting", JOptionPane.CLOSED_OPTION);
					 return;
				 }
				 if(chkReport.isSelected() && !txtFilePath.getText().trim().equals("")) {
					 SettingConfig.IS_REPORT= true;
					 SettingConfig.PATH= txtFilePath.getText().trim();
				 }
				 
				 if(rdRecord.isSelected()) {
					 SettingConfig.IS_RECORD = true;
				 }
				 SettingConfig.RESOLUTION = cbbSolution.getSelectedItem().toString();
				 JOptionPane.showConfirmDialog(null, "Save successful","Setting", JOptionPane.CLOSED_OPTION);
				 dispose();
				
			}
		});
		btnSave.setBounds(442, 11, 116, 33);
		panel_3.add(btnSave);
		
		JLabel lblNewLabel_6 = new JLabel("Setting");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_6.setBounds(231, 0, 186, 17);
		panel.add(lblNewLabel_6);
		lblPath.setVisible(false);
		btnGetPath.setVisible(false);
		txtFilePath.setVisible(false);
	
	}
	public FormSetting() {
		initGUI();
		setting_config = new SettingConfig();
	}
	public String getLoop() {
	
			return txtStepstep.getText().trim();
		
		
	}
}
