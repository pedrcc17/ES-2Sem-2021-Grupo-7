package Default;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class Main {

	private static BufferedReader restartReading(String dir, String file) {
		try {
			BufferedReader javaFile = new BufferedReader(new FileReader(dir + "\\" + file));
			String[] word = javaFile.readLine().split(" ");
			while (word[0].equals("package") || word[0].equals("import") || word[0].equals("") || word[0].equals("/*") || word[0].contains("*") || word[0].equals("*/") || word[0].contains(" *")) {
				word = javaFile.readLine().split(" ");
			}
			if (word[1].equals("class") || word[1].equals("interface")) {
				return javaFile;
			}
		}
		catch (Exception e) {
			System.out.println("Erro a abrir o ficheiro");
		}
		return null;

		
	}

	public static void main(String[] args) throws IOException, InvalidFormatException {
		ExcelAPI excelapi = new ExcelAPI();
		ArrayList<File> allJavaFiles = readEverything();
		for (File file : allJavaFiles) {
			System.out.println("files " + file);
			SmellyClass classy = new SmellyClass();
			classy.NOM(restartReading(file.getParent(), file.getName()));
			System.out.println("-------------------------");
			classy.WMC(restartReading(file.getParent(), file.getName()));
			System.out.println("-------------------------");
			classy.CYCLO_Method(restartReading(file.getParent(), file.getName()));
			System.out.println("-------------------------");
			classy.LOC_Method(restartReading(file.getParent(), file.getName()));
			System.out.println("-------------------------");
			classy.LOC_Class(restartReading(file.getParent(), file.getName()));
			System.out.println("-------------------------");
			classy.isLongMethod(5, 2, true, true, false);
			System.out.println("-------------------------");
			classy.isGodClass(15, 10, 100, true, true, true, true, true);
			excelapi.setClassAndPackageNames(file.getName(), file.getParent());
			excelapi.saveMetrics(classy);
		}
		excelapi.exportToExcel(excelapi.chooseName());

		//Ler um excel, guardar, e exportar um igual
//		excelapi.getExcelDataAsMap();
//		excelapi.exportToExcel(excelapi.chooseName());
		
		//ler o valor da cell 5x5
//		System.out.println(excelapi.findInMap(16, 9));
		
	}

	public static ArrayList<File> RecursiveFinder(File[] arr,int index,int level, ArrayList<File> toreturn) 
	{
		if(index == arr.length)
			return toreturn;


		if(arr[index].isFile()){
			if (arr[index].getName().endsWith(".java")) {
				toreturn.add(arr[index]);
			}
		}

		else if(arr[index].isDirectory())
		{              
			RecursiveFinder(arr[index].listFiles(), 0, level + 1, toreturn);
		}

		RecursiveFinder(arr,++index, level, toreturn);

		return toreturn;
	}

	public static ArrayList<File> readEverything(){
		ArrayList<File> vazio = new ArrayList<File>();
		JFileChooser chooser = new JFileChooser(".");
		chooser.setMultiSelectionEnabled(true);
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int ret = chooser.showOpenDialog(null);
		if(ret == JFileChooser.APPROVE_OPTION) {
			File fileDirectory = chooser.getSelectedFile();
			if(fileDirectory.exists() && fileDirectory.isDirectory()){
				File arr[] = fileDirectory.listFiles();

				RecursiveFinder(arr,0,0,vazio);
			}

		}
		return vazio;
	}
}
