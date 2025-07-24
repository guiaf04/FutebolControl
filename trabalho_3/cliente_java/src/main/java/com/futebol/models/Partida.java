package com.futebol.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Classe que representa uma partida de futebol
 */
public class Partida {
    @SerializedName("sigla_clube1")
    private String siglaClube1;
    
    @SerializedName("sigla_clube2")
    private String siglaClube2;
    
    private int gols1;
    private int gols2;
    private String campeonato;

    private static final Gson gson = new Gson();

    // Enums para resultado
    public enum Resultado {
        VITORIA_CLUBE1, VITORIA_CLUBE2, EMPATE
    }

    // Construtores
    public Partida() {
        this("", "", 0, 0, "");
    }

    public Partida(String siglaClube1, String siglaClube2, int gols1, int gols2, String campeonato) {
        this.siglaClube1 = siglaClube1;
        this.siglaClube2 = siglaClube2;
        this.gols1 = gols1;
        this.gols2 = gols2;
        this.campeonato = campeonato;
    }

    // Getters e Setters
    public String getSiglaClube1() { return siglaClube1; }
    public void setSiglaClube1(String siglaClube1) { this.siglaClube1 = siglaClube1; }

    public String getSiglaClube2() { return siglaClube2; }
    public void setSiglaClube2(String siglaClube2) { this.siglaClube2 = siglaClube2; }

    public int getGols1() { return gols1; }
    public void setGols1(int gols1) { this.gols1 = gols1; }

    public int getGols2() { return gols2; }
    public void setGols2(int gols2) { this.gols2 = gols2; }

    public String getCampeonato() { return campeonato; }
    public void setCampeonato(String campeonato) { this.campeonato = campeonato; }

    // Métodos calculados
    public Resultado getResultado() {
        if (gols1 > gols2) {
            return Resultado.VITORIA_CLUBE1;
        } else if (gols2 > gols1) {
            return Resultado.VITORIA_CLUBE2;
        } else {
            return Resultado.EMPATE;
        }
    }

    public String getVencedor() {
        Resultado resultado = getResultado();
        switch (resultado) {
            case VITORIA_CLUBE1:
                return siglaClube1;
            case VITORIA_CLUBE2:
                return siglaClube2;
            case EMPATE:
            default:
                return "EMPATE";
        }
    }

    public String getPerdedor() {
        Resultado resultado = getResultado();
        switch (resultado) {
            case VITORIA_CLUBE1:
                return siglaClube2;
            case VITORIA_CLUBE2:
                return siglaClube1;
            case EMPATE:
            default:
                return "EMPATE";
        }
    }

    public int getTotalGols() {
        return gols1 + gols2;
    }

    public boolean isEmpate() {
        return gols1 == gols2;
    }

    // Métodos de validação
    public boolean isValid() {
        return siglaClube1 != null && !siglaClube1.trim().isEmpty() &&
               siglaClube2 != null && !siglaClube2.trim().isEmpty() &&
               !siglaClube1.equals(siglaClube2) &&
               gols1 >= 0 && gols2 >= 0 &&
               campeonato != null && !campeonato.trim().isEmpty();
    }

    // Métodos JSON usando Gson
    public String toJson() {
        return gson.toJson(this);
    }

    public static Partida fromJson(String json) {
        return gson.fromJson(json, Partida.class);
    }

    @Override
    public String toString() {
        return String.format("Partida{%s %d x %d %s, campeonato='%s', resultado=%s}",
                siglaClube1, gols1, gols2, siglaClube2, campeonato, getResultado());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Partida partida = (Partida) obj;
        return gols1 == partida.gols1 && gols2 == partida.gols2 &&
               siglaClube1 != null && siglaClube1.equals(partida.siglaClube1) &&
               siglaClube2 != null && siglaClube2.equals(partida.siglaClube2) &&
               campeonato != null && campeonato.equals(partida.campeonato);
    }

    @Override
    public int hashCode() {
        int result = siglaClube1 != null ? siglaClube1.hashCode() : 0;
        result = 31 * result + (siglaClube2 != null ? siglaClube2.hashCode() : 0);
        result = 31 * result + gols1;
        result = 31 * result + gols2;
        result = 31 * result + (campeonato != null ? campeonato.hashCode() : 0);
        return result;
    }
}
