package com.futebol.ui;

import com.futebol.models.Partida;
import com.futebol.services.ApiService;
import com.futebol.services.ApiService.ApiResponse;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Janela para gestão de partidas
 */
public class PartidaWindow extends JFrame {
    private final ApiService apiService;
    
    // Campos de entrada
    private JTextField clube1Field, clube2Field, gols1Field, gols2Field, campeonatoField;
    private JTextField historicoSiglaField;
    
    // Tabela para mostrar histórico
    private JTable tabelaHistorico;
    private DefaultTableModel modeloTabela;
    
    // Área de saída
    private JTextArea outputArea;

    public PartidaWindow(ApiService apiService) {
        this.apiService = apiService;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Gestão de Partidas");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel principal com abas
        JTabbedPane tabbedPane = new JTabbedPane();
        
        tabbedPane.addTab("Registrar Partida", createRegistrarPanel());
        tabbedPane.addTab("Histórico do Clube", createHistoricoPanel());
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Área de saída na parte inferior
        outputArea = new JTextArea(8, 50);
        outputArea.setEditable(false);
        outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Resultado"));
        add(scrollPane, BorderLayout.SOUTH);
    }

    private JPanel createRegistrarPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Título
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel titleLabel = new JLabel("Registrar Nova Partida");
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        panel.add(titleLabel, gbc);
        
        gbc.gridwidth = 1;
        
        // Clube 1
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Clube 1 (Sigla):"), gbc);
        gbc.gridx = 1;
        clube1Field = new JTextField(20);
        panel.add(clube1Field, gbc);
        
        // Gols Clube 1
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Gols Clube 1:"), gbc);
        gbc.gridx = 1;
        gols1Field = new JTextField(20);
        panel.add(gols1Field, gbc);
        
        // Separador visual
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(new JLabel("X"), gbc);
        gbc.gridwidth = 1;
        
        // Clube 2
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Clube 2 (Sigla):"), gbc);
        gbc.gridx = 1;
        clube2Field = new JTextField(20);
        panel.add(clube2Field, gbc);
        
        // Gols Clube 2
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(new JLabel("Gols Clube 2:"), gbc);
        gbc.gridx = 1;
        gols2Field = new JTextField(20);
        panel.add(gols2Field, gbc);
        
        // Campeonato
        gbc.gridx = 0; gbc.gridy = 6;
        panel.add(new JLabel("Campeonato:"), gbc);
        gbc.gridx = 1;
        campeonatoField = new JTextField(20);
        panel.add(campeonatoField, gbc);
        
        // Botão registrar
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        JButton btnRegistrar = new JButton("Registrar Partida");
        btnRegistrar.addActionListener(this::registrarPartida);
        btnRegistrar.setBackground(new Color(34, 139, 34));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        panel.add(btnRegistrar, gbc);
        
        // Painel de exemplo
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        JPanel exemploPanel = new JPanel();
        exemploPanel.setBorder(BorderFactory.createTitledBorder("Exemplo"));
        exemploPanel.setLayout(new BoxLayout(exemploPanel, BoxLayout.Y_AXIS));
        exemploPanel.add(new JLabel("Clube 1: FLA"));
        exemploPanel.add(new JLabel("Gols 1: 2"));
        exemploPanel.add(new JLabel("Clube 2: CAM"));
        exemploPanel.add(new JLabel("Gols 2: 1"));
        exemploPanel.add(new JLabel("Campeonato: Brasileirão 2024"));
        panel.add(exemploPanel, gbc);
        
