package Default;

import java.io.BufferedReader;
import java.io.IOException;

public class SmellyClass {
	private BufferedReader javaFile;
	private String line = "";
	private int method = 0;
	private int lines_of_code = 0;

	
	public SmellyClass(BufferedReader in) {
		javaFile = in;
		startSmelling();
	}

	private void startSmelling() {
		NOM();
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

	private void WMC(BufferedReader file) {
		javaFile = file;
		try {
			line = javaFile.readLine();
			while (line != null) {
			}
		} catch (IOException e) {
			System.out.println("End of class");
		}
	}
}
