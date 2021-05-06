package Default;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SmellyClassTest {
	
	private SmellyClass smelly;
	private BufferedReader javaFile;
	
	/*
	 * Estes testes s�o preparados para o ficheiro ParsingException.java
	 * S�o utilizados os valores disponibilizados pelo professor no ficheiro Code_Smells.xlsx para os testes
	 * Estes valores s�o comparados com os valores calculados pelas nossas fun��es
	 * Sabemos de antem�o atrav�s de testes Black-Box que, no nosso ficheiro excel, os IDs dos m�todos s�o 90-95
	 * No excel disponibilizado pelo professor, estes t�m os IDs 5-10
	 */

	@BeforeEach
	void setUp() throws Exception {
		smelly = new SmellyClass();
		javaFile = new BufferedReader(new FileReader("/EngenhariaDeSoftware/jasml_0.10/src/com/jasml/compiler/ParsingException.java"));
	}

	@Test
	void testNOM() {
		smelly.NOM(javaFile);
		assertEquals(6, 0);
		fail("Not yet implemented");
	}

	@Test
	void testWMC() {
		fail("Not yet implemented");
	}

	@Test
	void testLOC_Method() {
		fail("Not yet implemented");
	}

	@Test
	void testCYCLO_Method() {
		fail("Not yet implemented");
	}

	@Test
	void testLOC_Class() {
		fail("Not yet implemented");
	}

}
