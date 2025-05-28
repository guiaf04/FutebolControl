import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe SistemaVotacao - Implementa um sistema de votação para clubes
 * Utiliza comunicação unicast (TCP) e multicast (UDP)
 */
public class SistemaVotacao {
    // Configurações para comunicação multicast
    private static final String MULTICAST_ADDRESS = "230.0.0.1";
    private static final int MULTICAST_PORT = 4446;
    
    private List<String> candidatos;
    private List<Voto> votos;
    private boolean votacaoAberta;
    
    /**
     * Construtor da classe SistemaVotacao
     */
    public SistemaVotacao() {
        this.candidatos = new ArrayList<>();
        this.votos = new ArrayList<>();
        this.votacaoAberta = true;
        
        // Adiciona alguns candidatos iniciais
        candidatos.add("Flamengo");
        candidatos.add("Palmeiras");
        candidatos.add("Grêmio");
        candidatos.add("São Paulo");
        candidatos.add("Fluminense");
    }
    
    /**
     * Método para adicionar um candidato
     * @param candidato Nome do candidato
     * @return true se o candidato foi adicionado, false caso contrário
     */
    public boolean adicionarCandidato(String candidato) {
        if (!candidatos.contains(candidato)) {
            candidatos.add(candidato);
            return true;
        }
        return false;
    }
    
    /**
     * Método para remover um candidato
     * @param candidato Nome do candidato
     * @return true se o candidato foi removido, false caso contrário
     */
    public boolean removerCandidato(String candidato) {
        return candidatos.remove(candidato);
    }
    
    /**
     * Método para registrar um voto
     * @param eleitor Nome do eleitor
     * @param candidato Nome do candidato
     * @return true se o voto foi registrado, false caso contrário
     */
    public boolean registrarVoto(String eleitor, String candidato) {
        if (votacaoAberta && candidatos.contains(candidato)) {
            votos.add(new Voto(eleitor, candidato));
            return true;
        }
        return false;
    }
    
    /**
     * Método para encerrar a votação
     */
    public void encerrarVotacao() {
        votacaoAberta = false;
    }
    
    /**
     * Método para calcular o resultado da votação
     * @return String com o resultado da votação
     */
    public String calcularResultado() {
        if (votacaoAberta) {
            return "A votação ainda está aberta.";
        }
        
        StringBuilder resultado = new StringBuilder("=== Resultado da Votação ===\n");
        resultado.append("Total de votos: ").append(votos.size()).append("\n\n");
        
        // Conta os votos para cada candidato
        for (String candidato : candidatos) {
            int contagem = 0;
            for (Voto voto : votos) {
                if (voto.getCandidato().equals(candidato)) {
                    contagem++;
                }
            }
            
            double percentagem = votos.size() > 0 ? (contagem * 100.0) / votos.size() : 0;
            resultado.append(candidato).append(": ").append(contagem).append(" votos (")
                    .append(String.format("%.2f", percentagem)).append("%)\n");
        }
        
        // Determina o vencedor
        String vencedor = "";
        int maiorContagem = -1;
        
        for (String candidato : candidatos) {
            int contagem = 0;
            for (Voto voto : votos) {
                if (voto.getCandidato().equals(candidato)) {
                    contagem++;
                }
            }
            
            if (contagem > maiorContagem) {
                maiorContagem = contagem;
                vencedor = candidato;
            }
        }
        
        resultado.append("\nVencedor: ").append(vencedor).append(" com ").append(maiorContagem).append(" votos.");
        
        return resultado.toString();
    }
    
    /**
     * Método para enviar uma nota informativa via multicast
     * @param mensagem Mensagem a ser enviada
     * @throws IOException Se ocorrer um erro de I/O
     */
    public void enviarNotaInformativa(String mensagem) throws IOException {
        InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
        MulticastSocket socket = new MulticastSocket(MULTICAST_PORT);
        socket.joinGroup(group);
        
        byte[] buffer = mensagem.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, MULTICAST_PORT);
        socket.send(packet);
        
