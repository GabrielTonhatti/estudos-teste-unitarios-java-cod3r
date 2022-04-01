package br.com.gabrieltonhatti.servicos;

import br.com.gabrieltonhatti.daos.LocacaoDAO;
import br.com.gabrieltonhatti.entidades.Filme;
import br.com.gabrieltonhatti.entidades.Locacao;
import br.com.gabrieltonhatti.entidades.Usuario;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static br.com.gabrieltonhatti.builders.FilmeBuilder.umFilme;
import static br.com.gabrieltonhatti.builders.UsuarioBuilder.umUsuario;
import static br.com.gabrieltonhatti.matchers.MatcherProprios.caiNumaSegunda;
import static br.com.gabrieltonhatti.utils.DataUtils.isMesmaData;
import static br.com.gabrieltonhatti.utils.DataUtils.obterData;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@SpringBootTest
@RunWith(PowerMockRunner.class)
@PrepareForTest({LocacaoService.class})
public class LocacaoServiceTest_PowerMock {

    @InjectMocks
    private LocacaoService service;

    @Mock
    private SPCService spc;

    @Mock
    private LocacaoDAO dao;

    @Mock
    private EmailService email;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        service = PowerMockito.spy(service);
        System.out.println("Iniciando 4...");
        CalculadoraTest.ordem.append("4");
    }

    @After
    public void tearDown() {
        System.out.println("Finalizando 4..");
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println(CalculadoraTest.ordem.toString());
    }

    @Test
    public void deveAlugarFilme() throws Exception {
        // Cenário
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = List.of(umFilme().comValor(5.0).agora());

        PowerMockito.whenNew(Date.class)
                .withNoArguments()
                .thenReturn(obterData(28, 4, 2017));

        // Ação
        Locacao locacao = service.alugarFilme(usuario, filmes);

        // Verificação
        error.checkThat(locacao.getValor(), is(equalTo(5.0)));
        error.checkThat(isMesmaData(locacao.getDataLocacao(), obterData(28, 4, 2017)), is(true));
        error.checkThat(isMesmaData(locacao.getDataRetorno(), obterData(29, 4, 2017)), is(true));
    }

    @Test
    public void deveDevolverNaSegundaAoAlugarNoSabado() throws Exception {
        // Cenário
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = List.of(umFilme().agora());

       PowerMockito.whenNew(Date.class)
                .withNoArguments()
                .thenReturn(obterData(29, 4, 2017));

        // Ação
        Locacao retorno = service.alugarFilme(usuario, filmes);

        // Verificação
        assertThat(retorno.getDataRetorno(), caiNumaSegunda());
    }

    @Test
    public void deveAlugarFilme_SemCalcularValor() throws Exception {
        // Cenário
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = List.of(umFilme().agora());

        PowerMockito
                .doReturn(1.0)
                .when(service, "calcularValorLocacao", filmes);

        // Ação
        Locacao locacao = service.alugarFilme(usuario, filmes);

        // Verificação
        assertThat(locacao.getValor(), is(1.0));
        PowerMockito
                .verifyPrivate(service)
                .invoke("calcularValorLocacao", filmes);
    }

    @Test
    public void deveCalcularValorLocacao() throws Exception {
        // Cenário
        List<Filme> filmes = List.of(umFilme().agora());

        // Ação
        Double valor = (Double) Whitebox
                .invokeMethod(service, "calcularValorLocacao", filmes);

        // Verificação
        assertThat(valor, is(4.0));
    }

}
