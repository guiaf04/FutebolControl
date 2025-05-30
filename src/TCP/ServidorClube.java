package TCP;

import Utils.Desempacotamento;
import model.*;
import streams.ClubeInputStream;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe TCP.ServidorClube - Implementa um servidor para o sistema de clubes
 * Recebe conexões de clientes e processa requisições
 */
public class ServidorClube {
    private static final int PORTA = 12345;
    private List<Clube> clubes;

    public ServidorClube() {
        this.clubes = new ArrayList<>();
        inicializarDados();
    }
    
    /**
     * Método para inicializar dados de exemplo
     */
    private void inicializarDados() {
        // Adiciona alguns clubes de exemplo
        Clube flamengo = new Clube("Flamengo", "Rio de Janeiro", 1895, "Maracanã", 45);
        Clube palmeiras = new Clube("Palmeiras", "São Paulo", 1914, "Allianz Parque", 42);
        Clube gremio = new Clube("Grêmio", "Porto Alegre", 1903, "Arena do Grêmio", 39);
        
        clubes.add(flamengo);
        clubes.add(palmeiras);
        clubes.add(gremio);
    }

    public static void main(String args[]) {
        try {
            System.out.println("Servidor iniciado");
            int serverPort = 7896; // the server port
            ServerSocket listenSocket = new ServerSocket(serverPort);
            while (true) {
                Socket clientSocket = listenSocket.accept();
                System.out.println(clientSocket.getInetAddress());
                System.out.println("conexão estabelecida");
                Connection c = new Connection(clientSocket);
            }
        } catch (IOException e) {
            System.out.println("Listen socket:" + e.getMessage());
        }
    }

}

class Connection extends Thread {
    ClubeInputStream in;
    DataOutputStream out;
    Socket clientSocket;

    public Connection(Socket aClientSocket) {
        try {
            clientSocket = aClientSocket;
            in = new ClubeInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            this.start();
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    public void run() {
        try {
            ArrayList<Object> listaDeClubes = in.readTcp();
            System.out.println("Servidor: " + listaDeClubes.size() + " clubes recebidos com sucesso.");

            if (!listaDeClubes.isEmpty() && listaDeClubes.getFirst() instanceof Clube primeiroCliente) {
                listaDeClubes.forEach(clube -> {
                    if (clube instanceof Clube c) {
                        System.out.println("Nome do clube: " + c.getNome());
                        System.out.println("Cidade do clube: " + c.getCidade());
                    }
                });
            }

            String resposta = "Servidor processou " + listaDeClubes.size() + " objetos.";
            out.writeUTF(resposta.toUpperCase());

        } catch (IOException e) {
            System.out.println("Erro na conexão: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) { /* Ignorar */}
        }
    }
}