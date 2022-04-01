package br.com.gabrieltonhatti.servicos;

import static br.com.gabrieltonhatti.utils.DataUtils.*;
import static br.com.gabrieltonhatti.utils.DataUtils.adicionarDias;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.gabrieltonhatti.daos.LocacaoDAO;
import br.com.gabrieltonhatti.entidades.Filme;
import br.com.gabrieltonhatti.entidades.Locacao;
import br.com.gabrieltonhatti.entidades.Usuario;
import br.com.gabrieltonhatti.exceptions.FilmeSemEstoqueException;
import br.com.gabrieltonhatti.exceptions.LocadoraException;
import br.com.gabrieltonhatti.utils.DataUtils;

public class LocacaoService {

    private LocacaoDAO dao;
    private SPCService spcService;
    private EmailService emailService;

    public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException {

        if (usuario == null) {
            throw new LocadoraException("Usuário vazio");
        }

        if (filmes == null || filmes.isEmpty()) {
            throw new LocadoraException("Filme vazio");
        }

        for (Filme filme : filmes) {
            if (filme.getEstoque() == 0) {
                throw new FilmeSemEstoqueException();
            }
        }

        boolean negativado;
        try {
            negativado = spcService.possuiNegativacao(usuario);
        } catch (Exception e) {
            throw new LocadoraException("Problemas com SPC, tente novamente");
        }
        if (negativado) {
            throw new LocadoraException("Usuário Negativado");
        }

        Locacao locacao = new Locacao();
        locacao.setFilmes(filmes);
        locacao.setUsuario(usuario);
        locacao.setDataLocacao(new Date());
        Double valorTotal = 0.0;
        for (int i = 0; i < filmes.size(); i++) {
            Filme filme = filmes.get(i);
            Double valorfilme = filme.getPrecoLocacao();

            switch (i) {
                case 2:
                    valorfilme = valorfilme * 0.75;
                    break;
                case 3:
                    valorfilme = valorfilme * 0.5;
                    break;
                case 4:
                    valorfilme = valorfilme * 0.25;
                    break;
                case 5:
                    valorfilme = 0.0;
            }

            valorTotal += valorfilme;
        }

        locacao.setValor(valorTotal);

        // Entrega no dia seguinte
        Date dataEntrega = new Date();
        dataEntrega = adicionarDias(dataEntrega, 1);

        if (verificarDiaSemana(dataEntrega, Calendar.SUNDAY)) {
            dataEntrega = adicionarDias(dataEntrega, 1);
        }

        locacao.setDataRetorno(dataEntrega);

        // Salvando a locacao...
        dao.salvar(locacao);

        return locacao;
    }

    public void notificarAtrasos() {
        List<Locacao> locacoes = dao.obterLocacoesPendentes();

        for (Locacao locacao : locacoes) {

            if (locacao.getDataRetorno().before(new Date())) {
                emailService.notificarAtraso(locacao.getUsuario());
            }

        }

    }

    public void prorrogarLocacao(Locacao locacao, int dias) {
        Locacao novaLocacao = new Locacao();
        novaLocacao.setUsuario(locacao.getUsuario());
        novaLocacao.setFilmes(locacao.getFilmes());
        novaLocacao.setDataLocacao(new Date());
        novaLocacao.setDataRetorno(obterDataComDiferencaDias(dias));
        novaLocacao.setValor(locacao.getValor() * dias);
        dao.salvar(novaLocacao);
    }

}