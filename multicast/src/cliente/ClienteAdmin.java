package cliente;

import com.google.gson.Gson;
import modelo.Mensagem;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

public class ClienteAdmin {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int TCP_PORT = 12345;
    private static final Gson gson = new Gson();
    
    public static void main(String[] args) {
        new MulticastReceiverThread().start();

        try (Socket socket = new Socket(SERVER_ADDRESS, TCP_PORT);
             DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("--- PAINEL DE ADMINISTRAÇÃO ---");

            // A chamada para realizarLogin agora será feita dentro de um loop
            String tipoUsuario = realizarLogin(scanner, in, out);
            if (tipoUsuario == null || !"ADMIN".equals(tipoUsuario)) { // Se não logar como admin ou sair
                System.out.println("Encerrando o cliente.");
                return;
            }
            
            menuAdmin(scanner, in, out);

        } catch (IOException e) {
            System.err.println("Não foi possível conectar ao servidor: " + e.getMessage());
        }
    }

    // O método realizarLogin agora retorna o tipo de usuário ou null
    private static String realizarLogin(Scanner scanner, DataInputStream in, DataOutputStream out) throws IOException {
        String login;
        String senha;
        Mensagem response;
        String tipoUsuarioLogado = null; // Para armazenar o tipo de usuário logado

        while (true) {
            System.out.print("Login (admin, digite 'sair' para encerrar): ");
            login = scanner.nextLine();
            if (login.equalsIgnoreCase("sair")) {
                return null; // O usuário decidiu sair
            }

            System.out.print("Senha (admin): ");
            senha = scanner.nextLine();

            Mensagem loginRequest = new Mensagem("LOGIN", Map.of("login", login, "senha", senha));
            out.writeUTF(gson.toJson(loginRequest));

            response = gson.fromJson(in.readUTF(), Mensagem.class);

            if ("LOGIN_SUCESSO".equals(response.getTipo())) {
                tipoUsuarioLogado = (String) response.getPayload().get("tipo_usuario");
                if ("ADMIN".equals(tipoUsuarioLogado)) {
                    System.out.println("Login de administrador realizado com sucesso!\n");
                    return tipoUsuarioLogado; // Retorna o tipo de usuário logado (ADMIN)
                } else {
                    System.err.println("Login bem-sucedido, mas o usuário não é um administrador. Tente novamente com um login de admin.");
                }
            } else {
                System.err.println("Falha no login: " + response.getPayload().get("mensagem") + ". Tente novamente.");
            }
        }
    }
    
    // ... (o restante do código de ClienteAdmin.java permanece o mesmo)
    // menuAdmin, addCandidato, removeCandidato, enviarNota
    private static void menuAdmin(Scanner scanner, DataInputStream in, DataOutputStream out) throws IOException {
        while (true) {
            System.out.println("\nOpções de Administrador:");
            System.out.println("1. Adicionar Candidato");
            System.out.println("2. Remover Candidato");
            System.out.println("3. Enviar Nota Informativa (Multicast)");
            System.out.println("4. Sair");
            System.out.print(">> ");
            String escolha = scanner.nextLine();

            switch (escolha) {
                case "1":
                    addCandidato(scanner, in, out);
                    break;
                case "2":
                    removeCandidato(scanner, in, out);
                    break;
                case "3":
                    enviarNota(scanner, in, out);
                    break;
                case "4":
                    System.out.println("Saindo do painel de admin.");
                    return;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }

    private static void addCandidato(Scanner scanner, DataInputStream in, DataOutputStream out) throws IOException {
        System.out.print("Nome do novo candidato: ");
        String nome = scanner.nextLine();
        System.out.print("Partido do novo candidato: ");
        String partido = scanner.nextLine();

        Mensagem request = new Mensagem("ADD_CANDIDATO", Map.of("nome", nome, "partido", partido));
        out.writeUTF(gson.toJson(request));

        Mensagem response = gson.fromJson(in.readUTF(), Mensagem.class);
        System.out.println("Status: " + response.getPayload().get("mensagem"));
    }
    
    private static void removeCandidato(Scanner scanner, DataInputStream in, DataOutputStream out) throws IOException {
        System.out.print("ID do candidato a remover: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("ID inválido.");
            return;
        }
        
        Mensagem request = new Mensagem("REMOVE_CANDIDATO", Map.of("id_candidato", id));
        out.writeUTF(gson.toJson(request));
        
        Mensagem response = gson.fromJson(in.readUTF(), Mensagem.class);
        System.out.println("Status: " + response.getPayload().get("mensagem"));
    }

    private static void enviarNota(Scanner scanner, DataInputStream in, DataOutputStream out) throws IOException {
        System.out.print("Digite a nota a ser enviada a todos: ");
        String nota = scanner.nextLine();
        
        Mensagem request = new Mensagem("ENVIAR_NOTA", Map.of("nota", nota));
        out.writeUTF(gson.toJson(request));
        
        Mensagem response = gson.fromJson(in.readUTF(), Mensagem.class);
        System.out.println("Status: " + response.getPayload().get("mensagem"));
    }
}