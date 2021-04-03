package Default;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmellyClass {
	private BufferedReader javaFile;
	private String line = "";
	private int method = 0;
	private int linesOfCode = 0;
	private int cyclo_methods = 1;
	private ArrayList<Integer> methodrec = new ArrayList<>();
	
	private ArrayList<String> methodNames = new ArrayList<>();
	private ArrayList<Integer> linesPerMethod = new ArrayList<>();
	private ArrayList<Boolean> areLongMethods = new ArrayList<>();
	private ArrayList<Integer> cyclosPerMethod = new ArrayList<>();
	private boolean isGodClass;
	private int wmcCount;
	private int Loc_Class_var;

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
					methodName(line);
				}
				linesOfCode++;
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
			wmcCount = cyclo_methods;
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
						linesPerMethod.add(linesOfCode);
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
							linesPerMethod.add(linesOfCode);
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
						cyclosPerMethod.add(cyclo_methods);
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

	public void isGodClass(int wmcThreshold, int nomThreshold, int locThreshold, boolean wmc, boolean nom, boolean loc,
			boolean isOr, boolean isOrAgain) {
		if (wmc && !nom && !loc) {
			if (wmcCount >= wmcThreshold) {
				System.out.println("Esta classe é uma god class.");
			} else {
				System.out.println("Esta classe não é uma god class.");
			}
		}
		if (!wmc && nom && !loc) {
			if (method >= nomThreshold) {
				System.out.println("Esta classe é uma god class.");
			} else {
				System.out.println("Esta classe não é uma god class.");
			}
		}
		if (!wmc && !nom && loc) {
			if (linesOfCode >= locThreshold) {
				System.out.println("Esta classe é uma god class.");
			} else {
				System.out.println("Esta classe não é uma god class.");
			}
		}
		if(wmc && nom && !loc && !isOr) {
			if ( wmcCount >= wmcThreshold && method >= nomThreshold) {
				System.out.println("Esta classe é uma god class.");
			} else {
				System.out.println("Esta classe não é uma god class.");
			}
		}
		if(wmc && nom && !loc && isOr) {
			if ( wmcCount >= wmcThreshold || method >= nomThreshold) {
				System.out.println("Esta classe é uma god class.");
			} else {
				System.out.println("Esta classe não é uma god class.");
			}
		}
		if (wmc && !nom && loc && !isOr) {
			if (wmcCount >= wmcThreshold && linesOfCode >= locThreshold) {
				System.out.println("Esta classe é uma god class.");
			} else {
				System.out.println("Esta classe não é uma god class.");
			}
		}
		if (wmc && !nom && loc && isOr) {
			if (wmcCount >= wmcThreshold || linesOfCode >= locThreshold) {
				System.out.println("Esta classe é uma god class.");
			} else {
				System.out.println("Esta classe não é uma god class.");
			}
		}
		if (!wmc && nom && loc && !isOr) {
			if (method >= nomThreshold && linesOfCode >= locThreshold) {
				System.out.println("Esta classe é uma god class.");
			} else {
				System.out.println("Esta classe não é uma god class.");
			}
		}
		if (!wmc && nom && loc && isOr) {
			if (method >= nomThreshold || linesOfCode >= locThreshold) {
				System.out.println("Esta classe é uma god class.");
			} else {
				System.out.println("Esta classe não é uma god class.");
			}
		}
		if (wmc && nom && loc && !isOr && !isOrAgain) {
			if(wmcCount >= wmcThreshold && method >= nomThreshold && linesOfCode >= locThreshold) {
				System.out.println("Esta classe é uma god class.");
			} else {
				System.out.println("Esta classe não é uma god class.");
			}
		}
		if (wmc && nom && loc && isOr && !isOrAgain) {
			if(wmcCount >= wmcThreshold || method >= nomThreshold || linesOfCode >= locThreshold) {
				System.out.println("Esta classe é uma god class.");
			} else {
				System.out.println("Esta classe não é uma god class.");
			}
		}
		if (wmc && nom && loc && !isOr && isOrAgain) {
			if(wmcCount >= wmcThreshold && method >= nomThreshold || linesOfCode >= locThreshold) {
				System.out.println("Esta classe é uma god class.");
			} else {
				System.out.println("Esta classe não é uma god class.");
			}
		}
		if (wmc && nom && loc && isOr && isOrAgain) {
			if(wmcCount >= wmcThreshold || method >= nomThreshold && linesOfCode >= locThreshold) {
				System.out.println("Esta classe é uma god class.");
			} else {
				System.out.println("Esta classe não é uma god class.");
			}
		}
		

	}

	public void isLongMethod(int locTreshold, int cycloTreshold, boolean loc, boolean cyclo, boolean isOr) {
		if (loc && !cyclo) {
			for (int a : linesPerMethod) {
				if (a >= locTreshold) {
					areLongMethods.add(true);
				} else {
					areLongMethods.add(false);
				}
			}
			for (int i = 0; i < areLongMethods.size(); i++) {
				System.out.println((i + 1) + "º método -> isLong(" + areLongMethods.get(i) + ")");
			}
		}
		if (!loc && cyclo) {
			for (int a : cyclosPerMethod) {
				if (a >= cycloTreshold) {
					areLongMethods.add(true);
				} else {
					areLongMethods.add(false);
				}
			}
			for (int i = 0; i < areLongMethods.size(); i++) {
				System.out.println((i + 1) + "º método -> isLong(" + areLongMethods.get(i) + ")");
			}
		}
		if (loc && cyclo && !isOr) {
			for (int i = 0; i != method; i++) {
				if (linesPerMethod.get(i) >= locTreshold && cyclosPerMethod.get(i) >= cycloTreshold) {
					areLongMethods.add(true);
				} else {
					areLongMethods.add(false);
				}
			}
			for (int i = 0; i < areLongMethods.size(); i++) {
				System.out.println((i + 1) + "º método -> isLong(" + areLongMethods.get(i) + ")");
			}
		}
		if (loc && cyclo && isOr) {
			for (int i = 0; i != method; i++) {
				if (linesPerMethod.get(i) >= locTreshold || cyclosPerMethod.get(i) >= cycloTreshold) {
					areLongMethods.add(true);
				} else {
					areLongMethods.add(false);
				}
			}
			for (int i = 0; i < areLongMethods.size(); i++) {
				System.out.println((i + 1) + "º método -> isLong(" + areLongMethods.get(i) + ")");
			}
		}
	}

	public void LOC_Class(BufferedReader file) {
		javaFile = file;
		linesOfCode = (int) javaFile.lines().count();
		System.out.println("A classe tem " + linesOfCode + " linhas de código.");
	}
	
	public void methodName(String line) {
//		Pattern p = Pattern.compile("(?U)(\\w+)\\W+(\\W+(\\w+)");
//
//		Matcher m = p.matcher(line);
//		if (m.find())
//		    methodNames.add(m.group(1));
//		else methodNames.add("-");
		//teste
		
		
		String withoutSpaces = line.substring(line.indexOf((" ")));
		System.out.println(withoutSpaces);
	}
	
	public int getMethod() {
		return method;
	}

	public int getLinesOfCode() {
		return linesOfCode;
	}

	public int getLinesPerMethod(int id) {
		return linesPerMethod.get(id);
	}

	public boolean getAreLongMethods(int id) {
		return areLongMethods.get(id);
	}

	public int getCyclosPerMethod(int id) {
		return cyclosPerMethod.get(id);
	}

	public boolean getisGodClass() {
		return isGodClass;
	}

	public int getWmcCount() {
		return wmcCount;
	}
	
	public String getMethodNames(int id) {
		return methodNames.get(id);
	}

}