package cliente;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import modelo.Candidato;
import modelo.Mensagem;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ClienteEleitor {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int TCP_PORT = 12345;
    private static final Gson gson = new Gson();

    public static void main(String[] args) {
        // Inicia o listener de multicast em uma thread separada
        new MulticastReceiverThread().start();

        try (Socket socket = new Socket(SERVER_ADDRESS, TCP_PORT);
             DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("--- BEM-VINDO AO SISTEMA DE VOTAÇÃO ---");

            // 1. Login
            // A chamada para realizarLogin agora será feita dentro de um loop
            String tipoUsuario = realizarLogin(scanner, in, out);
            if (tipoUsuario == null) { // Retorna null se o usuário quiser sair
                System.out.println("Encerrando o cliente.");
                return; 
            }
            // Se o login for bem-sucedido, tipoUsuario será "ELEITOR"

            // 2. Loop principal
            menuVotacao(scanner, in, out);

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
            System.out.print("Login (digite 'sair' para encerrar): ");
            login = scanner.nextLine();
            if (login.equalsIgnoreCase("sair")) {
                return null; // O usuário decidiu sair
            }

            System.out.print("Senha: ");
            senha = scanner.nextLine();

            Mensagem loginRequest = new Mensagem("LOGIN", Map.of("login", login, "senha", senha));
            out.writeUTF(gson.toJson(loginRequest));

            String jsonResponse = in.readUTF();
            response = gson.fromJson(jsonResponse, Mensagem.class);

            if ("LOGIN_SUCESSO".equals(response.getTipo())) {
                tipoUsuarioLogado = (String) response.getPayload().get("tipo_usuario");
                System.out.println("Login realizado com sucesso como " + tipoUsuarioLogado + "!\n");
                return tipoUsuarioLogado; // Retorna o tipo de usuário logado
            } else {
                System.err.println("Falha no login: " + response.getPayload().get("mensagem") + ". Tente novamente.");
            }
        }
    }
    
    private static void menuVotacao(Scanner scanner, DataInputStream in, DataOutputStream out) throws IOException {
        while (true) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1. Ver candidatos e votar");
            System.out.println("2. Sair");
            System.out.print(">> ");
            String escolha = scanner.nextLine();

            if ("1".equals(escolha)) {
                votar(scanner, in, out);
            } else if ("2".equals(escolha)) {
                System.out.println("Obrigado por usar o sistema.");
                break;
            } else {
                System.out.println("Opção inválida.");
            }
        }
    }

    private static void votar(Scanner scanner, DataInputStream in, DataOutputStream out) throws IOException {
        // Solicita a lista de candidatos
        Mensagem requestCandidatos = new Mensagem("LISTAR_CANDIDATOS", null);
        out.writeUTF(gson.toJson(requestCandidatos));
        
        String jsonResponseCandidatos = in.readUTF();
        Mensagem responseCandidatos = gson.fromJson(jsonResponseCandidatos, Mensagem.class);
        
        System.out.println("\n--- CANDIDATOS ---");
        // O Gson pode deserializar a lista de objetos para uma lista de Maps. Precisamos converter de volta.
        Type listType = new TypeToken<List<Candidato>>(){}.getType();
        List<Candidato> candidatos = gson.fromJson(gson.toJson(responseCandidatos.getPayload().get("candidatos")), listType);
        
        for (Candidato c : candidatos) {
            System.out.println(c);
        }
        System.out.println("--------------------");

        System.out.print("Digite o ID do candidato em quem deseja votar: ");
        int idVoto = -1;
        try {
            idVoto = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("ID inválido.");
            return;
        }

        Mensagem votoRequest = new Mensagem("VOTAR", Map.of("id_candidato", idVoto));
        out.writeUTF(gson.toJson(votoRequest));

        String jsonResponseVoto = in.readUTF();
        Mensagem responseVoto = gson.fromJson(jsonResponseVoto, Mensagem.class);
        
        System.out.println("Status: " + responseVoto.getPayload().get("mensagem"));
    }
}