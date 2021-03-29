package Default;

import java.io.BufferedReader;
import java.io.FileReader;

public class Main {

	public static void main(String[] args) {
		try {
			BufferedReader javaFile = new BufferedReader(new FileReader("src\\Default\\RandomizedQueue.java"));
			String[] word = javaFile.readLine().split(" ");
			System.out.println(word[0]);
			while(word[0].equals("package") ||word[0].equals("import") || word[0].equals("")) {
				System.out.println("Import or package, ignore.");
				word = javaFile.readLine().split(" ");
			}
			System.out.println(word[1]);
			if(word[1] == "class") {
				SmellyClass classy = new SmellyClass(javaFile);
				System.out.println("Created class");
			}
		} catch (Exception e) {
			System.out.println("Erro a abrir o ficheiro");
		}
	}
}
