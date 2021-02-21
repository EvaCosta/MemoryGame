package com.example.jogodamemoria.bd;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jogodamemoria.modelo.Jogador;

import java.util.ArrayList;
import java.util.List;

public class DataBase {
    private static final String DATABASE_NAME = "bd1";

    private static final int DATABASE_ACCESS = 0;

    private static final String SQL_STRUCT = "CREATE TABLE IF NOT EXISTS jogador " +
            "(id_ INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT NOT NULL, tempo TEXT NOT NULL, erros INTEGER NOT NULL);";

    private static final String SQL_INSERT = "INSERT INTO jogador (nome,tempo,erros) VALUES ('%s','%s','%s');";

    private static final String SQL_SELECT_ALL = "SELECT * FROM jogador ORDER BY nome;";

    private static final String SQL_CLEAR = "DROP TABLE IF EXISTS jogador;";

    private SQLiteDatabase database;
    private Cursor cursor;
    private int indexID, indexNome, indexTempo, indexErros;

    //Construtor
    public DataBase(Context context){
        //Utiliza o contexto da activity que vai manipular o BD
        database = context.openOrCreateDatabase(DATABASE_NAME, DATABASE_ACCESS, null);

        database.execSQL(SQL_STRUCT);
    }

    public void clear(){
        database.execSQL(SQL_CLEAR);
    }

    public void close(){
        database.close();
    }

    public void insert(Jogador jogador){
        String query = String.format(SQL_INSERT, jogador.getNome(), jogador.getTempo(), jogador.getErros());

        database.execSQL(query);
    }

    public List<Jogador> all(){
        List<Jogador> jogadorList = new ArrayList<Jogador>();
        Jogador jogador;

        cursor = database.rawQuery(SQL_SELECT_ALL, null);

        if(cursor.moveToFirst()){
            indexID = cursor.getColumnIndex("id_");
            indexNome = cursor.getColumnIndex("nome");
            indexTempo = cursor.getColumnIndex("tempo");
            indexErros = cursor.getColumnIndex("erros");

            do {
                jogador = new Jogador(cursor.getString(indexNome), cursor.getString(indexTempo), cursor.getString(indexErros));

                jogadorList.add(jogador);
            }while(cursor.moveToNext());
        }
        cursor.close();

        return jogadorList;
    }
}
