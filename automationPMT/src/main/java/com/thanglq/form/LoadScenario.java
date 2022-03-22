package com.thanglq.form;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.thanglq.bean.FieldBean;
import com.thanglq.dao.LoadData;

import javax.swing.JLabel;
import java.awt.Font;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;

public class LoadScenario extends JFrame {

	private JPanel contentPane;
	private JComboBox cbbScenario;
	private JComboBox cbbFeature;
	private LoadData load;
	private JButton btnLoad;
	private int scenario_id;
	private int feature_id;
	private FormMain form;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoadScenario frame = new LoadScenario();
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
	public LoadScenario() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(204, 204, 204));
		panel.setBounds(0, 0, 434, 261);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(204, 204, 204));
		panel_1.setBounds(10, 0, 424, 55);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Choice Scenario");
		lblNewLabel.setForeground(new Color(173, 255, 47));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(121, 11, 159, 33);
		panel_1.add(lblNewLabel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(204, 204, 204));
		panel_2.setBounds(52, 66, 351, 184);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Scenario:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(10, 26, 82, 31);
		panel_2.add(lblNewLabel_1);
		
		 cbbScenario = new JComboBox();
		 cbbScenario.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		
		 		if(e.getSource()==cbbScenario) {
		 			try {
						LoadFeature(cbbScenario.getSelectedItem().toString());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		 		}
		 		
		 	}
		 });
		cbbScenario.setBounds(118, 32, 137, 22);
		panel_2.add(cbbScenario);
		
		JLabel lblNewLabel_1_1 = new JLabel("Feature:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(10, 93, 82, 31);
		panel_2.add(lblNewLabel_1_1);
		
		 cbbFeature = new JComboBox();
		cbbFeature.setBounds(118, 99, 137, 22);
		panel_2.add(cbbFeature);
		
		 btnLoad = new JButton("Get Scenario");
		 btnLoad.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\icon\\load.png"));
		 btnLoad.setBackground(new Color(173, 216, 230));
		 btnLoad.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		
		 		try {
					 feature_id = load.getIDFeature(cbbFeature.getSelectedItem().toString());
					List<FieldBean> listField = load.getListField(scenario_id, feature_id);
					 form = new FormMain(listField, true, scenario_id, feature_id);
					
					form.setVisible(true);
					dispose();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		 		
		 	}
		 });
		btnLoad.setBounds(25, 150, 137, 23);
		panel_2.add(btnLoad);
		
		JButton btnNewScenario = new JButton("New Scenario");
		btnNewScenario.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\icon\\new.png"));
		btnNewScenario.setHorizontalAlignment(SwingConstants.RIGHT);
		btnNewScenario.setBackground(new Color(175, 238, 238));
		btnNewScenario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				form = new FormMain();
				form.setVisible(true);
			}
		});
		btnNewScenario.setBounds(192, 150, 127, 23);
		panel_2.add(btnNewScenario);
		try {
			Load();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void Load() throws SQLException {
		load = new LoadData();
		List<String> loadScenario = load.LoadScenario();
		for(String name : loadScenario) {
			cbbScenario.addItem(name);
		}
		
		
	}
	public void LoadFeature(String nameScenario) throws SQLException {
		cbbFeature.removeAllItems();
		 scenario_id = load.getIDScenario(nameScenario);
		System.out.println(scenario_id);
		List<String> loadFeature = load.LoadFeature(scenario_id);
		for(int i=0;i<loadFeature.size();i++) {
			cbbFeature.addItem(loadFeature.get(i));
//			System.out.println("lqthang  ="+ loadFeature.get(i));
		}
		
	}
}
