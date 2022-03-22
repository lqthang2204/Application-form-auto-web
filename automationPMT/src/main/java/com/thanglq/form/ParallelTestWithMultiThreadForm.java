package com.thanglq.form;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.Iterator;

import javax.swing.JList;
import javax.swing.JTextArea;

public class ParallelTestWithMultiThreadForm extends JFrame {

	private JPanel contentPane;
	private JList listFeature;
	 String week[]= { "Monday","Tuesday","Wednesday",
             "Thursday","Friday","Saturday","Sunday"};
	 DefaultListModel listModel1 = new DefaultListModel();

	 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ParallelTestWithMultiThreadForm frame = new ParallelTestWithMultiThreadForm();
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
	public ParallelTestWithMultiThreadForm() {
		initGUI();
		for(int i=0;i<week.length;i++) {
			listModel1.addElement(week[i]);

			
		}
//		listFeature.add(listModel1.toString());
		
	}
	 public void initGUI() {
		 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 962, 599);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JPanel panel = new JPanel();
			panel.setBounds(0, 0, 946, 560);
			contentPane.add(panel);
			panel.setLayout(null);
			
			JPanel panel_1 = new JPanel();
			panel_1.setBounds(0, 0, 442, 560);
			panel.add(panel_1);
			panel_1.setLayout(null);
			
			JComboBox comboBox = new JComboBox();
			comboBox.setBounds(192, 11, 218, 43);
			panel_1.add(comboBox);
			
			JLabel lblNewLabel = new JLabel("Feature name");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblNewLabel.setBounds(10, 11, 135, 43);
			panel_1.add(lblNewLabel);
			
			listFeature = new JList();
			listFeature.setBounds(442, 557, -426, -458);
			panel_1.add(listFeature);
			
			JPanel panel_2 = new JPanel();
			panel_2.setBounds(534, 0, 412, 560);
			panel.add(panel_2);
		
		
	}
	
}
