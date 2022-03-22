package com.thanglq.form;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.aventstack.extentreports.gherkin.model.Given;
import com.thanglq.base.TestBase;
import com.thanglq.bean.ActionsElement;
import com.thanglq.bean.FieldBean;
import com.thanglq.bean.Locators;

import com.thanglq.dao.FieldDao;
import com.thanglq.dao.LoadActions;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.*;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import javax.swing.JTextPane;
import static io.restassured.RestAssured.*;



public class ApiForm extends JFrame {

	private JPanel contentPane;
	private JTextField txtEnv;
	private String column[] = { "Type", "Key", "value", "Description"};
	private Object[][] obj = {};
	private JTable tblData;
	private DefaultTableModel tblModel;
	private JComboBox cbbAction;
	private JComboBox cbbType;
	private JComboBox cbbValue;
	private int count = 1;
	private TestBase testBase;
	private FieldBean fieldBean;
	private List<FieldBean> listField;
	private JButton btnDelete;
	private FieldDao dao;
	private InputName input;
	private boolean isHaveScenario;
	public static int scenario_id;
	public static int feature_id;
	private int size;
	private FormSetting setting;
	private ActionForm formAction;
	private JComboBox cbbPages;
	private JComboBox cbbElements;
	private JComboBox cbbMethod;
	private Map<String, String> mapFile;
	private Map<String, Locators> mapElements;
	private Locators locator;
	private ActionsElement scenarioAction;
	private Map<String, List<ActionsElement>> mapActions;
	private LoadActions actionID;
	private JComboBox cbbCondition;
	private JTextPane textPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApiForm frame = new ApiForm();
					frame.setTitle("Form Action");
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
	public ApiForm(List<FieldBean> listField, boolean isHaveScenario, int scenario_id, int feature_id) {
//		super();
		this.listField = listField;
		this.isHaveScenario = isHaveScenario;
		this.scenario_id = scenario_id;
		this.feature_id = feature_id;
		initGUI();


	}

	public ApiForm(int scenario_id, int feature_id) {
//		super();

		this.scenario_id = scenario_id;
		this.feature_id = feature_id;
//		initGUI();

	}

//	public  void setScenarioID(int scenario_id) {
//		this.scenario_id = scenario_id;
//	}
//	public   void setFeatureID(int feature_id) {
//		this.feature_id = feature_id;
//	}
	public void initGUI() {
		
//		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 200, 1294, 687);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 102, 102));
		panel.setBounds(0, 0, 1268, 648);
		contentPane.add(panel);
		panel.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 102, 102));
		panel_1.setBounds(20, 32, 416, 51);
		panel.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblEnv = new JLabel("URL");
		lblEnv.setBounds(10, 11, 63, 33);
		panel_1.add(lblEnv);

		txtEnv = new JTextField();
		txtEnv.setText("https://pmt-pmi-qa.qak8s.vibrenthealth.com/");
		txtEnv.setBounds(45, 14, 331, 27);
		panel_1.add(txtEnv);
		txtEnv.setColumns(10);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(0, 102, 102));
		panel_2.setBounds(828, 32, 430, 55);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
