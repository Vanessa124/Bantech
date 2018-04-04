package br.com.senaijandira.controlefinanceiro.Objetos;

import android.graphics.Bitmap;

/**
 * Created by 17170124 on 28/03/2018.
 */

public class EstatisticaCategoria {
    private Bitmap imgCategoria;
    private String nomeCategoria;
    private Float gastoTotal;
    private Float porcentagem;

    public Bitmap getImgCategoria() {
        return imgCategoria;
    }

    public void setImgCategoria(Bitmap imgCategoria) {
        this.imgCategoria = imgCategoria;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
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
