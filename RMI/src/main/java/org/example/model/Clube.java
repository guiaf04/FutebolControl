package org.example.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Clube implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String nome;
    private String cidade;
    private int anoFundacao;
    private String estadio;
    private int numeroTitulos;
    private ArrayList<EstatisticasClube> estatisticas;

    public Clube() {
    }

    public Clube(String nome, String cidade, int anoFundacao, String estadio, int numeroTitulos) {
        this.nome = nome;
        this.cidade = cidade;
        this.anoFundacao = anoFundacao;
        this.estadio = estadio;
        this.numeroTitulos = numeroTitulos;
        this.estatisticas = new ArrayList<>();
    }

    public Clube(String nome, String cidade) {
        this.nome = nome;
        this.cidade = cidade;
        this.anoFundacao = 0; // Valor padrão
        this.estadio = ""; // Valor padrão
        this.numeroTitulos = 0; // Valor padrão
        this.estatisticas = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clube clube = (Clube) o;
        return Objects.equals(nome, clube.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nome);
    }

    public ArrayList<EstatisticasClube> getEstatisticas() {
        return estatisticas;
    }

    public void setEstatisticas(ArrayList<EstatisticasClube> estatisticas) {
        this.estatisticas = estatisticas;
    }

    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getCidade() {
        return cidade;
    }
    
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    
    public int getAnoFundacao() {
        return anoFundacao;
    }
    
    public void setAnoFundacao(int anoFundacao) {
        this.anoFundacao = anoFundacao;
    }
    
    public String getEstadio() {
        return estadio;
    }
    
    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }
    
    public int getNumeroTitulos() {
        return numeroTitulos;
    }
    
    public void setNumeroTitulos(int numeroTitulos) {
        this.numeroTitulos = numeroTitulos;
    }
    
    /**
     * Método para exibir informações do clube
     * @return String com informações do clube
     */
    public String exibirInformacoes() {
        return "Clube: " + nome +
               "\nCidade: " + cidade + 
               "\nAno de Fundação: " + anoFundacao + 
               "\nEstádio: " + estadio + 
               "\nNúmero de Títulos: " + numeroTitulos;
    }
}