//				System.out.println("runing .....................");
//				RestAssured.baseURI="https://pmt-dho.qak8s.vibrenthealth.com";
//				given().header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJRV0tNSzBCcUxIN1NuRFAzNi1CU1h0QTN5Qm9TZXBabWlxbE5PZmJvdFJ3In0.eyJleHAiOjE2NDQ4NzQ1MTQsImlhdCI6MTY0NDgzMTQxMywiYXV0aF90aW1lIjoxNjQ0ODMxMzE0LCJqdGkiOiIwMjRkZWJiYy0xMWQ1LTQzNTQtOWExZi0zN2JmMDI4NWI3MzgiLCJpc3MiOiJodHRwczovL2tleWNsb2FrLWRldi52aWJyZW50aGVhbHRoLmNvbS9hdXRoL3JlYWxtcy9kaG9fcmVhbG0iLCJhdWQiOlsicGFydGljaXBhbnQiLCJoZWxwRGVzayIsImFjY291bnQiXSwic3ViIjoiN2I5NzM3YzYtYjI4NS00NTEyLTlmMzYtOWZkZGY0MGQyZjI4IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiUE1UX1VJX0NMSUVOVCIsIm5vbmNlIjoiNDQ1MjZhZjktZWRlMC00ODczLTg2M2QtYWY2YWI3MWNjYTVhIiwic2Vzc2lvbl9zdGF0ZSI6ImU1YWEwM2M5LTk0OTEtNDliZi1hMWY3LWNlMmI5Njg3YjgxNyIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cHM6Ly9wbXQtZGhvLnFhazhzLnZpYnJlbnRoZWFsdGguY29tIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJST0xFX01DX1NZU1RFTV9BRE1JTklTVFJBVE9SIiwiUk9MRV9NQ19TVVBQT1JUIiwiUk9MRV9NQ19QUk9HUkFNX01BTkFHRVIiLCJST0xFX01DX0NBVElfSU5URVJWSUVXRVIiLCJST0xFX01DX1NVUFBPUlRfQURNSU4iLCJNQ1VTRVIiXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBlbWFpbCBwYXJ0aWNpcGFudCBoZWxwRGVzayBwcm9maWxlIiwibGFzdExvZ291dFRpbWUiOiIxNjQ0ODI4NjI5OTczIiwiZ3JvdXBtZW1iZXJzaGlwIjpbIi9BbGwgb2YgVXMvUk9MRV9NQ19DQVRJX0lOVEVSVklFV0VSIiwiL0FsbCBvZiBVcy9ST0xFX01DX1BST0dSQU1fTUFOQUdFUiIsIi9BbGwgb2YgVXMvUk9MRV9NQ19TVVBQT1JUX0FETUlOIiwiL0FsbCBvZiBVcy9ST0xFX01DX1NZU1RFTV9BRE1JTklTVFJBVE9SIl0sImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJuYW1lIjoibWNhZG1pbiBtY2FkbWluIiwiRGVmYXVsdFByb2dyYW0iOiJBbGwgb2YgVXMiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJtY2FkbWluQGV4YW1wbGUuY29tIiwiZ2l2ZW5fbmFtZSI6Im1jYWRtaW4iLCJmYW1pbHlfbmFtZSI6Im1jYWRtaW4iLCJlbWFpbCI6Im1jYWRtaW5AZXhhbXBsZS5jb20ifQ.DVjRVFMMQLXZ6r-i6aYss59oQ4JHnEkMt4QsKiLGq0NOwIrEN6ft29Kpjc6mxvoBHDyvzYrq12XCMau4O_swNP1RTm4S8Ttb0prDwkAr38YnOmAQbecSMfe6oMK8BcfdgL-Sv0BH4CLjkBXFy829kt6P_1bvRFafhZ0XJ86oJv17vjCt95-Ddden5P7o8l9ZKnBN9eYZYN9IJIGKg3vigNcMrt3EIwm8hKZQgzsCIuzzUiPpWPFWKrMii41ZDEIr-3aj_wF76S4zYHInrQa433OBBPafkkFwDLUwLb6GbO-h0FDuZ4KekYf95z6kMbpGesFdicSFIBlv1MtwOUOECQ");
//				given().header("Cookie","_gid=GA1.2.2010154963.1644805442; _ga_CNMKX1CCF4=GS1.1.b1b64ca7-1f4d-ed72-8439-6a881a49cba2.58.1.1644826436.58; _ga=GA1.2.601168108.1628497350; _gat_UA-129207854-1=1; _ga_KLCX5ZS6H5=GS1.1.1644830052.544.1.1644831411.58; JSESSIONID=ZWExYjI0YzUtY2IyNi00NGI5LTlkYTctMzc5ODE3OGIwOGI2");
//				Response res = given().when().get("api/userAdmin/users?roleName=ROLE_MC_SYSTEM_ADMINISTRATOR");
//				
//				res.prettyPrint();
				  RestAssured.port = 9002;
				Response res = RestAssured.get("https://reqres.in/api/users/2");
				System.out.println(res.getStatusCode());
				
			}
		});
		btnSend.setBounds(10, 11, 138, 33);
		panel_2.add(btnSend);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(20, 94, 1238, 324);
		panel.add(panel_3);
		panel_3.setLayout(null);
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(new Color(0, 102, 102));
		panel_5.setBounds(456, 32, 362, 55);
		panel.add(panel_5);
		panel_5.setLayout(null);
		
		JLabel lblMethod = new JLabel("Method");
		lblMethod.setBounds(10, 11, 105, 33);
		panel_5.add(lblMethod);
		tblModel = new DefaultTableModel(obj, column);

		tblData = new JTable(tblModel) {
			private static final long serialVersionUID = 1L;

			/*
			 * @Override public Class getColumnClass(int column) { return getValueAt(0,
			 * column).getClass(); }
			 */
			@Override
			public Class getColumnClass(int column) {
				switch (column) {
				case 0:
					return String.class;
				case 1:
					return String.class;
				case 2:
					return String.class;
				case 3:
					return String.class;
								

				default:
					return String.class;
				}
			}
		};
		tblData.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnDelete.setVisible(true);