        socket.leaveGroup(group);
        socket.close();
    }
    
    /**
     * Método para salvar o estado do sistema em um arquivo
     * @param arquivo Nome do arquivo
     * @throws IOException Se ocorrer um erro de I/O
     */
    public void salvar(String arquivo) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            out.writeObject(candidatos);
            out.writeObject(votos);
            out.writeBoolean(votacaoAberta);
        }
    }
    
    /**
     * Método para carregar o estado do sistema de um arquivo
     * @param arquivo Nome do arquivo
     * @throws IOException Se ocorrer um erro de I/O
     * @throws ClassNotFoundException Se a classe do objeto lido não for encontrada
     */
    @SuppressWarnings("unchecked")
    public void carregar(String arquivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            candidatos = (List<String>) in.readObject();
            votos = (List<Voto>) in.readObject();
            votacaoAberta = in.readBoolean();
        }
    }
    
    /**
     * Método para obter a lista de candidatos
     * @return Lista de candidatos
     */
    public List<String> getCandidatos() {
        return candidatos;
    }
    
    /**
     * Método para verificar se a votação está aberta
     * @return true se a votação está aberta, false caso contrário
     */
    public boolean isVotacaoAberta() {
        return votacaoAberta;
    }
    
    /**
     * Classe interna para representar um voto
     */
    private static class Voto implements java.io.Serializable {
        private static final long serialVersionUID = 1L;
        
        private String eleitor;
        private String candidato;
        
        /**
         * Construtor da classe Voto
         * @param eleitor Nome do eleitor
         * @param candidato Nome do candidato
         */
        public Voto(String eleitor, String candidato) {
            this.eleitor = eleitor;
            this.candidato = candidato;
        }
        
        /**
         * Método para obter o nome do eleitor
         * @return Nome do eleitor
         */
        public String getEleitor() {
            return eleitor;
        }
        
        /**
         * Método para obter o nome do candidato
         * @return Nome do candidato
         */
        public String getCandidato() {
            return candidato;
        }
    }
    
    /**
     * Método principal para testar o sistema de votação
     * @param args Argumentos de linha de comando
     */
    public static void main(String[] args) {
        SistemaVotacao sistema = new SistemaVotacao();
        Scanner scanner = new Scanner(System.in);
        boolean executando = true;
        
        System.out.println("=== Sistema de Votação de Clubes de Futebol ===");
        
        while (executando) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1 - Listar candidatos");
            System.out.println("2 - Votar");
            System.out.println("3 - Adicionar candidato (admin)");
            System.out.println("4 - Remover candidato (admin)");
            System.out.println("5 - Enviar nota informativa (admin)");
            System.out.println("6 - Encerrar votação (admin)");
            System.out.println("7 - Ver resultado");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
            
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consome a quebra de linha
            
            try {
                switch (opcao) {
                    case 0:
                        executando = false;
                        System.out.println("Encerrando sistema...");
                        break;
                        
                    case 1:
                        // Listar candidatos
                        System.out.println("\n=== Lista de Candidatos ===");
                        List<String> candidatos = sistema.getCandidatos();
                        for (int i = 0; i < candidatos.size(); i++) {
                            System.out.println((i + 1) + " - " + candidatos.get(i));
                        }
                        break;
                        
                    case 2:
                        // Votar
                        if (!sistema.isVotacaoAberta()) {
                            System.out.println("A votação está encerrada.");
                            break;
                        }
                        
                        System.out.print("Digite seu nome: ");
                        String eleitor = scanner.nextLine();
                        
                        System.out.println("\n=== Lista de Candidatos ===");
                        List<String> candidatosVoto = sistema.getCandidatos();
                        for (int i = 0; i < candidatosVoto.size(); i++) {
                            System.out.println((i + 1) + " - " + candidatosVoto.get(i));
                        }
                        
                        System.out.print("Digite o número do candidato: ");
                        int numeroCandidato = scanner.nextInt();
                        scanner.nextLine(); // Consome a quebra de linha
                        
                        if (numeroCandidato > 0 && numeroCandidato <= candidatosVoto.size()) {
                            String candidato = candidatosVoto.get(numeroCandidato - 1);
                            boolean sucesso = sistema.registrarVoto(eleitor, candidato);
                            
                            if (sucesso) {
                                System.out.println("Voto registrado com sucesso para " + candidato);
                            } else {
                                System.out.println("Erro ao registrar voto.");
                            }
                        } else {
                            System.out.println("Número de candidato inválido.");
                        }
                        break;
                        
                    case 3:
                        // Adicionar candidato (admin)
                        System.out.print("Digite o nome do novo candidato: ");
                        String novoCandidato = scanner.nextLine();
                        
                        boolean sucesso = sistema.adicionarCandidato(novoCandidato);
                        if (sucesso) {
                            System.out.println("Candidato adicionado com sucesso.");
                        } else {
                            System.out.println("Erro ao adicionar candidato. Talvez ele já exista.");
                        }
                        break;
                        
                    case 4:
                        // Remover candidato (admin)
                        System.out.println("\n=== Lista de Candidatos ===");
                        List<String> candidatosRemover = sistema.getCandidatos();
                        for (int i = 0; i < candidatosRemover.size(); i++) {
                            System.out.println((i + 1) + " - " + candidatosRemover.get(i));
                        }
                        
                        System.out.print("Digite o número do candidato a remover: ");
                        int numeroRemover = scanner.nextInt();
                        scanner.nextLine(); // Consome a quebra de linha
                        
                        if (numeroRemover > 0 && numeroRemover <= candidatosRemover.size()) {
                            String candidatoRemover = candidatosRemover.get(numeroRemover - 1);
                            boolean sucessoRemover = sistema.removerCandidato(candidatoRemover);
                            
                            if (sucessoRemover) {
                                System.out.println("Candidato removido com sucesso.");
                            } else {
                                System.out.println("Erro ao remover candidato.");
                            }
                        } else {
                            System.out.println("Número de candidato inválido.");
                        }
                        break;
                        
                    case 5:
                        // Enviar nota informativa (admin)
                        System.out.print("Digite a nota informativa: ");
                        String nota = scanner.nextLine();
                        
                        sistema.enviarNotaInformativa(nota);
                        System.out.println("Nota informativa enviada com sucesso.");
                        break;
                        
                    case 6:
                        // Encerrar votação (admin)
                        sistema.encerrarVotacao();
                        System.out.println("Votação encerrada com sucesso.");
                        break;
                        
                    case 7:
                        // Ver resultado
                        String resultado = sistema.calcularResultado();
                        System.out.println("\n" + resultado);
                        break;
                        
                    default:
                        System.out.println("Opção inválida.");
                        break;
                }
            } catch (Exception e) {
                System.err.println("Erro: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        scanner.close();
    }
}
