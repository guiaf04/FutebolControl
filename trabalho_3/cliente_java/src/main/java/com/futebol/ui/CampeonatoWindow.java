package com.futebol.ui;

import com.futebol.models.Campeonato;
import com.futebol.models.Clube;
import com.futebol.services.ApiService;
import com.futebol.services.ApiService.ApiResponse;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Janela para gestão de campeonatos
 */
public class CampeonatoWindow extends JFrame {
    private final ApiService apiService;
    
    // Campos de entrada
    private JTextField nomeCampField, anoCampField;
    private JTextField putNomeOriginalField, putNovoNomeField, putNovoAnoField;
    private JTextField delNomeField;
    private JTextField addCampNomeField, addClubeSiglaField;
    private JTextField listCampClubesField;
    
    // Tabelas
    private JTable tabelaCampeonatos;
    private DefaultTableModel modeloTabelaCampeonatos;
    private JTable tabelaClubesCampeonato;
    private DefaultTableModel modeloTabelaClubes;
    
    // Área de saída
    private JTextArea outputArea;

    public CampeonatoWindow(ApiService apiService) {
        this.apiService = apiService;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Gestão de Campeonatos");
        setSize(900, 750);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel principal com abas
        JTabbedPane tabbedPane = new JTabbedPane();
        
        tabbedPane.addTab("Listar Campeonatos", createListarPanel());
        tabbedPane.addTab("Criar Campeonato", createCriarPanel());
        tabbedPane.addTab("Atualizar", createAtualizarPanel());
        tabbedPane.addTab("Deletar", createDeletarPanel());
        tabbedPane.addTab("Gerenciar Clubes", createGerenciarClubesPanel());
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Área de saída na parte inferior
        outputArea = new JTextArea(8, 50);
        outputArea.setEditable(false);
        outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Resultado"));
        add(scrollPane, BorderLayout.SOUTH);
        
        // Carregar dados iniciais
        listarCampeonatos();
    }

    private JPanel createListarPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Botão para atualizar
        JButton btnAtualizar = new JButton("Atualizar Lista");
        btnAtualizar.addActionListener(e -> listarCampeonatos());
        panel.add(btnAtualizar, BorderLayout.NORTH);
        
