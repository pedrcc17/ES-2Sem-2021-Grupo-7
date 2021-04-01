package Default;

import java.io.BufferedReader;
import java.io.FileReader;

public class Main {

    private static BufferedReader restartReading() {
        try {
            BufferedReader javaFile = new BufferedReader(new FileReader("src\\Default\\RandomizedQueue.java"));
            String[] word = javaFile.readLine().split(" ");
            while (word[0].equals("package") || word[0].equals("import") || word[0].equals("")) {
                word = javaFile.readLine().split(" ");
            }
            if (word[1].equals("class")) {
                return javaFile;
            }
        } catch (Exception e) {
            System.out.println("Erro a abrir o ficheiro");
        }
        return null;
    }

    public static void main(String[] args) {
        SmellyClass classy = new SmellyClass();
        classy.NOM(restartReading());
        System.out.println("-------------------------");
        classy.WMC(restartReading());
        System.out.println("-------------------------");
        classy.CYCLO_Method(restartReading());
        System.out.println("-------------------------");
        classy.LOC_Method(restartReading());
        System.out.println("-------------------------");
        classy.LOC_Class(restartReading());
    }
}