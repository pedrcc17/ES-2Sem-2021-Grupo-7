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
			System.out.println("The class has "+method+ " methods.");
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
            System.out.println(cyclo_methods);
        } catch (IOException e) {
            System.out.println("End of class");
        }
    }

	public void CYCLO(BufferedReader file) {
		javaFile = file;
		try {
			line = javaFile.readLine();
			while (line != null) {
				if ((line.contains("private") || line.contains("public")) && line.endsWith("{")
						&& !line.contains("class")) {
					System.out.println("WTFF");
				}
				line = javaFile.readLine();
			}
			javaFile.close();
		} catch (IOException e) {
			System.out.println("End of class");
		}
	}
}
