package br.com.senaijandira.controlefinanceiro.Objetos;

/**
 * Created by 17170124 on 02/04/2018.
 */

public class EstatisticaMes {

    private String mes;
    private String ano;
    private Float gastoTotal;
    private Float porcentagem;

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public Float getGastoTotal() {
        return gastoTotal;
    }

    public void setGastoTotal(Float gastoTotal) {
        this.gastoTotal = gastoTotal;
    }

    public Float getPorcentagem() {
        return porcentagem;
    }

    public void setPorcentagem(Float porcentagem) {
        this.porcentagem = porcentagem;
    }
}
