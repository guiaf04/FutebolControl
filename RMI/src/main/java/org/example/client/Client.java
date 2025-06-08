package org.example.client;

import org.example.model.Clube;
import org.example.model.Partida;
import org.example.shared.CampeonatoManager;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class Client {
  public static void main(String[] args) {
    try {
      Registry registry = LocateRegistry.getRegistry("localhost", 1099);
      CampeonatoManager manager = (CampeonatoManager) registry.lookup("CampeonatoService");

      System.out.println("Cliente conectado ao servidor RMI.");

      Scanner scanner = new Scanner(System.in);
      int choice = 0;
      while (choice != 5) {
        printMenu();
        choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
          case 1:
            adicionarClube(manager, scanner);
            break;
          case 2:
            listarClubes(manager);
            break;
          case 3:
            buscarClube(manager, scanner);
            break;
          case 4:
            registrarPartida(manager, scanner);
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

  private static void adicionarClube(CampeonatoManager manager, Scanner scanner) throws Exception {
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
    manager.adicionarClube(novoClube);
    System.out.println("Clube enviado para o servidor.");
  }

  private static void listarClubes(CampeonatoManager manager) throws Exception {
    List<Clube> clubes = manager.listarClubes();
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

  private static void buscarClube(CampeonatoManager manager, Scanner scanner) throws Exception {
    System.out.print("Digite o nome do clube a buscar: ");
    String nome = scanner.nextLine();
    Clube clube = manager.buscarClubePorNome(nome);
    if (clube != null) {
      System.out.println("\n--- Informações do Clube ---");
      System.out.println(clube.exibirInformacoes());
    } else {
      System.out.println("Clube não encontrado.");
    }
  }

  private static void registrarPartida(CampeonatoManager manager, Scanner scanner) throws Exception {
    System.out.print("Nome do clube mandante: ");
    String nomeMandante = scanner.nextLine();
    Clube mandante = manager.buscarClubePorNome(nomeMandante);

    System.out.print("Nome do clube visitante: ");
    String nomeVisitante = scanner.nextLine();
    Clube visitante = manager.buscarClubePorNome(nomeVisitante);

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
    String confirmacao = manager.registrarPartida(partida);
    System.out.println("Resposta do servidor: " + confirmacao);
  }
}