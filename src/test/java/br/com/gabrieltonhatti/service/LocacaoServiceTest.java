package br.com.gabrieltonhatti.service;

import br.com.gabrieltonhatti.entidades.Filme;
import br.com.gabrieltonhatti.entidades.Locacao;
import br.com.gabrieltonhatti.entidades.Usuario;
import br.com.gabrieltonhatti.servicos.LocacaoService;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static br.com.gabrieltonhatti.utils.DataUtils.isMesmaData;
import static br.com.gabrieltonhatti.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class LocacaoServiceTest {

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testeLocacao() throws Exception {
        // Cenário
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Usuário 1");
        Filme filme = new Filme("Filme 1", 2, 5.0);

        // Ação
        Locacao locacao = service.alugarFilme(usuario, filme);

        // Verificação
        error.checkThat(locacao.getValor(), is(equalTo(6.0)));
        error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
        error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(false));
    }

    @Test
    public void testLocacao_fimeSemEstoque() throws Exception {
        // Equivalente ao @Test(excepted = Exception.class) do org.junit.Test
        assertThrows(Exception.class, () -> {
            // Cenário
            LocacaoService service = new LocacaoService();
            Usuario usuario = new Usuario("Usuário 1");
            Filme filme = new Filme("Filme 1", 0, 5.0);

            // Ação
            service.alugarFilme(usuario, filme);
        });
    }

    @Test
    public void testLocacao_fimeSemEstoque_2() {
        // Cenário
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Usuário 1");
        Filme filme = new Filme("Filme 1", 0, 5.0);

        // Ação
        try {
            service.alugarFilme(usuario, filme);
            Assertions.fail("Deveria ter lançado uma exceção");
        } catch (Exception e) {
            assertThat(e.getMessage(), is("Filme sem estoque"));
        }
    }

    @org.junit.Test
    public void testLocacao_fimeSemEstoque_3() throws Exception {
            // Cenário
            LocacaoService service = new LocacaoService();
            Usuario usuario = new Usuario("Usuário 1");
            Filme filme = new Filme("Filme 1", 0, 5.0);

            exception.expect(Exception.class);
            exception.expectMessage("Filme sem estoque");

            // Ação
            service.alugarFilme(usuario, filme);

    }

}
