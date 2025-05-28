import model.Clube;
import model.Partidas;
import model.SerieA;
import model.SerieB;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe ServidorClube - Implementa um servidor para o sistema de clubes
 * Recebe conexões de clientes e processa requisições
 */
public class ServidorClube {
    private static final int PORTA = 12345;
    private List<Clube> clubes;
    private Confederacao confederacao;
    
    /**
     * Construtor da classe ServidorClube
     */
    public ServidorClube() {
        this.clubes = new ArrayList<>();
        this.confederacao = new Confederacao("CBF", "Brasil", 1914, "Ednaldo Rodrigues");
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
        
        // Adiciona os clubes à confederação
        confederacao.adicionarClube(flamengo);
        confederacao.adicionarClube(palmeiras);
        confederacao.adicionarClube(gremio);
        
        // Adiciona campeonatos
        SerieA serieA = new SerieA("Campeonato Brasileiro Série A", "Rio de Janeiro", 1971,
                                  "Diversos", 51, 2023, 20, "Palmeiras", "Botafogo", 38);
        clubes.add(serieA);
        confederacao.adicionarClube(serieA);
        
        String[] promovidos = {"Vitória", "Juventude", "Criciúma", "Atlético-GO"};
        String[] rebaixados = {"Londrina", "Tombense", "Chapecoense", "ABC"};
        SerieB serieB = new SerieB("Campeonato Brasileiro Série B", "Rio de Janeiro", 1971,
                                  "Diversos", 51, 2023, 20, promovidos, rebaixados, 38);
        clubes.add(serieB);
        confederacao.adicionarClube(serieB);
        
        String[] paises = {"Brasil", "Argentina", "Uruguai", "Paraguai", "Chile", "Colômbia", "Equador", "Venezuela", "Bolívia", "Peru"};
        Libertadores libertadores = new Libertadores("Copa Libertadores", "Assunção", 1960, 
                                                   "Diversos", 64, 2023, 32, paises, "Fluminense", 
                                                   "Boca Juniors", "Final");
        clubes.add(libertadores);
        confederacao.adicionarClube(libertadores);
    }
    
    /**
     * Método para iniciar o servidor
     */
    public void iniciar() {
        try (ServerSocket serverSocket = new ServerSocket(PORTA)) {
            System.out.println("Servidor iniciado na porta " + PORTA);
            
            while (true) {
                Socket clienteSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clienteSocket.getInetAddress().getHostAddress());
                
                // Cria uma nova thread para atender o cliente
                Thread thread = new Thread(new ClienteHandler(clienteSocket));
                thread.start();
            }
        } catch (IOException e) {
            System.err.println("Erro ao iniciar o servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Classe interna para tratar as requisições de cada cliente
     */
    private class ClienteHandler implements Runnable {
        private Socket clienteSocket;
        
        /**
         * Construtor da classe ClienteHandler
         * @param clienteSocket Socket do cliente
         */
        public ClienteHandler(Socket clienteSocket) {
            this.clienteSocket = clienteSocket;
        }
        
        /**
         * Método run da thread
         */
        @Override
        public void run() {
            try (
                ObjectInputStream in = new ObjectInputStream(clienteSocket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(clienteSocket.getOutputStream())
            ) {
                // Lê a requisição do cliente
                String requisicao = (String) in.readObject();
                System.out.println("Requisição recebida: " + requisicao);
                
                // Processa a requisição
                Object resposta = processarRequisicao(requisicao);
                
                // Envia a resposta ao cliente
                out.writeObject(resposta);
                out.flush();
                
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro ao processar requisição do cliente: " + e.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    clienteSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        /**
         * Método para processar a requisição do cliente
         * @param requisicao Requisição do cliente
         * @return Resposta para o cliente
         */
        private Object processarRequisicao(String requisicao) {
            String[] partes = requisicao.split(":");
            String comando = partes[0];
            
            switch (comando) {
                case "LISTAR_CLUBES":
                    return clubes;
                    
                case "LISTAR_CONFEDERACAO":
                    return confederacao;
                    
                case "BUSCAR_CLUBE":
                    if (partes.length > 1) {
                        String nomeClube = partes[1];
                        return confederacao.buscarClube(nomeClube);
                    }
                    return null;
                    
                case "REGISTRAR_PARTIDA":
                    if (partes.length > 4) {
                        String mandante = partes[1];
                        String visitante = partes[2];
                        String data = partes[3];
                        String local = partes[4];
                        
                        // Busca um campeonato para registrar a partida
                        for (Clube clube : clubes) {
                            if (clube instanceof Partidas) {
                                Partidas campeonato = (Partidas) clube;
                                int idPartida = campeonato.registrarPartida(mandante, visitante, data, local);
                                return "Partida registrada com ID: " + idPartida;
                            }
                        }
                    }
                    return "Erro ao registrar partida";
                    
                default:
                    return "Comando desconhecido";
            }
        }
    }
    
    /**
     * Método principal
     * @param args Argumentos de linha de comando
     */
    public static void main(String[] args) {
        ServidorClube servidor = new ServidorClube();
        servidor.iniciar();
    }
}
