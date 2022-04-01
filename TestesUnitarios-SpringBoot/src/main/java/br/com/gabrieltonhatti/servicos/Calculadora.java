package br.com.gabrieltonhatti.servicos;

import br.com.gabrieltonhatti.exceptions.NaoPodeDividirPorZeroException;

public class Calculadora {


    public int somar(int a, int b) {
        System.out.println("Estou executando o método somar");
        return a + b;
    }

    public int subtrair(int a, int b) {
        return a - b;
    }

    public int divide(int a, int b) throws NaoPodeDividirPorZeroException {
        if (b == 0) {
            throw new NaoPodeDividirPorZeroException();
        }

        return a / b;
    }

    public void imprime() {
        System.out.println("Passei aqui");
    }

    public int divide(String a, String b) {
        return Integer.valueOf(a) / Integer.valueOf(b);
    }
}
