package servidor;

import com.google.gson.Gson;
import modelo.Candidato;
import modelo.Usuario;

import java.io.IOException;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Servidor {
    // Configurações de Rede
    private static final int TCP_PORT = 12345;
    private static final String MULTICAST_ADDRESS = "230.0.0.0";
    private static final int MULTICAST_PORT = 54321;

    // Configuração da Votação
    private static final int DURACAO_VOTACAO_MINUTOS = 2; // Duração da votação
    private LocalDateTime fimVotacao;
    private boolean votacaoAberta = true;

    // Armazenamento de Dados (Thread-Safe)
    private final Map<String, Usuario> usuarios = new ConcurrentHashMap<>();
    private final Map<Integer, Candidato> candidatos = new ConcurrentHashMap<>();
    private final Map<Integer, AtomicInteger> votos = new ConcurrentHashMap<>();
    private final List<String> eleitoresQueVotaram = new ArrayList<>();
    private final AtomicInteger proximoIdCandidato = new AtomicInteger(3);

    public Servidor() {
        carregarDadosIniciais();
        iniciarTimerVotacao();
    }

    private void carregarDadosIniciais() {
        // Usuários
        usuarios.put("eleitor1", new Usuario("eleitor1", "123", Usuario.TipoUsuario.ELEITOR));
        usuarios.put("eleitor2", new Usuario("eleitor2", "123", Usuario.TipoUsuario.ELEITOR));
        usuarios.put("admin", new Usuario("admin", "admin", Usuario.TipoUsuario.ADMIN));

        // Candidatos
        adicionarCandidato(new Candidato(1, "João Silva", "Partido da Inovação"));
        adicionarCandidato(new Candidato(2, "Maria Santos", "Partido do Futuro"));
    }
    
    public void adicionarCandidato(Candidato c) {
        candidatos.put(c.getId(), c);
        votos.putIfAbsent(c.getId(), new AtomicInteger(0));
    }

    private void iniciarTimerVotacao() {
        this.fimVotacao = LocalDateTime.now().plusMinutes(DURACAO_VOTACAO_MINUTOS);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        System.out.println("✅ Servidor iniciado. A votação se encerrará às " + fimVotacao.format(formatter));

        Thread timerThread = new Thread(() -> {
            try {
                Thread.sleep(DURACAO_VOTACAO_MINUTOS * 60 * 1000);
                encerrarVotacao();
            } catch (InterruptedException e) {
                System.err.println("Timer da votação interrompido.");
            }
        });
        timerThread.start();
    }

    private void encerrarVotacao() {
        this.votacaoAberta = false;
        System.out.println("\n-------------------------------------------");
        System.out.println("🚫 TEMPO DE VOTAÇÃO ENCERRADO! 🚫");
        System.out.println("Calculando resultados...");
        
        // Calcular e exibir resultados
        int totalVotos = votos.values().stream().mapToInt(AtomicInteger::get).sum();
        System.out.println("Total de votos: " + totalVotos);

        if (totalVotos == 0) {
            System.out.println("Nenhum voto foi registrado.");
            return;
        }

        Candidato ganhador = null;
        int maxVotos = -1;

        for (Map.Entry<Integer, AtomicInteger> entry : votos.entrySet()) {
            Candidato c = candidatos.get(entry.getKey());
            int numVotos = entry.getValue().get();
            double percentual = (double) numVotos / totalVotos * 100.0;
            System.out.printf("   - %s: %d votos (%.2f%%)\n", c.getNome(), numVotos, percentual);

            if (numVotos > maxVotos) {
                maxVotos = numVotos;
                ganhador = c;
            }
        }
        System.out.println("\n🏆 Ganhador: " + (ganhador != null ? ganhador.getNome() : "Nenhum"));
        System.out.println("-------------------------------------------\n");

        // Envia nota de encerramento
        enviarNotaMulticast("A votação foi encerrada. Resultados apurados pelo servidor.");
    }

    public void iniciar() {
        try (ServerSocket serverSocket = new ServerSocket(TCP_PORT)) {
            System.out.println("Servidor TCP aguardando conexões na porta " + TCP_PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());
                new ClientHandler(clientSocket, this).start();
            }
        } catch (IOException e) {
            System.err.println("Erro no servidor TCP: " + e.getMessage());
        }
    }

    public void enviarNotaMulticast(String nota) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            String mensagemCompleta = "[NOTA DO ADMIN]: " + nota;
            byte[] buffer = mensagemCompleta.getBytes();

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, MULTICAST_PORT);
            socket.send(packet);
            System.out.println("Nota multicast enviada: '" + nota + "'");
        } catch (IOException e) {
            System.err.println("Erro ao enviar multicast: " + e.getMessage());
        }
    }

    // Getters para acesso thread-safe pelos Handlers
    public Map<String, Usuario> getUsuarios() { return usuarios; }
    public Map<Integer, Candidato> getCandidatos() { return candidatos; }
    public List<Candidato> getListaCandidatos() { return new ArrayList<>(candidatos.values()); }
    public boolean isVotacaoAberta() { return votacaoAberta; }
    public Map<Integer, AtomicInteger> getVotos() { return votos; }
    public List<String> getEleitoresQueVotaram() { return eleitoresQueVotaram; }
    public AtomicInteger getProximoIdCandidato() { return proximoIdCandidato; }


    public static void main(String[] args) {
        new Servidor().iniciar();
    }
}