        // Tabela
        String[] colunas = {"Nome", "Ano", "Número de Clubes"};
        modeloTabelaCampeonatos = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaCampeonatos = new JTable(modeloTabelaCampeonatos);
        tabelaCampeonatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollTable = new JScrollPane(tabelaCampeonatos);
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
        nomeCampField = new JTextField(20);
        panel.add(nomeCampField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Ano:"), gbc);
        gbc.gridx = 1;
        anoCampField = new JTextField(20);
        panel.add(anoCampField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JButton btnCriar = new JButton("Criar Campeonato");
        btnCriar.addActionListener(this::criarCampeonato);
        panel.add(btnCriar, gbc);
        
        return panel;
    }

    private JPanel createAtualizarPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nome Original:"), gbc);
        gbc.gridx = 1;
        putNomeOriginalField = new JTextField(20);
        panel.add(putNomeOriginalField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Novo Nome:"), gbc);
        gbc.gridx = 1;
        putNovoNomeField = new JTextField(20);
        panel.add(putNovoNomeField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Novo Ano:"), gbc);
        gbc.gridx = 1;
        putNovoAnoField = new JTextField(20);
        panel.add(putNovoAnoField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        JButton btnAtualizar = new JButton("Atualizar Campeonato");
        btnAtualizar.addActionListener(this::atualizarCampeonato);
        panel.add(btnAtualizar, gbc);
        
        return panel;
    }

    private JPanel createDeletarPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        delNomeField = new JTextField(20);
        panel.add(delNomeField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        JButton btnDeletar = new JButton("Deletar Campeonato");
        btnDeletar.addActionListener(this::deletarCampeonato);
        btnDeletar.setBackground(Color.RED);
        btnDeletar.setForeground(Color.WHITE);
        panel.add(btnDeletar, gbc);
        
        return panel;
    }

    private JPanel createGerenciarClubesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Painel superior para adicionar clube
        JPanel addPanel = new JPanel(new GridBagLayout());
        addPanel.setBorder(BorderFactory.createTitledBorder("Adicionar Clube ao Campeonato"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        addPanel.add(new JLabel("Nome do Campeonato:"), gbc);
        gbc.gridx = 1;
        addCampNomeField = new JTextField(15);
        addPanel.add(addCampNomeField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        addPanel.add(new JLabel("Sigla do Clube:"), gbc);
        gbc.gridx = 1;
        addClubeSiglaField = new JTextField(15);
        addPanel.add(addClubeSiglaField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JButton btnAdicionar = new JButton("Adicionar Clube");
        btnAdicionar.addActionListener(this::adicionarClube);
        addPanel.add(btnAdicionar, gbc);
        
        panel.add(addPanel, BorderLayout.NORTH);
        
        // Painel central para listar clubes
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBorder(BorderFactory.createTitledBorder("Clubes do Campeonato"));
        
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Campeonato:"));
        listCampClubesField = new JTextField(15);
        searchPanel.add(listCampClubesField);
        JButton btnListar = new JButton("Listar Clubes");
        btnListar.addActionListener(this::listarClubesCampeonato);
        searchPanel.add(btnListar);
        
        listPanel.add(searchPanel, BorderLayout.NORTH);
        
        // Tabela de clubes
        String[] colunasClubes = {"Nome", "Sigla", "Pontos", "Saldo de Gols"};
        modeloTabelaClubes = new DefaultTableModel(colunasClubes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaClubesCampeonato = new JTable(modeloTabelaClubes);
        JScrollPane scrollClubes = new JScrollPane(tabelaClubesCampeonato);
        listPanel.add(scrollClubes, BorderLayout.CENTER);
        
        panel.add(listPanel, BorderLayout.CENTER);
        
        return panel;
    }

    // ==================== MÉTODOS DE AÇÃO ====================

    private void listarCampeonatos() {
        ApiResponse<List<Campeonato>> response = apiService.listarCampeonatos();
        if (response.isSuccess()) {
            modeloTabelaCampeonatos.setRowCount(0);
            for (Campeonato campeonato : response.getData()) {
                Object[] row = {
                    campeonato.getNome(),
                    campeonato.getAno(),
                    campeonato.getNumeroTotalClubes()
                };
                modeloTabelaCampeonatos.addRow(row);
            }
            mostrarResposta("Lista atualizada com " + response.getData().size() + " campeonatos.");
        } else {
            mostrarErro("Erro ao listar campeonatos: " + response.getMessage());
        }
    }

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
            
            if (response.isSuccess()) {
                mostrarResposta("Campeonato criado com sucesso!");
                nomeCampField.setText("");
                anoCampField.setText("");
                listarCampeonatos(); // Atualizar lista
            } else {
                mostrarErro("Erro ao criar campeonato: " + response.getMessage());
            }
        } catch (NumberFormatException ex) {
            mostrarErro("Ano deve ser um número válido");
        }
    }

    private void atualizarCampeonato(ActionEvent e) {
        String nomeOriginal = putNomeOriginalField.getText().trim();
        String novoNome = putNovoNomeField.getText().trim();
        String novoAnoStr = putNovoAnoField.getText().trim();
        
        if (nomeOriginal.isEmpty()) {
            mostrarErro("Nome original é obrigatório");
            return;
        }

        // Obter dados atuais do campeonato
        ApiResponse<List<Campeonato>> responseList = apiService.listarCampeonatos();
        if (!responseList.isSuccess()) {
            mostrarErro("Erro ao obter dados dos campeonatos: " + responseList.getMessage());
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
        
        if (response.isSuccess()) {
            mostrarResposta("Campeonato atualizado com sucesso!");
            putNomeOriginalField.setText("");
            putNovoNomeField.setText("");
            putNovoAnoField.setText("");
            listarCampeonatos(); // Atualizar lista
        } else {
            mostrarErro("Erro ao atualizar campeonato: " + response.getMessage());
        }
    }

    private void deletarCampeonato(ActionEvent e) {
        String nome = delNomeField.getText().trim();
        
        if (nome.isEmpty()) {
            mostrarErro("Nome é obrigatório");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Tem certeza que deseja deletar o campeonato " + nome + "?",
            "Confirmar Exclusão",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            ApiResponse response = apiService.deletarCampeonato(nome);
            
            if (response.isSuccess()) {
                mostrarResposta("Campeonato deletado com sucesso!");
                delNomeField.setText("");
                listarCampeonatos(); // Atualizar lista
            } else {
                mostrarErro("Erro ao deletar campeonato: " + response.getMessage());
            }
        }
    }

    private void adicionarClube(ActionEvent e) {
        String nomeCampeonato = addCampNomeField.getText().trim();
        String siglaClube = addClubeSiglaField.getText().trim();
        
        if (nomeCampeonato.isEmpty() || siglaClube.isEmpty()) {
            mostrarErro("Nome do campeonato e sigla do clube são obrigatórios");
            return;
        }

        ApiResponse response = apiService.adicionarClubeAoCampeonato(nomeCampeonato, siglaClube);
        
        if (response.isSuccess()) {
            mostrarResposta("Clube adicionado ao campeonato com sucesso!");
            addCampNomeField.setText("");
            addClubeSiglaField.setText("");
            listarCampeonatos(); // Atualizar lista
        } else {
            mostrarErro("Erro ao adicionar clube: " + response.getMessage());
        }
    }

    private void listarClubesCampeonato(ActionEvent e) {
        String nome = listCampClubesField.getText().trim();
        
        if (nome.isEmpty()) {
            mostrarErro("Nome do campeonato é obrigatório");
            return;
        }

        ApiResponse<List<Clube>> response = apiService.listarClubesCampeonato(nome);
        if (response.isSuccess()) {
            modeloTabelaClubes.setRowCount(0);
            for (Clube clube : response.getData()) {
                Object[] row = {
                    clube.getNome(),
                    clube.getSigla(),
                    clube.getPontos(),
                    clube.getSaldoGols()
                };
                modeloTabelaClubes.addRow(row);
            }
            mostrarResposta("Campeonato " + nome + " tem " + response.getData().size() + " clubes.");
        } else {
            mostrarErro("Erro ao listar clubes do campeonato: " + response.getMessage());
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
