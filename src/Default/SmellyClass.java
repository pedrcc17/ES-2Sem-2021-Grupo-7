package Default;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class SmellyClass {
	private BufferedReader javaFile;
	private String line = "";
	private int method = 0;
	private int lines_of_code = 0;
	private int cyclo_methods = 1;
	private ArrayList<Integer> methodrec=new ArrayList<>();
	

	public SmellyClass() {

	}

	public void NOM(BufferedReader file) {
		javaFile = file;
		try {
			line = javaFile.readLine();
			while (line != null) {
				if ((line.contains("private") || line.contains("public")) && line.endsWith("{")
						&& !line.contains("class")) {
					method++;
				}
				lines_of_code++;
				methodrec.add(lines_of_code);
				line = javaFile.readLine();
			}
			javaFile.close();
			System.out.println("The class has " + method + " methods.");
		} catch (IOException e) {
			System.out.println("End of class");
		}
	}

	public void WMC(BufferedReader file) {
		javaFile = file;
		try {
			line = javaFile.readLine();
			while (line != null) {
				if (((line.contains("private") || line.contains("public")) && line.endsWith("{")
						&& !line.contains("class"))) {
					cyclo_methods++;
				}
				if ((line.contains("while") || line.contains("for") || line.contains("if")) && line.endsWith("{")) {
					cyclo_methods++;
				}
				line = javaFile.readLine();
			}
			javaFile.close();
			System.out.println("The class has a complexity of " + cyclo_methods + ".");
		} catch (IOException e) {
			System.out.println("End of class");
		}
	}

	public void CYCLO_Method(BufferedReader file) {
		javaFile = file;
		cyclo_methods = 1;
		int num_method = 0;
		try {
			line = javaFile.readLine();
			while (line != null) {
				if ((line.contains("private") || line.contains("public")) && line.endsWith("{")
						&& !line.contains("class")) {
					if (num_method != 0) {
						System.out.println("O " + num_method + "º método tem complexidade de " + cyclo_methods + ".");
					}
					cyclo_methods = 1;
					num_method++;
					line = javaFile.readLine();
				}
				if ((line.contains("while") || line.contains("for") || line.contains("if"))
						&& line.endsWith("{")) {
					cyclo_methods++;
				}
				line = javaFile.readLine();
				if(line == null) {
					System.out.println("O " + num_method + "º método tem complexidade de " + cyclo_methods + ".");
				}
			}
			javaFile.close();
		} catch (IOException e) {
			System.out.println("End of class");
		}
	}
	
	private void LOC_method() {
		try {
			int num_line;
			for(int ite :methodrec) {
				
				num_line=0;
				for (int i = 1; i <= ite; i++) {
						javaFile.readLine();
						num_line++;
					} 
					
				
				boolean end = true; 
				int acc=0;
				int final_line = num_line;
				while(!end) {
					String line = javaFile.readLine();
					final_line++;
					if(line.contains("{")) {
						acc++;
					}	
					else if(line.contains("}")) {
						acc--;
						if(acc==0)
							end = false;
					}
				}
				
				final_line = final_line - num_line;
				System.out.println("o numero de linhas do metodo um e "+final_line);
			}
			
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
