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
					System.out.println("new method");
				}
				lines_of_code++;
				line = javaFile.readLine();
			}
			System.out.println("done counting methods");
		} catch (IOException e) {
			System.out.println("End of class");
		}
	}



	public void WMC_Class(BufferedReader file) {
		javaFile = file;
		try {
			line = javaFile.readLine();
			while (line != null) {
				if ((line.contains("while") || line.contains("for")) && line.contains("if")
						&& line.endsWith("{")) {
					System.out.println("Cyclo add");
					cyclo_methods++;
				}
			}
		} catch (IOException e) {
			System.out.println("End of class");
		}
	}
}
