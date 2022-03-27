package br.com.gabrieltonhatti.service;

import br.com.gabrieltonhatti.entidades.Filme;
import br.com.gabrieltonhatti.entidades.Locacao;
import br.com.gabrieltonhatti.entidades.Usuario;
import br.com.gabrieltonhatti.exceptions.FilmeSemEstoqueException;
import br.com.gabrieltonhatti.exceptions.LocadoraException;
import br.com.gabrieltonhatti.servicos.LocacaoService;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
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
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class LocacaoServiceTest {

    private LocacaoService service;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        System.out.println("Before");
        this.service = new LocacaoService();
    }

    @After
    public void tearDown() {
        System.out.println("After");
    }

    @Test
    public void testeLocacao() throws Exception {
        // Cenário
        Usuario usuario = new Usuario("Usuário 1");
        Filme filme = new Filme("Filme 1", 2, 5.0);

        System.out.println("Teste!");

        // Ação
        Locacao locacao = service.alugarFilme(usuario, filme);

        // Verificação
        error.checkThat(locacao.getValor(), is(equalTo(5.0)));
        error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
        error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
    }

    // Forma elegante
    @Test
    public void testLocacao_fimeSemEstoque() throws Exception {
        // Equivalente ao @Test(excepted = Exception.class) do org.junit.Test
        assertThrows(FilmeSemEstoqueException.class, () -> {
            // Cenário
            Usuario usuario = new Usuario("Usuário 1");
            Filme filme = new Filme("Filme 1", 0, 5.0);

            // Ação
            service.alugarFilme(usuario, filme);
        });
    }

    // Forma robusta
    @Test
    public void testeLocacao_usuarioVazio() throws FilmeSemEstoqueException {
        // Cenário
        Filme filme = new Filme("Filme 1", 1, 5.0);

        // Ação
        try {
            service.alugarFilme(null, filme);
            fail();
        } catch (LocadoraException e) {
            assertThat(e.getMessage(), is("Usuário vazio"));
        }

    }

    // Forma nova
    @Test
    public void testLocacao_filmeVazio() throws FilmeSemEstoqueException, LocadoraException {
        // Cenário
        Usuario usuario = new Usuario("Usuário 1");

        exception.expect(LocadoraException.class);
        exception.expectMessage("Filme vazio");

        // Ação
        service.alugarFilme(usuario, null);

    }

}
