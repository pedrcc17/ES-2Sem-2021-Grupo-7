package Default;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
				eexport.setClassAndPackageNames(file.getName(), file.getParent());
				eexport.saveMetrics(classy);
			}
			eexport.exportToExcel(eexport.chooseName());
		}
	
	public static ArrayList<File> RecursivePrint(File[] arr,int index,int level, ArrayList<File> toreturn) 
    {
        // terminate condition
        if(index == arr.length)
            return toreturn;
          
          
        // for files
        if(arr[index].isFile()){
        	if (arr[index].getName().endsWith(".java")) {
        		toreturn.add(arr[index]);
        	}
        }
          
        // for sub-directories
        else if(arr[index].isDirectory())
        {              
            // recursion for sub-directories
            RecursivePrint(arr[index].listFiles(), 0, level + 1, toreturn);
        }
           
        // recursion for main directory
        RecursivePrint(arr,++index, level, toreturn);
        
        return toreturn;
   }
     
   // Driver Method
   public static ArrayList<File> readEverything(){
	   ArrayList<File> vazio = new ArrayList<File>();
	   JFileChooser chooser = new JFileChooser(".");
	   chooser.setMultiSelectionEnabled(true);
	   chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	   int ret = chooser.showOpenDialog(null);
	   if(ret == JFileChooser.APPROVE_OPTION) {
		   File fileDirectory = chooser.getSelectedFile();
		   if(fileDirectory.exists() && fileDirectory.isDirectory()){
           // array for files and sub-directories 
           // of directory pointed by fileDirectory
			   File arr[] = fileDirectory.listFiles();
             
			   System.out.println("**********************************************");
			   System.out.println("Files from main directory : " + fileDirectory);
			   System.out.println("**********************************************");
             
           // Calling recursive method
			   RecursivePrint(arr,0,0,vazio);
		   }
		   
      }
	  return vazio;
   }
}
