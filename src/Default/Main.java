package Default;

import java.io.BufferedReader;
import java.io.FileReader;

public class Main {

	public Main(String args) {

	}

	public static void main(String[] args) {
		try {
			BufferedReader javaFile = new BufferedReader(new FileReader("src\\Default\\RandomizedQueue.java"));
			if(javaFile.readLine().split(" ")[0] != "import") {
				System.out.println("Hooray");
			}
		} catch (Exception e) {
			System.out.println("Erro a abrir o ficheiro");
		}
	}
}
