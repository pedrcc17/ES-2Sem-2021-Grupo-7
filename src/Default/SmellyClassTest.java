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
	 * Estes testes são preparados para o ficheiro ParsingException.java
	 * São utilizados os valores disponibilizados pelo professor no ficheiro Code_Smells.xlsx para os testes
	 * Estes valores são comparados com os valores calculados pelas nossas funções
	 * Sabemos de antemão através de testes Black-Box que, no nosso ficheiro excel, os IDs dos métodos são 90-95
	 * No excel disponibilizado pelo professor, estes têm os IDs 5-10
	 */

	@BeforeEach
	void setUp() throws Exception {
		smelly = new SmellyClass();
		javaFile = new BufferedReader(new FileReader("C:\\Users\\andre\\git\\ES-2Sem-2021-Grupo-7\\jasml_0.10\\src\\com\\jasml\\compiler\\ParsingException.java"));
	}

	@Test
	void testNOM() {
		smelly.NOM(javaFile);
		assertEquals(6, smelly.getMethod(), "This class have 6 methods.");
	}

	@Test
	void testWMC() {
		smelly.WMC(javaFile);
		assertEquals(13, smelly.getWmcCount(), "This class have WMC = 13.");
	}

	@Test
	void testLOC_Method() {
		smelly.LOC_Method(javaFile);
		assertEquals(6, smelly.getLinesPerMethod(0), "The first method have 6 LOC.");
		assertEquals(5, smelly.getLinesPerMethod(1), "The second method have 5 LOC.");
		assertEquals(4, smelly.getLinesPerMethod(2), "The third method have 4 LOC.");
		assertEquals(3, smelly.getLinesPerMethod(3), "The fourth method have 3 LOC.");
		assertEquals(3, smelly.getLinesPerMethod(4), "The fifth method have 3 LOC.");
		assertEquals(21, smelly.getLinesPerMethod(5), "The sixth method have 21 LOC.");
	}

	@Test
	void testCYCLO_Method() {
		smelly.CYCLO_Method(javaFile);
		assertEquals(1, smelly.getCyclosPerMethod(0), "The first method have CYCLO = 1.");
		assertEquals(1, smelly.getCyclosPerMethod(1), "The second method have CYCLO = 1.");
		assertEquals(1, smelly.getCyclosPerMethod(2), "The third method have CYCLO = 1.");
		assertEquals(1, smelly.getCyclosPerMethod(3), "The fourth method have CYCLO = 1.");
		assertEquals(1, smelly.getCyclosPerMethod(4), "The fifth method have CYCLO = 1.");
		assertEquals(8, smelly.getCyclosPerMethod(5), "The sixth method have CYCLO = 8.");
	}

	@Test
	void testLOC_Class() {
		smelly.LOC_Class(javaFile);
		assertEquals(50, smelly.getLinesOfCode(), "This class have 50 LOC.");
	}

}
