package br.com.senaijandira.controlefinanceiro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import br.com.senaijandira.controlefinanceiro.DAO.LancamentoDAO;
import br.com.senaijandira.controlefinanceiro.Objetos.Lancamento;

public class MainActivity extends AppCompatActivity {

    TextView txt_saldo;
    LancamentoDAO lancamentoDAO = LancamentoDAO.getInstance();
    Context ctx = this;

    NumberFormat dinheiro_format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_saldo = (TextView) findViewById(R.id.txtSaldo);

        Float saldo = 0f;
        Float receitas = 0f;
        Float despesas = 0f;

        ArrayList<Lancamento> listaLancamentos = lancamentoDAO.selecionarTodos(ctx);

        for(Lancamento lancamento : listaLancamentos){
            if(lancamento.getTipo().equals("Receita")){
                receitas += lancamento.getSaldo();
            }else if(lancamento.getTipo().equals("Despesa")){
                despesas += lancamento.getSaldo();
            }

        }

        saldo = receitas - despesas;
        String str_saldo = dinheiro_format.format(saldo);
        txt_saldo.setText(str_saldo);

    }

    public void abrirCategoria(View v){
        Intent intent = new Intent(this, CategoriaActivity.class);

        startActivity(intent);

    }

    public void abrirLancamento(View v){
        Intent intent = new Intent(this, LancamentoActivity.class);

        startActivity(intent);
    }

    public void abrirEstatisticas(View v){
        Intent intent = new Intent(this, EstatisticasActivity.class);
        startActivity(intent);

    }

}
