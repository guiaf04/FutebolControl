import Utils.Desempacotamento;
import model.Clube; // Importe sua classe Clube
import streams.ClubeInputStream; // Importe seu stream customizado
import Utils.Empacotamento; // Importe sua classe de serialização

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TesteStreams {

    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("     INICIANDO BATERIA DE TESTES");
        System.out.println("=========================================\n");

        // Executa o primeiro teste (este não precisa de alteração)
        testeLeituraDeEntradaSimulada();

        System.out.println("\n-----------------------------------------\n");

        // Executa o segundo teste (este foi alterado)
        testePersistenciaEmArquivo();

        System.out.println("\n=========================================");
        System.out.println("      FIM DA BATERIA DE TESTES");
        System.out.println("=========================================");
    }

    /**
     * TESTE 1: Simula a entrada de dados pelo console para testar o
     * ClubeInputStream.readSystem().
     * (Este método permanece inalterado)
     */
    private static void testeLeituraDeEntradaSimulada() {
        System.out.println("--- INICIANDO TESTE 1: Leitura de Entrada do Sistema ---");
        try {
            // 1. PREPARAÇÃO: Simula a entrada do usuário
            String entradaSimulada = "2\nFlamengo\nRio de Janeiro\nPalmeiras\nSão Paulo\n";
            InputStream inputStreamSimulado = new ByteArrayInputStream(entradaSimulada.getBytes());
            ClubeInputStream leitor = new ClubeInputStream(inputStreamSimulado);

            // 2. AÇÃO: Executa o método a ser testado
            System.out.println("Ação: Chamando o método readSystem() com dados simulados...");
            ArrayList<Clube> clubesLidos = leitor.readSystem();

            // 3. VERIFICAÇÃO: Checa se os resultados estão corretos
            boolean sucesso = true;
            if (clubesLidos == null || clubesLidos.size() != 2) {
                System.out.println(">>> FALHA: A lista deveria ter 2 clubes, mas tem " + (clubesLidos != null ? clubesLidos.size() : 0));
                sucesso = false;
            } else if (!"Flamengo".equals(clubesLidos.get(0).getNome())) {
                System.out.println(">>> FALHA: O nome do primeiro clube está incorreto.");
                sucesso = false;
            } else if (!"São Paulo".equals(clubesLidos.get(1).getCidade())) {
                System.out.println(">>> FALHA: A cidade do segundo clube está incorreta.");
                sucesso = false;
            }

            if (sucesso) {
                System.out.println(">>> SUCESSO: O método readSystem() processou a entrada simulada corretamente!");
            }

        } catch (IOException e) {
            System.out.println(">>> FALHA: Ocorreu uma exceção inesperada durante o teste.");
            e.printStackTrace();
        }
    }

    /**
     * TESTE 2: Testa o ciclo de serialização e desserialização para um arquivo.
     * ATUALIZADO para usar o método Empacotamento.gravarArquivoBinario().
     */
    private static void testePersistenciaEmArquivo() {
        System.out.println("--- INICIANDO TESTE 2: Persistência de Dados em Arquivo ---");

        String nomeArquivo = "clubes_teste.dat";
        Path caminhoDoArquivo = Paths.get(nomeArquivo);

        try {
            // 1. PREPARAÇÃO: Cria uma lista de objetos
            ArrayList<Clube> listaOriginal = new ArrayList<>();
            listaOriginal.add(new Clube("Grêmio", "Porto Alegre"));
            listaOriginal.add(new Clube("Internacional", "Porto Alegre"));
            System.out.println("Ação: Preparando para salvar " + listaOriginal.size() + " clubes no arquivo '" + nomeArquivo + "'...");

            // 2. AÇÃO (Escrita): Chama diretamente o método refatorado.
            // A lógica de serializar e gravar está agora encapsulada neste método.
            Empacotamento.gravarArquivoBinario(listaOriginal, nomeArquivo);

            if (!Files.exists(caminhoDoArquivo)) {
                System.out.println(">>> FALHA: O arquivo não foi criado no disco.");
                return;
            }
            System.out.println("Ação: Arquivo salvo com sucesso. Lendo os dados de volta...");

            // 3. AÇÃO (Leitura): Lê do arquivo e desserializa
            byte[] bytesLidosDoArquivo = Files.readAllBytes(caminhoDoArquivo);
            ArrayList<Clube> listaLidaDoArquivo = Desempacotamento.lerArrayDeBytes(bytesLidosDoArquivo);

            // 4. VERIFICAÇÃO (Permanece a mesma)
            boolean sucesso = true;
            if (listaLidaDoArquivo == null || listaLidaDoArquivo.size() != listaOriginal.size()) {
                System.out.println(">>> FALHA: O tamanho da lista lida (" + (listaLidaDoArquivo != null ? listaLidaDoArquivo.size() : 0) + ") é diferente da original ("+ listaOriginal.size() +").");
                sucesso = false;
            } else if (!listaOriginal.get(0).getNome().equals(listaLidaDoArquivo.get(0).getNome())) {
                System.out.println(">>> FALHA: O conteúdo da lista lida não corresponde ao original.");
                sucesso = false;
            }

            if (sucesso) {
                System.out.println(">>> SUCESSO: A lista de clubes foi salva e lida do arquivo com sucesso!");
            }

        } catch (IOException e) {
            // O catch agora pode capturar exceções tanto de gravarArquivoBinario() quanto de Files.readAllBytes()
            System.out.println(">>> FALHA: Ocorreu uma exceção de I/O durante o teste de arquivo.");
            e.printStackTrace();
        } finally {
            // LIMPEZA: Continua sendo essencial
            try {
                if (Files.exists(caminhoDoArquivo)) {
                    Files.delete(caminhoDoArquivo);
                    System.out.println("Limpeza: Arquivo de teste '" + nomeArquivo + "' removido.");
                }
            } catch (IOException e) {
                System.err.println("Erro ao deletar o arquivo de teste: " + e.getMessage());
            }
        }
    }
}