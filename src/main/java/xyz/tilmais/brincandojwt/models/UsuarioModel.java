package xyz.tilmais.brincandojwt.models;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UsuarioModel {

    private Long id;

    @NotNull(message = "O campo nome é obrigatório.")
    private String nome;

    @NotNull(message = "O campo senha é obrigatório.")
    @Size(min = 8, max = 16, message = "A senha deve conter de 8 à 16 caracteres.")
    private String senha;

    @Digits(integer = 1, fraction = 0)
    private int situacao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSituacao() {
        return situacao;
    }

    public void setSituacao(int situacao) {
        this.situacao = situacao;
    }

    @Override
    public String toString() {
        return "RequestUsuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", senha='" + senha + '\'' +
                ", situacao=" + situacao +
                '}';
    }
}
