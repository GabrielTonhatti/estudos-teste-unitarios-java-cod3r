package br.com.gabrieltonhatti.servicos;

import br.com.gabrieltonhatti.entidades.Usuario;

public interface EmailService {

    public void notificarAtraso(Usuario usuario);

}
