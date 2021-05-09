package Default;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeSelectionModel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import net.miginfocom.swing.MigLayout;
import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;

public class GUI {

	public JFrame frame;
	private ExcelAPI excelAPI;
	private JScrollPane scrollPane;
	private JTree tree;
	private ArrayList<String> TotalsList;
	private JLabel totalPackages = new JLabel("-");
	private JLabel totalClasses = new JLabel("-");
	private JLabel totalMethods = new JLabel("-");
	private JLabel totalLOC = new JLabel("-");
	private JLabel lblNewLabel_1_3_1 = new JLabel("Classname");
	private JLabel lblNewLabel_2_2_1_1_2 = new JLabel("-");
	private JLabel lblNewLabel_2_2_1_1_4 = new JLabel("-");
	private JLabel lblNewLabel_2_2_1_1_6 = new JLabel("-");
	private JLabel lblNewLabel_2_2_1_1_8 = new JLabel("N/A");
	private JLabel methodCodeLinesLabel = new JLabel("-");
	private JLabel methodNameLabel = new JLabel("Methodname");
	private JLabel methodCyclesLabel = new JLabel("-");
	private JLabel methodIsLongLabel = new JLabel("N/A");
	private JTextField NOMperClass = new JTextField();
	private JTextField LOCperClass = new JTextField();
	private JTextField WMCofClass = new JTextField();
	private JTextField LOCperMethod = new JTextField();
	private JTextField CYCLOofMethod = new JTextField();
	private JComboBox<Object> LongComboBox = new JComboBox<Object>();
	private JComboBox<Object> GodComboBox1 = new JComboBox<Object>();
	private JComboBox<Object> GodComboBox2 = new JComboBox<Object>();
	private JButton btnNewButton = new JButton("Update Rule");
	private int selectedRule;
	private int oldNOM;
	private LoadProject lp = new LoadProject();


	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	private void setExcelAPI(ExcelAPI excelAPI) {
		this.excelAPI = excelAPI;
	}

	private ExcelAPI getExcelAPI() {
		return this.excelAPI;
	}

	private void removeExcelAPI() {
		this.excelAPI = null;
	}

