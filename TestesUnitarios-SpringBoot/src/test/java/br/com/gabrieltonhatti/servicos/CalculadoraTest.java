package br.com.gabrieltonhatti.servicos;

import br.com.gabrieltonhatti.exceptions.NaoPodeDividirPorZeroException;
import org.junit.*;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CalculadoraTest {

    public static StringBuffer ordem = new StringBuffer();

    private Calculadora calc;

    @Before
    public void setup() {
        calc = new Calculadora();
        System.out.println("Iniciando...");
        ordem.append("1");
    }

    @After
    public void tearDown() {
        System.out.println("Finalizando...");
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println(ordem.toString());
    }

    @Test
    public void deveSomarDoisValores() {
        // Cenário
        int a = 5;
        int b = 3;

        // Ação
        int resultado = calc.somar(a, b);

        // Verificação
        Assertions.assertEquals(8, resultado);
    }

    @Test
    public void deveSubtrairDoisValores() {
        // Cenário
        int a = 8;
        int b = 5;

        // Ação
        int resultado = calc.subtrair(a, b);

        // Verificação
        Assertions.assertEquals(3, resultado);
    }

    @Test
    public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException {
        // Cenário
        int a = 6;
        int b = 3;

        // Ação
        int resultado = calc.divide(a, b);

        // Verificação
        Assertions.assertEquals(2, resultado);
    }

    @Test(expected = NaoPodeDividirPorZeroException.class)
    public void deveLancarExcecaoAoDividirPorZero() throws NaoPodeDividirPorZeroException {
        int a = 10;
        int b = 0;

        calc.divide(a, b);

    }

    @Test
    public void deveDividir() {
        String a = "6";
        String b = "3";

        int resultado = calc.divide(a, b);

        Assert.assertEquals(2, resultado);
    }

}
