package org.example.model;

import java.io.Serializable;

public class EstatisticasClube implements Serializable {
    private int vitorias;
    private int empates;
    private int derrotas;
    private final String campeonato;

    @Override
    public String toString() {
        return "EstatisticasClube{" +
                "vitorias=" + vitorias +
                ", empates=" + empates +
                ", derrotas=" + derrotas +
                ", campeonato='" + campeonato + '\'' +
                '}';
    }

    public EstatisticasClube(int vitorias, int empates, int derrotas, String campeonato) {
        this.vitorias = vitorias;
        this.empates = empates;
        this.derrotas = derrotas;
        this.campeonato = campeonato;
    }

    public int getVitorias() {
        return vitorias;
    }

    public void adicionarVitoria() {
        this.vitorias++;
    }

    public int getEmpates() {
        return empates;
    }

    public void adicionarEmpate() {
        this.empates++;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public void adicionarDerrota() {
        this.derrotas++;
    }
}