package br.com.gabrieltonhatti.service;

import br.com.gabrieltonhatti.exceptions.NaoPodeDividirPorZeroException;
import br.com.gabrieltonhatti.servicos.Calculadora;
import org.junit.Assert;
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

    @Test
    public void deveDividir() {
        String a = "6";
        String b = "3";

        int resultado = calc.divide(a, b);

        Assert.assertEquals(2, resultado);
    }

    public static void main(String[] args) {
        new Calculadora().divide("a", "b");
    }

}
