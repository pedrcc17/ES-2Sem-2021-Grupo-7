package Default;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExporting {

	private XSSFWorkbook workbook = new XSSFWorkbook();
	private XSSFSheet spreadsheet = workbook.createSheet("Code Smells");
	private XSSFRow row;
	private Map<Integer, Object[] > metrics;
	private int id;
	private String className;
	private String packageName;
	private XSSFFont boldfont;

	public ExcelExporting() {
		id = 00;
		metrics = new TreeMap < Integer, Object[] >();
		boldfont = workbook.createFont();
	    boldfont.setBold(true);
		metrics.put(00, new Object [] {
				"MethodID","package","class","method","NOM_class","LOC_class","WMC_class","is_God_Class","LOC_method","CYCLO_method","is_Long_Method"});
	}

	public void saveMetrics(SmellyClass classy) {
		for(int i = 0; i < classy.getMethod(); i++) {
			id++;
			metrics.put(id, new Object [] {
					Integer.toString(id),
					packageName.substring(packageName.lastIndexOf("\\")+1),
					className,
					classy.getMethodNames(i),
					Integer.toString(classy.getMethod()),
					Integer.toString(classy.getLinesOfCode()),
					Integer.toString(classy.getWmcCount()),
					Boolean.toString(classy.getisGodClass()).toUpperCase(),
					Integer.toString(classy.getLinesPerMethod(i)),
					Integer.toString(classy.getCyclosPerMethod(i)),
					Boolean.toString(classy.getAreLongMethods(i)).toUpperCase(),
			});
		}
	}

	public void exportToExcel(String name) throws IOException {
		CellStyle hStyle = workbook.createCellStyle();
		Set < Integer > keyid = metrics.keySet();
		int rowid = 0;

		for (Integer key : keyid) {
			row = spreadsheet.createRow(rowid++);
			Object [] objectArr = metrics.get(key);
			int cellid = 0;

			for (Object obj : objectArr){
				Cell cell = row.createCell(cellid++);
				cell.setCellValue((String)obj);
				if(key==0) {
					hStyle.setFont(boldfont);
					cell.setCellStyle(hStyle);
				}
			}
		}
		FileOutputStream out = new FileOutputStream(
				new File("Excel Files/" + name + ".xlsx"));

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
			name = (String)JOptionPane.showInputDialog(null, "Choose Excel file name", JOptionPane.PLAIN_MESSAGE);
		}
		return name;
	}

}
