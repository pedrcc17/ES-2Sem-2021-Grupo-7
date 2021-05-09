package Default;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Test {
	
	/*
	 * Nesta classe estão todos os métodos possíveis de executar através da GUI com o propósito dos testes unitários através do plugin EclEmma.
	 */
	
	private static GUI gui;
	private static LoadProject lp;
	private static ExcelAPI excelapi;
	private static ArrayList<String> TotalsList;
	
	public static void main(String[] args) throws IOException, Exception {
		//Initialize most used classes
		gui = new GUI();
		lp = new LoadProject();
		excelapi = new ExcelAPI();
		
		//Open Project to Metrics
		lp.openProject();
		
		//Choose and Open Excel
		gui.selectExcel();
		excelapi = gui.getExcelAPI();
		TotalsList = excelapi.readExcelTotals();
		excelapi.readExcel();
		Map<String, String[]> methods = excelapi.getExcelMethods();
		excelapi.findClassSmellsByName("GrammerException.java"); //In this test we choose a class name that we know is used
		
		//Create New Rule
		String temp = gui.getNumberOfRulesTxt();
		
		//Change Rules
		ArrayList<ArrayList<String>> dataList = gui.readTxt();
	}
	
}
