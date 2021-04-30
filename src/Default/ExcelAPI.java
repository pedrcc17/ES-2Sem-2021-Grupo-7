package Default;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sun.source.tree.Tree;

public class ExcelAPI {

	private static XSSFWorkbook workbook = new XSSFWorkbook();
	private static XSSFSheet spreadsheet = workbook.createSheet("Code Smells");
	private static XSSFRow row;
	private static Map<Integer, Object[]> metrics;
	private int id;
	private String className;
	private String packageName;
	private XSSFFont boldfont;
	private File fileToRead;

	public ExcelAPI() {
		id = 00;
		metrics = new TreeMap<Integer, Object[]>();
		boldfont = workbook.createFont();
		boldfont.setBold(true);
		metrics.put(00, new Object[] { "MethodID", "package", "class", "method", "NOM_class", "LOC_class", "WMC_class",
				"is_God_Class", "LOC_method", "CYCLO_method", "is_Long_Method" });
	}

	public void saveMetrics(SmellyClass classy) {
		for (int i = 0; i < classy.getMethod(); i++) {
			id++;
			metrics.put(id, new Object[] { Integer.toString(id),
					packageName.substring(packageName.lastIndexOf("\\") + 1), className, classy.getMethodNames(i),
					Integer.toString(classy.getMethod()), Integer.toString(classy.getLinesOfCode()),
					Integer.toString(classy.getWmcCount()), Boolean.toString(classy.getisGodClass()).toUpperCase(),
					Integer.toString(classy.getLinesPerMethod(i)), Integer.toString(classy.getCyclosPerMethod(i)),
					Boolean.toString(classy.getAreLongMethods(i)).toUpperCase(), });
		}
	}

	public void exportToExcel(String name) throws IOException {
		CellStyle hStyle = workbook.createCellStyle();
		Set<Integer> keyid = metrics.keySet();
		int rowid = 0;

		for (Integer key : keyid) {
			row = spreadsheet.createRow(rowid++);
			Object[] objectArr = metrics.get(key);
			int cellid = 0;

			for (Object obj : objectArr) {
				Cell cell = row.createCell(cellid++);
				cell.setCellValue((String) obj);
				if (key == 0) {
					hStyle.setFont(boldfont);
					cell.setCellStyle(hStyle);
				}
			}
		}
		FileOutputStream out = new FileOutputStream(new File("Excel Files/" + name + ".xlsx"));

		workbook.write(out);
		out.close();
		System.out.println(name + ".xlsx written successfully");
	}

	public void setClassAndPackageNames(String file, String filePathString) {
		this.className = file;
		this.packageName = filePathString;
	}

	public String chooseName() {
		String name = null;
		while (name == null) {
			name = (String) JOptionPane.showInputDialog(null, "Choose Excel file name", JOptionPane.PLAIN_MESSAGE);
		}
		return name;
	}

	public void getExcelDataAsMap() throws IOException, InvalidFormatException {
		workbook = new XSSFWorkbook(fileToRead);
		spreadsheet = workbook.getSheet("Code Smells");
		int countOfRows = spreadsheet.getLastRowNum();

		for (int i = 01; i < countOfRows; i++) {
			row = spreadsheet.getRow(i);
			if (row.getLastCellNum() < 12) {
				metrics.put(i,
						new Object[] { Integer.toString(i), row.getCell(1).getStringCellValue(),
								row.getCell(2).getStringCellValue(), row.getCell(3).getStringCellValue(),
								row.getCell(4).getStringCellValue(), row.getCell(5).getStringCellValue(),
								row.getCell(6).getStringCellValue(), row.getCell(7).getStringCellValue(),
								row.getCell(8).getStringCellValue(), row.getCell(9).getStringCellValue(),
								row.getCell(10).getStringCellValue(), });
			}
		}
	}

	public String findInMap(int line, int column) {
		Object[] temp = metrics.get(line);
		return (String) temp[column];
	}

	// helping with small methods

	public String findMethodID(int line) {
		Object[] temp = metrics.get(line);
		return (String) temp[0];
	}

	public String findPackageName(int line) {
		Object[] temp = metrics.get(line);
		return (String) temp[1];
	}

	public String findClassName(int line) {
		Object[] temp = metrics.get(line);
		return (String) temp[2];
	}

