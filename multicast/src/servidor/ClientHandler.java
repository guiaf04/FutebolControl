package servidor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import modelo.Candidato;
import modelo.Mensagem;
import modelo.Usuario;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientHandler extends Thread {
    private final Socket socket;
    private final Servidor servidor;
    private final Gson gson = new Gson();
    private Usuario usuarioLogado = null;

    public ClientHandler(Socket socket, Servidor servidor) {
        this.socket = socket;
        this.servidor = servidor;
    }

    @Override
    public void run() {
        try (DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
            
            while (true) {
                String jsonRequest = in.readUTF();
                Type type = new TypeToken<Mensagem>() {}.getType();
                Mensagem request = gson.fromJson(jsonRequest, type);

                Mensagem response = processarRequisicao(request);

                String jsonResponse = gson.toJson(response);
                out.writeUTF(jsonResponse);
                out.flush();
            }
        } catch (IOException e) {
            System.out.println("Cliente desconectado: " + socket.getInetAddress());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                // Silencioso
            }
        }
    }

    private Mensagem processarRequisicao(Mensagem request) {
        Map<String, Object> payload = request.getPayload();

        switch (request.getTipo()) {
            case "LOGIN":
                return handleLogin(payload);
            case "LISTAR_CANDIDATOS":
                 return new Mensagem("LISTA_CANDIDATOS", Map.of("candidatos", servidor.getListaCandidatos()));
            case "VOTAR":
                return handleVotar(payload);
            case "ADD_CANDIDATO":
                return handleAddCandidato(payload);
            case "REMOVE_CANDIDATO":
                return handleRemoveCandidato(payload);
            case "ENVIAR_NOTA":
                return handleEnviarNota(payload);
            default:
                return new Mensagem("ERRO", Map.of("mensagem", "Tipo de requisição inválida."));
        }
    }

    private Mensagem handleLogin(Map<String, Object> payload) {
        String login = (String) payload.get("login");
        String senha = (String) payload.get("senha");
        Usuario user = servidor.getUsuarios().get(login);

        if (user != null && user.getSenha().equals(senha)) {
            this.usuarioLogado = user;
            return new Mensagem("LOGIN_SUCESSO", Map.of("tipo_usuario", user.getTipo().toString()));
        } else {
            return new Mensagem("LOGIN_FALHA", Map.of("mensagem", "Credenciais inválidas."));
        }
    }

    private Mensagem handleVotar(Map<String, Object> payload) {
        if (!servidor.isVotacaoAberta()) {
            return new Mensagem("VOTACAO_FECHADA", Map.of("mensagem", "A votação já foi encerrada."));
        }
        if (usuarioLogado.getTipo() != Usuario.TipoUsuario.ELEITOR) {
            return new Mensagem("ERRO", Map.of("mensagem", "Apenas eleitores podem votar."));
        }
        synchronized (servidor.getEleitoresQueVotaram()) {
            if (servidor.getEleitoresQueVotaram().contains(usuarioLogado.getLogin())) {
                return new Mensagem("ERRO", Map.of("mensagem", "Você já votou."));
            }
        }

        // Usamos Double para evitar problemas de casting de JSON (que pode ler como Double)
        Double idCandidatoDouble = (Double) payload.get("id_candidato");
        int idCandidato = idCandidatoDouble.intValue();

        if (servidor.getCandidatos().containsKey(idCandidato)) {
            servidor.getVotos().get(idCandidato).incrementAndGet();
            synchronized (servidor.getEleitoresQueVotaram()) {
                servidor.getEleitoresQueVotaram().add(usuarioLogado.getLogin());
            }
            return new Mensagem("VOTO_CONFIRMADO", Map.of("mensagem", "Seu voto foi registrado com sucesso!"));
        } else {
            return new Mensagem("ERRO", Map.of("mensagem", "Candidato inválido."));
        }
    }
    
    // Métodos para Admin
    private Mensagem handleAddCandidato(Map<String, Object> payload) {
        if (usuarioLogado.getTipo() != Usuario.TipoUsuario.ADMIN) {
             return new Mensagem("ERRO", Map.of("mensagem", "Acesso negado."));
        }
        String nome = (String) payload.get("nome");
        String partido = (String) payload.get("partido");
        int novoId = servidor.getProximoIdCandidato().getAndIncrement();
        
        servidor.adicionarCandidato(new Candidato(novoId, nome, partido));
        return new Mensagem("CANDIDATO_ADICIONADO", Map.of("mensagem", "Candidato adicionado com sucesso."));
    }

    private Mensagem handleRemoveCandidato(Map<String, Object> payload) {
         if (usuarioLogado.getTipo() != Usuario.TipoUsuario.ADMIN) {
             return new Mensagem("ERRO", Map.of("mensagem", "Acesso negado."));
        }
        Double idCandidatoDouble = (Double) payload.get("id_candidato");
        int idCandidato = idCandidatoDouble.intValue();

        if (servidor.getCandidatos().remove(idCandidato) != null) {
            servidor.getVotos().remove(idCandidato);
            return new Mensagem("CANDIDATO_REMOVIDO", Map.of("mensagem", "Candidato removido com sucesso."));
        } else {
             return new Mensagem("ERRO", Map.of("mensagem", "Candidato não encontrado."));
        }
    }
    
    private Mensagem handleEnviarNota(Map<String, Object> payload) {
        if (usuarioLogado.getTipo() != Usuario.TipoUsuario.ADMIN) {
             return new Mensagem("ERRO", Map.of("mensagem", "Acesso negado."));
        }
        String nota = (String) payload.get("nota");
        servidor.enviarNotaMulticast(nota);
        return new Mensagem("NOTA_ENVIADA", Map.of("mensagem", "Nota informativa enviada a todos os clientes."));
    }
}