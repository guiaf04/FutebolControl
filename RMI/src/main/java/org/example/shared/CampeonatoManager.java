package org.example.shared;

import org.example.model.Clube;
import org.example.model.Partida;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CampeonatoManager extends Remote {
  /**
   * Adiciona um novo clube ao sistema.
   * @param clube O objeto Clube a ser adicionado.
   * @throws RemoteException
   */
  void adicionarClube(Clube clube) throws RemoteException;

  /**
   * Lista todos os clubes cadastrados.
   * @return Uma lista de objetos Clube.
   * @throws RemoteException
   */
  List<Clube> listarClubes() throws RemoteException;

  /**
   * Busca um clube pelo seu nome.
   * @param nome O nome do clube a ser buscado.
   * @return O objeto Clube encontrado ou null.
   * @throws RemoteException
   */
  Clube buscarClubePorNome(String nome) throws RemoteException;

  /**
   * Registra uma nova partida no sistema.
   * @param partida O objeto Partida a ser registrado.
   * @return Uma confirmação em String.
   * @throws RemoteException
   */
  String registrarPartida(Partida partida) throws RemoteException;

  /**
   * Atualiza as informações de um clube existente.
   * @param clube O objeto Clube com as informações atualizadas.
   * @throws RemoteException
   */
  void atualizarClube(Clube clube) throws RemoteException;
}
