package Default;

import java.io.BufferedReader;
import java.io.FileReader;

public class Main {

	public static void main(String[] args) {
		try {
			BufferedReader javaFile = new BufferedReader(new FileReader("src\\Default\\RandomizedQueue.java"));
			String[] word = javaFile.readLine().split(" ");
			System.out.println(word[1]);
			while(word[1].equals("package") ||word[1].equals("import") || word[1].equals("")) {
				System.out.println("Import or package, ignore.");
				word[1] = javaFile.readLine().split(" ")[0];
			}
			if(word[2] == "class") {
				SmellyClass classy = new SmellyClass(javaFile);
			}
		} catch (Exception e) {
			System.out.println("Erro a abrir o ficheiro");
		}
	}
}
