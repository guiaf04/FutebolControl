package com.futebol.ui;

import com.futebol.models.Clube;
import com.futebol.services.ApiService;
import com.futebol.services.ApiService.ApiResponse;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Janela para gestão de clubes
 */
public class ClubeWindow extends JFrame {
    private final ApiService apiService;
    
    // Campos de entrada
    private JTextField nomeClubeField, siglaClubeField;
    private JTextField siglaStatsField;
    private JTextField putSiglaOriginalField, putNovoNomeField, putNovaSiglaField;
    private JTextField delSiglaField;
    private JTextField historicoSiglaField;
    
    // Tabela para mostrar clubes
    private JTable tabelaClubes;
    private DefaultTableModel modeloTabela;
    
    // Área de saída
    private JTextArea outputArea;

    public ClubeWindow(ApiService apiService) {
        this.apiService = apiService;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Gestão de Clubes");
        setSize(800, 700);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel principal com abas
        JTabbedPane tabbedPane = new JTabbedPane();
        
        tabbedPane.addTab("Listar Clubes", createListarPanel());
        tabbedPane.addTab("Criar Clube", createCriarPanel());
        tabbedPane.addTab("Estatísticas", createEstatisticasPanel());
        tabbedPane.addTab("Atualizar", createAtualizarPanel());
        tabbedPane.addTab("Deletar", createDeletarPanel());
        tabbedPane.addTab("Histórico", createHistoricoPanel());
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Área de saída na parte inferior
        outputArea = new JTextArea(8, 50);
        outputArea.setEditable(false);
        outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Resultado"));
        add(scrollPane, BorderLayout.SOUTH);
        
        // Carregar dados iniciais
        listarClubes();
    }

    private JPanel createListarPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Botão para atualizar
        JButton btnAtualizar = new JButton("Atualizar Lista");
        btnAtualizar.addActionListener(e -> listarClubes());
        panel.add(btnAtualizar, BorderLayout.NORTH);
        