//			BTNDE
			}
		});
//				tblData.setRowSorter(new TableRowSorter<TableModel>(tblmodel));
		TableColumn columnAction = tblData.getColumnModel().getColumn(0);
//
//		TableColumn columnType = tblData.getColumnModel().getColumn(3);
//		TableColumn columnvalue = tblData.getColumnModel().getColumn(4);
//		TableColumn columnPage = tblData.getColumnModel().getColumn(5);
//		TableColumn columnElement = tblData.getColumnModel().getColumn(6);
//		TableColumn columnCondition = tblData.getColumnModel().getColumn(8);

//		cbbRule = new JComboBox();
//		cbbData = new JComboBox();
		cbbAction = new JComboBox();
		cbbAction.addItem("Params");
		cbbAction.addItem("Authorization");
		cbbAction.addItem("Headers");
		cbbAction.addItem("Body");
		
		cbbType = new JComboBox();
		cbbValue = new JComboBox();
		cbbPages = new JComboBox();
		cbbElements = new JComboBox();
		cbbCondition = new JComboBox();

		
		
		cbbMethod = new JComboBox();
		cbbMethod.addItem("Get");
		cbbMethod.addItem("Put");
		cbbMethod.addItem("Post");
		cbbMethod.addItem("Delete");
		cbbMethod.setEditable(true);
		columnAction.setCellEditor(new DefaultCellEditor(cbbAction));
		cbbMethod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
	
		cbbMethod.setBounds(99, 16, 224, 28);
		panel_5.add(cbbMethod);
//		int row = tblData.getSelectedRow();
//		loadDataElements(cbbPages.getSelectedItem().toString(), row, 6);

//		columnAction.setCellEditor(new DefaultCellEditor(cbbAction));
//		columnType.setCellEditor(new DefaultCellEditor(cbbType));
//		columnvalue.setCellEditor(new DefaultCellEditor(cbbValue));
//		columnPage.setCellEditor(new DefaultCellEditor(cbbPages));
//		columnElement.setCellEditor(new DefaultCellEditor(cbbElements));
//		columnCondition.setCellEditor(new DefaultCellEditor(cbbCondition));

		cbbPages.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {

					int row = tblData.getSelectedRow();
				
					

				}

			}
		});
		cbbElements.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					cbbType.removeAllItems();
					cbbValue.removeAllItems();
					int row = tblData.getSelectedRow();
					String id = cbbElements.getSelectedItem().toString();
					Locators locators = mapElements.get(id);
				
					cbbType.addItem(locators.getType());
					cbbValue.addItem(locators.getValue());
					tblModel.setValueAt(locators.getType(), row, 3);
					tblModel.setValueAt(locators.getValue(), row, 2);
				}

			}

		});

		cbbType.setEditable(true);
		cbbValue.setEditable(true);