        return panel;
    }

    private JPanel createHistoricoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Painel superior para busca
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder("Buscar Histórico"));
        searchPanel.add(new JLabel("Sigla do Clube:"));
        historicoSiglaField = new JTextField(15);
        searchPanel.add(historicoSiglaField);
        JButton btnBuscar = new JButton("Buscar Histórico");
        btnBuscar.addActionListener(this::verHistorico);
        searchPanel.add(btnBuscar);
        
        panel.add(searchPanel, BorderLayout.NORTH);
        
        // Tabela para mostrar histórico
        String[] colunas = {"Clube 1", "Gols 1", "Clube 2", "Gols 2", "Resultado", "Campeonato"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaHistorico = new JTable(modeloTabela);
        tabelaHistorico.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Personalizar renderização da tabela
        tabelaHistorico.setRowHeight(25);
        tabelaHistorico.getTableHeader().setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        
        JScrollPane scrollTable = new JScrollPane(tabelaHistorico);
        scrollTable.setBorder(BorderFactory.createTitledBorder("Histórico de Partidas"));
        panel.add(scrollTable, BorderLayout.CENTER);
        
        return panel;
    }

    // ==================== MÉTODOS DE AÇÃO ====================

    private void registrarPartida(ActionEvent e) {
        String siglaClube1 = clube1Field.getText().trim();
        String siglaClube2 = clube2Field.getText().trim();
        String gols1Str = gols1Field.getText().trim();
        String gols2Str = gols2Field.getText().trim();
        String campeonato = campeonatoField.getText().trim();
        
        // Validações
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
            
            if (!partida.isValid()) {
                mostrarErro("Dados da partida são inválidos");
                return;
            }
            
            ApiResponse response = apiService.registrarPartida(partida);
            
            if (response.isSuccess()) {
                String resumo = String.format(
                    "=== PARTIDA REGISTRADA COM SUCESSO ===\n" +
                    "Jogo: %s %d x %d %s\n" +
                    "Resultado: %s\n" +
                    "Vencedor: %s\n" +
                    "Total de Gols: %d\n" +
                    "Campeonato: %s\n" +
                    "Status: %s",
                    siglaClube1, gols1, gols2, siglaClube2,
                    partida.getResultado(),
                    partida.getVencedor(),
                    partida.getTotalGols(),
                    campeonato,
                    response.getMessage()
                );
                
                outputArea.setText(resumo);
                
                // Limpar campos
                clube1Field.setText("");
                clube2Field.setText("");
                gols1Field.setText("");
                gols2Field.setText("");
                campeonatoField.setText("");
                
                // Mostrar confirmação visual
                JOptionPane.showMessageDialog(this, 
                    "Partida registrada com sucesso!", 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
                    
            } else {
                mostrarErro("Erro ao registrar partida: " + response.getMessage());
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
            modeloTabela.setRowCount(0);
            
            List<Partida> partidas = response.getData();
            if (partidas != null && !partidas.isEmpty()) {
                for (Partida partida : partidas) {
                    Object[] row = {
                        partida.getSiglaClube1(),
                        partida.getGols1(),
                        partida.getSiglaClube2(),
                        partida.getGols2(),
                        partida.getResultado(),
                        partida.getCampeonato()
                    };
                    modeloTabela.addRow(row);
                }
                
                // Estatísticas do histórico
                int totalPartidas = partidas.size();
                int vitorias = 0;
                int empates = 0;
                int derrotas = 0;
                
                for (Partida partida : partidas) {
                    if (partida.getVencedor().equals(sigla)) {
                        vitorias++;
                    } else if (partida.getVencedor().equals("EMPATE")) {
                        empates++;
                    } else {
                        derrotas++;
                    }
                }
                
                String estatisticas = String.format(
                    "=== HISTÓRICO DO CLUBE %s ===\n" +
                    "Total de Partidas: %d\n" +
                    "Vitórias: %d\n" +
                    "Empates: %d\n" +
                    "Derrotas: %d\n" +
                    "Aproveitamento: %.1f%%",
                    sigla, totalPartidas, vitorias, empates, derrotas,
                    totalPartidas > 0 ? (vitorias * 3.0 + empates) / (totalPartidas * 3.0) * 100 : 0
                );
                
                outputArea.setText(estatisticas);
            } else {
                modeloTabela.setRowCount(0);
                outputArea.setText("Nenhuma partida encontrada para o clube " + sigla);
            }
        } else {
            mostrarErro("Erro ao obter histórico: " + response.getMessage());
            modeloTabela.setRowCount(0);
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
