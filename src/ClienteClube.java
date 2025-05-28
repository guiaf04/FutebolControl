import model.Clube;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

/**
 * Classe ClienteClube - Implementa um cliente para o sistema de clubes
 * Conecta-se ao servidor e envia requisições
 */
public class ClienteClube {
    private static final String SERVIDOR = "localhost";
    private static final int PORTA = 12345;
    
    /**
     * Método para enviar uma requisição ao servidor
     * @param requisicao Requisição a ser enviada
     * @return Resposta do servidor
     * @throws IOException Se ocorrer um erro de I/O
     * @throws ClassNotFoundException Se a classe do objeto recebido não for encontrada
     */
    public static Object enviarRequisicao(String requisicao) throws IOException, ClassNotFoundException {
        try (
            Socket socket = new Socket(SERVIDOR, PORTA);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {
            // Envia a requisição
            out.writeObject(requisicao);
            out.flush();
            
            // Recebe a resposta
            return in.readObject();
        }
    }
    
    /**
     * Método principal
     * @param args Argumentos de linha de comando
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean executando = true;
        
        System.out.println("=== Cliente do Sistema de Controle de Clubes de Futebol ===");
        
        while (executando) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1 - Listar clubes");
            System.out.println("2 - Listar confederação");
            System.out.println("3 - Buscar clube");
            System.out.println("4 - Registrar partida");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
            
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consome a quebra de linha
            
            try {
                switch (opcao) {
                    case 0:
                        executando = false;
                        System.out.println("Encerrando cliente...");
                        break;
                        
                    case 1:
                        // Listar clubes
                        Object resposta1 = enviarRequisicao("LISTAR_CLUBES");
                        if (resposta1 instanceof List) {
                            List<Clube> clubes = (List<Clube>) resposta1;
                            System.out.println("\n=== Lista de Clubes ===");
                            for (Clube clube : clubes) {
                                System.out.println(clube.exibirInformacoes());
                                System.out.println("----------------------------");
                            }
                        }
                        break;
                        
                    case 2:
                        // Listar confederação
                        Object resposta2 = enviarRequisicao("LISTAR_CONFEDERACAO");
                        if (resposta2 instanceof Confederacao) {
                            Confederacao confederacao = (Confederacao) resposta2;
                            System.out.println("\n=== Informações da Confederação ===");
                            System.out.println(confederacao.exibirInformacoes());
                            System.out.println("\n" + confederacao.listarClubesFiliados());
                        }
                        break;
                        
                    case 3:
                        // Buscar clube
                        System.out.print("Digite o nome do clube: ");
                        String nomeClube = scanner.nextLine();
                        
                        Object resposta3 = enviarRequisicao("BUSCAR_CLUBE:" + nomeClube);
                        if (resposta3 instanceof Clube) {
                            Clube clube = (Clube) resposta3;
                            System.out.println("\n=== model.Clube Encontrado ===");
                            System.out.println(clube.exibirInformacoes());
                        } else {
                            System.out.println("model.Clube não encontrado.");
                        }
                        break;
                        
                    case 4:
                        // Registrar partida
                        System.out.print("Digite o nome do clube mandante: ");
                        String mandante = scanner.nextLine();
                        
                        System.out.print("Digite o nome do clube visitante: ");
                        String visitante = scanner.nextLine();
                        
                        System.out.print("Digite a data da partida: ");
                        String data = scanner.nextLine();
                        
                        System.out.print("Digite o local da partida: ");
                        String local = scanner.nextLine();
                        
                        Object resposta4 = enviarRequisicao("REGISTRAR_PARTIDA:" + mandante + ":" + visitante + ":" + data + ":" + local);
                        System.out.println(resposta4);
                        break;
                        
                    default:
                        System.out.println("Opção inválida.");
                        break;
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro ao comunicar com o servidor: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        scanner.close();
    }
}
