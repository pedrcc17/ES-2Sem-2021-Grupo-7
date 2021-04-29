package Default;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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

	public void getExcelDataAsMap(String excelFileName) throws IOException, InvalidFormatException {
		workbook = new XSSFWorkbook(fileToRead);
		spreadsheet = workbook.getSheet("Code Smells");
		int countOfRows = spreadsheet.getLastRowNum();
		for (int i = 01; i < countOfRows; i++) {
			row = spreadsheet.getRow(i);
			if (row.getLastCellNum() < 10) {
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

	public String findGodClass(int line) {
		Object[] temp = metrics.get(line);
		return (String) temp[7];
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

	public DefaultTreeModel readExcel() {
		String name = getFileToRead().replace("\\", " ");
		String[] myFile = name.split(" ");
		String myFileName = myFile[myFile.length - 1];

		DefaultTreeModel treeModel = new DefaultTreeModel(new DefaultMutableTreeNode(myFileName) {
			private static final long serialVersionUID = 1L;

			{
				DefaultMutableTreeNode node_1;
				

				node_1 = new DefaultMutableTreeNode("Classe 1");
				node_1.add(new DefaultMutableTreeNode("Metodo 1"));
				node_1.add(new DefaultMutableTreeNode("Metodo 2"));
				node_1.add(new DefaultMutableTreeNode("Metodo 3"));
				node_1.add(new DefaultMutableTreeNode("Metodo 4"));
				add(node_1);
				node_1 = new DefaultMutableTreeNode("Classe 2");
				node_1.add(new DefaultMutableTreeNode("Metodo 1"));
				node_1.add(new DefaultMutableTreeNode("Metodo 2"));
				node_1.add(new DefaultMutableTreeNode("Metodo 3"));
				node_1.add(new DefaultMutableTreeNode("Metodo 4"));
				add(node_1);
				node_1 = new DefaultMutableTreeNode("Classe 3");
				node_1.add(new DefaultMutableTreeNode("Metodo 1"));
				node_1.add(new DefaultMutableTreeNode("Metodo 2"));
				node_1.add(new DefaultMutableTreeNode("Metodo 3"));
				node_1.add(new DefaultMutableTreeNode("Metodo 4"));
				add(node_1);
				

			}
		});

		return treeModel;
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