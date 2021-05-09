package Default;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Everything related to the detection of code smells
 * @author G7 de ES LEI 2020/2021
 *
 */
public class SmellyClass {
	/** The .java file to check for code smells.*/
	private BufferedReader javaFile;
	/** Aid string. */
	private String line = "";
	/** Number of methods. */
	private int method = 0;
	/** Number of lines of code in the class */
	private int linesOfCode = 0;
	/** Aid Integer*/
	private int cyclo_methods = 1;
	/** Saved method names*/
	private ArrayList<String> methodNames = new ArrayList<>();
	/** Number of lines of code for each method in the class */
	private ArrayList<Integer> linesPerMethod = new ArrayList<>();
	/** An ArrayList of booleans for each method in the class where true means that the method is long, and false means otherwise */
	private ArrayList<Boolean> areLongMethods = new ArrayList<>();
	/** Cyclomatic complexity for each method in the class */
	private ArrayList<Integer> cyclosPerMethod = new ArrayList<>();
	/** True if the .java file is a God Class, false otherwise*/
	private boolean isGodClass;
	/** Cyclomatic complextiy of the class*/
	private int wmcCount;
	
	public SmellyClass() {

	}
	/**
	 * Saves the number of methods in a given class to the "method" class attribute.
	 * @param file The class to be evaluated
	 */
	public void NOM(BufferedReader file) {
		javaFile = file;
		try {
			line = javaFile.readLine();
			while (line != null) {
				if ((line.contains("private") || line.contains("public")) && !line.contains(";") && !line.contains("=") 
						&& !line.contains("class") && line.contains("(")){
						
						System.out.println(line);
						String atributes = line.substring(line.indexOf("("), line.indexOf(")")+1);
						String atributes_vf = "";
						
						if(atributes.contains(",")) {
							String[] atributes_v2 = atributes.split(",");
							for(int i=0;i<=atributes_v2.length-1;i++) {
								String[] atributes_v3 = atributes_v2[i].split(" ");
								
								if(atributes_v3.length == 2 ) {
									atributes_vf = atributes_vf + atributes_v3[0]+",";
								}
								else if(atributes_v3.length == 3) {
									atributes_vf = atributes_vf + atributes_v3[1]+",";
								}
							}
							method++;
							atributes_vf=atributes_vf.substring(0, atributes_vf.length() - 1);
							methodName(line,atributes_vf+")");
						}
						else {
							if(atributes.contains(" ")) {
								String[] atributes_v3 = atributes.split(" ");
								atributes_vf = atributes_vf + atributes_v3[0];
								method++;
								methodName(line,atributes_vf+")");
							}
							else {
								String[] atributes_v3 = atributes.split(" ");
								atributes_vf = atributes_vf + atributes_v3[0];
								methodName(line,atributes_vf);
								method++;
							}
							
						}

				
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
	/**
	 * Saves the cyclomatic complexity of the class given to the "wmcCount" class attribute
	 * @param file The class to be evaluated
	 */
	public void WMC(BufferedReader file) {
		javaFile = file;
		cyclo_methods = 0;
		try {
			line = javaFile.readLine();
			while (line != null) {
				if ((line.contains("private") || line.contains("public")) && !line.contains(";") && !line.contains("=") 
						&& !line.contains("class") && line.contains("(")){
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
	/**
	 * Saves to the "linesPerMethod" class attribute the number of lines in each method of the given class.
	 * @param file The class to be evaluated
	 */
	public void LOC_Method(BufferedReader file) {
		javaFile = file;
		int linesOfCode = 0;
		int num_method = 0;
		boolean inMethod = false;
		try {
			line = javaFile.readLine();
			while (line != null) {
				if ((line.contains("private") || line.contains("public")) && !line.contains(";") && !line.contains("=") 
						&& !line.contains("class") && line.contains("(")){
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
					if ((line.contains("private") || line.contains("public")) && !line.contains(";") && !line.contains("=") 
							&& !line.contains("class") && line.contains("(")){
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
						if(word.length != 0) {
							if (inMethod && !line.isEmpty() && !word[0].contains("//") &&  !line.contains("@Override")) {
								linesOfCode++;
							}
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
	/**
	 * Saves to the "cyclosPerMethod" class attribute the cyclomatic complexity of each method in the class given as the parameter. 
	 * @param file The class that will have its methods be evaluated
	 */
	public void CYCLO_Method(BufferedReader file) {
		javaFile = file;
		cyclo_methods = 1;
		int num_method = 0;
		try {
			line = javaFile.readLine();
			while (line != null) {
				if ((line.contains("private") || line.contains("public")) && !line.contains(";") && !line.contains("=") 
						&& !line.contains("class") && line.contains("(")){
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
	/**
	 * Checks if a class is a God Class by comparing the thresholds in the parameters with the rest of the values in the parameters.
	 * @param wmcThreshold Cyclomatic complexity threshold
	 * @param nomThreshold Number of methods threshold
	 * @param locThreshold Lines of code threshold
	 * @param wmc True if it is to be checked by the cyclomatic completity
	 * @param nom True if it is to be checked by the number of methods
	 * @param loc True if it is to be checked by the lines of code
	 * @param isOr True means "and", false means "or"
	 * @param isOrAgain True means "and", false means "or"
	 * @return True if it is a God Class, false if otherwise
	 */
	public boolean isGodClass(int wmcThreshold, int nomThreshold, int locThreshold, boolean wmc, boolean nom, boolean loc,
			boolean isOr, boolean isOrAgain) {
		if (wmc && !nom && !loc) {
			if (wmcCount >= wmcThreshold) {
				return true;
			} else {
				return false;
			}
		}
		if (!wmc && nom && !loc) {
			if (method >= nomThreshold) {
				return true;
			} else {
				return false;
			}
		}
		if (!wmc && !nom && loc) {
			if (linesOfCode >= locThreshold) {
				return true;
			} else {
				return false;
			}
		}
		if(wmc && nom && !loc && !isOr) {
			if ( wmcCount >= wmcThreshold && method >= nomThreshold) {
				return true;
			} else {
				return false;
			}
		}
		if(wmc && nom && !loc && isOr) {
			if ( wmcCount >= wmcThreshold || method >= nomThreshold) {
				return true;
			} else {
				return false;
			}
		}
		if (wmc && !nom && loc && !isOr) {
			if (wmcCount >= wmcThreshold && linesOfCode >= locThreshold) {
				return true;
			} else {
				return false;
			}
		}
		if (wmc && !nom && loc && isOr) {
			if (wmcCount >= wmcThreshold || linesOfCode >= locThreshold) {
				return true;
			} else {
				return false;
			}
		}
		if (!wmc && nom && loc && !isOr) {
			if (method >= nomThreshold && linesOfCode >= locThreshold) {
				return true;
			} else {
				return false;
			}
		}
		if (!wmc && nom && loc && isOr) {
			if (method >= nomThreshold || linesOfCode >= locThreshold) {
				return true;
			} else {
				return false;
			}
		}
		if (wmc && nom && loc && !isOr && !isOrAgain) {
			if(wmcCount >= wmcThreshold && method >= nomThreshold && linesOfCode >= locThreshold) {
				return true;
			} else {
				return false;
			}
		}
		if (wmc && nom && loc && isOr && !isOrAgain) {
			if(wmcCount >= wmcThreshold || method >= nomThreshold || linesOfCode >= locThreshold) {
				return true;
			} else {
				return false;
			}
		}
		if (wmc && nom && loc && !isOr && isOrAgain) {
			if(wmcCount >= wmcThreshold && method >= nomThreshold || linesOfCode >= locThreshold) {
				return true;
			} else {
				return false;
			}
		}
		if (wmc && nom && loc && isOr && isOrAgain) {
			if(wmcCount >= wmcThreshold || method >= nomThreshold && linesOfCode >= locThreshold) {
				return true;
			} else {
				return false;
			}
		}
		return false;


	}
	/**
	 * Saves to the "areLongMethods" class attribute if a method is long by comparing the thresholds given in the parameters with the method numbers in "loc" and "cyclo"
	 * @param locTreshold Line of code threshold	
	 * @param cycloTreshold Cyclomatic complexity threshold
	 * @param loc Lines of code to compare
	 * @param cyclo Cyclomatic complexity to compare
	 * @param isOr True means "and", false means "or"
	 */
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
	/**
	 * Counts the lines of code in a given file (class) and adds it to the "linesOfCode" class attribute.
	 * @param file
	 */
	public void LOC_Class(BufferedReader file) {
		javaFile = file;
		linesOfCode = (int) javaFile.lines().count();
		System.out.println("A classe tem " + linesOfCode + " linhas de código.");
		linesOfCode++;
	}
	/**
	 * Adds the method name in the line given to the methodNames class attribute.
	 * @param line
	 */
	public void methodName(String line, String atributes) {
		String[] parts = line.split("\\(");
		String name = parts[0].substring(parts[0].lastIndexOf(" "));
		System.out.println(name.trim()+atributes);
		methodNames.add(name.trim()+atributes);
	}

	/**
	 * Getter for "method".
	 */
	public int getMethod() {
		return method;
	}
	/**
	 * Getter for "linesOfCode".
	 */
	public int getLinesOfCode() {
		return linesOfCode;
	}
	/**
	 * Getter for "linesPerMethod" at the index given.
	 * @param id Index
	 */
	public int getLinesPerMethod(int id) {
		return linesPerMethod.get(id);
	}
	/**
	 * Getter for "areLongMethods" at the index given.
	 * @param id Index
	 */
	public boolean getAreLongMethods(int id) {
		return areLongMethods.get(id);
	}
	/**
	 * Getter for "cyclosPerMethod" at the index given.
	 * @param id Index
	 */
	public int getCyclosPerMethod(int id) {
		return cyclosPerMethod.get(id);
	}
	/**
	 * Getter for "isGodClass".
	 */
	public boolean getisGodClass() {
		return isGodClass;
	}
	/**
	 * Getter for "wmcCount".
	 */
	public int getWmcCount() {
		return wmcCount;
	}
	/**
	 * Getter for "methodNames" at the index given.
	 * @param id Index	 */
	public String getMethodNames(int id) {
		return methodNames.get(id);
	}
}