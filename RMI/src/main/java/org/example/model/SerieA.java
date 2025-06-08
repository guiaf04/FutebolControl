package org.example.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class SerieA implements Serializable, Campeonato {
    private static final long serialVersionUID = 1L;

    private int ano;
    private int numeroEquipes;
    private String campeao;
    private String viceCampeao;
    private int rodadaAtual;
    private Map<Clube, EstatisticasClube> estatisticasClubes;

    public SerieA() {
        super();
    }

    public SerieA(int ano, int numeroEquipes, String campeao, String viceCampeao, int rodadaAtual) {
        this.ano = ano;
        this.numeroEquipes = numeroEquipes;
        this.campeao = campeao;
        this.viceCampeao = viceCampeao;
        this.rodadaAtual = rodadaAtual;
        this.estatisticasClubes = new java.util.HashMap<>();
    }

    public void setEstatisticasClubes(Map<Clube, EstatisticasClube> estatisticasClubes) {
        this.estatisticasClubes = estatisticasClubes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getNumeroEquipes() {
        return numeroEquipes;
    }

    public void setNumeroEquipes(int numeroEquipes) {
        this.numeroEquipes = numeroEquipes;
    }

    public String getCampeao() {
        return campeao;
    }

    public void setCampeao(String campeao) {
        this.campeao = campeao;
    }

    public String getViceCampeao() {
        return viceCampeao;
    }

    public void setViceCampeao(String viceCampeao) {
        this.viceCampeao = viceCampeao;
    }

    public int getRodadaAtual() {
        return rodadaAtual;
    }

    public void setRodadaAtual(int rodadaAtual) {
        this.rodadaAtual = rodadaAtual;
    }

    public String exibirInformacoes() {
        return "\nAno: " + ano +
               "\nNúmero de Equipes: " + numeroEquipes +
               "\nCampeão: " + campeao +
               "\nVice-Campeão: " + viceCampeao +
               "\nRodada Atual: " + rodadaAtual;
    }

    // Implementação dos métodos da interface model.Partidas
    @Override
    public int registrarPartida(Partida partida) {
        // Implementação do registro de partida para Série A
        System.out.println("Partida de Série A registrada: " + partida.mandante + " x " + partida.visitante);
        return (partida.mandante.toString() + partida.visitante.toString() + partida.data).hashCode();
    }


    @Override
    public String obterInformacoesPartida(int idPartida) {
        // Implementação da obtenção de informações de partida para Série A
        return "Informações da partida " + idPartida + " do campeonato Série A";
    }

    @Override
    public ArrayList<EstatisticasClube> getEstatisticasPorClube(Clube clube) {
        ArrayList<EstatisticasClube> estatisticas = new ArrayList<>();
        for (Map.Entry<Clube, EstatisticasClube> entry : estatisticasClubes.entrySet()) {
            if (entry.getKey().equals(clube)) {
                estatisticas.add(entry.getValue());
            }
        }
        return estatisticas;
    }

}
