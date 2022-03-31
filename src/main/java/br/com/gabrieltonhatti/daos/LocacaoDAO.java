package br.com.gabrieltonhatti.daos;

import br.com.gabrieltonhatti.entidades.Locacao;

import java.util.List;

public interface LocacaoDAO {

    public void salvar(Locacao locacao);

    List<Locacao> obterLocacoesPendentes();
}
