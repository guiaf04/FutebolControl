package model;

public class EstatisticasClube {
    private int vitorias;
    private int empates;
    private int derrotas;

    public EstatisticasClube() {
        this.vitorias = 0;
        this.empates = 0;
        this.derrotas = 0;
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