package br.com.gabrieltonhatti.servicos;

import static br.com.gabrieltonhatti.utils.DataUtils.adicionarDias;

import java.util.Date;

import br.com.gabrieltonhatti.entidades.Filme;
import br.com.gabrieltonhatti.entidades.Locacao;
import br.com.gabrieltonhatti.entidades.Usuario;
import br.com.gabrieltonhatti.exceptions.FilmeSemEstoqueException;
import br.com.gabrieltonhatti.exceptions.LocadoraException;

public class LocacaoService {

    public Locacao alugarFilme(Usuario usuario, Filme filme) throws FilmeSemEstoqueException, LocadoraException {

        if (usuario == null) {
            throw new LocadoraException("Usuário vazio");
        }

        if (filme == null) {
            throw new LocadoraException("Filme vazio");
        }

        if (filme.getEstoque() == 0) {
            throw new FilmeSemEstoqueException();
        }

        Locacao locacao = new Locacao();
        locacao.setFilme(filme);
        locacao.setUsuario(usuario);
        locacao.setDataLocacao(new Date());
        locacao.setValor(filme.getPrecoLocacao());

        // Entrega no dia seguinte
        Date dataEntrega = new Date();
        dataEntrega = adicionarDias(dataEntrega, 1);
        locacao.setDataRetorno(dataEntrega);

        // Salvando a locacao...
        // TODO adicionar método para salvar

        return locacao;
    }

}