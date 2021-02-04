package com.example.jogodamemoria;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonList = new ArrayList<>();
        collorsList = new ArrayList<Integer>();
        sequencia = gerarSequencia();

        inicializaObjetos();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //Definindo as propriedades da barra de progresso
        progressBar.setMax(6);
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
            resetarJogo();
        }

        if(posicao == 7){
            progressBar.setProgress(0);
            Intent intent = new Intent(this, parabensActivity.class);
            intent.putExtra("Color",collorsList.get(respostaBotao - 1));
            startActivity(intent);
        }
    }

    public void resetarJogo(){
        layout = findViewById(R.id.fundo);
        progressBar.setProgress(0);
        posicao=1;
        mostrarBotoes();
        layout.setBackgroundColor(Color.WHITE);
    }

    public void reiniciar(View view){
        resetarJogo();
        gerarSequencia();
    }

    public void mostrarBotoes(){
        for (Button button : buttonList){
            button.setVisibility(View.VISIBLE);
        }
    }

    public List<Integer> gerarSequencia() {
        Integer[] numeros = new Integer[6];
        Random radom = new Random();
        int contador = 0;
        int indice = 0;

        while (contador != 6) {

            int numeroTmp = radom.nextInt(6) + 1;
            boolean contains = Arrays.asList(numeros).contains(numeroTmp);

            if (!contains) {
                contador++;
                numeros[indice] = numeroTmp;
                indice++;
            }
        }
        return Arrays.asList(numeros);
    }
}