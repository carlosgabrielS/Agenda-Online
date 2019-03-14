package br.senai.sp.agenda;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ContatoDAO extends SQLiteOpenHelper{

    public ContatoDAO(Context context) {
        super(context, "db_contato", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE tbl_contato(" +
                "id INTEGER PRIMARY KEY, " +
                "nome TEXT NOT NULL, " +
                "endereco TEXT NOT NULL, " +
                "telefone TEXT NOT NULL, " +
                "email TEXT NOT NULL, " +
                "endereco_linkedin TEXT NOT NULL)";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    public void salvar(Contato contato) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = getContentValues(contato);

        db.insert("tbl_contato", null, dados);

    }



    @NonNull
    private ContentValues getContentValues(Contato contato) {
        ContentValues dados = new ContentValues();

        dados.put("nome", contato.getNome());
        dados.put("endereco", contato.getEndereco());
        dados.put("telefone", contato.getTelefone());
        dados.put("email", contato.getEmail());
        dados.put("endereco_linkedin", contato.getEndereco_linkedin());

        return dados;
    }

    public List<Contato> getContatos() {

        SQLiteDatabase db = getReadableDatabase();

        String sql = "SELECT * FROM tbl_contato";

        Cursor c = db.rawQuery(sql, null);

        List<Contato> contatos = new ArrayList<>();

        while (c.moveToNext()){

            Contato contato = new Contato();
            contato.setId(c.getInt(c.getColumnIndex("id")));
            contato.setNome(c.getString(c.getColumnIndex("nome")));
            contato.setEndereco(c.getString(c.getColumnIndex("endereco")));
            contato.setTelefone(c.getString(c.getColumnIndex("telefone")));
            contato.setEmail(c.getString(c.getColumnIndex("email")));
            contato.setEndereco_linkedin(c.getString(c.getColumnIndex("endereco_linkedin")));

            contatos.add(contato);
        }
        return contatos;
    }

    public void atualizar(Contato contato) {

        SQLiteDatabase db = getWritableDatabase();
        String [] params = {String.valueOf(contato.getId())};
        ContentValues dados = getContentValues(contato);

        db.update("tbl_contato", dados, "id=?", params);
    }

    public void excluir (Contato contato){

        //chamo o banco de dados para poder ESCREVER e guardo no "db"
        SQLiteDatabase db = getWritableDatabase();

        //params, porém poderia ter outro nome
        String [] params = {String.valueOf(contato.getId())};

        //chamo o delete para remover do banco
        db.delete("tbl_contato", "id = ?", params);
    }
}
