package br.com.gabrieltonhatti.servicos;

import br.com.gabrieltonhatti.entidades.Usuario;

public interface SPCService {

    boolean possuiNegativacao(Usuario usuario) throws Exception;

}
