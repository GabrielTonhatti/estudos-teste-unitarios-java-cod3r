package br.com.gabrieltonhatti.service;

import br.com.gabrieltonhatti.entidades.Filme;
import br.com.gabrieltonhatti.entidades.Locacao;
import br.com.gabrieltonhatti.entidades.Usuario;
import br.com.gabrieltonhatti.exceptions.FilmeSemEstoqueException;
import br.com.gabrieltonhatti.exceptions.LocadoraException;
import br.com.gabrieltonhatti.servicos.LocacaoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
        this.service = new LocacaoService();
    }

    @Test
    public void deveAlugarFilme() throws Exception {
        // Cenário
        Usuario usuario = new Usuario("Usuário 1");
        List<Filme> filmes = List.of(new Filme("Filme 1", 1, 5.0));

        // Ação
        Locacao locacao = service.alugarFilme(usuario, filmes);

        // Verificação
        error.checkThat(locacao.getValor(), is(equalTo(5.0)));
        error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
        error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
    }

    // Forma elegante
    @Test
    public void naoDeveAlugarFilmeSemEstoque() throws Exception {
        // Equivalente ao @Test(excepted = Exception.class) do org.junit.Test
        assertThrows(FilmeSemEstoqueException.class, () -> {
            // Cenário
            Usuario usuario = new Usuario("Usuário 1");
            List<Filme> filmes = List.of(new Filme("Filme 1", 0, 4.0));

            // Ação
            service.alugarFilme(usuario, filmes);
        });
    }

    // Forma robusta
    @Test
    public void naoDeveAugarFilmeSemUsuario() throws FilmeSemEstoqueException {
        // Cenário
        List<Filme> filmes = List.of(new Filme("Filme 1", 1, 5.0));

        try {
            service.alugarFilme(null, filmes);
            fail();
        } catch (LocadoraException e) {
            assertThat(e.getMessage(), is("Usuário vazio"));
        }
    }

    // Forma nova
    @Test
    public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException {
        // Cenário
        Usuario usuario = new Usuario("Usuário 1");

        exception.expect(LocadoraException.class);
        exception.expectMessage("Filme vazio");

        // Ação
        service.alugarFilme(usuario, null);
    }

    @Test
    public void devePagar75PctNoFilme3() throws FilmeSemEstoqueException, LocadoraException {
        // Cenário
        Usuario usuario = new Usuario("Usuario 1");
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 1", 2, 4.0),
                new Filme("Filme 2", 2, 4.0),
                new Filme("Filme 3", 2, 4.0)
        );

        // Ação
        Locacao resultado = service.alugarFilme(usuario, filmes);

        // Verificação
        Assert.assertThat(resultado.getValor(), is(11.0));

    }

    @Test
    public void devePagar50PctNoFilme4() throws FilmeSemEstoqueException, LocadoraException {
        // Cenário
        Usuario usuario = new Usuario("Usuario 1");
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 1", 2, 4.0),
                new Filme("Filme 2", 2, 4.0),
                new Filme("Filme 3", 2, 4.0),
                new Filme("Filme 4", 2, 4.0)
        );

        // Ação
        Locacao resultado = service.alugarFilme(usuario, filmes);

        // Verificação
        Assert.assertThat(resultado.getValor(), is(13.0));

    }

    @Test
    public void devePagar25PctNoFilme5() throws FilmeSemEstoqueException, LocadoraException {
        // Cenário
        Usuario usuario = new Usuario("Usuario 1");
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 1", 2, 4.0),
                new Filme("Filme 2", 2, 4.0),
                new Filme("Filme 3", 2, 4.0),
                new Filme("Filme 4", 2, 4.0),
                new Filme("Filme 5", 2, 4.0)
        );

        // Ação
        Locacao resultado = service.alugarFilme(usuario, filmes);

        // Verificação
        Assert.assertThat(resultado.getValor(), is(14.0));

    }

    @Test
    public void devePagar0PctNoFilme6() throws FilmeSemEstoqueException, LocadoraException {
        // Cenário
        Usuario usuario = new Usuario("Usuario 1");
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 1", 2, 4.0),
                new Filme("Filme 2", 2, 4.0),
                new Filme("Filme 3", 2, 4.0),
                new Filme("Filme 4", 2, 4.0),
                new Filme("Filme 5", 2, 4.0),
                new Filme("Filme 6", 2, 4.0)
        );

        // Ação
        Locacao resultado = service.alugarFilme(usuario, filmes);

        // Verificação
        Assert.assertThat(resultado.getValor(), is(14.0));

    }


}
