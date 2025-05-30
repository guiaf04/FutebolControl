package model;

import java.io.Serializable;

public class SerieA implements Serializable, Campeonato {
    private static final long serialVersionUID = 1L;

    private int ano;
    private int numeroEquipes;
    private String campeao;
    private String viceCampeao;
    private int rodadaAtual;

    public SerieA() {
        super();
    }

    public SerieA(int ano, int numeroEquipes, String campeao, String viceCampeao, int rodadaAtual) {
        this.ano = ano;
        this.numeroEquipes = numeroEquipes;
        this.campeao = campeao;
        this.viceCampeao = viceCampeao;
        this.rodadaAtual = rodadaAtual;
    }

    // Getters e Setters
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

    @Override
    public String exibirInformacoes() {
        return "\nAno: " + ano +
               "\nNúmero de Equipes: " + numeroEquipes +
               "\nCampeão: " + campeao +
               "\nVice-Campeão: " + viceCampeao +
               "\nRodada Atual: " + rodadaAtual;
    }

    // Implementação dos métodos da interface model.Partidas
    @Override
    public int registrarPartida(String mandante, String visitante, String data, String local) {
        // Implementação do registro de partida para Série A
        System.out.println("Partida de Série A registrada: " + mandante + " x " + visitante);
        return (mandante + visitante + data).hashCode();
    }

    @Override
    public boolean registrarResultado(int idPartida, int golsMandante, int golsVisitante) {
        // Implementação do registro de resultado para Série A
        System.out.println("Resultado registrado para partida " + idPartida + ": " + golsMandante + " x " + golsVisitante);
        return true;
    }

    @Override
    public String obterInformacoesPartida(int idPartida) {
        // Implementação da obtenção de informações de partida para Série A
        return "Informações da partida " + idPartida + " do campeonato Série A";
    }

}
