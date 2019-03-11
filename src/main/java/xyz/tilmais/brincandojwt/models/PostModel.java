package xyz.tilmais.brincandojwt.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PostModel {

    @NotNull(message = "O campo titulo é obrigatório!")
    @Size(max = 240, message = "O título deve possuir no máximo 240 caracteres")
    private String titulo;

    @NotNull(message = "O campo conteúdo é obrigatório")
    private String conteudo;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    @Override
    public String toString() {
        return "PostModel{" +
                "titulo='" + titulo + '\'' +
                ", conteudo='" + conteudo + '\'' +
                '}';
    }
}
