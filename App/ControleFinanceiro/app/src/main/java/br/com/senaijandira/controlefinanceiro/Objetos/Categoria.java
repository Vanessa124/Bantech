package br.com.senaijandira.controlefinanceiro.Objetos;

import android.graphics.Bitmap;

/**
 * Created by 17170124 on 13/03/2018.
 */

public class Categoria {

    private String nome;
    private Bitmap foto;
    private Integer id;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
