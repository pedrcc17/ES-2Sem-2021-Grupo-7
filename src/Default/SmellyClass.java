package Default;

import java.io.BufferedReader;

public class SmellyClass {
	private BufferedReader javaFile;

	public SmellyClass(BufferedReader in) {
		javaFile = in;
		startSmelling();
	}

	private void startSmelling() {
		//NOM
		NOM nom = new NOM(javaFile);
		nom.run();
		//TODO
	}
}
