package com.example.jogodamemoria.modelo;

public class Jogador {
    private String nome;
    private String tempo;
    private String erros;

    public Jogador(){
        this.nome = tempo = erros = "";
    }

    public Jogador(String nome, String tempo, String erros){
        this.nome = nome;
        this.tempo = tempo;
        this.erros = erros;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getTempo(){
        return this.tempo;
    }

    public void setTempo(String tempo){
        this.tempo = tempo;
    }

    public String getErros(){
        return this.erros;
    }

    public void setErros(String erros){
        this.erros = erros;
    }
}
