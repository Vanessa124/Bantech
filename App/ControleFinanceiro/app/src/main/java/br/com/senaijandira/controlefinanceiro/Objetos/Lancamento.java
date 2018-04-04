package br.com.senaijandira.controlefinanceiro.Objetos;

import java.util.Date;

/**
 * Created by 17170124 on 13/03/2018.
 */

public class Lancamento {

    private int id;
    private float saldo;
    private Date dt_lancamento;
    private String descricao;
    private int idCategoria;
    private String tipo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public Date getDt_lancamento() {
        return dt_lancamento;
    }

    public void setDt_lancamento(Date dt_lancamento) {
        this.dt_lancamento = dt_lancamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
