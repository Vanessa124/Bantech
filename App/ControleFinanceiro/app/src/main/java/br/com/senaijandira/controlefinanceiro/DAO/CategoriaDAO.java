package br.com.senaijandira.controlefinanceiro.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import br.com.senaijandira.controlefinanceiro.Banco.DbHelper;
import br.com.senaijandira.controlefinanceiro.Objetos.Categoria;

/**
 * Created by 17170124 on 14/03/2018.
 */

public class CategoriaDAO {


    int id = 1;

    private static CategoriaDAO instance;

    public static CategoriaDAO getInstance(){

        if(instance == null){
            instance = new CategoriaDAO();
        }

        return instance;

    }

    public Boolean inserir(Categoria categoria, Context context){

        SQLiteDatabase db = new DbHelper(context).getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nomeCategoria", categoria.getNome());

        Long id = db.insert("tbl_categoria", null, valores);

        String nomeArquivo = String.valueOf(id);
        FileOutputStream arquivo = null;

        if(categoria.getFoto() != null) {
            try {
                arquivo = context.openFileOutput(nomeArquivo, Context.MODE_PRIVATE);
                byte[] fotoCategoria;
                fotoCategoria = transformarParaBytes(categoria.getFoto());
                arquivo.write(fotoCategoria);
                arquivo.close();

            } catch (Exception erro) {
                erro.printStackTrace();
            }
        }

        if(id != -1){
            return true;
        } else{
            return false;
        }

    }

    public ArrayList<Categoria> selecionarTodos(Context context){

        ArrayList<Categoria> retorno = new ArrayList<>();

        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        String sql="SELECT * FROM tbl_categoria";

        Cursor cursor = db.rawQuery(sql, null);
         
         while(cursor.moveToNext()){
            Categoria categoria = new Categoria();
            categoria.setId(cursor.getInt(0));
            categoria.setNome(cursor.getString(1));

            File arquivo = context.getFileStreamPath(categoria.getId().toString());

            if(arquivo.exists()){

                FileInputStream fileInputStream;

                try {
                    fileInputStream = context.openFileInput(categoria.getId().toString());
                    Bitmap foto = BitmapFactory.decodeStream(fileInputStream);
                    categoria.setFoto(foto);
                    fileInputStream.close();

                } catch (Exception erro) {
                    erro.printStackTrace();
                }

            }

            retorno.add(categoria);
         }

        return retorno;
    }

    public Boolean excluir(int id, Context ctx){
	
       SQLiteDatabase db = new DbHelper(ctx).getWritableDatabase();

       db.delete("tbl_categoria", "_idCategoria = ?", new String[]{ String.valueOf(id) });

        return true;

    }

    public Categoria selecionarUm(int id, Context context){
        
        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        String sql = "SELECT * FROM tbl_categoria WHERE _idCategoria= " + id;
  
        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            Categoria categoria = new Categoria();
            categoria.setId(cursor.getInt(0));
            categoria.setNome(cursor.getString(1));
            cursor.close();

            File arquivo = context.getFileStreamPath(categoria.getId().toString());

            if(arquivo.exists()){

                FileInputStream fileInputStream;

                try {
                    fileInputStream = context.openFileInput(categoria.getId().toString());
                    Bitmap foto = BitmapFactory.decodeStream(fileInputStream);
                    categoria.setFoto(foto);
                    fileInputStream.close();

                } catch (Exception erro) {
                    erro.printStackTrace();
                }

            }

            return categoria;
        }

        return null;

    }

    public Boolean atualizar(Categoria categoria, Context ctx){

        SQLiteDatabase db = new DbHelper(ctx).getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nomeCategoria", categoria.getNome());

        db.update("tbl_categoria", valores, "_idCategoria = ?", new String[] {categoria.getId().toString()});

        String nomeArquivo = String.valueOf(categoria.getId());
        FileOutputStream arquivo = null;

        if(categoria.getFoto()!=null){
            try {
                arquivo = ctx.openFileOutput(nomeArquivo, Context.MODE_PRIVATE);
                byte[] fotoCategoria;
                fotoCategoria = transformarParaBytes(categoria.getFoto());
                arquivo.write(fotoCategoria);
                arquivo.close();

            } catch (Exception erro) {
                erro.printStackTrace();
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

    /* Função que transforma um array de bytes em bitmap.*/
    private Bitmap transformarParaBitmap(byte[] img){

        return BitmapFactory.decodeByteArray(img, 0 , img.length);
    }

}
