package br.com.senaijandira.controlefinanceiro;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.senaijandira.controlefinanceiro.DAO.CategoriaDAO;
import br.com.senaijandira.controlefinanceiro.DAO.LancamentoDAO;
import br.com.senaijandira.controlefinanceiro.Objetos.Categoria;
import br.com.senaijandira.controlefinanceiro.Objetos.Lancamento;

public class CadastroLancamentoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    EditText txt_saldo_cadastro;
    EditText txt_dtLancamento_cadastro;
    EditText txt_descricao_cadastro;

    Spinner spinner_tipo_cadastro;
    Spinner spinner_categoria_cadastro;

    Button btn_lancamento;

    List<String> tipo_lancamento = new ArrayList<String>();
    List<String> categoria_lancamento = new ArrayList<String>();

    String tipo;

    SimpleDateFormat dt_format = new SimpleDateFormat("dd-MM-yyyy");

    int posicaoCategoria;

    Context ctx = this;

    Calendar calendar = Calendar.getInstance();

    Boolean atualizarLancamento = false;

    LancamentoDAO lancamentoDAO = LancamentoDAO.getInstance();
    Lancamento lancamentoAtualizar = null;
    Lancamento lancamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_lancamento);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txt_saldo_cadastro = (EditText) findViewById(R.id.txt_saldo_cadastro);
        txt_dtLancamento_cadastro = (EditText) findViewById(R.id.txt_dtLancamento_cadastro);
        txt_descricao_cadastro = (EditText) findViewById(R.id.txt_descricao_cadastro);

        btn_lancamento = (Button) findViewById(R.id.bnt_lancamento);

        spinner_tipo_cadastro = (Spinner) findViewById(R.id.spinner_tipo_cadastro);
        spinner_categoria_cadastro = (Spinner) findViewById(R.id.spinner_categoria_cadastro);



        /*Desabilita o teclado*/
        txt_dtLancamento_cadastro.setKeyListener(null);

        /*Evento do txt_dtLancamento_cadastro*/
        final DatePickerDialog datePickerDialog = new DatePickerDialog(ctx, this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        txt_dtLancamento_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });


        /*Inserindo itens no spinner do tipo de lançamento*/
        tipo_lancamento.add("Receita");
        tipo_lancamento.add("Despesa");

        ArrayAdapter<String> adaptadorTipo = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, tipo_lancamento);

        adaptadorTipo.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        spinner_tipo_cadastro.setAdapter(adaptadorTipo);

        /*Inserindo itens no spinner da categoria*/

        final CategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
        final ArrayList<Categoria> listaCategoriaDao = categoriaDAO.selecionarTodos(ctx);

        for(Categoria categoria : listaCategoriaDao){
            categoria_lancamento.add(categoria.getNome());

        }


        ArrayAdapter<String> adaptadorCategoria = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, categoria_lancamento);
        adaptadorCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_categoria_cadastro.setAdapter(adaptadorCategoria);

        /*Verifica se esta editando ou cadastrando um lançamento*/
        Intent intent = getIntent();
        int idLancamento = intent.getIntExtra("idLancamento", 0);

        if(idLancamento == 0){
            setTitle("Cadastro de Lançamentos");
            atualizarLancamento = false;
        } else {
            setTitle("Editar Lançamento");
            lancamentoAtualizar = lancamentoDAO.selecionarUm(idLancamento, ctx);

            txt_saldo_cadastro.setText(String.valueOf(lancamentoAtualizar.getSaldo()));

            Date data = lancamentoAtualizar.getDt_lancamento();
            String str_data = dt_format.format(data);
            txt_dtLancamento_cadastro.setText(str_data);

            txt_descricao_cadastro.setText(lancamentoAtualizar.getDescricao());

            if(lancamentoAtualizar.getTipo().equals("Receita")){
                spinner_tipo_cadastro.setSelection(0);
            } else if(lancamentoAtualizar.getTipo().equals("Despesa")){
                spinner_tipo_cadastro.setSelection(1);
            }

            spinner_categoria_cadastro.setSelection(lancamentoAtualizar.getIdCategoria() - 1);

            atualizarLancamento = true;
            btn_lancamento.setText("Salvar alterações");
        }

        /*Evento do botão de salvar o lançamento ou as alterações*/
        btn_lancamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txt_saldo_cadastro.getText().toString().equals("")){
                    txt_saldo_cadastro.setError("Preencha esse campo!");
                    return;
                } else if(txt_dtLancamento_cadastro.getText().toString().equals("")){
                    txt_dtLancamento_cadastro.setError("Preencha esse campo!");
                    return;
                }

                if(atualizarLancamento){
                    lancamento = lancamentoAtualizar;
                } else {
                    lancamento = new Lancamento();
                }

                String str_saldo = txt_saldo_cadastro.getText().toString();
                Float saldo = Float.parseFloat(str_saldo);
                lancamento.setSaldo(saldo);

                String str_dt_lancamento = txt_dtLancamento_cadastro.getText().toString();
                try {
                    Date data = dt_format.parse(str_dt_lancamento);
                    lancamento.setDt_lancamento(data);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                lancamento.setDescricao(txt_descricao_cadastro.getText().toString());

                tipo = spinner_tipo_cadastro.getSelectedItem().toString();
                lancamento.setTipo(tipo);

                posicaoCategoria = spinner_categoria_cadastro.getSelectedItemPosition();
                Categoria categoriaEscolhida = listaCategoriaDao.get(posicaoCategoria);
                lancamento.setIdCategoria(categoriaEscolhida.getId());

                if(atualizarLancamento){
                    lancamentoDAO.atualizar(lancamento, ctx);
                    Toast.makeText(ctx, "Alterações feitas com sucesso!", Toast.LENGTH_LONG).show();
                } else {
                    lancamentoDAO.inserir(lancamento, ctx);
                    Toast.makeText(ctx, "Cadastro feito com sucesso!", Toast.LENGTH_LONG).show();

                }

                finish();


            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
        /*Pegando o que foi selecionado do calendário*/
        calendar.set(Calendar.YEAR, ano);
        calendar.set(Calendar.MONTH, mes);
        calendar.set(Calendar.DAY_OF_MONTH, dia);

        Date dataSelecionada = calendar.getTime();
        String data = dt_format.format(dataSelecionada);

        txt_dtLancamento_cadastro.setText(data);
    }
}
