package com.futebol.ui;

import com.futebol.models.Clube;
import com.futebol.models.Campeonato;
import com.futebol.models.Partida;
import com.futebol.services.ApiService;
import com.futebol.services.ApiService.ApiResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Interface gráfica principal do cliente Futebol Control
 */
public class FutebolClientUI extends JFrame {
    private final ApiService apiService;
    
    // Campos de entrada para clubes
    private JTextField nomeClubeField, siglaClubeField, siglaStatsField;
    private JTextField putSiglaOriginalClubeField, putNomeClubeField, putNovaSiglaClubeField;
    private JTextField delSiglaClubeField;

    // Campos de entrada para campeonatos
    private JTextField nomeCampField, anoCampField;
    private JTextField putNomeOriginalCampField, putNovoNomeCampField, putNovoAnoCampField;
    private JTextField delNomeCampField;
    private JTextField addCampNomeField, addClubeSiglaField;
    private JTextField listCampClubesNomeField;

    // Campos de entrada para partidas
    private JTextField clube1Field, clube2Field, gols1Field, gols2Field, campPartidaField;
    private JTextField historicoSiglaField;
    
    // Área de saída
    private JTextArea outputArea;

    public FutebolClientUI() {
        this.apiService = new ApiService();
        initializeUI();
    }

