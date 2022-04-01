package br.com.gabrieltonhatti.service;

import br.com.gabrieltonhatti.servicos.Calculadora;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CalculadoraMockTest {

    @Test
    public void teste() {
        Calculadora calc = mock(Calculadora.class);
        when(calc.somar(eq(1), anyInt()))
                .thenReturn(5);

        assertEquals(5, calc.somar(1, 8));
    }
}
