package org.example.model;

import org.example.model.Campeonato;
import org.example.model.Clube;
import org.example.model.EstatisticasClube;
import org.example.model.Partida;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * Classe model.SerieA.Libertadores - Subclasse que representa o campeonato da model.SerieA.Libertadores
 * Estende a classe model.Clube e implementa Serializable para permitir a serialização
 */
public class Libertadores  implements Serializable, Campeonato {
  private static final long serialVersionUID = 1L;

  private int ano;
  private int numeroEquipes;
  private String[] paisesParticipantes;
  private String campeao;
  private String viceCampeao;
  private String faseAtual;
  private Map<Clube, EstatisticasClube> estatisticasClubes;

  /**
   * Construtor padrão
   */
  public Libertadores() {
    this.paisesParticipantes = new String[10];
  }

  public Libertadores(int ano, int numeroEquipes, String campeao, String viceCampeao, String faseAtual) {
    this.ano = ano;
    this.numeroEquipes = numeroEquipes;
    this.campeao = campeao;
    this.viceCampeao = viceCampeao;
    this.faseAtual = faseAtual;
  }

  // Getters e Setters
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

  public String[] getPaisesParticipantes() {
    return paisesParticipantes;
  }

  public void setPaisesParticipantes(String[] paisesParticipantes) {
    this.paisesParticipantes = paisesParticipantes;
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

  public String getFaseAtual() {
    return faseAtual;
  }

  public void setFaseAtual(String faseAtual) {
    this.faseAtual = faseAtual;
  }

  /**
   * Método para exibir informações do campeonato
   *
   * @return String com informações do campeonato
   */
  public String exibirInformacoes() {
    StringBuilder info = new StringBuilder();
    info.append("\nAno: ").append(ano);
    info.append("\nNúmero de Equipes: ").append(numeroEquipes);
    info.append("\nCampeão: ").append(campeao);
    info.append("\nVice-Campeão: ").append(viceCampeao);
    info.append("\nFase Atual: ").append(faseAtual);

    info.append("\nPaíses Participantes: ");
    for (String pais : paisesParticipantes) {
      if (pais != null) {
        info.append(pais).append(", ");
      }
    }

    return info.toString();
  }

  // Implementação dos métodos da interface model.Partidas

  public int registrarPartida(String mandante, String visitante, String data, String local) {
    // Implementação do registro de partida para model.SerieA.Libertadores
    System.out.println("Partida de model.SerieA.Libertadores registrada: " + mandante + " x " + visitante);
    return (mandante + visitante + data).hashCode();
  }

  public boolean registrarResultado(int idPartida, int golsMandante, int golsVisitante) {
    // Implementação do registro de resultado para model.SerieA.Libertadores
    System.out.println("Resultado registrado para partida " + idPartida + ": " + golsMandante + " x " + golsVisitante);
    return true;
  }

  @Override
  public int registrarPartida(Partida partida) {
    return 0;
  }

  public String obterInformacoesPartida(int idPartida) {
    // Implementação da obtenção de informações de partida para model.SerieA.Libertadores
    return "Informações da partida " + idPartida + " do campeonato model.SerieA.Libertadores";
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
