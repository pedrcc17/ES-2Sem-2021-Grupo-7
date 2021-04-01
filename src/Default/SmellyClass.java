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
	private ArrayList<Integer> methodrec = new ArrayList<>();
	private ArrayList<Integer> linesPerMethod = new ArrayList<>();
	private ArrayList<Boolean> areLongMethods = new ArrayList<>();
	private ArrayList<Integer> cyclosPerMethod = new ArrayList<>();

	public SmellyClass() {

	}

	public void NOM(BufferedReader file) {
		javaFile = file;
		try {
			line = javaFile.readLine();
			while (line != null) {
				if ((line.contains("private") || line.contains("public")) && !line.endsWith(";")
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
		cyclo_methods = 0;
		try {
			line = javaFile.readLine();
			while (line != null) {
				if (((line.contains("private") || line.contains("public")) && !line.endsWith(";")
						&& !line.contains("class"))) {
					cyclo_methods++;
				}
				if ((line.contains("while") || line.contains("else") || line.contains("for") || line.contains("if"))
						&& !line.endsWith(";")) {
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

	public void LOC_Method(BufferedReader file) {
		javaFile = file;
		int linesOfCode = 0;
		int num_method = 0;
		boolean inMethod = false;
		try {
			line = javaFile.readLine();
			while (line != null) {
				if (((line.contains("private") || line.contains("public"))) && !line.endsWith(";")
						&& !line.contains("class")) {
					if (num_method != 0 && inMethod) {
						System.out.println("O " + num_method + "º método tem " + linesOfCode + " linhas de código.");
						inMethod = false;
					}
					linesOfCode = 0;
					linesOfCode++;
					num_method++;
					inMethod = true;
				} else {
					if (((line.contains("private") || line.contains("public")))
							&& (line.endsWith(")") || line.endsWith(" ")) && !line.contains("class")) {
						if (num_method != 0 && inMethod) {
							System.out
									.println("O " + num_method + "º método tem " + linesOfCode + " linhas de código.");
							inMethod = false;
						}
						linesOfCode = 0;
						linesOfCode++;
						num_method++;
						inMethod = true;
					} else {
						String[] word = line.split(" ");
						if (inMethod && !line.isEmpty() && !word[0].contains("//") && !line.contains("@Override")) {
							linesOfCode++;
						}
					}
				}
				line = javaFile.readLine();
				if (line == null) {
					System.out.println("O " + num_method + "º método tem " + linesOfCode + " linhas de código.");
					linesPerMethod.add(linesOfCode);
				}
			}
			javaFile.close();
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
				if ((line.contains("private") || line.contains("public")) && !line.endsWith(";")
						&& !line.contains("class")) {
					if (num_method != 0) {
						System.out.println("O " + num_method + "º método tem complexidade de " + cyclo_methods + ".");
					}
					cyclo_methods = 1;
					num_method++;
					line = javaFile.readLine();
				}
				if ((line.contains("while") || line.contains("for") || line.contains("if") || line.contains("else"))
						&& line.endsWith("{")) {
					cyclo_methods++;
				}
				line = javaFile.readLine();
				if (line == null) {
					System.out.println("O " + num_method + "º método tem complexidade de " + cyclo_methods + ".");
					cyclosPerMethod.add(cyclo_methods);
				}
			}
			javaFile.close();
		} catch (IOException e) {
			System.out.println("End of class");
		}
	}
	
	public void isGodClass() {
		
	}

	public void isLongMethod(int locTreshold, int cycloTreshold, boolean loc, boolean cyclo) {
		if(loc && !cyclo) {
			for (int a : linesPerMethod) {
				if (a >= locTreshold) {
					areLongMethods.add(true);
				} else {
					areLongMethods.add(false);
				}
			}	
		}
		if(!loc && cyclo) {
			for (int a : cyclosPerMethod) {
				if (a >= cycloTreshold) {
					areLongMethods.add(true);
				} else {
					areLongMethods.add(false);
				}
			}	
		}
		if(loc && cyclo) {
			
		
		}
	}
}