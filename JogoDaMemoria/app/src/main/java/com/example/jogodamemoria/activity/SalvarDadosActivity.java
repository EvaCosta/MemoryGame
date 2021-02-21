package com.example.jogodamemoria.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jogodamemoria.R;
import com.example.jogodamemoria.bd.DataBase;
import com.example.jogodamemoria.modelo.Jogador;

public class SalvarDadosActivity extends AppCompatActivity {
    private DataBase db;
    private Button button;
    int escolha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salvar_dados);

        db = new DataBase(this);

        String tempo = getIntent().getStringExtra("Duração");
        String erros = getIntent().getStringExtra("Erros");


        if(obterDadosJogador(erros,tempo)){
            //telaRankingTempo();
            telaRankingErro();
        }

    }

    public void telaRankingErro(){
        ListView listView = (ListView) findViewById(R.id.lista);

        db = new DataBase(this);

        //Toast.makeText(this, "as -> " + db.all().size(), Toast.LENGTH_LONG).show();
        String[] itens = new String[db.all().size()];

        int indice = 0;
        for (Jogador jogador : db.all())
            itens[indice++] = String.format("Nome: %s - Erros: %s", jogador.getNome(), jogador.getErros());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itens);

        listView.setAdapter(adapter);

    }

    private void salvarDadosJogador(String erros, String tempo, String nome) {
        Jogador jogador = new Jogador(nome, tempo, erros);
        db.insert(jogador);
        db.close();
    }

    public Boolean obterDadosJogador(String erros, String tempo) {
        // Cria uma tela de dialogo com um botão
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        //AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

        // Define o titulo da caixa de dialogo
        alertDialog.setTitle(R.string.titulo_tela_obter_dados_jogador);

        // Define a mensagem da  caixa de dialogo
        String mensagem = getResources().getString(R.string.dados_exibicao_jogador);
        alertDialog.setMessage(String.format(mensagem, erros,tempo));

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        alertDialog.setView(input);

        // Configura o botão "Salvar"
        alertDialog.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,int which) {
                String nomeJogador = input.getText().toString();
                salvarDadosJogador(erros,tempo,nomeJogador);
            }
        });

        AlertDialog dialog = alertDialog.create();
        dialog.show();

        return true;
    }

    public void limparDados(View view) {
        db = new DataBase(this);

        // Cria uma tela de dialogo com um botão
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setCancelable(true);
        //AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

        // Define o titulo da caixa de dialogo
        alertDialog.setTitle(R.string.confirmar_exclusao);

        // Define a mensagem da  caixa de dialogo
        alertDialog.setMessage(R.string.mensagem_exclusao);


        // Configura o botão "Confirmar"
        alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,int which) {
                db.clear();

                ListView listView = (ListView) findViewById(R.id.lista);
                listView.setAdapter(null);
            }
        });
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,int which) {

            }
        });
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    public void jogar(View view){
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }

    public void telaRankingTempo(View view){
        ListView listView = (ListView) findViewById(R.id.lista);

        db = new DataBase(this);

        //Toast.makeText(this, "as -> " + db.all().size(), Toast.LENGTH_LONG).show();
        String[] itens = new String[db.all().size()];

        int indice = 0;
        for (Jogador jogador : db.all())
            itens[indice++] = String.format("Nome: %s - Tempo: %s", jogador.getNome(), jogador.getTempo());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itens);

        listView.setAdapter(adapter);
    }
}