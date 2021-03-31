package Default;

import java.io.BufferedReader;
import java.io.IOException;

public class NOM {

	private BufferedReader javaFile;
	private int numberOfMethods = 0;
	private String line;

	public NOM(BufferedReader javaFile) {
		this.javaFile = javaFile;
	}

	public void run() {
		countMethods();
	}

	private void countMethods() {
		try {
			line = javaFile.readLine();
			while (line != null) {
				if ((line.contains("private") || line.contains("public")) && line.endsWith("{")
						&& !line.contains("class")) {
					numberOfMethods++;
				}
				line = javaFile.readLine();
			}
			System.out.println("The class has "+numberOfMethods+ " methods.");
		} catch (IOException e) {
			System.out.println("End of class");
		}
	}

}
