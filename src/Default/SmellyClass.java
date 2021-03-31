package Default;

import java.io.BufferedReader;
import java.io.IOException;

public class SmellyClass {
	private BufferedReader javaFile;
	private String line = "";
	private int method = 0;
	private int lines_of_code = 0;
	private int cyclo_methods = 1;

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
}
