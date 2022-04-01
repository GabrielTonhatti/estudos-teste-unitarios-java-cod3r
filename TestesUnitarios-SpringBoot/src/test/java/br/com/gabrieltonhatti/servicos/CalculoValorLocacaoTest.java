package br.com.gabrieltonhatti.servicos;

import br.com.gabrieltonhatti.daos.LocacaoDAO;
import br.com.gabrieltonhatti.entidades.Filme;
import br.com.gabrieltonhatti.entidades.Locacao;
import br.com.gabrieltonhatti.entidades.Usuario;
import br.com.gabrieltonhatti.exceptions.FilmeSemEstoqueException;
import br.com.gabrieltonhatti.exceptions.LocadoraException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static br.com.gabrieltonhatti.builders.FilmeBuilder.umFilme;
import static br.com.gabrieltonhatti.builders.UsuarioBuilder.umUsuario;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.runners.Parameterized.Parameter;
import static org.junit.runners.Parameterized.Parameters;

@SpringBootTest
@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {

    @InjectMocks
    private LocacaoService service;

    @Mock
    private LocacaoDAO dao;

    @Mock
    private  SPCService spc;

    @Parameter
    public List<Filme> filmes;

    @Parameter(value = 1)
    public Double valorLocacao;

    @Parameter(value = 2)
    public String cenario;

    private static final Filme filme1 = umFilme().agora();
    private static final Filme filme2 = umFilme().agora();
    private static final Filme filme3 = umFilme().agora();
    private static final Filme filme4 = umFilme().agora();
    private static final Filme filme5 = umFilme().agora();
    private static final Filme filme6 = umFilme().agora();
    private static final Filme filme7 = umFilme().agora();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        System.out.println("Iniciando 3...");
        CalculadoraTest.ordem.append("3");
    }

    @After
    public void tearDown() {
        System.out.println("Finalizando 3..");
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println(CalculadoraTest.ordem.toString());
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
    public void deveCalcularValorLocacaoConsiderandoDescontos() throws FilmeSemEstoqueException, LocadoraException, InterruptedException {
        // Cenário
        Usuario usuario = umUsuario().agora();

        Thread.sleep(5000);

        // Ação
        Locacao resultado = service.alugarFilme(usuario, filmes);

        // Verificação
        Assert.assertThat(resultado.getValor(), is(valorLocacao));

    }
}
