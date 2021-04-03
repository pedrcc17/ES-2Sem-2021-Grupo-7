package Default;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;

public class Main {

	private static BufferedReader restartReading(String dir, String file) {
		try {
			BufferedReader javaFile = new BufferedReader(new FileReader(dir + "\\" + file));
			String[] word = javaFile.readLine().split(" ");
			while (word[0].equals("package") || word[0].equals("import") || word[0].equals("")) {
				word = javaFile.readLine().split(" ");
			}
			if (word[1].equals("class")) {
				return javaFile;
			}
		}
		catch (Exception e) {
			System.out.println("Erro a abrir o ficheiro");
		}
		return null;
	}

	public static void main(String[] args) throws IOException {
		ExcelExporting eexport = new ExcelExporting();
		File fileDirectory = null;
		File filePath = null;
		String[] fileDirectorylist = null;
		String filePathString = null;
		JFileChooser chooser = new JFileChooser(".");
		chooser.setMultiSelectionEnabled(true);
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int ret = chooser.showOpenDialog(null);
		if(ret == JFileChooser.APPROVE_OPTION) {
			fileDirectory = chooser.getSelectedFile();
			fileDirectorylist = fileDirectory.list();
			filePathString = fileDirectory.getPath();
			System.out.println(fileDirectory);
			for (String file : fileDirectorylist) {
				System.out.println(file);
				SmellyClass classy = new SmellyClass();
				classy.NOM(restartReading(filePathString, file));
				System.out.println("-------------------------");
				classy.WMC(restartReading(filePathString, file));
				System.out.println("-------------------------");
				classy.CYCLO_Method(restartReading(filePathString, file));
				System.out.println("-------------------------");
				classy.LOC_Method(restartReading(filePathString, file));
				System.out.println("-------------------------");
				classy.LOC_Class(restartReading(filePathString, file));
				System.out.println("-------------------------");
				classy.isLongMethod(5, 2, true, true, false);
				System.out.println("-------------------------");
				classy.isGodClass(15, 10, 100, true, true, true, true, true);
				eexport.setClassAndPackageNames(file, filePathString);
				eexport.saveMetrics(classy);
			}
			eexport.exportToExcel(eexport.chooseName());
		}
	}
}