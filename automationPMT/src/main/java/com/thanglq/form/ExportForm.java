package com.thanglq.form;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.json.JSONArray;
import org.json.JSONObject;

import com.thanglq.bean.FieldBean;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.border.LineBorder;

public class ExportForm extends JFrame {

	private JPanel contentPane;
	private JTextField txtFrom;
	private JTextField txtTo;
	private JTextField txtPath;
	private List<FieldBean> listTable;
	private String absolutePath;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExportForm frame = new ExportForm();
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
	public ExportForm() {
		initGUI();
	}
	
	public ExportForm(List<FieldBean> listTable) {
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
		panel_3.setBounds(0, 56, 665, 232);
		panel.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Steps");
		lblNewLabel.setBounds(10, 28, 57, 28);
		panel_3.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("From");
		lblNewLabel_1.setBounds(102, 35, 46, 14);
		panel_3.add(lblNewLabel_1);
		
		txtFrom = new JTextField();
		txtFrom.setText("");
		txtFrom.setBounds(186, 32, 116, 20);
		panel_3.add(txtFrom);
		txtFrom.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("To");
		lblNewLabel_2.setBounds(359, 35, 46, 14);
		panel_3.add(lblNewLabel_2);
		
		txtTo = new JTextField();
		txtTo.setBounds(394, 32, 134, 20);
		panel_3.add(txtTo);
		txtTo.setColumns(10);
		
		txtPath = new JTextField();
		txtPath.setBounds(10, 110, 398, 20);
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
		btnFilePath.setBounds(458, 109, 150, 23);
		panel_3.add(btnFilePath);
		
		JButton btnConvert = new JButton("Export");
		btnConvert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				exportFeature(txtPath.getText(), Integer.parseInt(txtFrom.getText()), Integer.parseInt(txtTo.getText()));
			}
		});
		btnConvert.setBounds(260, 204, 89, 23);
		panel_3.add(btnConvert);
		
		JLabel lblNewLabel_3 = new JLabel("Export Form");
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblNewLabel_3.setBounds(276, 11, 143, 34);
		panel.add(lblNewLabel_3);
	
		
	}
	
	public void exportFeature(String filePath, int step_from,int step_to) {

		if(step_to>listTable.size()) {
			JOptionPane.showConfirmDialog(null, "Step to input greater than row Table", "Error",
					JOptionPane.PLAIN_MESSAGE);
			return;
		}
		JSONObject obj = new JSONObject();
		int start = step_from-1;
		int last = step_to;
		JSONArray stepArray = new JSONArray();
		for(int i= start;i<last;i++) {
			JSONObject objStep = new JSONObject();
			objStep.put("order", listTable.get(i).getOrder());
			objStep.put("action", listTable.get(i).getAction());
			objStep.put("locator", listTable.get(i).getLocator());
			objStep.put("type", listTable.get(i).getType());
			objStep.put("value", listTable.get(i).getValue());
			objStep.put("page", listTable.get(i).getPage());
			objStep.put("locator_page", listTable.get(i).getLocator_page());
			objStep.put("action_page", listTable.get(i).getAction_page());
			objStep.put("run", listTable.get(i).getRun());
			objStep.put("description", listTable.get(i).getDescription());
			stepArray.put(i, objStep);
		}
		obj.put("Steps", stepArray);
		obj.put("browser", "chrome");
		try (FileWriter file = new FileWriter(filePath+".json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(obj.toString());
            file.flush();
            JOptionPane.showConfirmDialog(null, "Export feature file sucessful", "Sucessful",
					JOptionPane.PLAIN_MESSAGE);
 
        } catch (IOException e) {
        	 JOptionPane.showConfirmDialog(null, e.toString(), "Error",
 					JOptionPane.PLAIN_MESSAGE);
            e.printStackTrace();
        }
		
		
	}
}
