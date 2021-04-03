package Default;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExporting {

	private XSSFWorkbook workbook = new XSSFWorkbook();
	private XSSFSheet spreadsheet = workbook.createSheet("Code Smells");
	private XSSFRow row;
	private Map<String, Object[] > metrics;
	private int id;
	private String className;
	private String packageName;

	public ExcelExporting() {
		id = 00;
		metrics = new TreeMap < String, Object[] >();
		metrics.put("00", new Object [] {
				"MethodID","package","class","method","NOM_class","LOC_class","WMC_class","is_God_Class","LOC_method","CYCLO_method","is_Long_Method"});
	}

	public void saveMetrics(SmellyClass classy) {
		for(int i = 0; i < classy.getMethod(); i++) {
			id++;
			metrics.put(Integer.toString(id), new Object [] {
					Integer.toString(id),
					packageName.substring(packageName.lastIndexOf("\\")+1),
					className,
					"m�todos",
					Integer.toString(classy.getMethod()),
					Integer.toString(classy.getLinesOfCode()),
					Integer.toString(classy.getWmcCount()),
					Boolean.toString(classy.getisGodClass()),
					Integer.toString(classy.getLinesPerMethod(i)),
					Integer.toString(classy.getCyclosPerMethod(i)),
					Boolean.toString(classy.getAreLongMethods(i)),
			});
		}
	}

	public void exportToExcel(String name) throws IOException {
		Set < String > keyid = metrics.keySet();
		int rowid = 0;

		for (String key : keyid) {
			row = spreadsheet.createRow(rowid++);
			Object [] objectArr = metrics.get(key);
			int cellid = 0;

			for (Object obj : objectArr){
				Cell cell = row.createCell(cellid++);
				cell.setCellValue((String)obj);
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