	private void selectExcel() {
		JFileChooser chooser = new JFileChooser(".\\Excel Files");
		chooser.setMultiSelectionEnabled(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files", "xlsx", "excel");
		chooser.setFileFilter(filter);
		chooser.showOpenDialog(null);
		excelAPI = new ExcelAPI();
		excelAPI.setFileToRead(chooser.getSelectedFile());
		setExcelAPI(excelAPI);
	}

	public void updateLabel(JLabel label, String text) {
		label.setText(text);
		label.paintImmediately(label.getVisibleRect());
	}

	private void updateWindow() throws Exception, Exception {
		if (excelAPI != null) {
			tree = new JTree();
			tree.setModel(excelAPI.readExcel());
			tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

			tree.addTreeSelectionListener(new TreeSelectionListener() {
				public void valueChanged(TreeSelectionEvent e) {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

					if (node == null) {
						return;
					} else if (node.isLeaf() && node.getParent().toString().contains(".java")) {
						try {
							Map<String, String[]> methods = excelAPI.getExcelMethods();
							String[] info = methods.get(node.toString());
							updateLabel(methodCodeLinesLabel, info[0]);
							updateLabel(methodNameLabel, node.toString());
							updateLabel(methodCyclesLabel, info[1]);
							updateLabel(methodIsLongLabel, info[2]);
							String parent = node.getParent().toString();
							excelAPI.findClassSmellsByName(parent);
							updateLabel(lblNewLabel_1_3_1, parent);
							updateLabel(lblNewLabel_2_2_1_1_2, excelAPI.answers.get(0));
							updateLabel(lblNewLabel_2_2_1_1_4, excelAPI.answers.get(1));
							updateLabel(lblNewLabel_2_2_1_1_6, excelAPI.answers.get(2));
							updateLabel(lblNewLabel_2_2_1_1_8, excelAPI.answers.get(3));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {
						Object nodeInfo = node.getUserObject();

						if (nodeInfo.toString().contains(".java")) {
							updateLabel(lblNewLabel_1_3_1, nodeInfo.toString());
							excelAPI.findClassSmellsByName(nodeInfo.toString());
							updateLabel(lblNewLabel_2_2_1_1_2, excelAPI.answers.get(0));
							updateLabel(lblNewLabel_2_2_1_1_4, excelAPI.answers.get(1));
							updateLabel(lblNewLabel_2_2_1_1_6, excelAPI.answers.get(2));
							updateLabel(lblNewLabel_2_2_1_1_8, excelAPI.answers.get(3));
						}

					}
				}
			});
			// ExcelAPI a = new ExcelAPI();

			// System.out.println(excelAPI.answers.get(0));

			// updateLabel(lblNewLabel_2_2_1_1_2 ,a.answers.get(0));
			scrollPane.setViewportView(tree);
		}
		scrollPane.updateUI();
	}

	private void updateLabels() throws IOException, Exception {
		TotalsList = excelAPI.readExcelTotals();
		updateLabel(totalPackages, TotalsList.get(0));
		updateLabel(totalClasses, TotalsList.get(1));
		updateLabel(totalMethods, TotalsList.get(2));
		updateLabel(totalLOC, TotalsList.get(3));
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
	
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 850, 714);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuItem i1, i2, i3, i4, i5, i6, i7, i8;
		JMenu File = new JMenu("File");

		JMenu Rules = new JMenu("Rules");
	
		i1 = new JMenuItem("Project to Metrics File");
		i1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					lp.openProject();
				} catch (InvalidFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
				
				
		
		i2 = new JMenuItem("Open Metrics File");
		i2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectExcel();
				try {
					TotalsList = excelAPI.readExcelTotals();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					updateLabels();
					updateWindow();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		i3 = new JMenuItem("Create new Rule");
		i3 = new JMenuItem("Create new Rule");
		i3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame("New Rule");
				JPanel panel = new JPanel();
				GridBagLayout gbl = new GridBagLayout();
				JLabel title = new JLabel("New Rule", SwingConstants.CENTER);
				JLabel smell_one = new JLabel("Long Method", SwingConstants.CENTER);
				JLabel smell_two = new JLabel("God Class", SwingConstants.CENTER);
				JLabel nomClass = new JLabel("NOM per Class >", SwingConstants.CENTER);
				JLabel locClass = new JLabel("LOC per Class >", SwingConstants.CENTER);
				JLabel wmcClass = new JLabel("WMC of Class >", SwingConstants.CENTER);
				JLabel locMethod = new JLabel("LOC per Method >", SwingConstants.CENTER);
				JLabel cycloMethod = new JLabel("CYCLO of Method >", SwingConstants.CENTER);
				JTextArea valueNomClass = new JTextArea();
				JTextArea valueLocClass = new JTextArea();
				JTextArea valueWmcClass = new JTextArea();
				JTextArea valueLocMethod = new JTextArea();
				JTextArea valueCycloMethod = new JTextArea();
				String[] operators = { "", "And", "Or" };
				panel.setLayout(gbl);
				panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
				JButton button;
				panel.setLayout(new GridBagLayout());
				GridBagConstraints c = new GridBagConstraints();
				c.fill = GridBagConstraints.HORIZONTAL;
				c.ipady = 2; // increase height of the title
				c.weightx = 0.5;
				c.weighty = 0.25;
				c.gridwidth = 4;
				c.gridx = 0;
				c.gridy = 0;
				panel.add(title, c);
				c.ipady = 0;
				c.gridwidth = 2;
				c.weightx = 0.5;
				c.gridx = 0;
				c.gridy = 1;
				panel.add(smell_one, c); // Smell title 1
				c.weightx = 0.5;
				c.gridwidth = 2;
				c.gridx = 3;
				c.gridy = 1;
				panel.add(smell_two, c); // Smell Title 2
				c.fill = GridBagConstraints.NONE;
				c.ipady = 0;
				c.gridwidth = 1;
				c.weightx = 0.25;
				c.gridx = 0;
				c.gridy = 2;
				panel.add(locMethod, c);
				c.gridx = 1;
				JComboBox<String> methodOp = new JComboBox<String>(operators);
				c.gridx = 0;
				c.gridy = 3;
				panel.add(methodOp, c);
				c.gridy = 4;
				panel.add(cycloMethod, c); // metric method 2
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx = 2;
				c.gridy = 2;
				valueLocMethod.setMinimumSize(new Dimension(10, 5));
				panel.add(valueLocMethod, c); // value metric 1
				c.gridy = 4;
				valueCycloMethod.setMinimumSize(new Dimension(10, 2));
				panel.add(valueCycloMethod, c); // value metric 2
				c.fill = GridBagConstraints.NONE;
				c.gridx = 3;
				c.gridy = 2;
				panel.add(nomClass, c); // metric class 1
				JComboBox<String> classOpOne = new JComboBox<String>(operators);
				c.gridy = 3;
				panel.add(classOpOne, c);
				c.gridy = 4;
				c.gridx = 3;
				panel.add(locClass, c);// metric class 2
				JComboBox<String> classOpTwo = new JComboBox<String>(operators);
				c.gridy = 5;
				c.gridx = 3;
				panel.add(classOpTwo, c);
				c.gridx = 3;
				c.gridy = 6;
				panel.add(wmcClass, c);// metric class 3
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx = 4;
				c.gridy = 2;
				valueNomClass.setMinimumSize(new Dimension(10, 2));
				panel.add(valueNomClass, c); // value class 1
				c.gridy = 4;
				valueLocClass.setMinimumSize(new Dimension(10, 2));
				panel.add(valueLocClass, c); // value class 2
				c.gridy = 6;
				valueWmcClass.setMinimumSize(new Dimension(10, 2));
				panel.add(valueWmcClass, c); // value class 3
				button = new JButton("Save / Set Rule");
				c.ipady = 0;
				c.weighty = 1.0;
				c.insets = new Insets(10, 0, 0, 0);
				c.gridx = 1;
				c.gridwidth = 2;
				c.gridy = 8;
				panel.add(button, c);
				button.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int i = 0;
						try {
							if (getNumberOfRulesTxt() != null) {
								i = Integer.parseInt(getNumberOfRulesTxt()) + 1;
							}
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						}
						try {
							File ruleFile = new File("Rules.txt");
							if (ruleFile.createNewFile()) {
								System.out.println("Rule file created: " + ruleFile.getName());
								i = 0;
							}
							FileWriter outstream = new FileWriter(ruleFile, true);
							outstream.write("Rule " + i + "\n");
							outstream.write("Long Method :\n");
							ArrayList<Pair<Integer, Boolean>> results = new ArrayList<Pair<Integer, Boolean>>();
							if (!valueLocMethod.getText().isEmpty()) {
								outstream.write(locMethod.getText() + " " + valueLocMethod.getText() + " "
										+ methodOp.getSelectedItem() + "\n");
								Pair<Integer, Boolean> locMethodResult = new Pair<Integer, Boolean>(Integer.parseInt(valueLocMethod.getText()) , true);
								results.add(locMethodResult);
							}
							else {
								Pair<Integer, Boolean> locMethodResult = new Pair<Integer, Boolean>( 0, false);
								results.add(locMethodResult);
							}
							if (!valueCycloMethod.getText().isEmpty()) {
								outstream.write(cycloMethod.getText() + " " + valueCycloMethod.getText() + "\n");
								Pair<Integer, Boolean> cycloMethodResult = new Pair<Integer, Boolean>( Integer.parseInt(valueCycloMethod.getText()), true);
								results.add(cycloMethodResult);
							}else {
								Pair<Integer, Boolean> cycloMethodResult = new Pair<Integer, Boolean>( 0, false);
								results.add(cycloMethodResult);
							}
							if(methodOp.getSelectedItem().toString() == "Or") {
								Pair<Integer, Boolean> firstOp = new Pair<Integer, Boolean>(0,true);
								results.add(firstOp);
							}else {
								Pair<Integer, Boolean> firstOp = new Pair<Integer, Boolean>(0,false);
								results.add(firstOp);
							}
							outstream.write("God Class:\n");
							if (!valueNomClass.getText().isEmpty()){
								outstream.write(nomClass.getText() + " " + valueNomClass.getText() + " "
										+ classOpOne.getSelectedItem() + "\n");
								Pair<Integer, Boolean> nomClassResult = new Pair<Integer, Boolean>( Integer.parseInt(valueNomClass.getText()), true);
								results.add(nomClassResult);
							}else {
								Pair<Integer, Boolean> nomClassResult = new Pair<Integer, Boolean>( 0, false);
								results.add(nomClassResult);
							}
							if (!valueLocClass.getText().isEmpty()) {
								outstream.write(locClass.getText() + " " + valueLocClass.getText() + " "
										+ classOpTwo.getSelectedItem() + "\n");
								Pair<Integer, Boolean> locClassResult = new Pair<Integer, Boolean>( Integer.parseInt(valueLocClass.getText()), true);
								results.add(locClassResult);
							}else {
								Pair<Integer, Boolean> locClassResult = new Pair<Integer, Boolean>( 0, false);
								results.add(locClassResult);
							}
							if (!valueWmcClass.getText().isEmpty() ) {
								outstream.write(wmcClass.getText() + " " + valueWmcClass.getText() + "\n");
								Pair<Integer, Boolean> wmcClassResult = new Pair<Integer, Boolean>( Integer.parseInt(valueWmcClass.getText()), true);
								results.add(wmcClassResult);
							}else {
								Pair<Integer, Boolean> wmcClassResult = new Pair<Integer, Boolean>( 0, false);
								results.add(wmcClassResult);
							}
							if(classOpOne.getSelectedItem().toString() == "Or") {
								Pair<Integer, Boolean> secondOp = new Pair<Integer, Boolean>(0,true);
								results.add(secondOp);
							}else {
								Pair<Integer, Boolean> secondOp = new Pair<Integer, Boolean>(0,false);
								results.add(secondOp);
							}
							if(classOpTwo.getSelectedItem().toString() == "Or") {
								Pair<Integer, Boolean> thirdOp = new Pair<Integer, Boolean>(0,true);
								results.add(thirdOp);
							}else {
								Pair<Integer, Boolean> thirdOp = new Pair<Integer, Boolean>(0,false);
								results.add(thirdOp);
							}
							outstream.write("/////\n");
							outstream.close();
							lp.receiveRule(results);
							frame.dispose();
						} catch (IOException d) {
							System.out.println("An error occurred.");
							d.printStackTrace();
						}
					}
				});
				frame.add(panel);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.pack();
				frame.setSize(750, 300);
				frame.setVisible(true);
			}
		});
		i4 = new JMenuItem("Change Rules");
		i4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFrame frame = new JFrame();
				frame.setVisible(true);
				frame.setBounds(100, 100, 600, 302);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				JPanel panel = new JPanel();
				frame.getContentPane().add(panel);
				GridBagLayout gbl_panel = new GridBagLayout();
				gbl_panel.columnWidths = new int[] { 92, 207, 255, 0 };
				gbl_panel.rowHeights = new int[] { 260, 0 };
				gbl_panel.columnWeights = new double[] { 0.0, 1.0, 1.0, Double.MIN_VALUE };
				gbl_panel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
				panel.setLayout(gbl_panel);

				DefaultListModel<Object> d = new DefaultListModel<Object>();
				for (int i = 0; i < readTxt().size(); i++) {
					d.addElement("Regra " + (i + 1));
				}
				JList<Object> list = new JList<Object>(d);
				list.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						int index = list.locationToIndex(e.getPoint());
						ArrayList<String> dataList = readTxt().get(index);
						LOCperMethod.setText(dataList.get(0));
						if (dataList.get(1).equals("And")) {
							LongComboBox.setSelectedIndex(0);
						}
						if (dataList.get(1).equals("Or")) {
							LongComboBox.setSelectedIndex(1);
						}
						if (dataList.get(1).equals("---")) {
							LongComboBox.setSelectedIndex(2);
						}
						CYCLOofMethod.setText(dataList.get(2));
						NOMperClass.setText(dataList.get(3));
						if (dataList.get(4).equals("And")) {
							GodComboBox1.setSelectedIndex(0);
						}
						if (dataList.get(4).equals("Or")) {
							GodComboBox1.setSelectedIndex(1);
						}
						if (dataList.get(4).equals("---")) {
							GodComboBox1.setSelectedIndex(2);
						}
						LOCperClass.setText(dataList.get(5));
						if (dataList.get(6).equals("And")) {
							GodComboBox2.setSelectedIndex(0);
						}
						if (dataList.get(6).equals("Or")) {
							GodComboBox2.setSelectedIndex(1);
						}
						if (dataList.get(6).equals("---")) {
							GodComboBox2.setSelectedIndex(2);
						}
						WMCofClass.setText(dataList.get(7));
						selectedRule = index;
					}
				});
				list.setAlignmentY(Component.TOP_ALIGNMENT);
				list.setAlignmentX(Component.RIGHT_ALIGNMENT);

				GridBagConstraints gbc_list = new GridBagConstraints();
				gbc_list.insets = new Insets(0, 0, 0, 5);
				gbc_list.fill = GridBagConstraints.BOTH;
				gbc_list.gridx = 0;
				gbc_list.gridy = 0;
				panel.add(list, gbc_list);

				JPanel panel_1 = new JPanel();
				GridBagConstraints gbc_panel_1 = new GridBagConstraints();
				gbc_panel_1.insets = new Insets(0, 0, 0, 5);
				gbc_panel_1.fill = GridBagConstraints.BOTH;
				gbc_panel_1.gridx = 1;
				gbc_panel_1.gridy = 0;
				panel.add(panel_1, gbc_panel_1);
				GridBagLayout gbl_panel_1 = new GridBagLayout();
				gbl_panel_1.columnWidths = new int[] { 0, 0, 85, 0, 0, 0, 0, 0 };
				gbl_panel_1.rowHeights = new int[] { 50, 0, 50, 0, 50, 0, 50, 0 };
				gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
				gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
				panel_1.setLayout(gbl_panel_1);

				JLabel lblNewLabel = new JLabel("God Class");
				lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
				GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
				gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
				gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
				gbc_lblNewLabel.gridx = 2;
				gbc_lblNewLabel.gridy = 0;
				panel_1.add(lblNewLabel, gbc_lblNewLabel);

				JLabel lblNewLabel_3 = new JLabel("NOM per Class");
				GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
				gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
				gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
				gbc_lblNewLabel_3.gridx = 1;
				gbc_lblNewLabel_3.gridy = 2;
				panel_1.add(lblNewLabel_3, gbc_lblNewLabel_3);

				NOMperClass = new JTextField();
				GridBagConstraints gbc_textField = new GridBagConstraints();
				gbc_textField.insets = new Insets(0, 0, 5, 5);
				gbc_textField.fill = GridBagConstraints.HORIZONTAL;
				gbc_textField.gridx = 2;
				gbc_textField.gridy = 2;
				panel_1.add(NOMperClass, gbc_textField);
				NOMperClass.setColumns(10);

				GodComboBox1 = new JComboBox<Object>();
				GodComboBox1.setModel(new DefaultComboBoxModel<Object>(new String[] { "And", "Or", "Not Chosen" }));
				GodComboBox1.setSelectedIndex(2);
				GridBagConstraints gbc_comboBox = new GridBagConstraints();
				gbc_comboBox.insets = new Insets(0, 0, 5, 5);
				gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
				gbc_comboBox.gridx = 2;
				gbc_comboBox.gridy = 3;
				panel_1.add(GodComboBox1, gbc_comboBox);

				JLabel lblNewLabel_2 = new JLabel("LOC per Class");
				GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
				gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
				gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
				gbc_lblNewLabel_2.gridx = 1;
				gbc_lblNewLabel_2.gridy = 4;
				panel_1.add(lblNewLabel_2, gbc_lblNewLabel_2);

				LOCperClass = new JTextField();
				GridBagConstraints gbc_textField_1 = new GridBagConstraints();
				gbc_textField_1.insets = new Insets(0, 0, 5, 5);
				gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
				gbc_textField_1.gridx = 2;
				gbc_textField_1.gridy = 4;
				panel_1.add(LOCperClass, gbc_textField_1);
				LOCperClass.setColumns(10);

				GodComboBox2 = new JComboBox<Object>();
				GodComboBox2.setModel(new DefaultComboBoxModel<Object>(new String[] { "And", "Or", "Not Chosen" }));
				GodComboBox2.setSelectedIndex(2);
				GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
				gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
				gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
				gbc_comboBox_1.gridx = 2;
				gbc_comboBox_1.gridy = 5;
				panel_1.add(GodComboBox2, gbc_comboBox_1);

				JLabel lblNewLabel_4 = new JLabel("WMC of Class");
				GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
				gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
				gbc_lblNewLabel_4.insets = new Insets(0, 0, 0, 5);
				gbc_lblNewLabel_4.gridx = 1;
				gbc_lblNewLabel_4.gridy = 6;
				panel_1.add(lblNewLabel_4, gbc_lblNewLabel_4);

				WMCofClass = new JTextField();
				GridBagConstraints gbc_textField_2 = new GridBagConstraints();
				gbc_textField_2.insets = new Insets(0, 0, 0, 5);
				gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
				gbc_textField_2.gridx = 2;
				gbc_textField_2.gridy = 6;
				panel_1.add(WMCofClass, gbc_textField_2);
				WMCofClass.setColumns(10);

				JPanel panel_2 = new JPanel();
				GridBagConstraints gbc_panel_2 = new GridBagConstraints();
				gbc_panel_2.fill = GridBagConstraints.BOTH;
				gbc_panel_2.gridx = 2;
				gbc_panel_2.gridy = 0;
				panel.add(panel_2, gbc_panel_2);
				GridBagLayout gbl_panel_2 = new GridBagLayout();
				gbl_panel_2.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
				gbl_panel_2.rowHeights = new int[] { 60, 0, 60, 0, 60, 0, 0, 0, 0, 0 };
				gbl_panel_2.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
				gbl_panel_2.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
				panel_2.setLayout(gbl_panel_2);

				JLabel lblNewLabel_1 = new JLabel("Long Method");
				lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
				GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
				gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
				gbc_lblNewLabel_1.gridx = 2;
				gbc_lblNewLabel_1.gridy = 0;
				panel_2.add(lblNewLabel_1, gbc_lblNewLabel_1);

				JLabel lblNewLabel_5 = new JLabel("LOC per Method");
				GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
				gbc_lblNewLabel_5.anchor = GridBagConstraints.EAST;
				gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
				gbc_lblNewLabel_5.gridx = 1;
				gbc_lblNewLabel_5.gridy = 2;
				panel_2.add(lblNewLabel_5, gbc_lblNewLabel_5);

				LOCperMethod = new JTextField();
				GridBagConstraints gbc_textField_3 = new GridBagConstraints();
				gbc_textField_3.insets = new Insets(0, 0, 5, 5);
				gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
				gbc_textField_3.gridx = 2;
				gbc_textField_3.gridy = 2;
				panel_2.add(LOCperMethod, gbc_textField_3);
				LOCperMethod.setColumns(10);

				LongComboBox = new JComboBox<Object>();
				LongComboBox.setModel(new DefaultComboBoxModel<Object>(new String[] { "And", "Or", "Not Chosen" }));
				LongComboBox.setSelectedIndex(2);
				GridBagConstraints gbc_comboBox_2 = new GridBagConstraints();
				gbc_comboBox_2.insets = new Insets(0, 0, 5, 5);
				gbc_comboBox_2.fill = GridBagConstraints.HORIZONTAL;
				gbc_comboBox_2.gridx = 2;
				gbc_comboBox_2.gridy = 3;
				panel_2.add(LongComboBox, gbc_comboBox_2);

				JLabel lblNewLabel_6 = new JLabel("CYCLO of Method");
				GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
				gbc_lblNewLabel_6.anchor = GridBagConstraints.EAST;
				gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
				gbc_lblNewLabel_6.gridx = 1;
				gbc_lblNewLabel_6.gridy = 4;
				panel_2.add(lblNewLabel_6, gbc_lblNewLabel_6);

				CYCLOofMethod = new JTextField();
				GridBagConstraints gbc_textField_4 = new GridBagConstraints();
				gbc_textField_4.insets = new Insets(0, 0, 5, 5);
				gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
				gbc_textField_4.gridx = 2;
				gbc_textField_4.gridy = 4;
				panel_2.add(CYCLOofMethod, gbc_textField_4);
				CYCLOofMethod.setColumns(10);

				btnNewButton = new JButton("Update Rule");
				GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
				gbc_btnNewButton.gridwidth = 3;
				gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
				gbc_btnNewButton.gridx = 2;
				gbc_btnNewButton.gridy = 8;
				panel_2.add(btnNewButton, gbc_btnNewButton);
				btnNewButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						oldNOM = Integer.parseInt(readTxt().get(0).get(0));
						File myObj = new File("Rules.txt");
						Scanner myReader = null;
						BufferedWriter bw = null;
						boolean writed = false;
						try {
							myReader = new Scanner(myObj);
							bw = new BufferedWriter(new FileWriter(new File("Rule2.txt")));
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						while (myReader.hasNextLine()) {

							String data = myReader.nextLine();
							String[] dataDivided = data.split(" ");
							String selected = String.valueOf(selectedRule);

							if (dataDivided[0].equals("Rule") && dataDivided[1].equals(selected)) {
								try {
									bw.write("Rule " + selected + "\r\n" + "Long Method :\r\n" + "LOC per Method > "
											+ LOCperMethod.getText() + " " + LongComboBox.getSelectedItem() + "\r\n"
											+ "CYCLO of Method > " + CYCLOofMethod.getText() + "\r\n" + "God Class:\r\n"
											+ "NOM per Class > " + NOMperClass.getText() + " "
											+ GodComboBox1.getSelectedItem() + "\r\n" + "LOC per Class > "
											+ LOCperClass.getText() + " " + GodComboBox2.getSelectedItem() + "\r\n"
											+ "WMC of Class > " + WMCofClass.getText() + "\r\n" + "/////\r\n");
									for (int i = 0; i < 6; i++) {
										while (myReader.hasNextLine()) {
											myReader.nextLine();
										}
									}
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							} else {
								try {
									bw.write(data);
									bw.write("\n");
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
						try {
							bw.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						myReader.close();
						File file = new File("Rules.txt");
						file.delete();
						File file2 = new File("Rule2.txt");
						file2.renameTo(file);
						frame.dispose();
					}

				});

			}
		});
		
		i5 = new JMenuItem("Save Rules");
		i6 = new JMenuItem("Load Rules");

		File.add(i1);
		File.add(i2);
		
		Rules.add(i3);
		Rules.add(i4);
		Rules.add(i5);
		Rules.add(i6);

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(File);
		menuBar.add(Rules);
		frame.setJMenuBar(menuBar);
		frame.getContentPane()
				.setLayout(new MigLayout("", "[225.00px][400.00px][225.00px,grow]", "[42px][480px,grow]"));

		JPanel panel_22 = new JPanel();
		frame.getContentPane().add(panel_22, "cell 0 0 3 1,grow");

		JLabel lblNewLabel22 = new JLabel("Project Name");
		lblNewLabel22.setFont(new Font("Tahoma", Font.PLAIN, 26));
		panel_22.add(lblNewLabel22);

		JPanel panel2 = new JPanel();
		panel2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gbl_panel2 = new GridBagLayout();

		gbl_panel2.columnWidths = new int[] { 25, 175, 25, 0 };
		gbl_panel2.rowHeights = new int[] { 14, 100, 0, 76, 0, 0, 76, 0, 0, 76, 0, 0, 76, 0, 0, 0, 0, 0, 0 };
		gbl_panel2.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel2.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel2.setLayout(gbl_panel2);
		frame.getContentPane().add(panel2, "cell 0 1,grow");

		JLabel Geral = new JLabel("Geral");
		Geral.setFont(new Font("Tahoma", Font.BOLD, 20));

		GridBagConstraints gbc_Geral = new GridBagConstraints();
		gbc_Geral.anchor = GridBagConstraints.NORTH;
		gbc_Geral.insets = new Insets(0, 0, 5, 5);
		gbc_Geral.gridx = 1;
		gbc_Geral.gridy = 1;
		panel2.add(Geral, gbc_Geral);
		JLabel packages = new JLabel("Total #Packages");
		GridBagConstraints gbc_packages = new GridBagConstraints();
		gbc_packages.anchor = GridBagConstraints.NORTH;
		gbc_packages.insets = new Insets(0, 0, 5, 5);
		gbc_packages.gridx = 1;
		gbc_packages.gridy = 2;
		panel2.add(packages, gbc_packages);

		totalPackages.setFont(new Font("Tahoma", Font.BOLD, 25));
		GridBagConstraints gbc_lblNewLabel_2_3 = new GridBagConstraints();
		gbc_lblNewLabel_2_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2_3.gridx = 1;
		gbc_lblNewLabel_2_3.gridy = 3;
		panel2.add(totalPackages, gbc_lblNewLabel_2_3);

		JLabel classes_1 = new JLabel("Total #Classes");
		GridBagConstraints gbc_classes_1 = new GridBagConstraints();
		gbc_classes_1.insets = new Insets(0, 0, 5, 5);
		gbc_classes_1.gridx = 1;
		gbc_classes_1.gridy = 5;
		panel2.add(classes_1, gbc_classes_1);

		totalClasses.setFont(new Font("Tahoma", Font.BOLD, 25));
		GridBagConstraints gbc_lblNewLabel_2_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_2_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2_1_1.gridx = 1;
		gbc_lblNewLabel_2_1_1.gridy = 6;
		panel2.add(totalClasses, gbc_lblNewLabel_2_1_1);

		JLabel methods_1 = new JLabel("Total #Methods");
		GridBagConstraints gbc_methods_1 = new GridBagConstraints();
		gbc_methods_1.insets = new Insets(0, 0, 5, 5);
		gbc_methods_1.gridx = 1;
		gbc_methods_1.gridy = 8;
		panel2.add(methods_1, gbc_methods_1);

		totalMethods.setFont(new Font("Tahoma", Font.BOLD, 25));
		GridBagConstraints gbc_lblNewLabel_2_2_1 = new GridBagConstraints();
		gbc_lblNewLabel_2_2_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2_2_1.gridx = 1;
		gbc_lblNewLabel_2_2_1.gridy = 9;
		panel2.add(totalMethods, gbc_lblNewLabel_2_2_1);

		JLabel codelines_1 = new JLabel("Total #Codelines");
		GridBagConstraints gbc_codelines_1 = new GridBagConstraints();
		gbc_codelines_1.insets = new Insets(0, 0, 5, 5);
		gbc_codelines_1.gridx = 1;
		gbc_codelines_1.gridy = 11;
		panel2.add(codelines_1, gbc_codelines_1);

		totalLOC.setFont(new Font("Tahoma", Font.BOLD, 25));
		GridBagConstraints gbc_lblNewLabel_2_2_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_2_2_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2_2_1_1.gridx = 1;
		gbc_lblNewLabel_2_2_1_1.gridy = 12;
		panel2.add(totalLOC, gbc_lblNewLabel_2_2_1_1);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frame.getContentPane().add(panel_3, "cell 1 1,grow");
		panel_3.setLayout(new MigLayout("", "[224px,grow]", "[480px,grow]"));

		JSplitPane splitPane = new JSplitPane();
		splitPane.setContinuousLayout(true);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		panel_3.add(splitPane, "cell 0 0,grow");

		JScrollPane scrollPane = new JScrollPane();
		this.scrollPane = scrollPane;
		this.scrollPane.setMinimumSize(new Dimension(0, 315));
		splitPane.setLeftComponent(scrollPane);

