package Default;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 *  Aid methods for reading files
 * @author G7 de ES LEI 2020/2021
 *
 */
public class LoadProject {

	/** Set of rules to detect the code smells*/
	ArrayList<Pair<Integer, Boolean>> rule;
	
	/**
	 * Gets the BufferedReader for the file given if it is a java class or interface, in the directory given.
	 * @param dir Directory
	 * @param file File
	 * @return BufferedReader for java class or interface
	 */
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
	/**
	 * Exports all data from the files in the directory chosen in "readEverything()".
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public void openProject() throws IOException, InvalidFormatException {
		ExcelAPI excelapi = new ExcelAPI();
		ArrayList<File> allJavaFiles = readEverything();
		for (File file : allJavaFiles) {
			System.out.println("files " + file);
			SmellyClass classy = new SmellyClass();
			classy.NOM(restartReading(file.getParent(), file.getName()));
			classy.WMC(restartReading(file.getParent(), file.getName()));
			classy.CYCLO_Method(restartReading(file.getParent(), file.getName()));
			classy.LOC_Method(restartReading(file.getParent(), file.getName()));
			classy.LOC_Class(restartReading(file.getParent(), file.getName()));
			classy.isLongMethod(rule.get(0).first, rule.get(1).first, rule.get(0).second, rule.get(1).second, rule.get(2).second);
			classy.isGodClass(rule.get(3).first, rule.get(4).first, rule.get(5).first, rule.get(3).second, rule.get(4).second, rule.get(5).second, rule.get(6).second, rule.get(7).second);
			excelapi.setClassAndPackageNames(file.getName(), file.getParent());
			excelapi.saveMetrics(classy);
		}
		excelapi.exportToExcel(excelapi.chooseName());
	}
	/**
	 * Gets all the .java files in an array of File.
	 * @param arr The array of Files
	 * @param index Recursion index
	 * @param toreturn Array of .java files to return after recursion ends
	 * @return Array of .java files
	 */
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
	/**
	 * Setter for the "rule" class attribute
	 */
	public void receiveRule(ArrayList<Pair<Integer, Boolean>> rule) {
		this.rule = rule;
		}
	/**
	 * Turns a directory into an array of File for it to be used by "RecursiveFinder" to get all .java files in this directory.
	 * @return Array of .java files
	 */
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