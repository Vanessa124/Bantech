package br.com.senaijandira.controlefinanceiro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.senaijandira.controlefinanceiro.Adapters.CategoriaAdapter;
import br.com.senaijandira.controlefinanceiro.DAO.CategoriaDAO;
import br.com.senaijandira.controlefinanceiro.Objetos.Categoria;

public class CategoriaActivity extends AppCompatActivity {

    ListView lst_categorias;

    Context ctx = this;

    CategoriaDAO categoriaDAO = CategoriaDAO.getInstance();

    CategoriaAdapter adaptador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Categorias");

        lst_categorias = (ListView) findViewById(R.id.lst_categorias);

        adaptador = new CategoriaAdapter(this, new ArrayList<Categoria>());
        lst_categorias.setAdapter(adaptador);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        /*Evento do botão*/
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Mandando para a tela de Cadastro de categorias*/
                Intent intent = new Intent(ctx, CadastroCategoriaActivity.class);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }


    @Override
    protected void onResume() {
        super.onResume();

        atualizarListaCategoria();

    }

    public void atualizarListaCategoria(){
        ArrayList<Categoria> categoriasCadastradas;

        categoriasCadastradas = categoriaDAO.selecionarTodos(ctx);
        adaptador.clear();

        adaptador.addAll(categoriasCadastradas);
    }
}
