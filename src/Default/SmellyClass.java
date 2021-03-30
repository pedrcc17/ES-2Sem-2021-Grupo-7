package Default;

import java.io.BufferedReader;
import java.io.IOException;

public class SmellyClass {
	private BufferedReader javaFile;
	private int lines_of_code = 0;
	private String[] word;
	private String line = " ";
	private int method = 0;

	public SmellyClass(BufferedReader in) {
		javaFile = in;
		startSmelling();
	}

	private void startSmelling() {
		NOM();
	}

	private void NOM() {
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
			System.out.println("done");
			System.out.println(method);
		} catch (IOException e) {
			System.out.println("End of class");
		}
	}
}
