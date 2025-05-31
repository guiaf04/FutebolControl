package TCP;

import Utils.Desempacotamento;
import Utils.Empacotamento;
import model.*;
import service.ClubeService;
import streams.ClubeInputStream;
import streams.ClubeOutputStream;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServidorClube {
    private static final int PORTA = 12345;
    private List<Clube> clubes;
    private ArrayList<Campeonato> campeonatos = new ArrayList<>();

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

        SerieA campeonatoBrasileiro = new SerieA(2023, 20, "Flamengo", "Palmeiras", 10);
        Map<Clube, EstatisticasClube> estatisticas = Map.of(
            flamengo, new EstatisticasClube(10, 5, 3, "Brasileirão"),
            palmeiras, new EstatisticasClube(10, 6, 2, "Brasileirão"),
            gremio, new EstatisticasClube(10, 4, 4, "Brasileirão")
        );
        campeonatoBrasileiro.setEstatisticasClubes(estatisticas);

        Libertadores libertadores = new Libertadores(2023, 32, "Flamengo", "Palmeiras", "6");
        Map<Clube, EstatisticasClube> estatisticasLibertadores = Map.of(
            flamengo, new EstatisticasClube(6, 3, 2, "Libertadores"),
            palmeiras, new EstatisticasClube(6, 4, 1, "Libertadores")
        );
        libertadores.setEstatisticasClubes(estatisticasLibertadores);

        campeonatos.add(campeonatoBrasileiro);
        campeonatos.add(libertadores);
    }

    public static void main(String args[]) {
        try {
            System.out.println("Servidor iniciado");
            int serverPort = 7896; // the server port
            ServerSocket listenSocket = new ServerSocket(serverPort);
            ServidorClube servidorClube = new ServidorClube();
            while (true) {
                Socket clientSocket = listenSocket.accept();
                System.out.println(clientSocket.getInetAddress());
                System.out.println("conexão estabelecida");
                Connection c = new Connection(clientSocket, servidorClube);
            }
        } catch (IOException e) {
            System.out.println("Listen socket:" + e.getMessage());
        }
    }

    public ArrayList<Campeonato> getCampeonatos() {
        return campeonatos;
    }

    public void setCampeonatos(ArrayList<Campeonato> campeonatos) {
        this.campeonatos = campeonatos;
    }
}

class Connection extends Thread {
    ClubeInputStream in;
    ClubeOutputStream out;
    Socket clientSocket;
    private ServidorClube servidor;

    public Connection(Socket aClientSocket, ServidorClube servidor) {
        this.clientSocket = aClientSocket;
        this.servidor = servidor;
        this.start();
    }


    public void run() {
        try {
            // RECEBER USANDO O STREAM CORRIGIDO
            ClubeInputStream in = new ClubeInputStream(clientSocket.getInputStream());
            ArrayList<Clube> listaDeClubes = in.readTcp();
            System.out.println("Servidor: " + listaDeClubes.size() + " clubes recebidos com sucesso.");

            // Processar
            ArrayList<Clube> clubesComEstatisticas = ClubeService.enviaClubesComEstatisticas(
                    listaDeClubes,
                    servidor.getCampeonatos()
            );

            // ENVIAR RESPOSTA USANDO O STREAM CORRIGIDO
            byte[] conteudoResposta = Empacotamento.serializarParaBytes(clubesComEstatisticas);
            ClubeOutputStream out = new ClubeOutputStream(clientSocket.getOutputStream());
            out.enviarDados(conteudoResposta);

        } catch (IOException e) {
            System.out.println("Erro na conexão: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) { /* Ignorar */}
        }
    }
}