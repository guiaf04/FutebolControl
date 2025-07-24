import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class FutebolClient extends JFrame {
    private JTextField nomeClubeField, siglaClubeField, siglaStatsField;
    private JTextField putSiglaOriginalClubeField, putNomeClubeField, putNovaSiglaClubeField;
    private JTextField delSiglaClubeField;

    private JTextField nomeCampField, anoCampField;
    private JTextField putNomeOriginalCampField, putNovoNomeCampField, putNovoAnoCampField;
    private JTextField delNomeCampField;
    private JTextField addCampNomeField, addClubeSiglaField;
    private JTextField listCampClubesNomeField;


    private JTextField clube1Field, clube2Field, gols1Field, gols2Field, campPartidaField;
    private JTextField historicoSiglaField;
    private JTextArea outputArea;

    public FutebolClient() {
        setTitle("Futebol Control - Cliente Java");
        setSize(900, 950); // Aumentado para acomodar os novos campos e melhorar a visualização
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 1));

        // ============================
        // Painel de clube
        // ============================

        add(new JLabel("--- CLUBE ---"));
        add(new JLabel("Criar Clube - Nome:"));
        nomeClubeField = new JTextField();
        add(nomeClubeField);
        add(new JLabel("Criar Clube - Sigla:"));
        siglaClubeField = new JTextField();
        add(siglaClubeField);
        JButton btnCriarClube = new JButton("Criar Clube");
        btnCriarClube.addActionListener(e -> criarClube());
        add(btnCriarClube);

        add(new JLabel("Ver Estatísticas - Sigla do Clube:"));
        siglaStatsField = new JTextField();
        add(siglaStatsField);
        JButton btnVerStats = new JButton("Ver Estatísticas do Clube");
        btnVerStats.addActionListener(e -> verEstatisticas());
        add(btnVerStats);

        add(new JLabel("Atualizar Clube - Sigla Original:"));
        putSiglaOriginalClubeField = new JTextField();
        add(putSiglaOriginalClubeField);
        add(new JLabel("Atualizar Clube - Novo Nome:"));
        putNomeClubeField = new JTextField();
        add(putNomeClubeField);
        add(new JLabel("Atualizar Clube - Nova Sigla:"));
        putNovaSiglaClubeField = new JTextField();
        add(putNovaSiglaClubeField);
        JButton btnAtualizarClube = new JButton("Atualizar Clube");
        btnAtualizarClube.addActionListener(e -> atualizarClube());
        add(btnAtualizarClube);

        add(new JLabel("Deletar Clube - Sigla:"));
        delSiglaClubeField = new JTextField();
        add(delSiglaClubeField);
        JButton btnDeletarClube = new JButton("Deletar Clube");
        btnDeletarClube.addActionListener(e -> deletarClube());
        add(btnDeletarClube);

        // ============================
        // Painel de campeonato
        // ============================

        add(new JLabel("--- CAMPEONATO ---"));
        add(new JLabel("Criar Campeonato - Nome:"));
        nomeCampField = new JTextField();
        add(nomeCampField);
        add(new JLabel("Criar Campeonato - Ano:"));
        anoCampField = new JTextField();
        add(anoCampField);
        JButton btnCriarCamp = new JButton("Criar Campeonato");
        btnCriarCamp.addActionListener(e -> criarCampeonato());
        add(btnCriarCamp);

        add(new JLabel("Atualizar Campeonato - Nome Original:"));
        putNomeOriginalCampField = new JTextField();
        add(putNomeOriginalCampField);
        add(new JLabel("Atualizar Campeonato - Novo Nome:"));
        putNovoNomeCampField = new JTextField();
        add(putNovoNomeCampField);
        add(new JLabel("Atualizar Campeonato - Novo Ano:"));
        putNovoAnoCampField = new JTextField();
        add(putNovoAnoCampField);
        JButton btnAtualizarCamp = new JButton("Atualizar Campeonato");
        btnAtualizarCamp.addActionListener(e -> atualizarCampeonato());
        add(btnAtualizarCamp);

        add(new JLabel("Deletar Campeonato - Nome:"));
        delNomeCampField = new JTextField();
        add(delNomeCampField);
        JButton btnDeletarCamp = new JButton("Deletar Campeonato");
        btnDeletarCamp.addActionListener(e -> deletarCampeonato());
        add(btnDeletarCamp);

        add(new JLabel("Adicionar Clube ao Campeonato - Nome do Campeonato:"));
        addCampNomeField = new JTextField();
        add(addCampNomeField);
        add(new JLabel("Adicionar Clube ao Campeonato - Sigla do Clube:"));
        addClubeSiglaField = new JTextField();
        add(addClubeSiglaField);
        JButton btnAddClubeCamp = new JButton("Adicionar Clube ao Campeonato");
        btnAddClubeCamp.addActionListener(e -> adicionarClubeAoCampeonato());
        add(btnAddClubeCamp);

        add(new JLabel("Ver Clubes do Campeonato - Nome do Campeonato:"));
        listCampClubesNomeField = new JTextField();
        add(listCampClubesNomeField);
        JButton btnListCampClubes = new JButton("Ver Clubes do Campeonato");
        btnListCampClubes.addActionListener(e -> verClubesCampeonato());
        add(btnListCampClubes);

        // ============================
        // Painel de partida
        // ============================

        add(new JLabel("--- PARTIDA ---"));
        add(new JLabel("Registrar Partida - Clube 1 (sigla):"));
        clube1Field = new JTextField();
        add(clube1Field);
        add(new JLabel("Gols Clube 1:"));
        gols1Field = new JTextField();
        add(gols1Field);

        add(new JLabel("Clube 2 (sigla):"));
        clube2Field = new JTextField();
        add(clube2Field);
        add(new JLabel("Gols Clube 2:"));
        gols2Field = new JTextField();
        add(gols2Field);

        add(new JLabel("Campeonato:"));
        campPartidaField = new JTextField();
        add(campPartidaField);
        JButton btnRegistrarPartida = new JButton("Registrar Partida");
        btnRegistrarPartida.addActionListener(e -> registrarPartida());
        add(btnRegistrarPartida);

        add(new JLabel("Ver histórico - Sigla do clube:"));
        historicoSiglaField = new JTextField();
        add(historicoSiglaField);
        JButton btnHistorico = new JButton("Ver Histórico do Clube");
        btnHistorico.addActionListener(e -> verHistorico());
        add(btnHistorico);

        // ============================
        // Área de saída
        // ============================

        add(new JLabel("--- SAÍDA ---"));
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane);
    }

    // ========= Funções HTTP =========

    private void criarClube() {
        String nome = nomeClubeField.getText();
        String sigla = siglaClubeField.getText();
        String json = String.format("{\"nome\":\"%s\", \"sigla\":\"%s\"}", nome, sigla);
        sendPost("http://localhost:8000/clubes", json);
    }

    private void verEstatisticas() {
        String sigla = siglaStatsField.getText();
        String resposta = sendGet("http://localhost:8000/clubes/estatisticas?sigla=" + sigla);
        outputArea.setText(resposta);
    }

    private void atualizarClube() {
        String siglaOriginal = putSiglaOriginalClubeField.getText();
        String novoNome = putNomeClubeField.getText();
        String novaSigla = putNovaSiglaClubeField.getText();

        if (siglaOriginal.isEmpty() || novoNome.isEmpty() || novaSigla.isEmpty()) {
            outputArea.setText("Erro: Todos os campos de atualização do clube são obrigatórios.");
            return;
        }

        String json = String.format("{\"nome\":\"%s\", \"sigla\":\"%s\"}", novoNome, novaSigla);
        sendPut("http://localhost:8000/clubes?sigla_original=" + siglaOriginal, json);
    }

    private void deletarClube() {
        String sigla = delSiglaClubeField.getText();
        if (sigla.isEmpty()) {
            outputArea.setText("Erro: A sigla do clube é obrigatória para deletar.");
            return;
        }
        sendDelete("http://localhost:8000/clubes?sigla=" + sigla);
    }

    private void criarCampeonato() {
        String nome = nomeCampField.getText();
        String ano = anoCampField.getText();
        if (nome.isEmpty() || ano.isEmpty()) {
            outputArea.setText("Erro: Nome e Ano do campeonato são obrigatórios.");
            return;
        }
        String json = String.format("{\"nome\":\"%s\", \"ano\":%s}", nome, ano);
        sendPost("http://localhost:8000/campeonatos", json);
    }

    private void atualizarCampeonato() {
        String nomeOriginal = putNomeOriginalCampField.getText();
        String novoNome = putNovoNomeCampField.getText();
        String novoAnoStr = putNovoAnoCampField.getText();

        if (nomeOriginal.isEmpty()) {
            outputArea.setText("Erro: O nome original do campeonato é obrigatório para atualizar.");
            return;
        }

        try {
            // Passo 1: Obter a lista de todos os campeonatos
            String allCampsJson = sendGet("http://localhost:8000/campeonatos");
            
            // Esta é uma simplificação extrema para extrair dados sem uma biblioteca JSON.
            // Em uma aplicação real, você usaria org.json, Gson, ou Jackson para parsear.
            String clubesExistentesJson = "[]";
            int anoExistente = 0;

            // Encontra o objeto do campeonato pelo nome original
            String searchPattern = "\"nome\":\"" + nomeOriginal + "\"";
            int campStartIndex = allCampsJson.indexOf(searchPattern);

            if (campStartIndex != -1) {
                // Encontra o início do objeto do campeonato
                int bracketCount = 0;
                int currentPos = campStartIndex;
                while (currentPos >= 0 && allCampsJson.charAt(currentPos) != '{') {
                    currentPos--;
                }
                int objStartIndex = currentPos;

                // Encontra o final do objeto do campeonato
                bracketCount = 0;
                currentPos = objStartIndex;
                while (currentPos < allCampsJson.length()) {
                    char c = allCampsJson.charAt(currentPos);
                    if (c == '{') {
                        bracketCount++;
                    } else if (c == '}') {
                        bracketCount--;
                    }
                    if (bracketCount == 0 && c == '}') {
                        break;
                    }
                    currentPos++;
                }
                int objEndIndex = currentPos;

                if (objStartIndex != -1 && objEndIndex != -1) {
                    String campJsonSegment = allCampsJson.substring(objStartIndex, objEndIndex + 1);

                    // Extrair ano existente
                    int anoIdx = campJsonSegment.indexOf("\"ano\":");
                    if (anoIdx != -1) {
                        anoIdx += 6; // Pula "ano":"
                        int anoEndIdx = campJsonSegment.indexOf(",", anoIdx);
                        if (anoEndIdx == -1) anoEndIdx = campJsonSegment.indexOf("}", anoIdx);
                        if (anoEndIdx != -1) {
                            anoExistente = Integer.parseInt(campJsonSegment.substring(anoIdx, anoEndIdx).trim());
                        }
                    }

                    // Extrair clubes existentes
                    int clubesIdx = campJsonSegment.indexOf("\"clubes\":");
                    if (clubesIdx != -1) {
                        clubesIdx += 9; // Pula "clubes":
                        int clubesEndIdx = campJsonSegment.indexOf("]", clubesIdx) + 1;
                        if (clubesEndIdx != -1) {
                            clubesExistentesJson = campJsonSegment.substring(clubesIdx, clubesEndIdx);
                        }
                    }
                }
            }

            int finalAno = novoAnoStr.isEmpty() ? anoExistente : Integer.parseInt(novoAnoStr);
            String finalNome = novoNome.isEmpty() ? nomeOriginal : novoNome;

            String json = String.format("{\"nome\":\"%s\", \"ano\":%d, \"clubes\":%s}",
                                        finalNome,
                                        finalAno,
                                        clubesExistentesJson);
            
            sendPut("http://localhost:8000/campeonatos?nome_original=" + URLEncoder.encode(nomeOriginal, "UTF-8"), json);

        } catch (UnsupportedEncodingException e) {
            outputArea.setText("Erro de codificação: " + e.getMessage());
        } catch (NumberFormatException e) {
            outputArea.setText("Erro: Ano deve ser um número inteiro. " + e.getMessage());
        } catch (Exception e) {
            outputArea.setText("Erro ao atualizar campeonato: " + e.getMessage());
        }
    }


    private void deletarCampeonato() {
        String nome = delNomeCampField.getText();
        if (nome.isEmpty()) {
            outputArea.setText("Erro: O nome do campeonato é obrigatório para deletar.");
            return;
        }
        try {
            sendDelete("http://localhost:8000/campeonatos?nome=" + URLEncoder.encode(nome, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            outputArea.setText("Erro de codificação: " + e.getMessage());
        }
    }

    private void adicionarClubeAoCampeonato() {
        String nomeCampeonato = addCampNomeField.getText();
        String siglaClube = addClubeSiglaField.getText();
        if (nomeCampeonato.isEmpty() || siglaClube.isEmpty()) {
            outputArea.setText("Erro: Nome do campeonato e sigla do clube são obrigatórios.");
            return;
        }
        try {
            String url = "http://localhost:8000/campeonatos/adicionar_clube?nome_campeonato=" + URLEncoder.encode(nomeCampeonato, "UTF-8") + "&sigla_clube=" + URLEncoder.encode(siglaClube, "UTF-8");
            sendPost(url, "");
        } catch (UnsupportedEncodingException e) {
            outputArea.setText("Erro de codificação: " + e.getMessage());
        }
    }

    private void verClubesCampeonato() {
        String nome = listCampClubesNomeField.getText();
        if (nome.isEmpty()) {
            outputArea.setText("Erro: Nome do campeonato é obrigatório.");
            return;
        }
        try {
            String resposta = sendGet("http://localhost:8000/campeonatos/clubes_participantes?nome=" + URLEncoder.encode(nome, "UTF-8"));
            outputArea.setText(resposta);
        } catch (UnsupportedEncodingException e) {
            outputArea.setText("Erro de codificação: " + e.getMessage());
        }
    }

    private void registrarPartida() {
        String c1 = clube1Field.getText();
        String c2 = clube2Field.getText();
        String g1 = gols1Field.getText();
        String g2 = gols2Field.getText();
        String camp = campPartidaField.getText();

        if (c1.isEmpty() || c2.isEmpty() || g1.isEmpty() || g2.isEmpty() || camp.isEmpty()) {
            outputArea.setText("Erro: Todos os campos da partida são obrigatórios.");
            return;
        }

        String json = String.format("{\"sigla_clube1\":\"%s\", \"sigla_clube2\":\"%s\", \"gols1\":%s, \"gols2\":%s, \"campeonato\":\"%s\"}", c1, c2, g1, g2, camp);
        sendPost("http://localhost:8000/partidas", json);
    }

    private void verHistorico() {
        String sigla = historicoSiglaField.getText();
        if (sigla.isEmpty()) {
            outputArea.setText("Erro: A sigla do clube é obrigatória.");
            return;
        }
        String resposta = sendGet("http://localhost:8000/clubes/partidas?sigla=" + sigla);
        outputArea.setText(resposta);
    }

    private void sendPost(String url, String jsonInput) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            if (!jsonInput.isEmpty()) {
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonInput.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }
            }

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK && responseCode != HttpURLConnection.HTTP_CREATED) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    outputArea.setText("Erro (" + responseCode + "): " + response.toString());
                }
            } else {
                Scanner s = new Scanner(conn.getInputStream()).useDelimiter("\\A");
                String response = s.hasNext() ? s.next() : "";
                outputArea.setText(response);
            }
            conn.disconnect();
        } catch (Exception e) {
            outputArea.setText("Erro: " + e.getMessage());
        }
    }

    private String sendGet(String url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    return "Erro (" + responseCode + "): " + response.toString();
                }
            } else {
                Scanner s = new Scanner(conn.getInputStream()).useDelimiter("\\A");
                String response = s.hasNext() ? s.next() : "";
                conn.disconnect();
                return response;
            }
        } catch (Exception e) {
            return "Erro: " + e.getMessage();
        }
    }

    private void sendPut(String url, String jsonInput) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    outputArea.setText("Erro (" + responseCode + "): " + response.toString());
                }
            } else {
                Scanner s = new Scanner(conn.getInputStream()).useDelimiter("\\A");
                String response = s.hasNext() ? s.next() : "";
                outputArea.setText(response);
            }
            conn.disconnect();
        } catch (Exception e) {
            outputArea.setText("Erro: " + e.getMessage());
        }
    }

    private void sendDelete(String url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("DELETE");

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    outputArea.setText("Erro (" + responseCode + "): " + response.toString());
                }
            } else {
                Scanner s = new Scanner(conn.getInputStream()).useDelimiter("\\A");
                String response = s.hasNext() ? s.next() : "";
                outputArea.setText(response);
            }
            conn.disconnect();
        } catch (Exception e) {
            outputArea.setText("Erro: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FutebolClient().setVisible(true));
    }
}