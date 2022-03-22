package com.thanglq.form;

import java.awt.BorderLayout;
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
import javax.xml.stream.events.StartDocument;

import org.json.JSONArray;
import org.json.JSONObject;
import org.yaml.snakeyaml.Yaml;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.thanglq.base.TestBase;
import com.thanglq.bean.ActionsElement;
import com.thanglq.bean.FieldBean;
import com.thanglq.bean.Locators;
import com.thanglq.bean.SettingConfig;
import com.thanglq.dao.FieldDao;
import com.thanglq.dao.LoadActions;
import com.thanglq.util.LoadConfig;

import atu.testrecorder.ATUTestRecorder;
import atu.testrecorder.exceptions.ATUTestRecorderException;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import javax.swing.JTextPane;

public class FormMain extends JFrame {

	private JPanel contentPane;
	private JTextField txtEnv;
	private String column[] = { "Order", "Action", "Locator", "Type", "Value", "Choose file load locator",
			"locator's file yaml", "scenario action", "run","Description" };
	private Object[][] obj = {};
	private JTable tblData;
	private DefaultTableModel tblModel;
	private JComboBox cbbAction;
	private JComboBox cbbType;
	private JComboBox cbbValue;
	private int count = 1;
	private JComboBox cbbBrowser;
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
	private NewFormAction formAction;
	private JComboBox cbbPages;
	private JComboBox cbbElements;
	private JComboBox cbbActionID;
	private Map<String, String> mapFile;
	private Map<String, Locators> mapElements;
	private Locators locator;
	private ActionsElement scenarioAction;
	private Map<String, List<ActionsElement>> mapActions;
	private LoadActions actionID;
	private Thread thread;
	private ExportForm formExport;
	private ImportForm importForm;
	public static List<FieldBean> LIST_IMPORT;
	private ATUTestRecorder recorder;
	public  JScrollPane scrollPaneContent;
	public static JTextPane textPane;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormMain frame = new FormMain();
					frame.setTitle("Form Automation");
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
	public FormMain(List<FieldBean> listField, boolean isHaveScenario, int scenario_id, int feature_id) {
//		super();
		this.listField = listField;
		this.isHaveScenario = isHaveScenario;
		this.scenario_id = scenario_id;
		this.feature_id = feature_id;
		initGUI();

	}

	public FormMain(int scenario_id, int feature_id) {
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

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1347, 883);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 102, 153));
		panel.setBounds(0, 0, 1321, 850);
		contentPane.add(panel);
		panel.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 102, 153));
		panel_1.setBounds(20, 32, 416, 51);
		panel.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblEnv = new JLabel("ENV");
		lblEnv.setBounds(10, 11, 63, 33);
		panel_1.add(lblEnv);

		txtEnv = new JTextField();		
		txtEnv.setBounds(45, 14, 331, 27);
		panel_1.add(txtEnv);
		txtEnv.setColumns(10);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(0, 102, 153));
		panel_2.setBounds(828, 32, 430, 55);
		panel.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblNewLabel = new JLabel("Browser");
		lblNewLabel.setBounds(214, 11, 59, 33);
		panel_2.add(lblNewLabel);

		cbbBrowser = new JComboBox();
		cbbBrowser.setBounds(316, 11, 104, 27);
		panel_2.add(cbbBrowser);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(20, 94, 1291, 549);
		panel.add(panel_3);
		panel_3.setLayout(null);
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
					return Integer.class;
				case 1:
					return String.class;
				case 2:
					return String.class;
				case 3:
					return String.class;
				case 4:
					return String.class;
				case 5:
					return String.class;
				case 6:
					return String.class;
				case 7:
					return String.class;
				case 8:
					return Boolean.class;
				case 9:
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
		TableColumn columnAction = tblData.getColumnModel().getColumn(1);

		TableColumn columnType = tblData.getColumnModel().getColumn(3);
		TableColumn columnvalue = tblData.getColumnModel().getColumn(4);
		TableColumn columnPage = tblData.getColumnModel().getColumn(5);
		TableColumn columnElement = tblData.getColumnModel().getColumn(6);
		TableColumn columnActionID = tblData.getColumnModel().getColumn(7);

