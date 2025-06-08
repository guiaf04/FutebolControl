package org.example.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface Campeonato {
    int registrarPartida(Partida partida);
    String obterInformacoesPartida(int idPartida);

    ArrayList<EstatisticasClube> getEstatisticasPorClube(Clube clube);
}
