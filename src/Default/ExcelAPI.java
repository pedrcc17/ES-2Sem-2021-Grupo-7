	package Default;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Everything directly related to Excel in the project
 * @author G7 de ES LEI 2020/2021
 *
 */
public class ExcelAPI {
	/** Used to create a Spreadsheet.*/
	private static XSSFWorkbook workbook = new XSSFWorkbook();
	/** Used to create the Excel files.*/
	private static XSSFSheet spreadsheet = workbook.createSheet("Code Smells");
	/** Spreadsheet rows.*/
	private static XSSFRow row;
	/** Map where the metrics are saved while the application is running.*/
	private static Map<Integer, Object[]> metrics;
	/** Index/key for each new addition in the metrics map. */
	private int id;
	/** Class Name */
	private String className;
	/** Package Name */
	private String packageName;
	/** Used to bold text in the exported Excel files.*/
	private XSSFFont boldfont;
	/** File to be used while the application is running. */
	private File fileToRead;
	/** Name for the Excel File */
	private String name;
	/** Array to store true positives, true negatives, false positives and false negatives */
	private int[] quality;
	/** Workbook for specialist */
	private static XSSFWorkbook workbook2 = new XSSFWorkbook();
	/** Spreadsheet for specialist */
	private static XSSFSheet spreadsheet2 = workbook2.createSheet("Code Smells");
	/** Rows for specialist */
	private static XSSFRow row2;
	/** Map of the metrics given by the teacher */
	private static Map<Integer, Object[]> specialists;

