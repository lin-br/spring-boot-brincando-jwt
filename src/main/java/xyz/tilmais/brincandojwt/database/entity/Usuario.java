package xyz.tilmais.brincandojwt.database.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Usuario {

    private Long id;
    private String nome;
    private String senha;
    private Integer situacao;
    private Timestamp dataCriacao;
    private Timestamp dataAlteracao;
    private Timestamp dataExclusao;
    private List<GrantedAuthority> listaRegras = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getSituacao() {
        return situacao;
    }

    public void setSituacao(Integer situacao) {
        this.situacao = situacao;
    }

    public Timestamp getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Timestamp dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Timestamp getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Timestamp dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public Timestamp getDataExclusao() {
        return dataExclusao;
    }

    public void setDataExclusao(Timestamp dataExclusao) {
        this.dataExclusao = dataExclusao;
    }

    public List<GrantedAuthority> getListaRegras() {
        return Collections.unmodifiableList(this.listaRegras);
    }

    public void adicionarRegra(String regra) {
        this.listaRegras.add(new SimpleGrantedAuthority(regra));
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", senha='" + senha + '\'' +
                ", situacao=" + situacao +
                ", dataCriacao=" + dataCriacao +
                ", dataAlteracao=" + dataAlteracao +
                ", dataExclusao=" + dataExclusao +
                ", listaRegras=" + listaRegras +
                '}';
    }
}