//		cbbActionID.setEditable(true);
		tblData.setModel(tblModel);
		tblData.setBackground(Color.WHITE);
		tblData.setForeground(Color.BLACK);
		tblData.setSelectionBackground(Color.GRAY);
		tblData.setGridColor(Color.CYAN);
		tblData.setSelectionForeground(Color.white);
		tblData.setFont(new Font("TAHOMA", Font.PLAIN, 17));
		tblData.setRowHeight(30);
		tblData.setAutoCreateRowSorter(false);

		JScrollPane scrollPanel = new JScrollPane(tblData);

		scrollPanel.setForeground(Color.red);
		scrollPanel.setBackground(Color.white);
		scrollPanel.setBounds(10, 11, 1218, 313);

		panel_3.add(scrollPanel);

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(new Color(153, 255, 255)));
		panel_4.setBackground(new Color(0, 102, 102));
		panel_4.setBounds(10, 573, 1248, 64);
		panel.add(panel_4);
		panel_4.setLayout(null);

		JButton btnAddRow = new JButton("Add Row");
		btnAddRow.setIcon(new ImageIcon((System.getProperty("user.dir") + "\\icon\\add.png")));
		btnAddRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				if(isHaveScenario) {
//					size = listField.size()+count;
//					
//					tblModel.addRow(new Object[] {
//							"Step "+ size, "", "","","" });
//					size++;
//				}
//				
//				else {
//					tblModel.addRow(new Object[] {
//							"Step "+ (count+1), "", "","","" });
//				}
//				count++;
				tblModel.addRow(new Object[] { "", "", "", "" });
				
				btnDelete.setVisible(true);

			}
		});
		btnAddRow.setBounds(490, 11, 110, 42);
		panel_4.add(btnAddRow);

		btnDelete = new JButton("Delete Row");
		btnDelete.setIcon(new ImageIcon((System.getProperty("user.dir") + "\\icon\\delete.png")));
		btnDelete.setVisible(false);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (tblData.getSelectedRow() != -1) {
					tblModel.removeRow(tblData.getSelectedRow());
					int rowCount = tblModel.getRowCount();
					for (int i = 0; i < rowCount; i++) {
						tblModel.setValueAt("Step " + (i + 1), i, 0);
					}
				}
				if (tblModel.getRowCount() == 0) {
					btnDelete.setVisible(false);
				}
			}
		});
		btnDelete.setBounds(315, 11, 123, 42);
		panel_4.add(btnDelete);

		JButton btnSave = new JButton("Save Request");
		btnSave.setIcon(new ImageIcon((System.getProperty("user.dir") + "\\icon\\save.png")));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int rowCount = tblModel.getRowCount();
				if (rowCount > 0) {
					listField = new LinkedList<>();

					for (int i = 0; i < rowCount; i++) {
						fieldBean = new FieldBean();

						fieldBean = new FieldBean();
						fieldBean.setOrder(tblModel.getValueAt(i, 0).toString());
						fieldBean.setAction(tblModel.getValueAt(i, 1).toString());
						fieldBean.setLocator(tblModel.getValueAt(i, 2).toString());
						fieldBean.setType(tblModel.getValueAt(i, 3).toString());
						fieldBean.setValue(tblModel.getValueAt(i, 4) == null ? "" : tblModel.getValueAt(i, 4).toString());
						fieldBean.setPage(tblModel.getValueAt(i, 5) == null ? "" : tblModel.getValueAt(i, 5).toString());
						fieldBean.setLocator_page(
								tblModel.getValueAt(i, 6) == null ? "" : tblModel.getValueAt(i, 6).toString());
						fieldBean.setRun(Boolean.parseBoolean(tblModel.getValueAt(i, 7).toString()));
						fieldBean.setCondition(tblModel.getValueAt(i, 8).toString()== null ? "" : tblModel.getValueAt(i, 8).toString());
						fieldBean.setTimeOut(tblModel.getValueAt(i, 9).toString()== null ? "" : tblModel.getValueAt(i, 9).toString());
						
//						fieldBean.setAssertValue(tblModel.getValueAt(i, 5)== null ? "": tblModel.getValueAt(i, 4).toString());
//						fieldBean.setImage(Boolean.parseBoolean(tblModel.getValueAt(i, 6).toString()));

						listField.add(fieldBean);
					}
					if(!cbbMethod.getSelectedItem().toString().trim().equals("")) {
						try {
							actionID.InsertActions(cbbMethod.getSelectedItem().toString().trim(), "updated", listField);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}else {
						formAction = new ActionForm(listField);
						formAction.setVisible(true);
						dispose();
					}
						

					

				} else {
					JOptionPane.showMessageDialog(null, "Please, Add Scenario to Test", "Insert",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		btnSave.setBounds(94, 11, 168, 42);
		panel_4.add(btnSave);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
			
		});
		btnExit.setBounds(622, 11, 98, 42);
		panel_4.add(btnExit);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);
		menuBar.setForeground(new Color(0, 0, 0));
		menuBar.setBackground(new Color(204, 255, 102));
		menuBar.setBounds(20, 0, 121, 22);
		panel.add(menuBar);

		JMenu mnNewMenu = new JMenu("File");
		mnNewMenu.setIcon(new ImageIcon(System.getProperty("user.dir") + "\\icon\\setting.png"));
		mnNewMenu.setForeground(new Color(0, 0, 0));
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("Setting");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setting = new FormSetting();

				setting.setVisible(true);

			}
		});
		mnNewMenu.add(mntmNewMenuItem);

		JMenuBar menuBar_1 = new JMenuBar();
		menuBar_1.setBorderPainted(false);
		menuBar_1.setBackground(new Color(204, 255, 102));
		menuBar_1.setBounds(151, -1, 101, 22);
		panel.add(menuBar_1);

		JMenu mnNewMenu_1 = new JMenu("Add Row");
		mnNewMenu_1.setBackground(new Color(153, 204, 204));
		menuBar_1.add(mnNewMenu_1);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Above");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					tblModel.insertRow(tblData.getSelectedRow(), new Object[] { "", "", "", "", "", "", "", true ,"",""});
					int rowCount = tblModel.getRowCount();
					for (int i = 0; i < rowCount; i++) {
						tblModel.setValueAt("Step " + (i + 1), i, 0);

					}
				} catch (Exception e2) {
					JOptionPane.showConfirmDialog(null, "please choise row insert ", "Warning",
							JOptionPane.CLOSED_OPTION);
					return;
				}

			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_2);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Below");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					tblModel.insertRow(tblData.getSelectedRow() + 1,
							new Object[] { "", "", "", "", "", "", "", true ,"",""});
					int rowCount = tblModel.getRowCount();
					for (int i = 0; i < rowCount; i++) {
						tblModel.setValueAt("Step " + (i + 1), i, 0);

					}
				} catch (Exception e2) {
					JOptionPane.showConfirmDialog(null, "please choise row insert ", "Warning",
							JOptionPane.CLOSED_OPTION);
					return;
				}

			}

		});
		mnNewMenu_1.add(mntmNewMenuItem_1);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBounds(20, 427, 1238, 135);
		panel.add(panel_6);
		panel_6.setLayout(null);
		
		textPane = new JTextPane();
		textPane.setBounds(0, 0, 1238, 135);
	
		
		JScrollPane scrollPane = new JScrollPane(textPane);
		scrollPane.setBounds(312, 86, 2, 2);
		panel_6.add(scrollPane);
		
		
		LoadData();

	}

	public ApiForm() {
		initGUI();

	}

	public void LoadData() {


			tblModel.addRow(new Object[] { "", "", "", "" });

	}



	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

}
