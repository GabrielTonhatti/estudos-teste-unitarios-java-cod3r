package br.com.gabrieltonhatti.builders;

import br.com.gabrieltonhatti.entidades.Usuario;

public class UsuarioBuilder {

    private Usuario usuario;

    private UsuarioBuilder() {

    }

    public static UsuarioBuilder umUsuario() {
        UsuarioBuilder builder = new UsuarioBuilder();
        builder.usuario = new Usuario();
        builder.usuario.setNome("Usuario 1");

        return builder;
    }

    public UsuarioBuilder comNome(String nome) {
        usuario.setNome(nome);

        return this;
    }

    public Usuario agora() {
        return this.usuario;
    }

}
