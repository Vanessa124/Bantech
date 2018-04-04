package br.com.senaijandira.controlefinanceiro.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import br.com.senaijandira.controlefinanceiro.Objetos.EstatisticaMes;
import br.com.senaijandira.controlefinanceiro.R;

/**
 * Created by 17170124 on 02/04/2018.
 */

public class EstatisticasMesAdapter extends ArrayAdapter<EstatisticaMes>{

    public EstatisticasMesAdapter(Context context, ArrayList<EstatisticaMes> lista) {
        super(context, 0, lista);
    }

    NumberFormat dinheiro_format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if(v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.lista_item_estatisticas_mes, null);
        }

        EstatisticaMes estatisticaMes = getItem(position);

        TextView txt_mes_ano;
        TextView txt_estatistica_mes_porcentagem;
        TextView txt_estatisticas_total;

        txt_mes_ano = v.findViewById(R.id.txt_mes_ano);
        txt_estatistica_mes_porcentagem = v.findViewById(R.id.estatisticas_mes_porcentagem);
        txt_estatisticas_total = v.findViewById(R.id.txt_estatisticas_total);

        txt_mes_ano.setText(estatisticaMes.getMes() + ", " + estatisticaMes.getAno());

        String str_porcentagem = String.format("%.2f", estatisticaMes.getPorcentagem());
        txt_estatistica_mes_porcentagem.setText(str_porcentagem + "%");

        String str_gasto_total = dinheiro_format.format(estatisticaMes.getGastoTotal());
        txt_estatisticas_total.setText(str_gasto_total);

        return v;
    }
}
