package br.com.senaijandira.controlefinanceiro;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import br.com.senaijandira.controlefinanceiro.Adapters.LancamentoAdapter;
import br.com.senaijandira.controlefinanceiro.DAO.LancamentoDAO;
import br.com.senaijandira.controlefinanceiro.Objetos.Lancamento;

public class LancamentoActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, DatePickerDialog.OnDateSetListener{

    ListView lst_lancamentos;

    Context ctx = this;

    LancamentoAdapter adaptador;

    LancamentoDAO lancamentoDAO = LancamentoDAO.getInstance();

    Calendar calendar = Calendar.getInstance();

    String mesEscolhido = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
            + ", " + calendar.get(Calendar.YEAR);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lancamento);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lst_lancamentos = (ListView) findViewById(R.id.lst_lancamentos);
        adaptador = new LancamentoAdapter(this, new ArrayList<Lancamento>());
        lst_lancamentos.setAdapter(adaptador);

        setTitle(mesEscolhido);
        /*Evento do botão*/
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Mandando para a tela de cadastro*/
                Intent intent = new Intent(ctx, CadastroLancamentoActivity.class);
                startActivity(intent);
            }
        });

        /*Coloca um listener na lista*/
        lst_lancamentos.setOnItemClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    @Override
    protected void onResume() {
        super.onResume();
        atualizarListaLancamentos();

    }

    public void atualizarListaLancamentos(){
        ArrayList<Lancamento> lancamentosCadastrados;
        lancamentosCadastrados = lancamentoDAO.selecionarTodos(ctx);

        ArrayList<Lancamento> lancamentosMes = new ArrayList<>();

        Calendar dt_lancamento = Calendar.getInstance();

        for(Lancamento lancamento : lancamentosCadastrados){
            dt_lancamento.setTime(lancamento.getDt_lancamento());
            if(calendar.get(Calendar.MONTH) == dt_lancamento.get(Calendar.MONTH) && calendar.get(Calendar.YEAR) == dt_lancamento.get(Calendar.YEAR)){
                lancamentosMes.add(lancamento);
            }
        }

        adaptador.clear();
        adaptador.addAll(lancamentosMes);
    }

    /*Trata o listener na listview*/
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Lancamento item = adaptador.getItem(i);

        Intent intent = new Intent(ctx, VisualizarActivity.class);

        intent.putExtra("idLancamento", item.getId());

        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_lancamento, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.icone_calendario){

            final DatePickerDialog datePickerDialog = new DatePickerDialog(ctx, android.R.style.Theme_Holo_Dialog, this, calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.getDatePicker().findViewById(getResources().getIdentifier("day","id","android")).setVisibility(View.GONE);
            datePickerDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
        calendar.set(Calendar.YEAR, ano);
        calendar.set(Calendar.DAY_OF_MONTH, dia);
        calendar.set(Calendar.MONTH, mes);

        mesEscolhido = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        mesEscolhido += ", " + ano;

        getSupportActionBar().setTitle(mesEscolhido);
        atualizarListaLancamentos();

    }
}
