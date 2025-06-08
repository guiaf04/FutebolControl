package org.example.server;

import org.example.shared.CampeonatoManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
  public static void main(String[] args) {
    try {
      CampeonatoManager manager = new CampeonatoManagerImpl();
      Registry registry = LocateRegistry.createRegistry(1099);
      registry.bind("CampeonatoService", manager);

      System.out.println("Servidor RMI está pronto.");
      System.out.println("Aguardando invocações de clientes...");

    } catch (Exception e) {
      System.err.println("Exceção no servidor: " + e.toString());
      e.printStackTrace();
    }
  }
}