//		JTree tree = new JTree();
//		this.tree = tree;
//		scrollPane.setViewportView(this.tree);

		JPanel layeredPane = new JPanel();
		layeredPane.setMaximumSize(new Dimension(0, 315));

		layeredPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		splitPane.setRightComponent(layeredPane);
		layeredPane.setLayout(new MigLayout("", "[200][200]", "[][][10.00][][10.00][][10.00][][15.00][][]"));

		
		lblNewLabel_1_3_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_3_1.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(lblNewLabel_1_3_1, "cell 0 0,alignx center,aligny center");

		methodNameLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		methodNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(methodNameLabel, "cell 1 0,alignx center,aligny center");

		JLabel lblNewLabel_1_3 = new JLabel("#Methods");
		lblNewLabel_1_3.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(lblNewLabel_1_3, "cell 0 1,alignx center,aligny center");

		JLabel lblNewLabel_1_1_2 = new JLabel("#Codelines");
		lblNewLabel_1_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(lblNewLabel_1_1_2, "flowx,cell 1 1,alignx center,aligny center");

		JLabel lblNewLabel_12 = new JLabel("");
		lblNewLabel_12.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(lblNewLabel_12, "cell 1 1,alignx center,aligny center");

		
		lblNewLabel_2_2_1_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_2_1_1_2.setFont(new Font("Tahoma", Font.BOLD, 25));
		layeredPane.add(lblNewLabel_2_2_1_1_2, "cell 0 2,alignx center,aligny center");

		methodCodeLinesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		methodCodeLinesLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		layeredPane.add(methodCodeLinesLabel, "cell 1 2,alignx center,aligny center");

		JLabel lblNewLabel_1_1_1 = new JLabel("#Codelines");
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(lblNewLabel_1_1_1, "cell 0 3,alignx center,aligny center");

		JLabel lblNewLabel_1_1 = new JLabel("Cycles");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(lblNewLabel_1_1, "cell 1 3,alignx center,aligny center");

		
		lblNewLabel_2_2_1_1_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_2_1_1_4.setFont(new Font("Tahoma", Font.BOLD, 25));
		layeredPane.add(lblNewLabel_2_2_1_1_4, "cell 0 4,alignx center,aligny center");
		

		methodCyclesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		methodCyclesLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		layeredPane.add(methodCyclesLabel, "cell 1 4,alignx center,aligny center");

		JLabel lblNewLabel_1_2_1 = new JLabel("Complexity");
		lblNewLabel_1_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(lblNewLabel_1_2_1, "cell 0 5,alignx center,aligny center");

		
		lblNewLabel_2_2_1_1_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_2_1_1_6.setFont(new Font("Tahoma", Font.BOLD, 25));
		layeredPane.add(lblNewLabel_2_2_1_1_6, "cell 0 6,alignx center,aligny center");
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.LIGHT_GRAY);
		layeredPane.add(separator, "cell 0 7 2 1,grow");

		JLabel lblNewLabel_1_2_2_1 = new JLabel("GodClass");
		lblNewLabel_1_2_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(lblNewLabel_1_2_2_1, "cell 0 9,alignx center,aligny center");
		
				JLabel lblNewLabel_1_2 = new JLabel("Long");
				lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.CENTER);
				layeredPane.add(lblNewLabel_1_2, "cell 1 9,alignx center,aligny center");

		
		lblNewLabel_2_2_1_1_8.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_2_1_1_8.setFont(new Font("Tahoma", Font.BOLD, 25));
		layeredPane.add(lblNewLabel_2_2_1_1_8, "cell 0 10,alignx center,aligny center");
		
		methodIsLongLabel.setHorizontalAlignment(SwingConstants.CENTER);
		methodIsLongLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		layeredPane.add(methodIsLongLabel, "cell 1 10,alignx center,aligny center");

		JPanel panel_12 = new JPanel();
		panel_12.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frame.getContentPane().add(panel_12, "cell 2 1,grow");
		GridBagLayout gbl_panel_12 = new GridBagLayout();
		gbl_panel_12.columnWidths = new int[] { 25, 175, 25, 0 };
		gbl_panel_12.rowHeights = new int[] { 14, 100, 0, 76, 0, 0, 76, 0, 0, 76, 0, 0, 76, 0, 0, 0, 0, 0, 0 };
		gbl_panel_12.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_12.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_12.setLayout(gbl_panel_12);

		JLabel lblIndicators = new JLabel("Indicators");
		lblIndicators.setFont(new Font("Tahoma", Font.BOLD, 20));
		GridBagConstraints gbc_lblIndicators = new GridBagConstraints();
		gbc_lblIndicators.anchor = GridBagConstraints.NORTH;
		gbc_lblIndicators.insets = new Insets(0, 0, 5, 5);
		gbc_lblIndicators.gridx = 1;
		gbc_lblIndicators.gridy = 1;
		panel_12.add(lblIndicators, gbc_lblIndicators);

		JLabel lblFalsePositives = new JLabel("True positives");
		GridBagConstraints gbc_lblFalsePositives = new GridBagConstraints();
		gbc_lblFalsePositives.anchor = GridBagConstraints.NORTH;
		gbc_lblFalsePositives.insets = new Insets(0, 0, 5, 5);
		gbc_lblFalsePositives.gridx = 1;
		gbc_lblFalsePositives.gridy = 2;
		panel_12.add(lblFalsePositives, gbc_lblFalsePositives);

		JLabel lblNewLabel_2_3_1 = new JLabel("50");
		lblNewLabel_2_3_1.setFont(new Font("Tahoma", Font.BOLD, 25));
		GridBagConstraints gbc_lblNewLabel_2_3_1 = new GridBagConstraints();
		gbc_lblNewLabel_2_3_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2_3_1.gridx = 1;
		gbc_lblNewLabel_2_3_1.gridy = 3;
		panel_12.add(lblNewLabel_2_3_1, gbc_lblNewLabel_2_3_1);

		JLabel lblTrueNegatives = new JLabel("True negatives");
		GridBagConstraints gbc_lblTrueNegatives = new GridBagConstraints();
		gbc_lblTrueNegatives.insets = new Insets(0, 0, 5, 5);
		gbc_lblTrueNegatives.gridx = 1;
		gbc_lblTrueNegatives.gridy = 5;
		panel_12.add(lblTrueNegatives, gbc_lblTrueNegatives);

		JLabel lblNewLabel_2_1_1_1 = new JLabel("50");
		lblNewLabel_2_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 25));
		GridBagConstraints gbc_lblNewLabel_2_1_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_2_1_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2_1_1_1.gridx = 1;
		gbc_lblNewLabel_2_1_1_1.gridy = 6;
		panel_12.add(lblNewLabel_2_1_1_1, gbc_lblNewLabel_2_1_1_1);

		JLabel methods_1_1 = new JLabel("False positives");
		GridBagConstraints gbc_methods_1_1 = new GridBagConstraints();
		gbc_methods_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_methods_1_1.gridx = 1;
		gbc_methods_1_1.gridy = 8;
		panel_12.add(methods_1_1, gbc_methods_1_1);

		JLabel lblNewLabel_2_2_1_2 = new JLabel("50");
		lblNewLabel_2_2_1_2.setFont(new Font("Tahoma", Font.BOLD, 25));
		GridBagConstraints gbc_lblNewLabel_2_2_1_2 = new GridBagConstraints();
		gbc_lblNewLabel_2_2_1_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2_2_1_2.gridx = 1;
		gbc_lblNewLabel_2_2_1_2.gridy = 9;
		panel_12.add(lblNewLabel_2_2_1_2, gbc_lblNewLabel_2_2_1_2);

		JLabel codelines_1_1 = new JLabel("False negatives");
		GridBagConstraints gbc_codelines_1_1 = new GridBagConstraints();
		gbc_codelines_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_codelines_1_1.gridx = 1;
		gbc_codelines_1_1.gridy = 11;
		panel_12.add(codelines_1_1, gbc_codelines_1_1);

		JLabel lblNewLabel_2_2_1_1_1 = new JLabel("50");
		lblNewLabel_2_2_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 25));
		GridBagConstraints gbc_lblNewLabel_2_2_1_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_2_2_1_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2_2_1_1_1.gridx = 1;
		gbc_lblNewLabel_2_2_1_1_1.gridy = 12;
		panel_12.add(lblNewLabel_2_2_1_1_1, gbc_lblNewLabel_2_2_1_1_1);
		
	}

	private ArrayList<ArrayList<String>> readTxt() {
		try {
			ArrayList<ArrayList<String>> rules = new ArrayList<ArrayList<String>>();
			File myObj = new File("Rules.txt");
			if (myObj.exists()) {
				Scanner myReader = new Scanner(myObj);
				ArrayList<String> info = new ArrayList<String>();
				while (myReader.hasNextLine()) {
					String data = myReader.nextLine();
					String[] dataDivided = data.split(" ");
					if (dataDivided[0].equals("LOC") && dataDivided[2].equals("Method")) {
						if (dataDivided.length <= 4) {
							info.add("0");
						} else {
							info.add(dataDivided[4]);
						}
						if (dataDivided.length <= 5) {
							info.add("---");
						} else {
							info.add(dataDivided[5]);
						}
					}
					if (dataDivided[0].equals("CYCLO")) {
						if (dataDivided.length <= 4) {
							info.add("0");
						} else {
							info.add(dataDivided[4]);
						}
					}
					if (dataDivided[0].equals("NOM")) {
						if (dataDivided.length <= 4) {
							info.add("0");
						} else {
							info.add(dataDivided[4]);
						}
						if (dataDivided.length <= 5) {
							info.add("---");
						} else {
							info.add(dataDivided[5]);
						}
					}
					if (dataDivided[0].equals("LOC") && dataDivided[2].equals("Class")) {
						if (dataDivided.length <= 4) {
							info.add("0");
						} else {
							info.add(dataDivided[4]);
						}
						if (dataDivided.length <= 5) {
							info.add("---");
						} else {
							info.add(dataDivided[5]);
						}
					}
					if (dataDivided[0].equals("WMC")) {
						if (dataDivided.length <= 4) {
							info.add("0");
						} else {
							info.add(dataDivided[4]);
						}
					}
					if (dataDivided[0].equals("/////")) {
						rules.add(info);
						info = new ArrayList<String>();
					}
				}
				myReader.close();
			}
			return rules;
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return null;
	}

	private String getNumberOfRulesTxt() throws FileNotFoundException {
		String i = null;
		File myObj = new File("Rules.txt");
		if (myObj.exists()) {
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				String[] dataDivided = data.split(" ");
				if (dataDivided[0].equals("Rule")) {
					i = dataDivided[1];
				}
			}
			myReader.close();
		}
		return i;
	}

}
