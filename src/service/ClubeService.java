package service;

import model.Campeonato;
import model.Clube;
import model.EstatisticasClube;

import java.util.ArrayList;

public class ClubeService {

  public static ArrayList<Clube> enviaClubesComEstatisticas(ArrayList<Clube> clubes, ArrayList<Campeonato> campeonatos){
    ArrayList<Clube> clubesComEstatisticas = new ArrayList<>();

    for (Clube clube : clubes) {
      Clube clubeComEstatisticas = new Clube();
      clubeComEstatisticas.setNome(clube.getNome());
      clubeComEstatisticas.setCidade(clube.getCidade());
      clubeComEstatisticas.setAnoFundacao(clube.getAnoFundacao());
      clubeComEstatisticas.setEstadio(clube.getEstadio());

      ArrayList<EstatisticasClube> estatisticas = new ArrayList<>();

      for (Campeonato campeonato : campeonatos) {
        ArrayList<EstatisticasClube> estatisticasClube = campeonato.getEstatisticasPorClube(clubeComEstatisticas);
        if (estatisticasClube != null) {
          estatisticas.addAll(estatisticasClube);
        }
      }

      clubeComEstatisticas.setEstatisticas(estatisticas);
      clubesComEstatisticas.add(clubeComEstatisticas);
    }

    return clubesComEstatisticas;
  }
}