	public String findMethodName(int line) {
		Object[] temp = metrics.get(line);
		return (String) temp[3];
	}

	public String findNOM_Class(int line) {
		Object[] temp = metrics.get(line);
		return (String) temp[4];
	}

	public String findLOC_Class(int line) {
		Object[] temp = metrics.get(line);
		return (String) temp[5];
	}

	public String findWMC_Class(int line) {
		Object[] temp = metrics.get(line);
		return (String) temp[6];
	}

	public String findGodClass(int line) {
		Object[] temp = metrics.get(line);
		return (String) temp[7];
	}

	public String findLOC_Method(int line) {
		Object[] temp = metrics.get(line);
		return (String) temp[8];
	}

	public String findCYCLO_Method(int line) {
		Object[] temp = metrics.get(line);
		return (String) temp[9];
	}

	public String findIsLongMethod(int line) {
		Object[] temp = metrics.get(line);
		return (String) temp[10];
	}

	public void setFileToRead(File file) {
		this.fileToRead = file;
	}

	public void removeFileToRead() {
		this.fileToRead = null;
	}

	public String getFileToRead() {
		return this.fileToRead.toString();
	}

	public DefaultTreeModel readExcel() throws Exception, IOException {
		getExcelDataAsMap();
		String name = getFileToRead().replace("\\", " ");
		String[] myFile = name.split(" ");
		String myFileName = myFile[myFile.length - 1];
		DefaultTreeModel treeModel = new DefaultTreeModel(
				new DefaultMutableTreeNode(myFileName) {
					private static final long serialVersionUID = 1L;
					{
						DefaultMutableTreeNode node_1 = null;
						DefaultMutableTreeNode node_11 = null;
						boolean addedPackage = false;
						String packageName = findPackageName(1);
						boolean addedClass = false;
						String className = findClassName(1);
						for (int i = 1; i < metrics.size(); i++) {
							String newPackageName = findPackageName(i);
							String newClassName = findClassName(i);
							if ((packageName != newPackageName) || !addedPackage) {
								{
									node_1 = new DefaultMutableTreeNode(newPackageName);
									addedPackage=true;
									packageName = newPackageName;
								}
							}
							if ((className != newClassName) || !addedClass) {
								node_11 = new DefaultMutableTreeNode(findClassName(i));
								addedClass=true;
								className = newClassName;
							}
								node_1.add(node_11);
								node_11.add(new DefaultMutableTreeNode(findMethodName(i)));
								add(node_1);
						}
					}
				});

		return treeModel;

	}
	
	public ArrayList<String> readExcelTotals() throws Exception, IOException {
		getExcelDataAsMap();
		ArrayList<String> packagesList = new ArrayList<String>();
		ArrayList<String> classesList = new ArrayList<String>();
		ArrayList<Integer> LOCList = new ArrayList<Integer>();

		for (int i = 1; i < metrics.size(); i++) {
		packagesList.add(findPackageName(i));
		classesList.add(findClassName(i));
		LOCList.add(Integer.parseInt(findLOC_Method(i)));
		
		}
		Set<String> packagesset = new HashSet<>(packagesList);
		Set<String> classesset = new HashSet<>(classesList);

		packagesList.clear();
		packagesList.addAll(packagesset);
		classesList.clear();
		classesList.addAll(classesset);
		
		String total_packages = String.valueOf(packagesList.size());
		String total_classes = String.valueOf(classesList.size());
		String total_methods = String.valueOf(metrics.size());
		String total_LOC = String.valueOf(LOCList.stream().mapToInt(Integer::intValue).sum());
		ArrayList<String> totalsList = new ArrayList<String>();
		totalsList.add(total_packages);
		totalsList.add(total_classes);
		totalsList.add(total_methods);
		totalsList.add(total_LOC);
		return totalsList;
	}

	public static String getCellValue(Cell cell) {
		if (cell.getCellType().toString().equals("CELL_TYPE_NUMERIC")) {
			return cell.getNumericCellValue() + "\t\t\t";
		}
		if (cell.getCellType().toString().equals("CELL_TYPE_STRING")) {
			return cell.getStringCellValue() + "\t\t\t";
		}
		return "";
	}

}