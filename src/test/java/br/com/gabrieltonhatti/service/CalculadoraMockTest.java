package br.com.gabrieltonhatti.service;

import br.com.gabrieltonhatti.servicos.Calculadora;
import br.com.gabrieltonhatti.servicos.EmailService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CalculadoraMockTest {

    @Mock
    private Calculadora calcMock;

    @Spy
    private Calculadora calcSpy;

    @Mock
    private EmailService email;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void devoMostrarDiferencaEntreMockSpy() {
        when(calcMock.somar(1, 2))
                .thenReturn(5);

//        when(calcSpy.somar(1, 2))
//                .thenReturn(5);

        doReturn(5)
                .when(calcSpy)
                .somar(1, 2);

        doNothing()
                .when(calcSpy)
                .imprime();

        System.out.println("Mock: " + calcMock.somar(1, 2));
        System.out.println("Spy: " + calcSpy.somar(1, 2));

        System.out.println("Mock: ");
        calcMock.imprime();
        System.out.println("Spy: ");
        calcSpy.imprime();
    }

    @Test
    public void teste() {
        Calculadora calc = mock(Calculadora.class);
        ArgumentCaptor<Integer> argCapt = ArgumentCaptor.forClass(Integer.class);
        when(calc.somar(argCapt.capture(), argCapt.capture()))
                .thenReturn(5);

        assertEquals(5, calc.somar(1, 1000));
//        System.out.println(argCapt.getAllValues());
    }
}