//		cbbRule = new JComboBox();
//		cbbData = new JComboBox();
		cbbAction = new JComboBox();
		cbbType = new JComboBox();
		cbbValue = new JComboBox();
		cbbPages = new JComboBox();
		cbbElements = new JComboBox();
		cbbActionID = new JComboBox();

//		int row = tblData.getSelectedRow();
//		loadDataElements(cbbPages.getSelectedItem().toString(), row, 6);
		LoadValueIntoCombobox();
		columnAction.setCellEditor(new DefaultCellEditor(cbbAction));
		columnType.setCellEditor(new DefaultCellEditor(cbbType));
		columnvalue.setCellEditor(new DefaultCellEditor(cbbValue));
		columnPage.setCellEditor(new DefaultCellEditor(cbbPages));
		columnElement.setCellEditor(new DefaultCellEditor(cbbElements));
		columnActionID.setCellEditor(new DefaultCellEditor(cbbActionID));

		cbbPages.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {

					int row = tblData.getSelectedRow();
					loadDataElements(cbbPages.getSelectedItem().toString(), row, 6);
					

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
					loadCombobox();
					cbbType.addItem(locators.getType());
					cbbValue.addItem(locators.getValue());
					tblModel.setValueAt(locators.getType(), row, 3);
					tblModel.setValueAt(locators.getValue(), row, 2);
				}

			}

		});

		cbbType.setEditable(true);
		cbbValue.setEditable(true);
		cbbActionID.setEditable(true);
		tblData.setModel(tblModel);
		tblData.setBackground(Color.WHITE);
		tblData.setForeground(Color.BLACK);
		tblData.setSelectionBackground(Color.GRAY);
		tblData.setGridColor(Color.CYAN);
		tblData.setSelectionForeground(Color.white);
		tblData.setFont(new Font("TAHOMA", Font.PLAIN, 17));
		tblData.setRowHeight(30);
		tblData.setAutoCreateRowSorter(false);
		tblData.setAutoscrolls(true);
		

		JScrollPane scrollPanel = new JScrollPane(tblData);

		scrollPanel.setForeground(Color.red);
		scrollPanel.setBackground(Color.white);
		scrollPanel.setBounds(10, 11, 1281, 538);

		panel_3.add(scrollPanel);

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(new Color(153, 255, 255)));
		panel_4.setBackground(new Color(0, 102, 153));
		panel_4.setBounds(20, 654, 1248, 64);
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
				tblModel.addRow(new Object[] { "", "", "", "", "", "", "", "", true });
				int rowCount = tblModel.getRowCount();
				for (int i = 0; i < rowCount; i++) {
					tblModel.setValueAt("Step " + (i + 1), i, 0);

				}
				btnDelete.setVisible(true);

			}
		});
		btnAddRow.setBounds(490, 11, 110, 42);
		panel_4.add(btnAddRow);

		JButton btnStart = new JButton("Start");
		btnStart.setIcon(new ImageIcon((System.getProperty("user.dir") + "\\icon\\start.png")));
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thread = new Thread(new Runnable() {

					@Override
					public void run() {
						btnStart.setEnabled(false);
						Record(System.getProperty("user.dir")+"\\Report");
						if (txtEnv.getText().trim().equals("")) {
							JOptionPane.showConfirmDialog(null, "please input website address", "Warning",
									JOptionPane.CLOSED_OPTION);
							return;

						}
						if (scenario_id == 0 && feature_id == 0) {
							JOptionPane.showConfirmDialog(null, "please input Scenario", "Warning", JOptionPane.CLOSED_OPTION);
							return;

						}

						List<FieldBean> listTable = getListTable();
						if (scenario_id > 0 && feature_id > 0) {
							try {
								boolean updateStep = dao.UpdateStep(listTable, feature_id, scenario_id);

							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						if(LoadConfig.APP_FORM.equals("false")) {
							
							Start();
							try {
								
								testBase.StepByStep(listField,tblData,false);
							} catch (Exception e2) {
								TestBase.content = TestBase.content+ e2.toString() + "\n";
								FormMain.textPane.setText(TestBase.content);
								FormMain.textPane.setCaretPosition(FormMain.textPane.getDocument().getLength());
							}
						}
						else if(LoadConfig.APP_FORM.equals("true")){
						
							testBase.StepByStep(listField,tblData,true);
						}
						if(SettingConfig.IS_RECORD) {
							try {
								
								recorder.stop();
							} catch (ATUTestRecorderException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
//						JOptionPane.showConfirmDialog(null, "Test Completed", "Information", JOptionPane.CLOSED_OPTION);
					
						btnStart.setEnabled(true);
						
					}});
				thread.start();
			
				
//				
			}
		});
		btnStart.setBounds(738, 11, 110, 42);
		panel_4.add(btnStart);

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

		JButton btnSave = new JButton("Save");
		btnSave.setIcon(new ImageIcon((System.getProperty("user.dir") + "\\icon\\save.png")));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int rowCount = tblModel.getRowCount();
				if (rowCount > 0) {
				
					List<FieldBean> listTable = getListTable();
					
					if (scenario_id > 0 && feature_id > 0) {
						try {
							boolean updateStep = dao.UpdateStep(listTable, feature_id, scenario_id);
							if (updateStep) {
								JOptionPane.showConfirmDialog(null, "Update", "Update Sucessfull",
										JOptionPane.PLAIN_MESSAGE);
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {
						input = new InputName(listField);
						input.setVisible(true);

					}

				} else {
					JOptionPane.showMessageDialog(null, "Please, Add Scenario to Test", "Insert",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		btnSave.setBounds(94, 11, 123, 42);
		panel_4.add(btnSave);

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
		
		JMenuItem mnbSaveActions = new JMenuItem("New Action");
		mnbSaveActions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				formAction = new NewFormAction();

				formAction.setVisible(true);
			}
			
		});
		mnNewMenu.add(mnbSaveActions);
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mnbExport = new JMenuItem("Export Feature");
		mnbExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<FieldBean> listTable = getListTable();
				formExport = new ExportForm(listTable);
				formExport.setVisible(true);
				
			}
		});
		mnNewMenu.add(mnbExport);
		
		JMenuItem mnbImport = new JMenuItem("Import File Feature");
		mnbImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				importForm = new ImportForm();
				importForm.setVisible(true);
						
			}
		});
		mnNewMenu.add(mnbImport);

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
					tblModel.insertRow(tblData.getSelectedRow(), new Object[] { "", "", "", "", "", "", "", "", true,"" });
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
							new Object[] { "", "", "", "", "", "", "", "", true,"" });
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
		
		JButton btnRefresh = new JButton("");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<String> listActions;
				try {
					listActions = actionID.getActionForm();
					cbbActionID.removeAllItems();
					for(int i=0;i<listActions.size();i++) {
						cbbActionID.addItem(listActions.get(i));
					}
			
					if(FormMain.LIST_IMPORT!=null && FormMain.LIST_IMPORT.size()>0) {
						List<FieldBean> listField = FormMain.LIST_IMPORT;
						for (int i = 0; i < listField.size(); i++) {
							tblModel.addRow(new Object[] {"", listField.get(i).getAction(),
									listField.get(i).getLocator(), listField.get(i).getType(), listField.get(i).getValue(),
									listField.get(i).getPage(), listField.get(i).getLocator_page(),
									listField.get(i).getAction_page(), listField.get(i).getRun(),listField.get(i).getDescription() });
						}
						int rowCount = tblModel.getRowCount();
						for (int i = 0; i < rowCount; i++) {
							tblModel.setValueAt("Step " + (i + 1), i, 0);
						}
						
						FormMain.LIST_IMPORT.clear();
					}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		btnRefresh.setIcon(new ImageIcon((System.getProperty("user.dir") + "\\icon\\refresh.png")));
		btnRefresh.setBounds(663, 43, 33, 29);
		panel.add(btnRefresh);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBounds(10, 729, 1301, 106);
		panel.add(panel_5);
		panel_5.setLayout(null);
		textPane = new JTextPane();
		textPane.setBounds(10, 13, 1281, 90);
		
		scrollPaneContent = new JScrollPane(textPane);
		scrollPaneContent.setBounds(0, 0, 1291, 114);
		panel_5.add(scrollPaneContent);

		LoadData();

	}

	public FormMain() {
		initGUI();

	}

	public void LoadData() {
		if (isHaveScenario) {
			for (int i = 0; i < listField.size(); i++) {
				tblModel.addRow(new Object[] { listField.get(i).getOrder(), listField.get(i).getAction(),
						listField.get(i).getLocator(), listField.get(i).getType(), listField.get(i).getValue(),
						listField.get(i).getPage(), listField.get(i).getLocator_page(),
						listField.get(i).getAction_page(), listField.get(i).getRun(),listField.get(i).getDescription() });
			}

		} else {

			tblModel.addRow(new Object[] { "Step " + count, "", "", "", "", "", "", "", true,"" });
		}
		SettingConfig.RESOLUTION ="";
	}

	public void LoadValueIntoCombobox() {		
		try {
			LoadConfig.readConfigAcction();
			
			String[] arr = LoadConfig.ACTION.split(",");
			for(int i=0;i<arr.length;i++) {
				cbbAction.addItem(arr[i].trim());
			}
			String[] arrType = LoadConfig.TYPE.split(",");

			for(int i=0;i<arrType.length;i++) {
				cbbType.addItem(arrType[i].trim());
			}

			String[] arrBrowser = LoadConfig.BROWSER.split(",");

			for(int i=0;i<arrBrowser.length;i++) {
				cbbBrowser.addItem(arrBrowser[i].trim());
			}
			txtEnv.setText(LoadConfig.URL);


			testBase = new TestBase();
			LoadPage();
			mapElements = new LinkedHashMap<>();
			mapActions = new LinkedHashMap<String, List<ActionsElement>>();
			dao = new FieldDao();
			actionID = new LoadActions();
			List<String> listActions = actionID.getActionForm();
			for(int i=0;i<listActions.size();i++) {
				cbbActionID.addItem(listActions.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void Start() {

		testBase.StartBrowser(cbbBrowser.getSelectedItem().toString(), txtEnv.getText().trim());

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

	public void LoadPage() {
		mapFile = new LinkedHashMap<>();

		Map<String, String> listTemp = findFile(new File(System.getProperty("user.dir") + "/Pages"), mapFile);
		if (!listTemp.isEmpty()) {
			Set<String> keySet = listTemp.keySet();
			for (String key : keySet) {
				cbbPages.addItem(key);
			}
		}

	}

	public void loadDataElements(String filename, int row, int col) {
		List<String> list = new LinkedList<>();
		try {

			String path = mapFile.get(filename);
			File f = new File(path);
			String json;

			json = convertYamlToJson(f);
			JSONObject obj = new JSONObject(json);
			JSONArray arr = new JSONArray(obj.get("elements").toString());
			for (int i = 0; i < arr.length(); i++) {
				locator = new Locators();
				JSONObject obj2 = new JSONObject(arr.get(i).toString());
//				tblModel.setValueAt(cbbType.setSelectedItem(obj2.get("id")), row, i)
				locator.setId(obj2.getString("id"));
				locator.setDescription(obj2.getString("description"));
				JSONArray arrTemp = new JSONArray(obj2.get("locators").toString());
				for (int j = 0; j < arrTemp.length(); j++) {
					JSONObject obj3 = new JSONObject(arrTemp.get(j).toString());

					if (obj3.has("type")) {
						locator.setType(obj3.getString("type") == null ? "" : obj3.getString("type"));
					}
					if (obj3.has("value")) {
						locator.setValue(obj3.getString("value") == null ? "" : obj3.getString("value"));
					}

				}
				if (!list.contains(locator.getId())) {
					list.add(locator.getId());
				}
//				if(mapElements.get(locator.getId())!=null && mapElements.containsKey(locator.getId())) {
//					JOptionPane.showConfirmDialog(null, "duplicate ID "+locator.getId() + " in file yaml la ", "Warning", JOptionPane.CLOSED_OPTION);
//					return;
//				}
				mapElements.put(locator.getId(), locator);
			}
			cbbElements.removeAllItems();
			if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					cbbElements.addItem(list.get(i));
					tblModel.setValueAt(locator.getId(), row, col);
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String convertYamlToJson(File f) throws IOException {

		ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
		Object obj = yamlReader.readValue(f, Object.class);

		ObjectMapper jsonWriter = new ObjectMapper();
		return jsonWriter.writeValueAsString(obj);
	}


	public Map<String, String> findFile(File dir, Map<String, String> list) {

		File[] listFiles = dir.listFiles();
		for (File file : listFiles) {
			if (file.isFile()) {
				list.put(file.getName(), file.getAbsolutePath());

			} else if (file.isDirectory()) {
				findFile(new File(file.getAbsolutePath()), list);
			}
		}
		return list;
	}
	public void loadCombobox() {
		String[] arr = LoadConfig.ACTION.split(",");
		for(int i=0;i<arr.length;i++) {
			cbbAction.addItem(arr[i].trim());
		}
		String[] arrType = LoadConfig.TYPE.split(",");

		for(int i=0;i<arrType.length;i++) {
			cbbType.addItem(arrType[i].trim());
		}
	}
	public List<FieldBean> getListTable(){
		listField= new LinkedList<FieldBean>();
		int rowCount = tblModel.getRowCount();
		listField = new LinkedList<>();
		for (int i = 0; i < rowCount; i++) {
			fieldBean = new FieldBean();
			fieldBean.setOrder(tblModel.getValueAt(i, 0).toString());
			fieldBean.setAction(tblModel.getValueAt(i, 1).toString());
			fieldBean.setLocator(tblModel.getValueAt(i, 2).toString());
			fieldBean.setType(tblModel.getValueAt(i, 3).toString());
			fieldBean.setValue(tblModel.getValueAt(i, 4) == null ? "" : tblModel.getValueAt(i, 4).toString());
			fieldBean.setPage(tblModel.getValueAt(i, 5) == null ? "" : tblModel.getValueAt(i, 5).toString());
			fieldBean.setLocator_page(
					tblModel.getValueAt(i, 6) == null ? "" : tblModel.getValueAt(i, 6).toString());
			fieldBean.setAction_page(
					tblModel.getValueAt(i, 7) == null ? "" : tblModel.getValueAt(i, 7).toString());
			fieldBean.setRun(Boolean.parseBoolean(tblModel.getValueAt(i, 8).toString()));
			fieldBean.setDescription(
					tblModel.getValueAt(i, 9) == null ? "" : tblModel.getValueAt(i, 9).toString());
//			fieldBean.setAssertValue(tblModel.getValueAt(i, 5)== null ? "": tblModel.getValueAt(i, 4).toString());
//			fieldBean.setImage(Boolean.parseBoolean(tblModel.getValueAt(i, 6).toString()));

			listField.add(fieldBean);
		}
		
		return listField;
	}
	public void Record(String path) {
		if (SettingConfig.IS_RECORD) {
			DateFormat dateFormate = new SimpleDateFormat("yy-MM-dd HH-mm-ss");
			Date date = new java.util.Date();
			
			try {
				recorder = new ATUTestRecorder(path,
						"RecordedVideo-" + dateFormate.format(date), false);
				recorder.start();
			} catch (ATUTestRecorderException e) {
				try {
					recorder.stop();
				} catch (ATUTestRecorderException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
	}
}
