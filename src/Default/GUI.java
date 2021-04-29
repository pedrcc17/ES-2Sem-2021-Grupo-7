package Default;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import net.miginfocom.swing.MigLayout;

public class GUI {

	private JFrame frame;
	private ExcelAPI excelAPI;
	
	private JScrollPane scrollPane;
	private JTree tree;

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
			JTree tree = new JTree();
			tree.setModel(excelAPI.readExcel());
			scrollPane.setViewportView(tree);
		}
		scrollPane.updateUI();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 650, 714);
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
				ExcelAPI excelAPI = new ExcelAPI();
				excelAPI.setFileToRead(chooser.getSelectedFile());
				setExcelAPI(excelAPI);
				try {
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
				final JFrame parent = new JFrame();
		        JButton button = new JButton();

		        button.setText("Click me to show dialog!");
		        parent.add(button);
		        parent.pack();
		        parent.setVisible(true);
				String name = JOptionPane.showInputDialog(parent, "What is your name?", null);

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
				.setLayout(new MigLayout("", "[203px][246.00px,grow][144.00px,grow]", "[42px][480px,grow]"));

		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, "cell 0 0 3 1,grow");

		JLabel lblNewLabel = new JLabel("Project Name");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		panel_2.add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gbl_panel = new GridBagLayout();

		gbl_panel.columnWidths = new int[] { 40, 120, 38, 0 };
		gbl_panel.rowHeights = new int[] { 14, 0, 0, 25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);
		frame.getContentPane().add(panel, "cell 0 1,alignx left,growy");

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

		JLabel lblNewLabel_2_3 = new JLabel("50");
		lblNewLabel_2_3.setFont(new Font("Tahoma", Font.BOLD, 25));
		GridBagConstraints gbc_lblNewLabel_2_3 = new GridBagConstraints();
		gbc_lblNewLabel_2_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2_3.gridx = 1;
		gbc_lblNewLabel_2_3.gridy = 3;
		panel.add(lblNewLabel_2_3, gbc_lblNewLabel_2_3);

		JLabel classes_1 = new JLabel("Total #Classes");
		GridBagConstraints gbc_classes_1 = new GridBagConstraints();
		gbc_classes_1.insets = new Insets(0, 0, 5, 5);
		gbc_classes_1.gridx = 1;
		gbc_classes_1.gridy = 5;
		panel.add(classes_1, gbc_classes_1);

		JLabel lblNewLabel_2_1_1 = new JLabel("50");
		lblNewLabel_2_1_1.setFont(new Font("Tahoma", Font.BOLD, 25));
		GridBagConstraints gbc_lblNewLabel_2_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_2_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2_1_1.gridx = 1;
		gbc_lblNewLabel_2_1_1.gridy = 6;
		panel.add(lblNewLabel_2_1_1, gbc_lblNewLabel_2_1_1);

		JLabel methods_1 = new JLabel("Total #Methods");
		GridBagConstraints gbc_methods_1 = new GridBagConstraints();
		gbc_methods_1.insets = new Insets(0, 0, 5, 5);
		gbc_methods_1.gridx = 1;
		gbc_methods_1.gridy = 8;
		panel.add(methods_1, gbc_methods_1);

		JLabel lblNewLabel_2_2_1 = new JLabel("50");
		lblNewLabel_2_2_1.setFont(new Font("Tahoma", Font.BOLD, 25));
		GridBagConstraints gbc_lblNewLabel_2_2_1 = new GridBagConstraints();
		gbc_lblNewLabel_2_2_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2_2_1.gridx = 1;
		gbc_lblNewLabel_2_2_1.gridy = 9;
		panel.add(lblNewLabel_2_2_1, gbc_lblNewLabel_2_2_1);

		JLabel codelines_1 = new JLabel("Total #Codelines");
		GridBagConstraints gbc_codelines_1 = new GridBagConstraints();
		gbc_codelines_1.insets = new Insets(0, 0, 5, 5);
		gbc_codelines_1.gridx = 1;
		gbc_codelines_1.gridy = 11;
		panel.add(codelines_1, gbc_codelines_1);

		JLabel lblNewLabel_2_2_1_1 = new JLabel("50");
		lblNewLabel_2_2_1_1.setFont(new Font("Tahoma", Font.BOLD, 25));
		GridBagConstraints gbc_lblNewLabel_2_2_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_2_2_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2_2_1_1.gridx = 1;
		gbc_lblNewLabel_2_2_1_1.gridy = 12;
		panel.add(lblNewLabel_2_2_1_1, gbc_lblNewLabel_2_2_1_1);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frame.getContentPane().add(panel_3, "cell 1 1,grow");
		panel_3.setLayout(new MigLayout("", "[224px,grow]", "[480px,grow]"));

		JSplitPane splitPane = new JSplitPane();
		splitPane.setToolTipText("");
		splitPane.setContinuousLayout(true);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		panel_3.add(splitPane, "cell 0 0,grow");

		JScrollPane scrollPane = new JScrollPane();
		this.scrollPane = scrollPane;
		splitPane.setLeftComponent(this.scrollPane);

