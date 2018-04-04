package br.com.senaijandira.controlefinanceiro.Banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

import br.com.senaijandira.controlefinanceiro.R;

/**
 * Created by 17170124 on 21/03/2018.
 */

public class DbHelper extends SQLiteOpenHelper {

    /*Informações do banco*/
    private static String nome_banco = "db_bantech";
    private static int versao_banco = 1;

    Context ctx;

    /*Construtor da classe*/
    public DbHelper(Context ctx){
        super(ctx, nome_banco, null, versao_banco);
        this.ctx = ctx;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String criarTabelaCategoria = "CREATE TABLE tbl_categoria (" +
                "_idCategoria INTEGER primary key autoincrement, " +
                "nomeCategoria TEXT)";

        String criarTabelaLancamento = "CREATE TABLE tbl_lancamento (" +
                "_idLancamento INTEGER primary key autoincrement, " +
                "saldo REAL, " +
                "dt_lancamento INTEGER, " +
                "descricao TEXT, " +
                "tipo TEXT, " +
                "idCategoria INTEGER)";


        sqLiteDatabase.execSQL(criarTabelaCategoria);
        sqLiteDatabase.execSQL(criarTabelaLancamento);

        /****
         * Criando as categorias padrões do aplicativo
         * ****/

        ContentValues valor1 = new ContentValues();
        valor1.put("nomeCategoria", "Lazer");
        Bitmap imgLazer = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.ic_local_library_black_24dp);
        Long idLazer = sqLiteDatabase.insert("tbl_categoria", null, valor1);
        inserirFotoCategoriaPadrao(idLazer, imgLazer);

        ContentValues valor2 = new ContentValues();
        valor2.put("nomeCategoria", "Transporte");
        Bitmap imgTransporte = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.ic_directions_bus_black_24dp);
        Long idTransporte = sqLiteDatabase.insert("tbl_categoria", null, valor2);
        inserirFotoCategoriaPadrao(idTransporte, imgTransporte);

        ContentValues valor3 = new ContentValues();
        valor3.put("nomeCategoria", "Saúde");
        Bitmap imgSaude = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.ic_local_hospital_black_24dp);
        Long idSaude = sqLiteDatabase.insert("tbl_categoria", null, valor3);
        inserirFotoCategoriaPadrao(idSaude, imgSaude);

        ContentValues valor4 = new ContentValues();
        valor4.put("nomeCategoria", "Moradia");
        Bitmap imgMoradia = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.ic_home_black_24dp);
        Long idMoradia = sqLiteDatabase.insert("tbl_categoria", null, valor4);
        inserirFotoCategoriaPadrao(idMoradia, imgMoradia);

        ContentValues valor5 = new ContentValues();
        valor5.put("nomeCategoria", "Salário");
        Bitmap imgSalario = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.ic_attach_money_black_24dp);
        Long idSalario = sqLiteDatabase.insert("tbl_categoria", null, valor5);
        inserirFotoCategoriaPadrao(idSalario, imgSalario);

        ContentValues valor6 = new ContentValues();
        valor6.put("nomeCategoria", "Outros");
        Bitmap imgOutros = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.ic_view_list_black_24dp);
        Long idOutros = sqLiteDatabase.insert("tbl_categoria", null, valor6);
        inserirFotoCategoriaPadrao(idOutros, imgOutros);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("drop table tbl_categoria");
        sqLiteDatabase.execSQL("drop table tbl_lancamento");

        onCreate(sqLiteDatabase);
    }

    private Boolean inserirFotoCategoriaPadrao(Long id, Bitmap fotoCategoria){
        String nomeArquivo = String.valueOf(id);
        FileOutputStream arquivo = null;

        if(fotoCategoria != null){
            try{
                arquivo = ctx.openFileOutput(nomeArquivo, Context.MODE_PRIVATE);
                byte[] foto;
                foto = transformarParaBytes(fotoCategoria);
                arquivo.write(foto);
                arquivo.close();

            } catch (Exception e){
                e.printStackTrace();
            }
        }

        return true;

    }

    /* Função que transforma um bitmap para um array de bytes.*/
    private byte[] transformarParaBytes(Bitmap bitmap){

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);

        return stream.toByteArray();

    }
}

