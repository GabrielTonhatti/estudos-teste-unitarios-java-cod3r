package br.com.gabrieltonhatti.service;

import br.com.gabrieltonhatti.entidades.Filme;
import br.com.gabrieltonhatti.entidades.Locacao;
import br.com.gabrieltonhatti.entidades.Usuario;
import br.com.gabrieltonhatti.servicos.LocacaoService;
import br.com.gabrieltonhatti.utils.DataUtils;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;

@SpringBootTest
public class LocacaoServiceTest {

    @Test
    public void teste() {
        // Cenário
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Usuário 1");
        Filme filme = new Filme("Filme 1", 2, 5.0);

        // Ação
        Locacao locacao = service.alugarFilme(usuario, filme);

        // Verificação

        // Equivalente ao Assert.assertThat
        MatcherAssert.assertThat(locacao.getValor(), is(5.0));
        Assertions.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
        Assertions.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));

    }

}
