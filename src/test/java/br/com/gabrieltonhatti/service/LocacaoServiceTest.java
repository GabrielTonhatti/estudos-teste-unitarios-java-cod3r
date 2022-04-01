package br.com.gabrieltonhatti.service;

import br.com.gabrieltonhatti.daos.LocacaoDAO;
import br.com.gabrieltonhatti.entidades.Filme;
import br.com.gabrieltonhatti.entidades.Locacao;
import br.com.gabrieltonhatti.entidades.Usuario;
import br.com.gabrieltonhatti.exceptions.FilmeSemEstoqueException;
import br.com.gabrieltonhatti.exceptions.LocadoraException;
import br.com.gabrieltonhatti.servicos.EmailService;
import br.com.gabrieltonhatti.servicos.LocacaoService;
import br.com.gabrieltonhatti.servicos.SPCService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static br.com.gabrieltonhatti.builders.FilmeBuilder.umFilme;
import static br.com.gabrieltonhatti.builders.FilmeBuilder.umFilmeSemEstoque;
import static br.com.gabrieltonhatti.builders.LocacaoBuilder.umLocacao;
import static br.com.gabrieltonhatti.builders.UsuarioBuilder.umUsuario;
import static br.com.gabrieltonhatti.matchers.MatcherProprios.*;
import static br.com.gabrieltonhatti.utils.DataUtils.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LocacaoServiceTest {

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
    }

    @Test
    public void deveAlugarFilme() throws Exception {
        assumeFalse(verificarDiaSemana(new Date(), Calendar.SATURDAY));

        // Cenário
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = List.of(umFilme().comValor(5.0).agora());

        // Ação
        Locacao locacao = service.alugarFilme(usuario, filmes);

        // Verificação
        error.checkThat(locacao.getValor(), is(equalTo(5.0)));
        error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
        error.checkThat(locacao.getDataLocacao(), ehHoje());
        error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
        error.checkThat(locacao.getDataRetorno(), ehHojeComDiferencaDias(1));
    }

    // Forma elegante
    @Test
    public void naoDeveAlugarFilmeSemEstoque() throws Exception {
        // Equivalente ao @Test(excepted = Exception.class) do org.junit.Test
        assertThrows(FilmeSemEstoqueException.class, () -> {
            // Cenário
            Usuario usuario = umUsuario().agora();
            List<Filme> filmes = List.of(umFilmeSemEstoque().agora());

            // Ação
            service.alugarFilme(usuario, filmes);
        });
    }

    // Forma robusta
    @Test
    public void naoDeveAugarFilmeSemUsuario() throws FilmeSemEstoqueException {
        // Cenário
        List<Filme> filmes = List.of(umFilme().agora());

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
        Usuario usuario = umUsuario().agora();

        exception.expect(LocadoraException.class);
        exception.expectMessage("Filme vazio");

        // Ação
        service.alugarFilme(usuario, null);
    }

    @Test
    public void deveDevolverNaSegundaAoAlugarNoSabado() throws Exception {
        // Cenário
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = List.of(umFilme().agora());

        // Ação
        Locacao retorno = service.alugarFilme(usuario, filmes);

        // Verificação
        assertThat(retorno.getDataRetorno(), caiNumaSegunda());
    }

    @Test
    public void naoDeveAludarFilmeParaNegativadoSPC() throws Exception {
        // Cenário
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = List.of(umFilme().agora());

        when(spc.possuiNegativacao(any(Usuario.class)))
                .thenReturn(true);

        // Ação
        try {
            service.alugarFilme(usuario, filmes);
            // Verificação
            Assert.fail();
        } catch (LocadoraException e) {
            assertThat(e.getMessage(), is("Usuário Negativado"));
        }

        verify(spc).possuiNegativacao(usuario);
    }

    @Test
    public void deveEnviarEmailParaLocacoesAtrasadas() {
        // Cenário
        Usuario usuario = umUsuario().agora();
        Usuario usuario2 = umUsuario().comNome("Usuario em dia").agora();
        Usuario usuario3 = umUsuario().comNome("Outro atrasado").agora();
        List<Locacao> locacoes = List.of(
                umLocacao()
                        .comUsuario(usuario)
                        .atrasado()
                        .agora(),
                umLocacao()
                        .comUsuario(usuario2)
                        .agora(),
                umLocacao()
                        .comUsuario(usuario3)
                        .atrasado()
                        .agora(),
                umLocacao()
                        .comUsuario(usuario3)
                        .atrasado()
                        .agora()
        );

        when(dao.obterLocacoesPendentes())
                .thenReturn(locacoes);

        // Ação
        service.notificarAtrasos();

        // Verificação
        verify(email, times(3)).notificarAtraso(any(Usuario.class));
        verify(email).notificarAtraso(usuario);
        verify(email, atLeastOnce()).notificarAtraso(usuario3);
        verify(email, never()).notificarAtraso(usuario2);
        verifyNoMoreInteractions(email);
        verifyNoInteractions(spc);
    }

    @Test
    public void deveTratarErroNoSPC() throws Exception {
        // Cenário
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = List.of(
                umFilme().agora()
        );

        when(spc.possuiNegativacao(usuario))
                .thenThrow(new Exception("Falha catastrófica"));

        // Verificação
        exception.expect(LocadoraException.class);
        exception.expectMessage("Problemas com SPC, tente novamente");

        // Ação
        service.alugarFilme(usuario, filmes);
    }

    @Test
    public void deveProrrogarUmaLocacao() {
        // Cenário
        Locacao locacao = umLocacao().agora();

        // Ação
        service.prorrogarLocacao(locacao, 3);

        // Verificação
        ArgumentCaptor<Locacao> argCapt = ArgumentCaptor.forClass(Locacao.class);
        verify(dao).salvar(argCapt.capture());
        Locacao locacaoRetornada = argCapt.getValue();

        error.checkThat(locacaoRetornada.getValor(), is(12.0));
        error.checkThat(locacaoRetornada.getDataLocacao(), ehHoje());
        error.checkThat(locacaoRetornada.getDataRetorno(), ehHojeComDiferencaDias(3));
    }

}
