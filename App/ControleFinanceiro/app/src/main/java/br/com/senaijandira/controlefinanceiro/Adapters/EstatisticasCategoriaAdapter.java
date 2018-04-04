package br.com.senaijandira.controlefinanceiro.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import br.com.senaijandira.controlefinanceiro.Objetos.Categoria;
import br.com.senaijandira.controlefinanceiro.Objetos.EstatisticaCategoria;
import br.com.senaijandira.controlefinanceiro.R;

/**
 * Created by 17170124 on 28/03/2018.
 */

public class EstatisticasCategoriaAdapter extends ArrayAdapter<EstatisticaCategoria> {

    NumberFormat dinheiro_format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public EstatisticasCategoriaAdapter (Context ctx, ArrayList<EstatisticaCategoria> lista){
        super(ctx, 0, lista);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if(v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.lista_item_estatisticas_categoria, null);
        }

        EstatisticaCategoria estatisticaCategoria = getItem(position);

        ImageView img_categoria;
        TextView txt_nome_categoria;
        TextView txt_categoria_porcentagem;
        TextView txtTotal;

        img_categoria = v.findViewById(R.id.estatisticas_img_categoria);
        txt_nome_categoria = v.findViewById(R.id.estatisticas_nome_categoria);
        txt_categoria_porcentagem = v.findViewById(R.id.estatisticas_categoria_porcentagem);
        txtTotal = v.findViewById(R.id.txt_estatisticas_total);

        if(estatisticaCategoria.getImgCategoria() != null){
            img_categoria.setImageBitmap(estatisticaCategoria.getImgCategoria());
        } else {
            img_categoria.setImageResource(android.R.drawable.ic_menu_camera);
        }

        txt_nome_categoria.setText(estatisticaCategoria.getNomeCategoria());

        String str_porcentagem = String.format("%.2f" , estatisticaCategoria.getPorcentagem());
        txt_categoria_porcentagem.setText(str_porcentagem + "%");

        String str_total = dinheiro_format.format(estatisticaCategoria.getGastoTotal());

        txtTotal.setText(str_total);

        return v;
    }
}