    public FutebolClientUI(String apiBaseUrl) {
        this.apiService = new ApiService(apiBaseUrl);
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Futebol Control - Cliente Java (Refatorado)");
        setSize(900, 950);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 1));

        setupClubeSection();
        setupCampeonatoSection();
        setupPartidaSection();
        setupOutputSection();
    }

    private void setupClubeSection() {
        add(new JLabel("=== GESTÃO DE CLUBES ==="));
        
        // Criar clube
        add(new JLabel("Criar Clube - Nome:"));
        nomeClubeField = new JTextField();
        add(nomeClubeField);
        add(new JLabel("Criar Clube - Sigla:"));
        siglaClubeField = new JTextField();
        add(siglaClubeField);
        add(createButton("Criar Clube", this::criarClube));

        // Ver estatísticas
        add(new JLabel("Ver Estatísticas - Sigla do Clube:"));
        siglaStatsField = new JTextField();
        add(siglaStatsField);
        add(createButton("Ver Estatísticas do Clube", this::verEstatisticas));

        // Atualizar clube
        add(new JLabel("Atualizar Clube - Sigla Original:"));
        putSiglaOriginalClubeField = new JTextField();
        add(putSiglaOriginalClubeField);
        add(new JLabel("Atualizar Clube - Novo Nome:"));
        putNomeClubeField = new JTextField();
        add(putNomeClubeField);
        add(new JLabel("Atualizar Clube - Nova Sigla:"));
        putNovaSiglaClubeField = new JTextField();
        add(putNovaSiglaClubeField);
        add(createButton("Atualizar Clube", this::atualizarClube));

        // Deletar clube
        add(new JLabel("Deletar Clube - Sigla:"));
        delSiglaClubeField = new JTextField();
        add(delSiglaClubeField);
        add(createButton("Deletar Clube", this::deletarClube));
    }

    private void setupCampeonatoSection() {
        add(new JLabel("=== GESTÃO DE CAMPEONATOS ==="));
        
        // Criar campeonato
        add(new JLabel("Criar Campeonato - Nome:"));
        nomeCampField = new JTextField();
        add(nomeCampField);
        add(new JLabel("Criar Campeonato - Ano:"));
        anoCampField = new JTextField();
        add(anoCampField);
        add(createButton("Criar Campeonato", this::criarCampeonato));

        // Atualizar campeonato
        add(new JLabel("Atualizar Campeonato - Nome Original:"));
        putNomeOriginalCampField = new JTextField();
        add(putNomeOriginalCampField);
        add(new JLabel("Atualizar Campeonato - Novo Nome:"));
        putNovoNomeCampField = new JTextField();
        add(putNovoNomeCampField);
        add(new JLabel("Atualizar Campeonato - Novo Ano:"));
        putNovoAnoCampField = new JTextField();
        add(putNovoAnoCampField);
        add(createButton("Atualizar Campeonato", this::atualizarCampeonato));

        // Deletar campeonato
        add(new JLabel("Deletar Campeonato - Nome:"));
        delNomeCampField = new JTextField();
        add(delNomeCampField);
        add(createButton("Deletar Campeonato", this::deletarCampeonato));

        // Adicionar clube ao campeonato
        add(new JLabel("Adicionar Clube ao Campeonato - Nome do Campeonato:"));
        addCampNomeField = new JTextField();
        add(addCampNomeField);
        add(new JLabel("Adicionar Clube ao Campeonato - Sigla do Clube:"));
        addClubeSiglaField = new JTextField();
        add(addClubeSiglaField);
        add(createButton("Adicionar Clube ao Campeonato", this::adicionarClubeAoCampeonato));

        // Ver clubes do campeonato
        add(new JLabel("Ver Clubes do Campeonato - Nome do Campeonato:"));
        listCampClubesNomeField = new JTextField();
        add(listCampClubesNomeField);
        add(createButton("Ver Clubes do Campeonato", this::verClubesCampeonato));
    }

    private void setupPartidaSection() {
        add(new JLabel("=== GESTÃO DE PARTIDAS ==="));
        
        // Registrar partida
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
        add(createButton("Registrar Partida", this::registrarPartida));

        // Ver histórico
        add(new JLabel("Ver Histórico - Sigla do Clube:"));
        historicoSiglaField = new JTextField();
        add(historicoSiglaField);
        add(createButton("Ver Histórico do Clube", this::verHistorico));
    }

    private void setupOutputSection() {
        add(new JLabel("=== SAÍDA ==="));
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setPreferredSize(new Dimension(800, 200));
        add(scrollPane);
        
        add(createButton("Limpar Saída", this::limparSaida));
    }

    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        return button;
    }

    // ==================== MÉTODOS DE AÇÃO - CLUBES ====================

    private void criarClube(ActionEvent e) {
        String nome = nomeClubeField.getText().trim();
        String sigla = siglaClubeField.getText().trim();
        
        if (nome.isEmpty() || sigla.isEmpty()) {
            mostrarErro("Nome e sigla são obrigatórios");
            return;
        }

        Clube clube = new Clube(nome, sigla);
        ApiResponse response = apiService.criarClube(clube);
        mostrarResposta(response);
        
        if (response.isSuccess()) {
            limparCampos(nomeClubeField, siglaClubeField);
        }
    }

    private void verEstatisticas(ActionEvent e) {
        String sigla = siglaStatsField.getText().trim();
        
        if (sigla.isEmpty()) {
            mostrarErro("Sigla é obrigatória");
            return;
        }

        ApiResponse<Clube> response = apiService.obterEstatisticasClube(sigla);
        if (response.isSuccess()) {
            Clube clube = response.getData();
            String detalhes = String.format(
                "=== ESTATÍSTICAS DO CLUBE ===\n" +
                "Clube: %s\n" +
                "Total de Jogos: %d\n" +
                "Pontos: %d\n" +
                "Saldo de Gols: %d\n" +
                "Detalhes: %s",
                clube.toString(),
                clube.getTotalJogos(),
                clube.getPontos(),
                clube.getSaldoGols(),
                response.getData()
            );
            outputArea.setText(detalhes);
        } else {
            mostrarResposta(response);
        }
    }

    private void atualizarClube(ActionEvent e) {
        String siglaOriginal = putSiglaOriginalClubeField.getText().trim();
        String novoNome = putNomeClubeField.getText().trim();
        String novaSigla = putNovaSiglaClubeField.getText().trim();
        
        if (siglaOriginal.isEmpty()) {
            mostrarErro("Sigla original é obrigatória");
            return;
        }

        // Obter dados atuais do clube
        ApiResponse<Clube> responseGet = apiService.obterEstatisticasClube(siglaOriginal);
        if (!responseGet.isSuccess()) {
            mostrarResposta(responseGet);
            return;
        }

        Clube clubeAtual = responseGet.getData();
        
        // Atualizar apenas os campos fornecidos
        if (!novoNome.isEmpty()) {
            clubeAtual.setNome(novoNome);
        }
        if (!novaSigla.isEmpty()) {
            clubeAtual.setSigla(novaSigla);
        }

        ApiResponse response = apiService.atualizarClube(siglaOriginal, clubeAtual);
        mostrarResposta(response);
        
        if (response.isSuccess()) {
            limparCampos(putSiglaOriginalClubeField, putNomeClubeField, putNovaSiglaClubeField);
        }
    }

    private void deletarClube(ActionEvent e) {
        String sigla = delSiglaClubeField.getText().trim();
        
        if (sigla.isEmpty()) {
            mostrarErro("Sigla é obrigatória");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Tem certeza que deseja deletar o clube " + sigla + "?",
            "Confirmar Exclusão",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            ApiResponse response = apiService.deletarClube(sigla);
            mostrarResposta(response);
            
            if (response.isSuccess()) {
                limparCampos(delSiglaClubeField);
            }
        }
    }

    // ==================== MÉTODOS DE AÇÃO - CAMPEONATOS ====================

    private void criarCampeonato(ActionEvent e) {
        String nome = nomeCampField.getText().trim();
        String anoStr = anoCampField.getText().trim();
        
        if (nome.isEmpty() || anoStr.isEmpty()) {
            mostrarErro("Nome e ano são obrigatórios");
            return;
        }

        try {
            int ano = Integer.parseInt(anoStr);
            Campeonato campeonato = new Campeonato(nome, ano);
            ApiResponse response = apiService.criarCampeonato(campeonato);
            mostrarResposta(response);
            
            if (response.isSuccess()) {
                limparCampos(nomeCampField, anoCampField);
            }
        } catch (NumberFormatException ex) {
            mostrarErro("Ano deve ser um número válido");
        }
    }

    private void atualizarCampeonato(ActionEvent e) {
        String nomeOriginal = putNomeOriginalCampField.getText().trim();
        String novoNome = putNovoNomeCampField.getText().trim();
        String novoAnoStr = putNovoAnoCampField.getText().trim();
        
        if (nomeOriginal.isEmpty()) {
            mostrarErro("Nome original é obrigatório");
            return;
        }

        // Obter dados atuais do campeonato
        ApiResponse<List<Campeonato>> responseList = apiService.listarCampeonatos();
        if (!responseList.isSuccess()) {
            mostrarResposta(responseList);
            return;
        }

        Campeonato campeonatoAtual = null;
        for (Campeonato camp : responseList.getData()) {
            if (camp.getNome().equals(nomeOriginal)) {
                campeonatoAtual = camp;
                break;
            }
        }

        if (campeonatoAtual == null) {
            mostrarErro("Campeonato não encontrado");
            return;
        }

        // Atualizar apenas os campos fornecidos
        if (!novoNome.isEmpty()) {
            campeonatoAtual.setNome(novoNome);
        }
        
        if (!novoAnoStr.isEmpty()) {
            try {
                int novoAno = Integer.parseInt(novoAnoStr);
                campeonatoAtual.setAno(novoAno);
            } catch (NumberFormatException ex) {
                mostrarErro("Ano deve ser um número válido");
                return;
            }
        }

        ApiResponse response = apiService.atualizarCampeonato(nomeOriginal, campeonatoAtual);
        mostrarResposta(response);
        
        if (response.isSuccess()) {
            limparCampos(putNomeOriginalCampField, putNovoNomeCampField, putNovoAnoCampField);
        }
    }

    private void deletarCampeonato(ActionEvent e) {
        String nome = delNomeCampField.getText().trim();
        
        if (nome.isEmpty()) {
            mostrarErro("Nome é obrigatório");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Tem certeza que deseja deletar o campeonato " + nome + "?",
            "Confirmar Exclusão",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            ApiResponse response = apiService.deletarCampeonato(nome);
            mostrarResposta(response);
            
            if (response.isSuccess()) {
                limparCampos(delNomeCampField);
            }
        }
    }

    private void adicionarClubeAoCampeonato(ActionEvent e) {
        String nomeCampeonato = addCampNomeField.getText().trim();
        String siglaClube = addClubeSiglaField.getText().trim();
        
        if (nomeCampeonato.isEmpty() || siglaClube.isEmpty()) {
            mostrarErro("Nome do campeonato e sigla do clube são obrigatórios");
            return;
        }

        ApiResponse response = apiService.adicionarClubeAoCampeonato(nomeCampeonato, siglaClube);
        mostrarResposta(response);
        
        if (response.isSuccess()) {
            limparCampos(addCampNomeField, addClubeSiglaField);
        }
    }

    private void verClubesCampeonato(ActionEvent e) {
        String nome = listCampClubesNomeField.getText().trim();
        
        if (nome.isEmpty()) {
            mostrarErro("Nome do campeonato é obrigatório");
            return;
        }

        ApiResponse<List<Clube>> response = apiService.listarClubesCampeonato(nome);
        if (response.isSuccess()) {
            StringBuilder sb = new StringBuilder();
            sb.append("=== CLUBES DO CAMPEONATO ").append(nome).append(" ===\n");
            
            List<Clube> clubes = response.getData();
            if (clubes.isEmpty()) {
                sb.append("Nenhum clube encontrado.\n");
            } else {
                for (int i = 0; i < clubes.size(); i++) {
                    Clube clube = clubes.get(i);
                    sb.append(String.format("%d. %s (Pontos: %d, Saldo: %d)\n", 
                                          i + 1, clube.toString(), clube.getPontos(), clube.getSaldoGols()));
                }
            }
            
            outputArea.setText(sb.toString());
        } else {
            mostrarResposta(response);
        }
    }

    // ==================== MÉTODOS DE AÇÃO - PARTIDAS ====================

    private void registrarPartida(ActionEvent e) {
        String siglaClube1 = clube1Field.getText().trim();
        String siglaClube2 = clube2Field.getText().trim();
        String gols1Str = gols1Field.getText().trim();
        String gols2Str = gols2Field.getText().trim();
        String campeonato = campPartidaField.getText().trim();
        
        if (siglaClube1.isEmpty() || siglaClube2.isEmpty() || gols1Str.isEmpty() || 
            gols2Str.isEmpty() || campeonato.isEmpty()) {
            mostrarErro("Todos os campos são obrigatórios");
            return;
        }

        if (siglaClube1.equals(siglaClube2)) {
            mostrarErro("Um clube não pode jogar contra si mesmo");
            return;
        }

        try {
            int gols1 = Integer.parseInt(gols1Str);
            int gols2 = Integer.parseInt(gols2Str);
            
            if (gols1 < 0 || gols2 < 0) {
                mostrarErro("Número de gols não pode ser negativo");
                return;
            }

            Partida partida = new Partida(siglaClube1, siglaClube2, gols1, gols2, campeonato);
            ApiResponse response = apiService.registrarPartida(partida);
            
            if (response.isSuccess()) {
                String resumo = String.format(
                    "=== PARTIDA REGISTRADA ===\n" +
                    "Jogo: %s\n" +
                    "Resultado: %s\n" +
                    "Vencedor: %s\n" +
                    "Total de Gols: %d\n" +
                    "Resposta da API: %s",
                    partida.toString(),
                    partida.getResultado(),
                    partida.getVencedor() != null ? partida.getVencedor() : "Empate",
                    partida.getTotalGols(),
                    response.getData()
                );
                outputArea.setText(resumo);
                limparCampos(clube1Field, clube2Field, gols1Field, gols2Field, campPartidaField);
            } else {
                mostrarResposta(response);
            }
        } catch (NumberFormatException ex) {
            mostrarErro("Gols devem ser números válidos");
        }
    }

    private void verHistorico(ActionEvent e) {
        String sigla = historicoSiglaField.getText().trim();
        
        if (sigla.isEmpty()) {
            mostrarErro("Sigla é obrigatória");
            return;
        }

        ApiResponse<List<Partida>> response = apiService.obterHistoricoClube(sigla);
        if (response.isSuccess()) {
            StringBuilder sb = new StringBuilder();
            sb.append("=== HISTÓRICO DO CLUBE ").append(sigla).append(" ===\n");
            
            List<Partida> partidas = response.getData();
            if (partidas.isEmpty()) {
                sb.append("Nenhuma partida encontrada.\n");
            } else {
                for (int i = 0; i < partidas.size(); i++) {
                    Partida partida = partidas.get(i);
                    sb.append(String.format("%d. %s - %s (Total: %d gols)\n", 
                                          i + 1, partida.toString(), partida.getResultado(), partida.getTotalGols()));
                }
            }
            
            outputArea.setText(sb.toString());
        } else {
            mostrarResposta(response);
        }
    }

    // ==================== MÉTODOS UTILITÁRIOS ====================

    private void mostrarResposta(ApiResponse response) {
        String resultado = String.format(
            "Status: %s\n" +
            "Mensagem: %s\n" +
            "Dados: %s",
            response.isSuccess() ? "SUCESSO" : "ERRO",
            response.getMessage(),
            response.getData() != null ? response.getData().toString() : "N/A"
        );
        outputArea.setText(resultado);
    }

    private void mostrarErro(String mensagem) {
        outputArea.setText("ERRO: " + mensagem);
        JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    private void limparCampos(JTextField... campos) {
        for (JTextField campo : campos) {
            campo.setText("");
        }
    }

    private void limparSaida(ActionEvent e) {
        outputArea.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FutebolClientUI().setVisible(true));
    }
}
