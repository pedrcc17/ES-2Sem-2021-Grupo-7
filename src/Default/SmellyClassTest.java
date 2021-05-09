package Default;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
/**
 * Unit Tests
 * @author G7 de ES LEI 2020/2021
 *
 */
class SmellyClassTest {
	
	private SmellyClass smelly;
	private BufferedReader javaFile;
	
	/*
	 * Estes testes são preparados para o ficheiro ParsingException.java
	 * São utilizados os valores disponibilizados pelo professor no ficheiro Code_Smells.xlsx para os testes
	 * Estes valores são comparados com os valores calculados pelas nossas funções
	 */

	/**
	 * Sets up SmellyClass before each test
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		smelly = new SmellyClass();
		javaFile = new BufferedReader(new FileReader("jasml_0.10/src/com/jasml/compiler/ParsingException.java"));
	}

	/**
	 * Tests the method NOM from SmellyClass
	 */
	@Test
	@DisplayName("Verificação da contagem de número de métodos")
	void testNOM() {
		smelly.NOM(javaFile);
		assertEquals(6, smelly.getMethod(), "This class have 6 methods.");
	}

	/**
	 * Tests the method WMC from SmellyClass
	 */
	@Test
	@DisplayName("Verificação do WMC")
	void testWMC() {
		smelly.WMC(javaFile);
		assertEquals(13, smelly.getWmcCount(), "This class have WMC = 13.");
	}

	/**
	 * Tests the method LOC_Method from SmellyClass
	 */
	@Test
	@DisplayName("Verificação da contagem de linhas de código por método")
	void testLOC_Method() {
		smelly.LOC_Method(javaFile);
		assertEquals(6, smelly.getLinesPerMethod(0), "The first method have 6 LOC.");
		assertEquals(5, smelly.getLinesPerMethod(1), "The second method have 5 LOC.");
		assertEquals(4, smelly.getLinesPerMethod(2), "The third method have 4 LOC.");
		assertEquals(3, smelly.getLinesPerMethod(3), "The fourth method have 3 LOC.");
		assertEquals(3, smelly.getLinesPerMethod(4), "The fifth method have 3 LOC.");
		assertEquals(21, smelly.getLinesPerMethod(5), "The sixth method have 21 LOC.");
	}

	/**
	 * Tests the method CYCLO_Method from SmellyClass
	 */
	@Test
	@DisplayName("Verificação do CYCLO por método")
	void testCYCLO_Method() {
		smelly.CYCLO_Method(javaFile);
		assertEquals(1, smelly.getCyclosPerMethod(0), "The first method have CYCLO = 1.");
		assertEquals(1, smelly.getCyclosPerMethod(1), "The second method have CYCLO = 1.");
		assertEquals(1, smelly.getCyclosPerMethod(2), "The third method have CYCLO = 1.");
		assertEquals(1, smelly.getCyclosPerMethod(3), "The fourth method have CYCLO = 1.");
		assertEquals(1, smelly.getCyclosPerMethod(4), "The fifth method have CYCLO = 1.");
		assertEquals(8, smelly.getCyclosPerMethod(5), "The sixth method have CYCLO = 8.");
	}

	/**
	 * Tests the method LOC_Class from SmellyClass
	 */
	@Test
	@DisplayName("Verificação da contagem de linhas de código da classe")
	void testLOC_Class() {
		smelly.LOC_Class(javaFile);
		assertEquals(50, smelly.getLinesOfCode(), "This class have 50 LOC.");
	}
	
	/**
	 * Tests the correct name of the classes
	 */
	@Test
	@DisplayName("Verificação do nome dos métodos")
	void testMethodNames() {
		smelly.NOM(javaFile);
		assertEquals("ParsingException", smelly.getMethodNames(0), "The first method name is ParsingException(int,int,int,String)");
		assertEquals("ParsingException", smelly.getMethodNames(1), "The second method name is ParsingException(int,int,String)");
		assertEquals("ParsingException", smelly.getMethodNames(2), "The third method name is ParsingException(int,String)");
		assertEquals("ParsingException", smelly.getMethodNames(3), "The fourth method name is ParsingException(String,Exception)");
		assertEquals("ParsingException", smelly.getMethodNames(4), "The fifth method name is ParsingException(String)");
		assertEquals("getMessage", smelly.getMethodNames(5), "The sixth method name is getMessage()");
	}

}
