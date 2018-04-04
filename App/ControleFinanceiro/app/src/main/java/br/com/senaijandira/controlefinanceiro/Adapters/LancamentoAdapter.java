package br.com.senaijandira.controlefinanceiro.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import br.com.senaijandira.controlefinanceiro.DAO.CategoriaDAO;
import br.com.senaijandira.controlefinanceiro.LancamentoActivity;
import br.com.senaijandira.controlefinanceiro.Objetos.Categoria;
import br.com.senaijandira.controlefinanceiro.Objetos.Lancamento;
import br.com.senaijandira.controlefinanceiro.R;

/**
 * Created by 17170124 on 13/03/2018.
 */

public class LancamentoAdapter extends ArrayAdapter<Lancamento> {

    /*Construtor da Classe*/
    public LancamentoAdapter (Context ctx, ArrayList<Lancamento> lista){
        super(ctx, 0, lista);
    }

    NumberFormat dinheiro_format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        if(v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.lista_lancamento_item, null);
        }

        Lancamento lancamento = getItem(position);

        ImageView img_categoria = v.findViewById(R.id.view_img_cat);
        TextView nome_categoria = v.findViewById(R.id.view_txt_nome_cat);
        TextView txt_saldo = v.findViewById(R.id.view_txt_saldo);
        TextView txt_tipo_lancamento = v.findViewById(R.id.txt_tipo_lancamento);

        String str_saldo = dinheiro_format.format(lancamento.getSaldo());
        txt_saldo.setText(str_saldo);

        if(lancamento.getTipo().equals("Receita")){
            txt_tipo_lancamento.setText("Recebido");
            txt_tipo_lancamento.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        } else if(lancamento.getTipo().equals("Despesa")){
            txt_tipo_lancamento.setText("Pago");
            txt_tipo_lancamento.setTextColor(ContextCompat.getColor(getContext(), R.color.vermelho));
        }

        CategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
        Categoria categoria = categoriaDAO.selecionarUm(lancamento.getIdCategoria(), getContext());
        nome_categoria.setText(categoria.getNome());

        if(categoria.getFoto() != null){
            img_categoria.setImageBitmap(categoria.getFoto());
        } else {
            img_categoria.setImageResource(android.R.drawable.ic_menu_camera);
        }

        LancamentoActivity activiy = (LancamentoActivity) getContext();
        activiy.atualizarListaLancamentos();

        return v;


    }
}
