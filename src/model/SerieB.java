package model;

import java.io.Serializable;

/**
 * Classe model.SerieB - Subclasse que representa um campeonato da Série B
 * Estende a classe model.Clube e implementa Serializable para permitir a serialização
 */
public class SerieB extends Clube implements Serializable, Campeonato {
    private static final long serialVersionUID = 1L;
    
    private int ano;
    private int numeroEquipes;
    private String[] clubesPromovidos;
    private String[] clubesRebaixados;
    private int rodadaAtual;
    
    /**
     * Construtor padrão
     */
    public SerieB() {
        super();
        this.clubesPromovidos = new String[4];
        this.clubesRebaixados = new String[4];
    }
    
    /**
     * Construtor com parâmetros
     * @param nome Nome do campeonato
     * @param cidade Cidade sede da organização
     * @param anoFundacao Ano de fundação do campeonato
     * @param estadio Estádio principal
     * @param numeroTitulos Número de edições
     * @param ano Ano da edição atual
     * @param numeroEquipes Número de equipes participantes
     * @param clubesPromovidos Clubes promovidos para Série A
     * @param clubesRebaixados Clubes rebaixados para Série C
     * @param rodadaAtual Rodada atual do campeonato
     */
    public SerieB(String nome, String cidade, int anoFundacao, String estadio, int numeroTitulos,
                 int ano, int numeroEquipes, String[] clubesPromovidos, String[] clubesRebaixados, int rodadaAtual) {
        super(nome, cidade, anoFundacao, estadio, numeroTitulos);
        this.ano = ano;
        this.numeroEquipes = numeroEquipes;
        this.clubesPromovidos = clubesPromovidos;
        this.clubesRebaixados = clubesRebaixados;
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
    
    public String[] getClubesPromovidos() {
        return clubesPromovidos;
    }
    
    public void setClubesPromovidos(String[] clubesPromovidos) {
        this.clubesPromovidos = clubesPromovidos;
    }
    
    public String[] getClubesRebaixados() {
        return clubesRebaixados;
    }
    
    public void setClubesRebaixados(String[] clubesRebaixados) {
        this.clubesRebaixados = clubesRebaixados;
    }
    
    public int getRodadaAtual() {
        return rodadaAtual;
    }
    
    public void setRodadaAtual(int rodadaAtual) {
        this.rodadaAtual = rodadaAtual;
    }
    
    /**
     * Método para exibir informações do campeonato
     * @return String com informações do campeonato
     */
    @Override
    public String exibirInformacoes() {
        StringBuilder info = new StringBuilder(super.exibirInformacoes());
        info.append("\nAno: ").append(ano);
        info.append("\nNúmero de Equipes: ").append(numeroEquipes);
        info.append("\nRodada Atual: ").append(rodadaAtual);
        
        info.append("\nClubes Promovidos: ");
        for (String clube : clubesPromovidos) {
            if (clube != null) {
                info.append(clube).append(", ");
            }
        }
        
        info.append("\nClubes Rebaixados: ");
        for (String clube : clubesRebaixados) {
            if (clube != null) {
                info.append(clube).append(", ");
            }
        }
        
        return info.toString();
    }
    
    // Implementação dos métodos da interface model.Partidas
    @Override
    public int registrarPartida(String mandante, String visitante, String data, String local) {
        // Implementação do registro de partida para Série B
        System.out.println("Partida de Série B registrada: " + mandante + " x " + visitante);
        return (mandante + visitante + data).hashCode();
    }
    
    @Override
    public boolean registrarResultado(int idPartida, int golsMandante, int golsVisitante) {
        // Implementação do registro de resultado para Série B
        System.out.println("Resultado registrado para partida " + idPartida + ": " + golsMandante + " x " + golsVisitante);
        return true;
    }
    
    @Override
    public String obterInformacoesPartida(int idPartida) {
        // Implementação da obtenção de informações de partida para Série B
        return "Informações da partida " + idPartida + " do campeonato Série B";
    }
}
