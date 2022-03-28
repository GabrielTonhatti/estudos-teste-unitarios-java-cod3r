package br.com.gabrieltonhatti.service;

import br.com.gabrieltonhatti.exceptions.NaoPodeDividirPorZeroException;
import br.com.gabrieltonhatti.servicos.Calculadora;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class CalculadoraTest {

    private Calculadora calc;

    @Before
    public void setup() {
        calc = new Calculadora();
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

}
