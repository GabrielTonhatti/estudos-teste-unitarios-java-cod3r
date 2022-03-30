package br.com.gabrieltonhatti.service;

import br.com.gabrieltonhatti.entidades.Filme;
import br.com.gabrieltonhatti.entidades.Locacao;
import br.com.gabrieltonhatti.entidades.Usuario;
import br.com.gabrieltonhatti.exceptions.FilmeSemEstoqueException;
import br.com.gabrieltonhatti.exceptions.LocadoraException;
import br.com.gabrieltonhatti.servicos.LocacaoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.runners.Parameterized.Parameter;
import static org.junit.runners.Parameterized.Parameters;

@SpringBootTest
@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {

    @Parameter
    public List<Filme> filmes;

    @Parameter(value = 1)
    public Double valorLocacao;

    @Parameter(value = 2)
    public String cenario;

    private LocacaoService service;

    private static final Filme filme1 = new Filme("Filme 1", 2, 4.0);
    private static final Filme filme2 = new Filme("Filme 2", 2, 4.0);
    private static final Filme filme3 = new Filme("Filme 3", 2, 4.0);
    private static final Filme filme4 = new Filme("Filme 4", 2, 4.0);
    private static final Filme filme5 = new Filme("Filme 5", 2, 4.0);
    private static final Filme filme6 = new Filme("Filme 6", 2, 4.0);
    private static final Filme filme7 = new Filme("Filme 6", 2, 4.0);

    @Before
    public void setup() {
        this.service = new LocacaoService();
    }

    @Parameters(name = "{2}")
    public static Collection<Object[]> getParametros() {
        return Arrays.asList(new Object[][]{
                {Arrays.asList(filme1, filme2), 8.0, "2 Filmes: Sem Desconto"},
                {Arrays.asList(filme1, filme2, filme3), 11.0, "3 Filmes: 25%"},
                {Arrays.asList(filme1, filme2, filme3, filme4), 13.0, "4 Filmes: 50%"},
                {Arrays.asList(filme1, filme2, filme3, filme4, filme5), 14.0, "5 Filmes: 75%"},
                {Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 14.0, "6 Filmes: 100%"},
                {Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6, filme7), 18.0, "7 Filmes: Sem Desconto"},
        });
    }

    @Test
    public void deveCalcularValorLocacaoConsiderandoDescontos() throws FilmeSemEstoqueException, LocadoraException {
        // Cenário
        Usuario usuario = new Usuario("Usuario 1");

        // Ação
        Locacao resultado = service.alugarFilme(usuario, filmes);

        // Verificação
        Assert.assertThat(resultado.getValor(), is(valorLocacao));

    }
}
