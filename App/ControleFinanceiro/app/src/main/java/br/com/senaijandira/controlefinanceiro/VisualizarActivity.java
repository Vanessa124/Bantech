package br.com.senaijandira.controlefinanceiro;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import br.com.senaijandira.controlefinanceiro.DAO.CategoriaDAO;
import br.com.senaijandira.controlefinanceiro.DAO.LancamentoDAO;
import br.com.senaijandira.controlefinanceiro.Objetos.Categoria;
import br.com.senaijandira.controlefinanceiro.Objetos.Lancamento;

public class VisualizarActivity extends AppCompatActivity {

    TextView txt_saldo;
    TextView txt_dtLancamento;
    TextView txt_tipo;
    TextView txt_categoria;
    TextView txt_descricao;

    NumberFormat dinheiro_format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    SimpleDateFormat dt_format = new SimpleDateFormat("dd-MM-yyyy");

    LancamentoDAO lancamentoDAO = LancamentoDAO.getInstance();
    Lancamento lancamento;

    int idLancamento;

    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Detalhes do lançamento");

        /*Pega o id do lançamento que veio através do intent do LancamentoActivity*/
        Intent intent = getIntent();
        idLancamento = intent.getIntExtra("idLancamento", 0);


        txt_saldo = (TextView) findViewById(R.id.txt_visualizar_saldo);
        txt_categoria = (TextView) findViewById(R.id.txt_visualizar_categoria);
        txt_descricao = (TextView) findViewById(R.id.txt_visualizar_descricao);
        txt_dtLancamento = (TextView) findViewById(R.id.txt_visualizar_dtLancamento);
        txt_tipo = (TextView) findViewById(R.id.txt_visualizar_tipo);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /*Atualiza a tela de visualização*/
    @Override
    protected void onResume() {
        lancamento = LancamentoDAO.getInstance().selecionarUm(idLancamento, ctx);

        String str_saldo = dinheiro_format.format(lancamento.getSaldo());
        txt_saldo.setText(str_saldo);

        String str_data = dt_format.format(lancamento.getDt_lancamento());
        txt_dtLancamento.setText(str_data);

        Categoria categoria = CategoriaDAO.getInstance().selecionarUm(lancamento.getIdCategoria(), this);
        txt_categoria.setText(categoria.getNome());

        if(lancamento.getDescricao().equals("")){
            txt_descricao.setText("Sem descrição.");
        } else {
            txt_descricao.setText(lancamento.getDescricao());
        }

        txt_tipo.setText(lancamento.getTipo());


        super.onResume();
    }

    /*Abre o menu para editar ou excluir*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_visualizar_lancamento, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.menu_excluir){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Excluir lancamento");
            builder.setMessage("Tem certeza que deseja excluir esse lançamento? ");

            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    LancamentoDAO lancamentoDAO = LancamentoDAO.getInstance();
                    lancamentoDAO.excluir(lancamento.getId(), ctx);
                    finish();
                }
            });

            builder.setNegativeButton("Não", null);

            builder.create().show();

        }

        if(item.getItemId() == R.id.menu_editar){
            Intent intent = new Intent(this, CadastroLancamentoActivity.class);
            intent.putExtra("idLancamento", lancamento.getId());
            startActivity(intent);

        }



        return super.onOptionsItemSelected(item);
    }


}
