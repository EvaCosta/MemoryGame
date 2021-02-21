package com.example.jogodamemoria.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.jogodamemoria.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private List<Integer> sequencia;
    private int posicao = 1;
    private List<Button> buttonList;
    public List<Integer> collorsList;
    private ProgressBar progressBar;
    private ConstraintLayout layout;
    private TextView parabens, memoria, progresso;
    public int contErrors = 0;
    public Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonList = new ArrayList<>();
        collorsList = new ArrayList<Integer>();
        parabens = findViewById(R.id.textViewParabens);
        memoria = findViewById(R.id.textViewMemoria);
        progresso = findViewById(R.id.textViewProgress);

        sequencia = gerarSequencia();

        inicializaObjetos();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //Definindo as propriedades da barra de progresso
        progressBar.setMax(6);

        chronometer = (Chronometer) findViewById(R.id.chronometer);
        startChronometer(chronometer);
    }

    public void startChronometer(View view){
        //A contagem é reiniciada.
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    public void pauseChronometer(View view){
        chronometer.stop();
    }

    public void inicializaObjetos(){
        //Inicializa a lista com os botões da interface
        for(int i = 1; i <= 6; i++)
            buttonList.add((Button)findViewById(getResources().getIdentifier("button" + i,"id", getPackageName())));


        collorsList.add(getResources().getColor(R.color.button1));
        collorsList.add(getResources().getColor(R.color.button2));
        collorsList.add(getResources().getColor(R.color.button3));
        collorsList.add(getResources().getColor(R.color.button4));
        collorsList.add(getResources().getColor(R.color.button5));
        collorsList.add(getResources().getColor(R.color.button6));

        //Torna as mensagens de conclusao do jogo invisiveis 0 para visivel, 1 para invisivel
        isVisivel(parabens,1);
        isVisivel(memoria,1);

    }

    public void acaoBotao(View view){
        layout = findViewById(R.id.fundo);
        Button botao = (Button)view;

        int respostaBotao = Integer.parseInt(botao.getText().toString());

        if(respostaBotao == sequencia.get(posicao-1)){
            botao.setVisibility(View.INVISIBLE);

            layout.setBackgroundColor(collorsList.get(respostaBotao - 1));
            progressBar.setProgress(posicao);
            posicao++;
        }else{
            contErrors++;
            //Toast.makeText(this,"erros " + contErrors, Toast.LENGTH_LONG).show();
            resetarJogo();
        }

        if(posicao == 7){
            /*  Versão anterior na qual era exibida uma mensagem parabenizando o jogador por ter finalizado o jogo.

                isVisivel(parabens, 0);
                isVisivel(memoria, 0);
            */

            progressBar.setVisibility(View.INVISIBLE);
            progresso.setVisibility(View.INVISIBLE);
            progressBar.setProgress(0);
            chronometer.stop();

            obterNomeJogador();

            contErrors = 0;
        }
    }

    public void obterNomeJogador(){
        Intent intent = new Intent(this, SalvarDadosActivity.class);

        intent.putExtra("Duração", chronometer.getText());
        intent.putExtra("Erros", String.format("%s",contErrors));

        startActivity(intent);
    }

    public void isVisivel(TextView mensagem, int auxiliar){
       if(auxiliar == 0)
            mensagem.setVisibility(View.VISIBLE);
        else if(auxiliar == 1)
            mensagem.setVisibility(View.INVISIBLE);
    }

    public void resetarJogo(){
        layout = findViewById(R.id.fundo);
        progressBar.setProgress(0);
        posicao=1;
        mostrarBotoes();
        layout.setBackgroundColor(Color.WHITE);
    }

    public void reiniciar(View view){
        isVisivel(parabens, 1);
        isVisivel(memoria, 1);
        sequencia = gerarSequencia();
        contErrors=0;
        startChronometer(chronometer);
        resetarJogo();
    }

    public void mostrarBotoes(){
        for (Button button : buttonList){
            button.setVisibility(View.VISIBLE);
        }
    }

    public List<Integer> gerarSequencia() {
        Integer[] numeros = new Integer[6];
        Random radom = new Random();
        int indice = 0;

        while (indice != 6) {

            int numeroTmp = radom.nextInt(6) + 1;
            boolean contains = Arrays.asList(numeros).contains(numeroTmp);

            if (!contains) {
                numeros[indice] = numeroTmp;
                indice++;
            }
        }
        return Arrays.asList(numeros);
    }
}