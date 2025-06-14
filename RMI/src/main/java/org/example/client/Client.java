package org.example.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.model.Clube;
import org.example.model.Partida;
import org.example.shared.CampeonatoManager;

import java.lang.reflect.Type;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
  Gson gson = new Gson();
  public static void main(String[] args) {
    try {
      Registry registry = LocateRegistry.getRegistry("localhost", 1099);
      CampeonatoManager manager = (CampeonatoManager) registry.lookup("CampeonatoService");
      Client client = new Client();
      System.out.println("Cliente conectado ao servidor RMI.");

      Scanner scanner = new Scanner(System.in);
      int choice = 0;
      while (choice != 5) {
        printMenu();
        choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
          case 1:
            client.adicionarClube(manager, scanner);
            break;
          case 2:
            client.listarClubes(manager);
            break;
          case 3:
            client.buscarClube(manager, scanner);
            break;
          case 4:
            client.registrarPartida(manager, scanner);
            break;
          case 5:
            System.out.println("Saindo...");
            break;
          default:
            System.out.println("Opção inválida. Tente novamente.");
        }
      }

    } catch (Exception e) {
      System.err.println("Exceção no cliente: " + e);
      e.printStackTrace();
    }
  }

  private static void printMenu() {
    System.out.println("\n--- Menu ---");
    System.out.println("1. Adicionar Clube");
    System.out.println("2. Listar Clubes");
    System.out.println("3. Buscar Clube por Nome");
    System.out.println("4. Registrar Partida");
    System.out.println("5. Sair");
    System.out.print("Escolha uma opção: ");
  }

  private void adicionarClube(CampeonatoManager manager, Scanner scanner) throws Exception {
    System.out.print("Nome do clube: ");
    String nome = scanner.nextLine();
    System.out.print("Cidade: ");
    String cidade = scanner.nextLine();
    System.out.print("Ano de fundação: ");
    int ano = scanner.nextInt();
    scanner.nextLine();
    System.out.print("Estádio: ");
    String estadio = scanner.nextLine();

    Clube novoClube = new Clube(nome, cidade, ano, estadio, 0);
    String novoClubeJSon = this.gson.toJson(novoClube);
    manager.adicionarClube(novoClubeJSon);
    System.out.println("Clube enviado para o servidor.");
  }

  private void listarClubes(CampeonatoManager manager) throws Exception {
    String jsonClubes = manager.listarClubes();

    if (jsonClubes == null || jsonClubes.trim().isEmpty()) {
      System.out.println("Nenhum clube cadastrado no servidor.");
      return;
    }

    Type tipoListaClubes = new TypeToken<ArrayList<Clube>>() {}.getType();

    List<Clube> clubes = gson.fromJson(jsonClubes, tipoListaClubes);

    if (clubes.isEmpty()) {
      System.out.println("Nenhum clube cadastrado.");
    } else {
      System.out.println("\n--- Clubes Cadastrados ---");
      for (Clube clube : clubes) {
        System.out.println(clube.exibirInformacoes());
        System.out.println("--------------------");
      }
    }
  }

  private void buscarClube(CampeonatoManager manager, Scanner scanner) throws Exception {
    System.out.print("Digite o nome do clube a buscar: ");
    String nome = scanner.nextLine();
    Clube clube = gson.fromJson(manager.buscarClubePorNome(nome), Clube.class);
    if (clube != null) {
      System.out.println("\n--- Informações do Clube ---");
      System.out.println(clube.exibirInformacoes());
    } else {
      System.out.println("Clube não encontrado.");
    }
  }

  private void registrarPartida(CampeonatoManager manager, Scanner scanner) throws Exception {
    System.out.print("Nome do clube mandante: ");
    String nomeMandante = scanner.nextLine();
    Clube mandante = gson.fromJson(manager.buscarClubePorNome(nomeMandante), Clube.class);

    System.out.print("Nome do clube visitante: ");
    String nomeVisitante = scanner.nextLine();
    Clube visitante = gson.fromJson(manager.buscarClubePorNome(nomeVisitante), Clube.class);

    if(mandante == null || visitante == null) {
      System.out.println("Um ou ambos os clubes não foram encontrados. Crie-os primeiro.");
      return;
    }

    System.out.print("Data da partida (ex: 2025-06-08): ");
    String data = scanner.nextLine();
    System.out.print("Gols do mandante: ");
    int golsMandante = scanner.nextInt();
    System.out.print("Gols do visitante: ");
    int golsVisitante = scanner.nextInt();
    scanner.nextLine();

    Partida partida = new Partida(0, mandante, visitante, data, "", golsMandante, golsVisitante);
    String confirmacao = manager.registrarPartida(gson.toJson(partida));
    System.out.println("Resposta do servidor: " + confirmacao);
  }
}