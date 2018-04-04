package br.com.senaijandira.controlefinanceiro;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import br.com.senaijandira.controlefinanceiro.Adapters.EstatisticasCategoriaAdapter;
import br.com.senaijandira.controlefinanceiro.DAO.CategoriaDAO;
import br.com.senaijandira.controlefinanceiro.DAO.LancamentoDAO;
import br.com.senaijandira.controlefinanceiro.Objetos.EstatisticaCategoria;
import br.com.senaijandira.controlefinanceiro.Objetos.Categoria;
import br.com.senaijandira.controlefinanceiro.Objetos.Lancamento;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriaFragment extends Fragment {

    ListView lista_estatistica_categoria;
    LancamentoDAO lancamentoDAO = LancamentoDAO.getInstance();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_categoria, container, false);

        ArrayList<EstatisticaCategoria> lista = new ArrayList<>();

        lista_estatistica_categoria = v.findViewById(R.id.lst_estatisticas_categoria);
        EstatisticasCategoriaAdapter adapter = new EstatisticasCategoriaAdapter(getContext(), lista);
        lista_estatistica_categoria.setAdapter(adapter);

        CategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
        ArrayList<Categoria> listaCategoria = categoriaDAO.selecionarTodos(getContext());

        for(Categoria categoria : listaCategoria){
            EstatisticaCategoria estatisticaCategoria = new EstatisticaCategoria();
            estatisticaCategoria.setImgCategoria(categoria.getFoto());
            estatisticaCategoria.setNomeCategoria(categoria.getNome());

            ArrayList<Lancamento> lancamentosPorCategoria = lancamentoDAO.selecionarPorCategoria(getContext() ,categoria.getId());

            Float totalCategoria = 0f;
            for(Lancamento lancamento : lancamentosPorCategoria){
                if(lancamento.getTipo().equals("Despesa")){
                    totalCategoria += lancamento.getSaldo();
                }

            }

            Float despesas = LancamentoDAO.getInstance().getTotalDespesas(getContext());
            Float porcentagem = totalCategoria * 100 / despesas;

            estatisticaCategoria.setGastoTotal(totalCategoria);
            estatisticaCategoria.setPorcentagem(porcentagem);

            lista.add(estatisticaCategoria);

        }

        /*Organiza a lista em ordem decrescente*/
        Collections.sort(lista, new Comparator<EstatisticaCategoria>() {
            @Override
            public int compare(EstatisticaCategoria estatisticaCategoria, EstatisticaCategoria t1) {
                if(estatisticaCategoria.getPorcentagem() == t1.getPorcentagem()){
                    return 0;
                } else if(estatisticaCategoria.getPorcentagem() > t1.getPorcentagem()){
                    return -1;
                } else {
                    return 1;
                }

            }
        });

        return v;

    }

}
