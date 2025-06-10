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
            if (!realizarLogin(scanner, in, out)) {
                return; // Encerra se o login falhar
            }
            
            // 2. Loop principal
            menuVotacao(scanner, in, out);

        } catch (IOException e) {
            System.err.println("Não foi possível conectar ao servidor: " + e.getMessage());
        }
    }

    private static boolean realizarLogin(Scanner scanner, DataInputStream in, DataOutputStream out) throws IOException {
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Mensagem loginRequest = new Mensagem("LOGIN", Map.of("login", login, "senha", senha));
        out.writeUTF(gson.toJson(loginRequest));

        String jsonResponse = in.readUTF();
        Mensagem response = gson.fromJson(jsonResponse, Mensagem.class);

        if ("LOGIN_SUCESSO".equals(response.getTipo())) {
            System.out.println("Login realizado com sucesso!\n");
            return true;
        } else {
            System.err.println("Falha no login: " + response.getPayload().get("mensagem"));
            return false;
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