package br.com.gabrieltonhatti.suites;

import br.com.gabrieltonhatti.servicos.CalculoValorLocacaoTest;
import br.com.gabrieltonhatti.servicos.LocacaoServiceTest;

import static org.junit.runners.Suite.SuiteClasses;

//@RunWith(Suite.class)
@SuiteClasses({
        LocacaoServiceTest.class,
        CalculoValorLocacaoTest.class
})
public class SuiteExecucao {

    // Remova se puder!
}