//		JTree tree = new JTree();
//		this.tree = tree;
//		scrollPane.setViewportView(this.tree);

		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		splitPane.setRightComponent(layeredPane);
		layeredPane.setLayout(new MigLayout("", "[100.00][100.00]", "[][][10.00][][10.00][][10.00][][]"));

		JLabel lblNewLabel_1_3_1 = new JLabel("Classname");
		lblNewLabel_1_3_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_3_1.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(lblNewLabel_1_3_1, "cell 0 0,alignx center,aligny center");

		JLabel lblNewLabel_1_3_2 = new JLabel("Methodname");
		lblNewLabel_1_3_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_3_2.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(lblNewLabel_1_3_2, "cell 1 0,alignx center,aligny center");

		JLabel lblNewLabel_1_3 = new JLabel("#Methods");
		lblNewLabel_1_3.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(lblNewLabel_1_3, "cell 0 1,alignx center,aligny center");

		JLabel lblNewLabel_1_1_2 = new JLabel("#Codelines");
		lblNewLabel_1_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(lblNewLabel_1_1_2, "flowx,cell 1 1,alignx center,aligny center");

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(lblNewLabel_1, "cell 1 1,alignx center,aligny center");

		JLabel lblNewLabel_2_2_1_1_2 = new JLabel("50");
		lblNewLabel_2_2_1_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_2_1_1_2.setFont(new Font("Tahoma", Font.BOLD, 25));
		layeredPane.add(lblNewLabel_2_2_1_1_2, "cell 0 2,alignx center,aligny center");

		JLabel lblNewLabel_2_2_1_1_3 = new JLabel("50");
		lblNewLabel_2_2_1_1_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_2_1_1_3.setFont(new Font("Tahoma", Font.BOLD, 25));
		layeredPane.add(lblNewLabel_2_2_1_1_3, "cell 1 2,alignx center,aligny center");

		JLabel lblNewLabel_1_1_1 = new JLabel("#Codelines");
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(lblNewLabel_1_1_1, "cell 0 3,alignx center,aligny center");

		JLabel lblNewLabel_1_1 = new JLabel("Cycles");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(lblNewLabel_1_1, "cell 1 3,alignx center,aligny center");

		JLabel lblNewLabel_2_2_1_1_4 = new JLabel("50");
		lblNewLabel_2_2_1_1_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_2_1_1_4.setFont(new Font("Tahoma", Font.BOLD, 25));
		layeredPane.add(lblNewLabel_2_2_1_1_4, "cell 0 4,alignx center,aligny center");

		JLabel lblNewLabel_2_2_1_1_5 = new JLabel("50");
		lblNewLabel_2_2_1_1_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_2_1_1_5.setFont(new Font("Tahoma", Font.BOLD, 25));
		layeredPane.add(lblNewLabel_2_2_1_1_5, "cell 1 4,alignx center,aligny center");

		JLabel lblNewLabel_1_2_1 = new JLabel("Complexity");
		lblNewLabel_1_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(lblNewLabel_1_2_1, "cell 0 5,alignx center,aligny center");

		JLabel lblNewLabel_1_2 = new JLabel("Long");
		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(lblNewLabel_1_2, "cell 1 5,alignx center,aligny center");

		JLabel lblNewLabel_2_2_1_1_6 = new JLabel("50");
		lblNewLabel_2_2_1_1_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_2_1_1_6.setFont(new Font("Tahoma", Font.BOLD, 25));
		layeredPane.add(lblNewLabel_2_2_1_1_6, "cell 0 6,alignx center,aligny center");

		JLabel lblNewLabel_2_2_1_1_7 = new JLabel("True");
		lblNewLabel_2_2_1_1_7.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_2_1_1_7.setFont(new Font("Tahoma", Font.BOLD, 25));
		layeredPane.add(lblNewLabel_2_2_1_1_7, "cell 1 6,alignx center,aligny center");

		JLabel lblNewLabel_1_2_2_1 = new JLabel("GodClass");
		lblNewLabel_1_2_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(lblNewLabel_1_2_2_1, "cell 0 7,alignx center,aligny center");

		JLabel lblNewLabel_2_2_1_1_8 = new JLabel("False");
		lblNewLabel_2_2_1_1_8.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_2_1_1_8.setFont(new Font("Tahoma", Font.BOLD, 25));
		layeredPane.add(lblNewLabel_2_2_1_1_8, "cell 0 8,alignx center,aligny center");

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frame.getContentPane().add(panel_1, "cell 2 1,grow");
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 40, 120, 38, 0 };
		gbl_panel_1.rowHeights = new int[] { 14, 0, 0, 25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
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
}
