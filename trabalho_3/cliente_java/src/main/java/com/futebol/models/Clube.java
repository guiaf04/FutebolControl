package com.futebol.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Clube {
    private String nome;
    private String sigla;
    private int vitorias;
    private int empates;
    private int derrotas;
    
    @SerializedName("gols_pro")
    private int golsPro;
    
    @SerializedName("gols_contra")
    private int golsContra;

    private static final Gson gson = new Gson();

    public Clube() {
        this("", "", 0, 0, 0, 0, 0);
    }

    public Clube(String nome, String sigla) {
        this(nome, sigla, 0, 0, 0, 0, 0);
    }

    public Clube(String nome, String sigla, int vitorias, int empates, int derrotas, int golsPro, int golsContra) {
        this.nome = nome;
        this.sigla = sigla;
        this.vitorias = vitorias;
        this.empates = empates;
        this.derrotas = derrotas;
        this.golsPro = golsPro;
        this.golsContra = golsContra;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getSigla() { return sigla; }
    public void setSigla(String sigla) { this.sigla = sigla; }

    public int getVitorias() { return vitorias; }
    public void setVitorias(int vitorias) { this.vitorias = vitorias; }

    public int getEmpates() { return empates; }
    public void setEmpates(int empates) { this.empates = empates; }

    public int getDerrotas() { return derrotas; }
    public void setDerrotas(int derrotas) { this.derrotas = derrotas; }

    public int getGolsPro() { return golsPro; }
    public void setGolsPro(int golsPro) { this.golsPro = golsPro; }

    public int getGolsContra() { return golsContra; }
    public void setGolsContra(int golsContra) { this.golsContra = golsContra; }

    public int getTotalJogos() {
        return vitorias + empates + derrotas;
    }

    public int getPontos() {
        return vitorias * 3 + empates;
    }

    public int getSaldoGols() {
        return golsPro - golsContra;
    }

    public String toJson() {
        return gson.toJson(this);
    }

    public static Clube fromJson(String json) {
        return gson.fromJson(json, Clube.class);
    }

    @Override
    public String toString() {
        return String.format("Clube{nome='%s', sigla='%s', vitorias=%d, empates=%d, derrotas=%d, golsPro=%d, golsContra=%d, pontos=%d, saldoGols=%d}",
                nome, sigla, vitorias, empates, derrotas, golsPro, golsContra, getPontos(), getSaldoGols());
    }
}
