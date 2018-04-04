package br.com.senaijandira.controlefinanceiro;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import br.com.senaijandira.controlefinanceiro.Adapters.EstatisticasMesAdapter;
import br.com.senaijandira.controlefinanceiro.DAO.LancamentoDAO;
import br.com.senaijandira.controlefinanceiro.Objetos.EstatisticaMes;
import br.com.senaijandira.controlefinanceiro.Objetos.Lancamento;


/**
 * A simple {@link Fragment} subclass.
 */
public class MesFragment extends Fragment {

    ListView lst_estatisticas_mes;
    LancamentoDAO lancamentoDAO = LancamentoDAO.getInstance();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_mes, container, false);

        lst_estatisticas_mes = v.findViewById(R.id.lst_estatisticas_mes);

        ArrayList<EstatisticaMes> lista = new ArrayList<>();
        EstatisticasMesAdapter adapter = new EstatisticasMesAdapter(getContext(), lista);
        lst_estatisticas_mes.setAdapter(adapter);

        ArrayList<Lancamento> listaLancamentos = lancamentoDAO.selecionarTodos(getContext());

        Calendar dt_lancamento = Calendar.getInstance();
        Float porcentagem = 0f;
        Float despesas = lancamentoDAO.getTotalDespesas(getContext());
        Float gastoTotal = 0f;

        for(Lancamento lancamento: listaLancamentos){
            Boolean referencia = true;
            if(lancamento.getTipo().equals("Despesa")) {
                dt_lancamento.setTime(lancamento.getDt_lancamento());
                String str_ano = String.valueOf(dt_lancamento.get(Calendar.YEAR));
                String str_mes = dt_lancamento.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

                for(EstatisticaMes e : lista) {
                    /*Compara se o mês e o ano ja existe na lista.
                    * Caso exista, o valor do gasto total será atualizado e a referência será falsa*/
                    if (e.getMes().equals(str_mes) && e.getAno().equals(str_ano)) {
                        Float saldo = e.getGastoTotal();
                        e.setGastoTotal(saldo + lancamento.getSaldo());
                        gastoTotal = e.getGastoTotal();
                        porcentagem = gastoTotal * 100 / despesas;
                        e.setPorcentagem(porcentagem);
                        referencia = false;
                        break;
                    }
                }

                /*A referencia é verdadeira quando o mês e o ano do lançamento ainda não existe na lista,
                * por isso ela esta sendo criada.*/
                if(referencia) {
                    EstatisticaMes estatisticaMes = new EstatisticaMes();
                    estatisticaMes.setAno(str_ano);
                    estatisticaMes.setMes(str_mes);
                    estatisticaMes.setGastoTotal(lancamento.getSaldo());

                    gastoTotal = estatisticaMes.getGastoTotal();
                    porcentagem = gastoTotal * 100 / despesas;
                    estatisticaMes.setPorcentagem(porcentagem);

                    lista.add(estatisticaMes);
                }
            }
        }

        return v;
    }



}