        // Tabela
        String[] colunas = {"Nome", "Sigla", "Vitórias", "Empates", "Derrotas", "Gols Pró", "Gols Contra", "Pontos", "Saldo"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaClubes = new JTable(modeloTabela);
        tabelaClubes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollTable = new JScrollPane(tabelaClubes);
        panel.add(scrollTable, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createCriarPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        nomeClubeField = new JTextField(20);
        panel.add(nomeClubeField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Sigla:"), gbc);
        gbc.gridx = 1;
        siglaClubeField = new JTextField(20);
        panel.add(siglaClubeField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JButton btnCriar = new JButton("Criar Clube");
        btnCriar.addActionListener(this::criarClube);
        panel.add(btnCriar, gbc);
        
        return panel;
    }

    private JPanel createEstatisticasPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Sigla do Clube:"), gbc);
        gbc.gridx = 1;
        siglaStatsField = new JTextField(20);
        panel.add(siglaStatsField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        JButton btnStats = new JButton("Ver Estatísticas");
        btnStats.addActionListener(this::verEstatisticas);
        panel.add(btnStats, gbc);
        
        return panel;
    }

    private JPanel createAtualizarPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Sigla Original:"), gbc);
        gbc.gridx = 1;
        putSiglaOriginalField = new JTextField(20);
        panel.add(putSiglaOriginalField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Novo Nome:"), gbc);
        gbc.gridx = 1;
        putNovoNomeField = new JTextField(20);
        panel.add(putNovoNomeField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Nova Sigla:"), gbc);
        gbc.gridx = 1;
        putNovaSiglaField = new JTextField(20);
        panel.add(putNovaSiglaField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        JButton btnAtualizar = new JButton("Atualizar Clube");
        btnAtualizar.addActionListener(this::atualizarClube);
        panel.add(btnAtualizar, gbc);
        
        return panel;
    }

    private JPanel createDeletarPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Sigla:"), gbc);
        gbc.gridx = 1;
        delSiglaField = new JTextField(20);
        panel.add(delSiglaField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        JButton btnDeletar = new JButton("Deletar Clube");
        btnDeletar.addActionListener(this::deletarClube);
        btnDeletar.setBackground(Color.RED);
        btnDeletar.setForeground(Color.WHITE);
        panel.add(btnDeletar, gbc);
        
        return panel;
    }

    private JPanel createHistoricoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Sigla do Clube:"), gbc);
        gbc.gridx = 1;
        historicoSiglaField = new JTextField(20);
        panel.add(historicoSiglaField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        JButton btnHistorico = new JButton("Ver Histórico");
        btnHistorico.addActionListener(this::verHistorico);
        panel.add(btnHistorico, gbc);
        
        return panel;
    }

    // ==================== MÉTODOS DE AÇÃO ====================

    private void listarClubes() {
        ApiResponse<List<Clube>> response = apiService.listarClubes();
        if (response.isSuccess()) {
            modeloTabela.setRowCount(0);
            for (Clube clube : response.getData()) {
                Object[] row = {
                    clube.getNome(),
                    clube.getSigla(),
                    clube.getVitorias(),
                    clube.getEmpates(),
                    clube.getDerrotas(),
                    clube.getGolsPro(),
                    clube.getGolsContra(),
                    clube.getPontos(),
                    clube.getSaldoGols()
                };
                modeloTabela.addRow(row);
            }
            mostrarResposta("Lista atualizada com " + response.getData().size() + " clubes.");
        } else {
            mostrarErro("Erro ao listar clubes: " + response.getMessage());
        }
    }

    private void criarClube(ActionEvent e) {
        String nome = nomeClubeField.getText().trim();
        String sigla = siglaClubeField.getText().trim();
        
        if (nome.isEmpty() || sigla.isEmpty()) {
            mostrarErro("Nome e sigla são obrigatórios");
            return;
        }

        Clube clube = new Clube(nome, sigla);
        ApiResponse response = apiService.criarClube(clube);
        
        if (response.isSuccess()) {
            mostrarResposta("Clube criado com sucesso!");
            nomeClubeField.setText("");
            siglaClubeField.setText("");
            listarClubes(); // Atualizar lista
        } else {
            mostrarErro("Erro ao criar clube: " + response.getMessage());
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
                "Nome: %s\n" +
                "Sigla: %s\n" +
                "Vitórias: %d\n" +
                "Empates: %d\n" +
                "Derrotas: %d\n" +
                "Gols Pró: %d\n" +
                "Gols Contra: %d\n" +
                "Total de Jogos: %d\n" +
                "Pontos: %d\n" +
                "Saldo de Gols: %d",
                clube.getNome(),
                clube.getSigla(),
                clube.getVitorias(),
                clube.getEmpates(),
                clube.getDerrotas(),
                clube.getGolsPro(),
                clube.getGolsContra(),
                clube.getTotalJogos(),
                clube.getPontos(),
                clube.getSaldoGols()
            );
            outputArea.setText(detalhes);
        } else {
            mostrarErro("Erro ao obter estatísticas: " + response.getMessage());
        }
    }

    private void atualizarClube(ActionEvent e) {
        String siglaOriginal = putSiglaOriginalField.getText().trim();
        String novoNome = putNovoNomeField.getText().trim();
        String novaSigla = putNovaSiglaField.getText().trim();
        
        if (siglaOriginal.isEmpty()) {
            mostrarErro("Sigla original é obrigatória");
            return;
        }

        // Obter dados atuais do clube
        ApiResponse<Clube> responseGet = apiService.obterEstatisticasClube(siglaOriginal);
        if (!responseGet.isSuccess()) {
            mostrarErro("Erro ao obter dados do clube: " + responseGet.getMessage());
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
        
        if (response.isSuccess()) {
            mostrarResposta("Clube atualizado com sucesso!");
            putSiglaOriginalField.setText("");
            putNovoNomeField.setText("");
            putNovaSiglaField.setText("");
            listarClubes(); // Atualizar lista
        } else {
            mostrarErro("Erro ao atualizar clube: " + response.getMessage());
        }
    }

    private void deletarClube(ActionEvent e) {
        String sigla = delSiglaField.getText().trim();
        
        if (sigla.isEmpty()) {
            mostrarErro("Sigla é obrigatória");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Tem certeza que deseja deletar o clube " + sigla + "?",
            "Confirmar Exclusão",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            ApiResponse response = apiService.deletarClube(sigla);
            
            if (response.isSuccess()) {
                mostrarResposta("Clube deletado com sucesso!");
                delSiglaField.setText("");
                listarClubes(); // Atualizar lista
            } else {
                mostrarErro("Erro ao deletar clube: " + response.getMessage());
            }
        }
    }

    private void verHistorico(ActionEvent e) {
        String sigla = historicoSiglaField.getText().trim();
        
        if (sigla.isEmpty()) {
            mostrarErro("Sigla é obrigatória");
            return;
        }

        ApiResponse response = apiService.obterHistoricoClube(sigla);
        if (response.isSuccess()) {
            StringBuilder sb = new StringBuilder();
            sb.append("=== HISTÓRICO DO CLUBE ").append(sigla).append(" ===\n");
            
            Object data = response.getData();
            if (data != null) {
                sb.append(data.toString());
            } else {
                sb.append("Nenhuma partida encontrada.");
            }
            
            outputArea.setText(sb.toString());
        } else {
            mostrarErro("Erro ao obter histórico: " + response.getMessage());
        }
    }

    // ==================== MÉTODOS UTILITÁRIOS ====================

    private void mostrarResposta(String mensagem) {
        outputArea.setText(mensagem);
    }

    private void mostrarErro(String mensagem) {
        outputArea.setText("ERRO: " + mensagem);
        JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
