package Default;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import net.miginfocom.swing.MigLayout;

public class GUI {

	private JFrame frame;
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
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

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
	
	private void updateWindow() throws Exception, Exception {
		if(excelAPI!=null) {
			tree = new JTree();
			tree.setModel(excelAPI.readExcel());
			tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			
			tree.addTreeSelectionListener(new TreeSelectionListener() {
			    public void valueChanged(TreeSelectionEvent e) {
			        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
			                           tree.getLastSelectedPathComponent();

			        if (node == null) { 
			        	return;
			        } else if (node.isLeaf()){
			        	try {
							Map<String, String[]> methods = excelAPI.getExcelMethods();
							String[] info = methods.get(node.toString());
							updateLabel(methodCodeLinesLabel,info[0]);
							updateLabel(methodNameLabel, node.toString());
							updateLabel(methodCyclesLabel,info[1]);
							updateLabel(methodIsLongLabel, info[2]);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			        } else {
			        	Object nodeInfo = node.getUserObject();
			       
			        	if(nodeInfo.toString().contains(".java")) {
			        		updateLabel(lblNewLabel_1_3_1, nodeInfo.toString());
			        		excelAPI.findClassSmellsByName(nodeInfo.toString());
			        		updateLabel(lblNewLabel_2_2_1_1_2,excelAPI.answers.get(0));
			        		updateLabel(lblNewLabel_2_2_1_1_4,excelAPI.answers.get(1));
			        		updateLabel(lblNewLabel_2_2_1_1_6,excelAPI.answers.get(2));
			        		updateLabel(lblNewLabel_2_2_1_1_8,excelAPI.answers.get(2));
			        }
			        
			    }
			}});
			//ExcelAPI a = new ExcelAPI(); 
			
			//System.out.println(excelAPI.answers.get(0));
			
			//updateLabel(lblNewLabel_2_2_1_1_2 ,a.answers.get(0));
			scrollPane.setViewportView(tree);
		}
		scrollPane.updateUI();
	}
	
	private void updateLabels() {
		updateLabel(totalPackages,TotalsList.get(0));
		updateLabel(totalClasses,TotalsList.get(1));
		updateLabel(totalMethods,TotalsList.get(2));
		updateLabel(totalLOC,TotalsList.get(3));
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

		i1 = new JMenuItem("Open Project");
		i2 = new JMenuItem("Open Metrics File");
		i2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser(".");
				chooser.setMultiSelectionEnabled(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files", "xlsx", "excel");
				chooser.setFileFilter(filter);
				chooser.showOpenDialog(null);
				excelAPI = new ExcelAPI();
				excelAPI.setFileToRead(chooser.getSelectedFile());
				setExcelAPI(excelAPI);
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
		
		i3 = new JMenuItem("Save Metrics");
		i4 = new JMenuItem("Create new Rule");
		i4.addActionListener(new ActionListener() {
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
						try {
							File ruleFile = new File("Rules.txt");
							if (ruleFile.createNewFile()) {
								System.out.println("Rule file created: " + ruleFile.getName());
							}
							FileWriter outstream = new FileWriter(ruleFile, true);
							outstream.write("Long Method :\n");
							if(valueLocMethod != null) {
								outstream.write(locMethod.getText()  + " " + valueLocMethod.getText() + " " + methodOp.getSelectedItem() + "\n");
							}
							if(valueCycloMethod != null) {
								outstream.write(cycloMethod.getText()  + " " + valueCycloMethod.getText() + "\n");
							}
							outstream.write("God Class:\n");
							if(valueNomClass != null) {
								outstream.write(nomClass.getText()  + " " + valueNomClass.getText() + " " + classOpOne.getSelectedItem() + "\n");
							}
							if(valueLocClass != null) {
								outstream.write(locClass.getText()  + " " + valueLocClass.getText() + " " + classOpTwo.getSelectedItem() + "\n");
							}
							if(valueWmcClass != null) {
								outstream.write(wmcClass.getText()  + " " + valueWmcClass.getText() + "\n");
							}
							outstream.write("/////");
							outstream.close();
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
		i5 = new JMenuItem("Change Rules");
		i6 = new JMenuItem("Save Rules");
		i7 = new JMenuItem("Load Rules");
		i8 = new JMenuItem("History");

		File.add(i1);
		File.add(i2);
		File.add(i3);

		Rules.add(i4);
		Rules.add(i5);
		Rules.add(i6);
		Rules.add(i7);
		Rules.add(i8);

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(File);
		menuBar.add(Rules);
		frame.setJMenuBar(menuBar);
		frame.getContentPane()
				.setLayout(new MigLayout("", "[225.00px][400.00px][225.00px,grow]", "[42px][480px,grow]"));

		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, "cell 0 0 3 1,grow");

		JLabel lblNewLabel = new JLabel("Project Name");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		panel_2.add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gbl_panel = new GridBagLayout();

		gbl_panel.columnWidths = new int[] { 25, 175, 25, 0 };
		gbl_panel.rowHeights = new int[] { 14, 100, 0, 76, 0, 0, 76, 0, 0, 76, 0, 0, 76, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);
		frame.getContentPane().add(panel, "cell 0 1,grow");

		JLabel Geral = new JLabel("Geral");
		Geral.setFont(new Font("Tahoma", Font.BOLD, 20));

		GridBagConstraints gbc_Geral = new GridBagConstraints();
		gbc_Geral.anchor = GridBagConstraints.NORTH;
		gbc_Geral.insets = new Insets(0, 0, 5, 5);
		gbc_Geral.gridx = 1;
		gbc_Geral.gridy = 1;
		panel.add(Geral, gbc_Geral);
		JLabel packages = new JLabel("Total #Packages");
		GridBagConstraints gbc_packages = new GridBagConstraints();
		gbc_packages.anchor = GridBagConstraints.NORTH;
		gbc_packages.insets = new Insets(0, 0, 5, 5);
		gbc_packages.gridx = 1;
		gbc_packages.gridy = 2;
		panel.add(packages, gbc_packages);

		totalPackages.setFont(new Font("Tahoma", Font.BOLD, 25));
		GridBagConstraints gbc_lblNewLabel_2_3 = new GridBagConstraints();
		gbc_lblNewLabel_2_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2_3.gridx = 1;
		gbc_lblNewLabel_2_3.gridy = 3;
		panel.add(totalPackages, gbc_lblNewLabel_2_3);

		JLabel classes_1 = new JLabel("Total #Classes");
		GridBagConstraints gbc_classes_1 = new GridBagConstraints();
		gbc_classes_1.insets = new Insets(0, 0, 5, 5);
		gbc_classes_1.gridx = 1;
		gbc_classes_1.gridy = 5;
		panel.add(classes_1, gbc_classes_1);

		totalClasses.setFont(new Font("Tahoma", Font.BOLD, 25));
		GridBagConstraints gbc_lblNewLabel_2_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_2_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2_1_1.gridx = 1;
		gbc_lblNewLabel_2_1_1.gridy = 6;
		panel.add(totalClasses, gbc_lblNewLabel_2_1_1);

		JLabel methods_1 = new JLabel("Total #Methods");
		GridBagConstraints gbc_methods_1 = new GridBagConstraints();
		gbc_methods_1.insets = new Insets(0, 0, 5, 5);
		gbc_methods_1.gridx = 1;
		gbc_methods_1.gridy = 8;
		panel.add(methods_1, gbc_methods_1);

		totalMethods.setFont(new Font("Tahoma", Font.BOLD, 25));
		GridBagConstraints gbc_lblNewLabel_2_2_1 = new GridBagConstraints();
		gbc_lblNewLabel_2_2_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2_2_1.gridx = 1;
		gbc_lblNewLabel_2_2_1.gridy = 9;
		panel.add(totalMethods, gbc_lblNewLabel_2_2_1);

		JLabel codelines_1 = new JLabel("Total #Codelines");
		GridBagConstraints gbc_codelines_1 = new GridBagConstraints();
		gbc_codelines_1.insets = new Insets(0, 0, 5, 5);
		gbc_codelines_1.gridx = 1;
		gbc_codelines_1.gridy = 11;
		panel.add(codelines_1, gbc_codelines_1);

		totalLOC.setFont(new Font("Tahoma", Font.BOLD, 25));
		GridBagConstraints gbc_lblNewLabel_2_2_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_2_2_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2_2_1_1.gridx = 1;
		gbc_lblNewLabel_2_2_1_1.gridy = 12;
		panel.add(totalLOC, gbc_lblNewLabel_2_2_1_1);

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
		scrollPane.setMinimumSize(new Dimension(0, 315));
		splitPane.setLeftComponent(this.scrollPane);

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

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(lblNewLabel_1, "cell 1 1,alignx center,aligny center");

		
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

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frame.getContentPane().add(panel_1, "cell 2 1,grow");
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 25, 175, 25, 0 };
		gbl_panel_1.rowHeights = new int[] { 14, 100, 0, 76, 0, 0, 76, 0, 0, 76, 0, 0, 76, 0, 0, 0, 0, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		JLabel lblIndicators = new JLabel("Indicators");
		lblIndicators.setFont(new Font("Tahoma", Font.BOLD, 20));
		GridBagConstraints gbc_lblIndicators = new GridBagConstraints();
		gbc_lblIndicators.anchor = GridBagConstraints.NORTH;
		gbc_lblIndicators.insets = new Insets(0, 0, 5, 5);
		gbc_lblIndicators.gridx = 1;
		gbc_lblIndicators.gridy = 1;
		panel_1.add(lblIndicators, gbc_lblIndicators);

		JLabel lblFalsePositives = new JLabel("True positives");
		GridBagConstraints gbc_lblFalsePositives = new GridBagConstraints();
		gbc_lblFalsePositives.anchor = GridBagConstraints.NORTH;
		gbc_lblFalsePositives.insets = new Insets(0, 0, 5, 5);
		gbc_lblFalsePositives.gridx = 1;
		gbc_lblFalsePositives.gridy = 2;
		panel_1.add(lblFalsePositives, gbc_lblFalsePositives);

		JLabel lblNewLabel_2_3_1 = new JLabel("50");
		lblNewLabel_2_3_1.setFont(new Font("Tahoma", Font.BOLD, 25));
		GridBagConstraints gbc_lblNewLabel_2_3_1 = new GridBagConstraints();
		gbc_lblNewLabel_2_3_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2_3_1.gridx = 1;
		gbc_lblNewLabel_2_3_1.gridy = 3;
		panel_1.add(lblNewLabel_2_3_1, gbc_lblNewLabel_2_3_1);

		JLabel lblTrueNegatives = new JLabel("True negatives");
		GridBagConstraints gbc_lblTrueNegatives = new GridBagConstraints();
		gbc_lblTrueNegatives.insets = new Insets(0, 0, 5, 5);
		gbc_lblTrueNegatives.gridx = 1;
		gbc_lblTrueNegatives.gridy = 5;
		panel_1.add(lblTrueNegatives, gbc_lblTrueNegatives);

		JLabel lblNewLabel_2_1_1_1 = new JLabel("50");
		lblNewLabel_2_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 25));
		GridBagConstraints gbc_lblNewLabel_2_1_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_2_1_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2_1_1_1.gridx = 1;
		gbc_lblNewLabel_2_1_1_1.gridy = 6;
		panel_1.add(lblNewLabel_2_1_1_1, gbc_lblNewLabel_2_1_1_1);

		JLabel methods_1_1 = new JLabel("False positives");
		GridBagConstraints gbc_methods_1_1 = new GridBagConstraints();
		gbc_methods_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_methods_1_1.gridx = 1;
		gbc_methods_1_1.gridy = 8;
		panel_1.add(methods_1_1, gbc_methods_1_1);

		JLabel lblNewLabel_2_2_1_2 = new JLabel("50");
		lblNewLabel_2_2_1_2.setFont(new Font("Tahoma", Font.BOLD, 25));
		GridBagConstraints gbc_lblNewLabel_2_2_1_2 = new GridBagConstraints();
		gbc_lblNewLabel_2_2_1_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2_2_1_2.gridx = 1;
		gbc_lblNewLabel_2_2_1_2.gridy = 9;
		panel_1.add(lblNewLabel_2_2_1_2, gbc_lblNewLabel_2_2_1_2);

		JLabel codelines_1_1 = new JLabel("False negatives");
		GridBagConstraints gbc_codelines_1_1 = new GridBagConstraints();
		gbc_codelines_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_codelines_1_1.gridx = 1;
		gbc_codelines_1_1.gridy = 11;
		panel_1.add(codelines_1_1, gbc_codelines_1_1);

		JLabel lblNewLabel_2_2_1_1_1 = new JLabel("50");
		lblNewLabel_2_2_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 25));
		GridBagConstraints gbc_lblNewLabel_2_2_1_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_2_2_1_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2_2_1_1_1.gridx = 1;
		gbc_lblNewLabel_2_2_1_1_1.gridy = 12;
		panel_1.add(lblNewLabel_2_2_1_1_1, gbc_lblNewLabel_2_2_1_1_1);

	}
	
	public void updateLabel(JLabel label, String text) {
		label.setText(text);
		label.paintImmediately(label.getVisibleRect());
		
	}
	
}
