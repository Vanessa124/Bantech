package br.com.senaijandira.controlefinanceiro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

import br.com.senaijandira.controlefinanceiro.DAO.CategoriaDAO;
import br.com.senaijandira.controlefinanceiro.Objetos.Categoria;

public class CadastroCategoriaActivity extends AppCompatActivity {

    EditText txt_nome_categoria;

    ImageView img_categoria;

    Bitmap fotoCategoria;
    int COD_GALERIA = 1;

    Context ctx = this;

    Button btn_cadastro;

    CategoriaDAO categoriaDAO = CategoriaDAO.getInstance();

    Boolean atualizarCategoria = false;

    Categoria categoriaAtualizar = null;

    Categoria categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_categoria);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btn_cadastro = (Button) findViewById(R.id.btn_cadastro);
        img_categoria = (ImageView) findViewById(R.id.cadastro_img_categoria);
        txt_nome_categoria = (EditText) findViewById(R.id.cadastro_nome_categoria);

        Intent intent = getIntent();
        int idCategoria = intent.getIntExtra("idCategoria", 0);

        if(idCategoria == 0){
            getSupportActionBar().setTitle("Cadastro de Categorias");
            atualizarCategoria = false;
        } else {
            getSupportActionBar().setTitle("Editar Categoria");
            btn_cadastro.setText("Salvar alterações");
            atualizarCategoria = true;

            categoriaAtualizar = CategoriaDAO.getInstance().selecionarUm(idCategoria, ctx);
            txt_nome_categoria.setText(categoriaAtualizar.getNome());

            if(categoriaAtualizar.getFoto() != null){
                img_categoria.setImageBitmap(categoriaAtualizar.getFoto());
            }

        }



        btn_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Validação do campo que não pode ser null*/
                if(txt_nome_categoria.getText().toString().equals("")){
                    txt_nome_categoria.setError("Preencha esse campo!");
                } else {
                    if(atualizarCategoria){
                        categoria = categoriaAtualizar;
                    } else {
                        categoria = new Categoria();
                    }

                    /*Setando os atributos da categoria */
                    categoria.setNome(txt_nome_categoria.getText().toString());
                    if(fotoCategoria != null){
                        categoria.setFoto(fotoCategoria);
                    }


                    /* Atualiza ou insere uma categoria */
                    if(atualizarCategoria){
                        categoriaDAO.atualizar(categoria, ctx);
                        Toast.makeText(ctx, "Alterações salvas com sucesso!", Toast.LENGTH_LONG).show();

                    } else {
                        categoriaDAO.inserir(categoria, ctx);
                        Toast.makeText(ctx, "Categoria cadastrada com sucesso!", Toast.LENGTH_LONG).show();
                    }

                    finish();

                }
            }
        });



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void abrirGaleria(View v){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        startActivityForResult(intent, COD_GALERIA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == COD_GALERIA){
            if(resultCode == Activity.RESULT_OK){

                try {
                    InputStream inp = getContentResolver().openInputStream(data.getData());

                    fotoCategoria = BitmapFactory.decodeStream(inp);

                    img_categoria.setImageBitmap(fotoCategoria);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}
