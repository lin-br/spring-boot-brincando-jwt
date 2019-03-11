package xyz.tilmais.brincandojwt.database.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Item {

    private Integer id;
    private String nome;
    private String descricao;
    private Integer quantidade;
    private BigDecimal preco;
    private Timestamp dataCriacao;
    private Timestamp dataAlteracao;
    private Timestamp dataExclusao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
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

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", quantidade=" + quantidade +
                ", preco=" + preco +
                ", dataCriacao=" + dataCriacao +
                ", dataAlteracao=" + dataAlteracao +
                ", dataExclusao=" + dataExclusao +
                '}';
    }
}
