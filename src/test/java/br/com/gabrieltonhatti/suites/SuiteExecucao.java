package br.com.gabrieltonhatti.suites;

import br.com.gabrieltonhatti.service.CalculadoraTest;
import br.com.gabrieltonhatti.service.CalculoValorLocacaoTest;
import br.com.gabrieltonhatti.service.LocacaoServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static org.junit.runners.Suite.SuiteClasses;

//@RunWith(Suite.class)
@SuiteClasses({
        CalculadoraTest.class,
        LocacaoServiceTest.class,
        CalculoValorLocacaoTest.class
})
public class SuiteExecucao {

    // Remova se puder!
}
