package org.example.server;

import com.google.gson.Gson;
import org.example.model.Clube;
import org.example.model.Partida;
import org.example.shared.CampeonatoManager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class CampeonatoManagerImpl extends UnicastRemoteObject implements CampeonatoManager {
  // Usando coleções thread-safe para gerenciar os dados
  private final List<Clube> clubes;
  private final List<Partida> partidas;
  private final Gson gson;

  public CampeonatoManagerImpl() throws RemoteException {
    super();

    this.clubes = new CopyOnWriteArrayList<>();
    this.partidas = new CopyOnWriteArrayList<>();
    this.gson = new Gson();

    clubes.add(new Clube("Flamengo", "Rio de Janeiro", 1895, "Maracanã", 8));
    clubes.add(new Clube("Palmeiras", "São Paulo", 1914, "Allianz Parque", 12));
  }

  @Override
  public synchronized void adicionarClube(String clubeJson) throws RemoteException {
    Clube clube = gson.fromJson(clubeJson, Clube.class);

    // Simples verificação para não adicionar clubes com mesmo nome
    if (clubes.stream().noneMatch(c -> c.getNome().equalsIgnoreCase(clube.getNome()))) {
      clubes.add(clube);
      System.out.println("SERVER: Clube adicionado: " + clube.getNome());
    } else {
      System.out.println("SERVER: Tentativa de adicionar clube que já existe: " + clube.getNome());
    }
  }

  @Override
  public String listarClubes() throws RemoteException {
    System.out.println("SERVER: Requisição para listar clubes recebida.");
    return gson.toJson(new ArrayList<>(clubes)); // Retorna uma cópia para evitar modificação direta
  }

  @Override
  public String buscarClubePorNome(String nome) throws RemoteException {
    System.out.println("SERVER: Buscando clube: " + nome);
    Clube clubeFiltrado = clubes.stream()
            .filter(clube -> clube.getNome().equalsIgnoreCase(nome))
            .findFirst()
            .orElse(null);
    return (clubeFiltrado != null) ? gson.toJson(clubeFiltrado) : null;
  }

  @Override
  public String registrarPartida(String partidaJson) throws RemoteException {
    Partida partida = gson.fromJson(partidaJson, Partida.class);

    System.out.println("fui chamado");
    this.partidas.add(partida);
    System.out.println("SERVER: Partida registrada entre " + partida.mandante.getNome() + " e " + partida.visitante.getNome());
    return "Partida registrada com sucesso! ID da Partida (hash): " + partida.hashCode();
  }

  @Override
  public synchronized void atualizarClube(Clube clubeAtualizado) throws RemoteException {
    for (int i = 0; i < clubes.size(); i++) {
      if (clubes.get(i).getNome().equalsIgnoreCase(clubeAtualizado.getNome())) {
        clubes.set(i, clubeAtualizado);
        System.out.println("SERVER: Clube atualizado: " + clubeAtualizado.getNome());
        return;
      }
    }
    System.out.println("SERVER: Tentativa de atualizar clube não existente: " + clubeAtualizado.getNome());
  }
}
