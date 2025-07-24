package com.futebol.models;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

public class Campeonato {
    private String nome;
    private int ano;
    private List<String> clubes;

    private static final Gson gson = new Gson();

    public Campeonato() {
        this("", 0, new ArrayList<>());
    }

    public Campeonato(String nome, int ano) {
        this(nome, ano, new ArrayList<>());
    }

    public Campeonato(String nome, int ano, List<String> clubes) {
        this.nome = nome;
        this.ano = ano;
        this.clubes = clubes != null ? new ArrayList<>(clubes) : new ArrayList<>();
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }

    public List<String> getClubes() { return new ArrayList<>(clubes); }
    public void setClubes(List<String> clubes) { 
        this.clubes = clubes != null ? new ArrayList<>(clubes) : new ArrayList<>(); 
    }

    public void adicionarClube(String siglaClube) {
        if (siglaClube != null && !siglaClube.trim().isEmpty() && !clubes.contains(siglaClube)) {
            clubes.add(siglaClube);
        }
    }

    public void removerClube(String siglaClube) {
        clubes.remove(siglaClube);
    }

    public boolean contemClube(String siglaClube) {
        return clubes.contains(siglaClube);
    }

    public int getNumeroTotalClubes() {
        return clubes.size();
    }

    public String toJson() {
        return gson.toJson(this);
    }

    public static Campeonato fromJson(String json) {
        return gson.fromJson(json, Campeonato.class);
    }

    @Override
    public String toString() {
        return String.format("Campeonato{nome='%s', ano=%d, numClubes=%d, clubes=%s}",
                nome, ano, clubes.size(), clubes);
    }
}
