package com.thanglq.form;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.thanglq.bean.FieldBean;
import com.thanglq.dao.LoadActions;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class ActionForm extends JFrame {

	private JPanel contentPane;
	private JTextField txtDescription;
	private List<FieldBean> listField;
	private LoadActions action;
	private JComboBox cbbName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ActionForm frame = new ActionForm();
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

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 488, 312);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(51, 153, 204));
		panel.setBounds(0, 0, 469, 273);
		contentPane.add(panel);
		panel.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(51, 153, 204));
		panel_1.setBounds(0, 21, 469, 63);
		panel.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel = new JLabel("Form Action");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(200, 11, 123, 41);
		panel_1.add(lblNewLabel);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(153, 204, 0));
		panel_2.setBounds(0, 88, 469, 185);
		panel.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Name:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(62, 34, 62, 31);
		panel_2.add(lblNewLabel_1);

		cbbName = new JComboBox();
		cbbName.setBounds(182, 40, 152, 22);
		panel_2.add(cbbName);
		cbbName.setEditable(true);

		JButton btnSave = new JButton("Save Actions");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (e.getSource() == btnSave) {
					if(!cbbName.getSelectedItem().toString().equals("") && !txtDescription.getText().trim().toString().equals("")) {
						try {
							action.InsertActions(cbbName.getSelectedItem().toString(), txtDescription.getText().trim().toString(), listField);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						dispose();
						
						
					}else {
						JOptionPane.showMessageDialog(null, "Please! input name  and description", "Insert", JOptionPane.ERROR_MESSAGE);
						return;
					}
					

				}

			}
		});
		
		btnSave.setFont(UIManager.getFont("Button.font"));
		btnSave.setForeground(Color.BLACK);
		btnSave.setBackground(Color.WHITE);
		btnSave.setBounds(157, 151, 117, 23);
		panel_2.add(btnSave);

		JLabel lblNewLabel_2 = new JLabel("Description:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(45, 92, 108, 22);
		panel_2.add(lblNewLabel_2);

		txtDescription = new JTextField();
		txtDescription.setBounds(182, 96, 241, 20);
		panel_2.add(txtDescription);
		txtDescription.setColumns(10);

	}

	public ActionForm() {
		initGUI();
	}

	public ActionForm(List<FieldBean> listField) {
		this.listField = listField;
		initGUI();
		action = new LoadActions();
		LoadData();
	}
	public void LoadData() {
		
		try {
			List<String> list = action.getActionForm();
			for(int i=0;i<list.size();i++) {
				cbbName.addItem(list.get(i));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
