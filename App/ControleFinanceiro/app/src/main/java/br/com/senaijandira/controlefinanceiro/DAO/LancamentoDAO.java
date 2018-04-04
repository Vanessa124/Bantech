package br.com.senaijandira.controlefinanceiro.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.senaijandira.controlefinanceiro.Banco.DbHelper;
import br.com.senaijandira.controlefinanceiro.Objetos.Lancamento;

/**
 * Created by 17170124 on 20/03/2018.
 */


public class LancamentoDAO {

    SimpleDateFormat dt_format = new SimpleDateFormat("dd-MM-yyyy");
    int id=1;

    private static LancamentoDAO instante;

    public static LancamentoDAO getInstance(){
        if(instante == null) {
            instante = new LancamentoDAO();
        }

        return instante;
    }

    public Boolean inserir(Lancamento lancamento, Context ctx){

        SQLiteDatabase db = new DbHelper(ctx).getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("saldo", lancamento.getSaldo());

        Date data = lancamento.getDt_lancamento();
        valores.put("dt_lancamento", data.getTime());
        valores.put("descricao", lancamento.getDescricao());
        valores.put("tipo", lancamento.getTipo());
        valores.put("idCategoria", lancamento.getIdCategoria());

        Long id = db.insert("tbl_lancamento", null, valores);

        if(id != -1){
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Lancamento> selecionarTodos(Context ctx){

        ArrayList<Lancamento> retorno = new ArrayList<Lancamento>();

        SQLiteDatabase db = new DbHelper(ctx).getReadableDatabase();

        String sql = "SELECT * FROM tbl_lancamento";

        Cursor cursor = db.rawQuery(sql, null);

        while(cursor.moveToNext()){
            Lancamento lancamento = new Lancamento();
            lancamento.setId(cursor.getInt(0));
            lancamento.setSaldo(cursor.getFloat(1));

            Date data = null;
            data = new Date(cursor.getLong(2));
            lancamento.setDt_lancamento(data);

            lancamento.setDescricao(cursor.getString(3));
            lancamento.setTipo(cursor.getString(4));
            lancamento.setIdCategoria(cursor.getInt(5));

            retorno.add(lancamento);

        }

        return retorno;
    }

    public ArrayList<Lancamento> selecionarPorCategoria(Context ctx, int idCategoria){

        ArrayList<Lancamento> retorno = new ArrayList<Lancamento>();

        SQLiteDatabase db = new DbHelper(ctx).getReadableDatabase();

        String sql = "SELECT * FROM tbl_lancamento WHERE idCategoria = " + idCategoria;

        Cursor cursor = db.rawQuery(sql, null);

        while(cursor.moveToNext()){
            Lancamento lancamento = new Lancamento();
            lancamento.setId(cursor.getInt(0));
            lancamento.setSaldo(cursor.getFloat(1));

            Date data = null;
            data = new Date(cursor.getLong(2));
            lancamento.setDt_lancamento(data);

            lancamento.setDescricao(cursor.getString(3));
            lancamento.setTipo(cursor.getString(4));
            lancamento.setIdCategoria(cursor.getInt(5));

            retorno.add(lancamento);
        }

        return retorno;
    }

    public Lancamento selecionarUm(int id, Context ctx){

        SQLiteDatabase db = new DbHelper(ctx).getReadableDatabase();

        String sql = "SELECT * FROM tbl_lancamento WHERE _idLancamento = " + id;

        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            Lancamento lancamento = new Lancamento();
            lancamento.setId(cursor.getInt(0));
            lancamento.setSaldo(cursor.getFloat(1));

            Date data = null;
            data = new Date(cursor.getLong(2));
            lancamento.setDt_lancamento(data);

            lancamento.setDescricao(cursor.getString(3));
            lancamento.setTipo(cursor.getString(4));
            lancamento.setIdCategoria(cursor.getInt(5));

            cursor.close();
            return lancamento;

        }

        return null;
    }

    public Boolean excluir(int id, Context ctx){

        SQLiteDatabase db = new DbHelper(ctx).getWritableDatabase();

        db.delete("tbl_lancamento", "_idLancamento = ?", new String[]{String.valueOf(id)});

        return true;
    }

    public Boolean atualizar(Lancamento lancamento, Context ctx){

        SQLiteDatabase db = new DbHelper(ctx).getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("saldo", lancamento.getSaldo());

        Date data = lancamento.getDt_lancamento();
        valores.put("dt_lancamento", data.getTime());

        valores.put("descricao", lancamento.getDescricao());
        valores.put("tipo", lancamento.getTipo());
        valores.put("idCategoria", lancamento.getIdCategoria());

        db.update("tbl_lancamento", valores, "_idLancamento = ?", new String[]{String.valueOf(lancamento.getId())});

        return true;
    }


    public  Float getTotalDespesas(Context ctx){
        Float despesas = 0f;

        SQLiteDatabase db = new DbHelper(ctx).getReadableDatabase();

        String sql_mostrarDespesas = "SELECT * FROM tbl_lancamento WHERE tipo = 'Despesa'";
        Cursor cursorDespesas = db.rawQuery(sql_mostrarDespesas, null);
        while(cursorDespesas.moveToNext()){
            despesas += cursorDespesas.getFloat(1);
        }

        return despesas;

    }

}