	/**
	 * Creates the base ExcelAPI along with the first index of the "metrics" and "specialists" TreeMap where the metrics will be stored.
	 */
	public ExcelAPI() {
		id = 00;
		metrics = new TreeMap<Integer, Object[]>();
		boldfont = workbook.createFont();
		boldfont.setBold(true);
		metrics.put(00, new Object[] { "MethodID", "package", "class", "method", "NOM_class", "LOC_class", "WMC_class",
				"is_God_Class", "LOC_method", "CYCLO_method", "is_Long_Method" });
		
		specialists = new TreeMap<Integer, Object[]>();
		specialists.put(00, new Object[] { "MethodID", "package", "class", "method", "is_God_Class", "is_Long_Method" });
	}
	/**
	 * Saves the metrics of the class given in the TreeMap.
	 * @param classy Class to be read
	 */
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
	/**
	 * Exports the metrics saved in the application to an Excel file.
	 * @param name The desired name for the exported Excel file
	 * @throws IOException
	 */
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
		workbook.close();
		System.out.println(name + ".xlsx written successfully");
	}
	/**
	 * Exports the metrics in the "specialists" attribute to an Excel file with the name as the parameter
	 * @param name Name of the Excel file
	 * @throws IOException
	 */
	public void printSpecialists(String name) throws IOException {
		CellStyle hStyle = workbook2.createCellStyle();
		Set<Integer> keyid = specialists.keySet();
		int rowid = 0;

		for (Integer key : keyid) {
			row2 = spreadsheet2.createRow(rowid++);
			Object[] objectArr = specialists.get(key);
			int cellid = 0;

			for (Object obj : objectArr) {
				Cell cell = row2.createCell(cellid++);
				cell.setCellValue((String) obj);
				if (key == 0) {
					hStyle.setFont(boldfont);
					cell.setCellStyle(hStyle);
				}
			}
		}
		FileOutputStream out = new FileOutputStream(new File("Excel Files/" + name + ".xlsx"));

		workbook2.write(out);
		out.close();
		workbook2.close();
		System.out.println(name + ".xlsx written successfully");
	}
	/**
	 * Sets the "className" and "packageName" class attributes.
	 * @param file className
	 * @param filePathString packageName
	 */
	public void setClassAndPackageNames(String file, String filePathString) {
		this.className = file;
		this.packageName = filePathString;
	}
	/**
	 * Opens an InputDialog for the user to choose the Excel file name.
	 * @return Users input
	 */
	public String chooseName() {
		String name = null;
		while (name == null) {
			name = (String) JOptionPane.showInputDialog(null, "Choose Excel file name", JOptionPane.PLAIN_MESSAGE);
		}
		this.name = name;
		return name;
	}
	/**
	 * Gets the data from the "fileToRead" class attribute and adds it to the "metrics" TreeMap class attribute.
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
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
	/**
	 * Reads the file "Code_Smells.xlsx" and adds it to the "specialists" TreeMap
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public void readCodeSmells() throws IOException, InvalidFormatException {
		workbook = new XSSFWorkbook(new File("Excel Files/Code_Smells.xlsx"));
		spreadsheet = workbook.getSheet("Code Smells");
		int countOfRows = spreadsheet.getLastRowNum();

		for (int i = 01; i < countOfRows; i++) {
			row = spreadsheet.getRow(i);
			if (row.getLastCellNum() < 12) {
				specialists.put(i,
						new Object[] { Integer.toString(i), row.getCell(1).getStringCellValue(),
								row.getCell(2).getStringCellValue(), row.getCell(3).getStringCellValue(),
								row.getCell(7).getBooleanCellValue(),
								row.getCell(10).getRawValue() });
			}
		}
	}
	/**
	 * Gets the value stored in the line and collumn given from the class TreeMap "metrics".
	 * @param line
	 * @param column
	 * @return The value stored
	 */
	public String findInMap(int line, int column) {
		Object[] temp = metrics.get(line);
		return (String) temp[column];
	}

	/**
	 * Gets the "Method ID" value in the specified index of the TreeMap "metrics".
	 * @param line TreeMap key
	 * @return Method ID value
	 */
	public String findMethodID(int line) {
		Object[] temp = metrics.get(line);
		return (String) temp[0];
	}
	/**
	 * Gets the "Package Name" value in the specified index of the TreeMap "metrics".
	 * @param line TreeMap key
	 * @return Package Name value
	 */
	public String findPackageName(int line) {
		Object[] temp = metrics.get(line);
		return (String) temp[1];
	}
	/**
	 * Gets the "Class Name" value in the specified index of the TreeMap "metrics".
	 * @param line TreeMap key
	 * @return Class Name value
	 */
	public String findClassName(int line) {
		Object[] temp = metrics.get(line);
		return (String) temp[2];
	}
	/**
	 * Gets the "Method Name" value in the specified index of the TreeMap "metrics".
	 * @param line TreeMap key
	 * @return Method Name value
	 */
	public String findMethodName(int line) {
		Object[] temp = metrics.get(line);
		return (String) temp[3];
	}
	/**
	 * Gets the "Nom_Class" value in the specified index of the TreeMap "metrics".
	 * @param line TreeMap key
	 * @return Nom_Class value
	 */
	public String findNOM_Class(int line) {
		Object[] temp = metrics.get(line);
		return (String) temp[4];
	}
	/**
	 * Gets the "LOC_Class" value in the specified index of the TreeMap "metrics".
	 * @param line TreeMap key
	 * @return LOC_Class value
	 */
	public String findLOC_Class(int line) {
		Object[] temp = metrics.get(line);
		return (String) temp[5];
	}
	/**
	 * Gets the "WMC_Class" value in the specified index of the TreeMap "metrics".
	 * @param line TreeMap key
	 * @return WMC_Class value
	 */
	public String findWMC_Class(int line) {
		Object[] temp = metrics.get(line);
		return (String) temp[6];
	}
	/**
	 * Gets the "God Class" value in the specified index of the TreeMap "metrics".
	 * @param line TreeMap key
	 * @return God Class value
	 */
	public String findGodClass(int line) {
		Object[] temp = metrics.get(line);
		return (String) temp[7];
	}
	/**
	 * Gets the "LOC_Method" value in the specified index of the TreeMap "metrics".
	 * @param line TreeMap key
	 * @return LOC_Method value
	 */
	public String findLOC_Method(int line) {
		Object[] temp = metrics.get(line);
		return (String) temp[8];
	}
	/**
	 * Gets the "CYCLO_Method" value in the specified index of the TreeMap "metrics".
	 * @param line TreeMap key
	 * @return CYCLO_Method value
	 */
	public String findCYCLO_Method(int line) {
		Object[] temp = metrics.get(line);
		return (String) temp[9];
	}
	/**
	 * Gets the "Long Method" value in the specified index of the TreeMap "metrics".
	 * @param line TreeMap key
	 * @return Long Method value
	 */
	public String findIsLongMethod(int line) {
		Object[] temp = metrics.get(line);
		return (String) temp[10];
	}

	/**
	 * Sets the "fileToRead" class attribute to the file given.
	 * @param file File that is meant to be read
	 */
	public void setFileToRead(File file) {
		this.fileToRead = file;
	}
	/**
	 * Sets the "fileToRead" class attribute to null.
	 */
	public void removeFileToRead() {
		this.fileToRead = null;
	}
	/**
	 * Gets the "fileToRead" class attribute as a String.
	 * @return fileToRead as String
	 */
	public String getFileToRead() {
		return this.fileToRead.toString();
	}
	/**
	 * Getter for "name" class attribute
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * Gets the Method ID value in the specified index of the TreeMap "specialists".
	 * @param line TreeMap key
	 */
	public int findLineSpecialist(int line) {
		Object[] temp = specialists.get(line);
		return (int) temp[0];
	}
	/**
 	 * Gets the Package Name value in the specified index of the TreeMap "specialists".
	 * @param line TreeMap key
	 */
	public String findPackageNameSpecialist(int line) {
		Object[] temp = specialists.get(line);
		return (String) temp[1];
	}
	/**
	 * Gets the Class Name value in the specified index of the TreeMap "specialists".
	 * @param line TreeMap key
	 */
	public String findClassNameSpecialist(int line) {
		Object[] temp = specialists.get(line);
		return (String) temp[2];
	}
	/**
	 * Gets the Method Name value in the specified index of the TreeMap "specialists".
	 * @param line TreeMap key
	 */
	public String findMethodNameSpecialist(int line) {
		Object[] temp = specialists.get(line);
		return (String) temp[3];
	}
	/**
	 * Gets the is_God_Class value in the specified index of the TreeMap "specialists".
	 * @param line TreeMap key
	 */
	public String findGodClassSpecialist(int line) {
		Object[] temp = metrics.get(line);
		return (String) temp[4];
	}
	/**
	 * Gets the is_Long_Method value in the specified index of the TreeMap "specialists".
	 * @param line TreeMap key
	 */
	public String findIsLongMethodSpecialist(int line) {
		Object[] temp = metrics.get(line);
		return (String) temp[5];
	}
	/**
	 * Gets Excel ID in "specialists" TreeMap by giving the package name, class name and method name
	 * @param packageName Package name
	 * @param className Class name
	 * @param methodName Method Name
	 * @return Excel ID
	 */
	public int findIDInSpecialist(String packageName, String className, String methodName) {
		for(int i = 0; i < metrics.size(); i++) {
			if(findPackageNameSpecialist(i).contains(packageName) &&
					className.contains(findClassNameSpecialist(i)) &&
					findMethodNameSpecialist(i).contains(methodName))
			return i;
		}
		return 0;
	}
	/**
	 * Compares the GodClass values in "metrics" and "specialists" attributes to check for True Positives(1), False Positives(2), True Negatives(3), False Negatives(4)
	 * @param project Metrics key
	 * @param specialist Specialist key
	 * @return True Positives(1), False Positives(2), True Negatives(3), False Negatives(4)
	 */
	public int compareGodClassCodeSmells(int project, int specialist) {
		if(specialist == 0) return 0;
		String project_value = findGodClass(project);
		Object specialist_value = findGodClassSpecialist(specialist);
		if(project_value.equals("TRUE") && specialist_value.equals("true"))
			return 1;
		if(project_value.equals("TRUE") && specialist_value.equals("false"))
			return 2;
		if(project_value.equals("FALSE") && specialist_value.equals("false"))
			return 3;
		if(project_value.equals("FALSE") && specialist_value.equals("true"))
			return 4;
		return 0;
	}
	/**
	 * Compares the IsLongMethod values in "metrics" and "specialists" attributes to check for True Positives(1), False Positives(2), True Negatives(3), False Negatives(4)
	 * @param project Metrics key
	 * @param specialist Specialist key
	 * @return True Positives(1), False Positives(2), True Negatives(3), False Negatives(4)
	 */
	public int compareLongMethodCodeSmells(int project, int specialist) {
		if(specialist == 0) return 0;
		String project_value = findIsLongMethod(project);
		Object specialist_value = findIsLongMethodSpecialist(specialist);
		if(project_value.equals("TRUE") && specialist_value.equals("1"))
			return 1;
		if(project_value.equals("TRUE") && specialist_value.equals("0"))
			return 2;
		if(project_value.equals("FALSE") && specialist_value.equals("0"))
			return 3;
		if(project_value.equals("FALSE") && specialist_value.equals("1"))
			return 4;
		return 0;
	}
	/**
	 * Counts the amount of Indicators (True Positives, False Positives, True Negatives and False Negatives)
	 */
	public void countQuality() {
		int[] temp = {0,0,0,0};
		for(int i = 0; i < metrics.size(); i++) {
			int idSpecialist = findIDInSpecialist(findPackageName(i), findClassName(i), findMethodName(i));
			if(compareGodClassCodeSmells(i, idSpecialist) == 1) temp[0] = temp[0] + 1;
			else if(compareGodClassCodeSmells(i, idSpecialist) == 2) temp[1] = temp[1] + 1;
			else if(compareGodClassCodeSmells(i, idSpecialist) == 3) temp[2] = temp[2] + 1;
			else if(compareGodClassCodeSmells(i, idSpecialist) == 4) temp[3] = temp[3] + 1;
			else if(compareLongMethodCodeSmells(i, idSpecialist) == 1) temp[0] = temp[0] + 1;
			else if(compareLongMethodCodeSmells(i, idSpecialist) == 2) temp[1] = temp[1] + 1;
			else if(compareLongMethodCodeSmells(i, idSpecialist) == 3) temp[2] = temp[2] + 1;
			else if(compareLongMethodCodeSmells(i, idSpecialist) == 4) temp[3] = temp[3] + 1;
		}
		setQuality(temp);
		System.out.println();
	}
	/**
	 * Gets the "fileToRead" class attribute and stores its data as a DefaultTreeModel.
	 * @return DefaultTreeModel containing the fileToRead data
	 * @throws Exception
	 * @throws IOException
	 */
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
	/**
	 * Gets the "metrics" class attribute totals value (number of packages, classes, methods and lines of code) and stores them in a String ArrayList.
	 * @return ArrayList of Strings containing the totals 
	 * @throws Exception
	 * @throws IOException
	 */
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
	
	/**
	 * Searches the "metrics" class attribute and returns the info of the class className in String ArrayList.
	 * @param className Name of the class to find
	 */
	public ArrayList<String> findClassSmellsByName(String className){
		ArrayList<String> answers = new ArrayList<String>();
		for (int i = 1; i < metrics.size(); i++) {
			if(findClassName(i).equals(className)) {
				
				answers.add(findNOM_Class(i));
				answers.add(findLOC_Class(i));
				answers.add(findWMC_Class(i));
				answers.add(findGodClass(i));
			}	
		}
		return answers;
	}
	/**
	 * Searches the "metrics" class attribute and stores the info of each method in a TreeMap mapped by the names of the methods. 
	 * @return TreeMap with the names of the methods as the keys and the mapped values are metrics related to the methods as String arrays 
	 * @throws Exception
	 * @throws IOException
	 */
	public Map<String,String[]> getExcelMethods() throws Exception, IOException {
		getExcelDataAsMap();
		Map<String, String[]> methods = new TreeMap<String, String[]>();
		for (int i =1; i<metrics.size(); i++) {
			String first = (String) metrics.get(i)[3];
			String[] second = {(String)metrics.get(i)[8],(String)metrics.get(i)[9],(String)metrics.get(i)[10]};
			methods.put(first, second);
		}
		return methods;
	}
	
	/**
	 * Gets a given cell's value as a String.
	 * @param cell Cell to get the value of
	 * @return Cell's value as String
	 */
	public static String getCellValue(Cell cell) {
		if (cell.getCellType().toString().equals("CELL_TYPE_NUMERIC")) {
			return cell.getNumericCellValue() + "\t\t\t";
		}
		if (cell.getCellType().toString().equals("CELL_TYPE_STRING")) {
			return cell.getStringCellValue() + "\t\t\t";
		}
		return "";
	}
	/**
	 * Getter for quality integers 
	 * @param i True Positives(1), False Positives(2), True Negatives(3), False Negatives(4)
	 * @return The number of the parameter the user input
	 */
	public int getQuality(int i) {
		return this.quality[i];
	}
	/**
	 * Setter
	 */
	public void setQuality(int[] i) {
		this.quality = i;
	}

}