package br.com.senaijandira.controlefinanceiro.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.senaijandira.controlefinanceiro.CadastroCategoriaActivity;
import br.com.senaijandira.controlefinanceiro.CategoriaActivity;
import br.com.senaijandira.controlefinanceiro.DAO.CategoriaDAO;
import br.com.senaijandira.controlefinanceiro.DAO.LancamentoDAO;
import br.com.senaijandira.controlefinanceiro.Objetos.Categoria;
import br.com.senaijandira.controlefinanceiro.Objetos.Lancamento;
import br.com.senaijandira.controlefinanceiro.R;

/**
 * Created by 17170124 on 13/03/2018.
 */

public class CategoriaAdapter extends ArrayAdapter<Categoria>{


    /*Construtor da Classe*/
    public CategoriaAdapter (Context ctx, ArrayList<Categoria> lista){
        super(ctx, 0, lista);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        if(v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.lista_categoria_item, null);
        }

        final Categoria categoria = getItem(position);

        ImageView img_categoria = v.findViewById(R.id.img_categoria);
        TextView txt_nome_categoria = v.findViewById(R.id.txt_nome_categoria);

        ImageView img_excluir_categoria = v.findViewById(R.id.img__excluir_categoria);
        ImageView img_editar_categoria = v.findViewById(R.id.img_editar_categoria);

        img_excluir_categoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                LancamentoDAO lancamentoDAO = LancamentoDAO.getInstance();
                ArrayList<Lancamento> listaLancamento = lancamentoDAO.selecionarTodos(getContext());

                for(Lancamento lancamento : listaLancamento){
                    if(lancamento.getIdCategoria() == categoria.getId()){
                        builder.setTitle("Impossível excluir.");
                        builder.setMessage("Essa categoria possui lançamentos.");
                        builder.setPositiveButton("OK", null);
                        builder.create().show();

                        return;
                    }
                }

                builder.setTitle("Excluir categoria");
                builder.setMessage("Tem certeza que deseja excluir essa categoria? ");

                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
                        categoriaDAO.excluir(categoria.getId(), getContext());

                        CategoriaActivity activity = (CategoriaActivity) getContext();
                        activity.atualizarListaCategoria();

                    }
                });

                builder.setNegativeButton("Não", null);

                builder.create().show();

            }
        });


        img_editar_categoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Mandando para a tela que edita a categoria, passando o id da categoria*/
                Intent intent = new Intent(getContext(), CadastroCategoriaActivity.class);
                intent.putExtra("idCategoria", categoria.getId());
                getContext().startActivity(intent);
            }
        });


        txt_nome_categoria.setText(categoria.getNome());

        if(categoria.getFoto() != null) {
            img_categoria.setImageBitmap(categoria.getFoto());
        } else {
            img_categoria.setImageResource(android.R.drawable.ic_menu_camera);
        }

        return v;

    }



}
